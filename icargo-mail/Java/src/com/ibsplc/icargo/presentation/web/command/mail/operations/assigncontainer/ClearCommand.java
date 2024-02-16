/*
 * ClearCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ClearCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");      
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String TARGET_SUCCESS = "clear_success"; 
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	
    	assignContainerForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
    	//assignContainerForm.setAssignedto(CONST_FLIGHT);
    	
    	assignContainerForm.setDeparturePort(logonAttributes.getAirportCode());
    	
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
    	    	    	    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ClearCommand","execute");
    	
    }
       
}
