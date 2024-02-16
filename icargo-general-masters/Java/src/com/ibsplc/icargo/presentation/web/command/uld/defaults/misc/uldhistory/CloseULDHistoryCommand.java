/*
 * CloseULDHistoryCommand.java Created on Oct 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldhistory;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDHistoryForm;

/**
 *
 * @author A-2619
 *
 */

public class CloseULDHistoryCommand extends BaseCommand {

	private Log log;

	private static final String SCREEN_ID = "uld.defaults.uldhistory";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLOSE_SUCCESS = "uldhistory_close_success";

	public CloseULDHistoryCommand() {
		log = LogFactory.getLogger("CloseULDHistoryCommand");
	}
	
	/**
	 * @author A-2619
	 *         
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("CloseULDHistoryCommand", "execute");
		ULDHistoryForm uldHistoryForm= (ULDHistoryForm) invocationContext.screenModel;

		invocationContext.target = CLOSE_SUCCESS;
		return;
	}

}
