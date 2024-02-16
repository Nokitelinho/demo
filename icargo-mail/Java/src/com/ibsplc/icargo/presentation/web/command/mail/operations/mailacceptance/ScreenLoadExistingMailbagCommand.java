/*
 * ScreenLoadExistingMailbagCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenLoadExistingMailbagCommand extends BaseCommand{

private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	/**
	 * TARGET
	 */
	private static final String TARGET = "screenload_success";
	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * */
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
	
log.entering("ScreenloadAcceptMailCommand","execute");
		
		MailAcceptanceForm mailAcceptanceForm = 
			(MailAcceptanceForm)invocationContext.screenModel;
		MailAcceptanceSession mailAcceptanceSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		mailAcceptanceForm.setCurrentStation(logonAttributes.getAirportCode());
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			Collection<OneTimeVO> statusVOs = oneTimes.get("mailtracking.defaults.flightstatus");
			mailAcceptanceSession.setOneTimeFlightStatus(statusVOs);
			}
		
		invocationContext.target = TARGET;
		log.exiting("ScreenLoadExistingMailbagCommand","execute");
		
	
	}

		/**
		 * This method will be invoked at the time of screen load
		 * @param companyCode
		 * @return Map<String, Collection<OneTimeVO>>
		 */
		public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
			Map<String, Collection<OneTimeVO>> oneTimes = null;
			Collection<ErrorVO> errors = null;
			try{
				Collection<String> fieldValues = new ArrayList<String>();
				fieldValues.add("mailtracking.defaults.flightstatus");
				oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
			}
			return oneTimes;
		}
		
}
