/*
 * InvoiceDtlByAirlinePrintCommand.java Created on Mar 16,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.report;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.report.OutwardBillingReportForm;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;

/**
 * @author A-2458
 *
 */
public class InvoiceDtlByAirlinePrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");

	private static final String REPORT_ID = "RPTMRA011";

	private static final String RESOURCE_BUNDLE_KEY = "outwardreport";

	private static final String ACTION = "findInvoicesCollectionByAirline";

	private static final String AIRLINECODE_MANDATORY = "mra.airlinebilling.reports.airlinecodemandatory";

	private static final String AIRLINENUMBER_MANDATORY = "mra.airlinebilling.reports.airlinenumbermandatory";

	private static final String INVALID_DATERANGE = "mra.airlinebilling.reports.invaliddaterange";

	private static final String PRINT_FAILURE = "normal-report-error-jsp";

	private static final String BLANK = "";

	/**
	 * @param invocationContext
	 * @return void
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("MRA_AIRLINEBILLING",
				"  Outward InvoiceDetailByAirlinePrintCommand entered");
			OutwardBillingReportForm form = (OutwardBillingReportForm) invocationContext.screenModel;
			AirlineCN51FilterVO airlineCN51FilterVO = new AirlineCN51FilterVO();
			Collection<ErrorVO> errors = null;
			errors = validateForm(form, airlineCN51FilterVO);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = PRINT_FAILURE;
				return;
			}
			getReportSpec().setReportId(REPORT_ID);
			getReportSpec().setProductCode(form.getProduct());
			getReportSpec().setSubProductCode(form.getSubProduct());
			getReportSpec().setPreview(true);
			getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
			getReportSpec().addFilterValue(airlineCN51FilterVO);
			getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
			getReportSpec().setAction(ACTION);
			generateReport();

			if (getErrors() != null && getErrors().size() > 0) {
				invocationContext.addAllError(getErrors());
				invocationContext.target = PRINT_FAILURE;
				return;
			}
		
		log.exiting("MRA_AIRLINEBILLING",
				"Outward InvoiceDetailByAirlinePrintCommand exit");
		invocationContext.target = getTargetPage();
	}

	/**
	 * @param form
	 * @param airlineCN51FilterVO
	 * @return  Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(OutwardBillingReportForm form,
			AirlineCN51FilterVO airlineCN51FilterVO)

	{
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVo = null;
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		String airlineCode = form.getRpt011AirlineCode().trim().toUpperCase();
		String airlineNumber = form.getRpt011AirlineNum();
		String frmDate = form.getRpt011FromDate();
		String tooDate = form.getRpt011ToDate();
		if (airlineNumber != null && airlineNumber.trim().length() > 0) {
			try {
				new AirlineDelegate().validateNumericCode(companyCode, airlineNumber);
				airlineCN51FilterVO.setAirlineNum(airlineNumber);
				
			} catch (BusinessDelegateException businessDelegateException) {
				return handleDelegateException(businessDelegateException);

			}

		}
		if (airlineCode != null && airlineCode.trim().length() > 0) {
			try {
				AirlineValidationVO airlineValidationVO = new AirlineDelegate()
						.validateAlphaCode(companyCode, airlineCode);
				airlineCN51FilterVO.setAirlineIdentifier(airlineValidationVO
						.getAirlineIdentifier());
				airlineCN51FilterVO.setAirlineCode(airlineCode);
				
			} catch (BusinessDelegateException businessDelegateException) {
				return handleDelegateException(businessDelegateException);
			}
		}
		airlineCN51FilterVO.setCompanyCode(companyCode);
		airlineCN51FilterVO.setInterlineBillingType(form.getBillingType());//to change form.getRpt018BillingType()
		
		if (!BLANK.equals(frmDate)) {
			airlineCN51FilterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(frmDate));
		}
		if (!BLANK.equals(tooDate)) {
			airlineCN51FilterVO.setToDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(tooDate));

		}

		if ((frmDate != null && frmDate.trim().length() > 0)
				&& (tooDate != null && tooDate.trim().length() > 0)) {
			if (airlineCN51FilterVO.getToDate().before(
					airlineCN51FilterVO.getFromDate())) {
				errorVo = new ErrorVO(INVALID_DATERANGE);
				errors.add(errorVo);
			}
		}
		return errors;
	}
}