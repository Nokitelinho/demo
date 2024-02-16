/* ClearSCMSeqNoLovCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmreconcile;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMSequenceNoLovForm;

/**
 * 
 * @author A-2046
 * 
 */
public class ClearSCMSeqNoLovCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.scmreconcile";

	private static final String BLANK = "";

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		SCMSequenceNoLovForm form = (SCMSequenceNoLovForm) invocationContext.screenModel;
		SCMReconcileSession session = getScreenSession(MODULE, SCREENID);
		form.setSequenceNo(BLANK);
		form.setDisplayPage("1");
		session.setSCMReconcileLovVOs(null);
		invocationContext.target=CLEAR_SUCCESS;

	}
}
