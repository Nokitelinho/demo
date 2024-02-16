/* ReconcileSCMULDErrorCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmulderrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2046
 *
 */
public class ReconcileSCMULDErrorCommand extends BaseCommand {
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private Log log = LogFactory.getLogger("SCM_ULD_RECONCILE");

	private static final String SCREENID_MAINTAINULD = "uld.defaults.maintainuld";

	private static final String SCREENID_RECORDULD = "uld.defaults.misc.recorduldmovement";

	private static final String SCREENID_DISCREPANCY = "uld.defaults.listulddiscrepancies";

	private static final String SCREENID_MAINTAIN_DISCREPANCY = "uld.defaults.maintainulddiscrepancies";
	private static final String SCREEN_ID_LOANBORROWULD = "uld.defaults.loanborrowuld";

	private static final String SCREEN_ID_RETURN = "uld.defaults.loanborrowdetailsenquiry";

	private static final String MAINTAINULD_SUCCESS = "maintainULD_success";

	private static final String MAINTAINDISCREPANCY_SUCCESS = "maintaindiscrepancy_success";

	private static final String RECORDULD_SUCCESS = "recorduld_success";

	private static final String PAGE_URL = "fromScmUldReconcile";

	private static final String MISSINGULD_ERRORCODE = "ERR1";

	private static final String FOUNDULD_ERRORCODE = "ERR2";

	private static final String ULD_NOTIN_SYSTEM = "ERR3";

	private static final String TOBERETURNED = "ERR4";

	private static final String LOANTXN = "ERR5";

	private static final String RETURN_SUCCESS = "returntxn_success";

	private static final String BORROW_SUCCESS = "borrow_success";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();

		SCMULDErrorLogForm scmUldReconcileForm = (SCMULDErrorLogForm) invocationContext.screenModel;
		SCMULDErrorLogSession scmUldSession = (SCMULDErrorLogSession) getScreenSession(
				MODULE, SCREENID);
		Page<ULDSCMReconcileDetailsVO> reconcileDetailsVOs = scmUldSession
				.getSCMReconcileDetailVOs();
		log.log(Log.FINE, "SELECTED INDEX-------------->", scmUldReconcileForm.getRowIndex());
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDSCMReconcileDetailsVO scmreconcileDetailsVO = reconcileDetailsVOs
				.get(Integer.parseInt(scmUldReconcileForm.getRowIndex()));
		Collection<String> coll = new ArrayList<String>();
		coll.add(scmreconcileDetailsVO.getUldNumber());
		ULDDefaultsDelegate uldDelegate = new ULDDefaultsDelegate();
		if (FOUNDULD_ERRORCODE.equals(scmreconcileDetailsVO.getErrorCode())) {
			RecordUldMovementSession recordUldMovementSession = getScreenSession(
					MODULE, SCREENID_RECORDULD);

			SCMMessageFilterVO filterVO = new SCMMessageFilterVO();
			filterVO = populateMessageFilterVO(filterVO, scmUldSession);
			filterVO.setUldNumbers(coll);
			log.log(Log.FINE, "FilterVO to server----------------->", filterVO);
			String pol = "";
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				pol = uldDelegate.findCurrentAirportForULD(filterVO);
			} catch (BusinessDelegateException exception) {
				exception.getMessage();
				error = handleDelegateException(exception);
			}
			log.log(Log.FINE, "Station from server--------------->", pol);
			ULDMovementVO uldMovementVo = new ULDMovementVO();
			Collection<ULDMovementVO> uldMovementVos = new ArrayList<ULDMovementVO>();

