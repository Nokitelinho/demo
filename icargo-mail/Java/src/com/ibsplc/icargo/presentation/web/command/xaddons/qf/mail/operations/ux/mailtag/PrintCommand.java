/*
 * PrintCommand.java Created on Oct 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.qf.mail.operations.ux.mailtag;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author 204569
 *
 */
public class PrintCommand extends AbstractPrintCommand {
	
private static final Log LOGGER = LogFactory.getLogger("OPERATIONS PrintCommand");
	
   /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
		public void execute(InvocationContext invocationContext) throws CommandInvocationException {

			LOGGER.entering("PrintCommand", "execute");

			
			invocationContext.target = "list_mail_tag_label_print";
			LOGGER.exiting("PrintCommand", "execute");
		}
 
    
}
