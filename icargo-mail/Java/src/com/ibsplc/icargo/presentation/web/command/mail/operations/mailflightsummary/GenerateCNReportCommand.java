/*
 * GenerateCNReportCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailflightsummary;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailFlightSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailFlightSummaryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class GenerateCNReportCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";	
   
	
   private static final String TARGET_SUCCESS = "print_success";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("GenerateCNReportCommand","execute");
    	
    	MailFlightSummaryForm mailFlightSummaryForm = 
    		(MailFlightSummaryForm)invocationContext.screenModel;
    	MailFlightSummarySession mailFlightSummarySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();    	    
    	
    	OperationalFlightVO operationalFlightVO = 
    		mailFlightSummarySession.getOperationalFlightVO();
    	log.log(Log.FINE, "operationalFlightVO---------->>",
				operationalFlightVO);
		try {			
			mailTrackingDefaultsDelegate.generateControlDocumentReport(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}	
		//mailFlightSummaryForm.setScreenStatFlag("LIST");
		
    	mailFlightSummaryForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET_SUCCESS;
    	log.exiting("GenerateCNReportCommand","execute");
    	
    }
	
       
}
