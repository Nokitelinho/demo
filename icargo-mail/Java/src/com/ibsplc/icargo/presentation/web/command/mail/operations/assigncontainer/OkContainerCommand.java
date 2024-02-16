/*
 * OkContainerCommand.java Created on Jun 30 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory; 


/**
 * @author A-5991
 *
 */
public class OkContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String MODULE_NAME_TM = "mail.operations";	
   private static final String SCREEN_ID_TM = "mailtracking.defaults.transfermail";
   private static final String MODULE_NAME_RM = "mail.operations";	
   private static final String SCREEN_ID_RM = "mailtracking.defaults.reassignmail";
   private static final String TARGET_SUCCESS = "ok_success";
   private static final String OPERFLAG_INSERT_UPDATE = "IU";
   private static final String SCREEN_ID_CF = "mailtracking.defaults.mailarrival";
   private static final String CHANGE_FLIGHT = "CHANGE_FLIGHT";
   private static final String CHANGE_FLIGHT_CLOSE = "CHANGE_FLIGHT_CLOSE";
   private static final String BULK_NAME = "BULK-";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("OkContainerCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID); 
    	
    	TransferMailSession transferMailSession = 
    		getScreenSession(MODULE_NAME_TM,SCREEN_ID_TM);
    	
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME_RM,SCREEN_ID_RM);
    	
    	MailArrivalSession mailArrivalSession = 
        		getScreenSession(MODULE_NAME,SCREEN_ID_CF);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		if("TRANSFER_MAIL".equals(assignContainerForm.getFromScreen())){
			containerVOs = transferMailSession.getContainerVOs();
		}else if("REASSIGN_MAIL".equals(assignContainerForm.getFromScreen())){
			containerVOs = reassignMailSession.getContainerVOs();
		}else{
		    containerVOs = assignContainerSession.getContainerVOs();
		}
		log.log(Log.INFO, "containerVOs before:-------->>", containerVOs);
		/*
		 * If there are no containervos present,add all the vos 
		 * to the parent collection
		 */
		if (containerVOs == null) {
			containerVOs = new ArrayList<ContainerVO>();
			for (ContainerVO selectedvo : selectedContainerVOs) {
				selectedvo.setAssignedUser(logonAttributes.getUserId().toUpperCase());
				LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
				selectedvo.setAssignedDate(date);
				selectedvo.setCompanyCode(logonAttributes.getCompanyCode());
			}
			containerVOs.addAll(selectedContainerVOs);
		}		
		else {
			/*
			 * If there are vos in the parent collection,
			 * add the new vos, update the old vos and
			 * update the ones with I flag of selected for modification
			 */
			try {
				for (ContainerVO selectedvo : selectedContainerVOs) {			
					if (ContainerVO.OPERATION_FLAG_INSERT.equals(selectedvo.getOperationFlag())
							|| selectedvo.getOperationFlag() == null) {
						selectedvo.setAssignedUser(logonAttributes.getUserId().toUpperCase());
						LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
						selectedvo.setAssignedDate(date);
						selectedvo.setCompanyCode(logonAttributes.getCompanyCode());
						containerVOs.add(selectedvo);
					}
					else if (ContainerVO.OPERATION_FLAG_UPDATE.equals(selectedvo.getOperationFlag())) {
						for (ContainerVO vo : containerVOs) {							
							if (selectedvo.getContainerNumber().equals(vo.getContainerNumber())) {
								//vo = selectedvo;								
								BeanHelper.copyProperties(vo,selectedvo);										
								break;
							}							
						}					
					}
					else if (OPERFLAG_INSERT_UPDATE.equals(selectedvo.getOperationFlag())) {						
						for (ContainerVO vo : containerVOs) {
							if (selectedvo.getContainerNumber().equals(vo.getContainerNumber())) {
								selectedvo.setAssignedUser(logonAttributes.getUserId().toUpperCase());
								LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
								selectedvo.setAssignedDate(date);
								selectedvo.setCompanyCode(logonAttributes.getCompanyCode());
								selectedvo.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
								//vo = selectedvo;								
								BeanHelper.copyProperties(vo,selectedvo);								
								break;
							}
						}						
					}				
				}
			}catch (SystemException systemException) {
				systemException.getMessage();
			}
		}			
				
		
		log.log(Log.INFO,
				"assignContainerForm.getFromScreen() before:-------->>",
				assignContainerForm.getFromScreen());
		if("TRANSFER_MAIL".equals(assignContainerForm.getFromScreen())){
			
			transferMailSession.setContainerVOs(containerVOs);
	    	if (containerVOs != null && containerVOs.size() > 0) {
	    		/*
	    		 * Construct lock vos for implicit locking
	    		 */
	    		Collection<LockVO> locks = prepareLocksForSave(containerVOs,
	    			LockConstants.ACTION_ASSIGNCONTAINER);
	    		log.log(Log.FINE, "LockVO for implicit check", locks);
				try {
	    			FlightValidationVO flightValidationVO = transferMailSession.getFlightValidationVO();
	    			
	    			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
	    			/*OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
	    			operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
	    			operationalFlightVO.setPol(logonAttributes.getAirportCode());
	    			operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
					operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
					operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
					operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					operationalFlightVO.setDirection("O");*/
	    			
	    			mailAcceptanceVO.setFlightCarrierCode(flightValidationVO.getCarrierCode());
					mailAcceptanceVO.setFlightNumber(flightValidationVO.getFlightNumber());
					mailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					mailAcceptanceVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					mailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					mailAcceptanceVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
					mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
					mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());	
					mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
					mailAcceptanceVO.setPreassignNeeded(false);
					
					Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
					
					 for (ContainerVO vo : containerVOs) {						 
						    ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						    mailAcceptanceVO.setDestination(vo.getPou());
						    containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							containerDetailsVO.setContainerNumber(vo.getContainerNumber());
							containerDetailsVO.setContainerType(vo.getType());
							containerDetailsVO.setCarrierId(vo.getCarrierId());
							/**Changed by A-4809 for ICRD-98214 from flightvalidationVO to vo 
							passed from validateContainercommand..Start
							**/
							containerDetailsVO.setFlightNumber(vo.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
							containerDetailsVO.setPol(vo.getAssignedPort());
							containerDetailsVO.setLegSerialNumber(vo.getLegSerialNumber());
							containerDetailsVO.setFlightDate(vo.getFlightDate());
							containerDetailsVO.setSegmentSerialNumber(vo.getSegmentSerialNumber());
							if(vo.isReassignFlag()){
							containerDetailsVO.setReassignFlag(true); 
							}
							//Changed by A-4809 for ICRD-98214..Ends
							containerDetailsVO.setOwnAirlineCode(vo.getOwnAirlineCode());
							containerDetailsVO.setAcceptedFlag("Y");
							containerDetailsVO.setArrivedStatus("N");
							containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
							containerDetailsVO.setPou(vo.getPou());
							if("Y".equals(vo.getAcceptanceFlag())){
								containerDetailsVO.setOperationFlag("U");
								containerDetailsVO.setContainerOperationFlag("U");
							}
							else{								
								containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
								containerDetailsVO.setOperationFlag("I");
								containerDetailsVO.setContainerOperationFlag("I");
							}
							containerDetailsVO.setWareHouse(vo.getWarehouseCode());
							containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
							containerDetailsVO.setDestination(vo.getFinalDestination());
							containerDetailsVO.setPaBuiltFlag(vo.getPaBuiltFlag());
							containerDetailsVO.setPaCode(vo.getShipperBuiltCode());
							containers.add(containerDetailsVO);
        				 
        		    }
					 mailAcceptanceVO.setContainerDetails(containers);
					 
					 
		    		
					 /*new MailTrackingDefaultsDelegate().saveContainers(
		    				operationalFlightVO,containerVOs,locks);*/
					 new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
				}catch (BusinessDelegateException businessDelegateException) {
					 handleDelegateException(businessDelegateException);
				}
	    	}
			assignContainerForm.setFromScreen("TRANSFER_MAIL_CLOSE");
			
		}if("REASSIGN_MAIL".equals(assignContainerForm.getFromScreen())){
			reassignMailSession.setContainerVOs(containerVOs);
	    	if (containerVOs != null && containerVOs.size() > 0) {
	    		/*
	    		 * Construct lock vos for implicit locking
	    		 */
	    		Collection<LockVO> locks = prepareLocksForSave(containerVOs,LockConstants.ACTION_ASSIGNCONTAINER);
	    		log.log(Log.FINE, "LockVO for implicit check", locks);
				String assignedto = assignContainerForm.getAssignedto();
	        	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
				//OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
    			//operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    			//operationalFlightVO.setPol(logonAttributes.getAirportCode());
    			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
	        	if ("FLIGHT".equals(assignedto)) {  
	    	
	    			FlightValidationVO flightValidationVO = reassignMailSession.getFlightValidationVO();
	    			/*
	    			operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
					operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
					operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
					operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					operationalFlightVO.setDirection("O");*/
	    			mailAcceptanceVO.setFlightCarrierCode(flightValidationVO.getCarrierCode());
					mailAcceptanceVO.setFlightNumber(flightValidationVO.getFlightNumber());
					mailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					mailAcceptanceVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					mailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					mailAcceptanceVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
					Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
					for (ContainerVO vo : selectedContainerVOs) {//Modified for ICRD-103379
						ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());	
						mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
						mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
						mailAcceptanceVO.setPreassignNeeded(false);
						mailAcceptanceVO.setDestination(vo.getPou());

						containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
						containerDetailsVO.setContainerNumber(vo.getContainerNumber());
						containerDetailsVO.setContainerType(vo.getType());
						if (vo.isReassignFlag()) {
							containerDetailsVO.setReassignFlag(true);
							containerDetailsVO.setCarrierId(vo.getCarrierId());
							containerDetailsVO.setFlightNumber(vo.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
							containerDetailsVO.setLegSerialNumber(vo.getLegSerialNumber());
							containerDetailsVO.setFlightDate(vo.getFlightDate());
							containerDetailsVO.setCarrierCode(vo.getCarrierCode());

						} else {
							containerDetailsVO.setCarrierId(flightValidationVO.getFlightCarrierId());
							containerDetailsVO.setFlightNumber(flightValidationVO.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
							containerDetailsVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
							containerDetailsVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
							containerDetailsVO.setCarrierCode(flightValidationVO.getCarrierCode());
						}						
						containerDetailsVO.setPol(vo.getAssignedPort());
						containerDetailsVO.setSegmentSerialNumber(vo.getSegmentSerialNumber());
						containerDetailsVO.setOwnAirlineCode(vo.getOwnAirlineCode());
						containerDetailsVO.setAcceptedFlag("Y");
						containerDetailsVO.setArrivedStatus("N");
						containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
						containerDetailsVO.setPou(vo.getPou());
						if("Y".equals(vo.getAcceptanceFlag())){
							containerDetailsVO.setOperationFlag("U");
							containerDetailsVO.setContainerOperationFlag("U");
						}
						else{								
							containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
							containerDetailsVO.setOperationFlag("I");
							containerDetailsVO.setContainerOperationFlag("I");
						}
						containerDetailsVO.setWareHouse(vo.getWarehouseCode());
						containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
						containerDetailsVO.setDestination(vo.getFinalDestination());
						containerDetailsVO.setPaBuiltFlag(vo.getPaBuiltFlag());
						containerDetailsVO.setPaCode(vo.getShipperBuiltCode());
						containers.add(containerDetailsVO);							

						vo.setCompanyCode(logonAttributes.getCompanyCode());
						vo.setCarrierCode(flightValidationVO.getCarrierCode());
						vo.setCarrierId(flightValidationVO.getFlightCarrierId());
						vo.setFlightNumber(flightValidationVO.getFlightNumber());
						vo.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
						vo.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
						vo.setLegSerialNumber(flightValidationVO.getLegSerialNumber());

					}
					 mailAcceptanceVO.setContainerDetails(containers);

					
	        	}else{
	        		AirlineValidationVO airlineValidationVO = null;
	        	    String carrierCode = assignContainerForm.getCarrier().trim().toUpperCase();
	            	if (carrierCode != null && !"".equals(carrierCode)) {
	            		try {
	            			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
	            					logonAttributes.getCompanyCode(),carrierCode);
	            		}catch (BusinessDelegateException businessDelegateException) {
	            			handleDelegateException(businessDelegateException);
	            		}
	            	}
	            	 mailAcceptanceVO.setFlightCarrierCode(airlineValidationVO.getAlphaCode());
					 mailAcceptanceVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
					 mailAcceptanceVO.setFlightNumber("-1");
					 mailAcceptanceVO.setFlightSequenceNumber(-1);
					 mailAcceptanceVO.setLegSerialNumber(-1);
					 Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();

	                 /*operationalFlightVO.setCarrierCode(airlineValidationVO.getAlphaCode());
	                 operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
	        		 operationalFlightVO.setFlightNumber("-1");
	                 operationalFlightVO.setFlightSequenceNumber(-1);
	                 operationalFlightVO.setLegSerialNumber(-1);*/
					 for (ContainerVO vo : selectedContainerVOs) {//Modified for ICRD-103379

						 ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						 mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
						 mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
						 mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());	
						 mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
						 mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
						 mailAcceptanceVO.setPreassignNeeded(false);

						 containerDetailsVO.setCompanyCode(vo.getCompanyCode());
						 containerDetailsVO.setContainerNumber(vo.getContainerNumber());
						 containerDetailsVO.setContainerType(vo.getType());
							if (vo.isReassignFlag()) {
								containerDetailsVO.setReassignFlag(true);
								containerDetailsVO.setCarrierId(vo.getCarrierId());
								containerDetailsVO.setCarrierCode(vo.getCarrierCode());
								containerDetailsVO.setFlightNumber(vo.getFlightNumber());
								containerDetailsVO.setFlightDate(vo.getFlightDate());
								containerDetailsVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
								containerDetailsVO.setLegSerialNumber(vo.getLegSerialNumber());
								containerDetailsVO.setSegmentSerialNumber(vo.getSegmentSerialNumber());
							} else {
								containerDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
								containerDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
								containerDetailsVO.setFlightNumber("-1");
								containerDetailsVO.setFlightSequenceNumber(-1);
								containerDetailsVO.setLegSerialNumber(-1);
								containerDetailsVO.setSegmentSerialNumber(-1);
								containerDetailsVO.setFlightDate(null);
							}						
						 containerDetailsVO.setAcceptedFlag("Y");
						 containerDetailsVO.setArrivedStatus("N");
						 containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
						 if("Y".equals(vo.getAcceptanceFlag())){
							 containerDetailsVO.setOperationFlag("U");
							 containerDetailsVO.setContainerOperationFlag("U");
						 }
						 else{								
							 containerDetailsVO.setOperationFlag("I");
							 containerDetailsVO.setContainerOperationFlag("I");
							 containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						 }
						 containerDetailsVO.setPol(logonAttributes.getAirportCode());
						 containerDetailsVO.setPou(vo.getPou());
						 containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());		 	    			
						 containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
						 containerDetailsVO.setDestination(vo.getFinalDestination());
						 containerDetailsVO.setPaBuiltFlag(vo.getPaBuiltFlag());
						 containerDetailsVO.setPaCode(vo.getShipperBuiltCode());
						 containerDetailsVO.setWareHouse(vo.getWarehouseCode());
						 containers.add(containerDetailsVO);
						 
	                	 vo.setCompanyCode(logonAttributes.getCompanyCode());
                	     vo.setCarrierCode(airlineValidationVO.getAlphaCode());
                	     vo.setCarrierId(airlineValidationVO.getAirlineIdentifier());
        				 vo.setFlightSequenceNumber(-1);
        				 vo.setSegmentSerialNumber(-1);
        				 vo.setLegSerialNumber(-1);
					 }
	                 mailAcceptanceVO.setContainerDetails(containers);

	        	}
				
				try {	
					new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
		    		//new MailTrackingDefaultsDelegate().saveContainers(
		    		//		operationalFlightVO,containerVOs,locks);

				}catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
	    	}
	    	assignContainerForm.setFromScreen("REASSIGN_MAIL_CLOSE");
		}if(CHANGE_FLIGHT.equals(assignContainerForm.getFromScreen())){
			mailArrivalSession.setContainerVOs(containerVOs);
	    	if (containerVOs != null && containerVOs.size() > 0) {
	    		/*
	    		 * Construct lock vos for implicit locking
	    		 */
	    		Collection<LockVO> locks = prepareLocksForSave(containerVOs,
	    			LockConstants.ACTION_ASSIGNCONTAINER);
	    		log.log(Log.FINE, "LockVO for implicit check", locks);
				try {
	    			FlightValidationVO flightValidationVO = mailArrivalSession.getChangeFlightValidationVO();
	    			MailArrivalVO mailArrivalVO = new MailArrivalVO();
	    			mailArrivalVO.setFlightCarrierCode(flightValidationVO.getCarrierCode());
	    			mailArrivalVO.setFlightNumber(flightValidationVO.getFlightNumber());
	    			mailArrivalVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	    			mailArrivalVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	    			mailArrivalVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	    			mailArrivalVO.setArrivalDate(flightValidationVO.getApplicableDateAtRequestedAirport());
	    			mailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	    			mailArrivalVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
	    			mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());	
	    			mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
	    			mailArrivalVO.setPol(flightValidationVO.getLegOrigin());
	    			mailArrivalVO.setPou(flightValidationVO.getLegDestination());
	    			mailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
					Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
					 for (ContainerVO vo : containerVOs) {					 
						    ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						    mailArrivalVO.setPou(vo.getPou());
						    containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							containerDetailsVO.setContainerType(vo.getType());
							if(MailConstantsVO.BULK_TYPE.equals(vo.getType())){
							containerDetailsVO.setContainerNumber(BULK_NAME+vo.getPou());
		}else{
							containerDetailsVO.setContainerNumber(vo.getContainerNumber());
							}
							containerDetailsVO.setCarrierId(vo.getCarrierId());
							containerDetailsVO.setFlightNumber(vo.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(vo.getFlightSequenceNumber());
							containerDetailsVO.setPol(vo.getPol());
							containerDetailsVO.setLegSerialNumber(vo.getLegSerialNumber());
							containerDetailsVO.setFlightDate(vo.getFlightDate());
							containerDetailsVO.setSegmentSerialNumber(vo.getSegmentSerialNumber());
							if(vo.isReassignFlag()){
							containerDetailsVO.setReassignFlag(true); 
							}
							containerDetailsVO.setOwnAirlineCode(vo.getOwnAirlineCode());
							containerDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_NO);
							containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
							containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
							containerDetailsVO.setPou(vo.getPou());
							if("Y".equals(vo.getAcceptanceFlag())||(vo.getArrivedStatus()!=null)){
								containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								containerDetailsVO.setContainerOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
							}
							else{								
								containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
								containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
								containerDetailsVO.setContainerOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
							}
							containerDetailsVO.setWareHouse(vo.getWarehouseCode());
							containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
							containerDetailsVO.setDestination(vo.getFinalDestination());
							containerDetailsVO.setPaBuiltFlag(vo.getPaBuiltFlag());
							containerDetailsVO.setPaCode(vo.getShipperBuiltCode());
							containers.add(containerDetailsVO);
        		    }
					 mailArrivalVO.setContainerDetails(containers);
					 new MailTrackingDefaultsDelegate().saveArrivalDetails(mailArrivalVO,locks);
				}catch (BusinessDelegateException businessDelegateException) {
					 handleDelegateException(businessDelegateException);
				}
	    	}
			assignContainerForm.setFromScreen(CHANGE_FLIGHT_CLOSE);
		}
		else{
			assignContainerSession.setSelectedContainerVOs(null);
			assignContainerSession.setPointOfLadings(null);
			assignContainerSession.setContainerVOs(containerVOs);
			reassignMailSession.setReassigStatus("true");
			log.log(Log.INFO, "SelectedContainerVOs after:-------->>",
					assignContainerSession.getSelectedContainerVOs());
			log.log(Log.INFO, "ContainerVOs after:-------->>",
					assignContainerSession.getContainerVOs());
		}
		log.log(Log.INFO,
				"assignContainerForm.getFromScreen() after:-------->>",
				assignContainerForm.getFromScreen());
		assignContainerForm.setCurrentAction("CLOSEWINDOW");
    	    	    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("OkContainerCommand","execute");
    	
    }
    
    /*
     * Added by Indu
     */
    private Collection<LockVO> prepareLocksForSave(
    		Collection<ContainerVO> containerVOs, String lockAction) {
    	log.log(Log.FINE, "\n prepareLocksForSave------->>", containerVOs);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<LockVO> locks = new ArrayList<LockVO>();
    
    	if (containerVOs != null && containerVOs.size() > 0) {
    		for (ContainerVO conVO : containerVOs) {
    			ULDLockVO lock = new ULDLockVO();
    			lock.setAction(lockAction);
    			lock.setClientType(ClientType.WEB);
    			lock.setCompanyCode(logonAttributes.getCompanyCode());
    			lock.setScreenId(SCREEN_ID);
    			lock.setStationCode(logonAttributes.getStationCode());
    			lock.setUldNumber(conVO.getContainerNumber());
    			lock.setDescription(conVO.getContainerNumber());
    			lock.setRemarks(conVO.getContainerNumber());
    			log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
    		}
    	}
    	return locks;
    }
    
          
}
