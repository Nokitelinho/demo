/*
 * ClearCommand.java Created on Jun 30, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 * 
 */
public class ClearCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	private static final String CLEAR_SUCCESS = "clear_success";

	

	private static final String BLANK = "";

	/**
	 * Method to implement the clear operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	GPABillingInvoiceEnquiryForm  Form = ( GPABillingInvoiceEnquiryForm)invocationContext.screenModel;

    	GPABillingInvoiceEnquirySession session =
    		(GPABillingInvoiceEnquirySession )getScreenSession(MODULE_NAME, SCREENID);

    	// clearing vos in session
    	session.removeCN66VOs();
    	session.removeCN51SummaryVO();
    	session.setGpaBillingInvoiceEnquiryFilterVO(null);

    	// clearing form fields
    	 Form.setGpaCode(BLANK);
    	 Form.setGpaName(BLANK);
    	 Form.setInvoiceNo(BLANK);
    	 Form.setFrmDate(BLANK);
    	 Form.setToDate(BLANK);
    	 Form.setInvoiceStatus(BLANK);
    	 Form.setInvoiceDate(BLANK);
    	 Form.setCurrency(BLANK);

    	invocationContext.target = CLEAR_SUCCESS;
    }

}
