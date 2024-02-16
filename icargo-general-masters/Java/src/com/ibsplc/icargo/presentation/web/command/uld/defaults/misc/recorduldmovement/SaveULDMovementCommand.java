/*
 * SaveULDMovementCommand.java Created on jan 29, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.recorduldmovement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDMovementSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;

/**
 *
 * @author A-1936
 *
 */
public class SaveULDMovementCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD_DEFAULTS");

	private static final String SAVE_ULDMOVEMENT_FAILURE = "save_uldmovement_failure";

	private static final String LIST_ULDMOVEMENT = "listUldMvt";

	private static final String LIST_ULD = "listUld";

	private static final String SAVE_ULDERRORLOG = "save_ulderrorlog";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

	private static final String PAGE_URL = "fromScmUldReconcile";

	private static final String SAVE_SCMERRORLOG = "save_scmerrorlog";
	/*
	 * ScreenID for captureHAWB
	 */
	private static final String SCREEN_ID_RECORDULDMOVEMENT = "uld.defaults.misc.recorduldmovement";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SaveULDMovementCommand", "execute");
		RecordULDMovementForm recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;
		ListULDMovementSession listUldMovementSession = (ListULDMovementSession) getScreenSession(
				"uld.defaults", "uld.defaults.misc.listuldmovement");
		ListULDSession listULDSession = (ListULDSession) getScreenSession(
				"uld.defaults", "uld.defaults.listuld");
		RecordUldMovementSession session = (RecordUldMovementSession) getScreenSession(
				"uld.defaults", "uld.defaults.misc.recorduldmovement");
		
		ListUldDiscrepancySession discrepancySession = getScreenSession("uld.defaults", "uld.defaults.listulddiscrepancies");
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String[] uldNumbers = recordULDMovementForm.getUldNumber();
		String[] carrierCode = recordULDMovementForm.getCarrierCode();
		String[] content = recordULDMovementForm.getContent();
		String[] flightDate = recordULDMovementForm.getFlightDateString();
		String[] flightNumber = recordULDMovementForm.getFlightNumber();
		String[] pointOfLading = recordULDMovementForm.getPointOfLading();
		String[] pointOfUnLading = recordULDMovementForm.getPointOfUnLading();
		String[] chkValues = recordULDMovementForm.getDummyMovement();
		String dummyIndex = recordULDMovementForm.getDummyCheckedIndex();
		boolean isValidAirport = true;
		
		String[] selectedIndexes=dummyIndex.split(",");
		
		Collection<ErrorVO> invalidUldFormat = null;
		Collection<ULDMovementVO> uldMovementVos = null;
		AirlineValidationVO airlineValidation = null;
		ArrayList<String> collectionForValidation = null;
		Map<String, AirlineValidationVO> mapForCarrierCode = null;
		Map<String, AirportValidationVO> mapForPointForLading = null;
		  //code removed by nisha for handled carrier
		Collection<LockVO> locks = new ArrayList<LockVO>();
		/*
		 * Validate for client errors. The method will check for mandatory
		 * fields
		 */

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(recordULDMovementForm);

		log.log(Log.FINE, "errors------------->>>", errors);
		if (errors != null && errors.size() > 0) {
			recordULDMovementForm.setErrorFlag("Y");
			
			log.entering("SaveULDMovementCommand ",
					"INSIDE THE ERRORS FOR THE VALIDATION OF FORM after validateForm"
							+ errors);
			log.log(Log.FINE, "recordUld", recordULDMovementForm);
			log.entering("SaveULDMovementCommand ",
					"INSIDE THE ERRORS FOR THE VALIDATION OF FORM");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_ULDMOVEMENT_FAILURE;
			return;
		}


		String[] opFlag = recordULDMovementForm.getHiddenOpFlagForULD();
		String[] opFlagFlight = recordULDMovementForm.getHiddenOpFlag();
		
		if (uldNumbers != null && uldNumbers.length > 1){
			log
			.log(Log.FINE, "\n uldNumbers.length 1c------->>",
					uldNumbers.length);
			int cnt=0;
			for (int i = 0; i < uldNumbers.length; i++) {
				log.log(Log.FINE, "\n opffffffffffflag", opFlag, i);
				if("I".equals(opFlag[i])){
					cnt++;
				}
			}
			if(cnt==0){
				errors.add(new ErrorVO(
				"uld.defaults.recordULDMovement.msg.err.uldnumbermandatory"));
				recordULDMovementForm.setErrorFlag("Y");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_ULDMOVEMENT_FAILURE;
				return;
			}
			
		}else{
			errors.add(new ErrorVO(
			"uld.defaults.recordULDMovement.msg.err.uldnumbermandatory"));
			recordULDMovementForm.setErrorFlag("Y");
			invocationContext.addAllError(errors);
	
			invocationContext.target = SAVE_ULDMOVEMENT_FAILURE;
			return;
		}
		
		if (uldNumbers != null && uldNumbers.length > 0) {
			for (int i = 0; i < uldNumbers.length; i++) {
				log.log(Log.FINE, "\n opFlag[i] 1c------->>", opFlag, i);
				log
						.log(Log.FINE, "\n uldNumbers[i] 1c------->>",
								uldNumbers, i);
				if(!"NOOP".equals(opFlag[i])){
					invalidUldFormat = null;
						invalidUldFormat = validateULDFormat(logonAttributes
								.getCompanyCode(), uldNumbers[i].toUpperCase());
					if (invalidUldFormat != null && invalidUldFormat.size() > 0) {
						errors.addAll(invalidUldFormat);
					}
				}
			}
		}

		/*
		 *
		 * Validation whether the Carrier Codes exist in the System
		 */
		if (carrierCode != null && carrierCode.length > 0) {
			invalidUldFormat = null;
			collectionForValidation = new ArrayList<String>();
			for (int i = 0; i < carrierCode.length; i++) {
				log.log(Log.INFO, "opr flag***", opFlagFlight, i);
				if (carrierCode[i] != null
						&& !(carrierCode[i].trim().length() == 0)
						&& !collectionForValidation.contains(carrierCode[i]
								.toUpperCase())&& !"NOOP".equals(opFlagFlight[i])) {
					collectionForValidation.add(carrierCode[i].toUpperCase());
				}
				log.log(Log.FINE, "VALUE OF CARRIERCODES:", carrierCode, i);
			}
			try {
				log.log(Log.FINE, "SIZE OF CARRIERCODES:",
						collectionForValidation.size());
				log.entering("SaveULDMovementCommand ",
						"CALLING VALIDATE ALPHACODES");
				mapForCarrierCode = new AirlineDelegate().validateAlphaCodes(
						logonAttributes.getCompanyCode(),
						collectionForValidation);
			} catch (BusinessDelegateException businessDelegateException) {
				log.entering("SaveULDMovementCommand",
						"Business Delegate Exception");
				invalidUldFormat = handleDelegateException(businessDelegateException);
			}

			if (invalidUldFormat != null && invalidUldFormat.size() > 0) {
				errors.addAll(invalidUldFormat);
			}
		}

		/*
		 * Validation for the PointOfLading and PointOfLading Airport code Exist
		 * in the System
		 */
		collectionForValidation = new ArrayList<String>();
		if (pointOfLading != null && pointOfLading.length > 0){
			for (int i = 0; i < pointOfLading.length; i++) {
				log.log(Log.INFO, "opr flag***", opFlagFlight, i);
				if (!collectionForValidation.contains(pointOfLading[i]
						.toUpperCase()) && pointOfLading[i]!=null && pointOfLading[i].length()>0 && !"NOOP".equals(opFlagFlight[i])) {
					collectionForValidation.add(pointOfLading[i].toUpperCase());
					log.log(Log.FINE, "SIZE OF POL", collectionForValidation.size());
				}
			}
		}
		if( pointOfUnLading != null && pointOfUnLading.length > 0) {		
			
			for (int i = 0; i < pointOfUnLading.length; i++) {
				log.log(Log.INFO, "opr flag***", opFlagFlight, i);
				if (!collectionForValidation.contains(pointOfUnLading[i]
						.toUpperCase()) && pointOfUnLading[i]!=null && pointOfUnLading[i].length()>0 && !"NOOP".equals(opFlagFlight[i])) {
					collectionForValidation.add(pointOfUnLading[i]
							.toUpperCase());
					log.log(Log.FINE, "SIZE OF POL", collectionForValidation.size());
				}
				if (recordULDMovementForm.getCurrentStation() != null
						&& recordULDMovementForm.getCurrentStation().trim()
								.length() > 0
						&& !collectionForValidation
								.contains(recordULDMovementForm
										.getCurrentStation().toUpperCase())) {
					collectionForValidation.add(recordULDMovementForm
							.getCurrentStation().toUpperCase());

				}

			}
		}

		if (collectionForValidation != null) {
			invalidUldFormat = null;
			try {
				log.entering("SaveULDMovementCommand ",
						"CALLING VALIDATE AIRPORTCODES"+collectionForValidation.size());
				mapForPointForLading = new AreaDelegate().validateAirportCodes(
						logonAttributes.getCompanyCode(),
						collectionForValidation);
			} catch (BusinessDelegateException businessDelegateException) {
				isValidAirport = false;
				log.entering("SaveULDMovementCommand ",
						"CALLING VALIDATE AIRPORTCODES Business Delegate");
				businessDelegateException.getMessage();
				invalidUldFormat = handleDelegateException(businessDelegateException);
			}
			if (invalidUldFormat != null && invalidUldFormat.size() > 0) {
				errors.addAll(invalidUldFormat);
			}
		}
		/*
		 *
		 * Validate whether the POL and POU are the same
		 */
		if (pointOfLading != null && pointOfLading.length > 0
				&& pointOfUnLading != null && pointOfUnLading.length > 0) {
			for (int i = 0; i < pointOfLading.length; i++) {
				if(pointOfLading[i].length() >0 && pointOfLading[i]!=null && pointOfUnLading[i].length() >0 && pointOfUnLading[i]!=null && !"NOOP".equals(opFlagFlight[i]) ){
					if (pointOfLading[i].equals(pointOfUnLading[i])) {
						errors.add(new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.polpou"));
					}
				}
			}
		}
		Collection<String> checkValuesColl = new ArrayList<String>();
		if (selectedIndexes != null) {
			for (int i = 0; i < selectedIndexes.length; i++) {
				if(selectedIndexes[i] !=null && selectedIndexes[i].length()>0 && !"NOOP".equals(opFlagFlight[i])){
				checkValuesColl.add(selectedIndexes[i]);
				log.log(Log.FINE, "THE VALUES IN THE COLLECTION",
						selectedIndexes, i);
				log.log(Log.FINE, "THE VALUES IN THE COLLECTIUON ARE ",
						checkValuesColl);
				}
			}
		}


	/*	if (chkValues != null) {
			for (int i = 0; i < chkValues.length; i++) {
				if(chkValues[i] !=null && chkValues[i].length()>0 && !"NOOP".equals(opFlagFlight[i])){
				checkValuesColl.add(chkValues[i]);
				log.log(Log.FINE, "THE VALUES IN THE COLLECTION ARE "
						+ chkValues[i]);
				log.log(Log.FINE, "THE VALUES IN THE COLLECTIUON ARE "
						+ checkValuesColl);
				}
			}
		}*/

		/*
		 * If DummyMovement is TRUE Validates the Flight and if Flight is Valid
		 * validates wether the PointOfLading and POintofUNLading are in the
		 * Flight Route
		 *
		 */
		//ArrayList<String> flightKeyVals=new ArrayList<String>();
		//HashMap<String , Collection<FlightValidationVO>> flights = null;
		if (carrierCode != null && carrierCode.length > 0
				&& mapForCarrierCode != null && mapForCarrierCode.size() > 0 && isValidAirport) {
			Collection<FlightFilterVO> flights = null;
			invalidUldFormat = null;
			Collection<FlightFilterVO> coll = new ArrayList<FlightFilterVO>();
			for (int i = 0; i < carrierCode.length; i++) {
				if (!checkValuesColl.contains(String.valueOf(i))
						&& carrierCode[i] != null
						&& carrierCode[i].trim().length() > 0 && !"NOOP".equals(opFlagFlight[i])) {
					log.log(Log.FINE, "AIRLINE IDENTIFIER IS", logonAttributes.getAirlineIdentifier());
					log.log(Log.FINE, " OWNER AIRLINE IDENTIFIER IS",
							logonAttributes.getOwnAirlineIdentifier());
					log.log(Log.FINE, " OWNER AIRLINE IDENTIFIER CODEC IS",
							logonAttributes.getOwnAirlineNumericCode());
						log.entering("SaveULDMovementCommand ",
								"INSIDE THE VALIDATION FOR THE FLIGHT");
						FlightFilterVO flightFilterVO = new FlightFilterVO();
						flightFilterVO.setCarrierCode(carrierCode[i]
								.toUpperCase());
						flightFilterVO.setCompanyCode(logonAttributes
								.getCompanyCode());
						flightFilterVO.setFlightNumber(flightNumber[i].toUpperCase());
						flightFilterVO.setFlightCarrierId(mapForCarrierCode
								.get(carrierCode[i].toUpperCase())
								.getAirlineIdentifier());
						log.log(Log.FINE, " FlightCarrierId :", flightFilterVO.getFlightCarrierId());
						LocalDate flightDateLocal = new LocalDate(pointOfLading[i].toUpperCase(),Location.ARP, false);
						log.log(Log.FINE, " flightDateLocal ", flightDateLocal);
						flightDateLocal.setDate(flightDate[i]);
						log.log(Log.FINE, " flightDateLocal ", flightDateLocal);
						flightFilterVO.setFlightDate(flightDateLocal);
						flightFilterVO
								.setOrigin(pointOfLading[i].toUpperCase());
						flightFilterVO.setDestination(pointOfUnLading[i]
								.toUpperCase());
						coll.add(flightFilterVO);
						 log.log(Log.FINE, " THE SIZE OF THE COLLECTION ", coll.size());
						
log.log(Log.FINE, " THE SIZE OF THE COLLECTION ", coll.size());
				}
			}
			if (coll != null && coll.size() > 0) {
				try {
					flights = new ULDDefaultsDelegate()
							.validateFlightsForSegments(coll);
				} catch (BusinessDelegateException businessDelegateException) {
					log.entering("ERRORS CREATED FOR THE", "VALIDATE FLIGHT");
					invalidUldFormat = handleDelegateException(businessDelegateException);
				}

				if (flights != null && flights.size() > 0) {
					log.entering("INVALID FLIGHTS ARE THERE",
							" CREATE THE ERRORS");
					log.entering("INVALID FLIGHTS ARE THERE",
							" CREATE THE ERRORS");
					for (FlightFilterVO vo : flights) {

						String str = new StringBuffer(vo.getCarrierCode())
								.append("-")
								.append(vo.getFlightNumber())
								.append("-")
								.append(
										TimeConvertor
												.toStringFormat(
														vo.getFlightDate()
																.toCalendar(),
														TimeConvertor.CALENDAR_DATE_FORMAT))
								.toString();
						log.log(Log.FINE, "THE APPENDED FLIGHT IS >>>>>>>>>",
								str);
						errors
								.add(new ErrorVO(
										"uld.defaults.recordULDMovement.msg.err.invalidflights",
										new Object[] { str }));

					}
					 //code removed by nisha for handled carrier
				}
			}
		}

		if (errors != null && errors.size() > 0) {
			recordULDMovementForm.setErrorFlag("Y");
			log.entering("INSIDE THE ERRORS ",
					"INSIDE THE ERRORS FOR THE FIELDVALIDATION");
			log.entering("INSIDE THE ERRORS ",
					"INSIDE THE ERRORS FOR THE FIELDVALIDATION");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_ULDMOVEMENT_FAILURE;
			return;
		}

		 //code removed by nisha for handled carrier
		/*
		 * Create a Collection of the UldNumbers for the Persistence
		 *
		 */
	//	 String[] opFlag = recordULDMovementForm.getHiddenOpFlagForULD();
		if (uldNumbers != null && uldNumbers.length > 0) {
			collectionForValidation = new ArrayList<String>();
			for (int i = 0; i < uldNumbers.length; i++) {
				if(!"NOOP".equals(opFlag[i])){
					collectionForValidation.add(uldNumbers[i].toUpperCase());
					locks = prepareLocksForSave(uldNumbers);
				}
			}
		   //code removed
		}
		/*
		 *
		 * Create a collection of uldMovementVos for persistence
		 */
		//if (carrierCode != null && carrierCode.length > 0) {
		if (pointOfUnLading != null && pointOfUnLading.length > 0) {
			uldMovementVos = new ArrayList<ULDMovementVO>();
			//for (int i = 0; i < carrierCode.length; i++) {
				for (int i = 0; i < pointOfUnLading.length; i++) {
				if(pointOfUnLading[i] !=null && pointOfUnLading[i].length()>0 && !"NOOP".equals(opFlagFlight[i])){
					ULDMovementVO vo = new ULDMovementVO();
					 //code removed by nisha for handled carrier
					if (carrierCode[i] != null
							&& carrierCode[i].trim().length() > 0) {
						airlineValidation = mapForCarrierCode.get(carrierCode[i]
								.toUpperCase());
						log.log(Log.FINE, " AIRLINE IDENTIFIER ",
								airlineValidation.getAirlineIdentifier());
						vo.setFlightCarrierIdentifier(airlineValidation
								.getAirlineIdentifier());
					}
					//Change made by SreekumarS on 24Jan08 INT BUG - ULD158
					if(content[i] != null && content[i].trim().length() > 0){
						vo.setContent(content[i]);
					}else{
						vo.setContent("E");
					}
					//Change made by SreekumarS ends
					if (checkValuesColl.contains(String.valueOf(i))) {
						vo.setDummyMovement(true);
					} else {
						vo.setDummyMovement(false);
					}
					
					if (flightDate[i] != null && flightDate[i].trim().length() > 0 &&
							pointOfLading[i] != null && pointOfLading[i].trim().length()>0) {
						LocalDate flightDateLocal = new LocalDate(pointOfLading[i].toUpperCase(),Location.ARP, false);
						vo.setFlightDate(flightDateLocal.setDate(flightDate[i]));
					}
					if(flightNumber[i]!=null && flightNumber[i].trim().length()>0){
						vo.setFlightNumber(flightNumber[i].toUpperCase());
					}
					if(pointOfLading[i]!=null && pointOfLading[i].trim().length()>0){
						vo.setPointOfLading(pointOfLading[i].toUpperCase());
					}
					if(pointOfUnLading[i]!=null && pointOfUnLading[i].trim().length()>0){
						vo.setPointOfUnLading(pointOfUnLading[i].toUpperCase());
					}
					if(carrierCode[i]!=null && carrierCode[i].trim().length()>0){
						vo.setCarrierCode(carrierCode[i].toUpperCase());
					}
					vo.setUpdateCurrentStation(recordULDMovementForm
							.getUpdateCurrentStation());
					log.log(Log.FINE, "UPDATE CURRENT STATION",
							recordULDMovementForm.getUpdateCurrentStation());
					vo.setCurrentStation(recordULDMovementForm.getCurrentStation()
							.toUpperCase());
					vo.setRemark(recordULDMovementForm.getRemarks());
					log.log(Log.FINE, "REMARKS REMARKS REMARKS",
							recordULDMovementForm.getRemarks());
					log
							.log(Log.FINE, "REMARKS REMARKS REMARKS", vo.getRemark());
					vo.setLastUpdatedUser(logonAttributes.getUserId());
					//vo.setPolLocation(discrepancySession.getPolLocation());
					if(discrepancySession.getDiscrepancyDetails()!=null){
						log
								.log(
										Log.INFO,
										"discrepancySession.getDiscrepancyDetails()---->>>>>>",
										discrepancySession.getDiscrepancyDetails());
						log.log(Log.FINE, "Location in SaveCommand------->>",
								discrepancySession.getDiscrepancyDetails().getLocation());
						vo.setPouLocation(discrepancySession.getDiscrepancyDetails().getLocation());
						log.log(Log.FINE,
								"FacilityType in SaveCommand------->>",
								discrepancySession.getDiscrepancyDetails().getFacilityType());
						vo.setFacilityType(discrepancySession.getDiscrepancyDetails().getFacilityType());
						vo.setReportingAirport(discrepancySession.getDiscrepancyDetails().getReportingStation());
						vo.setDiscrepancyDate(discrepancySession.getDiscrepancyDetails().getDiscrepencyDate());
						vo.setRemarks(discrepancySession.getDiscrepancyDetails().getRemarks());
						vo.setDiscrepancyCode(discrepancySession.getDiscrepancyDetails().getDiscrepencyCode());
					}
					//added by nisha on 07feb08 starts
					//last movement date is set based on the current station
					if(vo.getCurrentStation()!=null && vo.getCurrentStation().trim().length()>0){
						vo.setLastUpdatedTime(new LocalDate(vo.getCurrentStation(),Location.ARP, true));
					}else if(pointOfUnLading[i]!=null && pointOfUnLading[i].trim().length()>0){
						vo.setLastUpdatedTime(new LocalDate(pointOfUnLading[i].toUpperCase(),Location.ARP, true));
					}
					//added by nisha on 07feb08 starts
					vo.setCompanyCode(logonAttributes.getCompanyCode());
					if ("ulddiscrepancy".equals(recordULDMovementForm
							.getDiscrepancyStatus())) {
						vo.setDiscrepancyToBeSolved(true);
						log.log(Log.FINE, "the DISCREPANCY STATUS IS ", vo.isDiscrepancyToBeSolved());
						log
								.log(
										Log.FINE,
										"the DISCREPANCY STATUS IS collect6ion in session",
										session.getULDMovementVOs());
						//added by nisha for bugfix starts
						if(session.getULDMovementVOs()!=null && session.getULDMovementVOs().size()>0){
							vo.setDiscrepancyCode(session.getULDMovementVOs().iterator().next().getDiscrepancyCode());
						}
						//ends
					}
					uldMovementVos.add(vo);
				}
				}
		}

		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		log.log(Log.FINE,
				"before settg to lock delegate collectionForValidation >>",
				collectionForValidation);
		log.log(Log.FINE, "LockVO for implicit check", locks);
		log.log(Log.FINE,
				"before settg to delegate collectionForValidation >>",
				collectionForValidation);
		log.log(Log.FINE, "before settg to delegate uldMovementVos >>",
				uldMovementVos);
		log.log(Log.FINE, "before settg to delegate uldMovementVos >>",
				uldMovementVos.size());
		//Added by Tarun
		if(uldMovementVos.size()==0){
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.carriercode"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.flightnumber"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.flightdate"));
			//errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.pointoflading"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.pointofUnlading"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.content"));
			
			recordULDMovementForm.setErrorFlag("Y");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_ULDMOVEMENT_FAILURE;
			return;
		}
		//Tarun ends
		try {
			String overrideError = "N";
			if(recordULDMovementForm.getOverrideError()!=null && recordULDMovementForm.getOverrideError().startsWith("canCreateULD")){
				overrideError = recordULDMovementForm.getOverrideError();
			}					
			delegate.saveULDMovement(collectionForValidation, uldMovementVos,"REC_ULD",overrideError,locks);
		} catch (BusinessDelegateException businessDelegateException) {
			invalidUldFormat = handleDelegateException(businessDelegateException);
			errors.addAll(invalidUldFormat);
		}

		if (errors != null && errors.size() > 0) {
			log.entering("INSIDE THE ERRORS ",
					"INSIDE THE ERRORS FOR THE VALIDATION OF FORM");
			log.entering("INSIDE THE ERRORS ",
					"INSIDE THE ERRORS FOR THE VALIDATION OF FORM");
			recordULDMovementForm.setErrorFlag("Y");
			for(ErrorVO errorVO : errors) {
				StringBuffer errorCodes = new StringBuffer();
				if("uld.defaults.warning.uldisnotinthesystem".equals(errorVO.getErrorCode()) && errorVO.getErrorData()!=null && errorVO.getErrorData().length>0){
					errorCodes.append("canCreateULD").append("~").append(errorVO.getErrorData()[0]);
				}
				recordULDMovementForm.setOverrideError(errorCodes.toString());
			}						
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_ULDMOVEMENT_FAILURE;
			return;
		}

		if ((("fromulderrorlog").equals(recordULDMovementForm.getPageurl()) 
						|| ("fromulderrorlogMessage").equals(recordULDMovementForm.getPageurl()))) {
			ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					"uld.defaults", SCREENID_ULDERRORLOG);
			log
					.log(
							Log.FINE,
							"\n \n listULDTransactionSession.getULDFlightMessageReconcileDetailsVO()",
							session.getULDFlightMessageReconcileDetailsVO());
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				log.log(Log.FINE, "\n reconcile  delegate ");
				new ULDDefaultsDelegate().reconcileUCMULDError(session
						.getULDFlightMessageReconcileDetailsVO());
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			recordULDMovementForm.setPageurl("close");
			uldErrorLogSession.setPageURL("fromrecorduld");
			session.removeAllAttributes();
			invocationContext.target = SAVE_ULDERRORLOG;
			return;
		} else if (PAGE_URL.equals(recordULDMovementForm.getPageurl())) {
			recordULDMovementForm.setPageurl("scm_close");
			session.removeAllAttributes();
			invocationContext.target = SAVE_SCMERRORLOG;
			return;
		}
		
		uldMovementVos.clear();
		ULDMovementVO uldMovementVo = new ULDMovementVO();
	    uldMovementVo.setFlightDateString("");
	    uldMovementVos.add(uldMovementVo);
	    session.setULDMovementVOs(uldMovementVos);
		session.setULDNumbers(null);
		clearRecordULDMovementForm(recordULDMovementForm);
		log.log(Log.FINE, "THE SCREENNAME IS ", recordULDMovementForm.getScreenName());
		log.log(Log.FINE, "THE SCREENNAME IS ", recordULDMovementForm.getScreenName());
		if (LIST_ULD.equals(recordULDMovementForm.getScreenName())) {
			log.log(Log.INFO, "PARENT SCREEN IS LISTULD");

			log.log(Log.INFO, "PARENT SCREEN IS LISTULD");
			listULDSession.setListStatus("noListForm");
			recordULDMovementForm.setFlagForCheck("list");
			// invocationContext.target="list_listuld";
		} else if (LIST_ULDMOVEMENT.equals(recordULDMovementForm
				.getScreenName())) {
			log.log(Log.INFO, "PARENT SCREEN IS LISTULDMOVEMENT");
			log.log(Log.INFO, "PARENT SCREEN IS LISTULDMOVEMENT");
			listUldMovementSession.setListStatus("recorduld");
			recordULDMovementForm.setFlagForCheck("listmvt");
			// invocationContext.target= "list_listuldmvt";
		} else if (recordULDMovementForm.getScreenName() == null
				|| "ulddiscrepancy".equals(recordULDMovementForm
						.getDiscrepancyStatus())) {
			recordULDMovementForm.setFlagForCheck("ulddiscrepancy");
			log.log(Log.INFO,
					"recordULDMovementForm.getFlagForCheck()---->>>>",
					recordULDMovementForm.getFlagForCheck());
			recordULDMovementForm.setFlagForUldDiscrepancy("ulddiscrepancy");
			log.log(Log.INFO,
					"recordULDMovementForm.getFlagForUldDiscrepancy()---->>>>",
					recordULDMovementForm.getFlagForUldDiscrepancy());
		}else{
			//ErrorVO error = new ErrorVO("uld.defaults.recordULDMovement.msg.err.datasavedsuccessfully");
			log.log(Log.INFO,"inside else loop");
			ErrorVO error = new ErrorVO("uld.defaults.recordULDMovement.savedsuccessfully");
			error.setErrorDisplayType(ErrorDisplayType.STATUS);
			log.log(Log.INFO,"inside else loop after setting");
			errors.add(error);
			if(errors != null && errors.size() > 0){
				invocationContext.addAllError(errors);
			}
		}
		//ErrorVO error = new ErrorVO("uld.defaults.recordULDMovement.savedsuccessfully");
	    //error.setErrorDisplayType(ErrorDisplayType.STATUS);
	    //errors = new ArrayList<ErrorVO>();
	   //errors.add(error);
	   // invocationContext.addAllError(errors);
		invocationContext.target = "save_success";

	}

	/**
	 * Method to check for mandatory fields.
	 *
	 * @param form
	 * @return errors
	 */
	private Collection<ErrorVO> validateForm(RecordULDMovementForm form) {
		log.entering("INSIDE THE VALIDATE METOD", "validateForm");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		String[] carrierCode = form.getCarrierCode();
		String[] flightNumber = form.getFlightNumber();
		String[] flightDate = form.getFlightDateString();
		String[] pointOfLading = form.getPointOfLading();
		String[] pointOfUnLading = form.getPointOfUnLading();
		String[] content = form.getContent();
		String[] uldNumbers = form.getUldNumber();
		String[] chkValues = form.getDummyMovement();
		String[] opFlagFlight = form.getHiddenOpFlag();
		Collection<String> collectionForValidation = null;
		Collection<String> checkValuesColl = new ArrayList<String>();

		//Added by A-4421 for ICRD-3078
		String dummyIndex = form.getDummyCheckedIndex();
		String[] selectedIndexes=dummyIndex.split(",");
		
		//Added by Tarun on 27-12-07
		//Validating whether min of one row is adding
		boolean size=false;
		if(opFlagFlight!=null){
			for(int i=0;i<opFlagFlight.length;i++){
				if(!("NOOP").equals(opFlagFlight[i])){
					size=true;
					break;
				}
			}
		}
		
		if(!size){
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.carriercode"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.flightnumber"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.flightdate"));
			//errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.pointoflading"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.pointofUnlading"));
			errors.add(new ErrorVO("uld.defaults.recordULDMovement.msg.err.content"));
		}
		//Tarun ends
		
	/*	if (chkValues != null) {
			for (int i = 0; i < chkValues.length; i++) {
				checkValuesColl.add(chkValues[i]);
				log.log(Log.FINE, "THE VALUES IN THE COLLECTIUON ARE "
						+ checkValuesColl);
			}
		}*/
		if(selectedIndexes!=null){
			for(int i=0;i<selectedIndexes.length;i++){
				checkValuesColl.add(selectedIndexes[i]);
				log.log(Log.FINE, "THE VALUES IN THE COLLECTIUON ARE ",
						checkValuesColl);
			}
		}

		/*
		 *
		 * Validates wether the CarrierCode is Mandatory
		 */
		if (carrierCode != null && carrierCode.length > 0) {
			for (int i = 0; i < carrierCode.length; i++) {
				if(!"NOOP".equals(opFlagFlight[i])){
				if (carrierCode[i] == null
						|| carrierCode[i].trim().length() == 0) {
					log.log(Log.FINE, "CHECK FOR VALIDATION ", carrierCode, i);
					if (!checkValuesColl.contains(String.valueOf(i))) {
						error = new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.carriercode");
						errors.add(error);
						break;
					}

				}
			}
		}
		}

		/*
		 * Validates the FlightNumber is mandatory
		 */
		if (flightNumber != null && flightNumber.length > 0) {
			for (int i = 0; i < flightNumber.length; i++) {
				if(!"NOOP".equals(opFlagFlight[i])){
				if (flightNumber[i] == null
						|| flightNumber[i].trim().length() == 0) {
					log.log(Log.FINE, "CHECK FOR VALIDATION ", flightNumber, i);
					if (!checkValuesColl.contains(String.valueOf(i))) {
						error = new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.flightnumber");
						errors.add(error);
						break;
					}

				}
			}
		}
		}
		/*
		 * Validates wether the FlightDate is mandatory
		 *
		 */
		if (flightDate != null && flightDate.length > 0) {
			for (int i = 0; i < flightDate.length; i++) {
				if(!"NOOP".equals(opFlagFlight[i])){
				if (flightDate[i] == null || flightDate[i].trim().length() == 0) {
					log.log(Log.FINE, "CHECK FOR VALIDATION ", flightDate, i);
					if (!checkValuesColl.contains(String.valueOf(i))) {
						error = new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.flightdate");
						errors.add(error);
						break;
					}

				}
			}
		}
		}
		/*
		 * Validates whether the PoL is present
		 * need to be validated only if it is flight movement...
		 *
		 */
		if (pointOfLading != null && pointOfLading.length > 0) {
			for (int i = 0; i < pointOfLading.length; i++) {
				if(!"NOOP".equals(opFlagFlight[i])){
				if (pointOfLading[i] == null
						|| pointOfLading[i].trim().length() == 0) {
					if (!checkValuesColl.contains(String.valueOf(i))) {
						log.log(Log.FINE, "CHECK FOR VALIDATION FOR POL ",
								pointOfLading, i);
						error = new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.pointoflading");
						errors.add(error);
						break;
					}
				}
			}
		}
		}
		/*
		 *
		 * validates the PointOFLanding for the Mandatory
		 */
		if (pointOfUnLading != null && pointOfUnLading.length > 0) {
			for (int i = 0; i < pointOfUnLading.length; i++) {
				if(!"NOOP".equals(opFlagFlight[i])){
				if (pointOfUnLading[i] == null
						|| pointOfUnLading[i].trim().length() == 0) {
					log.log(Log.FINE, "CHECK FOR VALIDATION ", pointOfUnLading,
							i);
					error = new ErrorVO(
							"uld.defaults.recordULDMovement.msg.err.pointofUnlading");
					errors.add(error);
					break;
				}
			}
		}
		}
		/*
		 * Mandatory check for the Content
		 */ 
		//Commented By Sreekumar S based on the requirement change(INTBUG - ULD158)
/*		if (content != null) {
			for (int i = 0; i < content.length; i++) {
				if(!"NOOP".equals(opFlagFlight[i])){
				if (content[i] == null || content[i].trim().length() == 0) {
					log.log(Log.FINE, "CHECK FOR VALIDATION  FOR THE >>>>"
							+ content[i]);
					if (!("ulddiscrepancy".equals(form.getDiscrepancyStatus()))) {
						log.log(Log.FINE, "CHECK FOR VALIDATION THE >>>>  "
								+ content[i]);
						error = new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.content");
						errors.add(error);
						break;
					}

				}
			}
		}
		}*/
		/*
		 * If updatecurrentstation is true ,Check the mandatory condition for
		 * the CurrentStation
		 */
		if (form.getUpdateCurrentStation() && form.getCurrentStation().trim().length() == 0) {
			log.entering("CHECK FOR VALIDATING THE UPDATE CURRENT STATION",
					"CHECK FOR VALIDATING THE UPDATE CURRENT STATIONN");
				if (pointOfUnLading != null && pointOfUnLading.length > 0) {
					for (int i = 0; i < pointOfUnLading.length; i++) {
						if(!"NOOP".equals(opFlagFlight[i])){
						if (pointOfUnLading[i] != null || pointOfUnLading[i].trim().length() != 0) {
							form.setCurrentStation(pointOfUnLading[i]);
						}
					}
				}
				}
				if(form.getCurrentStation().trim().length() == 0){
				error = new ErrorVO(
						"uld.defaults.recordULDMovement.msg.err.currentstation");
				errors.add(error);
				}
		}
		/*
		 * Check wether atleast OneRow of ULD is Present
		 *
		 */
		if (form.getUldNumber() == null) {
			log.entering("ENTER THE ULDNUMBER", "ENTER THE ULDNUMBER");
			error = new ErrorVO(
					"uld.defaults.recordULDMovement.msg.err.uldnumber");
			errors.add(error);
		}

		/*
		 * ULDNumber is mandatory
		 *
		 */
		if (form.getUldNumber() != null) {
			String[] uldNumber = form.getUldNumber();
			String[] opFlag = form.getHiddenOpFlagForULD();
			log.entering("CHECK FOR VALIDATION", "FOR THE ULDNUMBER");
			log.entering("CHECK FOR VALIDATION", "FOR THE ULDNUMBER");
			log.log(Log.FINE, "\n uldNumber.length 1a------->>",
					uldNumber.length);
			for (int i = 0; i < uldNumber.length; i++) {
				log.log(Log.FINE, "\n opFlag[i] 1a------->>", opFlag, i);
				log
						.log(Log.FINE, "\n uldNumbers[i] 1a------->>",
								uldNumbers, i);
				if(!"NOOP".equals(opFlag[i])){
					if (uldNumbers[i] == null || uldNumbers[i].trim().length() == 0) {
						error = new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.uldnumbermandatory");
						errors.add(error);
						break;
					}
				}
			}
		}
		/*
		 * Enter one Row of FlightDetails
		 *
		 */
		if (form.getPointOfLading() == null) {
			log.entering("CHECK FOR VALIDATION", "FOR THE FLIGHT ROWS");
			error = new ErrorVO("uld.defaults.recordULDMovement.msg.err.flight");
			errors.add(error);
		}
		/*
		 * Duplicate check for the ULDNUmber
		 *
		 */
		if (form.getUldNumber() != null) {
			String[] uldNumber = form.getUldNumber();
			String[] opFlag = form.getHiddenOpFlagForULD();
			log
					.entering("CHECK FOR VALIDATION",
							"FOR THE  DUPLICATE ULDNUMBER");
			collectionForValidation = new ArrayList<String>();
			Collection<String> coll = new ArrayList<String>();
			log.log(Log.FINE, "\n uldNumber.length 1b------->>",
					uldNumber.length);
			for (int i = 0; i < uldNumber.length; i++) {
				log.log(Log.FINE, "\n opFlag[i] 1b------->>", opFlag, i);
				log
						.log(Log.FINE, "\n uldNumbers[i] 1b------->>",
								uldNumbers, i);
				if(!"NOOP".equals(opFlag[i])){
					if (uldNumbers[i] != null && uldNumbers[i].trim().length() > 0
							&& !(collectionForValidation.contains(uldNumbers[i]))) {
						collectionForValidation.add(uldNumbers[i]);
					} else if (uldNumbers[i].trim().length() > 0
							&& !coll.contains(uldNumbers[i])) {
						error = new ErrorVO(
								"uld.defaults.recordULDMovement.msg.err.uldnumberduplicate",
								new Object[] { uldNumbers[i] });
						coll.add(uldNumbers[i]);
						errors.add(error);
					}
				}


			}
		}
		/*
		 * check wether the FlightDate is valid
		 */
		if (flightDate != null) {
			for (int i = 0; i < flightDate.length; i++) {
				if(!"NOOP".equals(opFlagFlight[i])){
				if (flightDate[i] != null
						&& !(flightDate[i].trim().length() == 0)) {
					if (DateUtilities.isValidDate(flightDate[i], "dd-MMM-yyyy")) {
						log.entering("INSIDE THE VALIDATION FOR FLIGHTDATE",
								"INSIDE THE VALIDATION FOR FLIGHTDATE");
					} else {
						log.entering("INSIDE THE VALIDATION FOR FLIGHTDATE",
								"ERRORS CREATED");
						errors
								.add(new ErrorVO(
										"uld.defaults.recordULDMovement.msg.err.invaliddate",
										new Object[] { flightDate[i] }));
					}
				}
			}
		}
		}
		/*
		 * Remark is mandatory
		 */
		/*if(form.getRemarks() == null ||
				form.getRemarks().trim().length() == 0) {
			error = new ErrorVO(
				"uld.defaults.recordULDMovement.msg.err.remarksmandatory");
			errors.add(error);
		}*/
		return errors;

	}

	/**
	 *
	 * @param recordULDMovementForm
	 */
	private void clearRecordULDMovementForm(
			RecordULDMovementForm recordULDMovementForm) {
		recordULDMovementForm.setUpdateCurrentStation(false);
		recordULDMovementForm.setCurrentStation("");
		recordULDMovementForm.setRemarks("");
	}

	 private Collection<ErrorVO> validateULDFormat(String companyCode,String uldNumber){
		 Collection<ErrorVO> invalidUldFormat = null;
		 try {
				log.entering("CALLING VALIDATE ULDFORMAT",
						"CALLINGVALIDATEULDFORMAT");
				new ULDDefaultsDelegate().validateULDFormat(companyCode, uldNumber);
			} catch (BusinessDelegateException businessDelegateException) {
				log.entering("ERRORS CREATED FOR",
						"CALLINGVALIDATEULDFORMAT");
				invalidUldFormat = handleDelegateException(businessDelegateException);
			}

		return invalidUldFormat;
		}

		/*
		 * Added by ayswarya
		 */
		private Collection<LockVO> prepareLocksForSave(
				String[]  uldNumbers) {
			log.entering("prepareLocksForSave","uldNumbers");
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			Collection<LockVO> locks = new ArrayList<LockVO>();
			
			if (uldNumbers != null && uldNumbers.length > 0) {
				log.log(Log.FINE, "\n uldNumbers.length 1b------->>",
						uldNumbers.length);
				for (int i = 0; i < uldNumbers.length-1; i++) {
					ULDLockVO lock = new ULDLockVO();
					log.log(Log.FINE, "\n lock------->>enter", lock);
					lock.setAction(LockConstants.ACTION_RECORDULDMOVEMENT);
					lock.setClientType(ClientType.WEB);
					lock.setCompanyCode(logonAttributes.getCompanyCode());
					lock.setScreenId(SCREEN_ID_RECORDULDMOVEMENT);
					log.log(Log.FINE, "\n uldNumbers------->>", uldNumbers[i]);
					lock.setDescription(uldNumbers[i].toUpperCase());
					lock.setRemarks(uldNumbers[i].toUpperCase());
					lock.setStationCode(logonAttributes.getStationCode());
					lock.setUldNumber(uldNumbers[i].toUpperCase());
					log.log(Log.FINE, "\n lock------->>exit", lock);
					locks.add(lock);
				}
			}
			log.exiting("prepareLocksForSave","locks");
			log.log(Log.FINE, "\n locks------->>returng\n", locks);
			return locks;
	}

}
