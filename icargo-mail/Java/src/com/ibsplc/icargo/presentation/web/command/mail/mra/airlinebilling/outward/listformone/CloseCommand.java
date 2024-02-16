/*
 * CloseCommand.java Created on July 16, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.listformone;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ListFormOneSession;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 * 
 * Removes all data from session 
 */
public class CloseCommand extends BaseCommand {


	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String MODULE = "mra.airlinebilling";
	
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.listform1";

	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	        Log log = LogFactory.getLogger("MRA airlinebilling");
		log.entering(CLASS_NAME, "execute");				
		
		ListFormOneSession  listFormOneSession =
			(ListFormOneSession)getScreenSession(MODULE, SCREENID);
		
		listFormOneSession.removeAllAttributes();
		
		invocationContext.target = CLOSE_SUCCESS;	// sets target
		log.exiting(CLASS_NAME,"execute");
    }

}
