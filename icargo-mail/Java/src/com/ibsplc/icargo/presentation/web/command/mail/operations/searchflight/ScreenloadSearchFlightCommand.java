/*
 * ScreenloadSearchFlightCommand.java Created on Jul 09, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-3817
 * 
 */
public class ScreenloadSearchFlightCommand extends BaseCommand {
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENID = "mailtracking.defaults.searchflight";

	private static final String PARAMETER_KEY_MAILSTA = "mailtracking.defaults.flightstatus";

	private static final String MODULENAME = "mail.operations";

	private static final String PARAMETER_KEY_FLIGHTSTA = "flight.operation.flightlegstatus";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		SearchFlightSession session = (SearchFlightSession) getScreenSession(
				MODULENAME, SCREENID);
		SearchFlightForm form = (SearchFlightForm) invocationContext.screenModel;
		String carrierCode = getApplicationSession().getLogonVO()
				.getOwnAirlineCode();
		form.setCarrierCode(carrierCode);
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		Collection<String> parameters = new ArrayList<String>();
		parameters.add(PARAMETER_KEY_MAILSTA);
		parameters.add(PARAMETER_KEY_FLIGHTSTA);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		form.setDepartingPort(logonAttributes.getAirportCode());
		form.setFromParentScreen("ON");
		form.setFromScreen(MailConstantsVO.OPERATION_OUTBOUND);
		session.setOperationalFlightVOs(null);
		session.setOneTimeVOs(getOnetimes(companyCode, parameters));
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

	/**
	 * The method to obtain the onetime values. The method will call the
	 * sharedDefaults delegate and returns the map of requested onetimes
	 * 
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOnetimes(
			String companyCode, Collection<String> parameters) {
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		Collection<ErrorVO> errors = null;
		try {
			oneTimeMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, parameters);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeMap;
	}
}
