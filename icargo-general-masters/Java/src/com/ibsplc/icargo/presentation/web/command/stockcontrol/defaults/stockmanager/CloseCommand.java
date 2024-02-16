/*
 * CloseCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockmanager;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockManagerForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class CloseCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("STOCK MANAGER");
	
	private static final String CLOSE_SUCCESS = 
		"close_stockmanager_success";
	private static final String STOCKLIST_SUCCESS = 
		"stocklist_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = 
		"stockcontrol.defaults.stockmanager";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
		log.entering("CloseCommandStockManager", "execute");
		
		StockManagerForm form = (StockManagerForm)
				invocationContext.screenModel;
		StockListSession stockListSession = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.cto.stocklist");
			
		StockManagerSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		session.removeAllAttributes();
		if(AbstractVO.FLAG_YES.equals(form.getFromStockList())) {			
			invocationContext.target = STOCKLIST_SUCCESS;			
		}
		else {
			invocationContext.target = CLOSE_SUCCESS;		
		}
		stockListSession.removeFilterFromList();		
		form.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
		log.exiting("CloseCommandStockManager", "execute");
    }

}
