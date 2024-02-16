/*
 * ListFlightCommand.java Created on APR 01, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigndsn;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignDSNSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignDSNForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author RENO K ABRAHAM
 * Command class for List FlightDetails.
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         APR 01, 2008  	 RENO K ABRAHAM         Coding
 */
public class ListFlightCommand extends BaseCommand {
	/**
	 * Log
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING");

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.reassigndsn";

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
	private static final String MODULE_NAME_FLIGHT = "flight.operation";

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
		ReassignDSNForm reassignDSNForm = (ReassignDSNForm) invocationContext.screenModel;
		ReassignDSNSession reassignDSNSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);

		reassignDSNForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

		reassignDSNForm.setDuplicateFlightStatus(FLAG_NO);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AreaDelegate areaDelegate = new AreaDelegate();
		
		//START : Updating the ReassignedPcs And ReassignedWgt 
    	Collection<DespatchDetailsVO> despatchDetailVOs=reassignDSNSession.getDespatchDetailsVOs();
		int k=0;
		if(despatchDetailVOs!=null && despatchDetailVOs.size()>0){
			for(DespatchDetailsVO despatchDetailsVO: despatchDetailVOs){
				if((reassignDSNForm.getReAssignedPcs()[k]!=null || !"".equals(reassignDSNForm.getReAssignedPcs()[k]))
						&& reassignDSNForm.getReAssignedPcs()[k].length()>0){
					despatchDetailsVO.setAcceptedBags(Integer.parseInt(reassignDSNForm.getReAssignedPcs()[k]));
				}else{
					despatchDetailsVO.setAcceptedBags(0);
				}
				if((reassignDSNForm.getReAssignedWt()[k]!=null || !"".equals(reassignDSNForm.getReAssignedWt()[k]))
						&& reassignDSNForm.getReAssignedWt()[k].length()>0){
					//despatchDetailsVO.setAcceptedWeight(Double.parseDouble(reassignDSNForm.getReAssignedWt()[k]));
					despatchDetailsVO.setAcceptedWeight(reassignDSNForm.getReAssignedWtMeasure()[k]);//added by A-7371
				}else{
					//despatchDetailsVO.setAcceptedWeight(0);
					despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,0));//added by A-7371
				}
				k++;			
			}
	    	reassignDSNSession.setDespatchDetailsVOs(despatchDetailVOs);
		}
    	//END
    	
		String assignTo = reassignDSNForm.getAssignToFlight();
		log.log(Log.FINE, "assignTo ===", assignTo);
		if (CONST_FLIGHT.equalsIgnoreCase(assignTo)) {
			errors = validateFormFlight(reassignDSNForm);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			FlightFilterVO flightFilterVO = handleFlightFilterVO(
					reassignDSNForm, logonAttributes);
			String flightNum = (reassignDSNForm
					.getFlightNumber().toUpperCase());

			AirlineValidationVO airlineValidationVO = null;
			String flightCarrierCode = reassignDSNForm.getFlightCarrierCode();
			if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
				try {
					airlineValidationVO = airlineDelegate.validateAlphaCode(
							logonAttributes.getCompanyCode(), flightCarrierCode
							.trim().toUpperCase());

				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (errors != null && errors.size() > 0) {
					errors = new ArrayList<ErrorVO>();
					Object[] obj = { flightCarrierCode.toUpperCase() };
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.invalidcarrier", obj);
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
			flightFilterVO.setFlightCarrierId(airlineValidationVO
					.getAirlineIdentifier());
			flightFilterVO.setFlightNumber(flightNum);
			Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ",
						flightFilterVO);
				flightValidationVOs = mailTrackingDefaultsDelegate
				.validateFlight(flightFilterVO);

			} catch (BusinessDelegateException businessDelegateException) {
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
				Object[] obj = {
						reassignDSNForm.getFlightCarrierCode(),
						reassignDSNForm.getFlightNumber(),
						reassignDSNForm.getDepDate().toString()
						.substring(0, 11) };
				invocationContext.addError(new ErrorVO(
						"mailtracking.defaults.noflightdetails", obj));
				invocationContext.target = SCREENLOAD_FAILURE;
			} else if (flightValidationVOs.size() == 1) {
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
				reassignDSNSession.setFlightValidationVO(flightValidationVO);
				log
						.log(Log.FINE, "flightValidationVOs ===",
								flightValidationVO);
				invocationContext.target = SCREENLOAD_SUCCESS;
			} else {
				duplicateFlightSession
				.setFlightValidationVOs((ArrayList<FlightValidationVO>) flightValidationVOs);
				duplicateFlightSession.setParentScreenId(SCREEN_ID);
				duplicateFlightSession.setFlightFilterVO(flightFilterVO);
				maintainFlightSession.setCompanyCode(logonAttributes
						.getCompanyCode());
				reassignDSNForm.setDuplicateFlightStatus(FLAG_YES);
				invocationContext.target = DUPLICATE_SUCCESS;
			}
		} else {
			errors = validateFormDestination(reassignDSNForm);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
			AirlineValidationVO airlineValidationVO = null;
			String carrierCode = reassignDSNForm.getCarrierCode();
			if (carrierCode != null && !"".equals(carrierCode)) {
				try {
					airlineValidationVO = airlineDelegate.validateAlphaCode(
							logonAttributes.getCompanyCode(), carrierCode
							.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (errors != null && errors.size() > 0) {
					errorsMail.add(new ErrorVO(
							"mailtracking.defaults.invalidcarrier",
							new Object[] { carrierCode.toUpperCase() }));
				} else {
					reassignDSNForm.setCarrierId(airlineValidationVO
							.getAirlineIdentifier());
				}
			}

			AirportValidationVO airportValidationVO = null;
			String destination = reassignDSNForm.getDestination();
			if (destination != null && !"".equals(destination)) {
				try {
					airportValidationVO = areaDelegate.validateAirportCode(
							logonAttributes.getCompanyCode(), destination
							.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (errors != null && errors.size() > 0) {
					errorsMail.add(new ErrorVO(
							"mailtracking.defaults.invalidairport",
							new Object[] { destination.toUpperCase() }));
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
			ReassignDSNForm reassignDSNForm) {
		String flightCarrierCode = reassignDSNForm.getFlightCarrierCode();
		String flightNumber = reassignDSNForm.getFlightNumber();
		String depDate = reassignDSNForm.getDepDate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))) {
			errors.add(new ErrorVO(
			"mailtracking.defaults.flightcarriercode.empty"));
		}
		if (flightNumber == null || ("".equals(flightNumber.trim()))) {
			errors.add(new ErrorVO("mailtracking.defaults.flightnumber.empty"));
		}
		if (depDate == null || ("".equals(depDate.trim()))) {
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
			ReassignDSNForm reassignDSNForm) {
		//		String destination = reassignMailForm.getDestination();
		String carrierCode = reassignDSNForm.getCarrierCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//		if(destination == null || ("".equals(destination.trim()))){
		//			errors.add(new ErrorVO("mailtracking.defaults.destination.empty"));
		//		}
		if (carrierCode == null || ("".equals(carrierCode.trim()))) {
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
			ReassignDSNForm reassignDSNForm, LogonAttributes logonAttributes) {

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(reassignDSNForm.getDepDate());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, false);
		flightFilterVO
		.setFlightDate(date.setDate(reassignDSNForm.getDepDate()));
		return flightFilterVO;
	}

	/**
	 * This method is to format flightNumber
	 * Not using - CRQ-AirNZ989-12
	 * @param flightNumber 
	 * @return String
	 */
	/*private String formatFlightNumber(String flightNumber) {
		int numLength = flightNumber.length();
		String newFlightNumber = "";
		if (numLength == 1) {
			newFlightNumber = new StringBuilder("000").append(flightNumber)
			.toString();
		} else if (numLength == 2) {
			newFlightNumber = new StringBuilder("00").append(flightNumber)
			.toString();
		} else if (numLength == 3) {
			newFlightNumber = new StringBuilder("0").append(flightNumber)
			.toString();
		} else {
			newFlightNumber = flightNumber;
		}
		return newFlightNumber;
	}*/
}
