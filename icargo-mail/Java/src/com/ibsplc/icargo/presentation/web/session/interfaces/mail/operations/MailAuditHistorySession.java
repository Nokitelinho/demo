 /*
 * MailAuditHistorySession.java Created on Nov 5 2015 by A-5945 for ICRD-119569
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

public interface MailAuditHistorySession extends ScreenSession {
	


	
		
		  /**
	     * 
	     * @return
	     */
	     HashMap<String,String> getTransactions();
	     /**
	 	 * Method for setting auditDetailsVOs to session
	 	 * @param transactionCodes
	 	 */
	     void setTransactions(HashMap<String,String> transactionCodes);
	     
	     /**
	      * 
	      * @return
	      */
	      Collection<String> getAuditableFields();
	      /**
	  	 * Method for setting auditDetailsVOs to session
	  	 * @param auditableFields
	  	 */
	      void setAuditableFields(Collection<String> auditableFields);
	      
	      Collection<MailBagAuditHistoryVO> getMailBagAuditHistoryVOs();
	      
	      void setMailBagAuditHistoryVOs(Collection<MailBagAuditHistoryVO> mailBagAuditHistoryVOs);

	}



