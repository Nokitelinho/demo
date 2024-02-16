/*
 * GPAReportScreenLoadCommand.java Created on Mar 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingReportsSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class GPAReportScreenLoadCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.gpabillingreports";
    
    private static final String SCREENLOAD_SUCCESS ="screenload_success";
    
    private static final String CLASS_NAME = "ScreenLoadCommand";
    
    private static final String KEY_BILLING_STATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
    private static final String KEY_GPABILLING_REPORTS_ONETIME = "mail.mra.gpabilling.gpabillingreports";
    /**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException{

    	Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	GPABillingReportsSession session=(GPABillingReportsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_STATUS_ONETIME);
		oneTimeActiveStatusList.add(KEY_GPABILLING_REPORTS_ONETIME);
		
		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		if(errors!=null && errors.size()>0){
	    	invocationContext.addAllError(errors);
	    	}
    	invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}