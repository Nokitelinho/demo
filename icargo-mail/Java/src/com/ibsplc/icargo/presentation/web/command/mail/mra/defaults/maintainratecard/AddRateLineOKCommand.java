/*
 * AddRateLineOKCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.shared.airportpair.vo.AirportPairVO;
import com.ibsplc.icargo.business.shared.citypair.vo.CityPairVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airportpair.AirportPairDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.citypair.CityPairDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class AddRateLineOKCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AddRateLineOkCommand ");
	private static final String CLASS_NAME = "AddRateLineOkCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String ACTION_FAILURE = "screenload_failure";
	static final String INVALIDCITYPAIR="shared.citypair.invalidcitypair";

	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("AddRateLIneOKCommand","execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO error=null;
    	MaintainUPURateCardSession session=null;
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		String opFlags[]=form.getOperationFlag();
   		String origin[]=new String[opFlags.length-1];
   		String destn[]=new String[opFlags.length-1];
   		ArrayList<RateLineVO> rates=null;
   		rates=new ArrayList<RateLineVO>();
   		Page<RateLineVO> ratelinevopage=null;
   		log.log(Log.INFO, "orgin length ", form.getPopupOrigin().length);
		int len=form.getPopupOrigin().length;
   		for(int i=0;i<len;i++){
   		//	log.log(Log.INFO,"value of noopflag "+opFlags[i]+" at position "+i);
   			if(!(("NOOP").equals(opFlags[i]))&&!(("D").equals(opFlags[i]))){
   				log.log(Log.INFO, "value of i inside loop", i);
				log.log(Log.INFO, "orgin", form.getPopupOrigin(), i);
				log.log(Log.INFO, "destn", form.getPopupDestn(), i);
				origin[i]=form.getPopupOrigin()[i];
   				destn[i]=form.getPopupDestn()[i];
   			} else {
				len=len-1;
			}
   		}
   		form.setOkFlag("OK");
   		int flag=0;
   		int j=0;
   		Double iataKm[]=new Double[100];
   		
			ArrayList<String> orgdst=new ArrayList<String>();
			
			for(int i=0;i<origin.length;i++){
   				if(origin[i]!=null && origin[i].trim().length()>0 && destn[i]!=null && destn[i].trim().length()>0){
   				RateLineVO rateline=new RateLineVO();
   				rateline.setOrigin(origin[i]);
   				rateline.setDestination(destn[i]);
   				rateline.setOrgDstLevel(form.getOrgDstLevel());
   				rates.add(rateline);
   				log.log(Log.INFO, "rateline ", rateline);
				if(!(orgdst.contains(origin[i]))) {
					orgdst.add(origin[i]);
				}
   				if(!(orgdst.contains(destn[i]))) {
					orgdst.add(destn[i]);
				}
   				}
   			}
   			log.log(Log.INFO, "size of rate ", rates.size());
			int siz=rates.size();
   			for(int i=0;i<siz;i++){
   	   			
   	   			ratelinevopage =new Page<RateLineVO>(rates,1,0,rates.size(),0,0,false);
   	   			}
   	   		
   	   		session.setNewRateLineDetails(ratelinevopage);
   			
   			//popOrigin=rates.get(siz-1).getOrigin();
		//	popDst=rates.get(siz-1).getDestination();
			
			
	   	/*	if(popOrigin.equals(popDst)){
	   			orgdst.add(popOrigin);
	   		}
	   		else{
	   			orgdst.add(popOrigin);
	   			orgdst.add(popDst);
	   		}*/
		   
	   	 try{
	   	    	AreaDelegate areaDelegate = new AreaDelegate();
	   	    	if("A".equals(form.getOrgDstLevel())){
	   	    		areaDelegate.validateAirportCodes(companyCode,orgdst);
	   	    	}else{
	   	    	areaDelegate.validateCityCodes(companyCode,orgdst);
	   	    	}
	   	    }
	   	    catch(BusinessDelegateException businessDelegateException){
	   	    	log.log(Log.INFO,"validate city error");
	   	    	flag++;
	   	      errors=handleDelegateException(businessDelegateException);
	   	    }
   			if(flag==0){
   				try{
   					int ratesize=rates.size();
   				for(int i=0;i<ratesize;i++){
   					ArrayList<String> pair=new ArrayList<String>();
   					log.log(Log.INFO, "ratelinevos in rates ", rates.get(i));
					log.log(Log.INFO, "origin in rates ", rates.get(i).getOrigin());
					log.log(Log.INFO, "origin in rates ", rates.get(i).getDestination());
					if(rates.get(i).getOrigin()!=null && rates.get(i).getOrigin().trim().length()>0 
   							&& rates.get(i).getDestination()!=null && rates.get(i).getDestination().trim().length()>0){
   				StringBuilder sb=new StringBuilder(rates.get(i).getOrigin());
   				sb.append("-");
   				sb.append(rates.get(i).getDestination());
   				
   				String pairval=new String(sb);
   			//	if(!(citypair.contains(cpair)))
   				pair.add(pairval);
   				
   				if("C".equals(form.getOrgDstLevel())){
   				CityPairDelegate delegate = new CityPairDelegate();
   	   			ArrayList<CityPairVO> citypairvo =(ArrayList<CityPairVO>)
   	   							delegate.findCityPair(companyCode,pair);
   	   			
   	   		if(citypairvo!=null && !citypairvo.isEmpty()){
   				
   			//	int vosize=citypairvo.size();
   				
   		//	for(int j=0;j<vosize;j++){
   				
   				if(citypairvo.get(0)!=null){
   					
   					log.log(Log.INFO, "citypairvo ", citypairvo.get(0));
   					Measure cityPairDist = citypairvo.get(0).getCityPairDistance();
					iataKm[j]= cityPairDist != null ? cityPairDist.getSystemValue() : 0.0;
   			        log.log(Log.INFO, "iata ", iataKm, j);
					j++;
   					}
   	   			//}
   	   		}
   				}else{
   					AirportPairDelegate delegate = new AirportPairDelegate();
						ArrayList<AirportPairVO> airportPairVO = (ArrayList<AirportPairVO>)
								delegate.findAirportPair(companyCode,pair);
						if(airportPairVO != null && !airportPairVO.isEmpty()){
			   				
				   				
				   				if(airportPairVO.get(0)!=null){
				   					
				   					log.log(Log.INFO, "citypairvo ", airportPairVO.get(0));
				   					Measure airportPairDist = airportPairVO.get(0).getAirportPairDistance();
									iataKm[j]= airportPairDist != null ? airportPairDist.getSystemValue() : 0.0;
				   			        log.log(Log.INFO, "iata ", iataKm, j); 
									j++;
				   					}
				   	   			//}
				   	   		
							
						}
   				}
   		}
   				}
   				}
   				catch (BusinessDelegateException businessDelegateException) {
   					log.log(Log.INFO,"validate citypair error");
   					errors=handleDelegateException(businessDelegateException);
   					
   					
   				}
   	}
   			if(errors != null && errors.size() > 0 ){
   	   			form.setOkFlag("Not OK");
   	   			invocationContext.addAllError(errors);
   	   			invocationContext.target = ACTION_FAILURE;
   	   			return;
   	   		}
   			int ratsize=rates.size();
   			for(int i=0;i<ratsize;i++){
   				log.log(Log.INFO, "iata after loop ", iataKm, i);
			rates.get(i).setIataKilometre(iataKm[i]);
   			ratelinevopage =new Page<RateLineVO>(rates,1,0,rates.size(),0,0,false);
   			}
   		//}
   		session.setNewRateLineDetails(ratelinevopage);
   		Page<RateLineVO> ratelinevos=null;
   		if(session.getNewRateLineDetails()!=null){
   			ratelinevos=session.getNewRateLineDetails();
   		}
   		Page<RateLineVO> oldratespage=null;
   		if(session.getRateLineDetails()!=null){
   			oldratespage=session.getRateLineDetails();
   		}
   		RateCardVO ratecardvo=session.getRateCardDetails();
			RateLineVO ratelinevo=null;
			if((session.getNewRateLineDetails()!=null)&&(session.getNewRateLineDetails().size()>0)){
				  rates=new ArrayList<RateLineVO>(session.getNewRateLineDetails());
				int  rsize=rates.size();
			for(int i=0;i<rsize;i++){
				if(rates.get(i).getOrigin()!=null && rates.get(i).getOrigin().trim().length()>0 
							&& rates.get(i).getDestination()!=null && rates.get(i).getDestination().trim().length()>0){
			iataKm[i]=rates.get(i).getIataKilometre();
			
			
			ratelinevo=rates.get(i);
			ratelinevos.remove(ratelinevo);
			
			ratelinevo.setOperationFlag(RateLineVO.OPERATION_FLAG_INSERT);
			ratelinevo.setOrigin(rates.get(i).getOrigin());
			ratelinevo.setCompanyCode(companyCode);
			ratelinevo.setDestination(rates.get(i).getDestination());
			ratelinevo.setIataKilometre(iataKm[i]);
			ratelinevo.setRateCardID(ratecardvo.getRateCardID());
			Double mdf=ratecardvo.getMailDistanceFactor();
			
			
			
			ratelinevo.setMailKilometre(iataKm[i]*mdf);
			
			ratelinevo.setRateInSDRForCategoryRefOne(getScaledValue(iataKm[i]*mdf*((ratecardvo.getCategoryTonKMRefOne())/1000),4));
			ratelinevo.setRateInSDRForCategoryRefTwo(getScaledValue(iataKm[i]*mdf*((ratecardvo.getCategoryTonKMRefTwo())/1000),4));
		    ratelinevo.setRateInSDRForCategoryRefThree(getScaledValue(iataKm[i]*mdf*((ratecardvo.getCategoryTonKMRefThree())/1000),4));
			ratelinevo.setRatelineStatus("N");
			ratelinevo.setOrgDstLevel(form.getOrgDstLevel());
			rates.set(i,ratelinevo);
			ratelinevos.add(ratelinevo);
		
			
			session.setNewRateLineDetails(ratelinevos);
			if(session.getRateLineDetails()!=null){
				ArrayList<RateLineVO>	ratess=new ArrayList<RateLineVO>(session.getRateLineDetails()); 
				ratess.add(ratelinevo);
				int size=ratess.size();
				//Modification for Pagination done as part of ICRD-154268	
			oldratespage=new Page<RateLineVO>(ratess,1,25,size,0,0,false,size);
			session.setRateLineDetails(oldratespage);
			}
			
			}
			}
			if(session.getRateLineDetails()==null)
			   {    
			        siz=rates.size();
			        ArrayList<RateLineVO> newrates=new ArrayList<RateLineVO>(session.getNewRateLineDetails()); 
			        oldratespage=new Page<RateLineVO>(newrates,1,25,siz,0,0,false,siz);
		   			session.setRateLineDetails(oldratespage);
		   		}
			}
   		invocationContext.target = ACTION_SUCCESS;
		log.exiting("AddRateLineOkCommand", "execute");		
		
    }
/**
 * 	Method		:	AddRateLineOKCommand.getScaledValue
 *	Added by 	:	a-4809 on Oct 30, 2014
 * 	Used for 	:	to round the values
 *	Parameters	:	@param value
 *	Parameters	:	@param precision
 *	Parameters	:	@return 
 *	Return type	: 	double
 */
	private double getScaledValue(double value, int precision) {
		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
		return bigDecimal.setScale(precision,
				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

