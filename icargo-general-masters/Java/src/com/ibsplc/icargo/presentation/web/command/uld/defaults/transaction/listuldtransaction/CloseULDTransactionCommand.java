/*
 * CloseULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class CloseULDTransactionCommand  extends BaseCommand {
    
	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
	
	/**
	 *Target  if success
	 */
	private static final String CLOSE_SUCCESS = "close_success";
    
    /**
  	 * transactionFilterVO
  	 */
  	private static final String KEY_TRANSACTIONFILTERVO = "transactionFilterVO";
  	
  	/**
  	 * transactionListVO
  	 */
  	private static final String KEY_TRANSACTIONLISTVO = "transactionListVO";
  	//added by a-3045 for bug18209 starts
  	private static final String CLOSE_MUCTRACKING = "close_muctracking";
	
	private static final String SCREENID_MUCTRACKING = "uld.defaults.messaging.muctracking";
 	//added by a-3045 for bug18209 ends
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ListULDTransactionCommand","execute");
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    	
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
	 	//added by a-3045 for bug18209 starts
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE_NAME,
				SCREENID_MUCTRACKING);  
		if (("ListLoanBorrowEnq").equals(listULDTransactionForm.getPageURL())) {
			log.log(Log.FINE, "\ninside muctracking condition ");
			mucTrackingSession.setListStatus("noListForm");
			invocationContext.target = CLOSE_MUCTRACKING;
		}else{
		listULDTransactionSession.removeTransactionListVO(KEY_TRANSACTIONLISTVO);
		listULDTransactionSession.removeTransactionFilterVO(KEY_TRANSACTIONFILTERVO);
		listULDTransactionForm.setCloseFlag(FLAG_YES);
	   	listULDTransactionForm.setScreenStatusFlag(
		      ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    invocationContext.target =CLOSE_SUCCESS;
             
         	log.exiting("ListULDTransactionCommand","execute");
		}
	 	//added by a-3045 for bug18209 ends
    }
   
}
