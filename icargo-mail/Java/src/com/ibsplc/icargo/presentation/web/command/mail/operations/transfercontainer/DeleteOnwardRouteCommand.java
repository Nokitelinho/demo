/*
 * DeleteOnwardRouteCommand.java Created on Oct 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfercontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory; 


/**
 * @author A-1861
 *
 */
public class DeleteOnwardRouteCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";	
   private static final String TARGET_SUCCESS = "success"; 
   private static final String BLANKSPACE = "";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteOnwardRoutingCommand","execute");
    	  
    	TransferContainerForm transferContainerForm = 
    		(TransferContainerForm)invocationContext.screenModel;
    	TransferContainerSession transferContainerSession = 
    		(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		Collection<ContainerVO> selectedContainerVOs = transferContainerSession.getSelectedContainerVOs();		
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		// UPDATING THE CONTAINERVO IN SESSION
		ContainerVO currentvo = updateContainerVO(
				transferContainerSession,
				transferContainerForm,
				logonAttributes); 
		
		String[] selectedRows = transferContainerForm.getReassignSubCheck();    	
    	int size = selectedRows.length;
    	Collection <OnwardRoutingVO> onwardRoutings = currentvo.getOnwardRoutings();
    	Collection <OnwardRoutingVO> updatedvos = new ArrayList<OnwardRoutingVO>();
    	
    	int row = 0;
    	for (OnwardRoutingVO routevo : onwardRoutings) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				routevo.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_DELETE);
    				break;
    			}	    
    		}
    		if (!OnwardRoutingVO.OPERATION_FLAG_DELETE.equals(routevo.getOperationFlag())) {
				updatedvos.add(routevo);
			}
    		row++;
    	}
    	currentvo.setOnwardRoutings(updatedvos);
		
		log.log(Log.INFO, "currentvo:-------->>", currentvo);
		transferContainerSession.setContainerVO(currentvo);
		
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("DeleteOnwardRoutingCommand","execute");
    	
    }
   /**
    * Method for updating the currnet vo that is displayed
    * @param transferContainerSession
    * @param transferContainerForm
    * @param logonAttributes
    * @return ContainerVO
    */
    private ContainerVO updateContainerVO(
    		TransferContainerSession transferContainerSession,
    		TransferContainerForm transferContainerForm,
    		LogonAttributes logonAttributes) {
    	
    	String reassignedto = transferContainerForm.getReassignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", reassignedto);
		String[] fltCarriers = transferContainerForm.getFltCarrier();
		String[] fltNos = transferContainerForm.getFltNo();
		String[] depDates = transferContainerForm.getDepDate();
		String[] pous = transferContainerForm.getPointOfUnlading();
		
		ContainerVO containerVO = transferContainerSession.getContainerVO();
						
		Collection <OnwardRoutingVO> onwardRoutings = containerVO.getOnwardRoutings();
		if (onwardRoutings == null) {
			onwardRoutings = new ArrayList<OnwardRoutingVO>();
		}
		if (fltCarriers != null) {						
			int row = 0;
			for (OnwardRoutingVO routevo : onwardRoutings) {
				//routevo.setCarrierId();
				routevo.setOnwardCarrierCode(upper(fltCarriers[row]));						
				routevo.setOnwardFlightNumber(fltNos[row]);
				LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);			 		
				if (depDates[row] != null 
						&& !BLANKSPACE.equals(depDates[row])) {
					routevo.setOnwardFlightDate(date.setDate(
							depDates[row]));
				}
				routevo.setPou(upper(pous[row]));
				
				row++;
			}		
		}
		
		containerVO.setOnwardRoutings(onwardRoutings);
				
		return containerVO;   	
    }
    /**
     * This method is used to convert a string to upper case if 
     * it is not null
	 * @param input
	 * @return String
	 */
	private String upper(String input){//to convert sting to uppercase

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return BLANKSPACE;
		}
	}
}