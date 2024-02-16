/*
 * ViewULDsCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
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
public class ViewULDsCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("View ULDs Command", "UCM IN OUT");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ULDFlightMessageReconcileDetailsVO> detailsFromINOUT = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		FlightValidationVO flightValidationVO = session
				.getFlightValidationVOSession();
		ULDFlightMessageReconcileVO reconcileVO = session
				.getMessageReconcileVO();
		int ucmCount=reconcileVO.getUcmReportedCount();
		int totalCount=reconcileVO.getTotalUldCount();
		int manualCount=reconcileVO.getManualUldCount();
		log.log(Log.FINE, "\n\n\nOUT CONFIRM------------>", form.getOutConfirmStatus());
		log.log(Log.FINE, "\n\n\nSERVER CONFIRM------------>", form.getUcmVOStatus());
		errors = validateForm(form);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			return;
		}
		// for ucm in validation

		if (flightValidationVO != null) {
			log.log(Log.FINE, "\n\n\nUCM OUT STATUS----------------->", form.getOutConfirmStatus());
			/*
			 * show warning message and check whether ucm out is required
			 */
			if ("IN".equals(session.getMessageStatus())) {
				if (!"Y".equals(form.getOutConfirmStatus())) {
					log.log(Log.INFO, "\n\n\nCHECKING FOR UCMINOUT");
					errors = checkForUCMOUT(form, session);
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
			}

			if ("N".equals(form.getUcmVOStatus())) {
				if ("IN".equals(session.getMessageStatus())) {
					ArrayList<String> stations = new ArrayList<String>();
					stations.add(form.getOrigin().toUpperCase());
					session.setDestinations(stations);
				} else if ("OUT".equals(session.getMessageStatus())) {
					if (form.getDestination() != null
							&& form.getDestination().trim().length() > 0
							&& !"ALL".equals(form.getDestination())) {
						ArrayList<String> stations = new ArrayList<String>();
						stations.add(form.getDestination().toUpperCase());
						session.setDestinations(stations);
					}
				}
				Collection<ErrorVO> err = new ArrayList<ErrorVO>();
				try {
					reconcileVO = delegate
							.listUCMMessage(populateFlightFilterVO(
									flightValidationVO, form, session));
				} catch (BusinessDelegateException ex) {
					ex.getMessage();
					err = handleDelegateException(ex);
				}

				log.log(Log.FINE, "reconcile vo from server--------->",
						reconcileVO);

			} else if ("Y".equals(form.getUcmVOStatus())) {
				String route = flightValidationVO.getFlightRoute();
				ArrayList<String> stations = new ArrayList<String>();
				String[] loginStationSplit = route.split(form.getOrigin()
						.toUpperCase());
				if(loginStationSplit.length>1){
				String[] outgoingStations = loginStationSplit[1].split("-");
				for (int i = 0; i < outgoingStations.length; i++) {
					if (outgoingStations[i] != null
							&& outgoingStations[i].trim().length() > 0) {
						stations.add(outgoingStations[i]);
					}
				}
				}
				stations.add(form.getOrigin().toUpperCase());
				log.log(Log.FINE, "Stations Set For IN OUT Capture------->",
						stations);
				session.setDestinations(stations);

				log
						.log(Log.INFO,
								"*****************INSIDE INOUT SERVER CALL***************");
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				try {
					detailsFromINOUT = delegate
							.listUCMINOUTMessage(populateFlightFilterVO(
									flightValidationVO, form, session));
				} catch (BusinessDelegateException exception) {
					exception.getMessage();
					error = handleDelegateException(exception);
				}

				log
						.log(
								Log.INFO,
								"***************** DETAILS FROM INOUT SERVER CALL***************",
								detailsFromINOUT);
				for (ULDFlightMessageReconcileDetailsVO detailsVO : detailsFromINOUT) {
					detailsVO
							.setOperationFlag(ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_INSERT);
				}
				reconcileVO.setReconcileDetailsVOs(detailsFromINOUT);
			}

			if (reconcileVO != null
					&& reconcileVO.getFlightCarrierIdentifier() > 0) {
				reconcileVO
						.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
				Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs = reconcileVO
						.getReconcileDetailsVOs();

				if (detailsVOs != null && detailsVOs.size() > 0) {
					for (ULDFlightMessageReconcileDetailsVO detailsVO : detailsVOs) {
						detailsVO
								.setOperationFlag(ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_INSERT);
					}
				}
				reconcileVO.setTotalUldCount(totalCount);
				reconcileVO.setUcmReportedCount(ucmCount);
				reconcileVO.setManualUldCount(manualCount);
				session.setMessageReconcileVO(reconcileVO);
			}
		}
		form.setLinkStatus("Y");
		form.setOrgDestStatus("Y");

		

		invocationContext.target = LIST_SUCCESS;

	}

