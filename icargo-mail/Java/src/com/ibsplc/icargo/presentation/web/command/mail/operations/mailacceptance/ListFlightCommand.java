/*
 * ListFlightCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
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
 *  0.1         Jul 1 2016  Denny         Coding
 */
public class ListFlightCommand extends BaseCommand {
    /**
     * Log
     */
    private Log log = LogFactory.getLogger("MAILOPERATIONS");



    private static final String MODULE_NAME = "mail.operations";

    private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";

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

    private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
    /**
     * Execute method
     * @param invocationContext InvocationContext
     * @throws CommandInvocationException
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ListFlightCommand", "execute");
        MailAcceptanceForm mailAcceptanceForm =
    		(MailAcceptanceForm)invocationContext.screenModel;
        MailAcceptanceSession mailAcceptanceSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();

		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		if(mailAcceptanceForm.getAssignToFlight() == null && mailAcceptanceSession.getOperationalFlightVO()!=null){
			mailAcceptanceForm.setAssignToFlight("FLIGHT");
			mailAcceptanceForm.setCarrierCode(mailAcceptanceSession.getOperationalFlightVO().getCarrierCode());
			mailAcceptanceForm.setFlightCarrierCode(mailAcceptanceSession.getOperationalFlightVO().getCarrierCode());
			mailAcceptanceForm.setFlightNumber(mailAcceptanceSession.getOperationalFlightVO().getFlightNumber());
			mailAcceptanceForm.setDepDate(mailAcceptanceSession.getOperationalFlightVO().getFlightDate().toDisplayDateOnlyFormat().toUpperCase());
		}
		mailAcceptanceVO.setPol(mailAcceptanceForm.getDeparturePort());

		mailAcceptanceForm.setDuplicateFlightStatus(FLAG_NO);
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    AirlineDelegate airlineDelegate = new AirlineDelegate();
	    AreaDelegate areaDelegate = new AreaDelegate();

	    String assignTo = mailAcceptanceForm.getAssignToFlight();
	    log.log(Log.FINE, "assignTo ===", assignTo);
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
    	String flightNum = (mailAcceptanceForm.getFlightNumber().toUpperCase());
		mailAcceptanceVO.setFlightNumber(flightNum);
		mailAcceptanceVO.setStrFlightDate(mailAcceptanceForm.getDepDate());
    	mailAcceptanceVO.setFlightCarrierCode(mailAcceptanceForm.getFlightCarrierCode().toUpperCase());
    	mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
    	errors = validateFormFlight(mailAcceptanceForm);
    	if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
    	}
    	FlightFilterVO flightFilterVO = handleFlightFilterVO(
				mailAcceptanceForm,logonAttributes);
    	mailAcceptanceVO.setFlightDate(flightFilterVO.getFlightDate());
    	AirlineValidationVO airlineValidationVO = null;
	    String flightCarrierCode = mailAcceptanceForm.getFlightCarrierCode().trim().toUpperCase();
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
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
    		}
    	}

		/*******************************************************************
		 * validate Flight
		 ******************************************************************/
    	flightFilterVO.setCarrierCode(flightCarrierCode);
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		mailAcceptanceVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
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
			mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		Collection<FlightValidationVO> tempFlightValidationVOs = new ArrayList<FlightValidationVO>();
		 //Added by A-5160 for ICRD-92869 starts
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
			for (FlightValidationVO flightValidVO : flightValidationVOs) {
				flightValidVO.setDirection(OUTBOUND);
				try {
					FlightValidationVO tempFlightValidationVO = new FlightValidationVO();
					BeanHelper.copyProperties(tempFlightValidationVO,
							flightValidVO);
					tempFlightValidationVOs.add(tempFlightValidationVO);
				} catch (SystemException e) {
					e.getMessage();
				}
				if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
						FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
					flightValidVO.setFlightRoute(null);
					flightValidVO.setAircraftType(null);
					flightValidVO.setEta(null);
					flightValidVO.setEtd(null);
					flightValidVO.setSta(null);
					flightValidVO.setStd(null);
					flightValidVO.setAta(null);
					flightValidVO.setAtd(null);
				}
			}
		}
		 //Added by A-5160 for ICRD-92869 ends
		//Added by A-5160 to find the flights from mail table when listed from a different airport due to route change
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
				flightValidationVOs =
					mailTrackingDefaultsDelegate.validateMailFlight(flightFilterVO);
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
						flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
					}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
					mailAcceptanceVO.getFlightNumber(),
					mailAcceptanceVO.getFlightDate().toString().substring(0,11)};
			//Added by A-7794 as part of ICRD-197439 ; to reset the containerVO 
			Collection<ContainerDetailsVO> containerVO = new ArrayList<ContainerDetailsVO>();
			mailAcceptanceVO.setContainerDetails(containerVO);
			invocationContext.addError(new ErrorVO("mailtracking.defaults.noflightdetails",obj));
			invocationContext.target = SCREENLOAD_FAILURE;
		} else if ( flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			try {
				for (FlightValidationVO flightValidVO : flightValidationVOs) {
					//A-5249 from ICRD-84046					
					/*if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
							FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
						if(!FlightValidationVO.FLAG_YES.equals(mailAcceptanceForm.getWarningOveride())){
						ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
						err.setErrorDisplayType(ErrorDisplayType.WARNING);
						invocationContext.addError(err);
						invocationContext.target = SCREENLOAD_FAILURE;
						mailAcceptanceForm.setWarningFlag(FLIGHT_TBC_TBA);
						return;
						}else{
							mailAcceptanceForm.setWarningFlag("");
							mailAcceptanceForm.setWarningOveride(null);
						}
						mailAcceptanceForm.setDisableButtons(FlightValidationVO.FLAG_YES);
					}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus())){
						Object[] obj = {flightCarrierCode.toUpperCase(),flightValidVO.getFlightNumber()};
						ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
						err.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(err);
						invocationContext.target = SCREENLOAD_FAILURE;
						return;
					} */
					BeanHelper.copyProperties(flightValidationVO,
							flightValidVO);
					break;
				}
				
			} catch (SystemException systemException) {
				systemException.getMessage();
			}
			flightValidationVO.setDirection("O");
			mailAcceptanceSession.setFlightValidationVO(flightValidationVO);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		    invocationContext.target = SCREENLOAD_SUCCESS;
		}else {
			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)tempFlightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREEN_ID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
			mailAcceptanceForm.setDuplicateFlightStatus(FLAG_YES);
			 invocationContext.target =DUPLICATE_SUCCESS;
   			 if(MailConstantsVO.FLAG_YES.equals(mailAcceptanceForm.getDuplicateAndTbaTbc())){
   				 invocationContext.target =SCREENLOAD_SUCCESS; 
   			 }
		   }
		    mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);

	    }else{

	    	mailAcceptanceVO.setFlightCarrierCode(mailAcceptanceForm.getCarrierCode().toUpperCase());
			mailAcceptanceVO.setDestination(mailAcceptanceForm.getDestination().toUpperCase());
			mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);

			errors = validateFormDestination(mailAcceptanceForm);
	    	if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
    			invocationContext.target = SCREENLOAD_FAILURE;
    			return;
	    	}
	    	Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
	    	AirlineValidationVO airlineValidationVO = null;
	    	 String carrierCode = mailAcceptanceForm.getCarrierCode();
	     	if (carrierCode != null && !"".equals(carrierCode)) {
	     		try {
	     			airlineValidationVO = airlineDelegate.validateAlphaCode(
	     					logonAttributes.getCompanyCode(),
	     					carrierCode.toUpperCase());
	     		}catch (BusinessDelegateException businessDelegateException) {
	     			errors = handleDelegateException(businessDelegateException);
	     		}
	     		if (errors != null && errors.size() > 0) {
	     			errors = new ArrayList<ErrorVO>();
	     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
			   				new Object[]{carrierCode.toUpperCase()}));
	     		}else{
	     			mailAcceptanceVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
	     		}
	     	}

	     	AirportValidationVO airportValidationVO = null;
	    	 String destination = mailAcceptanceForm.getDestination();
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

	     	mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);

	     	if (errorsMail != null && errorsMail.size() > 0) {
	     		invocationContext.addAllError(errorsMail);
     			invocationContext.target = SCREENLOAD_FAILURE;
     			return;
	     	}
	     	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	     	invocationContext.target = SCREENLOAD_SUCCESS;

	    }
		  log.exiting("ListFlightCommand", "execute");
    }


	/**
	 * Method to validate form in FLIGHT MODE.
	 * @param mailAcceptanceForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormFlight(
			MailAcceptanceForm mailAcceptanceForm) {
		String flightCarrierCode = mailAcceptanceForm.getFlightCarrierCode();
		String flightNumber = mailAcceptanceForm.getFlightNumber();
		String depDate = mailAcceptanceForm.getDepDate();
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
	 * @param mailAcceptanceForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFormDestination(
			MailAcceptanceForm mailAcceptanceForm) {
		String destination = mailAcceptanceForm.getDestination();
		String carrierCode = mailAcceptanceForm.getCarrierCode();
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
	     * @param mailAcceptanceForm
	     * @param logonAttributes
	     * @return FlightFilterVO
	     */
	    private FlightFilterVO handleFlightFilterVO(
	    		MailAcceptanceForm mailAcceptanceForm,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			//Modified by A-7794 as part of ICRD-197439
			flightFilterVO.setStation(mailAcceptanceForm.getDeparturePort());
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setActiveAlone(false);
			flightFilterVO.setStringFlightDate(mailAcceptanceForm.getDepDate());
			//Modified by A-7794 as part of ICRD-197439
			LocalDate date = null;
			if(mailAcceptanceForm.getDeparturePort()!=null && !mailAcceptanceForm.getDeparturePort().isEmpty())
	 		date = new LocalDate(mailAcceptanceForm.getDeparturePort(),Location.ARP,false);
	 		else
	 			date = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);                 
	 		flightFilterVO.setFlightDate(date.setDate(mailAcceptanceForm.getDepDate()));
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
