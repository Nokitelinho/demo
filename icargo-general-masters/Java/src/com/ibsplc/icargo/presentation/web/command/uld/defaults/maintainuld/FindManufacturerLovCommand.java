/*
 * FindManufacturerLovCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

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
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ManufacturerLovForm;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author 
 * Class to find the FindManufacturerLovCommand
 */
public class FindManufacturerLovCommand extends BaseCommand {

	private static final String MANUFACTURER_SUCCESS="manufaturer_success";
	private static final String MANUFACTURER_ONETIME = "uld.defaults.manufacturer";
	private Log log = LogFactory.getLogger("FindManufacturerLovCommand");
	
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */		
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		ManufacturerLovForm manufacturerLovForm= (ManufacturerLovForm)invocationContext.screenModel;
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		log.log(Log.FINE, "oneTimeValues------------>>", oneTimeValues);
		Collection<String> manufacturers = new ArrayList<String>();	
		int val = -1;
		if (oneTimeValues.keySet() != null && oneTimeValues.keySet().size() > 0) {
		      for(String manufacturer:oneTimeValues.keySet()) {
		    	  Collection<OneTimeVO> col = oneTimeValues.get(manufacturer);
		    	  log.log(Log.FINE, "col------------>>", col);
				for(OneTimeVO vo:col){	    		  
		    		  log.log(Log.FINE, " i------------>>", val);
					if(!("").equals(vo.getFieldValue())){
		    			  val++;
		    			  log.log(Log.FINE, " i------------>>", val);
						log.log(Log.FINE, " vo.getFieldValue()------------>>",
								vo.getFieldValue());
						manufacturers.add(vo.getFieldValue());	
		    		  }
		    	  }
		      }
		}
		log.log(Log.FINE, "manufacturers------------>>", manufacturers);
		manufacturerLovForm.setManufacturer(manufacturers);
		invocationContext.target =MANUFACTURER_SUCCESS;
	}
	 /**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("FindManufacturerLovCommand","getOneTimeValues");
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
		log.exiting("FindManufacturerLovCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("FindManufacturerLovCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();    	
    	parameterTypes.add(MANUFACTURER_ONETIME);      	
    	log.exiting("FindManufacturerLovCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
    
}
