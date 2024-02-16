/*
 * ScreenloadCommand.java Created on Mar 6, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.report;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.GPAReportsSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2245
 * 
 * 
 * Revision History
 * 
 * Version      Date           		Author              Description
 * 
 *  0.1         Feb 13, 2007  		A-2245				Initial draft
 *  
 */
public class ScreenloadCommand extends BaseCommand {
	/*
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA GPAReports");
	private static final String CLASS_NAME = "ScreenloadCommand";
	/*
	 * MODULE_NAME and SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.gpareport";
		
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/*
	 * String ONE TIME CODES
	 */
	private static final String KEY_EXCEPTIONCODE = "mailtracking.mra.gpareporting.exceptioncodes";
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		GPAReportsSession gpaReportsSession = getScreenSession(MODULE_NAME,SCREENID);
		/*
		 * getting OneTimeValues
		 */
		getOneTimeValues(gpaReportsSession);
		invocationContext.target = ACTION_SUCCESS;
		
		log.exiting(CLASS_NAME, "execute");
	}	
	/**
	 * method to getOneTimeValues
	 * @param gpaReportsSession
	 */
	private void getOneTimeValues(GPAReportsSession gpaReportsSession){
		log.entering(CLASS_NAME,"getOneTimeValues");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    Map<String,Collection<OneTimeVO>> hashMap = null;
	    Collection<String> oneTimeList = new ArrayList<String>();
	    oneTimeList.add(KEY_EXCEPTIONCODE);
		try {
			log.log(Log.FINEST,"***********************************inside try");
			hashMap = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
			log.log(Log.FINEST, "hash map*****************************",
					hashMap);

		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
			log.log(Log.SEVERE, "status fetch exception");
		}
		if(hashMap!=null){
			gpaReportsSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)hashMap);
			log.log(Log.INFO, "OneTimeValues(after fetching):>",
					gpaReportsSession.getOneTimeVOs());
		}
		log.exiting(CLASS_NAME,"getOneTimeValues");
	}
	
}
