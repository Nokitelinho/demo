/*
 * ClearCarditEnquiryCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearCarditEnquiryCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";
   private static final String TARGET = "clear_success";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearConsignmentCommand","execute");    	  
    	CarditEnquiryForm carditEnquiryForm = 
    		(CarditEnquiryForm)invocationContext.screenModel;
    	CarditEnquirySession carditEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	carditEnquirySession.removeCarditEnquiryVO();
		carditEnquirySession.setMailbagVOsCollection(null);
		carditEnquirySession.setMailBagVOsForListing(null);
    	carditEnquiryForm.setCarrierCode("");
    	carditEnquiryForm.setConsignmentDocument("");
    	carditEnquiryForm.setDespatchSerialNumber("");
    	carditEnquiryForm.setDoe("");
    	carditEnquiryForm.setFlightDate("");
    	carditEnquiryForm.setFlightNumber("");
    	carditEnquiryForm.setFromDate("");
    	carditEnquiryForm.setMailCategoryCode("");
    	carditEnquiryForm.setMailSubclass("");
    	carditEnquiryForm.setOoe("");
    	carditEnquiryForm.setReceptacleSerialNumber("");
    	carditEnquiryForm.setResdit("");
    	carditEnquiryForm.setSearchMode("");
    	carditEnquiryForm.setToDate("");
    	carditEnquiryForm.setYear("");	
    	carditEnquiryForm.setPao("");
    	carditEnquiryForm.setPort("");
    	carditEnquiryForm.setDeparturePort("");
    	carditEnquiryForm.setPol("");
    	carditEnquiryForm.setFlightType("");
    	carditEnquiryForm.setYear(""); 
    	carditEnquiryForm.setUldNumber(""); 
    	carditEnquiryForm.setMailStatus("");
    	carditEnquiryForm.setNotAccepted("");
    	carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = TARGET;
    	log.exiting("ClearConsignmentCommand","execute");
    	
    }
       
}
