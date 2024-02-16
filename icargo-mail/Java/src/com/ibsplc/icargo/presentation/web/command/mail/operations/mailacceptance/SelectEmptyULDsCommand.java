/*
 * SelectEmptyULDsCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class SelectEmptyULDsCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILOPERATIONS,SelectEmptyULDsCommand");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "select_success";	

		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("SelectEmptyULDsCommand","execute");
	    	
	    	MailAcceptanceForm mailAcceptanceForm = 
							(MailAcceptanceForm)invocationContext.screenModel;
	    	mailAcceptanceForm.setUldsPopupCloseFlag("");
	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	invocationContext.target = TARGET;

	}

}
