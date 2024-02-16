/*
 * PrintCommand.java Created on Oct 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-3429
 * 
 */
public class PrintCommand extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String REPORT_ID = "RPTLST301";

	private static final String CLASS_NAME = "PrintCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private static final String INVOICE_STATUS = "mra.gpabilling.invoicestatus";

	private static final String ERROR_KEY_DATE = "mailtracking.mra.gpabilling.listcn51.nodatefields";

	private static final String ERROR_KEY_NO_INVALID_DATE = "mailtracking.mra.gpabilling.listcn51.notvaliddate";

	private static final String BLANK = "";

	private static final String RESOURCE_BUNDLE_KEY = "listgpabillinginvoiceresources";

	private static final String ACTION = "listGPABillingInvoiceReport";

	/**
	 * Method to implement the List operation
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");

		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;

		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(
				MODULE_NAME, SCREENID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		CN51SummaryFilterVO cn51SummaryFilterVO = new CN51SummaryFilterVO();
		// listGPABillingInvoiceSession.removeCN51SummaryVOs();

		errors = validateForm(listGPABillingInvoiceForm, errors);

		if (errors != null && errors.size() > 0) {

			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		cn51SummaryFilterVO.setCompanyCode(companyCode);
		cn51SummaryFilterVO.setFromDate(convertToDate(listGPABillingInvoiceForm
				.getFromDate()));
		cn51SummaryFilterVO.setToDate(convertToDate(listGPABillingInvoiceForm
				.getToDate()));
		if (listGPABillingInvoiceForm.getGpacode() != null
				&& listGPABillingInvoiceForm.getGpacode().trim().length() > 0) {
			cn51SummaryFilterVO.setGpaCode(listGPABillingInvoiceForm
					.getGpacode());
		}
		if (listGPABillingInvoiceForm.getInvoiceNo() != null
				&& listGPABillingInvoiceForm.getInvoiceNo().trim().length() > 0) {
			cn51SummaryFilterVO.setInvoiceNumber(listGPABillingInvoiceForm
					.getInvoiceNo());
		}
		if (listGPABillingInvoiceForm.getInvoiceStatus() != null
				&& listGPABillingInvoiceForm.getInvoiceStatus().trim().length() > 0) {
			cn51SummaryFilterVO.setInvoiceStatus(listGPABillingInvoiceForm
					.getInvoiceStatus());
		}
		cn51SummaryFilterVO.setPageNumber(Integer.parseInt(listGPABillingInvoiceForm.getDisplayPage()));
		/*
		 * Getting OneTime values
		 */
		log.log(Log.INFO, "OneTimes+++===>", cn51SummaryFilterVO);
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes+++===>", oneTimes);
		Collection<OneTimeVO> invoicetatus = new ArrayList<OneTimeVO>();
		if (oneTimes != null) {
			invoicetatus = oneTimes.get(INVOICE_STATUS);
			log.log(Log.INFO, "invoicetatus++", invoicetatus);
		}
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listGPABillingInvoiceForm.getProduct());
		reportSpec.setSubProductCode(listGPABillingInvoiceForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(cn51SummaryFilterVO);
		reportSpec.addExtraInfo(invoicetatus);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);

		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {
			log.log(Log.INFO, "inside getERRORS", getErrors());
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("MRA_LISTGPABILLINGINVOICE", "PrintCommand exit");
		invocationContext.target = getTargetPage();
	}

	/**
	 * 
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date) {

		if (date != null && !date.equals(BLANK)) {

			return (new LocalDate(LocalDate.NO_STATION, Location.NONE, false)
					.setDate(date));
		}
		return null;
	}

	/**
	 * 
	 * @param form
	 * @param errors
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ListGPABillingInvoiceForm form,
			Collection<ErrorVO> errors) {

		if (BLANK.equals(form.getFromDate()) || BLANK.equals(form.getToDate())) {

			errors.add(new ErrorVO(ERROR_KEY_DATE));

		} else if (!validateDate(form.getFromDate(), form.getToDate())) {

			errors.add(new ErrorVO(ERROR_KEY_NO_INVALID_DATE));
		}

		return errors;
	}

	/**
	 * validating fromdate and todate
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private boolean validateDate(String fromDate, String toDate) {

		if (((toDate != null) && (toDate.trim().length() > 0))
				&& ((fromDate != null) && (fromDate.trim().length() > 0))
				&& !(toDate.equals(fromDate))) {

			return DateUtilities.isLessThan(fromDate, toDate,
					LocalDate.CALENDAR_DATE_FORMAT);

		} else {

			return true;
		}
	}

	/**
	 * This method is used to get the one time details
	 * 
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getOneTimeDetails() {
		// server call for onetime
		log.entering("PrintCommand", "getOneTimeDetails");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(INVOICE_STATUS);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimes;
	}
}
