/*
 * GenerateCommand.java Created on Sep 15, 2010
 * 
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailexceptionreports;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailExceptionReportsFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailExceptionsReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-2414
 * 
 * Data for the report is fetched.
 *
 */
public class GenerateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "GenerateCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREENID = "mailtracking.mra.defaults.mailexceptionreports";

	private static final String GENERATE_SUCCESS = "generate_success";
	private static final String GENERATE_FAILURE = "generate_failure";
	
	private static final String ERROR_FROM_DATE_MANDATORY ="mailtracking.mra.defaults.report.error.fromdatemandatory";
	private static final String ERROR_TO_DATE_MANDATORY ="mailtracking.mra.defaults.report.error.todatemandatory";
	private static final String ERROR_TO_DATE_LESS_THAN_FROM_DATE ="mailtracking.mra.defaults.report.error.todatelessthanfromdate";
	private static final String ERROR_NO_DATA_FOUND ="mailtracking.mra.defaults.report.error.nodatafound";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @return void
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailExceptionsSession mailExceptionsSession = getScreenSession(
				MODULE_NAME, SCREENID);

		MailExceptionsReportForm mailExceptionsReportForm = (MailExceptionsReportForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailExceptionReportsFilterVO mailExceptionReportsFilterVo = new MailExceptionReportsFilterVO();

		/*
		 * Setting the form values into the VO fields.
		 */
		mailExceptionReportsFilterVo.setCompanyCode(logonAttributes.getCompanyCode());
		mailExceptionReportsFilterVo.setOwnerIdentifier(logonAttributes.getOwnAirlineIdentifier());
		mailExceptionReportsFilterVo.setReportType(mailExceptionsReportForm
				.getReportType());
		
		log.log(Log.FINE, "After setting mailExceptionReportsFilterVo --->\n",
				mailExceptionReportsFilterVo);
		/*
		 * Performing the mandatory field checks
		 * Mandatory fields - From Date, To Date and Report Type
		 * 
		 * No check is given for Report Type since it is always defaulted to a value
		 * on screenload.
		 */
		if (mailExceptionsReportForm.getFromDate() != null
				&& mailExceptionsReportForm.getFromDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(mailExceptionsReportForm
					.getFromDate(), "dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				frmDate.setDate(mailExceptionsReportForm.getFromDate());
				mailExceptionReportsFilterVo.setFromDate(frmDate);
			}
		} else {
			log.log(Log.FINE,"From date is mandatory");
			ErrorVO errorVo = new ErrorVO(ERROR_FROM_DATE_MANDATORY);
			errors.add(errorVo);
		}
		
		if (mailExceptionsReportForm.getToDate() != null
				&& mailExceptionsReportForm.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(mailExceptionsReportForm
					.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				toDate.setDate(mailExceptionsReportForm.getToDate());
				mailExceptionReportsFilterVo.setToDate(toDate);
			}
		} else {
			log.log(Log.FINE,"To date is mandatory");
			ErrorVO errorVo = new ErrorVO(ERROR_TO_DATE_MANDATORY);
			errors.add(errorVo);
		}
		
		/*
		 * Validation to check if to date is greater than or equal to from date.
		 */
		if (mailExceptionsReportForm.getFromDate()!= null
				&& mailExceptionsReportForm.getToDate()!= null
				&& !"".equals(mailExceptionsReportForm.getFromDate().trim())
				&& !"".equals(mailExceptionsReportForm.getToDate().trim())) {
			if (!mailExceptionsReportForm.getFromDate().equals(
					mailExceptionsReportForm.getToDate())) {
				if (DateUtilities.isLessThan(mailExceptionsReportForm
						.getToDate(), mailExceptionsReportForm.getFromDate(),
						"dd-MMM-yyyy")) {
					ErrorVO error = new ErrorVO(
							ERROR_TO_DATE_LESS_THAN_FROM_DATE);
					errors.add(error);
				}
			}
		}
		
		if(errors!=null&& errors.size()>0){
			log.log(Log.FINE,"Going to return control");
			invocationContext.addAllError(errors);
			invocationContext.target = GENERATE_FAILURE;
			return;
		}
		
		/*
		 * Calling method to get the formatted data for file.
		 */
		MailTrackingMRADelegate mailTrackingMraDelegate = new MailTrackingMRADelegate();
		
		String fileData = null;
		try {
			fileData = mailTrackingMraDelegate.generateMailExceptionReport(mailExceptionReportsFilterVo);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			invocationContext.target = GENERATE_FAILURE;
			return;
		}
		
		if(fileData == null || "".equals(fileData)){
			log.log(Log.FINE,"No data found");
			ErrorVO errorVo = new ErrorVO(ERROR_NO_DATA_FOUND);
			errors.add(errorVo);
		}
		
		if(errors!=null&& errors.size()>0){
			log.log(Log.FINE,"Going to return control");
			invocationContext.addAllError(errors);
			invocationContext.target = GENERATE_FAILURE;
			return;
		}
		
		mailExceptionsSession.setData(fileData);
		
		log.exiting(CLASS_NAME, "execute");
		
		invocationContext.target=GENERATE_SUCCESS;
	}
		
}
