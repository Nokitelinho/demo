/*
 * SelectFlightDetailsCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
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
public class SelectFlightDetailsCommand extends BaseCommand {

	private static final String SELECT_SUCCESS = "select_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String MODULE_NAME_FLIGHT = "flight.operation";

	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_Messaging");

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("UCM IN OUT", "SELECT FLIGHT DETAILS COMMAND");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		form.setDuplicateFlightStatus(BLANK);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);

		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		FlightValidationVO flightValidationVO = duplicateFlightSession
				.getFlightValidationVO();
		
		

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
		if("IN".equals(form.getUcmOut())){
		flightValidationVO.setDirection(FlightFilterVO.INBOUND);
		}else{
			flightValidationVO.setDirection(FlightFilterVO.OUTBOUND);
			//added as part of ICRD-271261 by A-4393 starts
			if(flightValidationVO !=null){
				ULDFlightMessageReconcileVO uldFlightMessageReconcileVO=new ULDFlightMessageReconcileVO();
				FlightFilterMessageVO uldFlightMessageFilterVO = new FlightFilterMessageVO();
				uldFlightMessageFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				uldFlightMessageFilterVO.setAirportCode(form.getOrigin().toUpperCase());
				uldFlightMessageFilterVO.setFlightNumber(form.getFlightNo().toUpperCase());
				uldFlightMessageFilterVO.setCarrierCode(form.getCarrierCode().toUpperCase());
				uldFlightMessageFilterVO.setFlightCarrierId(flightValidationVO
						.getFlightCarrierId());
				uldFlightMessageFilterVO.setMessageType("OUT");
				log.log(Log.FINE, "ucm out----------------->", form.getUcmOut());
				if (form.getFlightDate() != null
						&& form.getFlightDate().trim().length() > 0) {
					LocalDate fltDate = new LocalDate(getApplicationSession()
							.getLogonVO().getAirportCode(), Location.ARP, false);
					uldFlightMessageFilterVO.setFlightDate(fltDate.setDate(form.getFlightDate()));
				}
				uldFlightMessageFilterVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
				ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
				try {
					uldFlightMessageReconcileVO=delegate.findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
					session.setMessageReconcileVO(uldFlightMessageReconcileVO); 
				} catch (BusinessDelegateException exception) {
					exception.getMessage();
					errors = handleDelegateException(exception);
				}
				}
			//added as part of ICRD-271261 by A-4393 starts
		}
		session.setFlightValidationVOSession(flightValidationVO);
		
//		 for preventing ucm out if end station is origin
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
			invocationContext.target=SELECT_SUCCESS;
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
			invocationContext.target=SELECT_SUCCESS;
			return;

		}
		
		//added as part of ICRD-271261 by A-4393 starts
		form.setRoute(flightValidationVO.getFlightRoute());
		if (flightValidationVO.getSta() != null) {
			form.setArrivalTime(flightValidationVO.getSta().toString());
		}
		if (flightValidationVO.getStd() != null) {
			form.setDepartureTime(flightValidationVO.getStd().toString());
		}
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
		//added as part of ICRD-271261 by A-4393 ends
		
		String[] stations = flightValidationVO.getFlightRoute().split(
				form.getOrigin().toUpperCase());
	ArrayList<String> stationsFromRoute = new ArrayList<String>();
		if(stations.length>1){
		String[] selectedDestns = stations[1].split("-");
		
		for (int i = 0; i < selectedDestns.length; i++) {

			if (selectedDestns[i] != null
					&& selectedDestns[i].trim().length() > 0) {
				stationsFromRoute.add(selectedDestns[i]);
			}

		}
		}else{
			stationsFromRoute=null;
		}
		log.log(Log.FINE, "\n\n\nStations to be set in combo------------->",
				stationsFromRoute);
		session.setDestinations(stationsFromRoute);
		session.setOutDestinations(stationsFromRoute);
		log.log(Log.FINE, "Flight ValidationVo-------------------->", session.getFlightValidationVOSession());
		form.setDuplicateFlightStatus(BLANK);
		 //added as part of ICRD-271261 by A-4393 starts
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
					invocationContext.target = SELECT_SUCCESS;
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
		//added as part of ICRD-271261 by A-4393 ends
		form.setViewUldStatus("Y");
		form.setMessageTypeStatus("Y");
		invocationContext.target = SELECT_SUCCESS;

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
	 * This method will the status description corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		if (oneTimeVOs != null) {
			for (OneTimeVO oneTimeVO:oneTimeVOs){
				if(status.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}
}
