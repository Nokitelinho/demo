/*
 * CloseCommand.java Created on Mar 27, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class CloseCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";

    private static final String CLOSE_SUCCESS ="close_success";

   private static final String CLASS_NAME = "ClearCommand";

   /**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException{
    	Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	session.removeAllAttributes();

    	invocationContext.target = CLOSE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

    }
}
