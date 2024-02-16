/*
 * OkArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
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
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;

/**
 * @author A-5991
 *
 */
public class OkArriveMailCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("OkArriveMailCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		Collection<ContainerDetailsVO> cntdlsVOs = mailArrivalVO.getContainerDetails();
		if(cntdlsVOs != null && !cntdlsVOs.isEmpty()){
			for(ContainerDetailsVO dtlVO : cntdlsVOs){
				if(!"B".equals(dtlVO.getContainerType()) && dtlVO.getContainerNumber() != null && dtlVO.getContainerNumber().trim().length() > 0 &&
						dtlVO.getPol() != null && dtlVO.getPol().trim().length() > 0){
					if(dtlVO.getContainerNumber().equals(mailArrivalForm.getContainerNo()) &&
							!(dtlVO.getPol().equals(mailArrivalForm.getPol()))){
						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.containerexistalready"));
						invocationContext.target = TARGET;
						return;
					}
				}
			}
		}

		ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
		log.log(Log.FINE,
				"containerDetailsVO.getContainerNumber .OK..in command",
				containerDetailsVO.getContainerNumber());
		Collection<MailbagVO> newMailbagVOs =  containerDetailsVO.getMailDetails();
    	StringBuilder errMails = new StringBuilder();
    	if(newMailbagVOs != null && newMailbagVOs.size() > 0) {
    		
			  for(MailbagVO newMailbagVO:newMailbagVOs) {
				  if(MailbagVO.OPERATION_FLAG_INSERT.equals(
						  newMailbagVO.getOperationalFlag())) {
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
			    	//Added for ICRD-135171 starts
				    if(newMailbagVO.getLatestScannedDate()==null){
					LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			    	newMailbagVO.setLatestScannedDate(currentDate);
				    }
			    	//Added for ICRD-135171 ends
				  }
				  newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
			    	newMailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());
			    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
			    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
			    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
			    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
			    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
			    	newMailbagVO.setPou(containerDetailsVO.getPou());
			    	
			    	if(newMailbagVO.getOperationalFlag() != null) {
				    	if(MailConstantsVO.FLAG_YES.equals(
				    			newMailbagVO.getDeliveredFlag()) && 
				    			MailConstantsVO.FLAG_YES.equals(
				    					newMailbagVO.getTransferFlag())) {				    	
				    		errMails.append(newMailbagVO.getMailbagId()).
				    				append(", ");
				    	}
			    	}
			    	
				if (newMailbagVO.getDamageFlag() != null && "Y".equals(
						newMailbagVO.getDamageFlag()) && newMailbagVO.getOperationalFlag() != 
							null && ("I".equals(newMailbagVO.getOperationalFlag()) || "U"
								.equals(newMailbagVO.getOperationalFlag()))) {
					
					if (newMailbagVO.getDamagedMailbags() == null || 
							newMailbagVO.getDamagedMailbags().size() == 0) {
						invocationContext.addError(new ErrorVO(
								"mailtracking.defaults.err.reasonfordamagemandatory"));
						mailArrivalForm.setPopupCloseFlag("N");
				  		invocationContext.target = TARGET;
				  		return;
					}
				}
			     if(newMailbagVO.getUndoArrivalFlag()!=null && MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag())) {
			    	if(newMailbagVO.getFlightSequenceNumber()!=newMailbagVO.getFromFlightSequenceNumber()||
			    			!(newMailbagVO.getFromFightNumber().equals(newMailbagVO.getFlightNumber())) ||
			    			MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(newMailbagVO.getMailStatus())
							||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(newMailbagVO.getMailStatus())) {
			    		newMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
			    		newMailbagVO.setOperationalFlag(null);
			    		newMailbagVO.setUndoArrivalFlag(null);      
			    		if(!MailConstantsVO.MAIL_STATUS_ARRIVED.equals(newMailbagVO.getMailStatus())){
			    		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailtransferredordelivered"));
						invocationContext.target = TARGET;                           
						return;       
			    	}     else{
			    		// newMailbagVO.setScannedDate(null);         
			    	}
			     }}
			     if(newMailbagVO.getUndoArrivalFlag()!=null && MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag())) {
				    	  	if("I".equals(newMailbagVO.getMraStatus())){
				    		newMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
				    		newMailbagVO.setOperationalFlag(null);
				    		newMailbagVO.setUndoArrivalFlag(null);      
				    		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagsimportedtoMRA"));
							invocationContext.target = TARGET;                           
							return;       
							
						} else{
				    	//	 newMailbagVO.setScannedDate(null);        
			    	}
			     }
				if (newMailbagVO.getMailCompanyCode()!= null && newMailbagVO.getMailCompanyCode().trim().length()>0 &&
						newMailbagVO.getOperationalFlag() != 
							null && ("I".equals(newMailbagVO.getOperationalFlag()) || "U"
								.equals(newMailbagVO.getOperationalFlag()))
				) {   boolean cmpCodecaptured=false;     
					String cmpCodeString=newMailbagVO.getAccepted();
					if(cmpCodeString!=null && cmpCodeString.trim().length()>0 && cmpCodeString.contains("-")){
					String[] cmpCodes=cmpCodeString.split("-");
					String mailCmpCode=cmpCodes[1];
					if("MALCMPCOD".equals(cmpCodes[0]) && mailCmpCode!=null && mailCmpCode.equals(newMailbagVO.getMailCompanyCode()) ){
						cmpCodecaptured=true;
					 
					}
					}
					if((MailConstantsVO.FLAG_NO.equals(newMailbagVO.getArrivedFlag())
							||(newMailbagVO.getScannedDate() == null)) && (!cmpCodecaptured)&&!MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag()))    
							{                              
						invocationContext.addError(new ErrorVO(
								"mailtracking.defaults.err.arrivalismandatoryforcompanycodecapture"));
						mailArrivalForm.setPopupCloseFlag("N");     
				  		invocationContext.target = TARGET;
				  		return;
					}
				}
				 if(newMailbagVO.getUndoArrivalFlag()!=null && MailConstantsVO.FLAG_YES.equals(newMailbagVO.getUndoArrivalFlag())) {
				    	if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(newMailbagVO.getMailStatus()) &&
				    			MailConstantsVO.FLAG_NO.equals(newMailbagVO.getArrivedFlag())) {
				    		newMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
				    		newMailbagVO.setOperationalFlag(null);
				    		newMailbagVO.setScannedDate(null); 
				    	}     
				     }
			  }
			  if(errMails.length() > 0) {
				  invocationContext.addError(
						  new ErrorVO("mailtracking.defaults.err.transferredmail",
			   				new Object[]{errMails.substring(0, errMails.length() - 2)}));
					mailArrivalForm.setPopupCloseFlag("N");
			  		invocationContext.target = TARGET;
			  		return;
			  }
			}
  	    containerDetailsVO.setMailDetails(newMailbagVOs);
  	  
  	  mailArrivalSession.setContainerDetailsVO(containerDetailsVO);


