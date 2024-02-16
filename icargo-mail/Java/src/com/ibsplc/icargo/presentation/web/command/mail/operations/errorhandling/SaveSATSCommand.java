/*
 * SaveCommand.java 
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailWebserviceVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailTrackingErrorHandlingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
/**
 * @author A-5945
 *
 */
public class SaveSATSCommand extends BaseCommand {
	
	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("ADMIN MONITORING");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";

	/**
	 * The ScreenID 
	 */
	private static final String SCREEN_ID = "mailtracking.defaults.errorhandligpopup";
	/**
	 * Target mappings for succes 
	 */
	private static final String SAVE_SUCCESS = "save_success";

	

	/**
	 * This method is used to execute the list requested construct rates details
	 * command
	 * 
	 * @param invocationContext
	 *            - InvocationContext
    * @throws CommandInvocationException
    */
   public void execute(InvocationContext invocationContext)
	                         throws CommandInvocationException {
	
	   log.entering("SaveCommand", "execute");
	   ErrorHandlingPopUpForm errorHandlingPopUpForm = (ErrorHandlingPopUpForm) invocationContext.screenModel;
	   MailTrackingErrorHandlingSession mailTrackingErrorHandlingSession= (MailTrackingErrorHandlingSession) getScreenSession(
				MODULE_NAME, SCREEN_ID)	;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		String txnId=mailTrackingErrorHandlingSession.getTxnid();
		log.log(Log.FINE, "***txnid inside save command*******",txnId);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<MailUploadVO> mailUploadVOs = new ArrayList<MailUploadVO>();

		MailUploadVO mailUploadVO = mailTrackingErrorHandlingSession
				.getScannedDetails();
		
		if (mailUploadVO != null) {
			
			mailUploadVO.setCarrierCode(errorHandlingPopUpForm
					.getFlightCarrierCode().toUpperCase());

			mailUploadVO.setFlightNumber(errorHandlingPopUpForm
					.getFlightNumber().toUpperCase());
			if (errorHandlingPopUpForm.getFlightDate() != null
					&& errorHandlingPopUpForm.getFlightDate().trim().length() > 0) {  
					
				mailUploadVO.setFlightDate(new LocalDate(logonAttributes
						.getAirportCode(), ARP, true)
						.setDate(errorHandlingPopUpForm.getFlightDate()));
			}  
        //Modified as part of code quality work by A-7531 starts
			if (MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getBulk()) ){
		//Modified as part of code quality work by A-7531 ends		
				mailUploadVO.setContainerType("B");
			} else
				{
				mailUploadVO.setContainerType("U");
				}

			mailUploadVO.setContainerNumber(errorHandlingPopUpForm
					.getContainer().toUpperCase());        
			mailUploadVO
					.setDestination(errorHandlingPopUpForm.getDestination().toUpperCase());
			
			
			mailUploadVO.setToPOU(errorHandlingPopUpForm.getPou().toUpperCase());
			//Added by A-5945	for ICRD-96162 starts
			mailUploadVO.setContainerPOU(errorHandlingPopUpForm.getPou().toUpperCase());   
			//Added by A-5945 for ICRD-96162 ends   
			if(errorHandlingPopUpForm.getDoe()!=null && errorHandlingPopUpForm.getDoe().trim().length()>0 && !mailUploadVO.getScanType().equals(MailConstantsVO.MAIL_STATUS_ACCEPTED))
			{
				mailUploadVO.setToPOU(errorHandlingPopUpForm.getDoe().substring(2, 5).toUpperCase())	;  
				
				mailUploadVO.setContainerPOU(errorHandlingPopUpForm.getDoe().substring(2, 5).toUpperCase());
			}

			if(errorHandlingPopUpForm.getOoe()!=null && errorHandlingPopUpForm.getOoe().trim().length()!=0) 
			{
			mailUploadVO.setOrginOE(errorHandlingPopUpForm.getOoe().toUpperCase());
			mailUploadVO.setContainerPol(errorHandlingPopUpForm.getOoe().substring(2, 5).toUpperCase());
			}
			if(errorHandlingPopUpForm.getDoe()!=null && errorHandlingPopUpForm.getDoe().trim().length()!=0) 
			{
			mailUploadVO.setDestinationOE(errorHandlingPopUpForm.getDoe().toUpperCase());  
			}
			if(errorHandlingPopUpForm.getCategory()!=null && errorHandlingPopUpForm.getCategory().trim().length()!=0) 
			{
			mailUploadVO.setCategory(errorHandlingPopUpForm.getCategory().toUpperCase());
			}
			if(errorHandlingPopUpForm.getSubclass()!=null && errorHandlingPopUpForm.getSubclass().trim().length()!=0) 
			{
			mailUploadVO.setSubClass(errorHandlingPopUpForm.getSubclass().toUpperCase());
			}
			if(errorHandlingPopUpForm.getYear()!=null && errorHandlingPopUpForm.getYear().trim().length()!=0) 
			{
			mailUploadVO.setYear(Integer.parseInt(errorHandlingPopUpForm.getYear()));
			}
			if(errorHandlingPopUpForm.getDsn()!=null && errorHandlingPopUpForm.getDsn().trim().length()!=0) 
			{
			mailUploadVO.setDsn(errorHandlingPopUpForm.getDsn());
			}
			if(errorHandlingPopUpForm.getRsn()!=null && errorHandlingPopUpForm.getRsn().trim().length()!=0) 
			{
			mailUploadVO.setRsn(errorHandlingPopUpForm.getRsn());
			}
			if(errorHandlingPopUpForm.getHni()!=null && errorHandlingPopUpForm.getHni().trim().length()!=0) 
			{
			mailUploadVO.setHighestnumberIndicator(errorHandlingPopUpForm.getHni());
			}
			if(errorHandlingPopUpForm.getRi()!=null && errorHandlingPopUpForm.getRi().trim().length()!=0) 
			{
			mailUploadVO.setRegisteredIndicator(errorHandlingPopUpForm.getRi());
			}
			if(errorHandlingPopUpForm.getWeight()!=null && errorHandlingPopUpForm.getWeight().trim().length()!=0) 
			{
				String weight=errorHandlingPopUpForm.getWeight();
				//Added by A-5945 for ICRD-107696 starts
				//For turkish locale the weight always come as decimal value
				if(weight.contains(".")){
					int len=weight.length();
					int i = weight.indexOf(".");
					String wgt=weight.substring(i+1, len);
					weight=wgt;
					}
				//Added by A-5945 for ICRD-107696 ends
				if(weight.length() == 3){
					weight = new StringBuilder("0").append(weight).toString();   
				}
				if(weight.length() == 2){
					weight = new StringBuilder("00").append(weight).toString();
				}
				if(weight.length() == 1){
					weight = new StringBuilder("000").append(weight).toString();  
				}
				errorHandlingPopUpForm.setWeight(weight);
			mailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(errorHandlingPopUpForm.getWeight())));    //Added by A-7550     
			}
			//Added by A-5945 for updating scandate at  the time of resolving each transactions
			if (mailUploadVO.getDateTime() != null
					&& mailUploadVO.getDateTime().trim().length() > 0) {
				LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			    mailUploadVO.setDateTime(scanDat.toDisplayFormat());        
			   // SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
				//return  sdf.format(scanDat); 
			}
			String mailbagId=mailUploadVO.getMailTag();
			StringBuilder mailTag=new StringBuilder();
			mailTag.append(errorHandlingPopUpForm.getOoe())
					.append(errorHandlingPopUpForm.getDoe())
					.append(errorHandlingPopUpForm.getCategory())
					.append((errorHandlingPopUpForm.getSubclass())
							.toUpperCase())
					.append(errorHandlingPopUpForm.getYear())
					.append(errorHandlingPopUpForm.getDsn())
					.append(errorHandlingPopUpForm.getRsn())
					.append(errorHandlingPopUpForm.getHni())
					.append(errorHandlingPopUpForm.getRi())
					.append(errorHandlingPopUpForm.getWeight());
			String mailBagId=mailTag.toString().toUpperCase();            
			if (mailBagId != null && mailBagId.trim().length() == 29) {        
				mailUploadVO.setMailTag(mailBagId);
			} else if(mailbagId!=null && mailbagId.trim().length()>0 ) {
				mailUploadVO.setMailTag(mailUploadVO.getContainerNumber());     
			}
			else  {
				mailUploadVO.setMailTag("");
			}

			mailUploadVOs.add(mailUploadVO);
			
			Collection<MailWebserviceVO> mailWebServiceVos=createMailWebServiceVOsFromMailUploadVO(mailUploadVOs);
			 
			
			try {
				scannedMailDetailsVO = mailTrackingDefaultsDelegate
				.performMailOperationForGHA(mailWebServiceVos,
						mailUploadVO.getScannedPort());
						
			} catch (BusinessDelegateException e) {   
				//Modified as part of code quality work by A-7531	starts		
				handleDelegateException(e);
			}
			//Modified as part of code quality work by A-7531 ends
			

