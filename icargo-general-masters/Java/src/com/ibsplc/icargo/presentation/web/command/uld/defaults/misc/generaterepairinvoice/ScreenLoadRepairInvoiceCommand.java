/*
 * ScreenLoadRepairInvoiceCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.generaterepairinvoice;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * This command class is invoked on the start up of the MaintainAccessoriesStock screen
 * 
 * @author A-1347
 */
public class ScreenLoadRepairInvoiceCommand extends BaseCommand {
    
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String SCREENLOAD_FAILURE = "screenload_failure";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        
        
    }
}
