/*
 * NavigateScreenCommand.java created on Aug 08, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformone;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.accounting.vo.AccountingFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.cra.accounting.ListAccountingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormOneSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-3447
 *
 */

/**
 * This Class is used to Navigate from CaptureFormOne Screen to CaptureFormThree
 * CaptureInvoice Screens,List Accounting Entries and Rejection Memo Screen
 */

/*
 * Revision History Revision Date Author Description
 * ==============================================================================
 * 0.1 13-Aug-08 A-3447 For Coding
 *
 * =============================================================================
 */

public class NavigateScreenCommand extends BaseCommand {
	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("MRA airlinebilling formone");

	/**
	 * command Name
	 */
	private static final String CLASS_NAME = "CaptureInvCommand";

	/**
	 * Success path
	 */
	private static final String CAPTURE_INVOICE_SUCCESS = "capture_inv_success";

	/**
	 * parent Module Name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * parent Screen id
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformone";

	/**
	 * Module name for capture invoice Screen
	 */

	private static final String INV_MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Screen Id for capture invoice Screen
	 */
	private static final String INV_SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * Form 3 Module Name
	 */

	private static final String FORM_THREE_MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Form 3 Screen ID
	 */
	private static final String FORM_THREE_SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformthree";

	/**
	 * Success path
	 */
	private static final String CAPTURE_FORM_THREE = "capture_form_three_success";

	/**
	 * Strings for SCREEN_ID ListAccountingEntries
	 */
	private static final String LISTACCOUNTING_SCREENID = "cra.accounting.listaccountingentries";

	/**
	 * Strings for MODULE NAME ListAccountingEntries
	 */

	private static final String LISTACCOUNTING_MODULENAME = "cra.accounting";

	/**
	 * For List Accounting Entries
	 */

	private static final String LIST_ACC_ENTRIES = "list_acc_success";

	/**
	 * other Reason
	 */
	private static final String TICKED = "Y";

	/**
	 * Reason for Rejection
	 */

	private static final String REASON = "Invoice Rejected due to the Difference in Form-1 and Form3";

	/**
	 * module name for rejection memo Screen
	 *
	 */
	private static final String REJECTION_MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * screen id for Rejection memo Screen
	 *
	 */
	private static final String REJECTION_SCREENID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";

	/**
	 *
	 * Success path of Rejection memo Screen
	 */
	private static final String REJECTION_SUCCESS = "reject_success";

	/**
	 * Failure Path
	 */
	private static final String REJECTION_FALIED = "reject_falied";
	/**
	 * Failure Path
	 */
	private static final String NAVIGATION_FAILED = "reject_falied";

	/**
	 * Error messages
	 */
	private static final String INVOICE_CANNOT_REJECTED = "mra.airlinebilling.inward.captureform1.invoice.rejection.failed";
	/**
	 * Error messages
	 */
	private static final String CLRPRD_MANDATORY = "mailtracking.mra.airlinebilling.inward.captureformone.clearanceprdmandatory";

	/**
	 * Zero
	 */
	private static final double ZERO=0.0;

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * @author A-3447
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureMailFormOneForm captureFormOneForm = (CaptureMailFormOneForm) invocationContext.screenModel;

		CaptureFormOneSession captureFormOneSession = getScreenSession(
				MODULE_NAME, SCREENID);
		String counter = captureFormOneForm.getSelectedRow();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<InvoiceInFormOneVO> invoiceInFormOneVOs = captureFormOneSession
				.getFormOneInvVOs();
		FormOneVO formOneVOFilter = null;
		if (captureFormOneForm.getClearancePeriod() != null) {
			formOneVOFilter = new FormOneVO();
			formOneVOFilter.setClearancePeriod(captureFormOneForm
					.getClearancePeriod());
			formOneVOFilter.setCompanyCode(getApplicationSession().getLogonVO()
					.getCompanyCode());

		}
		if (captureFormOneSession.getFormOneVO() != null) {

			formOneVOFilter = captureFormOneSession.getFormOneVO();
		} else {

			ErrorVO error = new ErrorVO(CLRPRD_MANDATORY);
			errors.add(error);
			error.setErrorDisplayType(ERROR);
			invocationContext.addAllError(errors);

			invocationContext.target = NAVIGATION_FAILED;

		}
		ArrayList<InvoiceInFormOneVO> invoiceInFormOneArrayList = new ArrayList<InvoiceInFormOneVO>(
				invoiceInFormOneVOs);

