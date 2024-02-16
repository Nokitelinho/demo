/*
 * ClearCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "clear_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearCommand","execute");
    	  
    	MailBagEnquiryForm mailBagEnquiryForm = 
    		(MailBagEnquiryForm)invocationContext.screenModel;
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	    	
    	mailBagEnquiryForm.setCategory("");
    	mailBagEnquiryForm.setContainerNo("");
    	mailBagEnquiryForm.setCurrentStatus("");
    	mailBagEnquiryForm.setDamaged(false);
    	mailBagEnquiryForm.setDestnOE("");
    	mailBagEnquiryForm.setDsn("");
    	mailBagEnquiryForm.setFlightCarrierCode("");
    	mailBagEnquiryForm.setFlightDate("");
    	mailBagEnquiryForm.setFlightNumber("");    	
    	mailBagEnquiryForm.setOriginOE("");    	
    	mailBagEnquiryForm.setRsn("");
    	mailBagEnquiryForm.setSubClass("");    	
    	mailBagEnquiryForm.setUserId("");
    	mailBagEnquiryForm.setYear("");
    	mailBagEnquiryForm.setFromScreen("");
    	mailBagEnquiryForm.setCarditStatus("");
    	mailBagEnquiryForm.setTransit("");
    	//Added for ICRD-133697 starts
    	mailBagEnquiryForm.setConsigmentNumber("");
    	mailBagEnquiryForm.setUpuCode("");
    	//Added for ICRD-133697 ends
    	mailBagEnquiryForm.setMailbagId("");//Added for ICRD-205027
    	mailBagEnquiryForm.setReqDeliveryTime("");
    	mailBagEnquiryForm.setReqDeliveryDate("");
    	
    	//int arrays=mailBagEnquiryForm.getCarditStatus().indexOf("_");
    	
    	
	    
    	LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		mailBagEnquiryForm.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailBagEnquiryForm.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
    	mailBagEnquiryForm.setPort(logonAttributes.getAirportCode());
		mailBagEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailBagEnquirySession.setMailbagVOs(null);	
    	invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("ClearCommand","execute");
    	
    }
       
}
