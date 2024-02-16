/*
 * AttachmentRefreshCommand created on October 29, 2018 
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8061
 *
 */
public class AttachmentRefreshCommand extends BaseCommand {

	private static final String REFRESH_SUCCESS= "refresh_success";
	private static final String CLASS_NAME= "AttachmentRefreshCommand";

	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");
		log.entering(CLASS_NAME, "execute");
		invocationContext.target = REFRESH_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		
	}

}
