/*
 * ClearStockHolderLovCommand.java Created on Sep 09, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.common.stockholderlov;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockHolderLovForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockHolderLovSession;

/**
 * @author A-1927
 *
 */
public class ClearStockHolderLovCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    		log.entering("ClearStockHolderCommand","*execute");
			StockHolderLovForm stockHolderLovForm = (StockHolderLovForm) invocationContext.screenModel;
			StockHolderLovSession session = getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.common.stockholderlov");
			/*Collection<StockHolderPriorityVO> stockHolderPriorityVO = session.getPrioritizedStockHolders();

			for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVO){

				 stockHolderPriority.setStockHolderCode(null);

			}

			session.setPrioritizedStockHolders(stockHolderPriorityVO);*/

			Page<StockHolderLovVO> page=null;
			stockHolderLovForm.setCode("");
			stockHolderLovForm.setDescription("");
			stockHolderLovForm.setStockHolderType("");
			stockHolderLovForm.setDisplayPage("1");
			stockHolderLovForm.setSelectedValues("");
			//stockHolderLovForm.setStockHolder(null);
			session.setStockHolderLovVOs(page);
			log.exiting("ClearStockHolderCommand","execute");
		    invocationContext.target = "screenload_success";




   }

}
