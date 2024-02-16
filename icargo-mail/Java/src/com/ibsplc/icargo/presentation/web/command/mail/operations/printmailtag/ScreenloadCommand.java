/*
 * ScreenloadCommand.java Created on Oct 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.printmailtag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PrintMailTagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PrintMailTagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class ScreenloadCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.printmailtag";	
   private static final String CATEGORY = "mailtracking.defaults.mailcategory";
   private static final String HNI = "mailtracking.defaults.highestnumbermail";
   private static final String RI = "mailtracking.defaults.registeredorinsuredcode";
   private static final String EXTERNAL_PRINT_PREVIEW = "external_print_preview";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadCommand","execute");
    	  
    	PrintMailTagForm printMailTagForm = 
    		(PrintMailTagForm)invocationContext.screenModel;
    	PrintMailTagSession printMailTagSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
    	
    	Collection<String> fieldTypes = new ArrayList<String>();
    	fieldTypes.add(CATEGORY);
		fieldTypes.add(HNI);
		fieldTypes.add(RI);
		
		Map<String,Collection<OneTimeVO>> oneTimeData
			= new HashMap<String,Collection<OneTimeVO>>();		
    	
    	try { 		
    		
    		oneTimeData = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),fieldTypes) ; 
    		
    	}catch (BusinessDelegateException businessDelegateException) {
    		errors = handleDelegateException(businessDelegateException);
		}
    	
		if (oneTimeData != null) {			
			 List<String> sortedOnetimes ;
   		     Collection<OneTimeVO> hniVOs = oneTimeData.get(HNI);
  		   Collection<OneTimeVO> riVOs = oneTimeData.get(RI);
  		 if(hniVOs!=null && !hniVOs.isEmpty()){
			  sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO hniVo: hniVOs){  
					sortedOnetimes.add(hniVo.getFieldValue());          
				}
				Collections.sort(sortedOnetimes);          
			
			
			int i=0;
			for(OneTimeVO hniVo: hniVOs){
				hniVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			if(riVOs!=null && !riVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO riVo: riVOs){
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);    
			
			
			int i=0;
			for(OneTimeVO riVo: riVOs){
				riVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			
			
			printMailTagSession.setOneTimeCat(oneTimeData.get(CATEGORY));
			printMailTagSession.setOneTimeHNI(hniVOs);
			printMailTagSession.setOneTimeRI(riVOs);
		}
	    
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		if(!Objects.equals(EXTERNAL_PRINT_PREVIEW,printMailTagForm.getFlag())) {
			printMailTagSession.setMailbagVOs(mailbagVOs);
		}
		
		
		printMailTagForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
        	
    	invocationContext.target = TARGET;
       	
    	log.exiting("ScreenloadCommand","execute");
    	
    }
       
}
