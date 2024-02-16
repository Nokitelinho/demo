/*
 * NavigateAcsriesCommand.java Created on Feb 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainaccessories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected accessories
 * 
 * @author A-2122
 */
public class NavigateAcsriesCommand extends BaseCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("MAINTAIN ACCESSORIES");
	
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.maintainaccessoriesstock";
	private static final String NAVIGATION_SUCCESS = "navigation_success";
	private static final String NAVIGATION_FAILURE = "navigation_failure";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.log(Log.FINE,"Navigate Command---Entry----------->");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MaintainAccessoriesStockSession maintainAccessoriesStockSession = 
    		(MaintainAccessoriesStockSession)getScreenSession(MODULE,SCREENID);
		MaintainAccessoriesStockForm maintainAccessoriesStockForm = 
			(MaintainAccessoriesStockForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		HashMap<String,AccessoriesStockConfigVO> accessoriesStockConfigVOs = 
			maintainAccessoriesStockSession.getAccessoriesStockConfigVOMap();
		log.log(Log.FINE, "Session values--->", accessoriesStockConfigVOs);
		int displayPage = Integer.parseInt(maintainAccessoriesStockForm.
    													getDisplayPage());
    	int currentPage = Integer.parseInt(maintainAccessoriesStockForm.
    														getCurrentPage());
    	
    	String[] accCodesSelected = maintainAccessoriesStockForm.
    									getAccCodesSelected().split(",");
    	String[] airCodesSelected = maintainAccessoriesStockForm.
    									getAirCodesSelected().split(",");
    	String[] stationsSelected = maintainAccessoriesStockForm.
    									getStationsSelected().split(",");
    	StringBuffer key = new StringBuffer();
    	key.append(accCodesSelected[currentPage-1]).
    				append(airCodesSelected[currentPage-1]).
    							append(stationsSelected[currentPage-1]);
    	String keyVal=key.toString();
       	AccessoriesStockConfigVO accessoriesStockConfigVO =
    		maintainAccessoriesStockSession.getAccessoriesStockConfigVOMap().
    									get(keyVal);
       	log.log(Log.FINE, "accessoriesStockConfigVO-->inside navigate",
				accessoriesStockConfigVO);
		log.log(Log.FINE, "keyval---->", keyVal);
		accessoriesStockConfigVO.setAccessoryCode
    					(maintainAccessoriesStockForm.getAccessoryCode());  							
    	accessoriesStockConfigVO.setAirlineCode
    							(maintainAccessoriesStockForm.getAirlineCode());
    	accessoriesStockConfigVO.setStationCode
								(maintainAccessoriesStockForm.getStationCode());
    	accessoriesStockConfigVO.setAccessoryDescription
    				  (maintainAccessoriesStockForm.getAccessoryDescription());
    	accessoriesStockConfigVO.setAvailable
    							(maintainAccessoriesStockForm.getAvailable());
    	accessoriesStockConfigVO.setLoaned
    								(maintainAccessoriesStockForm.getLoaned());
    	accessoriesStockConfigVO.setRemarks
    								(maintainAccessoriesStockForm.getRemarks());
    	log.log(Log.FINE, "Length of Array------- inside NavigateCommand..",
				accCodesSelected.length);
		StringBuffer keyDisplay = new StringBuffer();
		keyDisplay.append(accCodesSelected[displayPage-1]).
						append(airCodesSelected[displayPage-1]).
							append(stationsSelected[displayPage-1]);
		String keyValue=keyDisplay.toString();
		log.log(Log.FINE, "keyValue---->", keyValue);
		if(accessoriesStockConfigVOs.
    					get(keyValue)== null){
    		String airlineCode=airCodesSelected[displayPage-1];
    		int airlineIdentifier = 0;
    		try{
				AirlineValidationVO airlineValidationVO = null;
				AirlineDelegate airlineDelegate = new AirlineDelegate(); 
	    		airlineValidationVO=airlineDelegate.
	    						validateAlphaCode(logonAttributes.
	    										getCompanyCode(),airlineCode);
	    		airlineIdentifier =
	    					airlineValidationVO.getAirlineIdentifier();
	    		  		
	    		log.log(Log.FINE, "airlineidentifier--->", airlineIdentifier);
	    		}catch(BusinessDelegateException businessDelegateException){
	        	errors =handleDelegateException(businessDelegateException);	
	    		}
			try {
				accessoriesStockConfigVO = new ULDDefaultsDelegate().
				findAccessoriesStockDetails(logonAttributes.getCompanyCode(),
				accCodesSelected[displayPage-1],stationsSelected[displayPage-1],
															 airlineIdentifier);
    			}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    			}
    			
    		accessoriesStockConfigVOs.put(keyValue,accessoriesStockConfigVO);
    		log.log(Log.FINE, "keyValue", keyValue);
			log.log(Log.FINE, "AccessoryStockConfigVO----inside navigate>",
					accessoriesStockConfigVOs);
    	}
    	else {
    			accessoriesStockConfigVO = 
    				accessoriesStockConfigVOs.get(keyValue);
    			log.log(Log.FINE, "accessoriesStockConfigVO",
						accessoriesStockConfigVO);
				log.log(Log.FINE, "keyValue", keyValue);
    			
        	}
    	if(errors != null &&
				errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = NAVIGATION_FAILURE;
				return;
		}
    	
    	maintainAccessoriesStockSession.
    					setAccessoriesStockConfigVO(accessoriesStockConfigVO);
    	maintainAccessoriesStockForm.setCurrentPage
    											(Integer.toString(displayPage));
        maintainAccessoriesStockForm.setScreenStatusFlag(
      				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
        	invocationContext.target = NAVIGATION_SUCCESS;
    	
		}
					
		
}
