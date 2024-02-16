/*
 * ClearListInventoryCommand.java Created on May 27, 2008
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
 * 
 * @author a-2883
 *
 */
public class ClearListInventoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULDMANAGEMENT");
	private static final String SCREEN_ID = 
			"uld.defaults.stock.inventoryuld";
	private static final String MODULE_NAME = "uld.defaults";
	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ClearListInventoryCommand", "execute");
		ULDInventorySession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		InventoryULDForm form = 
			(InventoryULDForm)invocationContext.screenModel;
		session.setStatusFlag(null);
		session.setDisplayInventoryDetails(null);
		session.setListInventoryULDDetails(null);
		session.setInventoryPageFlag(null);
		form.setFromDate(null);
		form.setToDate(null);
		form.setAirportCode("");
		form.setUldType("");
		form.setChildPrimaryKey("");
		form.setStatusFlag("");
		form.setDetailRemarks("");
		form.setDetailRequiredULD(0);
		form.setDetailULDType("");
		form.setAbsoluteIndex(null);
		form.setDisplayPage("1");
		log.exiting("ClearListInventoryCommand", "execute");
		invocationContext.target = "success";
	}
}
