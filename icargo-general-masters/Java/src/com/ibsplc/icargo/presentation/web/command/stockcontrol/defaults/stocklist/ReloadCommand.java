/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stocklist;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ViewStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1747
 * 
 */
public class ReloadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		StockListForm form = (StockListForm) invocationContext.screenModel;

		ViewStockRequestSession session = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.cto.viewstockrequest");

		String[] rowValues = form.getRowId();

		log.log(Log.FINE, "rowidddddssss444");
		log.log(Log.FINE, "rowidddddssss", rowValues);
		session.setRowValues(rowValues[0]);
		form.setAfterReload("Y");
		invocationContext.target = "screenload_success";
	}
}
