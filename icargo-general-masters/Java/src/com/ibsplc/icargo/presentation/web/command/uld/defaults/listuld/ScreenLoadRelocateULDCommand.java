/*
 * ScreenLoadRelocateULDCommand.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.RelocateULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the RelocateULD popup screen
 * 
 * @author A-2052
 */
public class ScreenLoadRelocateULDCommand extends BaseCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Relocate ULD");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = "uld.defaults.listuld"; 
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
    
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenLoadRelocateULDCommand","execute");
     	ListULDSession listULDSession = (ListULDSession)getScreenSession(MODULE,SCREENID);
    	RelocateULDForm relocateULDForm = (RelocateULDForm) invocationContext.screenModel;
    	
    	String currentStation = null;
    	
    	if(relocateULDForm.getCurrentStation()!=null && relocateULDForm.getCurrentStation().trim().length()>0){
    		currentStation = relocateULDForm.getCurrentStation().toUpperCase();
    	}
    	HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
    	listULDSession.setLocationValues(oneTimeValues);
    	//populateCombo(relocateULDForm,listULDSession);  
    	log.exiting("ScreenLoadRelocateULDCommand","execute");
    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
    
    /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	/*private void populateCombo(RelocateULDForm relocateULDForm,ListULDSession listULDSession){
		log.entering("ScreenLoadRelocateULDCommand","populateCombo");
		/*
		 * Obtain the logonAttributes
		 */
	/*	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		HashMap<String, Collection<String>> locationList = 
			new HashMap<String, Collection<String>>();
		String currentStation = null;
		if(relocateULDForm.getCurrentStation()!=null && relocateULDForm.getCurrentStation().trim().length()>0){
    		currentStation = relocateULDForm.getCurrentStation().toUpperCase();
    	}
		try {
			locationList =	delegate.populateLocation(
					logonAttributes.getCompanyCode(), 
					currentStation);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
//printStackTrrace()();
		}
		log.log(Log.INFO, "locationList ---> " + locationList);
		listULDSession.setLocationValues(locationList);
		log.log(Log.FINE,"listULDSession.getLocationValues()-------->>>>"+listULDSession.getLocationValues());
		log.exiting("ScreenLoadRelocateULDCommand","populateCombo");
		return;
	}*/
    
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
    	parameterTypes.add(FACILITYTYPE_ONETIME);  	
    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}
