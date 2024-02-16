/*
 * ClearULDAuditEnquiryCommand.java Created on 03 April,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.audit;

import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDAuditEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2883
 */

public class ClearULDAuditEnquiryCommand extends BaseCommand{
	
	
	private static final String MODULE_NAME = "shared.audit";
	private static final String SCREEN_ID = "shared.audit.auditenquiry";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	   /**
	   * execute method
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 	log.entering("ClearULDAuditEnquiryCommand", "Entry");
		 	ApplicationSessionImpl applicationSession = getApplicationSession();
		 	AuditEnquirySession auditEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
		 	auditEnquirySession.removeAllAttributes();
			ULDAuditEnquiryForm uldAuditEnquiryForm = 
				(ULDAuditEnquiryForm) invocationContext.screenModel;
			uldAuditEnquiryForm.setUldNumber("");
			uldAuditEnquiryForm.setUldSuffix("");
			uldAuditEnquiryForm.setLocation("");
			uldAuditEnquiryForm.setUldAirport("");
			invocationContext.target = SCREENLOAD_SUCCESS;
			log.exiting("ClearULDAuditEnquiryCommand", "Exit");
			
	 }

}
