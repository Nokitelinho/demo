/*
 * ScreenLoadAddBorrowDetailsCommand.java Created on Feb 09, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
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
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadAddBorrowDetailsCommand  extends BaseCommand {
    
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
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	MaintainULDTransactionForm maintainULDTransactionForm =
    		(MaintainULDTransactionForm) invocationContext.screenModel;
    	LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
    	TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		loanBorrowULDSession.setTransactionVO(transactionVO);		
		log.log(log.INFO,"inside add Boooorw");
		maintainULDTransactionForm.setUldConditionCode("SER");
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionVO
				.getUldTransactionDetailsVOs();
		
		//IF MODIFY MODE
		if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
			String selULDs[]=maintainULDTransactionForm.getModifyUldNum().split("-");
			int size=selULDs.length;			
			ArrayList<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
					
			
			
		for (ULDTransactionDetailsVO uldVO : uldTransactionDetailsVOs) {
					for(int i=0;i<size;i++){
						if(selULDs[i]!=null && !("").equals(selULDs[i])){
							if (uldVO.getUldNumber().equals(selULDs[i])) {	
								uldTxnDetailsVOs.add(uldVO);
							}
						}
						
					}	
			
			loanBorrowULDSession.setSelectedULDColl(uldTxnDetailsVOs);
		}
		if(uldTxnDetailsVOs !=null && uldTxnDetailsVOs.size()>0){
			maintainULDTransactionForm.setBorrowUldNum(uldTxnDetailsVOs.get(0).getUldNumber());
		}
		maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD-MOD");
		}else
		{
			//Commented by A-2412 forLoan/Borrow CR
			
			/*if(loanBorrowULDSession.getCtrlRcptNo()==null){
				
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
				String crn=null;
				try{
					crn=new ULDDefaultsDelegate().findCRNForULDTransaction(logonAttributes.getCompanyCode(), maintainULDTransactionForm.getFromPartyCode().toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}				
			loanBorrowULDSession.setCtrlRcptNo(crn);
			}*/

			ArrayList<ULDTransactionDetailsVO> vos=(ArrayList<ULDTransactionDetailsVO>)loanBorrowULDSession.getTransactionVO().getUldTransactionDetailsVOs();
			log.log(Log.ALL, "ULDTransactionDetailsVO from form ", vos);
			String crnNumber=null;
			String airlineID=null;
			String fromPrtyCode = maintainULDTransactionForm.getFromPartyCode()
			.toUpperCase();
			AirlineValidationVO toOwnerVO = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			
			if (("B").equals(maintainULDTransactionForm.getTransactionType())) {
				if (fromPrtyCode != null && !("".equals(fromPrtyCode))) {

					try {
						toOwnerVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(),
								maintainULDTransactionForm.getFromPartyCode()
										.toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {

						errors = handleDelegateException(businessDelegateException);
					}
				}
			}
			
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_SUCCESS;
				return;
			}
				
			if(toOwnerVO !=null ){
				airlineID=toOwnerVO.getNumericCode()+"";				
			}
			
			String borrowTxnNumPrefix=null;
			if(vos!=null && vos.size()>0){
				crnNumber=vos.get(vos.size()-1).getControlReceiptNumber();		
			}	
			if(crnNumber==null){
				borrowTxnNumPrefix=new StringBuffer(airlineID).append("-").append("0").toString();
			}
			else{
				String crn=crnNumber;
				airlineID=crn.substring(0,3);
				int count=Integer.parseInt(crn.substring(4,5));
				if(count<2){
					count++;
					borrowTxnNumPrefix=new StringBuffer(airlineID).append("-").append(count).toString();
				}else{
					borrowTxnNumPrefix=new StringBuffer(airlineID).append("-").append("0").toString();
				}
			}
			loanBorrowULDSession.setCtrlRcptNo(null);
			loanBorrowULDSession.setCtrlRcptNoPrefix(borrowTxnNumPrefix);
			loanBorrowULDSession.setSelectedULDColl(null);
			maintainULDTransactionForm.setBorrowPopupFlag("BORROWSCREENLOAD");
		}
		
		
		
    	maintainULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
   
}
