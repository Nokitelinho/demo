package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailWebserviceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailTrackingErrorHandlingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5945
 *
 */

public class ResendOrReprocessPopUpSATSCommand extends BaseCommand{

	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("MAIL  DEFAULTS");

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
	private static final String PROCESS_SUCCESS = "openpopup_success";
	private static final String MAILCATEGORY = "mailtracking.defaults.mailcategory";
	private static final String MAIL_HNI = "mailtracking.defaults.highestnumbermail";
	private static final String MAIL_RI = "mailtracking.defaults.registeredorinsuredcode";

		
	
   public void execute(InvocationContext invocationContext)
	                         throws CommandInvocationException {
	   
	   log.entering("ResendOrReprocessPopupCommand", "execute");		
	   ErrorHandlingPopUpForm errorHandlingPopUpForm = (ErrorHandlingPopUpForm) invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		
		String txnId=errorHandlingPopUpForm.getSelectedtxnid();
		String DEST_FLT="-1"; 
		log.log(Log.FINE, "trxnid",txnId);
		List<String> sortedOnetimes ;
		MailTrackingErrorHandlingSession mailTrackingErrorHandlingSession= (MailTrackingErrorHandlingSession) getScreenSession(
				MODULE_NAME, SCREEN_ID)	;
		mailTrackingErrorHandlingSession.setTxnid(txnId);
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(companyCode);
		if(oneTimes!=null){
			Collection<OneTimeVO> catVOs = oneTimes.get(MAILCATEGORY);
			Collection<OneTimeVO> hniVOs = oneTimes.get(MAIL_HNI);
			Collection<OneTimeVO> riVOs = oneTimes.get(MAIL_RI);
			mailTrackingErrorHandlingSession.setOneTimeCat(catVOs);
			
			
			
			if(hniVOs!=null && !hniVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO hniVo: hniVOs){
					sortedOnetimes.add(hniVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);
			
			
			int i=0;
			for(OneTimeVO hniVo: hniVOs){
				hniVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			if(riVOs!=null && !riVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO riVo: riVOs){
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);
			
			
			int i=0;
			for(OneTimeVO riVo: riVOs){
				riVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			mailTrackingErrorHandlingSession.setOneTimeHni(hniVOs);
			mailTrackingErrorHandlingSession.setOneTimeRi(riVOs);
		}	
		Object[] txndetail = getTxnParameters(companyCode,txnId);
		log.log(Log.FINE, "size of object array",txndetail.length);
		log.log(Log.FINE, "object array",txndetail);
		
		for(Object obj : txndetail){			
			Collection<MailWebserviceVO> mailBagVOs=null;
			if (obj instanceof Collection)
			{
				mailBagVOs =(ArrayList<MailWebserviceVO>)obj;
				log.log(Log.FINE, "object mails", mailBagVOs);
			}
			String scannedport="";	
			if (obj instanceof String)
			{
				 scannedport=(String)obj;
				log.log(Log.FINE, "object mails", scannedport);          
			}
			Collection<MailUploadVO> mailUploadVOs=null;  
			MailWebserviceVO mailWebserviceVO=null;
			if(mailBagVOs!=null){
				mailWebserviceVO=mailBagVOs.iterator().next();
				 String errorFromMapping="";
					StringBuilder errorString = new StringBuilder();
			try {      
				mailUploadVOs = new MailTrackingDefaultsDelegate().createMailScanVOSForErrorStamping(mailBagVOs, mailWebserviceVO.getScanningPort(), errorString, errorFromMapping);
						
				//Modified as part of code quality work by A-7531 starts	
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
				
				//Modified as part of code quality work by A-7531 ends	
			}
			}
			if(mailBagVOs!=null && mailBagVOs.size()>0)         
			{
			for (MailUploadVO mailUploadVO : mailUploadVOs) {
				if(mailUploadVO.getScanType()==null && mailWebserviceVO!=null){
					mailUploadVO.setScanType(mailWebserviceVO.getScanType());
				}
				String function = mailUploadVO.getScanType();

				errorHandlingPopUpForm.setFunctionType(function);
				log.log(Log.FINE, "function",function);
					if (("DLV").equals(function) || ("OFL").equals(function) || ("RTN").equals(function) || ("DMG").equals(function)) {
					log.log(Log.FINE, " in side if looop",function);
					errorHandlingPopUpForm.setFlightCarrierCode(null);
					errorHandlingPopUpForm.setFlightNumber(null);
					errorHandlingPopUpForm.setDestination(null);
					errorHandlingPopUpForm.setFlightDate(null);
					errorHandlingPopUpForm.setContainer(null);
					errorHandlingPopUpForm.setPou(null);
					
					log.log(Log.FINE, " exiting if looop",function);
					} else if (("ARR").equals(function) || ("EXP").equals(function)) {
	             
						errorHandlingPopUpForm.setPou(null);
					}
					if (mailUploadVO.getCarrierCode() != null && mailUploadVO.getCarrierCode().trim().length() > 0) {
						errorHandlingPopUpForm.setFlightCarrierCode(mailUploadVO.getCarrierCode());
					}
					if (mailUploadVO.getFlightNumber() != null && mailUploadVO.getFlightNumber().trim().length() > 0 && !mailUploadVO.getFlightNumber().equals(DEST_FLT)) {
						errorHandlingPopUpForm.setFlightNumber(mailUploadVO.getFlightNumber());
					}
							//Added by a-4810 for icrd-84794
						if (("ACP").equals(function)) {
						if (mailUploadVO.getDestination() != null && mailUploadVO.getDestination().trim().length() > 0) {
							errorHandlingPopUpForm.setDestination(mailUploadVO.getDestination());
				         	}  
						if (mailUploadVO.getToPOU() != null && mailUploadVO.getToPOU().trim().length() > 0) {
								errorHandlingPopUpForm.setPou(mailUploadVO.getContainerPOU());
							}
						}  
						if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(function))        
						{
							errorHandlingPopUpForm.setPou(mailUploadVO.getScannedPort());      
						}
					if (("ACP").equals(function) || ("OFL").equals(function) || ("ARR").equals(function) || ("EXP").equals(function)) {
						if (mailUploadVO.getContainerNumber() != null && mailUploadVO.getContainerNumber().trim().length() > 0) {
							errorHandlingPopUpForm.setContainer(mailUploadVO.getContainerNumber());
						  }
						}
						if (mailUploadVO.getFlightDate() != null) {
						errorHandlingPopUpForm.setFlightDate(mailUploadVO.getFlightDate().toDisplayDateOnlyFormat());
				}
					if (mailUploadVO.getContainerType().trim().length() > 0 && ("B").equals(mailUploadVO.getContainerType())) {
					errorHandlingPopUpForm.setBulk("Y");
				
						} else {
				errorHandlingPopUpForm.setBulk("N");
						}	
//Added by A-5945 for ICRD-88905 starts
					if (("").equals(mailUploadVO.getContainerNumber()) && mailUploadVO.getContainerNumber().trim().length() == 0) {
							if(mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().length()<29 ){
								 errorHandlingPopUpForm.setContainer(mailUploadVO.getMailTag());
						}
						}		 
//Added by A-5945 for ICRd-88905 ends
					if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() == 29) {
							String mailBagId = mailUploadVO.getMailTag();
						errorHandlingPopUpForm.setOoe(mailBagId.substring(0, 6));
						errorHandlingPopUpForm.setDoe(mailBagId.substring(6, 12));
						errorHandlingPopUpForm.setCategory(mailBagId.substring(12, 13));
						errorHandlingPopUpForm.setSubclass(mailBagId.substring(13, 15));
						errorHandlingPopUpForm.setYear((mailBagId.substring(15, 16)));
						errorHandlingPopUpForm.setDsn(mailBagId.substring(16, 20));
						errorHandlingPopUpForm.setRsn(mailBagId.substring(20, 23));
						errorHandlingPopUpForm.setHni(mailBagId.substring(23, 24));
						errorHandlingPopUpForm.setRi(mailBagId.substring(24, 25));
						errorHandlingPopUpForm.setWeight(mailBagId.substring(25, 29));
					} else {
						errorHandlingPopUpForm.setOoe("");

					errorHandlingPopUpForm.setDoe("");
					errorHandlingPopUpForm.setCategory("");
					errorHandlingPopUpForm.setSubclass("");

					errorHandlingPopUpForm.setYear("");
					errorHandlingPopUpForm.setDsn("");
					errorHandlingPopUpForm.setRsn("");
					errorHandlingPopUpForm.setHni("");
					errorHandlingPopUpForm.setRi("");
					errorHandlingPopUpForm.setWeight("");
					}
					mailTrackingErrorHandlingSession.setScannedDetails(mailUploadVO);
				}
					

				}  

		}

		log.exiting("ResendOrReprocessPopupCommand", "execute");    
		invocationContext.target = PROCESS_SUCCESS;
	
}

  











private  Object[] getTxnParameters(String companyCode,String txnId) {
		
	   Object[] txndetails1 = null;
		try {
						 			
			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
			txndetails1 = mailTrackingDefaultsDelegate.getTxnParameters(
					companyCode, txnId);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}
		return txndetails1;
	}

private  Map<String, Collection<OneTimeVO>> findOneTimeDescription(
		String companyCode) {
	Map<String, Collection<OneTimeVO>> oneTimes1 = null;
	Collection<ErrorVO> errors = null;
	try{
		Collection<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(MAILCATEGORY);
		fieldValues.add(MAIL_HNI);
		fieldValues.add(MAIL_RI);
		
			oneTimes1 = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldValues);
	}catch(BusinessDelegateException businessDelegateException){
		errors = handleDelegateException(businessDelegateException);
	}
	return oneTimes1;
}

}