/**
 * ListFlightCommand.java Created on January 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ListFlightCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING");
	private static final String MODULE_NAME = "mail.operations";	
	private static final String MODULE_NAME_FLIGHT =  "flight.operation";
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";	
	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
	private static final String SCREEN_ID_FLIGHT = "flight.operation.maintainflight";
	private static final String SCREENLOAD_SUCCESS = "screenload_success"; 	  
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String DUPLICATE_SUCCESS = "duplicate_success";
	private static final String OUTBOUND = "O";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ListFlightCommand", "execute");
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationContext.screenModel;
		AssignMailBagSession assignMailBagSession =  getScreenSession(
				MODULE_NAME, SCREEN_ID);
		assignMailBagForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
			new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		errors = validateForm(assignMailBagForm);
		if (errors != null && errors.size() > 0) { 			
			invocationContext.addAllError(errors);
			assignMailBagForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}

		String flightNum = (assignMailBagForm.getFlightNo().toUpperCase());

		AirlineValidationVO airlineValidationVO = null;
		String flightCarrierCode = assignMailBagForm.getFlightCarrierCode().trim().toUpperCase();        	
		if (flightCarrierCode != null && flightCarrierCode.trim().length() >0) {        		
			try {
				log.log(Log.FINE, "flightCarrierCode------------> ",
						flightCarrierCode);
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),flightCarrierCode);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {            			
				Object[] obj = {flightCarrierCode};
				//assignMailBagForm.setListFlag("FAILURE");

				invocationContext.addError(new ErrorVO("mailtracking.defaults.national.assignmailbag.error.invalidcarrier",obj));
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		}

		FlightFilterVO flightFilterVO = handleFlightFilterVO(
				assignMailBagForm,logonAttributes,airlineValidationVO);

		/*******************************************************************
		 * validate Flight 
		 ******************************************************************/
		flightFilterVO.setCarrierCode(flightCarrierCode);		
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
			//assignMailBagForm.setListFlag("FAILURE");			
			invocationContext.addAllError(errors);			
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}



		FlightValidationVO flightValidationVO = null;
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {assignMailBagForm.getFlightCarrierCode(),
					assignMailBagForm.getFlightNo(),
					assignMailBagForm.getFlightDate().toString().substring(0,11)};

			errors.add(new ErrorVO("mailtracking.defaults.national.assignmailbag.error.noflightdetails",obj));
			if(errors != null && errors.size() >0){
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		} 
		else if ( flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			flightValidationVO = flightValidationVOs.iterator().next();			
			assignMailBagSession.setFlightValidationVO(flightValidationVO);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
		}else if(flightValidationVOs.size() >1){
			//Added for duplicate flights 
			log.log(Log.FINE, "flightValidationVOs has one VO");
			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)flightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREEN_ID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
			assignMailBagForm.setDuplicateFlightStatus(FLAG_YES);
			invocationContext.target =DUPLICATE_SUCCESS;
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);

		}
		invocationContext.target = SCREENLOAD_SUCCESS;
	}



	/**
	 * 
	 * @param assignMailBagForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			AssignMailBagForm assignMailBagForm) {
		String flightCarrierCode = assignMailBagForm.getFlightCarrierCode();
		String flightNumber = assignMailBagForm.getFlightNo();
		String depDate = assignMailBagForm.getFlightDate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if(flightCarrierCode == null || flightCarrierCode.trim().length() ==0){
			errors.add(new ErrorVO("mailtracking.defaults.national.assignmailbag.error.flightcarriercodemandatory"));

		}
		if(flightNumber == null || flightNumber.trim().length() == 0){

			errors.add(new ErrorVO("mailtracking.defaults.national.assignmailbag.error.flightnumbermandatory"));
		}
		if(depDate == null || depDate.trim().length() ==0){
			errors.add(new ErrorVO("mailtracking.defaults.national.assignmailbag.error.flightdatemandatory"));
		}

		return errors;
	}
	/**
	 *  
	 * @param assignMailBagForm
	 * @param logonAttributes
	 * @param airlineValidationVO	 
	 * @return flightFilterVO
	 */
	private FlightFilterVO handleFlightFilterVO(
			AssignMailBagForm assignMailBagForm,
			LogonAttributes logonAttributes,AirlineValidationVO airlineValidationVO){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);		
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setActiveAlone(true);
		flightFilterVO.setStringFlightDate(assignMailBagForm.getFlightDate());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		flightFilterVO.setFlightDate(date.setDate(assignMailBagForm.getFlightDate()));
		return flightFilterVO;
	}

}
