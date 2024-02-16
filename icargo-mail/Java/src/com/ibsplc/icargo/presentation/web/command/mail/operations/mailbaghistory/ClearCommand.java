/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbaghistory;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagHistoryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	   /**
	    * TARGET
	    */
	   private static final String TARGET_SUCCESS = "clear_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailbaghistory";	
	  
	   public void execute(InvocationContext invocationContext)
       throws CommandInvocationException {
	
		   log.entering("ClearCommand","execute");
		   MailBagHistoryForm mailBagHistoryForm =
				(MailBagHistoryForm)invocationContext.screenModel;
		   MailBagHistorySession mailBagHistorySession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);
		   mailBagHistorySession.removeAllAttributes();
		   mailBagHistoryForm.setMailbagId("");
		   mailBagHistoryForm.setBtnDisableReq("Y");
		   log.exiting("ClearCommand","execute");
		   invocationContext.target = TARGET_SUCCESS;
		   
	   }
	

}
