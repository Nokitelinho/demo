/*
 * SaveHandledCarrierSetupCommand.java Created on Dec 5, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.handledcarrier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHandledCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHandledCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.HandledCarrierSetupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2883
 */

public class SaveHandledCarrierSetupCommand extends BaseCommand{
	
	private static final String SCREEN_ID = 
		"uld.defaults.misc.handledcarriersetup";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	private static final String OPERATION_FLAG_INSERT="I";
	private static final String OPERATION_FLAG_DELETE="D";
	
	 /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		
		
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        HandledCarrierSetupForm handledCarrierSetupForm= (HandledCarrierSetupForm)invocationContext.screenModel;
        ULDHandledCarrierSession uldCarrierSession = 
    								getScreenSession(MODULE_NAME,SCREEN_ID);
        
        
        Collection<ULDHandledCarrierVO> uldHandledCarrierVOs =new ArrayList<ULDHandledCarrierVO>();
      
      
        String[] opFlag=handledCarrierSetupForm.getTemplateOperationFlag();
        String[] station=handledCarrierSetupForm.getTemplateStationCode();
        String[] airlinecode=handledCarrierSetupForm.getTemplateAirlineCode();
        String[] airlinename=handledCarrierSetupForm.getTemplateAirlineName();
      
         
        int length=0;
        length=handledCarrierSetupForm.getTemplateAirlineCode().length-1;
        log.log(Log.FINE, " \n oopflag", handledCarrierSetupForm.getTemplateOperationFlag().length);
		for(int k=0; k<length;k++){
        	ULDHandledCarrierVO handledCarrierSetupVO =new ULDHandledCarrierVO();
        	
        	/*log.log(Log.FINE, " \n oopflagstation"+station[k]);
        	log.log(Log.FINE, " \n oopflagairline"+airlinecode[k]);
        	log.log(Log.FINE, " \n oopflagairlinename"+airlinename[k]);
        	*/
        	if(opFlag[k]!=null){
        		log.log(Log.FINE, " \n oopflag", opFlag, k);
				handledCarrierSetupVO.setOperationFlag(opFlag[k]);
        	}else{
        		handledCarrierSetupVO.setOperationFlag("");
        	}
        	if(station[k]!=null){
        		handledCarrierSetupVO.setStationCode(station[k]);
        	}else{
        		handledCarrierSetupVO.setStationCode("");
        	}
        	if(airlinecode[k]!=null){
        		handledCarrierSetupVO.setAirlineCode(airlinecode[k]);
        	}else{
        		handledCarrierSetupVO.setAirlineCode("");
        	}
        	if(airlinename[k]!=null){
        		handledCarrierSetupVO.setAirlineName(airlinename[k]);
        	}else{
        		handledCarrierSetupVO.setAirlineName("");
        	}
        	handledCarrierSetupVO.setCompanyCode(logonAttributes.getCompanyCode().toUpperCase());
        	uldHandledCarrierVOs.add(handledCarrierSetupVO);
    		
        }
        
        uldCarrierSession.setULDHandledCarrierVO(uldHandledCarrierVOs);
        
