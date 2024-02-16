/*
 * ChangeInboundFlightRefreshCommand.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmaildetails;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * @author a-3817
 * 
 */
public class ChangeInboundFlightRefreshCommand extends BaseCommand {
	private static final String REFRESH_SUCCESS = "inboundflight_refresh_success";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		invocationContext.target = REFRESH_SUCCESS;

	}

}
