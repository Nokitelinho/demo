/*
 * ClearSubClassLovCommand.java Created on June 20, 2006
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
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassLovForm;

/**
 * @author A-2047
 *
 */
public class ClearSubClassLovCommand extends BaseCommand {

	private static final String SUCCESS="clearsubclasLov_Success";
	
	
	
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext) 
									throws CommandInvocationException {
		
		MailSubClassLovForm subClasLovForm = 
							(MailSubClassLovForm)invocationContext.screenModel;
		subClasLovForm.setCode("");
		subClasLovForm.setDescription("");
		subClasLovForm.setDisplayPage("1");
		subClasLovForm.setSelectedValues("");
		if(subClasLovForm.getSubClassLovPage()!=null){
			subClasLovForm.setSubClassLovPage(null);
		}
		invocationContext.target = SUCCESS;
	}


}
