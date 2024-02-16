package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory.ListMailBagHistory.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	13-Sep-2018		:	Draft
 */

public class ListMailBagHistory extends BaseCommand {
	
	private static final String SUCCESS = "list_success";
	
	private Log log = LogFactory.getLogger("ListMailBagHistory");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailbaghistory";
	
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String SERVICE_LEVEL = "mail.operations.mailservicelevels";
	private static final String BILLINGSTATUS = "mailtracking.mra.gpabilling.gpabillingstatus";
	
	private static final String MAILBAG_NO_DATA_FOUND = "mailtracking.defaults.mbHistory.msg.err.nodatafound";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	MailBagHistoryUxForm mailBagHistoryForm =
							(MailBagHistoryUxForm)invocationContext.screenModel;  
    	MailBagHistorySession mailBagHistorySession = 
    									getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    Collection<ErrorVO> errors = null;
	    
	    SharedDefaultsDelegate sharedDefaultsDelegate =
				new SharedDefaultsDelegate();
		Map hashMap = null;
		
		String billingStatus = null;
		mailBagHistoryForm.setBillingStatus(null);
		
		Collection<String> oneTimeList = new ArrayList<String>();
		
		oneTimeList.add(MAIL_STATUS);
		oneTimeList.add(SERVICE_LEVEL);
		oneTimeList.add(BILLINGSTATUS);
		
		try {
		
			hashMap = sharedDefaultsDelegate.findOneTimeValues
								(companyCode,oneTimeList);
			log.log(Log.FINEST, "\n\n hash map******************", hashMap);
		
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			log.log(Log.SEVERE, "\n\n message fetch exception..........;;;;");
		}
		
		Collection<OneTimeVO> oneTimeMailStatus =
		(Collection<OneTimeVO>)hashMap.get(MAIL_STATUS);
		Collection<OneTimeVO> oneTimesServiceLevel =
				(Collection<OneTimeVO>)hashMap.get(SERVICE_LEVEL);
		Collection<OneTimeVO> oneTimeBillingStatus =
				(Collection<OneTimeVO>)hashMap.get(BILLINGSTATUS);
		
