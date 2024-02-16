/*
 * CancelReconciliationCommand.java Created on JULY 24, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.FlightReconciliationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.FlightReconciliationForm;

/**
 * @author A-3817
 *
 */
public class CancelReconciliationCommand extends BaseCommand{
	private static final String SCREENID = "mailtracking.defaults.flightreconcilation";

	

	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREENID_SEARCH = "mailtracking.defaults.searchflight";

	
	
   private static final String CANCEL_SUCCESS="Cancel_success";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		FlightReconciliationForm form=(FlightReconciliationForm)invocationContext.screenModel;
		FlightReconciliationSession flightReconciliationSession =(FlightReconciliationSession)getScreenSession(MODULE_NAME, SCREENID);
		//SearchFlightSession searchFlightSession=(SearchFlightSession)getScreenSession(MODULE_NAME, SCREENID_SEARCH);
		form.setCloseFlag("O");
		form.setFinaliseBtnFlag("");
		flightReconciliationSession.removeAllAttributes();
		invocationContext.target=CANCEL_SUCCESS;
	}

}
