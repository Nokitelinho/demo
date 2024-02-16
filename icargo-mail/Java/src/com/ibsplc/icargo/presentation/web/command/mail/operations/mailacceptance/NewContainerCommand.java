/*
 * NewContainerCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class NewContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String ULD_TYPE = "U";	//Added for ICRD-128804
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListAcceptMailCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();

		if("FLIGHT".equals(mailAcceptanceForm.getAssignToFlight())){
			if((mailAcceptanceForm.getPou() == null || 
						mailAcceptanceForm.getPou().trim().length() == 0)){ 
				invocationContext.addError(new ErrorVO("mailtracking.defaults.pou.empty"));
				
				mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = TARGET;
		  		return;
			}
			if("U".equals(mailAcceptanceForm.getContainerType()) && (mailAcceptanceForm.getDestn()==null ||
					mailAcceptanceForm.getDestn().trim().length()==0)){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.destn.empty"));

				mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = TARGET;
		  		return;
			}
			if("B".equals(mailAcceptanceForm.getContainerType())){
				if(! (mailAcceptanceForm.getPou().equals(mailAcceptanceForm.getDestn()))){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destnandpouisnotsame"));

					mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);	
					invocationContext.target = TARGET;
			  		return;
				}
			}
		}	
		if(mailAcceptanceForm.getDestn().equals(logonAttributes.getAirportCode())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.destn.currentairport"));

			mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);	
			invocationContext.target = TARGET;
	  		return;
		}
    	ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
    	Collection<MailbagVO> newMailbagVOs =  containerDetailsVO.getMailDetails();
    	if(newMailbagVOs != null && newMailbagVOs.size() > 0) {
			  for(MailbagVO newMailbagVO:newMailbagVOs) {
			    /*String mailId = new StringBuilder()
			            .append(newMailbagVO.getOoe())
			            .append(newMailbagVO.getDoe())
						.append(newMailbagVO.getMailCategoryCode())
						.append(newMailbagVO.getMailSubclass())
						.append(newMailbagVO.getYear())
						.append(newMailbagVO.getDespatchSerialNumber())
						.append(newMailbagVO.getReceptacleSerialNumber())
						.append(newMailbagVO.getHighestNumberedReceptacle())
						.append(newMailbagVO.getRegisteredOrInsuredIndicator())
						.append(newMailbagVO.getStrWeight()).toString();*/
			    newMailbagVO.setMailbagId(newMailbagVO.getMailbagId());//Modified for ICRD-205027
			    String dsnId = new StringBuilder()
	            .append(newMailbagVO.getOoe())
	            .append(newMailbagVO.getDoe())
				.append(newMailbagVO.getMailCategoryCode())
				.append(newMailbagVO.getMailSubclass())
				.append(newMailbagVO.getYear())
				.append(newMailbagVO.getDespatchSerialNumber()).toString();
			    newMailbagVO.setDespatchId(dsnId);
			    newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
		    	newMailbagVO.setFlightDate(mailAcceptanceVO.getFlightDate());
		    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
		    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
		    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
		    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
		    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
		    	newMailbagVO.setPou(containerDetailsVO.getPou());
			  }
			}
		
  	    containerDetailsVO.setMailDetails(newMailbagVOs);
    	mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);

    	  //validate Location - Warehouse
    	String location = mailAcceptanceForm.getLocation();
    	if (location != null && location.trim().length() > 0) {
    	log.log(Log.FINE, "Going To validate Location - Warehouse ...in command");
    	LocationValidationVO locationValidationVO = new LocationValidationVO();
		  try {
			  locationValidationVO = new MailTrackingDefaultsDelegate().validateLocation(
					logonAttributes.getCompanyCode(),logonAttributes.getAirportCode(),
					mailAcceptanceForm.getWarehouse(),location.toUpperCase());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (locationValidationVO == null) {
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidlocation"));
	  		mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
	  		invocationContext.target = TARGET;
	  		return;
	  	  }
    	}
    	
//    	validate PA code
    	String invalidPACode = "";
    	Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
	  	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
	  		try {
		  		for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
		  			String paCode = despatchVO.getPaCode().toUpperCase();
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		  			
	  				postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
	  								logonAttributes.getCompanyCode(),paCode);	  			
	       
		  			if (postalAdministrationVO == null) {
		  				if("".equals(invalidPACode)){
		  					invalidPACode = paCode;
		  				}else{
		  					invalidPACode = new StringBuilder(invalidPACode).append(",").append(paCode).toString();
		  				}
		  			}
		  		}
	  		}catch (BusinessDelegateException businessDelegateException) {
  				errors = handleDelegateException(businessDelegateException);
  			}
	  	}
	  	if(!"".equals(invalidPACode)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{invalidPACode}));
			mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
	  		invocationContext.target = TARGET;
	  		return;
		}
	  	
		// Validate Transfer From Carrier
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	if(containerDetailsVO.getTransferFromCarrier() != null
    		&& !"".equals(containerDetailsVO.getTransferFromCarrier())){
	    	try {        			
	    		AirlineValidationVO airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(),containerDetailsVO.getTransferFromCarrier());
	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {            			
				Object[] obj = {containerDetailsVO.getTransferFromCarrier()};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
				invocationContext.target = TARGET;
				return;
			}
    	}
    	
    	// Validate Destination
    	AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
    	String destination = mailAcceptanceForm.getDestn();        	
    	if (destination != null && !"".equals(destination)) {        		
    		try {        			
    			airportValidationVO = areaDelegate.validateAirportCode(
    					logonAttributes.getCompanyCode(),destination.toUpperCase());
    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {destination.toUpperCase()};
				invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidairport",obj));
				invocationContext.target = TARGET;
				return;
    		}
    	}
    	
    	
    	//validate Mail bags
	  	Collection<MailbagVO> mailbgVOs = containerDetailsVO.getMailDetails();
    	Collection<MailbagVO> newMailbgVOs = new ArrayList<MailbagVO>();
    	if(mailbgVOs != null && mailbgVOs.size() > 0){
	  		for(MailbagVO mailbagVO:mailbgVOs){
	  			if("I".equals(mailbagVO.getOperationalFlag())
	  				|| "U".equals(mailbagVO.getOperationalFlag())){
	  				newMailbgVOs.add(mailbagVO);
	  			}
	  		}
	  	}
	  	log.log(Log.FINE, "Going To validate Mail bags ...in command",
				newMailbgVOs);
		try {
		    new MailTrackingDefaultsDelegate().validateMailBags(newMailbgVOs);
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
	  		invocationContext.target = TARGET;
	  		return;
	  	  }
	  	  
