/*
 * ScreenLoadModifyULDStockSetUpCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadModifyULDStockSetUpCommand  extends BaseCommand {

    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private Log log = LogFactory.getLogger("ScreenLoadModifyULDStockSetUpCommand");
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		MaintainULDStockForm maintainuldstockform = (MaintainULDStockForm) invocationContext.screenModel;
		String row = maintainuldstockform.getRowContents();
		String[] contents = row.split(",");
		/*String[] airlines = maintainuldstockform.getAirlineIdentifiers();
		String[] stations = maintainuldstockform.getStationCodes();
		String[] ulds = maintainuldstockform.getUldTypeCodes();
		String[] max = maintainuldstockform.getMaxQty();
		String[] min = maintainuldstockform.getMinQty();*/		
		maintainuldstockform.setAirlineCode(contents[0]);
		maintainuldstockform.setStationCode(contents[1]);
		maintainuldstockform.setUldTypeCode(contents[2]);
		maintainuldstockform.setMinimumQty(contents[4]);
		maintainuldstockform.setMaximumQty(contents[3]);
		log.log(Log.INFO, "###########Inside ScreenLoad from screen is ",
				maintainuldstockform.getValidateStatus());
		invocationContext.target=SCREENLOAD_SUCCESS;
    }

   
 }

