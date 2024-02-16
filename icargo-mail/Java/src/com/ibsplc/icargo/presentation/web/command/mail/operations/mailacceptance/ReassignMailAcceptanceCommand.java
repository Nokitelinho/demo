/*
 * ReassignMailAcceptanceCommand.java Created on Jul 1 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ReassignMailAcceptanceCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	

   private static final String TARGET_FAILURE = "reassign_failure";

   private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.reassignContainer";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ReassignMailAcceptanceCommand","execute");
    	 
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	MailAcceptanceVO mailAcceptanceVO= mailAcceptanceSession.getMailAcceptanceVO();
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailAcceptanceVO.getContainerDetails();
    	mailAcceptanceForm.setReassignScreenFlag("N");
    	String selCont = mailAcceptanceForm.getSelCont();
    	String[] primaryKey=selCont.split(",");
    	Collection<ContainerVO> reasgnContainerVOs = 
				new ArrayList<ContainerVO>();
    	
    	int cnt=0;
	   	int count = 1;
	   	int primaryKeyLen = primaryKey.length;
	   	
        int errorArrived = 0;
        String contArrived = "";
		String contAlreadyManifest = "";
        int errorAccepted=0;
        int errorInReassign=0;
       
    	if (containerDetailsVOs != null && containerDetailsVOs.size() != 0) {
    		for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
    			if(!"I".equals(containerDetailsVO.getContainerOperationFlag())){
    				String primaryKeyFromVO = containerDetailsVO.getCompanyCode()
    				+String.valueOf(count);
    				if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
    						equalsIgnoreCase(primaryKey[cnt].trim())) {
    					if(MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getArrivedStatus())){
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
    					//reassignVO.setWeight(containerDetailsVO.getTotalWeight());
    					reassignVO.setWeight(new Measure(UnitConstants.MAIL_WGT,containerDetailsVO.getTotalWeight().getRoundedSystemValue()));//added by A-7550
    					reassignVO.setFinalDestination(containerDetailsVO.getDestination());
    					reassignVO.setAssignedPort(containerDetailsVO.getPol());
    					reassignVO.setAssignedDate(containerDetailsVO.getAssignmentDate()); 
    					reassignVO.setShipperBuiltCode(containerDetailsVO.getPaCode());
    					reassignVO.setContainerJnyID(containerDetailsVO.getContainerJnyId());
    					reassignVO.setContentId(containerDetailsVO.getContentId());//Added as part of ICRD-239331
    					reasgnContainerVOs.add(reassignVO);
    					cnt++;
    				}

    				count++;
    			}else{
    				errorAccepted=1;
    				break;	    				
    			}
    		}
    		if(errorArrived == 1){
      	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.alreadyarrived",new Object[]{contArrived}));
      	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
      	    	mailAcceptanceForm.setCloseflight("Y");
    			invocationContext.target = "reassignmailacceptance_success";
    			return;		
           }
    		if(errorInReassign == 1){
      	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.cannnotreassigntransitcontainer",new Object[]{contAlreadyManifest}));
      	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
      	    	mailAcceptanceForm.setCloseflight("Y");
    			invocationContext.target = "reassignmailacceptance_success";
    			return;		
           }
    		if(errorAccepted == 1){
    			
		    	ErrorVO errorVO=new ErrorVO("mailtracking.defaults.mailacceptance.contnotsaved");
		    	//errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
      	    	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
      	    	invocationContext.addError(errorVO);
    			invocationContext.target = "reassignmailacceptance_success";        			
    			return;	
           }
    	}
    	 // VALIDATION FOR CONTAINERS 
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
    	    	return;
         }
 	    
 	   	if(errorOfl == 1){
 	   		invocationContext.addError(new ErrorVO(
 	   				"mailtracking.defaults.msg.err.containersHoldOffloadedMails",new Object[]{contOfl}));
 	   		invocationContext.target = TARGET_FAILURE;
 			return;

 	   	}
 	   	
 	   	if(errorArr == 1){
 	   		invocationContext.addError(new ErrorVO(
 	   				"mailtracking.defaults.msg.err.reassigncontainersarrived",new Object[]{contArr}));
 	   		invocationContext.target = TARGET_FAILURE;
 			return;
 	   	}
 	   	log.log(Log.ALL, "&&&&&&&&reasgnContainerVOs&&&&&&&",
				reasgnContainerVOs);
		reassignContainerSession.setSelectedContainerVOs(reasgnContainerVOs);
 	    reassignContainerSession.setContainerVO(new ContainerVO());
 	    mailAcceptanceForm.setReassignScreenFlag("Y");
		invocationContext.target = "reassignmailacceptance_success";
    	log.exiting("ReassignMailAcceptanceCommand","execute");
    	
    }
       
}
