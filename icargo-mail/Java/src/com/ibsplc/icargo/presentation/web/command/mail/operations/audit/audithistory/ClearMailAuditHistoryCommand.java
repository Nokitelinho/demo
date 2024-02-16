 /*
 * ClearCommand.java Created on Jul 1 2016 by A-5991
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.audit.audithistory;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAuditHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAuditHistoryForm;

public class ClearMailAuditHistoryCommand extends BaseCommand{


	private static final String SCREEN_ID = "mailtracking.defaults.mailaudithistory";
	private static final String MODULE_NAME = "mail.operations";
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String BLANK_STRING = "";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		MailAuditHistoryForm  mailAuditHistoryForm = (MailAuditHistoryForm)invocationContext.screenModel;
		MailAuditHistorySession mailAuditHistorySession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		      
		mailAuditHistoryForm.setAuditableField(BLANK_STRING);
		mailAuditHistoryForm.setScreenMode(BLANK_STRING);
		mailAuditHistoryForm.setTransaction(BLANK_STRING);	
		mailAuditHistoryForm.setDestinationExchangeOffice(BLANK_STRING);
		mailAuditHistoryForm.setMailCategoryCode(BLANK_STRING);
		mailAuditHistoryForm.setMailCategoryCode(BLANK_STRING);
		Collection<String> auditableFields = mailAuditHistorySession.getAuditableFields();
		HashMap<String,String> txnCodes = mailAuditHistorySession.getTransactions();
		mailAuditHistorySession.removeAllAttributes();
		mailAuditHistorySession.setAuditableFields(auditableFields);
		mailAuditHistorySession.setTransactions(txnCodes);
		invocationContext.target = CLEAR_SUCCESS;
		
	}

}
