/* CloseULDMovementHistoryCommand.java Created on Feb 2, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;

/**
 * @author A-2122
 *
 */
public class CloseULDMovementHistoryCommand extends BaseCommand {
	private static final String CLOSE_SUCCESS = "close_success";
	
	private static final String CLOSE_MAINTAIN = "close_maintain";
	
	private static final String CLOSE_LISTULD="close_listuld";
	
	private static final String BLANK = "";
	/**
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		ListULDMovementForm listULDMovementForm = (ListULDMovementForm) invocationContext.screenModel;
    	
    	 if(("frommaintainuld").equals(listULDMovementForm.getScreenloadstatus())) {
    		 	listULDMovementForm.setPageUrl(BLANK);
        		 invocationContext.target=CLOSE_MAINTAIN;
		} else if (("frommaintainuldlistdetail").equals(listULDMovementForm.getScreenloadstatus())
				|| ("listdetail").equals(listULDMovementForm.getScreenloadstatus())
				|| ("ListDamageReport").equals(listULDMovementForm.getScreenloadstatus())
				|| ("ListRepairReport").equals(listULDMovementForm.getScreenloadstatus())) {
    		 invocationContext.target=CLOSE_LISTULD;
		} else {
    		 invocationContext.target=CLOSE_SUCCESS;
    	 }
    }
    }