/*
 * ViewStockHolderCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidityDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
//T-1824 Ends
/**
 * @author A-1870
 *
 */
public class ViewStockHolderCommand extends BaseCommand {

	private static final String OPERATION_FLAG_UPDATE = "U";
	private static final String NULL_STRING="";

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {	
		//log.entering("AddRowCommand","execute");
		 /**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
		MaintainStockHolderForm maintainStockHolderForm=(MaintainStockHolderForm)invocationContext.screenModel;
		StockHolderVO stockHolderVO = null;
		/*maintainStockHolderForm.setStockHolderType(NULL_STRING);
		maintainStockHolderForm.setStockHolderName(NULL_STRING);
		maintainStockHolderForm.setControlPrivilege(NULL_STRING);
		maintainStockHolderForm.setContact(NULL_STRING);*/

		//Added as part of bug ICRD-2583 by A-3767 on 02Jun11
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();	
		try {
			if(session.getPartnerAirlines()==null || session.getPartnerAirlines().size()==0){
				List<AirlineLovVO> ownerAirlinesList=new ArrayList<AirlineLovVO>();
				Collection<String> systemParameterCodes=new ArrayList<String>();
				systemParameterCodes.add("logonattributes.ownairlineidentifier");
				Map<String, String> parameters=new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
				if(parameters!=null && parameters.size()>0){
					AirlineVO ownerAirline = new AirlineDelegate()
							.findAirlineDetails(
									logonAttributes.getCompanyCode(),
									Integer.valueOf(parameters
											.get("logonattributes.ownairlineidentifier")));
					AirlineLovVO ownerAirlineVO=new AirlineLovVO();
					ownerAirlineVO.setCompanyCode(ownerAirline.getCompanyCode());
					ownerAirlineVO.setAirlineCode(ownerAirline.getAirlineCode());
					ownerAirlineVO.setAirlineIdentifier(ownerAirline.getAirlineIdentifier());
					
					//added as part of ICRD-155121 starts
					LocalDate localDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
	        				true);     
					
					if(ownerAirline.getAirlineValidityDetailsVOs() != null && 
							ownerAirline.getAirlineValidityDetailsVOs().size() > 0 ) {
			    		for(AirlineValidityDetailsVO airlineValidityDetailsVO : ownerAirline.getAirlineValidityDetailsVOs()) {
			    				if((localDate.toGMTDate()).compareTo(airlineValidityDetailsVO.getValidFromDate().toGMTDate()) >= 0 
			    						&& (localDate.toGMTDate()).compareTo(airlineValidityDetailsVO.getValidTillDate().toGMTDate()) <= 0 ) {
			    						if(airlineValidityDetailsVO.getNumberCodeUsed() == 3){
			    							ownerAirline.setNumericCode(airlineValidityDetailsVO.getThreeNumberCode());
			    						}
			    						else {
			    							ownerAirline.setNumericCode(airlineValidityDetailsVO.getFourNumberCode());
			    						}
			    					break;
			    				}
			    		}
			    	}
					
					//added as part of ICRD-155121 ends	
					
					ownerAirlineVO.setAirlineNumber(ownerAirline.getNumericCode());
					ownerAirlineVO.setAirlineName(ownerAirline.getAirlineName());
					ownerAirlinesList.add(ownerAirlineVO);
					session.setPartnerAirlines(new Page<AirlineLovVO>(ownerAirlinesList, 0, 0, 0, 0, 0, false));
				}
				if(ownerAirlinesList==null || ownerAirlinesList.size()==0){
					ownerAirlinesList.add(new AirlineLovVO());
					session.setPartnerAirlines(new Page<AirlineLovVO>(ownerAirlinesList, 0, 0, 0, 0, 0, false));
				}
				//ICRD-2583 ends
				
//				added  by Shemeer T-1824 for the bug 108527  on 15/3/11
			loadSessionWithPartnerAirlines(session);
			}
			
		} catch (BusinessDelegateException e) {				
			//printStackTrrace()();
			log.log(Log.SEVERE, "BusinessDelegateException caught from findAirlineDetails");
		}

		//Ends T-1824
		
