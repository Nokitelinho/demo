/*
 * ShowTotalDemmurageCommand.java Created on Jul 24, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3278
 * As a part of CR QF1015 
 * To show the total demmurage amount
 * 
 */
public class ShowTotalDemmurageCommand extends BaseCommand {

	/**
	 * Logger for LoanBorrowDetailsEnquiry
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of LoanBorrowDetailsEnquiry screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	/**
	 * target String if success
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		transactionFilterVO = makeFilter(listULDTransactionForm,
				listULDTransactionSession, logonAttributes);
		//double totDemmurageAmt = 0.0;
		ULDTransactionDetailsVO uLDTransactionDetailsVO = null;
		try {
			uLDTransactionDetailsVO = uldDefaultsDelegate
					.findTotalDemmurage(transactionFilterVO);

			log.log(Log.FINE, "totDemmurageAmt ~~~~~~~~~~~~~~~~~",
					uLDTransactionDetailsVO.getTotalDemmurage());
			log.log(Log.FINE, "baseCurrency ~~~~~~~~~~~~~~~~~",
					uLDTransactionDetailsVO.getBaseCurrency());

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		listULDTransactionSession.setTotalDemmurage(uLDTransactionDetailsVO.getTotalDemmurage());
		listULDTransactionSession.setBaseCurrency(uLDTransactionDetailsVO.getBaseCurrency());
		invocationContext.target = SCREENLOAD_SUCCESS;

		log.exiting("ShowTotalDemmurageCommand", "execute");

	}

	/**
	 * 
	 * @param listULDTransactionForm
	 * @param listULDTransactionSession
	 * @param logonAttributes
	 * @return
	 */
	private TransactionFilterVO makeFilter(
			ListULDTransactionForm listULDTransactionForm,
			ListULDTransactionSession listULDTransactionSession,
			LogonAttributes logonAttributes) {
		log.entering("ShowTotalDemmurageCommand", "makeFilter");

		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();

		LocalDate txntodt = null;
		LocalDate txnfromdt = null;
		LocalDate retodt = null;
		LocalDate refromdt = null;
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation())
				&& listULDTransactionForm.getTxnStation() != null) {
			txntodt = new LocalDate(listULDTransactionForm.getTxnStation()
					.toUpperCase(), Location.ARP, true);
			txnfromdt = new LocalDate(listULDTransactionForm.getTxnStation()
					.toUpperCase(), Location.ARP, true);
		} else {
			txntodt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
			txnfromdt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation())
				&& listULDTransactionForm.getReturnStation() != null) {
			retodt = new LocalDate(listULDTransactionForm.getReturnStation()
					.toUpperCase(), Location.ARP, true);
			refromdt = new LocalDate(listULDTransactionForm.getReturnStation()
					.toUpperCase(), Location.ARP, true);
		} else {
			retodt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
			refromdt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
		}

		transactionFilterVO.setCompanyCode(listULDTransactionSession
				.getCompanyCode());
		if (!("").equalsIgnoreCase(listULDTransactionForm.getUldNum())
				&& listULDTransactionForm.getUldNum() != null) {
			transactionFilterVO.setUldNumber(listULDTransactionForm.getUldNum().trim()
					.toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getUldTypeCode())
				&& listULDTransactionForm.getUldTypeCode() != null) {
			transactionFilterVO.setUldTypeCode(listULDTransactionForm
					.getUldTypeCode().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getAccessoryCode())
				&& listULDTransactionForm.getAccessoryCode() != null) {
			transactionFilterVO.setAccessoryCode(listULDTransactionForm
					.getAccessoryCode().trim());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnType())
				&& listULDTransactionForm.getTxnType() != null) {
			transactionFilterVO.setTransactionType(listULDTransactionForm
					.getTxnType().trim());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnStatus())
				&& listULDTransactionForm.getTxnStatus() != null) {
			transactionFilterVO.setTransactionStatus(listULDTransactionForm
					.getTxnStatus().trim());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getPartyType())
				&& listULDTransactionForm.getPartyType() != null) {
			transactionFilterVO.setPartyType(listULDTransactionForm
					.getPartyType().trim());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getFromPartyCode())
				&& listULDTransactionForm.getFromPartyCode() != null) {
			transactionFilterVO.setFromPartyCode(listULDTransactionForm
					.getFromPartyCode().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getToPartyCode())
				&& listULDTransactionForm.getToPartyCode() != null) {
			transactionFilterVO.setToPartyCode(listULDTransactionForm
					.getToPartyCode().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation())
				&& listULDTransactionForm.getTxnStation() != null) {
			transactionFilterVO
					.setTransactionStationCode(listULDTransactionForm
							.getTxnStation().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation())
				&& listULDTransactionForm.getReturnStation() != null) {
			transactionFilterVO.setReturnedStationCode(listULDTransactionForm
					.getReturnStation().trim().toUpperCase());
		}

		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnFromDate())
				&& listULDTransactionForm.getTxnFromDate() != null) {

			String txnFrmDate = listULDTransactionForm.getTxnFromDate();
			StringBuilder txnFrmDateAndTime = new StringBuilder(txnFrmDate);
			String txnFrmTime = listULDTransactionForm.getTxnFrmTime();
			if (!txnFrmTime.contains(":")) {
				txnFrmTime = txnFrmTime.concat(":00");
			}
			transactionFilterVO.setStrTxnFrmTime(txnFrmTime);
			txnFrmDateAndTime.append(" ").append(txnFrmTime).append(":00");

			transactionFilterVO.setTxnFromDate(txntodt
					.setDateAndTime(txnFrmDateAndTime.toString()));

			transactionFilterVO.setStrTxnFromDate(listULDTransactionForm
					.getTxnFromDate());

		}

		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnToDate())
				&& listULDTransactionForm.getTxnToDate() != null) {

			String txnToDate = listULDTransactionForm.getTxnToDate();
			StringBuilder txnToDateAndTime = new StringBuilder(txnToDate);
			String txnToTime = listULDTransactionForm.getTxnToTime();
			if (!txnToTime.contains(":")) {
				txnToTime = txnToTime.concat(":00");
			}
			transactionFilterVO.setStrTxnToTime(txnToTime);

			txnToDateAndTime.append(" ").append(txnToTime).append(":00");

			transactionFilterVO.setTxnToDate(txnfromdt
					.setDateAndTime(txnToDateAndTime.toString()));
			transactionFilterVO.setStrTxnToDate(listULDTransactionForm
					.getTxnToDate());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnFromDate())
				&& listULDTransactionForm.getReturnFromDate() != null) {

			String returnFrmDate = listULDTransactionForm.getReturnFromDate();
			StringBuilder returnFrmDateAndTime = new StringBuilder(
					returnFrmDate);
			String returnFrmTime = listULDTransactionForm.getReturnFrmTime();
			if (!returnFrmTime.contains(":")) {
				returnFrmTime = returnFrmTime.concat(":00");
			}
			transactionFilterVO.setStrRetFrmTime(returnFrmTime);
			returnFrmDateAndTime.append(" ").append(returnFrmTime)
					.append(":00");

			transactionFilterVO.setReturnFromDate(retodt
					.setDateAndTime(returnFrmDateAndTime.toString()));
			transactionFilterVO.setStrReturnFromDate(listULDTransactionForm
					.getReturnFromDate());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnToDate())
				&& listULDTransactionForm.getReturnToDate() != null) {

			String returnToDate = listULDTransactionForm.getReturnToDate();
			StringBuilder returnToDateAndTime = new StringBuilder(returnToDate);
			String returnToTime = listULDTransactionForm.getReturnToTime();
			if (!returnToTime.contains(":")) {
				returnToTime = returnToTime.concat(":00");
			}
			transactionFilterVO.setStrRetToTime(returnToTime);
			returnToDateAndTime.append(" ").append(returnToTime).append(":00");

			transactionFilterVO.setReturnToDate(refromdt
					.setDateAndTime(returnToDateAndTime.toString()));
			transactionFilterVO.setStrReturnToDate(listULDTransactionForm
					.getReturnToDate());
		}
		//added by a-3278 on 03Dec08  for including the CRN number also as the filter in totalDemmurage calculation
		if (!("").equalsIgnoreCase(listULDTransactionForm
				.getControlReceiptNoPrefix())
				&& listULDTransactionForm.getControlReceiptNoPrefix() != null) {
			transactionFilterVO
					.setPrefixControlReceiptNo(listULDTransactionForm
							.getControlReceiptNoPrefix());
		}

		if (!("").equalsIgnoreCase(listULDTransactionForm
				.getControlReceiptNoMid())
				&& listULDTransactionForm.getControlReceiptNoMid() != null) {
			transactionFilterVO.setMidControlReceiptNo(listULDTransactionForm
					.getControlReceiptNoMid());
		}

		if (!("")
				.equalsIgnoreCase(listULDTransactionForm.getControlReceiptNo())
				&& listULDTransactionForm.getControlReceiptNo() != null) {
			transactionFilterVO.setControlReceiptNo(listULDTransactionForm
					.getControlReceiptNo());
		}
		//a-3278 on 03Dec08 ends
		log.exiting("ShowTotalDemmurageCommand", "makeFilter");
		return transactionFilterVO;
	}

}
