/*
 * DeleteUcmFlightCommand.java Created on Jul 20,2006
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
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;





import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;

import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;


import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */

public class DeleteUcmFlightCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ucmflightexceptionlist";
    
	private static final String DELETE_SUCCESS = "delete_success";
	private static final String DELETE_FAILURE = "delete_failure";
 
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		UCMFlightExceptionListForm ucmFlightExceptionListForm = 
			(UCMFlightExceptionListForm) invocationContext.screenModel;
		UCMFlightExceptionListSession ucmFlightExceptionListSession = 
			(UCMFlightExceptionListSession)getScreenSession(MODULE,SCREENID);
		ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs = 
			ucmFlightExceptionListSession.getUcmExceptionFlightVOs();
		updateLastRecord(ucmFlightExceptionListForm,
				ucmFlightExceptionListSession);
		if(ucmFlightExceptionListForm.getSelectedRows() != null
				&& ucmFlightExceptionListForm.getSelectedRows().length > 0) {
			 String[] selectedRows = ucmFlightExceptionListForm.getSelectedRows();
			 for(int i = selectedRows.length - 1; i >= 0; i--) {
				 int selectedIndex = Integer.parseInt(selectedRows[i]);
				 if(AbstractVO.OPERATION_FLAG_INSERT.equals(
						 ucmExceptionFlightVOs.get(selectedIndex).getOpeartionalFlag())) {
					 ucmExceptionFlightVOs.remove(selectedIndex);
				 }
				 else {
					 ucmExceptionFlightVOs.get(selectedIndex).setOpeartionalFlag(
							 AbstractVO.OPERATION_FLAG_DELETE);
				 }
			 }
		}
		invocationContext.target = DELETE_SUCCESS;
        
    }

	private void updateLastRecord(UCMFlightExceptionListForm ucmFlightExceptionListForm, UCMFlightExceptionListSession ucmFlightExceptionListSession) {
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
