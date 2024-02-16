/*
 * ListFlightContainersCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.FlightValidation;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class ListFlightContainersCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("ListFlightContainersCommand");
	
	private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String OPEN = "O";
	   private static final String CLOSED = "C";
	   private static final String FLTSTATUS = "mailtracking.defaults.flightstatus";
	   private static final String OUTBOUND = "O";

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ListFlightContainersCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();		
		ResponseVO responseVO = new ResponseVO();

		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AreaDelegate areaDelegate = new AreaDelegate();
		
		FlightValidation flightValidation = null;
		
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		ArrayList results = new ArrayList();	

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getFlightValidation() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getFlightValidation() not null");
				    	
			
			OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
			operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
			operationalFlightVO.setPol(logonAttributes.getAirportCode());
			
			flightValidation = mailbagEnquiryModel.getFlightValidation();
			 
			 if(FLAG_YES.equals(mailbagEnquiryModel.getDuplicateFlightStatus())){
				 mailbagEnquiryModel.setAssignToFlight(CONST_FLIGHT);
			 }
			String assignTo = mailbagEnquiryModel.getAssignToFlight();
			log.log(Log.FINE, "assignTo === ", assignTo);
			Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
			if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
				
				log.log(Log.FINE, "*******FLIGHT MODE******");
				
					
					Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
					if(oneTimes!=null){
						Collection<OneTimeVO> resultStatus=
							oneTimes.get("flight.operation.flightlegstatus");
						log.log(Log.FINE, "*******flightlegstatus******");
						//flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidation.getLegStatus()));
					}					
					
					if(flightValidation != null){
		    			if(flightValidation.isTBADueRouteChange()){
		    				Object [] obj = {flightValidation.getCarrierCode(),flightValidation.getFlightNumber(),flightValidation.getFlightDate()};
		    				ErrorVO errorVO = new ErrorVO(
		    						"mailtracking.defaults.transfermail.msg.err.flighttobeactioned",obj);
		    				if(errors == null){
		    					errors = new ArrayList<ErrorVO>();
		    				}
		    				errors.add(errorVO);
		    				actionContext.addAllError(errors);
		    				return;
		    			}
		    		}
					
			    	LocalDate flightDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
			    	
					operationalFlightVO.setFlightNumber(flightValidation.getFlightNumber());
					operationalFlightVO.setCarrierCode(flightValidation.getCarrierCode());
					operationalFlightVO.setCarrierId(flightValidation.getFlightCarrierId());
					flightDate.setDate(flightValidation.getFlightDate());
					operationalFlightVO.setFlightDate(flightDate);
					operationalFlightVO.setLegSerialNumber(flightValidation.getLegSerialNumber());
					operationalFlightVO.setFlightSequenceNumber(flightValidation.getFlightSequenceNumber());
					operationalFlightVO.setDirection(OUTBOUND);
				
				  log.log(Log.FINE, "operationalFlightVO in MA session...",
						operationalFlightVO);
				try {
					  containerVOs = new MailTrackingDefaultsDelegate().findFlightAssignedContainers(operationalFlightVO);
		          }catch (BusinessDelegateException businessDelegateException) {
		    			errors = (ArrayList)handleDelegateException(businessDelegateException);
		    	  }
		    	  if (errors != null && errors.size() > 0) {
		    		  actionContext.addAllError(errors);
		  			return;
		    	  }
		  		log.log(Log.FINE, "containervos ----->>", containerVOs);
				if (containerVOs == null || containerVOs.size() <= 0) {
		    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
					errors = new ArrayList<ErrorVO>();
					errors.add(errorVO);
					actionContext.addAllError(errors);
					return;
		    	}
		    		  boolean flightStatus=false;
		    	    	try{
		            		
		    	    	  flightStatus = new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);
		    	    	
		    	    	}catch (BusinessDelegateException businessDelegateException) {
		    				errors = (ArrayList<ErrorVO>)handleDelegateException(businessDelegateException);
		    			}
		    	    	
		    	    	String status="";
		    	    	log.log(Log.FINE, "flightStatus --------->>", flightStatus);
						if(flightStatus) {
							status=CLOSED;
						} else {
							status=OPEN;
						}
		    	    	
		    	    	if(oneTimes!=null){
		    				Collection<OneTimeVO> resultStatus=
		    					oneTimes.get(FLTSTATUS);
		    				log.log(Log.FINE, "*******flightlegstatus******");
		    				//flightValidationVO.setOperationalStatus(findOneTimeDescription(resultStatus,status));
		    			}   
		    	    	
		    	    	if(flightStatus){
		    	    		Object[] obj = {flightValidation.getCarrierCode(),
		    	    				flightValidation.getFlightNumber(),
		    	    				flightValidation.getApplicableDateAtRequestedAirport().toString().substring(0,11)};
		    	    		errors.add(new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj));
		    	    	}
		    	    	if(errors != null && errors.size() >0){
		    	    		actionContext.addAllError(errors);
		    				return;
		    	    	}
		    	    	/**
		    	    	 * Added for Bug 77985
		    	    	 * For flight closure check ends
		    	    	 */
		    	  
			}else{
				log.log(Log.FINE, "*******DESTINATION MODE******");
				
				////////////////
				

		    	errors = (ArrayList<ErrorVO>)validateFormDestination(mailbagEnquiryModel);
		    	if (errors != null && errors.size() > 0) {  
		    		actionContext.addAllError(errors);
	    			return;
		    	}
		    	Collection<ErrorVO> errorsMail = new ArrayList<ErrorVO>();
		    	AirlineValidationVO airlineValidationVO = null;
		    	 String carrierCode = mailbagEnquiryModel.getCarrierCode();        	
		     	if (carrierCode != null && !"".equals(carrierCode)) {        		
		     		try {        			
		     			airlineValidationVO = airlineDelegate.validateAlphaCode(
		     					logonAttributes.getCompanyCode(),
		     					carrierCode.toUpperCase());
		     		}catch (BusinessDelegateException businessDelegateException) {
		     			errors = (ArrayList<ErrorVO>)handleDelegateException(businessDelegateException);
		     		}
		     		if (errors != null && errors.size() > 0) {            			
		     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
				   				new Object[]{carrierCode.toUpperCase()}));
		     		}else{
		     			mailbagEnquiryModel.setCarrierID(airlineValidationVO.getAirlineIdentifier());
		     		}
		     	}
		     	
		     	AirportValidationVO airportValidationVO = null;
		    	 String destination = mailbagEnquiryModel.getDestination();        	
		     	if (destination != null && !"".equals(destination)) {        		
		     		try {        			
		     			airportValidationVO = areaDelegate.validateAirportCode(
		     					logonAttributes.getCompanyCode(),
		     					destination.toUpperCase());
		     		}catch (BusinessDelegateException businessDelegateException) {
		     			errors = (ArrayList<ErrorVO>)handleDelegateException(businessDelegateException);
		     		}
		     		if (errors != null && errors.size() > 0) {            			
		     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidairport",
				   				new Object[]{destination.toUpperCase()}));
		     		}
		     	}
		     	
		     	String upliftAirport = mailbagEnquiryModel.getUpliftAirport();        	
		     	if (upliftAirport != null && !"".equals(upliftAirport)) {        		
		     		try {        			
		     			airportValidationVO = areaDelegate.validateAirportCode(
		     					logonAttributes.getCompanyCode(),
		     					upliftAirport.toUpperCase());
		     		}catch (BusinessDelegateException businessDelegateException) {
		     			errors = (ArrayList<ErrorVO>)handleDelegateException(businessDelegateException);
		     		}
		     		if (errors != null && errors.size() > 0) {            			
		     			errorsMail.add(new ErrorVO("mailtracking.defaults.invalidupliftairport"));
		     		}
		     	}
		     	
		     	
		     	if (errorsMail != null && errorsMail.size() > 0) {
		     		actionContext.addAllError((ArrayList<ErrorVO>)errorsMail);
	     			return;
		     	}
				
		            DestinationFilterVO destinationFilterVO = new DestinationFilterVO();
		        	destinationFilterVO.setAirportCode(logonAttributes.getAirportCode());
		        	destinationFilterVO.setCarrierCode(mailbagEnquiryModel.getCarrierCode().toUpperCase());
		        	destinationFilterVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		        	destinationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		        	destinationFilterVO.setDestination(mailbagEnquiryModel.getDestination().toUpperCase());
		        	
		        	log.log(Log.FINE, "DestinationFilterVO ----->>",
							destinationFilterVO);
				try {
					  containerVOs = new MailTrackingDefaultsDelegate().findDestinationAssignedContainers(destinationFilterVO);
		          }catch (BusinessDelegateException businessDelegateException) {
		    			errors = (ArrayList)handleDelegateException(businessDelegateException);
		    	  }
		    	  if (errors != null && errors.size() > 0) {
		    		  actionContext.addAllError(errors);
		  			return;
		    	  }
		  		log.log(Log.FINE, "containervos ----->>", containerVOs);
				if (containerVOs == null || containerVOs.size() <= 0) {
		    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
					errors = new ArrayList<ErrorVO>();
					errors.add(errorVO);
					actionContext.addAllError(errors);
					return;
		    	}
			}
	    	
			Collection<ContainerDetails> containers = new ArrayList<ContainerDetails>();
			
			if(containerVOs!=null && containerVOs.size()>0){
				
				for(ContainerVO containerVO : containerVOs){
					containers.add(MailOperationsModelConverter.constructContainer(containerVO,logonAttributes));
				}				
			}
			
			mailbagEnquiryModel.setContainerDetails(containers);

		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}

		results.add(mailbagEnquiryModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ListFlightContainersCommand", "execute");

	}
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add("flight.operation.flightlegstatus");
			fieldValues.add("mailtracking.defaults.flightstatus");
			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	/**
	 * This method will the status description corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		if (oneTimeVOs != null) {
			for (OneTimeVO oneTimeVO:oneTimeVOs){
				if(status.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}
	private Collection<ErrorVO> validateFormDestination(
			MailbagEnquiryModel mailbagEnquiryModel) {		
		String destination = mailbagEnquiryModel.getDestination();
		String carrierCode = mailbagEnquiryModel.getCarrierCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(destination == null || ("".equals(destination.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.destination.empty"));
		}
		if(carrierCode == null || ("".equals(carrierCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.carriercode.empty"));
		}
		return errors;
	}

}
