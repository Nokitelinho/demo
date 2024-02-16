/*
 * ScreenLoadCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "screenload_success";	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";	

		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ScreenloadMailExportListCommand","execute");
	    	  
	    	MailExportListForm mailExportListForm =  (MailExportListForm)invocationContext.screenModel;
	    	MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			mailExportListSession.setMailAcceptanceVO(mailAcceptanceVO);
			
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			mailExportListSession.setFlightValidationVO(flightValidationVO);
			
			mailExportListForm.setInitialFocus(FLAG_YES);
			mailExportListForm.setDeparturePort(logonAttributes.getAirportCode());
			mailExportListForm.setAssignToFlight(CONST_FLIGHT);
			mailExportListForm.setStatus("NONE");
			mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = TARGET;
	       	
	    	log.exiting("ScreenloadMailExportListCommand","execute");
	    }

}
