/*
 * ScreenloadCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.dispatcher.BatchedResponse;
import com.ibsplc.xibase.client.framework.dispatcher.DispatcherException;
import com.ibsplc.xibase.client.framework.dispatcher.RequestDispatcher;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ScreenloadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
   private static final String CURRENTSTATUS = "mailtracking.defaults.mailstatus";
   private static final String CATEGORY = "mailtracking.defaults.mailcategory";
   private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
   private static final String OPERATIONTYPE = "mailtracking.defaults.operationtype";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenLoadCommand","execute");
    	  
    	MailBagEnquiryForm mailBagEnquiryForm = 
    		(MailBagEnquiryForm)invocationContext.screenModel;
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		
		mailBagEnquirySession.setMailbagVOs(null);
    	
    	Collection<String> fieldTypes = new ArrayList<String>();		
		fieldTypes.add(CURRENTSTATUS);
		fieldTypes.add(CATEGORY);
		fieldTypes.add(CONTAINERTYPE);
		fieldTypes.add(OPERATIONTYPE);
		
		Map<String,Collection<OneTimeVO>> oneTimeData
			= new HashMap<String,Collection<OneTimeVO>>();		
    	
    	/*
		 * Start the batch processing
		 */
		RequestDispatcher.startBatch();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();		
    	try { 		
    		
    		sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					fieldTypes);  
    		
    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
    	
    	/*
		 * Obtain the responses after the batch fetch
		 */
		try {
			BatchedResponse response[] = RequestDispatcher.executeBatch();
			log.log(Log.INFO, "Response length:--", response.length);
			if(!response[0].hasError()) {
				oneTimeData = (HashMap<String,Collection<OneTimeVO>>)response[0].getReturnValue();
				log.log(Log.INFO, "oneTimeData:--", oneTimeData);
			}	
				
		}catch (DispatcherException dispatcherException) {
			dispatcherException.getMessage();
		}
		
		if (oneTimeData != null) {			
			Collection<OneTimeVO> status = 
				oneTimeData.get(CURRENTSTATUS);	
			Collection<OneTimeVO> category = 
				oneTimeData.get(CATEGORY);	
			Collection<OneTimeVO> containerType = 
				oneTimeData.get(CONTAINERTYPE);	
			Collection<OneTimeVO> operationType = 
				oneTimeData.get(OPERATIONTYPE);	
			
			Collection<OneTimeVO> curStatus = new ArrayList<OneTimeVO>();
			if(status != null && status.size() > 0){
				for(OneTimeVO oneTimeVO:status){
					if(!oneTimeVO.getFieldDescription().contains("Resdit")
							&& !"CDT".equals(oneTimeVO.getFieldValue())
							&& !"MFT".equals(oneTimeVO.getFieldValue())){
						curStatus.add(oneTimeVO);
					}
				}
			}
			
			mailBagEnquirySession.setCurrentStatus(curStatus);
			mailBagEnquirySession.setMailCategory(category);
			mailBagEnquirySession.setContainerTypes(containerType);
			mailBagEnquirySession.setOperationTypes(operationType);
		}
	    
		mailBagEnquiryForm.setPort(logonAttributes.getAirportCode());
	//	mailBagEnquiryForm.setPort("BOM");
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		mailBagEnquiryForm.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailBagEnquiryForm.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailBagEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
        	
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenLoadCommand","execute");
    	
    }
       
}
