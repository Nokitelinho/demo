/*
 * ValidateFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the UCM error logs
 * @author A-5991
 */

public class ValidateFlightCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";
	 /*
     * Module name and Screen id for duplicate flight
     */
    private static final String SCREEN_ID_DUPFLIGHT = 
    					"flight.operation.duplicateflight";
    
    private static final String MODULE_NAME_FLIGHT = 
    					"flight.operation";
    
    private static final String VALIDATION_SUCCESS = "validation_success";
    private static final String VALIDATION_FAILURE = "validation_failure";
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	CarditEnquiryForm carditEnquiryForm = 
    			(CarditEnquiryForm)invocationContext.screenModel;
    	/*CarditEnquirySession carditEnquirySession = 
    			getScreenSession(MODULE_NAME,SCREEN_ID);*/		
		/*
		 * Obtain the duplicate flight session
		 */
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		Collection<ErrorVO> errors = validateForm(carditEnquiryForm,logonAttributes.getCompanyCode());
		if(errors != null &&
				errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATION_FAILURE;
			return;
		}		
		validateFlight(carditEnquiryForm,
					  logonAttributes.getCompanyCode(),
					  invocationContext,
					  duplicateFlightSession);
		
		invocationContext.target = VALIDATION_SUCCESS;
        
    }
    /**
     * 
     * @param ucmErrorLogForm
     * @param companyCode
     * @return
     */
    
	private Collection<ErrorVO> validateForm
			(CarditEnquiryForm carditEnquiryForm,String  companyCode){
		log.entering("ListUCMErorLogCommand", "validateForm");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String departurePort = carditEnquiryForm.getDeparturePort();
		String carrierCode = carditEnquiryForm.getCarrierCode();
		String flightNumber = carditEnquiryForm.getFlightNumber();
		String flightDate = carditEnquiryForm.getFlightDate();
		
		if(departurePort == null || 
				departurePort.trim().length() == 0){
			 error = new ErrorVO(
					 "mailtracking.defaults.carditenquiry.msg.err.portmandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(error);
		}
		else {
			AirportValidationVO airportValidationVO = null;
			try {
				airportValidationVO = new AreaDelegate().validateAirportCode(
						companyCode, departurePort.toUpperCase());
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if(airportValidationVO == null) {
				error = new ErrorVO(
				 "mailtracking.defaults.msg.err.invalidairport",
				 new Object[]{departurePort.toUpperCase()});
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if(carrierCode == null 
				|| carrierCode.trim().length() == 0 
				|| flightNumber == null 
				|| flightNumber.trim().length() == 0
				|| flightDate == null 
				|| flightDate.trim().length() == 0){
			 error = new ErrorVO(
					 "mailtracking.defaults.carditenquiry.msg.err.noFlightDetails");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(error);
		}
		else {			
			AirlineValidationVO airlineValidationVO = null;			
			try {
				airlineValidationVO =
					new AirlineDelegate().validateAlphaCode(
							companyCode, carrierCode.toUpperCase());

			} catch (BusinessDelegateException e) {
				handleDelegateException(e);			
			}

			if (airlineValidationVO == null){
				error = new ErrorVO(
					"mailtracking.defaults.msg.err.invalidcarrier",
					new Object[]{carrierCode.toUpperCase()});
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		log.exiting("ListUCMErrorLogCommand", "validateForm");
		return errors;
	}
    /**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param invocationContext
	 * @param duplicateFlightSession
	 * @return
	 */
	private boolean validateFlight(
			CarditEnquiryForm carditEnquiryForm,
			String companyCode,
			InvocationContext invocationContext,
			DuplicateFlightSession duplicateFlightSession) {
		log.entering("ListCommand","validateFlight");
		String airportCode = carditEnquiryForm.getDeparturePort();
		String carrierCode = carditEnquiryForm.getCarrierCode();
		String flightNumber = carditEnquiryForm.getFlightNumber();
		String flightDate = carditEnquiryForm.getFlightDate();
		String resdit = carditEnquiryForm.getResdit();
		
		boolean isValidFlight = true;
		String alphaCode = carrierCode.toUpperCase();
		AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(
				companyCode, alphaCode, invocationContext);
		if(airlineValidationVO == null) {
			isValidFlight = false;
		}
		else {
			Collection<FlightValidationVO> flightValidationVOs =
				populateFlightValidationVO(companyCode, airportCode,
						flightNumber, flightDate,  
					airlineValidationVO, invocationContext,
					resdit,carditEnquiryForm.getFlightType());
			/*
			 * If no error and flightValidationVOs is not null
			 */
			if(flightValidationVOs != null && flightValidationVOs.size() > 0){
				/*
				 * If the size of flightValidationVOs is 1
				 * then obtain the flightValidationVO from
				 * the first element of the collection
				 */
				if(flightValidationVOs.size() == 1){
					duplicateFlightSession.setFlightValidationVO(
				   ((ArrayList<FlightValidationVO>)flightValidationVOs).get(0));
				}
				/*
				 * If there are more than one element in the collection
				 * then display the duplicate flight popup
				 */
				else if(flightValidationVOs.size() > 1){
					//TODO : Display the duplicate flight popup
					log.log(Log.INFO,"###### Duplicate Exist####");
					duplicateFlightSession.setFlightValidationVOs((
							ArrayList<FlightValidationVO>)flightValidationVOs);
					duplicateFlightSession.setParentScreenId(SCREEN_ID);
					//duplicateFlightSession.setScreenOfParent(SCREEN_OF_PARENT);
					carditEnquiryForm.setDuplicateFlightStatus(FlightValidationVO.FLAG_YES);
					isValidFlight = false;
				}
			}
			else {
				log.log(Log.FINE, "flightValidationVOs is NULL");
				isValidFlight = false;
			}
		}
		log.exiting("ListCommand","validateFlight");
		return isValidFlight;
		
	}
	
	/**
	 * Method to get the AirlineValidationVO
	 * @param companyCode
	 * @param alphaCode
	 * @param invocationContext
	 * @return airlineValidationVO
	 */
	private AirlineValidationVO populateAirlineValidationVO(String companyCode,
			String alphaCode, InvocationContext invocationContext){
		log.entering("ListCommand","populateAirlineValidationVO");
		AirlineDelegate airlineDelegate = new AirlineDelegate();

		AirlineValidationVO airlineValidationVO = null;
		Collection<ErrorVO> errors = null;

		try {
			airlineValidationVO =
				airlineDelegate.validateAlphaCode(companyCode, alphaCode);

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			e.getMessageVO().getErrors();
		}

		if (errors != null && errors.size() > 0){
			ErrorVO error = new ErrorVO(
				"mailtracking.defaults.msg.err.invalidcarrier",
				new Object[]{alphaCode});
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
		}
		log.exiting("ListCommand","populateAirlineValidationVO");
		return airlineValidationVO;
	}
	
	/**
	 * Validate the flight and if the flight is valid ,
	 * returns the collection of flightvalidation vos
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param airlineValidationVO
	 * @param invocationContext
	 * @param direction
	 * @return
	 */
	private Collection<FlightValidationVO> populateFlightValidationVO(
			String companyCode,	String airportCode, 
			String flightNumber,String flightDate,
			AirlineValidationVO airlineValidationVO,
			InvocationContext invocationContext,String resdit,String flightType){

			log.entering("ListCommand","populateFlightValidationVO");
			Collection<ErrorVO> errors = null;
			/*
			 * Populate the flightFilterVo
			 */
			FlightFilterVO flightFilterVO =
				getFlightFilterVO(companyCode, airportCode,
						flightNumber,flightDate,
						resdit,flightType, airlineValidationVO);
			log.log(Log.FINE, "\nflightFilterVO ---> ", flightFilterVO);
			/*
			 * Validate flight - obtain the flightValidationVO
			 */
			MailTrackingDefaultsDelegate delegate =
									new MailTrackingDefaultsDelegate();
			Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				flightValidationVOs =
					delegate.validateFlight(flightFilterVO);
				log.log(Log.FINE, "flightValidationVOs --> ",
						flightValidationVOs);
			} catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessageVO().getErrors();
			}
			/*
			 * If error is present then set it to the
			 * invocationContext and return
			 */
			if(flightValidationVOs == null || flightValidationVOs.size() == 0){
				if(errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
				}
				else {
					 ErrorVO error = new ErrorVO (
					"mailtracking.defaults.carditenquiry.msg.err.flightnotpresent");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					// errors.add(error);
					invocationContext.addError(error);
				}
			}
			log.exiting("ListCommand","populateFlightValidationVO");
			return flightValidationVOs;
     }
	
	/**
	 * Methd to get the Flight FilterVO
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param direction
	 * @param airlineValidationVO
	 * @return
	 */			
	private FlightFilterVO getFlightFilterVO(
			String companyCode, String airportCode,
			String flightNumber,String flightDate, String resdit,
			String flightType,AirlineValidationVO airlineValidationVO){
		log.entering("ListCommand", "getFlightFilterVO");
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setStation(airportCode);
		flightFilterVO.setCompanyCode(companyCode);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setFlightNumber(flightNumber.toUpperCase());
		flightFilterVO.setFlightCarrierId(
					airlineValidationVO.getAirlineIdentifier());

		if(flightDate != null 
				&& flightDate.trim().length() > 0) {
			LocalDate flightTmpDate =
				new LocalDate(airportCode,Location.ARP,false) ;
			flightFilterVO.setFlightDate(
					flightTmpDate.setDate(flightDate));
		}
		if("O".equals(flightType)){
			if("21".equals(resdit)){
				flightFilterVO.setDirection("I");
			}else{
				flightFilterVO.setDirection("O");
			}
		}else{
			flightFilterVO.setDirection("O");
		}
		
		log.exiting("ListCommand", "getFlightFilterVO");
		return flightFilterVO;
	}
}
