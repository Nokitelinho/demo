/*
 * ScreenLoadDuplicateEmbargosCommand.java Created on Feb 07, 2014
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
 * 
 * @author a-5160
 * The Class ScreenLoadDuplicateEmbargosCommand.
 */
public class ScreenLoadDuplicateEmbargosCommand extends BaseCommand {
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MaintainEmbargoRulesSession maintainEmbargoSession = getScreenSession(
						"reco.defaults", "reco.defaults.maintainembargo");
		MaintainEmbargoRulesForm maintainEmbargoForm =
					(MaintainEmbargoRulesForm) invocationContext.screenModel;
		invocationContext.target = "modifyScreenloadSuccess";  
	}
}