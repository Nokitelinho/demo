/*
 * ScreenLoadMailBagHistory.java Created on June 16, 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbaghistory;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagHistoryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-6245
 *
 */
public class ScreenLoadMailBagHistory extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
    
		private static final String CLASS_NAME = "ScreenLoadMailBagHistory";
		private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	/**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering(CLASS_NAME,"execute");
    	MailBagHistoryForm mailBagHistoryForm =
				(MailBagHistoryForm)invocationContext.screenModel;
    	MailBagHistorySession mailBagHistorySession = 
			getScreenSession("mail.operations","mailtracking.defaults.mailbaghistory");
    	mailBagHistorySession.setMailBagHistoryVOs(null);
    	mailBagHistoryForm.setMailbagId("");
    	mailBagHistoryForm.setBtnDisableReq(MailConstantsVO.FLAG_YES);
    	
    	invocationContext.target = SCREENLOAD_SUCCESS;
    	
    	log.exiting(CLASS_NAME,"execute");
    	
    }

}
