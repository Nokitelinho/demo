/*
 * ValidateFlightCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ValidateFlightCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   private static final String OUTBOUND = "O";
   private static final String CONST_DELETE = "DELETE";
   private static final String CONST_MODIFY = "MODIFY";
   private static final String CONST_CREATE = "CREATE";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OPERFLAG_INSERT_UPDATE = "IU";
   private static final String OPERFLAG_INSERT_DELETE = "ID";
   private static final String CONST_SHOWPOPUP = "showAddPopup";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "validate_success";
   private static final String TARGET_FAILURE = "validate_failure";
 
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ValidateFlightCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		String mode = assignContainerForm.getFlightStatus();
    	log.log(Log.INFO, "Mode:------------>>", mode);
		assignContainerSession.setSelectedContainerVOs(null);
    	
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
        	//operationalFlightVO.setPou();
        	        	        	
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
    			return;
    		}
    		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
			if (isFlightClosed) {
    			assignContainerForm.setStatus(""); 
    			
    			Object[] obj = {flightValidationVO.getCarrierCode(),
    					flightValidationVO.getFlightNumber(),
    					flightValidationVO.getApplicableDateAtRequestedAirport().toString().substring(0,11)};
    			ErrorVO errorVO = new ErrorVO(
    					"mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj);
    			errors = new ArrayList<ErrorVO>();
    			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;				
    		}    		
    		else {
    			if (CONST_DELETE.equals(mode)) {
    				errors = isMailBagsPresent(
    						assignContainerForm,
    						assignContainerSession);
    				if (errors != null && errors.size() > 0) {
    					assignContainerForm.setStatus("");
            			invocationContext.addAllError(errors);
            			invocationContext.target = TARGET_FAILURE;
            			return;
            		}    				
    			}
    			else {    				
    				if (CONST_MODIFY.equals(mode)) {
    					
    					try {
    						handleSelectedContainers(
    								assignContainerForm,
    								assignContainerSession,
    								logonAttributes);
    					}catch (SystemException systemException) {
    						systemException.getMessage();
    					}
    				}
    				else if (CONST_CREATE.equals(mode)) {
    					Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
    					ContainerVO containerVO = new ContainerVO();
    					containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
    					containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    					containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
    					containerVO.setCompanyCode(flightValidationVO.getCompanyCode());
    					containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
    					containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    					containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());  
    					containerVO.setAssignedPort(logonAttributes.getAirportCode());
    					containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
    					LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
    					containerVO.setAssignedDate(date);
    					containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
    					newContainerVOs.add(containerVO);
    					assignContainerSession.setSelectedContainerVOs(newContainerVOs);
    				}
    				assignContainerForm.setStatus(CONST_SHOWPOPUP);    				
    			}					
    		} 
    	}
    	// IF CONTAINER ASSIGNED TO DESTINATION
    	else {
    		if (CONST_DELETE.equals(mode)) {
				errors = isMailBagsPresent(
						assignContainerForm,
						assignContainerSession);
				if (errors != null && errors.size() > 0) {
					assignContainerForm.setStatus("");
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
			}
			else {
				if (CONST_MODIFY.equals(mode)) {
				
					try {
						handleSelectedContainers(
								assignContainerForm,
								assignContainerSession,
								logonAttributes);
					}catch (SystemException systemException) {
						systemException.getMessage();
					}					
				}
				else if (CONST_CREATE.equals(mode)) {
					AirlineValidationVO airlineValidationVO = assignContainerSession.getAirlineValidationVO();
					Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
					ContainerVO containerVO = new ContainerVO();
					containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
					containerVO.setCompanyCode(logonAttributes.getCompanyCode());
					containerVO.setAssignedPort(logonAttributes.getAirportCode());
					containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
					LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
					containerVO.setAssignedDate(date);
					containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
					containerVO.setCarrierCode(airlineValidationVO.getAlphaCode());
					containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					log.log(Log.INFO, "getDestination :----------->>",
							assignContainerForm.getDestn());
					containerVO.setFinalDestination(assignContainerForm.getDestn());
					log
							.log(Log.INFO, "containerVO :----------->>",
									containerVO);
					newContainerVOs.add(containerVO);
					assignContainerSession.setSelectedContainerVOs(newContainerVOs);
				}				
				assignContainerForm.setStatus(CONST_SHOWPOPUP);
			}			
    	}
    	
    	log.log(Log.INFO, "ContainerVOS selected :----------->>",
				assignContainerSession.getSelectedContainerVOs());
		invocationContext.target = TARGET_SUCCESS;	
       	
    	log.exiting("ValidateFlightCommand","execute");
    	
    }
    /**
     * Method used to get the selected containers
     * @param assignContainerForm
     * @param assignContainerSession
     * @param logonAttributes
     * @throws SystemException
     */
    private void handleSelectedContainers(
    		AssignContainerForm assignContainerForm,
    		AssignContainerSession assignContainerSession,
    		LogonAttributes logonAttributes) 
    		throws SystemException{
    	
    	log.entering("ValidateFlightCommand","getSelectedContainers");
    	
    	Collection<ContainerVO> selectedContainerVOs = new ArrayList<ContainerVO>();
    	Collection<ContainerVO> containerVOs = assignContainerSession.getContainerVOs();
    	String[] selectedRows = assignContainerForm.getSubCheck();    	
    	int size = selectedRows.length;
    	int row = 0;
    	for (ContainerVO containervo : containerVOs) {
    		ContainerVO newcontainervo = new ContainerVO();
    		BeanHelper.copyProperties(newcontainervo,containervo);
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				if (ContainerVO.OPERATION_FLAG_INSERT.equals(newcontainervo.getOperationFlag())) {
    					newcontainervo.setOperationFlag(OPERFLAG_INSERT_UPDATE);
    				}
    				else {    					
    					newcontainervo.setOperationFlag(ContainerVO.OPERATION_FLAG_UPDATE);
    				}    	
    				newcontainervo.setAssignedPort(logonAttributes.getAirportCode());
    				selectedContainerVOs.add(newcontainervo);    				
    			}	    
    		}
    		row++;
    	}
    	assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
    }
    /**
     * Method ised to check whether the containers selected
     * to delete contains mails bags or not
     * @param assignContainerForm
     * @param assignContainerSession
     * @return
     */
    private Collection<ErrorVO> isMailBagsPresent(
    		AssignContainerForm assignContainerForm,
    		AssignContainerSession assignContainerSession) {
    	
    	log.entering("ValidateFlightCommand","handleMailBags");
    	    	
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
    	
    	Collection<ContainerVO> containerVOs = assignContainerSession.getContainerVOs();
    	Collection<ContainerVO> updatedvos = new ArrayList<ContainerVO>();
    	String[] selectedRows = assignContainerForm.getSubCheck();    	
    	int size = selectedRows.length;
    	int row = 0;
    	boolean isBagsPresent = false;
    	StringBuilder errordata =  new StringBuilder();
    	for (ContainerVO containervo : containerVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				if (containervo.getBags() >= 0 && containervo.getAcceptanceFlag()!=null) {
    					
    					isBagsPresent = true;
    					errordata.append(containervo.getContainerNumber()).append(",");    					
    				}
    				else {
    					if (ContainerVO.OPERATION_FLAG_INSERT.equals(containervo.getOperationFlag())) {
    						containervo.setOperationFlag(OPERFLAG_INSERT_DELETE);
    					}
    					
    					else {
    						containervo.setOperationFlag(ContainerVO.OPERATION_FLAG_DELETE);
     
    					}    					
    				}
    			}	    
    		}
    		if (!OPERFLAG_INSERT_DELETE.equals(containervo.getOperationFlag())) {
				updatedvos.add(containervo);
			}
    		row++;
    	}
    	
    	if (isBagsPresent) {
    		Object[] obj = {errordata.substring(0,errordata.length()-1)};    		
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.mailacceptance.cannotdeletecontainers",obj);	    	    			
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			validationerrors.add(errorVO);
    	}
    	
    	log.log(Log.INFO, "containerVOs after updation--------->>", updatedvos);
		assignContainerSession.setContainerVOs(updatedvos);
    	
    	return validationerrors;
    }
       
}
