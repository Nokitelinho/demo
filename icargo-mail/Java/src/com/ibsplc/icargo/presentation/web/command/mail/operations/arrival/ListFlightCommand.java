/*
 * ListFlightCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @ author Roopak
 * Command class for List FlightDetails.
 *
 * Revision History
 *
 * Version      Date        Author         Description
 *
 *  0.1         Jun 30 2016  A-5991         Coding
 */
public class ListFlightCommand extends BaseCommand {
    /**
     * Log
     */
    private Log log = LogFactory.getLogger("MAILOPERATIONS");

    
    
    private static final String MODULE_NAME = "mail.operations";	
    
    private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
    
    /**
     * Screen id
     */
    private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   
    /**
     * Screen id
     */
    private static final String SCREEN_ID_FLIGHT = "flight.operation.maintainflight";

    /**
     * Module name
     */
    private static final String MODULE_NAME_FLIGHT =  "flight.operation";
    
   /**
     * Target string
     */
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    /**
     * Target string
     */
    private static final String SCREENLOAD_FAILURE = "screenload_failure";
    
    /**
     * Target string
     */
    private static final String DUPLICATE_SUCCESS = "duplicate_success";
    
    /**
     *  Status of flag
     */
    private static final String INBOUND = "I";
    
    private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
    

    /**
     * Execute method
     * @param invocationContext InvocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ListFlightCommand", "execute");
        
        MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
        MailArrivalSession mailArrivalSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		mailArrivalForm.setListFlag("");
		mailArrivalForm.setOperationalStatus("");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		
		mailArrivalSession.setMailArrivalFilterVO(null);
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		mailArrivalVO.setAirportCode(mailArrivalForm.getArrivalPort());
		mailArrivalVO.setMailStatus(mailArrivalForm.getMailStatus());
		
		mailArrivalForm.setDuplicateFlightStatus(FLAG_NO);
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    AirlineDelegate airlineDelegate = new AirlineDelegate();
	    
    	errors = validateForm(mailArrivalForm);
    	if (errors != null && errors.size() > 0) {  
    		mailArrivalForm.setListFlag("FAILURE");
    		
    		invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
    	}
    	
    	String flightNum = (mailArrivalForm.getFlightNumber().toUpperCase());
    	mailArrivalVO.setFlightNumber(flightNum);
    	mailArrivalVO.setFlightCarrierCode(mailArrivalForm.getFlightCarrierCode().toUpperCase());
    	
    	FlightFilterVO flightFilterVO = handleFlightFilterVO(
    			mailArrivalForm,logonAttributes);
    	mailArrivalVO.setArrivalDate(flightFilterVO.getFlightDate());
    	mailArrivalSession.setMailArrivalVO(mailArrivalVO);
    	
    	AirlineValidationVO airlineValidationVO = null;
	    String flightCarrierCode = mailArrivalForm.getFlightCarrierCode().trim().toUpperCase();        	
    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
    		try {        			
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),flightCarrierCode);
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {flightCarrierCode};
    			mailArrivalForm.setListFlag("FAILURE");
    			
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
    		}
    	}
		
    	AirlineValidationVO airlineValidVO = null;
	    String transferCarrier = mailArrivalForm.getTransferCarrier().trim().toUpperCase();        	
    	if (transferCarrier != null && !"".equals(transferCarrier)) {        		
    		try {        			
    			airlineValidVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),transferCarrier);
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {transferCarrier};
    			mailArrivalForm.setListFlag("FAILURE");
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invalidtransfercarrier",obj));
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
    		}
    		mailArrivalVO.setTransferCarrierId(airlineValidVO.getAirlineIdentifier());	
    	}
    	mailArrivalVO.setTransferCarrier(transferCarrier);
    	
    	
//    	validate PA code
    	String paCode = mailArrivalForm.getArrivalPA().toUpperCase();
    	PostalAdministrationVO postalAdministrationVO = null;
    	if (paCode != null && !"".equals(paCode)) {   
	  		try {
	  			
	  			postalAdministrationVO = new MailTrackingDefaultsDelegate().findPACode(
		  						logonAttributes.getCompanyCode(),paCode);
	  		}catch (BusinessDelegateException businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  		if (postalAdministrationVO == null) {       
	  			Object[] obj = {paCode};
    			mailArrivalForm.setListFlag("FAILURE");
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.invalidarrivalpa",obj));
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
		 }
    	}	
    	
		mailArrivalVO.setArrivalPA(paCode);
    	
    	
		/*******************************************************************
		 * validate Flight 
		 ******************************************************************/
    	flightFilterVO.setCarrierCode(flightCarrierCode);
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		mailArrivalVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setFlightNumber(flightNum);
		Collection<FlightValidationVO> flightValidationVOs = null;
		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs =
				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			mailArrivalForm.setListFlag("FAILURE");
			
