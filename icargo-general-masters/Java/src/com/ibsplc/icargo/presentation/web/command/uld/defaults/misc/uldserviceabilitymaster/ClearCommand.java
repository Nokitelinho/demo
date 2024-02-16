/*
 * ClearCommand.java Created on Sep  01, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldserviceabilitymaster;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDServiceabilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class ClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ClearCommand");

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS = "Clear_success";

	private static final String BLANK = "";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ULDServiceabilityForm form = (ULDServiceabilityForm) invocationContext.screenModel;
		ULDServiceabilityMasterSession session = getScreenSession(MODULE_NAME,
				SCREENID);
		form.setAfterList(BLANK);
		session.setPartyTypeValue(BLANK);
		session.removeULDServiceabilityVOs();
		invocationContext.target = CLEAR_SUCCESS;
	}

}
