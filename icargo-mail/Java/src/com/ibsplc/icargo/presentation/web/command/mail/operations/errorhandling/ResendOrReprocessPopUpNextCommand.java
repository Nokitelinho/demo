package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
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
 * @author A-5991
 *
 */

public class ResendOrReprocessPopUpNextCommand extends BaseCommand{

	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("MAIL  OPERATIONS");

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
	private static final String PROCESS_SUCCESS = "openpopup_next__success";
	
	 private static final String MAILCATEGORY = "mailtracking.defaults.mailcategory";
	 private static final String MAIL_HNI = "mailtracking.defaults.highestnumbermail";
	 private static final String MAIL_RI = "mailtracking.defaults.registeredorinsuredcode";
	 private static final String MAIL_CMPCOD = "mailtracking.defaults.companycode";
		
	
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
		String[] txnIds = mailTrackingErrorHandlingSession.getTxnids();
		   
	    
	     
	     errorHandlingPopUpForm.setTotalViewRecords(String.valueOf(txnIds.length));    
	     errorHandlingPopUpForm.setLastPopupPageNum(errorHandlingPopUpForm.getTotalViewRecords());
		
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(companyCode);
		if(oneTimes!=null){
			Collection<OneTimeVO> catVOs = oneTimes.get(MAILCATEGORY);
			Collection<OneTimeVO> hniVOs = oneTimes.get(MAIL_HNI);
			Collection<OneTimeVO> riVOs = oneTimes.get(MAIL_RI);
			Collection<OneTimeVO> cmpcodVos = oneTimes.get(MAIL_CMPCOD);
			mailTrackingErrorHandlingSession.setOneTimeCat(catVOs);
			mailTrackingErrorHandlingSession.setOneTimeCompanyCode(cmpcodVos);
			
			
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
		
		
		String tranID = txnIds[Integer.parseInt(errorHandlingPopUpForm
				.getDisplayPopupPage()) - 1];
			
			
		// for(String txnIdSelected : txnIds){        
		Object[] txndetail = getTxnParameters(companyCode,tranID);
		log.log(Log.FINE, "size of object array",txndetail.length);
		log.log(Log.FINE, "object array",txndetail);
		int id=0;
		MailUploadVO mailUplVO = new MailUploadVO();//added by A-8353 for ICRD-348042
		for(Object obj : txndetail){	  		
			ArrayList<MailUploadVO> mailBagVOs=null;
			if (obj instanceof Collection)
			{
				mailBagVOs =(ArrayList<MailUploadVO>)obj;
				log.log(Log.FINE, "object mails", mailBagVOs);
			}else if(obj instanceof MailUploadVO){//added by A-8353 for ICRD-348042
				mailUplVO= (MailUploadVO)obj;
				mailBagVOs = new ArrayList<MailUploadVO>();
				mailBagVOs.add(mailUplVO);
			}
					
			if (obj instanceof String)
			{
				String scannedport=(String)obj;
				log.log(Log.FINE, "object mails", scannedport);          
			}
		
			if(mailBagVOs!=null && mailBagVOs.size()>0)         
			{
			for (MailUploadVO mailUploadVO : mailBagVOs) {
				String function = mailUploadVO.getScanType();    
				errorHandlingPopUpForm.setSelectedtxnid(tranID);
			errorHandlingPopUpForm.setFunctionType(function);
			log.log(Log.FINE, "function",function);
					if (("DLV").equals(function) || ("OFL").equals(function) || ("RTN").equals(function) || ("DMG").equals(function)) {
				log.log(Log.FINE, " in side if looop",function);
				
				//Modified as part of code quality work by A-7531 starts
				if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsFlightCarrierCodeChanged())){
					
				errorHandlingPopUpForm.setFlightCarrierCode("");
				}
			
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsFlightNumberChanged())){
	
				errorHandlingPopUpForm.setFlightNumber("");
				}
				
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsDestinationChanged())){
	
				errorHandlingPopUpForm.setDestination("");
}
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getFlightDate())){
				errorHandlingPopUpForm.setFlightDate("");
	
}
				
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsContainerNumberChanged())){
				errorHandlingPopUpForm.setContainer("");
}
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsPouChanged())){
				errorHandlingPopUpForm.setPou("");
}
				
//Modified as part of code quality work by A-7531 ends
				log.log(Log.FINE, " exiting if looop",function);
					} 	 
