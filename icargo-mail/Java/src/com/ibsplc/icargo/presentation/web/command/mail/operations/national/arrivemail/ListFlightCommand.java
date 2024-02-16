/* ListFlightCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.arrivemail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ArriveDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */
public class ListFlightCommand extends BaseCommand{
	 /**
     * Log
     */
    private Log log = LogFactory.getLogger("MAILTRACKING");

    
    
    private static final String MODULE_NAME = "mail.operations";	
    
    private static final String SCREEN_ID = "mailtracking.defaults.national.mailarrival";	
    
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
    

    /**
     * Execute method
     * @param invocationContext InvocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
    	log.entering("ListFlightCommand", "execute");

    	ArriveDispatchForm arrivedispatchForm = 
    		(ArriveDispatchForm)invocationContext.screenModel;
    	ArriveDispatchSession arriveDispatchSession = getScreenSession(
    			MODULE_NAME, SCREEN_ID);
    	DuplicateFlightSession duplicateFlightSession = getScreenSession(
    			MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
    	MaintainFlightSession maintainFlightSession = getScreenSession(
    			MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
    	arrivedispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

    	arrivedispatchForm.setOperationalStatus("");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();

    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();

    	arriveDispatchSession.setMailArrivalFilterVO(null);
    	arriveDispatchSession.setMailArrivalVO(null);

    	arrivedispatchForm.setDuplicateFlightStatus(FLAG_NO);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	AirlineDelegate airlineDelegate = new AirlineDelegate();

    	errors = validateForm(arrivedispatchForm);
    	if (errors != null && errors.size() > 0) {  


    		invocationContext.addAllError(errors);
    		invocationContext.target = SCREENLOAD_FAILURE;
    		return;
    	}
    	
    	String flightNum = (arrivedispatchForm.getFlightNumber().toUpperCase());


    	FlightFilterVO flightFilterVO = handleFlightFilterVO(
    			arrivedispatchForm,logonAttributes);


    	AirlineValidationVO airlineValidationVO = null;
    	String flightCarrierCode = arrivedispatchForm.getFlightCarrierCode().trim().toUpperCase();        	
    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
    		try {        			
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),flightCarrierCode);
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {flightCarrierCode};
    			arrivedispatchForm.setListFlag("FAILURE");

    			invocationContext.addError(new ErrorVO("mailtracking.defaults.national.invalidcarrier",obj));
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
    		}
    	}
		
    	
    	
    	/*******************************************************************
    	 * validate Flight 
    	 ******************************************************************/
    	flightFilterVO.setCarrierCode(flightCarrierCode);
    	flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
    	//mailArrivalVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
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

    		
    		invocationContext.addError(new ErrorVO("mailtracking.defaults.national.noflightdetails"));
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
    		flightValidationVO.setDirection(INBOUND);
    		
    		arriveDispatchSession.setFlightValidationVO(flightValidationVO);
    		log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
			invocationContext.target = SCREENLOAD_SUCCESS;
    	}else {
    		duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
    		duplicateFlightSession.setParentScreenId(SCREEN_ID);
    		duplicateFlightSession.setFlightFilterVO(flightFilterVO);
    		maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
    		arrivedispatchForm.setDuplicateFlightStatus(FLAG_YES);
    		invocationContext.target =DUPLICATE_SUCCESS;
    	}


    	log.exiting("ListFlightCommand", "execute");
	
    }
	   

    /**
     * Method to validate form.
     * @param mailArrivalForm
     * @return Collection<ErrorVO>
     */
    private Collection<ErrorVO> validateForm(
    		ArriveDispatchForm arrivedispatchForm) {
    	String flightCarrierCode = arrivedispatchForm.getFlightCarrierCode();
    	String flightNumber = arrivedispatchForm.getFlightNumber();

    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	if(flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))){
    		errors.add(new ErrorVO("mailtracking.defaults.national.flightcarriercode.empty"));
    	}
    	if(flightNumber == null || ("".equals(flightNumber.trim()))){
    		errors.add(new ErrorVO("mailtracking.defaults.national.flightnumber.empty"));
    	}
    	if(arrivedispatchForm.getArrivalDate() == null || ("".equals(arrivedispatchForm.getArrivalDate().trim()))){
    		errors.add(new ErrorVO("mailtracking.defaults.national.arrivaldate.empty"));
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
    		ArriveDispatchForm arrivedispatchForm,
    		LogonAttributes logonAttributes){

    	FlightFilterVO flightFilterVO = new FlightFilterVO();

    	flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

    	flightFilterVO.setStation(logonAttributes.getAirportCode());
    	flightFilterVO.setDirection(INBOUND);
    	flightFilterVO.setActiveAlone(false);
    	flightFilterVO.setStringFlightDate(arrivedispatchForm.getArrivalDate());
    	LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    	flightFilterVO.setFlightDate(date.setDate(arrivedispatchForm.getArrivalDate()));
    	return flightFilterVO;
    }


}
