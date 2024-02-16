/*
 * OnSaveCommand.java Created on Jul 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Sandeep. T
 * Save Command class to save the details
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Jul 02, 2007 Sandeep.T Initial draft
 */
public class OnSaveCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */

	private Log log = LogFactory
			.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");

	private static final String CLASS_NAME = "OnSaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";

	private static final String NO_OPERATION = "NOOP";

	private static final String SAVE_FAIL = "save_failure";

	private static final String SAVE_SUCCESS = "save_success";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureRejectionMemoSession captureRejectionMemoSession = (CaptureRejectionMemoSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);

		CaptureRejectionMemoForm captureRejectionMemoForm = (CaptureRejectionMemoForm) invocationContext.screenModel;

		Collection<ErrorVO> errors = null;

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		ArrayList<MemoInInvoiceVO> newMemoInInvoiceVos = updateDetails(
				captureRejectionMemoForm, companyCode);

		// For checking whether free rows or columns exists in the table
		errors = validateTable(captureRejectionMemoForm,
				captureRejectionMemoSession);
		// for debugging only
		// for(ErrorVO e:errors){
		// log.log(Log.FINE,"/n/n/n Errors "+e.getErrorCode());
		// }
		// //
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "/n/n/n inside blank check == failure");
			captureRejectionMemoSession
					.setMemoInInvoiceVOs(newMemoInInvoiceVos);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAIL;
			return;
		}

		// For checking whether duplicate invoice no and duplicate clearance
		// period exists in the table
		errors = checkDuplicate(captureRejectionMemoForm,
				captureRejectionMemoSession);

		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE,
					"/n/n/n inside duplicate clr prd / inv num  == failure");
			captureRejectionMemoSession
					.setMemoInInvoiceVOs(newMemoInInvoiceVos);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAIL;
			return;
		}

		// For validating the clearance period exists in the table
		errors = validateClearancePeriods(captureRejectionMemoForm,
				captureRejectionMemoSession, companyCode);
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "/n/n/n inside invalid clrprd");
			captureRejectionMemoSession
					.setMemoInInvoiceVOs(newMemoInInvoiceVos);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAIL;
			return;
		}
		// /
		if (errors == null || errors.size() == 0) {
			log.log(Log.FINE, "\n\n\n Errors are NULL>>>>>>>");
			try {
				new MailTrackingMRADelegate().saveMemo(newMemoInInvoiceVos);
				captureRejectionMemoSession.removeMemoInInvoiceVOs();
				captureRejectionMemoSession.removeMemoFilterVO();
				captureRejectionMemoForm.setAirlineCode("");
				captureRejectionMemoForm.setInvoiceNo("");
				captureRejectionMemoForm.setClearancePeriod("");
				captureRejectionMemoForm.setInterlineBillingType("");
				captureRejectionMemoForm.setSelectAll(false);
				captureRejectionMemoForm.setLinkStatusFlag("disable");
				captureRejectionMemoForm.setStatusFlag("screenload");
				captureRejectionMemoForm
						.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				ErrorVO error = new ErrorVO(
						"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.info.datasavedsuccessfully");
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				log.log(Log.FINE, "Errors", errors.size());
				invocationContext.target = SAVE_SUCCESS;
			} catch (BusinessDelegateException e) {
				e.getMessage();
				errors = handleDelegateException(e);
			}
		}

	}

	// Method for updating the session
	/**
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 * @param companyCode
	 */
	public ArrayList<MemoInInvoiceVO> updateDetails(
			CaptureRejectionMemoForm captureRejectionMemoForm,
			String companyCode) {
		log.entering("updateDetails", "execute");

		CaptureRejectionMemoSession captureRejectionMemoSession = (CaptureRejectionMemoSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);

		MemoFilterVO memoFilterVO = captureRejectionMemoSession
				.getMemoFilterVO();

		ArrayList<MemoInInvoiceVO> updatedMemoInInvoiceVOs = new ArrayList<MemoInInvoiceVO>();

		/*
		 * ArrayList
		 */
		ArrayList<MemoInInvoiceVO> updVos = (ArrayList<MemoInInvoiceVO>) captureRejectionMemoSession
				.getMemoInInvoiceVOs();

		String[] rejectionMemoNo = captureRejectionMemoForm
				.getRejectionMemoNo();

		String[] invoiceNo = captureRejectionMemoForm.getInvoiceNos();

		String[] clearancePeriod = captureRejectionMemoForm
				.getClearancePeriods();

		String[] rejectionDate = captureRejectionMemoForm.getRejectionDate();

		String[] operationFlags = captureRejectionMemoForm.getOperationalFlag();

		// log.log(Log.FINE,"\nrejectionMemoNo size"+rejectionMemoNo.length);

		double[] provisionalAmt = captureRejectionMemoForm
				.getProvisionalAmount();

		double[] reportedAmt = captureRejectionMemoForm.getReportedAmount();

		double[] rejectedAmt = captureRejectionMemoForm.getRejectedAmount();

		double[] previousDifferenceAmount = captureRejectionMemoForm
				.getPreviousDifferenceAmount();

		// String[] operationalFlag =
		// captureRejectionMemoForm.getOperationalFlag();

		String[] remarks = captureRejectionMemoForm.getRemarks();

		String airlineCode = captureRejectionMemoForm.getAirlineCode();

		String billingType = memoFilterVO.getInterlineBillingType();

		// log.log(Log.FINE,"OperationFlags size"+operationalFlag);
		MemoInInvoiceVO memoInInvoiceVO = null;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		// for(MemoInInvoiceVO
		// memoInInvoiceVO:captureRejectionMemoSession.getMemoInInvoiceVOs()){

		if (operationFlags != null) {
			int index = 0;
			for (String opFlag : operationFlags) {

				log.log(Log.FINE, "\nOperationflag ", opFlag);
				log.log(Log.FINE, "\nindex ", index);
				if (!"NOOP".equals(opFlag)) {

					memoInInvoiceVO = new MemoInInvoiceVO();
					if (OPERATION_FLAG_INSERT.equals(opFlag)
							|| OPERATION_FLAG_DELETE.equals(opFlag)) {
						memoInInvoiceVO.setOperationalFlag(opFlag);
					} else if (!"NOOP".equals(opFlag)) {
						memoInInvoiceVO
								.setOperationalFlag(OPERATION_FLAG_UPDATE);
					}

					memoInInvoiceVO.setCompanyCode(companyCode);
					memoInInvoiceVO.setLastUpdatedUser(logonAttributes.getUserId());
					memoInInvoiceVO.setAirlineCode(airlineCode);
					memoInInvoiceVO.setAirlineIdentifier(memoFilterVO
							.getAirlineIdentifier());
					memoInInvoiceVO.setInterlineBlgType(billingType);

					// log.log(Log.FINE,"\nindex...here "+index);
					// log.log(Log.FINE,"\nrejectionMemoNo[index]
					// "+rejectionMemoNo[index]);
					if (!("").equals(rejectionMemoNo[index])) {
						memoInInvoiceVO.setMemoCode(rejectionMemoNo[index]);
					} else {
						memoInInvoiceVO.setMemoCode("");
					}

					if (!("").equals(invoiceNo[index])) {
						memoInInvoiceVO.setInvoiceNumber(invoiceNo[index]);
					} else {
						memoInInvoiceVO.setInvoiceNumber("");
					}

					if (!("").equals(clearancePeriod[index])) {
						memoInInvoiceVO
								.setClearancePeriod(clearancePeriod[index]);
					} else {
						memoInInvoiceVO.setClearancePeriod("");
					}

					if (!("").equals(rejectionDate[index])) {
						memoInInvoiceVO.setMemoDate(new LocalDate(NO_STATION,
								NONE, false).setDate(rejectionDate[index]));
					} else {
						memoInInvoiceVO.setMemoDate(null);
					}

					if (0.0 != provisionalAmt[index]) {
						memoInInvoiceVO
								.setProvisionalAmount(provisionalAmt[index]);
					} else {
						memoInInvoiceVO.setProvisionalAmount(0.0);
					}
					if (0.0 != reportedAmt[index]) {
						memoInInvoiceVO.setReportedAmount(reportedAmt[index]);
					} else {
						memoInInvoiceVO.setReportedAmount(0.0);
					}
					if (0.0 != rejectedAmt[index]) {
						memoInInvoiceVO.setDifferenceAmount(rejectedAmt[index]);
					} else {
						memoInInvoiceVO.setDifferenceAmount(0.0);
					}

					if (0.0 != previousDifferenceAmount[index]) {
						memoInInvoiceVO
								.setPreviousDifferenceAmount(previousDifferenceAmount[index]);
					} else {
						memoInInvoiceVO.setPreviousDifferenceAmount(0.0);
					}
					/*
					 * if(!("").equals(operationalFlag[index])){
					 * log.log(Log.FINE,"OperationFlag added in memoInInvoiceVO
					 * is"+operationalFlag[index]);
					 * memoInInvoiceVO.setOperationalFlag(operationalFlag[index]); }
					 * else { memoInInvoiceVO.setOperationalFlag(""); }
					 */
					if (!("").equals(remarks[index])) {
						log.log(Log.FINE, "Remarks", remarks, index);
						memoInInvoiceVO.setRemarks(remarks[index]);
					} else {
						memoInInvoiceVO.setRemarks("");
					}
					if (updVos != null && updVos.size() > 0) {
						memoInInvoiceVO.setLastUpdatedTime(updVos.get(index)
								.getLastUpdatedTime());
						memoInInvoiceVO.setLastUpdatedUser(updVos.get(index)
								.getLastUpdatedUser());
					}
					updatedMemoInInvoiceVOs.add(memoInInvoiceVO);
					// ++index;
				}
				++index;
			}
		}

		log.log(Log.FINE, "UPDATED MEMOININVOICEVOS in command/n/n/n",
				updatedMemoInInvoiceVOs);
		return updatedMemoInInvoiceVOs;

	}

	// For checking whether free rows or columns exists in the table
	/**
	 * @return Collection<ErrorVO>
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 */
	public Collection<ErrorVO> validateTable(
			CaptureRejectionMemoForm captureRejectionMemoForm,
			CaptureRejectionMemoSession captureRejectionMemoSession) {
		log.entering("validateTable", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		String[] invoiceNo = captureRejectionMemoForm.getInvoiceNos();

		String[] clearancePeriod = captureRejectionMemoForm
				.getClearancePeriods();

		double[] differenceAmt = captureRejectionMemoForm.getRejectedAmount();

		String[] operationFlag = captureRejectionMemoForm.getOperationalFlag();

		if (differenceAmt != null) {

			for (int i = 0; i < differenceAmt.length; i++) {
				if (!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
						&& !(NO_OPERATION).equals(operationFlag[i])) {
					if (("").equals(invoiceNo[i])) {
						log.log(Log.FINE, "invoice blank");
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.noinvoiceno");
						errorVO.setErrorDisplayType(ERROR);
						errors.add(errorVO);
						break;
					}
				}
			}

			for (int i = 0; i < differenceAmt.length; i++) {
				if (!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
						&& !(NO_OPERATION).equals(operationFlag[i])) {
					if (("").equals(clearancePeriod[i])) {
						log.log(Log.FINE, "clr period blank");
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.noclrprd");
						errorVO.setErrorDisplayType(ERROR);
						errors.add(errorVO);
						break;
					}
				}
			}

			for (int i = 0; i < differenceAmt.length; i++) {
				if (!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
						&& !(NO_OPERATION).equals(operationFlag[i])) {
					if (differenceAmt[i] == 0) {
						log.log(Log.FINE, "difference amt ==0.0");
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.freedifferenceamtexists");
						errorVO.setErrorDisplayType(ERROR);
						errors.add(errorVO);
						break;
					}
				}
			}
		}
		log.exiting("validateTable", "execute");

		return errors;

	}

	// For checking whether duplicate records exist
	/**
	 * @return Collection<ErrorVO>
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 */
	public Collection<ErrorVO> checkDuplicate(
			CaptureRejectionMemoForm captureRejectionMemoForm,
			CaptureRejectionMemoSession captureRejectionMemoSession) {
		log.entering("checkDuplicate", "execute");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if (captureRejectionMemoForm.getOperationalFlag() != null) {

			String[] invoiceNumber = captureRejectionMemoForm.getInvoiceNos();

			String[] clearancePeriod = captureRejectionMemoForm
					.getClearancePeriods();

			String[] operationFlag = captureRejectionMemoForm
					.getOperationalFlag();

			if (invoiceNumber != null) {

				int invoiceClrPrdFlag = 0;

				int clrPrdFlag = 0;

				for (int i = 0; i < invoiceNumber.length; i++) {
					for (int j = 0; j < invoiceNumber.length; j++) {
						log.log(Log.INFO, "Going to compare First invoice No",
								invoiceNumber, i);
						log.log(Log.INFO, " and Second Invoice No ",
								invoiceNumber, j);
						log.log(Log.FINE,
								"Going to Compare First Clearance Period",
								clearancePeriod, i);
						log.log(Log.INFO, " and Second Clearance Period ",
								clearancePeriod, j);
						if (i != j) {
							if (!(OPERATION_FLAG_DELETE)
									.equals(operationFlag[i])
									&& !(OPERATION_FLAG_DELETE)
											.equals(operationFlag[j])
									&& !(NO_OPERATION).equals(operationFlag[i])
									&& !(NO_OPERATION).equals(operationFlag[j])) {
								if (invoiceNumber[i].equals(invoiceNumber[j])) {
									if (clearancePeriod[i]
											.equals(clearancePeriod[j])) {
										invoiceClrPrdFlag = 1;
									}
								}
							}
						}
					}
				}

				/*
				 * for(int i=0;i<clearancePeriod.length;i++) { for(int j=0;j<clearancePeriod.length;j++) {
				 * log.log(Log.INFO,"Going to compare "+clearancePeriod[i]);
				 * log.log(Log.INFO," and "+clearancePeriod[j]); if(i!=j) {
				 * if(!(OPERATION_FLAG_DELETE).equals(operationFlag[i]) &&
				 * !(OPERATION_FLAG_DELETE).equals(operationFlag[j])) {
				 * if(clearancePeriod[i].equals(clearancePeriod[j])) {
				 * clrPrdFlag = 1; } } } } }
				 */

				if (invoiceClrPrdFlag == 1) {
					log.log(Log.INFO,
							"Dup invoiceNumber and Clearance Period >>>>>>",
							invoiceClrPrdFlag);
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.dupinvoicenumberclrprdexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
				}

				if (clrPrdFlag == 1) {
					log
							.log(Log.INFO, "dup Clearance Period >>>>>>",
									clrPrdFlag);
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.dupclearanceperiodexists");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
				}
			}
		}
		log.exiting("checkDuplicate", "execute");
		return errors;
	}

	// For validating the clearnce period exist
	/**
	 * @return Collection<ErrorVO>
	 * @param captureRejectionMemoForm
	 * @param captureRejectionMemoSession
	 * @param companyCode
	 */
	public Collection<ErrorVO> validateClearancePeriods(
			CaptureRejectionMemoForm captureRejectionMemoForm,
			CaptureRejectionMemoSession captureRejectionMemoSession,
			String companyCode) {

		log.entering("validateClearancePeriods", "execute");
		String[] clearancePeriod = captureRejectionMemoForm
				.getClearancePeriods();

		String[] operationFlag = captureRejectionMemoForm.getOperationalFlag();

		UPUCalendarVO upuCalendarVO = new UPUCalendarVO();

		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if (clearancePeriod != null) {
			try {
				for (int i = 0; i < clearancePeriod.length; i++) {
					if (!(OPERATION_FLAG_DELETE).equals(operationFlag[i])
							&& !(NO_OPERATION).equals(operationFlag[i])) {
						if (!("").equals(clearancePeriod[i])) {
							log.log(Log.FINE, "Clearance Period",
									clearancePeriod, i);
							upuCalendarVO = mailTrackingMRADelegate
									.validateIataClearancePeriod(companyCode,
											clearancePeriod[i]);

						}
					}
				}
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "inside updateAirlinecaught busDelegateExc");
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}

			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "Errors Size", errors.size());
			} else {
				log.log(Log.FINE, "UPUCalendarVO", upuCalendarVO);
			}
		}
		log.exiting("validateClearancePeriods", "execute");
		return errors;

	}

}
