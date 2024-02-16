/*
 * ValidateCommand.java Created on March 8, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;
import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_TRANSFERRED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8672
 *
 */
public class ValidateCommand extends AbstractCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
//   private static final String TARGET_SUCCESS = "validate_success";
//   private static final String TARGET_FAILURE = "validate_failure";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
   
//   private static final String CONST_REASSIGN = "ShowReassignMailPopup";
//   private static final String CONST_TRANSFER = "ShowTransferMailPopup";
//   private static final String CONST_RETURN = "ShowReturnMailPopup";
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
   //Added by A-7540
   private static final String AUTOARRIVALFUNCTIONPOINTS="mail.operations.autoarrivalfunctionpoints";
   private static final String AUTOARRIVALENABLEDPAS="mail.operations.autoarrivalenabledPAs";
   private static final String FUNPNTS_RTN  = "RTN";
   private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
   private static final String ALL = "All";
   private static final String ONLINE_HANDLED_AIRPORT="operations.flthandling.isonlinehandledairport";
   private static final String ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING="operations.flthandling.enableatdcapturevalildationforimporthandling";
   private static final String ATD_NOT_CAPTURED="mailtracking.defaults.mailbagenquiry.msg.err.atdnotcaptured";
   private static final String ATD_NOT_CAPTURED_FOR_ONE_ULD="mailtracking.defaults.mailbagenquiry.msg.err.atdnotcapturedforoneuld";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param actionContext
	 * @return
	 * @throws CommandInvocationException
	 * @throws BusinessDelegateException 
	 */
    public void execute(ActionContext actionContext)
            throws CommandInvocationException, BusinessDelegateException {
    	
    	log.entering("ValidateCommand","execute");
    	
    	LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
    	ResponseVO responseVO = new ResponseVO();
    	MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
//    	ApplicationSessionImpl applicationSession = getApplicationSession();
//		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
//    	Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
    	Collection<Mailbag> selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
    	Collection<MailbagVO> selectedMailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags, logonAttributes);
    	Page<OfficeOfExchangeVO> ooe = null;
    	//Added as part of IASCB-34119
		mailbagEnquiryModel.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		Collection<PartnerCarrierVO> partnerCarriers = new ArrayList<PartnerCarrierVO>();
		ArrayList<String> partnerCarriersList = new ArrayList<String>();
		try {
			partnerCarriers = new MailTrackingDefaultsDelegate().findAllPartnerCarriers(
					logonAttributes.getCompanyCode(), logonAttributes.getOwnAirlineCode(),
					logonAttributes.getAirportCode());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}
		if (partnerCarriers != null && partnerCarriers.size() > 0) {
			for (PartnerCarrierVO partnerCarrierVO : partnerCarriers) {
				partnerCarriersList.add(partnerCarrierVO.getPartnerCarrierCode());
			}
		}
		mailbagEnquiryModel.setPartnerCarriers(partnerCarriersList);
    	//Added as part of IASCB-34119 ends
    	ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(AUTOARRIVALFUNCTIONPOINTS);
		systemParameters.add(AUTOARRIVALENABLEDPAS);
		systemParameters.add(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
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
		 String valildationforimporthandling= null;
		boolean enableAutoArrival = false;
		if (systemParameterMap != null) {
			sysparfunctionpoints = systemParameterMap.get(AUTOARRIVALFUNCTIONPOINTS);
			autoArrEnabledPAs=systemParameterMap.get(AUTOARRIVALENABLEDPAS);
			valildationforimporthandling=systemParameterMap.get(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
		}
		
		if(sysparfunctionpoints!=null &&
				sysparfunctionpoints.contains(FUNPNTS_RTN)
				&& (autoArrEnabledPAs.contains(ALL) || autoArrEnabledPAs.contains(selectedMailbagVOs.iterator().next().getPaCode()))
				){
			enableAutoArrival = true;
		}
    	String currentaction = mailbagEnquiryModel.getStatus();
    	
    	
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
    		actionContext.addError(errorVO);			
//    		actionContext.target = TARGET_FAILURE;
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
			actionContext.addError(errorVO);			
			return;
    	}
    	
    		if (CONST_RETURN_MAIL.equals(currentaction)) {
	
    			/*
    			 * check whether the mailbag is INBOUND or OUTBOUND
    			 * if INBOUND - it must be accepted or offloaded at current airport
    			 * if OUTBOUND - it must be arrived at current airport 
    			 */
    			for (MailbagVO selectedvo : selectedMailbagVOs) {
    				Map<String, CityVO> cityVoMap = new HashMap<String, CityVO>();
        			Collection<PostalAdministrationVO> postalAdministrationVOs=new ArrayList<PostalAdministrationVO>();
    				String station="";
    				Collection<String> systemParameter = new ArrayList<String>();
    				Map<String, String> results = null;
    				String paCode_dom = null; 
    				if(selectedvo.getMailbagId().length()==29){
   				Collection<String> officeOfExchange = new ArrayList<String>();
   				officeOfExchange.add(selectedvo.getOoe());
   				Collection<ArrayList<String>> cityCodesforAirport = null;
   				try {
   					cityCodesforAirport = new MailTrackingDefaultsDelegate().findCityAndAirportForOE(selectedvo.getCompanyCode(),officeOfExchange);
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
     				log.log(Log.INFO,"ERROR--SERVER------findcityCodesforAirport---->>");
				}
   				if(cityCodesforAirport!=null && !cityCodesforAirport.isEmpty()){
   	 					for(ArrayList<String> cityAndArpForOE : cityCodesforAirport) {
   	 								 station=cityAndArpForOE.get(1); 
   	 					}
   	 			}	 
    				
    				try {
    					Collection<String> cityCodes = new ArrayList<String>();
    					cityCodes.add(station);
    					cityVoMap = new AreaDelegate().validateCityCodes(logonAttributes.getCompanyCode(), cityCodes);//added by a-7779 for ICRD-341626
    				} catch (BusinessDelegateException e1) {						
    					e1.getMessage();
    				}
    				if(cityVoMap!=null && !cityVoMap.isEmpty()){
    					String countryCode = cityVoMap.get(station).getCountryCode();
    			try {
    				postalAdministrationVOs = new MailTrackingDefaultsDelegate().findLocalPAs(
    						logonAttributes.getCompanyCode(),
    						countryCode);
    			} catch (BusinessDelegateException e1) {					
    				e1.getMessage();
		    			}
    			}
    		}
                    else{    					  					
    					systemParameter.add(USPS_DOMESTIC_PA);
    					try {
    						results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameter);
    					} catch (BusinessDelegateException businessDelegateException) {
    						Collection<ErrorVO> error = handleDelegateException(businessDelegateException);
    						log.log(Log.INFO,"");
    					}	    					
    					if (results != null) {    						
    						paCode_dom =results.get(USPS_DOMESTIC_PA);
    					}
    					PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();		  			
		  				
    		  			try {
							postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
												logonAttributes.getCompanyCode(),paCode_dom);
						} catch (BusinessDelegateException e) {							
							e.getMessage();
						}	
    		  			postalAdministrationVOs.add(postalAdministrationVO);
    			}
    			MailOperationsModelConverter mailOperationsModelConverter = new MailOperationsModelConverter();
    			mailbagEnquiryModel.setPostalAdministrations(mailOperationsModelConverter.convertPostalAdministartionVos(postalAdministrationVOs));
    				Collection<MailbagHistoryVO> mailhistroryVos = null;
    				String acceptedPort = null;
                  	if (CONST_INBOUND.equals(selectedvo.getOperationalStatus())) {
                  		if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort())){
                     		if(!( CONST_ACCEPT_FLG.equals(selectedvo.getLatestStatus())
                     			 || CONST_OFFLOAD_FLG.equals(selectedvo.getLatestStatus()))) {
                     			actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForInboundReturn"));			
                     			return;
                     		}
                     	 }
                  	}
                  	else if (CONST_OUTBOUND.equals(selectedvo.getOperationalStatus())) {
                  		if (!logonAttributes.getAirportCode().equals(selectedvo.getScannedPort())){
                    		if(!(CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus()))){
                    			actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForOutboundReturn"));			
            					return;
                    		}
                    	 }
                  	}
                  	if(CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())){
            			actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.deliveredbagscannotreturn"));			
    					return;
            		}
