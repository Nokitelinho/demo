/*
 * DeleteULDBorrowDetailsCommand.java  Created on Jan 12,07
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */


public class DeleteULDBorrowDetailsCommand extends BaseCommand {

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
	private static final String DELETE_SUCCESS = "delete_success";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ArrayList<ULDTransactionDetailsVO> selectedULDs=loanBorrowULDSession.getSelectedULDColl();
	    if(selectedULDs!=null && selectedULDs.size()>0){
	    ArrayList<ULDTransactionDetailsVO> tmpSelectedULDs=new ArrayList<ULDTransactionDetailsVO>();				   
	    for(ULDTransactionDetailsVO uldvo:selectedULDs){
		   if(!uldvo.getUldNumber().equals(maintainULDTransactionForm.getBorrowUldNum().toUpperCase())){
			   tmpSelectedULDs.add(uldvo);
		   }
	   }
	    loanBorrowULDSession.setSelectedULDColl(tmpSelectedULDs);
	    }
	    maintainULDTransactionForm.setBorrowUldNum("");
		maintainULDTransactionForm.setBorrowPopupClose(FLAG_NO);	 
		maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD");
		invocationContext.target = DELETE_SUCCESS;
	}
}
