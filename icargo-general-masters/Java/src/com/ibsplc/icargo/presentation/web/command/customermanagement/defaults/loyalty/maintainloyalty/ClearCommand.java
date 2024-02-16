/*
 * ClearCommand.java Created on Apr 12, 2006
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
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;

/**
 * This command class is used to clear Loyalty
 * @author A-1862
 */
public class ClearCommand extends BaseCommand {
	
	
	private static final String MODULE = "customermanagement.defaults";
	
	
	private static final String SCREENID =
		"customermanagement.defaults.maintainloyalty";
    
    private static final String CLEAR_SUCCESS = "clear_success";
    
    
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
 		
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		
		clearForm(maintainLoyaltyForm);
		maintainLoyaltySession.setLoyaltyProgrammeVO(null);
		maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);	
    	//maintainLoyaltyForm.setScreenStatusFlag("SCREENLOAD");
		maintainLoyaltyForm.setScreenStatusValue("SCREENLOAD");
    	maintainLoyaltySession.setAttributeValue(null);
		maintainLoyaltySession.setUnitValue(null);
		maintainLoyaltySession.setAmountValue(null);
		maintainLoyaltySession.setPointsValue(null);
		maintainLoyaltySession.setParameterVOsForDisplay(null);
		maintainLoyaltySession.setParameterVOsForLOV(null);
		maintainLoyaltyForm.setCloseWindow(false);
    	
    	invocationContext.target = CLEAR_SUCCESS;
        
    }
   
    private void clearForm(MaintainLoyaltyForm maintainLoyaltyForm) {
		
    	maintainLoyaltyForm.setLoyaltyName("");
		maintainLoyaltyForm.setLoyaltyDesc("");
		maintainLoyaltyForm.setEntryPoints("");
		maintainLoyaltyForm.setFromDate("");
		maintainLoyaltyForm.setToDate("");
		maintainLoyaltyForm.setExpiryPeriod("");
		maintainLoyaltyForm.setExpiryPeriodValue("");
		maintainLoyaltyForm.setStatus("");
		maintainLoyaltyForm.setStatusNew("");
		maintainLoyaltyForm.setAttribute("");
		maintainLoyaltyForm.setUnit("");
		maintainLoyaltyForm.setAmount("");
		maintainLoyaltyForm.setPoints("");
		
		
	
	}


}
