package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory.CloseCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	13-Sep-2018		:	Draft
 */
public class CloseCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String CLASS_NAME = "CloseCommand";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mail.operations.ux.mailbaghistory";	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_TO_MAILBAG_ENQUIRY = "screenload_mailbagenquiry";
	private static final String SCREENLOAD_TO_MAILINBOUND = "screenload_mailinbound";
	
	 public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
		 
		 log.entering(CLASS_NAME,"execute");
		 MailBagHistorySession mailBagHistorySession = 
					getScreenSession(MODULE_NAME,SCREEN_ID);
		 MailBagHistoryUxForm mailBagHistoryForm =
					(MailBagHistoryUxForm)invocationContext.screenModel;
		 if((mailBagHistorySession.getMailSequenceNumber()!=null) &&
	    			(!mailBagHistorySession.getMailSequenceNumber().isEmpty())){
			 mailBagHistorySession.removeAllAttributes();
	    		invocationContext.target = SCREENLOAD_TO_MAILBAG_ENQUIRY;
		 }else if(("yes").equals(mailBagHistorySession.getEnquiryFlag())){ 
	    		mailBagHistorySession.removeAllAttributes();
	    		invocationContext.target = SCREENLOAD_TO_MAILBAG_ENQUIRY;
	     }else if(mailBagHistoryForm.getFromScreenId()!=null
	    		 && mailBagHistoryForm.getFromScreenId().equals("MTK064")){
	    	 //mailBagHistorySession.removeAllAttributes();
	    	 invocationContext.target = SCREENLOAD_TO_MAILINBOUND;
	     }
		 else{
	    		invocationContext.target = SCREENLOAD_SUCCESS;
	     }
	    	log.exiting(CLASS_NAME,"execute");
	 }
	    		 

}
