package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.confirmstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-4443
 *
 */
public class CloseCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_SUCCESS = "close_success";
	
	/**
	 * The execute method in BaseCommand
	 * @author A-4443
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("CloseCommand","execute");
		ConfirmStockForm form = (ConfirmStockForm) invocationContext.screenModel;
		ConfirmStockSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.confirmstock");
 	    log.exiting("CloseCommand","execute");
 	   /* if("fromMonitorStock".equals(form.getButtonStatusFlag())){
 	    	invocationContext.target = RETURN_TO_MONITORSTOCK_SUCCESS;
 	    }
 	    else{*/
 	    	invocationContext.target = CLOSE_SUCCESS;	
 	  //}
	}
}