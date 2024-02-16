/*
 * ScreenLoadAddAccessoryDetailsCommand.java Created on Feb 9,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadAddAccessoryDetailsCommand  extends BaseCommand {
    
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
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
      
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
    	/*MaintainULDTransactionForm maintainULDTransactionForm =
    		(MaintainULDTransactionForm) invocationContext.screenModel;*/
    	LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
    	TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		loanBorrowULDSession.setTransactionVO(transactionVO);
		//Commented by A-3045 for CR QF1016 starts
		/*Collection<AccessoryTransactionVO> accessoryTransactionVOs  
        = transactionVO.getAccessoryTransactionVOs();*/
		/*//		IF MODIFY MODE
		if(maintainULDTransactionForm.getModifyMode()!=null &&
				maintainULDTransactionForm.getModifyMode().equals("MODIFYACC")){
			for (AccessoryTransactionVO accVO : accessoryTransactionVOs) {
				if (accVO.getAccessoryCode().equals(maintainULDTransactionForm.getModifyAcc().toUpperCase())) {
			
				log.log(Log.FINE, "\n\n\n\n SCREENLOAD ACC ---> " + accVO);
				maintainULDTransactionForm.setAccessCode(accVO.getAccessoryCode());				
				maintainULDTransactionForm.setQuantity(String.valueOf(accVO.getQuantity()));
				
			}				
		}
		}	
    	maintainULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);*/    	
    	//maintainULDForm.setStatusFlag("screenload");
		//Commented by A-3045 for CR QF1016 ends
    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    }

 }
