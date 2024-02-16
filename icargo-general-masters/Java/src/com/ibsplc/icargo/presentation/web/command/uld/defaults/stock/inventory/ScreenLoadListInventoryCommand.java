/*
 * ScreenLoadListInventoryCommand.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.inventory;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.ULDInventorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.InventoryULDForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2883
 */

public class ScreenLoadListInventoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULDMANAGEMENT");
	private static final String SCREEN_ID = 
			"uld.defaults.stock.inventoryuld";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String INVENTORYPAGE ="inventorypagination";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ScreenLoadListInventoryCommand", "execute");
		
		InventoryULDForm form = 
			(InventoryULDForm)invocationContext.screenModel;
		
		ULDInventorySession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		
		if(form.getInventoryFromScreen() !=null && form.getInventoryFromScreen().equals(INVENTORYPAGE)){
			form.setInventoryFromScreen(null);
		}
		else{
			session.setStatusFlag(null);
	    	session.setDisplayInventoryDetails(null);
	    	session.setListInventoryULDDetails(null);
	    	session.setInventoryPageFlag(null);
		}
		invocationContext.target = "success";
		log.exiting("ScreenLoadListInventoryCommand","execute");
	}
	
	
	
}