		InvoiceInFormOneVO invoiceInFormOneVO = null;
		if (!("formthree").equals(captureFormOneForm.getButtonFlag())) {
			log.log(Log.FINE, "-counter  ", counter);
			invoiceInFormOneVO = invoiceInFormOneArrayList.get(Integer
					.parseInt(counter));
			log.log(Log.FINE, "Vo obatained", invoiceInFormOneVO);
		}
		CaptureInvoiceSession captureInvoiceSession = (CaptureInvoiceSession) getScreenSession(
				INV_MODULE_NAME, INV_SCREEN_ID);
		CaptureFormThreeSession captureFormThreeSession = (CaptureFormThreeSession) getScreenSession(
				FORM_THREE_MODULE_NAME, FORM_THREE_SCREEN_ID);
		ListAccountingEntriesSession accountingEntrySession = getScreenSession(
				LISTACCOUNTING_MODULENAME, LISTACCOUNTING_SCREENID);
		RejectionMemoSession rejectionMemoSession = (RejectionMemoSession) getScreenSession(
				REJECTION_MODULE_NAME, REJECTION_SCREENID);

		if ("captureinvoicesummary".equals(captureFormOneForm.getButtonFlag())) {
			AirlineCN51FilterVO airlineCN51FilterVO = populateAirlineCN51FilterVO(invoiceInFormOneVO);
			captureInvoiceSession.setFilterVo(airlineCN51FilterVO);
			invocationContext.target = CAPTURE_INVOICE_SUCCESS;
		}

		else if (("formthree").equals(captureFormOneForm.getButtonFlag())) {

			InterlineFilterVO interlineFilterVO = populateInterlineFilterVO(formOneVOFilter);
			captureFormThreeSession.setInterlineFilterVO(interlineFilterVO);
			invocationContext.target = CAPTURE_FORM_THREE;
		}

		else if (("listaccountinentires").equals(captureFormOneForm
				.getButtonFlag()))

		{

			AccountingFilterVO accountingFilterVO = populateAccountingFilterVO(invoiceInFormOneVO);
			accountingEntrySession.setAccountingFilterVO(accountingFilterVO);
			FormOneVO formOneVO = new FormOneVO();
			formOneVO.setClearancePeriod(invoiceInFormOneVO
					.getClearancePeriod());
			formOneVO.setAirlineCode(invoiceInFormOneVO.getAirlineCode());
			formOneVO.setAirlineIdr(invoiceInFormOneVO.getAirlineIdentifier());
			//formOneVO.setInvStatus(invoiceInFormOneVO.getInvStatus());
			formOneVO.setCompanyCode(invoiceInFormOneVO.getCompanyCode());
			captureFormOneSession.setFormOneVO(formOneVO);
			invocationContext.target = LIST_ACC_ENTRIES;
		}

