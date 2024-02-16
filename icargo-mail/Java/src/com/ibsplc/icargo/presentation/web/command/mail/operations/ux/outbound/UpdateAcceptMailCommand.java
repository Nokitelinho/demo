package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class UpdateAcceptMailCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	 private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	 private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	 private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";

	 private static final String OUTBOUND = "O";
	 private static final String INT_REGEX = "[0-9]+";
     private static final String SPECIAL_CHARACTER_REMOVE_REGEX="[A-Za-z0-9]+";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
	log.entering("UpdateAcceptMailCommand ","execute");
	List<ErrorVO> errorVOs = null;
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	LogonAttributes logonAttributes =  getLogonAttribute();;
	Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
	Collection<ContainerDetailsVO> containerDetVO = new ArrayList<ContainerDetailsVO>();
	ArrayList<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
	ArrayList<DSNVO> dsnVos = new ArrayList<DSNVO>();
	ContainerDetailsVO newContainerVO = null;
	ContainerDetailsVO containerDetailsVO = null;
	ContainerDetails containerDetails = null;
	//ContainerDetailsVO containerDetailsVO=null;
	AreaDelegate areaDelegate = new AreaDelegate();
 	Map stationParameters = null; 
 	String stationCode = logonAttributes.getStationCode();
	String companyCode=logonAttributes.getCompanyCode();
	try {
		stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
	} catch (BusinessDelegateException e1) {
		e1.getMessage();
	}
	OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	containerDetails=outboundModel.getSelectedContainer();
	containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,logonAttributes);
	containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	containerDetailsVO.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	Collection<MailbagVO> mailbagVOsInContainer = new ArrayList<MailbagVO>();
	MailAcceptance mailAcceptance = outboundModel.getMailAcceptance();
	mailAcceptance.setShowWarning(outboundModel.getShowWarning());
	MailAcceptanceVO mailAcceptanceVO = MailOutboundModelConverter.constructMailAcceptanceVO(mailAcceptance, logonAttributes);
	List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
	if(outboundModel.getNewContainerInfo() !=null ) {
		ContainerDetails newContainer = outboundModel.getNewContainerInfo();
		
		
		//Validate ULD Type- Aircraft Compatibility starts
		if(newContainer!=null && "F".equals(outboundModel.getFlightCarrierflag()) && MailConstantsVO.ULD_TYPE.equals(newContainer.getType())){
		FlightValidationVO flightValidationVO = findFlightValidationVO(mailAcceptance,logonAttributes);
		validateULDIncomatibility(newContainer,flightValidationVO,actionContext);
		}
		//validateULDIncomatibility
		
		//Validate ULD Type- Aircraft Compatibility ends
		//Warehouse

			if ((newContainer.getDestination() == null || newContainer.getDestination().isEmpty())) {
				actionContext.addError(new ErrorVO("mailtracking.defaults.destn.empty"));
				return;
			} else if (("F".equals(outboundModel.getFlightCarrierflag()) && ("B".equals(newContainer.getType()))
					&& !(newContainer.getDestination().equals(newContainer.getPou())))) {
				actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destnandpouisnotsame"));
				return;
			} else if (newContainer.getDestination().equals(logonAttributes.getAirportCode())) {
				actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destn.currentairport"));
				return;
			} else {
				String destination = newContainer.getDestination();
				int errorFlag = 0;
				if (destination != null && !destination.isEmpty()) {
					try {
						areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(), destination.toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {
						errorFlag = 1;
						errors = handleDelegateException(businessDelegateException);
					}
					if (errors != null && !errors.isEmpty() && errorFlag == 1) {
						Object[] obj = { destination.toUpperCase() };
						actionContext.addError(new ErrorVO("mailtracking.defaults.invalidairport", obj));
						return;
					}
				}
			}

 		if(newContainer.getWareHouse() != null){
			if(!newContainer.getWareHouse().equals(containerDetailsVO.getWareHouse())){
				containerDetailsVO.setWareHouse(newContainer.getWareHouse());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
 		if(newContainer.getContainerNumber() != null){
 			containerDetailsVO.setContainerNumber(newContainer.getContainerNumber());
 		}
		//Location
		if(newContainer.getLocation() != null){
			if(!newContainer.getLocation().equals(containerDetailsVO.getLocation())){
				containerDetailsVO.setLocation(newContainer.getLocation());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
		//Remarks
		if(newContainer.getRemarks() != null){
			if(!newContainer.getRemarks().equals(containerDetailsVO.getRemarks())){
				containerDetailsVO.setRemarks(newContainer.getRemarks());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				} 
			}
		} 
		  
		//Container Type
		if(newContainer.getType() != null){
			if(!newContainer.getType().equals(containerDetailsVO.getContainerType())){
				containerDetailsVO.setContainerType(newContainer.getType());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
		//POU
		if(newContainer.getPou() != null){
			if(!newContainer.getPou().equals(containerDetailsVO.getPou())){
				containerDetailsVO.setPou(newContainer.getPou());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
		//Destination
		
		if(newContainer.getDestination() != null){ 
			if(!newContainer.getDestination().equals(containerDetailsVO.getDestination())){
				containerDetailsVO.setDestination(newContainer.getDestination());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
		//PaBuilt Flag
		if(newContainer.getPaBuiltFlag() != null){
			if(!newContainer.getPaBuiltFlag().equals(containerDetailsVO.getPaBuiltFlag())){
				containerDetailsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
		
		//Container Jny IDR Flag
		if(newContainer.getContainerJnyId() != null){
			if(!newContainer.getContainerJnyId().equals(containerDetailsVO.getContainerJnyId())){
				containerDetailsVO.setContainerJnyId(newContainer.getContainerJnyId());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
		//PA CODE
		if(newContainer.getPaCode() != null){
			if(!newContainer.getPaCode().equals(containerDetailsVO.getPaCode())){
				containerDetailsVO.setPaCode(newContainer.getPaCode());
				if(!"I".equals(containerDetails.getContainerOperationFlag())
						&& !"N".equals(containerDetails.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		
		
		//Transfer From Carrier
		if(newContainer.getTransferFromCarrier()!= null){
			if(!newContainer.getTransferFromCarrier().equals(containerDetailsVO.getTransferFromCarrier())){
				containerDetailsVO.setTransferFromCarrier(newContainer.getTransferFromCarrier());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
					containerDetailsVO.setOperationFlag("U");
				}
			}
		}
		 //validate Location - Warehouse
    	String location = newContainer.getLocation();
    	if (location != null && location.trim().length() > 0) {
    	log.log(Log.FINE, "Going To validate Location - Warehouse ...in command");
    	LocationValidationVO locationValidationVO = new LocationValidationVO();
		  try {
			  locationValidationVO = new MailTrackingDefaultsDelegate().validateLocation(
					logonAttributes.getCompanyCode(),logonAttributes.getAirportCode(),
					newContainer.getWareHouse(),location.toUpperCase());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (locationValidationVO == null) {
	  		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidlocation"));
	  		return;
	  	  }
    	}
//    	validate PA code
    	String invalidGeneralPACode = "";
    	if(containerDetailsVO.getPaCode()!=null){
    		try {
		  			String paCode = containerDetailsVO.getPaCode().toUpperCase();
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
	  				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
	  								logonAttributes.getCompanyCode(),paCode);
		  			if (postalAdministrationVO == null) {
		  				if("".equals(invalidGeneralPACode)){
		  					invalidGeneralPACode = paCode;
		  				}else{
		  					invalidGeneralPACode = new StringBuilder(invalidGeneralPACode).append(",").append(paCode).toString();
		  				}
		  		}
	  		}catch (BusinessDelegateException businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
    	}
	  	if(!"".equals(invalidGeneralPACode)){
			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{invalidGeneralPACode}));
	  		return;
		}
	  	AirlineValidationVO airlineValidationVO = null;
		if (containerDetailsVO.getTransferFromCarrier() != null && !"".equals(containerDetailsVO.getTransferFromCarrier())) {
			try {
				airlineValidationVO = new AirlineDelegate()
						.validateAlphaCode(
								companyCode,
								newContainer.getTransferFromCarrier());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			catch(Exception e){
				log.log(Log.FINE, e.getMessage());
			}  
 if (airlineValidationVO == null) {
		  		actionContext.addError(new ErrorVO("Invalid from carrier code"));
		  		return;
		 }
		}
//    	validate PA code
    	String invalidPACode = "";
    	Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
	  	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
	  		try {
		  		for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
		  			String paCode = despatchVO.getPaCode().toUpperCase();
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
	  				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
	  								logonAttributes.getCompanyCode(),paCode);
		  			if (postalAdministrationVO == null) {
		  				if("".equals(invalidPACode)){
		  					invalidPACode = paCode;
		  				}else{
		  					invalidPACode = new StringBuilder(invalidPACode).append(",").append(paCode).toString();
		  				}
		  			}
		  		}
	  		}catch (BusinessDelegateException businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  	}
	  	if(!"".equals(invalidPACode)){
			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{invalidPACode}));
	  		return;
		}
	  	String overrideWarningtoNonPAbuilt=null;
		String overrideWarningtoPAbuilt=null;
		Map<String, String> existigWarningMap = outboundModel.getWarningMessagesStatus();
		if(existigWarningMap != null && existigWarningMap.size()>0) {
			overrideWarningtoNonPAbuilt=existigWarningMap.get("mailtracking.defaults.modifycontainer.pabuiltToNonPabuilt");
		}
		if(overrideWarningtoNonPAbuilt == null){  
			overrideWarningtoNonPAbuilt = ContainerVO.FLAG_NO;	
    	}
	  	if((overrideWarningtoNonPAbuilt.equals(ContainerVO.FLAG_NO)) && (newContainer.getPaBuiltFlag()==null || newContainer.getPaBuiltFlag().equals("N")) && (containerDetails.getPaBuiltFlag()!=null && containerDetails.getPaBuiltFlag().equals("Y"))){
			ErrorVO warningError = new ErrorVO(
					"mailtracking.defaults.modifycontainer.pabuiltToNonPabuilt",
					new Object[] { newContainer.getContainerNumber() });
			warningError.setErrorDisplayType(ErrorDisplayType.WARNING_MULTIOPTION);
			warningErrors.add(warningError);
			actionContext.addAllError(warningErrors); 
			return;
	    } 
	  	

	} 


	if(containerDetailsVO.getDestination().equals(logonAttributes.getAirportCode())){
		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destn.currentairport"));
		
	}else{
    	AirportValidationVO airportValidationVO = null;
    	String destination = containerDetailsVO.getDestination();  
    	int errorFlag=0;
    	if (destination != null && !"".equals(destination)) {        		
    		try {        			
    			airportValidationVO = areaDelegate.validateAirportCode(
    					logonAttributes.getCompanyCode(),destination.toUpperCase());
    		}catch (BusinessDelegateException businessDelegateException) {
    			errorFlag=1;
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0
    				&& errorFlag==1) {            			
    			Object[] obj = {destination.toUpperCase()};
    			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
				return;
    		}
    	}
	}
	 
	
	 
	
	 
	
	
	
	
	
					
					 
				
					
				
								
						   
					   
	
	 int existingsize=mailbagVOsInContainer.size();
	
	
		int size = 0;
		   
	Collection<Mailbag> newMailbags = new ArrayList<Mailbag>();
	if(outboundModel.getMailbags()!=null) {
	newMailbags= outboundModel.getMailbags();
	
	Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
	Collection<MailbagVO> deletedMailbagVOs = containerDetailsVO.getDeletedMailDetails();
	if(deletedMailbagVOs==null ){    
		deletedMailbagVOs=new ArrayList<MailbagVO>();     
	}
	size = newMailbags.size();
	int index=0;
	for(Mailbag newMailbag : newMailbags) {
		
		
		if(newMailbag.getLatestStatus()!=null && newMailbag.getLatestStatus().equals("DLV") && newMailbag.getFromPanel()!=null && newMailbag.getFromPanel().equals("LYINGLIST")){
			actionContext.addError(new ErrorVO("mailtracking.defaults.deliveredmailbagscannotbereassigned"));
	  		return;
		}
		if(newMailbag.getFromPanel()!=null && newMailbag.getFromPanel().equals("DEVIATIONPANEL")){
			ErrorVO error  = null;
			try {
				error = new MailTrackingDefaultsDelegate().validateContainerNumberForDeviatedMailbags(
						containerDetailsVO,newMailbag.getMailSequenceNumber());
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if(error!=null){
				actionContext.addError(error);
				return;
			}
		}
		//added as part of IASCB-48541
		/* Commenting below code, scenario: accepted mailbags(no assignment) , found delivery 
		boolean isTransfer = false;
		if(outboundModel.getMailAcceptance()!=null && outboundModel.getMailAcceptance().isFromDeviationList()) {
			if ((!"NEW".equals(newMailbag.getLatestStatus())
					&& !"BKD".equals(newMailbag.getLatestStatus()) && newMailbag.getMailorigin() != null
					&& !newMailbag.getMailorigin().equals(mailAcceptanceVO.getPol())
					&& !new MailTrackingDefaultsDelegate().validateCoterminusairports(newMailbag.getMailorigin(), mailAcceptanceVO.getPol(),
							MailConstantsVO.RESDIT_RECEIVED, newMailbag.getPaCode()))) {
				isTransfer = true;
			}
			
			if("Y".equals(newMailbag.getAccepted()) && !isTransfer) {
				actionContext.addError(new ErrorVO("mailtracking.defaults.err.acceptedortransferedmailbagcannotaccept"));
		  		return;
			}
		} */
		if(("D").equals(newMailbag.getOperationFlag())){
			List<MailbagHistoryVO> mailhistories = new ArrayList<MailbagHistoryVO>();
			mailhistories = (ArrayList<MailbagHistoryVO>) new MailTrackingDefaultsDelegate().findMailbagHistories(logonAttributes.getCompanyCode(), newMailbag.getMailbagId(), newMailbag.getMailSequenceNumber());
			if(mailhistories!=null){
				for(MailbagHistoryVO history : mailhistories){
					if(("ARR").equals(history.getMailStatus())|| ("TRA").equals(history.getMailStatus())|| ("DLV").equals(history.getMailStatus())){
						actionContext.addError(new ErrorVO("mailtracking.defaults.err.mailbagisalreadyarrivedordelivered"));
						return;
					} else if(!logonAttributes.getAirportCode().equals(history.getScannedPort()) && ("ASG".equals(history.getMailStatus()) || "ACP".equals(history.getMailStatus()))) {
						//above condition will handle transfer to own airline case 
						actionContext.addError(new ErrorVO("mailtracking.defaults.err.mailbagisalreadyarrivedordelivered"));
						return;
					}
				}
			}
		}
	}
	//added by A-7815 as part of IASCB-46145
	if(mailAcceptance != null && "F".equals(outboundModel.getFlightCarrierflag())){
	      if (FlightValidationVO.FLT_LEG_STATUS_TBC.equals(mailAcceptance.getFlightStatus())
					|| FlightValidationVO.FLT_STATUS_CANCELLED.equals(mailAcceptance.getFlightStatus())) {
				Object [] obj = {mailAcceptance.getCarrierCode(),mailAcceptance.getFlightNumber(),mailAcceptance.getFlightDate()};
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.err.flightintbcortba",obj);
				actionContext.addError(errorVO);
				return;
			}
		}
	if(outboundModel.getMailAcceptance()!=null && outboundModel.getMailAcceptance().isFromDeviationList() && "F".equals(outboundModel.getFlightCarrierflag())){
		FlightValidationVO flightValidationVO = findFlightValidationVO(mailAcceptance,logonAttributes);
		if(flightValidationVO!=null && (flightValidationVO.getAta()==null || flightValidationVO.getAtd() ==null)) {
			ErrorVO errorVO = new ErrorVO("Flight  ATD/ATA is not captured ");
			actionContext.addError(errorVO); 
			return;
		}
	}
	//if(mailOpFlag != null && mailOpFlag.length > 0) {
	for(Mailbag newMailbag : newMailbags) {
		//if(index >= size){
				MailbagVO newMailbagVO = new MailbagVO();
				    newMailbagVO.setMailRemarks(newMailbag.getMailRemarks());
					newMailbagVO.setCompanyCode(newMailbag.getCompanyCode());
					newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
			    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
			    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
			    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			    	newMailbagVO.setArrivedFlag("N");
			    	newMailbagVO.setDeliveredFlag("N");
			    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
			    	newMailbagVO.setPou(containerDetailsVO.getPou());
					newMailbagVO.setOperationalFlag(newMailbag.getOperationFlag());
					newMailbagVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
					newMailbagVO.setMailCompanyCode(newMailbag.getMailCompanyCode());
					newMailbagVO.setMailSequenceNumber(newMailbag.getMailSequenceNumber());
				//	newMailbagVO.setMailRemarks(containerDetailsVO.getMailRemarks());
					 int errorFlag=1;
					if(newMailbag.getMailorigin()!=null&&newMailbag.getMailorigin().trim().length()>0){
						try {
							areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(), newMailbag.getMailorigin().toUpperCase());
						} catch (BusinessDelegateException businessDelegateException) {
							errorFlag = 1;
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && !errors.isEmpty() && errorFlag == 1) {
							Object[] obj = { newMailbag.getMailorigin().toUpperCase(),newMailbag.getMailbagId() };
							actionContext.addError(new ErrorVO("mail.operation.invalidorigin", obj));
							return;
						}
					}
					if(newMailbag.getMailDestination()!=null&&newMailbag.getMailDestination().trim().length()>0){
						try {
							areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(), newMailbag.getMailDestination().toUpperCase());
						} catch (BusinessDelegateException businessDelegateException) {
							errorFlag = 1;
							errors = handleDelegateException(businessDelegateException);
						}
						if (errors != null && !errors.isEmpty() && errorFlag == 1) {
							Object[] obj = { newMailbag.getMailDestination().toUpperCase(),newMailbag.getMailbagId() };
							actionContext.addError(new ErrorVO("mail.operation.invaliddestination", obj));
							return;
						}
					}
					newMailbagVO.setOrigin(newMailbag.getMailorigin());
					newMailbagVO.setMailOrigin(newMailbag.getMailorigin());
					newMailbagVO.setDestination(newMailbag.getMailDestination());
					newMailbagVO.setMailDestination(newMailbag.getMailDestination());
					newMailbagVO.setAcceptancePostalContainerNumber(newMailbag.getAcceptancePostalContainerNumber());
					newMailbagVO.setPaBuiltFlagUpdate(newMailbag.isPaBuiltFlagUpdate());
					newMailbagVO.setPaContainerNumberUpdate(newMailbag.isPaContainerNumberUpdate());
				//	newMailbagVO.setMailRemarks(containerDetailsVO.getMailRemarks());
					if (newMailbagVO.getMailOrigin()!=null&&newMailbagVO.getMailOrigin().trim().length()>0
						&&newMailbagVO.getMailDestination()!=null
						&&newMailbagVO.getMailDestination().trim().length()>0&&
						newMailbagVO.getMailOrigin().equals(newMailbagVO.getMailDestination())) {
						actionContext.addError(new ErrorVO("mail.operation.sameorigindest",
						new Object[] { newMailbagVO.getMailbagId() }));
						return;
					}
					if(isValidMailtag(newMailbag.getMailbagId(),true)&&!(("CARDIT").equals(newMailbag.getFromPanel()) || ("DEVIATIONPANEL").equals(newMailbag.getFromPanel()))
						&&newMailbag.getMailDestination()!=null&&newMailbag.getMailDestination().trim().length()>0&&newMailbag.getMailorigin()!=null&&newMailbag.getMailorigin().trim().length()>0){
						String ooe=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForCarditMissingDomMail(logonAttributes.getCompanyCode(),newMailbag.getMailorigin());
						String doe=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForCarditMissingDomMail(logonAttributes.getCompanyCode(),newMailbag.getMailDestination());
						if(ooe!=null){
						    newMailbag.setOoe(ooe);
						}
						if(doe!=null){
							newMailbag.setDoe(doe);
						}
					}
					
					 
						newMailbagVO.setDisplayLabel("N");
					if(newMailbag.getPaCode()!= null){
						newMailbagVO.setPaCode(newMailbag.getPaCode());
				}else{
					MailbagVO existingMailbagVO = null;
					try {
						existingMailbagVO = new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
								logonAttributes.getCompanyCode(), newMailbag.getMailbagId().toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {
						handleDelegateException(businessDelegateException);
					}
					if (Objects.nonNull(existingMailbagVO)) {
						newMailbagVO.setPaCode(existingMailbagVO.getPaCode());
					}
					}
					if(containerDetailsVO.getPaBuiltFlag()!=null){
						newMailbagVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
					}else{
						newMailbagVO.setPaBuiltFlag("N");
					}
					
					if(newMailbag.getOoe() != null) {
						if(newMailbag.getOoe() != null && !("".equals(newMailbag.getOoe()))) {
							newMailbagVO.setOoe(newMailbag.getOoe().toUpperCase());
						}
					}
					if(newMailbag.getDoe() != null) {
						if(newMailbag.getDoe() != null && !("".equals(newMailbag.getDoe()))) {
							newMailbagVO.setDoe(newMailbag.getDoe().toUpperCase());
						}
					}
					if(newMailbag.getMailCategoryCode() != null) {
						if(newMailbag.getMailCategoryCode()!= null && !("".equals(newMailbag.getMailCategoryCode()))) {
							newMailbagVO.setMailCategoryCode(newMailbag.getMailCategoryCode());
						}
					}
					if(newMailbag.getMailClass()!= null && newMailbag.getMailClass().trim().length()>0) {
						newMailbagVO.setMailClass(newMailbag.getMailClass().toUpperCase());
					}
					if(newMailbag.getMailSubclass() != null) {
						if(newMailbag.getMailSubclass()!= null && !("".equals(newMailbag.getMailSubclass()))) {
							newMailbagVO.setMailSubclass(newMailbag.getMailSubclass().toUpperCase());
							newMailbagVO.setMailClass(newMailbag.getMailSubclass().substring(0,1));
						}
					}
					if(newMailbag.getYear() != 0) {
						//if(newMailbagVO.getYear() != null && !("".equals(mailYr[index]))) {
							newMailbagVO.setYear(newMailbag.getYear());
						//}
					}
					if(newMailbag.getDespatchSerialNumber() != null) {
						if(newMailbag.getDespatchSerialNumber() != null && !("".equals(newMailbag.getDespatchSerialNumber()))) {
							newMailbagVO.setDespatchSerialNumber(newMailbag.getDespatchSerialNumber().toUpperCase());
						}
					}
					if(newMailbag.getReceptacleSerialNumber() != null) {
						if(newMailbag.getReceptacleSerialNumber() != null && !("".equals(newMailbag.getReceptacleSerialNumber()))) {
							newMailbagVO.setReceptacleSerialNumber(newMailbag.getReceptacleSerialNumber().toUpperCase());
						}
					}
					if(newMailbag.getHighestNumberedReceptacle() != null) {
						if(newMailbag.getHighestNumberedReceptacle() != null && !("".equals(newMailbag.getHighestNumberedReceptacle()))) {
							newMailbagVO.setHighestNumberedReceptacle(newMailbag.getHighestNumberedReceptacle());
						}
					}
					if(newMailbag.getRegisteredOrInsuredIndicator() != null) { 
						if(newMailbag.getRegisteredOrInsuredIndicator()!= null && !("".equals(newMailbag.getRegisteredOrInsuredIndicator()))) {
							newMailbagVO.setRegisteredOrInsuredIndicator(newMailbag.getRegisteredOrInsuredIndicator());
						}
					}
					if(newMailbag.getMailbagId() != null) {  
						if(newMailbag.getMailbagId() != null && !("".equals(newMailbag.getMailbagId()))) {
							newMailbagVO.setMailbagId(newMailbag.getMailbagId());
						}
					} 
					if((newMailbagVO.getMailbagId().trim().length()==29 && !validateMailTagFormat(newMailbagVO.getMailbagId()))||!isValidMailtag(newMailbag.getMailbagId(),false)){
						actionContext.addError(new ErrorVO("Invalid Mailbag:"+newMailbag.getMailbagId()));
						return;
					}
						if(newMailbag.getMraStatus() != null && !("".equals(newMailbag.getMraStatus()))) {
							newMailbagVO.setMraStatus(newMailbag.getMraStatus());
							if("D".equals(newMailbag.getOperationFlag()) && "I".equals(newMailbag.getMraStatus())){
		  						actionContext.addError(new ErrorVO("Cannot delete. Mailbag " + newMailbag.getMailbagId().toString()  +" already imported to MRA"));
		  						return;
								}
						}
					if(newMailbag.getMailbagWeight()!= null) {
						if(newMailbag.getMailbagWeight() != null && !("".equals(newMailbag.getMailbagWeight()))) {
							
							
							Double mailbagWt=Double.parseDouble(newMailbag.getMailbagWeight());
							
							if (newMailbag.getMailbagId()!=null && newMailbag.getMailbagId().length() ==29 ) {
								newMailbag.setDisplayUnit("K");
								mailbagWt=Double.parseDouble(newMailbag.getMailbagId().substring(25, 29))/10; 
							}
							else{
								
								newMailbag.setDisplayUnit("L");
								mailbagWt=Double.parseDouble(newMailbag.getMailbagId().substring(10, 12));
								  
							}      
							
							
						Measure strWt=new Measure(UnitConstants.MAIL_WGT,0.0,
									mailbagWt,newMailbag.getDisplayUnit());
						newMailbagVO.setStrWeight(strWt);
						newMailbagVO.setWeight(strWt);
					
					 }
					}
					if(newMailbag.getMailbagVolume()!= null) {
						if(newMailbag.getMailbagVolume() != null && !("".equals(newMailbag.getMailbagVolume()))) {
							newMailbagVO.setVolume(new Measure(UnitConstants.VOLUME,0.0,Double.parseDouble(newMailbag.getMailbagVolume()),(String)stationParameters.get(STNPAR_DEFUNIT_VOL)));      //Added by A-7550
						}
					}
					
						if(containerDetailsVO.getTransferFromCarrier()!=null) {     
							newMailbagVO.setTransferFromCarrier(containerDetailsVO.getTransferFromCarrier());   
						}
						else if(newMailbag.getCarrier() != null && !("".equals(newMailbag.getCarrier()))) {
							newMailbagVO.setTransferFromCarrier(newMailbag.getCarrier());
						}
					if((newMailbag.getScannedDate() != null)){
						
							String scanDT=null;
					
					
					
							
					
					
						        
					  
										
										LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
							if(newMailbag.getScannedTime() != null){
							if(newMailbag.getScannedTime().length()==8){
								if(newMailbag.getScannedTime().equals("00:00:00")){
									LocalDate date=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
									scanDT = new StringBuilder(newMailbag.getScannedDate()).append(" ") 
											.append(date.toDisplayTimeOnlyFormat(false)).toString();									
								}else{
									scanDT = new StringBuilder(newMailbag.getScannedDate()).append(" ") 
											.append(newMailbag.getScannedTime()).toString();									
								}
							}
							else{
								scanDT = new StringBuilder(newMailbag.getScannedDate()).append(" ") 
									.append(newMailbag.getScannedTime()).append(":00").toString();
							}
							}
							else{
								scanDT = new StringBuilder(newMailbag.getScannedDate()).append(" ") 
										.append("00:00:00").toString();
										}
										newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
						
					}
					
					
					
		
					if(newMailbag.getDamageFlag()!= null && !("".equals(newMailbag.getDamageFlag()))) {
						newMailbagVO.setDamageFlag(newMailbag.getDamageFlag());
					}
		
		
					if(newMailbag.getMailRemarks() != null && !("".equals(newMailbag.getMailRemarks()))) {
						newMailbagVO.setMailRemarks(newMailbag.getMailRemarks());
					}
					
					if(newMailbag.getBellyCartId() != null && !("".equals(newMailbag.getBellyCartId()))) {
						newMailbagVO.setBellyCartId(newMailbag.getBellyCartId());
					}
					if(newMailbag.getFromPanel()!=null){
						newMailbagVO.setFromPanel(newMailbag.getFromPanel());
					}
					
					if(newMailbag.getFromPanel()!=null && (("CARDIT").equals(newMailbag.getFromPanel()) || ("DEVIATIONPANEL").equals(newMailbag.getFromPanel()))){
						newMailbagVO.setScanTimeEntered(true);
					}
			
					newMailbagVO.setPaBuiltFlagUpdate(newMailbag.isPaBuiltFlagUpdate());
					newMailbagVO.setPaContainerNumberUpdate(newMailbag.isPaContainerNumberUpdate());
					newMailbagVO.setImportMailbag(newMailbag.isImportMailbag());
                    if(newMailbag.getOperationFlag() != null &&newMailbag.getOperationFlag().equals("D")) {
								
								
									
							    	        
									deletedMailbagVOs.add(newMailbagVO);
                     }
									       
							   
							 
					   
				   
	
			
	    	 
	    	 
	    	 
	
	
		                
			
				
					
				
					
					
				
				
					  
			
			else{
					
			       
			
 
					newMailbagVOs.add(newMailbagVO);
	               }
		}
	containerDetailsVO.setMailDetails(newMailbagVOs);
	containerDetailsVO.setDeletedMailDetails(deletedMailbagVOs);
	}
	outboundModel.setSelectedContainer(containerDetails);
	    actionContext.setAttribute("containerDetails", containerDetailsVO);
	    actionContext.setAttribute("mailAcceptanceDetails", mailAcceptanceVO);
	    actionContext.setAttribute("flightCarrierFlag", outboundModel.getFlightCarrierflag());

	}
	/**
	 * @author A-5526 for IASCB-34124 
	 * findFlightValidationVO
	 * @param mailAcceptance
	 * @param logonAttributes
	 * @return
	 */
	private FlightValidationVO findFlightValidationVO(MailAcceptance mailAcceptance, LogonAttributes logonAttributes) {
		FlightFilterVO flightFilterVO = null;

		FlightValidationVO flightValidationVO = new FlightValidationVO();
		Collection<ErrorVO> errors = new ArrayList();
		if (mailAcceptance.getFlightNumber() != null && mailAcceptance.getFlightNumber().trim().length() > 0) {
			Collection<FlightValidationVO> flightValidationVOs = null;
			try {
				flightFilterVO = handleFlightFilterVO(mailAcceptance, logonAttributes);

				flightFilterVO.setCarrierCode(mailAcceptance.getFlightCarrierCode());
				flightFilterVO.setFlightCarrierId(mailAcceptance.getCarrierId());
				flightFilterVO.setFlightNumber(mailAcceptance.getFlightNumber());

				flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

			if (flightValidationVOs.size() == 1) {
				log.log(Log.FINE, "flightValidationVOs has one VO");
				try {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						BeanHelper.copyProperties(flightValidationVO, flightValidVO);
						// carditEnquirySession.setFlightValidationVO(flightValidationVO);
						// break;
					}
				} catch (SystemException systemException) {
					systemException.getMessage();
				}
			}
			return flightValidationVO;
		}
		return flightValidationVO;
	}
	private Collection<String> getStationParameterCodes()
	  {
	    Collection stationParameterCodes = new ArrayList();

	    stationParameterCodes.add(STNPAR_DEFUNIT_VOL);
	    return stationParameterCodes;
  }
	
	/**
	 * 
	 * 	Method		:	UpdateAcceptMailCommand.isValidMailtag
	 *	Added by 	:	A-8164 on 23-Jan-2020
	 * 	Used for 	:	IASCB-31815
	 *	Parameters	:	@param mailtagLength
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 * @throws BusinessDelegateException 
	 */
	private boolean isValidMailtag(String mailbagId,boolean checkDomMail) throws BusinessDelegateException
	{
		boolean valid=false;
		int mailtagLength=mailbagId!=null?mailbagId.toString().trim().length():0;
		String systemParameterValue=null; 
		ArrayList<String> systemParameters = new ArrayList<String>();
	    systemParameters.add(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate() 
			     	.findSystemParameterByCodes(systemParameters);
		if (systemParameterMap != null) {
			systemParameterValue = systemParameterMap.get(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
		}
		if(mailtagLength==29 && mailbagId.matches(SPECIAL_CHARACTER_REMOVE_REGEX) && !checkDomMail){
			return true;
		}
		if(systemParameterValue!=null && !systemParameterValue.equals("NA"))
		{
		 String[] systemParameterVal = systemParameterValue.split(","); 
	        for (String a : systemParameterVal) 
	        {
	        	if(Integer.valueOf(a)==mailtagLength)
	        	{
	        		valid=true;
	        		break;
	        	}
	        }
		}
		return valid;
	}
	/**
	 * @author A-5526 for IASCB-34124 
	 * handleFlightFilterVO
	 * @param mailAcceptance
	 * @param logonAttributes
	 * @return
	 */
	 private FlightFilterVO handleFlightFilterVO(
			 MailAcceptance mailAcceptance,
			LogonAttributes logonAttributes) {

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		// Modified by A-7794 as part of ICRD-197439
		flightFilterVO.setStation(mailAcceptance.getPol());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setIncludeACTandTBC(true);
		if (mailAcceptance.getFlightDate() != null) {
			flightFilterVO.setStringFlightDate(mailAcceptance.getFlightDate().toString().substring(0, 11));
			LocalDate cd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);
			if ((mailAcceptance.getFlightDate() != null && mailAcceptance.getFlightDate().trim().length() > 0)) {
				flightFilterVO.setFlightDate(cd.setDate(mailAcceptance.getFlightDate()));
			}

		}
		// flightFilterVO.setFlightDate(date);
		return flightFilterVO;
	}
	
	/**
	 * validateULDIncomatibility
	  *@author A-5526 for IASCB-34124 
	 * @param newContainer
	 * @param flightValidationVO
	 * @param actionContext
	 */
	private void validateULDIncomatibility(ContainerDetails newContainer, FlightValidationVO flightValidationVO,
			ActionContext actionContext) {

		Collection<String> parameterCodes = new ArrayList<String>();
		// ICRD-56719

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

		if (newContainer.getContainerOperationFlag() != null
				&& (MailConstantsVO.OPERATION_FLAG_INSERT.equals(newContainer.getContainerOperationFlag())
						|| MailConstantsVO.OPERATION_FLAG_UPDATE.equals(newContainer.getContainerOperationFlag()))) {
			/*
			 * ULD type compatibility validation
			 */
			if (newContainer.getContainerNumber() != null && newContainer.getContainerNumber().trim().length() > 0) {
				String uldType = newContainer.getContainerNumber().substring(0, 3);
				if (!uldTypeCodes.contains(uldType.toUpperCase())) {
					uldTypeCodes.add(uldType.toUpperCase());
				}
				uldNumberCodes.add(newContainer.getContainerNumber());
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

							actionContext.addError(new ErrorVO(
									"mailtracking.defaults.mailacceptance.msg.err.uldincompatileforaircrafttype",
									new Object[] { errorDatum }));

							return;

						}
					}

				}
			}
		}
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
	private boolean validateMailTagFormat(String mailbagId){
		boolean isValid=false;
		String mailYr=mailbagId.substring(15,16);
		String mailDSN=mailbagId.substring(16,20);
		String mailRSN=mailbagId.substring(20,23);
		String mailHNI=mailbagId.substring(23,24);
		String mailRI=mailbagId.substring(24,25);
		String mailWt=mailbagId.substring(25,29);
		  if(   mailYr.matches(INT_REGEX) && mailYr.length()==1&&
				mailDSN.matches(INT_REGEX) && mailDSN.length()==4&&
				mailRSN.matches(INT_REGEX) && mailRSN.length()==3&&
				mailHNI.matches(INT_REGEX) && mailHNI.length()==1&&
				mailRI.matches(INT_REGEX) && mailRI.length()==1&&
				mailWt.matches(INT_REGEX) && mailWt.length()==4){
			isValid=true;
		}
		return isValid;
	}
	
}
