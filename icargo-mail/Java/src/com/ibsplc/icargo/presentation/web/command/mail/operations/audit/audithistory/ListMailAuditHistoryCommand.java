 /*
 * ListMailAuditHistoryCommand.java Created on Jul 1 2016 by A-5991 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.audit.audithistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailAuditHistoryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditFiltersVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAuditHistorySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAuditHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListMailAuditHistoryCommand  extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String SCREEN_ID = "mailtracking.defaults.mailaudithistory";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREENID_ENQUIRY = "shared.audit.auditenquiry";
	private static final String MODULE_NAME_ENQUIRY = "shared.audit";
	private static final String LIST_SUCCESS = "list_success";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				log.entering("ListMailAuditCommand","execute");
				
		MailAuditHistoryForm  mailAuditHistoryForm = (MailAuditHistoryForm)invocationContext.screenModel;
		AuditEnquirySession auditEnquirySession =
				getScreenSession(MODULE_NAME_ENQUIRY, SCREENID_ENQUIRY);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap=auditEnquirySession.getFilterValues();
		AuditFiltersVO auditFilter = auditEnquirySession.getAuditFiltersVO();
		    
		MailAuditHistorySession mailAuditHistorySession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
	//	ScreenloadMailAuditHistoryCommand  screenload = new ScreenloadMailAuditHistoryCommand();
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	Collection<MailBagAuditHistoryVO> historyVOs =new ArrayList<MailBagAuditHistoryVO>();        
    	MailAuditHistoryFilterVO filtervo = null;
    	filtervo = populateFilterVo(mailAuditHistoryForm,logonAttributes,hashMap,auditFilter);
    	try{
    	 historyVOs	 = new MailTrackingDefaultsDelegate().findMailAuditHistoryDetails(filtervo);
		}catch(BusinessDelegateException businessdelegateException){
			invocationContext.addAllError(handleDelegateException(businessdelegateException));
		}
    	
    	mailAuditHistorySession.setMailBagAuditHistoryVOs(historyVOs);
    	mailAuditHistorySession.setTransactions(getTransactionCodes(logonAttributes));
		mailAuditHistorySession.setAuditableFields(getAuditableFields());
		 if(historyVOs==null || historyVOs.size()==0){
    		invocationContext.addError(new ErrorVO("mailtracking.defaults.audit.audithistory.msg.err.norecordsfound"));
    			invocationContext.target = LIST_SUCCESS;
    			return;
    		
    		}
    	
    	invocationContext.target = LIST_SUCCESS;
		log.exiting("ListCommand", "execute");
	}
	
	private MailAuditHistoryFilterVO populateFilterVo(MailAuditHistoryForm form ,LogonAttributes logon,HashMap<String, String> hashMap ,AuditFiltersVO auditFilter ){
		
		MailAuditHistoryFilterVO filtervo = new MailAuditHistoryFilterVO();
		if(auditFilter!=null){
			if("MAL".equals(auditFilter.getChildEntity())){
				String mailID = hashMap.get("MALIDR");
				form.setMailbagId(mailID);
				form.setCompanyCode(logon.getCompanyCode());
		 filtervo.setMailbagId(form.getMailbagId());
		 filtervo.setCompanyCode(logon.getCompanyCode());
		 filtervo.setDestinationExchangeOffice(form.getDestinationExchangeOffice());
		 filtervo.setDsn(form.getDsn());
		 if(form.getTransaction()!=null&&!("").equals(form.getTransaction())){
			 filtervo.setTransaction(form.getTransaction())	; 
		 }
		 if(form.getAuditableField()!=null&&!("").equals(form.getAuditableField())){
			 if("Company Code".equals(form.getAuditableField())){
			 filtervo.setAuditableField("MAILCOMPANYCODE") ;
			 }
		 }
		} 
		}
		return filtervo;
	}
	private HashMap<String,String> getTransactionCodes(LogonAttributes logonAttributes){
		Collection<String> entities = new ArrayList<String>();
		entities.add(MailbagAuditVO.ENTITY_MAIL);
		HashMap<String,String> txnCodeMap = null;
		try{
			txnCodeMap = new MailTrackingDefaultsDelegate().findAuditTransactionCodes(entities, true,logonAttributes.getCompanyCode());
		}catch(BusinessDelegateException businessDelegateException){
			handleDelegateException(businessDelegateException);
		}
    
		return txnCodeMap;
	}        
	
	private ArrayList<String> getAuditableFields(){
		ArrayList<String> auditableFields = new ArrayList<String>();
		String[] allAuditableFields = MailbagAuditVO.AUDITABLEFIELDS;
		for(String field:allAuditableFields){
			auditableFields.add(field);         
		}
		return auditableFields;
	}

    
}
