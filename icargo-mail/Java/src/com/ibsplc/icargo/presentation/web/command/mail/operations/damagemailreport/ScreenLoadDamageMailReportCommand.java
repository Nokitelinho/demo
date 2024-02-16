/*
 * ScreenLoadDamageMailReportCommand.java Created on JUL 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.damagemailreport;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamageMailReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamageMailReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenLoadDamageMailReportCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");	

	/**
	 * TARGET
	 */
	private static final String TARGET = "screenload_success";   
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.damagemailreport"; 

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {    	
		log.entering("ScreenLoadDamageMailReportCommand","execute");    	  
		DamageMailReportForm damageMailReportForm = (DamageMailReportForm)invocationContext.screenModel;
		DamageMailReportSession damageMailReportSession = getScreenSession(MODULE_NAME,SCREEN_ID);    	
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;	
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			Collection<OneTimeVO> damageCodes = oneTimes.get("mailtracking.defaults.return.reasoncode");
			damageMailReportSession.setOneTimeDamageCodes(damageCodes);
		}	
		Map<String, Collection<OneTimeVO>> subClassGroup = null;

		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.mailsubclassgroup");
			subClassGroup = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		if(subClassGroup!=null){
			Collection<OneTimeVO> subClassGroupCodes = subClassGroup.get("mailtracking.defaults.mailsubclassgroup");
			damageMailReportSession.setSubClassGroups(subClassGroupCodes);
		}
		log.log(Log.FINE,"SubClassGroups", damageMailReportSession.getSubClassGroups());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		damageMailReportForm.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		damageMailReportForm.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		damageMailReportForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		damageMailReportForm.setFlightCarrierCode("");
		damageMailReportForm.setFlightNumber("");
		damageMailReportForm.setFlightDate("");
		invocationContext.target = TARGET;

		log.exiting("ScreenLoadDamageMailReportCommand","execute");

	}
	
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode){
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;	
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.return.reasoncode");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
			log.log(Log.FINE, "findOneTimeDescription", oneTimes);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
}
