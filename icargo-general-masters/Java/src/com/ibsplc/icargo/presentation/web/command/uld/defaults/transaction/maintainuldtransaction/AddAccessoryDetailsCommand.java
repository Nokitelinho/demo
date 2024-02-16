/*
 * AddAccessoryDetailsCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class AddAccessoryDetailsCommand  extends BaseCommand {
    
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
	private static final String ADD_SUCCESS = "add_success";
   	
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
    	LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		Collection<AccessoryTransactionVO> accessoryTransactionVOs  
		                                    = transactionVO.getAccessoryTransactionVOs();
		
		AccessoryTransactionVO accessoryTransactionVO = new AccessoryTransactionVO();
		accessoryTransactionVO.setAccessoryCode(maintainULDTransactionForm.getAccessCode().toUpperCase());
		String quantity = maintainULDTransactionForm.getQuantity();
		if(!("").equalsIgnoreCase(quantity)) {
			accessoryTransactionVO.setQuantity(Integer.parseInt(quantity));
		}
		accessoryTransactionVO.setOperationalFlag("I");
		
		boolean isNotDuplicate = true;
		if(accessoryTransactionVOs != null && accessoryTransactionVOs.size() > 0) {
			
			if(("MODIFYACC").equals(maintainULDTransactionForm.getModifyMode())){

				for (AccessoryTransactionVO accVO : accessoryTransactionVOs) {
					if (!accVO.getAccessoryCode().equals(maintainULDTransactionForm.getModifyAcc().toUpperCase()) 
							&& accVO.getAccessoryCode().equals(
									accessoryTransactionVO.getAccessoryCode())) {
						isNotDuplicate = false;
					}
				}
				if (isNotDuplicate) {
					//uldTransactionDetailsVOs.add(uldTransactionDetailsVO);
					for (AccessoryTransactionVO accVO : accessoryTransactionVOs) {
						if (accVO.getAccessoryCode().equals(maintainULDTransactionForm.getModifyAcc().toUpperCase())) {
							accVO.setAccessoryCode(accessoryTransactionVO.getAccessoryCode());
							accVO.setQuantity(accessoryTransactionVO.getQuantity());
							accVO.setOperationalFlag(accessoryTransactionVO.getOperationalFlag());
							
						}	
						
					}
					
					transactionVO.setAccessoryTransactionVOs(accessoryTransactionVOs);
					log.log(Log.FINE,
							"\n\n\n\n transactionVO AFTER MODIFY ---> ",
							transactionVO);
					maintainULDTransactionForm.setAccPopupClose(FLAG_YES);
					
				} else {
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.transaction.accalreadyexist");
					maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					
				}
				
			}
			else{
				for (AccessoryTransactionVO accVO : accessoryTransactionVOs) {
				if (accVO.getAccessoryCode().equals(
						accessoryTransactionVO.getAccessoryCode())) {
					isNotDuplicate = false;
				}
			}
			if (isNotDuplicate) {
				accessoryTransactionVOs.add(accessoryTransactionVO);
				transactionVO.setAccessoryTransactionVOs(accessoryTransactionVOs);
				maintainULDTransactionForm.setAccPopupClose(FLAG_YES);
				
			} else {
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.accalreadyexist");
				maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);
				errorVO.setErrorDisplayType(ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				
			}}
		}		
			
			
			
			
			
			
		   
		else {
			Collection<AccessoryTransactionVO> accTransactionVOs 
									= new ArrayList<AccessoryTransactionVO>();
			accTransactionVOs.add(accessoryTransactionVO);
			transactionVO.setAccessoryTransactionVOs(accTransactionVOs);
			maintainULDTransactionForm.setAccPopupClose(FLAG_YES);
		}
		
		loanBorrowULDSession.setTransactionVO(transactionVO);
		
		
    	maintainULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target = ADD_SUCCESS;
        
    }

}
