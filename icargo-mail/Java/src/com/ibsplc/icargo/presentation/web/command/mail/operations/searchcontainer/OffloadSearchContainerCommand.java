/*
 * OffloadSearchContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class OffloadSearchContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "offload_success";
   private static final String TARGET_FAILURE = "offload_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
   private static final String OFFLOAD_SCREEN_ID = "mailtracking.defaults.offload";	
   
   private static final String CONST_OFFLOAD = "showOffloadScreen";
   private static final String OUTBOUND = "O";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("OffloadSearchContainerCommand","execute");
    	  
    	SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationContext.screenModel;
    	SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	OffloadSession offloadSession = 
    		getScreenSession(MODULE_NAME,OFFLOAD_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	String[] primaryKey = searchContainerForm.getSelectContainer();
    	
 	    int cnt=0;
 	    int count = 1;
        int primaryKeyLen = primaryKey.length;
        Collection<ContainerVO> containerVOs = 
        	            searchContainerSession.getListContainerVOs();
        
	     Collection<ContainerVO> selectedContainerVOs = 
	    	                            new ArrayList<ContainerVO>();
	     if (containerVOs != null && containerVOs.size() != 0) {
	    	for (ContainerVO containerVO : containerVOs) {
	    		String primaryKeyFromVO = containerVO.getCompanyCode()
	    				+String.valueOf(count);
	    		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
	    				          equalsIgnoreCase(primaryKey[cnt].trim())) {
	    			selectedContainerVOs.add(containerVO);
	    			cnt++;
	    		}
	    		count++;
	    	  }
	     }
	     // Validation to check whether any containers are destination assigned
	     for (ContainerVO selectedvo : selectedContainerVOs) {
	    	 if (("-1").equals(selectedvo.getFlightNumber())) {
				invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.msg.err.notFlightAssigned"));			
				invocationContext.target = TARGET_FAILURE;
				return;
	    	 }
	     }
	     
	     int errorPort = 0;
	     String contPort = "";
	     for (ContainerVO selectedvo : selectedContainerVOs) {
	    	 if(!logonAttributes.getAirportCode().equals(selectedvo.getAssignedPort())){
	    		 errorPort = 1;
    				if("".equals(contPort)){
    					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
					                  .append(",")
					                  .append(selectedvo.getContainerNumber())
					                  .toString();	
	       			}
	    	 }
	     }
	     if(errorPort == 1){
	   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
	   	   	invocationContext.target = TARGET_FAILURE;
	   	   	return;
	     }
	     
	     // VALIDATION FOR CONTAINERS HOLDING OFFLOADED MAILS
	       int errorArr = 0;
	       String contArr = "";
	       int errorAlreadyManifest=0;
		   String contAlreadyManifest = "";
		  	for (ContainerVO selectedvo : selectedContainerVOs) {
		   		if (selectedvo.getContainerNumber() != null ) {
		   			if("Y".equals(selectedvo.getArrivedStatus())){
		   				errorArr = 1;
	       				if("".equals(contArr)){
	       					contArr = selectedvo.getContainerNumber();
		       			}else{
		       				contArr = new StringBuilder(contArr)
		       				          .append(",")
		       				          .append(selectedvo.getContainerNumber())
		       				          .toString();	
		       			}
		   			}
					if(MailConstantsVO.BULK_TYPE.equals(selectedvo.getType()) && 
							MailConstantsVO.FLAG_NO.equals(selectedvo.getTransitFlag())) {
						errorAlreadyManifest = 1;
						if("".equals(contAlreadyManifest)){
							contAlreadyManifest = selectedvo.getContainerNumber();
						}else{
							contAlreadyManifest = new StringBuilder(contAlreadyManifest)
							.append(",")
							.append(selectedvo.getContainerNumber())
							.toString();	
						}
					}
		   		}
		   	}
		   	
		   	if(errorArr == 1){
		   		invocationContext.addError(new ErrorVO(
		   				"mailtracking.defaults.msg.err.offloadcontainersarrived",new Object[]{contArr}));
		   		invocationContext.target = TARGET_FAILURE;
		        log.exiting("OffloadSearchContainerCommand","execute");
				return;
		   	}
			if(errorAlreadyManifest == 1){
				//Modified for icrd-129222
	  	    	invocationContext.addError(new ErrorVO(
	  	    			"mailtracking.defaults.searchcontainer.err.cannnotoffloadtransitcontainer",new Object[]{contAlreadyManifest}));  	    		    	
				invocationContext.target = TARGET_FAILURE;
		        log.exiting("OffloadSearchContainerCommand","execute");
				return;		
	       }
	     
	     String carriercode = "";
         String fltNo = "";
         String airport = "";
         long fltseqNo = 0;
         int legSerialNo = 0;
         int carrierid = 0;
         LocalDate fltDate = null;
         String containerNumber="";
        String containerType="";
         for (ContainerVO selectedvo : selectedContainerVOs) {
        	 if(selectedvo.getCarrierCode()!=null && selectedvo.getCarrierCode().trim().length()>0){
         	carriercode = selectedvo.getCarrierCode();
        	 }
         	fltNo = selectedvo.getFlightNumber();
         	fltseqNo = selectedvo.getFlightSequenceNumber();
         	legSerialNo = selectedvo.getLegSerialNumber();
         	carrierid = selectedvo.getCarrierId();
         	fltDate = selectedvo.getFlightDate();
         	airport=selectedvo.getAssignedPort();
         	containerNumber=selectedvo.getContainerNumber();
         	containerType=selectedvo.getType();
         	break;
         }   
      	    
    	// Validating whether asigned to different flight
        for (ContainerVO selectedvo : selectedContainerVOs) {
        	if (!carriercode.equals(selectedvo.getCarrierCode())
        			|| !fltNo.equals(selectedvo.getFlightNumber())
        			|| fltseqNo != selectedvo.getFlightSequenceNumber()
        			|| legSerialNo != selectedvo.getLegSerialNumber()) {
        		
        		ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.searchcontainer.msg.err.notassignedToSameFlight");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);			
				invocationContext.target = TARGET_FAILURE;
		        log.exiting("OffloadSearchContainerCommand","execute");
				return;
        	}
        }
        // Validating whether any mailbags are accepted in the containers
        StringBuilder errorcode = new StringBuilder("");
        boolean isBagsPresent = true;
        for (ContainerVO selectedvo : selectedContainerVOs) {
    		if ("N".equals(selectedvo.getAcceptanceFlag())) {
    			errorcode.append(selectedvo.getContainerNumber()).append(",");
        		isBagsPresent = false;
    		}        		
        }
        if (!isBagsPresent) {
        	Object[] obj = {errorcode.substring(0,errorcode.length()-1)};
        	ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.searchcontainer.msg.err.notBagsAccepted",obj);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);			
			invocationContext.target = TARGET_FAILURE;
	        log.exiting("OffloadSearchContainerCommand","execute");
			return;
        }
               
        OffloadVO offloadVO = new OffloadVO();
        offloadVO.setCompanyCode(logonAttributes.getCompanyCode());
        offloadVO.setFlightNumber(fltNo);
        offloadVO.setFlightSequenceNumber(fltseqNo);
        offloadVO.setFlightDate(fltDate);
        offloadVO.setLegSerialNumber(legSerialNo);
        offloadVO.setCarrierCode(carriercode);
        offloadVO.setCarrierId(carrierid);
        offloadVO.setOffloadContainers(selectedContainerVOs);
        offloadVO.setPol(logonAttributes.getAirportCode());
       
        offloadSession.setOffloadVO(offloadVO); 
        
//      Validating Flight to obtain the Flight Validation VO - START
        FlightFilterVO flightFilterVO = new FlightFilterVO();
  		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
  		flightFilterVO.setFlightNumber(fltNo);
  		flightFilterVO.setStation(airport);
  		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
  		flightFilterVO.setStringFlightDate(String.valueOf(fltDate));
   		flightFilterVO.setFlightDate(fltDate);
   		flightFilterVO.setCarrierCode(carriercode);
 		flightFilterVO.setFlightCarrierId(carrierid); 		
 		Collection<FlightValidationVO> flightValidationVOs = null;
 		FlightValidationVO flightValidationVO = null;
 		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
 		try {
 			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs =
 				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
 		}catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
 		} 		
 		//Added by A-5160 to find the flights from mail table when listed from a different airport due to route change
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
				flightValidationVOs =
					mailTrackingDefaultsDelegate.validateMailFlight(flightFilterVO);
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
						flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
					}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
 		if (flightValidationVOs != null) {
 			log
					.log(Log.FINE, "SIZE ------------> ", flightValidationVOs.size());
			if (flightValidationVOs.size() == 1 ) { 
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					flightValidationVO = validationVO;
 					break;
 				}
 			}
 			else if(flightValidationVOs.size() > 1) {
 				for (FlightValidationVO validationVO : flightValidationVOs) {
 					if (validationVO.getFlightSequenceNumber() == fltseqNo) {
 						flightValidationVO = validationVO;
 						break;
 					}				
 				}
 			}
 		}		
 		log.log(Log.FINE, "flightValidationVO ------------> ",
				flightValidationVO);
		// 		Validating Flight Closure
    	boolean isFlightClosed = false;
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()))
    		{
    		operationalFlightVO.setLegSerialNumber(legSerialNo);	
    		}	
    	else
    	{
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	}
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	try {	    		
    		isFlightClosed = 
    				mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);   		        		
			
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
	        log.exiting("OffloadSearchContainerCommand","execute");
			return;
		}
		log.log(Log.FINE,
				"MailbagEnquiry-->OffloadMailCommand--->isFlightClosed----->",
				isFlightClosed);
		if(!isFlightClosed){
			Object[] obj = {new StringBuilder(carriercode).append("").append(fltNo).toString(),fltDate.toDisplayDateOnlyFormat()};				
			invocationContext.addError(new ErrorVO("mailtracking.defaults.err.flightclosed",obj));
 	   		invocationContext.target =TARGET_SUCCESS; 
 	        log.exiting("OffloadSearchContainerCommand","execute");
 	   		return;
		}
 		
 		offloadSession.setFlightValidationVO(flightValidationVO);
//      Validating Flight to obtain the Flight Validation VO - END
 		
        searchContainerForm.setStatus(CONST_OFFLOAD);
    	
        invocationContext.target = TARGET_SUCCESS;
       	
        log.exiting("OffloadSearchContainerCommand","execute");
    	
    }
       
}
