/*
 * CloseListCN51sScreenCommand.java Created on Mar 20,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2049
 *
 */
public class CloseListCN51sScreenCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");
	
	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";
	
	private static final String CLOSE_SUCCESS = "close_success";
	
	/**
	 * 
	 */
	public void execute(InvocationContext invContext)
			throws CommandInvocationException {
		log.entering("CloseListCN51sScreenCommand","execute");
		ListCN51ScreenSession cn51ScreenSession
				= (ListCN51ScreenSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		cn51ScreenSession.removeAllAttributes();
		invContext.target = CLOSE_SUCCESS;
		log.exiting("CloseListCN51sScreenCommand","execute");
	}

}
