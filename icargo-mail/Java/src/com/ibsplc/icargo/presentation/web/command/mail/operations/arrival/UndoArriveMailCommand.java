
/*
 * UndoArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5526
 *
 */
public class UndoArriveMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "save_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   private static final String SAVE_SUCCESS = 
		"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
  

   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException { 
    	
    	log.entering("UndoArriveMailCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    
		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		MailArrivalVO mailvo = mailArrivalSession.getMailArrivalVO();
		
		ArrayList<ContainerDetailsVO> containerDetailsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
		String[] selectedContainers = mailArrivalForm.getSelectContainer();
		String[] selectedChildContainers = mailArrivalForm.getChildContainer();
		int sizeOfContainers = 0;
		boolean isContainerSelected = false;
		boolean isDSNSelected = false;
		
		int sizeOfChildContainers = 0;
		if(selectedContainers !=null) {
			sizeOfContainers = selectedContainers.length;
			isContainerSelected = true;
		} else {
			sizeOfContainers = containerDetailsVOs.size();
		}
		
		if(selectedChildContainers !=null)
		{
			sizeOfChildContainers = selectedChildContainers.length;
			//dsns of only 1 container can be selected
			sizeOfContainers = 1;
			isDSNSelected = true;
		} 
		//If both container and dsn are selected together need to throw one error message
		if(isContainerSelected && isDSNSelected){
			log.log(Log.FINE, "Conatiner and Mailbag selected,either one to be select");
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.eithercontormail"));
			invocationContext.target = TARGET;
			return;
		}
		//If more than one dsns are selected form different conatiners 
		//need to check whether the selected dsn s are arrived or not/transferred or not/delivered or not before go for the undo arrival operation
		if(selectedChildContainers!=null){
    				for(int i=0;i<selectedChildContainers.length;i++){
	    				int val=Integer.parseInt(selectedChildContainers[i].split("~")[1]);
	    				int containerVal = Integer.parseInt(selectedChildContainers[i].split("~")[0]);
	    				ContainerDetailsVO containerVO = containerDetailsVOs.get(containerVal);
				ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)containerVO.getDsnVOs();
				DSNVO dsnVO = dsnVos.get(val);
				if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){//DSNs at mailbag level is selected
					log.log(Log.FINE, "mailbag is selected");       
					Collection<MailbagVO> mailbagVOs= containerVO.getMailDetails();
					for(MailbagVO mail: mailbagVOs){
							String selectedDSN=new StringBuilder(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice()).append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass()).append(dsnVO.getYear()).toString();
							String selectedMail=new StringBuilder(mail.getOoe()).append(mail.getDoe()).append(mail.getMailCategoryCode()).append(mail.getMailSubclass()).append(mail.getYear()).toString();
						if(selectedDSN.equals(selectedMail)){//Added as part of bug ICRD-151355
						//Added as part of bug ICRD-130056 by A-5526 starts
						if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
							if(dsnVO.getReceivedBags()==0){  
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailnotarrived"));
								invocationContext.target = TARGET;
								return;
							}  
						
						}   
						//Added as part of bug ICRD-130056 by A-5526 ends
						
						if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
						if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
							//String selectedDSN=new StringBuilder(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice()).append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass()).append(dsnVO.getYear()).toString();
							//String selectedMail=new StringBuilder(mail.getOoe()).append(mail.getDoe()).append(mail.getMailCategoryCode()).append(mail.getMailSubclass()).append(mail.getYear()).toString();
						 
							if(dsnVO.getReceivedBags()==0){
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailnotarrived"));
								invocationContext.target = TARGET;
								return;
							}else if("I".equals(mail.getOperationalFlag())){
								//unsaved save information
								if(selectedDSN.equals(selectedMail)){ 
									mailArrivalForm.setWarningFlag("Unsaved Data");
								//	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.unsaveddata"));
									invocationContext.target = TARGET;
									return;         	
								}
							}
							//To check whether the mailbag is transfered or delivered
							else if(mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
									!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||                            
									MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())|| 
									MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
									||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()) ){
								if(selectedDSN.equals(selectedMail)){ 
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailtransferredordelivered"));
								invocationContext.target = TARGET;              
								return;
								}
							}else 	if("I".equals(mail.getMraStatus())){
								invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.mailbagsimportedtoMRA"));
								invocationContext.target = TARGET;
								return;	
							}
							else{
								if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag()) && !"I".equals(mail.getOperationalFlag())){
								mail.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
								containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
								//Added as part of bug ICRD-130056 by A-5526 
								containerVO.setUndoArrivalFlag("DSN");    
							}
							}
							}          
						}
						}
					}
				}
			}
		}else{ 
			//If a container is selected entire mailbags inside that container need to be considered.
			//So need to check whether any of the mailbags are arrived or not/transferred or not/delivered or not 
			//before go for undo arrival operation
			
			int conIdx = 0;
			//if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
			if(sizeOfContainers > 0){
				for(int iter=0;iter<sizeOfContainers;iter++)
				{
					/*for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs)*/
					if(isContainerSelected) {
						//if container selced take tht
						conIdx = Integer.parseInt(selectedContainers[iter]);					
					} 
			if(containerDetailsVOs!=null && containerDetailsVOs.size()>0){
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				boolean containerArrived = false;
				boolean importedToMRA = false;
				ContainerDetailsVO contVO=containerDetailsVOs.get(conIdx);   
				  
					if(!(contVO.getMailDetails().size()==0)){
						for(MailbagVO mailbag : contVO.getMailDetails()){
							if(MailConstantsVO.FLAG_YES.equals(mailbag.getArrivedFlag())){
								containerArrived = true;
								
							} 
									if("I".equals(mailbag.getMraStatus())){
									importedToMRA=true;
						
								}
							
					}
					} 	if(!containerArrived){
							error.add(new ErrorVO("mailtracking.defaults.mailarrival.containernotarrived"));
							invocationContext.addAllError(error);
							invocationContext.target = TARGET;
							return;	 	
					}        
					ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)contVO.getDsnVOs();        
					if(dsnVos!=null && dsnVos.size()>0){
						for(DSNVO dsnVO:dsnVos){
							if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){
								Collection<MailbagVO> mailbagVOs= contVO.getMailDetails();	
								for(MailbagVO mail: mailbagVOs){
									if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
									if("I".equals(mail.getOperationalFlag())){
										if(!"".equals(mailArrivalForm.getWarningFlag())){
										mailArrivalForm.setWarningFlag("Unsaved Data");
										invocationContext.target = TARGET;
										return;      
										}
									}                      
									if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){            
										if(MailConstantsVO.FLAG_YES.equals(mail.getTransferFlag()) || 
												MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())||
												mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
												!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||
												MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
														||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()) ){
											if(!"I".equals(mail.getOperationalFlag())){
											error.add(new ErrorVO("mailtracking.defaults.mailarrival.containertransferredordelivered"));
											invocationContext.addAllError(error);
											invocationContext.target = TARGET;
											return;         
											}
										}else if(importedToMRA){
								error.add(new ErrorVO("mailtracking.defaults.mailarrival.mailbagsimportedtoMRA"));
								invocationContext.addAllError(error);
								invocationContext.target = TARGET;
								return;	 
						
					
										
									}else{
											if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
												if(!"I".equals(mail.getOperationalFlag())){
												//Undo arrive flag need to be set only for those arrived mailbags
										mail.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
										}
											}
										}
										}
									}
								}
							}
						}
					}
					if( error.size()==0){
					contVO.setUndoArrivalFlag("CON");  
					contVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					}
				//}
			}
				}
			}
		}     
		// Error thowing for already arrived case..Ends

		
		if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			containerDtlsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
    		}
    	}
		
		mailArrivalVO.setContainerDetails(containerDetailsVOs);
		
		log.log(Log.FINE, "Going To Save ...in command...UndoArrival...",
				mailArrivalVO);
		
		
		
		         
		  try {
		    new MailTrackingDefaultsDelegate().saveUndoArrivalDetails(mailArrivalVO);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
    	  if (errors != null && errors.size() > 0) {
    			for(ErrorVO err : errors){
					if("mailtracking.defaults.err.mailAlreadyTransferedOrDelivered".equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO("mailtracking.defaults.mailarrival.containertransferredordelivered");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
					}else if ("mailtracking.defaults.err.mailNotYetArrived".equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO("mailtracking.defaults.err.mailNotYetArrived");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
					}
							else{
    		invocationContext.addAllError(errors);
					}
				}
    		mailArrivalSession.setMailArrivalVO(mailArrivalVO);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
    	  }
    	
    	MailArrivalVO mailArriveVO = new MailArrivalVO(); 
    	//Added for ICRD-134007 starts
    	LocalDate arrivalDate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
    	arrivalDate.setDate(mailArrivalForm.getArrivalDate());
    	mailArriveVO.setFlightCarrierCode(mailArrivalForm.getFlightCarrierCode());
    	mailArriveVO.setFlightNumber(mailArrivalForm.getFlightNumber());
    	mailArriveVO.setArrivalDate(arrivalDate);
    	//Added for ICRD-134007 ends
    	mailArrivalSession.setMailArrivalVO(mailArriveVO);
    	mailArrivalForm.setSaveSuccessFlag(FLAG_YES);//Added for ICRD-134007
    	//mailArrivalForm.setListFlag("FAILURE");//Commented for ICRD-134007
    	mailArrivalForm.setOperationalStatus("");
    	FlightValidationVO flightValidationVO = new FlightValidationVO();
    	mailArrivalSession.setFlightValidationVO(flightValidationVO);
    	
    	mailArrivalForm.setInitialFocus(FLAG_YES);
    	mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
		
    	//invocationContext.addError(new ErrorVO(SAVE_SUCCESS));//Commented for ICRD-134007
    	
    	invocationContext.target = TARGET;
       	
    	log.exiting("ArriveMailCommand","execute");
    	
    }
  
    
}
