/*
 * ScreenloadCommand.java Created on July 01, 2016
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.dispatcher.BatchedResponse;
import com.ibsplc.xibase.client.framework.dispatcher.DispatcherException;
import com.ibsplc.xibase.client.framework.dispatcher.RequestDispatcher;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class ScreenloadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";	
   private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
   private static final String CATEGORY = "mailtracking.defaults.mailcategory";
   private static final String MAILCLASS = "mailtracking.defaults.mailclass";
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
    	  
    	DsnEnquiryForm dsnEnquiryForm = 
    		(DsnEnquiryForm)invocationContext.screenModel;
    	DsnEnquirySession dsnEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = null;
		
		dsnEnquirySession.setDespatchDetailsVOs(null);
    	
    	Collection<String> fieldTypes = new ArrayList<String>();		
		fieldTypes.add(CONTAINERTYPE);
		fieldTypes.add(CATEGORY);
		fieldTypes.add(MAILCLASS);
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
			Collection<OneTimeVO> containerTypes = 
				oneTimeData.get(CONTAINERTYPE);	
			Collection<OneTimeVO> category = 
				oneTimeData.get(CATEGORY);	
			Collection<OneTimeVO> mailclass = 
				oneTimeData.get(MAILCLASS);	
			updateMilitaryClass(mailclass);
			Collection<OneTimeVO> opertnType = 
				oneTimeData.get(OPERATIONTYPE);	
			
			dsnEnquirySession.setContainerTypes(containerTypes);
			dsnEnquirySession.setMailCategory(category);
			dsnEnquirySession.setMailClass(mailclass);
			dsnEnquirySession.setOperationTypes(opertnType);
		}
		
//		dsnEnquiryForm.setMailClass("U");
		dsnEnquiryForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		dsnEnquiryForm.setFromDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dsnEnquiryForm.setToDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dsnEnquiryForm.setPort(logonAttributes.getAirportCode());
		dsnEnquiryForm.setLoginAirport(logonAttributes.getAirportCode());
		dsnEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
    	
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenLoadCommand","execute");
    	
    }
       
	/**
	 * Appends the values of various oneTimeVOs's fieldValues if they all have the
	 * same description 
	 * Feb 7, 2007, A-1739
	 * @param mailClasses
	 */
	private void updateMilitaryClass(Collection<OneTimeVO> mailClasses) {
		log.exiting("ScreenLoadCommand","updateClass");
		if(mailClasses != null && mailClasses.size() > 0) {
			Collection<Collection<OneTimeVO>> mailClassVOs = 
				new ArrayList<Collection<OneTimeVO>>();
			String classDesc = null;
			String fildDes=null;
			Collection<OneTimeVO> militaryVOs = 
				new ArrayList<OneTimeVO>();
			for(OneTimeVO mailClassVO : mailClasses) {
				//finding vos with same description, possibly military mail
				if(mailClassVO.getFieldDescription().equals(classDesc)) {
					militaryVOs.add(mailClassVO);
				} else {
					classDesc = mailClassVO.getFieldDescription();
					militaryVOs = new ArrayList<OneTimeVO>();
					militaryVOs.add(mailClassVO);
					
				}
				if(militaryVOs.size() > 1) {
					String fieldDesc=((ArrayList<OneTimeVO>)militaryVOs).get(0).getFieldDescription();
					if(!fieldDesc.equals(fildDes)){
						mailClassVOs.add(militaryVOs);	
						fildDes=fieldDesc;
					}
				}					
			}
			if(mailClassVOs.size() > 0) { 
				for(Collection<OneTimeVO> oneTimeVOs : mailClassVOs) {
					if(oneTimeVOs.size() > 0) {
						mailClasses.removeAll(oneTimeVOs);				
						StringBuilder oneTimeVal = new StringBuilder();
						for(OneTimeVO militaryVO : oneTimeVOs) {
							oneTimeVal.append(militaryVO.getFieldValue()).
							append(MailConstantsVO.MALCLS_SEP);
						}						
						OneTimeVO mailClassVO = oneTimeVOs.iterator().next();				
						mailClassVO.setFieldValue(
								oneTimeVal.substring(0,oneTimeVal.length()-1).toString());						
						mailClasses.add(mailClassVO);
					}
				}
			}
		}
		log.exiting("ScreenLoadCommand","updateClass");
	}
}
