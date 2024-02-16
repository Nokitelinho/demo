/*
 * PreAdviceCommand.java Created on Jul 1 2016
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PreAdviceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class PreAdviceCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAIL,PreAdviceCommand");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "preadvice_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	   
	   private static final String PREADVICE_SCREEN_ID = "mailtracking.defaults.preadvice";
	
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            							throws CommandInvocationException {
	    	
	    	log.entering("PreAdviceCommand","execute");
	    	
	    	MailAcceptanceForm mailAcceptanceForm = 
	    					(MailAcceptanceForm)invocationContext.screenModel;

	    	MailAcceptanceSession mailAcceptanceSession = 
	    								getScreenSession(MODULE_NAME,SCREEN_ID);
	       	PreAdviceSession preAdviceSession = 
	       					getScreenSession(MODULE_NAME,PREADVICE_SCREEN_ID);
	       	
	       	preAdviceSession.setFlightValidationVO(mailAcceptanceSession.getFlightValidationVO());
	       	preAdviceSession.setOperationalFlightVO(mailAcceptanceSession.getOperationalFlightVO());
	       	
	       	mailAcceptanceForm.setPreAdviceFlag("Y");
	       	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	       	invocationContext.target = TARGET;

	}

}
