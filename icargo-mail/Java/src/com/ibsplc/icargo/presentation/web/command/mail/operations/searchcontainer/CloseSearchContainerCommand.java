
/*
 * CloseSearchContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;

public class CloseSearchContainerCommand extends BaseCommand  {

	
	 private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
	   private static final String SCREEN_ID_MHL = "mailtracking.defaults.mailonhandlist";
	   private static final String CLOSE_SUCCESS = "close_success";
	   private static final String CLOSE_FAILURE = "close_failure";
	   private static final String BLANK="";
	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationcontext.screenModel;
		SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		if("Mailonhandlist".equalsIgnoreCase(searchContainerSession.getParentScreen())){
			searchContainerForm.setFromScreen("");
			invocationcontext.target = CLOSE_SUCCESS;
			
		}
		else
		{
			invocationcontext.target = CLOSE_FAILURE;
		}
		
	}

}
