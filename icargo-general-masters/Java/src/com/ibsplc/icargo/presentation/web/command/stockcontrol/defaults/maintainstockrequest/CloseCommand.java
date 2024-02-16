package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockrequest;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockRequestForm;



/**
 * 
 * @author A-1885
 *
 */
public class CloseCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_MAINTAIN_SUCCESS = "returnToStockRequestList_success";
	private static final String RETURN_TO_MONITORSTOCK_SUCCESS = "returnToMonitorStock_success";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MaintainStockRequestForm form = (MaintainStockRequestForm)invocationContext.screenModel;
    	if("StockRequestList".equals(form.getFromStockRequestList())||
    			"StockRequestListCreate".equals(form.getFromStockRequestList())){
    		form.setFromStockRequestList("");
    		log.log(Log.FINE,"close command from list screen");
    		invocationContext.target =  CLOSE_MAINTAIN_SUCCESS;
    	}
    	else if("fromMonitorStock".equals(form.getButtonStatusFlag())){
 	    	invocationContext.target = RETURN_TO_MONITORSTOCK_SUCCESS;
 	    }
    	else{
    		invocationContext.target =  CLOSE_SUCCESS;
    	}
		}
	

}