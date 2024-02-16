/*
 * AddNewCommand.java Created on Jun 30 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory; 


/**
 * @author A-5991
 *
 */
public class AddNewCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String MODULE_NAME_TM = "mail.operations";	
   private static final String SCREEN_ID_TM = "mailtracking.defaults.transfermail";
   private static final String MODULE_NAME_RM = "mail.operations";	
   private static final String SCREEN_ID_RM = "mailtracking.defaults.reassignmail";
   private static final String TARGET_SUCCESS = "add_new_success"; 
   private static final String ROUTE_DELIMETER = "-";
   private static final String CONST_TRUE = "Y";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_CREATE = "CREATE";
   private static final String CONST_ULD = "U";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddNewCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);  
    	
    	TransferMailSession transferMailSession = 
    		getScreenSession(MODULE_NAME_TM,SCREEN_ID_TM);
    	
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME_RM,SCREEN_ID_RM);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		if("TRANSFER_MAIL".equals(assignContainerForm.getFromScreen())){
			
			assignContainerForm.setAssignedto(CONST_FLIGHT);
			FlightValidationVO flightValidationVO = transferMailSession.getFlightValidationVO();
			
			ContainerVO containerVO = new ContainerVO();
			log.log(Log.INFO, "flightValidationVO:---TRANSFER_MAIL----->>",
					flightValidationVO);
			if (flightValidationVO.getFlightSequenceNumber() > 0) {
				assignContainerSession.setFlightValidationVO(flightValidationVO);
				/*
				 * Obtain the collection of POUs
				 */
				Collection<String> pointOfUnladings =
					getPointOfUnladings(flightValidationVO.getFlightRoute(), logonAttributes);			
				assignContainerSession.setPointOfLadings(pointOfUnladings);
	//			 flight assigned.
				containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
				containerVO.setCompanyCode(flightValidationVO.getCompanyCode());
				containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				
				assignContainerForm.setAssignedto("FLIGHT");
			}else{
				assignContainerForm.setAssignedto("DESTINATION");
			}
			
			containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
			containerVO.setAssignedPort(logonAttributes.getAirportCode());
			containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			selectedContainerVOs.add(containerVO);
			
		}else if("REASSIGN_MAIL".equals(assignContainerForm.getFromScreen())){
			
			assignContainerForm.setAssignedto(CONST_FLIGHT);
			FlightValidationVO flightValidationVO = reassignMailSession.getFlightValidationVO();
			
			ContainerVO containerVO = new ContainerVO();
			log.log(Log.INFO, "flightValidationVO:---REASSIGN_MAIL----->>",
					flightValidationVO);
			if (flightValidationVO.getFlightSequenceNumber() > 0) {
				assignContainerSession.setFlightValidationVO(flightValidationVO);
				/*
				 * Obtain the collection of POUs
				 */
				Collection<String> pointOfUnladings =
					getPointOfUnladings(flightValidationVO.getFlightRoute(), logonAttributes);			
				assignContainerSession.setPointOfLadings(pointOfUnladings);
	//			 flight assigned.
				containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
				containerVO.setCompanyCode(flightValidationVO.getCompanyCode());
				containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				assignContainerForm.setContainerDestination("");
				assignContainerForm.setAssignedto("FLIGHT");
			}else{
				
				DestinationFilterVO destinationFilterVO = reassignMailSession.getDestinationFilterVO();
				
				containerVO.setCarrierId(destinationFilterVO.getCarrierId());
				containerVO.setCarrierCode(destinationFilterVO.getCarrierCode());
				containerVO.setCompanyCode(destinationFilterVO.getCompanyCode());
				containerVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
				assignContainerForm.setDestn(destinationFilterVO.getDestination());
				assignContainerForm.setAssignedto("DESTINATION");
			}
			
			containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
			containerVO.setAssignedPort(logonAttributes.getAirportCode());
			containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			
			selectedContainerVOs.add(containerVO);
			
		}else{
		
		ContainerVO containerVO = new ContainerVO();
		containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
		containerVO.setCompanyCode(logonAttributes.getCompanyCode());		
		containerVO.setAssignedPort(logonAttributes.getAirportCode());
		containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		containerVO.setAssignedDate(date);
		containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		
		String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		if (CONST_FLIGHT.equals(assignedto)) { 
    		
    		FlightValidationVO flightValidationVO = assignContainerSession.getFlightValidationVO();
    		containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
    		containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
			containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());  
			assignContainerForm.setContainerDestination("");
    	}
    	else {
    		AirlineValidationVO airlineValidationVO = 
    			assignContainerSession.getAirlineValidationVO();
    		containerVO.setCarrierCode(airlineValidationVO.getAlphaCode());
    		containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
    		String destination = null;
    		for(ContainerVO prevCon : selectedContainerVOs) {
    			destination = prevCon.getFinalDestination();
    			break;    			
    		}
    		assignContainerForm.setDestn(destination);
    	}		
				
		selectedContainerVOs.add(containerVO);
		
		}
				
		assignContainerForm.setContainerType(CONST_ULD);
		assignContainerForm.setContainerNumber("");
		assignContainerForm.setBarrowCheck(false);//Added for ICRD-128804 
		assignContainerForm.setPou("");
		assignContainerForm.setPaBuilt("N");
		assignContainerForm.setRemarks("");
		assignContainerForm.setCurrentIndex(selectedContainerVOs.size()-1);
		assignContainerForm.setStatus(CONST_CREATE);
		
		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
		log.log(Log.INFO, "SelectedContainerVOs after:-------->>",
				assignContainerSession.getSelectedContainerVOs());
		invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("AddNewCommand","execute");
    	
    }
    
    
    /**
	 * The method will obtain the collection of POUs
	 * from the route
	 * @param route
	 * @param logonAttributes
	 * @return pointOfUnladings
	 */
	private Collection<String> getPointOfUnladings(
			String route, LogonAttributes logonAttributes) {
		log.exiting("ScreenloadPopupCommand", "getPOUs");

		Collection<String> pointOfUnladings = new ArrayList<String>();
		/*
		 * Split the route string using the delimeter "-"
		 */		
		if(route != null && route.length() > 0){
			String[] stations = route.split(ROUTE_DELIMETER);
			boolean isLoginStation = false;
			for(int index=0; index < stations.length; index++) {
				/*
				 * the pous should start from the current station
				 */
				if (logonAttributes.getAirportCode().equals(stations[index])) {
					isLoginStation = true;
					continue;
				}
				if (isLoginStation) {
					pointOfUnladings.add(stations[index]);
				}				
			}
		}
		log.log(Log.FINE, "pointOfUnladings ---> ", pointOfUnladings);
		log.exiting("ScreenloadPopupCommand", "getPOUs");
		
		return pointOfUnladings;
	}
    
          
}
