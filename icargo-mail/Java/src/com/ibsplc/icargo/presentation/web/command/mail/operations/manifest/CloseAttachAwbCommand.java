/*
 * CloseAttachAwbCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class CloseAttachAwbCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "close_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("CloseAttachAwbCommand","execute");
    	  
    	MailManifestForm mailManifestForm = 
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		
		mailManifestSession.setAWBDetailVO(null);
		mailManifestSession.setContainerDetailsVO(null);
		
		mailManifestForm.setScreenStatus("CLOSE");
					
		invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("CloseAttachAwbCommand","execute");
    	
    }
    
}
