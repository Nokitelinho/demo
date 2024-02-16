/*
 * CloseCommand.java Created on Feb 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 23, 2007			a-2257		Created
 */
public class CloseCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "CloseCommand";
		
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/*
	 * String for MODULE_NAME, SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";	
	/*
	 * ScreenId for ASSIGNEXPTNS_SCREENID
	 */
	private static final String ASSIGNEXPTNS_SCREENID = 
							"mailtracking.mra.gpareporting.assignexceptions";
	/*
	 * Close action for Assign Exceptions screen
	 */
	private static final String ASSIGNEXPTNS_LISTACTION = "assignexptns_listaction";
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");		
		/*
		 * getting session
		 */
		CaptureGPAReportSession captureGPAReportSession = 
			(CaptureGPAReportSession)getScreenSession(MODULE_NAME, SCREENID);	
		if(ASSIGNEXPTNS_SCREENID.equals(captureGPAReportSession.getParentScreenFlag())){
			invocationContext.target = ASSIGNEXPTNS_LISTACTION;
		}else{
		invocationContext.target = ACTION_SUCCESS;      	
		}
		captureGPAReportSession.removeAllAttributes();
		log.exiting(CLASS_NAME, "execute");
	}
}
