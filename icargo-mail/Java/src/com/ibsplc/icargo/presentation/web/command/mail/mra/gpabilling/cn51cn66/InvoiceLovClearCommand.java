/*
 * InvoiceLovClearCommand.java Created on Feb 08, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2270
 *
 */
public class InvoiceLovClearCommand extends BaseCommand {


	private static final String CLEAR_SUCCESS = "clear_success";
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