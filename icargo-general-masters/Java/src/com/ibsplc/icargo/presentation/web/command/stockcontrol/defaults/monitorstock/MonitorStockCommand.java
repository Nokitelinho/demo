/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * @author A-1883
 *
 */
public class MonitorStockCommand extends BaseCommand {
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		invocationContext.target = "screenload_success";
	}

}
