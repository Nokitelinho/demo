/*
 * SaveUcmFlightExceptionCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmflightexceptionlist;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */

public class SaveUcmFlightExceptionCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ucmflightexceptionlist";
    
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	UCMFlightExceptionListForm ucmFlightExceptionListForm = 
			(UCMFlightExceptionListForm) invocationContext.screenModel;
		UCMFlightExceptionListSession ucmFlightExceptionListSession = 
			(UCMFlightExceptionListSession)getScreenSession(MODULE,SCREENID);
		
    	if(invocationContext.getErrors() != null
    			&& invocationContext.getErrors().size() > 0) {
    		updateLastRecord(ucmFlightExceptionListForm,
    				ucmFlightExceptionListSession);
    		ucmFlightExceptionListForm.setScreenStatusFlag(
    				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = SAVE_FAILURE;
    		return;
    	}
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
		ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs =
			ucmFlightExceptionListSession.getUcmExceptionFlightVOs();
		ucmFlightExceptionListForm.setDuplicateFlightStatus(
				AbstractVO.FLAG_NO);
		ucmFlightExceptionListForm.setActionStatus(
				"");
		if(ucmExceptionFlightVOs != null &&
				ucmExceptionFlightVOs.size() > 0) {
			for(UCMExceptionFlightVO ucmExceptionFlightVO :
				ucmExceptionFlightVOs) {
				ucmExceptionFlightVO.setCompanyCode(compCode);
			}
			try {
				new ULDDefaultsDelegate().saveExceptionFlights(
						ucmExceptionFlightVOs);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
		}
		ucmFlightExceptionListForm.setAirportCode("");
		ucmFlightExceptionListSession.removeUcmExceptionFlightVOs();
		ucmFlightExceptionListForm.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	
		
		ErrorVO error = new ErrorVO("uld.defaults.ucmFlightExceptionList.savedsuccessfully");
		ucmFlightExceptionListForm.setListStatus("");
     	error.setErrorDisplayType(ErrorDisplayType.STATUS);
     	invocationContext.addError(error);     	
		invocationContext.target = SAVE_SUCCESS;
        
    }
    
    private void updateLastRecord(
    		UCMFlightExceptionListForm ucmFlightExceptionListForm, 
    		UCMFlightExceptionListSession ucmFlightExceptionListSession) {
		String[] carrierCodes = ucmFlightExceptionListForm.getFlightCarrier();
		String[] flightNumbers = ucmFlightExceptionListForm.getFlightNumber();
		String[] flightDates = ucmFlightExceptionListForm.getFlightDate();
		if(flightNumbers != null && flightNumbers.length > 0) {		
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
			UCMExceptionFlightVO ucmExceptionFlightVO =
				ucmFlightExceptionListSession.getUcmExceptionFlightVOs().get(lastVosIndex);
			if(carrierCodes[lastIndex] != null) {
				ucmExceptionFlightVO.setCarrierCode(carrierCodes[lastIndex].toUpperCase());
			}
			if(flightNumbers[lastIndex] != null) {
				ucmExceptionFlightVO.setFlightNumber(flightNumbers[lastIndex].toUpperCase());
			}
			ucmExceptionFlightVO.setPol(ucmFlightExceptionListForm.getAirportCode().toUpperCase());
			if(flightDates[lastIndex] != null &&
					flightDates[lastIndex].trim().length() > 0) {
				LocalDate flightDate = new LocalDate(ucmFlightExceptionListForm.getAirportCode().toUpperCase(),
						Location.ARP,false);
				flightDate.setDate(flightDates[lastIndex].toUpperCase());			
				ucmExceptionFlightVO.setFlightDate(flightDate);			
			}
		}
	}
  
	
}
