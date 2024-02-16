/*
 * ClearTransferMailCommand.java Created on Oct 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermail;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class ClearTransferMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.transfermail";	
   private static final String CONST_FLIGHT = "FLIGHT";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearTransferMailCommand","execute");
    	  
    	TransferMailForm transferMailForm = 
    		(TransferMailForm)invocationContext.screenModel;
    	TransferMailSession transferMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
		
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
		transferMailSession.setContainerVOs(containerVOs);
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		transferMailSession.setFlightValidationVO(flightValidationVO);
		
		log.log(Log.FINE, "transferMailForm.getHideRadio() Before===>",
				transferMailForm.getHideRadio());
		if("FLIGHT".equals(transferMailForm.getHideRadio())){
			transferMailForm.setAssignToFlight("DESTINATION");
		}else if("CARRIER".equals(transferMailForm.getHideRadio())){
			transferMailForm.setAssignToFlight(CONST_FLIGHT);
			
		}else{
			transferMailForm.setHideRadio("NONE");
			transferMailForm.setAssignToFlight(CONST_FLIGHT);
			transferMailForm.setInitialFocus(FLAG_YES);
		}
		
		transferMailForm.setFlightCarrierCode("");
		transferMailForm.setFlightNumber("");
		transferMailForm.setFlightDate("");
		transferMailForm.setCarrierCode("");
		transferMailForm.setDestination("");
		transferMailForm.setDuplicateFlightStatus(FLAG_NO);
		transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		transferMailForm.setHidScanTime("N");
		log.log(Log.FINE, "transferMailForm.getHideRadio() After===>",
				transferMailForm.getHideRadio());
		invocationContext.target = TARGET;
       	
    	log.exiting("ClearTransferMailCommand","execute");
    	
    }
       
}
