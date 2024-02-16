/*
 * ScreenLoadULDStockSetUpCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadULDStockSetUpCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ULD STOCK SETUP");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
    private static final String MODULE = "uld.defaults";	
	
    private static final String SCREENID ="uld.defaults.maintainuldstock";
   
    private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";
	
    private static final String GHA_CONSTANT="GHA";
	
	private static final String AIRLINE_CONSTANT="airline";
	
	private static final String BLANK = "";
	 
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDStockForm actionForm = 
			(MaintainULDStockForm) invocationContext.screenModel;
    	ListULDStockSetUpSession listULDStockSession =
    		getScreenSession(MODULE, SCREENID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		listULDStockSession.setOneTimeValues(getOneTimeValues());
		
    	actionForm.setAirlineIdentifier(BLANK);
    	actionForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);    	
		
		listULDStockSession.removeAirLineCode();
		if(logonAttributes.isAirlineUser()){
    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
    		actionForm.setStkDisableStatus(AIRLINE_CONSTANT);
    	}
    	else{
    		actionForm.setStationCode(logonAttributes.getAirportCode());
    		actionForm.setStkDisableStatus(GHA_CONSTANT);
    	} 
    	
    	listULDStockSession.removeULDStockConfigVOs();
    	listULDStockSession.removeULDStockDetails();
    	
		//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 30Apr08
	
			if(logonAttributes.isAirlineUser()){
				actionForm.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		listULDStockSession.setAirLineCode(logonAttributes.getOwnAirlineCode());
	    		actionForm.setStkDisableStatus(AIRLINE_CONSTANT);
	    	}
	    	else{
	    		actionForm.setStationCode(logonAttributes.getAirportCode());
	    		actionForm.setStkDisableStatus(GHA_CONSTANT);
	    	} 
		
    	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471) ends
		
		/*if(logonAttributes.isAirlineUser()){
    		listULDStockSession.setAirLineCode(airlineCode);
    		actionForm.setStkDisableStatus("airline");
    	}
    	else{
    		actionForm.setStationCode(logonAttributes.getAirportCode());
    		actionForm.setStkDisableStatus("GHA");
    	} */
		invocationContext.addAllError(error);
        invocationContext.target=SCREENLOAD_SUCCESS;
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
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
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
    	parameterTypes.add(ULDNATURE_ONETIME);    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
    
}
