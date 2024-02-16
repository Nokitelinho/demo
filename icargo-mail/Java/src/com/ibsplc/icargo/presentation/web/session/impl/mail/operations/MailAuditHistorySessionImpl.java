
 /*
 * MailAuditHistorySessionImpl.java Created on Nov 5 2015 by A-5945 for ICRD-119569
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAuditHistorySession;

public class MailAuditHistorySessionImpl    extends AbstractScreenSession  implements MailAuditHistorySession {

	
	private static final String SCREEN_ID = "mailtracking.defaults.mailaudithistory";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_TXNCODES = "transactioncodes";
	private static final String KEY_AUDITABLEFIELDS = "auditablefields";
	private static final String KEY_MAILAUDITHISTORYVOS = "mailaudithistoryvos";
	  /**
     * method to get modulename
     * @return MODULE_NAME
     */
    public String getModuleName() {
        return MODULE_NAME;
    }
    /**
     * method to get screen id
     * @return SCREEN_ID
     */
    public String getScreenID() {
        return SCREEN_ID;
    }
    /**
     * 
     * @return
     */
    public HashMap<String,String> getTransactions(){
    	return (HashMap<String,String>)getAttribute(KEY_TXNCODES);
    }
     /**
 	 * Method for setting auditDetailsVOs to session
 	 * @param transactionCodes
 	 */
    public void setTransactions(HashMap<String,String> transactionCodes){
    	setAttribute(KEY_TXNCODES,(HashMap<String,String>)transactionCodes);
    }

    /**
     * 
     * @return
     */
     public Collection<String> getAuditableFields(){
    	 return (Collection<String>)getAttribute(KEY_AUDITABLEFIELDS);
     }
     /**
 	 * Method for setting auditDetailsVOs to session
 	 * @param auditableFields
 	 */
     public void setAuditableFields(Collection<String> auditableFields){
    	 setAttribute(KEY_AUDITABLEFIELDS,(ArrayList<String>)auditableFields);
     }
	
	public Collection<MailBagAuditHistoryVO> getMailBagAuditHistoryVOs() {
		return (Collection<MailBagAuditHistoryVO>)getAttribute(KEY_MAILAUDITHISTORYVOS);
	}

	public void setMailBagAuditHistoryVOs(
			Collection<MailBagAuditHistoryVO> mailBagAuditHistoryVOs) {
		setAttribute(KEY_MAILAUDITHISTORYVOS,(ArrayList<MailBagAuditHistoryVO>)mailBagAuditHistoryVOs);
		
	}
	

}
