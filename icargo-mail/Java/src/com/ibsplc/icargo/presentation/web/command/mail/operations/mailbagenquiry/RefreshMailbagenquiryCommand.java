/*
 * RefreshMailbagenquiryCommand.java 
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
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
public class RefreshMailbagenquiryCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	   /**
	    * TARGET
	    */
	   private static final String REFRESH_SUCCESS = "refresh_success";
	   
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
	    	
	    	log.entering("RefreshMailAcceptanceCommand","execute");
	    	  
	    	MailBagEnquiryForm mailBagEnquiryForm = 
	    		(MailBagEnquiryForm)invocationContext.screenModel;
	    	MailBagEnquirySession mailBagEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);  
	    	/*ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
	    	
	    	MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
	    	mailbagEnquiryFilterVO = mailBagEnquirySession.getMailbagEnquiryFilterVO();
	    	
	    	mailBagEnquiryForm.setOriginOE(mailbagEnquiryFilterVO.getOoe());
	    	mailBagEnquiryForm.setDestnOE(mailbagEnquiryFilterVO.getDoe());
	    	mailBagEnquiryForm.setCategory(mailbagEnquiryFilterVO.getMailCategoryCode());
	    	mailBagEnquiryForm.setSubClass(mailbagEnquiryFilterVO.getMailSubclass());
	    	mailBagEnquiryForm.setYear(mailbagEnquiryFilterVO.getYear());
	    	mailBagEnquiryForm.setDsn(mailbagEnquiryFilterVO.getDespatchSerialNumber());
	    	mailBagEnquiryForm.setRsn(mailbagEnquiryFilterVO.getReceptacleSerialNumber());
	    	mailBagEnquiryForm.setConsigmentNumber(mailbagEnquiryFilterVO.getConsigmentNumber());
	    	mailBagEnquiryForm.setUpuCode(mailbagEnquiryFilterVO.getUpuCode());
	    	mailBagEnquiryForm.setCurrentStatus(mailbagEnquiryFilterVO.getCurrentStatus());
	    	mailBagEnquiryForm.setPort(mailbagEnquiryFilterVO.getScanPort());
	    	if(mailbagEnquiryFilterVO.getScanFromDate()!=null){
	    	mailBagEnquiryForm.setFromDate(mailbagEnquiryFilterVO.getScanFromDate().toDisplayDateOnlyFormat());
	    	}
	    	else{
	    	mailBagEnquiryForm.setFromDate("");
	    	}
	    	if(mailbagEnquiryFilterVO.getScanToDate()!=null){
	    	mailBagEnquiryForm.setToDate(mailbagEnquiryFilterVO.getScanToDate().toDisplayDateOnlyFormat());
	    	}
	    	else{
	    		mailBagEnquiryForm.setToDate("");
	    	}
	    	mailBagEnquiryForm.setUserId(mailbagEnquiryFilterVO.getScanUser());
	    	mailBagEnquiryForm.setContainerNo(mailbagEnquiryFilterVO.getContainerNumber());
	    	mailBagEnquiryForm.setFlightCarrierCode(mailbagEnquiryFilterVO.getCarrierCode());
	    	mailBagEnquiryForm.setFlightNumber(mailbagEnquiryFilterVO.getFlightNumber());
	    	if(mailbagEnquiryFilterVO.getFlightDate()!=null){
	    	mailBagEnquiryForm.setFlightDate(mailbagEnquiryFilterVO.getFlightDate().toDisplayDateOnlyFormat());
	    	}
	    	else{
	    		mailBagEnquiryForm.setFlightDate("");
	    	}
	    	if(("Y").equalsIgnoreCase(mailbagEnquiryFilterVO.getDamageFlag()))
	    	{
	    		mailBagEnquiryForm.setDamaged(true);
	    	}
	    	else if(("N").equalsIgnoreCase(mailbagEnquiryFilterVO.getDamageFlag()))
	    	{
	    		mailBagEnquiryForm.setDamaged(false);	
	    	}
	       	mailBagEnquiryForm.setTransit(mailbagEnquiryFilterVO.getTransitFlag());
	    	mailBagEnquiryForm.setCarditStatus(mailbagEnquiryFilterVO.getCarditPresent());
	    	mailBagEnquiryForm.setFromCarrier(mailbagEnquiryFilterVO.getFromCarrier());    	
	    	mailBagEnquiryForm.setMailbagId("");//Added for ICRD-205027
			//mailBagEnquiryForm.setReList("Y");
			mailBagEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);	    	
			invocationContext.target = REFRESH_SUCCESS;	       	
	    	log.exiting("RefreshMailAcceptanceCommand","execute");
}
}