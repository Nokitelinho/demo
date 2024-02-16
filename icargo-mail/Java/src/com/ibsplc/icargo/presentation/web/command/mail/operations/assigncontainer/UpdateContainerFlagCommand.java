/*
 * UpdateContainerFlagCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory; 

/**
 * @author A-5991
 *
 */
public class UpdateContainerFlagCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String REASSIGNMAIL_SCREEN_ID = "mailtracking.defaults.reassignmail";	
   private static final String TARGET_SUCCESS_ADDNEW = "validate_container_addnew_success";
   private static final String TARGET_SUCCESS_OK = "validate_container_ok_success"; 
   private static final String CONST_ADDNEW = "ADDNEW";
   private static final String CON_REASSIGN_WARN_FLIGHT = "reassignContainerForFlight";
   private static final String ULD_NOT_RELEASED_FROM_INBOUND_FLIGHT = "uldnotreleasedfrominboundflight";
   private static final String ULD_ALREADY_ASSIGNED_TO_CARRIER = "uldalreadyassignedtocarrier";
   private static final String CON_ASSIGNEDTO_DIFFFLT="uldalreadyassignedtoflgt";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("UpdateContainerCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);   
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME,REASSIGNMAIL_SCREEN_ID);
    	
		String currentaction = assignContainerForm.getCurrentAction();
		int currentindex = assignContainerForm.getCurrentIndex();
		String warningcode = assignContainerForm.getWarningCode();
		log.log(Log.INFO, "--warningcode:-------->>", warningcode);
		Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();	
		ContainerAssignmentVO containerAssignmentVO = assignContainerSession.getContainerAssignmentVO();
		log.log(Log.INFO, "ContainerAssignmentVO:-------->>",
				containerAssignmentVO);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		int index = 0;
		for (ContainerVO vo : selectedContainerVOs) {
			if (index == currentindex) {
				ContainerVO dummyContainerVO = new ContainerVO(); 
				try {
					BeanHelper.copyProperties(dummyContainerVO, vo);
				} catch (SystemException exception) {
					log.log(Log.INFO,"----!!-BeanHelper.copyProperties----FAILED !!!");
					// No need to do anything
				}
				vo.setAcceptanceFlag(containerAssignmentVO.getAcceptanceFlag());
				if(CON_REASSIGN_WARN_FLIGHT.equals(warningcode)) {
					vo.setReassignFlag(true);
					vo.setOperationFlag(null);
					vo.setCarrierId(containerAssignmentVO.getCarrierId());
					vo.setCarrierCode(containerAssignmentVO.getCarrierCode());				
					vo.setFlightNumber(containerAssignmentVO.getFlightNumber());
					vo.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
					vo.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
					vo.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
					vo.setFlightDate(containerAssignmentVO.getFlightDate());
                    //Added by Deepu for CR QF1551 starts
				} else if (ULD_NOT_RELEASED_FROM_INBOUND_FLIGHT.equals(warningcode)|| (CON_ASSIGNEDTO_DIFFFLT.equals(warningcode))){ 
					Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
					ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
					if (containerAssignmentVO != null) {
						containerDetailsVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
						containerDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
						containerDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
						containerDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
						containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
						containerDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
						containerDetailsVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
						containerDetailsVO.setFlightDate(containerAssignmentVO.getFlightDate());
						containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
						containerDetailsVO.setPou(containerAssignmentVO.getPou());
						containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
					}
				
					containerDetailsVOs.add(containerDetailsVO);
					MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
					try {
						mailTrackingDefaultsDelegate.autoAcquitContainers(containerDetailsVOs);
					} catch (BusinessDelegateException businessDelegateException){
						handleDelegateException(businessDelegateException);
					}
					
				}
				//	Added by Deepu for CR QF1551 Ends
//				added for bug 101579 on 03Nov10 starts
				 else if (ULD_ALREADY_ASSIGNED_TO_CARRIER.equals(warningcode)){
					 
						Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
						ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
						Collection<ContainerDetailsVO> newContainerDetailsVOs = null;
						Collection<MailbagVO> selMailbagVOs = new ArrayList<MailbagVO>();
						int totalBagCount = 0;
						double totalWeight = 0.0;
						ApplicationSessionImpl applicationSession = getApplicationSession();
						LogonAttributes logonAttributes = applicationSession.getLogonVO();
						
						LocalDate ldate = new LocalDate(logonAttributes
								.getAirportCode(), Location.ARP, true);
						
						if (containerAssignmentVO != null) {
							containerDetailsVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
							containerDetailsVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
							containerDetailsVO.setCarrierId(containerAssignmentVO.getCarrierId());
							containerDetailsVO.setContainerType(containerAssignmentVO.getContainerType());
							containerDetailsVO.setFlightNumber(containerAssignmentVO.getFlightNumber());
							containerDetailsVO.setSegmentSerialNumber(containerAssignmentVO.getSegmentSerialNumber());
							containerDetailsVO.setFlightSequenceNumber(containerAssignmentVO.getFlightSequenceNumber());
							containerDetailsVO.setContainerNumber(containerAssignmentVO.getContainerNumber());
							containerDetailsVO.setFlightDate(containerAssignmentVO.getFlightDate());
							containerDetailsVO.setLegSerialNumber(containerAssignmentVO.getLegSerialNumber());
							containerDetailsVO.setPol(containerAssignmentVO.getAirportCode());
						}
					
						containerDetailsVOs.add(containerDetailsVO);
											
						
						try {
							newContainerDetailsVOs = new MailTrackingDefaultsDelegate()
									.findMailbagsInContainer(containerDetailsVOs);
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						for (ContainerDetailsVO newConVO : newContainerDetailsVOs) {
							if (newConVO.getMailDetails() != null) {
								selMailbagVOs = (newConVO.getMailDetails());
							}
							totalBagCount = totalBagCount+newConVO.getTotalBags();
							totalWeight = totalWeight+newConVO.getTotalWeight().getRoundedSystemValue();
						}
						log
								.log(
										Log.FINE,
										"selMailbagVOs in RM session for manifest=>>==>",
										selMailbagVOs);
					ContainerVO reassignVO = new ContainerVO();
					reassignVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
					reassignVO.setType(MailConstantsVO.BULK_TYPE);
					reassignVO.setCarrierId(containerAssignmentVO.getCarrierId());
					reassignVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
					reassignVO.setOwnAirlineId(logonAttributes
							.getOwnAirlineIdentifier());
					reassignVO.setPaBuiltFlag(MailConstantsVO.FLAG_NO);
					reassignVO.setCompanyCode(containerAssignmentVO.getCompanyCode());
					reassignVO.setRemarks(null);
					reassignVO.setFlightDate(null);
					reassignVO.setFlightNumber(String
							.valueOf(MailConstantsVO.DESTN_FLT));
					reassignVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
					reassignVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
					reassignVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
					reassignVO.setCarrierCode(containerAssignmentVO.getCarrierCode());
					reassignVO.setPou(null);
					reassignVO.setReassignFlag(true);
					reassignVO.setContainerNumber(constructBulkULDNumber(containerAssignmentVO
							.getCarrierCode(),containerAssignmentVO.getDestination()));
					reassignVO.setOffloadFlag(MailConstantsVO.FLAG_NO);
					reassignVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
					reassignVO.setTransferFlag(MailConstantsVO.FLAG_NO);
					reassignVO.setAssignedUser(logonAttributes.getUserId());
					reassignVO.setBags(totalBagCount);
					reassignVO.setWeight(new Measure(UnitConstants.MAIL_WGT,totalWeight));//added by A-7550
					//reassignVO.setWeight(totalWeight);
					reassignVO.setFinalDestination(containerAssignmentVO.getDestination());
					reassignVO.setAssignedPort(containerAssignmentVO.getAirportCode());			
					reassignVO.setAssignedDate(ldate);
					reassignVO.setTransactionCode(MailConstantsVO.MAIL_STATUS_ASSIGNED);

					log.log(Log.FINE, "reassignVO  for manifest===>",
							reassignVO);
					try {
						containerDetailsVOs = new MailTrackingDefaultsDelegate()
								.reassignMailbags(selMailbagVOs, reassignVO);

					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				 
				}
				//added for bug 101579 on 03Nov10 ends
				else {
					vo.setReassignFlag(true);
					vo.setOperationFlag(null);
					vo.setCarrierCode(containerAssignmentVO.getCarrierCode());
					vo.setCarrierId(containerAssignmentVO.getCarrierId());
					vo.setFlightNumber("-1");
					vo.setFlightSequenceNumber(-1);
					vo.setLegSerialNumber(-1);
					vo.setSegmentSerialNumber(-1);
					vo.setPou(assignContainerForm.getPou());
				}
				Collection<MailbagVO> mailbagVOs = reassignMailSession.getMailbagVOs();
				if (mailbagVOs != null && mailbagVOs.size() > 0) {
					for(MailbagVO mailbagVO:mailbagVOs){
						if(mailbagVO.getCarrierId() == vo.getCarrierId()
								&& mailbagVO.getFlightNumber()!=null && mailbagVO.getFlightNumber().equals(vo.getFlightNumber())
								&& mailbagVO.getFlightSequenceNumber() == vo.getFlightSequenceNumber()
								&& mailbagVO.getSegmentSerialNumber() == vo.getSegmentSerialNumber()
								&& mailbagVO.getContainerNumber()!=null && mailbagVO.getContainerNumber().equals(vo.getContainerNumber())){	

							mailbagVO.setCarrierId(dummyContainerVO.getCarrierId());
							mailbagVO.setCarrierCode(dummyContainerVO.getCarrierCode());				
							mailbagVO.setFlightNumber(dummyContainerVO.getFlightNumber());
							mailbagVO.setFlightSequenceNumber(dummyContainerVO.getFlightSequenceNumber());
							mailbagVO.setLegSerialNumber(dummyContainerVO.getLegSerialNumber());
							mailbagVO.setSegmentSerialNumber(dummyContainerVO.getSegmentSerialNumber());
							mailbagVO.setFlightDate(dummyContainerVO.getFlightDate());
							mailbagVO.setPou(dummyContainerVO.getFinalDestination());
						}
					}
				}
				Collection<DespatchDetailsVO> despatchDetailsVOs = reassignMailSession.getDespatchDetailsVOs();
//				if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
//					for(DespatchDetailsVO despatchDetailsVO:despatchDetailsVOs){
//						if(despatchDetailsVO.getCarrierId() == vo.getCarrierId()
//							&& despatchDetailsVO.getFlightNumber().equals(vo.getFlightNumber())
//							&& despatchDetailsVO.getFlightSequenceNumber() == vo.getFlightSequenceNumber()
//							&& despatchDetailsVO.getSegmentSerialNumber() == vo.getSegmentSerialNumber()
//							&& despatchDetailsVO.getContainerNumber().equals(vo.getContainerNumber())){
//
//							despatchDetailsVO.setCarrierId(dummyContainerVO.getCarrierId());
//							despatchDetailsVO.setCarrierCode(dummyContainerVO.getCarrierCode());				
//							despatchDetailsVO.setFlightNumber(dummyContainerVO.getFlightNumber());
//							despatchDetailsVO.setFlightSequenceNumber(dummyContainerVO.getFlightSequenceNumber());
//							despatchDetailsVO.setLegSerialNumber(dummyContainerVO.getLegSerialNumber());
//							despatchDetailsVO.setSegmentSerialNumber(dummyContainerVO.getSegmentSerialNumber());
//							despatchDetailsVO.setFlightDate(dummyContainerVO.getFlightDate());
//							despatchDetailsVO.setDestination(dummyContainerVO.getFinalDestination());
//						}
//					}
//				}
				reassignMailSession.setMailbagVOs(mailbagVOs);
				reassignMailSession.setDespatchDetailsVOs(despatchDetailsVOs);
				break;
			}			
			index++;
		}
						
		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
		log.log(Log.INFO, "SelectedContainerVOs after:-------->>",
				assignContainerSession.getSelectedContainerVOs());
		if (CONST_ADDNEW.equals(currentaction)) {
			invocationContext.target = TARGET_SUCCESS_ADDNEW;		 	
		}
		else {
			invocationContext.target = TARGET_SUCCESS_OK;		
		}
		       	
    	log.exiting("UpdateContainerCommand","execute");
    	
    }   
    
    private String constructBulkULDNumber(String carrierCode,String destination) {

		// This case comes during aquital of ULDs
		// A bulk container is created with a name corresponding to each carrier
		// for ex: BULK-ACQ-QF-SIN
		return MailConstantsVO.CONST_BULK_ACQ_ARP.concat(
				MailConstantsVO.SEPARATOR).concat(carrierCode).
					concat(MailConstantsVO.SEPARATOR).concat(destination);
    }   
            
}
