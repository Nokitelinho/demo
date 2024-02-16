package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;



/**
 * 
 * @author A-1885
 *
 */
public class CloseCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_MAINTAIN_SUCCESS = "returnToStockHolderList_success";
	private static final String CLOSE_MESSAGEINBOX_SUCCESS = "closeToMessageInbox_success";
	private static final String LISTSTATUS_AFTER_CLOSE = "fromAnotherScreen";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MaintainStockHolderForm maintainStockHolderForm = (MaintainStockHolderForm)invocationContext.screenModel;
    	if("StockHolderList".equals(maintainStockHolderForm.getFromStockHolderList())){
    		maintainStockHolderForm.setFromStockHolderList("");
    		log.log(Log.FINE,"close command from list screen");
    		invocationContext.target =  CLOSE_MAINTAIN_SUCCESS;
    	}
    	else{
    		invocationContext.target =  CLOSE_SUCCESS;
    	}
    	//Added by A-7364 as part of ICRD-217145 starts
    	MessageInboxSession messageInboxSession = 
				(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
		if(messageInboxSession.getMessageDetails() != null){
			messageInboxSession.setListStatus(LISTSTATUS_AFTER_CLOSE);
			invocationContext.target =  CLOSE_MESSAGEINBOX_SUCCESS;
		}
		//Added by A-7364 as part of ICRD-217145 ends
		}
	

}