/*
 * DeleteAccessoriesStockCommand.java  Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for deleting accessory
 *
 * @author A-2122
 */
public class DeleteAccessoriesStockCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Delete Accessories");
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.stock.listaccessoriesstock";

	private static final String DELETE_SUCCESS = "delete_success";
	private static final String DELETE_FAILURE = "delete_failure";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	invocationContext.target = DELETE_SUCCESS;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ListAccessoriesStockForm listAccessoriesStockForm = 
			(ListAccessoriesStockForm) invocationContext.screenModel;
		ListAccessoriesStockSession listAccessoriesStockSession = 
			getScreenSession(MODULE, SCREENID);
		Page<AccessoriesStockConfigVO>  accessoryStockConfigList=
					listAccessoriesStockSession.getAccessoriesStockConfigVOs();
		 Collection<AccessoriesStockConfigVO> accessoriesStockConfigVOColl=
			 				new ArrayList<AccessoriesStockConfigVO>();
    	if(listAccessoriesStockForm.getSelect() != null &&
    			listAccessoriesStockForm.getSelect().length > 0) {
    		String select[] = listAccessoriesStockForm.getSelect();
    		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		
	    	for(int i = select.length - 1; i >= 0; i--) {
	    		AccessoriesStockConfigVO accessoriesStockConfigVO = 
	    								new AccessoriesStockConfigVO();
	    		accessoriesStockConfigVO.setOperationFlag(
	    					AccessoriesStockConfigVO.OPERATION_FLAG_DELETE);
	    		accessoriesStockConfigVO.setCompanyCode(
	    							logonAttributes.getCompanyCode());
	    		accessoriesStockConfigVO.setAccessoryCode(
	    			accessoryStockConfigList.get(Integer.parseInt(select[i])).
	    							getAccessoryCode().toUpperCase());
	    		accessoriesStockConfigVO.setAirlineCode(
	    				accessoryStockConfigList.get(Integer.parseInt
	    						(select[i])).getAirlineCode().toUpperCase());
	    		accessoriesStockConfigVO.setStationCode(
	    				accessoryStockConfigList.get(Integer.parseInt
	    						(select[i])).getStationCode().toUpperCase());
	    		
	    		String airlineCode =accessoryStockConfigList.get(Integer.
	    				parseInt(select[i])).getAirlineCode().toUpperCase();
	    		int airlineIdentifier=0;
	    		AirlineValidationVO airlineValidationVO = null;
	    		airlineValidationVO=validateAlphaCode(logonAttributes.
	    									getCompanyCode(),airlineCode);
	    		airlineIdentifier =
	    					airlineValidationVO.getAirlineIdentifier();
	    		  		
	    		log.log(Log.FINE, "airlineidentifier--->", airlineIdentifier);
				accessoriesStockConfigVO.setAirlineIdentifier(
		    										airlineIdentifier);
	    		accessoriesStockConfigVOColl.add(accessoriesStockConfigVO);
    			log.log(Log.FINE, "Inside delete uldVO------>>>",
						accessoriesStockConfigVO);
        	  	}
	    	
    		try {
				new ULDDefaultsDelegate().saveAccessories(
												accessoriesStockConfigVOColl);
			}
			catch(BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> deleteErrors = 
						handleDelegateException(businessDelegateException);
				if(deleteErrors != null &&
						deleteErrors.size() > 0 ) {
					errors.addAll(deleteErrors);
				}
   			}
			if(errors != null &&
	    			errors.size() > 0 ) {
	    			invocationContext.addAllError(errors);
	    			invocationContext.target = DELETE_FAILURE;
	    	}
	    		
    		for(int i = select.length - 1; i >= 0; i--) {
    			accessoryStockConfigList.remove(accessoryStockConfigList.get(
						Integer.parseInt(select[i])));
    		
    		}
	    	
    	}
    	
    	invocationContext.target = DELETE_SUCCESS;	

    }
    
	/**
	 * 
	 * @param companyCode
	 * @param airlineCode
	 * @return
	 */
	private AirlineValidationVO validateAlphaCode(String companyCode,String airlineCode){
		AirlineValidationVO airlineValidationVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try{			
			AirlineDelegate airlineDelegate = new AirlineDelegate(); 
    		airlineValidationVO=airlineDelegate.
    					validateAlphaCode(companyCode,airlineCode);    	
    	}catch(BusinessDelegateException businessDelegateException){
    		log.log(Log.FINE,"InvalidAlphaCode--->");
    	}
		return airlineValidationVO;
	}

}

