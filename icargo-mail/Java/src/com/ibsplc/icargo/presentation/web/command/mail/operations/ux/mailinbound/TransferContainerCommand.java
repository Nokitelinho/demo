package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.TransferDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.TransferContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	23-Oct-2018		:	Draft
 */
public class TransferContainerCommand extends AbstractCommand  {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String CONST_FLIGHT = "F";
	private static final String OUTBOUND = "O";
	private static final String ROUTE_DELIMETER = "-";
	 private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS TransferContainerCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		this.log.entering("TransferContainerCommand", "execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		TransferDetails transferDetails = 
				(TransferDetails) mailinboundModel.getTransferDetails();
		

		ArrayList<OnwardRouting> onwardRoutingDetailsCollection=
				mailinboundModel.getTransferDetails().getOnwardRoutingDetailsCollection();
		FlightValidationVO flightValidationVO=
				new FlightValidationVO();
	 	String printFlag=MailConstantsVO.FLAG_NO;
	 	TransferManifestVO transferManifestVO=null;
	 	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 

		/**
		 * Validating the transfferDetails
		 */
	 	errors = validateForm(transferDetails,logonAttributes);
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		
		ArrayList<ContainerDetails> containerDetailsCollection = 
				 mailinboundModel.getContainerDetailsCollection();
		MailArrivalVO mailArrivalVO = null;
		ArrayList<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		
		
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVOToFetch=null;
		operationalFlightVOToFetch=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		try {
			mailArrivalVO = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVOToFetch);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(null!=mailArrivalVO){
			containerDetailsVos=mailArrivalVO.getContainerDetails();
		}
		
		String airport = logonAttributes.getAirportCode();
		Collection<ContainerVO> containerVOs = 
				new ArrayList<ContainerVO>();
	 	
	 	for(ContainerDetailsVO containerDetailsVO:containerDetailsVos){
			containerDetailsVoMap.put(
					containerDetailsVO.getContainerNumber(), containerDetailsVO);
		}
		
		for(ContainerDetails containerDetail:containerDetailsCollection){
			
			if(containerDetailsVoMap.containsKey(containerDetail.getContainerno())){
				containerDetailsVosSelected.add(
						containerDetailsVoMap.get(containerDetail.getContainerno()));
			}
		}
		
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected){
		
			ContainerVO vo=new ContainerVO();
			vo.setCompanyCode(containerDetailsVO.getCompanyCode());
			vo.setContainerNumber(containerDetailsVO.getContainerNumber());
			vo.setCarrierId(containerDetailsVO.getCarrierId());
			vo.setFlightNumber(containerDetailsVO.getFlightNumber());
			
			//LocalDate flightDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			vo.setFlightDate(containerDetailsVO.getFlightDate());   
			vo.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			vo.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
			vo.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			vo.setFinalDestination(containerDetailsVO.getDestination());
			vo.setAssignedPort(containerDetailsVO.getPol());
			vo.setAssignedUser(logonAttributes.getUserId());
			vo.setType(containerDetailsVO.getContainerType());
			vo.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
			vo.setCarrierCode(mailArrivalVO.getOwnAirlineCode()); 
			vo.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,true)); 
			vo.setULDLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE,true));  
			vo.setPou(containerDetailsVO.getPou());
			vo.setShipperBuiltCode(containerDetailsVO.getPaCode());
			vo.setContainerJnyID(containerDetailsVO.getContainerJnyId());
			
			Collection<OnwardRoutingVO> onwardRoutingVos = new ArrayList<OnwardRoutingVO>();
			String onwardFlightRouting = containerDetailsVO.getOnwardFlights();
			
			if (onwardFlightRouting != null) {
				String [] onwardFlightRoutings = onwardFlightRouting.split(",");
				if (onwardFlightRoutings != null && onwardFlightRoutings.length > 0) {
					for (String onwardFlightRoute : onwardFlightRoutings) {
						String [] onwardFlightRouteDetails =  onwardFlightRoute.split("-");
						OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
						onwardRoutingVO.setOnwardCarrierCode(onwardFlightRouteDetails[0]);
						onwardRoutingVO.setOnwardFlightNumber(onwardFlightRouteDetails[1]);
						StringBuilder OwnflightDate = new StringBuilder(onwardFlightRouteDetails[2]);
						OwnflightDate.append("-").append(onwardFlightRouteDetails[3]).append("-").append(onwardFlightRouteDetails[4]);
						LocalDate date =  new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
						date.setDate(OwnflightDate.toString());
						onwardRoutingVO.setOnwardFlightDate(date);
						onwardRoutingVO.setPou(onwardFlightRouteDetails[5]);
						onwardRoutingVO.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_INSERT);
						onwardRoutingVos.add(onwardRoutingVO);
					}
				}
				
				vo.setOnwardRoutings(onwardRoutingVos);
			}
			containerVOs.add(vo);
		}
		
		String reassignedto = transferDetails.getReassignedto();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
    	AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
    	
    	/**
    	 * If container is reassigned to Flight 
    	 */
    	if ("Flight".equals(reassignedto)) { 
    		
    		Collection<ErrorVO> fltErrors = new ArrayList<ErrorVO>();
    		
    		String flightCarrierCode = transferDetails.getFlightCarrierCode();
    		
    		/**
    		 * Validating flight carrier code
    		 */
        	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
        		try {        			
        			airlineValidationVO = airlineDelegate.validateAlphaCode(
        					logonAttributes.getCompanyCode(),
        					flightCarrierCode.trim().toUpperCase());

        		}catch (BusinessDelegateException businessDelegateException) {
        			errors = handleDelegateException(businessDelegateException);
        		}
        		
        		if (errors != null && errors.size() > 0) {            			
        			errors = new ArrayList<ErrorVO>();
        			Object[] obj = {flightCarrierCode.toUpperCase()};
    				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.invalidCarrier",obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					fltErrors.addAll(errors);
        		}
        	}
        	
        	/**
        	 * Validating POU and final destination
        	 */
        	Collection<String> airports = new ArrayList<String>();
        	airports.add(transferDetails.getDestination().toUpperCase());
        	Map<String,AirportValidationVO> stations = new HashMap<String,AirportValidationVO>();  
        	errors = null;
        	try {
        		stations = areaDelegate.validateAirportCodes(
    					logonAttributes.getCompanyCode(),
    					airports);			
    			
    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    		}
        	if (errors != null && errors.size() > 0) {
    			fltErrors.addAll(errors);    			
    		}
        	
        	/**
        	 * Validating POU and current station
        	 */
        	if(transferDetails.getFlightPou() != null && 
        			transferDetails.getFlightPou().trim().length() > 0 &&
    				logonAttributes.getAirportCode().equals(transferDetails.getFlightPou())) {   
    				if(errors == null) {
    					errors = new ArrayList<ErrorVO>();
    				}
    				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.invalidflightsegment");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					fltErrors.addAll(errors);
    		}
        	
        	/**
        	 * Validating POUs in onward routing
        	 */
        	if(onwardRoutingDetailsCollection.size()>0){
        		Map<String,AirportValidationVO> pointOfUnladings = 
        				new HashMap<String,AirportValidationVO>();
        		Set<String> stns = new HashSet<String>();
        		errors = null;
        		for(OnwardRouting onwardRoutingDetails:onwardRoutingDetailsCollection){
        			if(onwardRoutingDetails.getPou()!=null)
        			stns.add(onwardRoutingDetails.getPou().toUpperCase());
        		}
        		try {
        			if(stns.size()>0)
        			pointOfUnladings = areaDelegate.validateAirportCodes(
        					logonAttributes.getCompanyCode(),
        					stns);

        		}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			fltErrors.addAll(errors);
        		}
        	}
        	
        	/**
        	 * Validating Flight Carriers
        	 */
        	if(onwardRoutingDetailsCollection.size()>0){
        		Map<String,AirlineValidationVO> carrierIds = 
        				new HashMap<String,AirlineValidationVO>();
        		errors = null;
        		Set<String> carriers = new HashSet<String>();
        		for(OnwardRouting onwardRoutingDetails:onwardRoutingDetailsCollection){
        			if(onwardRoutingDetails.getCarrierCode()!=null)
        			carriers.add(onwardRoutingDetails.getCarrierCode().toUpperCase());
        		}
        		
        		try {
        			if(carrierIds.size()>0)
        			carrierIds = airlineDelegate.validateAlphaCodes(
        					logonAttributes.getCompanyCode(),
        					carriers);

        		}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			fltErrors.addAll(errors);
        		}
        		
        	}
        	
        	if (fltErrors != null && fltErrors.size() > 0) {
    			actionContext.addAllError((List<ErrorVO>) fltErrors);
    			return;
    		}
        	
        	
			// Updating routing vos skipped

			FlightFilterVO flightFilterVO = 
					handleFlightFilterVO(transferDetails, logonAttributes);

			flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
			flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());

			Collection<FlightValidationVO> flightValidationVOs = null;

			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
				flightValidationVOs = 
						mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
			if (errors != null && errors.size() > 0) {
    			actionContext.addAllError((List<ErrorVO>) errors);
    			return;
    		}
			/**
			 * If no results returned
			 */
			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
				Object[] obj = {transferDetails.getFlightCarrierCode().toUpperCase(),
						transferDetails.getFlightNumber().toUpperCase(),
						transferDetails.getFlightDate()};
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noflightDetails",obj);
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
			
			/**
			 * If only 1 VO is returned
			 */
			else if ( flightValidationVOs.size() == 1) {        				
				for (FlightValidationVO flightValidVO : flightValidationVOs) {
					
					flightValidationVO=flightValidVO;
		    		errors = isPouValid(transferDetails,flightValidVO,logonAttributes);
		    		//based on the Soncy comment
        			//IASCB-63549: Modified	    		
		    		boolean isToBeActioned = FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ;
		    		isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
		    		if((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())||
		                    FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus()))){
						Object[] obj = {flightCarrierCode.toUpperCase(),flightValidVO.getFlightNumber()};
						ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
						err.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(err);						
						return;
					}
		    		if (errors != null && errors.size() > 0) {
		    			actionContext.addAllError((List<ErrorVO>) errors);
		    			return;
		    		}
		    		//Validate ULD Type- Aircraft Compatibility starts
		    		    		
		    		errors=validateULDIncomatibility(containerDetailsVosSelected,flightValidVO);
		    		if (errors != null && errors.size() > 0) {
		    			actionContext.addAllError((List<ErrorVO>) errors);
		    			return;
		    		}
	    		//validateULDIncomatibility
	    		
		    		
		    		
					break;
				}
			}
			/**
			 * If more then 1 Vos returned
			 */
			else if(flightValidationVOs.size() > 1){
				//Duplicate flights skipped
				return;
			}
			
			//Transfer manifest report flag skipped
    	}
    	
    	/**
    	 * If container is reassigned to destination, not flight 
    	 */
    	else{
    		
    		Collection<ErrorVO> destnErrors = new ArrayList<ErrorVO>();
    		
    		/**
    		 * Validating carrier code
    		 */
        	String carrierCode = transferDetails.getCarrier();
        	if (carrierCode != null && !"".equals(carrierCode)) {        		
        		try {        			
        			airlineValidationVO = airlineDelegate.validateAlphaCode(
        					logonAttributes.getCompanyCode(),
        					carrierCode.toUpperCase());

        		}catch (BusinessDelegateException businessDelegateException) {
        			errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {        			
        			errors = new ArrayList<ErrorVO>();
        			Object[] obj = {carrierCode.toUpperCase()};
    				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.invalidCarrier",obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
        			destnErrors.addAll(errors);
        		}
        	}
        	
        	/**
        	 * Validating Destination
        	 */
        	String destn = transferDetails.getDestination();        	
        	errors = null;
        	if (destn != null && !"".equals(destn)) {
        		try {
        			airportValidationVO = areaDelegate.validateAirportCode(
        					logonAttributes.getCompanyCode(),
        					destn.toUpperCase());			
        			
        		}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        		}
        		if (errors != null && errors.size() > 0) {
        			destnErrors.addAll(errors);
        		}
        	}
        	
        	if (destnErrors != null && destnErrors.size() > 0) {
    			actionContext.addAllError((List<ErrorVO>) destnErrors);
    			return;
    		}
        	
    		String firstDestn = null;
    		for (ContainerVO containervo : containerVOs) {
    			firstDestn = containervo.getFinalDestination();    			
    			break;
    		}
    		for (ContainerVO containervo : containerVOs) {
    			if(firstDestn!=null && firstDestn.length()>0){
	    			if (!firstDestn.equalsIgnoreCase(containervo.getFinalDestination())) {
	    				errors = new ArrayList<ErrorVO>();
	    				ErrorVO errorVO = new ErrorVO(
	    						"mailtracking.defaults.reassigncontainer.msg.err.reassigningDifferentDestinations");
	    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    				errors.add(errorVO);
	    				actionContext.addAllError((List<ErrorVO>) errors);
	    				return;
	    			}
    			}
    		}
    		
    		//Transfer manifest report flags skipped
    		
    	}
    	
    	 Collection <OnwardRoutingVO> routingVos= 
    			 getOnwardRoutingVos(transferDetails,logonAttributes);
    	for(ContainerVO containervo:containerVOs ){
    		containervo.setOnwardRoutings(routingVos);
    	}
		
    	if(transferDetails.getScanDate()==null && ("").equals(transferDetails.getScanTime())){
			actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
			return; 
		}
    	if(transferDetails.getScanTime()==null ||("").equals(transferDetails.getScanTime())){
    		actionContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
 	   		return; 
		}
    	
    	String scanDate= new StringBuilder().append(transferDetails.getScanDate()).append(" ")
    						.append(transferDetails.getScanTime()).append(":00").toString();
	    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    scanDat.setDateAndTime(scanDate);
    	
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate1 = 
    		new MailTrackingDefaultsDelegate();
    	
    	String reassignedto1 = transferDetails.getReassignedto();

    	
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	operationalFlightVO.setOwnAirlineId(
    			logonAttributes.getOwnAirlineIdentifier());
    	operationalFlightVO.setOperator(
    			logonAttributes.getUserId());
    	
    	operationalFlightVO.setOperationTime(scanDat);
    	
    	if ("Flight".equals(reassignedto)) {  
    		errors = isReassignedToSameFlight(
					flightValidationVO,containerVOs,transferDetails);
    		if (errors != null && errors.size() > 0) {      			
    			actionContext.addAllError((List<ErrorVO>) errors);
    			return;
    		}
    		
    		if(flightValidationVO != null){
    			if(flightValidationVO.isTBADueRouteChange() && !canIgnoreToBeActionedCheck()){
    				Object [] obj = {flightValidationVO.getCarrierCode(),
    									flightValidationVO.getFlightNumber(),transferDetails.getFlightDate()};
    				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.transfercontainer.msg.err.flighttobeactioned",obj);
    				if(errors == null){
    					errors = new ArrayList<ErrorVO>();
    				}
    				errors.add(errorVO);
    				actionContext.addAllError((List<ErrorVO>) errors);
    				return;
    			}
    			//based on the Soncy comment
    			//IASCB-63549: Modified
    			boolean isToBeActioned = FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ;
    			isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
    			if((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
                        FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
    				Object[] obj = {flightValidationVO.getCarrierCode().toUpperCase(),flightValidationVO.getFlightNumber()};
    				ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
    				err.setErrorDisplayType(ErrorDisplayType.ERROR);
    				actionContext.addError(err);
    				return;
    			}
    		}
    			
    	
		    	for (ContainerVO vo : containerVOs) {	
					vo.setRemarks(transferDetails.getRemarks());
					vo.setReassignFlag(true);
					vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
					vo.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
							Location.NONE,true)); 
					vo.setULDLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
							Location.NONE,true));
					vo.setScannedDate(scanDat);
				}
				
				operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());    	
		    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
		    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
		    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		    	operationalFlightVO.setPou(transferDetails.getDestination().toUpperCase());
		    	
		    	if(flightValidationVO.getAtd() != null){
					operationalFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
				}
			}
    	else{

    			operationalFlightVO.setCarrierCode(airlineValidationVO.getAlphaCode());
            	operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());  
            	operationalFlightVO.setFlightDate(null);
            	operationalFlightVO.setFlightNumber("-1");
            	operationalFlightVO.setFlightSequenceNumber(-1);
            	operationalFlightVO.setLegSerialNumber(-1);
            	operationalFlightVO.setPou(transferDetails.getDestination().toUpperCase());
            	
            	for (ContainerVO vo : containerVOs) {					
    				vo.setRemarks(transferDetails.getRemarks());
    				vo.setReassignFlag(true);
    				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
    				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
    				vo.setFinalDestination(transferDetails.getDestination().toUpperCase());
    				vo.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
    						Location.NONE,true));   
    				vo.setULDLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
    						Location.NONE,true));
    				vo.setScannedDate(scanDat);   
        		}
        	}
   
    	try {
    	
    	transferManifestVO=mailTrackingDefaultsDelegate.transferContainers(
						containerVOs,operationalFlightVO,printFlag);
    	
    	}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
    	}
    	
    	if (errors != null && errors.size() > 0) {
			
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}	else{
    		  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
    			  	//Print skipped  
    				 
    		   }    
    	  }
    	
    	ResponseVO responseVO = new ResponseVO();	  
	    responseVO.setStatus("transfersave_success");
	    ErrorVO error = new ErrorVO("mail.operations.succ.transfercontainersuccess");      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
	    actionContext.setResponseVO(responseVO);  
		log.exiting("TransferContainerCommand","execute");
    	
    	


	}
		
    	
    	
    	
			
				
		
		
	
	
	
	
	
	private Collection<ErrorVO> isReassignedToSameFlight(FlightValidationVO flightValidationVO,
			Collection<ContainerVO> containerVOs, TransferDetails transferDetails) {
		
		boolean isSameFlight = false;
    	StringBuilder errorcode = new StringBuilder("");
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();    	
    	log.log(Log.FINE, "ReassignedFlightValidationVO-------> ",
    			flightValidationVO);
		for (ContainerVO selectedvo : containerVOs) {
    		if (!("-1").equals(selectedvo.getFlightNumber())) {
    			if ((flightValidationVO.getFlightCarrierId() == selectedvo.getCarrierId())
            			&& (flightValidationVO.getFlightNumber().equals(selectedvo.getFlightNumber()))
            			&& (flightValidationVO.getLegSerialNumber() == selectedvo.getLegSerialNumber())
            			&& (flightValidationVO.getFlightSequenceNumber() == selectedvo.getFlightSequenceNumber())
            			&& (transferDetails.getFlightPou().equalsIgnoreCase(selectedvo.getPou()))
        			) {
    				errorcode.append(selectedvo.getContainerNumber()).append(",");
    				isSameFlight = true;
            	}    			
    		}    		    		
    	}
		
		if (isSameFlight) {
    		Object[] obj = {errorcode.substring(0,errorcode.length()-1)};
    		ErrorVO errorVO = new ErrorVO(
    				"mailtracking.defaults.reassigncontainer.msg.err.cannotReassignToSameFlight",obj);
    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    		validationerrors.add(errorVO);
    	}
    	  	
    	return validationerrors;
		
	}




	private Collection<ErrorVO> isPouValid(TransferDetails transferDetails, FlightValidationVO flightValidVO,
			LogonAttributes logonAttributes) {
		Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
    	/**
    	 * Obtain the collection of POUs
    	 */
    	
		Collection<String> pointOfUnladings =
			getPointOfUnladings(
					flightValidVO.getFlightRoute(), logonAttributes);	
		StringBuilder fullroute = new StringBuilder("");
		for (String route : pointOfUnladings) {
			fullroute.append(route).append("-");
		}
		
		if(!pointOfUnladings.contains(transferDetails.getDestination().toUpperCase())) {
			Object[] obj = {transferDetails.getDestination().toUpperCase(),
					flightValidVO.getCarrierCode(),
					flightValidVO.getFlightNumber(),
					fullroute.substring(0,fullroute.length()-1)};
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.invalidFlightpou",obj);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);			
			validationerrors.add(errorVO);
		}
    	
    	return validationerrors;
	}
	
	
	private Collection<String> getPointOfUnladings(
			String route, LogonAttributes logonAttributes) {
		log.exiting("SaveContainerCommand", "getPOUs");

		Collection<String> pointOfUnladings = new ArrayList<String>();
		String[] stations = route.split(ROUTE_DELIMETER);
		if(route != null && route.length() > 0){
			/**
			 * the pous should start from the current station
			 */
			for(int index=1; index < stations.length; index++) {
				pointOfUnladings.add(stations[index]);
			}
		}
		
		return pointOfUnladings;
	}




	private boolean isTerminating(Collection<String> nearbyOEToCurrentAirport,
			MailbagVO mailbagvo){
		boolean isTerminating = false;
		if(nearbyOEToCurrentAirport != null && nearbyOEToCurrentAirport.size() > 0){
			for(String officeOfExchange : nearbyOEToCurrentAirport){
				if(mailbagvo != null && !"Y".equals(mailbagvo.getDeliveredFlag())){
					isTerminating = officeOfExchange.equals(mailbagvo.getDoe()) ? true : false;
					if(isTerminating){
						break;	
					}
				}
			}
		}
		return isTerminating;
	}
	
	
	 private Collection <OnwardRoutingVO> getOnwardRoutingVos(
	     		TransferDetails transferDetails, LogonAttributes logonAttributes) {
	     	
	     	String reassignedto = transferDetails.getReassignedto();
			ContainerVO containerVO = new ContainerVO();
			Collection <OnwardRoutingVO> onwardRoutings=
					new ArrayList<OnwardRoutingVO>();
			ArrayList<OnwardRouting> onwardRoutingDetailsIterate=
					transferDetails.getOnwardRoutingDetailsCollection();
			for(OnwardRouting details:onwardRoutingDetailsIterate){
			if (details.getPol() != null && details.getPol().trim().length() > 0 && details.getPou() != null
					&& details.getPou().trim().length() > 0) {
				OnwardRoutingVO onwardRoutingVO=new OnwardRoutingVO();
				onwardRoutingVO.setOnwardCarrierCode(details.getCarrierCode());
				onwardRoutingVO.setOnwardFlightNumber(details.getFlightNumber());
				LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
				if(details.getFlightDate()!=null){
					onwardRoutingVO.setOnwardFlightDate(date.setDate(details.getFlightDate()));
				}
				onwardRoutingVO.setPou(details.getPou());
				onwardRoutingVO.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_INSERT);
				onwardRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
				onwardRoutingVO.setAssignmenrPort(logonAttributes.getAirportCode());
				onwardRoutings.add(onwardRoutingVO);
			}
				
			}
			
			return onwardRoutings;

	     }
	 
	 
	 /**
	  * 
	  * 	Method		:	TransferContainerCommand.validateForm
	  *		Added by 	:	A-8164 on 23-Oct-2018
	  * 	Used for 	:
	  *		Parameters	:	@param transferDetails
	  *		Parameters	:	@param logonAttributes
	  *		Parameters	:	@return 
	  *		Return type	: 	Collection<ErrorVO>
	  */
	 private Collection<ErrorVO> validateForm(
			 TransferDetails transferDetails,LogonAttributes logonAttributes) {
		 
		 Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
			
		 String reassignedto = transferDetails.getReassignedto();
		 
		 if ("Flight".equals(reassignedto)) {    		
				if (("").equals(transferDetails.getFlightCarrierCode())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightCarrierCode");
					formErrors.add(errorVO);
				}
				if (("").equals(transferDetails.getFlightNumber())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightNumber");
					formErrors.add(errorVO);
				}
				if (("").equals(transferDetails.getFlightDate())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightDate");
					formErrors.add(errorVO);
				}
				if (("").equals(transferDetails.getFlightPou())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noFlightPou");
					formErrors.add(errorVO);
				}
				
				ArrayList<OnwardRouting> onwardRoutingDetailsIterate=
						transferDetails.getOnwardRoutingDetailsCollection();
				boolean hasFlightError = false;
				if(onwardRoutingDetailsIterate.size()>0){
					for(OnwardRouting onwardRoutingDetails:onwardRoutingDetailsIterate){
						if (("").equals(onwardRoutingDetails.getCarrierCode())) {
							ErrorVO errorVO = new ErrorVO(
									"mailtracking.defaults.reassigncontainer.msg.err.noFltCarrier");
							formErrors.add(errorVO);
							hasFlightError = true;
						}
						if (("").equals(onwardRoutingDetails.getFlightNumber())) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noFltNo");
							formErrors.add(errorVO);
							hasFlightError = true;
						}
						if (("").equals(onwardRoutingDetails.getFlightDate())) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noFltDate");
							formErrors.add(errorVO);
							hasFlightError = true;
						}
						if (("").equals(onwardRoutingDetails.getPou())) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noPou");
							formErrors.add(errorVO);
							hasFlightError = true;
						}
						if (hasFlightError) {
							break;
						}
					}
				}
				
				boolean hasPouError = false;
				if(onwardRoutingDetailsIterate.size()>0){
					for(OnwardRouting onwardRoutingDetails:onwardRoutingDetailsIterate){
						if (onwardRoutingDetails.getPou() != null && 
								!("").equals(onwardRoutingDetails.getPou())) {
							if (onwardRoutingDetails.getPou().equalsIgnoreCase(logonAttributes.getAirportCode())) {
								ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.pouEqualsCurrentAirport");
								formErrors.add(errorVO);
								hasPouError = true;
							}
						}
						if (hasPouError) {
							break;
						}
					}
				}
				
				if(onwardRoutingDetailsIterate.size()>0){
					for(int i=0;i<onwardRoutingDetailsIterate.size();i++){
						for(int j=i+1;j<onwardRoutingDetailsIterate.size();j++){
							if(onwardRoutingDetailsIterate.get(i).getPou().equals(
									onwardRoutingDetailsIterate.get(j).getPou())){
								Object obj[] = {onwardRoutingDetailsIterate.get(i).getPou().toUpperCase()};
								ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.pouduplicated",obj);
								formErrors.add(errorVO);
								break;
							}
						}
					}
				}
		 }
		 else{
			 
			 if (("").equals(transferDetails.getCarrier())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.noCarrier");
					formErrors.add(errorVO);
			 }
			 //Same carrier validation skipped
			 
			 if (("").equals(transferDetails.getDestination())) {
					ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.noDestination");
					formErrors.add(errorVO);
					}
					else if(logonAttributes.getAirportCode().equals(transferDetails.getDestination())) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.destnEqualToCurrentAirport");
						formErrors.add(errorVO);
					}
	 
		 }
				
		
					return formErrors;
		 
	 }
	 
	 
	 /**
	  * 
	  * 	Method		:	TransferContainerCommand.handleFlightFilterVO
	  *		Added by 	:	A-8164 on 23-Oct-2018
	  * 	Used for 	:	Method to create the filter vo for flight validation
	  *		Parameters	:	@param transferContainerForm
	  *		Parameters	:	@param logonAttributes
	  *		Parameters	:	@return 
	  *		Return type	: 	FlightFilterVO
	  */
	 private FlightFilterVO handleFlightFilterVO(
			 TransferDetails transferDetails,LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			flightFilterVO.setFlightNumber(transferDetails.getFlightNumber().toUpperCase());
			flightFilterVO.setStation(logonAttributes.getAirportCode());
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setIncludeACTandTBC(true);
			flightFilterVO.setStringFlightDate(transferDetails.getFlightDate());
	 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
	 		flightFilterVO.setFlightDate(date.setDate(
	 				transferDetails.getFlightDate()));
			return flightFilterVO;
		}

	 /**
		 * validateULDIncomatibility
		  *@author A-5526 for IASCB-34124 
		 * @param containerDetailsVosSelected
		 * @param flightValidationVO
		 * @param actionContext
		 */
		private Collection<ErrorVO> validateULDIncomatibility(ArrayList<ContainerDetailsVO> containerDetailsVosSelected, FlightValidationVO flightValidationVO) {

			Collection<String> parameterCodes = new ArrayList<String>();
			// ICRD-56719
			Collection<ErrorVO> errors = null;

			parameterCodes.add(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);

			Map<String, String> systemParameters = null;
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			try {
				systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
			} catch (BusinessDelegateException e) {
				log.log(Log.INFO, "caught BusinessDelegateException ");
				e.getMessageVO().getErrors();
			}

			ArrayList<String> uldTypeCodes = new ArrayList<String>();
			ArrayList<String> uldNumberCodes = new ArrayList<String>();

			if(containerDetailsVosSelected!= null &&
					containerDetailsVosSelected.size()>0){
				for(ContainerDetailsVO containerVO:containerDetailsVosSelected){
					
						/*
						 * ULD type compatibility validation
						 */
						if(containerVO.getContainerNumber() != null &&
								containerVO.getContainerNumber().trim().length() > 0 ){
							String uldType=containerVO.getContainerNumber().substring(0, 3);
							if(!uldTypeCodes.contains(uldType.toUpperCase())){
								uldTypeCodes.add(uldType.toUpperCase());
							}
							uldNumberCodes.add(containerVO.getContainerNumber());
						}
					
				}
			}
			
			
			
			
			
				Collection<ULDPositionFilterVO> filterVOs = new ArrayList<ULDPositionFilterVO>();
				if (flightValidationVO != null) {
					Collection<String> aircraftTypes = new ArrayList<String>();
					aircraftTypes.add(flightValidationVO.getAircraftType());
					ULDPositionFilterVO filterVO = null;
					Collection<String> validatedUldTypeCodes = validateAirCraftCompatibilityforUldTypes(uldTypeCodes,
							systemParameters);
					if (validatedUldTypeCodes != null && validatedUldTypeCodes.size() > 0) {
						for (String uldType : validatedUldTypeCodes) {
							filterVO = new ULDPositionFilterVO();
							filterVO.setAircraftTypes(aircraftTypes);
							filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
							filterVO.setUldCode(uldType);
							filterVOs.add(filterVO);
						}
					}
				}
				if (filterVOs != null && filterVOs.size() > 0) {
					try {
						new ULDDelegate().findULDPosition(filterVOs);
					} catch (BusinessDelegateException businessDelegateException) {
						Collection<ErrorVO> errs = handleDelegateException(businessDelegateException);
						for (ErrorVO error : errs) {
							log.log(Log.FINE, "Error code received -->>", error.getErrorCode());
							if (MailConstantsVO.ULD_INCOMPATIBLEAIRCRAFT.equals(error.getErrorCode())) {
								Object[] errorData = error.getErrorData();
								String errorDatum = null;
								if (errorData != null && errorData.length > 0) {
									errorDatum = (String) errorData[0];
								}

								
								if(errors == null){
		        					errors = new ArrayList<ErrorVO>();
		        				}
								errors.add(new ErrorVO(
										"mailtracking.defaults.reassigncontainer.msg.err.uldincompatileforaircrafttype",
										new Object[] { errorDatum }));
		        				
		        				return errors;
								
							}
						}

					}
				}
			return null;
		}
		 
		 /**
		  * @author A-5526 for IASCB-34124
		  * validateAirCraftCompatibilityforUldTypes
		  * @param uldTypeCodes
		  * @param systemParameterMap
		  * @return
		  */
		public Collection<String> validateAirCraftCompatibilityforUldTypes(Collection<String> uldTypeCodes,
				Map<String, String> systemParameterMap) {
			log.entering("SaveAcceptanceCommand", "validateAirCraftCompatibilityforUldTypes");
			ArrayList<String> uldTypeCodesForValidation = null;
			if (systemParameterMap != null && systemParameterMap.size() > 0) {
				String configuredTypes = systemParameterMap.get(AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED);
				if (configuredTypes != null && configuredTypes.length() > 0 && !"N".equals(configuredTypes)) {
					if ("*".equals(configuredTypes)) {
						for (String uldType : uldTypeCodes) {
							if (uldTypeCodesForValidation == null) {
								uldTypeCodesForValidation = new ArrayList<String>();
							}
							uldTypeCodesForValidation.add(uldType);
						}
					} else {
						List<String> configuredTypesList = Arrays.asList(configuredTypes.split(","));
						if (uldTypeCodes != null && uldTypeCodes.size() > 0) {
							for (String uldType : uldTypeCodes) {
								if (configuredTypesList.contains(uldType)) {
									if (uldTypeCodesForValidation == null) {
										uldTypeCodesForValidation = new ArrayList<String>();
									}
									uldTypeCodesForValidation.add(uldType);
								}
							}
						}
					}
				}
			}
			log.exiting("SaveAcceptanceCommand", "validateAirCraftCompatibilityforUldTypes");
			return uldTypeCodesForValidation;
		}
		/**
		 * for AA no need to validate against TBC flight
		 * @return
		 */
		private boolean canIgnoreToBeActionedCheck() {
			Collection<String> parameterCodes = new ArrayList<String>();
			parameterCodes.add("mail.operations.ignoretobeactionedflightvalidation");
			Map<String, String> systemParameters = null;
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			try {
				systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterCodes);
			} catch (BusinessDelegateException e) {
				log.log(Log.INFO, "caught BusinessDelegateException ");
			}
			if(systemParameters!=null && systemParameters.containsKey("mail.operations.ignoretobeactionedflightvalidation")) {
				return "Y".equals(systemParameters.get("mail.operations.ignoretobeactionedflightvalidation"));
			}
			return false;
		}
}
