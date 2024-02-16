/*
 * CloseListULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;

/**
 * This command class is invoked for closing the screen
 *
 * @author A-1347
 */
public class CloseListULDCommand extends BaseCommand {
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.listuld";
	
	private static final String MODULE_MONITORSTOCK = "uld.defaults";

	private static final String SCREENID_MONITORSTOCK =
		"uld.defaults.monitoruldstock";
	private static final String CLOSE_MONITORSTOCK = "close_monitorstock";
	private static final String CLOSE_SUCCESS = "close_success";
  
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ListULDSession listULDSession = (ListULDSession)getScreenSession(MODULE,SCREENID);
    	ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		
    	MonitorULDStockSession monitorULDStockSession = 
    		(MonitorULDStockSession)getScreenSession(MODULE_MONITORSTOCK,SCREENID_MONITORSTOCK);
		
    	listULDSession.setIsListed(false);
		listULDSession.setListFilterVO(null);
		listULDSession.setListStatus("");
		listULDSession.setListDisplayPage(null);
		if("monitorStock".equals(listULDForm.getScreenLoadStatus())) {
			monitorULDStockSession.setListStatus("fromAnotherScreen");
			invocationContext.target = CLOSE_MONITORSTOCK;
		}
		else {
			invocationContext.target = CLOSE_SUCCESS;
		}

    }
}
