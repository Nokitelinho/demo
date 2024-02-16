/*
 * RefreshTransferMailCommand.java 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermail;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3251
 *
 */
public class RefreshTransferMailCommand extends BaseCommand { 
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "refresh_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.transfermail";
	   private static final String SCREEN_ID_MBE = "mailtracking.defaults.mailBagEnquiry";	
	   private static final String SCREEN_ID_DSN = "mailtracking.defaults.dsnEnquiry";	
	  
	   private static final String CONST_FLIGHT = "FLIGHT";
	   
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ScreenloadTransferMailCommand","execute");
	    	  
	    	TransferMailForm transferMailForm = 
	    		(TransferMailForm)invocationContext.screenModel;
	    /*	TransferMailSession transferMailSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailBagEnquirySession mailBagEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID_MBE);
	    	DsnEnquirySession dsnEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID_DSN);*/
	    	
	    	/*ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
			
			transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);			
	    	invocationContext.target = TARGET;
	    	log.exiting("ScreenloadTransferMailCommand","execute");
	    }
	       
	}
