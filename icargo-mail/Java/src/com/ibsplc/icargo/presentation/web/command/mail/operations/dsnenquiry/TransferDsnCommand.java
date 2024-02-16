/*
 * TransferDsnCommand.java Created on July 01, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class TransferDsnCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "success";
   private static final String TARGET_FAILURE = "failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";	
   
   private static final String CONST_TRANSFERDSN = "showTransferDsnPopup";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("TransferDsnCommand","execute");
    	  
    	DsnEnquiryForm dsnEnquiryForm = 
    		(DsnEnquiryForm)invocationContext.screenModel;
     	DsnEnquirySession dsnEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		Collection<DespatchDetailsVO> despatchDetailsVOs = dsnEnquirySession.getDespatchDetailsVOs();
		ArrayList<DespatchDetailsVO> selectedvos = new ArrayList<DespatchDetailsVO>();
	    String operationType = 
	    	dsnEnquirySession.getDsnEnquiryFilterVO().getOperationType();
	    boolean isOutbound = operationType.
        	equals(MailConstantsVO.OPERATION_OUTBOUND)? true : false ;
	    String selectedPort = null;
	    if(dsnEnquirySession.getDsnEnquiryFilterVO().getAirportCode()!=null && dsnEnquirySession.getDsnEnquiryFilterVO().getAirportCode().trim().length()>0)
	    {
	   selectedPort = 
	    	dsnEnquirySession.getDsnEnquiryFilterVO().getAirportCode();
	    }
	    else
	    {
	    	selectedPort=logonAttributes.getAirportCode();
	    }  
		
		String[] selectedRows = dsnEnquiryForm.getSubCheck();    	
    	int row = 0;
    	for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
    		if (row == Integer.parseInt(selectedRows[0])) {
    			selectedvos.add(despatchDetailsVO);
			}
    		row++;
    	}
    	log.log(Log.FINE, "selectedvos --------->>", selectedvos);
		//    	 VALIDATING WHETHER PLT FLAGS ARE ENABLED
    	boolean isPltEnabled = false;
        for (DespatchDetailsVO selectedvo : selectedvos) {
     		if ("true".equals(selectedvo.getPltEnabledFlag())) {
     			isPltEnabled = true;
     			break;
     		}
     		if(selectedvo.isDelivered()){
     			ErrorVO errorVO = new ErrorVO(
	    		"mailtracking.defaults.err.alreadydelivered");	
	    		invocationContext.addError(errorVO);			
	    		invocationContext.target = TARGET_FAILURE;
	    		return;
     		}
	    	/*
	    	 * Mail Captured through Capture Consignemnt,But not Accedpted to the system
	    	 * Done for ANZ CR AirNZ1039
	    	 */
			if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selectedvo.getCapNotAcceptedStatus())) {
	    		ErrorVO errorVO = new ErrorVO(
	    		"mailtracking.defaults.err.capturedbutnotaccepted");	
	    		invocationContext.addError(errorVO);			
	    		invocationContext.target = TARGET_FAILURE;
	    		return;
			}
 	    }
     	
     	if(isPltEnabled){
			invocationContext.addError(
 	   	   			new ErrorVO("mailtracking.defaults.dsnenquiry.msg.err.cannottransferpltenabled"));
			invocationContext.target = TARGET_FAILURE;
			return;
    	}
     	
     	
//      VALIDATION FOR already transferred
    	int  errorPort = 0;
        String contPort = "";
 	   	for (DespatchDetailsVO selectedvo : selectedvos) {
 	   		if (selectedvo.getContainerNumber() != null ) {
 	   			if("Y".equals(selectedvo.getTransferFlag())){
        				errorPort = 1;
        				if("".equals(contPort)){
        					contPort = selectedvo.getDsn();
 	       			}else{
 	       				contPort = new StringBuilder(contPort)
    					                  .append(",")
    					                  .append(selectedvo.getDsn())
    					                  .toString();	
 	       			}
        		}
 	   		}
 	   	} 
 	     if(errorPort == 1){
 	   	   	invocationContext.addError(
 	   	   			new ErrorVO("mailtracking.defaults.msg.err.alreadytransferred",
 	   	   			new Object[]{contPort}));
 	   	   	invocationContext.target = TARGET_FAILURE;
 	   	   	return;
 	     }
        
