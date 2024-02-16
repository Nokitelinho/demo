/*
 * NewArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class NewArriveMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
//   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String ULD_TYPE = "U";	//Added for ICRD-128804
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("NewArriveMailCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
	   	ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
	   	
	   	log.log(Log.FINE, "containerDetailsVO START...in command",
				containerDetailsVO);
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
			    newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		    	//newMailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
		    	//newMailbagVO.setFlightDate(mailArrivalVO.getFlightDate());
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
  	  mailArrivalSession.setContainerDetailsVO(containerDetailsVO);

    	 
    	
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
			mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
	  		invocationContext.target = TARGET;
	  		return;
		}
    	
    	
    	log.log(Log.FINE, "Going To validate Mail bags ...in command",
				containerDetailsVO.getMailDetails());
		try {
		    new MailTrackingDefaultsDelegate().validateMailBags(containerDetailsVO.getMailDetails());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
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
	        							//added by anitha
	        							//.append(despatchVO.getMailClass())
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
				mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
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
			mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
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
		  		mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
		  		invocationContext.target = TARGET;
		  		return;	
		  	}  
		  	
		
//	 checking duplicate mailbags across containers
	  	int duplicateAcross = 0;
	  	String container = "";
	  	String mailId = "";
	  	MailArrivalVO mailArriveVO = mailArrivalSession.getMailArrivalVO();
    	Collection<ContainerDetailsVO> containerDtlVOs =  mailArriveVO.getContainerDetails();
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
	  		mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
	  		invocationContext.target = TARGET;
	  		return;	
	  	}
    	
    	log.log(Log.FINE, "containerDetailsVO END...in command",
				containerDetailsVO);
		Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalSession.getContainerDetailsVOs();
    	Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())
    					&& containerDtlsVO.getPol().equals(containerDtlsVO.getPol())){
    				newContainerDetailsVOs.add(containerDetailsVO);
    			}else{
    				newContainerDetailsVOs.add(containerDtlsVO);
    			}
    		}
    	}
		
    	mailArrivalSession.setContainerDetailsVOs(newContainerDetailsVOs); 
    	ContainerDetailsVO containerDtlsVO = new ContainerDetailsVO();
    	containerDtlsVO.setContainerNumber("");
    	containerDtlsVO.setOperationFlag("I");
  	    mailArrivalSession.setContainerDetailsVO(containerDtlsVO);
  	    mailArrivalForm.setContainerNo("");
  	    //Added For ICRD-128804 starts
  	    mailArrivalForm.setBarrowCheck(false);
  	    mailArrivalForm.setContainerType(ULD_TYPE);
  	    //Added For ICRD-128804 starts
        mailArrivalForm.setDisableFlag("");
        
//      To set POL
		 FlightValidationVO flightValidationVO = mailArrivalSession.getFlightValidationVO();
		 String route = flightValidationVO.getFlightRoute();
		 String[] routeArr = route.split("-");
		 String pol = "";
		 for(int i=0;i<routeArr.length;i++){
			 if(routeArr[i].equals(logonAttributes.getAirportCode())){
				 pol = routeArr[i-1];
				 break;
			 }
		 }
		mailArrivalForm.setPol(pol);
    	
    	invocationContext.target = TARGET;
       	
    	log.exiting("NewArriveMailCommand","execute");
    	
    }
       
}
