package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.approverlov;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * 
 * @author A-1870
 * class for reloading the screen
 *
 */
public class ReloadCommand extends BaseCommand {
	
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		invocationContext.target = "screenload_success";
	}

}