//                  	
//                  	if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())) {
//               	    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.transferred"));			
//            	 		actionContext.target = TARGET_FAILURE;
//        	 			return;
//               	    }
                  	//Validation added for ICRD-117307 starts
                  	try {
         				
                		mailhistroryVos = 
         					new MailTrackingDefaultsDelegate().findMailbagHistories(logonAttributes.getCompanyCode(),
         							selectedvo.getMailbagId(),0l);
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
    				//Added by A-7540
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
                  	if( acceptedPort == null || (acceptedPort != null && !(acceptedPort.equals(selectedvo.getScannedPort()))) ){
                  		log.log(Log.INFO,"Mail Bags acceptance port is different from the return operation's airport");
                  		actionContext.addError(new ErrorVO("mailtracking.defaults.manifestedcannotreturnatimport"));			
    					return;
                  	 }
         		}
         		else{        
                    if(!MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(selectedvo.getLatestStatus()) && 
                  		  !MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(selectedvo.getLatestStatus()) &&
                  		airportCode.equals(selectedvo.getScannedPort())&& (acceptedPort==null || !acceptedPort.equals(selectedvo.getMailDestination()))){
                  	  log.log(Log.INFO,"Mail Bags cannot be returned at destination");
                  	actionContext.addError(new ErrorVO("mailtracking.defaults.manifestedcannotreturnatdestination"));			                 	
     					return; 
                     }
                    
                 if(!airportCode.equals(selectedvo.getScannedPort()) && (coTerminusRdyForDelivery || coTerminusForDelivery)){
                	 actionContext.addError(new ErrorVO("mailtracking.defaults.coterminusvalidation"));			                 	
  					return; 
                 }
                     //else if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())
                  		 //  || MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(selectedvo.getLatestStatus())){
       					//log.log(Log.INFO,"Mail Bags acceptance port is different from the return operation's airport");
       					//actionContext.addError(new ErrorVO("mailtracking.defaults.manifestedcannotreturnatimport"));			       					
      					//return;
       				//} 
  			}
                	//Validation added for ICRD-117307 ends
                 }
    			
