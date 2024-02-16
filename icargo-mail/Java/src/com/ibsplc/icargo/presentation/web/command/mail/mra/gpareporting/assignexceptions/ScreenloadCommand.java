/*
 * ScreenloadCommand.java Created on Feb 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.assignexceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.AssignExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 *  
 * @author A-2257
 * @author A-2245
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1         Feb 13, 2007   Meera Vijayan			Initial draft
 *  
 *  0.2			Feb 20,2007		Divya Sara Ponnat		Revised version
 *  
 */
public class ScreenloadCommand extends BaseCommand {
	/*
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	/*
	 * String constants for CLASS_NAME
	 */
	private static final String CLASS_NAME = "ScreenloadCommand";
	/*
	 * String constants for MODULE_NAME,SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.assignexceptions";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/*
	 * String ONE TIME CODES
	 */
	private static final String EXCEPTION_CODE = "mailtracking.mra.gpareporting.exceptioncodes";
	
	private static final String CCA_STATUS = "mra.defaults.ccastatus";
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
		/*
		 * assignExceptionsForm defined
		 */
		AssignExceptionsForm assignExceptionsForm = 
							(AssignExceptionsForm)invocationContext.screenModel;
		/*
		 * logonAttributes defined
		 */
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		/*
		 * localdate defined
		 */
		LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		String localDateString = TimeConvertor.toStringFormat(localDate,logonAttributes.getDateFormat());
		assignExceptionsForm.setFromDate(localDateString);
		assignExceptionsForm.setToDate(localDateString);
		/*
		 * assignExceptionsSession defined
		 */
		AssignExceptionsSession assignExceptionsSession = getScreenSession(MODULE_NAME,SCREENID);
		assignExceptionsSession.removeAllAttributes();
		/*
		 * getting OneTimeValues
		 */
		getOneTimeValues(assignExceptionsSession);
//		assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}	
	/**
	 * method to getOneTimeValues
	 * @param assignExceptionsSession
	 */
	private void getOneTimeValues(AssignExceptionsSession assignExceptionsSession){
		log.entering(CLASS_NAME,"getOneTimeValues");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    Map<String,Collection<OneTimeVO>> hashMap = null;
	    Collection<String> oneTimeList = new ArrayList<String>();
	    oneTimeList.add(EXCEPTION_CODE);
	    oneTimeList.add(CCA_STATUS);
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
			assignExceptionsSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)hashMap);
			log.log(Log.INFO, "OneTimeValues(after fetching):>",
					assignExceptionsSession.getOneTimeVOs());
		}
		log.exiting(CLASS_NAME,"getOneTimeValues");
	}
}
