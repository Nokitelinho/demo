/*
 * ListArlAuditCommand.java Created on Aug 16, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.audit;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.MRAArlAuditFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.MRAAirlineAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 * 
 */
public class ListArlAuditCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ListAirlineAuditCommand");

	private static final String SCREENID_AUD = "shared.audit.auditenquiry";

	private static final String MODULE_NAME_AUD = "shared.audit";

	private static final String SUCCESS = "list_success";

	private static final String FAILURE = "list_failure";

	/**
	 * This method overrides the executre method of BaseComand class
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListArlAuditCommand", "execute");

		MRAAirlineAuditForm airlineAuditForm = (MRAAirlineAuditForm) invocationContext.screenModel;
		AuditEnquirySession auditEnquirySession = getScreenSession(
				MODULE_NAME_AUD, SCREENID_AUD);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		/**
		 * Validate Mandatory fields
		 */
		errors = validateForm(airlineAuditForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		MRAArlAuditFilterVO airlineAuditFilterVO = new MRAArlAuditFilterVO();
		airlineAuditFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (airlineAuditForm.getAirlineCode() != null
				&& !("").equals(airlineAuditForm.getAirlineCode())) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), airlineAuditForm
								.getAirlineCode().toUpperCase());
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
		}

		if (airlineValidationVO != null
				&& airlineAuditForm.getAirlineCode() != null
				&& !("").equals(airlineAuditForm.getAirlineCode())) {
			airlineAuditFilterVO.setAirlineIdentifier(airlineValidationVO
					.getAirlineIdentifier());

		}

		if (airlineAuditForm.getAirlineCode() != null
				&& !("").equals(airlineAuditForm.getAirlineCode())) {
			airlineAuditFilterVO.setAirlineCode(airlineAuditForm
					.getAirlineCode().toUpperCase());
		}
		if (airlineAuditForm.getClearancePeriod() != null
				&& !("").equals(airlineAuditForm.getClearancePeriod())) {
			airlineAuditFilterVO.setClearancePeriod(airlineAuditForm
					.getClearancePeriod().toUpperCase());
		}
		if (airlineAuditForm.getTxnFromDate() != null
				&& !("").equals(airlineAuditForm.getTxnFromDate())) {
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			airlineAuditFilterVO.setTxnFromDate(fromDate
					.setDate(airlineAuditForm.getTxnFromDate()));
		}
		if (airlineAuditForm.getTxnToDate() != null
				&& !("").equals(airlineAuditForm.getTxnToDate())) {
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			airlineAuditFilterVO.setTxnToDate(toDate.setDate(airlineAuditForm
					.getTxnToDate()));
		}

		Collection<AuditDetailsVO> auditDetailsVOs = new ArrayList<AuditDetailsVO>();

		try {
			MailTrackingMRADelegate del = new MailTrackingMRADelegate();
			auditDetailsVOs = del.findArlAuditDetails(airlineAuditFilterVO);
			log.log(Log.FINE, "auditDetailsVOs DSN:", auditDetailsVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}

		if (auditDetailsVOs == null || auditDetailsVOs.size() == 0) {
			auditDetailsVOs = new ArrayList<AuditDetailsVO>();
			invocationContext.addError(new ErrorVO(
					"shared.audit.auditEnquiry.msg.err.noflightDetails"));
		}

		auditEnquirySession.setAuditDetailsVOs(auditDetailsVOs);
		invocationContext.target = SUCCESS;
		log.exiting("ListAirlineAuditCommand", "execute");
	}

	/**
	 * Used to validate for mandatory checks
	 * 
	 * @param airlineAuditForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MRAAirlineAuditForm airlineAuditForm) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		log.log(Log.FINE, "Dates  ---> from date", airlineAuditForm.getTxnFromDate());
		log.log(Log.FINE, "Dates >>>>>>> to date", airlineAuditForm.getTxnToDate());
		if ((airlineAuditForm.getTxnFromDate() != null && airlineAuditForm
				.getTxnFromDate().trim().length() > 0)
				&& (airlineAuditForm.getTxnToDate() != null && airlineAuditForm
						.getTxnToDate().trim().length() > 0)) {

			LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			LocalDate frmDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			frmDate.setDate(airlineAuditForm.getTxnFromDate());
			toDate.setDate(airlineAuditForm.getTxnToDate());
			if (toDate.isLesserThan(frmDate)) {
				ErrorVO error = new ErrorVO(
						"shared.audit.auditenquiry.msg.err.todatelessthanfrmdate");
				errors.add(error);
			}
		} else {
			if (airlineAuditForm.getTxnFromDate() == null
					|| airlineAuditForm.getTxnFromDate().trim().length() == 0) {

				ErrorVO error = new ErrorVO(
						"shared.audit.auditenquiry.msg.err.txnfrmdateerror");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);

			}
			if (airlineAuditForm.getTxnToDate() == null
					|| airlineAuditForm.getTxnToDate().trim().length() == 0) {
				ErrorVO error = new ErrorVO(
						"shared.audit.auditenquiry.msg.err.txntodateerror");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}

		return errors;

	}
}
