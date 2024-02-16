/*
 * UpdateUcmFlightCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmflightexceptionlist;



import java.util.ArrayList;






import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;


import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;


import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;

import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;

import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm;

	
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */

public class UpdateUcmFlightCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ucmflightexceptionlist";
	
	 /*
     * Module name and Screen id for duplicate flight
     */
    private static final String SCREEN_ID_DUPFLIGHT = 
    					"flight.operation.duplicateflight";
    private static final String MODULE_NAME_FLIGHT = 
		"flight.operation";
    
    private static final String UPDATE_SUCCESS = "update_success";
    private static final String UPDATE_ADD_SUCCESS = "update_add_success";
    private static final String UPDATE_SAVE_SUCCESS = "update_save_success";
    private static final String UPDATE_FAILURE = "update_failure";
    
    private static final String ACTION_STATUS_ADD = "add_duplicate";
    private static final String ACTION_STATUS_SAVE = "save_duplicate";
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	if(invocationContext.getErrors() != null
    			&& invocationContext.getErrors().size() > 0) {    		
    		return;
    	}		
    	/*
		 * Obtain the duplicate flight session
		 */
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		UCMFlightExceptionListForm ucmFlightExceptionListForm = 
			(UCMFlightExceptionListForm) invocationContext.screenModel;
		UCMFlightExceptionListSession ucmFlightExceptionListSession = 
			(UCMFlightExceptionListSession)getScreenSession(MODULE,SCREENID);
		
		FlightValidationVO flightValidationVO =
			duplicateFlightSession.getFlightValidationVO();				
		String[] carrierCodes = ucmFlightExceptionListForm.getFlightCarrier();
		String[] flightDates = ucmFlightExceptionListForm.getFlightDate();
		
		if(flightDates != null && flightDates.length > 0) {
			int lastIndex = flightDates.length - 1;
			int lastVosIndex = -1;
			ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs =
				ucmFlightExceptionListSession.getUcmExceptionFlightVOs();
			int noOfElements = ucmExceptionFlightVOs.size();
			for(int i = noOfElements - 1; i >= 0; i-- ) {
				if(!AbstractVO.OPERATION_FLAG_DELETE.equals(
						ucmExceptionFlightVOs.get(i).getOpeartionalFlag())) {
					lastVosIndex = i;
					break;
				}
			}
			if(lastVosIndex < 0) {
				return;
			}
			UCMExceptionFlightVO ucmExceptionFlightVO =
				ucmFlightExceptionListSession.getUcmExceptionFlightVOs().get(lastVosIndex);
			if(ucmExceptionFlightVO.getFlightSequenceNumber() > 0) {
				return;
			}
			
			ucmExceptionFlightVO.setCarrierCode(carrierCodes[lastIndex].toUpperCase());
			ucmExceptionFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			ucmExceptionFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
			ucmExceptionFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			ucmExceptionFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			ucmExceptionFlightVO.setPol(ucmFlightExceptionListForm.getAirportCode().toUpperCase());
			LocalDate flightDate = new LocalDate(ucmFlightExceptionListForm.getAirportCode().toUpperCase(),
					Location.ARP,false);
			flightDate.setDate(flightDates[lastIndex].toUpperCase());
			ucmExceptionFlightVO.setFlightDate(flightDate);			
		}
		if(ACTION_STATUS_ADD.equals(
				ucmFlightExceptionListForm.getActionStatus())) {
			invocationContext.target = UPDATE_ADD_SUCCESS;
		}
		else if(ACTION_STATUS_SAVE.equals(
				ucmFlightExceptionListForm.getActionStatus())) {
			invocationContext.target = UPDATE_SAVE_SUCCESS;
		}
		else {
			invocationContext.target = UPDATE_SUCCESS;
		}
        
    }
  
	
}
