/*
 * CloseCommand.java Created on Nov 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingentries;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingEntriesForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class CloseCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListBillingEntries ScreenloadCommand");

	private static final String CLASS_NAME = "ClearCommand";

//	private static final String MODULE_NAME = "mailtracking.mara.defaults";
//	private static final String SCREEN_ID = "mailtracking.mra.defaults.listbillingentries";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "close_success";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
//    	ListBillingEntriesSession session=null;
//   		session=(ListBillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
//   		session.removeBillingEntries();
   		ListBillingEntriesForm form=(ListBillingEntriesForm)invocationContext.screenModel;
   		form.setFromDate(null);
   		form.setToDate(null);
   		form.setScreenStatus("Y");
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}
