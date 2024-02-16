/*
 * DeliverMailCommand.java Created on Jan 09, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8061
 *
 */
public class DeliverMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET_FAILURE = "deliver_success";
   private static final String TARGET_SUCCESS = "deliver_failure";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";	
   private static final String CONST_DELIVERED_FLG = "DLV";
   private static final String CONST_ARRIVED_FLG = "ARR";

   
  
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeliverMailCommand","execute");
    	  
    	MailBagEnquiryForm mailBagEnquiryForm = 
    		(MailBagEnquiryForm)invocationContext.screenModel;
    	MailBagEnquirySession mailBagEnquirySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

    	Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
    	Collection<MailbagVO> selectedMailbagVOs = new ArrayList<MailbagVO>();
    	Collection<MailArrivalVO> mailArrivalVOs = null;
    	Collection<String> does=new ArrayList<String>();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
				new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		Integer errorFlag = 0;

		
		String airport = logonAttributes.getAirportCode();
		String companyCode = logonAttributes.getCompanyCode();
		
	  	// Getting the selected MailBags
    	String[] selectedRows = mailBagEnquiryForm.getSubCheck();  
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
    	
    	

		// validation to check whether any of the selected mailbags are delivered
    	
     	for (MailbagVO selectedvo : selectedMailbagVOs) {
    		
     		if(CONST_DELIVERED_FLG.equals(selectedvo.getLatestStatus())) {
     			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.mailbagalreadydelivered"));			
     			invocationContext.target = TARGET_FAILURE;
     			return;
     		}
     		
    		if(!CONST_ARRIVED_FLG.equals(selectedvo.getLatestStatus())) {
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannotdeliveratthisport",new Object[]{logonAttributes.getAirportCode()}));		
     			invocationContext.target = TARGET_FAILURE;
     			return;
     		}

     	}

     	
     	//validate DOes 
		for(MailbagVO selectedvo : selectedMailbagVOs) {
	 		does.add(selectedvo.getDoe());
	 	}
		errorFlag=validateDOEs(does,companyCode,airport);
	
		if(1==errorFlag){
			
		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.cannotdeliveratthisport",new Object[]{logonAttributes.getAirportCode()}));
		invocationContext.target = TARGET_FAILURE;
		return;
		} 

		//validate flight closure
		
		String fltNo = "";
		String carrierCode = "";
		String scannedPort = "";
		long fltseqNo = 0;
		int carrierid = 0;
		LocalDate fltDate = null;
		int legSerialNum =0;

		for (MailbagVO selectedvo : selectedMailbagVOs) {
			
			fltNo = (selectedvo.getFlightNumber() != null) ? selectedvo.getFlightNumber() : "";
			fltseqNo = selectedvo.getFlightSequenceNumber();
			carrierid = selectedvo.getCarrierId();
			carrierCode = selectedvo.getCarrierCode();
			scannedPort = selectedvo.getScannedPort();
			fltDate = selectedvo.getFlightDate();
			legSerialNum = selectedvo.getLegSerialNumber();
		
				// Validating Flight to obtain the LegSerialNumber
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				flightFilterVO.setFlightNumber(fltNo);
				flightFilterVO.setStation(scannedPort);
				flightFilterVO.setActiveAlone(false);
				flightFilterVO.setStringFlightDate(String.valueOf(fltDate));
				flightFilterVO.setFlightDate(fltDate);
				flightFilterVO.setCarrierCode(carrierCode);
				flightFilterVO.setFlightCarrierId(carrierid);
				flightFilterVO.setFlightSequenceNumber(fltseqNo);
				Collection<FlightValidationVO> flightValidationVOs = null;
				FlightValidationVO flightValidationVO = new FlightValidationVO();
				

				try {
					log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
					flightValidationVOs = mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}

				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					log.log(Log.FINE, "SIZE ------------> ", flightValidationVOs.size());
					if(flightValidationVOs.size()>1){		
						try {
							//Modified by A-7540 for ICRD-322594 starts
							for (FlightValidationVO flightValidVO : flightValidationVOs) {
								if(legSerialNum == flightValidVO.getLegSerialNumber()){
								BeanHelper.copyProperties(flightValidationVO,flightValidVO);
								break;
								}
							}
						} catch (SystemException systemException) {
							systemException.getMessage();
						}
						
						/*
						 * to set POL
						 */
						String route = flightValidationVO.getFlightRoute();
						String[] stations = route.split("-");
						String pol = "";
						if(route != null && route.length() > 0){
							for(int index=stations.length-1;index >= 0;index--){
								if(stations[index].equals(logonAttributes.getAirportCode())){
									pol = stations[index-1];
								}
							}
						}
						selectedvo.setPol(pol);
						
					//Modified by A-7540 for ICRD-322594 ends
					}
					else{
						flightValidationVO = ((ArrayList<FlightValidationVO>)flightValidationVOs).get(0);
						selectedvo.setPol(flightValidationVO.getLegOrigin());
					}
				
				log.log(Log.FINE, "flightValidationVO ------------> ", flightValidationVO);
				// Validating Flight Closure
				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
				operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				operationalFlightVO.setPou(selectedvo.getPou());
				
				if (FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus())) {
					
					for (MailbagVO selectedMailbagVO : selectedMailbagVOs) {
						operationalFlightVO.setLegSerialNumber(selectedMailbagVO.getLegSerialNumber());
					}
				} else{
					operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					
					selectedvo.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				}

				try {
					isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForInboundOperations(
							operationalFlightVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				log.log(Log.FINE, "MailbagEnquiry>OffloadMailCommand->isFlightClosed->", isFlightClosed);
				
				if (isFlightClosed) {
					Object[] obj = {new StringBuilder(carrierCode).append("").append(fltNo).append(" on ").append(fltDate.toDisplayDateOnlyFormat()).toString()};
					invocationContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.flightclosed", 
							obj));
					invocationContext.target = TARGET_FAILURE;
					return;
				}
		    }	
		}

	//deliver mail bag(s)

		try {
			mailArrivalVOs=makeMailArrivalVOsForDelivery(selectedMailbagVOs,logonAttributes);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		try {
			new MailTrackingDefaultsDelegate().saveScannedDeliverMails(mailArrivalVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		} 

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		mailBagEnquiryForm.setSubCheck(null);
    	invocationContext.target = TARGET_SUCCESS;
       	
    	log.exiting("DeliverMailCommand","execute");
    	
    }
       
    
    /**
     * 
     * @param selectedMailbagVOs
     * @param logonAttributes
     * @return
     * @throws BusinessDelegateException
     */
 
	private Collection<MailArrivalVO> makeMailArrivalVOsForDelivery(
			Collection<MailbagVO> selectedMailbagVOs,LogonAttributes logonAttributes) throws BusinessDelegateException{
		log.entering("DeliverMailCommand", "makeMailArrivalVOsForDelivery");
		
		Collection<MailbagVO> mailbagVOsToSave =selectedMailbagVOs;
		Collection<ScannedMailDetailsVO> scannedMailsForDelivery = new ArrayList<ScannedMailDetailsVO>();
		Collection<MailArrivalVO> mailArrivalVOsForDelivery = null;
		Map<String,ScannedMailDetailsVO> scannedMailsMapForDelivery = new HashMap<String, ScannedMailDetailsVO>();
		String deliverMapKey = null;
		ScannedMailDetailsVO scannedMailDetailsVOForDelivery = null;
		if(mailbagVOsToSave != null && mailbagVOsToSave.size() >0){
			for(MailbagVO mailbagVOToSave : mailbagVOsToSave){
				deliverMapKey = new StringBuilder(mailbagVOToSave.getCompanyCode())
				.append(mailbagVOToSave.getCarrierCode())
				.append(mailbagVOToSave.getFlightNumber())
				.append(mailbagVOToSave.getFlightDate())
				.append(mailbagVOToSave.getCarrierId())
				.append(mailbagVOToSave.getFlightSequenceNumber())
				.append(mailbagVOToSave.getLegSerialNumber())
				.append(mailbagVOToSave.getPou())
				.append(mailbagVOToSave.getContainerNumber())
				.toString();
				if(!scannedMailsMapForDelivery.containsKey(deliverMapKey)){
					scannedMailDetailsVOForDelivery = new ScannedMailDetailsVO();
					scannedMailDetailsVOForDelivery.setCompanyCode(mailbagVOToSave.getCompanyCode());
					scannedMailDetailsVOForDelivery.setFlightNumber(mailbagVOToSave.getFlightNumber());
					scannedMailDetailsVOForDelivery.setFlightDate(mailbagVOToSave.getFlightDate());
					scannedMailDetailsVOForDelivery.setFlightSequenceNumber(mailbagVOToSave.getFlightSequenceNumber());
					scannedMailDetailsVOForDelivery.setLegSerialNumber(mailbagVOToSave.getLegSerialNumber());
					scannedMailDetailsVOForDelivery.setCarrierId(mailbagVOToSave.getCarrierId());
					scannedMailDetailsVOForDelivery.setCarrierCode(mailbagVOToSave.getCarrierCode());
					scannedMailDetailsVOForDelivery.setContainerNumber(mailbagVOToSave.getContainerNumber());
					scannedMailDetailsVOForDelivery.setContainerType(mailbagVOToSave.getContainerType());					
					scannedMailDetailsVOForDelivery.setPol(mailbagVOToSave.getPol());
					scannedMailDetailsVOForDelivery.setPou(mailbagVOToSave.getPou());
					scannedMailDetailsVOForDelivery.setSegmentSerialNumber(mailbagVOToSave.getSegmentSerialNumber());
					scannedMailDetailsVOForDelivery.setMailDetails(new ArrayList<MailbagVO>());
					scannedMailDetailsVOForDelivery.getMailDetails().add(mailbagVOToSave);
					scannedMailsMapForDelivery.put(deliverMapKey, scannedMailDetailsVOForDelivery);
					
				}else{
					scannedMailDetailsVOForDelivery = scannedMailsMapForDelivery.get(deliverMapKey);
					scannedMailDetailsVOForDelivery.getMailDetails().add(mailbagVOToSave);
				}
			}
		}
		scannedMailsForDelivery = scannedMailsMapForDelivery.values();		
		if(scannedMailsForDelivery != null && scannedMailsForDelivery.size() > 0){
			mailArrivalVOsForDelivery = new ArrayList<MailArrivalVO>();
			MailArrivalVO mailArrivalVOForDelivery = null;
		
			for(ScannedMailDetailsVO mailDetailsVO : scannedMailsForDelivery){
				mailArrivalVOForDelivery = makeMailArrivalVO(mailDetailsVO, logonAttributes);
				mailArrivalVOsForDelivery.add(mailArrivalVOForDelivery);
			}

		}
		log.exiting("MailController", "makeMailArrivalVOsForDelivery");	
		return mailArrivalVOsForDelivery;
	}
	
    
 
	/**
	 * This method constructs the MailArrivalVO for a particular session
	 * @param scannedMailDetailsVO
	 * @param logonAttributes
	 * @return
	 * @throws BusinessDelegateException 
	 */
	private MailArrivalVO makeMailArrivalVO (
			ScannedMailDetailsVO scannedMailDetailsVO,LogonAttributes logonAttributes) throws BusinessDelegateException {
		log.entering("makeMailArrivalVO","execute");
		
		MailArrivalVO mailArrivalVO = new MailArrivalVO();
		mailArrivalVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailArrivalVO.setAirportCode(logonAttributes.getAirportCode());
		mailArrivalVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		mailArrivalVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		mailArrivalVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		mailArrivalVO.setFlightCarrierCode(scannedMailDetailsVO.getCarrierCode());
		mailArrivalVO.setArrivalDate(scannedMailDetailsVO.getFlightDate());
		mailArrivalVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		mailArrivalVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailArrivalVO.setArrivedUser(logonAttributes.getUserId().toUpperCase());
		mailArrivalVO.setScanDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
		
		mailArrivalVO.setScanned(true);

		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
		ContainerDetailsVO containerDetailsVO = scannedMailDetailsVO.getValidatedContainer();
		if(containerDetailsVO == null) {
			containerDetailsVO = new ContainerDetailsVO();
		}
	
		containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		containerDetailsVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
		containerDetailsVO.setFlightDate(scannedMailDetailsVO.getFlightDate());
		containerDetailsVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		containerDetailsVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
		containerDetailsVO.setPou(logonAttributes.getAirportCode());
		containerDetailsVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
		containerDetailsVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
		containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);//to do
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
		containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		
	
			containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
			if(MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())){
				containerDetailsVO.setContainerNumber(constructBulkULDNumber(logonAttributes.getAirportCode()));
			}
		
		
		if(containerDetailsVO.getPol() == null){
			containerDetailsVO.setDestination(logonAttributes.getAirportCode());
			containerDetailsVO.setPol(scannedMailDetailsVO.getPol());
			
			
			MailbagVO mailbagVO =null;
			 if (scannedMailDetailsVO.getMailDetails() != null) {
					mailbagVO = scannedMailDetailsVO.getMailDetails()
							.iterator().next();
			 }
			 MailTrackingDefaultsDelegate defaultsDelegate = new MailTrackingDefaultsDelegate();
			ArrayList<MailbagHistoryVO>  mailhistories = new  ArrayList<MailbagHistoryVO>();
			 mailhistories =(ArrayList<MailbagHistoryVO>) defaultsDelegate.findMailbagHistories(scannedMailDetailsVO.getCompanyCode(),mailbagVO.getMailbagId(),mailbagVO.getMailSequenceNumber());
			 if(mailhistories!=null&& mailhistories.size()>0){
				 for(MailbagHistoryVO mailbaghistoryvo :mailhistories ){
					if (mailbaghistoryvo.getFlightNumber()!=null && mailbaghistoryvo.getFlightNumber().equals(
							mailbagVO.getFlightNumber())
							&& mailbaghistoryvo.getFlightSequenceNumber() == mailbagVO
									.getFlightSequenceNumber()
							&& (MailConstantsVO.MAIL_STATUS_ACCEPTED
									.equals(mailbaghistoryvo
											.getMailStatus()) || MailConstantsVO.MAIL_STATUS_ASSIGNED
											.equals(mailbaghistoryvo
													.getMailStatus())|| MailConstantsVO.MAIL_STATUS_TRANSFERRED
													.equals(mailbaghistoryvo
															.getMailStatus()) )) {    
						containerDetailsVO.setPol(mailbaghistoryvo.getScannedPort());
						        
					 }
				 }

		}
			 
		}
		
			
		Collection<MailbagVO> mailbagArriveVOs = scannedMailDetailsVO.getMailDetails();
		if(mailbagArriveVOs != null && mailbagArriveVOs.size() > 0){
			for(MailbagVO mailbagToArrive : mailbagArriveVOs){
				mailbagToArrive.setCompanyCode(logonAttributes.getCompanyCode());
				mailbagToArrive.setCarrierCode(containerDetailsVO.getCarrierCode());
				mailbagToArrive.setCarrierId(containerDetailsVO.getCarrierId());
				mailbagToArrive.setFlightNumber(containerDetailsVO.getFlightNumber());
				mailbagToArrive.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				mailbagToArrive.setFlightDate(containerDetailsVO.getFlightDate());
				mailbagToArrive.setContainerNumber(containerDetailsVO.getContainerNumber());
				mailbagToArrive.setContainerType(containerDetailsVO.getContainerType());
				if(MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())){
					mailbagToArrive.setUldNumber(containerDetailsVO.getContainerNumber());
				}else{
					mailbagToArrive.setUldNumber(constructBulkULDNumber(containerDetailsVO.getPou()));					
				}
				mailbagToArrive.setPou(containerDetailsVO.getPou());
				mailbagToArrive.setScannedPort(logonAttributes.getAirportCode());
				mailbagToArrive.setScannedUser(logonAttributes.getUserId().toUpperCase());
				//mailbagToArrive.setArrivedFlag(MailConstantsVO.FLAG_YES);
				mailbagToArrive.setDeliveredFlag(MailConstantsVO.FLAG_YES);//to do
				mailbagToArrive.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
				if(mailbagToArrive.getSegmentSerialNumber() > 0){
					mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					
						mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_DELIVERED);
				
				}else{
					mailbagToArrive.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
					mailbagToArrive.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
					mailbagToArrive.setAcceptanceFlag(MailConstantsVO.FLAG_NO);
				}
			}
		}

		containerDetailsVO.setMailDetails(mailbagArriveVOs);
		HashMap<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
		if (mailbagArriveVOs != null && mailbagArriveVOs.size() > 0) {
			for (MailbagVO mailbgVO : mailbagArriveVOs) {
				int numBags = 0;
				double bagWgt = 0;
				String outerpk = mailbgVO.getOoe()+mailbgVO.getDoe()
				+(mailbgVO.getMailSubclass())
				+ mailbgVO.getMailCategoryCode()
				+mailbgVO.getDespatchSerialNumber()+mailbgVO.getYear();
				if(dsnMap.get(outerpk) == null){
					DSNVO dsnVO = new DSNVO();
					dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
					dsnVO.setDsn(mailbgVO.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailbgVO.getOoe());
					dsnVO.setDestinationExchangeOffice(mailbgVO.getDoe());
					dsnVO.setMailClass(mailbgVO.getMailSubclass().substring(0,1));
					dsnVO.setMailSubclass(mailbgVO.getMailSubclass());
					dsnVO.setMailCategoryCode(mailbgVO.getMailCategoryCode());
					dsnVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
					dsnVO.setYear(mailbgVO.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
					for(MailbagVO innerVO : mailbagArriveVOs){
						String innerpk = innerVO.getOoe()+innerVO.getDoe()
						+(innerVO.getMailSubclass())
						+ innerVO.getMailCategoryCode()
						+innerVO.getDespatchSerialNumber()+innerVO.getYear();
						if(outerpk.equals(innerpk)){
							numBags = numBags + 1;
							//bagWgt = bagWgt + innerVO.getWeight();
							bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added by A-7371
						}
					}
					dsnVO.setReceivedBags(numBags);
					//dsnVO.setReceivedWeight(bagWgt);
					dsnVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
					dsnMap.put(outerpk,dsnVO);
					numBags = 0;
					bagWgt = 0;
				}
			}
		}
		Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
		int totBags = 0;
		double totWgt = 0;
		for(String key:dsnMap.keySet()){
			DSNVO dsnVO = dsnMap.get(key);
			totBags = totBags + dsnVO.getReceivedBags();
			//totWgt = totWgt + dsnVO.getReceivedWeight();
			totWgt = totWgt + dsnVO.getReceivedWeight().getRoundedSystemValue();//added by A-7371
			newDSNVOs.add(dsnVO);
		}
		containerDetailsVO.setMailDetails(mailbagArriveVOs);
		containerDetailsVO.setDsnVOs(newDSNVOs);
		containerDetailsVO.setReceivedBags(totBags);
		//containerDetailsVO.setReceivedWeight(totWgt);
		containerDetailsVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,totWgt));//added by A-7371
		containerDetailsVOs.add(containerDetailsVO);
 
        	mailArrivalVO.setPartialDelivery(false);
        	
        
		mailArrivalVO.setContainerDetails(containerDetailsVOs);

		log.exiting("makeMailArrivalVO","execute");
		return mailArrivalVO;
	}

	/**
	 * @param pou
	 * @return
	 */
	private String constructBulkULDNumber(String airport) {
		/*
		 * This "airport" can be the POU / Destination
		 */
		String bulkULDNumber = "";
		if(airport != null && airport.trim().length() > 0) {
			bulkULDNumber =  new StringBuilder().append(MailConstantsVO.CONST_BULK).append(
					MailConstantsVO.SEPARATOR).append(airport).toString();
		}
		return bulkULDNumber;
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
 								doe.equals(cityAndArpForOE.get(0)) &&! 
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
}
