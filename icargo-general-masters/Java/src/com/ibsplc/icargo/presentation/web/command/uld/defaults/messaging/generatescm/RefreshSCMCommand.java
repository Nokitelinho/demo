/*
 * RefreshSCMCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class RefreshSCMCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Generate SCM");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String REFRESH_SUCCESS = "refresh_success";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AddExtraULDCommand", "execute");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();

		GenerateSCMSession generateSCMSession = (GenerateSCMSession) getScreenSession(
				MODULE, SCREENID);

		GenerateSCMForm generateSCMForm = (GenerateSCMForm) invocationContext.screenModel;
		Collection<ULDVO> uldVOs = generateSCMSession.getSystemStock();
		int uldIndex = 0;
		String[] ulNumbers = generateSCMForm.getExtrauld();
		for (ULDVO uldVo : uldVOs) {
			uldVo.setUldNumber(ulNumbers[uldIndex].toUpperCase());
			uldIndex++;
		}
		
		
		invocationContext.target = REFRESH_SUCCESS;
	}
}
