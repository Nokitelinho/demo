/*
 * CloseCommand.java Created on Jun 20, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.generateinvoice;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2049
 *
 */
public class CloseCommand extends BaseCommand {

	private static final String CLOSE_SUCCESS = "close_success";
	
	private Log log = LogFactory.getLogger("MailTracking:Mra");
	/**
	 * Method to implement the screen load operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("CloseCommand","execute");
    	invocationContext.target = CLOSE_SUCCESS;
    	log.exiting("CloseCommand","execute");
    }

}
