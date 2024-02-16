/*
 *
 * OnSaveCommand.java Created on Sep 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.upucalendar.maintainupucalendar;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.UPUCalendarForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-2270
 * 
 * Save functionality of upu calendar
 * 
 */
public class OnSaveCommand extends BaseCommand {

	/**
	 * String for CLASS_NAME
	 */
	private static final String CLASS_NAME = "OnSaveCommand";

	/**
	 * Log for AddCommand
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING DEFAULTS");

	/*
	 * Target String for success
	 */
	private static final String ONSAVE_SUCCESS = "OnSave_success";

	private static final String ONSAVE_FAILURE = "OnSave_failure";

	private static final String ERROR_KEY_NO_INVALID_DATE = "mra.airlinebilling.defaults.upucalendar.notvaliddate";

	private static final String ERROR_KEY_NO_DATE = "mra.airlinebilling.defaults.upucalendar.nodatefields";

	private static final String ERROR_KEY_PRIMARY_KEY_MISSING = "mra.airlinebilling.defaults.upucalendar.noprimarykey";

	/*
	 * Strings for SCREEN_ID and MODULE_NAME
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.upucalendar";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String BLANK = "";

	/**
	 * Method execute
	 * 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		UPUCalendarForm upuCalendarForm = (UPUCalendarForm) invocationContext.screenModel;

		Collection<UPUCalendarVO> upuCalendarVOs = new ArrayList<UPUCalendarVO>();

		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		/** getting values from form */

		String[] lastUpdateUser = upuCalendarForm.getLastUpdateUser();

		String[] lastUpdateTime = upuCalendarForm.getLastUpdateTime();

		String[] billingPeriod = upuCalendarForm.getBillingPeriod();

		String[] fromDate = upuCalendarForm.getFromDate();

		String[] toDate = upuCalendarForm.getToDate();

		String[] submissionDate = upuCalendarForm.getSubmissionDate();

		String[] generateAfterToDate = upuCalendarForm.getGenerateAfterToDate();

		String[] opFlags = upuCalendarForm.getOperationalFlag();

		UPUCalendarSession upuCalendarSession = (UPUCalendarSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();

		ArrayList<UPUCalendarVO> updVos = (ArrayList<UPUCalendarVO>) upuCalendarSession
				.getUPUCalendarVOs();

		// log.log(Log.FINE, "size of UpdVos>>"+updVos.size());

		if (opFlags != null) {
			int index = 0;
			// int timeIndex = 1;
			Boolean blgFlag = true;
			Boolean dateFlag = true;
			Boolean dateChkFlag = true;

			for (String opFlag : opFlags) {
				log.log(Log.FINEST, "OPERATION FLAG-->", opFlag);
				UPUCalendarVO upuCalVO = new UPUCalendarVO();
				if (OPERATION_FLAG_INSERT.equals(opFlag)
						|| OPERATION_FLAG_DELETE.equals(opFlag)) {
					upuCalVO.setOperationalFlag(opFlag);
				} else if (!"NOOP".equals(opFlag)) {
					upuCalVO.setOperationalFlag(OPERATION_FLAG_UPDATE);
				}
				if (!"NOOP".equals(opFlag)) {
					log.log(Log.FINE, "inside opflag!= NOOP index is ", index);
					upuCalVO.setCompanyCode(companyCode);

					if (!BLANK.equals(billingPeriod[index])) {
						upuCalVO.setBillingPeriod(billingPeriod[index]);
					} else {
						upuCalVO.setBillingPeriod(BLANK);
						if (blgFlag) {
							log.log(Log.FINEST, "INSIDE BLGPRD NULL");
							blgFlag = false;
							error = new ErrorVO(ERROR_KEY_PRIMARY_KEY_MISSING);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
						}

					}

					if (!BLANK.equals(fromDate[index])) {
						upuCalVO.setFromDate(new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false)
								.setDate(fromDate[index]));
					} else {
						upuCalVO.setFromDate(null);
					}

					if (!BLANK.equals(toDate[index])) {
						upuCalVO.setToDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false).setDate(toDate[index]));
					} else {
						upuCalVO.setToDate(null);
					}

					if (!BLANK.equals(submissionDate[index])) {
						upuCalVO.setSubmissionDate(new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false)
								.setDate(submissionDate[index]));
					} else {
						upuCalVO.setSubmissionDate(null);
					}

