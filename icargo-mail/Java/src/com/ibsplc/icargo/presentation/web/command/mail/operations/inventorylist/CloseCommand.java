/*
 * CloseCommand.java Created on Jul 1 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.inventorylist;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;

/**
 * @author A-5991
 *
 */
public class CloseCommand extends BaseCommand {
	//private Log log = LogFactory.getLogger("MAILTRACKING");
	
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "list_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		
		MailArrivalSession mailArrivalSession =getScreenSession(MODULE_NAME,SCREEN_ID);
		
		if(mailArrivalSession.getFromScreen()!=null && ("MailArrival").equals(mailArrivalSession.getFromScreen())){
			
			mailArrivalSession.setFromScreen(null);
			invocationContext.target="MailArrival";
		}else{
			invocationContext.target=TARGET;
		}
		
		
		
	}

}
