/*
 * RemoveDataCommand.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
/**
 * 
 * @author A-1754
 *
 */
public class RemoveDataCommand extends BaseCommand {
/**
 * The execute method in BaseCommand
 * @author A-1754
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
			MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");	
			session.setProductValidationVos(null);
			session.setSubProductVOs(null);
			invocationContext.target = "screenload_success";
		
	}

}
