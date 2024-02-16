/*
 * ClearULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ClearULDTransactionCommand  extends BaseCommand {
    
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
	private static final String CLEAR_SUCCESS = "clear_success";
    
    /**
  	 * transactionFilterVO
  	 */
  	private static final String KEY_TRANSACTIONFILTERVO = "transactionFilterVO";
  	
  	/**
  	 * transactionListVO
  	 */
  	private static final String KEY_TRANSACTIONLISTVO = "transactionListVO";
    
  	private static final String RETURN_FLAG = "R";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ClearULDTransactionCommand","execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    	
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
			  
		TransactionFilterVO transactionFilterVO =new TransactionFilterVO();
	    transactionFilterVO.setCompanyCode(listULDTransactionSession.getCompanyCode());
		
		listULDTransactionSession.removeTransactionListVO(KEY_TRANSACTIONLISTVO);
		listULDTransactionSession.removeTransactionFilterVO(KEY_TRANSACTIONFILTERVO);
		listULDTransactionForm.setDisplayPage("1");
		listULDTransactionForm.setUldNum("");
		
		listULDTransactionForm.setLeaseOrReturnFlg(RETURN_FLAG);
		if(logonAttributes.isAirlineUser()){
    		listULDTransactionForm.setEnquiryDisableStatus("airline");
    	}
    	else{
    		transactionFilterVO.setTransactionStationCode(logonAttributes.getAirportCode());
    		listULDTransactionForm.setEnquiryDisableStatus("GHA");
    	}
		
	    listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
	    //added by a-3278 for QF1015 on 25Jul08
	    listULDTransactionSession.setTotalDemmurage(0.0);
	    listULDTransactionSession.setBaseCurrency("");	    
	    listULDTransactionSession.setTotalRecords(-1);
	    //a-3278 ends
		listULDTransactionForm.setScreenStatusFlag(
		      ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		//added by T-1927 for the BUG icrd-30539
		listULDTransactionForm.setListStatus("");
        invocationContext.target =CLEAR_SUCCESS;
             
         	log.exiting("ClearULDTransactionCommand","execute");
		
    }
   
}
