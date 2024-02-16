/*
 * ClearAccessoriesStockCommand.java Created on Jan 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.ListAccessriesStockSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for clearing the screen
 *
 * @author A-1940
 */
public class ClearAccessoriesStockCommand extends BaseCommand {

    private static final String SCREEN_ID =
    				"uld.defaults.stock.listaccessoriesstock";
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
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        
		ListAccessoriesStockForm listAccessoriesStockForm = 
			(ListAccessoriesStockForm)invocationContext.screenModel;
		ListAccessriesStockSessionImpl listAccessriesStockSessionImpl = 
		(ListAccessriesStockSessionImpl)getScreenSession(MODULE_NAME,SCREEN_ID);
		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("ClearAccessoriesStockCommand","execute");
		listAccessoriesStockForm.setAccessoryCode("");
		listAccessoriesStockForm.setAirlineCode("");
		listAccessoriesStockForm.setStation("");
		listAccessoriesStockForm.setSelectAll(false);
		listAccessoriesStockForm.setSelectFlag(0);
		listAccessriesStockSessionImpl.setAccessoriesStockConfigFilterVO(null);
		listAccessriesStockSessionImpl.setAccessoriesStockConfigVOs(null);
		new ICargoComponent().setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		AccessoriesStockConfigFilterVO filterVO = new AccessoriesStockConfigFilterVO();
		
		
	   	// Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471)
      	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
   
			if(logonAttributes.isAirlineUser()){
	    		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		listAccessoriesStockForm.setListDisableStatus("airline");
	    	}
	    	else{
	    		filterVO.setStationCode(logonAttributes.getAirportCode());
	    		listAccessoriesStockForm.setListDisableStatus("GHA");
	    	}
	    	listAccessoriesStockForm.setAirlineCode(logonAttributes.getOwnAirlineCode());
		
		listAccessriesStockSessionImpl.setAccessoriesStockConfigFilterVO(filterVO);
		invocationContext.addAllError(error);
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting("ClearAccessoriesStockCommand","execute");
    }
}
