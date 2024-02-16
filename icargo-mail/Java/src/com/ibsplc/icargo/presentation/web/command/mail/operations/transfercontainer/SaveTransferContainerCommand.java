/*
 * SaveTransferContainerCommand.java Created on Oct 05, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfercontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class SaveTransferContainerCommand extends AbstractPrintCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";
   private static final String FLIGHT_MODULE_NAME = "flight.operation";
   private static final String FLIGHT_SCREEN_ID = "flight.operation.duplicateflight";
   
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_YES = "YES";
   private static final String SHOW_DUPLICATE = "showDuplicateFlights";
   private static final String OUTBOUND = "O";
   private static final String TARGET_SUCCESS = "success";
   private static final String TARGET_FAILURE = "failure"; 
   private static final String TRFMFT_REPORT_ID = "RPTOPS066";
   private static final String PRODUCTCODE = "mail";
   private static final String SUBPRODUCTCODE = "operations"; 
   private static final String BUNDLE = "transferMailManifestResources";
   private static final String ACTION = "generateTransferManifestReportForContainer";
   private static final String FROM_SCREEN_EXPORTLIST="MAIL_EXPORT_LIST";
   private static final String FROM_SCREEN_ACCEPTANCE="MAIL_ACCEPTANCE";
   private static final String FROM_SCREEN_CONTAINER="SEARCHCONTAINER";
   private static final String EMBARGO_EXISTS = "embargo_exists";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveTransferContainerCommand","execute");
    	  
    	TransferContainerForm transferContainerForm = 
    		(TransferContainerForm)invocationContext.screenModel;
    	TransferContainerSession transferContainerSession = 
    		(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	DuplicateFlightSession duplicateFlightSession = (DuplicateFlightSession)getScreenSession(
				FLIGHT_MODULE_NAME, FLIGHT_SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	boolean printNeeded=false;
    	if(transferContainerForm.getScanDate()==null && ("").equals(transferContainerForm.getMailScanTime())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
 	   		invocationContext.target =TARGET_FAILURE; 
 	   		return; 
		}
		if(transferContainerForm.getMailScanTime()==null ||("").equals(transferContainerForm.getMailScanTime())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
 	   		invocationContext.target =TARGET_FAILURE; 
 	   		return; 
		}
		
		if(EMBARGO_EXISTS.equals(transferContainerForm.getEmbargoFlag())){ //Added by A-8164 for ICRD-271652
	   		invocationContext.target =TARGET_FAILURE; 
	   		return;  
	   	}
		
		String scanDate= new StringBuilder().append(transferContainerForm.getScanDate()).append(" ").append(transferContainerForm.getMailScanTime()).append(":00").toString();
	    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    scanDat.setDateAndTime(scanDate);
    	
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();
    	
    	String reassignedto = transferContainerForm.getReassignedto();
    	log.log(Log.FINE, "REASSIGNED TO ------------> ", reassignedto);
		Collection<ContainerVO> selectedContainerVOs = transferContainerSession.getSelectedContainerVOs();
    	ContainerVO currentvo = transferContainerSession.getContainerVO();
    	
    	OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	operationalFlightVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
    	operationalFlightVO.setOwnAirlineId(
    			logonAttributes.getOwnAirlineIdentifier());
    	operationalFlightVO.setOperator(
    			logonAttributes.getUserId());
    	
    	operationalFlightVO.setOperationTime(scanDat);
    	
    	// IF REASSIGNING TO FLIGHT
    	if (CONST_FLIGHT.equals(reassignedto)) {  
    		FlightValidationVO flightValidationVO = null;
    		
    		if (SHOW_DUPLICATE.equals(transferContainerForm.getStatus())) {
    			flightValidationVO = duplicateFlightSession.getFlightValidationVO();
    			transferContainerForm.setStatus("");
    		}
    		else {
    			flightValidationVO = transferContainerSession.getFlightValidationVO(); 
    		}
    		log.log(Log.FINE, "FlightValidationVO ------------> ",
					flightValidationVO);
			// validating whether the container is already assigned to same flight
    		String assignedto = transferContainerForm.getAssignedto();
    		log.log(Log.FINE, "assignedto ------------> ", assignedto);
			if (CONST_FLIGHT.equals(assignedto)) {
    			errors = isReassignedToSameFlight(
    					flightValidationVO,
    					selectedContainerVOs,
    					transferContainerForm);
        		if (errors != null && errors.size() > 0) {      			
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
        		if(flightValidationVO != null){
        			if(flightValidationVO.isTBADueRouteChange()){
        				Object [] obj = {flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),transferContainerForm.getFlightDate()};
        				ErrorVO errorVO = new ErrorVO(
        						"mailtracking.defaults.transfercontainer.msg.err.flighttobeactioned",obj);
        				if(errors == null){
        					errors = new ArrayList<ErrorVO>();
        				}
        				errors.add(errorVO);
        				invocationContext.addAllError(errors);
        				invocationContext.target = TARGET_FAILURE;
        				return;
        			}
        			if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
                            FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
                            FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
        				Object[] obj = {flightValidationVO.getCarrierCode().toUpperCase(),flightValidationVO.getFlightNumber()};
        				ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
        				err.setErrorDisplayType(ErrorDisplayType.ERROR);
        				invocationContext.addError(err);
        				invocationContext.target = TARGET_FAILURE;
        				return;
        			}
        		}
    		}
    		
    		
    		for (ContainerVO vo : selectedContainerVOs) {
				vo.setOnwardRoutings(currentvo.getOnwardRoutings());	
				vo.setRemarks(transferContainerForm.getRemarks());
				vo.setReassignFlag(true);
				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
				vo.setMailSource(transferContainerForm.getNumericalScreenId());//Added for ICRD-156218
    		}
    		
    		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
        	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());    	
        	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
        	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
        	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
        	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
        	operationalFlightVO.setPou(transferContainerForm.getFlightPou().toUpperCase());
        	
        	if(flightValidationVO.getAtd() != null){
				operationalFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
			}
    		
    	}
    	// IF REASSIGNING TO DESTINATION
    	else {
    		AirlineValidationVO airlineValidationVO = 
    			transferContainerSession.getAirlineValidationVO();
    		log.log(Log.FINE, "AirlineValidationVO ------------> ",
					airlineValidationVO);
			operationalFlightVO.setCarrierCode(airlineValidationVO.getAlphaCode());
        	operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());  
        	operationalFlightVO.setFlightDate(null);
        	operationalFlightVO.setFlightNumber("-1");
        	operationalFlightVO.setFlightSequenceNumber(-1);
        	operationalFlightVO.setLegSerialNumber(-1);
        	operationalFlightVO.setPou(transferContainerForm.getDestination().toUpperCase());
        	
        	for (ContainerVO vo : selectedContainerVOs) {					
				vo.setRemarks(transferContainerForm.getRemarks());
				vo.setReassignFlag(true);
				vo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
				vo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
				//ADDED FOR ICRD-95510
				//vo.setPou(transferContainerForm.getDestination().toUpperCase());
				vo.setFinalDestination(transferContainerForm.getDestination().toUpperCase());
				vo.setMailSource(transferContainerForm.getNumericalScreenId());//Added for ICRD-156218
    		}
    	}
    	TransferManifestVO transferManifestVO=null;
    	String printFlag=transferContainerForm.getPrintTransferManifestFlag();
    	try {
    		log.log(Log.FINE, "selectedContainerVOs for saving-------> ",
					selectedContainerVOs);
			log.log(Log.FINE, "operationalFlightVO for saving-------> ",
					operationalFlightVO);
			
			
			
			if(FROM_SCREEN_EXPORTLIST.equals(transferContainerForm.getFromScreen())||FROM_SCREEN_ACCEPTANCE.equals(transferContainerForm.getFromScreen()) || (FROM_SCREEN_CONTAINER.equals(transferContainerForm.getFromScreen())
					&& logonAttributes.getAirportCode().equals(selectedContainerVOs.iterator().next().getPol()))){//added by A-7371 for ICRD-133987
				
				transferManifestVO=mailTrackingDefaultsDelegate.transferContainersAtExport(
	    				selectedContainerVOs,
	    				operationalFlightVO,printFlag);
				
			}else{
			
			transferManifestVO=mailTrackingDefaultsDelegate.transferContainers(
    				selectedContainerVOs,
    				operationalFlightVO,printFlag);
			}

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			transferContainerForm.setStatus("");//Patch provided by A-4809 as discussed with Santhi
			return;
		}	else{
	    	  log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->",
		    		  transferManifestVO);
    		  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
    			    printNeeded=true;
    				ReportSpec reportSpec = getReportSpec();    
    				reportSpec.addFilterValue(transferManifestVO);
    				reportSpec.setProductCode(PRODUCTCODE);
    				reportSpec.setSubProductCode(SUBPRODUCTCODE);
    				reportSpec.setPreview(true);
    				reportSpec.setResourceBundle(BUNDLE);
    				reportSpec.setReportId(TRFMFT_REPORT_ID);
    				reportSpec.setAction(ACTION);   
    				generateReport();  
    				 
    				  
    				
    		      }    
    	  }
	    	
		transferContainerForm.setStatus("closeWindow");
		 if(printNeeded){
        	 invocationContext.target = getTargetPage();    
         }else{
        	 invocationContext.target = TARGET_SUCCESS;	 }       
    		 	
       	
    	log.exiting("SaveContainerCommand","execute");
    	
    }
    /**
     * Method to validate whether the container is reassigned to same flight segment
     * @param reassignedFlightValidationVO
     * @param selectedContainerVOs
     * @param reassignContainerForm
     * @return Collection<ErrorVO>
     */
    private Collection<ErrorVO> isReassignedToSameFlight(
    		FlightValidationVO reassignedFlightValidationVO,
    		Collection<ContainerVO> selectedContainerVOs,
    		TransferContainerForm reassignContainerForm) {
    	
    	log.entering("SaveContainerCommand","isReassignedToSameFlight");
    	
    	boolean isSameFlight = false;
    	StringBuilder errorcode = new StringBuilder("");
    	Collection<ErrorVO> validationerrors = new ArrayList<ErrorVO>();    	
    	log.log(Log.FINE, "ReassignedFlightValidationVO-------> ",
				reassignedFlightValidationVO);
		for (ContainerVO selectedvo : selectedContainerVOs) {
    		if (!("-1").equals(selectedvo.getFlightNumber())) {
    			if ((reassignedFlightValidationVO.getFlightCarrierId() == selectedvo.getCarrierId())
            			&& (reassignedFlightValidationVO.getFlightNumber().equals(selectedvo.getFlightNumber()))
            			&& (reassignedFlightValidationVO.getLegSerialNumber() == selectedvo.getLegSerialNumber())
            			&& (reassignedFlightValidationVO.getFlightSequenceNumber() == selectedvo.getFlightSequenceNumber())
            			&& (reassignContainerForm.getFlightPou().equalsIgnoreCase(selectedvo.getPou()))
        			) {
    				errorcode.append(selectedvo.getContainerNumber()).append(",");
    				isSameFlight = true;
            	}    			
    		}    		    		
    	}
    	
    	log.log(Log.FINE, "isSameFlight-------> ", isSameFlight);
		log.log(Log.FINE, "errorcode-------> ", errorcode);
		if (isSameFlight) {
    		Object[] obj = {errorcode.substring(0,errorcode.length()-1)};
    		ErrorVO errorVO = new ErrorVO(
    				"mailtracking.defaults.reassigncontainer.msg.err.cannotReassignToSameFlight",obj);
    		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    		validationerrors.add(errorVO);
    	}
    	  	
    	return validationerrors;
    }
    
    
    
}
