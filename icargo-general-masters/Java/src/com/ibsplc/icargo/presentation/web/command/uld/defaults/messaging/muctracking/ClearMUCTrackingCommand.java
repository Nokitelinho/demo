/*
 * ClearMUCTrackingCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class ClearMUCTrackingCommand extends BaseCommand{
	
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("MUC Tracking");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.messaging.muctracking";

	/**
	 * Target if success
	 */
	private static final String CLEAR_SUCCESS = "clear_success";
			
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearMUCTrackingCommand","execute");
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);		
		mucTrackingSession.setListDisplayColl(null);
		mucTrackingSession.setListFilterVO(null);
        invocationContext.target =CLEAR_SUCCESS;
             
         	log.exiting("ClearMUCTrackingCommand","execute");
	}

}
