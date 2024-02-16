/*
 * ClearULDPoolOwnersCommand.java Created on AUG 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldpoolowners;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDPoolOwnersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ClearULDPoolOwnersCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("Clear Command", "ULD Pool Owners");
		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		form.setFirstAirline(logonAttributes.getOwnAirlineCode());
		form.setSecondAirline(BLANK);
		form.setPolAirport(BLANK);
		form.setPopupFlag(BLANK);
		session.setFlightValidationVOSession(null); 
		session.setUldPoolOwnerVO(null);
		form.setLinkStatus(BLANK);
		invocationContext.target = CLEAR_SUCCESS;

	}
}
