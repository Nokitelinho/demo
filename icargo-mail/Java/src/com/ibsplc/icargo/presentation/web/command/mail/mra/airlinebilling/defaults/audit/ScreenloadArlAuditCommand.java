/*
 * ScreenloadArlAuditCommand.java Created on Aug 16, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.audit;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 * 
 */
public class ScreenloadArlAuditCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILTRACKING");

	//private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";	

	//private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.airlineaudit";	

	private static final String SUCCESS = "screenload_success";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenloadArlAuditCommand", "execute");
		invocationContext.target = SUCCESS;
		log.exiting("ScreenloadDsnAuditCommand", "execute");

	}
}
