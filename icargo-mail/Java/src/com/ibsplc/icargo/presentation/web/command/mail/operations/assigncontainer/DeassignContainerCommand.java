/*
 * DeassignContainerCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class DeassignContainerCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";
   private static final String OUTBOUND = "O";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String TARGET_SUCCESS = "deassign_container_success"; 
   private static final String TARGET_FAILURE = "deassign_container_failure";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeassignContainerCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
    	String assignedto = assignContainerForm.getAssignedto();
    	
    	 if (CONST_FLIGHT.equals(assignedto)) {
    		 
    		FlightValidationVO flightValidationVO = assignContainerSession.getFlightValidationVO();
     		
    		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
         	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
         	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
         	operationalFlightVO.setDirection(OUTBOUND);
         	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
         	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
         	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
         	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
         	operationalFlightVO.setPol(logonAttributes.getAirportCode());
         	
         	// CHECKING WHETHER THE FLIGHT IS CLOSED FOR MAIL OPERATIONS
        	boolean isFlightClosed = false;       	 
        	try {
        		
        		  isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);    		  
    			
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
			if (isFlightClosed) {
    			Object[] obj = {flightValidationVO.getCarrierCode(),
    					flightValidationVO.getFlightNumber(),
    					flightValidationVO.getApplicableDateAtRequestedAirport().toString().substring(0,11)};
    			ErrorVO errorVO = new ErrorVO(
    					"mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj);
    			errors = new ArrayList<ErrorVO>();
    			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;				
    		}
    	 }
    	    	
    	Collection<ContainerVO> containerVOs = assignContainerSession.getContainerVOs();
    	Collection<ContainerVO> selectedContainerVOs = new ArrayList<ContainerVO>();
    	Collection<ContainerVO> selectedEmptyContainerVOs = new ArrayList<ContainerVO>();
    	Collection<ContainerDetailsVO> selectedEmptyContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
    	String[] selectedRows = assignContainerForm.getSubCheck();    	
    	int size = selectedRows.length;
    	int row = 0;
    	int errorFlag = 0;    	
    	int errorArr = 0;
    	
    	StringBuilder bagaccepted = new StringBuilder("");
    	StringBuilder bagzero = new StringBuilder("");
    	StringBuilder contArr = new StringBuilder("");
    	for (ContainerVO containervo : containerVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				if("Y".equals(containervo.getArrivedStatus())){
    	   				errorArr = 1;
    	   				contArr.append(containervo.getContainerNumber()).append(",");           				
    	   			}
    				if (ContainerVO.OPERATION_FLAG_INSERT.equals(containervo.getOperationFlag())) {
		    			    Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
		    			ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.assigncontainer.msg.err.containersNotSaved");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						validationerrors.add(errorVO);
						invocationContext.addAllError(validationerrors);
						invocationContext.target = TARGET_FAILURE;
						return;
		    		}
    				if (containervo.getBags() == 0 && !"Y".equals(containervo.getAcceptanceFlag())) {
    					//if (containervo.getBags() == 0 ) {  
    					containervo.setFlightClosureCheckNeeded(false);
    					
    					selectedContainerVOs.add(containervo);   					
    				}
    				else {    					
    					errorFlag = 1;
    										if (containervo.getBags() > 0) {
           					bagzero.append(containervo.getContainerNumber()).append(",");
           				}
    					else if(containervo.getBags() == 0 && !"N".equals(containervo.getAcceptanceFlag())){    						
    						//bagaccepted.append(containervo.getContainerNumber()).append(","); 
    						//bagzero.append(containervo.getContainerNumber()).append(",");
    						ContainerDetailsVO detailsVO=constructConatinerDetailsFromContainer(containervo);
    						log.log(Log.FINE,"details vo---->"+detailsVO);
    						detailsVO.setContainerType(containervo.getType());
    						detailsVO.setPol(containervo.getAssignedPort());
    						 //Changed as part of bug ICRD-110575 by A-5526 starts
    						if(!"B".equalsIgnoreCase(containervo.getType())){
    						selectedEmptyContainerDetailsVOs.add(detailsVO);
    						}
    						 //Changed as part of bug ICRD-110575 by A-5526 ends
    						
    						selectedEmptyContainerVOs.add(containervo);
    						selectedContainerVOs.add(containervo); 
           				}    					
    				} 
    					
    				break;
    			}
    		}
    		row++;
    	}
    	
    	log.log(Log.INFO, "bagaccepted:-------->>", bagaccepted);
		log.log(Log.INFO, "bagzero:------------>>", bagzero);
		log.log(Log.INFO, "contArr:------------>>", contArr);
		if(errorArr == 1){
    	   if(!"".equals(contArr.toString().trim()) && contArr.length() > 0){
    		   Object[] obj = {contArr.substring(0,contArr.length()-1)};
    		   ErrorVO errorVO = new ErrorVO(
   					"mailtracking.defaults.msg.err.unassigncontainersarrived",obj);		    			
   			   errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);  
   			   errors.add(errorVO);  
   			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;   	   	   
    	   }    	   
        }
    	if(errorFlag == 1){
    		if(!"".equals(bagzero.toString()) && bagzero.length() > 0){
    			Object[] obj = {bagzero.substring(0,bagzero.length()-1)};
				ErrorVO errorVO = new ErrorVO(
    					"mailtracking.defaults.assigncontainer.msg.err.bagspresent",obj);		    			
    			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(errorVO);
    		}
    		if(!"".equals(bagaccepted.toString()) && bagaccepted.length() > 0){
    			Object[] obj = {bagaccepted.substring(0,bagaccepted.length()-1)};
				ErrorVO errorVO = new ErrorVO(
    					"mailtracking.defaults.assigncontainer.msg.err.cannotUnassignAcceptedbags",obj);		    			
    			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(errorVO);
    		}
    		/*
    		if(!"".equals(bagzero.toString().trim()) && bagzero.length() > 0){
    			Object[] obj = {bagzero.substring(0,bagzero.length()-1)};
				ErrorVO errorVO = new ErrorVO(
    					"mailtracking.defaults.assigncontainer.msg.err.cannotUnassignAcceptedbags",obj);		    			
    			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    			if (errors == null) {
    				errors = new ArrayList<ErrorVO>();
    			}
    			errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		} */
    	}
    	
    	log.log(Log.INFO, "ContainerVOS selected :----------->>",
				selectedContainerVOs);
		// VALIDATE WHETHER CONTAINERS ARE REASSIGNED BEFORE SAVING
    	for (ContainerVO selectedvo : selectedContainerVOs) {
    		if (ContainerVO.OPERATION_FLAG_INSERT.equals(selectedvo.getOperationFlag())) {
    			
    			Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();
    			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.assigncontainer.msg.err.containersNotSaved");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				validationerrors.add(errorVO);
				invocationContext.addAllError(validationerrors);
				invocationContext.target = TARGET_FAILURE;
				return;
    			
    		}
    	}
    	
    	if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}else {		    		    		
    		try {
    			if(selectedEmptyContainerDetailsVOs!=null && selectedEmptyContainerDetailsVOs.size()>0){
    				mailTrackingDefaultsDelegate.unassignEmptyULDs(selectedEmptyContainerDetailsVOs);
    			}
    			 if (selectedContainerVOs!= null && selectedContainerVOs.size()>0){
    				 if(selectedEmptyContainerDetailsVOs!=null && selectedEmptyContainerDetailsVOs.size()>0){
    						Collection<ContainerVO> selectedContainerVOsforDeletion = new HashSet<ContainerVO>();
    						Map<String,ContainerVO> selectedcontainermap = new HashMap<String, ContainerVO>();
    						for(ContainerVO containervo: selectedContainerVOs){
    							selectedcontainermap.put(containervo.getContainerNumber(), containervo);
    						}
    						for(ContainerDetailsVO  s :selectedEmptyContainerDetailsVOs){
    							String containernumber =s.getContainerNumber();
    							if(selectedcontainermap.containsKey(containernumber)){
    								selectedcontainermap.remove(containernumber);
    							}
    						}
    						 log.log(Log.INFO, "selectedcontainermap selected :----------->>",
    								 selectedcontainermap);
    					for(String container : selectedcontainermap.keySet()){
    						selectedContainerVOsforDeletion.add(selectedcontainermap.get(container));
    					}
    					log.log(Log.INFO, "ContainerVOS selected :----------->>",
       						 selectedContainerVOsforDeletion);
    				 log.log(Log.INFO, "ContainerVOS selected :----------->>",
    						 selectedContainerVOsforDeletion);
    			mailTrackingDefaultsDelegate.deleteContainers(selectedContainerVOsforDeletion);        
    				 } else
    			{
    			mailTrackingDefaultsDelegate.deleteContainers(selectedContainerVOs);
    			}
    			}

    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    		else {
    			ArrayList<ContainerVO> oldContainerVOs = (ArrayList<ContainerVO>)containerVOs;
    			for (ContainerVO selectedvo : selectedContainerVOs) {
    				if (oldContainerVOs.contains(selectedvo)) {
    					oldContainerVOs.remove(selectedvo);
        			}
    			} 
    			log
						.log(
								Log.INFO,
								"ContainerVOS after removing deassigned vos :----------->>",
								oldContainerVOs);
				assignContainerSession.setContainerVOs((Collection<ContainerVO>)oldContainerVOs);
    			assignContainerForm.setSubCheck(null);
    		}
    	}
    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("DeassignContainerCommand","execute");
    	
    }
    public ContainerDetailsVO constructConatinerDetailsFromContainer(ContainerVO containerVO){
    	ContainerDetailsVO containerDetails= new ContainerDetailsVO();
    	/*try {
			BeanHelper.copyProperties(containerDetails, containerVO);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	containerDetails.setCompanyCode(containerVO.getCompanyCode());
    	containerDetails.setCarrierId(containerVO.getCarrierId());
    	containerDetails.setFlightNumber(containerVO.getFlightNumber());
    	containerDetails.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
    	containerDetails.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
    	containerDetails.setContainerNumber(containerVO.getContainerNumber());
    	return containerDetails;
    }
}
