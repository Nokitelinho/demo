package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ChangeContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound.SaveContainerChangeCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	05-Nov-2018		:	Draft
 */
public class SaveContainerChangeCommand extends AbstractCommand {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	
	private static final String INVALID_CARRIER = "mailtracking.defaults.invalidcarrier";
	private static final String NO_FLIGHT_DETAILS = "mailtracking.defaults.noflightdetails";
	private static final String FLIGHT_CARRIER_CODE_EMPTY = "mailtracking.defaults.flightcarriercode.empty";
	private static final String FLIGHT_NUMBER_EMPTY = "mailtracking.defaults.flightnumber.empty";
	private static final String DEPDATE_EMPTY = "mailtracking.defaults.flightdate.empty";
	private static final String CANCELLED_FLIGHT = "mailtracking.defaults.consignment.err.flightcancelled";
	private static final String IS_SAME_FLIGHT = "mailtracking.defaults.changeflight.issameflight";
	private static final String INBOUND = "I";
	private static final String FLTSTATUS = "mailtracking.defaults.flightstatus";
	private static final String FLTLEGSTATUS = "flight.operation.flightlegstatus";
	private static final String FLIGHT_CLOSED = "mailtracking.defaults.changeflight.flightclosed";
	private static final String CON = "CON";
	private static final String NOT = "NOT";
	private static final String ERROR_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.err.mailAlreadyTransferedOrDelivered";
	private static final String CONTAINER_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.changeflight.containertransferredordelivered";
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS SaveContainerChangeCommand");
	
