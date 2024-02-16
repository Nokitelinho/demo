/*
 * SelectNextContainerCommand.java Created on Jul 1 2016
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
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
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
public class SelectNextContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SelectNextContainerCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();	
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
				if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())){
					newContainerDetailsVOs.add(containerDetailsVO);
				}else{
					newContainerDetailsVOs.add(containerDtlsVO);
				}
    		}
    	}
    	
    	// Putting selected ContainerNo Details VO into session...
    	int displayPage = Integer.parseInt(mailAcceptanceForm.getSuggestValue());
    	ContainerDetailsVO selContainerDtlsVO = 
    		((ArrayList<ContainerDetailsVO>)newContainerDetailsVOs).get(displayPage);
    	
    	mailAcceptanceForm.setContainerNo(selContainerDtlsVO.getContainerNumber());
    	
    	/**
		 * @author A-3227
		 */
		ContainerDetailsVO contDetailsVO=((ArrayList<ContainerDetailsVO>)newContainerDetailsVOs).get(displayPage);
		mailAcceptanceSession.setContainerDetailsVO(contDetailsVO);
		Collection<ContainerDetailsVO> contDetailVOs = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> newContDetailsVOs = null;
		int n=0;
		if(contDetailsVO.getMailDetails()==null	&& contDetailsVO.getDesptachDetailsVOs()== null){
			if(contDetailsVO.getOperationFlag()== null || ("U").equals(contDetailsVO.getOperationFlag())){
				contDetailVOs.add(contDetailsVO);
				try {
					newContDetailsVOs =new MailTrackingDefaultsDelegate().findMailbagsInContainer(contDetailVOs);
				} catch (BusinessDelegateException businessDelegateException){
					errors = handleDelegateException(businessDelegateException);
				}
				for( ContainerDetailsVO contnrDtlsVO :newContainerDetailsVOs){
					for(ContainerDetailsVO newcontrDtlVO :newContDetailsVOs ){
						if(contnrDtlsVO.getContainerNumber().equals(newcontrDtlVO.getContainerNumber())){
							contnrDtlsVO.setMailDetails(newcontrDtlVO.getMailDetails());
							contnrDtlsVO.setDesptachDetailsVOs(newcontrDtlVO.getDesptachDetailsVOs());
							
							Collection<MailbagVO> malbagVOs = contnrDtlsVO.getMailDetails();
							if (malbagVOs != null && malbagVOs.size() != 0) {
								for (MailbagVO mailbagVO: malbagVOs) {
									String malId = mailbagVO.getMailbagId();
									double displayWt=Double.parseDouble(malId.substring(malId.length()-4,malId.length()));
									Measure strWt= new Measure(UnitConstants.WEIGHT,displayWt/10);
									mailbagVO.setStrWeight(strWt);//added by A-7371
									/*
			    					 * Added By RENO K ABRAHAM :
			    					 * As a part of performance Upgrade
			    					 * START.
			    					 */
			           		       	if(mailbagVO.getOperationalFlag()==null 
			           		       			|| !("I").equals(mailbagVO.getOperationalFlag())){
			           		       		mailbagVO.setDisplayLabel("Y");
			           		       	}      
			           		       	//END
								}
							}
		        			Collection<DespatchDetailsVO> despatchDtlsVOs = contnrDtlsVO.getDesptachDetailsVOs();
		        			if(despatchDtlsVOs != null && despatchDtlsVOs.size() > 0) {
			       		       	for (DespatchDetailsVO despatchDetailsVO: despatchDtlsVOs) {	       		       		
			       		       		/*
			    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
			    					 * As a part of performance Upgrade
			    					 * START.
			    					 */
			           		       	if(despatchDetailsVO.getOperationalFlag()==null 
			           		       			|| !("I").equals(despatchDetailsVO.getOperationalFlag())){
			           		       		despatchDetailsVO.setDisplayLabel("Y");
			           		       	}      
			           		       	//END
			       		       	}        				
		        			}
							mailAcceptanceSession.setContainerDetailsVO(contnrDtlsVO);
							n++;
							break;
						}
					}
					if(n!=0){
						break;
					}
				}
			}
		}
		
    	mailAcceptanceSession.setContainerDetailsVOs(newContainerDetailsVOs); 
    	
    	invocationContext.target = TARGET;
    	log.exiting("SelectNextContainerCommand","execute");
    	
    }
    
}
