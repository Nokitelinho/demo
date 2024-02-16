/*
 * DetailsListAccessoriesCommand.java Created on Feb 8, 2006
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
import java.util.Map;


import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected ulds
 * 
 * @author A-2122
 */
public class DetailsListAccessoriesCommand extends BaseCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("MAINTAIN ACCESSORIES");
		
	private static final String MODULE_MAINTAINACSRS = "uld.defaults";

	private static final String SCREENID_MAINTAINACSRS =
		"uld.defaults.maintainaccessoriesstock";
	private static final String DETAILS_SUCCESS = "details_success";
	//private static final String DETAILS_FAILURE = "details_error";
    
	private static final String ACCESSORYCODE_ONETIME = "uld.defaults.accessoryCode";
    
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		int airlineIdentifier = 0;
		
		MaintainAccessoriesStockSession maintainAccessoriesStockSession= 
    		(MaintainAccessoriesStockSession)
    			getScreenSession(MODULE_MAINTAINACSRS,SCREENID_MAINTAINACSRS);
       HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
    	maintainAccessoriesStockSession.setOneTimeValues(oneTimeValues);
    	MaintainAccessoriesStockForm maintainAccessoriesStockForm = 
    			 (MaintainAccessoriesStockForm) invocationContext.screenModel;
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("DetailsListAccessoriesCommand","execute");
		
		AccessoriesStockConfigVO accessoriesStockConfigVO = null;	
						
		HashMap<String,AccessoriesStockConfigVO> accessoriesStockConfigVOs =
				new HashMap<String,AccessoriesStockConfigVO>();
		String[] accCodesSelected = maintainAccessoriesStockForm.
											getAccCodesSelected().split(",");
		String[] airCodesSelected = maintainAccessoriesStockForm.
											getAirCodesSelected().split(",");
		String[] stationsSelected = maintainAccessoriesStockForm.
											getStationsSelected().split(",");
			String airlineCode=airCodesSelected[0];
			log.log(Log.FINE, "airlineCode--->", airlineCode);
			log.log(Log.FINE, "accessoryCode--->", accCodesSelected);
			log.log(Log.FINE, "stationCode--->", stationsSelected);
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
				accCodesSelected[0],stationsSelected[0],airlineIdentifier);
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		maintainAccessoriesStockSession.
    					setAccessoriesStockConfigVO(accessoriesStockConfigVO);
    		log.log(Log.FINE, "VO in Details Command-------------->",
					accessoriesStockConfigVO);
			StringBuffer key = new StringBuffer();
    		key.append(accCodesSelected[0]).append(airCodesSelected[0]).
    										append(stationsSelected[0]);
    		String keyValue=key.toString();
    		log.log(Log.FINE, "keyValue", keyValue);
			accessoriesStockConfigVOs.put(keyValue,accessoriesStockConfigVO);
    		maintainAccessoriesStockSession.
    				setAccessoriesStockConfigVOMap(accessoriesStockConfigVOs);
    		log.log(Log.FINE, "vos inside  session in Details Command-->",
					accessoriesStockConfigVOs);
			maintainAccessoriesStockForm.setDisplayPage("1");
    		maintainAccessoriesStockForm.setCurrentPage("1");
    		maintainAccessoriesStockForm.
    				setLastPageNum(Integer.toString(accCodesSelected.length));
    		maintainAccessoriesStockForm.
    				setTotalRecords(Integer.toString(accCodesSelected.length));
			maintainAccessoriesStockForm.setScreenStatusFlag
			                (ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			maintainAccessoriesStockForm.setStatusFlag("U");
			maintainAccessoriesStockForm.setDetailsFlag("From List");
			maintainAccessoriesStockForm.setModeFlag("Y");
			maintainAccessoriesStockForm.setLovFlag("Y");
			invocationContext.target = DETAILS_SUCCESS;
		
			
			
			
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = DETAILS_SUCCESS; //ICRD-115009  Error for invalid airport shown here
		}
    }
    	
    	
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("DetailsListAccessoriesCommand.java","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try******",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("DetailsListAccessoriesCommand.java","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    log.entering("DetailsListAccessoriesCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(ACCESSORYCODE_ONETIME);    	
    	
    	log.exiting("DetailsListAccessoriesCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}


