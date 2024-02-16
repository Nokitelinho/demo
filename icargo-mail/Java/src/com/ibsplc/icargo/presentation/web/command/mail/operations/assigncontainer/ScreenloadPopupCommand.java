/*
 * ScreenloadPopupCommand.java Created on July 1 2016
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
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DestinationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory; 


/**
 * @author A-5991
 *
 */
public class ScreenloadPopupCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String MODULE_NAME_TM = "mail.operations";	
   private static final String SCREEN_ID_TM = "mailtracking.defaults.transfermail";
   private static final String MODULE_NAME_RM = "mail.operations";	
   private static final String SCREEN_ID_RM = "mailtracking.defaults.reassignmail";
   private static final String ROUTE_DELIMETER = "-";
   private static final String CONST_TRUE = "Y";
   private static final String TARGET_SUCCESS = "screenload_success"; 
   private static final String CONST_ULD = "U";
   private static final String CONST_CREATE = "CREATE";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
   private static final String SCREEN_ID_CF = "mailtracking.defaults.mailarrival";
   private static final String CHANGE_FLIGHT = "CHANGE_FLIGHT";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadPopupCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	TransferMailSession transferMailSession = 
    		getScreenSession(MODULE_NAME_TM,SCREEN_ID_TM);
    	
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME_RM,SCREEN_ID_RM);
    	
    	MailArrivalSession mailArrivalSession = 
        		getScreenSession(MODULE_NAME,SCREEN_ID_CF);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		 /*
        * Getting OneTime values
        */
        Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
	    if(oneTimes!=null){
		   Collection<OneTimeVO> containerTypeVOs = oneTimes.get(CONTAINERTYPE);
		   assignContainerSession.setContainerTypes(containerTypeVOs);
		}	
    	
		if("TRANSFER_MAIL".equals(assignContainerForm.getFromScreen())){
			
			assignContainerForm.setAssignedto(CONST_FLIGHT);
			FlightValidationVO flightValidationVO = transferMailSession.getFlightValidationVO();
			
			Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
			ContainerVO containerVO = new ContainerVO();
			log.log(Log.INFO, "flightValidationVO:---TRANSFER_MAIL----->>",
					flightValidationVO);
			if (flightValidationVO.getFlightSequenceNumber() > 0) {
				assignContainerSession.setFlightValidationVO(flightValidationVO);
				/*
				 * Obtain the collection of POUs
				 */
				Collection<String> pointOfUnladings =
					getPointOfUnladings(flightValidationVO.getFlightRoute(), logonAttributes);			
				assignContainerSession.setPointOfLadings(pointOfUnladings);
	//			 flight assigned.
				containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
				containerVO.setCompanyCode(flightValidationVO.getCompanyCode());
				containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				
				assignContainerForm.setAssignedto("FLIGHT");
			}else{
				assignContainerForm.setAssignedto("DESTINATION");
			}
			
			containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
			containerVO.setAssignedPort(logonAttributes.getAirportCode());
			containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			newContainerVOs.add(containerVO);
			assignContainerSession.setSelectedContainerVOs(newContainerVOs);
			
			Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
			log.log(Log.INFO, "selectedContainerVOs:-------->>",
					selectedContainerVOs);
			for (ContainerVO vo : selectedContainerVOs) {
				assignContainerForm.setContainerType(vo.getType());
				assignContainerForm.setContainerNumber(vo.getContainerNumber());
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
				assignContainerForm.setCurrentIndex(0);	
				break;
			}
			
			assignContainerForm.setContainerType(CONST_ULD);
			assignContainerForm.setBarrowCheck(false);//Added for ICRD-128804 
			assignContainerForm.setOverrideFlag("");
			
		}else if("REASSIGN_MAIL".equals(assignContainerForm.getFromScreen())){
			
			assignContainerForm.setAssignedto(CONST_FLIGHT);
			FlightValidationVO flightValidationVO = reassignMailSession.getFlightValidationVO();
			
			Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
			ContainerVO containerVO = new ContainerVO();
			log.log(Log.INFO, "flightValidationVO:---REASSIGN_MAIL----->>",
					flightValidationVO);
			if (flightValidationVO.getFlightSequenceNumber() > 0) {
				assignContainerSession.setFlightValidationVO(flightValidationVO);
				/*
				 * Obtain the collection of POUs
				 */
				Collection<String> pointOfUnladings =
					getPointOfUnladings(flightValidationVO.getFlightRoute(), logonAttributes);			
				assignContainerSession.setPointOfLadings(pointOfUnladings);
	//			 flight assigned.
				containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
				containerVO.setCompanyCode(flightValidationVO.getCompanyCode());
				containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				
				assignContainerForm.setAssignedto("FLIGHT");
			}else{
				
				DestinationFilterVO destinationFilterVO = reassignMailSession.getDestinationFilterVO();
				
				containerVO.setCarrierId(destinationFilterVO.getCarrierId());
				containerVO.setCarrierCode(destinationFilterVO.getCarrierCode());
				containerVO.setCompanyCode(destinationFilterVO.getCompanyCode());
				containerVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
				assignContainerForm.setAssignedto("DESTINATION");
			}
			
			containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
			containerVO.setAssignedPort(logonAttributes.getAirportCode());
			containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			
			newContainerVOs.add(containerVO);
			assignContainerSession.setSelectedContainerVOs(newContainerVOs);
			
			Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
			log.log(Log.INFO, "selectedContainerVOs:-------->>",
					selectedContainerVOs);
			for (ContainerVO vo : selectedContainerVOs) {
				assignContainerForm.setContainerType(vo.getType());
				assignContainerForm.setContainerNumber(vo.getContainerNumber());
				assignContainerForm.setPou(vo.getPou());
				
				String pabuilt = vo.getPaBuiltFlag();
				if (CONST_TRUE.equals(pabuilt)) {
					assignContainerForm.setPaBuilt("Y");
				}
				else {
					assignContainerForm.setPaBuilt("N");
				}		
				
				log.log(Log.FINE, "#######$$$$$$$$$$$", assignContainerForm.getDestn());
				assignContainerForm.setContainerDestination(assignContainerForm.getDestn());

				assignContainerForm.setRemarks(vo.getRemarks());
				assignContainerForm.setCurrentIndex(0);	
				break;
			}
			
			assignContainerForm.setContainerType(CONST_ULD);
			assignContainerForm.setBarrowCheck(false);//Added for ICRD-128804 
			assignContainerForm.setOverrideFlag("");
			
		}else if(CHANGE_FLIGHT.equals(assignContainerForm.getFromScreen())){
			FlightValidationVO flightValidationVO = mailArrivalSession.getChangeFlightValidationVO();
			Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
			ContainerVO containerVO = new ContainerVO();
			log.log(Log.INFO, "flightValidationVO:---CHANGE_FLIGHT----->>",
					flightValidationVO);
			if (flightValidationVO.getFlightSequenceNumber() > 0) {
				assignContainerSession.setFlightValidationVO(flightValidationVO);
				/*
				 * Obtain the collection of POUs
				 */
				Collection<String> pointOfUnladings =new ArrayList<String>();
				pointOfUnladings.add(flightValidationVO.getLegDestination());
				assignContainerSession.setPointOfLadings(pointOfUnladings);
				containerVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				containerVO.setCarrierCode(flightValidationVO.getCarrierCode());
				containerVO.setCompanyCode(flightValidationVO.getCompanyCode());
				containerVO.setFlightNumber(flightValidationVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				containerVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				containerVO.setPol(flightValidationVO.getLegOrigin());
				assignContainerForm.setAssignedto(CONST_FLIGHT);
			}
			containerVO.setOperationFlag(ContainerVO.OPERATION_FLAG_INSERT);
			containerVO.setAssignedPort(logonAttributes.getAirportCode());
			containerVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setAssignedDate(new LocalDate(logonAttributes.getAirportCode(),ARP,true));
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			newContainerVOs.add(containerVO);
			assignContainerSession.setSelectedContainerVOs(newContainerVOs);
			Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
			log.log(Log.INFO, "selectedContainerVOs:-------->>",
					selectedContainerVOs);
			for (ContainerVO vo : selectedContainerVOs) {
				assignContainerForm.setContainerType(vo.getType());
				assignContainerForm.setContainerNumber(vo.getContainerNumber());
				assignContainerForm.setPou(vo.getPou());
				String pabuilt = vo.getPaBuiltFlag();
				if (CONST_TRUE.equals(pabuilt)) {
					assignContainerForm.setPaBuilt(MailConstantsVO.FLAG_YES);
				}
				else {
					assignContainerForm.setPaBuilt(MailConstantsVO.FLAG_NO);
				}		
				assignContainerForm.setContainerDestination(vo.getFinalDestination());
				assignContainerForm.setRemarks(vo.getRemarks());
				assignContainerForm.setCurrentIndex(0);	
				break;
			}
			assignContainerForm.setContainerType(CONST_ULD);
			assignContainerForm.setBarrowCheck(false);//Added for ICRD-128804 
			assignContainerForm.setOverrideFlag("");
		}	
		else{
			FlightValidationVO flightValidationVO = assignContainerSession.getFlightValidationVO();
			
			log.log(Log.INFO, "AssignedTo:-------->>", assignContainerForm.getAssignedto());
			if (flightValidationVO != null) {
				/*
				 * Obtain the collection of POUs
				 */
				Collection<String> pointOfUnladings =
					getPointOfUnladings(
							flightValidationVO.getFlightRoute(), logonAttributes);			
				assignContainerSession.setPointOfLadings(pointOfUnladings);
			} 
			
			Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
			log.log(Log.INFO, "selectedContainerVOs:-------->>",
					selectedContainerVOs);
			for (ContainerVO vo : selectedContainerVOs) {
				assignContainerForm.setContainerType(vo.getType());
				assignContainerForm.setContainerNumber(vo.getContainerNumber());
				assignContainerForm.setPou(vo.getPou());
				
				String pabuilt = vo.getPaBuiltFlag();
				if (CONST_TRUE.equals(pabuilt)) {
					assignContainerForm.setPaBuilt("Y");
				}
				else {
					assignContainerForm.setPaBuilt("N");
				}		
				
				//Added for ICRD-128804 starts
				if("U".equalsIgnoreCase(vo.getType())){
					assignContainerForm.setBarrowCheck(false);
		       		}else{
		       		assignContainerForm.setBarrowCheck(true);
		       	}
				//Added for ICRD-128804 ends
				assignContainerForm.setContainerDestination(vo.getFinalDestination());
				assignContainerForm.setRemarks(vo.getRemarks());
				assignContainerForm.setCurrentIndex(0);	
				break;
			}
			
			if (CONST_CREATE.equals(assignContainerForm.getStatus())) {
				assignContainerForm.setContainerType(CONST_ULD);
				assignContainerForm.setBarrowCheck(false);//Added for ICRD-128804 
			}
	    	
			assignContainerForm.setOverrideFlag("");
		}
		
		
		
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ScreenloadPopupCommand","execute");
    	
    }
    /**
	 * The method will obtain the collection of POUs
	 * from the route
	 * @param route
	 * @param logonAttributes
	 * @return pointOfUnladings
	 */
	private Collection<String> getPointOfUnladings(
			String route, LogonAttributes logonAttributes) {
		log.exiting("ScreenloadPopupCommand", "getPOUs");

		Collection<String> pointOfUnladings = new ArrayList<String>();
		/*
		 * Split the route string using the delimeter "-"
		 */		
		if(route != null && route.length() > 0){
			String[] stations = route.split(ROUTE_DELIMETER);
			boolean isLoginStation = false;
			for(int index=0; index < stations.length; index++) {
				/*
				 * the pous should start from the current station
				 */
				if (logonAttributes.getAirportCode().equals(stations[index])) {
					isLoginStation = true;
					continue;
				}
				if (isLoginStation) {
					pointOfUnladings.add(stations[index]);
				}				
			}
		}
		log.log(Log.FINE, "pointOfUnladings ---> ", pointOfUnladings);
		log.exiting("ScreenloadPopupCommand", "getPOUs");
		
		return pointOfUnladings;
	}
	
	
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(CONTAINERTYPE);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	
	
	
	
       
}
