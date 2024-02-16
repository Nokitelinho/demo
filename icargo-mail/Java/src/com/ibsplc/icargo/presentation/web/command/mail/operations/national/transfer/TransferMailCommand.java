/* TransferMailCommand.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ArriveDispatchSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.TransferDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.TransferDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-4810
 */

public class TransferMailCommand extends BaseCommand{
	 /**
    * Log
    */
   private Log log = LogFactory.getLogger("MAILTRACKING");

   
   
   private static final String MODULE_NAME = "mail.operations";	
   
   private static final String SCREEN_ID = "mailtracking.defaults.national.transfermail";	
   
   private static final String MODULE_NAME1 = "mail.operations";	
   
   private static final String SCREEN_ID1 = "mailtracking.defaults.national.mailarrival";	
	
   private static final String SCREEN_ID_DISPATCH = "mailtracking.defaults.national.dispatchEnquiry";
   /**
    * Screen id
    */
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
  
   /**
    * Screen id
    */
   private static final String SCREEN_ID_FLIGHT = "flight.operation.maintainflight";

   /**
    * Module name
    */
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   
  /**
    * Target string
    */
   private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
   /**
    * Target string
    */
   private static final String SCREENLOAD_FAILURE = "screenload_failure";
   
      
   /**
    *  Status of flag
    */
   private static final String OUTBOUND = "O";
	private static final String ARR_MAIL_BAG = "ARR_MAIL_BAG";
	private static final String DSN_ENQUIRY = "DSN_ENQUIRY";
   

   /**
    * Execute method
    * @param invocationContext InvocationContext
    * @throws CommandInvocationException
    */
   public void execute(InvocationContext invocationContext)
           throws CommandInvocationException {
       log.entering("TransferMailCommand", "execute");
       
       TransferDispatchForm transferDispatchForm = 
    	   (TransferDispatchForm)invocationContext.screenModel;
       TransferDispatchSession transferDispatchSession = getScreenSession(
    		   MODULE_NAME, SCREEN_ID);
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID_DISPATCH);
       transferDispatchForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

