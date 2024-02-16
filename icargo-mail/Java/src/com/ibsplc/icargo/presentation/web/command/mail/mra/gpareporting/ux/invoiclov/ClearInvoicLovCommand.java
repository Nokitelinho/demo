/*
 * ClearInvoicLovCommand.java Created on Dec 13, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoiclov;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.InvoicLovForm;


/** 
 * @author A-8464
 */
public class ClearInvoicLovCommand  extends BaseCommand{
	private static final String SCREEN_SUCCESS = "screenload_success";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		InvoicLovForm invoicLovForm = (InvoicLovForm) invocationContext.screenModel;
		invoicLovForm.setInvoicLovPage(null);
		invoicLovForm.setFromDateFilter(null);
		invoicLovForm.setToDateFilter(null);
		invoicLovForm.setGpaCodeFilter(null);
		invoicLovForm.setGpaCode(null);
		invoicLovForm.setInvoicRefId(null);
		invoicLovForm.setFromDate(null);
		invoicLovForm.setToDate(null);
		invoicLovForm.setLovDescriptionTxtFieldName("");
		invoicLovForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target=SCREEN_SUCCESS;
		
	}

}
