/*
 * ClearMailArrivalCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class ClearMailArrivalCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "clear_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearMailArrivalCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
		
		mailArrivalSession.setMailArrivalFilterVO(null);
		mailArrivalSession.setFromScreen(null);
		
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailArrivalSession.setFlightValidationVO(flightValidationVO);
		mailArrivalForm.setListFlag("FAILURE");
		mailArrivalForm.setOperationalStatus("");
		mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
		mailArrivalForm.setFlightCarrierCode("");
		mailArrivalForm.setFlightNumber("");
		mailArrivalForm.setArrivalDate("");
		mailArrivalForm.setMailStatus("");
		mailArrivalForm.setTransferCarrier("");
		mailArrivalForm.setArrivalPA("");
		mailArrivalForm.setArrivedStatus("");
		mailArrivalForm.setDuplicateFlightStatus(FLAG_NO);
		mailArrivalForm.setInitialFocus(FLAG_YES);
		mailArrivalForm.setWarningFlag("");
		mailArrivalForm.setDisableButtonsForTBA("");
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearMailArrivalCommand","execute");
    	
    }
       
}
