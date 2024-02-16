/*
 * ScreenloadReturnReasonCommand.java Created on Oct 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class ScreenloadReturnReasonCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	private static final String REASONCODE = "mailtracking.defaults.return.reasoncode";
	private static final String TARGET = "success";
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("ScreenloadReturnReasonCommand","execute");
		UploadMailForm uploadMailForm 
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
		
    	
    	/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
		
		ScannedDetailsVO scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		String returns = uploadMailForm.getReturnDetails();
		log.log(Log.INFO, "returns.\n", returns);
		log.log(Log.INFO, "scannedDetailsVO....after server.\n",
				scannedDetailsVO);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		invocationContext.target = TARGET;
		log.exiting("ScreenloadReturnReasonCommand","execute");
		
	}
	
}
