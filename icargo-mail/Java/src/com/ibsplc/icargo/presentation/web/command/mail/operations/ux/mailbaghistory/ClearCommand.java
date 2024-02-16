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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory.ClearCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	13-Sep-2018		:	Draft
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String TARGET_SUCCESS = "clear_success";
	   
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mail.operations.ux.mailbaghistory";
	
	public void execute(InvocationContext invocationContext)
		       throws CommandInvocationException {
		
		log.entering("ClearCommand","execute");
		MailBagHistoryUxForm mailBagHistoryForm =
				(MailBagHistoryUxForm)invocationContext.screenModel;
		MailBagHistorySession mailBagHistorySession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);
		mailBagHistorySession.removeAllAttributes();
		mailBagHistoryForm.setMailbagId("");
		mailBagHistoryForm.setBtnDisableReq("Y");
		log.exiting("ClearCommand","execute");
		invocationContext.target = TARGET_SUCCESS;
	
	}

}
