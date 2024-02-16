/*
 * ScreenLoadCommand.java Created on Aug 5, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.audit;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;

/**
 * @author A-5255 
 * @version	0.1, Aug 5, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Aug 5, 2015	     A-5255		First draft
 */

public class ScreenLoadCommand extends BaseCommand{
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

    private static final String SCREENID = 
    	"mailtracking.mra.defaults.billingmatrixaudit";
    private static final String SCREEN_SUCCESS = 
        	"screen_success";
	/**
	  * @author A-5255
	  * @param arg0
	  * @throws CommandInvocationException
	  * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	  */
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		 AuditEnquirySession auditEnquirySession = (AuditEnquirySession)getScreenSession(
	    	      "shared.audit", "shared.audit.auditenquiry");
		 auditEnquirySession.removeAllAttributes();
		invocationContext.target = SCREEN_SUCCESS;
	}

}
