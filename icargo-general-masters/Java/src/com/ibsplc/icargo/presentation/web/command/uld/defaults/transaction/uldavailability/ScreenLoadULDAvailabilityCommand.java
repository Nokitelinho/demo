/*
 * ScreenLoadULDAvailabilityCommand.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.uldavailability;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ULDAvailabilitySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDAvailabilityForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
//import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

/**
 * @author A-3278
 * 
 */
public class ScreenLoadULDAvailabilityCommand extends BaseCommand {

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldavailability";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String PARTY_TYPE = "uld.defaults.PartyType";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ULDAvailabilityForm form = (ULDAvailabilityForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Commented by Manaf for INT ULD510
		//String companyCode = logonAttributes.getCompanyCode();
		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes
				.getCompanyCode());
		Collection<OneTimeVO> partyTypes = oneTimeCollection.get(PARTY_TYPE);
		ULDAvailabilitySession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		session.setPartyType((ArrayList<OneTimeVO>) partyTypes);
		form.setIsPreview("NONE");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

	/**
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(PARTY_TYPE);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		//Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			//error = handleDelegateException(exception);
		}
		return hashMap;
	}
}
