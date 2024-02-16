/*
 * ClearMailManifestCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ClearMailManifestCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "clear_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearMailManifestCommand","execute");
    	  
    	MailManifestForm mailManifestForm = 
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailManifestVO mailManifestVO = new MailManifestVO();
		mailManifestSession.setMailManifestVO(mailManifestVO);
		
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailManifestSession.setFlightValidationVO(flightValidationVO);
		
		mailManifestForm.setDeparturePort(logonAttributes.getAirportCode());
		mailManifestForm.setFlightCarrierCode("");
		mailManifestForm.setFlightNumber("");
		mailManifestForm.setDepDate("");
		mailManifestForm.setOperationalStatus("");
		mailManifestForm.setDuplicateFlightStatus(FLAG_NO);
		mailManifestForm.setDisableSaveFlag(FLAG_NO);
		mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailManifestForm.setInitialFocus(FLAG_YES);
		mailManifestForm.setMailFlightSummary("");
		mailManifestForm.setMailFlighCarrierCode("");
		mailManifestForm.setFlightNumber("");
		mailManifestForm.setMaildepDate("");
		mailManifestForm.setDisableButtonsForTBA("");
		mailManifestForm.setWarningFlag("");
		mailManifestForm.setWarningOveride("");		
		mailManifestForm.setDuplicateAndTbaTbc("");
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearMailManifestCommand","execute");
    	
    }
       
}
