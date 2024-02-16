/*
 * ClearCommand.java Created on Jun 30, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
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

	private static final String SCREENID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String CLEAR_FAILURE = "clear_failure";

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

    	 ListGPABillingInvoiceForm  listGPABillingInvoiceForm = ( ListGPABillingInvoiceForm)invocationContext.screenModel;

    	 ListGPABillingInvoiceSession listGPABillingInvoiceSession =
    		(ListGPABillingInvoiceSession)getScreenSession(MODULE_NAME, SCREENID);

    	// clearing vos in session
    	 listGPABillingInvoiceSession.removeCN51SummaryVOs();
    	 listGPABillingInvoiceSession.removeCN51SummaryFilterVO();
    	 listGPABillingInvoiceSession.setCN51SummaryFilterVO(null);
    	 listGPABillingInvoiceSession.setCN51SummaryVOs(null);
    	 

    	// clearing form fields
    	listGPABillingInvoiceForm.setGpacode(BLANK);
    	listGPABillingInvoiceForm.setInvoiceNo(BLANK);
    	listGPABillingInvoiceForm.setInvoiceStatus(BLANK);
    	listGPABillingInvoiceForm.setFromDate(BLANK);
    	listGPABillingInvoiceForm.setToDate(BLANK);
    	listGPABillingInvoiceForm.setFromDate(BLANK);
    	listGPABillingInvoiceForm.setToDate(BLANK);
    	listGPABillingInvoiceForm.setDisplayPage("1");
		listGPABillingInvoiceForm.setPassFileName(BLANK);
		listGPABillingInvoiceForm.setCheckPASS(BLANK);
		listGPABillingInvoiceForm.setPeriodNumber(BLANK);
    	//listGPABillingInvoiceForm.setViewFlag(BLANK);
    	listGPABillingInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = CLEAR_SUCCESS;
    }

}
