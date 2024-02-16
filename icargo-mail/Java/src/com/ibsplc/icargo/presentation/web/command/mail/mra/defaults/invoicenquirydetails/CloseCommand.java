/*
 * CloseCommand.java Created on Aug 01, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicenquirydetails;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquirySummaryVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquiryDetailsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicEnquiryDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2270
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Aug 01, 2007			a-2270		Created
 */
public class CloseCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "CloseCommand";
		
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "close_success";
	/*
	 * String for MODULE_NAME, SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.defaults.invoicenquirydetails";	
	
	private static final String SCREENID_PARENT = "mailtracking.mra.defaults.invoicenquiry";
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");		
		/*
		 * getting session
		 */
		InvoicEnquiryDetailsSession session = (InvoicEnquiryDetailsSession)getScreenSession(MODULE_NAME, SCREENID);
		InvoicEnquirySession invoicEnquirySession = (InvoicEnquirySession)getScreenSession(MODULE_NAME, SCREENID_PARENT);
		MailInvoicEnquirySummaryVO mailInvoicEnquirySummaryVO =  new MailInvoicEnquirySummaryVO();
		String invoiceNumber = session.getMailInvoicEnquiryDetailsVO().getInvoiceKey();
		log.log(Log.FINE, "invoice number set in sEssion is##########",
				invoiceNumber);
		mailInvoicEnquirySummaryVO.setInvoiceKey(invoiceNumber);
		if(invoicEnquirySession.getMailInvoicEnquirySummaryVO()!=null){
			invoicEnquirySession.getMailInvoicEnquirySummaryVO().setInvoiceKey(invoiceNumber);
		}
		//invoicEnquirySession.setMailInvoicEnquirySummaryVO(mailInvoicEnquirySummaryVO);
		InvoicEnquiryDetailsForm form = (InvoicEnquiryDetailsForm)invocationContext.screenModel;
		session.removeMailInvoicEnquiryDetailsVO();
		session.removeAllAttributes();
		form.setInvoiceKey("");
		form.setReceptacleIdentifier("");
		form.setReceptacleIdentifier("");
		form.setSectorOrigin("");
		form.setSectorDestination("");
		invocationContext.target = ACTION_SUCCESS;      	
		}
}
