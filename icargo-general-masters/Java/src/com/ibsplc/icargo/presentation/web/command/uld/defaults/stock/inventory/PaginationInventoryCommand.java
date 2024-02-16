/*
 * PaginationInventoryCommand.java Created on May 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.inventory;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
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
public class PaginationInventoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULDMANAGEMENT");
	private static final String SCREEN_ID = 
			"uld.defaults.stock.inventoryuld";
	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String FLAG_FLIGHT = "FLIGHT";
	
	
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("PaginationInventoryCommand", "execute");
		ULDInventorySession session =
			getScreenSession(MODULE_NAME, SCREEN_ID);
		InventoryULDForm form = 
			(InventoryULDForm)invocationContext.screenModel;
		
		List<InventoryULDVO> listdetails = new ArrayList<InventoryULDVO>();
		List<InventoryULDVO> displaydetails = new ArrayList<InventoryULDVO>();
		InventoryULDVO vo = new InventoryULDVO();
		listdetails = session.getListInventoryULDDetails();
		vo = listdetails.get(Integer.parseInt(form.getDisplayPage())-1);
		displaydetails.add(vo);
		session.setDisplayInventoryDetails((ArrayList<InventoryULDVO>)displaydetails);
		session.setInventoryPageFlag(form.getDisplayPage());
		log.exiting("PaginationInventoryCommand", "execute");
		invocationContext.target = "success";
	}
}
