/*
 * CloseULDAvailabilityCommand.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.uldavailability;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDAvailabilityForm;

/**
 * @author A-3278
 *
 */
public class CloseULDAvailabilityCommand extends BaseCommand {

	private static final String CLOSE = "close";
	private static final String MODULE = "operations.flthandling";
	private static final String SCREENID = "operations.flthandling.flightscreen";
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ULDAvailabilityForm form = (ULDAvailabilityForm)invocationContext.screenModel;
		invocationContext.target = CLOSE;

	}

}
