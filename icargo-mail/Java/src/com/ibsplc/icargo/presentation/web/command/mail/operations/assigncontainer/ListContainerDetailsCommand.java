/*
 * ListContainerDetailsCommand.java Created on Jun 30 2016
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
public class ListContainerDetailsCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String TARGET_SUCCESS = "list_success"; 
   private static final String CONST_TRUE = "Y";
   private static final String BLANKSPACE = "";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListContainerDetailsCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID); 
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		int currentindex = assignContainerForm.getCurrentIndex();
		
		// UPDATING THE CONTAINERVO CURRENTLY DISPLAYED
		updateContainerVO(
				selectedContainerVOs,
				assignContainerForm,				
				currentindex,
				logonAttributes);			
			
		String contNo = assignContainerForm.getContainerNumber();
		log.log(Log.INFO, "contNumber:-------->>", contNo);
		/*
		 * Getting the container number selected in ths combo and
		 * displaying the details of that containervo
		 */
		int index = 0;
		for (ContainerVO vo : selectedContainerVOs) {
			if (contNo.equals(vo.getContainerNumber())) {
				assignContainerForm.setContainerType(vo.getType());				
				assignContainerForm.setPou(vo.getPou());
				
				String pabuilt = vo.getPaBuiltFlag();
				if (CONST_TRUE.equals(pabuilt)) {
					assignContainerForm.setPaBuilt("Y");
				}
				else {
					assignContainerForm.setPaBuilt("N");
				}			
				
				assignContainerForm.setContainerDestination(vo.getFinalDestination());
				assignContainerForm.setRemarks(vo.getRemarks());
				assignContainerForm.setCurrentIndex(index);
				break;
			}
			index++;
		}
				    	    	    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ListContainerDetailsCommand","execute");    	
    }
    /**
     * Method for updating the currnet vo that is displayed
     * @param selectedContainerVOs
     * @param assignContainerForm
     * @param currentIndex
     * @param logonAttributes
     * @return ContainerVO
     */
    private void updateContainerVO(
    		Collection<ContainerVO> selectedContainerVOs,
    		AssignContainerForm assignContainerForm,
    		int currentIndex,
    		LogonAttributes logonAttributes) {    	  	
    	
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
								
				vo.setPou(upper(assignContainerForm.getPou()));
				if ("Y".equals(assignContainerForm.getPaBuilt())) {
					vo.setPaBuiltFlag("Y");
				}
				else {
					vo.setPaBuiltFlag("N");
				}
				vo.setFinalDestination(upper(assignContainerForm.getContainerDestination()));
				vo.setRemarks(assignContainerForm.getRemarks());
								
				Collection <OnwardRoutingVO> onwardRoutings = vo.getOnwardRoutings();
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
				
				log.log(Log.FINE, "DisplayVO ------------> ", vo);
				break;
			}
			index++;
		}	
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
