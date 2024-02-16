/*
 * ScreenloadCommand.java Created on Sep 15, 2010
 * 
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailexceptionreports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailExceptionsSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2414
 *
 */
public class ScreenloadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
	
	private static final String CLASS_NAME = "ScreenloadCommand";
	
	private static final String SCREENLOAD_SUCCESS="screenload_success";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.mailexceptionreports";
	private static final String KEY_EXCEPTIONREPORT_ONETIME ="mail.mra.defaults.exceptionreports";
	/**
	 * Execute method
	 *
	 * @param invocationContext
	 * @return void
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering(CLASS_NAME,"execute");	
		MailExceptionsSession mailExceptionsSession = getScreenSession(
				MODULE_NAME, SCREENID);
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_EXCEPTIONREPORT_ONETIME);
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors=handleDelegateException( e );
		}
		mailExceptionsSession.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		log.log(Log.FINE, "onetimeVOs",mailExceptionsSession.getOneTimeVOs());
		invocationContext.target=SCREENLOAD_SUCCESS;
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
		}
		log.exiting(CLASS_NAME,"execute");

	}

	
}
