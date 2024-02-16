/*
 * ClearReassignMailCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class ClearReassignMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "clear_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignmail";	
   private static final String CONST_FLIGHT = "FLIGHT";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearReassignMailCommand","execute");
    	  
    	ReassignMailForm reassignMailForm = 
    		(ReassignMailForm)invocationContext.screenModel;
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		reassignMailSession.setContainerVOs(containerVOs);
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		reassignMailSession.setFlightValidationVO(flightValidationVO);
		reassignMailForm.setDeparturePort(logonAttributes.getAirportCode());
		
		if("FLIGHT".equals(reassignMailForm.getHideRadio())){
			reassignMailForm.setAssignToFlight("DESTINATION");
		}else if("CARRIER".equals(reassignMailForm.getHideRadio())){
			reassignMailForm.setAssignToFlight(CONST_FLIGHT);
			
		}else{
			reassignMailForm.setHideRadio("NONE");
			reassignMailForm.setAssignToFlight(CONST_FLIGHT);
			reassignMailForm.setInitialFocus(FLAG_YES);
		}
		
		reassignMailForm.setFlightCarrierCode("");
		reassignMailForm.setFlightNumber("");
		reassignMailForm.setDepDate("");
		reassignMailForm.setCarrierCode("");
		reassignMailForm.setDestination("");
		reassignMailForm.setDuplicateFlag("");
		reassignMailForm.setDuplicateFlightStatus(FLAG_NO);
		reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearReassignMailCommand","execute");
    	
    }
       
}
