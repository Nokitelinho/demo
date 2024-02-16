/*
 * CloseCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2401
 *
 */
public class CloseCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("CloseCommand");
	private static final String CLASS_NAME = "CloseCommand";
	private static final String SCREEN_ID = "mailtracking.mra.flown.assignexceptions";
	private static final String CLOSE_SUCCESS= "close_success";
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		AssignExceptionSession assignExceptionSession = 
			(AssignExceptionSession)
		getScreenSession(MODULE_NAME, SCREEN_ID);
		assignExceptionSession.removeAllAttributes();
		log.exiting("CloseCommand","exit successfully");
		invocationContext.target = CLOSE_SUCCESS;	
	}

}
