/*
 * ScreenloadConsignmentCommand.java Created on Jan 04, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4826
 *
 */
public class ScreenloadConsignmentCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";	
   
   
	 /**    
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ConsignmentSession consignmentSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	CaptureMailDocumentForm captureMailDocumentForm =  (CaptureMailDocumentForm)invocationContext.screenModel;
    	captureMailDocumentForm.setScreenLoadFlag("Y");
    	captureMailDocumentForm.setConsignmentOriginFlag("N");
    	captureMailDocumentForm.setDataFlag("N");
    	captureMailDocumentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	populateOneTimes(consignmentSession);
    	invocationContext.target = TARGET;
    	
    	
    }
    
    private  void  populateOneTimes(ConsignmentSession consignmentSession){
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	 Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
  	   if(oneTimes!=null){
  		   Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
  		   Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
  		   Collection<OneTimeVO> rsnVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
  		   Collection<OneTimeVO> mailClassVOs = oneTimes.get("mailtracking.defaults.mailclass");
  		   Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
  		   log.log(Log.FINE, "*******Getting OneTimeVOs***catVOs***", catVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***rsnVOs***", rsnVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***", hniVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***hniVOs***", mailClassVOs.size());
		log.log(Log.FINE, "*******Getting OneTimeVOs***typeVOs***", typeVOs.size());
		consignmentSession.setOneTimeCat(catVOs);
  		   consignmentSession.setOneTimeRSN(rsnVOs);
  		   consignmentSession.setOneTimeHNI(hniVOs);
  		   consignmentSession.setOneTimeMailClass(mailClassVOs);
  		   consignmentSession.setOneTimeType(typeVOs);
  		}
    	
    }
    
    /**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.mailclass");
			fieldValues.add("mailtracking.defaults.consignmentdocument.type");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
    	
       
}
