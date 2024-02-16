/*
 * ScreenLoadCommand.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listmailcontracts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailContractsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1946
 *
 */
public class ScreenLoadCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listmailcontracts";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String AGREEMENTYPE_ONETIME = "mailtracking.mra.agreementType";
	
	private static final String AGREEMENSTAUS_ONETIME ="mailtracking.mra.agreementStatus";
	
	private static final String BLANK = "";
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListMailContractsSession  session=	(ListMailContractsSession)getScreenSession(
				MODULE_NAME, SCREENID);
		ListMailContractsForm form=(ListMailContractsForm)invocationContext.screenModel;
		/**
		 * Getting log on attribute here
		 */
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		session.removeMailContractVOs();
		form.setAgreementStatus(BLANK);
		form.setAgreementType(BLANK);
		form.setAirlineCode(BLANK);
		form.setContractDate(BLANK);
		form.setContractRefNo(BLANK);
		form.setFromDate(BLANK);
		form.setPaCode(BLANK);
		form.setToDate(BLANK);
		form.setVersionNumberFilter("LATEST");
		/**
		 * one time is setting here
		 */
		getOneTimeValues(session,logonAttributes);
		
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * Method to get OneTimeValues into session
	 * @param maintainRateAuditSession
	 * @param logonAttributes
	 * @param session
	 */
	void getOneTimeValues(ListMailContractsSession session,
			LogonAttributes logonAttributes){
		log.entering(CLASS_NAME,"getOneTimeValues");
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String,Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(AGREEMENTYPE_ONETIME);
		oneTimeList.add(AGREEMENSTAUS_ONETIME);		
		try {
			log.log(Log.FINEST,"***********************************inside try");
			hashMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
			log.log(Log.FINEST, "hash map*****************************",
					hashMap);
			
		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
			log.log(Log.SEVERE, "status fetch exception");
		}
		if(hashMap!=null){
			session.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)hashMap);
		}
		log.exiting(CLASS_NAME,"getOneTimeValues");		
		
	}
}