/**
 * 
 * @param flightValidationVO
 * @param form
 * @param session
 * @return
 */
	private FlightFilterMessageVO populateFlightFilterVO(
			FlightValidationVO flightValidationVO, UCMINOUTForm form,
			UCMINOUTSession session) {
		FlightFilterMessageVO flightFilterMessageVO = new FlightFilterMessageVO();
		flightFilterMessageVO.setCompanyCode(getApplicationSession()
				.getLogonVO().getCompanyCode());
		flightFilterMessageVO.setAirportCode(form.getOrigin().toUpperCase());
		if (form.getDestination() != null
				&& form.getDestination().trim().length() > 0
				&& !"ALL".equalsIgnoreCase(form.getDestination())) {
			if ("OUT".equals(session.getMessageStatus())) {
				flightFilterMessageVO.setPointOfUnloading(form.getDestination()
						.toUpperCase());
			}
		}

		if ("IN".equals(session.getMessageStatus())) {
			flightFilterMessageVO.setPointOfUnloading(form.getOrigin()
					.toUpperCase());

		}

		if ("OUT".equals(session.getMessageStatus())) {
			flightFilterMessageVO.setMessageType("OUT");
		} else if ("IN".equals(session.getMessageStatus())) {
			flightFilterMessageVO.setMessageType("OUT");
		}
		if ("Y".equals(form.getUcmVOStatus())) {
			flightFilterMessageVO.setMessageType(BLANK);
		}

		flightFilterMessageVO.setFlightCarrierId(flightValidationVO
				.getFlightCarrierId());
		flightFilterMessageVO.setCarrierCode(flightValidationVO
				.getCarrierCode());
		flightFilterMessageVO.setFlightDate(flightValidationVO
				.getApplicableDateAtRequestedAirport());
		flightFilterMessageVO.setFlightNumber(flightValidationVO
				.getFlightNumber());

		flightFilterMessageVO.setFlightSequenceNumber((int) flightValidationVO
				.getFlightSequenceNumber());
		flightFilterMessageVO.setLegSerialNumber(flightValidationVO
				.getLegSerialNumber());
		log.log(Log.FINE, "filter vo to server------------->",
				flightFilterMessageVO);
		return flightFilterMessageVO;

	}



	/**
	 * 
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateForm(UCMINOUTForm form) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (form.getOrigin() == null || form.getOrigin().trim().length() == 0) {
			error = new ErrorVO("uld.defaults.ucminout.msg.err.enterorigin");
			errors.add(error);
		}

		/*
		 * if (form.getDestination() != null &&
		 * form.getDestination().trim().length() > 0 &&
		 * !"ALL".equals(form.getDestination())) { if
		 * (validateAirportCodes(form.getDestination().toUpperCase()) != null) {
		 * error = new ErrorVO(
		 * "uld.defaults.ucminout.msg.err.invaliddestination");
		 * errors.add(error); } }
		 */
		return errors;
	}

	/**
	 * @param station
	 * @param logonAttributes
	 * @return errors
	 */
	public Collection<ErrorVO> validateAirportCodes(String station) {
		log.entering("View ULD Command", "validateAirportCodes");
		log.log(Log.FINE, " Station ---> ", station);
		AirportValidationVO airportValidationVO = null;
		Collection<ErrorVO> errors = null;

		try {
			AreaDelegate delegate = new AreaDelegate();
			airportValidationVO = delegate.validateAirportCode(
					getApplicationSession().getLogonVO().getCompanyCode(),
					station);

		} catch (BusinessDelegateException e) {
			e.getMessage();
			log.log(Log.FINE, " Error Airport ---> ", e.getMessageVO().getErrorType());
			errors = handleDelegateException(e);
		}
		return errors;
	}

	/**
	 * 
	 * @param form
	 * @param session
	 * @return
	 */
	private Collection<ErrorVO> checkForUCMOUT(UCMINOUTForm form,
			UCMINOUTSession session) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		FlightValidationVO flightValidationVO = session
				.getFlightValidationVOSession();
		String route = BLANK;
		if (flightValidationVO.getFlightRoute() != null
				&& flightValidationVO.getFlightRoute().trim().length() > 0) {
			route = flightValidationVO.getFlightRoute();

			if ("IN".equals(session.getMessageStatus())) {
				if (!route.endsWith(form.getOrigin().toUpperCase())) {
					error = new ErrorVO(
							"uld.defaults.ucminout.msg.warning.checkforucmout");
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);

				}

			}
		}
		return errors;

	}

}
