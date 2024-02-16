/*
 * ClearCommand.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicclaimsenquiry;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicClaimsEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicClaimsEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ClearCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.invoicclaimsenquiry";

	private static final String CLEAR_SUCCESS = "clear_success";

	/**
	 * Method  implementing clearing of screen
	 * @author a-2270
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	
    	InvoicClaimsEnquiryForm form = (InvoicClaimsEnquiryForm)invocationContext.screenModel;
    	InvoicClaimsEnquirySession session = (InvoicClaimsEnquirySession)getScreenSession(MODULE_NAME, SCREENID);
    	// clearing vos in session
    	session.removeMailInvoicClaimsEnquiryVOs();
    	// clearing form fields
    	form.setGpaCode("");
    	form.setFromDate(null);
    	form.setToDate(null);
    	invocationContext.target = CLEAR_SUCCESS;

    }

}

