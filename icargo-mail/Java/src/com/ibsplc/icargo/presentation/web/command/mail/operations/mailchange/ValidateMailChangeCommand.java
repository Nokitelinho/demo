/*
 * ValidateMailChangeCommand.JAVA Created on Jul 1 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailchange;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class to validate dsn for change flight operation
 * @author a-5991
 *
 */
public class ValidateMailChangeCommand extends BaseCommand { 
	
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	      
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	   private static final String CLASS_NAME = "ValidateMailChangeCommand";

	   private static final String TARGET_SUCCESS = "success";
	   private static final String TARGET_FAILURE = "failure";
	   private static final String SHOW_MAIL_POPUP = "showMailPopUpPopUp";
	   private static final String PARTIALLY_ARRIVED = "partiallydelivered";
	   private static final String DSN = "DSN";
	   private static final String IMPORTED_TO_MRA = "I";
	   private static final String UNSAVED_DATA = "Unsaved Data";
	   private static final String MAIL_NOT_ARRIVED = "mailtracking.defaults.changeflight.mailnotarrived";
	   private static final String MAILBAS_IMPORTED_TO_MRA = "mailtracking.defaults.changeflight.mailbagsimportedtoMRA";
	   private static final String MAILBAGS_TRANSFERRED_OR_DELIVERED = "mailtracking.defaults.changeflight.containertransferredordelivered";
	   private static final String CONTAINERS_PARTIALLY_ARRIVED = "mailtracking.defaults.mailchange.partiallyarrived";
	   
	   
	   
	   
		 /**
		 * This method overrides the execute method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException 
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering(CLASS_NAME,"execute");
	    	
	    	MailArrivalForm mailArrivalForm = (MailArrivalForm)invocationContext.screenModel;
	    	MailArrivalSession mailArrivalSession = 
	        		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ArrayList<ContainerDetailsVO> containerDetailsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
			String[] selectedChildContainers =mailArrivalForm.getChildCont().split(",");
			
			errors=validateForm(mailArrivalForm,containerDetailsVOs,selectedChildContainers);
			if(errors!=null&& errors.size() > 0){
				mailArrivalSession.setMailArrivalVO(mailArrivalVO);
	    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			mailArrivalForm.setChangePopUpFlag(SHOW_MAIL_POPUP);
			invocationContext.target = TARGET_SUCCESS;		 	
	       	
	    	log.exiting("CLASS_NAME","execute");
	    }
private Collection<ErrorVO> validateForm(MailArrivalForm mailArrivalForm,ArrayList<ContainerDetailsVO> containerDetailsVOs,
		String[] selectedChildContainers) {
	
	
	int sizeOfChildContainers = 0;	
	boolean isPartiallyArrived=false;
	if(selectedChildContainers !=null){
		sizeOfChildContainers = selectedChildContainers.length;
	} 
	
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	
	//If more than one dsns are selected form different conatiners 
			//need to check whether the selected dsn s are arrived or not/transferred or not/delivered or not before go for the undo arrival operation
			if(selectedChildContainers!=null&&sizeOfChildContainers>0){
	    				for(int i=0;i<selectedChildContainers.length;i++){
	    					int count=0;
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
							if(selectedDSN.equals(selectedMail)){
							if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
								if(dsnVO.getReceivedBags()!=0&&(dsnVO.getReceivedBags()<dsnVO.getBags())){  
									isPartiallyArrived=true;
								}
								if(dsnVO.getBags()!=0&&dsnVO.getReceivedBags()==0){
									errors.add(new ErrorVO(MAIL_NOT_ARRIVED));
									return errors;
								}  
							
							}   
							
							if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
							if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){
								//String selectedDSN=new StringBuilder(dsnVO.getOriginExchangeOffice()).append(dsnVO.getDestinationExchangeOffice()).append(dsnVO.getMailCategoryCode()).append(dsnVO.getMailSubclass()).append(dsnVO.getYear()).toString();
								//String selectedMail=new StringBuilder(mail.getOoe()).append(mail.getDoe()).append(mail.getMailCategoryCode()).append(mail.getMailSubclass()).append(mail.getYear()).toString();
							 
								if(dsnVO.getReceivedBags()==0){
									errors.add(new ErrorVO(MAIL_NOT_ARRIVED));
									return errors;

								}else if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag())){
									//unsaved save information
									if(selectedDSN.equals(selectedMail)){ 
										mailArrivalForm.setWarningFlag(UNSAVED_DATA);										
										return errors;
									}
								}
								//To check whether the mailbag is transfered or delivered
								else if(mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
										!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||                            
										MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())|| 
										MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
										||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus())
										 ){
									if(selectedDSN.equals(selectedMail)){ 
										count++;
										isPartiallyArrived=true;
									}

								}else 	if(IMPORTED_TO_MRA.equals(mail.getMraStatus())){
									errors.add(new ErrorVO(MAILBAS_IMPORTED_TO_MRA));
									return errors;	
								}
								else{
									if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag()) && !MailConstantsVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag())){
									mail.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
									containerVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
									containerVO.setUndoArrivalFlag(DSN); 
								}
								}     
								if(count==dsnVO.getReceivedBags()){
									errors.add(new ErrorVO(MAILBAGS_TRANSFERRED_OR_DELIVERED));            
									return errors;
								}
								}
							}
							}
						}

					}
				}
			}
			if(isPartiallyArrived){
				mailArrivalForm.setWarningFlag(PARTIALLY_ARRIVED);
				errors.add(new ErrorVO(CONTAINERS_PARTIALLY_ARRIVED)); 
			}
			return errors;
	}
}
