/*
 * CloseCommand.java Created on May18,2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockdetails;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class CloseCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_SUCCESS = "close_success";
	
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		
		log.entering("CloseCommand","execute");
		StockDetailsForm stockDetailsForm = (StockDetailsForm) invocationContext.screenModel;
		
		invocationContext.target = CLOSE_SUCCESS;
	}

}
