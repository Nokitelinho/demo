/*
 * ClearAccessoriesStockCommand.java Created on Jan 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainaccessories;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for clearing the screen
 *
 * @author A-2122
 */


public class ClearAccessoriesStockCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAINTAIN ACCESSORIES");	
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID = 
				"uld.defaults.maintainaccessoriesstock";
	private static final String CLEAR_SUCCESS = "clear_success";
    private static final String EMPTY_STRING="";

    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		
		log.entering("ClearCommand", "execute");
		
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
   	 
		MaintainAccessoriesStockForm maintainAccessoriesStockForm = 
					(MaintainAccessoriesStockForm)invocationContext.screenModel;

	    MaintainAccessoriesStockSession maintainAccessoriesStockSession = 
	   (MaintainAccessoriesStockSession) getScreenSession(MODULE_NAME, SCREEN_ID);

	    removeAllValues(maintainAccessoriesStockForm,
	    								maintainAccessoriesStockSession);
		maintainAccessoriesStockSession.removeAccessoriesStockConfigVO();
		maintainAccessoriesStockForm.setModeFlag("N");
		maintainAccessoriesStockForm.setLovFlag("N");
		//commented by Tarun for INT_ULD43_28Dec07
		AccessoriesStockConfigVO filterVO = new AccessoriesStockConfigVO();
    	
    	/*if(logonAttributes.isAirlineUser()){
    		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
    		maintainAccessoriesStockForm.setAccessoryDisableStatus("airline");
    	}
    	else{
    		filterVO.setStationCode(logonAttributes.getAirportCode());
    		maintainAccessoriesStockForm.setAccessoryDisableStatus("GHA");
    	}
    	maintainAccessoriesStockSession.setAccessoriesStockConfigVO(filterVO);*/
    	
	   	// Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471)
      	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
      
			if(logonAttributes.isAirlineUser()){
	    		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		maintainAccessoriesStockForm.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		maintainAccessoriesStockForm.setAccessoryDisableStatus("airline");
	    	}
	    	else{
	    		filterVO.setStationCode(logonAttributes.getAirportCode());
	    		maintainAccessoriesStockForm.setAccessoryDisableStatus("GHA");
	    	}
		
		
//		Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471) ends
		maintainAccessoriesStockForm.setScreenStatusFlag
				(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.addAllError(error);
        invocationContext.target=CLEAR_SUCCESS;
        
        log.exiting("ClearCommand", "execute");
		
	}

    private void removeAllValues
    	(MaintainAccessoriesStockForm maintainAccessoriesStockForm,
    		MaintainAccessoriesStockSession maintainAccessoriesStockSession){
    	maintainAccessoriesStockForm.setAccessoryCode(EMPTY_STRING);
    	maintainAccessoriesStockForm.setAccessoryDescription(EMPTY_STRING);  
    	// Added by Preet for ULD 395 on 13May08 starts
    	if(getApplicationSession().getLogonVO().isAirlineUser()){
    		maintainAccessoriesStockForm.setStationCode(EMPTY_STRING);  
    	}
    	// Added by preet for ULD 395 on 13May08 ends
    	maintainAccessoriesStockForm.setAirlineCode(EMPTY_STRING);   	
    	maintainAccessoriesStockForm.setAvailable(0);
    	maintainAccessoriesStockForm.setLastUpdateTime(EMPTY_STRING);
    	maintainAccessoriesStockForm.setLoaned(0);
    	maintainAccessoriesStockForm.setMinimumQuantity(0);
    	maintainAccessoriesStockForm.setRemarks(EMPTY_STRING);   	
    }
}