//    			mailBagEnquiryForm.setStatus(CONST_RETURN);
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
        				 actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.arrivedcannotreassign"));			
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
        	   	   	actionContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));
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
               		 
               	 		actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));			
               	 		return;
               	 	}
               	 	if(CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus())){
               	 		actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));			
           	 			return;
               	 	}
               	    if(CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())){
            	 		actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForReassign"));			
        	 			return;
            	 	}
	               	 /*if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())) {
	            	    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.transferred"));			
	         	 		actionContext.target = TARGET_FAILURE;
	     	 			return;
	            	}*/
               		//Validation added for ICRD-135140 starts
                  	try {
                		mailhistroryVos = 
         					new MailTrackingDefaultsDelegate().findMailbagHistories(logonAttributes.getCompanyCode(),
         							selectedvo.getMailbagId(), 0l);
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
                  		actionContext.addError(new ErrorVO(MAILBAG_ISALREADY_DELIVERED));			
    					return;
                  	 }
                	//Validation added for ICRD-135140 ends
                }
        		int errorval=0;
        		if("Y".equals(valildationforimporthandling)){
        			for (MailbagVO selectedvo : selectedMailbagVOs) {
        				
        				errorval = validationForATDCapture(selectedMailbagVOs, selectedvo,errorval);	
        			}
        		}
        		if(errorval== 1){
   				 ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED_FOR_ONE_ULD);
   				  actionContext.addError(errorVO);
   				  return;
   			}
   			else{
   				if(errorval== 2){
   					ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED);
   					  actionContext.addError(errorVO);
   					  return;
   				}
   			}
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
         	   	   	actionContext.addError(
         	   	   			new ErrorVO("mailtracking.defaults.msg.err.alreadytransferred",
         	   	   			new Object[]{contPort}));
         	   	   	return;
         	     }
         	     
         	     
          	    /*
          		 * check whether the mailbag is scanned at current airport
          		 * and the status is Accepted,Offloaded or NotUplifted
          		 * - else error
          		 */
          		for (MailbagVO selectedvo : selectedMailbagVOs) {
                     if(CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())){
              	 		actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.deliveredmailbagcannottransfer"));			
          	 			return;
              	 	}
                     if(MAIL_STATUS_TRANSFERRED.equals(selectedvo.getLatestStatus())) {
                	    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.transferred"));			
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
        	   	   	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.differentpou",new Object[]{contPort}));
        	   	   	actionContext.target = TARGET_FAILURE;
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
//         	   	   	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.differentpou",
//         	   	   			new Object[]{contPort}));
//         	   	   	actionContext.target = TARGET_FAILURE;
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
         			//Commented by A-7540 for ICRD-322239
         			/*if(CONST_ACCEPT_FLG.equals(selectedvo.getLatestStatus()) && selectedvo.getScannedPort().equals(logonAttributes.getAirportCode())){
         				log.log(Log.INFO,"<<---- Transfer operation from acceptance port --->>");
         				actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannottransferfromacceptanceport"));
         				return;
             		}*/
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
         				actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannottransfermailrchddestn"));
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
           	    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.differentpou",new Object[]{contPort}));
           	   	   	return;
           	     }
           	//Added by A-7540 ICRD-322239 ends 
           	  int errorval=0;
           	  if("Y".equals(valildationforimporthandling)){
      			for (MailbagVO selectedvo : selectedMailbagVOs) {
      				
      				
      				errorval = validationForATDCapture(selectedMailbagVOs, selectedvo,errorval);		
      			}
      		}
           	if(errorval== 1){
				 ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED_FOR_ONE_ULD);
				  actionContext.addError(errorVO);
				  return;
			}
			else{
				if(errorval== 2){
					ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED);
					  actionContext.addError(errorVO);
					  return;
				}
			}
           	     
        	} 
    		LocalDate date=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
    		mailbagEnquiryModel.setScanDate(date.toDisplayDateOnlyFormat());
    		mailbagEnquiryModel.setScanTime(date.toDisplayTimeOnlyFormat(true));
    		ArrayList results = new ArrayList();
    		results.add(mailbagEnquiryModel);
    		responseVO.setResults(results);
    	    	
    		responseVO.setStatus("success");
    		actionContext.setResponseVO(responseVO);
       	
        log.exiting("ValidateCommand","execute");
    	
    }
	private int validationForATDCapture(Collection<MailbagVO> selectedMailbagVOs,
			MailbagVO selectedvo,int errorval) throws BusinessDelegateException {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(selectedvo.getCompanyCode());
		flightFilterVO.setFlightCarrierId(selectedvo.getCarrierId());
		flightFilterVO.setFlightNumber(selectedvo.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(selectedvo.getFlightSequenceNumber());
		Collection<FlightValidationVO> flightValidationVOs = null;
		 String onlineHndledParameter = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
				new MailTrackingDefaultsDelegate();
		flightValidationVOs = mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			
			String fltOrigin =  flightValidationVOs.iterator().next().getLegOrigin();
			flightFilterVO.setAirportCode(fltOrigin);
			Collection<String> parCodes =new ArrayList<>();
			parCodes.add(ONLINE_HANDLED_AIRPORT);
			
			Map<String, String> arpMap= mailTrackingDefaultsDelegate.findAirportParameterCode(flightFilterVO,parCodes);
			 onlineHndledParameter =arpMap.get(ONLINE_HANDLED_AIRPORT);
			
			 if(("N").equals(onlineHndledParameter) && flightValidationVOs.iterator().next().getAtd()==null && selectedMailbagVOs.size() >1){
				 errorval = 1;
				}
			 
			 else{
				 if(("N").equals(onlineHndledParameter) && flightValidationVOs.iterator().next().getAtd()==null && selectedMailbagVOs.size()==1){  
					 errorval = 2;
				 }
			 }
				 
			
		}
		return errorval;
    }
}