//    	validate PA code
    	String invalidPACode = "";
    	errMails = new StringBuilder();
    	Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
	  	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
	  		try {
		  		for(DespatchDetailsVO despatchVO:despatchDetailsVOs){		  			
		  			if(despatchVO.getOperationalFlag() != null) {
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
			  			
			  			if((despatchVO.getDeliveredBags() > 0) && 
				    			MailConstantsVO.FLAG_YES.equals(
				    					despatchVO.getTransferFlag())) {
				    		errMails.append(despatchVO.getDsn()).
                                append(despatchVO.getOriginOfficeOfExchange()).
				    		append(despatchVO.getDestinationOfficeOfExchange()).
				    		append(despatchVO.getMailCategoryCode()).
				    		append(despatchVO.getMailSubclass()).
				    		append(despatchVO.getYear()).append(", ");
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
			mailArrivalForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
		}
	  	
//    	validate class and subclass
    	String invalidSubClass = "";
    	Collection<DespatchDetailsVO> desDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
	  	log.log(Log.FINE, "Going To validate class and subclass ...in command");
	  	if(desDetailsVOs != null && desDetailsVOs.size() > 0){	  		
		  		for(DespatchDetailsVO despatchVO:desDetailsVOs){
		  			if(despatchVO.getMailClass()!=null && despatchVO.getMailClass().trim().length()>0
		  					&& despatchVO.getMailSubclass()!=null && despatchVO.getMailSubclass().trim().length()>0){
		  			String mailclass = despatchVO.getMailClass().toUpperCase();
		  			String mailsubclass = despatchVO.getMailSubclass().toUpperCase();		  				  			
		  			if(!mailsubclass.startsWith(mailclass)){
		  				invalidSubClass=mailsubclass;
		  			}}
		  		}
	  	}
	  	if(!"".equals(invalidSubClass)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.arrivemail.invalidsubclass"));
			mailArrivalForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
		}
	  	
	  	
	  	
	  	
	  	if(errMails.length() > 0) {
	  		invocationContext.addError(
					  new ErrorVO("mailtracking.defaults.err.transferreddsns",
		   				new Object[]{errMails.substring(0, errMails.length() - 2)}));
			mailArrivalForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	}


    	//validate Mail bags
    	log.log(Log.FINE, "Going To validate Mail bags ...in command");
		  try {
		    new MailTrackingDefaultsDelegate().validateMailBags(containerDetailsVO.getMailDetails());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailArrivalForm.setPopupCloseFlag("N");
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
				mailArrivalForm.setPopupCloseFlag("N");
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
			mailArrivalForm.setPopupCloseFlag("N");
			invocationContext.target = TARGET;
	  		return;
		}

//Validate Scan Date
		int scandate = 0;
		Collection<MailbagVO> mailbagsVOs = containerDetailsVO.getMailDetails();
		if(mailbagsVOs != null && mailbagsVOs.size() > 0){
			for(MailbagVO mailbagVO:mailbagsVOs){
				// modified for icrd-84390 by a-4810
				if (mailbagVO.getScannedDate() == null && MailbagVO.FLAG_YES.equals(
						mailbagVO.getArrivedFlag())) {
					scandate = 1;
					invocationContext.addError(new ErrorVO(
							"mailtracking.defaults.mailacceptance.scandatemandatory"));
				}
				// modified for icrd-81527 by a-4810
				else if (mailbagVO.getScannedDate() != null && MailbagVO.FLAG_NO.equals(
						mailbagVO.getArrivedFlag())&& ! MailConstantsVO.FLAG_YES.equals(mailbagVO.getUndoArrivalFlag())) {
					scandate = 1;
					invocationContext.addError(new ErrorVO(
							"mailtracking.defaults.mailacceptance.receivedflagmandatory"));
				}
				// Added for bug ICRD-97307 by A-5526 starts
				else if("N".equals(mailbagVO.getArrivedFlag()) && "Y".equals(mailbagVO.getDamageFlag())&& mailbagVO.getOperationalFlag()!=null){   
					scandate =1;
					invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.receivedflagmandatoryfordamage"));
					break;
				} else if((MailConstantsVO.FLAG_NO.equals(mailbagVO.getArrivedFlag())
						||(mailbagVO.getScannedDate() == null))
						&& MailConstantsVO.FLAG_NO.equals(mailbagVO.getDamageFlag())
						&& mailbagVO.getOperationalFlag()!=null && !MailConstantsVO.FLAG_YES.equals(mailbagVO.getUndoArrivalFlag())) {
					mailbagVO.setOperationalFlag(null);
				} else if (mailbagVO.getScannedDate() == null
						&& MailConstantsVO.FLAG_YES.equals(mailbagVO.getDamageFlag())
						&& mailbagVO.getOperationalFlag()!=null) {
					scandate = 1;
					invocationContext.addError(new ErrorVO(
					"mailtracking.defaults.mailacceptance.scandatemandatory"));
				}


				// Added for bug ICRD-97307 by A-5526 ends
			}			
		}
		if(scandate==1){
	  		mailArrivalForm.setPopupCloseFlag("N");
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
	  		mailArrivalForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	}

//		 checking duplicate mailbags across containers
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
    								if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())&&(
    										"Y".equals(secMailbagVO.getArrivedFlag()) && "Y".equals(fstMailbagVO.getArrivedFlag()))){
    									duplicateAcross = 1;
    									container = popupVO.getContainerNumber();
    									mailId = fstMailbagVO.getMailbagId();
    									fstMailbagVO.setScannedDate(null);
    									fstMailbagVO.setOperationalFlag(null);
    									fstMailbagVO.setArrivedFlag(MailConstantsVO.FLAG_NO);
    									fstMailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_NO);
    									fstMailbagVO.setDeliveryNeeded(false);                    
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
	  		mailArrivalForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	}

    	//Updating Session
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalSession.getContainerDetailsVOs();
    	Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
    	
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			if(containerDtlsVO.getContainerNumber() != null &&
						containerDtlsVO.getContainerNumber().trim().length() > 0){
				if("I".equals(containerDtlsVO.getOperationFlag())){
					containerDtlsVO.setPou(logonAttributes.getAirportCode());
					containerDtlsVO.setDestination(logonAttributes.getAirportCode());
				}
    			}
				if(containerDtlsVO.getContainerNumber() != null &&
						containerDtlsVO.getContainerNumber().trim().length() > 0){
    			if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())
    					&& containerDtlsVO.getPol().equals(containerDtlsVO.getPol())){
					if("I".equals(containerDetailsVO.getOperationFlag())){
						containerDtlsVO.setPou(logonAttributes.getAirportCode());
				    	containerDtlsVO.setDestination(logonAttributes.getAirportCode());
				    }
					
					newContainerDetailsVOs.add(containerDtlsVO);
	    			}
    				
				}
    			else{
    				if(containerDtlsVO.getContainerNumber() != null &&
    						containerDtlsVO.getContainerNumber().trim().length() > 0){
    				newContainerDetailsVOs.add(containerDtlsVO);
    				}
    			}
    		}
    	}
    	log.log(Log.FINE, "newContainerDetailsVOs ...in...OK...command",
				newContainerDetailsVOs);
		mailArrivalSession.setContainerDetailsVOs(newContainerDetailsVOs);


    	// making Summary DSN VOs
    	newContainerDetailsVOs = makeDSNVOs(newContainerDetailsVOs,logonAttributes);
    	log.log(Log.FINE, "newContainerDetailsVOs ..after.in...OK...command",
				newContainerDetailsVOs);
		//    	validate DSN VOs
    	log.log(Log.FINE, "Going To validate DSN VOs ...in command");
		  try {
		    new MailTrackingDefaultsDelegate().validateDSNs(containerDetailsVO.getDsnVOs());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (errors != null && errors.size() > 0) {
	  		invocationContext.addAllError(errors);
	  		mailArrivalForm.setPopupCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
	  	  }

    	//updating in main screen VOs
		Collection<ContainerDetailsVO> contDetailsVOs = mailArrivalVO.getContainerDetails();
		log.log(Log.FINE, "contDetailsVOs ...in...OK...command",
				newContainerDetailsVOs);
		Collection<ContainerDetailsVO> newVOs = new ArrayList<ContainerDetailsVO>();
		int flag = 0;
		if(contDetailsVOs != null && contDetailsVOs.size() > 0){
		  for(ContainerDetailsVO mainscreenVO:contDetailsVOs){
			  if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
				for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
				  if(mainscreenVO.getContainerNumber().equals(popupVO.getContainerNumber())
						  && mainscreenVO.getPol().equals(popupVO.getPol())){
   						newVOs.add(popupVO);
   						flag = 1;
				  }
		        }
			  }
			  if(flag == 1){
				  flag = 0;
			  }else{
				  newVOs.add(mainscreenVO);
			  }
		  }
		}

		Collection<ContainerDetailsVO> newCDVOs = new ArrayList<ContainerDetailsVO>();
		if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
				if(newVOs.size() > 0){
					for(ContainerDetailsVO mainscreenVO:newVOs){
				 	  if(popupVO.getContainerNumber().equals(mainscreenVO.getContainerNumber())
				 			  && mainscreenVO.getPol().equals(popupVO.getPol())){
				 		   flag = 1;
				 	  }
			        }
				  }
				  if(flag == 1){
					  flag = 0;
				  }else{
					  newCDVOs.add(popupVO);
				  }
			  }
			}


			if(newCDVOs.size() > 0){
				if(newVOs.size() > 0){
					newVOs.addAll(newCDVOs);
			    }else{
			    	newVOs = new ArrayList<ContainerDetailsVO>();
			    	newVOs.addAll(newCDVOs);
			    }
			}
		log.log(Log.FINE, "newVOs ...in...OK...command", newVOs);
		mailArrivalVO.setContainerDetails(newVOs);
		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
		//Added for SAA403 STARTS
		String unsavedDataFlag ="";
		unsavedDataFlag = constructUnsavedDataFlag(mailArrivalForm, unsavedDataFlag);
		mailArrivalForm.setUnsavedDataFlag(unsavedDataFlag);
