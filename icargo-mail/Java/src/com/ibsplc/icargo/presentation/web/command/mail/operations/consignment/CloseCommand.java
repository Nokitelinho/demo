/*
 * CloseCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MAIL OPERATIONS");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREENID = "mailtracking.defaults.consignment";
	private static final String MODULE_AIRLINE = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID_AIRLINE = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	private static final String CLOSE_SUCCESS = "close_success";
	private static final String  CLOSE_TOINTERLINE = "close_toairline";
	private static final String  FROM_INTERLINE = "fromInterLineBilling";
	private static final String  FROM_CARDIT = "carditEnquiry";
	private static final String TO_INTERLINE = "toInterLineBilling";
	private static final String CLOSE_CARDIT = "close_cardit";
	
        
 
	/**
	 * 	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ConsignmentForm consignmentForm = 
    		(ConsignmentForm)invocationContext.screenModel;
    
    	ListInterlineBillingEntriesSession interlineBillingSession = (ListInterlineBillingEntriesSession) getScreenSession(
    			MODULE_AIRLINE, SCREEN_ID_AIRLINE);
    	if(FROM_CARDIT.equals(consignmentForm.getFromScreen())){
			log.log(Log.INFO,"FRM CARDIT IN CLSOE COMMAND");
			consignmentForm.setFromScreen("");
			invocationContext.target = CLOSE_CARDIT;
			
		}
    	else if(FROM_INTERLINE.equals(consignmentForm.getFromScreen())){
			log.log(Log.INFO,"FROM_INTERLINE IN CLSOE COMMAND");
			interlineBillingSession.setCloseFlag(TO_INTERLINE);
			invocationContext.target = CLOSE_TOINTERLINE;
		
		}
		else{
			log.log(Log.INFO, "none IN CLSOE COMMAND", consignmentForm.getFromScreen());
			invocationContext.target = CLOSE_SUCCESS;
			
		}
		
		log.exiting(CLASS_NAME,"execute"); 
    }

    }
