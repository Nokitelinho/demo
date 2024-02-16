/*
 * ScreenloadCommand.java Created on Mar 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for loading captureform3
 * 
 * Revision History
 * 
 * Version   Date            Author            Description
 * 
 * 0.1     Mar 20, 2007     Rani Rose John     Initial draft
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenloadCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String PRORTATESTATUS_ONETIME = "cra.proration.proratestatus";

	private static final String PRORTATESOURCE_ONETIME = "cra.proration.proratesource";

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
		MaintainMraProrateFactorsForm MaintainProrateFactorsForm = (MaintainMraProrateFactorsForm) invocationContext.screenModel;
		MaintainMraProrateFactorsSession maintainProrateFactorsSession = (MaintainMraProrateFactorsSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		maintainProrateFactorsSession
				.setOneTimeValues(
						(HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		maintainProrateFactorsSession.setFilterDetails(null);
		maintainProrateFactorsSession.setFactors(null);
		MaintainProrateFactorsForm.setLinkStatusFlag(LINKSTATUS);
		MaintainProrateFactorsForm
				.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * 
	 * @return Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(PRORTATESTATUS_ONETIME);
		parameterTypes.add(PRORTATESOURCE_ONETIME);
		return parameterTypes;
	}
}