//		Added for SAA403 ENDS
		mailArrivalForm.setPopupCloseFlag("Y");
		mailArrivalForm.setSelectContainer(null);
    	invocationContext.target = TARGET;
    	log.exiting("OkArriveMailCommand","execute");

    }

    /**
	 * Mehtod to update DSN Summary VOs
	 * @param newContainerDetailsVOs
	 * @param logonAttributes
	 * @return Collection<ContainerDetailsVO>
	 */
    public Collection<ContainerDetailsVO>  makeDSNVOs(Collection<ContainerDetailsVO> newContainerDetailsVOs,
    		LogonAttributes logonAttributes){

    	if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){

				HashMap<String,DSNVO> dsnMapDespatch = new HashMap<String,DSNVO>();
				HashMap<String,String> despatchMap = new HashMap<String,String>();
				Collection<DSNVO> mainDSNVOs = popupVO.getDsnVOs();
				if(mainDSNVOs != null && mainDSNVOs.size() > 0){
					for(DSNVO dsnVO:mainDSNVOs){
						if(!"Y".equals(dsnVO.getPltEnableFlag())){
							if(!"I".equals(dsnVO.getOperationFlag())){
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           //added by anitha
					           //+dsnVO.getMailClass()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           //+dsnVO.getMailClass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							dsnMapDespatch.put(dsnpk,dsnVO);
							}
						}
				    }
				}

				int rcvdBags = 0;
				double rcvdWgt = 0;
				int delvdBags = 0;
				double delvdWgt = 0;
				Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO.getDesptachDetailsVOs();
				 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
					for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
						String outerpk = despatchVO.getOriginOfficeOfExchange()
						           +despatchVO.getDestinationOfficeOfExchange()
						           //added by anitha
						           //+despatchVO.getMailClass()
						           +despatchVO.getMailCategoryCode()
						           +despatchVO.getMailSubclass()
						           +despatchVO.getDsn()
						           +despatchVO.getYear();

						if(despatchMap.get(outerpk) == null){
						if(dsnMapDespatch.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(despatchVO.getDsn());
							dsnVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
							dsnVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
							dsnVO.setMailClass(despatchVO.getMailClass());
							//added by anitha
							dsnVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
							dsnVO.setMailSubclass(despatchVO.getMailSubclass());
							dsnVO.setYear(despatchVO.getYear());
							dsnVO.setPltEnableFlag("N");
							dsnVO.setOperationFlag("I");
						for(DespatchDetailsVO innerVO:despatchDetailsVOs){
							String innerpk = innerVO.getOriginOfficeOfExchange()
					           +innerVO.getDestinationOfficeOfExchange()
					           //added by anitha
					           //+innerVO.getMailClass()
					           +innerVO.getMailCategoryCode()
					           +innerVO.getMailSubclass()
					           +innerVO.getDsn()
					           +innerVO.getYear();
							if(outerpk.equals(innerpk)){
								rcvdBags = rcvdBags + innerVO.getReceivedBags();
								//rcvdWgt = rcvdWgt + innerVO.getReceivedWeight();
								rcvdWgt = rcvdWgt + innerVO.getReceivedWeight().getRoundedSystemValue();//added by A-7550
								delvdBags = delvdBags + innerVO.getDeliveredBags();
								//delvdWgt = delvdWgt + innerVO.getDeliveredWeight();
								delvdWgt = delvdWgt + innerVO.getDeliveredWeight().getRoundedSystemValue();//added by A-7371
							}
						}
						dsnVO.setReceivedBags(rcvdBags);
						//dsnVO.setReceivedWeight(rcvdWgt);
						dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvdWgt));//Added by A-7550
						dsnVO.setDeliveredBags(delvdBags);
						//dsnVO.setDeliveredWeight(delvdWgt);
						dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, delvdWgt));//Added by A-7550
						dsnMapDespatch.put(outerpk,dsnVO);

						}else{
							DSNVO dsnVO = dsnMapDespatch.get(outerpk);
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           //added by anitha
					           //+dsnVO.getMailClass()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							if(despatchDetailsVOs.size() > 0){
								for(DespatchDetailsVO dsptchVO:despatchDetailsVOs){
								String despatchpk = dsptchVO.getOriginOfficeOfExchange()
								           +dsptchVO.getDestinationOfficeOfExchange()
								           //added by anitha
								           //+dsptchVO.getMailClass()
								           +dsptchVO.getMailCategoryCode()
								           +dsptchVO.getMailSubclass()
								           +dsptchVO.getDsn()
								           +dsptchVO.getYear();
									    if(dsnpk.equals(despatchpk)){
									    	if("I".equals(dsptchVO.getOperationalFlag())){
									    		dsnVO.setOperationFlag("U");
									    	}
											rcvdBags = rcvdBags + despatchVO.getReceivedBags();
											//rcvdWgt = rcvdWgt + despatchVO.getReceivedWeight();
											rcvdWgt = rcvdWgt + despatchVO.getReceivedWeight().getRoundedSystemValue();//added by A-7550
											delvdBags = delvdBags + despatchVO.getDeliveredBags();
											//delvdWgt = delvdWgt + despatchVO.getDeliveredWeight();
											delvdWgt = delvdWgt + despatchVO.getDeliveredWeight().getRoundedSystemValue();//added by A-7371
										}
								}
								if(dsnVO.getReceivedBags()!= rcvdBags
									|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= rcvdWgt
									|| dsnVO.getDeliveredBags()!= delvdBags
									|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= delvdWgt){
									if(!"I".equals(dsnVO.getOperationFlag())){
										dsnVO.setOperationFlag("U");
									}
								}
								dsnVO.setReceivedBags(rcvdBags);
								//dsnVO.setReceivedWeight(rcvdWgt);
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvdWgt));//Added by A-7550
								dsnVO.setDeliveredBags(delvdBags);
								//dsnVO.setDeliveredWeight(delvdWgt);
								dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, delvdWgt));//Added by A-7550
								dsnMapDespatch.put(outerpk,dsnVO);
							}
						  }
						despatchMap.put(outerpk,outerpk);
						}
						rcvdBags = 0;
						rcvdWgt = 0;
						delvdBags = 0;
						delvdWgt = 0;
						}
					}


				 /**
				  * For Mail Bag Details
				  */

				 HashMap<String,String> mailMap = new HashMap<String,String>();
				 HashMap<String,DSNVO> dsnMapMailbag = new HashMap<String,DSNVO>();
					if(mainDSNVOs != null && mainDSNVOs.size() > 0){
						for(DSNVO dsnVO:mainDSNVOs){
							if("Y".equals(dsnVO.getPltEnableFlag())){
								if(!"I".equals(dsnVO.getOperationFlag())){
								String dsnpk = dsnVO.getOriginExchangeOffice()
						           +dsnVO.getDestinationExchangeOffice()
						           //added by anitha
						           //+dsnVO.getMailClass()
						           +dsnVO.getMailCategoryCode()
						           +dsnVO.getMailSubclass()
						           +dsnVO.getDsn()
						           +dsnVO.getYear();
								dsnMapMailbag.put(dsnpk,dsnVO);
								}
							}
					    }
					}

				int numBags = 0;
				double bagWgt = 0;
				int dlvBags = 0;
				double dlvWgt = 0;
				 Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
				 if(mailbagVOs != null && mailbagVOs.size() > 0){
					for(MailbagVO mailbagVO:mailbagVOs){
						
						String outerpk = mailbagVO.getOoe()+mailbagVO.getDoe()
						//added by anitha
								   + mailbagVO.getMailCategoryCode()
						           + mailbagVO.getMailSubclass()
						           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
						if(mailMap.get(outerpk) == null){
						if(dsnMapMailbag.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
							//added by anitha
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag("Y");
							dsnVO.setOperationFlag("I");
						for(MailbagVO innerVO:mailbagVOs){
							String innerpk = innerVO.getOoe()+innerVO.getDoe()
							//added by anitha
							+ innerVO.getMailCategoryCode()
					           +(innerVO.getMailSubclass())
					           +innerVO.getDespatchSerialNumber()+innerVO.getYear();
							if(outerpk.equals(innerpk)){
								if("Y".equals(innerVO.getArrivedFlag())){
									dsnVO.setReceivedBags(numBags + 1);
									//dsnVO.setReceivedWeight(bagWgt + innerVO.getWeight());
									try {
										dsnVO.setReceivedWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,0.0,bagWgt,innerVO.getWeight().getDisplayUnit()), innerVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}//modified by A-7371			
									//Added by A-4809 to add received bags and wgts for same dsn-Starts
									numBags= dsnVO.getReceivedBags();
									//bagWgt = dsnVO.getReceivedWeight();
									bagWgt = dsnVO.getReceivedWeight().getRoundedSystemValue(); // Added by A-7550
									//Added by A-4809-Ends
								}
								if("Y".equals(innerVO.getDeliveredFlag())){
									dsnVO.setDeliveredBags(dlvBags + 1);
									//dsnVO.setDeliveredWeight(dlvWgt + innerVO.getWeight());
									try {
										dsnVO.setDeliveredWeight(Measure.addMeasureValues(new Measure(UnitConstants.MAIL_WGT,dlvWgt),  innerVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										log.log(Log.SEVERE, "UnitException",e.getMessage());
									}//modified by A-7371
									//Added by A-4809 to add delivered bags and wgts for same dsn
									dlvBags = dsnVO.getDeliveredBags();
									//dlvWgt = dsnVO.getDeliveredWeight();
									dlvWgt = dsnVO.getDeliveredWeight().getRoundedSystemValue(); // Added by A-7550
									//Added by A-4809-Ends
								}
							}
						}
						dsnMapMailbag.put(outerpk,dsnVO);
						}else{
							DSNVO dsnVO = dsnMapMailbag.get(outerpk);
							String dsnpk = dsnVO.getOriginExchangeOffice()
					           +dsnVO.getDestinationExchangeOffice()
					           //added by anitha
					           //+dsnVO.getMailClass()
					           +dsnVO.getMailCategoryCode()
					           +dsnVO.getMailSubclass()
					           +dsnVO.getDsn()
					           +dsnVO.getYear();
							if(mailbagVOs.size() > 0){
								for(MailbagVO mbagVO:mailbagVOs){
								String mailpk = mbagVO.getOoe()+mbagVO.getDoe()
								//added by anitha
								   + mbagVO.getMailCategoryCode()
						           + mbagVO.getMailSubclass()
						           + mbagVO.getDespatchSerialNumber()+mbagVO.getYear();
									    if(dsnpk.equals(mailpk)){
									    	if("I".equals(mbagVO.getOperationalFlag())
									    			|| "U".equals(mbagVO.getOperationalFlag())){
									    		dsnVO.setOperationFlag("U");
									    	}
											if("Y".equals(mbagVO.getArrivedFlag()) ){
												numBags = numBags + 1;
												if(mbagVO.getWeight()!=null){
													//bagWgt = bagWgt + mbagVO.getWeight()
												bagWgt = bagWgt + mbagVO.getWeight().getRoundedSystemValue();//added by A-7550
												}
											}
											//added by anitha - start
											if("Y".equals(mbagVO.getDamageFlag())){									
												dsnVO.setOperationFlag("U");
										    }
											//added by anitha - end
											if("Y".equals(mbagVO.getDeliveredFlag())){
												dlvBags = dlvBags + 1;
												if(mbagVO.getWeight()!=null){
												dlvWgt = dlvWgt + mbagVO.getWeight().getRoundedSystemValue();//added by A-7371
												}
											}
										}
								}
								//if(dsnVO.getReceivedBags()!= numBags
								//		|| dsnVO.getReceivedWeight()!= bagWgt){
								//Added by A-7550
								if(dsnVO.getReceivedBags()!= numBags
											|| dsnVO.getReceivedWeight().getRoundedSystemValue()!= bagWgt){
										dsnVO.setOperationFlag("U");
								}
								dsnVO.setReceivedBags(numBags);
								//dsnVO.setReceivedWeight(bagWgt);
								dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, bagWgt)); //Added by A-7550
								//if(dsnVO.getDeliveredBags()!= dlvBags
								//		|| dsnVO.getDeliveredWeight()!= dlvWgt){
								//Added by A-7550
								if(dsnVO.getDeliveredBags()!= dlvBags
											|| dsnVO.getDeliveredWeight().getRoundedSystemValue()!= dlvWgt){
									
											dsnVO.setOperationFlag("U");
									}
								dsnVO.setDeliveredBags(dlvBags);
								//dsnVO.setDeliveredWeight(dlvWgt);
								dsnVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT, dlvWgt)); //Added by A-7550
								dsnMapMailbag.put(outerpk,dsnVO);
							}
						  }
						mailMap.put(outerpk,outerpk);
						}
						numBags = 0;
						bagWgt = 0;
						dlvBags = 0;
						dlvWgt = 0;
					}
				}

				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for(String key:dsnMapDespatch.keySet()){
					DSNVO dsnVO = dsnMapDespatch.get(key);
					newDSNVOs.add(dsnVO);
				}
				for(String key:dsnMapMailbag.keySet()){
					DSNVO dsnVO = dsnMapMailbag.get(key);
					if(newDSNVOs.size() == 0){
					   newDSNVOs = new ArrayList<DSNVO>();
					}
					newDSNVOs.add(dsnVO);
				}

				Collection<DSNVO> oldDSNVOs = popupVO.getDsnVOs();
				int accBags = 0;
				double accWgt = 0;
				int rcvedBags = 0;
				double rcvedWgt = 0;
				if(newDSNVOs.size() > 0){
					for(DSNVO dsnVO1:newDSNVOs){
						String outerpk = dsnVO1.getOriginExchangeOffice()
						   +dsnVO1.getDestinationExchangeOffice()
						   //added by anitha
				           //+dsnVO1.getMailClass()
						   +dsnVO1.getMailCategoryCode()
						   +dsnVO1.getMailSubclass()
				           +dsnVO1.getDsn()+dsnVO1.getYear();
						int flag = 0;
						if(oldDSNVOs != null && oldDSNVOs.size() > 0){
							for(DSNVO dsnVO2:oldDSNVOs){
								String innerpk = dsnVO2.getOriginExchangeOffice()
								   +dsnVO2.getDestinationExchangeOffice()
								   //added by anitha
						           //+dsnVO2.getMailClass()
								   +dsnVO2.getMailCategoryCode()
								   +dsnVO2.getMailSubclass()
						           +dsnVO2.getDsn()+dsnVO2.getYear();
								if(outerpk.equals(innerpk)){
									if(!"I".equals(dsnVO2.getOperationFlag())){
										dsnVO1.setPrevBagCount(dsnVO2.getPrevBagCount());
										dsnVO1.setPrevBagWeight(dsnVO2.getPrevBagWeight());
                                        dsnVO1.setPrevReceivedBags(dsnVO2.getPrevReceivedBags());
                                        dsnVO1.setPrevReceivedWeight(dsnVO2.getPrevReceivedWeight());
                                        dsnVO1.setPrevDeliveredBags(dsnVO2.getPrevDeliveredBags());
                                        dsnVO1.setPrevDeliveredWeight(dsnVO2.getPrevDeliveredWeight());
									}
									flag = 1;
								}
							}
						}
						if(flag == 1){
							flag = 0;
						}
						accBags = accBags + dsnVO1.getBags();
						//accWgt = accWgt + dsnVO1.getWeight();
						if(dsnVO1.getWeight()!=null){
						accWgt = accWgt + dsnVO1.getWeight().getRoundedSystemValue(); //Added by A-7550
						}
						rcvedBags = rcvedBags + dsnVO1.getReceivedBags();
						rcvedWgt = rcvedWgt + dsnVO1.getReceivedWeight().getRoundedSystemValue(); //Added by A-7550
					}
				}
				popupVO.setTotalBags(accBags);
				//popupVO.setTotalWeight(accWgt);
				popupVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, accWgt)); //Added by A-7550
				popupVO.setReceivedBags(rcvedBags);
				//popupVO.setReceivedWeight(rcvedWgt);
				popupVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT, rcvedWgt)); //Added by A-7550
				popupVO.setDsnVOs(newDSNVOs);
			}
    	 }

    	return newContainerDetailsVOs;
    }
    /**
     * 
     * @param mailArrivalForm
     * @return
     */
    private String constructUnsavedDataFlag(MailArrivalForm mailArrivalForm,String unsavedDataFlag ){

    	if(mailArrivalForm.getMailOpFlag()!=null && mailArrivalForm.getMailOpFlag().length>0){
    		for(int i=0;i<mailArrivalForm.getMailOpFlag().length;i++){
    			if("I".equals(mailArrivalForm.getMailOpFlag()[i])){
    				return "Y";
    			}
    		}
    	}
    	if(mailArrivalForm.getDespatchOpFlag()!=null && mailArrivalForm.getDespatchOpFlag().length>0){
    		for(int i=0;i<mailArrivalForm.getDespatchOpFlag().length;i++){
    			if("I".equals(mailArrivalForm.getDespatchOpFlag()[i])){
    				return "Y";
    			}
    		}
    	}
    	return unsavedDataFlag;
    }
}
