/*
 * SaveRepairInvoiceCommand.java Created on Aug 1, 2005
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
 * This command class is used to save the details of the specified stock 
 * 
 * @author A-1347
 */
public class SaveRepairInvoiceCommand extends BaseCommand {
    
    private static final String SAVE_SUCCESS = "save_success";
    private static final String SAVE_FAILURE = "save_failure";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        
        
    }
}
