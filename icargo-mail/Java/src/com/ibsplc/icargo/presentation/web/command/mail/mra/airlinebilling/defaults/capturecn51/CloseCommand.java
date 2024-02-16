/*
 * CloseCommand.java Created on Feb 21, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 *
 */
public class CloseCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String CLOSE_SUCCESS="close_success";
	private static final String CLOSEFROMCN66_SUCCESS="closefromcn66_success";
	private static final String CLOSEFROMVIEWCN51_SUCCESS="closefromviewcn51_success";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String SCREENID_VIEW = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		//CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		if(SCREENID.equals(captureCN51Session.getParentId())) {
			captureCN51Session.setParentId("");
			invocationContext.target=CLOSEFROMCN66_SUCCESS;
		}else if(SCREENID_VIEW.equals(captureCN51Session.getParentId())) {
			captureCN51Session.setParentId("");
			invocationContext.target=CLOSEFROMVIEWCN51_SUCCESS;
		}else {		
			invocationContext.target=CLOSE_SUCCESS;
		}
		log.exiting(CLASS_NAME,"execute");

	}
	
}
