/*
 * CloseCommand.java Created on Aug 25, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3434
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.mailproration";
	private static final String MODULE_AIRLINE = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID_AIRLINE = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	private static final String CLOSE_SUCCESS = "close_success";
	private static final String  CLOSE_TOINTERLINE = "close_toairline";
	private static final String  BLANK = "";
	
		


	/**
	 * 	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	MailProrationSession  prorationSession = (MailProrationSession) getScreenSession(
				MODULE_NAME, SCREENID);
    	ListInterlineBillingEntriesSession interlineBillingSession = (ListInterlineBillingEntriesSession) getScreenSession(
    			MODULE_AIRLINE, SCREEN_ID_AIRLINE);
    	prorationSession.removeAllAttributes();
    	log.log(Log.FINE, "FromScreen...", interlineBillingSession.getFromScreen());
		if("fromInterLineBilling".equals(interlineBillingSession.getFromScreen())){
			
			interlineBillingSession.setDocumentBillingDetailVOs(null);
			interlineBillingSession.setFromScreen(BLANK);
			invocationContext.target = CLOSE_TOINTERLINE;
			
		}
		else{
			invocationContext.target = CLOSE_SUCCESS;
			
		}
		
		log.exiting(CLASS_NAME,"execute"); 
    }

    }
