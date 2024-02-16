/*
 * ListCarditEnquiryCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class ListCarditEnquiryCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");  
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";	
   /*
    * Module name and Screen id for duplicate flight
    */
   private static final String SCREEN_ID_DUPFLIGHT = 
   					"flight.operation.duplicateflight";
   
   private static final String MODULE_NAME_FLIGHT = 
   					"flight.operation";
   /**
    * TARGET
    */
   private static final String LIST_SUCCESS = "list_success";
   private static final String LIST_FAILURE = "list_failure";
  
    /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListConsignmentCommand","execute");
    	  
    	CarditEnquiryForm carditEnquiryForm = 
    		(CarditEnquiryForm)invocationContext.screenModel;
    	CarditEnquirySession carditEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	if((invocationContext.getErrors() != null &&
    			invocationContext.getErrors().size() > 0)
    			|| AbstractVO.FLAG_YES.equals(
    					carditEnquiryForm.getDuplicateFlightStatus())) {
    		carditEnquiryForm.setScreenStatusFlag(
    				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    		invocationContext.target = LIST_FAILURE;
    		return;
    	}
    	/*
		 * Obtain the duplicate flight session
		 */
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		FlightValidationVO flightValidationVO = 
			duplicateFlightSession.getFlightValidationVO();

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
    	CarditEnquiryFilterVO carditEnquiryFilterVO = 
    		populateCarditFilterVO(carditEnquiryForm, flightValidationVO); 
    	log.log(Log.FINE, "carditEnquiryFilterVO ----->>>",
				carditEnquiryFilterVO);
		carditEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	CarditEnquiryVO carditEnquiryVO = null;
    	try {
			carditEnquiryVO = new MailTrackingDefaultsDelegate().findCarditDetails(
					carditEnquiryFilterVO);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		
		if(carditEnquiryForm.getNotAccepted()==null){
			carditEnquiryForm.setNotAccepted("N");
		}
		log.log(Log.FINE, "carditEnquiryForm.getNotAccepted ----->>>",
				carditEnquiryForm.getNotAccepted());
		log.log(Log.FINE, "carditEnquiryVO ----->>>", carditEnquiryVO);
		carditEnquirySession.setCarditEnquiryVO(carditEnquiryVO);
		if(carditEnquiryVO == null 
				||((carditEnquiryVO.getMailbagVos() == null 
						|| carditEnquiryVO.getMailbagVos().size() == 0)
					&& (carditEnquiryVO.getDespatchDetailVos() == null 
						|| carditEnquiryVO.getDespatchDetailVos().size() == 0)
					&& (carditEnquiryVO.getConsignmentDocumentVos() == null 
						|| carditEnquiryVO.getConsignmentDocumentVos().size() == 0)
					&& (carditEnquiryVO.getContainerVos() == null 
						|| carditEnquiryVO.getContainerVos().size() == 0))) {
			carditEnquiryForm.setScreenStatusFlag(
    				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			ErrorVO error = new ErrorVO(
			 "mailtracking.defaults.carditenquiry.msg.err.noresultfound");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = LIST_SUCCESS;
			return;
		}
		carditEnquiryForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListConsignmentCommand","execute");
	}

	private CarditEnquiryFilterVO populateCarditFilterVO(CarditEnquiryForm carditEnquiryForm,
			FlightValidationVO flightValidationVO) {
		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		carditEnquiryFilterVO.setCarrierCode(
				carditEnquiryForm.getCarrierCode().toUpperCase());
		carditEnquiryFilterVO.setCarrierId(
				flightValidationVO.getFlightCarrierId());
		carditEnquiryFilterVO.setFlightNumber(
				flightValidationVO.getFlightNumber());
		carditEnquiryFilterVO.setLegSerialNumber(
				flightValidationVO.getLegSerialNumber());
		carditEnquiryFilterVO.setFlightSequenceNumber(
				flightValidationVO.getFlightSequenceNumber());
		
		if("O".equals(carditEnquiryForm.getFlightType())){
			if("21".equals(carditEnquiryForm.getResdit())){
				carditEnquiryFilterVO.setFlighDirection("I");
			}else{
				carditEnquiryFilterVO.setFlighDirection("O");
			}
		}else{
			carditEnquiryFilterVO.setFlighDirection("O");
		}
		
		if(carditEnquiryForm.getConsignmentDocument() != null 
				&& carditEnquiryForm.getConsignmentDocument().trim().length() > 0) {
			carditEnquiryFilterVO.setConsignmentDocument(
				carditEnquiryForm.getConsignmentDocument().toUpperCase());
		}
		if(carditEnquiryForm.getNotAccepted()!= null 
				&& carditEnquiryForm.getNotAccepted().trim().length() > 0) {			
			carditEnquiryFilterVO.setNotAccepted(carditEnquiryForm.getNotAccepted());
		}
		else{
			carditEnquiryFilterVO.setNotAccepted("N");
		}
		if(carditEnquiryForm.getFlightType() != null 
				&& carditEnquiryForm.getFlightType().trim().length() > 0) {
			carditEnquiryFilterVO.setFlightType(
				carditEnquiryForm.getFlightType().toUpperCase());
		}
		if(carditEnquiryForm.getFromDate() != null 
				&& carditEnquiryForm.getFromDate().trim().length() > 0) {
			LocalDate fromDate = new LocalDate(
					carditEnquiryForm.getDeparturePort().toUpperCase(),
					Location.ARP, false);
			fromDate.setDate(carditEnquiryForm.getFromDate().toUpperCase());
			carditEnquiryFilterVO.setFromDate(fromDate);
		}
		if(!MailConstantsVO.CARDITENQ_MODE_DOC.equals(carditEnquiryForm.getSearchMode())) {
			if(carditEnquiryForm.getDespatchSerialNumber() != null 
					&& carditEnquiryForm.getDespatchSerialNumber().trim().length() > 0) {
				carditEnquiryFilterVO.setDespatchSerialNumber(
					carditEnquiryForm.getDespatchSerialNumber().toUpperCase());
			}
			if(carditEnquiryForm.getDoe() != null 
					&& carditEnquiryForm.getDoe().trim().length() > 0) {
				carditEnquiryFilterVO.setDoe(
					carditEnquiryForm.getDoe().toUpperCase());
			}
			if(carditEnquiryForm.getMailCategoryCode() != null 
					&& carditEnquiryForm.getMailCategoryCode().trim().length() > 0) {
				carditEnquiryFilterVO.setMailCategoryCode(
					carditEnquiryForm.getMailCategoryCode().toUpperCase());
			}
			if(carditEnquiryForm.getMailSubclass() != null 
					&& carditEnquiryForm.getMailSubclass().trim().length() > 0) {
				carditEnquiryFilterVO.setMailClass(
					carditEnquiryForm.getMailSubclass().toUpperCase());
			}
			if(carditEnquiryForm.getOoe() != null 
					&& carditEnquiryForm.getOoe().trim().length() > 0) {
				carditEnquiryFilterVO.setOoe(
					carditEnquiryForm.getOoe().toUpperCase());
			}
			if(carditEnquiryForm.getReceptacleSerialNumber() != null 
					&& carditEnquiryForm.getReceptacleSerialNumber().trim().length() > 0) {
				carditEnquiryFilterVO.setReceptacleSerialNumber(
					carditEnquiryForm.getReceptacleSerialNumber().toUpperCase());
			}
			if(carditEnquiryForm.getYear() != null 
					&& carditEnquiryForm.getYear().trim().length() > 0) {
				carditEnquiryFilterVO.setYear(
					carditEnquiryForm.getYear().toUpperCase());
			}
		}
		
		if(carditEnquiryForm.getResdit() != null 
				&& carditEnquiryForm.getResdit().trim().length() > 0) {
			carditEnquiryFilterVO.setResdit(
				carditEnquiryForm.getResdit().toUpperCase());
		}
		if(carditEnquiryForm.getSearchMode() != null 
				&& carditEnquiryForm.getSearchMode().trim().length() > 0) {
			carditEnquiryFilterVO.setSearchMode(
				carditEnquiryForm.getSearchMode().toUpperCase());
		}		
		if(carditEnquiryForm.getToDate() != null 
				&& carditEnquiryForm.getToDate().trim().length() > 0) {
			LocalDate toDate = new LocalDate(
					carditEnquiryForm.getDeparturePort().toUpperCase(),
					Location.ARP, false);
			toDate.setDate(carditEnquiryForm.getToDate().toUpperCase());
			carditEnquiryFilterVO.setToDate(toDate);
		}
		return carditEnquiryFilterVO;
	}
	   
}
