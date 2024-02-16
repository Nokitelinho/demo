/*
 * ReassignContainerCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ReassignContainerCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";
   private static final String REASSIGN_SCREEN_ID = "mailtracking.defaults.reassignContainer";    
   private static final String CONST_SHOW_REASSIGN = "showReassignPopup";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OUTBOUND = "O";
   private static final String TARGET_SUCCESS = "reassign_container_success";
   private static final String TARGET_FAILURE = "reassign_container_failure";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ReassignContainerCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME,REASSIGN_SCREEN_ID);
    	
    	reassignContainerSession.setSelectedContainerVOs(null);
    	
    	String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		// IF CONTAINER ASSIGNED TO FLIGHT
    	if (CONST_FLIGHT.equals(assignedto)) {   
    		
    		FlightValidationVO flightValidationVO = assignContainerSession.getFlightValidationVO();   	
        	
        	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
        		new MailTrackingDefaultsDelegate();
        	        	
        	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
        	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
        	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
        	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
        	operationalFlightVO.setDirection(OUTBOUND);
        	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
        	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
        	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
        	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	operationalFlightVO.setPol(logonAttributes.getAirportCode());
        	        	        	
        	boolean isFlightClosed = false;
        	 
        	try {
        			isFlightClosed = 
        				mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);
        		
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			log.exiting("ReassignContainerCommand","execute");
    			return;
    		}
    		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
			if (isFlightClosed) {
    			assignContainerForm.setStatus(""); 
    			errors = new ArrayList<ErrorVO>();
    			Object[] obj = {flightValidationVO.getCarrierCode(),
    					flightValidationVO.getFlightNumber(),
    					flightValidationVO.getApplicableDateAtRequestedAirport().toString().substring(0,11)};
    			errors.add(new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj));
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			log.exiting("ReassignContainerCommand","execute");
    			return;				
    		}  
    	}
    	
    	Collection<ContainerVO> selectedContainerVOs = new ArrayList<ContainerVO>();
    	Collection<ContainerVO> containerVOs = assignContainerSession.getContainerVOs();
    	String[] selectedRows = assignContainerForm.getSubCheck();    	
    	int size = selectedRows.length;
    	int row = 0;
    	for (ContainerVO containervo : containerVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				
    				selectedContainerVOs.add(containervo);    				
    			}	    
    		} 
    		row++;
    	}
    	
    	log.log(Log.FINE, "Selected ContainerVOs ------------> ",
				selectedContainerVOs);
		// VALIDATE WHETHER ANY UNSAVED CONTAINERS PRESENT
    	for (ContainerVO selectedvo : selectedContainerVOs) {
    		if (ContainerVO.OPERATION_FLAG_INSERT.equals(selectedvo.getOperationFlag())) {
    			
    			errors = new ArrayList<ErrorVO>();
    			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.assigncontainer.msg.err.containersNotSaved");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				log.exiting("ReassignContainerCommand","execute");
				return;
    			
    		}
    	}
    	
    	 int errorOfl = 0;
         String contOfl = "";
         int errorArr = 0;
         String contArr = "";
         int errorInReassign=0;
 		String contAlreadyManifest = "";
    	for (ContainerVO selectedvo : selectedContainerVOs) {
    		if (selectedvo.getContainerNumber() != null ) {

    	    	// VALIDATION FOR CONTAINERS HOLDING OFFLOADED MAILS
    			if (selectedvo.getContainerNumber().startsWith("OFL")) {
	   				errorOfl = 1;
       				if("".equals(contOfl)){
       					contOfl = selectedvo.getContainerNumber();
	       			}else{
	       				contOfl = new StringBuilder(contOfl).append(",").append(selectedvo.getContainerNumber()).toString();	
	       			}
	   			}
    	    	// VALIDATION FOR ARRIVED CONTAINERS
	   			if("Y".equals(selectedvo.getArrivedStatus())){
	   				errorArr = 1;
       				if("".equals(contArr)){
       					contArr = selectedvo.getContainerNumber();
	       			}else{
	       				contArr = new StringBuilder(contArr).append(",").append(selectedvo.getContainerNumber()).toString();	
	       			}
	   			}
	        	// VALIDATE WHETHER ANY TRANSIT CONTAINERS PRESENT
				if(MailConstantsVO.BULK_TYPE.equals(selectedvo.getType()) && 
						MailConstantsVO.FLAG_NO.equals(selectedvo.getTransitFlag())) {
					errorInReassign = 1;
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
    	
    	if(errorOfl == 1){
	   		invocationContext.addError(new ErrorVO(
	   				"mailtracking.defaults.msg.err.containersHoldOffloadedMails",new Object[]{contOfl}));
	   		invocationContext.target = TARGET_FAILURE;
			log.exiting("ReassignContainerCommand","execute");
			return;
	   	}
	   	
	   	if(errorArr == 1){
	   		invocationContext.addError(new ErrorVO(
	   				"mailtracking.defaults.msg.err.reassigncontainersarrived",new Object[]{contArr}));
	   		invocationContext.target = TARGET_FAILURE;
			log.exiting("ReassignContainerCommand","execute");
			return;
	   	}

		if(errorInReassign == 1){
  	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.cannnotreassigntransitcontainer",new Object[]{contAlreadyManifest}));  	    	     	    	
			invocationContext.target = TARGET_FAILURE;
			log.exiting("ReassignContainerCommand","execute");
			return;		
       }
    	
    	reassignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
    	reassignContainerSession.setContainerVO(new ContainerVO());
    	assignContainerForm.setStatus(CONST_SHOW_REASSIGN);
    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ReassignContainerCommand","execute");
    	
    }
}
