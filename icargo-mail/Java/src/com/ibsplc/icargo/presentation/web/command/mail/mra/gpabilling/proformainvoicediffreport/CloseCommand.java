/*
 * CloseCommand.java Created on Aug 11, 2008
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.proformainvoicediffreport;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3271
 *
 */
public class CloseCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MAILTRACKING GPABILLING");
	private static final String CLASS_NAME = "CloseCommand";
	private static final String CLOSE_SUCCESS="close_success";
	
	/** @param invocationContext
	/* @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
	log.entering(CLASS_NAME, "execute");
	log.log(Log.FINE,"Exiting from Proforma-Invoice Diff Report Screen");

	invocationContext.target = CLOSE_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
	
	}
}