			invocationContext.addAllError(errors);
			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {mailArrivalVO.getFlightCarrierCode(),
					mailArrivalVO.getFlightNumber(),
					mailArrivalVO.getArrivalDate().toString().substring(0,11)};
			mailArrivalForm.setListFlag("FAILURE");
			//Added by A-7794 as part of ICRD-197439 ; to reset the containerVO 
			Collection<ContainerDetailsVO> containerVO = new ArrayList<ContainerDetailsVO>();
			mailArrivalVO.setContainerDetails(containerVO);
			invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
			invocationContext.target = SCREENLOAD_FAILURE;
		} else if ( flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			try {
				for (FlightValidationVO flightValidVO : flightValidationVOs) {
					BeanHelper.copyProperties(flightValidationVO,
							flightValidVO);
					// Added by A-5153 for BUG_ICRD-90139
					if(FlightValidationVO.FLT_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
							FlightValidationVO.FLT_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
						if(!FlightValidationVO.FLAG_YES.equals(mailArrivalForm.getWarningOveride())){
						ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
						err.setErrorDisplayType(ErrorDisplayType.WARNING);
						invocationContext.addError(err);
						invocationContext.target = SCREENLOAD_FAILURE;
						mailArrivalForm.setWarningFlag(FLIGHT_TBC_TBA);
						return;
						}else{
							mailArrivalForm.setWarningFlag("");
							mailArrivalForm.setWarningOveride(null);
						}
						mailArrivalForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
					}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())){
						Object[] obj = {flightCarrierCode.toUpperCase(),flightValidationVO.getFlightNumber()};
						ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);                   
						err.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(err);
						invocationContext.target = SCREENLOAD_FAILURE;                   
						return;
					}
					break;
				}
			} catch (SystemException systemException) {
				systemException.getMessage();
			}
			flightValidationVO.setDirection(INBOUND);
			mailArrivalForm.setListFlag("");
			mailArrivalSession.setFlightValidationVO(flightValidationVO);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
			invocationContext.target = SCREENLOAD_SUCCESS;
		}else {
			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREEN_ID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
			mailArrivalForm.setDuplicateFlightStatus(FLAG_YES);
			invocationContext.target =DUPLICATE_SUCCESS;
		}
		mailArrivalSession.setMailArrivalVO(mailArrivalVO); 
		
	log.exiting("ListFlightCommand", "execute");
	
    }
	   
    
	/**
	 * Method to validate form.
	 * @param mailArrivalForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MailArrivalForm mailArrivalForm) {
		String flightCarrierCode = mailArrivalForm.getFlightCarrierCode();
		String flightNumber = mailArrivalForm.getFlightNumber();
		String depDate = mailArrivalForm.getArrivalDate();
		String mailStatus = mailArrivalForm.getMailStatus();
		String transferCarrier = mailArrivalForm.getTransferCarrier();
		String paCode             = mailArrivalForm.getArrivalPA();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.flightcarriercode.empty"));
		}
		if(flightNumber == null || ("".equals(flightNumber.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.flightnumber.empty"));
		}
		if(depDate == null || ("".equals(depDate.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.depdate.empty"));
		}
		//Modified for bug ICRD-96756 by A-5526 starts
		if(!MailConstantsVO.MAIL_TRANSHIP.equals(mailStatus)&& transferCarrier!=null&&transferCarrier.length()>0) ////Modified for bug ICRD-96756 by A-5526 ends     
		 {
			errors.add(new ErrorVO("mailtracking.defaults.arrival.trncarrier.check")); 	
		 }
		if(paCode!=null && paCode.length() >0)
		 {
			if(!MailConstantsVO.MAIL_TERMINATING.equals(mailStatus))
					{
				      errors.add(new ErrorVO("mailtracking.defaults.arrival.pacode.check")); 	
					}
		 } 
		return errors;
	}
	
    
       /**
	     * Method to create the filter vo for flight validation
	     * @param mailArrivalForm
	     * @param logonAttributes
	     * @return FlightFilterVO
	     */
	    private FlightFilterVO handleFlightFilterVO(
	    		MailArrivalForm mailArrivalForm,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			//Modified by A-7794 as part of ICRD-197439
			flightFilterVO.setStation(mailArrivalForm.getArrivalPort());
			flightFilterVO.setDirection(INBOUND);
			flightFilterVO.setActiveAlone(false);
			flightFilterVO.setStringFlightDate(mailArrivalForm.getArrivalDate());
			//Modified by A-7794 as part of ICRD-197439
			LocalDate date;
			//Modified by A-7540 for ICRD-258770
			if(mailArrivalForm.getArrivalPort()!=null && !(mailArrivalForm.getArrivalPort().isEmpty())){ 
	 		date = new LocalDate(mailArrivalForm.getArrivalPort(),Location.ARP,false);    
			}
			else{
				date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
			}
	 		flightFilterVO.setFlightDate(date.setDate(mailArrivalForm.getArrivalDate()));
			return flightFilterVO;
		}
		
		/**
		 * This method is to format flightNumber
		 * Not using accoding to the CRQ-AirNZ989-12
		 * @param flightNumber 
		 * @return String
		 */
		/*private String formatFlightNumber(String flightNumber){
			
			int numLength = flightNumber.length();
			String newFlightNumber = "" ;
		    
			if(numLength == 1) { 
		    	newFlightNumber = new  StringBuilder("000").append(flightNumber).toString();
		    }else if(numLength == 2) {
		    	newFlightNumber = new  StringBuilder("00").append(flightNumber).toString();
		    }else if(numLength == 3) { 
		    	newFlightNumber = new  StringBuilder("0").append(flightNumber).toString();
		    }else {
		    	newFlightNumber = flightNumber ;
		    }
			return newFlightNumber;
			
		}*/
}
