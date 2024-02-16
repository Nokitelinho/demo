/*
 * ValidateContainerChangeCommand.JAVA Created on Jul 1 2016
 *
 * Copyright 2016 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.containerchange;

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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-5991
 *
 */
public class ValidateContainerChangeCommand extends BaseCommand { 
	
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	      
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	   private static final String CLASS_NAME = "ValidateContainerChangeCommand";

	   private static final String TARGET_SUCCESS = "success";
	   private static final String TARGET_FAILURE = "failure";
	   private static final String SHOW_CONTAINER_POPUP = "showContainerPopUp";
	   private static final String IMPORTED_TO_MRA = "I";
	   private static final String UNSAVED_DATA = "Unsaved Data";
	   private static final String CONTAINER_NOT_ARRIVED ="mailtracking.defaults.changeflight.containernotarrived";
	   private static final String MAILBAS_IMPORTED_TO_MRA = "mailtracking.defaults.changeflight.mailbagsimportedtoMRA";
	   private static final String CONTAINER_TRANSFERRED_OR_DELIVERED ="mailtracking.defaults.changeflight.containertransferredordelivered";
	   private static final String CONTAINER_ALREADY_INAN_INBOUNDFLIGHT ="mailtracking.defaults.mailarrival.containernum.isalreadyinanoutboundflight";
	   
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
			String[] selectedContainers =mailArrivalForm.getChildCont().split(",");
			
			errors=validateForm(mailArrivalForm,containerDetailsVOs,selectedContainers);
			if(errors!=null&& errors.size() > 0){
				mailArrivalSession.setMailArrivalVO(mailArrivalVO);
	    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			mailArrivalForm.setChangePopUpFlag(SHOW_CONTAINER_POPUP);
			
			invocationContext.target = TARGET_SUCCESS;		 	
	       	
	    	log.exiting("CLASS_NAME","execute");
	    }
private Collection<ErrorVO> validateForm(MailArrivalForm mailArrivalForm,ArrayList<ContainerDetailsVO> containerDetailsVOs,String[] selectedContainers) {
	    	
	    	int conIdx = 0;	    	
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
	    	int sizeOfContainers=selectedContainers.length;
			if(sizeOfContainers > 0){
				for(int iter=0;iter<sizeOfContainers;iter++){
						conIdx = Integer.parseInt(selectedContainers[iter]);				
	    	
	    	if(containerDetailsVOs!=null && containerDetailsVOs.size()>0){
				boolean containerArrived = false;
				boolean importedToMRA = false;
				ContainerDetailsVO contVO=containerDetailsVOs.get(conIdx);  
				  
					if(!(contVO.getMailDetails().size()==0)){
						for(MailbagVO mailbag : contVO.getMailDetails()){
							if(MailConstantsVO.FLAG_YES.equals(mailbag.getArrivedFlag())){
								containerArrived = true;
							}
							if(IMPORTED_TO_MRA.equals(mailbag.getMraStatus())){
								importedToMRA=true;
						
						}
							
					}
					} 	if(!containerArrived){
							errors.add(new ErrorVO(CONTAINER_NOT_ARRIVED));
							return errors;
					}        
					ArrayList<DSNVO> dsnVos = (ArrayList<DSNVO>)contVO.getDsnVOs();                                
					if(dsnVos!=null && dsnVos.size()>0){
						for(DSNVO dsnVO:dsnVos){
							if(FLAG_YES.equals(dsnVO.getPltEnableFlag())){                               
								Collection<MailbagVO> mailbagVOs= contVO.getMailDetails();	
								for(MailbagVO mail: mailbagVOs){         
									if(MailConstantsVO.FLAG_YES.equals(mail.getArrivedFlag())){
									if(MailConstantsVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag())){
										if(!"".equals(mailArrivalForm.getWarningFlag())){
										mailArrivalForm.setWarningFlag(UNSAVED_DATA);
										return errors;
										}
									}                      
									if(dsnVO.getDsn().equals(mail.getDespatchSerialNumber())){            
										if(MailConstantsVO.FLAG_YES.equals(mail.getTransferFlag()) || 
												MailConstantsVO.FLAG_YES.equals(mail.getDeliveredFlag())||
												mail.getFromFlightSequenceNumber()!= mail.getFlightSequenceNumber()||
												!(mail.getFromFightNumber().equals(mail.getFlightNumber()))||
												MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mail.getMailStatus())
														||MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mail.getMailStatus()) ){
											if(!MailConstantsVO.OPERATION_FLAG_INSERT.equals(mail.getOperationalFlag())){
												errors.add(new ErrorVO(CONTAINER_TRANSFERRED_OR_DELIVERED));
												return errors;
											}
										}else if(importedToMRA){
											errors.add(new ErrorVO(MAILBAS_IMPORTED_TO_MRA));
											return errors;
										
									}
									}
								}
							}
								
							}
						}
					}

					/*String containerNum = contVO.getContainerNumber();
					String containerType = contVO.getContainerType();
					MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
					ContainerAssignmentVO containerAssignmentVO = null;
					if (containerNum != null && containerType!=null) {         
						try {
							containerAssignmentVO = mailTrackingDefaultsDelegate.findLatestContainerAssignment(containerNum);
						} catch (BusinessDelegateException e) {
						}
						if(containerAssignmentVO!=null && 
								containerType.equals(containerAssignmentVO.getContainerType()) && 
								MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag()) ){
							if(logonAttributes.getAirportCode().equals(containerAssignmentVO.getAirportCode()) 
									|| (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getArrivalFlag()) &&
											!(logonAttributes.getAirportCode().equals(containerAssignmentVO.getAirportCode())))){
							Object[] obj = {new StringBuilder(containerAssignmentVO.getCarrierCode())
							 .append("").append(containerAssignmentVO.getFlightNumber()).append(" ").toString(),
							 containerAssignmentVO.getFlightDate().toDisplayDateOnlyFormat()};   
							errors.add(new ErrorVO(CONTAINER_ALREADY_INAN_INBOUNDFLIGHT,obj));      
							return errors;
							}
						}
						else if(containerAssignmentVO!=null && 
								containerType.equals(containerAssignmentVO.getContainerType()) && 
								MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())&&
								MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getReleasedFlag())){
							Object[] obj = {new StringBuilder(containerAssignmentVO.getCarrierCode())
							 .append("").append(containerAssignmentVO.getFlightNumber()).append(" ").toString(),
							 containerAssignmentVO.getFlightDate().toDisplayDateOnlyFormat()};   
							errors.add(new ErrorVO(CONTAINER_ALREADY_INAN_INBOUNDFLIGHT,obj));                          
						}
					}*/	
			}
	    	
	    }
	    
		}
			return errors;
	    }

}
