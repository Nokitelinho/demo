/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ValidateContainerTransferCommand.java
 *
 *	Created by	:	A-7929
 *	Created on	:	25-Sep-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.TransferForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ValidateContainerTransferCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7929	:	25-Sep-2018	:	Draft
 */
public class ValidateContainerTransferCommand extends AbstractCommand {
	   private Log log = LogFactory.getLogger("Mail Operations");
	 
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
	   private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.transfercontainer";
	   private static final String MAILS_TO_DELIVER = "deliverable_mails_present";
	   private static final String CONST_TRANSFER = "showTransferScreen";
	   private static final String SYSPAR_ULDTOBARROW_ALLOW="mail.operations.allowuldasbarrow";
	   private static final String ONLINE_HANDLED_AIRPORT="operations.flthandling.isonlinehandledairport";
	   private static final String ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING="operations.flthandling.enableatdcapturevalildationforimporthandling";
	   private static final String ATD_NOT_CAPTURED="mail.operations.ux.listcontainer.msg.err.atdnotcaptured";
	   private static final String ATD_NOT_CAPTURED_FOR_ONE_ULD="mail.operations.ux.listcontainer.msg.err.atdnotcapturedforoneuld";
	 public void execute(ActionContext actionContext) throws CommandInvocationException, BusinessDelegateException {
		 
		    log.entering("ValidateContainerTransferCommand","execute");
	    	
	    	ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
	    	Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();    
	    	//Collection<ContainerDetail> containerActionData = listContainerModel.getSelectedContainerData(); 
	    	LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
	    	//SearchContainerForm searchContainerForm = (SearchContainerForm)actionContext.getScreenModel();
	    	
	    	Collection<ContainerVO> reasgnContainerVOs = new ArrayList<ContainerVO>();
	    	Collection<String> nearbyOEToCurrentAirport  = new ArrayList<String>();
	    	 SharedDefaultsDelegate sharedDefaultsDelegate = 
		    	      new SharedDefaultsDelegate();
		     Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	    	Map<String, String> paramResults = null;
	    	Collection<String> codes = new ArrayList<String>();
	    	codes.add(SYSPAR_ULDTOBARROW_ALLOW);
	    	codes.add(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
	    	  try {
		    		paramResults = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
		    	} catch(BusinessDelegateException businessDelegateException) {
		    		handleDelegateException(businessDelegateException);
		    	}
	    	
	    	  String allowuldasbarrow =paramResults.get(SYSPAR_ULDTOBARROW_ALLOW);
	    	 for(ContainerDetails containerVO : selectedContainerData){
	    		 //PURE TRANSFER will be excluded , IASCB-57383      
	      	   if(logonAttributes.getAirportCode().equals(containerVO.getFinalDestination()) && 
	      			   !"PURE TRANSFER".equalsIgnoreCase(containerVO.getContainerPureTransfer())){ 
	      		  // ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.containeratfinaldest");
	      		   ErrorVO errorVO = new ErrorVO("Transfer cannot be done at the final destination of the container");
	      		   actionContext.addError(errorVO);	
	      		   log.exiting("ValidateContainerTransferCommand............","execute");
	     			return;    		   
	      	   }
	          }
            int errorval=0;
			errorval=validateATDCapture(paramResults,selectedContainerData,errorval);
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
	    	  //  VALIDATE flight or destn assigned 
	         int errorPort = 0;
	         String contPort = "";
	  	   	 for (ContainerDetails selectedvo : selectedContainerData) {
	  	   		if (selectedvo.getContainerNumber() != null ) {
	  	   			if("-1".equals(selectedvo.getFlightNumber())){
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
	  	   		}
	  	   	}
	  	   	
	  	    if(errorPort == 1){
	  	    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.destnassigned",new Object[]{contPort}));
	  	        log.exiting("ValidateContainerTransferCommand....","execute");
	    	    	return;
	         }
	  	    
           //  validate whether already transferred
	  	     int transferError = 0;
	  	     contPort = "";
	  		   	for (ContainerDetails selectedvo : selectedContainerData) {
	  		   		if (selectedvo.getContainerNumber() != null ) {
	  		   			if("Y".equals(selectedvo.getTransferFlag())){
	  		   				transferError = 1;
	  	    				if("".equals(contPort)){
	  	    					contPort = selectedvo.getContainerNumber();
	  		       			}else{
	  		       				contPort = new StringBuilder(contPort)
	  						                  .append(",")
	  						                  .append(selectedvo.getContainerNumber())
	  						                  .toString();	
	  		       			}
	  	    			}
	  		   		}
	  		   	}
	  		   	
	  		    if(transferError == 1){
	  		    	//actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.alreadytransferred",new Object[]{contPort}));
	  		    	actionContext.addError(new ErrorVO("The selected mailbag is already transferred",new Object[]{contPort}));
	  		    	 log.exiting("ValidateContainerTransferCommand","execute");
	  		    	return;
	  	     }
	  	    
	  		   	
	  		//  VALIDATION FOR Assigned port and current airport
	  	        errorPort = 0;
	  	        contPort = "";
	  		   	for (ContainerDetails selectedvo : selectedContainerData) 
	  		   		if (selectedvo.getContainerNumber() != null ) {
	  		   			if(logonAttributes.getAirportCode().equals(selectedvo.getAssignedPort())){
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
	  		   		}
	  		   				
	  			   	
	  			    if(errorPort == 1){
	  			    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.samescannedport",new Object[]{contPort}));
	  			    	 log.exiting("ValidateContainerTransferCommand....","execute");
	  		   	    	return;
	  		        }		
	  		   	
	  			    
	  			  MailbagVO mailbagToDeliver = null; 
	  		      //  VALIDATING ULD RELEASE FROM THE CURRENT SEGMENT
	  		       errorPort = 0;
	  		       contPort = "";
	  			   	for (ContainerDetails selectedvo : selectedContainerData) {
	  			   		if (MailConstantsVO.FLAG_YES.equals(selectedvo.getReleasedFlag())) {
	  		       				errorPort = 1;
	  		       				contPort = selectedvo.getContainerNumber();
	  		       				break;
	  			   		}
	  			
	  			   		if(MailConstantsVO.FLAG_YES.equals(selectedvo.getArrivedStatus())){
	  			   			errorPort = 2;
	  	       				contPort = selectedvo.getContainerNumber();    
	  	       				break;
	  			   		}
	  			   	if(MailConstantsVO.ULD_TYPE.equals(selectedvo.getType()) && logonAttributes.getAirportCode()!= null &&selectedvo.getContainerNumber()!=null ){
	  			   	String notValidContainerForTransfer =null;
					 try {
						 notValidContainerForTransfer=new MailTrackingDefaultsDelegate().checkMailInULDExistForNextSeg(selectedvo.getContainerNumber(),logonAttributes.getAirportCode(),logonAttributes.getCompanyCode());
					} catch (BusinessDelegateException e) {
						log.log(Log.INFO,"ERROR--SERVER------isValidContainerForULDlevelTransfer---->>");
					}
					if(notValidContainerForTransfer!=null && MailConstantsVO.FLAG_YES.equals(notValidContainerForTransfer)) {
						errorPort = 2;
  	       				contPort = selectedvo.getContainerNumber();    
  	       				break;
	  			   	    }
	  			   	  }
	  			   	}
	  			    if(errorPort == 1){
	  			    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.cannottransferuldalreadyreleasedfromflight",new Object[]{contPort}));
	  			    	 log.exiting("ValidateContainerTransferCommand.....","execute");
	  			    	return;
	  		        }
	  			   
	  			    if(errorPort == 2){
	  			    	actionContext.addError(new ErrorVO("mailtracking.defaults.msg.err.cannottransferuldalreadyarrivedfromflight",new Object[]{contPort}));
	  			    	 log.exiting("ValidateContainerTransferCommand.....","execute");
	  			    	return;
	  		        }	
	  		   		
	  		   	
	  			  /*// VALIDATE CARRIER CODE
	  		    	String carrierCode = searchContainerForm.getCarrier(); //added by A-7371 for ICRD-133987
	  		    		if(logonAttributes.getOwnAirlineCode().equals(carrierCode)){
	  		    		//throw error 'OAL transfer cannot be done to own airline'
	  					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.err.ownairline");
	  					actionContext.addError(errorVO);
	  					return;
	  		        }  */
	  			    
	  		    	
	  				    String airport = logonAttributes.getAirportCode();
	  				    try {
	  						 nearbyOEToCurrentAirport  = 
	  							new MailTrackingDefaultsDelegate().findOfficeOfExchangesForAirport(
	  									logonAttributes.getCompanyCode(), airport);
	  					}catch (BusinessDelegateException businessDelegateException) {
	  						Collection<ErrorVO> err = handleDelegateException(businessDelegateException);
	  						log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
	  					}	
	  				    Collection<ContainerDetailsVO> containerDetailsVOs =  new ArrayList<ContainerDetailsVO>();
	  				    containerDetailsVOs = makeContainerDetailsVO(selectedContainerData);
	  				    Collection<ContainerDetailsVO> containerVOsToTransfer = new ArrayList<ContainerDetailsVO>();
	  				    int errorFlag = 0;
	  				    if(containerDetailsVOs != null && containerDetailsVOs.size() >0){
	  				    try {
	  				    	containerVOsToTransfer = new MailTrackingDefaultsDelegate().findMailbagsInContainerForImportManifest(containerDetailsVOs);
	  					}catch (BusinessDelegateException businessDelegateException) {
	  						log
	  								.log(
	  										Log.SEVERE,
	  										"BusinessDelegateException---findMailbagsInContainerForManifest",
	  										businessDelegateException.getMessage());
	  					}
	  				    }
	  						if(containerVOsToTransfer != null && containerVOsToTransfer.size() >0){
	  							for(ContainerDetailsVO containerDtlsVO :containerVOsToTransfer){
	  								if(containerVOsToTransfer != null && containerVOsToTransfer.size() >0) {
	  								for(DSNVO dsnvo : containerDtlsVO.getDsnVOs()) {
	  									if(dsnvo.getMailbags()!= null && dsnvo.getMailbags().size() >0){
	  									for(MailbagVO  mailvo:dsnvo.getMailbags()){
	  										//log.log(Log.INFO, "<<<<<<DOE --- >>>", dsnVO.getDestinationExchangeOffice());
	  											if (!MailConstantsVO.FLAG_YES.equals(mailvo.getDeliveredFlag())) { 
	  												if(isTerminating(nearbyOEToCurrentAirport,mailvo)){	
	  												 mailbagToDeliver = mailvo;
	  													errorFlag = 1;
	  														break;
	  												}	
	  										}
	  									}
	  								}
	  							}
	  						}
	  						}
	  					}
	  					/*if(errorFlag ==1){
	  						if(!MailConstantsVO.FLAG_YES.equals(searchContainerForm.getWarningOveride())){
	  						Object[] obj = {mailbagToDeliver.getDoe()};
	  						ErrorVO err = new ErrorVO("mailtracking.defaults.deliverableMailbagsPresent",obj);
	  						err.setErrorDisplayType(ErrorDisplayType.WARNING);
	  						actionContext.addError(err);
	  						searchContainerForm.setWarningFlag(MAILS_TO_DELIVER);
	  						return;
	  						}
	  						else {
	  							searchContainerForm.setWarningFlag("");
	  							searchContainerForm.setWarningOveride(null);
	  						}
	  					}*/
	  				    	
	  		  	   //searchContainerForm.setStatus(CONST_TRANSFER);
	  		       //searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	  		          	    
				 LocalDate date = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
				 TransferForm transferForm = new TransferForm();
				 listContainerModel.setTransferForm(transferForm);
				 listContainerModel.getTransferForm().setReassignFromDate(date.toDisplayDateOnlyFormat());
				 listContainerModel.getTransferForm().setScanDate(date.toDisplayDateOnlyFormat());
				 listContainerModel.getTransferForm().setMailScanTime(date.toDisplayTimeOnlyFormat(true));
				 listContainerModel.getTransferForm().setReassignToDate(date.addDays(2).toDisplayDateOnlyFormat());
				 listContainerModel.getTransferForm().setUldToBarrow(allowuldasbarrow);//added by A-8893 for IASCB-38903
				 
				 ArrayList results = new ArrayList();
				 results.add(listContainerModel);
	  			 ResponseVO responseVO = new ResponseVO();	
				 responseVO.setResults(results);
	  		     responseVO.setStatus("success");
	  		     log.log(Log.FINE, "---status---" +responseVO.getStatus());
	  		     System.out.println("response vo status-->"+responseVO.getStatus());
	  		     actionContext.setResponseVO(responseVO);       	
	  		     log.exiting("ValidateContainerTransferCommand","execute");	    
	       }	

	
	     /**
		 * 
		 * 	Method		:	ValidateContainerTransferCommand.makeContainerDetailsVO
		 *	Added by 	:	A-7929 on 26-Sep-2018
		 * 	Used for 	:
		 *	Parameters	:	@return 
		 *	Return type	: 	ContainerDetailsVO
		 */
		private Collection<ContainerDetailsVO> makeContainerDetailsVO(Collection<ContainerDetails> containerActionData) {
			// TODO Auto-generated method stub
			  Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
			  for (ContainerDetails containerVO :containerActionData ) {
				//IF uld then only ---alert also !delivered flag check needed?
				if("U".equals(containerVO.getType()) && containerVO.getPol() != null ){
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
				containerDetailsVO.setCarrierId(containerVO.getCarrierId());
				containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
				containerDetailsVO.setPol(containerVO.getPol());
				containerDetailsVOs.add(containerDetailsVO);
			 }
			}
			return containerDetailsVOs;
		}
		
		/**
		 * 
		 * 	Method		:	ValidateContainerTransferCommand.isTerminating
		 *	Added by 	:	a-7929 on 26-Sep-2018
		 * 	Used for 	:
		 *	Parameters	:	@return 
		 *	Return type	: 	boolean isTerminating
		 */
		private boolean isTerminating(Collection<String> nearbyOEToCurrentAirport,MailbagVO mailbagvo){
			log.entering("TransferContainerCommand","break;");
			boolean isTerminating = false;
			if(nearbyOEToCurrentAirport != null && nearbyOEToCurrentAirport.size() > 0){
				for(String officeOfExchange : nearbyOEToCurrentAirport){
					if(mailbagvo != null && !"Y".equals(mailbagvo.getDeliveredFlag())){
						isTerminating = officeOfExchange.equals(mailbagvo.getDoe()) ? true : false;
						if(isTerminating){
							break;	
						}
					}
				}
			}
			log.exiting("TransferContainerCommand","break;");
			return isTerminating;
		 }
		private int validateATDCapture(Map<String, String> paramResults, Collection<ContainerDetails> selectedContainerData,int errorval) throws BusinessDelegateException{
			
			String valildationforimporthandling=paramResults.get(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
			
			for(ContainerDetails containerVO : selectedContainerData){
				 if("Y".equals(valildationforimporthandling)){
		    		  
		    		    FlightFilterVO flightFilterVO = new FlightFilterVO();
		    			flightFilterVO.setCompanyCode(containerVO.getCompanyCode());
		    			flightFilterVO.setFlightCarrierId(containerVO.getCarrierId());
		    			flightFilterVO.setFlightNumber(containerVO.getFlightNumber());
		    			flightFilterVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		    			
						 errorval= doATDValidation(selectedContainerData, flightFilterVO,errorval);	
		    	   }
			}
			return errorval;
			
		}


		private int doATDValidation(Collection<ContainerDetails> selectedContainerData,
		FlightFilterVO flightFilterVO, int seterror) throws BusinessDelegateException {
	Collection<FlightValidationVO> flightValidationVOs = null;
	String onlineHndledParameter = null;
	String currOnlineHndledParameter = null;
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
		 
		 	LogonAttributes logonAttributes = getLogonAttribute();
			flightFilterVO.setAirportCode(logonAttributes.getAirportCode());
			Map<String, String> curArpMap = mailTrackingDefaultsDelegate.findAirportParameterCode(flightFilterVO,
					parCodes);
			currOnlineHndledParameter = curArpMap.get(ONLINE_HANDLED_AIRPORT);
		
		 if(("Y").equals(onlineHndledParameter) && ("Y").equals(currOnlineHndledParameter) && flightValidationVOs.iterator().next().getAtd()==null){
			 if(selectedContainerData.size()>1){
				 seterror = 1; 
			 }
			 
		 else{
			 if(selectedContainerData.size()==1){
				 seterror = 2;
			 }
		 }
			 
		 }
	}
	return seterror;
}
}
