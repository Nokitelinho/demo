/*
 * ScreenLoadULDHistoryCommand.java Created on Oct 17, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDHistoryForm;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

/**
 * 
 * @author A-2619
 * 
 */

public class ScreenLoadULDHistoryCommand extends BaseCommand {

	private Log log;

	private static final String SCREEN_ID = "uld.defaults.uldhistory";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREENLOAD_SUCCESS = "uldhistory_screenload_success";

	private static final String ULDHISTORY = "uld.defaults.uldhistory";

	public ScreenLoadULDHistoryCommand() {
		log = LogFactory.getLogger("ScreenLoadCustomsFlightStatusCommand");
	}

	/**
	 * @author A-2619
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadULDHistoryCommand", "execute");
		ULDHistoryForm uldHistoryForm = (ULDHistoryForm) invocationContext.screenModel;
		uldHistoryForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ULDHistorySession uldHistorySession = getScreenSession(MODULE_NAME,
				SCREEN_ID);

		// onetimevo
		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(ULDHISTORY);
		Map<String, Collection<OneTimeVO>> oneTimeData = new HashMap<String, Collection<OneTimeVO>>();

		try {
			oneTimeData = new SharedDefaultsDelegate().findOneTimeValues(
					logonAttributes.getCompanyCode(), fieldTypes);
			// adding onetime values into the session
			if (oneTimeData != null) {
				uldHistorySession.setOneTimeULDHistoryVO(oneTimeData
						.get(ULDHISTORY));
			}
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
		return;
	}

}
