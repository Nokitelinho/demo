/*
 * ScreenloadCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.dispatcher.BatchedResponse;
import com.ibsplc.xibase.client.framework.dispatcher.DispatcherException;
import com.ibsplc.xibase.client.framework.dispatcher.RequestDispatcher;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ScreenloadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String SCREEN_ID_ACCP = "mailtracking.defaults.mailacceptance";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_ACCEPT = "MAILACCEPTANCE";
   private static final String CONST_FROMACCEPT = "FROMMAILACCEPTANCE";
   private static final String CONST_MAILBAGENQUIRY = "MAILBAGENQUIRY";
   private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
   private static final String DEPARTED_CONFIGURATION = "mailtracking.defaults.assignment.departedflights";
   private static final String NONDEPARTED_CONFIGURATION = "mailtracking.defaults.assignment.nondepartedflights";
   private static final String FLT_OPR_FLIGHTLEG_STATUS = "flight.operation.flightlegstatus";
   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenLoadCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	//-------------added to directly go from mailaccp to assign container screen bypassing assigncontainer command class---------------------   	
    	MailAcceptanceSession mailAcceptanceSession = 
        		getScreenSession(MODULE_NAME,SCREEN_ID_ACCP); 
    	if (CONST_ACCEPT.equals(assignContainerForm.getFromScreen())){
    		log.log(Log.FINE, "assignContainerForm.getFromScreen():::>>>",
					assignContainerForm.getFromScreen());
		  	
    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();   	
    	String assignTo = assignContainerForm.getAssignedto();
		log.log(Log.FINE, "assignTo ===", assignTo);
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
    	    assignContainerSession.setFlightValidationVO( mailAcceptanceSession.getFlightValidationVO());
    	}else{
    		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    		airlineValidationVO.setAirlineIdentifier(mailAcceptanceVO.getCarrierId());
    		airlineValidationVO.setAlphaCode(mailAcceptanceVO.getFlightCarrierCode());
    		assignContainerSession.setAirlineValidationVO(airlineValidationVO);
    	}  	
    	}
    	//--------------------------------------------------------------------------------------------
    	//Added by A-7929 as part of ICRD-249963 starts--
    	 Collection<String> systemparamCodes = new ArrayList<String>();
    	 systemparamCodes.add(MAIL_COMMODITY_SYS);
 	    Map<String, String> systemParameterMap = null;
 	    try {
 	    	systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemparamCodes);
     	} catch(BusinessDelegateException businessDelegateException) {
     		handleDelegateException(businessDelegateException);
     	}
     	if(systemParameterMap != null && systemParameterMap.size() > 0) {
     		mailAcceptanceSession.setMailCommidityCode(systemParameterMap.get(MAIL_COMMODITY_SYS));
     		log.log(Log.FINE, "*******DEnsity Factor******", systemParameterMap.get(MAIL_COMMODITY_SYS));
     	}
    	
    	//Added by A-7929 as part of ICRD-249963 ends--  
    	
    	Collection<String> fieldTypes = new ArrayList<String>();		
		fieldTypes.add(CONTAINERTYPE);
		fieldTypes.add(FLT_OPR_FLIGHTLEG_STATUS);
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(DEPARTED_CONFIGURATION);
		parameterTypes.add(NONDEPARTED_CONFIGURATION);
		
		Map<String,Collection<OneTimeVO>> oneTimeData
			= new HashMap<String,Collection<OneTimeVO>>();
		Map<String, String> parameters = null;
    	
    	/*
		 * Start the batch processing
		 */
		RequestDispatcher.startBatch();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();		
    	try { 		
    		
    		sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					fieldTypes);  
    		
    		sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
    		    		    		    		
    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
    	
    	/*
		 * Obtain the responses after the batch fetch
		 */
		try {
			BatchedResponse response[] = RequestDispatcher.executeBatch();
			log.log(Log.INFO, "Response length:--", response.length);
			if(!response[0].hasError()) {
				oneTimeData = (HashMap<String,Collection<OneTimeVO>>)response[0].getReturnValue();
				log.log(Log.INFO, "oneTimeData:--", oneTimeData);
			}	
			if(!response[1].hasError()) {
				parameters = (HashMap<String,String>)response[1].getReturnValue();
				log.log(Log.INFO, "parameters:--", parameters);
			}			
		}catch (DispatcherException dispatcherException) {
			dispatcherException.getMessage();
		}
		
		if (oneTimeData != null) {			
			Collection<OneTimeVO> containerTypes = 
				oneTimeData.get(CONTAINERTYPE);	
			Collection<OneTimeVO> flightStatus = 
				oneTimeData.get(FLT_OPR_FLIGHTLEG_STATUS);	
			assignContainerSession.setContainerTypes(containerTypes);
			assignContainerSession.setFlightStatus(flightStatus);
		}
		if (parameters != null) {
			String departedConfig = parameters.get(DEPARTED_CONFIGURATION);						
			String nondepartedConfig = parameters.get(NONDEPARTED_CONFIGURATION);
			assignContainerSession.setDepartedConfiguration(departedConfig);
			assignContainerSession.setNonDepartedConfiguration(nondepartedConfig);
		}		
    	
		if (!CONST_ACCEPT.equals(assignContainerForm.getFromScreen())
			&& !CONST_MAILBAGENQUIRY.equals(assignContainerForm.getFromScreen())
			&& !CONST_FROMACCEPT.equals(assignContainerForm.getFromScreen())) {
			assignContainerForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
	    	assignContainerForm.setAssignedto(CONST_FLIGHT);
	    	
	    	assignContainerForm.setDeparturePort(logonAttributes.getAirportCode());
	    	
	    	
	    	assignContainerForm.setCarrier("");
	    	assignContainerForm.setDestn("");
	    	assignContainerForm.setFlightCarrierCode("");
	    	assignContainerForm.setFlightDate("");
	    	assignContainerForm.setFlightNumber("");
	    	assignContainerForm.setFlightStatus("");
	    	assignContainerForm.setStatus("");   
	    	
	    	assignContainerSession.setContainerVOs(null);
	    	assignContainerSession.setFlightValidationVO(null);
	    	assignContainerSession.setSelectedContainerVOs(null);
	    	assignContainerSession.setAirlineValidationVO(null);
	    	assignContainerSession.setPointOfLadings(null);
		}
		if("SUCCESS".equals(assignContainerSession.getReassignStatus())){
			assignContainerForm.setAssignedto(CONST_FLIGHT);
	    	
	    	assignContainerForm.setDeparturePort(logonAttributes.getAirportCode());
	    	
	    	assignContainerForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
	    	assignContainerForm.setCarrier("");
	    	assignContainerForm.setDestn("");
	    	assignContainerForm.setFlightCarrierCode("");
	    	assignContainerForm.setFlightDate("");
	    	assignContainerForm.setFlightNumber("");
	    	assignContainerForm.setFlightStatus("");
	    	assignContainerForm.setStatus("");   
	    	assignContainerForm.setOverrideFlag("");
	    	assignContainerForm.setWarningCode("");
	    	assignContainerForm.setFromScreen("");
	    	assignContainerForm.setWarningFlag("");
	    	assignContainerForm.setDisableButtonsForTBA("");
	    	assignContainerForm.setDuplicateAndTbaTbc("");
	    	assignContainerSession.setContainerVOs(null);
	    	assignContainerSession.setFlightValidationVO(null);
	    	assignContainerSession.setSelectedContainerVOs(null);
	    	assignContainerSession.setAirlineValidationVO(null);
	    	assignContainerSession.setPointOfLadings(null);
	    	assignContainerSession.setReassignStatus("");
	    	ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.savesuccess");
	    	invocationContext.addError(errorVO);
		}
		
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenLoadCommand","execute");
    	
    }
       
}
