/*
 * ClearCommand.java Created on July 16, 2008
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
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");
	
	private static final String MODULE = "mra.airlinebilling";
	
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.listform1";
		
	private static final String CLEAR_SUCCESS = "clear_success";
	
	private static final String EMPTY_STRING = "";
	
	private static final String CLASS_NAME = "ClearCommand";
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		 ListMailFormOneForm listMailFormOneForm = ( ListMailFormOneForm)invocationContext.screenModel;
		
		ListFormOneSession  listFormOneSession =
			(ListFormOneSession)getScreenSession(MODULE, SCREENID);
		
		listFormOneSession.removeAllAttributes();
		
		//listFormOneForm.setFormOnePage(null);
		listMailFormOneForm.setClearancePeriod(EMPTY_STRING);
		listMailFormOneForm.setAirlineCodeFilterField(EMPTY_STRING);
		listMailFormOneForm.setAirlineNumber(EMPTY_STRING);
		log.exiting(CLASS_NAME,"execute");
		invocationContext.target=CLEAR_SUCCESS;

	}

}
