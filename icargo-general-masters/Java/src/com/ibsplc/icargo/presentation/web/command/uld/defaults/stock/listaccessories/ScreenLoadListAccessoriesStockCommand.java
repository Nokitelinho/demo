/*
 * ScreenLoadListAccessoriesStockCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listaccessories;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListAccessoriesStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListAccessoriesStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start 
 * up of the MaintainAccessoriesStock screen
 * 
 * @author A-1347
 */
public class ScreenLoadListAccessoriesStockCommand extends BaseCommand {
	private static final String SCREEN_ID = 
							"uld.defaults.stock.listaccessoriesstock";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String ACCESSORYCODE_ONETIME = "uld.defaults.accessoryCode";
    private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
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
		
		ListAccessoriesStockForm listAccessoriesStockForm = 
			(ListAccessoriesStockForm) invocationContext.screenModel;
    	ListAccessoriesStockSession listAccessriesStockSessionImpl = 
    	(ListAccessoriesStockSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues =getOneTimeValues();
    	listAccessriesStockSessionImpl.setOneTimeValues(oneTimeValues);
    	listAccessriesStockSessionImpl.setAccessoriesStockConfigFilterVO(null);
    	listAccessriesStockSessionImpl.setAccessoriesStockConfigVOs(null);
    	
    	
    	new ICargoComponent().setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
    	
    	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		//removed by nisha on 29Apr08
    	
    	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471) ends
		AccessoriesStockConfigFilterVO filterVO = new AccessoriesStockConfigFilterVO();
		//Added by Tarun for BUG_4642_03Dec08
		
			if(logonAttributes.isAirlineUser()){
	    		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		listAccessoriesStockForm.setListDisableStatus("airline");
	    	}
	    	else{
	    		filterVO.setStationCode(logonAttributes.getAirportCode());
	    		listAccessoriesStockForm.setListDisableStatus("GHA");
	    	}
			listAccessoriesStockForm.setAirlineCode(logonAttributes.getOwnAirlineCode()); 
		
		//Added by Tarun for BUG_4642_03Dec08 ends
    	listAccessriesStockSessionImpl.setAccessoriesStockConfigFilterVO(filterVO);
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
		log.entering("ScreenLoadListAccesriesStockCommand","getOneTimeValues");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
			log.log(Log.FINE, "OneTimeParameterType",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		}catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"caught businessDelegateException");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadListAccessoriesStockCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadListAccessStkComnd","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(ACCESSORYCODE_ONETIME);
   	
    	log.exiting("ScreenLoadListAccesStokComand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}
