/*
 * ExceptionCommand.java Created on Mar 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.InvoiceExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2408
 *
 */
public class ExceptionCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ExceptionCommand");

	private static final String CLASS_NAME = "ExceptionCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String MODULENAME="mailtracking.mra";
	private static final String SCREENID="mailtracking.mra.airlinebilling.inward.invoiceexceptions";
	private static final String EXCEPTION_STATUS = "mailtracking.mra.exceptionstatus";
	private static final String MEMO_STATUS = "mailtracking.mra.memostatus";
	
	//private static final String MODULENAME = "mailtracking.mra.airlinebilling";
	//private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		session.setPresentScreenStatus(form.getScreenStatus());
		
//		for setting onetimes in invoice exp
		InvoiceExceptionsSession  expsession=	(InvoiceExceptionsSession)getScreenSession(
				   MODULENAME, SCREENID);
			LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
			getOneTimeValues(expsession,logonAttributes);
		
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * Method to get OneTimeValues into session
	 * @param maintainRateAuditSession
	 * @param logonAttributes
	 * @param session
	 */
	void getOneTimeValues(InvoiceExceptionsSession expsession,
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
			expsession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)hashMap);
		}
		log.exiting(CLASS_NAME,"getOneTimeValues");		
		
	}
	
}
