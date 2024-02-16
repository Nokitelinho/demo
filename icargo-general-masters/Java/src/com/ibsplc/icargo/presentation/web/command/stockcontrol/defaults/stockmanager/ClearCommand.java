/*
 * ClearCommand.java Created on Jan 17, 2006
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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("STOCK MANAGER");
	
	private static final String CLEAR_SUCCESS = 
		"clear_stockmanager_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = "stockcontrol.defaults.stockmanager";	

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearCommandStockManager", "execute");
    	
    	
    	StockManagerForm form = (StockManagerForm)invocationContext.screenModel;
    	StockManagerSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
    	StockListSession stockListSession = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.cto.stocklist");
    	
    	stockListSession.removeFilterFromList();    	
    	form.setAirline("");
    	form.setDocumentType("");
    	form.setDocumentSubType("");
    	session.removeStockAllocationVO();
    	form.setSummaryCount("");
    	form.setForMessage("N");
    	
		invocationContext.target = CLEAR_SUCCESS;
		form.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_SCREENLOAD);
		log.exiting("ClearCommandStockManager", "execute");

    }

}
