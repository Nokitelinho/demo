/*
 * PrintStockAllocationReportCommand.java Created on Apr 5, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.returnstockrange.report;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PrintStockAllocationReportCommand.
 */
public class PrintStockAllocationReportCommand extends AbstractPrintCommand {	
	
	/** The Constant GENERATE_RPT. */
	private static final String GENERATE_RPT = "generatereport";
	
	private Log log = LogFactory.getLogger("STOCK CONTROL");
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {	
		log.entering("PrintStockAllocationReportCommand", "-->execute<--- From Return Stock");
		//Added by A-5237 for ICRD-96246 STARTS
		if(invocationContext.hasErrors()){
			invocationContext.target ="screenload_success";
			return;
		}
		//Added by A-5237 for ICRD-96246 Ends 
		MonitorStockSession monitorSession = getScreenSession("stockcontrol.defaults","stockcontrol.defaults.monitorstock");
		
			monitorSession.setReportGenerateMode(GENERATE_RPT);
			invocationContext.target = "screenload_success";			
		  
		}		
	}


