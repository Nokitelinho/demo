package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
/**
 * 
 * @author A-1754
 *
 */
public class OpenLovCommand extends BaseCommand {
/**
 * The execute method in BaseCommand
 * @author A-1754
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		invocationContext.target = "screenload_success";
		
	}

}
