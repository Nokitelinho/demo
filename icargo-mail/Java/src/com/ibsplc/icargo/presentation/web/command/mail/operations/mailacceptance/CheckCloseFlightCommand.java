/*
 * CheckCloseFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class CheckCloseFlightCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String CONST_FLIGHT = "FLIGHT";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("CheckCloseFlightCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
    	
    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
    	String assignTo = mailAcceptanceForm.getAssignToFlight();
	    log.log(Log.FINE, "assignTo ===", assignTo);
		String[] primaryKey = mailAcceptanceForm.getSelectMail();
	    
	    if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
    	
	    boolean isFlightClosed = false;
    	try {
    		
    		isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(mailAcceptanceSession.getOperationalFlightVO());
		  
			
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			mailAcceptanceForm.setCloseflight("N");
			return;
		}
		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
		if (isFlightClosed) {
			
			Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
					mailAcceptanceVO.getFlightNumber(),
					mailAcceptanceVO.getFlightDate().toString().substring(0,11)};
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			mailAcceptanceForm.setDisableSaveFlag("Y");
			mailAcceptanceForm.setCloseflight("Y");
			invocationContext.target = TARGET;
			return;				
		} 
		
		/**
		 * Added for CR SAA 410 STARTS
		 */
		FlightValidationVO flightValidationVO = mailAcceptanceSession.getFlightValidationVO();
		if(flightValidationVO != null){
			if(flightValidationVO.isTBADueRouteChange()){
				Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
						mailAcceptanceVO.getFlightNumber(),
						mailAcceptanceVO.getFlightDate().toString().substring(0,11)};
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.msg.err.flighttobeactioned",obj);
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET;
				return;
			}
		}
		
		/**
		 * Added for CR SAA 410 ENDS
		 */
		
		
	    }
	    
	    /**
	     * To check for arrived mailbags not to be accepted
	     */
	    
		Collection<ContainerDetailsVO> contDetailsVOs = mailAcceptanceVO.getContainerDetails();
	    int cnt=0;
	    int count = 1;
	    if(primaryKey != null){
        int primaryKeyLen = primaryKey.length;
        int errorArrived = 0;
        String contArrived = "";
       if (contDetailsVOs != null && contDetailsVOs.size() != 0) {
       	for (ContainerDetailsVO contDetailsVO : contDetailsVOs) {
       		String primaryKeyFromVO = contDetailsVO.getCompanyCode()
       				+String.valueOf(count);
       		if ((cnt < primaryKeyLen) && (primaryKeyFromVO.trim()).
       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
       			if("Y".equals(contDetailsVO.getArrivedStatus())){
       				errorArrived = 1;
       				if("".equals(contArrived)){
       					contArrived = contDetailsVO.getContainerNumber();
	       			}else{
	       				contArrived = new StringBuilder(contArrived)
   					                  .append(",")
   					                  .append(contDetailsVO.getContainerNumber())
   					                  .toString();	
	       			}
       			}
       			cnt++;
       		}
       		count++;
       	  }
       	}
       if(errorArrived == 1){
  	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.alreadyarrived",new Object[]{contArrived}));
  	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
  	    	mailAcceptanceForm.setCloseflight("Y");
			invocationContext.target = TARGET;
			return;		
       }
	   }
	    
	   mailAcceptanceForm.setCloseflight("N");
       mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	   invocationContext.target = TARGET;
       	
       log.exiting("CheckCloseFlightCommand","execute");
    	
    }
       
}
