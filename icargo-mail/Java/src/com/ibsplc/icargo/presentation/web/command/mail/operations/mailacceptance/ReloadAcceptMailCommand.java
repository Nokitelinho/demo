/*
 * ReloadAcceptMailCommand.java Created on Jul 1 2016
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
	public class ReloadAcceptMailCommand extends BaseCommand  {
		private Log log = LogFactory.getLogger("MAILOPERATIONS");
		private static final String TARGET = "screenload_success";
		/**
		 * Execute method
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {

    log.entering("ReloadAcceptMailCommand","execute");
		
    invocationContext.target = TARGET;
    log.exiting("ReloadAcceptMailCommand","execute");
	}
}