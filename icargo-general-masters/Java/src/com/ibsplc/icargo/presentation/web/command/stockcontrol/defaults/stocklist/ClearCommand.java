/*
 * ClearCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stocklist;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm;

/**
 * @author A-1619
 *
 */
public class ClearCommand extends BaseCommand {

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		StockListForm form = (StockListForm) invocationContext.screenModel;
		StockListSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.stocklist");

		form.setAirline("");
		form.setDocSubType("");
		form.setDocumentType("");
		form.setAfterReload("");
		form.setFromStockList("");
		//session.setStockVOs(null);
		session.removeAllAttributes();
		invocationContext.target = "screenload_success";
	}

}
