/*
 * MailTrackingDefaultsServiceImpl.java Created on 20-03-2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.errorhandling;
import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
//import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailtrackingTxErrorReferenceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.mail.operations.MailUploadController;
import com.ibsplc.icargo.business.mail.operations.vo.MailWebserviceVO;
//import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;


import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.tx.txmonitor.processor.AbstractErrorProcessor;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailtrackigMessagePostProcessor extends AbstractErrorProcessor {
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.xibase.saveMailUploadDetailsFromJobserver.framework.event.action.ActionBuilder#doAction
	 * (java.lang.String[], java.lang.String[])
	 */
	
	public void saveMailUploadDetails(Collection<MailUploadVO> mailBagVOs, String scanningPort) throws 
	SystemException {

		if (mailBagVOs != null && mailBagVOs.size() > 0 && "MLD".equalsIgnoreCase(
				mailBagVOs.iterator().next().getMailSource())) {
			return;
		}

		String DEST_FLT = "-1";
		String OUTBOUND = "EXP";
		log.log(Log.FINE, "Inside Processor calll...................................");
		MailtrackingTxErrorReferenceVO errorVO = new MailtrackingTxErrorReferenceVO();
		
		for (MailUploadVO mailbag : mailBagVOs) {
			errorVO.setContainer(mailbag.getContainerNumber());

			if (!DEST_FLT.equals(mailbag.getFlightNumber())) {
				errorVO.setFlightnumber(mailbag.getFlightNumber());
		}
			
			errorVO.setCarrierCode(mailbag.getCarrierCode());
			errorVO.setFunction(mailbag.getScanType());
			
			if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbag.getScanType()) && mailbag.isDeliverd()) {
				errorVO.setFunction(MailConstantsVO.MAIL_STATUS_DELIVERED);     
			}
			
			if (OUTBOUND.equals(mailbag.getScanType())) {
			errorVO.setFunction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		}
		
			if (mailbag.getFlightDate() != null) {
				errorVO.setFlightDate(mailbag.getFlightDate().toDisplayDateOnlyFormat());
			}
			//LocalDate scanDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			//scanDate.setDateAndTime(mailbag.getDateTime());
			 LocalDate scanDates = new LocalDate(mailbag.getScannedPort(),Location.ARP,true);
				errorVO.setScannedDateAndTime(scanDates.toDisplayFormat());        
			
			if (mailbag.getContainerNumber() != null) {
		errorVO.setContainer(mailbag.getContainerNumber());
			}
			
			if (!DEST_FLT.equals(mailbag.getFlightNumber())) {
			errorVO.setFlightnumber(mailbag.getFlightNumber());      
			}
			errorVO.setCarrierCode(mailbag.getCarrierCode());
			
			if (mailbag.getMailTag() != null && mailbag.getMailTag().trim().length() == 29) {
			if(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(mailbag.getScanType()) || MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag.getScanType()) )
			{
				errorVO.setContainer(mailbag.getToContainer());  
			}
			
		errorVO.setMailbagId(mailbag.getMailTag());
			} else if (mailbag.getMailTag() != null && mailbag.getMailTag().trim().length() != 0) {
				errorVO.setContainer(mailbag.getMailTag());	
			}
			
			if (mailbag.getFlightDate() != null) {
				errorVO.setFlightDate(mailbag.getFlightDate().toDisplayDateOnlyFormat());
			}
			log.log(Log.FINE, "ScannedPort", mailbag.getScannedPort());
			
			if (mailbag.getScannedPort() != null) {
				errorVO.setAirportCode(mailbag.getScannedPort());
			}
			log.log(Log.FINE, "ScannedPort to set", errorVO.getAirportCode());
			//Added by A-5945 for ICRD-113473 starts
			if(mailbag.getFromCarrierCode()!=null&&mailbag.getFromCarrierCode().trim().length()>0 ){
				errorVO.setTransferCarrier(mailbag.getFromCarrierCode().toUpperCase())	;
			}
			if(mailbag.getPaCode()!=null&&mailbag.getPaCode().trim().length()>0 &&"Y".equals(mailbag.getPaCode())){
				 if(errorVO.getContainer()!=null &&errorVO.getContainer().trim().length()>0){
				 errorVO.setContainer(errorVO.getContainer()+"(SB)");
				 }
				 else if(mailbag.getContainerNumber() != null && mailbag.getContainerNumber().trim().length()>0){
				 errorVO.setContainer(mailbag.getContainerNumber()+"(SB)");
				 }
			 }
			//Added by A-5945 for ICRD-113473 ends
		}
		log.log(Log.FINE, "Vo to be saved", errorVO);
		this.setTxErrorReferenceVO(errorVO);
		
		log.log(Log.FINE, "Inssside Processor calll...................................");
	}
	
	
	public void saveMailDetailsFromJob(Collection<MailUploadVO> mailBagVOs, String scanningPort) throws 
	SystemException {
		log.log(Log.FINE, "Inssside Processor calll saveMailDetailsFromJob...................................");
		saveMailUploadDetails(mailBagVOs,scanningPort);
	}
	
	 public void performMailOperationForGHA(
				Collection<MailWebserviceVO> webServicesVos, String scanningPort)
	    throws SystemException
	  {

		 MailWebserviceVO mailWebserviceVO = webServicesVos.iterator().next();

		 String destFlt = "-1";
		 String outbound = "EXP";
		 log.log(Log.FINE, "Inside Processor call........................");
		 MailtrackingTxErrorReferenceVO errorVO = new MailtrackingTxErrorReferenceVO();
			 errorVO.setContainer(mailWebserviceVO.getContainerNumber());
			 if (!destFlt.equals(mailWebserviceVO.getFlightNumber())) {
				 errorVO.setFlightnumber(mailWebserviceVO.getFlightNumber());
			 }
			 errorVO.setCarrierCode(mailWebserviceVO.getCarrierCode());
			 errorVO.setFunction(mailWebserviceVO.getScanType());
			 if (outbound.equals(mailWebserviceVO.getScanType())) {
				 errorVO.setFunction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			 }
			 if (mailWebserviceVO.getFlightDate() != null) {
				 errorVO.setFlightDate(mailWebserviceVO.getFlightDate().toDisplayDateOnlyFormat());
			 }
			 LocalDate scanDates = new LocalDate(mailWebserviceVO.getScanningPort(),Location.ARP,true);
				errorVO.setScannedDateAndTime(scanDates.toDisplayFormat());        
			 if (mailWebserviceVO.getContainerNumber() != null) {
				 errorVO.setContainer(mailWebserviceVO.getContainerNumber());
			 }
			 if (!destFlt.equals(mailWebserviceVO.getFlightNumber())) {
				 errorVO.setFlightnumber(mailWebserviceVO.getFlightNumber());      
			 }
			 errorVO.setCarrierCode(mailWebserviceVO.getCarrierCode());
			 if (mailWebserviceVO.getMailBagId() != null && mailWebserviceVO.getMailBagId().trim().length() == 29) {
				 if(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(mailWebserviceVO.getScanType()) || MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailWebserviceVO.getScanType()) )
		 {
					 errorVO.setContainer(mailWebserviceVO.getToContainer());  
				 }

				 errorVO.setMailbagId(mailWebserviceVO.getMailBagId());
				  } else if (mailWebserviceVO.getMailBagId() != null && mailWebserviceVO.getMailBagId().trim().length() != 0) {
				 errorVO.setContainer(mailWebserviceVO.getContainerNumber());	//modified for icrd-236825
				 errorVO.setMailbagId(mailWebserviceVO.getMailBagId());
			 }  else{
				 log.log(Log.FINE, "Inside else");
			 }
			 
			if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailWebserviceVO.getScanType())
					&& mailWebserviceVO.getMailBagId()!=null && mailWebserviceVO.getMailBagId().length()!=29 && mailWebserviceVO.getMailBagId().length()!=12){
					
							errorVO.setContainer(mailWebserviceVO.getMailBagId());	//modified for icrd-236825
							errorVO.setMailbagId("");
										
			}
			 if (mailWebserviceVO.getFlightDate() != null) {
				 errorVO.setFlightDate(mailWebserviceVO.getFlightDate().toDisplayDateOnlyFormat());
			 }
			 log.log(Log.FINE, "ScannedPort is ", mailWebserviceVO.getScanningPort());
				 errorVO.setAirportCode(mailWebserviceVO.getScanningPort());
			 
			 log.log(Log.FINE, "ScannedPort to set is", errorVO.getAirportCode());
			 if(mailWebserviceVO.getCarrierCode()!=null&&mailWebserviceVO.getCarrierCode().trim().length()>0 ){
				 errorVO.setTransferCarrier(mailWebserviceVO.getCarrierCode().toUpperCase())	;
			 }
		 log.log(Log.FINE, "Vo to be saved is ", errorVO);
		 this.setTxErrorReferenceVO(errorVO);
	}
	
	 /**
	 * @author A-8236
	 * @param mailBagVOs
	 * @param scanningPort
	 * @throws SystemException
	 */
	public void saveMailUploadDetailsForAndroid(MailUploadVO mailbag, String scanningPort) throws 
	 SystemException {


		 String DEST_FLT = "-1";
		 String OUTBOUND = "EXP";
		 log.log(Log.FINE, "Inside Processor calll...................................");
		 MailtrackingTxErrorReferenceVO errorVO = new MailtrackingTxErrorReferenceVO();
		 if(mailbag.isRestrictErrorLogging()){
		 		return;
		 	}

			 errorVO.setContainer(mailbag.getContainerNumber());

			 if (!DEST_FLT.equals(mailbag.getFlightNumber())) {
				 errorVO.setFlightnumber(mailbag.getFlightNumber());
			 }

			 errorVO.setCarrierCode(mailbag.getCarrierCode());
			 errorVO.setFunction(mailbag.getScanType());

			 if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbag.getScanType()) && mailbag.isDeliverd()) {
				 errorVO.setFunction(MailConstantsVO.MAIL_STATUS_DELIVERED);     
			 }

			 if (OUTBOUND.equals(mailbag.getScanType())) {
				 errorVO.setFunction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			 }

			 if (mailbag.getFlightDate() != null) {
				 errorVO.setFlightDate(mailbag.getFlightDate().toDisplayDateOnlyFormat());
			 }
			 //LocalDate scanDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			 //scanDate.setDateAndTime(mailbag.getDateTime());
			 LocalDate scanDates = new LocalDate(mailbag.getScannedPort(),Location.ARP,true);
				errorVO.setScannedDateAndTime(scanDates.toDisplayFormat());        

			 if (mailbag.getContainerNumber() != null) {
				 errorVO.setContainer(mailbag.getContainerNumber());
			 }

			 if (!DEST_FLT.equals(mailbag.getFlightNumber())) {
				 errorVO.setFlightnumber(mailbag.getFlightNumber());      
			 }
			 errorVO.setCarrierCode(mailbag.getCarrierCode());

			 if (mailbag.getMailTag() != null && mailbag.getMailTag().trim().length() == 29) {
				 if(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(mailbag.getScanType()) || MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag.getScanType()) )
				 {
					 errorVO.setContainer(mailbag.getToContainer());  
				 }

				 errorVO.setMailbagId(mailbag.getMailTag());
			 } else if (mailbag.getMailTag() != null && mailbag.getMailTag().trim().length() != 0) {
				 errorVO.setContainer(mailbag.getContainerNumber());	//modified for icrd-236825
				 errorVO.setMailbagId(mailbag.getMailTag());
			 }

			//Added by A-7540 for IASCB-21272
			if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbag.getScanType())){
					if(mailbag.getMailTag()!=null && mailbag.getMailTag().length()!=29 && mailbag.getMailTag().length()!=12){
							errorVO.setContainer(mailbag.getMailTag());	//modified for icrd-236825
							errorVO.setMailbagId("");
						}				
			}
			 if (mailbag.getFlightDate() != null) {
				 errorVO.setFlightDate(mailbag.getFlightDate().toDisplayDateOnlyFormat());
			 }
			 log.log(Log.FINE, "ScannedPort", mailbag.getScannedPort());

			 if (mailbag.getScannedPort() != null) {
				 errorVO.setAirportCode(mailbag.getScannedPort());
			 }
			 log.log(Log.FINE, "ScannedPort to set", errorVO.getAirportCode());
			 //Added by A-5945 for ICRD-113473 starts
			 if(mailbag.getFromCarrierCode()!=null&&mailbag.getFromCarrierCode().trim().length()>0 ){
				 errorVO.setTransferCarrier(mailbag.getFromCarrierCode().toUpperCase())	;
			 }
			 if(mailbag.getPaCode()!=null&&mailbag.getPaCode().trim().length()>0 &&"Y".equals(mailbag.getPaCode())){
				 if(errorVO.getContainer()!=null &&errorVO.getContainer().trim().length()>0){
				 errorVO.setContainer(errorVO.getContainer()+"(SB)");
				 }
				 else if(mailbag.getContainerNumber() != null && mailbag.getContainerNumber().trim().length()>0){
				 errorVO.setContainer(mailbag.getContainerNumber()+"(SB)");
				 }
			 }
			 //Added by A-5945 for ICRD-113473 ends
		 
		 log.log(Log.FINE, "Vo to be saved", errorVO);
		 this.setTxErrorReferenceVO(errorVO);

		 log.log(Log.FINE, "Inssside Processor calll...................................");
	 }
	/**
	 * @author A-7371
	 * @param mailbag
	 * @throws SystemException
	 */
	public void validateMailBagDetails(MailUploadVO mailbag) throws 
	 SystemException {


		 String DEST_FLT = "-1";
		 String OUTBOUND = "EXP";
		 log.log(Log.FINE, "Inside Processor calll...................................");
		 MailtrackingTxErrorReferenceVO errorVO = new MailtrackingTxErrorReferenceVO();

		 	if(mailbag.isRestrictErrorLogging()){
		 		return;
		 	}
			 errorVO.setContainer(mailbag.getContainerNumber());

			 if (!DEST_FLT.equals(mailbag.getFlightNumber())) {
				 errorVO.setFlightnumber(mailbag.getFlightNumber());
			 }

			 errorVO.setCarrierCode(mailbag.getCarrierCode());
			 errorVO.setFunction(mailbag.getScanType());

			 if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbag.getScanType()) && mailbag.isDeliverd()) {
				 errorVO.setFunction(MailConstantsVO.MAIL_STATUS_DELIVERED);     
			 }

			 if (OUTBOUND.equals(mailbag.getScanType())) {
				 errorVO.setFunction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			 }

			 if (mailbag.getFlightDate() != null) {
				 errorVO.setFlightDate(mailbag.getFlightDate().toDisplayDateOnlyFormat());
			 }
			 
			//LocalDate scanDate = new LocalDate(mailbag.getScannedPort(),Location.ARP,mailbag.getDateTime()., true);
			 LocalDate scanDates = new LocalDate(mailbag.getScannedPort(),Location.ARP,true);
			errorVO.setScannedDateAndTime(scanDates.toDisplayFormat());     

			 if (mailbag.getContainerNumber() != null) {
				 errorVO.setContainer(mailbag.getContainerNumber());
			 }

			 if (!DEST_FLT.equals(mailbag.getFlightNumber())) {
				 errorVO.setFlightnumber(mailbag.getFlightNumber());      
			 }
			 errorVO.setCarrierCode(mailbag.getCarrierCode());

			 if (mailbag.getMailTag() != null && mailbag.getMailTag().trim().length() == 29) {
				 if(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(mailbag.getScanType()) || MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailbag.getScanType()) )
				 {
					 errorVO.setContainer(mailbag.getToContainer());  
				 }

				 errorVO.setMailbagId(mailbag.getMailTag());
			 } else if (mailbag.getMailTag() != null && mailbag.getMailTag().trim().length() != 0) {
				 errorVO.setContainer(mailbag.getContainerNumber());	
				 errorVO.setMailbagId(mailbag.getMailTag());
			 }

			 if (mailbag.getFlightDate() != null) {
				 errorVO.setFlightDate(mailbag.getFlightDate().toDisplayDateOnlyFormat());
			 }
			 log.log(Log.FINE, "ScannedPort", mailbag.getScannedPort());

			 if (mailbag.getScannedPort() != null) {
				 errorVO.setAirportCode(mailbag.getScannedPort());
			 }
			 log.log(Log.FINE, "ScannedPort to set", errorVO.getAirportCode());
			 
			 if(mailbag.getFromCarrierCode()!=null&&mailbag.getFromCarrierCode().trim().length()>0 ){
				 errorVO.setTransferCarrier(mailbag.getFromCarrierCode().toUpperCase())	;
			 }
			
		 
		 log.log(Log.FINE, "Vo to be saved", errorVO);
		 this.setTxErrorReferenceVO(errorVO);

		 log.log(Log.FINE, "Inssside Processor calll...................................");
	 }
	
	
	/**
	 * @author A-8061
	 * @param mailFlightSummaryVO
	 * @param eventCode
	 * @return
	 * @throws SystemException
	 */
	public void performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO,String eventCode)
			throws SystemException{
		

		 log.log(Log.FINE, "Inside Processor calll...................................");
		 MailtrackingTxErrorReferenceVO errorVO = new MailtrackingTxErrorReferenceVO();

			// errorVO.setContainer(mailbag.getContainerNumber());
			 errorVO.setFlightnumber(mailFlightSummaryVO.getFlightNumber());
			// errorVO.setCarrierCode(mailbag.getCarrierCode());
			 errorVO.setFunction(eventCode);
			 if (mailFlightSummaryVO.getFlightDate() != null) {
				 errorVO.setFlightDate(mailFlightSummaryVO.getFlightDate().toDisplayDateOnlyFormat());
			 }
			// errorVO.setScannedDateAndTime(mailbag.getDateTime());     
			// errorVO.setCarrierCode(mailbag.getCarrierCode());
			//errorVO.setMailbagId(mailbag.getMailTag());
			 if (mailFlightSummaryVO.getAirportCode() != null) {
				 errorVO.setMailbagId(mailFlightSummaryVO.getAirportCode());
			 }
			 errorVO.setAirportCode("XXX");
			 log.log(Log.FINE, "ScannedPort to set", errorVO.getAirportCode());

			 log.log(Log.FINE, "Vo to be saved", errorVO);
			 this.setTxErrorReferenceVO(errorVO);
			 log.log(Log.FINE, "Inssside Processor calll...................................");
		 
	}
	
	
	
	
