/*
 * CloseCommand.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author a-2449
 *
 */
public class CloseCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Flown CloseCommand");
	
	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREEN_ID = "mra.flown.viewflownmail";
	private static final String CLOSE_SUCCESS = "close_success";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ViewFlownMailSession session = (ViewFlownMailSession) getScreenSession(MODULE_NAME,SCREEN_ID);
		session.removeAllAttributes();
		
		invocationContext.target = CLOSE_SUCCESS;	
		log.exiting(CLASS_NAME,"execute");
	}
	
}