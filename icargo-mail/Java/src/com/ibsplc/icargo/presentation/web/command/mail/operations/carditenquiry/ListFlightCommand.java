/*
 * ListFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListFlightCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");



    private static final String MODULE_NAME = "mail.operations";

    private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";
    private static final String SCREEN_PARENT = "SENDRESDITS";

    /**
     * Screen id
     */
    private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

   
    /**
     * Module name
     */
    private static final String MODULE_NAME_FLIGHT =  "flight.operation";

   /**
     * Target string
     */
    private static final String LIST_SUCCESS = "list_success";

    /**
     * Target string
     */
    private static final String LIST_FAILURE = "list_failure";

    /**
     * Target string
     */
    private static final String DUPLICATE_SUCCESS = "duplicate_success";

    /**
     *  Status of flag
     */
    private static final String OUTBOUND = "O";


	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
            log.entering("ListFlightCommand", "execute");
            CarditEnquiryForm carditEnquiryForm =
        		(CarditEnquiryForm)invocationContext.screenModel;
            CarditEnquirySession carditEnquirySession = getScreenSession(
    			MODULE_NAME, SCREEN_ID);
    		DuplicateFlightSession duplicateFlightSession = getScreenSession(
    				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);

    		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

    		carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

    		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
        		new MailTrackingDefaultsDelegate();
    	

    		carditEnquiryForm.setDupFlightStatus(FLAG_NO);
    	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	    AirlineDelegate airlineDelegate = new AirlineDelegate();
    	    AreaDelegate areaDelegate = new AreaDelegate();

        	errors = validateFormFlight(carditEnquiryForm);
        	if (errors != null && errors.size() > 0) {
        		invocationContext.addAllError(errors);
    			invocationContext.target = LIST_FAILURE;
    			return;
        	}
        	FlightFilterVO flightFilterVO = handleFlightFilterVO(
        			carditEnquiryForm,logonAttributes);
        	
        	AirlineValidationVO airlineValidationVO = null;
    	    String flightCarrierCode = carditEnquiryForm.getCarrierCod().trim().toUpperCase();
        	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
        		try {
        			airlineValidationVO = airlineDelegate.validateAlphaCode(
        					logonAttributes.getCompanyCode(),
        					flightCarrierCode);

        		}catch (BusinessDelegateException businessDelegateException) {
        			errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			errors = new ArrayList<ErrorVO>();
        			Object[] obj = {flightCarrierCode};
    				ErrorVO errorVO = new ErrorVO(
    					"mailtracking.defaults.invalidcarrier",obj);
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorVO);
        			invocationContext.addAllError(errors);
        			invocationContext.target = LIST_FAILURE;
        			return;
        		}
        	}

    		/*******************************************************************
    		 * validate Flight
    		 ******************************************************************/
        	flightFilterVO.setCarrierCode(flightCarrierCode);
    		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
    		flightFilterVO.setFlightNumber(carditEnquiryForm.getFlightNo());
    		Collection<FlightValidationVO> flightValidationVOs = null;
    		try {
    			log.log(Log.FINE, "FlightFilterVO ------------> ",
						flightFilterVO);
				flightValidationVOs =
    				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);    				
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = LIST_FAILURE;
    			return;
    		}
    		FlightValidationVO flightValidationVO = new FlightValidationVO();
    		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
    			log.log(Log.FINE, "flightValidationVOs is NULL");
    			Object[] obj = {carditEnquiryForm.getCarrierCod(),
    					carditEnquiryForm.getFlightNo(),
    					carditEnquiryForm.getFlightDat().toString().substring(0,11)};
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
    			invocationContext.target = LIST_FAILURE;
    			return;
    		} else if ( flightValidationVOs.size() == 1) {
    			log.log(Log.FINE, "flightValidationVOs has one VO");
    			try {
    				for (FlightValidationVO flightValidVO : flightValidationVOs) {
    					BeanHelper.copyProperties(flightValidationVO,
    							flightValidVO);
    					carditEnquirySession.setFlightValidationVO(flightValidationVO);
    					break;
    				}
    			} catch (SystemException systemException) {
    				systemException.getMessage();
    			}
    			flightValidationVO.setDirection("O");
    			log
						.log(Log.FINE, "flightValidationVOs ===",
								flightValidationVO);
				carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		    invocationContext.target = LIST_SUCCESS;
    		}else {
    			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
    			duplicateFlightSession.setParentScreenId(SCREEN_ID);
    			duplicateFlightSession.setScreenOfParent(SCREEN_PARENT);
    			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
    			carditEnquiryForm.setDupFlightStatus(FLAG_YES);
    			invocationContext.target =DUPLICATE_SUCCESS;
    		 }
    		AirportValidationVO airportValidationVO = null;
    		Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
	    	String destination = carditEnquiryForm.getPou();
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
    			invocationContext.target = LIST_FAILURE;
    			return;
	     	}
    	     invocationContext.target = LIST_SUCCESS;
    	     log.exiting("ListFlightCommand", "execute");
     }


	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param carditEnquiryForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(CarditEnquiryForm carditEnquiryForm) {
		String flightCarrierCode = carditEnquiryForm.getCarrierCod();
		String flightNumber = carditEnquiryForm.getFlightNo();
		String depDate = carditEnquiryForm.getFlightDat();
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
     * Method to create the filter vo for flight validation
     * @param mailAcceptanceForm
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		CarditEnquiryForm carditEnquiryForm,
			LogonAttributes logonAttributes){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(carditEnquiryForm.getFlightDat());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(carditEnquiryForm.getFlightDat()));
		return flightFilterVO;
	}
    	
    }

   
