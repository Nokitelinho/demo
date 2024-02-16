/*
 * ClearCommand.java Created on Dec 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.invoicelov;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.InvoiceLOVForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2524
 *
 */
public class ClearCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");

	private final static String CLEAR_SUCCESS = "clear_success";
	private final static String EMPTY_STRING = "";
		
	private static final String CLASS_NAME = "ClearCommand";
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "ClearCommand");
		log.entering(CLASS_NAME, "execute");
		InvoiceLOVForm invoiceLovForm = 
			(InvoiceLOVForm)invocationContext.screenModel;
		invoiceLovForm.setInvoiceNumber(EMPTY_STRING);
		invoiceLovForm.setCode(EMPTY_STRING);
		invoiceLovForm.setClearancePeriod(EMPTY_STRING);
		invoiceLovForm.setAirlineCode(EMPTY_STRING);
		invoiceLovForm.setBillingType(EMPTY_STRING);
		invoiceLovForm.setInvoiceLovVos(null);
		log.exiting(CLASS_NAME,"execute");
    	invocationContext.target=CLEAR_SUCCESS;

	}

}
