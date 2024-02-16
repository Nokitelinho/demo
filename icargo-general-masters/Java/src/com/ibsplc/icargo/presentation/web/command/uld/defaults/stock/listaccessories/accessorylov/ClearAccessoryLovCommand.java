/*
 * ClearAccessoryLovCommand.java Created on May 18, 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories.accessorylov;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.AccessoryStockLovForm;

/**
 * @author A-3278
 * 
 */
public class ClearAccessoryLovCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String BLANK = "";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		AccessoryStockLovForm accessoryStockLovForm = (AccessoryStockLovForm) invocationContext.screenModel;
		accessoryStockLovForm.setAccessoryStkCode(BLANK);
		accessoryStockLovForm.setAirline(BLANK);
		accessoryStockLovForm.setAccessoryStkName(BLANK);
		accessoryStockLovForm.setDisplayPage("1");
		accessoryStockLovForm.setPageAccessoryLov(null);
		invocationContext.target = CLEAR_SUCCESS;

	}

}
