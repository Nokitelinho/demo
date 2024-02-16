/*
 * CloseCommand.java Created on Feb 01 , 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.pomailstatement;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.POMailStatementSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4777
 *
 */

public class CloseCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILTRACKING,CloseCommand");
	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.mailstatement";
	private static final String CLOSE_SUCCESS = "close_success";
	
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering("CloseCommand","execute");
		POMailStatementSession poMailStatementSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		poMailStatementSession.setSelectedPOMailStatementVOs(null);		
		invocationContext.target = CLOSE_SUCCESS;
		
		log.exiting("CloseCommand","execute");
		
	}
		
}