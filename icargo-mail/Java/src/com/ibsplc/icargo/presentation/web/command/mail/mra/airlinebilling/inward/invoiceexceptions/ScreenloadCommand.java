/*
 * ScreenloadCommand.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.invoiceexceptions;


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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Sreekanth.V.G
 * Command Class  for InvoiceExceptions  screen.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 20, 2007   Sreekanth.V.G   			Initial draft
 *  
 */
public class ScreenloadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	private static final String CLASS_NAME = "ScreenloadCommand";
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	/**
	 * screen id
	 *
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	/**
	 * SCREENLOAD_SUCCESS Action
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	/**
	 * for one time list
	 */
	private static final String EXCEPTION_STATUS = "mailtracking.mra.exceptionstatus";
	
	private static final String MEMO_STATUS = "mailtracking.mra.memostatus";
	/**
	 *
	 * Execute method	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		InvoiceExceptionsForm form =
			(InvoiceExceptionsForm)invocationContext.screenModel;
		InvoiceExceptionsSession  session=	(InvoiceExceptionsSession)getScreenSession(
				MODULE_NAME, SCREENID);
		/**
		 * session is removed here
		 */
		session.removeExceptionInInvoiceVOs();
		session.removeOneTimeVOs();
		session.removeExceptionInInvoiceFilterVO();
		/**
		 * Getting log on attribute here
		 */
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		/**
		 * one time is setting here
		 */
		getOneTimeValues(session,logonAttributes);
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
	/**
	 * Method to get OneTimeValues into session
	 * @param maintainRateAuditSession
	 * @param logonAttributes
	 * @param session
	 */
	void getOneTimeValues(InvoiceExceptionsSession session,
			LogonAttributes logonAttributes){
		log.entering(CLASS_NAME,"getOneTimeValues");
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String,Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(EXCEPTION_STATUS);
		oneTimeList.add(MEMO_STATUS);		
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
