/*
 * PreAdviceCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PreAdviceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class PreAdviceCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";
   private static final String PREADVICE_SCREEN_ID = "mailtracking.defaults.preadvice";
   private static final String CONST_SHOW_PREADVICE = "showPreAdvice";
   private static final String OUTBOUND = "O";
   
   private static final String TARGET_SUCCESS = "preadvice_success";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("PreAdviceCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	PreAdviceSession preAdviceSession = 
				getScreenSession(MODULE_NAME,PREADVICE_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	FlightValidationVO flightValidationVO = assignContainerSession.getFlightValidationVO();
		
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	
    	preAdviceSession.setFlightValidationVO(flightValidationVO);
       	preAdviceSession.setOperationalFlightVO(operationalFlightVO);
    	
    	assignContainerForm.setStatus(CONST_SHOW_PREADVICE);
    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("PreAdviceCommand","execute");
    	
    }
}
