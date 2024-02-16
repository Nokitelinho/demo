/*
 * ScreenLoadCommand.java Created on Apr 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.listloyalty;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListLoyaltyForm;

/**
 * @author A-1862
 *
 */
public class ScreenLoadCommand  extends BaseCommand {

	
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
    private static final String SCREENID = "customermanagement.defaults.listloyalty";
    private static final String MODULE = "customermanagement.defaults";
   
 /**
  * @param invocationContext
  * @throws CommandInvocationException
  */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
       
		
		ListLoyaltyForm listLoyaltyForm = 
			(ListLoyaltyForm) invocationContext.screenModel;
		ListLoyaltySession listLoyaltySession = 
			(ListLoyaltySession)getScreenSession(MODULE,SCREENID);
		
		listLoyaltySession.removeAllAttributes();
		listLoyaltyForm.setFromList("fromlist");
		//listLoyaltyForm.setScreenStatusFlag("SCREENLOAD");
	
		
		
        invocationContext.target=SCREENLOAD_SUCCESS;
    }
 
    
	

}