		MailTrackingDefaultsDelegate delegate = 
				new MailTrackingDefaultsDelegate();
		String mailBagId="";
		long mailSeqNumber=0l;
		boolean mailValidationReq=false;
		boolean isInvalidMailbag=true;
		if(mailBagHistoryForm.getMailbagId() == null ||
		mailBagHistoryForm.getMailbagId().trim().length() == 0){
			
			//Added by A-8464 for ICRD-243079
			if(mailBagHistoryForm.getFromScreenId().equals("mail.operations.ux.mailperformancemonitor")){
			
					mailSeqNumber = mailBagHistoryForm.getMailSequenceNumber();
				
			}
			
			else {
				if (mailBagHistorySession.getMailSequenceNumber() != null
						&& mailBagHistorySession.getMailSequenceNumber().size() > 0) {
				mailSeqNumber=mailBagHistorySession.getMailSequenceNumber().get(0);
			}else{
				mailBagHistorySession.removeAllAttributes();
				mailBagHistoryForm.setMailbagId("");
				mailBagHistoryForm.setReqDeliveryTime("");
				mailBagHistoryForm.setBtnDisableReq("Y");
				mailBagHistorySession.setEnquiryFlag("yes"); 
				invocationContext.target = SUCCESS;
				return;
			}
		}
			
		}
		else{
			mailBagId= mailBagHistoryForm.getMailbagId();
			
			mailValidationReq=true;
			if(mailBagId.length()!=29){
				mailValidationReq=false;
			}
		}
//No Data found to be shown in case of invalid tag given
//Changes made as part of IASCB-34071		
//		if(mailValidationReq){
//			MailbagVO mailVO = new MailbagVO();
//			Collection<MailbagVO> mailbagVos = new ArrayList<MailbagVO>();
//			try{
//				mailVO.setCompanyCode(companyCode);
//				mailVO.setDoe(mailBagId.substring(6, 12));
//				mailVO.setDespatchSerialNumber(mailBagId.substring(16, 20));
//				mailVO.setMailbagId(mailBagId.trim());
//				mailVO.setMailSubclass(mailBagId.substring(13, 15));
//				mailVO.setMailCategoryCode(mailBagId.substring(12, 13));
//				mailVO.setOoe(mailBagId.substring(0, 6));
//				mailVO.setYear(Integer.parseInt(mailBagId.substring(15, 16)));
//				mailVO.setOperationalFlag("I");
//				mailbagVos.add(mailVO);
//				isInvalidMailbag = delegate.validateMailBags(mailbagVos);
//			}catch(Exception exception){
//				log.log(Log.FINEST, "Invalid Mailbag entered in Mail History Popup", isInvalidMailbag);
//				isInvalidMailbag = false;
//			}
//		}
//		if(!isInvalidMailbag){
//			ErrorVO errorVO = new ErrorVO(
//			"mailtracking.defaults.mbHistory.msg.err.invalidmailbag");
//			errors = new ArrayList<ErrorVO>();
//			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
//			errors.add(errorVO);
//			mailBagHistoryForm.setMailbagId("");
//			invocationContext.addAllError(errors);
//			invocationContext.target = SUCCESS;
//			return;
//		}
		log.log(Log.FINEST, "\n\n *******mailBagId***********", mailBagId);
		Collection<MailbagHistoryVO> mailBagHistoryVOs = 
		new ArrayList<MailbagHistoryVO>();
		ArrayList<MailbagVO> mailBagVOs=new ArrayList<MailbagVO>();
		
		
		Collection<MailHistoryRemarksVO> mailHistoryRemarksVOs = 
				new ArrayList<>();

		
		try{
			mailBagVOs = 
			delegate.findDuplicateMailbag(companyCode,mailBagId);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		
		int i=0;
		if(mailBagVOs!=null && !mailBagVOs.isEmpty()){
			mailSeqNumber=mailBagVOs.get(0).getMailSequenceNumber();
		mailBagHistorySession.setMailBagVos(mailBagVOs);
		if(mailBagHistoryForm.getIndex()==null||"".equalsIgnoreCase(mailBagHistoryForm.getIndex())){
		mailSeqNumber=mailBagVOs.get(0).getMailSequenceNumber();
		}else{
			for(MailbagVO mailVO:mailBagVOs){
				
			if(i==Integer.parseInt(mailBagHistoryForm.getIndex())){
			mailSeqNumber=mailVO.getMailSequenceNumber();
			}
			i++;
			}
			
		}
		if (mailSeqNumber==0 && mailBagHistoryForm.getFromScreenId().equals("mail.operations.ux.mailbagenquiry")){
			mailSeqNumber=mailBagVOs.get(0).getMailSequenceNumber();
		
		}
		
		}
		

		
		try{
			mailBagHistoryVOs = 
			delegate.findMailbagHistoriesFromWebScreen(companyCode,mailBagId,mailSeqNumber);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		
		try{
			mailHistoryRemarksVOs = delegate.findMailbagNotes(mailBagId);
		}catch(BusinessDelegateException businessDelegateException){
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "\n\n mailBagHistoryVOs---------->  ",
		mailBagHistoryVOs);
		if((mailBagHistoryVOs==null||mailBagHistoryVOs.size()==0) && mailSeqNumber<=0){
			ErrorVO errorVO = new ErrorVO(
			MAILBAG_NO_DATA_FOUND);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			mailBagHistoryForm.setMailbagId("");
			mailBagHistorySession.setMailBagHistoryVOs(null);
			invocationContext.addAllError(errors);
			invocationContext.target = SUCCESS;
			return;
		}
		if(mailSeqNumber>0) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			mailbagVO.setMailSequenceNumber(mailSeqNumber); 
			try {
				mailbagVO = delegate.findMailBillingStatus(mailbagVO );
			} catch (BusinessDelegateException e) {
				log.log(Log.FINE, "\n\n Exception findMailbagBillingStatus ---------->  ");
			}

			if(mailbagVO!=null && mailbagVO.getBillingStatus()!=null) {

				billingStatus = mailbagVO.getBillingStatus();
			}
		}
		if(mailBagHistoryVOs==null) {
			mailBagHistoryVOs = new ArrayList<>();
		}
		for(MailbagHistoryVO mailbagHistoryVO:mailBagHistoryVOs){
			if(mailbagHistoryVO.getOriginExchangeOffice()!=null){
				String yearPrefix = new LocalDate
				(logonAttributes.getAirportCode(),ARP,false).
									toDisplayFormat("yyyy").substring(0,3);
				
				log.log(Log.FINE, "\n\n yearPrefix---------->  ", yearPrefix);
				mailBagHistoryForm.setOoe(mailbagHistoryVO.getOriginExchangeOffice());
				mailBagHistoryForm.setDoe(mailbagHistoryVO.getDestinationExchangeOffice());
				mailBagHistoryForm.setCatogory(mailbagHistoryVO.getMailCategoryCode());
				mailBagHistoryForm.setMailClass(mailbagHistoryVO.getMailClass());
				mailBagHistoryForm.setMailSubclass(mailbagHistoryVO.getMailSubclass()); 
				mailBagHistoryForm.setYear(
				new StringBuffer(yearPrefix).append(mailbagHistoryVO.getYear()).toString());
				mailBagHistoryForm.setDsn(mailbagHistoryVO.getDsn());
				mailBagHistoryForm.setRsn(mailbagHistoryVO.getRsn());
				
				mailBagHistoryForm.setWeightMeasure(mailbagHistoryVO.getWeight());    
				mailBagHistoryForm.setMailbagId(mailbagHistoryVO.getMailbagId()); 
				mailBagHistoryForm.setActualWeightMeasure(mailbagHistoryVO.getActualWeight()); //Added by A-8164 for ICRD-323182      
				if(mailbagHistoryVO.getMailSerLvl()!=null){
				mailBagHistoryForm.setMailSerLvl(mailbagHistoryVO.getMailSerLvl());//added by A-8353 for ICRD-ICRD-327150
				}
				LocalDate reqDeliveryTime = mailbagHistoryVO.getReqDeliveryTime();
				if(reqDeliveryTime!=null){
					mailBagHistoryForm.setReqDeliveryTime(reqDeliveryTime.toDisplayFormat("dd-MMM-yyyy HH:mm"));
				}
				mailBagHistoryForm.setOrigin(mailbagHistoryVO.getOrigin());
				mailBagHistoryForm.setDestination(mailbagHistoryVO.getDestination());
				mailBagHistoryForm.setConsignmentNumber(mailbagHistoryVO.getConsignmentNumber());
				LocalDate consignmentDate = mailbagHistoryVO.getConsignmentDate();
				if(consignmentDate!=null){
					mailBagHistoryForm.setConsignmentDate(consignmentDate.toDisplayFormat("dd-MMM-yyyy HH:mm"));
				}
				LocalDate transportSrvWindow = mailbagHistoryVO.getTransportSrvWindow();
				if(transportSrvWindow!=null){
					mailBagHistoryForm.setTransportWindowDate(transportSrvWindow.toDisplayFormat("dd-MMM-yyyy HH:mm"));
				}
				if(mailbagHistoryVO.getPoacod()!=null) {
				mailBagHistoryForm.setPoacod(mailbagHistoryVO.getPoacod());
				} 
				mailBagHistoryForm.setBillingStatus(billingStatus);
				mailBagHistoryForm.setAcceptancePostalContainerNumber(mailbagHistoryVO.getAcceptancePostalContainerNumber());
				break;
			}
		}
		
		if(mailBagHistoryVOs!=null && !mailBagHistoryVOs.isEmpty())
		{
			MailbagHistoryVO mailbagHistoryVO=mailBagHistoryVOs.iterator().next();
			mailBagHistoryForm.setMailRemarks(mailbagHistoryVO.getMailRemarks());
		}
		//added for IASCB-91419
		if (mailBagVOs != null && !mailBagVOs.isEmpty()) {
			if ((mailBagHistoryVOs == null || mailBagHistoryVOs.size() == 0) && mailSeqNumber > 0) {

				String yearPrefix = new LocalDate(logonAttributes.getAirportCode(), ARP, false).toDisplayFormat("yyyy")
						.substring(0, 3);

				log.log(Log.FINE, "\n\n yearPrefix---------->  ", yearPrefix);
				mailBagHistoryForm.setOoe(mailBagVOs.get(0).getOoe());
				mailBagHistoryForm.setDoe(mailBagVOs.get(0).getDoe());
				mailBagHistoryForm.setCatogory(mailBagVOs.get(0).getMailCategoryCode());
				mailBagHistoryForm.setMailClass(mailBagVOs.get(0).getMailClass());
				mailBagHistoryForm.setMailSubclass(mailBagVOs.get(0).getMailSubclass());
				mailBagHistoryForm.setYear(new StringBuffer(yearPrefix).append(mailBagVOs.get(0).getYear()).toString());
				mailBagHistoryForm.setDsn(mailBagVOs.get(0).getDespatchSerialNumber());
				mailBagHistoryForm.setRsn(mailBagVOs.get(0).getReceptacleSerialNumber());

				mailBagHistoryForm.setWeightMeasure(mailBagVOs.get(0).getWeight());
				mailBagHistoryForm.setMailbagId(mailBagVOs.get(0).getMailbagId());
				mailBagHistoryForm.setActualWeightMeasure(mailBagVOs.get(0).getActualWeight()); 
																								
				if (mailBagVOs.get(0).getMailServiceLevel() != null) {
					mailBagHistoryForm.setMailSerLvl(mailBagVOs.get(0).getMailServiceLevel());
																								
				}
				LocalDate reqDeliveryTime = mailBagVOs.get(0).getReqDeliveryTime();
				if (reqDeliveryTime != null) {
					mailBagHistoryForm.setReqDeliveryTime(reqDeliveryTime.toDisplayFormat("dd-MMM-yyyy HH:mm"));
				}
				mailBagHistoryForm.setOrigin(mailBagVOs.get(0).getOrigin());
				mailBagHistoryForm.setDestination(mailBagVOs.get(0).getDestination());
				mailBagHistoryForm.setConsignmentNumber(mailBagVOs.get(0).getConsignmentNumber());
				LocalDate consignmentDate = mailBagVOs.get(0).getConsignmentDate();
				if (consignmentDate != null) {
					mailBagHistoryForm.setConsignmentDate(consignmentDate.toDisplayFormat("dd-MMM-yyyy HH:mm"));
				}
				LocalDate transportSrvWindow = mailBagVOs.get(0).getTransWindowEndTime();
				if (transportSrvWindow != null) {
					mailBagHistoryForm.setTransportWindowDate(transportSrvWindow.toDisplayFormat("dd-MMM-yyyy HH:mm"));
				}
				if (mailBagVOs.get(0).getPaCode() != null) {
					mailBagHistoryForm.setPoacod(mailBagVOs.get(0).getPaCode());
				}
				mailBagHistoryForm.setBillingStatus(billingStatus);
			}
		}
		
		mailBagHistorySession.setMailHistoryRemarksVOs(mailHistoryRemarksVOs);
		mailBagHistorySession.setMailBagHistoryVOs(mailBagHistoryVOs);
		mailBagHistorySession.setOneTimeStatus(oneTimeMailStatus);
		mailBagHistorySession.setOneTimeServiceLevel(oneTimesServiceLevel);
		mailBagHistorySession.setOneTimeBillingStatus(oneTimeBillingStatus);
		
		mailBagHistoryForm.setMailSequenceNumber(mailSeqNumber);
		mailBagHistoryForm.setScreenStatusFlag
		(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		mailBagHistoryForm.setBtnDisableReq("");
		invocationContext.target = SUCCESS;
			}

}
