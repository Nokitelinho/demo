/*
 * RefreshDsnCommand.java 
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * Author(s)			: A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;



import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5991
 *
 */
public class RefreshDsnCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "refresh_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";		  
	  
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ScreenLoadCommand","execute");
	    	  
	    	DsnEnquiryForm dsnEnquiryForm = 
	    		(DsnEnquiryForm)invocationContext.screenModel;
	    	DsnEnquirySession dsnEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	DSNEnquiryFilterVO dSNEnquiryFilterVO = new DSNEnquiryFilterVO();	    	
	    	dSNEnquiryFilterVO=dsnEnquirySession.getDsnEnquiryFilterVO();
	    	dsnEnquiryForm.setDsn(dSNEnquiryFilterVO.getDsn());
	    	dsnEnquiryForm.setConsignmentNo(dSNEnquiryFilterVO.getConsignmentNumber());
	    	dsnEnquiryForm.setPostalAuthorityCode(dSNEnquiryFilterVO.getPaCode());
	    	dsnEnquiryForm.setOriginCity(dSNEnquiryFilterVO.getOriginCity());
	    	dsnEnquiryForm.setDestnCity(dSNEnquiryFilterVO.getDestinationCity());
	    	dsnEnquiryForm.setCategory(dSNEnquiryFilterVO.getMailCategoryCode());
	    	dsnEnquiryForm.setMailClass(dSNEnquiryFilterVO.getMailClass());
	     	if(dSNEnquiryFilterVO.getFromDate()!=null) {
				dsnEnquiryForm.setFromDate(dSNEnquiryFilterVO.getFromDate().toDisplayDateOnlyFormat());
			} else {
				dsnEnquiryForm.setFromDate("");
			}
	    	if(dSNEnquiryFilterVO.getToDate()!=null) {
				dsnEnquiryForm.setToDate(dSNEnquiryFilterVO.getToDate().toDisplayDateOnlyFormat());
			} else {
				dsnEnquiryForm.setToDate("");
			}
	    	dsnEnquiryForm.setFlightCarrierCode(dSNEnquiryFilterVO.getCarrierCode());
	    	dsnEnquiryForm.setFlightNumber(dSNEnquiryFilterVO.getFlightNumber());
	    	if(dSNEnquiryFilterVO.getFlightDate()!=null) {
				dsnEnquiryForm.setFlightDate(dSNEnquiryFilterVO.getFlightDate().toDisplayDateOnlyFormat());
			} else {
				dsnEnquiryForm.setFlightDate("");
			}
	    	dsnEnquiryForm.setOperationType(dSNEnquiryFilterVO.getOperationType());
	    	dsnEnquiryForm.setPort(dSNEnquiryFilterVO.getAirportCode());
	    	dsnEnquiryForm.setContainerType(dSNEnquiryFilterVO.getContainerType());
	    	dsnEnquiryForm.setUldNo(dSNEnquiryFilterVO.getContainerNumber());
	    	
	    	if(("Y").equalsIgnoreCase(dSNEnquiryFilterVO.getPltEnabledFlag()))
	    	{
	    		dsnEnquiryForm.setPlt(true);
	    	}
	    	else if(("N").equalsIgnoreCase(dSNEnquiryFilterVO.getPltEnabledFlag()))
	    	{
	    		dsnEnquiryForm.setPlt(false);	
	    	}
	    	 dsnEnquiryForm.setTransit(dSNEnquiryFilterVO.getTransitFlag());	
	    	 dsnEnquiryForm.setReList("Y");
	    	 
			dsnEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	
	    	invocationContext.target = TARGET;
	       	
	    	log.exiting("ScreenLoadCommand","execute");
	    	
	    }
	       
}