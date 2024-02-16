/*
 * ChangeInboundFlightOkCommand.java Created on Oct 06, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmaildetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDInFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3817
 *
 */
public class ChangeInboundFlightOkCommand extends BaseCommand {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";

	/*
	 * INVOCATION TARGETS
	 */
	private static final String INBFLT_FAILURE = "inboundflight_ok_failure";
	private static final String INBFLT_SUCCESS = "inboundflight_ok_success";

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");

	/*
	 * ERROR CODES
	 */
	private static final String FLIGHT_CANCELLED = "mailtracking.defaults.uploadmaildetails.flightcancelled";
	private static final String FLIGHT_STATUS_CANCELLED = "CAN";
	private static final String FLIGHT_NOT_PRESENT = "mailtracking.defaults.noflightdetails";
	private static final String FLIGHT_INBOUND_CLOSED = "mailtracking.defaults.mailarrival.checkflightclosed";
	private static final String FLIGHT_OUTBOUND_CLOSED = "mailtracking.defaults.err.flightclosed";
	private static final String FLIGHT_DUPLICATE = "mailtracking.defaults.uploadmaildetails.flightduplicate";
	private static final String INVALID_CARRIER = "mailtracking.defaults.msg.err.invalidcarrier";
	public static final String  NO_CARRIER_CODE = "mailtracking.defaults.batchupload.changeinboundflight.nocarrier";
	public static final String  NO_FLIGHT_CARRIER_CODE = "mailtracking.defaults.batchupload.changeinboundflight.noflightcarrier";
	public static final String  NO_FLIGHT_NUMBER = "mailtracking.defaults.batchupload.changeinboundflight.noflight";
	public static final String  NO_FLIGHT_DATE = "mailtracking.defaults.batchupload.changeinboundflight.noflightdate";
	public static final String  NO_FLIGHT_AND_CARRIER = "mailtracking.defaults.batchupload.modifyuploaddetails.noflightandcarrier";
	public static final String  POU_NOT_REQUIRED = "mailtracking.defaults.batchupload.modifyuploaddetails.pounotrequiredforcarrier";
	public static final String  NO_BULK_INDICATOR = "mailtracking.defaults.batchupload.modifyuploaddetails.nobulkindicator";
	public static final String  NO_TO_CONTAINER = "mailtracking.defaults.batchupload.modifyuploaddetails.notocontainer";
	public static final String  NO_POU = "mailtracking.defaults.batchupload.modifyuploaddetails.nopou";
	public static final String  NO_DEST = "mailtracking.defaults.batchupload.modifyuploaddetails.nodestination";
	public static final String  INVALID_AIRPORT = "mailtracking.defaults.invalidairport";
	public static final String  INVALID_CONTAINER = "mailtracking.defaults.batchupload.modifyuploaddetails.invaliduldnumber";
	public static final String  CONTAINER_NOT_ASSIGNED = "mailtracking.defaults.batchupload.modifyuploaddetails.containernotassigned";

	/*
	 * Module name and Screen id for duplicate flight
	 */
	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
	private static final String MODULE_NAME_FLIGHT = "flight.operation";

	/*
	 * Constants
	 */
	public static final String FLIGHT_ASSIGNED = "F";

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ChangeInboundFlightOkCommand","execute");
		UploadMailDetailsForm uploadMailDetailsForm = (UploadMailDetailsForm) invocationContext.screenModel;
		UploadMailDetailsSession uploadMailDetailsSession = (UploadMailDetailsSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);

		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		Collection<ErrorVO> errors = null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		int selectedRow = Integer.parseInt(uploadMailDetailsForm.getSelectedScannedVOIndx());
		ScannedMailDetailsVO scannedDetailsVO = ((ArrayList<ScannedMailDetailsVO>)
				uploadMailDetailsSession.getScannedMailDetailsVOs()).get(selectedRow);
		String processPoint = scannedDetailsVO.getProcessPoint();
		uploadMailDetailsForm.setSelectedProcessPoint(processPoint);

