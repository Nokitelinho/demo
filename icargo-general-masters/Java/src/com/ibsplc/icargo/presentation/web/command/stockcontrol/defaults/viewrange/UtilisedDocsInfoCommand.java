package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.viewrange;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.UtilisedDocsInfoForm;

/**
 * @author a-5160
 * The Class UtilisedDocsInfoCommand.
 */
public class UtilisedDocsInfoCommand extends BaseCommand {
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		UtilisedDocsInfoForm utilisedDocsInfoForm = (UtilisedDocsInfoForm) invocationContext.screenModel;
		
		invocationContext.target = "screenload_success";
	}
}