					if (!BLANK.equals(generateAfterToDate[index])) {
						upuCalVO.setGenerateAfterToDate(Integer
								.parseInt(generateAfterToDate[index]));
					} else {
						upuCalVO.setGenerateAfterToDate(0);
					}

					if ((BLANK.equals(fromDate[index]) || BLANK
							.equals(toDate[index]))
							&& dateFlag) {
						log.log(Log.FINEST, "INSIDE DATE IS  NULL");
						error = new ErrorVO(ERROR_KEY_NO_DATE);
						errors.add(error);
						dateFlag = false;
					} else if (!(validateDate(fromDate[index], toDate[index]))
							&& dateChkFlag) {
						log.log(Log.FINEST, "INSIDE DATECHK IS  NULL");
						error = new ErrorVO(ERROR_KEY_NO_INVALID_DATE);
						errors.add(error);
						dateChkFlag = false;
					}
					/**
					 * Added for Optimistic Locking mechanism
					 */

					if ("I".equals(opFlag)) {
						ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
						LogonAttributes logonAttributes = applicationSessionImpl
								.getLogonVO();
						upuCalVO.setLastUpdateUser(logonAttributes.getUserId());
					} else {
						if (lastUpdateUser != null
								&& (!BLANK.equals(lastUpdateUser[index]))) {
							if (updVos.size() >= index + 1) {
								UPUCalendarVO upuVo = updVos.get(index);
								upuCalVO.setLastUpdateUser(upuVo
										.getLastUpdateUser());
							}
						} else {
							ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
							LogonAttributes logonAttributes = applicationSessionImpl
									.getLogonVO();
							upuCalVO.setLastUpdateUser(logonAttributes
									.getUserId());
						}
					}

					if ("I".equals(opFlag)) {
						upuCalVO.setLastUpdateTime(null);
					} else {
						if (lastUpdateTime != null
								&& (!BLANK.equals(lastUpdateTime[index]))) {
							// timeIndex = index -1;
							if (updVos.size() >= index + 1) {
								UPUCalendarVO upuVo = updVos.get(index);
								upuCalVO.setLastUpdateTime(upuVo
										.getLastUpdateTime());
							}
						} else {
							upuCalVO.setLastUpdateTime(null);
						}
					}
					upuCalendarVOs.add(upuCalVO);
					++index;
				}
			}
			log.log(Log.FINEST,
					"\n\n***Collection before Save in Command\n\n***-->",
					upuCalendarVOs);
			if (errors != null && errors.size() > 0) {
				log
						.log(
								Log.FINEST,
								"\n\n***Inside Errors,,setting upuCalendarVo back to session\n\n***-->",
								upuCalendarVOs);
				upuCalendarSession.setUPUCalendarVOs(upuCalendarVOs);
				invocationContext.addAllError(errors);
				invocationContext.target = ONSAVE_FAILURE;
				log.exiting(CLASS_NAME, "execute");
				return;

			} else {
				try {
					new MailTrackingMRADelegate()
							.saveUPUCalendarDetails(upuCalendarVOs);
					upuCalendarSession.setUPUCalendarVOs(null);
					upuCalendarForm.setClrHsCodeLst(BLANK);
					upuCalendarForm.setFromDateLst(BLANK);
					upuCalendarForm.setToDateLst(BLANK);
					upuCalendarForm
							.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					invocationContext.target = ONSAVE_SUCCESS;
				} catch (BusinessDelegateException e) {
					e.getMessage();
					handleDelegateException(e);
				}
			}

		}

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
				&& ((fromDate != null) && (fromDate.trim().length() > 0))) {

			return DateUtilities.isLessThan(fromDate, toDate,
					LocalDate.CALENDAR_DATE_FORMAT);

		} else {

			return true;
		}
	}

}
