/*
 * RefreshCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * @author A-2105
 *
 */
public class RefreshCommand  extends BaseCommand {

    private static final String CREATE_SUCCESS = "reload_success";


/**
 * execute method
 * @param invocationContext
 * @throws CommandInvocationException
*/
public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

	invocationContext.target = CREATE_SUCCESS;

}
}