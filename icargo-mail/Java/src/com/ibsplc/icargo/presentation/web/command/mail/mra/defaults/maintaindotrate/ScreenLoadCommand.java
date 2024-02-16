/*
 * ScreenLoadCommand.java Created on Aug 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaindotrate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainDOTRateSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainDOTRateForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MaintainDOTRate ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaindotrate";

	private static final String KEY_RATECODE = "mailtracking.mra.defaults.ratecode";

	private static final String KEY_REGIONCODE = "mailtracking.mra.defaults.regioncode";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";

	//private static final String ACTION_FAILURE = "screenload_failure";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		MaintainDOTRateForm form = (MaintainDOTRateForm) invocationContext.screenModel;
		MaintainDOTRateSession session = null;
		session = (MaintainDOTRateSession) getScreenSession(MODULE_NAME,
				SCREEN_ID);
		session.removeAllAttributes();
		Map oneTimeHashMap = null;
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_RATECODE);
		oneTimeActiveStatusList.add(KEY_REGIONCODE);
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			handleDelegateException(e);
		}
		session
				.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap);
		form.setScreenFlag("screenload");

		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
