/*
 * InvoiceDetailCommand.java Created on AUGUST 1, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

/**
 * /**
 * 
 * @author A-3447
 * 
 */

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class InvoiceDetails
 * 
 */
/*
 * Revision History Revision Date Author Description
 * ==============================================================================
 * 0.1 01-08-2008 A-3447 For Coding
 * 
 * =============================================================================
 */

/**
 * @author A-3447
 * 
 */
public class InvoiceDetailCommand extends BaseCommand {

	/*
	 * /* The Module and ScreenID mappings
	 */

	private static final String SUCCESS = "invoicedetail_success";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	/**
	 * logger
	 */
	private Log log = LogFactory.getLogger("InvoiceDetailCommand SUCCESS");

	/**
	 * @author A-3447 execute method for InvoiceDetails
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("InvoiceDetails", "execute");
		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession = (GPABillingInvoiceEnquirySession) getScreenSession(
				MODULE, SCREENID);
		GpaBillingInvoiceEnquiryFilterVO filterVO = new GpaBillingInvoiceEnquiryFilterVO();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String counter = listGPABillingInvoiceForm.getSelectedRow();
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession
				.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cN51SummaryVOArraylist = new ArrayList<CN51SummaryVO>(
				cN51SummaryVOs);
		CN51SummaryVO cN51SummaryVO = null;
		log.log(Log.FINE, "counter--->>", counter);
		cN51SummaryVO = cN51SummaryVOArraylist.get(Integer.parseInt(counter));
		log.log(Log.FINE, "cN51SummaryVO selected-->>", cN51SummaryVO);
		String companyCode = logonAttributes.getCompanyCode();
		filterVO.setCompanyCode(companyCode);
		filterVO.setInvoiceNumber(cN51SummaryVO.getInvoiceNumber());
		gPABillingInvoiceEnquirySession
				.setGpaBillingInvoiceEnquiryFilterVO(filterVO);
		log.log(Log.FINE, "filterVO-->>", filterVO);
		gPABillingInvoiceEnquirySession.setParentScreenID("listinvoice");
		
		invocationContext.target = SUCCESS;
		log.exiting("--InvoiceDetailCommand--", "execute");

	}

}
