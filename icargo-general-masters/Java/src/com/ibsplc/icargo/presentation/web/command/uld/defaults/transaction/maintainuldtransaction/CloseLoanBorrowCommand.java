/*
 * CloseLoanBorrowCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 * 
 */
public class CloseLoanBorrowCommand extends BaseCommand {

	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");

	private static final String MODULE_LISTULD = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	private static final String SCREENID_LISTULD = "uld.defaults.listuld";

	private static final String CLOSE_ERRORLOG = "close_ulderrorlog";

	private static final String CLOSE_SCMERRORLOG = "close_scmulderrorlog";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

	/**
	 * target String if success
	 */
	private static final String CLOSE_LISTULD = "close_listuld";
	
	private static final String CLOSE_MUCTRACKING = "close_muctracking";
	
	private static final String SCREENID_MUCTRACKING = "uld.defaults.messaging.muctracking";

	private static final String PAGE_URL = "fromScmUldReconcile";

	private static final String SCREENID_SCMERRORLOG = "uld.defaults.scmulderrorlog";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
		ListULDSession listULDSession = (ListULDSession) getScreenSession(
				MODULE_LISTULD, SCREENID_LISTULD);
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_LISTULD, SCREEN_ID);
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE_LISTULD,
				SCREENID_MUCTRACKING);

		log
				.log(
						Log.FINE,
						"\n\n\nPage URL on entering Maintain ULD Transaction Form----->",
						maintainULDTransactionForm.getPageurl());
		if (("ListULD").equals(maintainULDTransactionForm.getCloseStatus())) {
			listULDSession.setListStatus("noListForm");
			invocationContext.target = CLOSE_LISTULD;
		}else if (("MUCTracking").equals(maintainULDTransactionForm.getCloseStatus())) {
			log.log(Log.FINE, "\ninside muctracking condition ");
			mucTrackingSession.setListStatus("noListForm");
			invocationContext.target = CLOSE_MUCTRACKING;
		}
		if ((("fromulderrorlogforborrow").equals(maintainULDTransactionForm.getPageurl())
						|| ("fromulderrorlogforloan").equals(maintainULDTransactionForm.getPageurl()))) {

			log.log(Log.FINE, "\n reconcile  delegate ");
			ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					MODULE_LISTULD, SCREENID_ULDERRORLOG);
			uldErrorLogSession.setPageURL("fromloanborrowuld");
			loanBorrowULDSession.removeAllAttributes();
			invocationContext.target = CLOSE_ERRORLOG;
		} else if ((PAGE_URL.equals(maintainULDTransactionForm.getPageurl()))
				|| "fromScmReconcileBorrow".equals(maintainULDTransactionForm
						.getPageurl())) {
			SCMULDErrorLogSession scmSession = getScreenSession(MODULE_LISTULD,
					SCREENID_SCMERRORLOG);
			scmSession.setPageUrl("fromloanborrowuld");
			loanBorrowULDSession.removeAllAttributes();
			invocationContext.target = CLOSE_SCMERRORLOG;
		}
	}
}
