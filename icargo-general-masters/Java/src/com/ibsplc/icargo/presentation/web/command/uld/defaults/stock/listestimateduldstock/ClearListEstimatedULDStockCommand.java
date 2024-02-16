/*
 * ClearAccessoriesStockCommand.java Created on Oct 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listestimateduldstock;


import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListEstimatedULDStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListEstimatedULDStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for clearing the screen
 *
 * @author A-2934
 */
public class ClearListEstimatedULDStockCommand extends BaseCommand {

    private static final String SCREEN_ID =
    				"uld.defaults.stock.listestimateduldstock";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String CLEAR_SUCCESS = "clear_success";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
        ListEstimatedULDStockForm listEstimatedULDStockForm = 
			(ListEstimatedULDStockForm)invocationContext.screenModel;
        ListEstimatedULDStockSession listEstimatedULDStockSession =  getScreenSession(MODULE_NAME,SCREEN_ID);
		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("ClearListEstimatedULDStockCommand","execute");
		listEstimatedULDStockForm.setAirport(logonAttributes.getAirportCode());
		listEstimatedULDStockForm.setAirlinecode(logonAttributes.getOwnAirlineCode());
		listEstimatedULDStockForm.setUldType("");
		listEstimatedULDStockForm.setSelectAll(false);
		listEstimatedULDStockForm.setSelectFlag(0);
		listEstimatedULDStockForm.setStockdisplayPage("1");
		listEstimatedULDStockForm.setListStatus(CLEAR_SUCCESS);
		listEstimatedULDStockSession.setListStatus(null);
		listEstimatedULDStockSession.setEstimatedULDStockFilterVO(null);
    	listEstimatedULDStockSession.setEstimatedULDStockVO(null);
    	listEstimatedULDStockSession.setEstimatedULDStockVOs(null);
		listEstimatedULDStockSession.removeAllAttributes();
		new ICargoComponent().setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		EstimatedULDStockFilterVO filterVO = new EstimatedULDStockFilterVO();
		
		
		listEstimatedULDStockSession.setEstimatedULDStockFilterVO(filterVO);
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting("ClearListEstimatedULDStockCommand","execute");
    }
}
