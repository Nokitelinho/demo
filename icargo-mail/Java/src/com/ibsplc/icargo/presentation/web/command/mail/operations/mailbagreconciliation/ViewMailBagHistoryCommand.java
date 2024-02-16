/*
 * ViewMailBagHistoryCommand.java Created on Jul 1 2016
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagreconciliation;

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationDetailsVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailbagReconciliationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailbagReconciliationForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-5991
 *
 */
public class ViewMailBagHistoryCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET_SUCCESS = "show_history_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.MailbagReconciliation";	
	   private static final String HISTORY_SCREEN_ID = "mailtracking.defaults.mailbaghistory";
	  
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ShowHistoryCommand","execute");
	    	  
	    	MailbagReconciliationForm form = 
	    		(MailbagReconciliationForm)invocationContext.screenModel;
		    MailbagReconciliationSession session = 
	    		getScreenSession(MODULE_NAME, "mailtracking.defaults.MailbagReconciliation");
	    	MailBagHistorySession mailBagHistorySession = 
				getScreenSession(MODULE_NAME,"mailtracking.defaults.mailbaghistory");
	    	    	
	    	Page<MailReconciliationDetailsVO> mailReconciliationDetailsVOs = session.getMailReconciliationDetailsVOs();
	    	MailReconciliationDetailsVO selectedvo = null;
	    
	    	String[] selectedRows = form.getRowID();    	
	    	int row = 0;
	    	if(mailReconciliationDetailsVOs!=null){
		    	for (MailReconciliationDetailsVO mailReconciliationDetailsVO : mailReconciliationDetailsVOs) {
		    		if (row == Integer.parseInt(selectedRows[0])) {
		    			selectedvo = mailReconciliationDetailsVO;
						break;
					}
		    		row++;
		    	}
	    	}
	    	log.log(Log.FINE, "selectedvo.getMailbagId() --------->>",
					selectedvo.getMailbagId());
			mailBagHistorySession.setMailBagId(selectedvo.getMailbagId());
	    	form.setStatus("ShowHistoryPopup");
	        	
	    	invocationContext.target = TARGET_SUCCESS;
	       	
	    	log.exiting("ShowHistoryCommand","execute");
	    	
	    }     
	}

