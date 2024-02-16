/*
 * ClearArriveMailCommand.java Created on Jun 30 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ClearArriveMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
//   private static final String CONST_FLIGHT = "FLIGHT";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearArriveMailCommand","execute");
   	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
 	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
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
			    newMailbagVO.setMailbagId(newMailbagVO.getMailbagId());
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
    	

  	    boolean isExist = false;
  	    int flag = 0;
  	    int errorFlag = 0;
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalSession.getContainerDetailsVOs();
    	Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
    	ContainerDetailsVO contDtlsVO = new ContainerDetailsVO();
    	log.log(Log.FINE, "ContainerNumber() - - - -", containerDetailsVO.getContainerNumber());
    	String acceptedFlag = null;
		try{
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			acceptedFlag = containerDtlsVO.getAcceptedFlag();
    			if(isExist){
				    isExist = false;
				    flag = 1;
				    log.log(Log.FINE, "flag = 1");
				    BeanHelper.copyProperties(contDtlsVO,containerDtlsVO);
			    }
				if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())
						&& containerDtlsVO.getPol().equals(containerDetailsVO.getPol())){
					
					if(!"I".equals(containerDtlsVO.getOperationFlag())){
						newContainerDetailsVOs.add(containerDtlsVO);
						errorFlag = 1;
						log.log(Log.FINE, "errorFlag = 1");
					}else{
						isExist = true;
					}
				}else{
					newContainerDetailsVOs.add(containerDtlsVO);
					log.log(Log.FINE, "else..1");
				}
			 }
		  }
    	}catch (SystemException e) {
    		e.getError();
	    }
    	if(errorFlag == 1){
    		if(acceptedFlag != null || ((containerDetailsVO.getMailDetails()==null || containerDetailsVO.getMailDetails().isEmpty())
    				&& "Y".equals(mailArrivalForm.getDeleteFlag()))){
    		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.cannotdelete"));
    		}
    	}else{
			if(flag == 1){
				mailArrivalForm.setContainerNo(contDtlsVO.getContainerNumber());
				mailArrivalForm.setPol(contDtlsVO.getPol());
				mailArrivalForm.setDisableFlag("Y");
			}else{
				mailArrivalForm.setContainerNo("");
				mailArrivalForm.setDisableFlag("");
				contDtlsVO.setOperationFlag("I");
				contDtlsVO.setContainerNumber("");
//				 To set POL
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
			}
			mailArrivalSession.setContainerDetailsVO(contDtlsVO);
    	}
    	mailArrivalSession.setContainerDetailsVOs(newContainerDetailsVOs); 
  	    
        
    	invocationContext.target = TARGET;
       	
    	log.exiting("ClearArriveMailCommand","execute");
    	
    }
       
}
