/*
 * ClearCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailflightsummary;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailFlightSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailFlightSummaryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "clear_success";   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";  
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearCommand","execute");    	  
    	MailFlightSummaryForm mailFlightSummaryForm = 
    		(MailFlightSummaryForm)invocationContext.screenModel;
    	MailFlightSummarySession mailFlightSummarySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);	
    	mailFlightSummaryForm.setStatus("");
    	mailFlightSummaryForm.setStatusFlag("");
    	mailFlightSummaryForm.setFlightCarrierCode("");
    	mailFlightSummaryForm.setFlightDate("");
    	mailFlightSummaryForm.setFlightNumber("");
    	mailFlightSummaryForm.setSelectedMails("");
    	mailFlightSummaryForm.setPopupStatus("");
    	mailFlightSummaryForm.setScreenStatFlag("SCREENLOAD");
    	mailFlightSummarySession.removeAllAttributes();		
    	mailFlightSummaryForm.setScreenStatusFlag(ComponentAttributeConstants.
    			SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = TARGET_SUCCESS;
       	log.exiting("ClearCommand","execute");
   }       
}
