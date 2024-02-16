/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry.mailbooking.ClearCommand.java
 *
 *	Created by	:	A-7531
 *	Created on	:	08-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.mailbooking;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.xaddons.bs.mail.operations.MailbookingPopupForm;




import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry.mailbooking.ClearCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	08-Aug-2017	:	Draft
 */
public class ClearCommand extends BaseCommand{

	 private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   private static final String MODULE_NAME = "bsmail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	   private static final String TARGET = "clearmail_success";
	   
	  /* 
	   This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ClearCommand","execute");    	  
	    	MailbookingPopupForm mailPopupForm = 
	    		(MailbookingPopupForm)invocationContext.screenModel;
	    	SearchConsignmentSession carditEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	mailPopupForm.setAgentCode("");
	    	mailPopupForm.setBookingCarrierCode("");
	    	mailPopupForm.setBookingFlightFrom("");
	    	mailPopupForm.setBookingFlightNumber("");
	    	mailPopupForm.setBookingFlightTo("");
	    	mailPopupForm.setBookingFrom("");
	    	mailPopupForm.setBookingStatus("");
	    	mailPopupForm.setBookingTo("");
	    	mailPopupForm.setBookingUserId("");
	    	mailPopupForm.setCustomerCode("");
	    	mailPopupForm.setDestinationOfBooking("");
	    	mailPopupForm.setMailProduct("");
	    	mailPopupForm.setMailScc("");
	    	mailPopupForm.setMasterDocumentNumber("");
	    	mailPopupForm.setOrginOfBooking("");
	    	mailPopupForm.setShipmentDate("");
	    	mailPopupForm.setShipmentPrefix("");
	    	mailPopupForm.setStationOfBooking("");
	    	mailPopupForm.setViaPointOfBooking("");
	    	carditEnquirySession.setMailBookingDetailsVOs(null);
	    	carditEnquirySession.setselectedMailbagVOs(null);
	    	carditEnquirySession.setMailBookingDetailsVO(null);
	   
	    	invocationContext.target = TARGET;
	    	log.exiting("ClearCommand","execute");
	    	
	    }
	       

}


