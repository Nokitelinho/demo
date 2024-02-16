package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.PostalAdministrationUxLovForm;

public class ClearPALovCommand extends BaseCommand {

	private static final String SUCCESS="clearPALov_Success";
	
	
	
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		PostalAdministrationUxLovForm paLovForm = 
					(PostalAdministrationUxLovForm)invocationContext.screenModel;
		paLovForm.setCode("");
		paLovForm.setDescription("");
		paLovForm.setDisplayPage("1");
		paLovForm.setSelectedValues("");
		if(paLovForm.getPaLovPage()!=null){
			paLovForm.setPaLovPage(null);
		}
		invocationContext.target = SUCCESS;
	}

}
