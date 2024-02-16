/*
 * ScreenloadUploadMailDetailsCommand.java Created on Oct 06, 2006
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
public class ScreenloadUploadMailDetailsCommand extends BaseCommand {
	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";
	
	private static final String UNSAVED = "U";

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		
		/*UploadMailDetailsForm uploadMailDetailsForm = 
			(UploadMailDetailsForm)invocationContext.screenModel;
		
		UploadMailDetailsSession uploadMailDetailsSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);*/
		
		

		invocationContext.target = "success";
	}
}
