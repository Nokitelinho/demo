/*
 * ValidateContainerReassignCommand.java Created on Sep 26, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
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
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ReassignContainer;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.ValidateContainerReassignCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	26-Sep-2018	:	Draft
 */
public class ValidateContainerReassignCommand extends AbstractCommand {
	
   private Log log = LogFactory.getLogger("MAIL");
   private static final String ONLINE_HANDLED_AIRPORT="operations.flthandling.isonlinehandledairport";
   private static final String ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING="operations.flthandling.enableatdcapturevalildationforimporthandling";	
   private static final String ATD_NOT_CAPTURED="mail.operations.ux.listcontainer.msg.err.atdnotcaptured";
   private static final String ATD_NOT_CAPTURED_FOR_ONE_ULD="mail.operations.ux.listcontainer.msg.err.atdnotcapturedforoneuld";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 * @throws BusinessDelegateException 
	 */
    public void execute(ActionContext actionContext)
            throws CommandInvocationException, BusinessDelegateException {
    	
    	log.entering("ValidateContainerReassignCommand","execute");
    	
    	ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
    	System.out.println("---->listContainerModel"+listContainerModel);
    	Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();    	
    	LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
    	Map<String, String> paramResults = null;
    	Collection<String> codes = new ArrayList<>();
    	codes.add(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
    	try {
    		paramResults = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
    	 String valildationforimporthandling=paramResults.get(ATD_CAPTURE_VALIDATION_FOR_IMPORTHANDLING);
    	selectedContainerData = findContainerData(selectedContainerData, logonAttributes);
        for(ContainerDetails containerVO : selectedContainerData){
    	   if(logonAttributes.getAirportCode().equals(containerVO.getFinalDestination())){
    		   ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.containeratfinaldest");
    		   actionContext.addError(errorVO);		
   			   return;    		   
    	   }
    	   
    	   if("Y".equals(valildationforimporthandling)){
    		  
    		    FlightFilterVO flightFilterVO = new FlightFilterVO();
    			flightFilterVO.setCompanyCode(containerVO.getCompanyCode());
    			flightFilterVO.setFlightCarrierId(containerVO.getCarrierId());
    			flightFilterVO.setFlightNumber(containerVO.getFlightNumber());
    			flightFilterVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
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
						 
						 		flightFilterVO.setAirportCode(logonAttributes.getAirportCode());
					Map<String, String> curArpMap = mailTrackingDefaultsDelegate
							.findAirportParameterCode(flightFilterVO, parCodes);
					currOnlineHndledParameter = curArpMap.get(ONLINE_HANDLED_AIRPORT);
						
						
						 if(("Y").equals(onlineHndledParameter) && ("Y").equals(currOnlineHndledParameter) && flightValidationVOs.iterator().next().getAtd()==null && selectedContainerData.size()>1){
							  ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED_FOR_ONE_ULD);
				    		  actionContext.addError(errorVO);		
				   			  return;   
							}
						 else{
							 if(("Y").equals(onlineHndledParameter) && ("Y").equals(currOnlineHndledParameter) && flightValidationVOs.iterator().next().getAtd()==null && selectedContainerData.size()==1){
								 ErrorVO errorVO = new ErrorVO(ATD_NOT_CAPTURED);
								  actionContext.addError(errorVO); 
								  return;   
							 }
						 }
						
					}
    	   }
        }
       
       
       // VALIDATION FOR CONTAINERS HOLDING OFFLOADED MAILS
       int errorOfl = 0;
       int errorPort = 0;
       
       String contOfl = "";
       int errorArr = 0;
       String contArr = "";
       
       String contPort = "";
       int errorAlreadyManifest=0;
	   String contAlreadyManifest = "";
	   int errorNonPartner =0;
	   String nonPartner="";
	   String conNum="";
       
	   	for (ContainerDetails selectedvo : selectedContainerData) {
	   		if (selectedvo.getContainerNumber() != null ) {
	   			
	   			if(!logonAttributes.getAirportCode().equals(selectedvo.getAirportCode())){
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
	   			
	   			
				//Added by A-5945 for ICRD-96261 starts
				if(selectedvo.getCarrierCode()!=null){
					nonPartner=	selectedvo.getCarrierCode();
					conNum=selectedvo.getContainerNumber();
				Collection<PartnerCarrierVO> partnerCarrierVOs = null;
				ArrayList<String> partnerCarriers = new ArrayList<String>();
				MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
		    		new MailTrackingDefaultsDelegate();
				String companyCode =logonAttributes.getCompanyCode();
				String ownCarrierCode = logonAttributes.getOwnAirlineCode();
				String airportCode = logonAttributes.getAirportCode();
				String CarrierCode =selectedvo.getCarrierCode();
				try {
					partnerCarrierVOs=mailTrackingDefaultsDelegate.findAllPartnerCarriers(companyCode,ownCarrierCode,airportCode);
				}catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				log.log(Log.INFO, "partnerCarrierVOs-----------------",
						partnerCarrierVOs);
				if(partnerCarrierVOs!= null){
					for(PartnerCarrierVO partner :partnerCarrierVOs){
						String partnerCarrier =	 partner.getPartnerCarrierCode();
						partnerCarriers.add(partnerCarrier);
						} 
						partnerCarriers.add(ownCarrierCode);  
					if(!(partnerCarriers.contains(CarrierCode))){
						errorNonPartner = 1;
						if("TRANSFER".equals(listContainerModel.getMode())||"TRANSFER_FLIGHT".equals(listContainerModel.getMode())){
							errorNonPartner = 0;
						}
						
						}					
				}
				}
				//Added by A-5945 for ICRD-96261 ends
				
	   		}
	   	}
	  //Added by A-5945 for ICRD-96261 starts
	    if(errorNonPartner == 1){
	    	actionContext.addError(new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.notHandledCarrier",new Object[]{conNum,nonPartner}));
   	      log.exiting("ValidateContainerReassignCommand","execute");
   	    	return;
        }
	  //Added by A-5945 for ICRD-96261 ends    
	    if(errorPort == 1){
	    	actionContext.addError(new ErrorVO("mailtracking.defaults.searchcontainer.differentport",new Object[]{contPort}));   	    	
   	        log.exiting("ValidateContainerReassignCommand","execute");
   	    	return;
        }
	    
	   	if(errorOfl == 1){
	   		actionContext.addError(new ErrorVO(
	   				"mailtracking.defaults.msg.err.containersHoldOffloadedMails",new Object[]{contOfl}));	   		
	        log.exiting("ValidateContainerReassignCommand","execute");
			return;

	   	}
	   	
	   	if(errorArr == 1){
	   		actionContext.addError(new ErrorVO(
	   				"mailtracking.defaults.msg.err.reassigncontainersarrived",new Object[]{contArr}));	   		
	        log.exiting("ValidateContainerReassignCommand","execute");
			return;
	   	}
		if(errorAlreadyManifest == 1){
			actionContext.addError(new ErrorVO(
  	    			"mailtracking.defaults.searchcontainer.err.cannnotreassigntransitcontainer",new Object[]{contAlreadyManifest}));  	    		    	
		    log.exiting("ValidateContainerReassignCommand","execute");
			return;		
       }
	   	
	  

  	 //  searchContainerForm.setStatus(CONST_REASSIGN);
     
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
		ReassignContainer reassignContainer = new ReassignContainer();
		listContainerModel.setReassignContainer(reassignContainer);
		listContainerModel.getReassignContainer().setFromDate(date.toDisplayDateOnlyFormat());
		listContainerModel.getReassignContainer().setScanDate(date.toDisplayDateOnlyFormat());
		listContainerModel.getReassignContainer().setMailScanTime(date.toDisplayTimeOnlyFormat(true));
		listContainerModel.getReassignContainer().setReassignToDate(date.addDays(1).toDisplayDateOnlyFormat());
		ResponseVO responseVO = new ResponseVO();	  
       responseVO.setStatus("success");
		ArrayList results = new ArrayList();
		results.add(listContainerModel);
		responseVO.setResults(results);
       actionContext.setResponseVO(responseVO);       	
       log.exiting("ValidateContainerReassignCommand","execute");
    	
    }
    public Collection<ContainerDetails> findContainerData(Collection<ContainerDetails> selectedContainerData, LogonAttributes logonAttributes){
    	Page<ContainerVO> containerVOs = null;
    	SearchContainerFilterVO searchContainerFilterVO = new SearchContainerFilterVO();
    	for(ContainerDetails container : selectedContainerData){
			searchContainerFilterVO.setSource("REASSIGN_CONTAINER");
    		searchContainerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    		searchContainerFilterVO.setContainerNumber(container.getContainerNumber());
    		searchContainerFilterVO.setShowEmptyContainer("Y");
    		if(container.getFlightNumber().equals("-1")){
    			searchContainerFilterVO.setSearchMode("DESTN");
    		}else{
    			searchContainerFilterVO.setSearchMode("FLT");
    		}
    		searchContainerFilterVO.setOperationType("O");
    		try
    	      {
    	        containerVOs = new MailTrackingDefaultsDelegate().findContainers(searchContainerFilterVO, 
    	          1);
    	      } catch (BusinessDelegateException businessDelegateException) {
    	        businessDelegateException.getMessageVO().getErrors();
    	        handleDelegateException(businessDelegateException);
    	      }
    		if(containerVOs!=null && containerVOs.size() > 0  ){
    		for(ContainerVO containerVO : containerVOs){
    			if(containerVO.getActualWeight()!=null)
    			container.setActualWeight(containerVO.getActualWeight().getRoundedDisplayValue());
    			container.setContentId(containerVO.getContentId());
					if (containerVO.getActWgtSta() != null) {
						container.setActWgtSta(containerVO.getActWgtSta());
					}
    			
    		}
    	}}
    	return selectedContainerData;
    }
    
   
    
    
       
}
