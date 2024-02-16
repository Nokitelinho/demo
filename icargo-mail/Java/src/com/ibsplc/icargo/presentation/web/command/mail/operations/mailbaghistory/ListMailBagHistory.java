/*
 * ListMailBagHistory.java Created on July 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbaghistory;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListMailBagHistory extends BaseCommand {
	
	private static final String SUCCESS = "list_success";
	
	private Log log = LogFactory.getLogger("ListMailBagHistory");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailbaghistory";
	
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	
	private static final String MAILBAG_NO_DATA_FOUND = "mailtracking.defaults.mbHistory.msg.err.nodatafound";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	MailBagHistoryForm mailBagHistoryForm =
							(MailBagHistoryForm)invocationContext.screenModel;
    	MailBagHistorySession mailBagHistorySession = 
    									getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    Collection<ErrorVO> errors = null;
	    
	    SharedDefaultsDelegate sharedDefaultsDelegate =
												new SharedDefaultsDelegate();
	    Map hashMap = null;

	    Collection<String> oneTimeList = new ArrayList<String>();

	    oneTimeList.add(MAIL_STATUS);

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
	    
	    MailTrackingDefaultsDelegate delegate = 
	    	new MailTrackingDefaultsDelegate();
	    String mailBagId="";
	    long mailSeqNumber=0l;
	    boolean mailValidationReq=false;
	    boolean isInvalidMailbag=true;
	    if(mailBagHistoryForm.getMailbagId() == null ||
	    		mailBagHistoryForm.getMailbagId().trim().length() == 0){
	    	if(mailBagHistorySession.getMailSequenceNumber()!=null &&
	    			mailBagHistorySession.getMailSequenceNumber().size()>0){
	    		mailSeqNumber=mailBagHistorySession.getMailSequenceNumber().get(0);
	    		mailBagHistoryForm.setDisplayPopupPage("0");
    			mailBagHistoryForm.setTotalViewRecords(String.valueOf(mailBagHistorySession.getMailSequenceNumber().size()));
	    	}else{
	    		mailBagHistorySession.removeAllAttributes();
	    		mailBagHistoryForm.setMailbagId("");
	    		mailBagHistoryForm.setReqDeliveryTime("");
	    		mailBagHistoryForm.setBtnDisableReq("Y");
	    		mailBagHistorySession.setEnquiryFlag("yes"); //Added by A-8164 for ICRD-260365
	    		invocationContext.target = SUCCESS;
	    		return;
	    	}
	    }
	    else{
	    	mailBagId= mailBagHistoryForm.getMailbagId();
	    	mailValidationReq=true;
	    	if(mailBagId.length()!=29){
	    		mailValidationReq=false;
	    	}
	    }
	    if(mailValidationReq){
	    	mailBagHistoryForm.setDisplayPopupPage("0");
	    	mailBagHistoryForm.setTotalViewRecords("1");
	    	MailbagVO mailVO = new MailbagVO();
	    	Collection<MailbagVO> mailbagVos = new ArrayList<MailbagVO>();
	    	try{
		    	mailVO.setCompanyCode(companyCode);
		    	mailVO.setDoe(mailBagId.substring(6, 12));
		    	mailVO.setDespatchSerialNumber(mailBagId.substring(16, 20));
		    	mailVO.setMailbagId(mailBagId.trim());
		    	mailVO.setMailSubclass(mailBagId.substring(13, 15));
		    	mailVO.setMailCategoryCode(mailBagId.substring(12, 13));
		    	mailVO.setOoe(mailBagId.substring(0, 6));
		    	mailVO.setYear(Integer.parseInt(mailBagId.substring(15, 16)));
		    	mailVO.setOperationalFlag("I");
		    	mailbagVos.add(mailVO);
		    	isInvalidMailbag = delegate.validateMailBags(mailbagVos);
	    	}catch(Exception exception){
	    		log.log(Log.FINEST, "Invalid Mailbag entered in Mail History Popup", isInvalidMailbag);
	    		isInvalidMailbag = false;
	    	}
	    }
	    if(!isInvalidMailbag){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.mbHistory.msg.err.invalidmailbag");
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			mailBagHistoryForm.setMailbagId("");
			invocationContext.addAllError(errors);
			invocationContext.target = SUCCESS;
			return;
}
	    //Added by A-8164 for ICRD-322758 starts
	    ArrayList<MailbagVO> mailBagVOs=new ArrayList<MailbagVO>();
	    try{
			mailBagVOs = 
			delegate.findDuplicateMailbag(companyCode,mailBagId);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
	    
	    if(mailBagVOs!=null && !mailBagVOs.isEmpty()){
			mailBagHistorySession.setMailBagVos(mailBagVOs);
			mailBagHistoryForm.setTotalViewRecords(Integer.toString(mailBagVOs.size()));
			mailBagHistoryForm.setDisplayPopupPage("0");
			mailBagId=mailBagVOs.get(0).getMailbagId();
			mailSeqNumber=mailBagVOs.get(0).getMailSequenceNumber();
			mailBagHistoryForm.setMailbagDuplicatePresent(true);
	    }
	  //Added by A-8164 for ICRD-322758 ends
	    
log.log(Log.FINEST, "\n\n *******mailBagId***********", mailBagId);
		Collection<MailbagHistoryVO> mailBagHistoryVOs = 
	    	new ArrayList<MailbagHistoryVO>();
	    
	    try{
	    	mailBagHistoryVOs = 
	    		delegate.findMailbagHistories(companyCode,mailBagId,mailSeqNumber);
	    }catch(BusinessDelegateException businessDelegateException){
	    	errors = handleDelegateException(businessDelegateException);
	    }
	    
	    log.log(Log.FINE, "\n\n mailBagHistoryVOs---------->  ",
				mailBagHistoryVOs);
	    if(mailBagHistoryVOs==null||mailBagHistoryVOs.size()==0){
	    	ErrorVO errorVO = new ErrorVO(
	    			MAILBAG_NO_DATA_FOUND);
	    			errors = new ArrayList<ErrorVO>();
	    			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
	    			errors.add(errorVO);
	    			mailBagHistoryForm.setMailbagId("");
	    			invocationContext.addAllError(errors);
	    			invocationContext.target = SUCCESS;
	    			return;
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
	    			mailBagHistoryForm.setMailSubclass(mailbagHistoryVO.getMailSubclass()); //Added by A-8164 for ICRD-257677
	    			mailBagHistoryForm.setYear(
	    					new StringBuffer(yearPrefix).append(mailbagHistoryVO.getYear()).toString());
	    			mailBagHistoryForm.setDsn(mailbagHistoryVO.getDsn());
	    			mailBagHistoryForm.setRsn(mailbagHistoryVO.getRsn());
	    			//mailBagHistoryForm.setWeight(String.valueOf(mailbagHistoryVO.getWeight()));
	    			mailBagHistoryForm.setWeightMeasure(mailbagHistoryVO.getWeight());//Added by A-7929 as part of ICRD-249964
	    			mailBagHistoryForm.setActualWeightMeasure(mailbagHistoryVO.getActualWeight()); //Added by A-8164 for ICRD-323182
	    			mailBagHistoryForm.setMailbagId(mailbagHistoryVO.getMailbagId());
	    			//Added for ICRD-214795 starts
	    			LocalDate reqDeliveryTime = mailbagHistoryVO.getReqDeliveryTime();
	    			if(reqDeliveryTime!=null){
	    			mailBagHistoryForm.setReqDeliveryTime(reqDeliveryTime.toDisplayFormat("dd-MMM-yyyy HH:mm"));
	    			}
	    			//Added for ICRD-214795 ends
	    			break;
	    	}
	    }
		/*String ooe = ((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getOriginExchangeOffice();
		String doe = ((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getDestinationExchangeOffice();
		String catagory = ((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getMailCategoryCode();
		String mailClass = ((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getMailClass();
		String year = String.valueOf(((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getYear());
		String dsn = ((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getDsn();
		String rsn = ((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getRsn();
		String weight = "";
		weight = String.valueOf(((ArrayList<MailbagHistoryVO>) mailBagHistoryVOs).get(0).getWeight());*/
		/*if(Integer.parseInt(mailBagId.substring(25,26)) == 0){
			if(Integer.parseInt(mailBagId.substring(26,27)) == 0){
				weight = new StringBuffer(mailBagId.substring(27,28))
								.append(".").append(mailBagId.substring(28))
																	.toString();
			}else{
				weight = new StringBuffer(mailBagId.substring(26,28))
								.append(".").append(mailBagId.substring(28))
																	.toString();
			}
		}else{
			weight = new StringBuffer(mailBagId.substring(25,28))
								.append(".").append(mailBagId.substring(28))
																	.toString();
		}*/
		
		  //Added as a part of ICRD-197419 by a-7540 for setting the values into form 
	    if(mailBagHistoryVOs!=null && !mailBagHistoryVOs.isEmpty())
	    {
	    MailbagHistoryVO mailbagHistoryVO=mailBagHistoryVOs.iterator().next();
	    mailBagHistoryForm.setMailRemarks(mailbagHistoryVO.getMailRemarks());
	    }
		
		/*String yearPrefix = new LocalDate
			(logonAttributes.getAirportCode(),ARP,false).
										toDisplayFormat("yyyy").substring(0,3);
		
		log.log(Log.FINE, "\n\n yearPrefix---------->  ", yearPrefix);
		mailBagHistoryForm.setOoe(ooe);
		mailBagHistoryForm.setDoe(doe);
		mailBagHistoryForm.setCatogory(catagory);
		mailBagHistoryForm.setMailClass(mailClass);
		mailBagHistoryForm.setYear(
				new StringBuffer(yearPrefix).append(year).toString());
		mailBagHistoryForm.setDsn(dsn);
		mailBagHistoryForm.setRsn(rsn);
		mailBagHistoryForm.setWeight(weight);
		mailBagHistoryForm.setMailbagId(mailBagId);*/
		
		mailBagHistorySession.setMailBagHistoryVOs(mailBagHistoryVOs);
		mailBagHistorySession.setOneTimeStatus(oneTimeMailStatus);
		
		mailBagHistoryForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		mailBagHistoryForm.setBtnDisableReq("");
		invocationContext.target = SUCCESS;

	}

}
