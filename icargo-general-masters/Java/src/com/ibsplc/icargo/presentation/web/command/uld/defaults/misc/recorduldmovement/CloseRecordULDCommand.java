/*
 * CloseRecordULDCommand.java  Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.recorduldmovement;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 * 
 */
public class CloseRecordULDCommand extends BaseCommand {

	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("CLOSE RETURN TXN");

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREENID = "uld.defaults.misc.recorduldmovement";

	private static final String CLOSE_ERRORLOG = "close_ulderrorlog";

	private static final String SCREENID_SCMERRORLOG = "uld.defaults.scmulderrorlog";

	private static final String CLOSE_SCMERRORLOG = "close_scmerrorlog";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

	private static final String PAGE_URL = "fromScmUldReconcile";
	

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		RecordULDMovementForm recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;

		RecordUldMovementSession recordUldMovementSession = getScreenSession(
				MODULE_NAME, SCREENID);

		log.log(Log.FINE, "\n close command ", recordULDMovementForm.getPageurl());
		if ((("fromulderrorlog").equals(recordULDMovementForm.getPageurl())
						 || ("fromulderrorlogMessage").equals(recordULDMovementForm.getPageurl()))) {
			log.log(Log.FINE, "\n reconcile  delegate ");
			recordULDMovementForm.setPageurl("close");
			ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					MODULE_NAME, SCREENID_ULDERRORLOG);
			uldErrorLogSession.setPageURL("fromrecorduld");
			recordUldMovementSession.removeAllAttributes();
			invocationContext.target = CLOSE_ERRORLOG;
		} else if (recordULDMovementForm.getPageurl() != null
				&& PAGE_URL.equals(recordULDMovementForm.getPageurl())) {
			SCMULDErrorLogSession scmSession = getScreenSession(MODULE_NAME,
					SCREENID_SCMERRORLOG);
			scmSession.setPageUrl("fromrecorduld");
			recordUldMovementSession.removeAllAttributes();
			recordULDMovementForm.setPageurl("scm_close");
			invocationContext.target = CLOSE_SCMERRORLOG;
			return;
		}
	}

}