//			Check for same OOE and DOE for despatches
			int sameOE = 0;
			Collection<DespatchDetailsVO> despatchVOs = containerDetailsVO.getDesptachDetailsVOs();
			if(despatchVOs != null && despatchVOs.size() > 0){
		  		for(DespatchDetailsVO despatchVO:despatchVOs){
	  				String ooe = despatchVO.getOriginOfficeOfExchange();	 
			    	String doe = despatchVO.getDestinationOfficeOfExchange();
			    	if(ooe.trim().length() == 6){
				      if(doe.trim().length() == 6){
				        if (ooe.equals(doe)) {  
			        	   sameOE = 1;
			        	   String pk = new StringBuilder(despatchVO.getOriginOfficeOfExchange())
	        							.append(despatchVO.getDestinationOfficeOfExchange())
	        							//.append(despatchVO.getMailClass())
	        							//added by anitha for change in pk
	        							.append(despatchVO.getMailCategoryCode())
	        							.append(despatchVO.getMailSubclass())
	        							.append(despatchVO.getYear())
	        							.append(despatchVO.getDsn()).toString();
			        			 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{pk}));
			        	}
				      }
				    }
		  		}
		  	}
			if(sameOE == 1){
				mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
				invocationContext.target = TARGET;
		  		return;
			}	  	  
	  	  
