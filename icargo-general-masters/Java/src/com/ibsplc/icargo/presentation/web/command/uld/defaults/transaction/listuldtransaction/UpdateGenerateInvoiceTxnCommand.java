/*
 * UpdateGenerateInvoiceTxnCommand.java Created on Feb 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class UpdateGenerateInvoiceTxnCommand  extends BaseCommand {
    
	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	
	/**
	 *Target  if success
	 */
    private static final String UPDATE_SUCCESS = "update_success";
    
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("UpdateGenerateInvoiceTxnCommand","execute");
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    	
     	listULDTransactionForm.setMode("G");
     	     	
		listULDTransactionForm.setScreenStatusFlag(
		      ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
        invocationContext.target =UPDATE_SUCCESS;
             
         	log.exiting("UpdateGenerateInvoiceTxnCommand","execute");
         	
         
		
    }
   
}