        //validate VOs
    	Collection<ErrorVO> stationErrorVOs = new ArrayList<ErrorVO>();
    	Collection<ErrorVO> airlineErrorVOs = new ArrayList<ErrorVO>();
    	Collection<ErrorVO> dupVOs=new ArrayList<ErrorVO>();
    	Collection<ErrorVO> duplicaterow=new ArrayList<ErrorVO>();
    	
    	
        for(ULDHandledCarrierVO validateVO :uldHandledCarrierVOs){
        	if(validateVO.getOperationFlag()!=null && 
        			(validateVO.getOperationFlag().equals(OPERATION_FLAG_INSERT))){
        					
        		dupVOs=checkDuplicateAirlineCode(uldHandledCarrierVOs);
        		errorVOs.addAll(dupVOs);
        		stationErrorVOs = validateStationCode(validateVO.getStationCode());
        		errorVOs.addAll(stationErrorVOs);
        		airlineErrorVOs = validateAirlineCode(validateVO.getAirlineCode());
        		errorVOs.addAll(airlineErrorVOs);
        		 if (errorVOs.size() == 0) {
             		duplicaterow=checkDuplicateValue(validateVO);
              		errorVOs.addAll(duplicaterow);
             	 }
        	}
        	
        	
        }
        log.log(Log.FINE, " \n ", errorVOs.size());
		if (errorVOs != null && errorVOs.size() > 0) {
			log.log(Log.FINE, " \n ", errorVOs.size());
			for(ErrorVO evo :errorVOs){
				log.log(Log.FINE, " \n ", evo.getErrorCode());
			}
			invocationContext.addAllError(errorVOs);
			invocationContext.target = SCREENLOAD_FAILURE;
		}else{
			try {
				log.log(Log.FINE, " \n bbbeeere", uldHandledCarrierVOs);
				new ULDDefaultsDelegate()
						.saveHandledCarrier(uldHandledCarrierVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				
				errorVOs = handleDelegateException(businessDelegateException);
				log.log(Log.FINE, " \n  Excetption caught in savehandledcarrier");
			}
			if (errorVOs != null && errorVOs.size() > 0) {
				invocationContext.addAllError(errorVOs);
				invocationContext.target = SCREENLOAD_FAILURE;
			}else{
				ErrorVO errorVO =new ErrorVO("uld.defaults.misc.handledcarriersetup.savesuccessful");
	    		errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
	    		errorVOs.add(errorVO);
	    		invocationContext.addAllError(errorVOs);
	    		uldCarrierSession.setULDHandledCarrierVO(null);
				invocationContext.target = SCREENLOAD_SUCCESS;
			}
		
		}
	}
	
	private Collection<ErrorVO> validateStationCode(
			String station) {
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		log.log(Log.FINE, " \n inssssssstt");
		AirportValidationVO airportValidationVO = null;
		try {
			AreaDelegate delegate = new AreaDelegate();

			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(),  station
					.toUpperCase());
		} catch (BusinessDelegateException e) {

			errors = handleDelegateException(e);
			
		}
		
		return errors;
	}
	 
	 private Collection<ErrorVO> validateAirlineCode(
				 String airlineCode) {
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			AirlineValidationVO airlineValidationVO = null;
			try {
				AirlineDelegate delegate = new AirlineDelegate();
				airlineValidationVO = delegate.validateAlphaCode(logonAttributes
						.getCompanyCode().toUpperCase(), airlineCode
						.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			return errors;
		}
		
	

	 private Collection<ErrorVO> checkDuplicateAirlineCode(Collection<ULDHandledCarrierVO>uldHandledCarrierVOs){
		 
		 HashMap<String, ULDHandledCarrierVO> map=new HashMap<String, ULDHandledCarrierVO>();
		// Collection<String> value=new ArrayList<String>();
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 for(ULDHandledCarrierVO carrierVO :uldHandledCarrierVOs){
			 String key=new StringBuilder(carrierVO.getStationCode().trim().toUpperCase())
			 	.append(carrierVO.getAirlineCode().trim().toUpperCase()).toString();
			 log.log(Log.FINE, " \n kkkkk", key);
			if(map.containsKey(key)){
				 errors.add(new ErrorVO("uld.defaults.misc.handledcarriersetup.duplicateexists"));
				 break;
			 }else{
				 map.put(key, carrierVO);
			 }
		 }
		 
		 return errors;
	 }
	 
	 private Collection<ErrorVO> checkDuplicateValue(ULDHandledCarrierVO vo){
		 Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
		 Collection<ULDHandledCarrierVO> uldHandledCarrierVOs =new ArrayList<ULDHandledCarrierVO>();	
		 try{
			
			 ULDDefaultsDelegate uLDDefaultsDelegate = new ULDDefaultsDelegate();
	        	uldHandledCarrierVOs = 
	    			uLDDefaultsDelegate.findHandledCarrierSetup(
	    					vo);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
	        	
		}
		
		if(uldHandledCarrierVOs!=null && uldHandledCarrierVOs.size()>0){
			errors.add(new ErrorVO("uld.defaults.misc.handledcarriersetup.valuealreadyexists"));
		}
		
		 return errors;
	 }
	 
	 
}
