/*
 * CloseCommand.java Created on Aug 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 * 
 */
public class CloseCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
	
	private static final String MODULE = "mra.airlinebilling";
	
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	
	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String CLOSE_SUCCESS = "close_success";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		ListInterlineBillingEntriesSession  listInterlineBillingEntriesSession=
			(ListInterlineBillingEntriesSession)getScreenSession(MODULE, SCREENID);
		
		listInterlineBillingEntriesSession.removeAllAttributes();
		invocationContext.target = CLOSE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
