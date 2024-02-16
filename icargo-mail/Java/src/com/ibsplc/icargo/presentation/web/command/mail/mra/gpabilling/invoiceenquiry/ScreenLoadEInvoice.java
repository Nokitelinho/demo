/*
 * ScreenLoadEInvoice.java Created on Jan 23, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2554
 *
 */
public class ScreenLoadEInvoice extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "GenerateAndScreenLoadEInvoice";

	private static final String EINVOICE_SCREENLOADSUCCESS = "einvoicescreenload_success";

	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	


	/**
	 * Method to implement the clear operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		GPABillingInvoiceEnquiryForm form =(GPABillingInvoiceEnquiryForm)invocationContext.screenModel;
		GPABillingInvoiceEnquirySession session = (GPABillingInvoiceEnquirySession)getScreenSession(
				MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		CN51SummaryVO cN51SummaryVO=null;
		if(session !=null && session.getCN51SummaryVO()!=null){
		cN51SummaryVO = session.getCN51SummaryVO();
		}
		log.log(Log.FINE,
				"Inside Finalize invoice command-b4 delegate call- >>",
				cN51SummaryVO);
		if(cN51SummaryVO!=null){
    	log.log(Log.INFO, "\n\n\n\n<-------GENERATING EINVOICE---->");
		String EInvoiceMsg="";
		try {
			EInvoiceMsg = mailTrackingMRADelegate.generateEInvoiceMessage(cN51SummaryVO);
			form.setEInvoiceMessage(EInvoiceMsg);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}		
		
		}

    	invocationContext.target = EINVOICE_SCREENLOADSUCCESS;
    }

}
