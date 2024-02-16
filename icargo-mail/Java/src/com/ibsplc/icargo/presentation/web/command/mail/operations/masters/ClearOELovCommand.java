/*
 * ClearOELovCommand.java Created on June 21, 2006
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
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeLovForm;

/**
 * @author A-2047
 *
 */
public class ClearOELovCommand extends BaseCommand {

	private static final String SUCCESS="clearoeLov_Success";
	
	
	
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		OfficeOfExchangeLovForm oeLovForm = 
							(OfficeOfExchangeLovForm)invocationContext.screenModel;
		oeLovForm.setCode("");
		oeLovForm.setDescription("");
		oeLovForm.setAirportCode("");
		oeLovForm.setPoaCode("");
		oeLovForm.setDisplayPage("1");
		oeLovForm.setSelectedValues("");
		if(oeLovForm.getOeLovPage()!=null){
			oeLovForm.setOeLovPage(null);
		}
		invocationContext.target = SUCCESS;
	}


}
