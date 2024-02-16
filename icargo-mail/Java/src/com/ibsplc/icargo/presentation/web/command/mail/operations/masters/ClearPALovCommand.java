/*
 * ClearPALovCommand.java Created on June 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationLovForm;

/**
 * @author A-2047
 *
 */
public class ClearPALovCommand extends BaseCommand {

	private static final String SUCCESS="clearPALov_Success";
	
	
	
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		PostalAdministrationLovForm paLovForm = 
					(PostalAdministrationLovForm)invocationContext.screenModel;
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
