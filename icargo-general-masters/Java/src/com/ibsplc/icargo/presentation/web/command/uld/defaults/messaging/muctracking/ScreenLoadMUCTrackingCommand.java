/*
 * ScreenLoadMUCTrackingCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class ScreenLoadMUCTrackingCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MUC Tracking");
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of MUC Tracking
	 */
	private static final String SCREENID = "uld.defaults.messaging.muctracking";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	private static final String MUC_IATA_STATUS = "uld.defaults.muciatastatus";
	
	private static final String CONDITIONCODE_ONETIME = "uld.defaults.conditioncode";
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {	
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE,
				SCREENID);
		mucTrackingSession.setListFilterVO(null);
		mucTrackingSession.setListDisplayColl(null);
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		log.log(Log.INFO, "oneTimeValues gcfhgfhjg---> ", oneTimeValues);
		Collection<OneTimeVO> mucIataStatus = oneTimeValues.get(MUC_IATA_STATUS);
		Collection<OneTimeVO> iataStatus =new ArrayList<OneTimeVO>();
		for(OneTimeVO vo : mucIataStatus){
			if(!"N".equals(vo.getFieldValue())){
				iataStatus.add(vo);	
			}
		}	
		oneTimeValues.remove(MUC_IATA_STATUS);
		oneTimeValues.put(MUC_IATA_STATUS, iataStatus);
		log.log(Log.INFO, "oneTimeValues herecomes---> ", oneTimeValues);
		mucTrackingSession.setOneTimeValues(oneTimeValues);
		Collection<OneTimeVO> conditionCodes = oneTimeValues.get(CONDITIONCODE_ONETIME);
		log.log(Log.FINE, "****conditionCodes  OneTime******", conditionCodes);
		mucTrackingSession.setConditionCodes(conditionCodes);
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
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
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
    	parameterTypes.add(MUC_IATA_STATUS);    
    	parameterTypes.add(CONDITIONCODE_ONETIME);
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
	
}
