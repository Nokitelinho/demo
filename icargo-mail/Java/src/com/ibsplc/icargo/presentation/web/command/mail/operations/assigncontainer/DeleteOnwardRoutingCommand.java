/*
 * DeleteOnwardRoutingCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory; 


/**
 * @author A-5991
 *
 */
public class DeleteOnwardRoutingCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String TARGET_SUCCESS = "delete_onwardroute_success"; 
   private static final String OPERFLAG_INSERT_DELETE = "ID";
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
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);  
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();		
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		String contNo = assignContainerForm.getContainerNumber();
		log.log(Log.INFO, "contNo:-------->>", contNo);
		int currentindex = assignContainerForm.getCurrentIndex();
		
		// Updating the currently displayed ContainerVO
		ContainerVO currentvo = updateContainerVO(
				selectedContainerVOs,
				assignContainerForm,
				currentindex,
				logonAttributes);
		
		int index = 0;
		for (ContainerVO vo : selectedContainerVOs) {
			if (index == currentindex) {
				
				vo = currentvo;
				String[] selectedRows = assignContainerForm.getContSubCheck();    	
		    	int size = selectedRows.length;
				Collection <OnwardRoutingVO> onwardRoutings = vo.getOnwardRoutings();
				Collection <OnwardRoutingVO> onwardRoutesToRem = 
					new ArrayList<OnwardRoutingVO>();
				int row = 0;
		    	for (OnwardRoutingVO routevo : onwardRoutings) {
		    		for (int j = 0; j < size; j++) {
		    			if (row == Integer.parseInt(selectedRows[j])) {
		    				if (OnwardRoutingVO.OPERATION_FLAG_INSERT.equals(
		    						routevo.getOperationFlag())) {
		    					onwardRoutesToRem.add(routevo);
	    					} else {
	    						routevo.setOperationFlag(
	    								OnwardRoutingVO.OPERATION_FLAG_DELETE);
	    						vo.setOperationFlag(
	    								OnwardRoutingVO.OPERATION_FLAG_UPDATE);
	    					}
		    			}	    
		    		}
		    		row++;
		    	}		    	
		    	onwardRoutings.removeAll(onwardRoutesToRem);
log.log(Log.INFO, "VO after deletion:-------->>", vo);
				break;
			}				
			index++;
		}
						
		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
		log.log(Log.INFO, "SelectedContainerVOs after:-------->>",
				assignContainerSession.getSelectedContainerVOs());
		invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("DeleteOnwardRoutingCommand","execute");
    	
    }
    /**
     * Method for updating the currnet vo that is displayed
     * @param selectedContainerVOs
     * @param assignContainerForm
     * @param currentIndex
     * @param logonAttributes
     * @return ContainerVO
     */
    private ContainerVO updateContainerVO(
    		Collection<ContainerVO> selectedContainerVOs,
    		AssignContainerForm assignContainerForm,
    		int currentIndex,
    		LogonAttributes logonAttributes) {
    	
    	ContainerVO displayVO = null;
    	
    	String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		String[] fltCarriers = assignContainerForm.getFltCarrier();
		String[] fltNos = assignContainerForm.getFltNo();
		String[] depDates = assignContainerForm.getDepDate();
		String[] pous = assignContainerForm.getPointOfUnlading();
		
		// GETTING THE VO AT SPECIFIED INDEX AND UPDATING IT
		int index = 0;
		for (ContainerVO vo : selectedContainerVOs) {
			if (index == currentIndex) {
				
				displayVO = vo;
				displayVO.setType(assignContainerForm.getContainerType());
				displayVO.setContainerNumber(assignContainerForm.getContainerNumber());
				displayVO.setPou(assignContainerForm.getPou().toUpperCase());
				if ("Y".equals(assignContainerForm.getPaBuilt())) {
					displayVO.setPaBuiltFlag("Y");
				}
				else {
					displayVO.setPaBuiltFlag("N");
				}
				displayVO.setFinalDestination(assignContainerForm.getContainerDestination().toUpperCase());
				displayVO.setRemarks(assignContainerForm.getRemarks());
								
				Collection <OnwardRoutingVO> onwardRoutings = displayVO.getOnwardRoutings();
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
						routevo.setPou(pous[row]);
						
						row++;
					}
					displayVO.setOnwardRoutings(onwardRoutings);
				}
			
				log.log(Log.FINE, "DisplayVO ------------> ", displayVO);
				break;
			}
			index++;
		}		
		return displayVO;   	
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
