/*
 * ScreenLoadCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailonhandlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MainOnHandListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailOnHandListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends BaseCommand{
	  private Log log = LogFactory.getLogger("MAILOPERATIONS");
	   private static final String TARGET = "screenload_success";
	   private static final String FLTTYPE_ONETIME = "mailtracking.defaults.operationtype";
	   private static final String FLTTYPE_ONETIME_SEARCHMODE = "mailtracking.defaults.containersearchmode";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailonhandlist";	
	
	
	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		
		
		log.entering("ScreenLoadCommand","execute");
		MailOnHandListForm mailHandlistform =(MailOnHandListForm)invocationcontext.screenModel;
		MainOnHandListSession mailonlistsession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    
    	
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
    	mailonlistsession.setOneTimeValues(oneTimeValues);
		
    	mailHandlistform.setAirport(logonAttributes.getAirportCode());
    	
    	Page<MailOnHandDetailsVO> MailOnHandDetailsVOs = null; 
    	 
		SearchContainerFilterVO searchContainerFilterVO = 
									new SearchContainerFilterVO();
		mailonlistsession.setSearchContainerFilterVO(searchContainerFilterVO);
		    mailonlistsession.setMailOnHandDetailsVO(MailOnHandDetailsVOs);
    
		invocationcontext.target = TARGET;
	}

	
	
	
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		}catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	
	 private Collection<String> getOneTimeParameterTypes() {
	    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
	    	ArrayList<String> parameterTypes = new ArrayList<String>();
	    	parameterTypes.add(FLTTYPE_ONETIME);
	    	parameterTypes.add(FLTTYPE_ONETIME_SEARCHMODE);   	
	    	
	    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
	    	return parameterTypes;    	
	    }
}
