/*
 * ClearLoyaltyProgrammeLOVCommand.java Created on Apr 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.LoyaltyProgrammeLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1862
 *
 */
public class ClearLoyaltyProgrammeLOVCommand extends BaseCommand {

    private static final String CLEAR_SUCCESS = "clear_success";
    private Log log = LogFactory.getLogger("DeleteParameterCommand");
   
    private static final String MODULE = "customermanagement.defaults";
	private static final String SCREENID ="customermanagement.defaults.maintainloyalty";
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	    	
    	LoyaltyProgrammeLovForm loyaltyProgrammeLovForm = 
			(LoyaltyProgrammeLovForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		
		
		loyaltyProgrammeLovForm.setLoyaltyProgramDescription("");
		loyaltyProgrammeLovForm.setLoyaltyProgramName("");	
		maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);
    	
		log.log(Log.FINE, "\n\n\n\n cleared" 	);
    	invocationContext.target=CLEAR_SUCCESS;

    }



}