//Added 'ACP' function type also as part of ICRD-156832
else if (("ARR").equals(function) || ("EXP").equals(function) || ("ACP").equals(function)) {
						if (mailUploadVO.getMailCompanyCode() != null && mailUploadVO.getMailCompanyCode().trim().length() > 0) {
							errorHandlingPopUpForm.setMailCompanyCode(mailUploadVO.getMailCompanyCode());

			}
					errorHandlingPopUpForm.setPou("");
				}
					if (mailUploadVO.getCarrierCode() != null && mailUploadVO.getCarrierCode().trim().length() > 0) {
						
						//Modified as part of code quality work by A-7531 
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsFlightCarrierCodeChanged())){
							errorHandlingPopUpForm.setFlightCarrierCode(mailUploadVO.getCarrierCode());
						}
					}
					if (mailUploadVO.getFlightNumber() != null && mailUploadVO.getFlightNumber().trim().length() > 0 && !mailUploadVO.getFlightNumber().equals(DEST_FLT)) {
					
						   
						
						//Modified as part of code quality work by A-7531 
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsFlightNumberChanged())){
							errorHandlingPopUpForm.setFlightNumber(mailUploadVO.getFlightNumber());
						}
					}
					  
						
					
					 //Added by a-4810 for icrd-84794
					if (("ACP").equals(function)) {
						if (mailUploadVO.getDestination() != null && mailUploadVO.getDestination().trim().length() > 0) {
							if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsDestinationChanged())){
								errorHandlingPopUpForm.setDestination(mailUploadVO.getDestination());
							}
						}
						if (mailUploadVO.getToPOU() != null && mailUploadVO.getToPOU().trim().length() > 0) {
						
							
					
							
							//Modified as part of code quality work by A-7531 					
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsPouChanged())){
	
							errorHandlingPopUpForm.setPou(mailUploadVO.getContainerPOU());
							}
						}
					}	  

               if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(function))        
					{
						errorHandlingPopUpForm.setPou(mailUploadVO.getScannedPort());    
					}
					if (("ACP").equals(function) || ("OFL").equals(function) || ("ARR").equals(function) || ("EXP").equals(function)) {
						if (mailUploadVO.getContainerNumber() != null && mailUploadVO.getContainerNumber().trim().length() > 0) {
					
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsContainerNumberChanged())){
								errorHandlingPopUpForm.setContainer(mailUploadVO.getContainerNumber());
					  }
					
					  }
					}
					if (mailUploadVO.getFlightDate() != null) {
						
						//Modified as part of code quality work by A-7531 	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsFlightDateChanged())){
							errorHandlingPopUpForm.setFlightDate(mailUploadVO.getFlightDate().toDisplayDateOnlyFormat());
						}
					}
					if (mailUploadVO.getContainerType().trim().length() > 0 && ("B").equals(mailUploadVO.getContainerType())) {
					
						
						//Modified as part of code quality work by A-7531 		
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsContainerTypeChanged())){
	
				errorHandlingPopUpForm.setBulk("Y");
//Added by A-5945 for ICRD-141814
			errorHandlingPopUpForm.setBarrowCheck(true);  
						}
			
					
			
					} else {
						
						//Modified as part of code quality work by A-7531 			
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsContainerTypeChanged())){
							
			errorHandlingPopUpForm.setBulk("N"); 
//Added by A-5945 for ICRD-141814
			errorHandlingPopUpForm.setBarrowCheck(false); 
						}
	
						
					}	 
//Added by A-5945 for ICRD-88905 starts
					if (("").equals(mailUploadVO.getContainerNumber()) && mailUploadVO.getContainerNumber().trim().length() == 0) {
						if(mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().length()<29 ){
							
							//Modified as part of code quality work by A-7531 		
							if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsContainerNumberChanged())){
								
							 errorHandlingPopUpForm.setContainer(mailUploadVO.getMailTag());
					}
						}
					}	 
	//Added by A-5945 for ICRd-88905 ends
					if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() == 29) {
						String mailBagId = mailUploadVO.getMailTag();
						errorHandlingPopUpForm.setMailBag(mailBagId);//added by A-8353 for ICRD-348042
						//Modified as part of code quality work by A-7531 starts	
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsOOEChanged()) &&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setOoe(mailBagId.substring(0, 6));
						}
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsDOEChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setDoe(mailBagId.substring(6, 12));
						}
							
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsCategoryChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setCategory(mailBagId.substring(12, 13));
						}
							
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsSubClassChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setSubclass(mailBagId.substring(13, 15));
						}
						
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsYearChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setYear((mailBagId.substring(15, 16)));
						}

						
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsDsnChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setDsn(mailBagId.substring(16, 20));
						}
							
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsRsnChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setRsn(mailBagId.substring(20, 23));
						}
							
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsHniChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setHni(mailBagId.substring(23, 24));
						}
							
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsRiChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setRi(mailBagId.substring(24, 25));
						}
							
						if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsWeightChanged())&&(mailBagId.trim().length() == 29)){
							errorHandlingPopUpForm.setWeight(mailBagId.substring(25, 29));
						}
					} else {
						

if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsOOEChanged())){
							errorHandlingPopUpForm.setOoe("");
						}
	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsDOEChanged())){
				errorHandlingPopUpForm.setDoe("");
}
	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsCategoryChanged())){
				errorHandlingPopUpForm.setCategory("");
}
					
				if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsSubClassChanged())){
				errorHandlingPopUpForm.setSubclass("");
				}
					
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsYearChanged())){
				errorHandlingPopUpForm.setYear("");
				}
	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsDsnChanged())){
				errorHandlingPopUpForm.setDsn("");
}
	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsRsnChanged())){
				errorHandlingPopUpForm.setRsn("");
}
	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsHniChanged())){
				errorHandlingPopUpForm.setHni("");
}
	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsRiChanged())){
				errorHandlingPopUpForm.setRi("");
}
	
if(!MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsWeightChanged())){
				errorHandlingPopUpForm.setWeight("");    
}
				    
						
					}
					//Added by A-5945 for ICRD-113473 starts
					if(mailUploadVO.getFromCarrierCode()!=null && mailUploadVO.getFromCarrierCode().trim().length()>0){
						if(MailConstantsVO.FLAG_YES.equals(errorHandlingPopUpForm.getIsTransferCarrierChanged())){
						errorHandlingPopUpForm.setTransferCarrier(mailUploadVO.getFromCarrierCode());
						}
						
					}else{
						errorHandlingPopUpForm.setTransferCarrier(mailUploadVO.getFromCarrierCode());       
					}   
					   //Added by A-5945 for ICRD-113473 ends

				
					//Modified as part of code quality work by A-7531 ends
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
		fieldValues.add(MAIL_CMPCOD);
		
			oneTimes1 = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldValues);
	}catch(BusinessDelegateException businessDelegateException){
		errors = handleDelegateException(businessDelegateException);
	}
	return oneTimes1;
}
}