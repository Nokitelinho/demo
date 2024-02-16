/*
 * ScreenLoadCommand.java Created on Aug 01, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.audit;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2563
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD AUDIT");
	
	
	private static final String SCREENID_AUDIT = "shared.audit.auditenquiry";

	private static final String MODULE_NAME_AUD = "shared.audit";
	
	
	private static final String SUCCESS = "action_success";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.entering("ScreenLoadCommand","execute");  
    	AuditEnquirySession auditEnquirySession = getScreenSession(MODULE_NAME_AUD,
				SCREENID_AUDIT);
    	auditEnquirySession.removeAllAttributes();
    	
	    invocationContext.target = SUCCESS;
	    
	    log.exiting("ScreenLoadCommand","execute");  

	}

}
