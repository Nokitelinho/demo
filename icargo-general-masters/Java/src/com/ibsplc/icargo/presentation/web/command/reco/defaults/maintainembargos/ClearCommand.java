/*
 * ClearCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;

/**
 *  Class for clearing embargo details
 */
public class ClearCommand extends BaseCommand{

	private static final String STATION="S";
	
	/**
	 * The execute method for ClearCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		
		
		MaintainEmbargoRulesForm actionForm
			= (MaintainEmbargoRulesForm)invocationContext.screenModel;

		MaintainEmbargoRulesSession acceptanceSession
			= (MaintainEmbargoRulesSession) getScreenSession(
					"reco.defaults","reco.defaults.maintainembargo");

		acceptanceSession.removeEmbargoVos();
		acceptanceSession.removeApplicableCode();
		acceptanceSession.removeLevelCode();
		acceptanceSession.removeParameterCode();
		acceptanceSession.removeEmbargoParameterVos();
		acceptanceSession.removeValues();
		clearEmbargoForm(actionForm);
		invocationContext.target = "screenload_success";
	}

	/**
	 * @param actionForm
	 */
	private void clearEmbargoForm(
			MaintainEmbargoRulesForm actionForm) {

		actionForm.setDestinationType(STATION);
		actionForm.setDestination("");
		actionForm.setEndDate("");
		actionForm.setEmbargoLevel("");
		actionForm.setMode("");
		actionForm.setOriginType(STATION);
		actionForm.setOrigin("");
		actionForm.setStartDate("");
		actionForm.setStatus("");
		actionForm.setEmbargoDesc("");
		actionForm.setRemarks("");
		actionForm.setRefNumber("");
		actionForm.setViaPoint("");
		actionForm.setViaPointType("");
		actionForm.setDaysOfOperation("");
		actionForm.setDaysOfOperationApplicableOn("");
	}

}
