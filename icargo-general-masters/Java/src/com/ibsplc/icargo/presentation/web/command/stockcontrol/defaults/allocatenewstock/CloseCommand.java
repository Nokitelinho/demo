package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatenewstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateNewStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-1865
 *
 */
public class CloseCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String RETURN_TO_MONITORSTOCK_SUCCESS = "returnToMonitorStock_success";
	private static final String RETURN_TO_ALLOCATESTOCK_SUCCESS = "returnToAllocateStock_success";
	/**
	 * The execute method in BaseCommand
	 * @author A-1865
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("CloseCommand","execute");
		AllocateNewStockForm form = (AllocateNewStockForm) invocationContext.screenModel;
		//AllocateStockSession session = getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
		
 	    log.log(Log.INFO, "\n\n\nFlag----------------------------->>", form.getButtonStatusFlag());
		if("fromMonitorStock".equals(form.getButtonStatusFlag())){
 	    	log.log(Log.INFO,
					"\n\n\nFlag111111----------------------------->>", form.getButtonStatusFlag());
			invocationContext.target = RETURN_TO_MONITORSTOCK_SUCCESS;
 	    }
 	    else if("fromAllocateStock".equals(form.getButtonStatusFlag())){
 	    	log.log(Log.INFO, "\n\n\nFlag222----------------------------->>",
					form.getButtonStatusFlag());
			invocationContext.target = RETURN_TO_ALLOCATESTOCK_SUCCESS;
 	    }
 	    else{
 	    	invocationContext.target = CLOSE_SUCCESS;	
 	    }
 	    
 	   log.exiting("CloseCommand","execute");
	}
}