//			if (scannedMailDetailsVO != null) {
//
//				try {
//			  mailTrackingDefaultsDelegate
//							.saveAndProcessMailBags(scannedMailDetailsVO);
//				} catch (Exception e) {
//
//				}
//
//			}

			log.log(Log.FINE, "***values to be saved*******", mailUploadVOs);
		try{
				mailTrackingDefaultsDelegate.resolveTransaction(companyCode,
						txnId,null);
				
			} catch (BusinessDelegateException e) {
				//Modified as part of code quality work by A-7531 starts
				handleDelegateException(e);
		  
		  }
		//Modified as part of code quality work by A-7531 ends
		
		}
		errorHandlingPopUpForm.setCloseStatus("CLOSE");
		log.log(Log.FINE, "***save success*******");

        invocationContext.target=SAVE_SUCCESS;
		
	}

	private Collection<MailWebserviceVO> createMailWebServiceVOsFromMailUploadVO(
			Collection<MailUploadVO> mailUploadVOs) {
		Collection<MailWebserviceVO> mailbagVOs=new ArrayList<MailWebserviceVO>();
		MailUploadVO mailUploadVO=mailUploadVOs.iterator().next();

		MailWebserviceVO webServiceVO = new MailWebserviceVO();
		webServiceVO.setCompanyCode(mailUploadVO.getCompanyCode());
		webServiceVO.setScanningPort(mailUploadVO.getScannedPort());
		webServiceVO.setScanType(mailUploadVO.getScanType());      
		webServiceVO.setCarrierCode(mailUploadVO.getCarrierCode());
		
		webServiceVO.setFlightNumber(mailUploadVO.getFlightNumber());
		
		if(mailUploadVO.getFlightDate()!=null ){
			
			webServiceVO.setFlightDate(mailUploadVO.getFlightDate());
		}
		if(mailUploadVO.getContainerPOU()!=null && 
				mailUploadVO.getContainerPOU().trim().length()==0){
			webServiceVO.setContainerPou(null);
		}
		else{
		webServiceVO.setContainerPou(mailUploadVO.getContainerPOU());
		}
		if(mailUploadVO.getContainerNumber()!=null && 
				mailUploadVO.getContainerNumber().trim().length()==0){
			webServiceVO.setContainerNumber(null);
		}
		else{
		webServiceVO.setContainerNumber(mailUploadVO.getContainerNumber());
		}
		if(mailUploadVO.getContainerType()!=null && 
				mailUploadVO.getContainerType().trim().length()==0){
			webServiceVO.setContainerType(null);
		}
		else{
		webServiceVO.setContainerType(mailUploadVO.getContainerType());
		}
		if(mailUploadVO.getDestination()!=null && 
				mailUploadVO.getDestination().trim().length()==0){
			webServiceVO.setContainerDestination(null);
		}
		else{
		webServiceVO.setContainerDestination(mailUploadVO.getDestination());
		}
		webServiceVO.setContainerPol(mailUploadVO.getContainerPol());
		webServiceVO.setRemarks(mailUploadVO.getRemarks());
		//if(mailUploadVO.getMailTag()!=null && mailUploadVO.getMailTag().trim().length()==0){
		//	webServiceVO.setMailBagId(null);
		//}
		//else{
		webServiceVO.setMailBagId(mailUploadVO.getMailTag());
		//}
		if(mailUploadVO.getDamageCode()!=null && 
				mailUploadVO.getDamageCode().trim().length()==0){
			webServiceVO.setDamageCode(null);
		}
		else{
		webServiceVO.setDamageCode(mailUploadVO.getDamageCode());
		}
		webServiceVO.setDamageRemarks(mailUploadVO.getDamageRemarks());
		if(mailUploadVO.getOffloadReason()!=null && 
				mailUploadVO.getOffloadReason().trim().length()==0){
			webServiceVO.setOffloadReason(null);
		}
		else{
		webServiceVO.setOffloadReason(mailUploadVO.getOffloadReason());
		}
		if(mailUploadVO.getReturnCode()!=null && 
				mailUploadVO.getReturnCode().trim().length()==0){
			webServiceVO.setReturnCode(null);
		}
		else{
		webServiceVO.setReturnCode(mailUploadVO.getReturnCode());
		}
		if(mailUploadVO.getContainerType()!=null && 
				mailUploadVO.getContainerType().trim().length()==0){
			webServiceVO.setToContainerType(null);
		}
		else{
		webServiceVO.setToContainerType(mailUploadVO.getContainerType());
		}
		if(mailUploadVO.getToContainer()!=null && mailUploadVO.getToContainer().trim().length()==0){
			webServiceVO.setToContainer(null);
		}
		else{
		webServiceVO.setToContainer(mailUploadVO.getToContainer());
		}
		if(mailUploadVO.getToCarrierCode()!=null && 
				mailUploadVO.getToCarrierCode().trim().length()==0){
			webServiceVO.setToCarrierCod(null);
		}
		else{
		webServiceVO.setToCarrierCod(mailUploadVO.getToCarrierCode());
		}
		if(mailUploadVO.getToFlightNumber()!=null && 
				mailUploadVO.getToFlightNumber().trim().length()==0){
			webServiceVO.setToFlightNumber(null);
		}
		else{
		webServiceVO.setToFlightNumber(mailUploadVO.getToFlightNumber());
		}
		if(mailUploadVO.getToFlightDate()!=null ){
			
			webServiceVO.setToFlightDate(mailUploadVO.getToFlightDate());
		}
		if(mailUploadVO.getContainerPOU()!=null && 
				mailUploadVO.getContainerPOU().trim().length()==0){
			webServiceVO.setToContainerPou(null);
		}
		else{
		webServiceVO.setToContainerPou(mailUploadVO.getContainerPOU());
		}
		if(mailUploadVO.getDestination()!=null && 
				mailUploadVO.getDestination().trim().length()==0){
			webServiceVO.setToContainerDestination(null);
		}
		
		if(mailUploadVO.getConsignmentDocumentNumber()!=null && 
				mailUploadVO.getConsignmentDocumentNumber().trim().length()==0){
			webServiceVO.setConsignmentDocNumber(null);
		}
		else{
		webServiceVO.setConsignmentDocNumber(mailUploadVO.getConsignmentDocumentNumber());
		}
		webServiceVO.setSerialNumber(mailUploadVO.getSerialNumber());
		if(mailUploadVO.getPaCode()!=null)
			{
			webServiceVO.setPAbuilt(true);
			}
		
		webServiceVO.setUserName(mailUploadVO.getScanUser());
		if(mailUploadVO.getDateTime()!=null && mailUploadVO.getDateTime().trim().length()>0){
			//for testing only. Airport code is required.
			LocalDate scanDate = new LocalDate(webServiceVO.getScanningPort(),Location.ARP,
					false);
			//LocalDate scanDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			webServiceVO.setScanDateTime(scanDate.setDateAndTime(mailUploadVO.getDateTime()));
		}
		mailbagVOs.add(webServiceVO);
	
		return mailbagVOs;
	}

}