		if(maintainStockHolderForm.getStockHolderCode() == null ||
				maintainStockHolderForm.getStockHolderCode().length()==0){
			Object[] obj = { "stockholdercodeismandatory" };
			ErrorVO error = new ErrorVO("stockcontrol.defaults.maintainstockholder.err.plzspecifystkhldcode", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = "screenload_success";
			return;
		}
		Map<String, Collection<OneTimeVO>>  oneTimes=getScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
		HashMap<String,Collection<String>> documentList = null;
		try{
		 documentList =new HashMap<String,Collection<String>>(new DocumentTypeDelegate().
				 findAllDocuments(getApplicationSession().getLogonVO().getCompanyCode()));
		//documentList.put("",null);
		
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}

		session.setMap(documentList); //set in session
		if(oneTimes!=null){
		Collection<OneTimeVO> stockHolderTypes=oneTimes.get("stockcontrol.defaults.stockholdertypes");
//		Setting stock holders priority
		try{
		Collection<StockHolderPriorityVO> stockHolderpriorityVos =
			 new StockControlDefaultsDelegate().findStockHolderTypes(getApplicationSession().getLogonVO().getCompanyCode());
		
		populatePriorityStockHolders(stockHolderpriorityVos,stockHolderTypes,session);
		log.log(Log.FINE,
				"------------------stockHolderpriorityVos-----------",
				stockHolderpriorityVos);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}
		session.setStockHolderType(stockHolderTypes);
		
		}
		
		log.log(Log.FINE,
				"\n\n\n=============================== COde================",
				maintainStockHolderForm.getStockHolderCode());
		log
				.log(
						Log.FINE,
						"\n\n\n=============================== Control Privilage================",
						maintainStockHolderForm.getControlPrivilege());
		try{
			 stockHolderVO =  new StockControlDefaultsDelegate().findStockHolderDetails(
					getApplicationSession().getLogonVO().getCompanyCode()
					,upper(maintainStockHolderForm.getStockHolderCode()));
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE,"BusinessDelegateException caught..........");
		}
		if(stockHolderVO != null){
			session.setStockHolderVO(stockHolderVO);
			maintainStockHolderForm.setStockHolderType(stockHolderVO.getStockHolderType());
			maintainStockHolderForm.setControlPrivilege(stockHolderVO.getControlPrivilege());
			maintainStockHolderForm.setStockHolderName(stockHolderVO.getStockHolderName());
			maintainStockHolderForm.setContact(stockHolderVO.getStockHolderContactDetails());
			session.setStockVO(stockHolderVO.getStock());
			session.setMode(OPERATION_FLAG_UPDATE);		
			
			/*
			 * For #102543
			 * Populating the airline ids in the form
			 */			
			String[] airlineIds=new String[stockHolderVO.getStock().size()];
			int count=0;
			for(StockVO stockVO:stockHolderVO.getStock()){
				airlineIds[count++]=""+stockVO.getAirlineIdentifier();
			}
			maintainStockHolderForm.setAwbPrefix(airlineIds);
			/*
			 * End populating airlineIds
			 */
			
		}else{
			maintainStockHolderForm.setListSuccessful("N");
			/*maintainStockHolderForm.setStockHolderCode(NULL_STRING);
			maintainStockHolderForm.setStockHolderName(NULL_STRING);
			maintainStockHolderForm.setStockHolderType(NULL_STRING);
			maintainStockHolderForm.setControlPrivilege(NULL_STRING);
			maintainStockHolderForm.setContact(NULL_STRING);*/
		}
		invocationContext.target = "screenload_success";
		//log.exiting("AddSegmentCommand","execute");
		
		
	}
	
	/**
     * This method will be invoked at the time of screen load
     * @param companyCode
     * @return
     */
	public Map<String, Collection<OneTimeVO>> getScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try{
		Collection<String> fieldValues = new ArrayList<String>();
		fieldValues.add("stockcontrol.defaults.stockholdertypes");		

		 oneTimes =
				new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

			}catch(BusinessDelegateException businessDelegateException){
				log.log(Log.FINE,"\n\n<---------Inside BusinessDelegateException---------->\n\n");
//printStackTrrace()();
			}
	        return oneTimes;
	    }
	
	private void populatePriorityStockHolders(
			Collection<StockHolderPriorityVO> stockHolderpriorityVos,
			Collection<OneTimeVO> stockHolder,MaintainStockHolderSession session){
		log.entering("ScreenLoadCommand","populatePriorityStockHolders");
		if(stockHolderpriorityVos!=null){
			for(StockHolderPriorityVO priorityVO : stockHolderpriorityVos){
				for(OneTimeVO onetime : stockHolder){
					if(onetime.getFieldValue().equals(priorityVO.getStockHolderType())){
						priorityVO.setStockHolderDescription(onetime.getFieldDescription());
					}
				}
			}
			session.setPrioritizedStockHolders(stockHolderpriorityVos);
			log.exiting("ScreenLoadCommand","populatePriorityStockHolders");
		}
	}
	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}
	
//	added  by Shemeer T-1824 for the bug 108527  on 15/3/11 
	private void loadSessionWithPartnerAirlines(MaintainStockHolderSession session) throws BusinessDelegateException {
		AirlineLovFilterVO airlineLovFilterVO=new AirlineLovFilterVO();
		airlineLovFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		airlineLovFilterVO.setIsPartnerAirline("Y");
		//Edited as part of ICRD-81783
		Collection<AirlineLovVO> partnerAirlines = new AirlineDelegate().findAirlineLov(airlineLovFilterVO, 1);
		if(partnerAirlines != null && partnerAirlines.size() > 0) {
			session.getPartnerAirlines().addAll(partnerAirlines); 
		}		
		
	}
	//T-1824 ends 
}
