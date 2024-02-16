/*
 * CloseCommand.java Created on Aug 1, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry;

/**
 * @author a-3447
 */
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 * 
 */
public class CloseCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	private static final String CLOSE_INVOICE_ENQUIRY = "close_success";

	private static final String CLOSE_SCREEN = "close_gpa_inv";

	private static final String CLASS_NAME = "CloseCommand";

	private static final String INVOKE_SCREEN = "invoiceenquiry";

	private Log log = LogFactory.getLogger("CloseCommand :SUCCESS");

	/**
	 * @author A-3447 execute method for Close
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		GPABillingInvoiceEnquiryForm form = (GPABillingInvoiceEnquiryForm) invocationContext.screenModel;
		String screen = form.getInvokingScreen();
		GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession = (GPABillingInvoiceEnquirySession) getScreenSession(
				MODULE, SCREENID);
		String parentScreen = gPABillingInvoiceEnquirySession.getParentScreenID();
		
		
		log.log(Log.INFO, "selected screen-->", screen);
		gPABillingInvoiceEnquirySession.removeAllAttributes();
		if(parentScreen==null){
			log.log(Log.INFO, "\n\n\n<--------DIRECT CLOSE 1 parentscreen null---------->");
			invocationContext.target = "close_gpa_inv";
			
		}else
		if(("listinvoice").equals(parentScreen)){
			log.log(Log.INFO, "\n\n\n<--------FROM LISTINVOICE SCREEn---------->");
			invocationContext.target = "close_success";
		}
		else if (("settlementcapture").equals(parentScreen)){
			log.log(Log.INFO, "\n\n\n<--------FROM SETTLEMENTCAPTURE SCREEN---------->");
			invocationContext.target = "close_settle_success";
		}
		else{
			log.log(Log.INFO, "\n\n\n<--------DIRECT CLOSE 2 ---------->");
			invocationContext.target = "close_gpa_inv";
		}
			
		
		
		/*if (INVOKE_SCREEN.equals(screen)) {
			log.log(Log.INFO, "-->" + screen);
			invocationContext.target = CLOSE_INVOICE_ENQUIRY;
		} else if (!INVOKE_SCREEN.equals(screen)) {
			invocationContext.target = CLOSE_SCREEN;
		}*/

	}

}
