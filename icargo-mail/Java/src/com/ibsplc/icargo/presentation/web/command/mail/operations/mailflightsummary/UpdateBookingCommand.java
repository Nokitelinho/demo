/*
 * UpdateBookingCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailflightsummary;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.capacity.booking.vo.MailBookingVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInFlightSummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailFlightSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailFlightSummaryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class UpdateBookingCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");	
   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";	  
   
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {    	
    	log.entering("AssignDocScreenloadCommand","execute");    	  
    	MailFlightSummaryForm mailFlightSummaryForm = 
    		(MailFlightSummaryForm)invocationContext.screenModel;    	
    	MailFlightSummarySession mailFlightSummarySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID); 
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();			
		mailFlightSummaryForm.setPort(logonAttributes.getAirportCode());
		Collection<ErrorVO> errors = null;
		Collection<MailBookingVO> bookingVOs = mailFlightSummarySession.getBookingVOs();
		
		String[] primaryKey = mailFlightSummaryForm.getSelectUBR();
		
		MailBookingVO mailBookingVO = (MailBookingVO)((ArrayList)bookingVOs).get(Integer.parseInt(primaryKey[0]));
		Collection<MailInFlightSummaryVO> summaryVOs = mailFlightSummarySession.getSummaryVOs();
		
		String selected = mailFlightSummaryForm.getSelectBooking();
		String[] selArr = selected.split(",");
		Collection<MailInFlightSummaryVO> newSummaryVOs = new ArrayList<MailInFlightSummaryVO>();
		for(int sel=0;sel<selArr.length;sel++){
			newSummaryVOs.add((MailInFlightSummaryVO)((ArrayList)summaryVOs).get(Integer.parseInt(selArr[sel])));
		}
		
		try {
			new MailTrackingDefaultsDelegate().updateBookingDetailsForMail(
												newSummaryVOs,mailBookingVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "bookingVOs------>>", bookingVOs);
		mailFlightSummarySession.setBookingVOs(bookingVOs);
		
		mailFlightSummaryForm.setPopupStatus("CLOSE");
		mailFlightSummaryForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
    	invocationContext.target = TARGET;       	
    	log.exiting("AssignDocScreenloadCommand","execute");    	
    }       
}
