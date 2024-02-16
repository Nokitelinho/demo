package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory.ScreenLoadMailBagHistory.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Sep-2018		:	Draft
 */
public class ScreenLoadMailBagHistory extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
    
	private static final String CLASS_NAME = "ScreenLoadMailBagHistory";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailbaghistory";
	
	public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
		
		log.entering(CLASS_NAME,"execute");
    	MailBagHistoryUxForm mailBagHistoryForm =
				(MailBagHistoryUxForm)invocationContext.screenModel;
    	MailBagHistorySession mailBagHistorySession = 
			getScreenSession(MODULE,SCREEN_ID); 
    	mailBagHistorySession.setMailBagHistoryVOs(null);
    	mailBagHistoryForm.setMailbagId("");
    	mailBagHistoryForm.setBtnDisableReq(MailConstantsVO.FLAG_YES);
    	
    	invocationContext.target = SCREENLOAD_SUCCESS;
    	
    	log.exiting(CLASS_NAME,"execute");
	}

}
