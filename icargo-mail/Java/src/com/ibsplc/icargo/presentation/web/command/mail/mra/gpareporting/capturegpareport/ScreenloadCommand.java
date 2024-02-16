/*
 * ScreenloadCommand.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2257
 *  /*
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Feb 13, 2007 Meera Vijayan Initial draft
 * 
 * 
 * 
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "ScreenloadCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";

	private static final String MAILSTATUS_ONETIME = "mailtracking.mra.gpareporting.mailstatus";

	private static final String MAILCATEGORY_ONETIME = "mailtracking.defaults.mailcategory";

	private static final String HIGHESTNUM_ONETIME = "mailtracking.defaults.highestnumbermail";

	private static final String REGORINSIND_ONETIME = "mailtracking.defaults.registeredorinsuredcode";

	private static final String FROM_ACCOUNTING_SCREEN = "from_accountingScreen";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	private static final String SYS_PARA_ACC_ENTRY="cra.accounting.isaccountingenabled";
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		CaptureGPAReportSession session = (CaptureGPAReportSession) getScreenSession(
				MODULE_NAME, SCREENID);
		CaptureGPAReportForm form = (CaptureGPAReportForm) invocationContext.screenModel;

		Map<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		/**
		 * Setting one time values to session
		 */
		session.setMailStatus((ArrayList<OneTimeVO>) oneTimeValues
				.get(MAILSTATUS_ONETIME));

		session.setMailCategory((ArrayList<OneTimeVO>) oneTimeValues
				.get(MAILCATEGORY_ONETIME));

		session.setHeighestNum((ArrayList<OneTimeVO>) oneTimeValues
				.get(HIGHESTNUM_ONETIME));

		session.setRegOrInsInd((ArrayList<OneTimeVO>) oneTimeValues
				.get(REGORINSIND_ONETIME));

		if (!FROM_ACCOUNTING_SCREEN.equals(form.getFromScreen())) {

			session.setGPAReportingDetailsPage(null);

			// session.setGPAReportingDetailsVOs(null);

			session.setGPAReportingFilterVO(new GPAReportingFilterVO());

			session.setModifiedGPAReportingDetailsVOs(null);

			session.setSelectedGPAReportingDetailsVO(null);

			session.setIndexMap(null);

			form.setBasistype("");

			form.setGpaselect("");
			form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		} else {
			log.log(Log.FINE, "****inside ELSE*GPAFilterVo from sEssion",
					session.getGPAReportingFilterVO());
			populateFormFields(session.getGPAReportingFilterVO(), form);
			form.setScreenFlag(SCREEN_STATUS_DETAIL);
		}
//		 code for acc entry sys para starts
		Collection<String> systemParameterCodes = new ArrayList<String>();

		systemParameterCodes.add(SYS_PARA_ACC_ENTRY);

		Map<String, String> systemParameters = null;

		try {

			systemParameters = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			invocationContext.addAllError(handleDelegateException(e));
		}

		String accountingEnabled = (systemParameters.get(SYS_PARA_ACC_ENTRY));
		log.log(Log.INFO, "IS acc enabled--->", accountingEnabled);
		if("N".equals(accountingEnabled)){
			form.setAccEntryFlag("N");
		}else{
			form.setAccEntryFlag("Y");
		}
//		code for acc entry sys para ends
		invocationContext.target = ACTION_SUCCESS;

		log.exiting(CLASS_NAME, "execute");
	}

	/*
	 * method to populate the form fields
	 */
	private void populateFormFields(GPAReportingFilterVO filterVo,
			CaptureGPAReportForm form) {
		// all filter fileds are mandatory.
		log.log(Log.FINE, "****inside populate form fileds*******");
		form.setGpaCode(filterVo.getPoaCode());
		form.setFrmDate(filterVo.getReportingPeriodFrom()
				.toDisplayDateOnlyFormat());
		form.setToDate(filterVo.getReportingPeriodTo()
				.toDisplayDateOnlyFormat());
		if (filterVo.getProcessedFlag() != null
				&& filterVo.getProcessedFlag().length() > 0) {
			form.setAllProcessed(filterVo.getProcessedFlag());
		}
	}

	/**
	 * The method to obtain the onetime values. The method will call the
	 * sharedDefaults delegate and returns the map of requested onetimes
	 * 
	 * @return oneTimeValues
	 */
	private Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		log.entering("ScreenLoadCommand", "getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;

		try {
			log.log(Log.FINE, "****inside try*", getOneTimeParameterTypes());
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
		return oneTimeValues;
	}

	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(MAILSTATUS_ONETIME);
		parameterTypes.add(MAILCATEGORY_ONETIME);
		parameterTypes.add(HIGHESTNUM_ONETIME);
		parameterTypes.add(REGORINSIND_ONETIME);
		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}

}
