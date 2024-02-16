/*
 * ScreenLoadUCMErrorLogCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the
 * ScreenLoadUCMErrorLogCommand screen
 * 
 * @author A-1862
 */

public class ScreenLoadUCMErrorLogCommand extends BaseCommand {

	/**
	 * Logger for UCM Error Log
	 */
	private Log log = LogFactory.getLogger("UCM Error Log");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID = "uld.defaults.ucmerrorlog";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MESSAGE_STATUS = "uld.defaults.uldmessagesendflag";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Commented by Manaf for INT ULD510
		//String compCode = logonAttributes.getCompanyCode();
		UCMErrorLogSession ucmErrorLogSession = (UCMErrorLogSession) getScreenSession(
				MODULE, SCREENID);
		ucmErrorLogSession.removeAllAttributes();

		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes
				.getCompanyCode());
		Collection<OneTimeVO> statusValues = oneTimeCollection
				.get(MESSAGE_STATUS);

		ucmErrorLogSession
				.setMessageStatus((ArrayList<OneTimeVO>) statusValues);

		UCMErrorLogForm ucmErrorLogForm = (UCMErrorLogForm) invocationContext.screenModel;
		ucmErrorLogForm.setCarrierCode("");
		ucmErrorLogForm.setUcmerrorlogAirport("");
		ucmErrorLogForm.setFlightDate("");
		ucmErrorLogForm.setFlightNo("");
		ucmErrorLogForm.setCurrentPageNum("");
		ucmErrorLogForm.setDisplayPage("1");
		ucmErrorLogForm.setMsgType("");
		ucmErrorLogForm.setLastPageNumber("0");
		ucmErrorLogForm.setMsgType("OUT");
		ucmErrorLogForm.setMsgStatus("ALL");
		if (logonAttributes.isAirlineUser()) {
			ucmErrorLogForm.setUcmDisableStat("airline");
			ucmErrorLogForm.setCarrierCode(logonAttributes.getOwnAirlineCode());
		} else {
			ucmErrorLogForm.setUcmerrorlogAirport(logonAttributes
					.getAirportCode());
			ucmErrorLogForm.setUcmDisableStat("GHA");
		}

		invocationContext.target = SCREENLOAD_SUCCESS;

	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(MESSAGE_STATUS);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}

}
