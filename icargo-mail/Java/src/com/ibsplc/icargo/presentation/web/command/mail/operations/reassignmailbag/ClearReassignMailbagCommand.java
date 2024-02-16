/*
 * ClearReassignMailbagCommand.java 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmailbag;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailbagForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class ClearReassignMailbagCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "clear_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.reassignmailbag";	
	   private static final String CONST_FLIGHT = "FLIGHT";
	    
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ClearReassignMailbagCommand","execute");
	    	  
	    	ReassignMailbagForm reassignMailbagForm = 
	    		(ReassignMailbagForm)invocationContext.screenModel;
	    	ReassignMailbagSession reassignMailbagSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
			reassignMailbagSession.setContainerVOs(containerVOs);
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			reassignMailbagSession.setFlightValidationVO(flightValidationVO);
			reassignMailbagForm.setDeparturePort(logonAttributes.getAirportCode());
			
			if("FLIGHT".equals(reassignMailbagForm.getHideRadio())){
				reassignMailbagForm.setAssignToFlight("DESTINATION");
			}else if("CARRIER".equals(reassignMailbagForm.getHideRadio())){
				reassignMailbagForm.setAssignToFlight(CONST_FLIGHT);
				
			}
			
			reassignMailbagForm.setFlightCarrierCode("");
			reassignMailbagForm.setFlightNumber("");
			reassignMailbagForm.setDepDate("");
			reassignMailbagForm.setCarrierCode("");
			reassignMailbagForm.setDestination("");
			reassignMailbagForm.setDuplicateFlightStatus(FLAG_NO);
			reassignMailbagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			
			invocationContext.target = TARGET;
	       	
	    	log.exiting("ClearReassignMailbagCommand","execute");
	    	
	    }
}
