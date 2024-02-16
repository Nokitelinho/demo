/*
 * ScreenLoadLUCMessageCommand.java Created on Aug 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ScreenLoadLUCMessageCommand  extends BaseCommand {
    
    /**
	 * Logger for ScreenLoadLUCMessageCommand
	 */
	private Log log = LogFactory.getLogger("LUCMessage");
	
	/**
	 * target String if success
	 */
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
       
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
   
}
