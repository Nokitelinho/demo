/*
 * ValidateCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;
import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_TRANSFERRED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ValidateCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "validate_success";
   private static final String TARGET_FAILURE = "validate_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
   
   private static final String CONST_REASSIGN = "ShowReassignMailPopup";
   private static final String CONST_TRANSFER = "ShowTransferMailPopup";
   private static final String CONST_RETURN = "ShowReturnMailPopup";
   private static final String CONST_RETURN_MAIL = "RETURNMAIL";
   private static final String CONST_REASSIGN_MAIL = "REASSIGNMAIL";
   private static final String CONST_TRANSFER_MAIL = "TRANSFERMAIL";
   private static final String CONST_RETURN_CODE = "RTN";
   private static final String CONST_ACCEPT_FLG = "ACP";
   private static final String CONST_OFFLOAD_FLG = "OFL";
   private static final String CONST_NOTUPTIFTED_FLG = "NUP";
   private static final String CONST_ARRIVED_FLG = "ARR";
   private static final String CONST_DELIVERED_FLG = "DLV";
   private static final String CONST_INBOUND = "I";
   private static final String CONST_OUTBOUND = "O";
   private static final String MAILBAG_ISALREADY_DELIVERED = "mailtracking.defaults.mailbagenquiry.msg.mailbagisalreadydelivered";
   private static final String AUTOARRIVALFUNCTIONPOINTS="mail.operations.autoarrivalfunctionpoints";
   private static final String AUTOARRIVALENABLEDPAS="mail.operations.autoarrivalenabledPAs";
   private static final String FUNPNTS_RTN  = "RTN";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ValidateCommand","execute");
    	  
    	MailBagEnquiryForm mailBagEnquiryForm = 
    		(MailBagEnquiryForm)invocationContext.screenModel;
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
    	Collection<MailbagVO> selectedMailbagVOs = new ArrayList<MailbagVO>();
    
    	// Getting the selected MailBags
    	String[] selectedRows = mailBagEnquiryForm.getSubCheck();  
    	Page<OfficeOfExchangeVO> ooe = null;
    	int size = selectedRows.length;
    	int row = 0;
    	for (MailbagVO mailbagvo : mailbagVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				selectedMailbagVOs.add(mailbagvo);
    			}    			
			}
    		row++;
    	}
    	
    	ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		systemParameters.add(AUTOARRIVALENABLEDPAS);
	Map<String, String> systemParameterMap=null;
		try {
			systemParameterMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters);
		} catch (BusinessDelegateException businessDelegateException) {
			Collection<ErrorVO> error = handleDelegateException(businessDelegateException);
			log.log(Log.INFO,"");
		}	
		//Map<String, String> systemParameterMap1 = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameters2);
		String sysparfunctionpoints = null;
		String autoArrEnabledPAs= null;
		boolean enableAutoArrival = false;
		if (systemParameterMap != null) {
			sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
			autoArrEnabledPAs=systemParameterMap.get(AUTOARRIVALENABLEDPAS);
		}
		
		if(sysparfunctionpoints!=null &&
				sysparfunctionpoints.contains(FUNPNTS_RTN)
				&& autoArrEnabledPAs.contains(selectedMailbagVOs.iterator().next().getPaCode())
				){
			enableAutoArrival = true;
		}
    	String currentaction = mailBagEnquiryForm.getStatus();
    	
    	
    	// validation to check whether any of the selected mailbags are returned ones
    	
    	boolean isReturnedMailbag = false;
    	boolean isCapNotAcceptedMailbag = false;
    	for (MailbagVO selectedvo : selectedMailbagVOs) {
    		if (CONST_RETURN_CODE.equals(selectedvo.getLatestStatus())) {
    			isReturnedMailbag = true;
    			break;
    		}
    		if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selectedvo.getLatestStatus())) {
    			isCapNotAcceptedMailbag = true;
    			break;
    		}
    	}
    	/*
    	 * Captured Not Accepted
    	 */
    	if(isCapNotAcceptedMailbag) {
    		ErrorVO errorVO = new ErrorVO(
    		"mailtracking.defaults.err.capturedbutnotaccepted");	
    		invocationContext.addError(errorVO);			
    		invocationContext.target = TARGET_FAILURE;
    		return;
    	}
    	if (isReturnedMailbag) {
    		ErrorVO errorVO = null;
    		if (CONST_RETURN_MAIL.equals(currentaction)) {
    			errorVO = new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.returnedMailbagsCannotReturn");
        	}
        	else if (CONST_REASSIGN_MAIL.equals(currentaction)) {
        		errorVO = new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.returnedMailbagsCannotReassign");
        	}  
        	else if (CONST_TRANSFER_MAIL.equals(currentaction)) {
        		errorVO = new ErrorVO(
				"mailtracking.defaults.mailbagenquiry.msg.err.notransferforreturn");
        	}  		
			invocationContext.addError(errorVO);			
			invocationContext.target = TARGET_FAILURE;
			return;
    	}
    	
    		if (CONST_RETURN_MAIL.equals(currentaction)) {
    			
    			/*
    			 * check whether the mailbag is INBOUND or OUTBOUND
    			 * if INBOUND - it must be accepted or offloaded at current airport
    			 * if OUTBOUND - it must be arrived at current airport 
    			 */
    			for (MailbagVO selectedvo : selectedMailbagVOs) {
    				Collection<MailbagHistoryVO> mailhistroryVos = null;
    				String acceptedPort = null;
                  	if (CONST_INBOUND.equals(selectedvo.getOperationalStatus())) {
                  		if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort())){
                     		if(!( CONST_ACCEPT_FLG.equals(selectedvo.getLatestStatus())
                     			 || CONST_OFFLOAD_FLG.equals(selectedvo.getLatestStatus()))) {
                     			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForInboundReturn"));			
                     			invocationContext.target = TARGET_FAILURE;
                     			return;
                     		}
                     	 }
                  	}
                  	else if (CONST_OUTBOUND.equals(selectedvo.getOperationalStatus())) {
                  		if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort())){
                    		if(!(CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus()))){
                    			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForOutboundReturn"));			
            					invocationContext.target = TARGET_FAILURE;
            					return;
                    		}
                    	 }
                  	}
                  	if(CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())){
            			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.deliveredbagscannotreturn"));			
    					invocationContext.target = TARGET_FAILURE;
    					return;
            		}
