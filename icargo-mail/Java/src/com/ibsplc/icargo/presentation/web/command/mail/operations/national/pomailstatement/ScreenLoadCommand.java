/*
 * ScreenLoadCommand.java Created on Feb 01 , 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.pomailstatement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.POMailStatementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.POMailStatementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4777
 *
 */

public class ScreenLoadCommand extends BaseCommand{
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");		
	   
	   private static final String TARGET = "screenload_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.national.mailstatement"; 
	   private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	   
	    		 
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	log.entering("ScreenLoadCommand","execute");	
	    	
	    	POMailStatementForm poMailStatementForm =(POMailStatementForm)invocationContext.screenModel;
			POMailStatementSession poMailStatementSession = getScreenSession(MODULE_NAME,SCREEN_ID);
			ApplicationSessionImpl applicationSession = getApplicationSession();
			   
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			Collection<String> fieldTypes 		= new ArrayList<String>();
			fieldTypes.add(CATEGORY);
			try {
				oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE, "*****in the exception");
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
	    	
			poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
			poMailStatementSession.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
			poMailStatementSession.setSelectedPOMailStatementVOs(null);
			invocationContext.target = TARGET;
	       	
	    	log.exiting("ScreenLoadCommand","execute");
	    	
	    }
	       

	

}