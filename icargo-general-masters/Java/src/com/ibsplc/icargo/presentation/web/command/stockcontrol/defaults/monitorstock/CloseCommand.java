/*
 * CloseCommand.java Created on Dec 07, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5201
 * 
 */
public class CloseCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		MonitorStockSession session = getScreenSession("stockcontrol.defaults","stockcontrol.defaults.monitorstock");
		session.setSelected("back_to_allocate_stock");  
		if (("back_to_allocate_stock").equals(session.getCloseStatus())) {  
				invocationContext.target = "closetoAllocateStock";		
			}
		else {
			invocationContext.target = "closeSuccess";
			}
		log.exiting("EXIT", "MONITOR STOCK CLOSE "); 

	}

}
