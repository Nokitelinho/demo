/*
 * CloseMaintainDiscrepancyCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainUldDiscrepanciesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 */
public class CloseMaintainDiscrepancyCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CloseMaintainDiscrepancyCommand");

	/**
	 * The Module Name
	 */
	private static final String SCREENID = "uld.defaults.maintainulddiscrepancies";

	private static final String SCREENID_SCMERRORLOG = "uld.defaults.scmulderrorlog";

	private static final String MODULE = "uld.defaults";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSEMAINTAIN_SUCCESS = "close_maintain_success";

	private static final String CLOSEMAINTAIN_LIST_SUCCESS = "close_maintain_list_success";

	private static final String PAGE_URL = "fromScmUldReconcile";

	private static final String CLOSESCM_SUCCESS = "list_success";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("CloseMaintainDiscrepancyCommand", "CloseCommand");
		MaintainUldDiscrepanciesForm form = (MaintainUldDiscrepanciesForm) invocationContext.screenModel;
		MaintainUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		form.setSaveFlag(false);
		
		if (("createmode").equals(form.getFlag())) {
			form.setFlag("");
			invocationContext.target = CLOSEMAINTAIN_SUCCESS;
			return;
		} else if (("modifymode").equals(form.getFlag())) {
			form.setFlag("");
			invocationContext.target = CLOSEMAINTAIN_SUCCESS;
			return;
		} else if (("createmode").equals(session.getCloseFlag())) {
			session.setCloseFlag("");
			invocationContext.target = CLOSEMAINTAIN_SUCCESS;
			return;
		} else if (("fromlistuldscreen").equals(session.getCloseFlag())) {
			session.setCloseFlag("");
			invocationContext.target = CLOSEMAINTAIN_LIST_SUCCESS;
			return;
		} else if (session.getPageURL() != null
				&& PAGE_URL.equals(session.getPageURL())) {
			SCMULDErrorLogSession scmSession = getScreenSession(MODULE,
					SCREENID_SCMERRORLOG);
			scmSession.setPageUrl("fromMaintainDiscrepancy");
			session.removeAllAttributes();
			invocationContext.target = CLOSESCM_SUCCESS;
			return;
		}

		else {
			invocationContext.target = CLOSE_SUCCESS;
			return;
		}
	}
}
