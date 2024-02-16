/*
 * ScreenLoadCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.ux.checkembargo;

import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.ux.checkembargos.CheckEmbargoSession;

import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ux.CheckEmbargoRulesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 * This command class is used to load check embargo screen. This
 * screen is invoked by screens which act as entry point for shipments
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String SUCCESS_MODE = "success";

	private static final String MODULE = "reco.defaults";

	private static final String SCREEN_ID = "reco.defaults.ux.checkembargo";

	private Log log = LogFactory.getLogger("CHECK EMBARGO COMMAND");

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		CheckEmbargoRulesForm checkEmbargoForm = (CheckEmbargoRulesForm)invocationContext.screenModel;

		CheckEmbargoSession checkEmbargoSession = getScreenSession(
				MODULE, SCREEN_ID);

		Collection<EmbargoDetailsVO> embargos = checkEmbargoSession.getEmbargos();

		if(embargos == null) {
			invocationContext.target = "screenload_failure";
		}
		else {
			checkEmbargoForm.setEmbargoPage(embargos);
			checkEmbargoForm.setContinueAction(
				checkEmbargoSession.getContinueAction());
			invocationContext.target = "screenload_success";
		}
	}
}
