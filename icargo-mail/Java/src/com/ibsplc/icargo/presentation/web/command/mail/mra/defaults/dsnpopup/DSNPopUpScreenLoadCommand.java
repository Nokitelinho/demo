/*
 * DSNPopUpScreenLoadCommand.java Created on AUG 28, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dsnpopup;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
/**
 * 
 * @author A-2391
 *
 */
public class DSNPopUpScreenLoadCommand extends BaseCommand{
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		invocationContext.target=SCREENLOAD_SUCCESS;

	}

}
