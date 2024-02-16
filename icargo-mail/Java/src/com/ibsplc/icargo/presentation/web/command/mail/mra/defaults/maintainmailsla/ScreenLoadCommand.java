/*
 * ScreenLoadCommand.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "ScreenLoadCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String SCREENLOAD_SUCCESS="screenload_success";	
	private static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String LINK_STATUS = "Y";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");		
		
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		MaintainMailSLASession maintainMailSLASession=getScreenSession(MODULE_NAME,SCREEN_ID);
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		maintainMailSLAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		maintainMailSLAForm.setLinkStatus(LINK_STATUS);
		maintainMailSLASession.removeAllAttributes();
		getOneTimeValues(maintainMailSLASession,logonAttributes);			
		
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}
	
	/**
	 * 
	 * @param maintainMailSLASession
	 * @param logonAttributes
	 */
	 
	public void getOneTimeValues(MaintainMailSLASession maintainMailSLASession,
			LogonAttributes logonAttributes){
		log.entering(CLASS_NAME,"getOneTimeValues");
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String,Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(MAIL_CATEGORY);
			
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
			maintainMailSLASession.setOneTimeValues((HashMap<String,Collection<OneTimeVO>>)hashMap);
		}
		log.exiting(CLASS_NAME,"getOneTimeValues");		
		
	}

}
