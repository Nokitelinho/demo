/*
 * SaveCommand.java Created on Oct 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for clearing
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Nov 30, 2006 Rani Rose John Initial draft
 */
public class SaveCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		if (invocationContext.getErrors() == null
				|| invocationContext.getErrors().size() == 0) {
		
			MaintainMraProrateFactorsSession maintainProrateFactorsSession = 
				(MaintainMraProrateFactorsSession) getScreenSession(
					MODULE_NAME, SCREEN_ID);
			//Collection<ErrorVO> errors = null;
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			
			try {
				mailTrackingMRADelegate.saveProrationFactors(
						maintainProrateFactorsSession.getFactors());
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				 handleDelegateException(e);	
			}
			invocationContext.target = SAVE_SUCCESS;
		}else {
			invocationContext.target = SAVE_FAILURE;
		}
		
		log.exiting(CLASS_NAME, "execute");
	}

	
}