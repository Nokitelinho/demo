/*
 * CloseAccessoriesStockCommand.java Created on Jan 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainaccessories;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm;

/**
 * @author A-2122
 *
 */
public class CloseAccessoriesStockCommand extends BaseCommand {
	private static final String MODULELIST_NAME = "uld.defaults";
	private static final String SCREENLIST_ID =
								"uld.defaults.stock.listaccessoriesstock";
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String LIST_FORWARD = "list_forward";
	 /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainAccessoriesStockForm maintainAccessoriesStockForm = 
    		(MaintainAccessoriesStockForm)invocationContext.screenModel;
	ListAccessoriesStockSession listAccessoriesStockSession = 
   (ListAccessoriesStockSession)getScreenSession(MODULELIST_NAME,SCREENLIST_ID);
    	if(("From List").equals(maintainAccessoriesStockForm.getDetailsFlag())){
    		listAccessoriesStockSession.setListStatus("noListForm");
        	invocationContext.target = LIST_FORWARD; 
        	}
		else{        	
    	invocationContext.target=CLOSE_SUCCESS;
    }
    }
}