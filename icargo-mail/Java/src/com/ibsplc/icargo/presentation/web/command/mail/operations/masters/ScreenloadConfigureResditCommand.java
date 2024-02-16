/*
 * ScreenloadConfigureResditCommand.java Created on July 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ResditConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditTransactionDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConfigureResditSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConfigureResditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2001
 *
 */
public class ScreenloadConfigureResditCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.configureresdit";	
   
   private static final String ONETIME_MILESTONE = "mailtracking.defaults.resdit.transaction";
   
   
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadCarditEnquiryCommand","execute");
    	  
    	ConfigureResditForm configureResditForm = 
    		(ConfigureResditForm)invocationContext.screenModel;
    	ConfigureResditSession configureResditSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	//configureResditSession.removeCarditEnquiryVO();
    	HashMap<String, Collection<OneTimeVO>> oneTimeValues = 
    					getOneTimeValues();
    	
    	
    	Collection<OneTimeVO> milestoneOntimes = new ArrayList<OneTimeVO>();
    	
    	ArrayList<OneTimeVO> milestoneArrayList = (ArrayList<OneTimeVO>)oneTimeValues.get(ONETIME_MILESTONE);
    	milestoneOntimes.add(milestoneArrayList.get(0));
    	milestoneOntimes.add(milestoneArrayList.get(2));
    	milestoneOntimes.add(milestoneArrayList.get(3));
    	milestoneOntimes.add(milestoneArrayList.get(1));
    	milestoneOntimes.add(milestoneArrayList.get(5));
    	milestoneOntimes.add(milestoneArrayList.get(4));
    	
    	ResditConfigurationVO resditConfigurationVO = new ResditConfigurationVO(); 
    	Collection<ResditTransactionDetailVO> resditTransactionDetailVOs =
    		new ArrayList<ResditTransactionDetailVO>();
    	for(OneTimeVO milestoneOntime : milestoneOntimes) {
    		ResditTransactionDetailVO resditTransactionDetailVO =
    			new ResditTransactionDetailVO();
    		resditTransactionDetailVO.setTransaction(
    				milestoneOntime.getFieldValue());
    		resditTransactionDetailVO.setReceivedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setAssignedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setHandedOverResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setUpliftedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setLoadedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setHandedOverReceivedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setDeliveredResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setReadyForDeliveryFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setTransportationCompletedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setArrivedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setReturnedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVOs.add(resditTransactionDetailVO);
    	}
    	resditConfigurationVO.setResditTransactionDetails(resditTransactionDetailVOs);
    	configureResditSession.setResditConfigurationVO(resditConfigurationVO);
    	configureResditSession.setOneTimeVOs(oneTimeValues);
    	
    	configureResditForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = TARGET;
    	log.exiting("ScreenloadCarditEnquiryCommand","execute");
    	
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
		
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
//printStackTrrace()();
			handleDelegateException(businessDelegateException);
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
    	parameterTypes.add(ONETIME_MILESTONE);    	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
       
}
