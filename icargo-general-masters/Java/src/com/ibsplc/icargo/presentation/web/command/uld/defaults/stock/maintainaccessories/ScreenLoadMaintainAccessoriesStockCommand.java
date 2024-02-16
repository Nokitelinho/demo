/*
 * ScreenLoadMaintainAccessoriesStockCommand.java Created on Aug 1, 2005
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

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.MaintainAccessoriesStockSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is invoked on the start up of the 
 *  MaintainAccessoriesStock screen
 * 
 * @author A-1347
 */
public class ScreenLoadMaintainAccessoriesStockCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAINTAIN ACCESSORIES");	
	
	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String SCREEN_ID = 
								"uld.defaults.maintainaccessoriesstock";
   
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
	private static final String ACCESSORYCODE_ONETIME = "uld.defaults.accessoryCode";
	
	private static final String GHA_CONSTANT="GHA";
	
	private static final String AIRLINE_CONSTANT="airline";
    
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
        MaintainAccessoriesStockSessionImpl maintainAccessoriesStockSessionImpl= 
    		(MaintainAccessoriesStockSessionImpl) getScreenSession
													(MODULE_NAME, SCREEN_ID);
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues =getOneTimeValues();
    	maintainAccessoriesStockSessionImpl.setOneTimeValues(oneTimeValues);
    	MaintainAccessoriesStockForm maintainAccessoriesStockForm  = 
    			(MaintainAccessoriesStockForm)invocationContext.screenModel;
    	maintainAccessoriesStockForm.setScreenStatusFlag
    					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	maintainAccessoriesStockSessionImpl.setAccessoriesStockConfigVO(null);
    	AccessoriesStockConfigVO filterVO = new AccessoriesStockConfigVO();
   
		//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
	
			if(logonAttributes.isAirlineUser()){
	    		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		maintainAccessoriesStockForm.setAccessoryDisableStatus(AIRLINE_CONSTANT);
	    	}
	    	else{
	    		filterVO.setStationCode(logonAttributes.getAirportCode());
	    		maintainAccessoriesStockForm.setAccessoryDisableStatus(GHA_CONSTANT);
	    	}
		
    	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471) ends
		    	
    	maintainAccessoriesStockSessionImpl.setAccessoriesStockConfigVO(filterVO);
		invocationContext.addAllError(error);
        invocationContext.target = SCREENLOAD_SUCCESS;
    }
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ScreenLoadCommand","getOneTimeValues");
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
			log.log(Log.FINE, "****inside try*", getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(ACCESSORYCODE_ONETIME);    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}

