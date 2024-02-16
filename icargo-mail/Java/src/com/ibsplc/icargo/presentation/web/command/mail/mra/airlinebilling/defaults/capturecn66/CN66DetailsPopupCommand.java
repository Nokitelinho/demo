/*
 * CN66DetailsPopupCommand.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class CN66DetailsPopupCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ScreenloadCommand");

	private static final String CLASS_NAME = "CN66DetailsScreenLoadCommand";

	//private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	//private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "screenload_success";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}