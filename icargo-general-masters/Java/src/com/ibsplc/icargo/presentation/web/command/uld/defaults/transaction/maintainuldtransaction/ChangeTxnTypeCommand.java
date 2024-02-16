/*
 * ChangeTxnTypeCommand.java Created on Aug 05, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class ChangeTxnTypeCommand  extends BaseCommand {
    
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
    
    	MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
				
		log.log(Log.FINE, "\n\n\n\n ****SCREENLOAD******");
		
		TransactionVO transactionVO = new TransactionVO();
		
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		transactionVO=loanBorrowULDSession.getTransactionVO();
		transactionVO.setTransactionType(maintainULDTransactionForm.getTransactionType());
		log.log(Log.FINE, "\n\n\n\n ****TransactionType******",
				maintainULDTransactionForm.getTransactionType());
		if(("airline").equals(maintainULDTransactionForm.getDisableStatus())){
//			to get party name
			AirlineLovFilterVO airlineLovFilterVO=new  AirlineLovFilterVO();
			airlineLovFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			airlineLovFilterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
			airlineLovFilterVO.setDisplayPage(1);	
			Page<AirlineLovVO> page=null;
			try{
			page= new AirlineDelegate().findAirlineLov(airlineLovFilterVO,1);
			
			} catch (BusinessDelegateException businessDelegateException) {
	
				 handleDelegateException(businessDelegateException);
			}
		    String partyName=page.get(0).getAirlineName();
		    
		if(("L").equals(maintainULDTransactionForm.getTransactionType())){
			transactionVO.setFromPartyCode(logonAttributes.getOwnAirlineCode());	
			transactionVO.setFromPartyName(partyName);
			transactionVO.setToPartyCode("");
			transactionVO.setToPartyName("");
		}
		if(("B").equals(maintainULDTransactionForm.getTransactionType())){
			transactionVO.setToPartyCode(logonAttributes.getOwnAirlineCode());
			transactionVO.setToPartyName(partyName);
			transactionVO.setFromPartyCode("");
			transactionVO.setFromPartyName("");
		}
		}
		loanBorrowULDSession.setTransactionVO(transactionVO);
		log.log(Log.FINE, "\n\n\n\n ***transactionVO******", transactionVO);
		maintainULDTransactionForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	//maintainULDForm.setStatusFlag("screenload");
    	maintainULDTransactionForm.setTxnTypeDisable("");
    	log.log(Log.FINE, "\n\n\n\n ****SCREENLOAD******");
    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    }

   
	
	

}
