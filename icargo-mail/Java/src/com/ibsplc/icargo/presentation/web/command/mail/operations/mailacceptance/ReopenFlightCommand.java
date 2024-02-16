/*
 * ReopenFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ReopenFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILOPERATIONS,ReopenFlightCommand");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "reopen_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	

		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ReopenFlightCommand","execute");
	    	  
	    	MailAcceptanceForm mailAcceptanceForm = (MailAcceptanceForm)invocationContext.screenModel;
	    	MailAcceptanceSession mailAcceptanceSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
	    	OperationalFlightVO operationalFlightVO = mailAcceptanceSession.getOperationalFlightVO();
	    	
	    	log.log(Log.FINE, "\n\n operationalFlightVO----------> \n",
					operationalFlightVO);
			try{
	    		delegate.reopenFlight(operationalFlightVO);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}
	    	
			//Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
			//		mailAcceptanceVO.getFlightNumber(),mailAcceptanceVO.getStrFlightDate()};
			//ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.flightreopened",obj);
			//errors.add(errorVO);
			//invocationContext.addError(errorVO);
	    	
	    	mailAcceptanceSession.setMessageStatus("REOPENED");
			mailAcceptanceForm.setDisableSaveFlag(FLAG_NO);
	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	invocationContext.target = TARGET;

	}

}
