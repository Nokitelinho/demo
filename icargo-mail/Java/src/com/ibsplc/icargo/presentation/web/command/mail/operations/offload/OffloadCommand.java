/*
 * OffloadCommand.java Created on July 27, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.offload;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class OffloadCommand extends BaseCommand {
	
	@Override
	public boolean breakOnInvocationFailure() {
		return true;
	}	
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "save_success";
   private static final String TARGET_FAILURE = "save_failure";
   private static final String TARGET_OFFLOAD_SUCCESS = "offload_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.offload";	
   
   private static final String EMPTYULD_SCREEN_ID = "mailtracking.defaults.emptyulds";
   
   private static final String CONST_OVERRIDE_FLAG = "OVERRIDE";
   private static final String CONST_SEARCH_CONTAINER = "SEARCHCONTAINER";
   private static final String CONST_MAILBAG_ENQUIRY = "MAILBAGENQUIRY";
   private static final String CONST_DSN_ENQUIRY = "DSNENQUIRY";
   
   private static final String FLAG_OFFLOADED = "offloaded";
   private static final String FLAG_NORMAL = "NORMAL";
   private static final String OFFLOAD_SUCCESS = "mailtracking.defaults.offload.info.offloadsuccess";
   
   private static final String BULK_CONTAINER="mailtracking.defaults.offload.error.cannotoffloadbarrow";
   
   private static final String DEPARTED_FLIGHT_WARN="mailtracking.defaults.warn.flightdeparted";
   private static final String FLIGHT_DEPARTED="flight_departed";
   private static final String FLAG_YES = "Y";
   private static final String OFFLOAD_SUCCESS_FOR_CONTAINER = "mailtracking.defaults.offload.info.offloadsuccessforcontainer";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("OffloadCommand","execute");
    	  
    	OffloadForm offloadForm = 
    		(OffloadForm)invocationContext.screenModel;
    	OffloadSession offloadSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    		new MailTrackingDefaultsDelegate();		
		String offloadtype = offloadForm.getType();
		String fromscreen = offloadForm.getFromScreen();
		log.log(Log.FINE, "fromscreen ------------> ", fromscreen);
		updateOffloadVO(offloadtype,
				offloadForm,
				offloadSession);
		
		OffloadVO offloadVO = offloadSession.getOffloadVO();
		Collection<MailbagVO> mailbagVOsTobeRemoved = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOs =new ArrayList<MailbagVO>();
		OffloadVO newOffloadVO = new OffloadVO();
		
		log.log(Log.FINE, "Flight Status ------------> ", offloadForm.getFlightStatus());
		if (CONST_OVERRIDE_FLAG.equals(offloadForm.getFlightStatus())) {
			// set a boolean as departed check overrided
			newOffloadVO.setDepartureOverride(true);
		}
		offloadForm.setReListFlag(FLAG_YES);
		// IF OFFLOAD SCREEN CALLED FROM SEARCHCONTAINER SCREEN OR
		// MAILBAG ENQUIRY SCREEN OR DSN ENQUIRY SCREEN
		if (CONST_SEARCH_CONTAINER.equals(fromscreen)
				|| CONST_MAILBAG_ENQUIRY.equals(fromscreen)
				|| CONST_DSN_ENQUIRY.equals(fromscreen)) {
			
			newOffloadVO.setCarrierCode(offloadVO.getCarrierCode());
			newOffloadVO.setCarrierId(offloadVO.getCarrierId());
			newOffloadVO.setCompanyCode(logonAttributes.getCompanyCode());
			
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);		 		
	 		newOffloadVO.setFlightDate(date.setDate(
	 				offloadVO.getFlightDate().toDisplayFormat(CALENDAR_DATE_FORMAT)));
			
			newOffloadVO.setFlightNumber(offloadVO.getFlightNumber());
			newOffloadVO.setFlightSequenceNumber(offloadVO.getFlightSequenceNumber());
			newOffloadVO.setLegSerialNumber(offloadVO.getLegSerialNumber());
			newOffloadVO.setPol(offloadVO.getPol());
			newOffloadVO.setOffloadType(offloadtype);
			newOffloadVO.setUserCode(logonAttributes.getUserId().toUpperCase());
			
		}
		else {
			FlightValidationVO flightValidationVO = offloadSession.getFlightValidationVO();
						
			if(flightValidationVO!=null){			
			newOffloadVO.setCarrierCode(flightValidationVO.getCarrierCode());
			newOffloadVO.setCarrierId(flightValidationVO.getFlightCarrierId());
			newOffloadVO.setCompanyCode(logonAttributes.getCompanyCode());
			
			LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
			//Added by A-5160 for ICRD-90823
			if(flightValidationVO.getApplicableDateAtRequestedAirport()!=null)
	 		{
	 			newOffloadVO.setFlightDate(date.setDate(flightValidationVO.getApplicableDateAtRequestedAirport().toDisplayFormat(CALENDAR_DATE_FORMAT)));
	 		}
			else
				{
					newOffloadVO.setFlightDate(date.setDate(flightValidationVO.getFlightDate().toDisplayFormat(CALENDAR_DATE_FORMAT)));
				}
			
			newOffloadVO.setFlightNumber(flightValidationVO.getFlightNumber());
			newOffloadVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
			newOffloadVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			}
			else if(offloadVO.getOffloadContainers()!=null){
				for(ContainerVO containerVO : offloadVO.getOffloadContainers()){
					newOffloadVO.setCarrierCode(containerVO.getCarrierCode());
					newOffloadVO.setCarrierId(containerVO.getCarrierId());
					newOffloadVO.setCompanyCode(logonAttributes.getCompanyCode());
					
					LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
					newOffloadVO.setFlightDate(date.setDate(
							containerVO.getFlightDate().toDisplayFormat(CALENDAR_DATE_FORMAT)));
					
					newOffloadVO.setFlightNumber(containerVO.getFlightNumber());
					newOffloadVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
					newOffloadVO.setLegSerialNumber(containerVO.getLegSerialNumber());
				}				
			}
			else if(offloadVO.getOffloadMailbags()!=null){
				for(MailbagVO mailbagVO : offloadVO.getOffloadMailbags()){
					newOffloadVO.setCarrierCode(mailbagVO.getCarrierCode());
					newOffloadVO.setCarrierId(mailbagVO.getCarrierId());
					newOffloadVO.setCompanyCode(logonAttributes.getCompanyCode());
					
					LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
					newOffloadVO.setFlightDate(date.setDate(
							mailbagVO.getFlightDate().toDisplayFormat(CALENDAR_DATE_FORMAT)));
					
					newOffloadVO.setFlightNumber(mailbagVO.getFlightNumber());
					newOffloadVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
					newOffloadVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
				}				
			}
			newOffloadVO.setPol(offloadVO.getPol());
			newOffloadVO.setOffloadType(offloadtype);
			newOffloadVO.setUserCode(logonAttributes.getUserId().toUpperCase());
		}
				
		if (offloadVO != null) {			
			int row = 0;
			
			if (("U").equals(offloadtype)) {
				Collection<ContainerVO> containerVOs = offloadVO.getOffloadContainers();
				Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();
				Collection<ContainerVO> containerVOsTobeRemoved = new ArrayList<ContainerVO>();
				if (containerVOs != null && containerVOs.size() > 0) {
					String[] contOffloadReasons = offloadForm.getContOffloadReason();
					String[] contOffloadRemarks = offloadForm.getContOffloadRemarks();
					
					for (String str : contOffloadRemarks) {
						log.log(Log.FINE, "str --------->>", str);
					}
					
					String[] selectedRows = offloadForm.getUldSubCheck();   	
			    	int size = selectedRows.length;
			    	row = 0;
					for (ContainerVO containervo : containerVOs) {
						for (int j = 0; j < size; j++) {
			    			if (row == Integer.parseInt(selectedRows[j])) {
			    				log.log(Log.FINE, "row --------->>", row);
								log.log(Log.FINE,
										"contOffloadRemarks[row] ----->>",
										contOffloadRemarks, row);
								if(MailConstantsVO.BULK_TYPE.equals(containervo.getType())) {			    					
			    					ErrorVO errorVO = new ErrorVO(BULK_CONTAINER);
			    					errors = new ArrayList<ErrorVO>();
			    					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			    					errors.add(errorVO);
			    					invocationContext.addAllError(errors);		
			    		  	  		invocationContext.target = TARGET_FAILURE;
			    		  	  		return;
			    				}
			    				containervo.setOffloadedReason(contOffloadReasons[row]);
			    				containervo.setOffloadedRemarks(contOffloadRemarks[row]);
			    				containervo.setOffloadedDescription(handleReasonCodes(
			    						contOffloadReasons[row],
			    						offloadSession));
			    				containervo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			    				containervo.setOffload(true);
			    				//CONVERTED FRM airlineIdentifier TO OwnAirlineIdentifier AS airlineIdentifier was coming as 0 
			    				//Done for 51166
			    				//Changed by A-5945 for ICRD-98837
			    				containervo.setCarrierId(offloadVO.getCarrierId());
			    				containervo.setMailSource(offloadForm.getNumericalScreenId());//Added for ICRD-156218
			    				
			    				newContainerVOs.add(containervo);
			    				containerVOsTobeRemoved.add(containervo);
			    				
			    				newOffloadVO.setLegSerialNumber(containervo.getLegSerialNumber());
			    			}	    
			    		}						
						row ++;
					}
					containerVOs.removeAll(containerVOsTobeRemoved);
				}
				log.log(Log.FINE, "newContainerVOs --------->>",
						newContainerVOs);
				newOffloadVO.setOffloadContainers(newContainerVOs);
				
			}
			else if (("D").equals(offloadtype)) {
				Collection<DespatchDetailsVO> despatchDetailsVOs = offloadVO.getOffloadDSNs();
				Page<DespatchDetailsVO> newDespatchDetailsVOs = new Page<DespatchDetailsVO>(new ArrayList<DespatchDetailsVO>(),0,0,0,0,0,false);
				Collection<DespatchDetailsVO> despatchVOsTobeRemoved = new ArrayList<DespatchDetailsVO>();
				if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
					String[] dsnOffloadReasons = offloadForm.getDsnOffloadReason();
					String[] dsnOffloadRemarks = offloadForm.getDsnOffloadRemarks();
					
					String[] selectedRows = offloadForm.getDsnSubCheck();   	
			    	int size = selectedRows.length;
					row = 0;
					for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
						for (int j = 0; j < size; j++) {
			    			if (row == Integer.parseInt(selectedRows[j])) {			    				
			    				despatchDetailsVO.setOffloadedReason(dsnOffloadReasons[row]);
			    				despatchDetailsVO.setOffloadedRemarks(dsnOffloadRemarks[row]);
			    				despatchDetailsVO.setOffloadedDescription(handleReasonCodes(
			    						dsnOffloadReasons[row],
			    						offloadSession));
			    				//despatchDetailsVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			    				despatchDetailsVO.setOffload(true);
			    				
			    				newDespatchDetailsVOs.add(despatchDetailsVO);
			    				despatchVOsTobeRemoved.add(despatchDetailsVO);
			    			}	    
			    		}
						row ++;
					}
					despatchDetailsVOs.removeAll(despatchVOsTobeRemoved);
				}
				log.log(Log.FINE, "newDespatchDetailsVOs --------->>",
						newDespatchDetailsVOs);
				newOffloadVO.setOffloadDSNs(newDespatchDetailsVOs);
			}
			else if (("M").equals(offloadtype)) {
				mailbagVOs = offloadVO.getOffloadMailbags();
				Page<MailbagVO> newMailbagVOs = new Page<MailbagVO>(new ArrayList<MailbagVO>(),0,0,0,0,0,false);
				
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					String[] mailbagOffloadReasons = offloadForm.getMailbagOffloadReason();
					String[] mailbagOffloadRemarks = offloadForm.getMailbagOffloadRemarks();
					
					String[] selectedRows = offloadForm.getMailbagSubCheck();   	
			    	int size = selectedRows.length;
					row = 0;
					for (MailbagVO mailbagvo : mailbagVOs) {
						for (int j = 0; j < size; j++) {
			    			if (row == Integer.parseInt(selectedRows[j])) {			    				
			    				mailbagvo.setOffloadedReason(mailbagOffloadReasons[row]);
			    				mailbagvo.setOffloadedRemarks(mailbagOffloadRemarks[row]);
			    				mailbagvo.setOffloadedDescription(handleReasonCodes(
			    						mailbagOffloadReasons[row],
			    						offloadSession));
			    				//mailbagvo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			    				mailbagvo.setScannedUser(logonAttributes.getUserId());
			    				mailbagvo.setScannedPort(logonAttributes.getAirportCode());
			    				mailbagvo.setScannedDate(
			    						new LocalDate(logonAttributes.getAirportCode(), 
			    								ARP, true));
			    				mailbagvo.setIsoffload(true);
			    				//Changed by A-5945 for ICRD-98765 
			    				
			    				mailbagvo.setCarrierId(offloadVO.getCarrierId());
			    				mailbagvo.setMailSource(offloadForm.getNumericalScreenId());//Added for ICRD-156218
			    				
			    				newMailbagVOs.add(mailbagvo);
			    				mailbagVOsTobeRemoved.add(mailbagvo);
			    				
			    				newOffloadVO.setLegSerialNumber(mailbagvo.getLegSerialNumber());
			    			}	    
			    		}
						row ++;
					}
					 //Removed as part of bug ICRD-153648 by A-5526 
					//mailbagVOs.removeAll(mailbagVOsTobeRemoved);       
				}
				log.log(Log.FINE, "newMailbagVOs --------->>", newMailbagVOs);
				newOffloadVO.setOffloadMailbags(newMailbagVOs);
			}
		}
	
		//newOffloadVO.seto
		
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
				
		try {
			log.log(Log.FINE, "NewOffloadVO for offloading-----==-----> ",
					newOffloadVO);
			containerDetailsVOs = mailTrackingDefaultsDelegate.offload(newOffloadVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			boolean flightDeparted = false;
			for(ErrorVO errorVO : errors) {
				if(DEPARTED_FLIGHT_WARN.equalsIgnoreCase(errorVO.getErrorCode())) {
					offloadForm.setFlightStatus(FLIGHT_DEPARTED);
					flightDeparted = true;
					break;
				}
			}
			if(!flightDeparted) {
				offloadForm.setFlightStatus("");
			}
			if(CONST_MAILBAG_ENQUIRY.equals(offloadForm.getFromScreen())|| CONST_SEARCH_CONTAINER.equals(offloadForm.getFromScreen())||CONST_DSN_ENQUIRY.equals(offloadForm.getFromScreen()))//Modified for ICRD-158801
			{
			offloadSession.setOffloadVO(newOffloadVO);  
			}
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		 //Added as part of bug ICRD-153648 by A-5526 starts
		else{
			if(mailbagVOs!=null && mailbagVOs.size()>0)
				{
				mailbagVOs.removeAll(mailbagVOsTobeRemoved);                                       
				}                                       
			
		}
		 //Added as part of bug ICRD-153648 by A-5526 ends
		
		EmptyULDsSession emptyUldsSession = 
			  				getScreenSession(MODULE_NAME,EMPTYULD_SCREEN_ID);
		
		log.log(Log.FINE, "containerDetailsVOs ------------>>",
				containerDetailsVOs);
		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
  	  		emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
  	  		offloadForm.setFlightStatus("emptyuld");
  	  		//Modified for ICRD-153556 starts
  	  		offloadForm.setStatus("OFFLOAD");				
  	  		if("U".equals(offloadForm.getType())){
  	  		ErrorVO errorVO = new ErrorVO(OFFLOAD_SUCCESS_FOR_CONTAINER);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);		
  	  		invocationContext.target = TARGET_SUCCESS;
  	  		return;
  	  		}else{
			ErrorVO errorVO = new ErrorVO(OFFLOAD_SUCCESS);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);		
  	  		invocationContext.target = TARGET_SUCCESS;
  	  		return;
  	  		}
  	  		//Modified for ICRD-153556 ends
  	  	}
		
		//ErrorVO error = new ErrorVO("mailtracking.defaults.offload.info.offloadsuccess");
		//errors = new ArrayList<ErrorVO>();
		//errors.add(error);
		//invocationContext.addAllError(errors);
		
		offloadForm.setFlightStatus(FLAG_OFFLOADED);
		offloadForm.setClearFlag(MailConstantsVO.FLAG_NO);
		offloadForm.setStatus(FLAG_NORMAL);
		offloadForm.setMode(FLAG_NORMAL);
		offloadForm.setLastPageNumber(String.valueOf(MailConstantsVO.ZERO));
		offloadForm.setDisplayPageNum(String.valueOf(MailConstantsVO.ONE));
    	invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("OffloadCommand","execute");
    	
    }  
    /**
     * Method to update the offloadvo in session
     * @param offloadtype
     * @param offloadForm
     * @param offloadSession
     */
    private void updateOffloadVO(
    		String offloadtype,
    		OffloadForm offloadForm,
    		OffloadSession offloadSession) {
    	
    	log.entering("OffloadCommand","updateOffloadVO");
    	
    	OffloadVO offloadVO = offloadSession.getOffloadVO();
    	if (offloadVO != null) {		
			
			if (("U").equals(offloadtype)) {
				Collection<ContainerVO> containerVOs = offloadVO.getOffloadContainers();				
				String[] contOffloadReasons = offloadForm.getContOffloadReason();
				String[] contOffloadRemarks = offloadForm.getContOffloadRemarks();
				if (contOffloadReasons != null) {
					int row = 0;
					for (ContainerVO containervo : containerVOs) {
						containervo.setOffloadedReason(contOffloadReasons[row]);
	    				containervo.setOffloadedRemarks(contOffloadRemarks[row]);
	    				containervo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
	    				row++;
					}
					offloadVO.setOffloadContainers(containerVOs);
				}				
			}
			else if (("D").equals(offloadtype)) {
				Page<DespatchDetailsVO> despatchDetailsVOs = offloadVO.getOffloadDSNs();				
				String[] dsnOffloadReasons = offloadForm.getDsnOffloadReason();
				String[] dsnOffloadRemarks = offloadForm.getDsnOffloadRemarks();
				if (dsnOffloadReasons != null && despatchDetailsVOs != null) {
					int row = 0;
					for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
						despatchDetailsVO.setOffloadedReason(dsnOffloadReasons[row]);
	    				despatchDetailsVO.setOffloadedRemarks(dsnOffloadRemarks[row]);
	    				despatchDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
	    				row++;
					}
					offloadVO.setOffloadDSNs(despatchDetailsVOs);
				}				
			}
			else if (("M").equals(offloadtype)) {
				Page<MailbagVO> mailbagVOs = offloadVO.getOffloadMailbags();
				String[] mailbagOffloadReasons = offloadForm.getMailbagOffloadReason();
				String[] mailbagOffloadRemarks = offloadForm.getMailbagOffloadRemarks();
				if (mailbagOffloadReasons != null && mailbagVOs !=null) {
					int row = 0;
					for (MailbagVO mailbagvo : mailbagVOs) {
						mailbagvo.setOffloadedReason(mailbagOffloadReasons[row]);
	    				mailbagvo.setOffloadedRemarks(mailbagOffloadRemarks[row]);
	    				row++;
					}
					offloadVO.setOffloadMailbags(mailbagVOs);
				}
			}
			log.log(Log.FINE, "OffloadVO after update--------->>", offloadVO);
			offloadSession.setOffloadVO(offloadVO);
    	}
    }
    
    /**
     * Method is used to get the field description 
     * for a particular filed value
     * @param reasonCode
     * @param offloadSession
     * @return String
     */
    private String handleReasonCodes(
    		String reasonCode,
    		OffloadSession offloadSession) {
    	
    	String reasonDesc = "";
    	
    	Collection<OneTimeVO> reasonCodes = offloadSession.getOffloadReasonCode();
    	
    	for (OneTimeVO vo : reasonCodes) {
    		if (vo.getFieldValue().equals(reasonCode)) {
    			reasonDesc = vo.getFieldDescription();
    			break;
    		}
    	}
    	
    	return reasonDesc;
    }
}
