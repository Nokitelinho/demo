/*
 * CreateAccessoriesStockCommand.java Created on Jan 23, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainaccessories;

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
 * This command class is invoked for creating a new uldaccessories
 *
 * @author A-2122
 */
public class CreateAccessoriesStockCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAINTAIN ACCESSORIES");	
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID = 
								"uld.defaults.maintainaccessoriesstock";
	private static final String CREATE_SUCCESS = "create_success";
	private static final String CREATE_FAILURE = "create_failure";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String lastupdateuser=logonAttributes.getUserId();
		
				
	MaintainAccessoriesStockSession maintainAccessoriesStockSession = 
	 (MaintainAccessoriesStockSession )getScreenSession(MODULE_NAME, SCREEN_ID);
    	MaintainAccessoriesStockForm maintainAccessoriesStockForm   = 
    		(MaintainAccessoriesStockForm)invocationContext.screenModel;
    	Collection<ErrorVO> errors = null;
	    		
		if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = CREATE_FAILURE;
				return;
		}
		
		AccessoriesStockConfigVO accessoriesStockConfigVO = 
										new AccessoriesStockConfigVO();
		accessoriesStockConfigVO.setOperationFlag
					(accessoriesStockConfigVO.OPERATION_FLAG_INSERT);
		accessoriesStockConfigVO.setAccessoryCode
				(maintainAccessoriesStockForm.getAccessoryCode().toUpperCase());
    	accessoriesStockConfigVO.setAirlineCode
    		(maintainAccessoriesStockForm.getAirlineCode().toUpperCase());
    	accessoriesStockConfigVO.setStationCode
    			(maintainAccessoriesStockForm.getStationCode().toUpperCase());
    	accessoriesStockConfigVO.setAirlineIdentifier
    				(maintainAccessoriesStockForm.getAirlineIdentifier());
    	accessoriesStockConfigVO.setAccessoryDescription
    				(maintainAccessoriesStockForm.getAccessoryDescription());
    	accessoriesStockConfigVO.setAvailable
    							(maintainAccessoriesStockForm.getAvailable());
    	accessoriesStockConfigVO.setLoaned
    								(maintainAccessoriesStockForm.getLoaned());
    	accessoriesStockConfigVO.setMinimumQuantity
    						(maintainAccessoriesStockForm.getMinimumQuantity());
    	accessoriesStockConfigVO.setLastUpdateUser(lastupdateuser);
    	accessoriesStockConfigVO.setRemarks
    						(maintainAccessoriesStockForm.getRemarks());
    	
    	maintainAccessoriesStockSession.setAccessoriesStockConfigVO
    												(accessoriesStockConfigVO);
    	maintainAccessoriesStockForm.setModeFlag("Y");
    	maintainAccessoriesStockForm.setLovFlag("Y");
    	maintainAccessoriesStockForm.setScreenStatusFlag
    						(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    
	invocationContext.target = CREATE_SUCCESS;
	log.exiting("CreateAccessoriesStockCommand","execute");
    }
    
}