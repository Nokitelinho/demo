/*
 * ListUCMINOUTMessageCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ListUCMINOUTMessageCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String MODULE_NAME_FLIGHT = "flight.operation";

	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private static final String DUPLICATE_SUCCESS = "duplicate_success";

	private static final String UCM_OUT = "OUT";
	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(" ScreenLoadCommand", "UCM IN OUT");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		//Modified as part of bug ICRD-238855
		if(form.getUcmOut() != null 
			&& form.getUcmOut().trim().length()>0){
		session.setMessageStatus(form.getUcmOut().toUpperCase());
		}else{
			session.setMessageStatus(UCM_OUT);
			form.setUcmOut(UCM_OUT);
		}
		//Modified as part of bug ICRD-238855 ends
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
		log.log(Log.FINE, "flight number--------->", form.getFlightNo());
		log.log(Log.FINE, "flight date--------->", form.getFlightDate());
		ULDFlightMessageReconcileVO uldFlightMessageReconcileVO=new ULDFlightMessageReconcileVO();
		errors = validateForm(form,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		// validate carriercode
		if (form.getCarrierCode() != null
				&& form.getCarrierCode().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), form.getCarrierCode()
								.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				errors = new ArrayList<ErrorVO>();
				Object[] obj = { form.getCarrierCode().toUpperCase() };
				ErrorVO errorVO = new ErrorVO(
						" uld.defaults.ucminout.msg.err.invalidcarrier", obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
		}
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightValidationVO flightValidationVO = new FlightValidationVO();

		/*
		 * get flight validation vos
		 */
		FlightFilterVO flightFilterVO = null;
		flightFilterVO = getFlightFilterVO(logonAttributes ,
				form, airlineValidationVO);
		//Added by A-7359 for ICRD -192413 starts here
		flightValidationVOs = getFlightDetails(flightFilterVO);
		if ("IN".equals(form.getUcmOut())) {
			//Added by A-7359 fro ICRD-259943 starts here
			uldFlightMessageReconcileVO.setMessageType("IN");
			uldFlightMessageReconcileVO.setOperationFlag("I"); 
			//Added by A-7359 fro ICRD-259943 ends here
		} else {
			//Added by A-7359 for ICRD-223346 
			if(flightValidationVOs !=null&&flightValidationVOs.size() > 0){
			FlightFilterMessageVO uldFlightMessageFilterVO=getFlightFilterMessageVO(logonAttributes,form, airlineValidationVO);
			uldFlightMessageFilterVO.setFlightSequenceNumber((int)((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getFlightSequenceNumber());
			uldFlightMessageReconcileVO=findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
			}
		}  
		//Added by A-7359 for ICRD -192413 ends here
		//Added By Sreekumar S on 28Jan08 - INT_ULD183
		if(flightFilterVO.getDirection() != null && flightFilterVO.getDirection().trim().length() > 0){
			if(("I").equalsIgnoreCase(flightFilterVO.getDirection())){
				session.setContentType(session.getNewContentType());
			}
			else{
				session.setContentType(session.getNewContentType());
			}
		}
		//Added By Sreekumar S on 28Jan08 ends
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, " flightValidationVOs is NULL");
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.ucminout.msg.err.noflightdetails");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		} else if (flightValidationVOs != null
				&& flightValidationVOs.size() == 1) {
			log.log(Log.FINE,
					"\n\n\n********* No Duplicate flights**************");
			flightValidationVO = ((ArrayList<FlightValidationVO>) flightValidationVOs)
					.get(0);
					

		} else {
			log.log(Log.FINE, "\n\n\n*********Duplicate flights**************");
			duplicateFlightSession
					.setFlightValidationVOs((ArrayList<FlightValidationVO>) flightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREEN_ID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			form.setDuplicateFlightStatus(FLAG_YES);
			invocationContext.target = DUPLICATE_SUCCESS;
			return;
		}
		form.setRoute(flightValidationVO.getFlightRoute());

		if (flightValidationVO.getSta() != null) {
			form.setArrivalTime(flightValidationVO.getSta().toString());
		}
		if (flightValidationVO.getStd() != null) {
			form.setDepartureTime(flightValidationVO.getStd().toString());
		}
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes
				.getCompanyCode());
		if (oneTimes != null) {
			Collection<OneTimeVO> resultStatus = oneTimes
					.get("flight.operation.flightlegstatus");
			log.log(Log.FINE, "*******flightlegstatus******");
			flightValidationVO.setStatusDescription(findOneTimeDescription(
					resultStatus, flightValidationVO.getLegStatus()));
		}
		if ("IN".equals(form.getUcmOut())) {
			flightValidationVO.setDirection(FlightFilterVO.INBOUND);
		} else {
			flightValidationVO.setDirection(FlightFilterVO.OUTBOUND);
		}
		
		session.setFlightValidationVOSession(flightValidationVO);
		//Added by A-7359 for ICRD -192413
		session.setMessageReconcileVO(uldFlightMessageReconcileVO);
		
		// for preventing ucm out if end station is origin
		String[] endStations = flightValidationVO.getFlightRoute().split(
				"-");
		String endStation = endStations[endStations.length - 1];
		String startStation=endStations[0];
		log.log(Log.FINE, "\n\n\nEnd station is------------", endStation);
		log.log(Log.FINE, "\n\n\nOrigin from form-------------->", form.getOrigin());
		if ("OUT".equals(form.getUcmOut())
				&& form.getOrigin().equalsIgnoreCase(endStation)) {
			ErrorVO error = new ErrorVO("uld.defaults.cannotsenducmout",
					new Object[] { form.getOrigin().toUpperCase() });
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target=LIST_FAILURE;
			return;

		}
		
		//for preventing ucm in if origin is route origin
		if ("IN".equals(form.getUcmOut())
				&& form.getOrigin().equalsIgnoreCase(startStation)) {
			ErrorVO error = new ErrorVO("uld.defaults.cannotsenducmin",
					new Object[] { form.getOrigin().toUpperCase() });
			errors.add(error);
			invocationContext.addAllError(errors);
			form.setUcmBlockStatus("Y");
			invocationContext.target=LIST_FAILURE;
			return;

		}
		/* logic for destination cmb changed by A-5142*/
		ArrayList<String> stationsFromRoute = new ArrayList<String>();
		if(("IN").equalsIgnoreCase(form.getUcmOut())){
			stationsFromRoute.add(form.getOrigin());
		}
		else if(("OUT").equalsIgnoreCase(form.getUcmOut())){
		String[] stations = flightValidationVO.getFlightRoute().split(
				form.getOrigin().toUpperCase());
			if (stations!=null) {
				String []selectedDestns = stations[1].split("-");
				for (int i = 0; i < selectedDestns.length; i++) {
					if (selectedDestns[i] != null
							&& selectedDestns[i].trim().length() > 0 ) {
						stationsFromRoute.add(selectedDestns[i]);
					}
				}
				log.log(Log.FINE,
						"\n\n\nStations to be set in combo------------->",
						stationsFromRoute);
			}
		}
		
		/*String[] stations = flightValidationVO.getFlightRoute().split(
				startStation.toUpperCase());
		ArrayList<String> stationsFromRoute = new ArrayList<String>();
		if (stations!=null) {
			String[] selectedDestns = null;
		if ("OUT".equals(form.getUcmOut())) {
			selectedDestns = stations[1].split("-");
		}else if ("IN".equals(form.getUcmOut())) {
			selectedDestns = stations[0].split("-");
		}
			

		if (selectedDestns != null) {
			for (int i = 0; i < selectedDestns.length; i++) {

				if (selectedDestns[i] != null
						&& selectedDestns[i].trim().length() > 0) {
					stationsFromRoute.add(selectedDestns[i]);
				}

			}

		}
		
			log.log(Log.FINE, "\n\n\nStations to be set in combo------------->"
					+ stationsFromRoute);

		} else {

		}*/
		
		session.setDestinations(stationsFromRoute);
		session.setOutDestinations(stationsFromRoute);
		if ("IN".equals(session.getMessageStatus())) {
			if (!"Y".equals(form.getOutConfirmStatus())) {
				log.log(Log.INFO, "\n\n\nCHECKING FOR UCMINOUT");
				String route = "";
				if (flightValidationVO.getFlightRoute() != null
						&& flightValidationVO.getFlightRoute().trim().length() > 0) {
					route = flightValidationVO.getFlightRoute();
					ErrorVO error =null;
					if ("IN".equals(session.getMessageStatus())) {
						if (!route.endsWith(form.getOrigin().toUpperCase())) {
							error = new ErrorVO(
									"uld.defaults.ucminout.msg.warning.checkforucmout");
							error.setErrorDisplayType(ErrorDisplayType.WARNING);
							errors.add(error);
						}
					}
				}
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_SUCCESS;
					return;
				} else {
					form.setUcmVOStatus("N");
				}
			}
		} else {
			form.setUcmVOStatus("N");
			form.setUcmOut("OUT"); 
		}
		form.setLinkStatus("Y");
		form.setViewUldStatus("Y");
		form.setMessageTypeStatus("Y");
		invocationContext.target = LIST_SUCCESS;

	}

	/**
	 * 	Method		:	ListUCMINOUTMessageCommand.getFlightFilterMessageVO
	 *	Added by 	:	A-7359 on 12-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@param form
	 *	Parameters	:	@param airlineValidationVO
	 *	Parameters	:	@return 
	 *	Return type	: 	FlightFilterMessageVO
	 */
	private FlightFilterMessageVO getFlightFilterMessageVO(
			LogonAttributes logonAttributes, UCMINOUTForm form,
			AirlineValidationVO airlineValidationVO) {
		log.entering("ListCommand", "getFlightFilterMessageVO");
		FlightFilterMessageVO filtervo = new FlightFilterMessageVO();
		filtervo.setCompanyCode(logonAttributes.getCompanyCode());
		filtervo.setAirportCode(form.getOrigin().toUpperCase());
		filtervo.setFlightNumber(form.getFlightNo().toUpperCase());
		filtervo.setCarrierCode(form.getCarrierCode().toUpperCase());
		filtervo.setFlightCarrierId(airlineValidationVO
				.getAirlineIdentifier());
		filtervo.setMessageType("OUT");
		log.log(Log.FINE, "ucm out----------------->", form.getUcmOut());
		if (form.getFlightDate() != null
				&& form.getFlightDate().trim().length() > 0) {
			LocalDate fltDate = new LocalDate(getApplicationSession()
					.getLogonVO().getAirportCode(), Location.ARP, false);
			filtervo.setFlightDate(fltDate.setDate(form.getFlightDate()));
		}
		log.log(Log.FINE, "Flight filter message vo to server-------------->",
				filtervo);
		log.exiting("ListCommand", "getFlightFilterMessageVO");
		return filtervo;
	}
	/**
	 * Methd to get the Flight FilterVO
	 * 
	 * @param companyCode
	 * @param stationCode
	 * @param form
	 * @param airlineValidationVO
	 * @return flightFilterVO
	 */
	private FlightFilterVO getFlightFilterVO(LogonAttributes logonAttributes ,
			UCMINOUTForm form, AirlineValidationVO airlineValidationVO) {
		log.entering("ListCommand", "getFlightFilterVO");
		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setStation(form.getOrigin().toUpperCase());
		flightFilterVO.setFlightNumber(form.getFlightNo().toUpperCase());
		flightFilterVO.setCarrierCode(form.getCarrierCode().toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO
				.getAirlineIdentifier());
		//added as part of ICRD-271261 by A-4393 starts
		flightFilterVO.setActiveAlone(true); 
		//added as part of ICRD-271261 by A-4393 ends
		log.log(Log.FINE, "ucm out----------------->", form.getUcmOut());
		if ("OUT".equals(form.getUcmOut())) {
			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
			
		} else {
			flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		}

		if (form.getFlightDate() != null
				&& form.getFlightDate().trim().length() > 0) {
			LocalDate fltDate = new LocalDate(getApplicationSession()
					.getLogonVO().getAirportCode(), Location.ARP, false);
			flightFilterVO.setFlightDate(fltDate.setDate(form.getFlightDate()));
		}
		
		// flightFilterVO.setDirection(direction);

		log.log(Log.FINE, "Flight filter vo to server-------------->",
				flightFilterVO);
		log.exiting("ListCommand", "getFlightFilterVO");
		return flightFilterVO;
	}

	/**
	 * 
	 * @param flightFilterVO
	 * @return
	 */
	private Collection<FlightValidationVO> getFlightDetails(
			FlightFilterVO flightFilterVO) {
		Collection<FlightValidationVO> flightValidationVOs = null;
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			flightValidationVOs = delegate.fetchFlightDetails(flightFilterVO);
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return flightValidationVOs;
	}

	/**
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(UCMINOUTForm form,LogonAttributes logonAttributes) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (form.getFlightNo() == null
				|| form.getFlightNo().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.ucminout.msg.err.enterflightno");
			errors.add(error);
		}

		if (form.getCarrierCode() == null
				|| form.getCarrierCode().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.ucminout.msg.err.entercarriercode");
			errors.add(error);
		}
		if (form.getFlightDate() == null
				|| form.getFlightDate().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.ucminout.msg.err.enterflightdate");
			errors.add(error);
		}
		/*added by a-3278 for bug 38883 on 12Mar09
		 * to make the origin station editable in the SendUCMIN/OUT screen
		 * */
		if (form.getOrigin() == null
				|| form.getOrigin().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.ucminout.msg.err.enteroriginstation");
			errors.add(error);
		}else{
			if (validateAirportCodes(form.getOrigin()
					.toUpperCase(), logonAttributes.getCompanyCode()) != null) {
				error = new ErrorVO("uld.defaults.ucminout.msg.err.invalidorigin");
				errors.add(error);
			}
		}
		//a-3278 ends
		return errors;

	}

	/**
	 * This method will be invoked at the time of screen load
	 * 
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add("flight.operation.flightlegstatus");

			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldValues);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	/**
	 * This method will the status description corresponding to the value from
	 * onetime
	 * 
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs,
			String status) {
		if (oneTimeVOs != null) {
			for (OneTimeVO oneTimeVO : oneTimeVOs) {
				if (status.equals(oneTimeVO.getFieldValue())) {
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}
	
	/**
	 * @author A-3278
	 * @param station
	 * @param companyCode
	 * @return
	 */
	public Collection<ErrorVO> validateAirportCodes(String station,
			String companyCode) {
		log.entering("ListCommand", "validateAirportCodes");
		Collection<ErrorVO> errors = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(companyCode, station);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		log.exiting("ListCommand", "validateAirportCodes");
		return errors;
	}
	/**
	 * 
	 * 	Method		:	ListUCMINOUTMessageCommand.findConsolidatedUCMsForFlight
	 *	Added by 	:	A-7359 on 07-Sep-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param uldFlightMessageFilterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	ULDFlightMessageReconcileVO
	 */
	public ULDFlightMessageReconcileVO findConsolidatedUCMsForFlight(FlightFilterMessageVO uldFlightMessageFilterVO){
		log.entering("ListUCMINOUTMessageCommand", "findConsolidatedUCMsForFlight");
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ULDFlightMessageReconcileVO uldFlightMessageReconcileVO=null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			uldFlightMessageReconcileVO = delegate.findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		log.exiting("ListUCMINOUTMessageCommand", "findConsolidatedUCMsForFlight");
		return uldFlightMessageReconcileVO;
	}

}
