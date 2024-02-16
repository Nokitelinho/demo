/*
 * ScreenLoadULDIntMvtCommand.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldintmvthistory;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDIntMvtHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDIntMvtHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2412
 * This command class is invoked on the start up of the 
	 * Internal ULD Movement History screen
 */
public class ScreenLoadULDIntMvtCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD INTERNAL MOVEMENT HISTORY");

	private static final String SCREENID = "uld.defaults.misc.uldintmvthistory";

	private static final String MODULE = "uld.defaults";
	//added by a-3045 for uld378 on 30Apr08
	private static final String BLANK = "";
	//ends by a-3045 on 30Apr08
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String CONTENT_ONETIME = "uld.defaults.contentcodes";

	
	 /**
	 * @param invocationContext	
	 * @return
	 * @throws CommandInvocationException 
	 */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.INFO, "ENTERED ScreenLoadULDIntMvtHistoryCommand");
		// Getting One Time values
		HashMap<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		ULDIntMvtHistoryForm form = (ULDIntMvtHistoryForm) invocationContext.screenModel;
		ULDIntMvtHistorySession session = getScreenSession(MODULE, SCREENID);
		session.setIntULDMvtDetails(null);
		session.setULDIntMvtHistoryFilterVO(null);
		session.setOneTimeValues(oneTimeValues);
		form.setAfterList(BLANK);
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

	/**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		log.entering("ScreenLoadCommand", "getOneTimeValues");
		// Obtain the logonAttributes

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		try {
			log.log(Log.FINE, "****inside try******",
					getOneTimeParameterTypes());
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand", "getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
	}

	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(CONTENT_ONETIME);
		return parameterTypes;
	}

}
