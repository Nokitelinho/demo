/*
 * ClearMonitorStockCommand.java Created on Aug 26, 2005
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.Collection;

/**
 * @author A-1952
 *
 */
public class ClearMonitorStockCommand extends BaseCommand {

	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
	private static final String AWB = "AWB";
	private static final String S = "S";
	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");


	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearMonitorStockCommand", "execute");

		MonitorStockForm monitorStockForm = (MonitorStockForm) invocationContext.screenModel;
		MonitorStockSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.monitorstock");
		monitorStockForm.setManual(false);
		monitorStockForm.setDocType("");
		monitorStockForm.setSubType("");
		monitorStockForm.setLevel("");
		monitorStockForm.setStockHolderCode("");
		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
		//monitorStockForm.setStockHolderType("");
		monitorStockForm.setDocType("");
		monitorStockForm.setSubType("");
		// added by A-7636 as part of ICRD-169489
		monitorStockForm.setStockHolderType("");
		
		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
		Collection<StockHolderPriorityVO> stockHolderPriorityVO = session
				.getPrioritizedStockHolders();

		for (StockHolderPriorityVO stockHolderPriority : stockHolderPriorityVO) {

			stockHolderPriority.setStockHolderCode(null);

		}
		//Added by A-7364 as part of ICRD-320756 starts
		monitorStockForm.setPartnerAirline(false);
		monitorStockForm.setAwbPrefix("");
		monitorStockForm.setAirlineName("");
		//Added by A-7364 as part of ICRD-320756 ends
		session.setCollectionMonitorStockVO(null);
		session.setMonitorStockDetails(null);
		session.setMonitorStockHolderVO(null);
		invocationContext.target = "screenload_success";
	}

}