       ArriveDispatchSession arriveDispatchSession = 
    	   getScreenSession(MODULE_NAME1,SCREEN_ID1);
       ApplicationSessionImpl applicationSession = getApplicationSession();
       LogonAttributes logonAttributes = applicationSession.getLogonVO();
       LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);	
       AirportValidationVO   airportValidationVO = null;		
       MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
    	   new MailTrackingDefaultsDelegate();
				
       Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
       AirlineDelegate airlineDelegate = new AirlineDelegate();

       errors = validateForm(transferDispatchForm);
       if (errors != null && errors.size() > 0) {  

    	   invocationContext.addAllError(errors);
    	   invocationContext.target = SCREENLOAD_FAILURE;
    	   return;
       }
       
       airportValidationVO = validateairport(logonAttributes.getCompanyCode(), transferDispatchForm.getFlightPou().toUpperCase());
       if(airportValidationVO == null){
    	   errors.add(new ErrorVO("mailtracking.defaults.national.invalidpouentered"));
    	   if (errors != null && errors.size() > 0) {

    		   invocationContext.addAllError(errors);
    		   invocationContext.target = SCREENLOAD_FAILURE;
    		   return;
    	   }
       }
	   
       String flightNum = (transferDispatchForm.getFlightNumber().toUpperCase());

       FlightFilterVO flightFilterVO = handleFlightFilterVO(
    		   transferDispatchForm,logonAttributes);

       AirlineValidationVO airlineValidationVO = null;
       String flightCarrierCode = transferDispatchForm.getFlightCarrierCode().trim().toUpperCase();        	
       if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {        		
    	   try {        			
    		   airlineValidationVO = airlineDelegate.validateAlphaCode(
    				   logonAttributes.getCompanyCode(),flightCarrierCode);
    	   }catch (BusinessDelegateException businessDelegateException) {
    		   errors = handleDelegateException(businessDelegateException);
    	   }
    	   if (errors != null && errors.size() > 0) {            			
    		   Object[] obj = {flightCarrierCode};

    		   invocationContext.addError(new ErrorVO("mailtracking.defaults.national.invalidcarrier",obj));
    		   invocationContext.target = SCREENLOAD_FAILURE;
    		   return;
    	   }
       }
		
      	
		/*******************************************************************
		 * validate Flight 
		 ******************************************************************/
       flightFilterVO.setCarrierCode(flightCarrierCode);
       flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());

       flightFilterVO.setFlightNumber(flightNum);
       //flightFilterVO.setAirportCode(logonAttributes.getAirportCode());
       Collection<FlightValidationVO> flightValidationVOs = null;
       try {
    	   log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
		flightValidationVOs =
    		   mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

       }catch (BusinessDelegateException businessDelegateException) {
    	   errors = handleDelegateException(businessDelegateException);
       }
       if (errors != null && errors.size() > 0) {

    	   invocationContext.addAllError(errors);
    	   invocationContext.target = SCREENLOAD_FAILURE;
    	   return;
       }
		
		
       FlightValidationVO flightValidationVO = new FlightValidationVO();
       if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
    	   log.log(Log.FINE, "flightValidationVOs is NULL");

    	   invocationContext.addError(new ErrorVO("mailtracking.defaults.national.noflightdetails"));
    	   invocationContext.target = SCREENLOAD_FAILURE;
       } else if ( flightValidationVOs.size() == 1) {
    	   log.log(Log.FINE, "flightValidationVOs has one VO");
    	   
    	   //code for validation
    	   flightValidationVO = flightValidationVOs.iterator().next();
    	   Collection<ErrorVO> flightErrors = validateFlightRoute(transferDispatchForm,flightValidationVO,logonAttributes);
    	   if(flightErrors != null && flightErrors.size() >0){
    		   invocationContext.addAllError(flightErrors);
    		   invocationContext.target = SCREENLOAD_FAILURE;
    		   return;
    	   }

    	   //
				
    	   try {
    		   for (FlightValidationVO flightValidVO : flightValidationVOs) {
    			   BeanHelper.copyProperties(flightValidationVO,
    					   flightValidVO);
    			   break;
    		   }
    	   } catch (SystemException systemException) {
    		   systemException.getMessage();
    	   }
    	   flightValidationVO.setDirection(OUTBOUND);
           log.log(Log.FINE, "flightValidationVOs ===", flightValidationVO);
		Integer errorFlag = 0;
    	   Collection<String> does=new ArrayList<String>();
    	   String airport = logonAttributes.getAirportCode();
    	   String companyCode = logonAttributes.getCompanyCode();


    	   Collection<DespatchDetailsVO> despatchCollectionVO =  new ArrayList<DespatchDetailsVO>();

    	   //reassign
    	   Collection<ContainerVO> newContainerVOs = new ArrayList<ContainerVO>();	
    	   OperationalFlightVO operationalFlightVO =  new OperationalFlightVO();		
    	   operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	   operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	   operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	   operationalFlightVO.setFlightStatus(flightValidationVO.getFlightStatus());
    	   operationalFlightVO.setAirportCode(logonAttributes.getAirportCode());
    	   operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	   operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	   operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	   operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
    	   operationalFlightVO.setPou(flightValidationVO.getLegDestination());
    	   operationalFlightVO.setDirection(OUTBOUND);

    	   /**
    	    * To check whether flight is already closed.
    	    */
    	   boolean isFlightClosed = false;
    	   try {

    		   isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);

    	   }catch (BusinessDelegateException businessDelegateException) {
    		   errors = handleDelegateException(businessDelegateException);
    	   }		
    	   if (errors != null && errors.size() > 0) {
    		   invocationContext.addAllError(errors);
    		   //reassignForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		   invocationContext.target = SCREENLOAD_FAILURE;
    		   return;
    	   }
    	   if(isFlightClosed){		
    		   log.log(Log.INFO,"<<----Flight is closed --->>");
    		   invocationContext.addError(new ErrorVO("mailtracking.defaults.national.flightclosed"));
    		   invocationContext.target = SCREENLOAD_FAILURE;
    		   return;
    	   }
			
			
    	   try {
    		   newContainerVOs = 
    			   new MailTrackingDefaultsDelegate().findFlightAssignedContainers(operationalFlightVO);

    	   }catch (BusinessDelegateException businessDelegateException) {
    		   errors = handleDelegateException(businessDelegateException);
    	   }
    	   if(newContainerVOs == null || newContainerVOs.size() ==0){
				MailAcceptanceVO mailAcceptanceVO = constructAcceptanceVOFromConsignment(flightValidationVO,transferDispatchForm);	
    		   try{
    			   new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
    		   }catch (BusinessDelegateException businessDelegateException) {
    			   errors = handleDelegateException(businessDelegateException);
    		   }

    	   }
			
			

    	   Integer flag =0;
    	   DespatchDetailsVO detailsVO = new DespatchDetailsVO();
			//transfer from mail arrival screen
			if(transferDispatchForm.getFromScreen().equals(ARR_MAIL_BAG)){
				MailArrivalVO mailArrivalVO = arriveDispatchSession.getMailArrivalVO();
				Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
    	    //The code is added by a-4810 for icrd -20140
				ArrayList<ContainerDetailsVO> CollectionContainerDetailsVOs = (ArrayList<ContainerDetailsVO>) containerDetailsVOs;

				
				String selectedRow = transferDispatchForm.getSelectedcont();
				 String dsnIndex = "";
		         String contIndex = "";
				String[] containerDsns = selectedRow.split("~");
				   contIndex = containerDsns[0];
				   dsnIndex = containerDsns[1];
				   if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){	
					   ContainerDetailsVO containerDtlsVO = CollectionContainerDetailsVOs.get(Integer.parseInt(contIndex));
					   ArrayList<DSNVO> dSNVOs = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();
						DSNVO dsnv1 = dSNVOs.get(Integer.parseInt(dsnIndex));
				
				//
				
				  // The code is commented by a-4810 for icrd -20140
				   //The code is modified to accomodate delivery of despatches form more than one container
				//if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){		
    		  // for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){

    			  // ArrayList<DSNVO> dsnv = (ArrayList<DSNVO>)containerDtlsVO.getDsnVOs();

    			  // DSNVO dsnv1 = dsnv.get(Integer.parseInt(transferDispatchForm.getSelectedcont()));   			

    			   does.add(dsnv1.getDestinationExchangeOffice());
    			   errorFlag=validateDOEs(does,companyCode,airport);

    			   if(errorFlag == 1){
    				   
    				   errors.add(new ErrorVO("mailtracking.defaults.national.cannottransferdsn"));
    				   
    			   } 
                  //added by a-4810 for icrd-20351
    			    if (errors != null && errors.size() > 0) {
    			    	transferDispatchForm.setIstransfer("N");  
    			    	invocationContext.addAllError(errors);
    			    	invocationContext.target = SCREENLOAD_FAILURE;
    			        return;
    			    }
    			   detailsVO.setFlightNumber(containerDtlsVO.getFlightNumber());
    			   detailsVO.setFlightSequenceNumber(containerDtlsVO.getFlightSequenceNumber());
    			   detailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
    			   detailsVO.setDestination(dsnv1.getDestinationExchangeOffice());
    			   detailsVO.setCarrierId(containerDtlsVO.getCarrierId());
    			   detailsVO.setSegmentSerialNumber(containerDtlsVO.getSegmentSerialNumber());
    			   detailsVO.setCompanyCode(containerDtlsVO.getCompanyCode());
    			   detailsVO.setAirportCode(logonAttributes.getAirportCode());
    			   detailsVO.setFlightSequenceNumber(containerDtlsVO.getFlightSequenceNumber());
    			   detailsVO.setContainerType("B");
    			   detailsVO.setContainerNumber("BULK");
    			   detailsVO.setOperationalFlag(dsnv1.getOperationFlag());
    			   detailsVO.setOwnAirlineCode(logonAttributes.getAirportCode());
    			   detailsVO.setAcceptedDate(dsnv1.getAcceptedDate());
    			   detailsVO.setLegSerialNumber(containerDtlsVO.getLegSerialNumber());
    			   detailsVO.setReceivedBags(dsnv1.getReceivedBags());
    			   detailsVO.setReceivedWeight(dsnv1.getReceivedWeight());
    			   detailsVO.setDeliveredBags(dsnv1.getDeliveredBags());
    			   detailsVO.setDeliveredWeight(dsnv1.getDeliveredWeight());
    			   detailsVO.setTransferredPieces(Integer.parseInt(transferDispatchForm.getPieces()));
    			   //detailsVO.setTransferredWeight(Double.parseDouble(transferDispatchForm.getWeight()));
    			   detailsVO.setTransferredWeight(transferDispatchForm.getWeightMeasure());//added by A-7371
    			   detailsVO.setAlreadyTransferredPieces(dsnv1.getTransferredPieces());
    			   detailsVO.setAlreadyTransferredWeight(dsnv1.getTransferredWeight());
    			   detailsVO.setYear(dsnv1.getYear());
    			   detailsVO.setDsn(dsnv1.getDsn());
    			   detailsVO.setDestinationOfficeOfExchange(dsnv1.getDestinationExchangeOffice());
    			   detailsVO.setMailCategoryCode(dsnv1.getMailCategoryCode());
    			   detailsVO.setMailSubclass(dsnv1.getMailSubclass());
    			   detailsVO.setMailClass(dsnv1.getMailClass());
    			   detailsVO.setOriginOfficeOfExchange(dsnv1.getOriginExchangeOffice());
    			   detailsVO.setPaCode(dsnv1.getPaCode());
    			   detailsVO.setUldNumber("BULK");
    			   detailsVO.setConsignmentNumber(dsnv1.getCsgDocNum());
    			   detailsVO.setConsignmentSequenceNumber(dsnv1.getCsgSeqNum());
    			   detailsVO.setConsignmentDate(dsnv1.getConsignmentDate());
    			   detailsVO.setOperationType("I");    
    			   detailsVO.setPaCode(dsnv1.getPaCode());
    			   // detailsVO.setIstransfermail(true);
    			   // detailsVO.setTransferredPieces(dsnv1.getTransferredPieces());
    			   //detailsVO.setTransferredWeight(dsnv1.getTransferredWeight());
    			   // detailsVO.setIstransfermail(true);
    			 //Added by A-4810 as part of bug-fix-icrd-9151.
    			  // detailsVO.setiIsDomesticTransfer(true);
    			  
    			   //added by a-5133 as part of the CR ICRD-19090 starts
    			   detailsVO.setRemarks(transferDispatchForm.getRemarks());
    			  // if((dsnv1.getReceivedBags()!= 0 && dsnv1.getReceivedWeight()!= 0)){ //modified by a-5133 as part of BUG ICRD-25450
    			   if((dsnv1.getReceivedBags()!= 0 && dsnv1.getReceivedWeight().getRoundedSystemValue()!= 0)){ //modified by a-5133 as part of BUG ICRD-25450
        			   detailsVO.setAcceptedBags(Integer.parseInt(transferDispatchForm.getPieces()));
        			   //detailsVO.setAcceptedWeight(Double.parseDouble(transferDispatchForm.getWeight()));
        			   detailsVO.setAcceptedWeight(transferDispatchForm.getWeightMeasure());//added by A-7371
        			}else
        			{
        				errors.add(new ErrorVO("mailtracking.defaults.national.msg.err.CannotUpdateTransferedDetails"));
        			}
    			   detailsVO.setDomesticTransfer(true);
    			   //added by a-5133 as part of the CR ICRD-19090 ends
    		   }
    	  
		    despatchCollectionVO.add(detailsVO);
			}
			//transfer from dsnenquiry
			else if(transferDispatchForm.getFromScreen().equals(DSN_ENQUIRY)){		
				
				despatchCollectionVO = transferDispatchSession.getDespatchDetailsVOs();
				for(DespatchDetailsVO despatchDetailsVO : despatchCollectionVO){
					does.add(despatchDetailsVO.getDestinationOfficeOfExchange());
					errorFlag=validateDOEs(does,companyCode,airport);
					if(errorFlag == 1){
						errors.add(new ErrorVO("mailtracking.defaults.national.cannottransferdsn"));
					} 
					despatchDetailsVO.setCarrierCode(transferDispatchForm.getFlightCarrierCode());
					despatchDetailsVO.setAcceptedBags(Integer.parseInt(transferDispatchForm.getPieces()));
					//despatchDetailsVO.setAcceptedWeight(Double.parseDouble(transferDispatchForm.getWeight()));
					despatchDetailsVO.setAcceptedWeight(transferDispatchForm.getWeightMeasure());//added by A-7371
					despatchDetailsVO.setStatedBags(0);
					//despatchDetailsVO.setStatedWeight(0.0);
					despatchDetailsVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,0.0));//added by A-7371
					despatchDetailsVO.setTransferredPieces(Integer.parseInt(transferDispatchForm.getPieces()));
					despatchDetailsVO.setTransferredWeight(transferDispatchForm.getWeightMeasure());//added by A-7371
					despatchDetailsVO.setDomesticTransfer(true);
					despatchDetailsVO.setRemarks(transferDispatchForm.getRemarks());
					
				}
			}
		    ContainerVO containervo = new  ContainerVO();

		    containervo.setContainerNumber("BULK");
		    containervo.setType("B");
		    containervo.setFlightNumber(transferDispatchForm.getFlightNumber());
		    containervo.setCarrierCode(transferDispatchForm.getFlightCarrierCode());
		    containervo.setCarrierId(flightValidationVO.getFlightCarrierId());
		    int segSerialNumber = populateSegmentSerialNumber(flightValidationVO.getFlightRoute(), transferDispatchForm.getFlightPou(), logonAttributes.getAirportCode());
		    containervo.setSegmentSerialNumber(segSerialNumber);
		    containervo.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		    containervo.setAssignedPort(logonAttributes.getAirportCode());

		    containervo.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		    containervo.setCompanyCode(flightValidationVO.getCompanyCode());
		    containervo.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		    containervo.setBags(Integer.parseInt(transferDispatchForm.getPieces()));
		   // containervo.setWeight(Double.parseDouble(transferDispatchForm.getWeight()));
		    containervo.setWeight(transferDispatchForm.getWeightMeasure());//added by A-7371
		    containervo.setPou(transferDispatchForm.getFlightPou());

		    String printFlag = "N";
		    try {

		    	new MailTrackingDefaultsDelegate().transferDespatches(despatchCollectionVO,containervo);
		    }catch (BusinessDelegateException businessDelegateException) {
		    	errors = handleDelegateException(businessDelegateException);
		    }
		    if (errors != null && errors.size() > 0) {
		    	transferDispatchForm.setIstransfer("N");  
		    	invocationContext.addAllError(errors);
		    	invocationContext.target = SCREENLOAD_FAILURE;
		    	return;
		    }
		    transferDispatchForm.setIstransfer("Y"); 
		    invocationContext.target = SCREENLOAD_SUCCESS;
       }
		 
		
	log.exiting("TransferMailCommand", "execute");
	
   }
	   

   /**
    * Method to validate form.
    * @param mailArrivalForm
    * @return Collection<ErrorVO>
    */
   private Collection<ErrorVO> validateForm(
		   TransferDispatchForm transferDispatchForm) {
	   String flightCarrierCode = transferDispatchForm.getFlightCarrierCode();
	   String flightNumber = transferDispatchForm.getFlightNumber();
	   String depDate = transferDispatchForm.getFlightDate();
	   String pou = transferDispatchForm.getFlightPou();
	   String pieces = transferDispatchForm.getPieces();
	   String weight = transferDispatchForm.getWeight();
	   String remainingPcs = transferDispatchForm.getRemainingPcs() ;
	   String remainingWt = transferDispatchForm.getRemainingWt() ;
	   //String transferCarrier = arrivedispatchForm.getTransferCarrier();

	   Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	   if(flightCarrierCode == null || ("".equals(flightCarrierCode.trim()))){
		   errors.add(new ErrorVO("mailtracking.defaults.national.flightcarriercode.empty"));
	   }
	   if(flightNumber == null || ("".equals(flightNumber.trim()))){
		   errors.add(new ErrorVO("mailtracking.defaults.national.flightnumber.empty"));
	   }
	   if(depDate == null || ("".equals(depDate.trim()))){
		   errors.add(new ErrorVO("mailtracking.defaults.national.depDate.empty"));
	   }
	   if(pou == null || ("".equals(pou.trim()))){
		   errors.add(new ErrorVO("mailtracking.defaults.national.pou.empty"));
	   }
	   if(pieces == null || ("".equals(pieces.trim()))){
		   errors.add(new ErrorVO("mailtracking.defaults.national.transfer.pieces.empty"));
	   }
	   if(weight == null || ("".equals(weight.trim()))){
		   errors.add(new ErrorVO("mailtracking.defaults.national.transfer.weight.empty"));
	   }
	   //Added by A-4810 as part of bug-fix-icrd-9151.
	   if(transferDispatchForm.getFromScreen().equals(ARR_MAIL_BAG)){
		   
		   /**
		    * ICRD-26699_A-4816 : Checking whether the no:of pieces and 
		    * weight to be transferred are within the available range
		    */
	   if((Integer.parseInt(pieces)>Integer.parseInt(transferDispatchForm.getPieceavailable())) || 
			   (( Integer.parseInt(pieces) != 0 ) && ( remainingPcs != null ) && 
					   (Integer.parseInt(pieces)>Integer.parseInt(remainingPcs ) )) ) {
		   errors.add(new ErrorVO("mailtracking.defaults.national.transfer.pieces.greater")); 
	    }
	   if( Double.parseDouble(weight)>Double.parseDouble(transferDispatchForm.getWeightavailable())|| 
			   ( ( Double.parseDouble(weight) != 0 ) && ( remainingWt != null )&& 
					   (Double.parseDouble(weight)>Double.parseDouble(remainingWt) ) ) ) {
		   errors.add(new ErrorVO("mailtracking.defaults.national.transfer.weight.greater")); 
	    }
	   /**
	    * ICRD-26699_A-4816 : Checking whether the no:of pieces and 
	    * weight to be transferred are entered as zero
	    */
	   if( Integer.parseInt(pieces)==0 ) {
		   errors.add(new ErrorVO("mailtracking.defaults.national.transfer.pcs.zero")); 
	   }
	   if( Double.parseDouble(weight)==0 ) {
		   errors.add(new ErrorVO("mailtracking.defaults.national.transfer.weight.zero")); 
	   }
	    
	  }
	   return errors;
   }


   /**
    * Method to create the filter vo for flight validation
    * @param mailArrivalForm
    * @param logonAttributes
    * @return FlightFilterVO
    */
   private FlightFilterVO handleFlightFilterVO(
		   TransferDispatchForm transferDispatchForm,
		   LogonAttributes logonAttributes){

	   FlightFilterVO flightFilterVO = new FlightFilterVO();

	   flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

	   flightFilterVO.setStation(logonAttributes.getAirportCode());
	   flightFilterVO.setDirection(OUTBOUND);
	   flightFilterVO.setActiveAlone(false);
	   flightFilterVO.setAirportCode(logonAttributes.getAirportCode());
	   flightFilterVO.setStringFlightDate(transferDispatchForm.getFlightDate());
	   LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
	   flightFilterVO.setFlightDate(date.setDate(transferDispatchForm.getFlightDate()));
	   return flightFilterVO;
   }

   /**
    * validateDOEs will validate the DOE's of Mailbags/Dispatches to know whether 
    * the Current Airport is matching with the DOE for Deliver.
    * @param does
    * @param companyCode
    * @param airport
    * @return
    */
   private Integer validateDOEs(Collection<String> does,String companyCode,String airport){
	   Collection<ArrayList<String>> groupedOECityArpCodes = null;
	   Integer errorFlag = 0;
	   if(does != null && does.size()!=0){
		   try {
			   /*
			    * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
			    * the inner collection contains the values in the order :
			    * 0.OFFICE OF EXCHANGE
			    * 1.CITY NEAR TO OE
			    * 2.NEAREST AIRPORT TO CITY
			    */
			   groupedOECityArpCodes = 
				   new MailTrackingDefaultsDelegate().findCityAndAirportForOE(companyCode, does);
		   }catch (BusinessDelegateException businessDelegateException) {
			   Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
			   log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
		   }
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
	   }
	   return errorFlag;
   }   

   private int populateSegmentSerialNumber(String flightRoute,String pou,String pol){
	   String [] flightSegment = flightRoute.split("-");
	   List<String> flightSegmentS = new ArrayList<String>();
	   for(int i=0; i<flightSegment.length-1;i++){

		   for(int j =i+1; j<flightSegment.length;j++){
			   flightSegmentS.add(flightSegment[i].concat("~").concat(flightSegment[j]));

		   }

	   }
	   String currentFlightSegment = pol.concat("~").concat(pou);

	   for(int i =0;i<flightSegmentS.size();i++){

		   if(currentFlightSegment.equals(flightSegmentS.get(i))){
			   return i+1;

		   }

	   }
	   return 0;
   }

   private MailAcceptanceVO constructAcceptanceVOFromConsignment(
			FlightValidationVO flightValidationVO, TransferDispatchForm transferDispatchForm
   ) {
	   MailAcceptanceVO newMailAcceptanceVO = new MailAcceptanceVO();
	   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

	   newMailAcceptanceVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	   newMailAcceptanceVO.setCompanyCode(flightValidationVO.getCompanyCode());
	   newMailAcceptanceVO.setFlightCarrierCode(flightValidationVO.getCarrierCode());
	   newMailAcceptanceVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(transferDispatchForm.getFlightDate()));
	   newMailAcceptanceVO.setFlightNumber(flightValidationVO.getFlightNumber());
	   newMailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	   newMailAcceptanceVO.setPol(logonAttributes.getAirportCode());
	   newMailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	   newMailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	   newMailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
	   newMailAcceptanceVO.setContainerDetails(constructContainerDetails( logonAttributes, flightValidationVO,transferDispatchForm ));	


	   return newMailAcceptanceVO;
   }

   private Collection<ContainerDetailsVO> constructContainerDetails( LogonAttributes logonAttributes,FlightValidationVO flightValidationVO,TransferDispatchForm transferDispatchForm)
   {
	   ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
	   containerDetailsVO.setContainerNumber("BULK");
	   containerDetailsVO.setContainerType("B");
	   containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
	   containerDetailsVO.setAssignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
	   containerDetailsVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	   containerDetailsVO.setCarrierCode(flightValidationVO.getCarrierCode());
	   containerDetailsVO.setCompanyCode(flightValidationVO.getCompanyCode());
	   containerDetailsVO.setFlightNumber(flightValidationVO.getFlightNumber());
	   containerDetailsVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	   containerDetailsVO.setPol(logonAttributes.getAirportCode());
	   containerDetailsVO.setPou(transferDispatchForm.getFlightPou());
	   containerDetailsVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	   containerDetailsVO.setOperationFlag("I");
	   containerDetailsVO.setContainerOperationFlag("I");

	   int segSerNumber = populateSegmentSerialNumber(flightValidationVO.getFlightRoute(), transferDispatchForm.getFlightPou(), logonAttributes.getAirportCode());
	   containerDetailsVO.setSegmentSerialNumber(segSerNumber);

	   Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
	   containerDetailsVOs.add(containerDetailsVO);
	   return containerDetailsVOs;

   }

   /**
    * to check whether the segment specified is present in flight route
    * @param reassignDispatchForm
    * @param flightValidationVO
    * @param logonAttributes
    * @return Collection<ErrorVO>
    */
   private Collection<ErrorVO> validateFlightRoute(
		   TransferDispatchForm transferDispatchForm,
		   FlightValidationVO flightValidationVO,
		   LogonAttributes logonAttributes) {
	   Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	   String pol = logonAttributes.getStationCode();
	   boolean segmentPresent = false;
	   String [] flightSegment = flightValidationVO.getFlightRoute().split("-");
	   List<String> flightSegmentS = new ArrayList<String>();
	   for(int i=0; i<flightSegment.length-1;i++){

		   for(int j =i+1; j<flightSegment.length;j++){
			   flightSegmentS.add(flightSegment[i].concat("~").concat(flightSegment[j]));

		   }

	   }
	   String currentSegment = pol.concat("~").concat(transferDispatchForm.getFlightPou().toUpperCase());

	   for(int i =0;i<flightSegmentS.size();i++){

		   if(currentSegment.equals(flightSegmentS.get(i))){
			   return null;
		   }



	   }
	   errors.add(new ErrorVO("mailtracking.defaults.national.transfer.error.segmentnotinroute"));
	   return errors;


   }

   private AirportValidationVO validateairport(String companyCode,String airportCode){
	   AirportValidationVO   airportValidationVO = null;
	   try{
		   airportValidationVO= new AreaDelegate().validateAirportCode(companyCode, airportCode);
	   }catch(BusinessDelegateException businessDelegateException){
		   log.log(Log.FINE,  "BusinessDelegateException");
	   }
	   return airportValidationVO;
   }

}
