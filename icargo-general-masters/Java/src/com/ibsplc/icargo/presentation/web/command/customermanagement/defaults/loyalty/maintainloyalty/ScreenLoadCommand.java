/*
 * ScreenLoadCommand.java Created on Apr 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.maintainloyalty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.MaintainLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.MaintainLoyaltyForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ScreenLoadCommand  extends BaseCommand {

	
	private Log log = LogFactory.getLogger("ScreenLoadCommand");
	
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
    private static final String SCREENID = "customermanagement.defaults.maintainloyalty";
    private static final String MODULE = "customermanagement.defaults";
    
    private static final String EXPIRYPERIOD_ONETIME = "customer.addloyaltyparams.periodforredumption";
    private static final String STATUS_ONETIME = "customer.addloyaltyparams.activestatus";
   // private static final String PARAMETERS_ONETIME = "customer.addloyaltyparams.parameter";
   
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
		String  compCode = logonAttributes.getCompanyCode();
		MaintainLoyaltyForm maintainLoyaltyForm = 
			(MaintainLoyaltyForm) invocationContext.screenModel;
		MaintainLoyaltySession maintainLoyaltySession = 
			(MaintainLoyaltySession)getScreenSession(MODULE,SCREENID);
		
		maintainLoyaltySession.setLoyaltyProgrammeVO(null);
		maintainLoyaltySession.setLoyaltyNames(null);
		maintainLoyaltySession.setPageURL(null);
		maintainLoyaltySession.setParentScreenId(null);
		maintainLoyaltySession.setLoyaltyProgrammeLovVOs(null);	
		maintainLoyaltySession.setAttributeValue(null);
		maintainLoyaltySession.setUnitValue(null);
		maintainLoyaltySession.setAmountValue(null);
		maintainLoyaltySession.setPointsValue(null);
		maintainLoyaltySession.setParameterVOsForDisplay(null);
		maintainLoyaltySession.setParameterVOsForLOV(null);
		maintainLoyaltyForm.setCloseWindow(false);
		maintainLoyaltyForm.setScreenStatusValue("SCREENLOAD");
		//maintainLoyaltyForm.setScreenStatusFlag("SCREENLOAD");
	
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
		oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
		compCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		handleDelegateException(e);
		}
		maintainLoyaltySession.setOneTimeValues(
		(HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
		
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate =
			new CustomerMgmntDefaultsDelegate();
		Map<String, Collection<LoyaltyAttributeVO>> attributeValues = null;
		LinkedHashMap<String, Collection<String>> att=new LinkedHashMap<String, Collection<String>>();
		
		try {
			attributeValues =	customerMgmntDefaultsDelegate.listLoyaltyAttributeDetails(compCode);
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
		handleDelegateException(e);
		}
		
		maintainLoyaltySession.setAttributeValues(
		(HashMap<String, Collection<LoyaltyAttributeVO>>)attributeValues);
		
		for(String loy:attributeValues.keySet()){
			Collection<String> string=new ArrayList<String>();
			Collection<LoyaltyAttributeVO> laVO=attributeValues.get(loy);
			for(LoyaltyAttributeVO loyaltyAttributeVO:laVO){
				
				string.add(loyaltyAttributeVO.getUnitDescription());
			}
			att.put(loy,string);
		}
		maintainLoyaltySession.setAttributeString(
				(HashMap<String, Collection<String>>)att);
		
		Collection<ParameterDescriptionVO> parameters = findParameters(compCode);
		maintainLoyaltySession.setParameter((ArrayList<ParameterDescriptionVO>)parameters);
	    	
		
        invocationContext.target=SCREENLOAD_SUCCESS;
    }
 
    /**
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(EXPIRYPERIOD_ONETIME);
    	parameterTypes.add(STATUS_ONETIME);
    	//parameterTypes.add(PARAMETERS_ONETIME);
    	
    	    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
 
    /**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Collection<ParameterDescriptionVO> findParameters(String companyCode){
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate =
			new CustomerMgmntDefaultsDelegate();
		Collection<ParameterDescriptionVO> param = new ArrayList<ParameterDescriptionVO>();
		try{
			param = customerMgmntDefaultsDelegate.findParameterDetails(companyCode);
		}catch(BusinessDelegateException exception){
//printStackTrrace()();
			handleDelegateException( exception);
		}
		return param;
	}

}
