/*
 * InvoiceByClrPrdPrintCommand.java Created on Mar 16,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.report;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.report.OutwardBillingReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2458
 *
 */
public class InvoiceByClrPrdPrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");

	private static final String REPORT_ID = "RPTMRA010";

	private static final String RESOURCE_BUNDLE_KEY = "outwardreport";

	private static final String ACTION = "findInvoicesCollectionByClrPrd";

	private static final String CLEARANCEPERIOD_MANDATORY = "mra.airlinebilling.reports.clrprdmandatory";

	private static final String CLEARANCEPERIOD_INVALID = "mailtracking.mra.airlinebilling.error.invalidClearancePeriod";

	private static final String PRINT_FAILURE = "normal-report-error-jsp";

	/**
	 * @param invocationContext
	 * @return void
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("MRA_AIRLINEBILLING", " Outward Print Command entered");
		OutwardBillingReportForm form = (OutwardBillingReportForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = null;
		ErrorVO errorVO = null;

		String companyCode = getApplicationSession().getLogonVO()
		.getCompanyCode();
		String clearancePeriod = form.getRpt010ClrPrd().trim().toUpperCase();
		String airlineCode = form.getRpt010AirlineCode().trim().toUpperCase();
		String airlineNumber = form.getRpt010AirlineNum();

		errors = validateForm(companyCode, airlineNumber, airlineCode,
				clearancePeriod);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_FAILURE;
			return;
		}

		AirlineCN51FilterVO airlineCN51FilterVO = new AirlineCN51FilterVO();
		airlineCN51FilterVO.setCompanyCode(companyCode);

		airlineCN51FilterVO.setInterlineBillingType(form.getBillingType());
		if (clearancePeriod != null && clearancePeriod.trim().length() > 0) {
			airlineCN51FilterVO.setIataClearancePeriod(clearancePeriod);
		} else {

			errors = new ArrayList<ErrorVO>();
			errorVO = new ErrorVO(CLEARANCEPERIOD_MANDATORY);
			errors.add(errorVO);

		}

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_FAILURE;
			return;
		}
		if (airlineNumber != null && airlineNumber.trim().length() > 0)
		{
			airlineCN51FilterVO.setAirlineNum(airlineNumber);
		}
		if (airlineCode != null && airlineCode.trim().length() > 0)
		{
			airlineCN51FilterVO.setAirlineCode(airlineCode);
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

		log.exiting("MRA_AIRLINEBILLING", " Outward PrintCommand exit");
		invocationContext.target = getTargetPage();

	}

	/**
	 * @param companyCode
	 * @param airlineNumber
	 * @param airlineCode
	 * @param clearancePeriod
	 * @return  Collection<ErrorVO> 
	 */
	private Collection<ErrorVO> validateForm(String companyCode,
			String airlineNumber, String airlineCode, String clearancePeriod) {
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate();
		Collection<ErrorVO> errors = null;
		IATACalendarVO iatacalendarvo = null;
		IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
		iatacalendarfiltervo.setCompanyCode(companyCode);
		iatacalendarfiltervo.setClearancePeriod(clearancePeriod);
		if (clearancePeriod != null && clearancePeriod.trim().length() > 0) {
			try {

				iatacalendarvo=mailTrackingMRADelegate.validateClearancePeriod(iatacalendarfiltervo);
			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}
		}
		if (iatacalendarvo != null ) {
			log.log(Log.INFO, "iatacalendarvo not null ", iatacalendarvo);

		} else {
			log.log(log.INFO, "iatacalendarvo null--->");
			errors = new ArrayList<ErrorVO>();
			ErrorVO err = new ErrorVO(CLEARANCEPERIOD_INVALID);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
		}
		if (airlineNumber != null && airlineNumber.trim().length() > 0) {
			try {
				new AirlineDelegate().validateNumericCode(companyCode, airlineNumber);

			} catch (BusinessDelegateException businessDelegateException) {
				return handleDelegateException(businessDelegateException);
			}

		}
		if (airlineCode != null && airlineCode.trim().length() > 0) {
			try {
				new AirlineDelegate().validateAlphaCode(companyCode, airlineCode);

			} catch (BusinessDelegateException businessDelegateException) {
				return handleDelegateException(businessDelegateException);
			}
		}

		return errors;
	}
}
