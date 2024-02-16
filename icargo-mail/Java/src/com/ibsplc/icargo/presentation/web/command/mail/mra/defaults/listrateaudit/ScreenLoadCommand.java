/*
 * ScreenLoadCommand.java created on July 15,2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateaudit;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class ScreenLoadCommand  extends BaseCommand{
	
	
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listrateaudit";
	
	private static final String RATEAUDIT_SCREENID =
		"mailtracking.mra.defaults.rateauditdetails";
	

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String DSNSTAUS_ONETIME = "mailtracking.mra.defaults.rateaudit.status";
	
	private static final String MAILCATEGORY_ONETIME="mailtracking.defaults.mailcategory";
	private static final String BLANK = "";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListRateAuditForm listRateAuditForm=(ListRateAuditForm)invocationContext.screenModel;
		ListRateAuditSession listRateAuditSession = getScreenSession(
				MODULE_NAME, SCREENID);
		RateAuditDetailsSession rateAuditDetailsSession=getScreenSession(MODULE_NAME,RATEAUDIT_SCREENID);
		clearForm(listRateAuditForm);
		listRateAuditForm.setCarrierCode("NZ");
		listRateAuditSession.removeRateAuditVOs();
		listRateAuditSession.removeRateAuditFilterVO();
		listRateAuditSession.setFromScreen(null);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String,Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		oneTimeList.add(DSNSTAUS_ONETIME);
		
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
			listRateAuditSession.setOneTimeValues((HashMap<String,Collection<OneTimeVO>>)hashMap);
		}
		 
		
		 
		 /*
			 * Getting OneTime values
			 */
		 
		 
		Collection<ErrorVO> errors = null;
			try{
				
				oneTimeList.add(MAILCATEGORY_ONETIME);			
				hashMap = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList) ;
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
			}			
			if(hashMap!=null){
				Collection<OneTimeVO> catVOs = hashMap.get("mailtracking.defaults.mailcategory");
				rateAuditDetailsSession.setOneTimeCatVOs(catVOs);
			}
		 
		log.exiting(CLASS_NAME,"getOneTimeValues");	
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	private void clearForm(ListRateAuditForm form){
		
		form.setDsn(BLANK);
		form.setDsnDate(BLANK);
		form.setFlightDate(BLANK);
		form.setCarrierCode(BLANK);
		form.setFlightNo(BLANK);
		form.setFromDate(BLANK);
		form.setToDate(BLANK);
		form.setGpaCode(BLANK);
		form.setSubClass(BLANK);
		
		
	}

}
