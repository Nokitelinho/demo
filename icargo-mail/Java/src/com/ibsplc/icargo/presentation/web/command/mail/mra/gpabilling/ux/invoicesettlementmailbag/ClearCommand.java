/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ClearCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	24-Apr-2018
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.ClearCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	24-Apr-2018	:	Draft
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger(" ClearCommand");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel";
	private static final String ACTION_SUCCESS = "clear_success";
	private static final String CLEAR="clear";
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7531 on 26-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		log.entering(CLASS_NAME, "execute");
		
		
		InvoiceSettlementMailbagSession session=null;
		session=(InvoiceSettlementMailbagSession) getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementMailbagForm form=(InvoiceSettlementMailbagForm)invocationContext.screenModel;
		
		session.setInvoiceSettlementFilterVO(null);
		session.setSelectedGPASettlementVO(null);
		session.setInvoiceSettlementHistoryVO(null);
		form.setInvoiceNumber("");
		form.setInvoiceStatus("");
		form.setBillingfromDate("");
		form.setBillingtoDate("");
		form.setChequeNumberFilter("");
		form.setGpaCode("");
		form.setGpaCodeFilter("");
		form.setGpaName("");
		form.setMailbagId("");
		form.setMailbagidHistory("");
		form.setMcaNumber("");
		form.setSettlementReferenceNumber("");
		form.setTotalBilledAmt("");
		form.setTotalSettledAmt("");
		form.setPopupFlag(false);
		form.setActionFlag("CLEAR");
		
		
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		
	}
}
