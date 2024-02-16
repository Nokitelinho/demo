/*
 * ListCommand.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.InvoicEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquiryDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicEnquirySummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2270
 * 
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.invoicenquiry";

	private static final String LISTDETAILS_SUCCESS = "list_success";

	private static final String LISTDETAILS_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.defaults.invoicenquiry.nodatafound";

	private static final String KEY_MANDATORYCHK = "mailtracking.mra.defaults.invoicenquiry.noinvkey";

	private static final String BLANK = "";

	private static final String RECONCILIATION_STATUS_YES = "Y";

	private static final String RECONCILIATION_STATUS_RECONCILED = "RECONCILED";

	private static final String RECONCILIATION_STATUS_NEW = "NEW";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		InvoicEnquiryForm form = (InvoicEnquiryForm) invocationContext.screenModel;
		InvoicEnquirySession session = (InvoicEnquirySession) getScreenSession(
				MODULE, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Page<MailInvoicEnquiryDetailsVO> mailInvoicEnquiryDetailsVOs = null;
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		InvoicEnquiryFilterVO invoiceEnquiryFilterVo= new InvoicEnquiryFilterVO();
		// filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		invoiceEnquiryFilterVo.setCompanyCode(companyCode);
		invoiceEnquiryFilterVo.setDisplayPage(Integer.parseInt(form
					.getDisplayPage()));
		if (form.getInvoiceKey() == BLANK) {
			errors.add(new ErrorVO(KEY_MANDATORYCHK));
			invocationContext.addAllError(errors);
			invocationContext.target = LISTDETAILS_FAILURE;
			return;
		}
		else{
			invoiceEnquiryFilterVo.setInvoiceKey(form.getInvoiceKey());
		}
		log.log(Log.INFO, "Invoice number is -->", form.getInvoiceKey());
		//Added by A-5218 to enable Last Link in Pagination to start
		if(InvoicEnquiryForm.PAGINATION_MODE_FROM_FILTER.equals(form.getPaginationMode())){
			invoiceEnquiryFilterVo.setTotalRecordCount(-1);
		}
	    else{
	    	invoiceEnquiryFilterVo.setTotalRecordCount(session.getTotalRecords().intValue());
	    }
		//Added by A-5218 to enable Last Link in Pagination to end

		/*
		 * calling MailTrackingMRADelegate
		 */
		try {
			log.log(Log.FINE, "Inside try : Calling findInvoicEnquiryDetails");
			mailInvoicEnquiryDetailsVOs = delegate.
			                   findInvoicEnquiryDetails(invoiceEnquiryFilterVo);
			log.log(Log.FINE, "mailInvoicEnquiryDetailsVOs from Server:--> ",
					mailInvoicEnquiryDetailsVOs);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught Exception");
		}

		// for setting the invocation context in case of success
		if (mailInvoicEnquiryDetailsVOs != null
				&& mailInvoicEnquiryDetailsVOs.size() > 0
				|| mailInvoicEnquiryDetailsVOs != null
				&& mailInvoicEnquiryDetailsVOs.size() > 0) {
			MailInvoicEnquirySummaryVO summaryVO = new MailInvoicEnquirySummaryVO();
			summaryVO.setPoaCode(mailInvoicEnquiryDetailsVOs.get(0)
					.getPoaCode());
			summaryVO.setScheduledInvoiceDate(mailInvoicEnquiryDetailsVOs
					.get(0).getScheduledInvoiceDate());
			summaryVO.setPaymentType(mailInvoicEnquiryDetailsVOs.get(0)
					.getPaymentType());
			summaryVO
					.setReconcilStatus(RECONCILIATION_STATUS_YES
							.equals(mailInvoicEnquiryDetailsVOs.get(0)
									.getReconcilStatus()) ? RECONCILIATION_STATUS_RECONCILED
							: RECONCILIATION_STATUS_NEW);
			summaryVO.setCarrierCode(mailInvoicEnquiryDetailsVOs.get(0)
					.getCarrierCode());
			summaryVO.setCarrierName(mailInvoicEnquiryDetailsVOs.get(0)
					.getCarrierName());
			summaryVO.setControlValue(mailInvoicEnquiryDetailsVOs.get(0)
					.getControlValue());

			summaryVO.setTotalAdjustmentAmount(mailInvoicEnquiryDetailsVOs.get(
					0).getTotalAdjustmentAmount());
			session.setMailInvoicEnquiryDetailsVOs(mailInvoicEnquiryDetailsVOs);
			session.setMailInvoicEnquirySummaryVO(summaryVO);
			log.log(Log.FINEST, "SummaryVo ", summaryVO);
			invocationContext.target = LISTDETAILS_SUCCESS;
		}
		// for setting the invocation context in case of failure
		if (mailInvoicEnquiryDetailsVOs == null
				|| mailInvoicEnquiryDetailsVOs.size() == 0
				&& mailInvoicEnquiryDetailsVOs == null
				|| mailInvoicEnquiryDetailsVOs.size() == 0) {
			log.log(Log.FINE, "results from Server is ::null::");
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			invocationContext.target = LISTDETAILS_FAILURE;
		}
		//Added by A-5218 to enable Last Link in Pagination to start
		if(mailInvoicEnquiryDetailsVOs!=null && mailInvoicEnquiryDetailsVOs.size()>0){
			session.setListDisplayPages(mailInvoicEnquiryDetailsVOs);
			session.setTotalRecords(mailInvoicEnquiryDetailsVOs.getTotalRecordCount());
		}
		//Added by A-5218 to enable Last Link in Pagination to end
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		log.exiting(CLASS_NAME, "execute");
	}

}