		else if (("rejectinvoice").equals(captureFormOneForm.getButtonFlag())) {
			// Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			errors = validateInvoiceStatus(invoiceInFormOneVO);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "errors", errors.size());
				invocationContext.addAllError(errors);

				invocationContext.target = REJECTION_FALIED;
			} else {
				ApplicationSessionImpl applicationSession = getApplicationSession();
				LogonAttributes logonAttributes = applicationSession
						.getLogonVO();
				FormOneVO formOneVO = captureFormOneSession.getFormOneVO();
				RejectionMemoVO rejectionMemoVO = new RejectionMemoVO();
				rejectionMemoVO.setOtherIndicator(TICKED);
				rejectionMemoVO.setRemarks(REASON);
				rejectionMemoVO.setInwardInvoiceNumber(invoiceInFormOneVO
						.getInvoiceNumber());
				rejectionMemoVO.setInwardInvoiceDate(invoiceInFormOneVO
						.getInvoiceDate());
				rejectionMemoVO.setCompanycode(invoiceInFormOneVO
						.getCompanyCode());
				rejectionMemoVO.setAirlineCode(invoiceInFormOneVO
						.getAirlineCode());
				rejectionMemoVO.setAirlineIdentifier(invoiceInFormOneVO
						.getAirlineIdentifier());
				rejectionMemoVO.setClearanceperiod(invoiceInFormOneVO
						.getClearancePeriod());
				rejectionMemoVO.setClassType(invoiceInFormOneVO.getClassType());
				rejectionMemoVO.setInterlinebillingtype(invoiceInFormOneVO
						.getIntBlgTyp());
				rejectionMemoVO.setLastUpdatedUser(logonAttributes.getUserId());
				rejectionMemoVO.setBillingCurrencyCode(invoiceInFormOneVO
						.getLstCurCode());
				rejectionMemoVO.setBillingBilledAmount(invoiceInFormOneVO
						.getTotMisAmt());
				//rejectionMemoVO.
				rejectionMemoVO.setContractAcceptedAmount(ZERO);
				rejectionMemoVO.setContractRejectedAmount(invoiceInFormOneVO.getBillingTotalAmt().getAmount());
				rejectionMemoSession.setRejectionMemoVO(rejectionMemoVO);
				log.log(Log.FINE, "session-->", rejectionMemoSession.getRejectionMemoVO());
				invocationContext.target = REJECTION_SUCCESS;
			}
		}

	}

	/**
	 * @author A-3447
	 * @param invoiceInFormOneVO
	 * @return
	 */
	private Collection<ErrorVO> validateInvoiceStatus(
			InvoiceInFormOneVO invoiceInFormOneVO) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String invStatus = invoiceInFormOneVO.getInvStatus();
		if (invStatus != null) {
			if (("R").equals(invStatus) || ("B").equals(invStatus)
					|| ("P").equals(invStatus)) {
				ErrorVO error = new ErrorVO(INVOICE_CANNOT_REJECTED);
				errors.add(error);
				error.setErrorDisplayType(ERROR);
			}
		}
		return errors;
	}

	/**
	 * @author A-3447
	 * @param cn51SummaryVO
	 * @return
	 */
	private AirlineCN51FilterVO populateAirlineCN51FilterVO(
			InvoiceInFormOneVO invoiceInFormOneVO) {

		AirlineCN51FilterVO airlineCN51FilterVO = new AirlineCN51FilterVO();

		airlineCN51FilterVO.setCompanyCode(invoiceInFormOneVO.getCompanyCode());
		airlineCN51FilterVO.setIataClearancePeriod(invoiceInFormOneVO
				.getClearancePeriod());
		airlineCN51FilterVO.setAirlineIdentifier(invoiceInFormOneVO
				.getAirlineIdentifier());
		airlineCN51FilterVO.setInvoiceReferenceNumber(invoiceInFormOneVO
				.getInvoiceNumber());
		airlineCN51FilterVO.setInvDate(invoiceInFormOneVO.getInvoiceDate());
		airlineCN51FilterVO.setAirlineCode(invoiceInFormOneVO.getAirlineCode());
		log.log(Log.FINE, "Vo obatained--->>", airlineCN51FilterVO);
		return airlineCN51FilterVO;
	}

	/**
	 * @author A-3447
	 * @param formOneVOFilter
	 * @return
	 */

	private InterlineFilterVO populateInterlineFilterVO(
			FormOneVO formOneVOFilter) {

		InterlineFilterVO interlineFilterVO = new InterlineFilterVO();
		interlineFilterVO.setCompanyCode(formOneVOFilter.getCompanyCode());
		interlineFilterVO.setClearancePeriod(formOneVOFilter
				.getClearancePeriod());
		return interlineFilterVO;
	}

	/**
	 * @author A-3447
	 * @param invoiceInFormOneVO
	 * @return
	 */
	private AccountingFilterVO populateAccountingFilterVO(
			InvoiceInFormOneVO invoiceInFormOneVO) {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AccountingFilterVO accountingFilterVO = new AccountingFilterVO();
		accountingFilterVO.setCompanyCode(invoiceInFormOneVO.getCompanyCode());
		accountingFilterVO.setClearancePeriod(invoiceInFormOneVO
				.getClearancePeriod());
		accountingFilterVO.setInvoiceNumber(invoiceInFormOneVO
				.getInvoiceNumber());
		accountingFilterVO.setAirlineIdentifier(logonAttributes
				.getOwnAirlineIdentifier());
		accountingFilterVO
				.setFunctionPoint(InvoiceInFormOneVO.FUNCTIONPOINT_INWARDBILLING);
		accountingFilterVO.setSubSystem("M");
		return accountingFilterVO;
	}

}
