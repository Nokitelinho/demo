/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite.ScreenLoadCommand.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSiteSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteMasterForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class ScreenLoadCommand extends BaseCommand{
	
	/** The log. */
	private Log log = LogFactory.getLogger("Billing Site Master ScreenloadCommand");
	
	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/** The Constant SCREEN_ID. */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.billingsitemaster";
	
	/** The Constant ACTION_SUCCESS. */
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * Added by 			: A-5219 on 15-Nov-2013
	 * Used for 	:
	 * Parameters	:	@param invocationContext
	 * Parameters	:	@throws CommandInvocationException
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
	
		log.entering("ScreenLoadCommand", "execute");
		
		BillingSiteMasterForm billingSiteForm = (BillingSiteMasterForm) invocationContext.screenModel;
		BillingSiteSession billingSiteSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		if(billingSiteForm.getBillingSiteCode()==null){
			billingSiteForm.reset(); 
			billingSiteSession.removeAllAttributes();
		}
		billingSiteForm.setStatus("Screenload");
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("ScreenLoadCommand", "execute");
	}

}
