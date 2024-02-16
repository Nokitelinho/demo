/*
 * ClearCommand.java Created on Apr 25, 2006
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
 * This command class is used to clear Loyalty
 * @author A-1862
 */
public class ClearCommand extends BaseCommand {
	
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.listloyalty";
    
    private static final String CLEAR_SUCCESS = "clear_success";
    
    
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		
		ListLoyaltyForm listLoyaltyForm = 
			(ListLoyaltyForm) invocationContext.screenModel;
		ListLoyaltySession listLoyaltySession = 
			(ListLoyaltySession)getScreenSession(MODULE,SCREENID);
		
		clearForm(listLoyaltyForm);
		listLoyaltySession.removeAllAttributes();
		
		listLoyaltyForm.setScreenStatusFlag("SCREENLOAD");
    	
    	
    	invocationContext.target = CLEAR_SUCCESS;
        
    }
   
    private void clearForm(ListLoyaltyForm listLoyaltyForm) {
		
    	listLoyaltyForm.setLoyaltyName("");
    	listLoyaltyForm.setFromDate("");
    	listLoyaltyForm.setToDate("");
		
		
		
	
	}


}
