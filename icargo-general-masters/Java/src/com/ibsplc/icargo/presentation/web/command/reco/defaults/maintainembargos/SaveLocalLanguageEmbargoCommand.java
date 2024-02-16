/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos.SaveDescriptionCommand.java
 *
 *	Created by	:	a-7815
 *	Created on	:	31-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;

import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos
 * .SaveDescriptionCommand.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-7815 :
 * 31-Aug-2017 : Draft
 */
public class SaveLocalLanguageEmbargoCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.framework.web.command.Command#execute
	 * (com.ibsplc.icargo.framework.web.command.InvocationContext) Added by :
	 * a-7815 on 31-Aug-2017 Used for : Parameters : @param arg0 Parameters : @throws
	 * CommandInvocationException
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE,
				"<-----------SaveLocalLanguageEmbargoCommand Commmand----------------------->");
		MaintainEmbargoRulesSession maintainEmbargoSession = getScreenSession(
				"reco.defaults", "reco.defaults.maintainembargo");
		MaintainEmbargoRulesForm maintainEmbargoForm = (MaintainEmbargoRulesForm) invocationContext.screenModel;
		Map<String, String> localLanguageEmbargos = (HashMap<String, String>) maintainEmbargoSession
				.getLocalLanguageEmbargo();
		if (localLanguageEmbargos == null) {
			localLanguageEmbargos = new HashMap<String, String>();
			maintainEmbargoSession
					.setLocalLanguageEmbargo(localLanguageEmbargos);
		}
		String prevEmbargoLang = maintainEmbargoForm.getPrevEmbargoLang();
		String prevEmbargoDesc = maintainEmbargoForm.getPrevEmbargoDesc();
		if ((prevEmbargoDesc == null || prevEmbargoDesc.trim().length() <= 0)
				&& localLanguageEmbargos.containsKey(prevEmbargoLang)) {
			localLanguageEmbargos.remove(prevEmbargoLang);
		} else {
			localLanguageEmbargos.put(prevEmbargoLang, prevEmbargoDesc);
		}
		String currentEmbargoLang = maintainEmbargoForm.getCurrentEmbargoLang();
		maintainEmbargoForm.setCurrentEmbargoDesc(localLanguageEmbargos
				.get(currentEmbargoLang));
		invocationContext.target = "save_success";
	}

}
