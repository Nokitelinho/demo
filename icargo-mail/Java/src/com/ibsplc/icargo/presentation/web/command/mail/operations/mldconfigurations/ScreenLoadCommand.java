/*
 * ScreenloadCommand.java Created on December 15, 2015
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mldconfigurations;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MLDConfigurationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MLDConfigurationForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-5526
 * 
 */
public class ScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MAILTRACKING");

	/**
	 * TARGET
	 */
	private static final String TARGET = "screenload_success";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mldconfiguration";

	/**
	 * This method overrides the executre method of BaseComand class
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadCommand", "execute");

		MLDConfigurationForm mLDConfigurationForm = (MLDConfigurationForm) invocationContext.screenModel;
		MLDConfigurationSession mLDConfigurationSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		mLDConfigurationSession.setMLDConfigurationVOs(null);
		mLDConfigurationForm.setAirport("");
		mLDConfigurationForm.setCarrier("");
		invocationContext.target = TARGET;

		log.exiting("ScreenLoadCommand", "execute");

	}

}