///*Added as part of ICRD-229584 starts
		 public void getManifestInfo(MailbagVO mailbagvo) throws SystemException
		 {
			 MailUploadVO mailuploadvo;
		 MailtrackingTxErrorReferenceVO errorVO=new MailtrackingTxErrorReferenceVO();
		 errorVO.setMailbagId(mailbagvo.getMailbagId());
		 errorVO.setContainer(mailbagvo.getFromContainer());
		 errorVO.setCarrierCode(mailbagvo.getCarrierCode()); 
		 errorVO.setFlightnumber(mailbagvo.getFlightNumber());
		 errorVO.setFlightDate(String.valueOf(mailbagvo.getFlightDate()));
		 errorVO.setRemarks(mailbagvo.getReturnedRemarks());
		 errorVO.setResolutionStatus(mailbagvo.getReturnedReason());
		 errorVO.setScannedDateAndTime(String.valueOf(mailbagvo.getScannedDate()));
		 errorVO.setLastUpdateDateAndTime(String.valueOf(mailbagvo.getLastUpdateTime()));
		 errorVO.setAirportCode(mailbagvo.getOrigin());
		 errorVO.setTransferCarrier(mailbagvo.getCarrierCode());
		 this.setTxErrorReferenceVO(errorVO);
		 if(mailbagvo.isManifestInfoErrorFlag())
		 {
			 errorVO.setFunction(MailConstantsVO.MAILBAG_NOT_ARRIVED); 
		 }
		
		 }
		//Added as part of ICRD-229584 ends */
	
		 public void  performErrorStampingForFoundMailWebServices(MailUploadVO mailBagVOs,String scanningPort)throws SystemException 
		  {
			
		    this.log.log(3, "Inside processor  call performErrorStampingForFoundMailWebServices...................................");
		    saveMailUploadDetailsForAndroid(mailBagVOs, scanningPort);
		    
		  }
	
}
