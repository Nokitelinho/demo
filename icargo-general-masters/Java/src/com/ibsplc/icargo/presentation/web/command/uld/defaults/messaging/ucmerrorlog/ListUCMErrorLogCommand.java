/*
 * ListUCMErrorLogCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is used to list the UCM error logs
 * 
 * @author A-1862
 */

public class ListUCMErrorLogCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("LIST UCM Error Logs");

	private static final String MODULE_NAME_FLIGHT = "flight.operation";

	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

	private static final String DUPLICATE_SUCCESS = "duplicate_success";

	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.ucmerrorlog";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";
	//Added as part of ICRD-323721
	private static final String MESSAGE_STATUS = "uld.defaults.uldmessagesendflag";
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();

		UCMErrorLogForm ucmErrorLogForm = (UCMErrorLogForm) invocationContext.screenModel;
		UCMErrorLogSession ucmErrorLogSession = (UCMErrorLogSession) getScreenSession(
				MODULE, SCREENID);
		ucmErrorLogSession.setULDFlightMessageReconcileVOs(null);
		FlightFilterMessageVO flightFilterMessageVO = new FlightFilterMessageVO();
		flightFilterMessageVO.setFlightNumber(ucmErrorLogForm.getFlightNo());
		Collection<ErrorVO> errors = null;
		HashMap indexMap = null;
		HashMap finalMap = null;
		//added by A-5203
		if ("Y".equals(ucmErrorLogForm.getListflag())) {
			ucmErrorLogSession.setIndexMap(null);
		}	
		/* Added as part of ICRD-323721
		 * Message status  not populated as part of Navigation from uld023
		 */
		if(ucmErrorLogSession.getMessageStatus()==null || ucmErrorLogSession.getMessageStatus().size()==0) {
			Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes
					.getCompanyCode());
			Collection<OneTimeVO> statusValues = oneTimeCollection
					.get(MESSAGE_STATUS);
			ucmErrorLogSession
					.setMessageStatus((ArrayList<OneTimeVO>) statusValues);
		}
		ucmErrorLogForm.setListStatus("");
		log.log(Log.FINE, "ucmErrorLogSession.getPageURL() ---> ",
				ucmErrorLogSession.getPageURL());
		log.log(Log.FINE,
				"ucmErrorLogSession.getFlightFilterMessageVOSession() ---> ",
				ucmErrorLogSession.getFlightFilterMessageVOSession());
		log.log(Log.FINE,
				"ucmErrorLogForm.getFlightValidationStatus-------------->",
				ucmErrorLogForm.getFlightValidationStatus());
		//Modified by A-7359 for ICRD-192413
		if ((("fromulderrorlog").equals(ucmErrorLogSession.getPageURL()) && ucmErrorLogSession
				.getFlightFilterMessageVOSession() != null||("fromSendUCMInOut").equals(ucmErrorLogSession.getPageURL()) && ucmErrorLogSession
				.getFlightFilterMessageVOSession() != null)
				|| ("Y").equals(ucmErrorLogForm.getFlightValidationStatus())) {

			ucmErrorLogForm.setFlightValidationStatus("");
			ucmErrorLogForm.setDuplicateStatus("");
			if (indexMap == null) {
				log.log(Log.FINE, "INDEX MAP IS NULL");
				indexMap = new HashMap();
				indexMap.put("1", "1");
			}

			flightFilterMessageVO = ucmErrorLogSession
					.getFlightFilterMessageVOSession();
			flightFilterMessageVO.setAbsoluteIndex(1);
			String toDisplayPage = ucmErrorLogForm.getDisplayPage();
			int displayPage = Integer.parseInt(toDisplayPage);
			flightFilterMessageVO.setPageNumber(displayPage);
log.log(Log.INFO, "flight filter vo after returning from uld screen",
		flightFilterMessageVO);
			ucmErrorLogForm.setUcmerrorlogAirport(flightFilterMessageVO
					.getAirportCode());
			ucmErrorLogForm.setCarrierCode(flightFilterMessageVO
					.getCarrierCode());
			ucmErrorLogForm
					.setFlightNo(flightFilterMessageVO.getFlightNumber());
			String rDate = "";
			if(flightFilterMessageVO.getFlightDate()!=null){
			rDate = TimeConvertor.toStringFormat(flightFilterMessageVO
					.getFlightDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			}
			ucmErrorLogForm.setFlightDate(rDate);
			ucmErrorLogForm.setMsgType(flightFilterMessageVO.getMessageType());
			if(flightFilterMessageVO.getMessageStatus()!=null) {
				ucmErrorLogForm.setMsgStatus(flightFilterMessageVO.getMessageStatus());
			} else {
				ucmErrorLogForm.setMsgStatus("ALL");
			}
			//Added by A-7359 for ICRD-225848
			if(!(("fromSendUCMInOut").equals(ucmErrorLogSession.getPageURL()) && ucmErrorLogSession
				.getFlightFilterMessageVOSession() != null)){
			ucmErrorLogSession.setFlightFilterMessageVOSession(null);
			ucmErrorLogSession.setPageURL(null);
				}

		} else {

			/**
			 * Validate for client errors
			 */

			errors = validateForm(ucmErrorLogForm, logonAttributes
					.getCompanyCode());

			if (errors != null && errors.size() > 0) {
				//ucmErrorLogForm.setScreenStatusFlag("SCREENLOAD");
				ucmErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				ucmErrorLogForm.setScreenStatusValue("SCREENLOAD");
				ucmErrorLogForm.setDuplicateStatus("");
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}

			DuplicateFlightSession duplicateFlightSession = getScreenSession(
					MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);

			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			log.log(Log.FINE, "flight number--------->", ucmErrorLogForm.getFlightNo());
			log.log(Log.FINE, "flight date--------->", ucmErrorLogForm.getFlightDate());
			// validate carriercode
			if (ucmErrorLogForm.getCarrierCode() != null
					&& ucmErrorLogForm.getCarrierCode().trim().length() > 0) {
				try {
					airlineValidationVO = airlineDelegate.validateAlphaCode(
							logonAttributes.getCompanyCode(), ucmErrorLogForm
									.getCarrierCode().toUpperCase());

				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (errors != null && errors.size() > 0) {
					//ucmErrorLogForm.setScreenStatusFlag("SCREENLOAD");
					ucmErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					ucmErrorLogForm.setScreenStatusValue("SCREENLOAD");
					errors = new ArrayList<ErrorVO>();
					Object[] obj = { ucmErrorLogForm.getCarrierCode()
							.toUpperCase() };
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.ucmerrormsg.msg.err.invalidcarrier",
							obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					ucmErrorLogForm.setDuplicateStatus("");
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}
			Collection<FlightValidationVO> flightValidationVOs = null;
			/*
			 * commented as no validation is required(discussed with asharaf)
			 */
			// FlightValidationVO flightValidationVO = new FlightValidationVO();
			/*
			 * get flight validation vos
			 */
			/*
			 * FlightFilterVO flightFilterVO = null; flightFilterVO =
			 * (logonAttributes, ucmErrorLogForm, airlineValidationVO);
			 * flightValidationVOs = getFlightDetails(flightFilterVO); if
			 * (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			 * log.log(Log.FINE, "flightValidationVOs is NULL"); ErrorVO errorVO =
			 * new ErrorVO( "uld.defaults.ucmerrormsg.msg.err.noflightdetails");
			 * errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 * errors.add(errorVO); ucmErrorLogForm.setDuplicateStatus("");
			 * invocationContext.addAllError(errors); invocationContext.target =
			 * LIST_FAILURE; return; } else if (flightValidationVOs != null &&
			 * flightValidationVOs.size() == 1) { log.log(Log.FINE,
			 * "\n\n\n********* No Duplicate flights**************");
			 * flightValidationVO = ((ArrayList<FlightValidationVO>)
			 * flightValidationVOs) .get(0);
			 *  } else { log.log(Log.FINE, "\n\n\n*********Duplicate
			 * flights**************"); duplicateFlightSession
			 * .setFlightValidationVOs((ArrayList<FlightValidationVO>)
			 * flightValidationVOs);
			 * duplicateFlightSession.setParentScreenId(SCREENID);
			 * duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			 * ucmErrorLogForm.setDuplicateStatus(FLAG_YES);
			 * invocationContext.target = DUPLICATE_SUCCESS; return; }
			 */

			indexMap = getIndexMap(ucmErrorLogSession.getIndexMap(), invocationContext); //added by A-5203
			
			if(indexMap == null || indexMap.size()<=0) {
				log.log(Log.FINE, "INDEX MAP IS NULL");
				indexMap = new HashMap();
				indexMap.put("1", "1");
			}
			int nAbsoluteIndex = 0;
			String toDisplayPage = ucmErrorLogForm.getDisplayPage();
			int displayPage = Integer.parseInt(toDisplayPage);
			String strAbsoluteIndex = (String)indexMap.get(ucmErrorLogForm.getDisplayPage());
			if (strAbsoluteIndex != null) {
				nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			}
			// log.log(Log.FINE, "flightValidationVO ---> " +
			// flightValidationVO);

			flightFilterMessageVO.setCompanyCode(compCode);
			flightFilterMessageVO.setAirportCode(ucmErrorLogForm
					.getUcmerrorlogAirport().toUpperCase());
			flightFilterMessageVO.setMessageType(ucmErrorLogForm.getMsgType());
			flightFilterMessageVO
					.setFlightNumber(ucmErrorLogForm.getFlightNo());

			flightFilterMessageVO.setPageNumber(displayPage);
			flightFilterMessageVO.setAbsoluteIndex(nAbsoluteIndex);
			if (airlineValidationVO != null) {
				flightFilterMessageVO.setFlightCarrierId(airlineValidationVO
						.getAirlineIdentifier());
			}
			if(ucmErrorLogForm.getFlightDate() != null && 
			    			ucmErrorLogForm.getFlightDate().trim().length() > 0) {
			    		if(DateUtilities.isValidDate(ucmErrorLogForm.getFlightDate(),
			    											"dd-MMM-yyyy")) {
							LocalDate fltDate = new LocalDate(getApplicationSession().
													getLogonVO().getAirportCode(),Location.ARP,false);
							fltDate.setDate(ucmErrorLogForm.getFlightDate());
							flightFilterMessageVO.setFlightDate(fltDate);
						}   		
			    		
    		}
			

			// commentd as flight is not validated
			/*
			 * flightFilterMessageVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
			 * flightFilterMessageVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
			 * flightFilterMessageVO.setFlightNumber(flightValidationVO.getFlightNumber());
			 * flightFilterMessageVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
			 * flightFilterMessageVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			 * flightFilterMessageVO.setCarrierCode(ucmErrorLogForm.getCarrierCode().toUpperCase());
			 */

			// Added For CR Starts
			if ((!("ALL").equals(ucmErrorLogForm.getMsgStatus()))) {
				flightFilterMessageVO.setMessageStatus(ucmErrorLogForm
						.getMsgStatus());
			}
			if (ucmErrorLogForm.getFromDate() != null
					&& ucmErrorLogForm.getFromDate().trim().length() > 0) {
				//if-else condition added by a-3278 for bug 29939 on 11Dec08 starts
				LocalDate frmDate = null;
				if (ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase() != null
						&& ucmErrorLogForm.getUcmerrorlogAirport()
								.toUpperCase().trim().length() > 0) {
					frmDate = new LocalDate(ucmErrorLogForm
							.getUcmerrorlogAirport().toUpperCase(),
							Location.ARP, false);
				} else {
					frmDate = new LocalDate(getApplicationSession()
							.getLogonVO().getAirportCode().toUpperCase(),
							Location.ARP, false);
				}
				//if-else condition added for bug 29939 on 11Dec08 ends
				frmDate.setDate(ucmErrorLogForm.getFromDate().toUpperCase());
				flightFilterMessageVO.setFromDate(frmDate);
			}
			if (ucmErrorLogForm.getToDate() != null
					&& ucmErrorLogForm.getToDate().trim().length() > 0) {
				//if-else condition added for bug 29939 on 11Dec08 starts
				LocalDate toDate = null;
				if (ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase() != null
						&& ucmErrorLogForm.getUcmerrorlogAirport()
								.toUpperCase().trim().length() > 0) {
					toDate = new LocalDate(ucmErrorLogForm
							.getUcmerrorlogAirport().toUpperCase(),
							Location.ARP, false);
				} else {
					toDate = new LocalDate(getApplicationSession().getLogonVO()
							.getAirportCode().toUpperCase(), Location.ARP,
							false);
				}
				//if-else condition added for bug 29939 on 11Dec08 ends
				toDate.setDate(ucmErrorLogForm.getToDate().toUpperCase());
				flightFilterMessageVO.setToDate(toDate);
			}

			// Added For CR ends

		}
		flightFilterMessageVO.setCarrierCode(ucmErrorLogForm.getCarrierCode());
		ucmErrorLogSession
				.setFlightFilterMessageVOSession(flightFilterMessageVO);
		log.log(Log.FINE, "flightFilterMessageVO ---> ", flightFilterMessageVO);
		Page<ULDFlightMessageReconcileVO> uldFlightMessageReconcileVOs = null;
		ucmErrorLogForm.setMismatchStatus("");
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			uldFlightMessageReconcileVOs = new ULDDefaultsDelegate()
					.listUCMErrors(flightFilterMessageVO);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "uldFlightMessageReconcileVOs ---> ",
				uldFlightMessageReconcileVOs);
		if (uldFlightMessageReconcileVOs == null
				|| uldFlightMessageReconcileVOs.getActualPageSize() == 0) {
			ucmErrorLogForm.setDuplicateStatus("");
			//ucmErrorLogForm.setScreenStatusFlag("SCREENLOAD");
			ucmErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			ucmErrorLogForm.setScreenStatusValue("SCREENLOAD");
			invocationContext.addError(new ErrorVO(
					"uld.defaults.messaging.ucmerrorlog.noresults", null));
			ucmErrorLogSession.setULDFlightMessageReconcileVOs(null);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
			}
			invocationContext.target = LIST_FAILURE;
			return;
		}
		//added by  A-2883 for enabling/disabling buttons
		if(uldFlightMessageReconcileVOs!=null && uldFlightMessageReconcileVOs.size()>0){
			ucmErrorLogForm.setListStatus("N");
		}
		//A-2883 ends
		
		
		finalMap = indexMap;

		if (uldFlightMessageReconcileVOs != null) {
			finalMap = buildIndexMap(indexMap, uldFlightMessageReconcileVOs);
			//ucmErrorLogSession.setIndexMap(finalMap);
		}
		ucmErrorLogSession.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext)); //added by A-5203
		ucmErrorLogSession
				.setULDFlightMessageReconcileVOs(uldFlightMessageReconcileVOs);
		//ucmErrorLogForm.setScreenStatusFlag("LISTSUCCESS");
		ucmErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		ucmErrorLogForm.setScreenStatusValue("LISTSUCCESS");

		invocationContext.target = LIST_SUCCESS;

	}

	/**
	 * 
	 * @param ucmErrorLogForm
	 * @param companyCode
	 * @return
	 */

	private Collection<ErrorVO> validateForm(UCMErrorLogForm ucmErrorLogForm,
			String companyCode) {
		log.entering("ListUCMErorLogCommand", "validateForm");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (ucmErrorLogForm.getUcmerrorlogAirport() == null
				|| ucmErrorLogForm.getUcmerrorlogAirport().trim().length() == 0) {
			/*error = new ErrorVO(
					"uld.defaults.messaging.ucmerrorlog.airportmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);*/
		} else {
			if (validateAirportCodes(ucmErrorLogForm.getUcmerrorlogAirport()
					.toUpperCase(), companyCode) != null) {
				errors
						.add(new ErrorVO(
								"uld.defaults.messaging.ucmerrorlog.airportcodeinvalid",
								null));
			}

		}
		//added as part of bug 107159 by A-3767 on 24Feb11
		/*if (ucmErrorLogForm.getCarrierCode() == null
				|| ucmErrorLogForm.getCarrierCode().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.ucmerrorlog.flightdetailsmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		} else if (ucmErrorLogForm.getFlightNo() == null
				|| ucmErrorLogForm.getFlightNo().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.ucmerrorlog.flightdetailsmandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}*/ 
		//Fliter Validation made conditional for CR ICRD-233748
		if ((((ucmErrorLogForm.getCarrierCode() == null || ucmErrorLogForm.getCarrierCode().trim().length() == 0) 
				|| (ucmErrorLogForm.getFlightNo() == null || ucmErrorLogForm.getFlightNo().trim().length() == 0)
				||(ucmErrorLogForm.getFlightDate() == null || ucmErrorLogForm.getFlightDate().trim().length() == 0))
				&& ((ucmErrorLogForm.getFromDate() == null || ucmErrorLogForm.getFromDate().trim().length() == 0) 
						|| (ucmErrorLogForm.getToDate() == null || ucmErrorLogForm.getToDate().trim().length() == 0)))) { 
			ErrorVO errorVO = new ErrorVO("uld.defaults.messaging.ucmerrorlog.flightdetailsmandatory");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR); 
			errors.add(errorVO);
		}
		//bug 107159 ends
		/*
		 * if(ucmErrorLogForm.getCarrierCode()== null ||
		 * ucmErrorLogForm.getCarrierCode().trim().length() == 0){ error = new
		 * ErrorVO(
		 * "uld.defaults.messaging.ucmerrorlog.flightdetailsmandatory");
		 * error.setErrorDisplayType(ErrorDisplayType.ERROR); errors.add(error);
		 * }else if(ucmErrorLogForm.getFlightDate()== null ||
		 * ucmErrorLogForm.getFlightDate().trim().length() == 0){ error = new
		 * ErrorVO(
		 * "uld.defaults.messaging.ucmerrorlog.flightdetailsmandatory");
		 * error.setErrorDisplayType(ErrorDisplayType.ERROR); errors.add(error);
		 * }else if(ucmErrorLogForm.getFlightNo()== null ||
		 * ucmErrorLogForm.getFlightNo().trim().length() == 0){ error = new
		 * ErrorVO(
		 * "uld.defaults.messaging.ucmerrorlog.flightdetailsmandatory");
		 * error.setErrorDisplayType(ErrorDisplayType.ERROR); errors.add(error); }
		 */
		if (ucmErrorLogForm.getMsgType() == null
				|| ucmErrorLogForm.getMsgType().trim().length() == 0) {
			error = new ErrorVO(
					"uld.defaults.messaging.ucmerrorlog.msgtypemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		// Flight Date Should be greater than or equal to From Date
		// >>>>>>>>>>>>>>>>>>>>

		if (ucmErrorLogForm.getFromDate() != null
				&& ucmErrorLogForm.getFromDate().trim().length() > 0
				&& ucmErrorLogForm.getFlightDate() != null
				&& ucmErrorLogForm.getFlightDate().trim().length() > 0) {

			//if-else condition added for bug 29939 on 11Dec08 starts
			LocalDate fltDate = null;
			LocalDate frmDate = null;
			if (ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase() != null
					&& ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase()
							.trim().length() > 0) {
				fltDate = new LocalDate(ucmErrorLogForm.getUcmerrorlogAirport()
						.toUpperCase(), Location.ARP, false);
				frmDate = new LocalDate(ucmErrorLogForm.getUcmerrorlogAirport()
						.toUpperCase(), Location.ARP, false);
			} else {
				fltDate = new LocalDate(getApplicationSession().getLogonVO()
						.getAirportCode().toUpperCase(), Location.ARP, false);
				frmDate = new LocalDate(getApplicationSession().getLogonVO()
						.getAirportCode().toUpperCase(), Location.ARP, false);
			}
			//if-else condition added for bug 29939 on 11Dec08 ends
			fltDate.setDate(ucmErrorLogForm.getFlightDate().toUpperCase());			
			frmDate.setDate(ucmErrorLogForm.getFromDate().toUpperCase());

			if (frmDate.after(fltDate)) {
				error = new ErrorVO(
						"uld.defaults.messaging.ucmerrorlog.frmdatefltdateerror");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		}

		// Flight Date Should be less than or equal to to Date
		// >>>>>>>>>>>>>>>>>>>>

		if (ucmErrorLogForm.getToDate() != null
				&& ucmErrorLogForm.getToDate().trim().length() > 0
				&& ucmErrorLogForm.getFlightDate() != null
				&& ucmErrorLogForm.getFlightDate().trim().length() > 0) {

			//if-else condition added for bug 29939 on 11Dec08 starts
			LocalDate fltDate = null;
			LocalDate toDate = null;
			if (ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase() != null
					&& ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase()
							.trim().length() > 0) {
				fltDate = new LocalDate(ucmErrorLogForm.getUcmerrorlogAirport()
						.toUpperCase(), Location.ARP, false);
				toDate = new LocalDate(ucmErrorLogForm.getUcmerrorlogAirport()
						.toUpperCase(), Location.ARP, false);
			} else {
				fltDate = new LocalDate(getApplicationSession().getLogonVO()
						.getAirportCode().toUpperCase(), Location.ARP, false);
				toDate = new LocalDate(getApplicationSession().getLogonVO()
						.getAirportCode().toUpperCase(), Location.ARP, false);
			}
			//if-else condition added for bug 29939 on 11Dec08 ends
			
			fltDate.setDate(ucmErrorLogForm.getFlightDate().toUpperCase());			
			toDate.setDate(ucmErrorLogForm.getToDate().toUpperCase());

			if (fltDate.after(toDate)) {
				error = new ErrorVO(
						"uld.defaults.messaging.ucmerrorlog.todatefltdateerror");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		}

		// From Date Should be less than or equal to toDate>>>>>>>>>>>>>>>>

		if (ucmErrorLogForm.getFromDate() != null
				&& ucmErrorLogForm.getFromDate().trim().length() > 0
				&& ucmErrorLogForm.getToDate() != null
				&& ucmErrorLogForm.getToDate().trim().length() > 0) {

			//if-else condition added for bug 29939 on 11Dec08 starts
			LocalDate frmDate = null;
			LocalDate toDate = null;
			if (ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase() != null
					&& ucmErrorLogForm.getUcmerrorlogAirport().toUpperCase()
							.trim().length() > 0) {
				frmDate = new LocalDate(ucmErrorLogForm.getUcmerrorlogAirport()
						.toUpperCase(), Location.ARP, false);
				toDate = new LocalDate(ucmErrorLogForm.getUcmerrorlogAirport()
						.toUpperCase(), Location.ARP, false);
			} else {
				frmDate = new LocalDate(getApplicationSession().getLogonVO()
						.getAirportCode().toUpperCase(), Location.ARP, false);
				toDate = new LocalDate(getApplicationSession().getLogonVO()
						.getAirportCode().toUpperCase(), Location.ARP, false);
			}
			//if-else condition added for bug 29939 on 11Dec08 ends			
			
			frmDate.setDate(ucmErrorLogForm.getFromDate().toUpperCase());			
			toDate.setDate(ucmErrorLogForm.getToDate().toUpperCase());

			if (frmDate.after(toDate)) {
				error = new ErrorVO(
						"uld.defaults.messaging.ucmerrorlog.dateinvalid");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			//range should be up to 5 days CR ICRD-233748
			int period = DateUtilities.getDifferenceInDays(ucmErrorLogForm.getFromDate(),ucmErrorLogForm.getToDate(), "dd-MMM-yyyy");
			if (period > 5) { 
				error = new ErrorVO("uld.defaults.messaging.ucmerrorlog.daterangeexeeded"); 
				error.setErrorDisplayType(ErrorDisplayType.ERROR);    
				errors.add(error);
	        }
		}
		log.exiting("ListUCMErrorLogCommand", "validateForm");
		return errors;
	}

	/**
	 * 
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

	private HashMap buildIndexMap(HashMap existingMap, Page page) {

		HashMap finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}
		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
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
	private FlightFilterVO getFlightFilterVO(LogonAttributes logonAttributes,
			UCMErrorLogForm form, AirlineValidationVO airlineValidationVO) {
		log.entering("ListCommand", "getFlightFilterVO");
		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setStation(logonAttributes.getAirportCode());

		flightFilterVO.setFlightNumber(form.getFlightNo().toUpperCase());
		flightFilterVO.setCarrierCode(form.getCarrierCode().toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO
				.getAirlineIdentifier());
		log.log(Log.FINE, "getMsgType----------------->", form.getMsgType());
		if ("OUT".equals(form.getMsgType())) {
			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		} else {
			flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		}

		if (form.getFlightDate() != null
				&& form.getFlightDate().trim().length() > 0) {
			LocalDate fltDate = new LocalDate(form.getUcmerrorlogAirport()
					.toUpperCase(), Location.ARP, false);
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
			errors = handleDelegateException(exception);
		}
		return flightValidationVOs;
	}
	/**
	 * Added as part of ICRD-323721
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(MESSAGE_STATUS);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}
}
