package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.createstock;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession;


/**
 *
 * @author A-1865
 *
 */
public class CloseCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String RETURN_TO_MONITORSTOCK_SUCCESS = "returnToMonitorStock_success";
	/**
	 * The execute method in BaseCommand
	 * @author A-1865
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("CloseCommand","execute");
		CreateStockForm form = (CreateStockForm) invocationContext.screenModel;
		CreateStockSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.createstock");
 	    log.exiting("CloseCommand","execute");
 	    if("fromMonitorStock".equals(form.getButtonStatusFlag())){
 	    	invocationContext.target = RETURN_TO_MONITORSTOCK_SUCCESS;
 	    }
 	    else{
 	    	invocationContext.target = CLOSE_SUCCESS;	
 	    }
	}
}