			uldMovementVo.setPointOfLading(pol);
			uldMovementVo.setDummyMovement(true);
			uldMovementVo.setPointOfUnLading(scmreconcileDetailsVO
					.getAirportCode());
			uldMovementVos.add(uldMovementVo);
			recordUldMovementSession.setULDMovementVOs(uldMovementVos);
			recordUldMovementSession.setULDNumbers(coll);
			scmUldReconcileForm.setRowIndex("");
			scmUldReconcileForm.setReturnTxn("RECORDULDMOVT");
			recordUldMovementSession.setPageURL(PAGE_URL);
			invocationContext.target = RECORDULD_SUCCESS;
			return;
		} else if (ULD_NOTIN_SYSTEM
				.equals(scmreconcileDetailsVO.getErrorCode())) {
			MaintainULDSession maintainULDSession = (MaintainULDSession) getScreenSession(
					MODULE, SCREENID_MAINTAINULD);

			log
					.log(Log.FINE,
							"\n\n\n\n\n\n\n*********RECONCILE TO MAINTAIN ULD***************");

			String uldNumber = scmreconcileDetailsVO.getUldNumber();
			String airlineCode = uldNumber.substring(uldNumber.length()-3,uldNumber.length());

			if(Character.isDigit(airlineCode.charAt(0))){
				airlineCode=airlineCode.substring(1);
			}
			log.log(Log.FINE, "Airline--------------->", airlineCode);
			log.log(Log.FINE, "Airline Code from form-------------->",
					scmUldSession.
							getSCMULDFilterVO().getAirlineCode());
			if(scmUldSession.
					getSCMULDFilterVO().getAirlineCode().equalsIgnoreCase(airlineCode)){
			maintainULDSession.setPageURL(PAGE_URL);
			maintainULDSession.setSCMReconcileDetailsVO(scmreconcileDetailsVO);

			scmUldReconcileForm.setRowIndex("");
			invocationContext.target = MAINTAINULD_SUCCESS;
			return;
			}else{
				LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
						MODULE, SCREEN_ID_LOANBORROWULD);
				loanBorrowULDSession.setPageURL("fromScmReconcileBorrow");
				scmUldReconcileForm.setRowIndex("");
				loanBorrowULDSession
						.setULDSCMReconcileDetailsVO(scmreconcileDetailsVO);
				invocationContext.target = BORROW_SUCCESS;
				return;
			}
		} else if (MISSINGULD_ERRORCODE.equals(scmreconcileDetailsVO
				.getErrorCode())) {
			MaintainUldDiscrepancySession session = getScreenSession(MODULE,
					SCREENID_MAINTAIN_DISCREPANCY);
			session.setPageURL(PAGE_URL);
			session.setSCMULDReconcileDetailsVO(scmreconcileDetailsVO);
			invocationContext.target = MAINTAINDISCREPANCY_SUCCESS;
			return;
		} else if (TOBERETURNED.equals(scmreconcileDetailsVO.getErrorCode())) {
			ListULDTransactionSession listULDTransactionSession = getScreenSession(
					MODULE, SCREEN_ID_RETURN);
			listULDTransactionSession.setPageURL(PAGE_URL);
			TransactionListVO transactionListVO = new TransactionListVO();
			TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
			transactionFilterVO.setCompanyCode(compCode);
			transactionFilterVO.setTransactionType("L");
			transactionFilterVO.setUldNumber(scmreconcileDetailsVO
					.getUldNumber());
			transactionFilterVO.setTransactionStatus("T");
			transactionFilterVO.setPageNumber(1);
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				transactionListVO = uldDelegate
						.listULDTransactionDetails(transactionFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			ArrayList<ULDTransactionDetailsVO> transactionDetails = new ArrayList<ULDTransactionDetailsVO>(
					transactionListVO.getTransactionDetailsPage());
			transactionListVO.setUldTransactionsDetails(transactionDetails);
			log.log(Log.FINE, "\n********Transaction List VO************",
					transactionDetails);
			transactionListVO.setTransactionType(transactionDetails.get(0)
					.getTransactionType());
			log.log(Log.FINE, "\n*********transactionListVO****",
					transactionListVO);
			listULDTransactionSession
					.setReturnTransactionListVO(transactionListVO);
			listULDTransactionSession
					.setULDSCMReconcileDetailsVO(scmreconcileDetailsVO);

			scmUldReconcileForm.setRowIndex("");
			scmUldReconcileForm.setReturnTxn("RETURNTXN");
			invocationContext.target = RETURN_SUCCESS;
			return;

		} else if (LOANTXN.equals(scmreconcileDetailsVO.getErrorCode())) {
			log
					.log(Log.FINE,
							"\n\n\n\n\n\n\n****CASE 4*****RECONCILE TO LOAN***************");
			LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
					MODULE, SCREEN_ID_LOANBORROWULD);
			loanBorrowULDSession.setPageURL(PAGE_URL);
			scmUldReconcileForm.setRowIndex("");
			loanBorrowULDSession
					.setULDSCMReconcileDetailsVO(scmreconcileDetailsVO);
			invocationContext.target = BORROW_SUCCESS;
			return;

		}
	}

	/**
	 *
	 * @param filterVO
	 * @param session
	 * @return
	 */
	private SCMMessageFilterVO populateMessageFilterVO(
			SCMMessageFilterVO filterVO, SCMULDErrorLogSession session) {
		SCMMessageFilterVO filterVOSession = session.getSCMULDFilterVO();
		filterVO.setAirportCode(filterVOSession.getAirportCode());
		filterVO.setCompanyCode(filterVOSession.getCompanyCode());
		filterVO.setPageNumber(filterVOSession.getPageNumber());
		filterVO.setStockControlDate(filterVOSession.getStockControlDate());
		return filterVO;
	}
}
