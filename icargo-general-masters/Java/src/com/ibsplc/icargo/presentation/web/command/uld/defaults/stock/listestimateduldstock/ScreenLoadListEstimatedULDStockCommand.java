/*
 * ScreenLoadListAccessoriesStockCommand.java Created on Sep 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listestimateduldstock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListEstimatedULDStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListExcessStockAirportsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListEstimatedULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start 
 * up of the MaintainAccessoriesStock screen
 * 
 * @author A-2934
 */
public class ScreenLoadListEstimatedULDStockCommand extends BaseCommand {
	private static final String SCREEN_ID = 
							"uld.defaults.stock.listestimateduldstock";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String ACCESSORYCODE_ONETIME = "uld.defaults.accessoryCode";
    private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    private static final String SCREEN_ID_Excess_Stock_Airport = 
		"uld.defaults.stock.findairportswithexcessstock";
    private static final String LIST_STATUS ="noListForm";
    private static final String LIST_SUCCESS = "list_success";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenLoadListEstimatedULDStockCommand","ScreenLoadListEstimatedULDStockCommand");
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ListEstimatedULDStockForm listEstimatedULDStockForm = 
			(ListEstimatedULDStockForm) invocationContext.screenModel;
		
		ListExcessStockAirportsSession listExcessStockAirportsSession =   getScreenSession(MODULE_NAME,SCREEN_ID_Excess_Stock_Airport);
		ListEstimatedULDStockSession listEstimatedULDStockSession =   getScreenSession(MODULE_NAME,SCREEN_ID);
		if(LIST_STATUS.equals(listEstimatedULDStockSession.getListStatus())){
			listEstimatedULDStockForm.setListStatus(LIST_SUCCESS);
		}
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues =getOneTimeValues();
    	listEstimatedULDStockSession.setOneTimeValues(oneTimeValues);
    	listEstimatedULDStockSession.setEstimatedULDStockFilterVO(null);
    	listEstimatedULDStockSession.setEstimatedULDStockVO(null);
    	listEstimatedULDStockSession.setEstimatedULDStockVOs(null);
    	listExcessStockAirportsSession.setExcessStockAirportColl(null);
    	listExcessStockAirportsSession.setFlightSegmentForBookingVOs(null);
    	listExcessStockAirportsSession.setIndexMap(null);
    	listEstimatedULDStockSession.setListStatus(null);
    	listExcessStockAirportsSession.setFltIndexMap(null);
    	listExcessStockAirportsSession.setFlightAvailabilityFilterVO(null);
    	listExcessStockAirportsSession.removeAllAttributes();
    	listEstimatedULDStockSession.removeAllAttributes();
    	
    	new ICargoComponent().setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD); 
    	



		
		listEstimatedULDStockForm.setAirlinecode(logonAttributes.getOwnAirlineCode()); 
		listEstimatedULDStockForm.setAirport(logonAttributes.getAirportCode());
	

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
