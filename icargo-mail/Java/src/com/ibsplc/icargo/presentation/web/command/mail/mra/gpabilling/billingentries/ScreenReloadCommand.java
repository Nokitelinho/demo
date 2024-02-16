/*
 * ScreenReloadCommand.java Created on Dec 15, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ScreenReloadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("GPABillingEntries ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenReloadCommand";

	//private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	//private static final String SCREEN_ID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	//private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	//String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	//GPABillingEntriesSession session=null;
   		//session=(GPABillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);


    	GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
    	form.setScreenStatus("reload");
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}
