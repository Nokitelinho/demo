/**
 * ReassignDispatchCommand.java Created on January 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.reassign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReassignDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ReassignDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ReassignDispatchCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String MODULE_NAME = "mail.operations";
	private static final String OUTBOUND = "O";
	private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.national.reassign";
	private static final String FLIGHT_CLOSED = "mailtracking.defaults.national.reassign.error.flightclosed";
	private static final String ASG_MAIL_BAG = "ASG_MAIL_BAG";
	private static final String DSN_ENQUIRY = "DSN_ENQUIRY";
	private static final String CAPTURE_MAIL_BAG = "CAPTURE_MAIL_BAG";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadReassignCommand", "execute");
		AssignMailBagSession assignMailBagSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		ReassignDispatchSession reassignSession  = getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);
		ReassignDispatchForm reassignDispatchForm = (ReassignDispatchForm)invocationContext.screenModel;
		String fromScreen =  reassignDispatchForm.getFromScreen();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
			new MailTrackingDefaultsDelegate();
		errors = validateForm(reassignDispatchForm);
		if (errors != null && errors.size() > 0) { 
			invocationContext.addAllError(errors);
			reassignDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}


		if(reassignDispatchForm.getPou().trim().length()>0){
			AirportValidationVO airportValidationVO = null;		
			airportValidationVO = validateAirport(logonAttributes.getCompanyCode(),reassignDispatchForm.getPou().toUpperCase());
			if(airportValidationVO == null){			
				errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.invalidstation",new Object[]{reassignDispatchForm.getPou().toUpperCase()}));			
			}

			if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_SUCCESS;
				return;
			}
		}


		Boolean statusFlag = true;
		String flightNum = (reassignDispatchForm.getFlightNo().toUpperCase());
		AirlineValidationVO airlineValidationVO = null;
		String flightCarrierCode = reassignDispatchForm.getFlightCarrierCode().trim().toUpperCase();      
		if (flightCarrierCode != null && flightCarrierCode.trim().length() >0) {        		
			try {
				log.log(Log.FINE, "flightCarrierCode------------> ",
						flightCarrierCode);
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),flightCarrierCode);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (airlineValidationVO == null ) {            			
				Object[] obj = {flightCarrierCode};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.national.reassign.error.invalidcarrier",obj));
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		}
		FlightFilterVO flightFilterVO = handleFlightFilterVO(
				reassignDispatchForm,logonAttributes,airlineValidationVO);
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
			invocationContext.addAllError(errors);			
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}


		FlightValidationVO flightValidationVO = null;
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {reassignDispatchForm.getFlightCarrierCode(),
					reassignDispatchForm.getFlightNo(),
					reassignDispatchForm.getFlightDate().toString().substring(0,11)};
			errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.noflightdetails",obj));
			if(errors != null && errors.size() >0){
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		} else if ( flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			flightValidationVO = flightValidationVOs.iterator().next();
			Collection<ErrorVO> flightErrors = validateFlightRoute(reassignDispatchForm,flightValidationVO,logonAttributes);
			if(flightErrors != null && flightErrors.size() >0){
				invocationContext.addAllError(flightErrors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			reassignSession.setFlightValidationVO(flightValidationVO);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
		}else if(flightValidationVOs.size() >1){
			//Added for duplicate flights needs to be checked
			log.log(Log.FINE, "flightValidationVOs has one VO");
			flightValidationVO = flightValidationVOs.iterator().next();
			reassignSession.setFlightValidationVO(flightValidationVO);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);

		}



		OperationalFlightVO operationalFlightVO =  new OperationalFlightVO();		
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setFlightStatus(flightValidationVO.getFlightStatus());
		operationalFlightVO.setAirportCode(logonAttributes.getAirportCode());
		operationalFlightVO.setPol(logonAttributes.getAirportCode());
		operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
		operationalFlightVO.setPou(flightValidationVO.getLegDestination());
		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		operationalFlightVO.setDirection(OUTBOUND);

		/**
		 * To check whether flight is already closed.
		 */
		boolean isFlightClosed = false;
		try {

			isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}		
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			reassignDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		if(isFlightClosed){		
			ErrorVO errorVO = new ErrorVO(FLIGHT_CLOSED);
			Object [] obj = {reassignDispatchForm.getFlightCarrierCode(),reassignDispatchForm.getFlightNo(),reassignDispatchForm.getFlightDate()};
			errorVO.setErrorData(obj);
			errors.add(errorVO);
			invocationContext.addAllError(errors);		
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;
		}

		ContainerVO containerVO = null;
		Collection<DespatchDetailsVO> selectedDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();	
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<DSNVO> dsnvos = reassignSession.getSelectedDSNVO();
		Collection<ContainerVO> newContainerVOs = null;	
		Collection<ErrorVO> reassignerrors = new ArrayList<ErrorVO>();


		if(ASG_MAIL_BAG.equals(fromScreen)){			

			selectedDespatchDetailsVOs = populateDespatchDetailsVOs(dsnvos, flightValidationVO ,assignMailBagSession );
			validateReassign(selectedDespatchDetailsVOs,reassignDispatchForm,reassignerrors);
			if(reassignerrors != null  && reassignerrors.size() > 0){
				log.entering("in reassign command errors-->", "errors"+reassignerrors.size());
				invocationContext.addAllError(reassignerrors);	
				reassignDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SCREENLOAD_FAILURE;

				return;
			}

			for(DespatchDetailsVO despatchDetailsVO : selectedDespatchDetailsVOs){
				despatchDetailsVO.setCarrierCode(reassignDispatchForm.getFlightCarrierCode());
				despatchDetailsVO.setAcceptedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
				//despatchDetailsVO.setAcceptedWeight(Double.valueOf(reassignDispatchForm.getWeight()));
				despatchDetailsVO.setAcceptedWeight(reassignDispatchForm.getWeightMeasure());
				despatchDetailsVO.setStatedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
				//despatchDetailsVO.setStatedWeight(Double.valueOf(reassignDispatchForm.getWeight()));
				despatchDetailsVO.setStatedWeight(reassignDispatchForm.getWeightMeasure());
				despatchDetailsVO.setRemarks(reassignDispatchForm.getRemarks());

			}


		}else if(DSN_ENQUIRY.equals(fromScreen)){
			selectedDespatchDetailsVOs = reassignSession.getDespatchDetailsVOs();
			validateReassign(selectedDespatchDetailsVOs,reassignDispatchForm,reassignerrors);
			if(reassignerrors != null  && reassignerrors.size() > 0){
				log.entering("in reassign command errors-->", "errors"+reassignerrors.size());
				invocationContext.addAllError(reassignerrors);	
				reassignDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SCREENLOAD_FAILURE;

				return;
			}
			for(DespatchDetailsVO despatchDetailsVO : selectedDespatchDetailsVOs){
				despatchDetailsVO.setCarrierCode(reassignDispatchForm.getFlightCarrierCode());
				despatchDetailsVO.setAcceptedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
				//despatchDetailsVO.setAcceptedWeight(Double.valueOf(reassignDispatchForm.getWeight()));
				despatchDetailsVO.setAcceptedWeight(reassignDispatchForm.getWeightMeasure());//added by A-7371
				despatchDetailsVO.setStatedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
				//despatchDetailsVO.setStatedWeight(Double.valueOf(reassignDispatchForm.getWeight()));	
				despatchDetailsVO.setStatedWeight(reassignDispatchForm.getWeightMeasure());//added by A-7371	
				despatchDetailsVO.setRemarks(reassignDispatchForm.getRemarks());

			}

		}
		//Added as part of bug-fix -icrd-13564 by A-4810 to enable reassign from capture consignment document
		else if(CAPTURE_MAIL_BAG.equals(fromScreen)){
			//Collection<RoutingInConsignmentVO> routingInConsignmentVOs = reassignSession.getRoutingInConsignmentVOs();
			selectedDespatchDetailsVOs = populateDespatchRoutingDetailsVOs(reassignSession );
			validateReassign(selectedDespatchDetailsVOs,reassignDispatchForm,reassignerrors);
			if(reassignerrors != null  && reassignerrors.size() > 0){
				log.entering("in reassign command errors-->", "errors"+reassignerrors.size());
				invocationContext.addAllError(reassignerrors);	
				reassignDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SCREENLOAD_FAILURE;

				return;
			}
			for(DespatchDetailsVO despatchDetailsVO : selectedDespatchDetailsVOs){
				despatchDetailsVO.setCarrierCode(reassignDispatchForm.getFlightCarrierCode());
				despatchDetailsVO.setAcceptedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
				//despatchDetailsVO.setAcceptedWeight(Double.valueOf(reassignDispatchForm.getWeight()));
				despatchDetailsVO.setAcceptedWeight(reassignDispatchForm.getWeightMeasure());//added by A-7371
				despatchDetailsVO.setStatedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
				//despatchDetailsVO.setStatedWeight(Double.valueOf(reassignDispatchForm.getWeight()));
				despatchDetailsVO.setStatedWeight(reassignDispatchForm.getWeightMeasure());
				despatchDetailsVO.setRemarks(reassignDispatchForm.getRemarks());
				
			}

		}
		
       //
		containerVO = populateContainerVO(reassignDispatchForm ,flightValidationVO);

		try {
			newContainerVOs = 
				new MailTrackingDefaultsDelegate().findFlightAssignedContainers(operationalFlightVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if(newContainerVOs == null || newContainerVOs.size() ==0){
			MailAcceptanceVO mailAcceptanceVO = constructAcceptanceVOFromConsignment(containerVO);	
			try{
				new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

		}

		try {
			containerDetailsVOs = 
				new MailTrackingDefaultsDelegate().reassignDSNs(selectedDespatchDetailsVOs,containerVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			statusFlag = false;
		}
		if(statusFlag ){
			reassignDispatchForm.setCloseFlag("Y");
		}
		log.log(Log.FINE, "CloseFlag ===", reassignDispatchForm.getCloseFlag());
		reassignDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);

		invocationContext.target = SCREENLOAD_SUCCESS;
	}
	/**
	 * 
	 * @param companyCode
	 * @param airportCode
	 * @return
	 */
	private AirportValidationVO validateAirport(String companyCode,
			String airportCode) {
		AirportValidationVO   airportValidationVO = null;
		try{
			airportValidationVO= new AreaDelegate().validateAirportCode(companyCode, airportCode);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return airportValidationVO;
	}
	/**
	 * to check whether the segment specified is present in flight route
	 * @param reassignDispatchForm
	 * @param flightValidationVO
	 * @param logonAttributes
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateFlightRoute(
			ReassignDispatchForm reassignDispatchForm,
			FlightValidationVO flightValidationVO,
			LogonAttributes logonAttributes) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String pol = logonAttributes.getStationCode();		
		String [] flightSegment = flightValidationVO.getFlightRoute().split("-");
		List<String> flightSegmentS = new ArrayList<String>();
		for(int i=0; i<flightSegment.length-1;i++){

			for(int j =i+1; j<flightSegment.length;j++){
				flightSegmentS.add(flightSegment[i].concat("~").concat(flightSegment[j]));

			}

		}
		String currentSegment = pol.concat("~").concat(reassignDispatchForm.getPou().toUpperCase());

		for(int i =0;i<flightSegmentS.size();i++){

			if(currentSegment.equals(flightSegmentS.get(i))){

				return null;
			}



		}
		errors.add(new ErrorVO("mailtracking.defaults.national.assignmailbag.error.segmentnotinroute"));		
		return errors;


	}

	/**
	 * 
	 * @param despatchDetailsVOs
	 * @param reassignDispatchForm
	 * @param reassignerrors
	 * @return 
	 */
	private void validateReassign(
			Collection<DespatchDetailsVO> despatchDetailsVOs,
			ReassignDispatchForm reassignDispatchForm,Collection<ErrorVO> reassignerrors) {


		for( DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs){			

			//if(despatchDetailsVO.getAcceptedBags() < Integer.parseInt(reassignDispatchForm.getPieces()) || despatchDetailsVO.getAcceptedWeight() < Double.valueOf(reassignDispatchForm.getWeight()))	{
			try {
				if(despatchDetailsVO.getAcceptedBags() < Integer.parseInt(reassignDispatchForm.getPieces()) 
						|| Measure.compareTo(despatchDetailsVO.getAcceptedWeight(), reassignDispatchForm.getWeightMeasure())<0)	{
					reassignerrors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.acceptedweightlessthanreassigned"));
				}
			} catch (NumberFormatException | UnitException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE,"UnitException",e.getMessage());
			}


		}


	}
	/**
	 * 
	 * @param containerVO
	 * @param selectedDespatchDetailsVOs
	 * @return
	 */
	private MailAcceptanceVO constructAcceptanceVOFromConsignment(
			ContainerVO containerVO
	) {
		MailAcceptanceVO newMailAcceptanceVO = new MailAcceptanceVO();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		newMailAcceptanceVO.setCarrierId(containerVO.getCarrierId());
		newMailAcceptanceVO.setCompanyCode(containerVO.getCompanyCode());
		newMailAcceptanceVO.setFlightCarrierCode(containerVO.getCarrierCode());
		newMailAcceptanceVO.setFlightDate(containerVO.getFlightDate());
		newMailAcceptanceVO.setFlightNumber(containerVO.getFlightNumber());
		newMailAcceptanceVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		newMailAcceptanceVO.setPol(logonAttributes.getAirportCode());
		newMailAcceptanceVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		newMailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		newMailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		newMailAcceptanceVO.setContainerDetails(constructContainerDetails(containerVO, logonAttributes ));	
		return newMailAcceptanceVO;
	}
	/**
	 * 
	 * @param containerVO
	 * @param logonAttributes
	 * @return
	 */
	private Collection<ContainerDetailsVO> constructContainerDetails(
			ContainerVO containerVO, LogonAttributes logonAttributes ) {
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setContainerNumber("BULK");
		containerDetailsVO.setContainerType("B");
		containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
		containerDetailsVO.setAssignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		containerDetailsVO.setCarrierId(containerVO.getCarrierId());
		containerDetailsVO.setCarrierCode(containerVO.getCarrierCode());
		containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerDetailsVO.setPol(logonAttributes.getAirportCode());
		containerDetailsVO.setPou(containerVO.getPou());
		containerDetailsVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		containerDetailsVO.setOperationFlag("I");
		containerDetailsVO.setContainerOperationFlag("I");
		//containerDetailsVO.setReassignFlag(true);
		containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
		//containerDetailsVO.setDesptachDetailsVOs(selectedDespatchDetailsVOs);
		//containerDetailsVO.setDsnVOs(dsnvos);
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		containerDetailsVOs.add(containerDetailsVO);
		return containerDetailsVOs;
	}

	/**
	 * 
	 * @param reassignDispatchForm
	 * @param flightValidationVO
	 * @param mailManifestVO
	 * @return
	 */
	private ContainerVO populateContainerVO(ReassignDispatchForm reassignDispatchForm, FlightValidationVO flightValidationVO ) {
		ContainerVO containerVo = new ContainerVO();		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		containerVo.setCompanyCode(flightValidationVO.getCompanyCode());
		containerVo.setCarrierId(flightValidationVO.getFlightCarrierId());		
		containerVo.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		containerVo.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		containerVo.setCarrierCode(reassignDispatchForm.getFlightCarrierCode());
		containerVo.setFlightNumber(reassignDispatchForm.getFlightNo());		
		containerVo.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(reassignDispatchForm.getFlightDate()));
		containerVo.setBags(Integer.parseInt(reassignDispatchForm.getPieces()));
		//containerVo.setWeight(Double.valueOf(reassignDispatchForm.getWeight()));
		containerVo.setWeight(reassignDispatchForm.getWeightMeasure());//added by A-7371
		containerVo.setPou(reassignDispatchForm.getPou().toUpperCase());
		containerVo.setCompanyCode(logonAttributes.getCompanyCode());		
		//		containerVo.setFromFltDat(mailManifestVO.getDepDate());
		//		containerVo.setFromFltNum(mailManifestVO.getFlightNumber());
		//		containerVo.setFromCarrier(mailManifestVO.getFlightCarrierCode());		
		containerVo.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
		containerVo.setAssignedUser(logonAttributes.getUserId());
		containerVo.setAssignedPort(logonAttributes.getAirportCode());		
		containerVo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerVo.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		containerVo.setOperationFlag("U");
		containerVo.setFlightStatus(flightValidationVO.getFlightStatus());
		int segmentSerialNumber = populateSegmentSerialNumber(flightValidationVO.getFlightRoute(), reassignDispatchForm.getPou().toUpperCase(), logonAttributes.getAirportCode());
		containerVo.setSegmentSerialNumber(segmentSerialNumber);
		containerVo.setConsignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));	
		containerVo.setContainerNumber("BULK");
		containerVo.setType("B");
		containerVo.setReassignFlag(true);

		return containerVo;
	}


	/**
	 * F1-F2 Reassign
	 * This method is used for constructing despatches for unassigning information from F1
	 * @param dsnvos
	 * @param flightValidationVO 
	 * @param assignMailBagSession
	 * @return 
	 */
	private Collection<DespatchDetailsVO> populateDespatchDetailsVOs(Collection<DSNVO> dsnvos  , FlightValidationVO flightValidationVO, AssignMailBagSession assignMailBagSession) {

		DespatchDetailsVO despatchDetailsVO = null;
		Collection<DespatchDetailsVO> selectedDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		MailManifestVO mailManifestVO = assignMailBagSession.getMailManifestVO();

		for(DSNVO dsnvo : dsnvos){
			despatchDetailsVO = new DespatchDetailsVO();

			//despatchDetailsVO.setFlightNumber(reassignForm.getFlightNo());			
			//despatchDetailsVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(reassignDispatchForm.getFlightDate()));
			//despatchDetailsVO.setCarrierCode(reassignDispatchForm.getFlightCarrierCode());
			despatchDetailsVO.setFlightDate(mailManifestVO.getDepDate());
			//despatchDetailsVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			//despatchDetailsVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			despatchDetailsVO.setAirportCode(logonAttributes.getAirportCode());		
			despatchDetailsVO.setOperationalFlag("I");
			despatchDetailsVO.setContainerNumber("BULK");
			despatchDetailsVO.setUldNumber("BULK");
			despatchDetailsVO.setContainerType("B");
			//despatchDetailsVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			despatchDetailsVO.setPrevStatedBags(dsnvo.getBags());
			despatchDetailsVO.setPrevStatedWeight(dsnvo.getWeight());
			despatchDetailsVO.setCompanyCode(dsnvo.getCompanyCode());
			despatchDetailsVO.setConsignmentNumber(dsnvo.getCsgDocNum());
			despatchDetailsVO.setConsignmentSequenceNumber(dsnvo.getCsgSeqNum());
			despatchDetailsVO.setConsignmentDate(dsnvo.getConsignmentDate());
			despatchDetailsVO.setPaCode(dsnvo.getPaCode());
			despatchDetailsVO.setOperationalFlag(dsnvo.getOperationFlag());
			despatchDetailsVO.setOwnAirlineCode(logonAttributes.getAirportCode());			
			//despatchDetailsVO.setPou(reassignForm.getPou());
			despatchDetailsVO.setAcceptedDate(dsnvo.getAcceptedDate());
			despatchDetailsVO.setAcceptedUser(logonAttributes.getUserId());
			
			//despatchDetailsVO.setStatedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
			//despatchDetailsVO.setStatedWeight(Double.valueOf(reassignDispatchForm.getWeight()));
			despatchDetailsVO.setAcceptedBags(dsnvo.getBags());
			despatchDetailsVO.setAcceptedWeight(dsnvo.getWeight());
			despatchDetailsVO.setOriginOfficeOfExchange(dsnvo.getOriginExchangeOffice());
			despatchDetailsVO.setDestinationOfficeOfExchange(dsnvo.getDestinationExchangeOffice());
			despatchDetailsVO.setYear(dsnvo.getYear());

			despatchDetailsVO.setDsn(dsnvo.getDsn());
			despatchDetailsVO.setMailCategoryCode(dsnvo.getMailCategoryCode());
			despatchDetailsVO.setMailClass(dsnvo.getMailClass());
			despatchDetailsVO.setMailSubclass(dsnvo.getMailSubclass());
			//int segmentSerialNumber = populateSegmentSerialNumber(flightValidationVO.getFlightRoute(), reassignForm.getPou(), logonAttributes.getAirportCode());
			//despatchDetailsVO.setSegmentSerialNumber(segmentSerialNumber);
			despatchDetailsVO.setCarrierId(dsnvo.getCarrierId());
			despatchDetailsVO.setFlightNumber(dsnvo.getFlightNumber());
			despatchDetailsVO.setFlightSequenceNumber(dsnvo.getFlightSequenceNumber());
			despatchDetailsVO.setSegmentSerialNumber(dsnvo.getSegmentSerialNumber());
			despatchDetailsVO.setPou(dsnvo.getPou().toUpperCase());
		
			despatchDetailsVO.setRemarks(dsnvo.getRemarks());
			selectedDespatchDetailsVOs.add(despatchDetailsVO);
		}

		return selectedDespatchDetailsVOs;
	}
	/**
	 * 
	 * @param flightRoute
	 * @param pou
	 * @param pol
	 * @return
	 */
	private int populateSegmentSerialNumber(String flightRoute,String pou,String pol){
		String [] flightSegment = flightRoute.split("-");
		List<String> flightSegmentS = new ArrayList<String>();
		for(int i=0; i<flightSegment.length-1;i++){

			for(int j =i+1; j<flightSegment.length;j++){
				flightSegmentS.add(flightSegment[i].concat("~").concat(flightSegment[j]));

			}

		}
		String currentFlightSegment = pol.concat("~").concat(pou);

		for(int i =0;i<flightSegmentS.size();i++){

			if(currentFlightSegment.equals(flightSegmentS.get(i))){
				return i+1;

			}

		}
		return 0;
	}

	/**
	 * 
	 * @param reassignDispatchForm
	 * @param logonAttributes
	 * @param airlineValidationVO
	 * @return
	 */
	private FlightFilterVO handleFlightFilterVO(ReassignDispatchForm reassignDispatchForm,
			LogonAttributes logonAttributes,
			AirlineValidationVO airlineValidationVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setActiveAlone(true);
		flightFilterVO.setStringFlightDate(reassignDispatchForm.getFlightDate());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		flightFilterVO.setFlightDate(date.setDate(reassignDispatchForm.getFlightDate()));
		return flightFilterVO;
	}
	/**
	 * 
	 * @param reassignDispatchForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ReassignDispatchForm reassignDispatchForm) {
		String flightCarrierCode = reassignDispatchForm.getFlightCarrierCode();
		String flightNumber = reassignDispatchForm.getFlightNo();
		String depDate = reassignDispatchForm.getFlightDate();
		String pieces = reassignDispatchForm.getPieces();
		String weight = reassignDispatchForm.getWeight();
		String pou = reassignDispatchForm.getPou();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if(flightCarrierCode == null || flightCarrierCode.trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.flightcarriercodemandatory"));

		}
		if(flightNumber == null || flightNumber.trim().length() == 0){

			errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.flightnumbermandatory"));
		}
		if(depDate == null || depDate.trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.flightdatemandatory"));
		}

		if(pieces == null || pieces.trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.piecesmandatory"));
		}
		if(pou == null || pou.trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.poumandatory"));
		}
		if(weight == null || weight.trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.reassign.error.weightmandatory"));
		}		

		return errors;
	}


	/**
	 * F1-F2 Reassign
	 * This method is used for constructing despatches for unassigning information from F1 for capturemaildocument screen
	 * @param reassignSession
	 * @author A-4810
	 * @return 
	 */
	
	public Collection<DespatchDetailsVO>populateDespatchRoutingDetailsVOs( ReassignDispatchSession reassignSession){
		Collection<DespatchDetailsVO> selectedDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = reassignSession.getRoutingInConsignmentVOs();
		ConsignmentDocumentVO ConsignmentDocumentVO = reassignSession.getConsignmentDocumentVO();
		DespatchDetailsVO despatchDetailsVO = null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	  for( RoutingInConsignmentVO routingvo:routingInConsignmentVOs)
	  {
		  despatchDetailsVO = new DespatchDetailsVO();
		  despatchDetailsVO.setFlightDate(routingvo.getOnwardFlightDate());
			//despatchDetailsVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			//despatchDetailsVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			despatchDetailsVO.setAirportCode(logonAttributes.getAirportCode());		
			despatchDetailsVO.setOperationalFlag("I");
			despatchDetailsVO.setContainerNumber("BULK");
			despatchDetailsVO.setUldNumber("BULK");
			despatchDetailsVO.setContainerType("B");
			//despatchDetailsVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			despatchDetailsVO.setPrevStatedBags(routingvo.getNoOfPieces());
			despatchDetailsVO.setPrevStatedWeight(routingvo.getWeight());
			despatchDetailsVO.setCompanyCode(routingvo.getCompanyCode());
			despatchDetailsVO.setConsignmentNumber(routingvo.getConsignmentNumber());
			despatchDetailsVO.setConsignmentSequenceNumber(routingvo.getConsignmentSequenceNumber());
			despatchDetailsVO.setConsignmentDate(ConsignmentDocumentVO.getConsignmentDate());
			despatchDetailsVO.setPaCode(routingvo.getPaCode());
			despatchDetailsVO.setOperationalFlag(routingvo.getOperationFlag());
			despatchDetailsVO.setOwnAirlineCode(logonAttributes.getAirportCode());			
			//despatchDetailsVO.setPou(reassignForm.getPou());
			despatchDetailsVO.setAcceptedDate(ConsignmentDocumentVO.getConsignmentDate());
			despatchDetailsVO.setAcceptedUser(logonAttributes.getUserId());
			
			//despatchDetailsVO.setStatedBags(Integer.parseInt(reassignDispatchForm.getPieces()));
			//despatchDetailsVO.setStatedWeight(Double.valueOf(reassignDispatchForm.getWeight()));
			despatchDetailsVO.setAcceptedBags(routingvo.getNoOfPieces());
			despatchDetailsVO.setAcceptedWeight(routingvo.getWeight());
			despatchDetailsVO.setOriginOfficeOfExchange(routingvo.getOriginOfficeOfExchange());
			despatchDetailsVO.setDestinationOfficeOfExchange(routingvo.getDestinationOfficeOfExchange());
		
			despatchDetailsVO.setYear(routingvo.getYear());

			despatchDetailsVO.setDsn(routingvo.getDsn());
			despatchDetailsVO.setMailCategoryCode(routingvo.getMailCategoryCode());
			despatchDetailsVO.setMailClass(routingvo.getMailClass());
			despatchDetailsVO.setMailSubclass(routingvo.getMailSubClass());
			//int segmentSerialNumber = populateSegmentSerialNumber(flightValidationVO.getFlightRoute(), reassignForm.getPou(), logonAttributes.getAirportCode());
			//despatchDetailsVO.setSegmentSerialNumber(segmentSerialNumber);
			despatchDetailsVO.setCarrierId(routingvo.getOnwardCarrierId());
			despatchDetailsVO.setFlightNumber(routingvo.getOnwardFlightNumber());
			despatchDetailsVO.setFlightSequenceNumber(routingvo.getOnwardCarrierSeqNum());
			despatchDetailsVO.setSegmentSerialNumber(routingvo.getSegmentSerialNumber());
			despatchDetailsVO.setPou(routingvo.getPou().toUpperCase());
			//fix for reassigning offloaded bags 
			despatchDetailsVO.setDestination(routingvo.getPou().toUpperCase());
			if(routingvo.getOnwardCarrierSeqNum()==-1) {
				despatchDetailsVO.setContainerNumber("OFL"+"-"+routingvo.getCompanyCode()+"-"+routingvo.getPou());				
			}
			selectedDespatchDetailsVOs.add(despatchDetailsVO);
		  
	  }
	  return selectedDespatchDetailsVOs;
	}
  
}
