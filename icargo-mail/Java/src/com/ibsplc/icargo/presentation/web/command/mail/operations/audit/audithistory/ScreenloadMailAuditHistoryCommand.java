 /*
 * ScreenloadMailAuditHistoryCommand.java Created onJul 1 2016 by A-5991 
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

import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAuditHistorySession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenloadMailAuditHistoryCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String SCREEN_ID = "mailtracking.defaults.mailaudithistory";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("ScreenLoadCommand","execute");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		MailAuditHistorySession mailAuditHistorySession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		mailAuditHistorySession.setTransactions(getTransactionCodes(logonAttributes));
		mailAuditHistorySession.setAuditableFields(getAuditableFields());
		invocationContext.target = SCREENLOAD_SUCCESS;
		Collection<MailBagAuditHistoryVO> historyVOs =mailAuditHistorySession.getMailBagAuditHistoryVOs();
		if(historyVOs!=null && historyVOs.size()>0){
		log.log(Log.FINE, "historyVOs ---> ", historyVOs);
		}else
			{
			log.log(Log.FINE, "historyVOs is null---> ");	
			}	
		log.exiting("ScreenLoadCommand","execute");  
	}
	
	/**
	 * 
	 * @param logonAttributes
	 * @return
	 */
	private HashMap<String,String> getTransactionCodes(LogonAttributes logonAttributes){
		ArrayList<String> entities = new ArrayList<String>();
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
