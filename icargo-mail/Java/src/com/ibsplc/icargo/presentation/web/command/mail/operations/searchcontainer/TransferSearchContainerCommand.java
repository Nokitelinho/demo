/*
 * TransferSearchContainerCommand.java Created on Oct 05, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class TransferSearchContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "success";
   private static final String TARGET_FAILURE = "failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
   private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.transfercontainer";
   private static final String MAILS_TO_DELIVER = "deliverable_mails_present";
   
   private static final String CONST_TRANSFER = "showTransferScreen";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("TransferSearchContainerCommand","execute");
    	  
      	SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationContext.screenModel;
    	SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	TransferContainerSession transferContainerSession = 
    		(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	
    	Collection<ErrorVO> errors = null;
    	
       String[] primaryKey = searchContainerForm.getSelectContainer();
        	
	   int cnt=0;
	   int count = 1;
       int primaryKeyLen = primaryKey.length;
       Collection<ContainerVO> containerVOs = 
    	   			searchContainerSession.getListContainerVOs();
       Collection<String> nearbyOEToCurrentAirport  = new ArrayList<String>();
       Collection<ContainerVO> reasgnContainerVOs = 
    	   						new ArrayList<ContainerVO>();
       if (containerVOs != null && containerVOs.size() != 0) {
       	for (ContainerVO containerVO : containerVOs) {
       		String primaryKeyFromVO = containerVO.getCompanyCode()
       				+String.valueOf(count);
       		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
       				          equalsIgnoreCase(primaryKey[cnt].trim())) {
       			reasgnContainerVOs.add(containerVO);
       			cnt++;
       		}
       		count++;
       	  }
       	}
       for(ContainerVO containerVO : reasgnContainerVOs){
    	   if(logonAttributes.getAirportCode().equals(containerVO.getFinalDestination())){
    		   ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.containeratfinaldest");
    		   invocationContext.addError(errorVO);
    		   invocationContext.target = TARGET_FAILURE;
      	    	return;
    	   }
       }
       
       //  VALIDATE flight or destn assigned 
       int errorPort = 0;
       String contPort = "";
	   	for (ContainerVO selectedvo : reasgnContainerVOs) {
	   		if (selectedvo.getContainerNumber() != null ) {
	   			if("-1".equals(selectedvo.getFlightNumber())){
      				errorPort = 1;
      				if("".equals(contPort)){
      					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
  					                  .append(",")
  					                  .append(selectedvo.getContainerNumber())
  					                  .toString();	
	       			}
      			}
	   		}
	   	}
	   	
	    if(errorPort == 1){
  	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.destnassigned",new Object[]{contPort}));
  	    	invocationContext.target = TARGET_FAILURE;
  	    	return;
       }
       
//		  validate whether already transferred
     int transferError = 0;
     contPort = "";
	   	for (ContainerVO selectedvo : reasgnContainerVOs) {
	   		if (selectedvo.getContainerNumber() != null ) {
	   			if("Y".equals(selectedvo.getTransferFlag())){
	   				transferError = 1;
    				if("".equals(contPort)){
    					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
					                  .append(",")
					                  .append(selectedvo.getContainerNumber())
					                  .toString();	
	       			}
    			}
	   		}
	   	}
	   	
	    if(transferError == 1){
	    	invocationContext.addError(
	    			new ErrorVO("mailtracking.defaults.msg.err.alreadytransferred",
	    					new Object[]{contPort}));
	    	invocationContext.target = TARGET_FAILURE;
	    	return;
     }
       
	    //  VALIDATE for final destination 
       errorPort = 0;
      contPort = "";
	   	for (ContainerVO selectedvo : reasgnContainerVOs) {
	   		if (selectedvo.getContainerNumber() != null ) {
	   			if(logonAttributes.getAirportCode().equals(selectedvo.getFinalDestination())){
      				errorPort = 1;
     				if("".equals(contPort)){
      					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
  					                  .append(",")
  					                  .append(selectedvo.getContainerNumber())
 					                  .toString();	
	       			}
      			}	   		}
	   	}
	   	
	    if(errorPort == 1){
  	    	/*invocationContext.addError(
 	    			new ErrorVO("mailtracking.defaults.msg.err.currportfinaldest",  	    					new Object[]{contPort}));
 	    	invocationContext.target = TARGET_FAILURE;
  	    	return;*/
       }
	    
	    //  VALIDATION FOR Assigned port and current airport
        errorPort = 0;
        contPort = "";
	   	for (ContainerVO selectedvo : reasgnContainerVOs) {
	   		if (selectedvo.getContainerNumber() != null ) {
	   			if(logonAttributes.getAirportCode().equals(selectedvo.getAssignedPort())){
       				errorPort = 1;
       				if("".equals(contPort)){
       					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
   					                  .append(",")
   					                  .append(selectedvo.getContainerNumber())
   					                  .toString();	
	       			}
       			}
	   		}
	   	}
	   	
	    if(errorPort == 1){
   	    	/*invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.samescannedport",new Object[]{contPort}));
   	    	invocationContext.target = TARGET_FAILURE;
   	    	return;*/
        }
       //Commented by A-7371 for ICRD-133998 starts
      //  VALIDATION FOR POU and current airport
      /* errorPort = 0;
       contPort = "";
	   	for (ContainerVO selectedvo : reasgnContainerVOs) {
	   		if (selectedvo.getContainerNumber() != null ) {
	   			if(!logonAttributes.getAirportCode().equals(selectedvo.getPou())){
       				errorPort = 1;
       				if("".equals(contPort)){
       					contPort = selectedvo.getContainerNumber();
	       			}else{
	       				contPort = new StringBuilder(contPort)
   					                  .append(",")
   					                  .append(selectedvo.getContainerNumber())
   					                  .toString();	
	       			}
       			}
	   		}
	   	}*/
	   	
	    /*if(errorPort == 1){
   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.differentpou",new Object[]{contPort}));
   	    	invocationContext.target = TARGET_FAILURE;
   	    	return;
        }*/
	  //Commented by A-7371 for ICRD-133998 ends
	    
	    //added by A-7371 for ICRD-133998 starts
	 // VALIDATE CARRIER CODE
    	String carrierCode = searchContainerForm.getCarrier(); //added by A-7371 for ICRD-133987
    		if(logonAttributes.getOwnAirlineCode().equals(carrierCode)){
    		//throw error 'OAL transfer cannot be done to own airline'
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.err.ownairline");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errorVO);
			invocationContext.target = TARGET_FAILURE;
			return;
        }
    	    //added by A-7371 for ICRD-133998 ends
	    MailbagVO mailbagToDeliver = null; 
	      //  VALIDATING ULD RELEASE FROM THE CURRENT SEGMENT
	       errorPort = 0;
	       contPort = "";
		   	for (ContainerVO selectedvo : reasgnContainerVOs) {
		   		if (MailConstantsVO.FLAG_YES.equals(selectedvo.getReleasedFlag())) {
	       				errorPort = 1;
	       				contPort = selectedvo.getContainerNumber();
	       				break;
		   		}
		   	 //Added by A-5526 for bug ICRD-154686 starts
		   		if(MailConstantsVO.FLAG_YES.equals(selectedvo.getArrivedStatus())){
		   			errorPort = 2;
       				contPort = selectedvo.getContainerNumber();    
       				break;
		   		}
		   	 //Added by A-5526 for bug ICRD-154686 ends
		   	}
		    if(errorPort == 1){
	   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.cannottransferuldalreadyreleasedfromflight",new Object[]{contPort}));
	   	    	invocationContext.target = TARGET_FAILURE;
	   	    	return;
	        }
		    //Added by A-5526 for bug ICRD-154686 starts
		    if(errorPort == 2){
	   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.cannottransferuldalreadyarrivedfromflight",new Object[]{contPort}));
	   	    	invocationContext.target = TARGET_FAILURE;
	   	    	return;
	        }
		    //Added by A-5526 for bug ICRD-154686 ends
		    //doubt ??
		   // String airport = searchContainerForm.getDeparturePort();
		   // if(airport == null || "".equals(airport)){
		   
		  //  }
		    //MODIFIED FOR ICRD-95515
		    String airport = logonAttributes.getAirportCode();
		    try {
				 nearbyOEToCurrentAirport  = 
					new MailTrackingDefaultsDelegate().findOfficeOfExchangesForAirport(
							logonAttributes.getCompanyCode(), airport);
			}catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> err = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
			}	
		    Collection<ContainerDetailsVO> containerDetailsVOs =  new ArrayList<ContainerDetailsVO>();
		    containerDetailsVOs = makeContainerDetailsVO(reasgnContainerVOs);
		    Collection<ContainerDetailsVO> containerVOsToTransfer = new ArrayList<ContainerDetailsVO>();
		    int errorFlag = 0;
		    if(containerDetailsVOs != null && containerDetailsVOs.size() >0){
		    try {
		    	containerVOsToTransfer = new MailTrackingDefaultsDelegate().findMailbagsInContainerForImportManifest(containerDetailsVOs);
			}catch (BusinessDelegateException businessDelegateException) {
				log
						.log(
								Log.SEVERE,
								"BusinessDelegateException---findMailbagsInContainerForManifest",
								businessDelegateException.getMessage());
			}
		    }
				if(containerVOsToTransfer != null && containerVOsToTransfer.size() >0){
					for(ContainerDetailsVO containerDtlsVO :containerVOsToTransfer){
						if(containerVOsToTransfer != null && containerVOsToTransfer.size() >0) {
						for(DSNVO dsnvo : containerDtlsVO.getDsnVOs()) {
							if(dsnvo.getMailbags()!= null && dsnvo.getMailbags().size() >0){
							for(MailbagVO  mailvo:dsnvo.getMailbags()){
								//log.log(Log.INFO, "<<<<<<DOE --- >>>", dsnVO.getDestinationExchangeOffice());
									if (!MailConstantsVO.FLAG_YES.equals(mailvo.getDeliveredFlag())) { 
										if(isTerminating(nearbyOEToCurrentAirport,mailvo)){	
										 mailbagToDeliver = mailvo;
											errorFlag = 1;
												break;
										}	
								}
							}
						}
					}
				}
				}
			}
			if(errorFlag ==1){
				if(!MailConstantsVO.FLAG_YES.equals(searchContainerForm.getWarningOveride())){
				Object[] obj = {mailbagToDeliver.getDoe()};
				ErrorVO err = new ErrorVO("mailtracking.defaults.deliverableMailbagsPresent",obj);
				err.setErrorDisplayType(ErrorDisplayType.WARNING);
				invocationContext.addError(err);
				invocationContext.target = TARGET_FAILURE;
				searchContainerForm.setWarningFlag(MAILS_TO_DELIVER);
				return;
				}
				else {
					searchContainerForm.setWarningFlag("");
					searchContainerForm.setWarningOveride(null);
				}
			}
		    	
       transferContainerSession.setSelectedContainerVOs(reasgnContainerVOs);
       transferContainerSession.setContainerVO(new ContainerVO());
  	   searchContainerForm.setStatus(CONST_TRANSFER);
       searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
       invocationContext.target = TARGET_SUCCESS;
       	
       log.exiting("TransferSearchContainerCommand","execute");
    	
    }
       /**
        * 
        * @param reasgnContainerVOs
        * @return
        */
	private Collection<ContainerDetailsVO> makeContainerDetailsVO(Collection<ContainerVO> reasgnContainerVOs) {
		// TODO Auto-generated method stub
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		for (ContainerVO containerVO :reasgnContainerVOs ) {
			//IF uld then only ---alert also !delivered flag check needed?
			if("U".equals(containerVO.getType()) && containerVO.getPol() != null ){
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
			containerDetailsVO.setCarrierId(containerVO.getCarrierId());
			containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
			containerDetailsVO.setPol(containerVO.getPol());
			containerDetailsVOs.add(containerDetailsVO);
		 }
		}
		return containerDetailsVOs;
	}
	/**
	 * 
	 * @param nearbyOEToCurrentAirport
	 * @param mailbagvo
	 * @return
	 */
	private boolean isTerminating(Collection<String> nearbyOEToCurrentAirport,
			MailbagVO mailbagvo){
		log.entering("TransferContainerCommand","break;");
		boolean isTerminating = false;
		if(nearbyOEToCurrentAirport != null && nearbyOEToCurrentAirport.size() > 0){
			for(String officeOfExchange : nearbyOEToCurrentAirport){
				if(mailbagvo != null && !"Y".equals(mailbagvo.getDeliveredFlag())){
					isTerminating = officeOfExchange.equals(mailbagvo.getDoe()) ? true : false;
					if(isTerminating){
						break;	
					}
				}
			}
		}
		log.exiting("TransferContainerCommand","break;");
		return isTerminating;
	}
}
