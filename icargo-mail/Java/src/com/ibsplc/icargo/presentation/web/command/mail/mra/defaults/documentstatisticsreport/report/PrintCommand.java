/*
 * PrintCommand.java Created on SEP 2 , 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.documentstatisticsreport.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults.DocumentStatisticsSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailDocumentStatisticsForm;
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

	private static final String REPORT_ID = "RPTMIS031";

	private Log log = LogFactory.getLogger("print Document Statistics Report");

	private static final String SUBSYSTEM_ONETIME = "cra.accounting.subsystem";

	private static final String RESOURCE_BUNDLE_KEY = "documentStatisticsReport";

	private static final String ACTION = "printDocumentStatisticsReport";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.documentstatisticsreport";

	private static final String FLIGHT_STATUS = "mra.defaults.flightstatus";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		ErrorVO error = null;
		MailDocumentStatisticsForm form = (MailDocumentStatisticsForm) invocationContext.screenModel;
		DocumentStatisticsSessionImpl session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		DocumentStatisticsFilterVO statisticsFilterVO = new DocumentStatisticsFilterVO();
		log.log(Log.INFO, "inside print command");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		statisticsFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		statisticsFilterVO.setSubSystem(form.getSubSystem());
		if (form.getFlightNo() != null
				&& form.getFlightNo().trim().length() > 0) {
			statisticsFilterVO.setFlightNo(form.getFlightNo());
		}
		if (form.getCarrierCode() != null
				|| form.getCarrierCode().trim().length() != 0) {
			if (validateCarrierCode(form, getApplicationSession().getLogonVO()
					.getCompanyCode(), statisticsFilterVO) == null) {
				statisticsFilterVO.setCarrierCode(form.getCarrierCode()
						.toUpperCase());
			} else {
				error = new ErrorVO(
						"mailtracking.mra.defaults.documentststistics.err.invalidcarrier",
						new Object[] { form.getCarrierCode().toUpperCase() });
				errors.add(error);
			}
		}

		if (form.getFromDate() != null
				&& form.getFromDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFromDate(), "dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				frmDate.setDate(form.getFromDate());
				statisticsFilterVO.setFromDate(frmDate);
			}

		} else {
			statisticsFilterVO.setFromDate(null);
		}
		log.log(Log.INFO, "before to dates4++");
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				toDate.setDate(form.getToDate());
				statisticsFilterVO.setToDate(toDate);

			}

		} else {
			statisticsFilterVO.setToDate(null);
		}

		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes+++12344===>", oneTimes);
		Collection<OneTimeVO> subSystem = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> flightStatus = new ArrayList<OneTimeVO>();
		if (oneTimes != null) {
			subSystem = oneTimes.get(SUBSYSTEM_ONETIME);
			log.log(Log.INFO, "subsystems4++", subSystem);
			flightStatus = oneTimes.get(FLIGHT_STATUS);
			log.log(Log.INFO, "flightStatus++", flightStatus);

		}

		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(statisticsFilterVO);
		reportSpec.addExtraInfo(subSystem);
		reportSpec.addExtraInfo(flightStatus);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);

		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {
			log.log(Log.INFO, "inside getERRORS", getErrors());
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.exiting("MRA_LISTCCA", "PrintCommand exit");
		invocationContext.target = getTargetPage();
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
			fieldValues.add(SUBSYSTEM_ONETIME);
			fieldValues.add(FLIGHT_STATUS);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);
			log.log(Log.INFO, "One Times", oneTimes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.INFO, "inside catch exception");
			businessDelegateException.getMessageVO().getErrors();
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimes;
	}

	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param DocumentStatisticsFilterVO
	 * @return Collection<ErrorVO>
	 * @exception
	 */

	public Collection<ErrorVO> validateCarrierCode(
			MailDocumentStatisticsForm form, String companyCode,
			DocumentStatisticsFilterVO statisticsFilterVO) {
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (form.getCarrierCode() != null
				&& form.getCarrierCode().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, form.getCarrierCode().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO != null) {
				statisticsFilterVO.setCarrierIdentifier(airlineValidationVO
						.getAirlineIdentifier());
			}
		}
		return errors;
	}
}
