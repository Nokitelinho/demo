/*
 * GenerateMailManifestCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class GenerateMailManifestCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ScreenloadMailManifestCommand","execute");

    	/*MailManifestForm mailManifestForm =
    		(MailManifestForm)invocationContext.screenModel;*/
    	MailManifestSession mailManifestSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		//LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		Collection<String> printTypes = new ArrayList<String>();
		printTypes.add("Mailbag level");
		printTypes.add("AWB level");
		printTypes.add("Destn Category level");
		printTypes.add("DSN/Mailbag level");
		mailManifestSession.setPrintTypes(printTypes);
		
    	invocationContext.target = TARGET;

    	log.exiting("ScreenloadMailManifestCommand","execute");

    }

}
