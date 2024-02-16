/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ReassignContainerCommand.java
 *
 *	Created by	:	a-7779
 *	Created on	:	26-Sep-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDPositionFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ReassignContainer;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ReassignContainerCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	26-Sep-2018	:	Draft
 */
public class ReassignContainerCommand extends AbstractCommand{
	
	 private Log log = LogFactory.getLogger("MAIL"); 

	   private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String CONST_YES = "YES";
	   private static final String OUTBOUND = "O";
	   private static final String ROUTE_DELIMETER = "-";
	   private static final String STNPAR_DEFUNIT_WGT = "station.defaults.unit.weight";
	   private static final String AIRCRAFT_COMBATIBILITY_CHECK_REQUIRED = "operations.flthandling.aircraftcompatibilityrequireduldtypes";
	   private static final String CONST_SCREENID = "MTK058";
		private static final String SECURITY_SCREENING_WARNING="mail.operations.securityscreeningwarning";
		private static final String SECURITY_SCREENING_ERROR="mail.operations.securityscreeningerror";
		private static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";
		 

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		log.entering("ReassignContainerCommand","execute"); 
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel(); 
		Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();
		ReassignContainer reassignContainer = (ReassignContainer)listContainerModel.getReassignContainer();
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		boolean isModify=listContainerModel.isContainerModify();
		String screenId=CONST_SCREENID;
		if(listContainerModel.getNumericalScreenId()!=null){
		screenId= listContainerModel.getNumericalScreenId();
		}
		AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
		Collection<OnwardRouting> onwardRouting = listContainerModel.getOnwardRouting();
    	Collection<OnwardRoutingVO> onwardRoutingVOs = new ArrayList<OnwardRoutingVO>();
    	String destination=null;
    	for (OnwardRouting curOnwardRouting : onwardRouting) {     
    		onwardRoutingVOs.add(MailOperationsModelConverter.constructOnwardRoutingVO(curOnwardRouting, logonAttributes));
    	}
    	for(ContainerDetails container:selectedContainerData){
    		if(reassignContainer.getAssignedto().equals("C")){
    			if(!isModify){
        		if(reassignContainer.getCarrierCode().equals(container.getCarrierCode()) && reassignContainer.getDestination().equals(container.getDestination()) && container.getFlightNumber().equals("-1")){
        			ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.reassigncontainer.msg.err.reassigningToSameCarrier");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors = new ArrayList<ErrorVO>();
					errors.add(errorVO);
					break;
        		}
        		
    			}
        	}	
    		if(container.getDestination()==null || container.getDestination().trim().length()==0) {
    			actionContext.addError(new ErrorVO("Please enter Destination code"));
      		    return;
    		}
    	}
    		for(ContainerDetails container:selectedContainerData){
    	if(container.getDestination().equals(logonAttributes.getAirportCode())){
    		actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destn.currentairport"));
    		  return;
    	}
    		
    	
    		// Validate Destination
        	 areaDelegate = new AreaDelegate();
        	airportValidationVO = null;
        	 destination = container.getDestination();  
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
    	
    		
    	
    	if(errors!=null&&errors.size()>0){
			 ErrorVO curError = errors.iterator().next();
			 actionContext.addError(curError);
			 return;
    	}
		Collection<ContainerVO> selectedContainerVOs = constructContainerVOs(selectedContainerData,reassignContainer, onwardRoutingVOs,screenId);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		errors = constructOperationalFlightVO(operationalFlightVO,reassignContainer,selectedContainerVOs,isModify,screenId);
		if(errors!=null&&errors.size()>0){
			 ErrorVO curError = errors.iterator().next();
			 actionContext.addError(curError);
			 return;
			 
		}
		if(!isModify&&doSecurityAndScreeningValidations(operationalFlightVO,selectedContainerVOs, actionContext,listContainerModel)){
			return;
		}
		try {
    		log.log(Log.FINE, "selectedContainerVOs for saving-------> ", selectedContainerVOs);
			log.log(Log.FINE, "operationalFlightVO for saving-------> ", operationalFlightVO);
			
			mailTrackingDefaultsDelegate.reassignContainers(selectedContainerVOs,operationalFlightVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		 ResponseVO responseVO = new ResponseVO();	  
	     if(errors!=null&&errors.size()>0){
	    	 ErrorVO curError = errors.iterator().next();
	    	 if(curError.getErrorCode().equals("mailtracking.defaults.err.flightclosed")){
	    		 curError.setErrorCode("mailtracking.defaults.err.flightclosedforreassign");
	    	 }
	    	 actionContext.addError(curError);
	     }else{
	    	 responseVO.setStatus("reassign_success");
		     
	     }
	     actionContext.setResponseVO(responseVO); 
	     log.exiting("ReassignContainerCommand","execute");
	    	
	}

	/**
	 * 
	 * 	Method		:	ReassignContainerCommand.constructOperationalFlightVO
	 *	Added by 	:	a-7779 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	OperationalFlightVO
	 */
	private Collection<ErrorVO> constructOperationalFlightVO(OperationalFlightVO operationalFlightVO,ReassignContainer reassignContainer,Collection<ContainerVO> selectedContainerVOs,boolean isModify,String screenId) {
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	operationalFlightVO.setOwnAirlineId(
    			logonAttributes.getOwnAirlineIdentifier());
    	operationalFlightVO.setOperator(logonAttributes.getUserId());
    	
    	operationalFlightVO.setOperationTime(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
				reassignContainer.getScanDate()));
    	
    	
    	// IF REASSIGNING TO FLIGHT
    	AirlineValidationVO airlineValidationVO = null;
    	//AreaDelegate areaDelegate = new AreaDelegate();
    	//AirportValidationVO airportValidationVO = null;
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	String flightCarrierCode = reassignContainer.getCarrierCode();        	
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
				return errors;
    		}		
    		if(isModify){
    			for(ContainerVO containerVO : selectedContainerVOs){
        			if(containerVO.getPou()!=null){
        				operationalFlightVO.setPou(containerVO.getPou());
        			}
    			}
    		}else{
           // Added as part of ICRD-332565
    		operationalFlightVO.setPou(reassignContainer.getDestination());
    		}
    		//updating flight and airline info in containerVO
    		//if(airlineValidationVO!=null){
    		/*for(ContainerVO containerVO : selectedContainerVOs){
    			//containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
    			if(containerVO.getPou()!=null){
    				operationalFlightVO.setPou(containerVO.getPou());
    			}else{
    				operationalFlightVO.setPou(reassignContainer.getDestination());
    			}
    			
    		//}
    		}*/
    		
    	}
    	Collection<FlightValidationVO> flightValidationVOs = null;
    	
    	
    	if ("F".equals(reassignContainer.getReassignFilterType())) {  
    		FlightValidationVO flightValidationVO = null;
    		
    		// VALIDATING FLIGHT NUMBER   
        	log.log(Log.FINE,"VALIDATING FLIGHT NUMBER ------------> ");
    		FlightFilterVO flightFilterVO = handleFlightFilterVO(
    				reassignContainer,logonAttributes);
    		
    	
    		flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
    		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
    		        		
    		

			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ",
						flightFilterVO);
				flightValidationVOs =
					mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			return errors;
    		}
    		
    		log.log(Log.FINE, "FlightValidationVO ------------> ",
					flightValidationVO);
			// validating whether the container is already assigned to same flight
    		String assignedto = reassignContainer.getAssignedto();
    		log.log(Log.FINE, "assignedto ------------> ", assignedto);
    		if(flightValidationVOs!=null && flightValidationVOs.size()>0){
    		flightValidationVO = flightValidationVOs.iterator().next();
    		}
    		else{
    				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
    				Object[] obj = {operationalFlightVO.getCompanyCode().toUpperCase(),
    						reassignContainer.getFlightNumber().toUpperCase(),
    						reassignContainer.getFlightDate()};
    				ErrorVO errorVO = new ErrorVO(
    						"mailtracking.defaults.reassigncontainer.msg.err.noflightDetails",obj);
    				errors = new ArrayList<ErrorVO>();
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorVO);
    				return errors;
    	
    		}
    		
    		
    		
			if ("F".equals(assignedto) && flightValidationVO!=null) {
				
				//Validate ULD Type- Aircraft Compatibility starts
				
			
				
				errors=validateULDIncomatibility(selectedContainerVOs,flightValidationVO);
				if (errors != null && errors.size() > 0) {      
        			return errors;
        		}
				
				//validateULDIncomatibility
    			errors = isReassignedToSameFlight(
    					flightValidationVO,
    					selectedContainerVOs,
    					reassignContainer,isModify);
        		if (errors != null && errors.size() > 0) {      
        			return errors;
        		}
        	
        		if(flightValidationVO != null){ 
        			//based on the Soncy comment
        			//IASCB-63549: modified
        		  boolean isToBeActioned = flightValidationVO.isTBADueRouteChange()||FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus());
        		  isToBeActioned = isToBeActioned && !canIgnoreToBeActionedCheck();
      		      if ((isToBeActioned || FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())
      						|| FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))) {
        				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),flightValidationVO.getFlightDate()};
        				ErrorVO errorVO = new ErrorVO(
      							"mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
        				if(errors == null){
        					errors = new ArrayList<ErrorVO>();
        				}
        				errors.add(errorVO);
        				return errors;
        			}
        		}
        		
    		}
    		
    		// validating whether pou is present in the onward routes of flight
    	/*	errors = isPouValid(
    				reassignContainerForm,
    				flightValidationVO,
    				logonAttributes);*/
    		//Added as part of ICRD-115893 starts
    	/*	Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
        	try{
            	containerVOs = mailTrackingDefaultsDelegate
        		.findAllULDsInAssignedFlight(flightValidationVO);
            	}catch (BusinessDelegateException businessDelegateException) {
            		errors = handleDelegateException(businessDelegateException);
        			log.log(Log.INFO,"ERROR--SERVER------isReassignedToOldFlight---->>");
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
    		errors = isReassignedToOldFlight(
    				containerVOs,
    				selectedContainerVOs);
    		//Added as part of ICRD-115893 ends
    		
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}*/
    		
    /*		for (ContainerVO vo : selectedContainerVOs) {
				vo.setOnwardRoutings(currentvo.getOnwardRoutings());	
				vo.setRemarks(reassignContainerForm.getRemarks());
				vo.setReassignFlag(true);
				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				vo.setMailSource(reassignContainerForm.getNumericalScreenId());//Added for ICRD-156218
    		}*/
    	//FlightValidationVO  flightValidationVO = null;
			//Added by A-7540
			//Validating if the containers are already present in the flight with different container type
			Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
	    	try{
	    		newContainerVOs = mailTrackingDefaultsDelegate
	    		.findAllContainersInAssignedFlight(flightValidationVO);
	        	}catch (BusinessDelegateException businessDelegateException) {
	        		errors = handleDelegateException(businessDelegateException);	
	    		}
	    	if (errors != null && errors.size() > 0) {
    			return errors;
    		}
	    	errors = isReassignedWithSameContainer(
	    			newContainerVOs,
					selectedContainerVOs);
	    	if (errors != null && errors.size() > 0) {
    			return errors;
    		}
    	if(flightValidationVO!=null){
    		 
    		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
        	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());    	
        	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
        	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
        	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
        	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	operationalFlightVO.setFlightType(flightValidationVO.getFlightType());
        	//operationalFlightVO.setPou(flightValidationVO.getPou().toUpperCase()); to do
			if(flightValidationVO.getAtd() != null){
				operationalFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
			}
    	}
    	}
    	// IF REASSIGNING TO DESTINATION
    	else {
    		/*AirlineValidationVO airlineValidationVO = 
    			reassignContainerSession.getAirlineValidationVO();*/
    		log.log(Log.FINE, "AirlineValidationVO ------------> ",
					airlineValidationVO);
			operationalFlightVO.setCarrierCode(airlineValidationVO.getAlphaCode());
        	operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());  
        	operationalFlightVO.setFlightDate(null);
        	operationalFlightVO.setFlightNumber("-1");
        	operationalFlightVO.setFlightSequenceNumber(-1);
        	operationalFlightVO.setLegSerialNumber(-1);
        	operationalFlightVO.setPou(reassignContainer.getDestination().toUpperCase());
        	
        	for (ContainerVO vo : selectedContainerVOs) {					
				vo.setRemarks(reassignContainer.getRemarks());
				vo.setReassignFlag(true);
				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				vo.setMailSource(screenId);//Added for ICRD-156218
				vo.setFinalDestination(vo.getFinalDestination().toUpperCase());//Added for ICRD-134048
    		}
    	}
			
		return errors;
    	
	}

	/**
	 * 
	 * 	Method		:	ReassignContainerCommand.constructContainerVOs
	 *	Added by 	:	a-7779 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param selectedContainerData
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ContainerVO>
	 */
	private Collection<ContainerVO> constructContainerVOs(Collection<ContainerDetails> selectedContainerData,ReassignContainer reassignContainer, Collection<OnwardRoutingVO> onwardRoutings,String screenId) {
		
		/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<FlightValidationVO> flightValidationVOs = null;
		Collection<ErrorVO> errors = null;
		String actualWeightUnit=null;
 	    actualWeightUnit=findStationParameterValue(STNPAR_DEFUNIT_WGT);
		for(ContainerDetails ContainerDetail : selectedContainerData){
			ContainerVO containerVO  = new ContainerVO();
			containerVO.setCompanyCode(logonAttributes.getCompanyCode());
			containerVO.setCarrierCode(ContainerDetail.getCarrierCode());
			containerVO.setContainerNumber(ContainerDetail.getContainerNumber());
			containerVO.setFinalDestination(ContainerDetail.getFinalDestination());
			if(ContainerDetail.getFlightDate()!=null){
				containerVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
						ContainerDetail.getFlightDate()));
			}
			containerVO.setFlightNumber(ContainerDetail.getFlightNumber());
			
			containerVO.setAssignedPort(ContainerDetail.getAssignedPort());
			containerVO.setPol(ContainerDetail.getPol());
			if(ContainerDetail.isUldTobarrow()){
				containerVO.setPou(reassignContainer.getDestination());
			}else{
			containerVO.setPou(ContainerDetail.getPou());
			}
			containerVO.setReassignFlag(true);
			containerVO.setPaBuiltFlag(ContainerDetail.getPaBuiltFlag()!=null?ContainerDetail.getPaBuiltFlag():ContainerVO.FLAG_NO );
			containerVO.setRemarks(ContainerDetail.getRemarks());
			containerVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
			containerVO.setTransferFlag(ContainerVO.FLAG_NO);
			containerVO.setTransitFlag(ContainerVO.FLAG_YES);
			containerVO.setType(ContainerDetail.getType());
			containerVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(),ARP,false));
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setMailSource(screenId);
			//Added by A-8893 for IASCB-38903 starts
			if(ContainerDetail.isUldTobarrow()){
			containerVO.setUldTobarrow(true);
			}
			if(ContainerDetail.isBarrowToUld()){
			containerVO.setBarrowToUld(true);
			}
			//Added by A-8893 for IASCB-38903 ends
			containerVO.setOffloadFlag(ContainerVO.FLAG_NO);
			containerVO.setAcceptanceFlag(ContainerVO.FLAG_YES);//to do
			containerVO.setSegmentSerialNumber(ContainerDetail.getSegmentSerialNumber());
			containerVO.setCarrierId(ContainerDetail.getCarrierId());
			containerVO.setUldFulIndFlag(ContainerDetail.getUldFulIndFlag());
			if((reassignContainer.getScanDate() != null)){
				String scanDT=null;
				LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				if(reassignContainer.getMailScanTime() != null){
				if(reassignContainer.getMailScanTime().length()==8){
					if(reassignContainer.getMailScanTime().equals("00:00:00")){
						LocalDate date=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						scanDT = new StringBuilder(reassignContainer.getScanDate()).append(" ") 
								.append(date.toDisplayTimeOnlyFormat(false)).toString();									
					}else{
						scanDT = new StringBuilder(reassignContainer.getScanDate()).append(" ") 
								.append(reassignContainer.getMailScanTime()).toString();									
					}
				}
				else{
					scanDT = new StringBuilder(reassignContainer.getScanDate()).append(" ") 
						.append(reassignContainer.getMailScanTime()).append(":00").toString();
				}
				}
				else{
					scanDT = new StringBuilder(reassignContainer.getScanDate()).append(" ") 
							.append("00:00:00").toString();
							}
				containerVO.setAssignedDate(sd.setDateAndTime(scanDT,false));
				containerVO.setULDLastUpdateTime(sd.setDateAndTime(scanDT,false));
				containerVO.setScannedDate(sd.setDateAndTime(scanDT,false));
		}
			
			if(ContainerDetail.getFlightNumber().equals("-1")){
				containerVO.setFlightSequenceNumber(-1);
				containerVO.setLegSerialNumber(-1);
			}else{
				FlightFilterVO flightFilterVO = new FlightFilterVO();
	
				flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				flightFilterVO.setFlightNumber(ContainerDetail.getFlightNumber().toUpperCase());
				flightFilterVO.setStation(logonAttributes.getAirportCode());
				flightFilterVO.setDirection(OUTBOUND);
				flightFilterVO.setActiveAlone(false);
				flightFilterVO.setStringFlightDate(ContainerDetail.getFlightDate());
				if(ContainerDetail.getFlightDate()!=null){
					LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
					flightFilterVO.setFlightDate(date.setDate(
							ContainerDetail.getFlightDate()));
				}
				try {
					log.log(Log.FINE, "FlightFilterVO ------------> ",
							flightFilterVO);
					flightValidationVOs =
						mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
	
	    		}catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    		}
				if(ContainerDetail.getFlightSequenceNumber()>1){
					containerVO.setFlightSequenceNumber(ContainerDetail.getFlightSequenceNumber());
				}else{
					containerVO.setFlightSequenceNumber(flightValidationVOs.iterator().next().getFlightSequenceNumber());
				}				
				containerVO.setLegSerialNumber(ContainerDetail.getLegSerialNumber());
			}
			ContainerDetail.setActualWeightUnit(actualWeightUnit);
			 if(ContainerDetail.getActualWeight()!=0||ContainerDetail.isActualWeightUpdated()){   
				 containerVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT, 0,ContainerDetail.getActualWeight(),ContainerDetail.getActualWeightUnit()));
		        }
			if(ContainerDetail.getContentId()!=null && ContainerDetail.getContentId().trim().length()>0){
			containerVO.setContentId(ContainerDetail.getContentId());
			}
			containerVO.setOnwardRoutings(onwardRoutings);
			containerVO.setActWgtSta(ContainerDetail.getActWgtSta());
			containerVO.setContainerDestChanged(ContainerDetail.isContainerDestChanged());
			containerVOs.add(containerVO);
			
		}
		
		return containerVOs;
	}
	/**
	 * 
	 * 	Method		:	ReassignContainerCommand.handleFlightFilterVO
	 *	Added by 	:	a-7779 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param reassignContainer
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	FlightFilterVO
	 */
	   private FlightFilterVO handleFlightFilterVO(
			   ReassignContainer reassignContainer,
				LogonAttributes logonAttributes){

			FlightFilterVO flightFilterVO = new FlightFilterVO();

			flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			flightFilterVO.setFlightNumber(reassignContainer.getFlightNumber().toUpperCase());
			flightFilterVO.setStation(logonAttributes.getAirportCode());
			flightFilterVO.setDirection(OUTBOUND);
			flightFilterVO.setIncludeACTandTBC(true);
	 		if(reassignContainer.getFlightSeqNumber()>0){
	 			flightFilterVO.setFlightSequenceNumber(reassignContainer.getFlightSeqNumber());	
	 		}
	 		else{
	 			flightFilterVO.setStringFlightDate(reassignContainer.getFlightDate());
		 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
		 		flightFilterVO.setFlightDate(date.setDate(
		 				reassignContainer.getFlightDate()));
	 		}
			return flightFilterVO;
		}
	   
	   /**
	     * Method to validate whether the container is reassigned to same flight segment
	     * @param reassignedFlightValidationVO
	     * @param selectedContainerVOs
	     * @param reassignContainerForm
	     * @return Collection<ErrorVO>
	     */
	    private Collection<ErrorVO> isReassignedToSameFlight(
	    		FlightValidationVO reassignedFlightValidationVO,
	    		Collection<ContainerVO> selectedContainerVOs,
	    		ReassignContainer reassignContainer,boolean isModify) {
	    	
	    	log.entering("SaveContainerCommand","isReassignedToSameFlight");
	    	
	    	boolean isSameFlight = false;
	    	StringBuilder errorcode = new StringBuilder("");
	    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();    	
	    	log.log(Log.FINE, "ReassignedFlightValidationVO-------> ",
					reassignedFlightValidationVO);
			for (ContainerVO selectedvo : selectedContainerVOs) {
	    		if (!("-1").equals(selectedvo.getFlightNumber())) {
	    			if(!isModify){
	    			if ((reassignedFlightValidationVO.getFlightCarrierId() == selectedvo.getCarrierId())
	            			&& (reassignedFlightValidationVO.getFlightNumber().equals(selectedvo.getFlightNumber()))
	            			&& (reassignedFlightValidationVO.getLegSerialNumber() == selectedvo.getLegSerialNumber())
	            			&& (reassignedFlightValidationVO.getFlightSequenceNumber() == selectedvo.getFlightSequenceNumber())
	            			&& (reassignContainer.getDestination().equalsIgnoreCase(selectedvo.getPou()))
	        			) {
	    				errorcode.append(selectedvo.getContainerNumber()).append(",");
	    				isSameFlight = true;
	            	}
	    			}
	    		}    		    		
	    	}
	    	
	    	log.log(Log.FINE, "isSameFlight-------> ", isSameFlight);
			log.log(Log.FINE, "errorcode-------> ", errorcode);
			if (isSameFlight) {
	    		Object[] obj = {errorcode.substring(0,errorcode.length()-1)};
	    		ErrorVO errorVO = new ErrorVO(
	    				"mailtracking.defaults.reassigncontainer.msg.err.cannotReassignToSameFlight",obj);
	    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    		validationerrors.add(errorVO);
	    	}
	    	  	
	    	return validationerrors;
	    }
	 
	    /**
		 * @author a-7540
		 * Method is used to find whether the flight being reassigned to Already has the container of different type
		 * @param reassignedFlightValidationVO
		 * @param selectedContainerVOs
		 * @return
		 */
	    private Collection<ErrorVO> isReassignedWithSameContainer(
				Collection<ContainerVO> newContainerVOs,
	    		Collection<ContainerVO> selectedContainerVOs) {
	    	log.entering("SaveContainerCommand","isReassignedWithSameContainer");
	    	boolean diffContType = false;
	    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();  
	    	if(newContainerVOs!=null && newContainerVOs.size()>0){
			for (ContainerVO containerVO : newContainerVOs) {
				if(selectedContainerVOs!=null && selectedContainerVOs.size()>0){
					for (ContainerVO selectedvo : selectedContainerVOs) {
						if(selectedvo.getContainerNumber()!=null && selectedvo.getContainerNumber().trim().length()>0 &&
							   selectedvo.getAssignedPort()!=null && selectedvo.getAssignedPort().trim().length()>0){
							if(selectedvo.getContainerNumber().equalsIgnoreCase(containerVO.getContainerNumber())&&
									selectedvo.getAssignedPort().equalsIgnoreCase(containerVO.getAssignedPort()) &&
									!(selectedvo.getType().equalsIgnoreCase(containerVO.getType()))){
								diffContType = true;
								break;
							}
						}
						}
					}
				}
	    	}
	    	log.log(Log.FINE, "isSameFlight-------> ", diffContType);		
			if(diffContType){
				ErrorVO errorVO = new ErrorVO(
	    				"mailtracking.defaults.reassigncontainer.msg.err.containeralreadypresentwithdiffconttype");
	    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    		validationerrors.add(errorVO);
			}
	    	return validationerrors;
		}
		/**
		 * @author A-7779
		 * @param stnPar
		 * @return
		 */
		private String findStationParameterValue(String stnPar){
			LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
			AreaDelegate areaDelegate = new AreaDelegate();
			//String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL); 
			Map stationParameters = null; 
			String stationCode = logonAttributes.getStationCode();	
			Collection<String> parameterCodes = new ArrayList<String>();
			parameterCodes.add(STNPAR_DEFUNIT_WGT);
			try {
				stationParameters = areaDelegate.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode, parameterCodes);
			} catch (BusinessDelegateException e1) {
				e1.getMessage();
			} 
			return (String)stationParameters.get(STNPAR_DEFUNIT_WGT);
		}
		
		
		
		 
		 /**
			 * validateULDIncomatibility
			  *@author A-5526 for IASCB-34124 
			 * @param selectedContainerVOs
			 * @param flightValidationVO
			 * @param actionContext
			 */
			private Collection<ErrorVO> validateULDIncomatibility(Collection<ContainerVO> selectedContainerVOs, FlightValidationVO flightValidationVO) {

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

				if(selectedContainerVOs!= null &&
						selectedContainerVOs.size()>0){
					for(ContainerVO containerVO:selectedContainerVOs){
						
							/*
							 * ULD type compatibility validation
							 */
							if(containerVO.getContainerNumber() != null &&
									containerVO.getContainerNumber().trim().length() > 0 && (MailConstantsVO.ULD_TYPE.equals(containerVO.getType())||containerVO.isBarrowToUld())){
								if(!containerVO.isUldTobarrow()){
								String uldType=containerVO.getContainerNumber().substring(0, 3);
								if(!uldTypeCodes.contains(uldType.toUpperCase())){
									uldTypeCodes.add(uldType.toUpperCase());
								}
								uldNumberCodes.add(containerVO.getContainerNumber());
								}
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
    *  for AA no need to validate against TBC flight
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
	/**
	 * @author A-8353
	 * @param listContainerModel 
	 * @throws BusinessDelegateException 
	 */
	private boolean doSecurityAndScreeningValidations(OperationalFlightVO operationalFlightVO,
			Collection<ContainerVO> selectedContainerVOs,ActionContext actionContext, ListContainerModel listContainerModel) throws BusinessDelegateException {
		SecurityScreeningValidationVO securityScreeningValidationVO=null;
		Map<String, String> warningMap = listContainerModel.getWarningMessagesStatus();
		if ((warningMap == null) || (warningMap.isEmpty()))
		{
			warningMap = new HashMap<>();
			listContainerModel.setWarningMessagesStatus(warningMap);
		}
		String securityWarningStatus = "N";
		if(warningMap.size()>0 && warningMap.containsKey(SECURITY_SCREENING_WARNING)) {
			securityWarningStatus=warningMap.get(SECURITY_SCREENING_WARNING);  
		}
		if("N".equals(securityWarningStatus)) {
			securityScreeningValidationVO=new MailTrackingDefaultsDelegate().doSecurityAndScreeningValidationAtContainerLevel(operationalFlightVO,selectedContainerVOs);
			if(securityScreeningValidationVO!=null&&checkForWarningOrError(actionContext, securityScreeningValidationVO)){
					return true;
			}
		}
		return false;

	}
	/**
	 * @author A-8353
	 * @param mailbagVO
	 * @param actionContext
	 * @param existigWarningMap
	 * @param securityScreeningValidationVO
	 * @return
	 */
	private boolean checkForWarningOrError( ActionContext actionContext, SecurityScreeningValidationVO securityScreeningValidationVO) {
		if ("W".equals(securityScreeningValidationVO
				.getErrorType())) {
			List<ErrorVO> warningErrors = new ArrayList<>();
			ErrorVO warningError = new ErrorVO(
					SECURITY_SCREENING_WARNING,
					new Object[]{securityScreeningValidationVO.getMailbagID()});
			warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
			warningErrors.add(warningError);
			actionContext.addAllError(warningErrors); 
			return true;

		}
		if ("E".equals(securityScreeningValidationVO
				.getErrorType())) {
			if ("AR".equals(securityScreeningValidationVO
					.getValidationType())){
				actionContext.addError(new ErrorVO(APPLICABLE_REGULATION_ERROR));
			}
			else{
			actionContext.addError(new ErrorVO(SECURITY_SCREENING_ERROR,
					new Object[]{securityScreeningValidationVO.getMailbagID()}));
			}
			return true;
		}
		return false;
	}
		
}
