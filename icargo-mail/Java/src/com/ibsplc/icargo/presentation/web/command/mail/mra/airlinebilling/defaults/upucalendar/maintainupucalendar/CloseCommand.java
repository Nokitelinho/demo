/*
 * 
 * CloseCommand.java Created on Sep 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.upucalendar.maintainupucalendar;
/*
 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 * 
 * Removes all data from objects
 */
public class CloseCommand extends BaseCommand {

   
	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID =
    	"mailtracking.mra.airlinebilling.defaults.upucalendar";
	

	private static final String CLOSEDETAILS_SUCCESS = "closedetails_success";

	

	private static final String CLASS_NAME = "CloseCommand";
	
	/**
	 * Method  implementing close operation of upu details screen
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");

		log.entering(CLASS_NAME, "execute");

		UPUCalendarSession upuCalendarSession = (UPUCalendarSession)getScreenSession(
																		MODULE_NAME, SCREENID);

		upuCalendarSession.removeAllAttributes(); 		// removing VO collection from session

		invocationContext.target = CLOSEDETAILS_SUCCESS;	// sets target

		log.exiting(CLASS_NAME,"execute");
    }

}