	public void execute(ActionContext actionContext)
		    throws BusinessDelegateException {
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute(); 
		MailinboundModel mailinboundModel = 
				(MailinboundModel) actionContext.getScreenModel();
		ArrayList<ContainerDetails> containerDetailsCollection = 
				 mailinboundModel.getContainerDetailsCollection();
		MailArrivalVO mailArrivalVO =null;
		Collection<ContainerDetailsVO> containerDetailsVos=null;
		Collection<ContainerDetailsVO> containerDetailsVosSelected=
				new ArrayList<ContainerDetailsVO>();
		HashMap<String,ContainerDetailsVO> containerDetailsVoMap=
				new HashMap<String,ContainerDetailsVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ChangeContainerDetails changeContainerDetails= 
				(ChangeContainerDetails) mailinboundModel.getChangeContainerDetails();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		OperationalFlightVO operationalFlightVO=null;
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
	    		new MailTrackingDefaultsDelegate();  
		MailArrivalVO newMailArrivalVO = new MailArrivalVO();
		Collection<MailArrivalVO> mailArrivalVOs=new ArrayList<MailArrivalVO>();  
		
		boolean isFlightClosed = false; 
		
		MailinboundDetails mailinboundDetails = 
				(MailinboundDetails)mailinboundModel.getMailinboundDetails();
		operationalFlightVO=
				MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails, logonAttributes);
		try {
			mailArrivalVO = 
					new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(null!=mailArrivalVO){
			containerDetailsVos=mailArrivalVO.getContainerDetails();
		}
		
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
		
		
		errors = validateFormFlight(changeContainerDetails);
		
		if (errors != null && errors.size() > 0) {  
    		actionContext.addAllError((List<ErrorVO>) errors);
			return;
    	}
		
		FlightFilterVO flightFilterVO = handleFlightFilterVO(
							changeContainerDetails,logonAttributes);	
    	String flightNum = (changeContainerDetails.getFlightNumber().toUpperCase());
    	
    	AirlineValidationVO airlineValidationVO = null;
	    String flightCarrierCode = changeContainerDetails.getFlightCarrierCode();        	
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
						INVALID_CARRIER,obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
    			actionContext.addAllError((List<ErrorVO>) errors);
    			return;
    		}
    	}
    	
		/*******************************************************************
		 * validate Flight 
		 ******************************************************************/
    	
    	flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
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
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			log.log(Log.FINE, "flightValidationVOs is NULL");
			Object[] obj = {changeContainerDetails.getFlightCarrierCode(),
					changeContainerDetails.getFlightNumber(),
					changeContainerDetails.getDate().toString().substring(0,11)};
			errors.add(new ErrorVO(NO_FLIGHT_DETAILS,obj));
			if(errors != null && errors.size() >0){
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
		}
		
		else if ( flightValidationVOs.size() == 1) {
			log.log(Log.FINE, "flightValidationVOs has one VO");
			try {
				for (FlightValidationVO flightValidVO : flightValidationVOs) {
					BeanHelper.copyProperties(flightValidationVO,
							flightValidVO);
					if(FlightValidationVO.FLT_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
							FlightValidationVO.FLT_STATUS_TBC.equals(flightValidationVO.getFlightStatus())){
					}
					else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())){
							Object[] obj = {flightCarrierCode.toUpperCase(),flightValidationVO.getFlightNumber()};
							ErrorVO err = new ErrorVO(CANCELLED_FLIGHT,obj);                   
							err.setErrorDisplayType(ErrorDisplayType.ERROR);              
							return;
					}
					else if(operationalFlightVO.getFlightNumber().equals(flightValidationVO.getFlightNumber())
									&&(operationalFlightVO.getFlightSequenceNumber()
											==flightValidationVO.getFlightSequenceNumber())){
							ErrorVO err = new ErrorVO(IS_SAME_FLIGHT);                   
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							actionContext.addError(err);                  
							return;
					}
					break;
					}
				} catch (SystemException systemException) {
					systemException.getMessage();
				}
			
			flightValidationVO.setDirection(INBOUND);
			log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
			
			operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
			operationalFlightVO.setPol(flightValidationVO.getLegOrigin());
			operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
			operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
			operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
			operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			operationalFlightVO.setDirection(INBOUND);

			log.log(Log.FINE, "operationalFlightVO in MA session...", operationalFlightVO);

			try {
				containerVOs = new MailTrackingDefaultsDelegate().
						findFlightAssignedContainers(operationalFlightVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
		} else {
			
			
			/**
			 * Dulplicate flight session skipped
			 */
		}
		
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			log.log(Log.FINE, "oneTimes is not null");
			Collection<OneTimeVO> resultStatus=
				oneTimes.get(FLTLEGSTATUS);
			flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
			flightValidationVO.setDirection(INBOUND);
		}
			
		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setFlightDate(flightValidationVO.getFlightDate());
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
		operationalFlightVO.setDirection(INBOUND);
		operationalFlightVO.setPol(flightValidationVO.getLegOrigin());
		
		try {
    		if(new MailTrackingDefaultsDelegate().validateInboundFlight(
    				operationalFlightVO) != null) {
    			isFlightClosed = new MailTrackingDefaultsDelegate().
    				isFlightClosedForInboundOperations(operationalFlightVO);
    		}
			
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
		if (isFlightClosed) {
			actionContext.addError(new ErrorVO(FLIGHT_CLOSED));    			
			return;				
		}
		
		MailArrivalFilterVO mailArrivalFilterVO = 
				new MailArrivalFilterVO();
		
		/**
		 * mailArrivalFliterVO was taken from the session previously. Need to check if all necessary
		 * values are populated here.
		 */
			mailArrivalFilterVO = constructMailArrivalFilterVO(operationalFlightVO);
			mailArrivalFilterVO.setMailStatus("ALL");
			mailArrivalFilterVO.setNextCarrierId(mailArrivalVO.getTransferCarrierId());
			mailArrivalFilterVO.setPaCode(mailArrivalVO.getArrivalPA());
			mailArrivalFilterVO.setNextCarrierCode(mailArrivalVO.getTransferCarrier());

		
		log.log(Log.FINE, "mailArrivalFilterVO b4 gng to server call...", mailArrivalFilterVO);
		try {
			newMailArrivalVO = new MailTrackingDefaultsDelegate().findArrivalDetails(mailArrivalFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		log.log(Log.FINE, "newMailArrivalVO ----->>", newMailArrivalVO);
	  		
		newMailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
		newMailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
		newMailArrivalVO.setFlightCarrierCode(mailArrivalVO.getFlightCarrierCode());
		newMailArrivalVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		newMailArrivalVO.setArrivalDate(mailArrivalVO.getArrivalDate());
		newMailArrivalVO.setCarrierId(mailArrivalVO.getCarrierId());
		newMailArrivalVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		newMailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		newMailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		newMailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			  
		if (newMailArrivalVO.getContainerDetails() != null &&
				newMailArrivalVO.getContainerDetails().size() > 0) {
			for (ContainerDetailsVO containerdtlsvo : newMailArrivalVO.getContainerDetails()) {
				double manifestedWeight = 0;
				int manifestedBags = 0;
				double receivedWeight = 0;
				int receivedBags = 0;
				Collection<DSNVO> dSNVOs = new ArrayList<DSNVO>();
				dSNVOs = containerdtlsvo.getDsnVOs();
				if (dSNVOs != null && dSNVOs.size() > 0) {
					for (DSNVO dsnvo : dSNVOs) {
						manifestedWeight = manifestedWeight + dsnvo.getWeight().getRoundedSystemValue();
						manifestedBags = manifestedBags + dsnvo.getBags();
						receivedWeight = receivedWeight + dsnvo.getReceivedWeight().getRoundedSystemValue();
						receivedBags = receivedBags + dsnvo.getReceivedBags();
					}
				}
				containerdtlsvo.setTotalBags(manifestedBags);
				containerdtlsvo.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, manifestedWeight));
				containerdtlsvo.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, receivedWeight));
			}
		}
		
		
		LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		String date = currentDate.toDisplayDateOnlyFormat();
		String time = currentDate.toDisplayFormat("HH:mm");
		/**
		 * scan date and time set to form skipped 
		 */
    	
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected){
			containerDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
			containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
			containerDetailsVO.setUndoArrivalFlag(CON);
			for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
				if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag())){
					if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())){
						mailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
					}
  				}

			}
		}
		
		mailArrivalVO.setContainerDetails(containerDetailsVosSelected);

		log.log(Log.FINE, "Going To Save ...in command...UndoArrival...",
				mailArrivalVO);
		mailArrivalVO.setChangeFlightFlag(MailConstantsVO.FLAG_NO);

		mailArrivalVOs.add(mailArrivalVO);
		
		/**
		 * Save container Details to new flight begins
		 */
		
		String scanDate = new StringBuilder().append(date).append(" ")
				.append(time).append(":00").toString();
		LocalDate scanDat = 
				new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
		scanDat.setDateAndTime(scanDate);
		boolean isContainerPresent = false;
		ArrayList<ContainerDetailsVO> newContainerDetailsVOs = null;
		newContainerDetailsVOs = (ArrayList<ContainerDetailsVO>) newMailArrivalVO.getContainerDetails();
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		
		for(ContainerDetailsVO containerDetailsVO:containerDetailsVosSelected){
			ContainerDetailsVO newContainerVO = new ContainerDetailsVO();
			if(newContainerDetailsVOs!=null&&newContainerDetailsVOs.size()>0){
				for(ContainerDetailsVO newContainerDetailsVO:newContainerDetailsVOs){
	  					String CONT1=new StringBuilder(newContainerDetailsVO.getContainerNumber())
	  							.append(newContainerDetailsVO.getContainerType()).toString();
						String CONT2=new StringBuilder(containerDetailsVO.getContainerNumber())
								.append(containerDetailsVO.getContainerType()).toString();
						if(CONT1.equals(CONT2)){
							int newContainerVal=newContainerDetailsVOs.indexOf(newContainerDetailsVO);
							isContainerPresent=true;
							try{
			  					BeanHelper.copyProperties(newContainerVO, containerDetailsVO);
			  					}catch(SystemException e){
			  						 e.getMessage();
			  					}
								newContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
			  					newContainerVO.setFlightNumber(flightValidationVO.getFlightNumber());
			  					newContainerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			  					newContainerVO.setFlightDate(flightValidationVO.getFlightDate());
			  					newContainerVO.setFlightStatus(flightValidationVO.getFlightStatus());
			  					newContainerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			  					newContainerVO.setCarrierCode(flightValidationVO.getCarrierCode());
			  					newContainerVO.setScanDate(scanDat);
								newContainerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
	  							newContainerVO.setTotalBags(newContainerDetailsVO.getTotalBags());
	  							newContainerVO.setTotalWeight(newContainerDetailsVO.getTotalWeight());
								newContainerVO.setUndoArrivalFlag(NOT);
								newContainerVO.setRemarks(changeContainerDetails.getRemarks());
				  				ArrayList<DSNVO> oldDsnVos = (ArrayList<DSNVO>)containerDetailsVO.getDsnVOs();
				  				ArrayList<DSNVO> newDsnVos = (ArrayList<DSNVO>)newContainerDetailsVO.getDsnVOs();
	  				  			Collection<MailbagVO>oldMailbagVOs=containerDetailsVO.getMailDetails();
	  			  				Collection<MailbagVO>newMailbagVOs=new ArrayList<MailbagVO>();
	  			  				for(MailbagVO oldMailbagVO:oldMailbagVOs){
		  			  				if(MailConstantsVO.FLAG_YES.equals(oldMailbagVO.getArrivedFlag())){
		  			  					MailbagVO newMailbagVO=new MailbagVO();
		  								try{
		  									BeanHelper.copyProperties(newMailbagVO, oldMailbagVO);
		  									}catch(SystemException e){
		  										 e.getMessage();
		  									}
		  								newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
	  	  								newMailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
	  	  								newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_FLAG_INSERT);
	  	  								newMailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_NO);
	  	  								newMailbagVO.setScannedDate(scanDat);
	  	  								newMailbagVO.setCarrierCode(flightValidationVO.getCarrierCode());
	  	  								newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
	  	  						    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
	  	  								newMailbagVO.setContainerNumber(newContainerDetailsVO.getContainerNumber());
	  	  								newMailbagVO.setContainerType(newContainerDetailsVO.getContainerType());
	  	  						    	newMailbagVO.setSegmentSerialNumber(newContainerDetailsVO.getSegmentSerialNumber());
	  	  						    	newMailbagVO.setUldNumber(newContainerDetailsVO.getContainerNumber());
	  	  								newMailbagVO.setFlightDate(flightValidationVO.getFlightDate());
	  	  								newMailbagVO.setFlightNumber(flightValidationVO.getFlightNumber());
	  	  								newMailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	  	  								newMailbagVO.setScannedUser(logonAttributes.getUserId());
	  	  								newMailbagVO.setPou(newContainerDetailsVO.getPou());
	  	  								newMailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
	  	  								newMailbagVO.setMailbagDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
	  	  								newMailbagVOs.add(newMailbagVO);
	  	  								
		  	  							for(DSNVO oldDsnVO:oldDsnVos){
		  	  				  				String selectedDSN=new StringBuilder(oldDsnVO.getOriginExchangeOffice()).
		  	  				  						append(oldDsnVO.getDestinationExchangeOffice()).append(oldDsnVO.getMailCategoryCode()).append(oldDsnVO.getMailSubclass()).append(oldDsnVO.getYear()).append(oldDsnVO.getDsn()).toString();
		  	  								String selectedMail=new StringBuilder(oldMailbagVO.getOoe()).
		  	  										append(oldMailbagVO.getDoe()).append(oldMailbagVO.getMailCategoryCode()).append(oldMailbagVO.getMailSubclass()).append(oldMailbagVO.getYear()).append(oldMailbagVO.getDespatchSerialNumber()).toString();
		  	  								if(selectedDSN.equals(selectedMail)){
		  	  				  					DSNVO newDsnVO=new DSNVO();
		  	  									try{
		  	  										BeanHelper.copyProperties(newDsnVO, oldDsnVO);
		  	  										}catch(SystemException e){
		  	  											 e.getMessage();
		  	  										}
		  	  									if(newDsnVos!=null&&newDsnVos.size()>0){
			  	  									for(DSNVO dsn:newDsnVos ){
			  	  										String newDsnpk = dsn.getOriginExchangeOffice()
			  	  											   +dsn.getDestinationExchangeOffice()
			  	  											   +dsn.getMailCategoryCode()
			  	  											   +dsn.getMailSubclass()
			  	  									           +dsn.getDsn()+dsn.getYear();
			  	  										String oldDsnpk = oldDsnVO.getOriginExchangeOffice()
			  	   											   +oldDsnVO.getDestinationExchangeOffice()
			  	   											   +oldDsnVO.getMailCategoryCode()
			  	   											   +oldDsnVO.getMailSubclass()
			  	   									           +oldDsnVO.getDsn()+dsn.getYear();
			
			  	  										if(oldDsnpk.equals(newDsnpk)){
			  	  											newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
			  	  											newDsnVO.setPrevReceivedWeight(new Measure(UnitConstants.MAIL_WGT, 0.0));
			  	  											newDsnVO.setPrevReceivedBags(0);
			  	  										}else{
			  	  											newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
			  	  										}
			  	  									}
		  	  									}else{
		  	  										newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		  	  									}
		  	  				  					newDsnVO.setReceivedDate(scanDat);
		  	  				  					newDsnVO.setFlightNumber(flightValidationVO.getFlightNumber());
		  	  				  					newDsnVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		  	  				  					newDsnVO.setDestination(flightValidationVO.getLegDestination());
		  	  				  					newDsnVO.setCarrierCode(flightValidationVO.getCarrierCode());
		  	  				  					newDsnVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		  	  				  					newDsnVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		  	  				  					newDsnVO.setPol(flightValidationVO.getLegOrigin());
		  	  				  					newDsnVO.setPou(flightValidationVO.getLegDestination());
		  	  				  					newDSNVOs.add(newDsnVO);
		  	  				  				}
		  	  				  			}
		  			  				}
	  			  				
	  			  				}
	  			  				
	  			  			newContainerVO.setDsnVOs(newDSNVOs);
  			  				newContainerVO.setMailDetails(newMailbagVOs);
  			  				newContainerDetailsVOs.set(newContainerVal, newContainerVO);
						}
				}
			}
			
			//If the container is not present in the flight,then opflag is I
				if(!isContainerPresent||newContainerDetailsVOs.size()==0){
					ArrayList<DSNVO> oldDsnVos = (ArrayList<DSNVO>)containerDetailsVO.getDsnVOs();
					Collection<MailbagVO>oldMailbagVOs=containerDetailsVO.getMailDetails();
	  				try{
	  					BeanHelper.copyProperties(newContainerVO, containerDetailsVO);
	  					}catch(SystemException e){
	  						 e.getMessage();
	  					}
	  				
	  				newContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
  					newContainerVO.setFlightNumber(flightValidationVO.getFlightNumber());
  					newContainerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
  					newContainerVO.setFlightDate(flightValidationVO.getFlightDate());
  					newContainerVO.setFlightStatus(flightValidationVO.getFlightStatus());
  					newContainerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
  					newContainerVO.setCarrierCode(containerDetailsVO.getCarrierCode());
  					newContainerVO.setScanDate(scanDat);
  					newContainerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
  					newContainerVO.setUndoArrivalFlag("NOT");
					newContainerVO.setTotalBags(0);
					newContainerVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, 0.0));
  					newContainerVO.setRemarks(changeContainerDetails.getRemarks());
  					
  					
  					Collection<MailbagVO>newMailbagVOs=new ArrayList<MailbagVO>();
  	  				for(MailbagVO oldMailbagVO:oldMailbagVOs){
  	  					if(MailConstantsVO.FLAG_YES.equals(oldMailbagVO.getArrivedFlag())){
	  	  					MailbagVO newMailbagVO=new MailbagVO();
	  						try{
	  							BeanHelper.copyProperties(newMailbagVO, oldMailbagVO);
	  							}catch(SystemException e){
	  								 e.getMessage();
	  							}
	  						
	  						newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
							newMailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
							newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_FLAG_INSERT);
							newMailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_NO);
							newMailbagVO.setScannedDate(scanDat);
					    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
					    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
					    	newMailbagVO.setCarrierCode(flightValidationVO.getCarrierCode());
							newMailbagVO.setContainerNumber(newContainerVO.getContainerNumber());
							newMailbagVO.setContainerType(newContainerVO.getContainerType());
					    	newMailbagVO.setUldNumber(newContainerVO.getContainerNumber());
							newMailbagVO.setFlightDate(flightValidationVO.getFlightDate());
							newMailbagVO.setFlightNumber(flightValidationVO.getFlightNumber());
							newMailbagVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
							newMailbagVO.setScannedUser(logonAttributes.getUserId());
							newMailbagVO.setPou(newContainerVO.getPou());
							newMailbagVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
							newMailbagVO.setMailbagDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
							newMailbagVOs.add(newMailbagVO);
							
							for(DSNVO oldDsnVO:oldDsnVos){
			  					String selectedDSN=new StringBuilder(oldDsnVO.getOriginExchangeOffice()).append(oldDsnVO.getDestinationExchangeOffice()).append(oldDsnVO.getMailCategoryCode()).append(oldDsnVO.getMailSubclass()).append(oldDsnVO.getYear()).append(oldDsnVO.getDsn()).toString();
								String selectedMail=new StringBuilder(oldMailbagVO.getOoe()).append(oldMailbagVO.getDoe()).append(oldMailbagVO.getMailCategoryCode()).append(oldMailbagVO.getMailSubclass()).append(oldMailbagVO.getYear()).append(oldMailbagVO.getDespatchSerialNumber()).toString();
								if(selectedDSN.equals(selectedMail)){
			  					DSNVO newDsnVO=new DSNVO();
								try{
									BeanHelper.copyProperties(newDsnVO, oldDsnVO);
									}catch(SystemException e){
										 e.getMessage();
									}
								newDsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
			  					newDsnVO.setReceivedDate(scanDat);
			  					newDsnVO.setFlightNumber(flightValidationVO.getFlightNumber());
			  					newDsnVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			  					newDsnVO.setDestination(flightValidationVO.getLegDestination());
			  					newDsnVO.setCarrierCode(flightValidationVO.getCarrierCode());
			  					newDsnVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			  					newDsnVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			  					newDsnVO.setPol(flightValidationVO.getLegOrigin());
			  					newDsnVO.setPou(flightValidationVO.getLegDestination());
			  					newDSNVOs.add(newDsnVO);
			  					}
			  				}
  	  					}
  	  				}
  	  				
  	  			newContainerVO.setDsnVOs(newDSNVOs);
  				newContainerVO.setMailDetails(newMailbagVOs);
  				newContainerDetailsVOs.add(newContainerVO);
				}
		}
		
		newContainerDetailsVOs = makeDSNVOs(newContainerDetailsVOs,logonAttributes);
		newMailArrivalVO.setContainerDetails(newContainerDetailsVOs);
		
		newMailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		newMailArrivalVO.setFlightCarrierCode(flightValidationVO.getCarrierCode());
		newMailArrivalVO.setFlightNumber(flightValidationVO.getFlightNumber());
		newMailArrivalVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		newMailArrivalVO.setArrivalDate(flightValidationVO.getFlightDate());
		newMailArrivalVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		newMailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		newMailArrivalVO.setArrivalDate(flightValidationVO.getApplicableDateAtRequestedAirport());
		newMailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		newMailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		newMailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		newMailArrivalVO.setCompanyCode(flightValidationVO.getCompanyCode());
		newMailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		newMailArrivalVO.setPol(flightValidationVO.getLegOrigin());
		newMailArrivalVO.setPou(flightValidationVO.getLegDestination());
		newMailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
		newMailArrivalVO.setScanDate(scanDat);
		newMailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		newMailArrivalVO.setChangeFlightFlag(MailConstantsVO.FLAG_YES);
		newMailArrivalVO.setMailSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
		newMailArrivalVO.setMailDataSource(MailConstantsVO.MAIL_STATUS_ARRIVED);
		
		Collection<LockVO> locks = prepareLocksForSave(newMailArrivalVO);
  		log.log(Log.FINE, "LockVO for implicit check", locks);
  		if (locks == null || locks.size() == 0) {
  			locks = null;
  		}
  		
  		mailArrivalVOs.add(newMailArrivalVO);

		  try {
		    new MailTrackingDefaultsDelegate().saveChangeFlightDetails(mailArrivalVOs,locks);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
		  
		  if (errors != null && errors.size() > 0) {
				for(ErrorVO err : errors){
					if(ERROR_TRANSFERRED_OR_DELIVERED.equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO(CONTAINER_TRANSFERRED_OR_DELIVERED);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
					}else{
						actionContext.addAllError((List<ErrorVO>) errors);
					}
				}
	    		return;
    	  }
		  
		  
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		mailinboundModel.setMailArrivalVO(mailArrivalVO);
		result.add(mailinboundModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		ErrorVO error = new ErrorVO("mail.operations.succ.changeflightsuccess");      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error); 
		actionContext.setResponseVO(responseVO);  				
	  			  				
		}
		
		
		
    	
    	
			  
			  
		
	private Collection<LockVO> prepareLocksForSave(MailArrivalVO mailArrivalVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", mailArrivalVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<LockVO> locks = new ArrayList<LockVO>();
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();

    	if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
    		for (ContainerDetailsVO conVO : containerDetailsVOs) {
    			if(conVO.getOperationFlag()!=null && conVO.getOperationFlag().trim().length()>0){
    				ArrayList<MailbagVO> mailbagvos=new ArrayList<MailbagVO>(conVO.getMailDetails());
    				if(mailbagvos!=null && mailbagvos.size()>0){
    				for(MailbagVO bagvo:mailbagvos){
    					if(bagvo.getOperationalFlag()!=null && bagvo.getOperationalFlag().trim().length()>0){
    						ULDLockVO lock = new ULDLockVO();
    		    			lock.setAction(LockConstants.ACTION_MAILARRIVAL);
    		    			lock.setClientType(ClientType.WEB);
    		    			lock.setCompanyCode(logonAttributes.getCompanyCode());
    		    			lock.setScreenId(SCREEN_ID);
    		    			lock.setStationCode(logonAttributes.getStationCode());
    		    			if(bagvo.getContainerForInventory() != null){
    		    				lock.setUldNumber(bagvo.getContainerForInventory());
    		    				lock.setDescription(bagvo.getContainerForInventory());
    		    				lock.setRemarks(bagvo.getContainerForInventory());
    		    			log.log(Log.FINE, "\n lock------->>", lock);
							locks.add(lock);
    					    }
    					}

    					}
    			}

    		}
    	}

    }
    	return locks;
	}








	private ArrayList<ContainerDetailsVO> makeDSNVOs(ArrayList<ContainerDetailsVO> newContainerDetailsVOs,
			LogonAttributes logonAttributes) {
		
		
		if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){

				HashMap<String,DSNVO> dsnMapDespatch = new HashMap<String,DSNVO>();
				HashMap<String,String> despatchMap = new HashMap<String,String>();
				Collection<DSNVO> mainDSNVOs = popupVO.getDsnVOs();
				if(mainDSNVOs != null && mainDSNVOs.size() > 0){
					for(DSNVO dsnVO:mainDSNVOs){
						if(!MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
							if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							dsnMapDespatch.put(dsnpk,dsnVO);
							}
						}
				    }
				}

				int rcvdBags = 0;
				double rcvdWgt = 0;
				int delvdBags = 0;
				double delvdWgt = 0;
				Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO.getDesptachDetailsVOs();
				 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
					for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
						String outerpk = despatchVO.getOriginOfficeOfExchange()
						           +despatchVO.getDestinationOfficeOfExchange()
						           +despatchVO.getMailCategoryCode()
						           +despatchVO.getMailSubclass()
						           +despatchVO.getDsn()
						           +despatchVO.getYear();

						if(despatchMap.get(outerpk) == null){
						if(dsnMapDespatch.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(despatchVO.getDsn());
							dsnVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
							dsnVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
							dsnVO.setMailClass(despatchVO.getMailClass());
							dsnVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
							dsnVO.setMailSubclass(despatchVO.getMailSubclass());
							dsnVO.setYear(despatchVO.getYear());
							dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_NO);
							dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						for(DespatchDetailsVO innerVO:despatchDetailsVOs){
							String innerpk = innerVO.getOriginOfficeOfExchange()
					           +innerVO.getDestinationOfficeOfExchange()
					           +innerVO.getMailCategoryCode()
					           +innerVO.getMailSubclass()
					           +innerVO.getDsn()
					           +innerVO.getYear();
							if(outerpk.equals(innerpk)){
								rcvdBags = rcvdBags + innerVO.getReceivedBags();
								rcvdWgt = rcvdWgt + innerVO.getReceivedWeight().getRoundedSystemValue();
								delvdBags = delvdBags + innerVO.getDeliveredBags();
								delvdWgt = delvdWgt + innerVO.getDeliveredWeight().getRoundedSystemValue();
							}
						}
						dsnVO.setReceivedBags(rcvdBags);
						dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvdWgt)); 
						dsnVO.setDeliveredBags(delvdBags);
						dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, delvdWgt));
						dsnMapDespatch.put(outerpk,dsnVO);

						}else{
							DSNVO dsnVO = dsnMapDespatch.get(outerpk);
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							if(despatchDetailsVOs.size() > 0){
								for(DespatchDetailsVO dsptchVO:despatchDetailsVOs){
								String despatchpk = dsptchVO.getOriginOfficeOfExchange()
								           +dsptchVO.getDestinationOfficeOfExchange()
								           +dsptchVO.getMailCategoryCode()
								           +dsptchVO.getMailSubclass()
								           +dsptchVO.getDsn()
								           +dsptchVO.getYear();
									    if(dsnpk.equals(despatchpk)){
									    	if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsptchVO.getOperationalFlag())){
									    		dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									    	}
											rcvdBags = rcvdBags + despatchVO.getReceivedBags();
											rcvdWgt = rcvdWgt + despatchVO.getReceivedWeight().getRoundedSystemValue();
											delvdBags = delvdBags + despatchVO.getDeliveredBags();
											delvdWgt = delvdWgt + despatchVO.getDeliveredWeight().getRoundedSystemValue();
										}
								}

								if(dsnVO.getReceivedBags()!= rcvdBags
											|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= rcvdWgt
									|| dsnVO.getDeliveredBags()!= delvdBags
											|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= delvdWgt){
									if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
										dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									}
								}
								dsnVO.setReceivedBags(rcvdBags);
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvdWgt)); 
								dsnVO.setDeliveredBags(delvdBags);
								dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, delvdWgt));
								dsnMapDespatch.put(outerpk,dsnVO);
							}
						  }
						despatchMap.put(outerpk,outerpk);
						}
						rcvdBags = 0;
						rcvdWgt = 0;
						delvdBags = 0;
						delvdWgt = 0;
						}
					}


				 /**
				  * For Mail Bag Details
				  */

				 HashMap<String,String> mailMap = new HashMap<String,String>();
				 HashMap<String,DSNVO> dsnMapMailbag = new HashMap<String,DSNVO>();
					if(mainDSNVOs != null && mainDSNVOs.size() > 0){
						for(DSNVO dsnVO:mainDSNVOs){
							if(MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())){
								if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO.getOperationFlag())){
								String dsnpk = dsnVO.getOriginExchangeOffice()
						           +dsnVO.getDestinationExchangeOffice()
						           +dsnVO.getMailCategoryCode()
						           +dsnVO.getMailSubclass()
						           +dsnVO.getDsn()
						           +dsnVO.getYear();
								dsnMapMailbag.put(dsnpk,dsnVO);
								}
							}
					    }
					}

				int numBags = 0;
				double bagWgt = 0;
				int dlvBags = 0;
				double dlvWgt = 0;
				 Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
				 if(mailbagVOs != null && mailbagVOs.size() > 0){
					for(MailbagVO mailbagVO:mailbagVOs){

						String outerpk = mailbagVO.getOoe()+mailbagVO.getDoe()
								   + mailbagVO.getMailCategoryCode()
						           + mailbagVO.getMailSubclass()
						           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
						if(mailMap.get(outerpk) == null){
						if(dsnMapMailbag.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
							dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						for(MailbagVO innerVO:mailbagVOs){
							String innerpk = innerVO.getOoe()+innerVO.getDoe()
							+ innerVO.getMailCategoryCode()
					           +(innerVO.getMailSubclass())
					           +innerVO.getDespatchSerialNumber()+innerVO.getYear();
							if(outerpk.equals(innerpk)){
								if(MailConstantsVO.FLAG_YES.equals(innerVO.getArrivedFlag())){
									dsnVO.setReceivedBags(numBags + 1);
									try {
										dsnVO.setReceivedWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,bagWgt ), innerVO.getWeight()));
									} catch (UnitException e) {
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}
									
									numBags= dsnVO.getReceivedBags();
									bagWgt = dsnVO.getReceivedWeight().getRoundedSystemValue();
								}
								if(MailConstantsVO.FLAG_YES.equals(innerVO.getDeliveredFlag())){
									dsnVO.setDeliveredBags(dlvBags + 1);
									try {
										dsnVO.setDeliveredWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,dlvWgt), innerVO.getWeight()));
									} catch (UnitException e) {
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}
									
									dlvBags = dsnVO.getDeliveredBags();
									dlvWgt = dsnVO.getDeliveredWeight().getRoundedSystemValue(); 

								}
							}
						}
						dsnMapMailbag.put(outerpk,dsnVO);
						}else{
							DSNVO dsnVO = dsnMapMailbag.get(outerpk);
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							if(mailbagVOs.size() > 0){
								for(MailbagVO mbagVO:mailbagVOs){
								String mailpk = mbagVO.getOoe()+mbagVO.getDoe()
								   + mbagVO.getMailCategoryCode()
						           + mbagVO.getMailSubclass()
						           + mbagVO.getDespatchSerialNumber()+mbagVO.getYear();
									    if(dsnpk.equals(mailpk)){
									    	if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mbagVO.getOperationalFlag())
									    			|| MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mbagVO.getOperationalFlag())){
									    		dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									    	}
											if(MailConstantsVO.FLAG_YES.equals(mbagVO.getArrivedFlag()) ){
												numBags = numBags + 1;
												if(mbagVO.getWeight()!=null){
												bagWgt = bagWgt + mbagVO.getWeight().getRoundedSystemValue();
												}
											}
											if(MailConstantsVO.FLAG_YES.equals(mbagVO.getDamageFlag())){
												dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
										    }
											if(MailConstantsVO.FLAG_YES.equals(mbagVO.getDeliveredFlag())){
												dlvBags = dlvBags + 1;
												if(mbagVO.getWeight()!=null){
												dlvWgt = dlvWgt + mbagVO.getWeight().getRoundedSystemValue();
												}
											}
										}
								}
								if(dsnVO.getReceivedBags()!= numBags
											|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= bagWgt){
										dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								}
								dsnVO.setReceivedBags(numBags);
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, bagWgt));
								if(dsnVO.getDeliveredBags()!= dlvBags
											|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= dlvWgt){
											dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									}
								dsnVO.setDeliveredBags(dlvBags);
								dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, dlvWgt)); 
								dsnMapMailbag.put(outerpk,dsnVO);
							}
						  }
						mailMap.put(outerpk,outerpk);
						}
						numBags = 0;
						bagWgt = 0;
						dlvBags = 0;
						dlvWgt = 0;
					}
				}

				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for(String key:dsnMapDespatch.keySet()){
					DSNVO dsnVO = dsnMapDespatch.get(key);
					newDSNVOs.add(dsnVO);
				}
				for(String key:dsnMapMailbag.keySet()){
					DSNVO dsnVO = dsnMapMailbag.get(key);
					if(newDSNVOs.size() == 0){
					   newDSNVOs = new ArrayList<DSNVO>();
					}
					newDSNVOs.add(dsnVO);
				}

				Collection<DSNVO> oldDSNVOs = popupVO.getDsnVOs();
				int rcvedBags = 0;
				double rcvedWgt = 0;
				if(newDSNVOs.size() > 0){
					for(DSNVO dsnVO1:newDSNVOs){
						String outerpk = dsnVO1.getOriginExchangeOffice()
						   +dsnVO1.getDestinationExchangeOffice()
						   +dsnVO1.getMailCategoryCode()
						   +dsnVO1.getMailSubclass()
				           +dsnVO1.getDsn()+dsnVO1.getYear();
						int flag = 0;
						if(oldDSNVOs != null && oldDSNVOs.size() > 0){
							for(DSNVO dsnVO2:oldDSNVOs){
								String innerpk = dsnVO2.getOriginExchangeOffice()
								   +dsnVO2.getDestinationExchangeOffice()
								   +dsnVO2.getMailCategoryCode()
								   +dsnVO2.getMailSubclass()
						           +dsnVO2.getDsn()+dsnVO2.getYear();
								if(outerpk.equals(innerpk)){
									if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(dsnVO2.getOperationFlag())){
										dsnVO1.setPrevBagCount(dsnVO2.getPrevBagCount());
										dsnVO1.setPrevBagWeight(dsnVO2.getPrevBagWeight());
                                        dsnVO1.setPrevReceivedBags(dsnVO2.getPrevReceivedBags());
                                        dsnVO1.setPrevReceivedWeight(dsnVO2.getPrevReceivedWeight());
                                        dsnVO1.setPrevDeliveredBags(dsnVO2.getPrevDeliveredBags());
                                        dsnVO1.setPrevDeliveredWeight(dsnVO2.getPrevDeliveredWeight());
									}
									flag = 1;
								}
							}
						}
						if(flag == 1){
							flag = 0;
						}
						rcvedBags = rcvedBags + dsnVO1.getReceivedBags();
						rcvedWgt = rcvedWgt + dsnVO1.getReceivedWeight().getRoundedSystemValue();
					}
				}
				popupVO.setReceivedBags(rcvedBags);
				popupVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvedWgt));
				popupVO.setDsnVOs(newDSNVOs);
			}
    	 }

    	return newContainerDetailsVOs;
	}



	private MailArrivalFilterVO constructMailArrivalFilterVO(OperationalFlightVO operationalFlightVO) {
		
		MailArrivalFilterVO filterVO = new MailArrivalFilterVO();
		filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		filterVO.setCarrierId(operationalFlightVO.getCarrierId());
		filterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		filterVO.setFlightSequenceNumber(
				operationalFlightVO.getFlightSequenceNumber());
		filterVO.setFlightDate(operationalFlightVO.getFlightDate());
		filterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		filterVO.setPou(operationalFlightVO.getPou());
		filterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
		return filterVO;
	}

	private String findOneTimeDescription(Collection<OneTimeVO> resultStatus, String legStatus) {
		if (resultStatus != null) {
			for (OneTimeVO oneTimeVO:resultStatus){
				if(legStatus.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}

	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		
		Collection<ErrorVO> errors = null;
		
		try{
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(FLTLEGSTATUS);
			fieldValues.add(FLTSTATUS);
			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	private FlightFilterVO handleFlightFilterVO(ChangeContainerDetails changeContainerDetails,
			LogonAttributes logonAttributes) {
		
		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(INBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setStringFlightDate(changeContainerDetails.getDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(changeContainerDetails.getDate()));
		return flightFilterVO;
	}

	private Collection<ErrorVO> validateFormFlight(ChangeContainerDetails changeContainerDetails) {
		
		String flightCarrierCode = changeContainerDetails.getFlightCarrierCode();
		String flightNumber = changeContainerDetails.getFlightNumber();
		String depDate = changeContainerDetails.getDate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))){
			errors.add(new ErrorVO(FLIGHT_CARRIER_CODE_EMPTY));
		}
		if(flightNumber == null || ("".equals(flightNumber.trim()))){
			errors.add(new ErrorVO(FLIGHT_NUMBER_EMPTY));
		}
		if(depDate == null || ("".equals(depDate.trim()))){
			errors.add(new ErrorVO(DEPDATE_EMPTY));
		}
		return errors;
		
	}

}
