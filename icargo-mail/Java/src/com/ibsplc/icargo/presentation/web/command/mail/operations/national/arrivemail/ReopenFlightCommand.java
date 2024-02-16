/* ReopenFlightCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.arrivemail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ArriveDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */
public class ReopenFlightCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILTRACKING,ReopenFlightCommand");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";	

		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ReopenFlightCommand","execute");
	    	  
	    	ArriveDispatchForm arriveDispatchForm = (ArriveDispatchForm)invocationContext.screenModel;
	    	ArriveDispatchSession mailArrivalSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    	
	    	/*ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
	    	
	    	try{
	    		delegate.reopenInboundFlight(mailArrivalSession.getOperationalFlightVO());
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    	}
	    	
			
	    	arriveDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
	    	invocationContext.target = TARGET;
	    	
	    	log.exiting("ReopenFlightCommand","execute");

	}

}
