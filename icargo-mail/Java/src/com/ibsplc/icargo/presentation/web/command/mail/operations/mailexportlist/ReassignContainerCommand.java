/*
 * ReassignContainerCommand.java Created on Jul 1 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author Reno
 * Command class : ReassignContainerCommand
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         Jul 1 2016  	 	-5991           Coding
 */
public class ReassignContainerCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS = "reassign_success";
	private static final String TARGET_FAILURE = "reassign_failure";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";	
	private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.reassignContainer";

	private static final String CONST_REASSIGN = "showReassignScreen";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("ReassignContainerCommand","execute");

		MailExportListForm mailExportListForm =  (MailExportListForm)invocationContext.screenModel;
		MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		ReassignContainerSession reassignContainerSession = 
			(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailAcceptanceVO mailAcceptanceVO= mailExportListSession.getMailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetailsVOs =  mailExportListSession.getContainerDetailsVOs();   
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();    
		Collection<ContainerVO> reasgnContainerVOs = new ArrayList<ContainerVO>();

		Collection<ErrorVO> errors = null;
		
		String selectedContainer=mailExportListForm.getSelCont();
		String[] primaryKey = selectedContainer.split(",");
		
		int cnt=0;
		int count = 0;
		int primaryKeyLen = primaryKey.length;
		int errorArrived = 0;
        int errorInReassign=0;
		String contArrived = "";
		String contAlreadyManifest = "";
		
		//		Validating Flight Closure
    	boolean isFlightClosed = false;
    	OperationalFlightVO operationalFlightVO = mailExportListSession.getOperationalFlightVO();
    	if(operationalFlightVO!=null){
	    	try {	    		
	    		isFlightClosed = 
	    				mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);   		        		
				
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			log
					.log(
							Log.FINE,
							"MailbagEnquiry-->OffloadMailCommand--->isFlightClosed----->",
							isFlightClosed);
			if(isFlightClosed){			
				Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
						mailAcceptanceVO.getFlightNumber(),
						mailAcceptanceVO.getFlightDate().toString().substring(0,11)};				
				invocationContext.addError(new ErrorVO("mailtracking.defaults.exportlist.msg.err.flightclosed",obj));
				mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	 	   		invocationContext.target =TARGET_SUCCESS; 
	 	   		return;
			}
    	}
		
		if (containerDetailsVOs != null && containerDetailsVOs.size() != 0) {
			for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
				String primaryKeyFromVO = containerDetailsVO.getCompanyCode()
				+String.valueOf(count);
				if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
						equalsIgnoreCase(primaryKey[cnt].trim())) {
					if("Y".equals(containerDetailsVO.getArrivedStatus())){
						errorArrived = 1;
						if("".equals(contArrived)){
							contArrived = containerDetailsVO.getContainerNumber();
						}else{
							contArrived = new StringBuilder(contArrived)
							.append(",")
							.append(containerDetailsVO.getContainerNumber())
							.toString();	
						}
					}
					if(MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType()) && 
							MailConstantsVO.FLAG_NO.equals(containerDetailsVO.getTransitFlag())) {
						errorInReassign = 1;
						if("".equals(contAlreadyManifest)){
							contAlreadyManifest = containerDetailsVO.getContainerNumber();
						}else{
							contAlreadyManifest = new StringBuilder(contAlreadyManifest)
							.append(",")
							.append(containerDetailsVO.getContainerNumber())
							.toString();	
						}
					}
					ContainerVO reassignVO = new ContainerVO();
					reassignVO.setAcceptanceFlag(containerDetailsVO.getAcceptedFlag());
					reassignVO.setType(containerDetailsVO.getContainerType());
					reassignVO.setCarrierId(mailAcceptanceVO.getCarrierId());
					reassignVO.setOwnAirlineCode(mailAcceptanceVO.getOwnAirlineCode());
					reassignVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
					reassignVO.setOwnAirlineId(mailAcceptanceVO.getOwnAirlineId());
					reassignVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
					reassignVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
					reassignVO.setRemarks(containerDetailsVO.getRemarks());
					reassignVO.setFlightDate(mailAcceptanceVO.getFlightDate());
					reassignVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
					reassignVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
					reassignVO.setLegSerialNumber(mailAcceptanceVO.getLegSerialNumber());
					reassignVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
					reassignVO.setPou(containerDetailsVO.getPou());
					reassignVO.setReassignFlag(true);
					reassignVO.setContainerNumber(containerDetailsVO.getContainerNumber());
					reassignVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
					reassignVO.setOffloadFlag(containerDetailsVO.getOffloadFlag());
					reassignVO.setArrivedStatus(containerDetailsVO.getArrivedStatus());
					reassignVO.setTransferFlag(containerDetailsVO.getTransferFlag());
					reassignVO.setAssignedUser(containerDetailsVO.getAssignedUser());
					reassignVO.setBags(containerDetailsVO.getTotalBags());
					reassignVO.setWeight(containerDetailsVO.getTotalWeight());
					reassignVO.setFinalDestination(containerDetailsVO.getDestination());
					reassignVO.setAssignedPort(containerDetailsVO.getPol());
					reassignVO.setAssignedDate(containerDetailsVO.getAssignmentDate()); 
					reasgnContainerVOs.add(reassignVO);
					cnt++;
				}

				count++;
			}
			if(errorArrived == 1){
				log.log(Log.INFO, ">>>>>>>>>>>>>>Container Already Arrived At Some Port<<<<<<<<<<<<");
				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailexportlist.contalreadyarrived",new Object[]{contArrived}));
				mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
				log.exiting("ReassignContainerCommand","execute");
				return;		
			}
    		if(errorInReassign == 1){
      	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailexportlist.cannnotreassigntransitcontainer",new Object[]{contAlreadyManifest}));
      	    	mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);      	    	
    			invocationContext.target = TARGET_FAILURE;
				log.exiting("ReassignContainerCommand","execute");
    			return;		
           }
		}

		// VALIDATION FOR CONTAINERS HOLDING OFFLOADED MAILS
		int errorOfl = 0;
		String contOfl = "";
		int errorArr = 0;
		String contArr = "";
		int errorPort = 0;
		String contPort = "";

		for (ContainerVO selectedvo : reasgnContainerVOs) {
			if (selectedvo.getContainerNumber() != null ) {

				if(!logonAttributes.getAirportCode().equals(selectedvo.getAssignedPort())){
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

				if (selectedvo.getContainerNumber().startsWith("OFL")) {
					errorOfl = 1;
					if("".equals(contOfl)){
						contOfl = selectedvo.getContainerNumber();
					}else{
						contOfl = new StringBuilder(contOfl)
						.append(",")
						.append(selectedvo.getContainerNumber())
						.toString();	
					}
				}
				if("Y".equals(selectedvo.getArrivedStatus())){
					errorArr = 1;
					if("".equals(contArr)){
						contArr = selectedvo.getContainerNumber();
					}else{
						contArr = new StringBuilder(contArr)
						.append(",")
						.append(selectedvo.getContainerNumber())
						.toString();	
					}
				}
			}
		}

		if(errorPort == 1){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
			invocationContext.target = TARGET_FAILURE;
			log.exiting("ReassignContainerCommand","execute");
			return;
		}

		if(errorOfl == 1){
			invocationContext.addError(new ErrorVO(
					"mailtracking.defaults.msg.err.containersHoldOffloadedMails",new Object[]{contOfl}));
			invocationContext.target = TARGET_FAILURE;
			log.exiting("ReassignContainerCommand","execute");
			return;

		}

		if(errorArr == 1){
			invocationContext.addError(new ErrorVO(
					"mailtracking.defaults.msg.err.reassigncontainersarrived",new Object[]{contArr}));
			invocationContext.target = TARGET_FAILURE;
			log.exiting("ReassignContainerCommand","execute");
			return;
		}

		reassignContainerSession.setSelectedContainerVOs(reasgnContainerVOs);
		reassignContainerSession.setContainerVO(new ContainerVO());
		mailExportListForm.setStatus(CONST_REASSIGN);
		mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = TARGET_SUCCESS;

		log.exiting("ReassignContainerCommand","execute");

	}

}
