/*
 * ScreenloadCommand.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.uploadgpareport;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2257
 * @author A-2245
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1         Feb 13, 2007   Meera Vijayan			Initial draft
 *  
 *  0.2         Feb 21, 2007   Divya S P				Second draft
 *  
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	private static final String CLASS_NAME = "ScreenloadCommand";
	
		
	/**
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
		
		invocationContext.target = ACTION_SUCCESS;
		
		log.exiting(CLASS_NAME, "execute");
	}	
	
	
}