//		  VALIDATE flight or destn assigned
    	errorPort = 0;
	    contPort = "";
		   	for (DespatchDetailsVO selectedvo : selectedvos) {
		   		if (selectedvo.getContainerNumber() != null ) {
		   			//in case of despatch filght details are 0 instead of -1
		   			if(selectedvo.getFlightSequenceNumber() == 0){
	       				errorPort = 1;
	       				if("".equals(contPort)){
	       					contPort = selectedvo.getDsn();
		       			}else{
		       				contPort = new StringBuilder(contPort)
	   					                  .append(",")
	   					                  .append(selectedvo.getDsn())
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

		    //  VALIDATION FOR POU and current airport
	        errorPort = 0;
	        contPort = "";
	 	   	for (DespatchDetailsVO selectedvo : selectedvos) {
	 	   		if (selectedvo.getContainerNumber() != null ) {
	 	   			if(!(logonAttributes.getAirportCode().equals(selectedvo.getPou())
       	    			 && selectedPort.equals(selectedvo.getPou()))){
	        				errorPort = 1;
	        				if("".equals(contPort)){
	        					contPort = selectedvo.getDsn();
	 	       			}else{
	 	       				contPort = new StringBuilder(contPort)
	    					                  .append(",")
	    					                  .append(selectedvo.getDsn())
	    					                  .toString();	
	 	       			}
	        			}
	 	   		}
	 	   	}
	 	   	
	 	    if(errorPort == 1){
	    	    	invocationContext.addError(
	    	    			new ErrorVO("mailtracking.defaults.msg.err.differentpou",
	    	    					new Object[]{contPort}));
	    	    	invocationContext.target = TARGET_FAILURE;
	    	    	return;
	         } 
		    
//		  VALIDATION FOR Already accepted
 	        errorPort = 0;
 	        contPort = "";
 		   	for (DespatchDetailsVO selectedvo : selectedvos) {
 		   		if (selectedvo.getContainerNumber() != null ) {
 		   			if(selectedPort.equals(selectedvo.getAirportCode()) &&
 		   					isOutbound && 
 		   					selectedvo.getAcceptedBags() == selectedvo.getReceivedBags()){
 	       				errorPort = 1;
 	       				if("".equals(contPort)){
 	       					contPort = selectedvo.getDsn();
 		       			}else{
 		       				contPort = new StringBuilder(contPort)
 	   					                  .append(",")
 	   					                  .append(selectedvo.getDsn())
 	   					                  .toString();	
 		       			}
 	       			}
 		   		}
 		   	}
 		   	
 		    if(errorPort == 1){
 	   	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.samescannedport",new Object[]{contPort}));
 	   	    	invocationContext.target = TARGET_FAILURE;
 	   	    	return;
 	        }

     		/*
     		 * Validating the Destination Airport of mailbag
     		 */
    		Collection<String> does=new ArrayList<String>();
			Collection<ArrayList<String>> groupedOECityArpCodes = null;
    		String airport = logonAttributes.getAirportCode();

     		for (DespatchDetailsVO selectedvo : selectedvos) {
     			if(!does.contains(selectedvo.getDestinationOfficeOfExchange())){
     				does.add(selectedvo.getDestinationOfficeOfExchange());
     			}
     		}
 			if(does != null && does.size()>0){
 				try {
     				/*
     			     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
     			     * the inner collection contains the values in the order :
     			     * 0.OFFICE OF EXCHANGE
     			     * 1.CITY NEAR TO OE
     			     * 2.NEAREST AIRPORT TO CITY
     			     */
     				groupedOECityArpCodes = 
     					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(
     							logonAttributes.getCompanyCode(), does);
     			}catch (BusinessDelegateException businessDelegateException) {
 					Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
 					log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
 				}				
 				int errorFlag = 0;				
 				if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
 					for(String doe : does){
 						for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
 							if(cityAndArpForOE.size() == 3 && 
 									doe.equals(cityAndArpForOE.get(0)) && 
 									airport.equals(cityAndArpForOE.get(2))){
 								errorFlag = 1;
 								break;
 							}		
 						}
 						if(errorFlag == 1) {
 							break;
 						}
 					}
 				}							
 				if(errorFlag == 1){
 					log.log(Log.INFO,"<<----DOE of Mailbag(s) is Same as that of the current Airport --->>");
 					invocationContext.addError(new ErrorVO("mailtracking.defaults.dsnenquiry.msg.err.cannotTransferDespatchRchdDestn"));
 					invocationContext.target = TARGET_FAILURE;
 					return;
 				}
 			}

    	dsnEnquiryForm.setStatus(CONST_TRANSFERDSN);    	
		dsnEnquirySession.setSelectedDespatchDetailsVOs(selectedvos);
        invocationContext.target = TARGET_SUCCESS;
        log.exiting("TransferDsnCommand","execute");
    	
    }  
   
}
