/*
 * CloseCommand.java Created on Aug 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.viewformone;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 * 
 * 
 */
public class CloseCommand extends BaseCommand {


	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_TOLISTFORMONE = "close_tolistformone";

	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String MODULE = "mra.airlinebilling";
	
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
	private static final String FROM_LISTFORMONE = "fromListFormOne";
	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	        Log log = LogFactory.getLogger("MRA airlinebilling");
		log.entering(CLASS_NAME, "execute");				
		
		ViewFormOneSession  viewFormOneSession =
			(ViewFormOneSession)getScreenSession(MODULE, SCREENID);
			viewFormOneSession.removeFormOneVO();	
			viewFormOneSession.removeInvoiceFormOneDetailsVOs();	
		if (FROM_LISTFORMONE.equals(viewFormOneSession.getCloseStatus())){
			
			invocationContext.target = CLOSE_TOLISTFORMONE;
			viewFormOneSession.setCloseStatus(null);
		}
		else{
		invocationContext.target = CLOSE_SUCCESS;	// sets target
		}
		log.exiting(CLASS_NAME,"execute");
    }

}
