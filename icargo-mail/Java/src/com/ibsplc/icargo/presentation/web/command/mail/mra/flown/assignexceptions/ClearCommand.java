/*
 * ClearCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2401
 *
 */
public class ClearCommand extends BaseCommand{
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS_NAME = "ClearCommand";
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREEN_ID = "mailtracking.mra.flown.assignexceptions";
	private static final String CLEAR_SUCCESS = "clear_success";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		AssignExceptionSession assignExceptionSession = 
			(AssignExceptionSession)
		getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignExceptionsForm assignExceptionsForm = (
				AssignExceptionsForm)invocationContext.screenModel;
		assignExceptionsForm.setFlightCarrierCode(
				getApplicationSession().getLogonVO().getOwnAirlineCode());
		assignExceptionsForm.setFlightNumber("");
		assignExceptionsForm.setExceptionCode("");
		assignExceptionsForm.setAssignee("");
		assignExceptionsForm.setAssignedDate(null);
		assignExceptionsForm.setSegmentDestination("");
		assignExceptionsForm.setSegmentOrigin("");
		assignExceptionsForm.setResolvedDate(null);
		assignExceptionsForm.setFromDate(null);
		assignExceptionsForm.setToDate(null);
		assignExceptionSession.setExceptions(null);
		assignExceptionSession.setFilterDetails(null);
		assignExceptionsForm.setStatusFlag("");
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
