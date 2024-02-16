/*
 * ClearCommand.java Created on Apr 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.versionnumberlov;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.VersionNumberLOVForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2518
 * 
 */

public class ClearCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String CLASS_NAME = "ClearCommand";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		VersionNumberLOVForm versionNumberLovForm = (VersionNumberLOVForm) invocationContext.screenModel;
		versionNumberLovForm.setCode(null);
		versionNumberLovForm.setName(null);
		versionNumberLovForm.setVersionNumbers(null);
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
