/*
 * ValidateCommand.java Created on Dec 19, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2398
 * 
 */
public class ValidateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking SaveCommand");

	private static final String CLASS_NAME = "ValidateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String VLD_SUCCESS = "validate_success";

	private static final String VLD_FAILURE = "validate_failure";

	private static final String DATE_VALUE_NULL = "mailtracking.mra.defaults.maintainbillingmatrix.err.datevaluenull";

	private static final String DATE_VALUES_INVALID = "mailtracking.mra.defaults.maintainbillingmatrix.err.invaliddatevalues";

	private static final String FROMDATE_DATE_BEFORE_CUREENTDATE = "mailtracking.mra.defaults.fromdatebeforecurrentdate";
	private static final String DATE_LESSTHAN_CURRENTDATE = "DateLesser";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Collection<ErrorVO> errors = null;
		errors = validateForm(companyCode, form);
		Page<BillingLineVO> billingLineDetails = session
				.getBillingLineDetails();
		if (session.getBillingMatrixVO() != null) {
			if (form.getValidFrom() != null
					&& form.getValidFrom().trim().length() > 0) {
				LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				fromDate.setDate(form.getValidFrom().trim());
				session.getBillingMatrixVO().setValidityStartDate(fromDate);
			}
			if (form.getValidTo() != null
					&& form.getValidTo().trim().length() > 0) {
				LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				toDate.setDate(form.getValidTo().trim());
				session.getBillingMatrixVO().setValidityEndDate(toDate);
			}
			if(form.getDescription()!=null && form.getDescription().trim().length()>0){
				log.log(Log.INFO, "description ", form.getDescription());
				session.getBillingMatrixVO().setDescription(form.getDescription());
			}
		}
		if (errors.size() == 0) {
			log.exiting(CLASS_NAME, "execute");
			form.setValidateStatus("Y");
			invocationContext.addAllError(errors);
			invocationContext.target = VLD_SUCCESS;
		} else {
			log.exiting(CLASS_NAME, "execute");
			form.setValidateStatus("N");
			invocationContext.addAllError(errors);
			invocationContext.target = VLD_FAILURE;
		}
		session.setBillingLineDetails(billingLineDetails);
		form
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	}

	/**
	 * Function to validate the form entries
	 * 
	 * @param companyCode
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(String companyCode,
			BillingMatrixForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering(CLASS_NAME, "validateForm");
		if (form.getValidFrom() == null
				|| form.getValidFrom().trim().length() == 0
				|| form.getValidTo() == null
				|| form.getValidTo().trim().length() == 0) {
			ErrorVO error = new ErrorVO(DATE_VALUE_NULL);
			errors.add(error);
		} else {
			if (form.getValidFrom() != null
					&& form.getValidFrom().trim().length() > 0
					&& "I".equals(form.getOperationFlag())) {
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				LocalDate startDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				startDate.setDate(form.getValidFrom());
				if (startDate.before(currentDate)) {
					log.log(Log.INFO, "startDate... ", startDate);
					form.setFromDateFlag(DATE_LESSTHAN_CURRENTDATE);
				}
			} else {
				LocalDate startDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				startDate.setDate(form.getValidFrom());
				LocalDate endDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				endDate.setDate(form.getValidTo());
				if (endDate.before(startDate)) {
					ErrorVO error = new ErrorVO(DATE_VALUES_INVALID);
					errors.add(error);
					log.log(Log.INFO, "Error", error.toString());
				}
			}
		}
		log.exiting(CLASS_NAME, "validateForm");
		return errors;
	}
}
