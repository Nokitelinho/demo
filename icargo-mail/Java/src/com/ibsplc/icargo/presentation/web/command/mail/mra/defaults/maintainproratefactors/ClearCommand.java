/*
 * ClearCommand.java Created on Nov 30, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Rani Rose John
 * Command class for clearing
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Nov 30, 2006 Rani Rose John Initial draft
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("CRA PRORATION");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String LINKSTATUS = "disable";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MaintainMraProrateFactorsForm MaintainProrateFactorsForm = 
			(MaintainMraProrateFactorsForm) invocationContext.screenModel;
		MaintainMraProrateFactorsSession maintainProrateFactorsSession = 
			(MaintainMraProrateFactorsSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		maintainProrateFactorsSession.setFilterDetails(null);
		maintainProrateFactorsSession.setFactors(null);
		
		MaintainProrateFactorsForm.setOriginCityCode("");
		MaintainProrateFactorsForm.setOriginCityName("");
		MaintainProrateFactorsForm.setDestCityCode("");
		MaintainProrateFactorsForm.setDestCityName("");
		MaintainProrateFactorsForm.setStatusFilter("");
		MaintainProrateFactorsForm.setSourceFilter("");
		MaintainProrateFactorsForm.setEffFromDate("");
		MaintainProrateFactorsForm.setEffToDate("");
		MaintainProrateFactorsForm.setDateStatusFlag("");
		MaintainProrateFactorsForm.setLinkStatusFlag(LINKSTATUS);
		MaintainProrateFactorsForm
				.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	
}