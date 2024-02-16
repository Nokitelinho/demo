/*
 * UploadMailDetailsCommand.java Created on Nov 18, 2009
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

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3817
 * 
 */
public class UploadMailDetailsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";

	private static final String UNSAVED = "U";
	private static final String DMG_RET_REASONCODE = "mailtracking.defaults.return.reasoncode";
	private static final String OFL_REASONCODE = "mailtracking.defaults.offload.reasoncode";
	private static final String MAIL_COMMODITY_SYSPAR = "mailtracking.defaults.booking.commodity";
	private static final String SEPARATOR = "~";
	private static double densityFactor = 0.0D;
	
	private static final String TARGET_SUCCESS = "upload_details_success";	

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {log.entering("UploadMailDetailsCommand","execute");
	}
		

}
