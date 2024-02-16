/*
 * ClearULDIntMvtCommand.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldintmvthistory;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDIntMvtHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDIntMvtHistoryForm;

/**
 * This command is used to Clear the
 * Internal ULD Movement History screen
 * 
 * @author A-2412
 * @param invocationContext	
 * @return
 * @throws CommandInvocationException 
 */
public class ClearULDIntMvtCommand extends BaseCommand {
	private static final String SCREENID = "uld.defaults.misc.uldintmvthistory";

	private static final String MODULE = "uld.defaults";

	private static final String CLEAR_SUCCESS = "clear_success";

	/**
	 * @param invocationContext	
	 * @return
	 * @throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ULDIntMvtHistorySession session = getScreenSession(MODULE, SCREENID);
		ULDIntMvtHistoryForm form = (ULDIntMvtHistoryForm) invocationContext.screenModel;
		form.setCurrentLocation("");
		form.setOwnerCode("");
		form.setOwnerStation("");
		form.setUldNumber("");
		session.setIntULDMvtDetails(null);
		session.setULDIntMvtHistoryFilterVO(null);
		session.setOneTimeValues(null);
		form.setAfterList("");
		invocationContext.target = CLEAR_SUCCESS;

	}

}
