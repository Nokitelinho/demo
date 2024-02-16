/*
 * ClearMailAcceptanceCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ClearMailAcceptanceCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "clear_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_ASSIGNCONTAINER = "ASSIGNCONTAINER";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearMailAcceptanceCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
		
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailAcceptanceSession.setFlightValidationVO(flightValidationVO);
		
		mailAcceptanceForm.setDeparturePort(logonAttributes.getAirportCode());
		//mailAcceptanceForm.setAssignToFlight(CONST_FLIGHT);
		mailAcceptanceForm.setFlightCarrierCode("");
		mailAcceptanceForm.setFlightNumber("");
		mailAcceptanceForm.setDepDate("");
		mailAcceptanceForm.setCarrierCode("");
		mailAcceptanceForm.setDestination("");
		mailAcceptanceForm.setDuplicateFlightStatus(FLAG_NO);
		mailAcceptanceForm.setDisableDestnFlag(FLAG_NO);
		mailAcceptanceForm.setDisableSaveFlag(FLAG_NO);
		mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailAcceptanceForm.setFromScreen("");	
		mailAcceptanceForm.setInitialFocus(FLAG_YES);
		mailAcceptanceForm.setOperationalStatus("");
		mailAcceptanceForm.setDisableButtons("");
		mailAcceptanceForm.setDisableAddModifyDeleteLinks("Y");
		//Added by A-7794 as part of ICRD-197439
		//mailAcceptanceForm.setDisableButtonsForAirport("Y");
		mailAcceptanceForm.setTbaTbcWarningFlag("");
		mailAcceptanceForm.setWarningFlag("");
		mailAcceptanceForm.setWarningOveride("");		
		mailAcceptanceForm.setDuplicateAndTbaTbc("");
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearMailAcceptanceCommand","execute");
    	
    }
       
}
