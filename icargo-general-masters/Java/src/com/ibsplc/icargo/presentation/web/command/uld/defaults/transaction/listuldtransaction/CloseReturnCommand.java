/*
 * CloseReturnCommand.java  Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ReturnULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 * 
 */
public class CloseReturnCommand extends BaseCommand {

	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("CLOSE RETURN TXN");

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	private static final String CLOSE_ERRORLOG = "close_ulderrorlog";
	private static final String CLOSE_SCMERRORLOG = "close_scmulderrorlog";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

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

		ReturnULDTransactionForm returnULDTransactionForm = (ReturnULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		if (("fromulderrorlog").equals(returnULDTransactionForm.getPageurl())) {

			log.log(Log.FINE, "\n reconcile  delegate ");
			returnULDTransactionForm.setPageurl("close");
			ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					MODULE_NAME, SCREENID_ULDERRORLOG);
			uldErrorLogSession.setPageURL("fromreturnuld");
			listULDTransactionSession.removeAllAttributes();
			invocationContext.target = CLOSE_ERRORLOG;
		} else if (PAGE_URL.equals(returnULDTransactionForm.getPageurl())) {
			returnULDTransactionForm.setPageurl("scm_close");
			SCMULDErrorLogSession scmSession = getScreenSession(MODULE_NAME,
					SCREENID_SCMERRORLOG);
			scmSession.setPageUrl("fromreturnuld");
			listULDTransactionSession.removeAllAttributes();
			invocationContext.target = CLOSE_SCMERRORLOG;

		}
	}

}
