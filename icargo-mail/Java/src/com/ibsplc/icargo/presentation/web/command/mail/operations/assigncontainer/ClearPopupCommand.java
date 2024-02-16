/*
 * ClearPopupCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ClearPopupCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String TARGET_SUCCESS = "clear_popup_success"; 
   private static final String CONST_ULD = "U";
   private static final String CONST_CREATE = "CREATE";
   private static final String CONST_FLIGHT = "FLIGHT";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearPopupCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	
    	ArrayList<ContainerVO> selectedContainerVOs = (ArrayList<ContainerVO>)assignContainerSession.getSelectedContainerVOs();
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		int currentindex = assignContainerForm.getCurrentIndex();
		
		selectedContainerVOs.remove(currentindex);		
    	
		ContainerVO containerVO = new ContainerVO();
		containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
		containerVO.setCompanyCode(logonAttributes.getCompanyCode());		
		containerVO.setAssignedPort(logonAttributes.getAirportCode());
		containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);
		containerVO.setAssignedDate(date);
		containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		containerVO.setOnwardRoutings(null);
		
		String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		if (CONST_FLIGHT.equals(assignedto)) { 
    		
    		FlightValidationVO flightValidationVO = assignContainerSession.getFlightValidationVO();    		
    		containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());			
			containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
			containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());  
			assignContainerForm.setContainerDestination("");
    	}
    	else {
    		AirlineValidationVO airlineValidationVO = assignContainerSession.getAirlineValidationVO();
    		containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
    	}
		
		selectedContainerVOs.add(currentindex,containerVO);
				
		//assignContainerForm.setCurrentIndex(selectedContainerVOs.size()-1);
        	   	
    	//assignContainerForm.setCurrentAction("");    	
    	assignContainerForm.setCurrentDialogOption("");
    	assignContainerForm.setCurrentDialogId("");    	
    	assignContainerForm.setOverrideFlag("");
    	assignContainerForm.setWarningCode("");
    	assignContainerForm.setContainerNumber("");
		assignContainerForm.setPou("");
		assignContainerForm.setPaBuilt("N");
		assignContainerForm.setRemarks("");		
    	
    	if (CONST_CREATE.equals(assignContainerForm.getStatus())) {
			assignContainerForm.setContainerType(CONST_ULD);
		}
    	 
    	log.log(Log.INFO, "SelectedContainerVOs after:-------->>",
				selectedContainerVOs);
		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);   	
    	    	    	    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ClearPopupCommand","execute");
    	
    }
       
}
