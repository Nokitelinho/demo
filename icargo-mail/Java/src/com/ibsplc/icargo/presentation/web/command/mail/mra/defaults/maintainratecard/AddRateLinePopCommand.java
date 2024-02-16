/*
 * AddRateLinePopCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainratecard;

import java.util.ArrayList;
import java.util.Collection;

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
 * @author A-2391
 *
 */
public class AddRateLinePopCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("OriginDestination ScreenloadCommand");
	private static final String CLASS_NAME = "POPup ScreenLoadCommand";
	private static final String ACTION_FAILURE = "screenload_failure";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.upuratecard.maintainupuratecard";
	private static final String ACTION_SUCCESS = "screenload_success";
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	int flag=0;
    	log.entering("AddRateLinePopCommand","execute");
    	MaintainUPURateCardSession session=null;
    	session=(MaintainUPURateCardSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		MaintainUPURateCardForm form=(MaintainUPURateCardForm)invocationContext.screenModel;
   		
   		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
   		Page<RateLineVO> ratelinevopage=null;
   		Double iataKm[]=new Double[100];
   		
   		
   		if((session.getNewRateLineDetails()!=null)&&(session.getNewRateLineDetails().size()>0)){
   			String origin[]=form.getPopupOrigin();
   	   		String destn[]=form.getPopupDestn();
   	   		String popOrigin=null;
   	   		String popDst=null;
   			ArrayList<RateLineVO> rates=new ArrayList<RateLineVO>(session.getNewRateLineDetails());
   			int siz=rates.size();
   			for(int i=0;i<siz;i++){
   			   rates.get(i).setOrigin(origin[i]);
   			   rates.get(i).setDestination(destn[i]);
   			   
   			}
   			popOrigin=rates.get(siz-1).getOrigin();
			popDst=rates.get(siz-1).getDestination();
			ArrayList<String> orgdst=new ArrayList<String>();
			ArrayList<String> pair=new ArrayList<String>();
	   		if(popOrigin.equals(popDst)){
	   			orgdst.add(popOrigin);
	   		}
	   		else{
	   			orgdst.add(popOrigin);
	   			orgdst.add(popDst);
	   		}
	   	 try{
	   	    	AreaDelegate areaDelegate = new AreaDelegate();
	   	    	if("C".equals(form.getOrgDstLevel())){
	   	    		areaDelegate.validateCityCodes(companyCode,orgdst);
	   	    	}else{
	   	    		areaDelegate.validateAirportCodes(companyCode, orgdst);
	   	    	}
	   	    }
	   	    catch(BusinessDelegateException businessDelegateException){
	   	    	flag++;
	   	      errors=handleDelegateException(businessDelegateException);
	   	    }
   			if(flag==0){
   				StringBuilder sb=new StringBuilder(popOrigin);
   				sb.append("-");
   				sb.append(popDst);
   				
   				String pairval=new String(sb);
   				pair.add(pairval);
   				try{
   					if("C".equals(form.getOrgDstLevel())){
   				CityPairDelegate delegate = new CityPairDelegate();
   	   			ArrayList<CityPairVO> citypairvo =(ArrayList<CityPairVO>)
   	   							delegate.findCityPair(companyCode,pair);
   	   			
   	   		if(citypairvo!=null && citypairvo.size()>0){
   				
   				int vosize=citypairvo.size();
   				
   			for(int j=0;j<vosize;j++){
   				
   				if(citypairvo.get(j)!=null){
   					
   					Measure cityPairDist = citypairvo.get(j).getCityPairDistance();
   				    iataKm[j]= cityPairDist != null ? cityPairDist.getSystemValue() : 0.0;
   			        
   					}
   	   			}
   	   		}
   					}else{
   						AirportPairDelegate delegate = new AirportPairDelegate();
   						ArrayList<AirportPairVO> airportPairVO = (ArrayList<AirportPairVO>)delegate.findAirportPair(companyCode,pair);
   						
   						if(airportPairVO!=null && !airportPairVO.isEmpty()){
   			   				
   			   				int vosize=airportPairVO.size();
   			   				
   			   			for(int j=0;j<vosize;j++){
   			   				
   			   				if(airportPairVO.get(j)!=null){
   			   					
   			   					Measure airportPairDist = airportPairVO.get(j).getAirportPairDistance();
   			   				    iataKm[j]= airportPairDist != null ? airportPairDist.getSystemValue() : 0.0;
   			   			        
   			   					}
   			   	   			}
   			   	   		}
   					}
   		}
   				catch (BusinessDelegateException businessDelegateException) {
   		   			
   					errors=handleDelegateException(businessDelegateException);
   					
   					
   				}
   		}
   			if(errors != null && errors.size() > 0  ){
   	   			for(ErrorVO serverErrorVO : errors){
   	   				log.log(Log.INFO, " errorsCode ", serverErrorVO.getErrorCode());
   	   				
   	   			}
   	   			form.setOkFlag("Not OK");
   	   			invocationContext.addAllError(errors);
   	   			invocationContext.target = ACTION_FAILURE;
   	   			
   	   			return;
   	   		}
   			rates.get(siz-1).setIataKilometre(iataKm[0]);
   			
   			RateLineVO ratelinevo=new RateLineVO();
     		  ratelinevo.setCompanyCode(companyCode);
     		  ratelinevo.setOperationFlag(RateLineVO.OPERATION_FLAG_INSERT);
     		  ratelinevo.setOrigin("");
     		  ratelinevo.setDestination("");
     		  rates.add(ratelinevo);
     		  siz=siz+1;
     		  ratelinevopage =new Page<RateLineVO>(rates,1,0,siz,0,0,false);
   		}
   		else
   		{
   			
   		  ArrayList<RateLineVO> ratelinevos=new ArrayList<RateLineVO>();
   		  RateLineVO ratelinevo=new RateLineVO();
 		  ratelinevo.setCompanyCode(companyCode);
 		  ratelinevo.setOperationFlag(RateLineVO.OPERATION_FLAG_INSERT);
 		  ratelinevo.setOrigin("");
 		  ratelinevo.setDestination("");
 		  ratelinevos.add(ratelinevo);
 		 ratelinevopage =new Page<RateLineVO>(ratelinevos,1,0,1,0,0,false);
   		}
        
   		session.setNewRateLineDetails(ratelinevopage);
   		invocationContext.target = ACTION_SUCCESS;
		log.exiting("AddRateLinePopConmmand", "execute");	
    }
    }


