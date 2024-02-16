/*
 * CloseCommand.java Created on June 16, 2017
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbaghistory;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-6245
 *
 */
public class CloseCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
    
		private static final String CLASS_NAME = "CloseCommand";
		private static final String MODULE_NAME = "mail.operations";	
		private static final String SCREEN_ID = "mailtracking.defaults.mailbaghistory";	
		private static final String SCREENLOAD_SUCCESS = "screenload_success";
		private static final String SCREENLOAD_TO_MAILBAG_ENQUIRY = "screenload_mailbagenquiry";
	
	/**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering(CLASS_NAME,"execute");
    	
    	MailBagHistorySession mailBagHistorySession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
    	if((mailBagHistorySession.getMailSequenceNumber()!=null) &&
    			(!mailBagHistorySession.getMailSequenceNumber().isEmpty())){  /*modified by A-8149 for ICRD-248207*/
    		mailBagHistorySession.removeAllAttributes();
    		invocationContext.target = SCREENLOAD_TO_MAILBAG_ENQUIRY;
    	}else if(("yes").equals(mailBagHistorySession.getEnquiryFlag())){ //Added by A-8164 for ICRD-260365
    		mailBagHistorySession.removeAllAttributes();
    		invocationContext.target = SCREENLOAD_TO_MAILBAG_ENQUIRY;
    	}else if(("yes").equals(mailBagHistorySession.getEnquiryFlag())){ //Added by A-8164 for ICRD-260365
    		mailBagHistorySession.removeAllAttributes();
    		invocationContext.target = SCREENLOAD_TO_MAILBAG_ENQUIRY;
    	}
    	else{
    		invocationContext.target = SCREENLOAD_SUCCESS;
    	}
    	log.exiting(CLASS_NAME,"execute");
    	
    }

}
