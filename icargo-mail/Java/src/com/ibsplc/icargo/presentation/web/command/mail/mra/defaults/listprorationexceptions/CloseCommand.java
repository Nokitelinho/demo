/*
 * CloseCommand.java Created on Sep, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.log.Log;




/**
 * @author A-3108
 *
 */
public class CloseCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA PRORATION");

	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String CLASS_NAME = "CloseCommand";
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String INVOKING_SCREEN = "viewflightsectorrevenue";
	private static final String TO_LISTRATEAUDIT = "toListRateAudit";
	private static final String VIEWFLIGHT_CLOSE = "viewflight_close";
	private static final String LISTRATEAUDIT_CLOSE = "listrateaudit_close";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

	log.entering(CLASS_NAME, "execute");
	ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;
	log.log(Log.INFO, "listExceptionForm.getCloseFlag()===>>",
			listExceptionForm.getCloseFlag());
	if(listExceptionForm.getCloseFlag()!=null 
			&&INVOKING_SCREEN.equals(listExceptionForm.getCloseFlag())){
		log.log(Log.INFO, "inside if====>", listExceptionForm.getCloseFlag());
		invocationContext.target =  VIEWFLIGHT_CLOSE;
	}
	else if(listExceptionForm.getCloseFlag()!=null 
			&&TO_LISTRATEAUDIT.equals(listExceptionForm.getCloseFlag())){
		log.log(Log.INFO, "inside if====>", listExceptionForm.getCloseFlag());
		invocationContext.target =  LISTRATEAUDIT_CLOSE;
	}
	else{
		log.log(Log.INFO, "inside else");
		invocationContext.target =  CLOSE_SUCCESS;
	}
		
	log.exiting(CLASS_NAME, "execute");
	}

}
