package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.claimhistorypopup;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ClaimHistoryPopupSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimHistoryPopupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7929	:	14-Nov-2019	:	Draft
 */
public class ScreenloadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mail Mra claim historypopup ");
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux.claimhistory";
	private static final String SCREENLOAD_SUCCESS="screenload_success";
	private static final String SCREENLOAD_FAILURE="screenload_failure";
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String NO_RECORDS = "mail.mra.gpareporting.ux.claimhistory.norecords";
	
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
	
		log.entering("ScreenloadCommand", "execute");
		
		ClaimHistoryPopupForm claimHistoryPopupForm = (ClaimHistoryPopupForm) invocationContext.screenModel;
		ClaimHistoryPopupSession claimHistoryPopupSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<MailbagHistoryVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<InvoicDetailsVO> claimDetails = new ArrayList<InvoicDetailsVO>();
		Collection<InvoicDetailsVO> invoicDetails = new ArrayList<InvoicDetailsVO>();
		Collection<InvoicDetailsVO> updatedInvoicDetails = new ArrayList<InvoicDetailsVO>();
		Collection<ForceMajeureRequestVO> forceMajeureRequestVOs = new ArrayList<ForceMajeureRequestVO>();
		
		try {
			forceMajeureRequestVOs = new MailTrackingMRADelegate().findApprovedForceMajeureDetails(logonAttributes.getCompanyCode(),claimHistoryPopupForm.getMailbagId(),claimHistoryPopupForm.getMalseqnum());
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		if(forceMajeureRequestVOs!=null && !forceMajeureRequestVOs.isEmpty()){
			claimHistoryPopupSession.setForceMajeureDetails((ArrayList<ForceMajeureRequestVO>) forceMajeureRequestVOs);
		}else{
			claimHistoryPopupSession.setForceMajeureDetails(null);             
		}
			
		
		
		try {
			mailbagHistoryVOs = new MailTrackingMRADelegate().findMailbagHistories(logonAttributes.getCompanyCode(),claimHistoryPopupForm.getMailbagId(),claimHistoryPopupForm.getMalseqnum());
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		
		try {
			invoicDetails = new MailTrackingMRADelegate().findInvoicAndClaimDetails(logonAttributes.getCompanyCode(),claimHistoryPopupForm.getMalseqnum());
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		
		if(invoicDetails != null && !invoicDetails.isEmpty()){
			for(InvoicDetailsVO vo: invoicDetails){
				if(vo.getClaimReason()!=null && !vo.getClaimReason().isEmpty() ){
					String claimRemarks=null;
					
						if("NPR".equals(vo.getClaimReason())){    
							claimRemarks="No Pay Record claim";
						}else if("RVX".equals(vo.getClaimReason())){    
							claimRemarks="Wrong Rate claim";
						}else if("WXX".equals(vo.getClaimReason())){    
							claimRemarks="Weight claim";
						}else if("ODC".equals(vo.getClaimReason())){      
							claimRemarks="Incorrect Weight claim";
						}else if("TCO".equals(vo.getClaimReason())){            
							claimRemarks="Offload claim";
						}else if("SAV".equals(vo.getClaimReason())){            
							claimRemarks="Sac vides claim";
						}else if("MSX".equals(vo.getClaimReason())){    
							claimRemarks="Missing Scan claim";
						}else if("EDT".equals(vo.getClaimReason())){    
							claimRemarks="Expected Delivery Time claim";
						}  
						if(claimRemarks!=null && !claimRemarks.isEmpty()){
							StringBuilder claimReason=new StringBuilder(vo.getClaimReason()).append("-").append(claimRemarks);
							claimRemarks=claimReason.toString();         
						}
							
					vo.setClaimReason(claimRemarks);
				}
				if("I".equals(vo.getType())){
					updatedInvoicDetails.add(vo);
				}else{
					claimDetails.add(vo);
				}
			}
			claimHistoryPopupSession.setInvoicDetails(updatedInvoicDetails);
			claimHistoryPopupSession.setClaimDetails(claimDetails);
		}else{
			claimHistoryPopupSession.setInvoicDetails(null);
			claimHistoryPopupSession.setClaimDetails(null);
		}
		//claimHistoryPopupForm = populateForm(claimHistoryPopupForm,mailbagHistoryVOs);
		
		//For fetching one time
		 SharedDefaultsDelegate sharedDefaultsDelegate =
					new SharedDefaultsDelegate();
			Map hashMap = null;
			
			Collection<String> oneTimeList = new ArrayList<String>();
			
			oneTimeList.add(MAIL_STATUS);
			
			try {
			
				hashMap = sharedDefaultsDelegate.findOneTimeValues
									(logonAttributes.getCompanyCode(),oneTimeList);
				log.log(Log.FINEST, "\n\n hash map******************", hashMap);
			
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				log.log(Log.SEVERE, "\n\n message fetch exception..........;;;;");
			}
			
			Collection<OneTimeVO> oneTimeMailStatus =
			(Collection<OneTimeVO>)hashMap.get(MAIL_STATUS);
			
			
			
		
		if ((mailbagHistoryVOs == null || mailbagHistoryVOs.size() == 0) && (invoicDetails == null || invoicDetails.size() == 0)) {
			claimHistoryPopupSession.setMailbagHistoryVOsCollection(null);
			ErrorVO errorVO = new ErrorVO(NO_RECORDS);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
	    	invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		} else{
			if(mailbagHistoryVOs != null && mailbagHistoryVOs.size() > 0){
			claimHistoryPopupForm = populateForm(claimHistoryPopupForm,mailbagHistoryVOs);
			claimHistoryPopupSession.setMailbagHistoryVOsCollection((ArrayList<MailbagHistoryVO>) mailbagHistoryVOs);
			claimHistoryPopupSession.setOneTimeStatus(oneTimeMailStatus);
			}
		}

			String mailbagID =claimHistoryPopupForm.getMailbagId();
			Collection<ResditReceiptVO> resditReceiptVOs = new ArrayList<ResditReceiptVO>();
			try {
				resditReceiptVOs=new MailTrackingMRADelegate().getResditInfofromUSPS(mailbagID);
			} catch (BusinessDelegateException e) {
				e.getMessage();
			}
			if (resditReceiptVOs == null || resditReceiptVOs.size() == 0) {
				claimHistoryPopupSession.setUspsHistoryVOs(null);
				//ErrorVO errorVO = new ErrorVO(NO_RECORDS);
				//errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				//errors.add(errorVO);
		    	invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			} else{
				claimHistoryPopupSession.setUspsHistoryVOs((ArrayList<ResditReceiptVO>) resditReceiptVOs);
				//target=SCREEN_SUCCESS;

		}
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting("ScreenloadCommand", "execute");
		
	}


	private ClaimHistoryPopupForm populateForm(ClaimHistoryPopupForm claimHistoryPopupForm, Collection<MailbagHistoryVO> mailbagHistoryVOs) {
		
		
		MailbagHistoryVO mailbagHistoryVO = ((ArrayList<MailbagHistoryVO>) mailbagHistoryVOs).get(0);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String yearPrefix = new LocalDate
				(logonAttributes.getAirportCode(),ARP,false).
									toDisplayFormat("yyyy").substring(0,3);
			claimHistoryPopupForm.setMailbagId(mailbagHistoryVO.getMailbagId());
			claimHistoryPopupForm.setDestinationExchangeOffice(mailbagHistoryVO.getDestinationExchangeOffice());
			claimHistoryPopupForm.setOriginExchangeOffice(mailbagHistoryVO.getOriginExchangeOffice());
			claimHistoryPopupForm.setMailCategoryCode(mailbagHistoryVO.getMailCategoryCode());
			claimHistoryPopupForm.setMailClass(mailbagHistoryVO.getMailClass());
			claimHistoryPopupForm.setMailSubclass(mailbagHistoryVO.getMailSubclass());
			claimHistoryPopupForm.setPou(mailbagHistoryVO.getPou());
			claimHistoryPopupForm.setRsn(mailbagHistoryVO.getRsn());
			claimHistoryPopupForm.setYear(
					new StringBuffer(yearPrefix).append(mailbagHistoryVO.getYear()).toString());
			claimHistoryPopupForm.setWeight(mailbagHistoryVO.getWeight().getDisplayValue());
			claimHistoryPopupForm.setDsn(mailbagHistoryVO.getDsn());
			LocalDate reqDeliveryTime = mailbagHistoryVO.getReqDeliveryTime();
			if(reqDeliveryTime!=null){
				claimHistoryPopupForm.setReqDeliveryTime(reqDeliveryTime.toDisplayFormat("dd-MMM-yyyy HH:mm"));
			}
		
		return claimHistoryPopupForm;
	}
	
	

}
