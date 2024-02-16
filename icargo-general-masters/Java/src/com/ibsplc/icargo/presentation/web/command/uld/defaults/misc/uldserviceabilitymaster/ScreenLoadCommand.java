/*
 * ScreenLoadCommand.java Created on Sep 1, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldserviceabilitymaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDServiceabilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up 
 * 
 * @author A-2052
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ScreenLoadCommand");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String PARTYTYPE_STATUS = "uld.defaults.PartyType";

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_NAME = "screenLoad";

	private static final String BLANK = "";

	/** 
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		ULDServiceabilityForm form = (ULDServiceabilityForm) invocationContext.screenModel;
		ULDServiceabilityMasterSession session = getScreenSession(MODULE_NAME,
				SCREENID);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(PARTYTYPE_STATUS);
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
		Collection<OneTimeVO> partyType = (Collection<OneTimeVO>) hashMap
				.get(PARTYTYPE_STATUS);
		session.setPartyType(partyType);
		form.setPartyType(BLANK);
		log.log(Log.FINE, "The Screen Name before chk is null or not", form.getScreenName());
		if (form.getScreenName() == null
				|| form.getScreenName().trim().length() == 0) {
			form.setScreenName(SCREEN_NAME);
			log.log(Log.FINE, "The Screen Name is", form.getScreenName());
		}

		session.removeULDServiceabilityVOs();

		form
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting("ScreenLoadCommand", "execute");
	}
}
