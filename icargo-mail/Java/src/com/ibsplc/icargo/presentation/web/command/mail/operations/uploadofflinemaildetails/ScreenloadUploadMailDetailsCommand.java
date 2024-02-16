/*
 * ScreenloadUploadMailDetailsCommand.java Created on Oct 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadofflinemaildetails;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * @author A-6385
 * Added for ICRD-84459
 */
public class ScreenloadUploadMailDetailsCommand extends BaseCommand {
	
	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.offlinemailupload";
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		invocationContext.target = "success";
	}
}
