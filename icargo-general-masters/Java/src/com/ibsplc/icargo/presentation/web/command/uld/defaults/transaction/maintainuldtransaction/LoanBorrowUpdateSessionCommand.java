/*
 * LoanBorrowUpdateSessionCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;


import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class LoanBorrowUpdateSessionCommand  extends BaseCommand {
    
    /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";
	/**
	 * target String if success
	 */
	private static final String UPDATE_SUCCESS = "update_success";
       
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("LoanBorrowUpdateSessionCommand","execute");
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	MaintainULDTransactionForm maintainULDTransactionForm 
    								= (MaintainULDTransactionForm) invocationContext.screenModel;
        LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
									MODULE_NAME, SCREEN_ID);
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		
		transactionVO.setCompanyCode(loanBorrowULDSession.getCompanyCode());
	    transactionVO.setTransactionType(maintainULDTransactionForm.getTransactionType());
		transactionVO.setTransactionNature(maintainULDTransactionForm.getTransactionNature());
		transactionVO.setTransactionStation(maintainULDTransactionForm.getTransactionStation());
		String txnDate = maintainULDTransactionForm.getTransactionDate();
		String strTxnDate = maintainULDTransactionForm.getTransactionDate();
		String txnTime = maintainULDTransactionForm.getTransactionTime();
		LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		if(!txnTime.contains(":")){
			txnTime=txnTime.concat(":00");
		}
		StringBuilder txndat = new StringBuilder();
		txndat.append(txnDate).append(" ").append(txnTime).append(":00");
		//txnDate = txnDate.concat(" "+txnTime+(":00"));
		
		log.log(Log.FINE, "\n\n\n\nDATE AND TIME", txndat);
		if (txndat.length()>0){
			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			transactionVO.setStrTransactionDate(strTxnDate);
			transactionVO.setTransactionTime(txnTime);
			}else {
				transactionVO.setStrTransactionDate("");
				transactionVO.setTransactionTime("");
			}
		
		transactionVO.setTransactionRemark(maintainULDTransactionForm.getTransactionRemarks());
		if(maintainULDTransactionForm.getPartyType() != null && 
				maintainULDTransactionForm.getPartyType().trim().length() > 0){
			transactionVO.setPartyType(maintainULDTransactionForm.getPartyType().trim());
		}
		if(maintainULDTransactionForm.getFromPartyCode() != null && 
				maintainULDTransactionForm.getFromPartyCode().trim().length() > 0){
			transactionVO.setFromPartyCode(maintainULDTransactionForm.getFromPartyCode().trim().toUpperCase());
		}
		if(maintainULDTransactionForm.getToPartyCode() != null && 
				maintainULDTransactionForm.getToPartyCode().trim().length() > 0){
			transactionVO.setToPartyCode(maintainULDTransactionForm.getToPartyCode().trim().toUpperCase());
		}
		if(maintainULDTransactionForm.getFromPartyName() != null && 
				maintainULDTransactionForm.getFromPartyName().trim().length() > 0){
			transactionVO.setFromPartyName(maintainULDTransactionForm.getFromPartyName().trim());
		}
		if(maintainULDTransactionForm.getToPartyName() != null && 
				maintainULDTransactionForm.getToPartyName().trim().length() > 0){
			transactionVO.setToPartyName(maintainULDTransactionForm.getToPartyName().trim());
		}
		
			transactionVO.setAwbNumber(maintainULDTransactionForm.getAwbNumber());
		
		if("Y".equals(maintainULDTransactionForm.getLoaded())){
			transactionVO.setEmptyStatus("N");
		}else{
			transactionVO.setEmptyStatus("Y");
		}
		loanBorrowULDSession.setTransactionVO(transactionVO);
		
		//to disable txnType in Screen
		maintainULDTransactionForm.setTxnTypeDisable(FLAG_YES);
		
    	maintainULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = UPDATE_SUCCESS;
    	loanBorrowULDSession.setCtrlRcptNo("");
    	loanBorrowULDSession.setCtrlRcptNoPrefix("");
    	log.exiting("LoanBorrowUpdateSessionCommand","execute");
    }
    
}
