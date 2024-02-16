/*
 * ListULDErrorLogCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */
public class ListULDErrorLogCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("LIST ULD Error Logs");

	private static final String MODULE = "uld.defaults";

	private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

	private static final String DUPLICATE_SUCCESS = "duplicate_success";

	private static final String MODULE_NAME_FLIGHT = "flight.operation";
	private static final String OUT="OUT";
	/**
	 * Screen Id of ULD Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ulderrorlog";

	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String CONTENT_ONETIME="uld.defaults.contentcodes";

    /**
     * execute method
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
		String  compCode = logonAttributes.getCompanyCode();

		ULDErrorLogForm uldErrorLogForm =
			(ULDErrorLogForm) invocationContext.screenModel;
		ULDErrorLogSession uldErrorLogSession =
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID);

		FlightFilterMessageVO flightFilterMessageVO=new FlightFilterMessageVO();
		Collection<ErrorVO> errors = null;

//		for finding whether GHA/airline

		String isGHA="";
		if(logonAttributes.isAirlineUser()){
			isGHA="N";
		}else
		{
			isGHA="Y";
		}
		AreaDelegate areaDelegate =new AreaDelegate();
		AirportVO airportVO=null;
		Collection<ErrorVO> excep = new ArrayList<ErrorVO>();
		try {
			airportVO =	areaDelegate.findAirportDetails(compCode,logonAttributes.getAirportCode());
		} catch (BusinessDelegateException e) {
		e.getMessage();
		excep = handleDelegateException(e);
		}
		String isAirlineGHA="";
		if(airportVO!=null)
		{
			if(airportVO.getUsedAirportVO() != null && airportVO.getUsedAirportVO().isUldGHAFlag()){
				isAirlineGHA="Y";
			}else{
				isAirlineGHA="N";
			}
		}
		log.log(Log.FINE, "isGHA----------------->", isGHA);
		log.log(Log.FINE, "isAirlineGHA-------------------->", isAirlineGHA);
		uldErrorLogForm.setGha(isGHA);
		uldErrorLogForm.setAirlinegha(isAirlineGHA);

//		for finding whether GHA/airline

		/* Added by Preet on 26th Nov for displaying Content */
		 Collection<String> onetimeColl= new ArrayList<String>();

	 	    Map<String,Collection<OneTimeVO>> oneTimeValues=null;
	 	    onetimeColl.add(CONTENT_ONETIME);
	        Collection<ErrorVO> err = new ArrayList<ErrorVO>();
			   try{
	 	     oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),onetimeColl);
			   }catch(BusinessDelegateException ex){
				 ex.getMessage();
				 err = handleDelegateException(ex);
			   }
	 	  Collection<OneTimeVO> contentOneTimeValues = oneTimeValues.get(CONTENT_ONETIME);
	 	  log.log(Log.FINE, "content reurned is ====", contentOneTimeValues);
		uldErrorLogSession.setContent((ArrayList<OneTimeVO>)contentOneTimeValues);
	 	 /* Added by Preet on 26th Nov for displaying Content ends*/
		//Modifed by A-7359 for ICRD-225848
		if((("fromucmerrorlog").equals(uldErrorLogSession.getPageURL())
				|| ("frommaintainuld").equals(uldErrorLogSession.getPageURL())
				|| ("fromSendUCMInOut").equals(uldErrorLogSession.getPageURL())
				|| ("fromloanborrowuld").equals(uldErrorLogSession.getPageURL())
				|| ("frommaintaindmgrep").equals(uldErrorLogSession.getPageURL())
				|| ("fromreturnuld").equals(uldErrorLogSession.getPageURL())
				|| ("fromrecorduld").equals(uldErrorLogSession.getPageURL())
				|| ("fromreconcile").equals(uldErrorLogSession.getPageURL())) &&
				uldErrorLogSession.getFlightFilterMessageVOSession()!=null){
			flightFilterMessageVO=uldErrorLogSession.getFlightFilterMessageVOSession();
			log.log(Log.FINE, "flightFilterMessageVO ---> ",
					flightFilterMessageVO);
			String toDisplayPage = uldErrorLogForm.getDisplayPage();
			int displayPage = Integer.parseInt(toDisplayPage);
			flightFilterMessageVO.setPageNumber(displayPage);
			uldErrorLogForm.setUlderrorlogAirport(flightFilterMessageVO.getAirportCode());
			uldErrorLogForm.setCarrierCode(flightFilterMessageVO.getCarrierCode());
			String rDate="";
            rDate=TimeConvertor.toStringFormat(flightFilterMessageVO.getFlightDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
			uldErrorLogForm.setFlightDate(rDate);
			uldErrorLogForm.setFlightNo(flightFilterMessageVO.getFlightNumber());
			uldErrorLogForm.setMessageType(flightFilterMessageVO.getMessageType());
			//added by a-3278 as apart of bug 46741 on 20May09
			if(flightFilterMessageVO.getUldNumbers()!=null && flightFilterMessageVO.getUldNumbers().size()>0 &&
					uldErrorLogForm.getUlderrorlogULDNo()==null && uldErrorLogForm.getUlderrorlogULDNo().trim().length()== 0){
				ArrayList<String> uldnum=(ArrayList<String>)flightFilterMessageVO.getUldNumbers();
				uldErrorLogForm.setUlderrorlogULDNo(uldnum.get(0));
			}
			if(uldErrorLogForm.getUlderrorlogULDNo()!=null && uldErrorLogForm.getUlderrorlogULDNo().trim().length()>0){
				Collection<String> uldnum=new ArrayList<String>();
				uldnum.add(uldErrorLogForm.getUlderrorlogULDNo().toUpperCase());
				log.log(Log.FINE, "uldnum --is--> ", uldnum.size());
				flightFilterMessageVO.setUldNumbers(uldnum);
			}else{
				flightFilterMessageVO.setUldNumbers(null);
			}
			//a-3278 ends
			uldErrorLogSession.setFlightFilterMessageVOSession(null);

		}else{
		/**
		 * Validate for client errors
		 */

		errors = validateForm
					(uldErrorLogForm,logonAttributes.getCompanyCode());

		if(errors!=null && errors.size() > 0 ) {
			//uldErrorLogForm.setScreenStatusFlag("SCREENLOAD");
			uldErrorLogForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			uldErrorLogForm.setScreenStatusValue("SCREENLOAD");
			invocationContext.addAllError(errors);
			uldErrorLogForm.setDupStat("");
			invocationContext.target = LIST_FAILURE;
			return;
		}

		uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(null);


		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);

		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVO = null;
		log.log(Log.FINE, "flight number--------->", uldErrorLogForm.getFlightNo());
		log.log(Log.FINE, "flight date--------->", uldErrorLogForm.getFlightDate());
		// validate carriercode
		if (uldErrorLogForm.getCarrierCode() != null
				&& uldErrorLogForm.getCarrierCode().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), uldErrorLogForm.getCarrierCode()
								.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				//uldErrorLogForm.setScreenStatusFlag("SCREENLOAD");
				uldErrorLogForm.setScreenStatusValue("SCREENLOAD");
				errors = new ArrayList<ErrorVO>();
				Object[] obj = { uldErrorLogForm.getCarrierCode().toUpperCase() };
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.ulderrormsg.msg.err.invalidcarrier", obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				uldErrorLogForm.setDupStat("");
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
		flightFilterVO = getFlightFilterVO(logonAttributes,
				uldErrorLogForm, airlineValidationVO);
		flightValidationVOs = getFlightDetails(flightFilterVO);
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.ulderrormsg.msg.err.noflightdetails");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			uldErrorLogForm.setDupStat("");
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
			log.log(Log.FINE, "flight number--------->", uldErrorLogForm.getUlderrorlogAirport());
			duplicateFlightSession
					.setFlightValidationVOs((ArrayList<FlightValidationVO>) flightValidationVOs);
			duplicateFlightSession.setParentScreenId(SCREENID);
			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
			uldErrorLogForm.setDupStat(FLAG_YES);
			invocationContext.target = DUPLICATE_SUCCESS;
			return;
		}
		//newly added starts
		ArrayList<String> stationsFromRoute = new ArrayList<String>();
		if(OUT.equals(uldErrorLogForm.getMessageType())){
		String[] stations = flightValidationVO.getFlightRoute().split(
				uldErrorLogForm.getUlderrorlogAirport().toUpperCase());

		if (stations.length > 1) {
			String[] selectedDestns = stations[1].split("-");

			for (int i = 0; i < selectedDestns.length; i++) {

				if (selectedDestns[i] != null
						&& selectedDestns[i].trim().length() > 0) {
					stationsFromRoute.add(selectedDestns[i]);
				}

			}

			log.log(Log.FINE,
					"\n\n\nStations to be set in combo------------->",
					stationsFromRoute);

		}

		}else{
			stationsFromRoute.add(uldErrorLogForm.getUlderrorlogAirport().toUpperCase());
		}

		uldErrorLogSession.setPouValues(stationsFromRoute);
		//newly added ends


		flightFilterMessageVO.setCompanyCode(compCode);
		flightFilterMessageVO.setAirportCode(uldErrorLogForm.getUlderrorlogAirport().toUpperCase());

		if(uldErrorLogForm.getUlderrorlogULDNo()!=null && uldErrorLogForm.getUlderrorlogULDNo().trim().length()>0){
			Collection<String> uldnum=new ArrayList<String>();
			uldnum.add(uldErrorLogForm.getUlderrorlogULDNo().toUpperCase());
			log.log(Log.FINE, "uldnum ---> ", uldnum.size());
			flightFilterMessageVO.setUldNumbers(uldnum);
		}else
		{
			flightFilterMessageVO.setUldNumbers(null);
		}

		String toDisplayPage = uldErrorLogForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);
		flightFilterMessageVO.setPageNumber(displayPage);
		flightFilterMessageVO.setMessageType(uldErrorLogForm.getMessageType());
		flightFilterMessageVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
		flightFilterMessageVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
		flightFilterMessageVO.setFlightNumber(flightValidationVO.getFlightNumber());
		flightFilterMessageVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
		flightFilterMessageVO.setCarrierCode(uldErrorLogForm.getCarrierCode().toUpperCase());
		flightFilterMessageVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		}
		uldErrorLogSession.setFlightFilterMessageVOSession(flightFilterMessageVO);
		log.log(Log.FINE, "flightFilterMessageVO ---> ", flightFilterMessageVO);
		Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			uldFlightMessageReconcileDetailsVOs= new ULDDefaultsDelegate().listUldErrors(flightFilterMessageVO);
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "uldFlightMessageReconcileDetailsVOs ---> ",
				uldFlightMessageReconcileDetailsVOs);
		ArrayList<String> sequenceNumbers=new ArrayList<String>();
		if(uldFlightMessageReconcileDetailsVOs == null || uldFlightMessageReconcileDetailsVOs.getActualPageSize()==0) {
			//uldErrorLogSession.removeUcmNumberValues();
			FlightFilterMessageVO messageFilterVO=new FlightFilterMessageVO();
			messageFilterVO.setCompanyCode(compCode);
			if(flightFilterMessageVO!=null){
			messageFilterVO.setFlightCarrierId(flightFilterMessageVO.getFlightCarrierId());

			messageFilterVO.setFlightNumber(flightFilterMessageVO.getFlightNumber());


			messageFilterVO.setFlightSequenceNumber((int)flightFilterMessageVO.getFlightSequenceNumber());

			messageFilterVO.setAirportCode(flightFilterMessageVO.getAirportCode());

			messageFilterVO.setMessageType(flightFilterMessageVO.getMessageType());
			}
			try{
				sequenceNumbers=(ArrayList<String>)new ULDDefaultsDelegate().findUCMNoLOV(messageFilterVO);
			}
			catch(BusinessDelegateException businessDelegateException){
				errors=handleDelegateException(businessDelegateException);
			}
			if(sequenceNumbers!=null && sequenceNumbers.size()>0){
			    //uldErrorLogForm.setScreenStatusFlag("SCREENLOAD");
				uldErrorLogForm.setScreenStatusValue("SCREENLOAD");
				uldErrorLogForm.setScreenFlag("list");
			    uldErrorLogForm.setDupStat("");
			    uldErrorLogSession.setUcmNumberValues(sequenceNumbers);
			    invocationContext.addError(new ErrorVO(
	                "uld.defaults.messaging.ulderrorlog.noresults",null));

			}else{
				uldErrorLogForm.setDupStat("");
				uldErrorLogForm.setScreenFlag("screenload");
				 invocationContext.addError(new ErrorVO(
			                "uld.defaults.messaging.ulderrorlog.noucm",null));
			}
			    if(errors!=null){
				invocationContext.addAllError(errors);
			    }
			    uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(null);
				invocationContext.target = LIST_FAILURE;
				return;
		}else{
			uldErrorLogForm.setScreenFlag("list");
			for(ULDFlightMessageReconcileDetailsVO vo:uldFlightMessageReconcileDetailsVOs){
				if(!(sequenceNumbers.contains(vo.getSequenceNumber()))){
				sequenceNumbers.add(vo.getSequenceNumber());
				}
			}
		}
		uldErrorLogSession.setUcmNumberValues(sequenceNumbers);
		uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileDetailsVOs);
		//uldErrorLogForm.setScreenStatusFlag("LISTSUCCESS");
		uldErrorLogForm.setScreenStatusValue("LISTSUCCESS");

		invocationContext.target = LIST_SUCCESS;

    }
 /**
  *
  * @param uldErrorLogForm
  * @param companyCode
  * @return
  */
	private Collection<ErrorVO> validateForm
	(ULDErrorLogForm uldErrorLogForm, String companyCode){
		log.entering("ListULDErrorLogCommand", "validateForm");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(uldErrorLogForm.getUlderrorlogAirport()== null ||
				uldErrorLogForm.getUlderrorlogAirport().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.messaging.ulderrorlog.airportmandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {
			if(validateAirportCodes(uldErrorLogForm.getUlderrorlogAirport().toUpperCase(),companyCode)!=null){
				errors.add(new ErrorVO("uld.defaults.messaging.ulderrorlog.airportcodeinvalid",null));
			}

		}

		/*if(uldErrorLogForm.getUlderrorlogULDNo()== null ||
				uldErrorLogForm.getUlderrorlogULDNo().trim().length() == 0){
			 error = new ErrorVO(
					 "uld.defaults.messaging.ulderrorlog.uldnomandatory");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else {*/
			if(uldErrorLogForm.getUlderrorlogULDNo()!= null &&
					uldErrorLogForm.getUlderrorlogULDNo().trim().length() >0)
			{
				try {

					new ULDDefaultsDelegate().validateULDFormat(companyCode,
							uldErrorLogForm.getUlderrorlogULDNo().toUpperCase());

				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
				}
			}

			if(uldErrorLogForm.getCarrierCode()== null ||
					uldErrorLogForm.getCarrierCode().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.messaging.ulderrorlog.flightcarriermandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(uldErrorLogForm.getFlightDate()== null ||
					uldErrorLogForm.getFlightDate().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.messaging.ulderrorlog.flightdatemandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(uldErrorLogForm.getFlightNo()== null ||
					uldErrorLogForm.getFlightNo().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.messaging.ulderrorlog.flightnummandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(uldErrorLogForm.getMessageType()== null ||
					uldErrorLogForm.getMessageType().trim().length() == 0){
				 error = new ErrorVO(
						 "uld.defaults.messaging.ulderrorlog.msgtypemandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		log.exiting("ListULDErrorLogCommand", "validateForm");
		return errors;
	}

	/**
	 *
	 * @param station
	 * @param companyCode
	 * @return
	 */
	public Collection<ErrorVO> validateAirportCodes(
		String station,
		String companyCode){
	log.entering("ListCommand", "validateAirportCodes");
	Collection<ErrorVO> errors = null;
	try {
		AreaDelegate delegate = new AreaDelegate();
		delegate.validateAirportCode(
				companyCode,station);

	} catch (BusinessDelegateException e) {
		e.getMessage();
		errors = handleDelegateException(e);
	}
	log.exiting("ListCommand", "validateAirportCodes");
	return errors;
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
			ULDErrorLogForm form, AirlineValidationVO airlineValidationVO) {
		log.entering("ListCommand", "getFlightFilterVO");
		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		//flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setStation(form.getUlderrorlogAirport());
		flightFilterVO.setFlightNumber(form.getFlightNo().toUpperCase());
		flightFilterVO.setCarrierCode(form.getCarrierCode().toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO
				.getAirlineIdentifier());
		log
				.log(Log.FINE, "getMsgType----------------->", form.getMessageType());
		if("OUT".equals(form.getMessageType())){
			flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		}else{
			flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		}

		if (form.getFlightDate() != null
				&& form.getFlightDate().trim().length() > 0) {
			LocalDate fltDate = new LocalDate(form.getUlderrorlogAirport(),Location.ARP, false);
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
}
