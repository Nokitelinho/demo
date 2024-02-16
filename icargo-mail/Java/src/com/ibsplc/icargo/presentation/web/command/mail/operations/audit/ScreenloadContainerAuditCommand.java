/*
 * ScreenloadContainerAuditCommand.java Created on Jul 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.audit;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadContainerAuditCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String SUCCESS = "screenload_success";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.entering("ScreenloadContainerAuditCommand","execute");  
    	/*ContainerAuditForm containerAuditForm =
				(ContainerAuditForm)invocationContext.screenModel;
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();*/
    	
    		    invocationContext.target = SUCCESS;
	    
	    log.exiting("ScreenloadContainerAuditCommand","execute");  

	}

}
