/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.CloseCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	23-May-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ux.InvoiceSettlementMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceSettlementMailbagForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.CloseCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	23-May-2018	:	Draft
 */
public class CloseCommand extends BaseCommand {
	private Log log = LogFactory.getLogger(" CloseCommand");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
	private static final String ACTION_SUCCESS = "close_success";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		log.entering(CLASS_NAME, "execute");
		
		
		InvoiceSettlementMailbagSession session = (InvoiceSettlementMailbagSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationContext.screenModel;
		
		session.removeAllAttributes();
		
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		
	
}

}
