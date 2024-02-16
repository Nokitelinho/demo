/*
 * AssignContainerCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AssignContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
   private static final String MODULE_NAME_AC = "mailtracking.defaults";	
   private static final String SCREEN_ID_AC = "mailtracking.defaults.assignContainer";	
   private static final String CONST_FLIGHT = "FLIGHT";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AssignContainerCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME_AC,SCREEN_ID_AC);
    	
    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
    	
    	String assignTo = mailAcceptanceForm.getAssignToFlight();
		log.log(Log.FINE, "assignTo ===", assignTo);
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
    	    assignContainerSession.setFlightValidationVO( mailAcceptanceSession.getFlightValidationVO());
    	}else{
    		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    		airlineValidationVO.setAirlineIdentifier(mailAcceptanceVO.getCarrierId());
    		airlineValidationVO.setAlphaCode(mailAcceptanceVO.getFlightCarrierCode());
    		assignContainerSession.setAirlineValidationVO(airlineValidationVO);
    	}
    	mailAcceptanceForm.setCloseFlag("ASSIGN_CONTAINER");
    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = TARGET;
    	log.exiting("AssignContainerCommand","execute");
    	
    }
       
}
