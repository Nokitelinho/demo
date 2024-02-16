/*
 * CloseAccessoriesStockCommand.java Created on Jan 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.ListAccessriesStockSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is for closing the screen
 * 
 * @author A-1940
 *
 */
public class CloseAccessoriesStockCommand extends BaseCommand {
	private static final String CLOSE_SUCCESS ="close_success";
	private static final String SCREEN_ID = 
				"uld.defaults.stock.listaccessoriesstock";
	private static final String MODULE_NAME = "uld.defaults";

	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		ListAccessoriesStockForm listAccessoriesStockForm = 
			(ListAccessoriesStockForm)invocationContext.screenModel;
		ListAccessriesStockSessionImpl listAccessriesStockSessionImpl = 
		(ListAccessriesStockSessionImpl)getScreenSession(MODULE_NAME,SCREEN_ID);
		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("CloseAccessoriesStockCommand","execute");
		listAccessoriesStockForm.setAccessoryCode("");
		listAccessoriesStockForm.setAirlineCode("");
		listAccessoriesStockForm.setStation("");
		listAccessoriesStockForm.setSelectAll(false);
		listAccessoriesStockForm.setSelectFlag(0);
		listAccessriesStockSessionImpl.setAccessoriesStockConfigVOs(null);
		new ICargoComponent().setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = CLOSE_SUCCESS;
		log.exiting("CloseAccessoriesStockCommand","execute");
    }

}