//	  	Check for same OOE and DOE for mail bags
		sameOE = 0;
		Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
		if(mailbagVOs != null && mailbagVOs.size() > 0){
	  		for(MailbagVO mailbagVO:mailbagVOs){
				String ooe = mailbagVO.getOoe();	 
		    	String doe = mailbagVO.getDoe();
		    	if(ooe.trim().length() == 6){
			       if(doe.trim().length() == 6){
			    	  if (ooe.equals(doe)) {  
		        		 sameOE = 1;
		        		 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{mailbagVO.getMailbagId()}));
			    	  }
			       }
			    }
	  		}
	  	}
		if(sameOE == 1){
			mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
			invocationContext.target = TARGET;
	  		return;
		}	  	  
	  	  
	  	  
      //Duplicate check for Mail bags
		  	Collection<MailbagVO> firstMailbagVOs = containerDetailsVO.getMailDetails();
		  	Collection<MailbagVO> secMailbagVOs = containerDetailsVO.getMailDetails();
		  	int duplicate = 0;
		  	if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
		  		int count = 0;
		  		for(MailbagVO fstMailbagVO:firstMailbagVOs){
		  			for(MailbagVO secMailbagVO:secMailbagVOs){
		  				if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
		  					count++;
		  				}
		  			}
		  			if(count == 2){
		  				duplicate = 1;
		  				break;
		  			}
		  			count = 0;
		  		}
		  	}
		  	
		  	if(duplicate == 1){
		  		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.duplicatemailbag"));
		  		mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
		  		invocationContext.target = TARGET;
		  		return;	
		  	}  
		  	
		
//	 checking duplicate mailbags across containers
	  	int duplicateAcross = 0;
	  	String container = "";
	  	String mailId = "";
	  	MailAcceptanceVO mailAcceptVO = mailAcceptanceSession.getMailAcceptanceVO();
    	Collection<ContainerDetailsVO> containerDtlVOs =  mailAcceptVO.getContainerDetails();
    	if(containerDtlVOs != null && containerDtlVOs.size() > 0){
    		if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
    			for(MailbagVO fstMailbagVO:firstMailbagVOs){
    				for(ContainerDetailsVO popupVO:containerDtlVOs){
    					if(!fstMailbagVO.getContainerNumber().equals(popupVO.getContainerNumber())){
    						Collection<MailbagVO> secMailVOs = popupVO.getMailDetails();
    						if(secMailVOs != null && secMailVOs.size() > 0){
    							for(MailbagVO secMailbagVO:secMailVOs){
    								if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
    									duplicateAcross = 1;
    									container = popupVO.getContainerNumber();
    									mailId = fstMailbagVO.getMailbagId();
    								}
    							}
    						}
    					}
    				}
    			}
			}
		}
    	if(duplicateAcross == 1){
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.duplicatemailbagacross",new Object[]{container,mailId}));
	  		mailAcceptanceForm.setContainerNo(containerDetailsVO.getContainerNumber());
	  		invocationContext.target = TARGET;
	  		return;	
	  	}
    	
    	//Updating Session
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailAcceptanceSession.getContainerDetailsVOs();
    	Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			if(!"N".equals(containerDtlsVO.getContainerOperationFlag())){
	    			if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
	    				newContainerDetailsVOs.add(containerDetailsVO);
	    			}else{
	    				newContainerDetailsVOs.add(containerDtlsVO);
	    			}
    			}
    		}
    	}
    	
    	// Putting new ContainerNo Details VO into session...
    	mailAcceptanceForm.setContainerNo("");
       	ContainerDetailsVO contDetailsVO = new ContainerDetailsVO();
       	contDetailsVO.setContainerOperationFlag("N");
       	contDetailsVO.setContainerNumber("");
       	contDetailsVO.setContainerType("U");
       	newContainerDetailsVOs.add(contDetailsVO);
       	if("FLIGHT".equals(mailAcceptanceForm.getAssignToFlight())){
       		mailAcceptanceForm.setDestn("");
       	}
        mailAcceptanceForm.setDisableFlag("Y");
        //Added for ICRD-128804 starts
        mailAcceptanceForm.setBarrowCheck(false);
        mailAcceptanceForm.setContainerType(ULD_TYPE);
        //Added for ICRD-128804 ends
    	mailAcceptanceSession.setContainerDetailsVO(contDetailsVO);
    	mailAcceptanceSession.setContainerDetailsVOs(newContainerDetailsVOs); 
    	invocationContext.target = TARGET;
    	log.exiting("ListAcceptMailCommand","execute");
    	
    }
    
}
