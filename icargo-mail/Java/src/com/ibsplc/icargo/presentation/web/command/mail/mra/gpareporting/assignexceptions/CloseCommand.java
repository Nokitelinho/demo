/*
 * CloseCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.assignexceptions;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.AssignExceptionsSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2245
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date          	 	Author          Description
 * 
 *  0.1         Feb 21, 2007   		A-2245			Initial draft
 *  
 *  
 *  
 */
public class CloseCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	/*
	 * String constants for CLASS_NAME
	 */
	private static final String CLASS_NAME = "CloseCommand";
	/*
	 * String constants for MODULE_NAME,SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.assignexceptions";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		/*
		 * getting session
		 */
		AssignExceptionsSession assignExceptionsSession = getScreenSession(MODULE_NAME,SCREENID);
		assignExceptionsSession.removeAllAttributes();
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}	
	
	
}