/*
 * CancelDamageCommand.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival.capturedamage;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * The Class CancelDamageCommand.
 *
 * @author A-5991
 */
public class CancelDamageCommand extends BaseCommand {
	
   /** The log. */
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   /** The Constant TARGET. */
   private static final String TARGET = "cancel_success";   
   
   /** The Constant MODULE_NAME. */
   private static final String MODULE_NAME = "mail.operations";	
   
   /** The Constant SCREEN_ID. */
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
   
	 /**
 	 * This method overrides the execute method of BaseComand class.
 	 *
 	 * @param invocationContext the invocation context
 	 * @throws CommandInvocationException the command invocation exception
 	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("CancelDamageCommand", "execute");
		MailArrivalForm mailArrivalForm = (MailArrivalForm) invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		mailArrivalSession.setDamagedMailbagVOs(null);
		mailArrivalForm.setSelectedMailBag("CLOSE");
		invocationContext.target = TARGET;
		log.exiting("CancelDamageCommand", "execute");
	}
	
}