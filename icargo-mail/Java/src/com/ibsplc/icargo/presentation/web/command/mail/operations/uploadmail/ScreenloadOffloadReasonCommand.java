/*
 * ScreenloadOffloadReasonCommand.java Created on Sept 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2107
 *
 */
public class ScreenloadOffloadReasonCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	private static final String TARGET = "success";
	private static final String OFFLOAD_REASONCODE = "mailtracking.defaults.offload.reasoncode";
	
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc) 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("ScreenloadOffloadReasonCommand","execute");
	
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
	 	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	/**
		 * for taking one times
		 */
		Map oneTimeValues = null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(OFFLOAD_REASONCODE);
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "  the oneTimeHashMap after server call is ",
				oneTimeValues);
		uploadMailSession.setOffloadOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		invocationContext.target = TARGET;
		log.exiting("ScreenloadOffloadReasonCommand","execute");
		
	}
	
}
