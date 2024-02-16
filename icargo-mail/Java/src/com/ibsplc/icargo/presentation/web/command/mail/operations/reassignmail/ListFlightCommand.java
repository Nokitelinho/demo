/*
 * ListFlightCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for List FlightDetails.
 *
 * Revision History
 *
 * Version      Date        Author         Description
 *
 *  0.1         05/7/2006   Roopak         Coding
 */
public class ListFlightCommand extends BaseCommand {
    /**
     * Log
     */
    private Log log = LogFactory.getLogger("MAILTRACKING");

    
    
    private static final String MODULE_NAME = "mail.operations";	
    
    private static final String SCREEN_ID = "mailtracking.defaults.reassignmail";	
    
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
    private static final String OUTBOUND = "O";
    private static final String CONST_FLIGHT = "FLIGHT";
    

    /**
     * Execute method
     * @param invocationContext InvocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ListFlightCommand", "execute");
        ReassignMailForm reassignMailForm = 
    		(ReassignMailForm)invocationContext.screenModel;
        ReassignMailSession reassignMailSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
		
		reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
		
		reassignMailForm.setDuplicateFlightStatus(FLAG_NO);
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    AirlineDelegate airlineDelegate = new AirlineDelegate();
	    AreaDelegate areaDelegate = new AreaDelegate();
	    
	    String assignTo = reassignMailForm.getAssignToFlight();
	    log.log(Log.FINE, "assignTo ===", assignTo);
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
	    	errors = validateFormFlight(reassignMailForm);
	    	if (errors != null && errors.size() > 0) {  
	    		invocationContext.addAllError(errors);
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
	    	}
    	FlightFilterVO flightFilterVO = handleFlightFilterVO(
    			reassignMailForm,logonAttributes);	
    	String flightNum = (reassignMailForm.getFlightNumber().toUpperCase());
		
    	AirlineValidationVO airlineValidationVO = null;
	    String flightCarrierCode = reassignMailForm.getFlightCarrierCode();        	
    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
    		try {        			
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					flightCarrierCode.trim().toUpperCase());

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			errors = new ArrayList<ErrorVO>();
    			Object[] obj = {flightCarrierCode.toUpperCase()};
				ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.invalidcarrier",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
    		}
    	}
		
		/*******************************************************************
		 * validate Flight 
		 ******************************************************************/
    	flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
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
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {reassignMailForm.getFlightCarrierCode(),
					reassignMailForm.getFlightNumber(),
					reassignMailForm.getDepDate().toString().substring(0,11)};
			invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
			invocationContext.target = SCREENLOAD_FAILURE;
		} else if ( flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			try {
				for (FlightValidationVO flightValidVO : flightValidationVOs) {
					BeanHelper.copyProperties(flightValidationVO,
							flightValidVO);
					break;
				}
			} catch (SystemException systemException) {
				systemException.getMessage();
			}
			flightValidationVO.setDirection("O");
			reassignMailSession.setFlightValidationVO(flightValidationVO);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
			invocationContext.target = SCREENLOAD_SUCCESS;
		}else {
			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREEN_ID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
			reassignMailForm.setDuplicateFlightStatus(FLAG_YES);
			if(!FLAG_YES.equals(reassignMailForm.getDuplicateFlag())){
			reassignMailForm.setDuplicateFlag(FLAG_YES);
			 invocationContext.target =DUPLICATE_SUCCESS;
			}else{
				invocationContext.target = SCREENLOAD_SUCCESS;
			}
		   }
	    }else{
	    	errors = validateFormDestination(reassignMailForm);
	    	if (errors != null && errors.size() > 0) {  
	    		invocationContext.addAllError(errors);
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
	    	}
	    	Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
	    	AirlineValidationVO airlineValidationVO = null;
	    	 String carrierCode = reassignMailForm.getCarrierCode();        	
	     	if (carrierCode != null && !"".equals(carrierCode)) {        		
	     		try {        			
	     			airlineValidationVO = airlineDelegate.validateAlphaCode(
	     					logonAttributes.getCompanyCode(),
	     					carrierCode.toUpperCase());
	     		}catch (BusinessDelegateException businessDelegateException) {
	     			errors = handleDelegateException(businessDelegateException);
	     		}
	     		if (errors != null && errors.size() > 0) {            			
	     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
			   				new Object[]{carrierCode.toUpperCase()}));
	     		}else{
	     			reassignMailForm.setCarrierId(airlineValidationVO.getAirlineIdentifier());
	     		}
	     	}
	     	
	     	AirportValidationVO airportValidationVO = null;
	    	 String destination = reassignMailForm.getDestination();        	
	     	if (destination != null && !"".equals(destination)) {        		
	     		try {        			
	     			airportValidationVO = areaDelegate.validateAirportCode(
	     					logonAttributes.getCompanyCode(),
	     					destination.toUpperCase());
	     		}catch (BusinessDelegateException businessDelegateException) {
	     			errors = handleDelegateException(businessDelegateException);
	     		}
	     		if (errors != null && errors.size() > 0) {            			
	     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidairport",
			   				new Object[]{destination.toUpperCase()}));
	     		}
	     	}
	     	
	     	
	     	if (errorsMail != null && errorsMail.size() > 0) {
	     		invocationContext.addAllError(errorsMail);
     			invocationContext.target = SCREENLOAD_FAILURE;
     			return;
	     	}
	     	invocationContext.target = SCREENLOAD_SUCCESS;
	       	
	    }
		  log.exiting("ListFlightCommand", "execute");
    }
	   
    
	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param reassignMailForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(
			ReassignMailForm reassignMailForm) {
		String flightCarrierCode = reassignMailForm.getFlightCarrierCode();
		String flightNumber = reassignMailForm.getFlightNumber();
		String depDate = reassignMailForm.getDepDate();
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
		return errors;
	}
	
	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param reassignMailForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormDestination(
			ReassignMailForm reassignMailForm) {		
		String destination = reassignMailForm.getDestination();
		String carrierCode = reassignMailForm.getCarrierCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(destination == null || ("".equals(destination.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.destination.empty"));
		}
		if(carrierCode == null || ("".equals(carrierCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.carriercode.empty"));
		}
		return errors;
	}
    
       /**
	     * Method to create the filter vo for flight validation
	     * @param reassignMailForm
	     * @param logonAttributes
	     * @return FlightFilterVO
	     */
	    private FlightFilterVO handleFlightFilterVO(
	    		ReassignMailForm reassignMailForm,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			
			flightFilterVO.setStation(logonAttributes.getAirportCode());
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setActiveAlone(false);
			flightFilterVO.setStringFlightDate(reassignMailForm.getDepDate());
	 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
	 		flightFilterVO.setFlightDate(date.setDate(reassignMailForm.getDepDate()));
			return flightFilterVO;
		}
		
		/**
		 * This method is to format flightNumber
		 * Not using - CRQ-AirNZ989-12
		 * @param flightNumber 
		 * @return String
		 */
		/*private String formatFlightNumber(String flightNumber){
			int numLength = flightNumber.length();
			String newFlightNumber = "" ;
	        if(numLength == 1) { 
	        	newFlightNumber = new  StringBuilder("000").append(flightNumber).toString();
	        }
	        else if(numLength == 2) {
	        	newFlightNumber = new  StringBuilder("00").append(flightNumber).toString();
	        }
	        else if(numLength == 3) { 
	        	newFlightNumber = new  StringBuilder("0").append(flightNumber).toString();
	        }
	        else {
	        	newFlightNumber = flightNumber ;
	        }
				return newFlightNumber;
		}*/
}
