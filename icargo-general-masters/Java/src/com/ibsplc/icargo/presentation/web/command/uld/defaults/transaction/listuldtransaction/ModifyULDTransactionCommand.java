/*
 * ModifyULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ModifyULDTransactionCommand  extends BaseCommand {
    
	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
    
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME_TWO = "uld.defaults";
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID_TWO = "uld.defaults.loanborrowuld";
	
	/*
	 * target String if success
	 */
	private static final String MODIFY_SUCCESS = "modify_success";
	
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenloadModifyULDTransactionCommand","execute");
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;    	
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME_TWO, SCREEN_ID_TWO);		
		ULDTransactionDetailsVO uLDTxnDetailsVO = listULDTransactionSession.getULDTransactionDetailsVO();
		
		if(!("").equalsIgnoreCase(listULDTransactionForm.getModDuration()) && listULDTransactionForm.getModULDNo() != null){
			uLDTxnDetailsVO.setTransationPeriod(listULDTransactionForm.getModDuration());
	   }else {
		    uLDTxnDetailsVO.setTransationPeriod("0");
	   }
		log.log(Log.FINE, "\n\n\n\n uLDTxnDetailsVO in modify uld trans  ",
				uLDTxnDetailsVO);
		LocalDate ldte = new LocalDate(uLDTxnDetailsVO.getTransactionStationCode(),Location.ARP,false);
		
    	if(!("").equalsIgnoreCase(listULDTransactionForm.getModTxnDate()) && listULDTransactionForm.getModTxnDate() != null){
    		uLDTxnDetailsVO.setTransactionDate(ldte.setDate(listULDTransactionForm.getModTxnDate()));
    		uLDTxnDetailsVO.setStrTxnDate(listULDTransactionForm.getModTxnDate());
	    }else {
	    	uLDTxnDetailsVO.setStrTxnDate("");
	    }
	  if(!("").equalsIgnoreCase(listULDTransactionForm.getModTxnRemarks()) && listULDTransactionForm.getModTxnRemarks() != null){
		uLDTxnDetailsVO.setTransactionRemark(listULDTransactionForm.getModTxnRemarks());
	  }
		
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();	
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = transactionVO.getUldTransactionDetailsVOs();
		Collection<ULDTransactionDetailsVO> uldTxnDtlsVOs = new ArrayList<ULDTransactionDetailsVO>();
		
		if (uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() != 0) {
			for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTxnDetailsVOs) {
				if(uldTransactionDetailsVO.getSelectNumber() == uLDTxnDetailsVO.getSelectNumber() ) {
					uldTxnDtlsVOs.add(uLDTxnDetailsVO);
				}else {
					uldTxnDtlsVOs.add(uldTransactionDetailsVO);
				}
				
			}
		}
		
		transactionVO.setUldTransactionDetailsVOs(uldTxnDtlsVOs);
		loanBorrowULDSession.setTransactionVO(transactionVO);				
		listULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target =MODIFY_SUCCESS;
        
    }


}
