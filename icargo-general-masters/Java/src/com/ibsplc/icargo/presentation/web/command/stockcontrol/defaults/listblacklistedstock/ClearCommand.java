/*
 * ClearCommand.java Created on Sep 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listblacklistedstock;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListBlackListedStockForm;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListBlackListedStockSession;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1927
 *
 */

public class ClearCommand extends BaseCommand {

	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
	private static final String AWB = "AWB";
	private static final String S = "S";
	//Added by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ClearCommand","execute");
		ListBlackListedStockForm listBlackListedStockForm = (ListBlackListedStockForm) invocationContext.screenModel;
		ListBlackListedStockSession session =
			(ListBlackListedStockSession)getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listblacklistedstock");

		//Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix starts
		//listBlackListedStockForm.setDocType("");
		//listBlackListedStockForm.setSubType("");
	    listBlackListedStockForm.setDocType("");
	    listBlackListedStockForm.setSubType("");
	    //Modified by A-1927 @ NRT on 01-Aug-2007 for NCA Bug Fix ends
		listBlackListedStockForm.setRangeFrom("");
		listBlackListedStockForm.setRangeTo("");
		/*Collection<StockHolderPriorityVO> stockHolderPriorityVO = session.getPrioritizedStockHolders();
		if(stockHolderPriorityVO!=null){
	        for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVO){
	      		 stockHolderPriority.setStockHolderCode(null);
	        }
		}*/

		session.setBlacklistStockVOs(null);
		String dateString = DateUtilities.getCurrentDate(CALENDAR_DATE_FORMAT);
		listBlackListedStockForm.setFromDate(dateString);
		listBlackListedStockForm.setToDate(dateString);

		log.exiting("ClearCommand","execute");
		invocationContext.target = "screenload_success";
	}
}