		errors = validateForm(uploadMailDetailsForm,scannedDetailsVO,processPoint, errors);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = INBFLT_FAILURE;
			return;
		}
		errors = validateDetailsForUpload(
				uploadMailDetailsSession,
				uploadMailDetailsForm,
				logonAttributes,
				duplicateFlightSession,processPoint);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = INBFLT_FAILURE;
			return;
		}

		updateUploadSession(scannedDetailsVO, uploadMailDetailsForm);
		uploadMailDetailsSession.setMailExceptionDetails(null);
		uploadMailDetailsForm.setInboundFlightCloseflag("Y");

		invocationContext.target = INBFLT_SUCCESS;
		log.exiting("ChangeInboundFlightOkCommand","execute");
	}
	/**
	 *
	 * @param uploadMailDetailsForm
	 * @param errors
	 * @return
	 */
	private Collection<ErrorVO> validateForm(
			UploadMailDetailsForm uploadMailDetailsForm,
			ScannedMailDetailsVO scannedDetailsVO,
			String processPoint,
			Collection<ErrorVO> errors) {
		log.entering("validateForm","execute");
		ErrorVO errorVO = null;
		errors = new ArrayList<ErrorVO>();
		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint) ||
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(processPoint)){
			/*
			 * Acceptance/Reassign Mail
			 */
			if ((uploadMailDetailsForm.getOutboundCarrierCode() == null
					|| uploadMailDetailsForm.getOutboundCarrierCode().trim().length() == 0) &&
					(uploadMailDetailsForm.getOutboundFlightCarrierCode() == null
					|| uploadMailDetailsForm.getOutboundFlightCarrierCode().trim().length() == 0) &&
					(uploadMailDetailsForm.getOutboundFlightNumber() == null
					|| uploadMailDetailsForm.getOutboundFlightNumber().trim().length() == 0) &&
					(uploadMailDetailsForm.getOutboundFlightDate() == null
					|| uploadMailDetailsForm.getOutboundFlightDate().trim().length() == 0)) {
				/*
				 * NO CARRIER/FLIGHT DETAILS
				 */
				errorVO = new ErrorVO(NO_FLIGHT_AND_CARRIER);
				errors.add(errorVO);
			}else if ((uploadMailDetailsForm.getOutboundCarrierCode() != null
					&& uploadMailDetailsForm.getOutboundCarrierCode().trim().length() > 0) &&
					((uploadMailDetailsForm.getOutboundFlightCarrierCode() != null
					&& uploadMailDetailsForm.getOutboundFlightCarrierCode().trim().length() > 0) ||
					(uploadMailDetailsForm.getOutboundFlightNumber()!= null
					&& uploadMailDetailsForm.getOutboundFlightNumber().trim().length() > 0) ||
					(uploadMailDetailsForm.getOutboundFlightDate() != null
					&& uploadMailDetailsForm.getOutboundFlightDate().trim().length() > 0) ||
					(uploadMailDetailsForm.getPou() != null
					&& uploadMailDetailsForm.getPou().trim().length() > 0))) {
				if((uploadMailDetailsForm.getOutboundFlightCarrierCode() == null
						|| uploadMailDetailsForm.getOutboundFlightCarrierCode().trim().length() == 0) &&
						(uploadMailDetailsForm.getOutboundFlightNumber()== null
						|| uploadMailDetailsForm.getOutboundFlightNumber().trim().length() == 0) &&
						(uploadMailDetailsForm.getOutboundFlightDate() == null
						|| uploadMailDetailsForm.getOutboundFlightDate().trim().length() == 0) &&
						(uploadMailDetailsForm.getPou() != null
						&& uploadMailDetailsForm.getPou().trim().length() > 0)){
					/*
					 * NO FLIGHT DETAILS BUT POU MENTIONED
					 */
					errorVO = new ErrorVO(POU_NOT_REQUIRED);
					errors.add(errorVO);
				}else{
					/*
					 * NO CARRIER/FLIGHT DETAILS
					 */
					errorVO = new ErrorVO(NO_FLIGHT_AND_CARRIER);
					errors.add(errorVO);
				}
			}else if (uploadMailDetailsForm.getOutboundCarrierCode() == null
					|| uploadMailDetailsForm.getOutboundCarrierCode().trim().length() == 0) {
				/*
				 * CARRIER Code is not mentioned, but some of the flight details are mentioned.
				 * So verifying all the other fields also.
				 */
				if(uploadMailDetailsForm.getOutboundFlightCarrierCode() == null
						|| uploadMailDetailsForm.getOutboundFlightCarrierCode().trim().length() == 0){
					errorVO = new ErrorVO(NO_FLIGHT_CARRIER_CODE);
					errors.add(errorVO);
				}
				if(uploadMailDetailsForm.getOutboundFlightNumber() == null
						|| uploadMailDetailsForm.getOutboundFlightNumber().trim().length() == 0){
					errorVO = new ErrorVO(NO_FLIGHT_NUMBER);
					errors.add(errorVO);
				}
				if(uploadMailDetailsForm.getOutboundFlightDate() == null
						|| uploadMailDetailsForm.getOutboundFlightDate().trim().length() == 0){
					errorVO = new ErrorVO(NO_FLIGHT_DATE);
					errors.add(errorVO);
				}
				if(uploadMailDetailsForm.getPou() == null
						|| uploadMailDetailsForm.getPou().trim().length() == 0){
					errorVO = new ErrorVO(NO_POU);
					errors.add(errorVO);
				}
			}
			if((MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint)) &&
					(uploadMailDetailsForm.getToBulkFlag() == null
					|| uploadMailDetailsForm.getToBulkFlag().trim().length() == 0)){
				errorVO = new ErrorVO(NO_BULK_INDICATOR);
				errors.add(errorVO);
			}
			if((scannedDetailsVO != null && scannedDetailsVO.getContainerNumber() != null &&
					scannedDetailsVO.getContainerNumber().length() > 0) &&
					(uploadMailDetailsForm.getToContainer() == null
					|| uploadMailDetailsForm.getToContainer().trim().length() == 0)){
				errorVO = new ErrorVO(NO_TO_CONTAINER);
				errors.add(errorVO);
			}
			if(uploadMailDetailsForm.getDest() == null
					|| uploadMailDetailsForm.getDest().trim().length() == 0){
				errorVO = new ErrorVO(NO_DEST);
				errors.add(errorVO);
			}
		}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(processPoint)){
			/*
			 * Arrival
			 */
			if(uploadMailDetailsForm.getInboundCarrierCode() == null
					|| uploadMailDetailsForm.getInboundCarrierCode().trim().length() == 0){
				errorVO = new ErrorVO(NO_FLIGHT_CARRIER_CODE);
				errors.add(errorVO);
			}
			if(uploadMailDetailsForm.getInboundFlightNumber() == null
					|| uploadMailDetailsForm.getInboundFlightNumber().trim().length() == 0){
				errorVO = new ErrorVO(NO_FLIGHT_NUMBER);
				errors.add(errorVO);
			}
			if(uploadMailDetailsForm.getInboundFlightDate() == null
					|| uploadMailDetailsForm.getInboundFlightDate().trim().length() == 0){
				errorVO = new ErrorVO(NO_FLIGHT_DATE);
				errors.add(errorVO);
			}
			if(uploadMailDetailsForm.getFromBulkFlag() == null
					|| uploadMailDetailsForm.getFromBulkFlag().trim().length() == 0){
				errorVO = new ErrorVO(NO_BULK_INDICATOR);
				errors.add(errorVO);
			}
			if(uploadMailDetailsForm.getFromContainer() == null
					|| uploadMailDetailsForm.getFromContainer().trim().length() == 0){
				errorVO = new ErrorVO(NO_TO_CONTAINER);
				errors.add(errorVO);
			}
		}
		log.exiting("validateForm","execute");
		return errors;

	}

	/**
	 * validateCarrierAndFlightForUpload
	 * @param uploadMailDetailsForm
	 * @param logonAttributes
	 * @param duplicateFlightSession
	 * @return
	 */
	private Collection<ErrorVO> validateDetailsForUpload(
			UploadMailDetailsSession uploadMailDetailsSession,
			UploadMailDetailsForm uploadMailDetailsForm,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession,String processPoint) {
		log.entering("validateFlightForUpload", "execute");
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		Collection<String> airports = new ArrayList<String>();
		String alphaCode = null;
		String flightNumber = null;
		LocalDate flightDate = null;
		String pol = null;
		String pou = null;
		String destn = null;
		String containerNumber = null;
		String containerType = null;
		String bulkFlag = null;
		String direction = null;

		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint) ||
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(processPoint)){
			if(uploadMailDetailsForm.getOutboundFlightDate() != null
					&& uploadMailDetailsForm.getOutboundFlightDate().trim().length() > 0){
				alphaCode = uploadMailDetailsForm.getOutboundFlightCarrierCode();
				flightNumber = uploadMailDetailsForm.getOutboundFlightNumber();
				flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
				.setDate(uploadMailDetailsForm.getOutboundFlightDate());
			}else{
				alphaCode = uploadMailDetailsForm.getOutboundCarrierCode();
			}
			pol = logonAttributes.getAirportCode();
			pou = uploadMailDetailsForm.getPou();
			destn = uploadMailDetailsForm.getDest();
			containerNumber = uploadMailDetailsForm.getToContainer();
			bulkFlag = uploadMailDetailsForm.getToBulkFlag();
			if(uploadMailDetailsForm.getToBulkFlag() != null && uploadMailDetailsForm.getToBulkFlag().trim().length() > 0){
				if(MailConstantsVO.FLAG_YES.equals(uploadMailDetailsForm.getToBulkFlag().trim().toUpperCase())){
					containerType = MailConstantsVO.BULK_TYPE;
				}else{
					containerType = MailConstantsVO.ULD_TYPE;
				}
			}
			direction = MailConstantsVO.OPERATION_OUTBOUND;
		}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(processPoint)){
			if(uploadMailDetailsForm.getInboundFlightDate() != null
					&& uploadMailDetailsForm.getInboundFlightDate().trim().length() > 0){
				alphaCode = uploadMailDetailsForm.getInboundCarrierCode();
				flightNumber = uploadMailDetailsForm.getInboundFlightNumber();
				flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
				.setDate(uploadMailDetailsForm.getInboundFlightDate());
			}
			direction = MailConstantsVO.OPERATION_INBOUND;
			pou = logonAttributes.getAirportCode();
			containerNumber = uploadMailDetailsForm.getFromContainer();
			bulkFlag = uploadMailDetailsForm.getFromBulkFlag();
			if(uploadMailDetailsForm.getFromBulkFlag() != null && uploadMailDetailsForm.getFromBulkFlag().trim().length() > 0){
				if(MailConstantsVO.FLAG_YES.equals(uploadMailDetailsForm.getFromBulkFlag().trim().toUpperCase())){
					containerType = MailConstantsVO.BULK_TYPE;
				}else{
					containerType = MailConstantsVO.ULD_TYPE;
				}
			}
		}

		/*
		 * Constructing operationalFlightVO based on the minimum details captured
		 * from the screen for further Flight/Carrier validation.
		 */
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setCarrierCode(alphaCode);
		operationalFlightVO.setFlightNumber(flightNumber);
		operationalFlightVO.setFlightDate(flightDate);
		operationalFlightVO.setDirection(direction);
		operationalFlightVO.setPou(pou);
		operationalFlightVO.setPol(pol);

		/*
		 * Validating the Flight/Carrier details
		 */
		errorVOs = validateFlightOrCarrier(operationalFlightVO,
				logonAttributes, duplicateFlightSession);
		if (errorVOs != null && errorVOs.size() > 0) {
			return errorVOs;
		}
		/*
		 * Validating all the Airport Codes
		 */
		if(pol != null && pol.trim().length() > 0 && !airports.contains(pol)) {
			airports.add(pol);
		}
		if(pou != null && pou.trim().length() > 0 && !airports.contains(pou)) {
			airports.add(pou);
		}
		if(destn != null && destn.trim().length() > 0 && !airports.contains(destn)) {
			airports.add(destn);
		}
		errorVOs = validateAirports(airports,logonAttributes);
		if (errorVOs != null && errorVOs.size() > 0) {
			return errorVOs;
		}
		/*
		 * Validating the container.
		 */
		String modifyContainerOverideFlag = uploadMailDetailsForm.getUploadContinerModifyOverideFlag();
		if(!MailConstantsVO.FLAG_YES.equals(modifyContainerOverideFlag)){
			errorVOs = validateContainer(operationalFlightVO,logonAttributes,
					containerNumber,containerType,destn,processPoint);
			if (errorVOs != null && errorVOs.size() > 0) {
				return errorVOs;
			}
		}

		log.exiting("validateForm","execute");
		return errorVOs;
	}

	/**
	 *
	 * @param airports
	 * @return
	 */
	private Collection<ErrorVO> validateAirports(Collection<String> airports,
			LogonAttributes logonAttributes){
		log.entering("validateAirports","execute");
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();

		AreaDelegate areaDelegate = new AreaDelegate();
		if (airports != null && airports.size() > 0) {
			for(String airport : airports){
				AirportValidationVO airportValidationVO = null;
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				try {
					airportValidationVO = areaDelegate.validateAirportCode(
							logonAttributes.getCompanyCode(),airport.toUpperCase());
				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if ((errors != null && errors.size() > 0) || airportValidationVO == null ) {
					Object[] obj = {airport.toUpperCase()};
					errorVOs.add(new ErrorVO(INVALID_AIRPORT,obj));
				}
			}
		}
		log.exiting("validateAirports","execute");
		return errorVOs;
	}

	/**
	 *
	 * @param operationalFlightVO
	 * @param logonAttributes
	 * @param duplicateFlightSession
	 * @return
	 */
	private Collection<ErrorVO> validateFlightOrCarrier(
			OperationalFlightVO operationalFlightVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession){
		log.entering("validateFlightOrCarrier","execute");
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		/*
		 * Airline Validation
		 */
		AirlineValidationVO airlineValidationVO = populateAirlineValidationVO(
				logonAttributes.getCompanyCode(), operationalFlightVO.getCarrierCode(), errorVOs);
		if (errorVOs != null && errorVOs.size() > 0) {
			return errorVOs;
		}
		if(airlineValidationVO != null){
			operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		}
		if(operationalFlightVO.getFlightDate() != null && airlineValidationVO != null){
			errorVOs = validateFlightForUpload(operationalFlightVO,
					logonAttributes, duplicateFlightSession,airlineValidationVO);
		}else{
			/*
			 *Assigned to a Carrier
			 */
			operationalFlightVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			operationalFlightVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			operationalFlightVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
		}
		log.exiting("validateFlightOrCarrier","execute");
		return errorVOs;

	}
	/**
	 *
	 * @param operationalFlightVO
	 * @param logonAttributes
	 * @param containerNumber
	 * @param containerType
	 * @param destination
	 * @param processPoint
	 * @return
	 */
	private Collection<ErrorVO> validateContainer(
			OperationalFlightVO operationalFlightVO,
			LogonAttributes logonAttributes,String containerNumber,
			String containerType,String destination,String processPoint){
		log.entering("validateContainer","execute");
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		ULDDelegate uldDelegate = new ULDDelegate();
		MailTrackingDefaultsDelegate mailDefaultsDelegate = new MailTrackingDefaultsDelegate();

		ContainerVO containerVO = new ContainerVO();
		containerVO.setCompanyCode(logonAttributes.getCompanyCode());
		containerVO.setAssignedPort(logonAttributes.getAirportCode());
		containerVO.setContainerNumber(containerNumber);
		containerVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		containerVO.setCarrierId(operationalFlightVO.getCarrierId());
		containerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		containerVO.setType(containerType);
		containerVO.setPou(operationalFlightVO.getPou());
		containerVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		containerVO.setFinalDestination(destination);
		if(operationalFlightVO.getFlightSequenceNumber() > 0) {
			containerVO.setAssignmentFlag(FLIGHT_ASSIGNED);
		}
		log.log(Log.FINE, "*******containerVO***", containerVO);
		boolean isULDType = MailConstantsVO.ULD_TYPE.equals(containerType);
//		if(!isULDType && containerNumber != null && containerNumber.length() >= 3) {
//			String containerPart = containerNumber.substring(0,3);
//			log.log(Log.FINE,"$$$$$$containerPart------->>"+containerPart);
//			Collection<String> containerParts = new ArrayList<String>();
//			containerParts.add(containerPart);
//			try {
//				uldDelegate.validateULDTypeCodes(logonAttributes.getCompanyCode(),containerParts);
//				isULDType = true;
//			}catch (BusinessDelegateException businessDelegateException) {
//				isULDType = false;
//			}
//		}

		if(isULDType){
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			try {
				uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNumber);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (errors != null && errors.size() > 0) {
				errorVOs.add(new ErrorVO(INVALID_CONTAINER,new Object[]{containerNumber}));
			}
			if(errorVOs!= null && errorVOs.size() > 0){
				return errorVOs;
			}
			FlightDetailsVO flightDetails = null;
			Collection<ULDInFlightVO> uldInFlightVos = null;
			ULDInFlightVO uldInFlightVo = null;
			flightDetails = new FlightDetailsVO();
			flightDetails.setCompanyCode(logonAttributes.getCompanyCode());
			flightDetails.setCurrentAirport(logonAttributes.getAirportCode());
			if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint)){
				flightDetails.setDirection(MailConstantsVO.EXPORT);
			}
			if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(processPoint)){
				flightDetails.setDirection(MailConstantsVO.IMPORT);
			}
			if (operationalFlightVO.getFlightSequenceNumber() > 0) {
				flightDetails.setFlightNumber(operationalFlightVO.getFlightNumber());
				flightDetails.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
				flightDetails.setFlightCarrierIdentifier(operationalFlightVO.getCarrierId());
			}
			uldInFlightVos = new ArrayList<ULDInFlightVO>();
			uldInFlightVo = new ULDInFlightVO();
			uldInFlightVo.setUldNumber(containerNumber);
			uldInFlightVo.setPointOfLading(operationalFlightVO.getPol());
			uldInFlightVo.setPointOfUnLading(operationalFlightVO.getPou());
			uldInFlightVos.add(uldInFlightVo);
			flightDetails.setUldInFlightVOs(uldInFlightVos);
			Collection<String> uldNumbers=new ArrayList<String>();
			uldNumbers.add(containerNumber);
			try {
				//new ULDDefaultsProxy().validateULDs(flightDetails.getCompanyCode(),flightDetails.getCurrentAirport(),uldNumbers);
				new MailTrackingDefaultsDelegate().validateULDsForOperation(flightDetails);
			}catch (BusinessDelegateException businessDelegateException) {
				errorVOs = handleDelegateException(businessDelegateException);
			}
			if(errorVOs!= null && errorVOs.size() > 0){
				return errorVOs;
			}
		}
		if((MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint) ||
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(processPoint)) &&
				(containerNumber != null && containerNumber.length() > 0)){
			ContainerAssignmentVO containerAssignmentVO = null;
			try {
				containerAssignmentVO = mailDefaultsDelegate.findLatestContainerAssignment(containerNumber);
				if(containerAssignmentVO != null){
					if(!(containerAssignmentVO.getFlightNumber().equals(containerVO.getFlightNumber()) &&
							containerAssignmentVO.getCarrierCode().equals(containerVO.getCarrierCode()) &&
							containerAssignmentVO.getCarrierId() == containerVO.getCarrierId() &&
							containerAssignmentVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber() &&
							containerAssignmentVO.getLegSerialNumber() == containerVO.getLegSerialNumber() &&
							containerAssignmentVO.getAirportCode().equals(containerVO.getAssignedPort()))){
						/*
						 * Validating Container, only if the container is not present in current flight
						 */
						containerVO = mailDefaultsDelegate.validateContainer(logonAttributes.getAirportCode(), containerVO);
					}
				}else if(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(processPoint)){
					errorVOs.add(new ErrorVO(CONTAINER_NOT_ASSIGNED,new Object[]{containerNumber}));
					return errorVOs;
				}
				log.log(Log.FINE, "Resultvo ------------> ", containerVO);
			}catch (BusinessDelegateException businessDelegateException) {
				errorVOs = handleDelegateException(businessDelegateException);
			}
		}
		log.exiting("validateContainer","execute");
		return errorVOs;
	}


	/**
	 *
	 * @param uploadMailDetailsForm
	 * @param logonAttributes
	 * @param duplicateFlightSession
	 * @return
	 */
	private Collection<ErrorVO> validateFlightForUpload(
			OperationalFlightVO operationalFlightVO,
			LogonAttributes logonAttributes,
			DuplicateFlightSession duplicateFlightSession,
			AirlineValidationVO airlineValidationVO) {
		log.entering("validateFlightForUpload", "execute");
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		/*
		 * Validating Flight
		 */
		Collection<FlightValidationVO> flightValidationVOs = populateFlightValidationVO(
				operationalFlightVO, airlineValidationVO,
				logonAttributes, errorVOs);
		if (errorVOs != null && errorVOs.size() > 0) {
			return errorVOs;
		}
		/*
		 * If no error and flightValidationVOs is not null
		 */
		if (flightValidationVOs != null
				&& flightValidationVOs.size() > 0) {
			/*
			 * If the size of flightValidationVOs is 1 then obtain the
			 * flightValidationVO from the first element of the
			 * collection
			 */
			if (flightValidationVOs.size() == 1) {
				duplicateFlightSession.setFlightValidationVO(
						((ArrayList<FlightValidationVO>) flightValidationVOs).get(0));

				FlightValidationVO flightValidationVO = duplicateFlightSession.getFlightValidationVO();

				if (FLIGHT_STATUS_CANCELLED.equalsIgnoreCase(
						flightValidationVO.getFlightStatus())) {
					StringBuffer canErrorMsg = new StringBuffer("");
					canErrorMsg.append(operationalFlightVO.getCarrierCode())
					.append("-").append(operationalFlightVO.getFlightNumber())
					.append(" ").append(operationalFlightVO.getFlightDate());

					Object[] obj = { canErrorMsg.toString() };
					ErrorVO error = new ErrorVO(FLIGHT_CANCELLED, obj);
					errorVOs.add(error);
				}
				if (errorVOs != null && errorVOs.size() > 0) {
					return errorVOs;
				}
				MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

				// Validating Flight Closure
				boolean isFlightClosed = false;
				ErrorVO flightClosureError = null;
				operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
				operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
				operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
				operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());

				if(operationalFlightVO.getDirection().equals(MailConstantsVO.OPERATION_INBOUND)){
					operationalFlightVO.setPou(logonAttributes.getAirportCode());
					try {
						if(mailTrackingDefaultsDelegate.validateInboundFlight(operationalFlightVO)!= null) {
							isFlightClosed = new MailTrackingDefaultsDelegate().isFlightClosedForInboundOperations(operationalFlightVO);
						}
						if(isFlightClosed) {
							flightClosureError = new ErrorVO (FLIGHT_INBOUND_CLOSED);
						}
					} catch (BusinessDelegateException businessDelegateException) {
						log.log(Log.SEVERE, "BusinessDelegateException---->",
								businessDelegateException);
					}
				}else if(operationalFlightVO.getDirection().equals(MailConstantsVO.OPERATION_OUTBOUND)){
					operationalFlightVO.setPol(logonAttributes.getAirportCode());
					try {
						isFlightClosed = new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);
						if(isFlightClosed) {
							flightClosureError = new ErrorVO (FLIGHT_OUTBOUND_CLOSED);
						}
					} catch (BusinessDelegateException businessDelegateException) {
						log.log(Log.SEVERE, "BusinessDelegateException---->",
								businessDelegateException);
					}
				}
				log.log(Log.FINE, "isFlightClosed---->", isFlightClosed);
				if (flightClosureError != null) {
					Object[] obj = {
							operationalFlightVO.getCarrierCode(),
							operationalFlightVO.getFlightNumber(),
							operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat()};
					ErrorVO error = new ErrorVO(flightClosureError.getErrorCode(), obj);
					errorVOs.add(error);
				}
			}
			/*
			 * If there are more than one element in the collection
			 * then, for time being throw an error instead of displaying
			 * the duplicate flight popup
			 */
			else if (flightValidationVOs.size() > 1) {
				// TODO : Display the duplicate flight popup
				log.log(Log.INFO, "###### Duplicate Exist####");
				duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>) flightValidationVOs);
				duplicateFlightSession.setParentScreenId(SCREEN_ID);
				duplicateFlightSession.setFlightFilterVO(
						getFlightFilterVO(operationalFlightVO, logonAttributes,airlineValidationVO));
				ErrorVO error = new ErrorVO(FLIGHT_DUPLICATE);
				errorVOs.add(error);
				if (errorVOs != null && errorVOs.size() > 0) {
					return errorVOs;
				}
			}
		}
		log.exiting("validateFlightForUploads", "execute");
		return errorVOs;
	}

	/**
	 * Method to get the AirlineValidationVO
	 *
	 * @param companyCode
	 * @param alphaCode
	 * @param invocationContext
	 * @return airlineValidationVO
	 */
	private AirlineValidationVO populateAirlineValidationVO(String companyCode,
			String alphaCode, Collection<ErrorVO> errorVOs) {
		log.entering("populateAirlineValidationVO", "execute");
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			airlineValidationVO = airlineDelegate.validateAlphaCode(
					companyCode, alphaCode);

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			e.getMessageVO().getErrors();
		}

		if (errors != null && errors.size() > 0) {
			ErrorVO error = new ErrorVO(INVALID_CARRIER,
					new Object[] { alphaCode });
			errorVOs.add(error);
		}
		log.exiting("populateAirlineValidationVO", "execute");
		return airlineValidationVO;
	}

	/**
	 * Validate the flight and if the flight is valid , returns the collection
	 * of flightvalidation vos
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param airlineValidationVO
	 * @param invocationContext
	 * @param direction
	 * @return
	 */
	private Collection<FlightValidationVO> populateFlightValidationVO(
			OperationalFlightVO operationalFlightVO,
			AirlineValidationVO airlineValidationVO,
			LogonAttributes logonAttributes, Collection<ErrorVO> errorVOs) {

		log.entering("populateFlightValidationVO", "execute");
		Collection<ErrorVO> errors = null;
		/*
		 * Populate the flightFilterVo
		 */
		FlightFilterVO flightFilterVO = getFlightFilterVO(
				operationalFlightVO, logonAttributes, airlineValidationVO);
		log.log(Log.FINE, "\nflightFilterVO ---> ", flightFilterVO);
		/*
		 * Validate flight - obtain the flightValidationVO
		 */
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		Collection<FlightValidationVO> flightValidationVOs = null;
		try {
			flightValidationVOs = delegate.validateFlight(flightFilterVO);
			log.log(Log.FINE, "flightValidationVOs --> ", flightValidationVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			businessDelegateException.getMessageVO().getErrors();
		}
		/*
		 * If error is present then set it to the invocationContext and return
		 */
		if (flightValidationVOs == null || flightValidationVOs.size() == 0) {
			if (errors != null && errors.size() > 0) {
				errorVOs.addAll(errors);
			} else {
				Object[] obj = { operationalFlightVO.getCarrierCode(),
						operationalFlightVO.getFlightNumber(),
						operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat() };
				ErrorVO error = new ErrorVO(FLIGHT_NOT_PRESENT, obj);
				errorVOs.add(error);
			}
		}

		log.exiting("populateFlightValidationVO", "execute");
		return flightValidationVOs;
	}

	/**
	 * Methd to get the Flight FilterVO
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param form
	 * @param direction
	 * @param airlineValidationVO
	 * @return
	 */
	private FlightFilterVO getFlightFilterVO(
			OperationalFlightVO operationalFlightVO,
			LogonAttributes logonAttributes,
			AirlineValidationVO airlineValidationVO) {
		log.entering("getFlightFilterVO", "execute");
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setActiveAlone(true);
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber().toUpperCase());
		flightFilterVO.setFlightDate(operationalFlightVO.getFlightDate());
		flightFilterVO.setDirection(operationalFlightVO.getDirection());
		log.exiting("getFlightFilterVO", "execute");
		return flightFilterVO;
	}

	/**
	 *
	 * @param uploadMailDetailsSession
	 * @param uploadMailDetailsForm
	 */
	private void updateUploadSession(
			ScannedMailDetailsVO scannedDetailsVO,
			UploadMailDetailsForm uploadMailDetailsForm) {
		log.entering("updateUploadSession", "execute");
		String processPoint = uploadMailDetailsForm.getSelectedProcessPoint();
		if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint) ||
				MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(processPoint)){
			if(uploadMailDetailsForm.getOutboundFlightDate() !=null &&
					uploadMailDetailsForm.getOutboundFlightDate().trim().length() > 0){
				scannedDetailsVO.setCarrierCode(uploadMailDetailsForm.getOutboundFlightCarrierCode());
				scannedDetailsVO.setFlightNumber(uploadMailDetailsForm.getOutboundFlightNumber());
				scannedDetailsVO.setFlightDate(new LocalDate(
						LocalDate.NO_STATION,Location.NONE,false).setDate(
								uploadMailDetailsForm.getOutboundFlightDate()));
			}else{
				scannedDetailsVO.setCarrierCode(uploadMailDetailsForm.getOutboundCarrierCode());
				scannedDetailsVO.setFlightNumber(null);
				scannedDetailsVO.setFlightDate(null);
			}
			scannedDetailsVO.setPou(uploadMailDetailsForm.getPou());
			scannedDetailsVO.setDestination(uploadMailDetailsForm.getDest());
			scannedDetailsVO.setContainerNumber(uploadMailDetailsForm.getToContainer());
			if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint)){
				if(MailConstantsVO.FLAG_YES.equals(uploadMailDetailsForm.getToBulkFlag().trim().toUpperCase())){
					scannedDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
				}else{
					scannedDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
				}
			}
		}else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(processPoint)){
			scannedDetailsVO.setCarrierCode(uploadMailDetailsForm.getInboundCarrierCode());
			scannedDetailsVO.setFlightNumber(uploadMailDetailsForm.getInboundFlightNumber());
			scannedDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false).setDate(uploadMailDetailsForm.getInboundFlightDate()));
			scannedDetailsVO.setPol(uploadMailDetailsForm.getPol());
			scannedDetailsVO.setContainerNumber(uploadMailDetailsForm.getFromContainer());
			if(MailConstantsVO.FLAG_YES.equals(uploadMailDetailsForm.getFromBulkFlag().trim().toUpperCase())){
				scannedDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			}else{
				scannedDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			}
		}
		scannedDetailsVO.setExceptionBagCout(0);
		scannedDetailsVO.setErrorMailDetails(null);
		log.exiting("updateUploadSession", "execute");
	}
}
