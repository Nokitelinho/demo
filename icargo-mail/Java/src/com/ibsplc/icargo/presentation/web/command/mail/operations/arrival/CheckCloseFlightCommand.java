/*
 * CheckCloseFlightCommand.java Created on Jun 30 2016
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

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
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


/**
 * @author A-5991
 *
 */
public class CheckCloseFlightCommand extends BaseCommand {
	
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
    	
    	log.entering("CheckCloseFlightCommand","execute");
    	  
    	MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	mailArrivalForm.setScreenStatusFlag(
    			ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
    	
    	boolean isFlightClosed = false;
    	//Added by A-4809 for CR ICMN-2345 --->Starts
		String [] oprFlag= mailArrivalForm.getOperationalFlag();
    	String[] conDocNo=mailArrivalForm.getCsgDocNum();
    	ArrayList<DSNVO> dSNVOs = null;
    	DSNVO dsnvo = new DSNVO();
    	HashMap<String, Collection<DSNVO>> dsnvoMap = mailArrivalSession.getConsignmentMap();
    	MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
    	if(dsnvoMap==null)
    		{
    		dsnvoMap = new HashMap<String,Collection<DSNVO>>();
    		}
   	 
    	Collection<ContainerDetailsVO> containerDetailsVOss =  mailArrivalVO.getContainerDetails();
    	for(ContainerDetailsVO containerDetailsVO:containerDetailsVOss){
			if(dSNVOs==null)
				{
				dSNVOs = new ArrayList<DSNVO>();
				}
			dSNVOs.addAll(containerDetailsVO.getDsnVOs());       
    	}
    	if(oprFlag != null && oprFlag.length > 0){
		for(int i=0; i < oprFlag.length; i++){
			Collection<DSNVO> despatchVOs = null;
			if(ContainerDetailsVO.OPERATION_FLAG_UPDATE.equals(oprFlag[i]) && conDocNo[i] != null && conDocNo[i].length() > 0){
				dSNVOs.get(i).setCsgDocNum(conDocNo[i]);
				dsnvo = dSNVOs.get(i);
				if(dsnvoMap.containsKey(conDocNo[i]))
					{
					despatchVOs = dsnvoMap.get(conDocNo[i]);
					}
				else
					despatchVOs = new ArrayList<DSNVO>();
				despatchVOs.add(dsnvo);
				dsnvoMap.put(conDocNo[i],despatchVOs);
			}
		}
    	}
		log.log(Log.FINE, "-----:))))--setting to session----->");
		mailArrivalSession.setConsignmentMap(dsnvoMap);
		//Added by A-4809 for CR ICMN-2345 --->Ends  	 
    	
    	try {
    		if(mailTrackingDefaultsDelegate.validateInboundFlight(
    				mailArrivalSession.getOperationalFlightVO()) != null) {
    			isFlightClosed = mailTrackingDefaultsDelegate.
    				isFlightClosedForInboundOperations(mailArrivalSession.getOperationalFlightVO());
    		}
			
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			mailArrivalForm.setCheckFlight("N");
			return;
		}
		log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
		if (isFlightClosed) {
			
			Object[] obj = {mailArrivalForm.getFlightCarrierCode(),
					mailArrivalForm.getFlightNumber(),
					mailArrivalForm.getArrivalDate().toString().substring(0,11)};
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.checkflightclosed",obj));
			mailArrivalForm.setCheckFlight("Y");
			invocationContext.target = TARGET;
			return;				
		} 
		/**
		 * ADDED FOR CR SA 410 STARTS
		 */
		FlightValidationVO flightValidationVO = mailArrivalSession.getFlightValidationVO();
		if(flightValidationVO.isTBADueRouteChange()){
			Object[] obj = {mailArrivalForm.getFlightCarrierCode(),
					mailArrivalForm.getFlightNumber(),
					mailArrivalForm.getArrivalDate().toString().substring(0,11)};
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.flighttobeactioned",obj));
			invocationContext.target = TARGET;
			return;
		}
		/**
		 * ADDED FOR CR SA 410 ENDS
		 */
		
		mailArrivalForm.setCheckFlight("N");
		invocationContext.target = TARGET;
       	
    	log.exiting("CheckCloseFlightCommand","execute");
    	
    }
       
}
