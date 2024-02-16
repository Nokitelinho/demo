/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.InvoiceLovClearCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	21-May-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceLovForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.invoicesettlementmailbag.InvoiceLovClearCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	21-May-2018	:	Draft
 */
public class InvoiceLovClearCommand extends BaseCommand{
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.ux";
	private Log log = LogFactory.getLogger(
	"MRA invoice ClearCommand");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ClearCommand","execute");
    	InvoiceLovForm form =
			(InvoiceLovForm)invocationContext.screenModel;
    	form.setCode("");
    	form.setGpaCodeFilter("");


    	form.setLastPageNum("");
    	form.setInvoiceLovPage(null);

    	invocationContext.target=CLEAR_SUCCESS;
		log.exiting("ClearCommand","execute");

    }
}
