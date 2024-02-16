/*
 * ScreenloadMailAcceptanceCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadMailAcceptanceCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String PREASSIGNMENT_SYS = "mailtracking.defaults.acceptance.preassignmentneeded";
   private static final String INVENTORYENABLED_SYS = "mailtracking.defaults.inventoryenabled";
   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadMailAcceptanceCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		mailAcceptanceForm.setOperationalStatus("");
		mailAcceptanceForm.setDisableAddModifyDeleteLinks(FLAG_YES);
		//Added by A-7794 as part of ICRD-197439
		mailAcceptanceForm.setDisableButtonsForAirport(FLAG_YES);
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
		mailAcceptanceSession.setMessageStatus("");
		
		Collection<String> codes = new ArrayList<String>();
    	codes.add(PREASSIGNMENT_SYS);
    	codes.add(INVENTORYENABLED_SYS);
    	codes.add(MAIL_COMMODITY_SYS);
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
    	if(results != null && results.size() > 0) {
    		mailAcceptanceForm.setPreassignFlag(results.get(PREASSIGNMENT_SYS));
    		mailAcceptanceSession.setInventoryparameter(results.get(INVENTORYENABLED_SYS));
    		mailAcceptanceSession.setMailCommidityCode(results.get(MAIL_COMMODITY_SYS));
    		log.log(Log.FINE, "****mailAcceptanceForm.getPreassignFlag()***",
					mailAcceptanceForm.getPreassignFlag());
    	}
		
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		mailAcceptanceSession.setFlightValidationVO(flightValidationVO);
		mailAcceptanceForm.setInitialFocus(FLAG_YES);
		mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailAcceptanceForm.setDeparturePort(logonAttributes.getAirportCode());
		mailAcceptanceForm.setAssignToFlight(CONST_FLIGHT);
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenloadMailAcceptanceCommand","execute");
    	
    }
       
}
