/**
 * ListInvoiceCommand.java Created on 22 May 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;

/**
 * @author a-4823
 *
 */
public class ListInvoiceCommand extends BaseCommand{
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";      
	private static final String SCREENLOAD_SUCCESS ="screenload_success";
	
	private static final String MODULE_NAME_LISTINVOICE = "mailtracking.mra.gpabilling";
	private static final String SCREENID_LISTINVOICE = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	@Override
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
		GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession = (GPABillingInvoiceEnquirySession) getScreenSession(
				MODULE_NAME_LISTINVOICE, SCREENID_LISTINVOICE);
		GpaBillingInvoiceEnquiryFilterVO filterVO = new GpaBillingInvoiceEnquiryFilterVO();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String counter = form.getSelectedInvoice();
		String companyCode = logonAttributes.getCompanyCode();
		filterVO.setCompanyCode(companyCode);
		Collection<InvoiceSettlementVO> invoiceSettlementDetailsVOs= session.getGPASettlementVO().iterator().next().getInvoiceSettlementVOs();
		ArrayList<InvoiceSettlementVO> invoiceSettlementVOs = new ArrayList<InvoiceSettlementVO>(
				invoiceSettlementDetailsVOs);

		InvoiceSettlementVO invoiceSettlementVO=null;
		invoiceSettlementVO=invoiceSettlementVOs.get(Integer.parseInt(counter));
		filterVO.setInvoiceNumber(invoiceSettlementVO.getInvoiceNumber());	
		filterVO.setGpaCode(invoiceSettlementVO.getGpaCode());
		gPABillingInvoiceEnquirySession
		.setGpaBillingInvoiceEnquiryFilterVO(filterVO);
		form.setListInvoiceFlag("true");
		gPABillingInvoiceEnquirySession.setParentScreenID("settlementcapture");
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

}
