/*
 * AcceptMailCommand.java Created on Jun 30 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class AcceptMailCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = 
	   							"mailtracking.defaults.assignContainer";
   private static final String ACCEPTMAIL_SCREEN_ID = 
	   							"mailtracking.defaults.mailacceptance";
    
   private static final String CONST_SHOW_ACCEPTMAIL = "showAcceptMail";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OUTBOUND = "O";
   
   private static final String TARGET_SUCCESS = "acceptmail_success";
   private static final String TARGET_FAILURE = "acceptmail_failure";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AcceptMailCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,ACCEPTMAIL_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		
		Collection<ContainerVO> containerVOs = 
			assignContainerSession.getContainerVOs();
		
		//	VALIDATE WHETHER THERE ARE UNSAVED CONTAINERS 
		if(containerVOs != null && containerVOs.size() > 0){
	    	for (ContainerVO containerVO : containerVOs) {
	    		if (ContainerVO.OPERATION_FLAG_INSERT.equals(
	    				containerVO.getOperationFlag())) {
	    			
	    			errors = new ArrayList<ErrorVO>();
	    			ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.containersNotSavedForAccept");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAILURE;
					return;
	    			
	    		}
	    	}
		}
    	
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setPol(logonAttributes.getAirportCode());
		
		String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		if (CONST_FLIGHT.equals(assignedto)) { 
			
			FlightValidationVO flightValidationVO = 
				assignContainerSession.getFlightValidationVO();
			mailAcceptanceSession.setFlightValidationVO(flightValidationVO);
			
			operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
			operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
			operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
			operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			operationalFlightVO.setDirection(OUTBOUND);
		}
		else {
			
			AirlineValidationVO airlineValidationVO = 
				assignContainerSession.getAirlineValidationVO();
			
			operationalFlightVO.setCarrierCode(assignContainerForm.getCarrier().toUpperCase());
			operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			operationalFlightVO.setPou(assignContainerForm.getDestn().toUpperCase());			
            operationalFlightVO.setFlightNumber("-1");
            operationalFlightVO.setLegSerialNumber(-1);
            operationalFlightVO.setFlightSequenceNumber(-1);
		}
		
		mailAcceptanceSession.setOperationalFlightVO(operationalFlightVO);
    	
    	assignContainerForm.setStatus(CONST_SHOW_ACCEPTMAIL);
    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("AcceptMailCommand","execute");
    	
    }
}
