/*
 * ScreenloadCommand.java Created on July 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.peakfileupload;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PeakFileUploadSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PeakFileUploadForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2001
 */
public class ScreenloadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.peakfileupload";	
   
   private static final String ONETIME_OPERATION = "mailtracking.defaults.peakoperation";  
   
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadCommand","execute");		
    	PeakFileUploadForm peakFileUploadForm = 
    		(PeakFileUploadForm)invocationContext.screenModel;
    	PeakFileUploadSession peakFileUploadSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);    	
    	peakFileUploadSession.setOneTimeVOs(getOneTimeValues());
    	peakFileUploadForm.setScreenStatusFlag(
    			ComponentAttributeConstants.SCREEN_STATUS_DETAIL);		
    	invocationContext.target = TARGET;
    	log.exiting("ScreenloadCommand","execute");
    	
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
		
		Collection<OneTimeVO> operationTypes = oneTimeValues.get(ONETIME_OPERATION);
		
		Collection<OneTimeVO> newOperationTypes = new ArrayList<OneTimeVO>();
		if(operationTypes != null && operationTypes.size() > 0) {
			for(OneTimeVO oneTimeVO:operationTypes){
				if("D".equals(oneTimeVO.getFieldValue())){
					newOperationTypes.add(oneTimeVO);
				}
			}
			for(OneTimeVO oneTimeVO:operationTypes){
				if(!"D".equals(oneTimeVO.getFieldValue())){
					newOperationTypes.add(oneTimeVO);
				}
			}
		}
		oneTimeValues.put(ONETIME_OPERATION,newOperationTypes);
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
    	parameterTypes.add(ONETIME_OPERATION);   	
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
       
	
}
