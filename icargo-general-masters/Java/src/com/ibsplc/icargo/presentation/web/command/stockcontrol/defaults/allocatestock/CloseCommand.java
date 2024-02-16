package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
/**
 *
 * @author A-1865
 *
 */
public class CloseCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String RETURN_TO_MONITORSTOCK_SUCCESS = "returnToMonitorStock_success";
	private static final String CLOSE_MESSAGEINBOX_SUCCESS = "closeToMessageInbox_success";
	private static final String LISTSTATUS_AFTER_CLOSE = "fromAnotherScreen";
	/**
	 * The execute method in BaseCommand
	 * @author A-1865
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("CloseCommand","execute");
		AllocateStockForm form=(AllocateStockForm)invocationContext.screenModel;
		
 	    log.exiting("CloseCommand","execute");
 	    if("fromMonitorStock".equals(form.getButtonStatusFlag())){
 	    	invocationContext.target = RETURN_TO_MONITORSTOCK_SUCCESS;
 	    }
 	    else{
 	    	invocationContext.target = CLOSE_SUCCESS;	
 	    	//Added by A-7364 as part of ICRD-227512 starts
 	    	MessageInboxSession messageInboxSession = 
 					(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
 			if(messageInboxSession.getMessageDetails() != null){
 				messageInboxSession.setListStatus(LISTSTATUS_AFTER_CLOSE);
 				invocationContext.target =  CLOSE_MESSAGEINBOX_SUCCESS;
 			}
 			//Added by A-7364 as part of ICRD-227512 ends
 	    }
	}
}