//                  	
//                  	if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())) {
//               	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.transferred"));			
//            	 		invocationContext.target = TARGET_FAILURE;
//        	 			return;
//               	    }
                  	//Validation added for ICRD-117307 starts
                  	
                  	try {
         				
                		mailhistroryVos = 
         					new MailTrackingDefaultsDelegate().findMailbagHistories(logonAttributes.getCompanyCode(),
         							selectedvo.getMailbagId(),selectedvo.getMailSequenceNumber());
         			}catch (BusinessDelegateException businessDelegateException) {
         				errors = handleDelegateException(businessDelegateException);
         				log.log(Log.INFO,"ERROR--SERVER------findMailbagHistories---->>");
         			}				
                  	
                  	try{
                  		ooe = new MailTrackingDefaultsDelegate().findOfficeOfExchange(selectedvo.getCompanyCode(),
   							selectedvo.getDoe(), 1);
   					    				}catch (BusinessDelegateException businessDelegateException) {
   					 					// TODO Auto-generated catch block
   					 					errors = handleDelegateException(businessDelegateException);
   					 				}
                  	OfficeOfExchangeVO officeOfExchangeVO=new OfficeOfExchangeVO();
    				if(ooe!=null && !ooe.isEmpty()){
    					officeOfExchangeVO = ooe.iterator().next();
    				}
    				String cityCode = officeOfExchangeVO.getCityCode();
    				String airportCode=null;
    				if(officeOfExchangeVO.getAirportCode() != null){
    				airportCode = officeOfExchangeVO.getAirportCode();
    				}
    				if(officeOfExchangeVO.getAirportCode()==null){
    					Collection<String> does=new ArrayList<String>();
    	    			Collection<ArrayList<String>> groupedOECityArpCodes = null;
    	    			if(!does.contains(selectedvo.getDoe())){
             				does.add(selectedvo.getDoe());
             			}
    	    			try {
							groupedOECityArpCodes=new MailTrackingDefaultsDelegate().findCityAndAirportForOE(selectedvo.getCompanyCode(),does);
						} catch (BusinessDelegateException e) {
							e.getMessage();
						}
    	    			if (groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
    	    	            for (ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
    	    	                if (cityAndArpForOE.size() == 3) {
    	    	                    if (selectedvo.getDoe() != null && selectedvo.getDoe().length() > 0 && selectedvo.getDoe().equals(cityAndArpForOE.get(0))) {
    	    	                    	airportCode = cityAndArpForOE.get(2);

    	    					}
    	    				}
    	    			}
    	    		  } 
    				}
    				boolean coTerminusRdyForDelivery =false;
    				boolean coTerminusForDelivery = false;
    				LocalDate dspDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
    				try {
						coTerminusRdyForDelivery=new MailTrackingDefaultsDelegate().validateCoterminusairports(airportCode,selectedvo.getScannedPort(),MailConstantsVO.RESDIT_READYFOR_DELIVERY,officeOfExchangeVO.getPoaCode(),dspDate);
					} catch (BusinessDelegateException e) {
						e.getMessage();
					}
    				try {
    					coTerminusForDelivery=new MailTrackingDefaultsDelegate().validateCoterminusairports(airportCode,selectedvo.getScannedPort(),MailConstantsVO.RESDIT_DELIVERED,officeOfExchangeVO.getPoaCode(),dspDate);
					} catch (BusinessDelegateException e) {
						e.getMessage();
					}
         			 if(mailhistroryVos != null && mailhistroryVos.size() >0){
	                  	for( MailbagHistoryVO mailbagHistoryVO : mailhistroryVos) {
		                  	if(CONST_ACCEPT_FLG.equals(mailbagHistoryVO.getMailStatus())){
		                  		acceptedPort = mailbagHistoryVO.getScannedPort();
	         							break;
	         						}			
	         					}
    		        	}
         		if(!enableAutoArrival){
                  	if(acceptedPort == null || (acceptedPort != null && !(acceptedPort.equals(selectedvo.getScannedPort())))){
                 	
                  		log.log(Log.INFO,"Mail Bags acceptance port is different from the return operation's airport");
                  		invocationContext.addError(new ErrorVO("mailtracking.defaults.manifestedcannotreturnatimport"));			
    					invocationContext.target = TARGET_FAILURE;
    					return;
                  	 }
                	//Validation added for ICRD-117307 ends
                 }
         		else{          
                      if(!MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(selectedvo.getLatestStatus()) && 
                    		  !MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(selectedvo.getLatestStatus()) &&
                    		  airportCode.equals(selectedvo.getScannedPort())){
                    	  log.log(Log.INFO,"Mail Bags cannot be returned at destination");
                     	  invocationContext.addError(new ErrorVO("mailtracking.defaults.manifestedcannotreturnatdestination"));			
       					  invocationContext.target = TARGET_FAILURE;
       					return; 
                       }
                       else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())
                    		   || MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(selectedvo.getLatestStatus())){
         					log.log(Log.INFO,"Mail Bags acceptance port is different from the return operation's airport");
                      		invocationContext.addError(new ErrorVO("mailtracking.defaults.manifestedcannotreturnatimport"));			
        					invocationContext.target = TARGET_FAILURE;
        					return;
         				} 
                      if(!airportCode.equals(selectedvo.getScannedPort()) && (coTerminusRdyForDelivery || coTerminusForDelivery)){
                    	  invocationContext.addError(new ErrorVO("mailtracking.defaults.coterminusvalidation"));			                 	
                    	  invocationContext.target = TARGET_FAILURE;
                    	  return; 
                      }
    			}
    			mailBagEnquiryForm.setStatus(CONST_RETURN);
        	}
        	}
        	else if (CONST_REASSIGN_MAIL.equals(currentaction)) {
        		/* validate whether arrived
        		 * Arrived cannot be reassigned
        		 */
        		/**
        		 * Arrived flag was checked for selected mailbags.
        		 * Instead of this the latest status of mailbag need to checked.
        		 * For once arrived mailbag arrived flag will be Y.After transfer reassign will
        		 * be prevented in such cases.Hence latest status of mailbag is checked.
        		 * Done by A-4809 as part of fix for ICRD-93125
        		 */
        		 for (MailbagVO selectedvo : selectedMailbagVOs) {
        			 if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(selectedvo.getLatestStatus())){ 
        				 invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.arrivedcannotreassign"));			
                	 		invocationContext.target = TARGET_FAILURE;
            	 			return;
        			 }
        		 }
        		/*
        		 *  VALIDATE PORT 
        		 */ 
                int errorPort = 0;
        	     String contPort = "";
        	     for (MailbagVO selectedvo : selectedMailbagVOs) {
        	    	 if(!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort())){
        	    		 errorPort = 1;
           				if("".equals(contPort)){
           					contPort = selectedvo.getMailbagId();
        	       		}else{
        	       			contPort = new StringBuilder(contPort)
        				              .append(",")
        				              .append(selectedvo.getMailbagId())
        				              .toString();	
        	       		}
        	    	 }
        	     }
        	     if(errorPort == 1){
        	   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
        	   	   	invocationContext.target = TARGET_FAILURE;
        	   	   	return;
        	     }
            	
        		
        		/*
        		 * check whether the mailbag is scanned at current airport
        		 * and the status is Accepted,Offloaded or NotUplifted
        		 * - else error
        		 */
        		for (MailbagVO selectedvo : selectedMailbagVOs) {
        			Collection<MailbagHistoryVO> mailhistroryVos = null;
        			boolean isAlreadyDelivered=false;
               	 	if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort())
               			 && !(CONST_ACCEPT_FLG.equals(selectedvo.getLatestStatus())
               			 || CONST_OFFLOAD_FLG.equals(selectedvo.getLatestStatus())
               			 || CONST_NOTUPTIFTED_FLG.equals(selectedvo.getLatestStatus()))) {
               		 
               	 		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));			
               	 		invocationContext.target = TARGET_FAILURE;
               	 		return;
               	 	}
               	 	if(CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus())){
               	 		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));			
               	 		invocationContext.target = TARGET_FAILURE;
           	 			return;
               	 	}
               	    if(CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())){
            	 		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));			
            	 		invocationContext.target = TARGET_FAILURE;
        	 			return;
            	 	}
	               	 /*if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())) {
	            	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.transferred"));			
	         	 		invocationContext.target = TARGET_FAILURE;
	     	 			return;
	            	}*/
               		//Validation added for ICRD-135140 starts
                  	try {
                		mailhistroryVos = 
         					new MailTrackingDefaultsDelegate().findMailbagHistories(logonAttributes.getCompanyCode(),
         							selectedvo.getMailbagId(),01);
         			}catch (BusinessDelegateException businessDelegateException) {
         				errors = handleDelegateException(businessDelegateException);
         			}				
         			 if(mailhistroryVos != null && mailhistroryVos.size() >0){
	                  	for( MailbagHistoryVO mailbagHistoryVO : mailhistroryVos) {
		                  	if(CONST_DELIVERED_FLG.equals(mailbagHistoryVO.getMailStatus())){
		                  		isAlreadyDelivered=true;
	         					break;
	         						}			
	         					}
    		        	}
                  	if(isAlreadyDelivered){
                  		log.log(Log.INFO,"Mail Bag is already delivered so cannot reassign");
                  		invocationContext.addError(new ErrorVO(MAILBAG_ISALREADY_DELIVERED));			
    					invocationContext.target = TARGET_FAILURE;
    					return;
                  	 }
                	//Validation added for ICRD-135140 ends
                }
        		
        		 mailBagEnquiryForm.setStatus(CONST_REASSIGN);
        	} 
    		
    		if (CONST_TRANSFER_MAIL.equals(currentaction)) {
        		  
    			/*
         		 *  VALIDATE ALREADY transferred 
         		 */ 
                 int errorPort = 0;
         	     String contPort = "";
         	     for (MailbagVO selectedvo : selectedMailbagVOs) {
         	    	 if("Y".equals(selectedvo.getTransferFlag())){
         	    		 errorPort = 1;
        				if("".equals(contPort)){
        					contPort = selectedvo.getMailbagId();
         	       		}else{
         	       			contPort = new StringBuilder(contPort)
         				              .append(",")
         				              .append(selectedvo.getMailbagId())
         				              .toString();	
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
         	     
         	     
          	    /*
          		 * check whether the mailbag is scanned at current airport
          		 * and the status is Accepted,Offloaded or NotUplifted
          		 * - else error
          		 */
          		for (MailbagVO selectedvo : selectedMailbagVOs) {
                     if(CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())){
              	 		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.deliveredmailbagcannottransfer"));			
              	 		invocationContext.target = TARGET_FAILURE;
          	 			return;
              	 	}
                     if(MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())) {
                	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.transferred"));			
             	 		invocationContext.target = TARGET_FAILURE;
         	 			return;
                	    }
                  }
          	     //Commented as part of ICRD-133987 by A-7371 starts
    			/*
        		 *  VALIDATE POU and Current Port 
        		 */ 
                 /*errorPort = 0;
        	     contPort = "";
        	     for (MailbagVO selectedvo : selectedMailbagVOs) {
        	    	 if(!("Y".equals(selectedvo.getArrivedFlag())
        	    			 && selectedvo.getScannedPort().equals(logonAttributes.getAirportCode()))){

        	    		 errorPort = 1;
           				if("".equals(contPort)){
           					contPort = selectedvo.getMailbagId();
        	       		}else{
        	       			contPort = new StringBuilder(contPort)
        				              .append(",")
        				              .append(selectedvo.getMailbagId())
        				              .toString();	
        	       		}
        	    	 }
        	     }
        	     if(errorPort == 1){
        	   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.differentpou",new Object[]{contPort}));
        	   	   	invocationContext.target = TARGET_FAILURE;
        	   	   	return;
        	     }*/
          	 
          	//Commented as part of ICRD-133987 by A-7371 ends
         	     
//     			/*
//         		 *  VALIDATE arrival at current port 
//         		 */ 
//                  errorPort = 0;
//         	     contPort = "";
//         	     for (MailbagVO selectedvo : selectedMailbagVOs) {
//         	    	 if(!FLAG_YES.equals(selectedvo.getArrivedFlag())){
//         	    		 errorPort = 1;
//            				if("".equals(contPort)){
//            					contPort = selectedvo.getMailbagId();
//         	       		}else{
//         	       			contPort = new StringBuilder(contPort)
//         				              .append(",")
//         				              .append(selectedvo.getMailbagId())
//         				              .toString();	
//         	       		}
//         	    	 }
//         	     }
//         	     if(errorPort == 1){
//         	   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.differentpou",
//         	   	   			new Object[]{contPort}));
//         	   	   	invocationContext.target = TARGET_FAILURE;
//         	   	   	return;
//         	     }
         	

         		/*
         		 * Validating the Destination Airport of mailbag
         		 */
        		Collection<String> does=new ArrayList<String>();
    			Collection<ArrayList<String>> groupedOECityArpCodes = null;
        		String airport = logonAttributes.getAirportCode();
        		
        		
         		for (MailbagVO selectedvo : selectedMailbagVOs) {
         			if(!does.contains(selectedvo.getDoe())){
         				does.add(selectedvo.getDoe());
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
         				errors = handleDelegateException(businessDelegateException);
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
         							//sameCity = true;
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
         				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannottransfermailrchddestn"));
         				invocationContext.target = TARGET_FAILURE;
         				return;
         			}
         		}
         		//Added by A-7540 for ICRD-322239 starts
              	/* Throw Error if the scan port is not same as acceptance port */
              		errorPort = 0;
           	     contPort = "";
           	     for (MailbagVO selectedvo : selectedMailbagVOs) {
           	    	 if(!"Y".equals(selectedvo.getArrivedFlag())
           	    			 && !selectedvo.getScannedPort().equals(logonAttributes.getAirportCode())
           	    			  ){

           	    		 errorPort = 1;
              				if("".equals(contPort)){
              					contPort = selectedvo.getMailbagId();
           	       		}else{
           	       			contPort = new StringBuilder(contPort)
           				              .append(",")
           				              .append(selectedvo.getMailbagId())
           				              .toString();	
           	       		}
           	    	 }
           	     }
           	     if(errorPort == 1){
           	   	   	invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.differentpou",new Object[]{contPort}));
           	   	   	invocationContext.target = TARGET_FAILURE;
           	   	   	return;
           	     }
           	//Added by A-7540 ICRD-322239 ends 
      			
          	    mailBagEnquiryForm.setStatus(CONST_TRANSFER);

        	} 
    	    	
        invocationContext.target = TARGET_SUCCESS;
       	
        log.exiting("ValidateCommand","execute");
    	
    }
}
