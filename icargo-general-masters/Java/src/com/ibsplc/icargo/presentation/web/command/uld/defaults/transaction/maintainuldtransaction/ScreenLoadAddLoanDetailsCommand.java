/*
 * ScreenLoadAddLoanDetailsCommand.java Created onFeb 09, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
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
public class ScreenLoadAddLoanDetailsCommand  extends BaseCommand {
    
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
		
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionVO
		.getUldTransactionDetailsVOs();
		maintainULDTransactionForm.setUldConditionCode("SER");

		String loanTxnNumPrefix=null;

//IF MODIFY MODE
if(("MODIFY").equals(maintainULDTransactionForm.getModifyMode())){
	log.log(Log.FINE, "calling ScreenLoadAddLoanDetailsCommand in modify mode",
			uldTransactionDetailsVOs);
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
	maintainULDTransactionForm.setLoanUldNum(uldTxnDetailsVOs.get(0).getUldNumber());
}
maintainULDTransactionForm.setLoanPopupFlag("LOANSCREENLOAD-MOD");

}else
{
	// Added by A-2412 for Loan/Borrow CR
	ArrayList<ULDTransactionDetailsVO> vos=(ArrayList<ULDTransactionDetailsVO>)loanBorrowULDSession.getTransactionVO().getUldTransactionDetailsVOs();
	log.log(Log.ALL, "ULDTransactionDetailsVO from form ", vos);
	String crnNumber=null;
	if(vos!=null && vos.size()>0){
		crnNumber=vos.get(vos.size()-1).getControlReceiptNumber();
	}
	// Addition ends
	
	//if(loanBorrowULDSession.getCtrlRcptNo()==null){
	if(crnNumber==null){
		log.log(Log.ALL, "crn number null");
		String crn=null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		String controlRecieptNumber=null;
		
		try{
			crn=new ULDDefaultsDelegate().findCRNForULDTransaction(logonAttributes.getCompanyCode(), maintainULDTransactionForm.getFromPartyCode().toUpperCase());
			if(crn !=null && crn.length()>0){
				String airlineID=crn.substring(0,crn.length()-7);
				String number=crn.substring(crn.length()-7,crn.length());
				loanTxnNumPrefix=airlineID+"0";
				//String s3=loanTxnNumPrefix+number;				
				//crn=s3;
				crn=number;
			}
			
	} catch (BusinessDelegateException businessDelegateException) {
		errors = handleDelegateException(businessDelegateException);
	}
	loanBorrowULDSession.setCtrlRcptNo(crn);
	loanBorrowULDSession.setCtrlRcptNoPrefix(loanTxnNumPrefix);
	}
	// Added by A-2412 for Loan/Borrow CR
	
	else{
		//String crn=loanBorrowULDSession.getCtrlRcptNo();
		String crn=crnNumber;
		
		//String number=crn.substring(crn.length()-7,crn.length());
		//int count=Integer.parseInt(crn.substring(crn.length()-8,crn.length()-7));
		//String airlineID=crn.substring(0,crn.length()-8);
		
		String airlineID=crn.substring(0,4);
		int count=Integer.parseInt(crn.substring(4,5));
		String number=crn.substring(5,crn.length());
		
		log.log(Log.ALL, "crn number s1 :", airlineID);
		log.log(Log.ALL, "crn number s2 :", count);
		log.log(Log.ALL, "crn number s3 :", number);
		if(count<2){
			count++;	
			loanTxnNumPrefix=airlineID+count;
			crn=number;
		}else{
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
			try{
				crn=new ULDDefaultsDelegate().findCRNForULDTransaction(logonAttributes.getCompanyCode(), maintainULDTransactionForm.getFromPartyCode().toUpperCase());
				if(crn !=null && crn.length()>0){
					airlineID=crn.substring(0,crn.length()-7);
					number=crn.substring(crn.length()-7,crn.length());
					loanTxnNumPrefix=airlineID+"0";
					crn=number;
					//String s3=airlineID+"0"+number;				
					//crn=s3;
				}				
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		}
	loanBorrowULDSession.setCtrlRcptNo(crn);	
	loanBorrowULDSession.setCtrlRcptNoPrefix(loanTxnNumPrefix);
	}
	log.log(Log.ALL, "loanBorrowULDSession.getCtrlRcptNo() :",
			loanBorrowULDSession.getCtrlRcptNo());
	log.log(Log.ALL, "loanBorrowULDSession.getCtrlRcptNoPrefix() :",
			loanBorrowULDSession.getCtrlRcptNoPrefix());
	loanBorrowULDSession.setSelectedULDColl(null);
	maintainULDTransactionForm.setLoanPopupFlag("LOANSCREENLOAD");
}



maintainULDTransactionForm.setScreenStatusFlag(
			ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);




    	invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
   
}
