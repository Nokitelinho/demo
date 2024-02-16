/*
 * CloseCommand.java Created on Dec 20, 2006
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
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm;


/**
 *  Class for closing embargo details
 */
public class CloseCommand extends BaseCommand{

		
	/**
	 * The execute method for ClearCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		ListEmbargoRulesForm actionForm
			= (ListEmbargoRulesForm)invocationContext.screenModel;
		
		setEmbargoForm(actionForm);
		invocationContext.target = "close_success";
	}

	/**
	 * @param actionForm
	 */
	private void setEmbargoForm(
			ListEmbargoRulesForm actionForm) {
		actionForm.setDestinationType("");
		actionForm.setDestination("");
		actionForm.setOriginType("");
		actionForm.setOrigin("");
		actionForm.setComplianceType("");
		actionForm.setCategory("");
		actionForm.setApplicableTransactions("");
	}
}
