/*
 * AddNewULDLoanDetailsCommand.java  Created on Jan 12,07
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;

/**
 * @author A-1862
 *
 */


public class AddNewULDLoanDetailsCommand extends BaseCommand {

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
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Airport validation
		if (maintainULDTransactionForm.getLoanTransactionStation() != null
				&& maintainULDTransactionForm.getLoanTransactionStation()
						.trim().length() > 0) {
			
			
			Collection<ErrorVO> errorsOwnerStation = null;
			try {
				log.log(Log.FINE, "\n Airport delegate " );
				new AreaDelegate().validateAirportCode(getApplicationSession().getLogonVO().getCompanyCode(),
						maintainULDTransactionForm.getLoanTransactionStation().toUpperCase());
			}
			catch(BusinessDelegateException businessDelegateException) {
				errorsOwnerStation = handleDelegateException(businessDelegateException);
   			}
			if (errorsOwnerStation != null && errorsOwnerStation.size() > 0) {
				ErrorVO errorVO = new ErrorVO(
				"uld.defaults.transaction.invalidstation");
				errorVO.setErrorDisplayType(ERROR);
				
				invocationContext.addError(errorVO);
				maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);	
				invocationContext.target = ADD_SUCCESS;
				return;
			}
			
		}
		
		
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		log.log(Log.FINE, "\n\n\n\n loanBorrowULDSession.getCtrlRcptNo()---> ",
				loanBorrowULDSession.getCtrlRcptNo());
		ArrayList<ULDTransactionDetailsVO> selectedULDs=loanBorrowULDSession.getSelectedULDColl();
		
		log.log(Log.FINE, "before calling updateSession ", selectedULDs);
		selectedULDs= updateSession(maintainULDTransactionForm,selectedULDs,transactionVO.getFromPartyCode().toUpperCase());
		log.log(Log.FINE, "after calling updateSession", selectedULDs);
		ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
		
		/* Added by A-2412 for Loan/Borrow CR */
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		// Generating the next CRN number
		
		String txnNumPrefix = maintainULDTransactionForm.getLoanTxnNumPrefix();
		String txnNum = txnNumPrefix+maintainULDTransactionForm.getLoanTxnNum();
		if (txnNum !=null && txnNum.length()>0) {
		
		
		
		String number=txnNum.substring(5,txnNum.length());
		int count=Integer.parseInt(txnNum.substring(4,5));
		String airlineID=txnNum.substring(0,4);
		if(count<2){
			count++;	
			txnNumPrefix=airlineID+count;
			txnNum=number;
		}else{
				
			try{
				txnNum=new ULDDefaultsDelegate().findCRNForULDTransaction(logonAttributes.getCompanyCode(), maintainULDTransactionForm.getFromPartyCode().toUpperCase());
				if(txnNum !=null && txnNum.length()>0){
					airlineID=txnNum.substring(0,txnNum.length()-7);
					number=txnNum.substring(txnNum.length()-7,txnNum.length());
					//String s3=airlineID+"0"+number;		
					txnNumPrefix=airlineID+"0";
					txnNum=number;
					log.log(Log.FINE, "generating txnNum is:", txnNum);
				}				
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		}
	
			//if (!("").equalsIgnoreCase(txnNum)) {
			//uldTransactionDetailsVO.setCapturedRefNumber(txnNum);
			uldTransactionDetailsVO.setControlReceiptNumber(txnNumPrefix+txnNum);
			maintainULDTransactionForm.setLoanTxnNum(txnNum);
			maintainULDTransactionForm.setLoanTxnNumPrefix(txnNumPrefix);
			loanBorrowULDSession.setCtrlRcptNo(txnNum);
			loanBorrowULDSession.setCtrlRcptNoPrefix(txnNumPrefix);
			
		}	
		
		// Addition By A-2412 ends
		uldTransactionDetailsVO.setOperationalFlag("I");
		if ("true".equalsIgnoreCase(maintainULDTransactionForm.getLoanDamage())) {
			uldTransactionDetailsVO.setDamageStatus("Y");
		} else {
			uldTransactionDetailsVO.setDamageStatus("N");
		}
		
		uldTransactionDetailsVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
		
		if (maintainULDTransactionForm.getLoanTransactionStation() != null
				&& maintainULDTransactionForm.getLoanTransactionStation()
						.trim().length() > 0) {

			uldTransactionDetailsVO.setTxStationCode(maintainULDTransactionForm
					.getLoanTransactionStation().toUpperCase());
		} else {
			uldTransactionDetailsVO.setTxStationCode(getApplicationSession()
					.getLogonVO().getAirportCode());

		}
		uldTransactionDetailsVO.setUldNature(maintainULDTransactionForm.getLoanULDNature());
		uldTransactionDetailsVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
		
		//Added by A-2412
		uldTransactionDetailsVO.setUldConditionCode(maintainULDTransactionForm.getUldConditionCode());
		//ends	
					
		log.log(Log.ALL, "selectedULDs are---->", selectedULDs);
		ArrayList<ULDTransactionDetailsVO> selULDs=new ArrayList<ULDTransactionDetailsVO>();
	    if(selectedULDs==null){
	    	selectedULDs=new ArrayList<ULDTransactionDetailsVO>();	
	    	selectedULDs.add(uldTransactionDetailsVO);
	    }else{
	    	
	    	// Commented By A-2412 for Loan/Borrow CR
	    	
	    	/*boolean isPresent=false;
	    	for(ULDTransactionDetailsVO uldVO:selectedULDs){
	    		if (uldVO.getUldNumber().equals(maintainULDTransactionForm.getLoanUldNum().toUpperCase())) {
	    			isPresent=true;
					uldVO.setUldNumber(maintainULDTransactionForm.getLoanUldNum());
					String txnNums = maintainULDTransactionForm.getLoanTxnNum();
					if (!("").equalsIgnoreCase(txnNums)) {
						//uldVO.setCapturedRefNumber(txnNums);
						uldTransactionDetailsVO.setControlReceiptNumber(txnNums);
					}
					uldVO.setOperationalFlag("I");
					if ("true".equalsIgnoreCase(maintainULDTransactionForm
							.getLoanDamage())||"on".equalsIgnoreCase(maintainULDTransactionForm
									.getLoanDamage())) {
						uldVO.setDamageStatus("Y");
					} else {
						uldVO.setDamageStatus("N");
					}
					uldVO.setReturnPartyCode(transactionVO.getFromPartyCode().toUpperCase());
					uldVO.setUldNature(maintainULDTransactionForm.getLoanULDNature());
					
					if (maintainULDTransactionForm.getLoanTransactionStation() != null
							&& maintainULDTransactionForm.getLoanTransactionStation()
									.trim().length() > 0) {
						uldVO.setTxStationCode(maintainULDTransactionForm
								.getLoanTransactionStation().toUpperCase());
					} else {
						uldVO.setTxStationCode(getApplicationSession()
								.getLogonVO().getAirportCode());

					}								
				}	    		
	    	}
	    	if(!isPresent){
	    		selectedULDs.add(uldTransactionDetailsVO);	    		
	    	}*/
	    	
	    	for(ULDTransactionDetailsVO vo:selectedULDs){	    		
	    		if(!vo.getUldNature().equals(maintainULDTransactionForm.getLoanUldNum())){
	    			selULDs.add(vo);
	    	}	    	
	    }
	    }
	    loanBorrowULDSession.setSelectedULDColl(selULDs);
	    log.log(Log.FINE, "\n\n\n\n AFTER ADD NEW ---> ", loanBorrowULDSession.getSelectedULDColl());
		maintainULDTransactionForm.setLoanPopupClose(FLAG_NO);	 
		maintainULDTransactionForm.setLoanPopupFlag("LOANSCREENLOAD");
		maintainULDTransactionForm.setLoanUldNum("");
		maintainULDTransactionForm.setLoanULDNature("");
		maintainULDTransactionForm.setLoanTransactionStation("");
		maintainULDTransactionForm.setLoanDamage("false");		
		invocationContext.addAllError(errors);
		invocationContext.target = ADD_SUCCESS;
	}
	
	
	//Added by A-2412 For Loan/Borrow CR	
	/**
	 * updateSession method
	 *  
	 * @param form
	 * @param selectedULDs
	 * @param fromPartyCode
	 * @return ArrayList<ULDTransactionDetailsVO>
	 */
	public ArrayList<ULDTransactionDetailsVO> updateSession(MaintainULDTransactionForm form,ArrayList<ULDTransactionDetailsVO> selectedULDs,String fromPartyCode){
		ULDTransactionDetailsVO vo=new ULDTransactionDetailsVO();
			vo.setUldNumber(form.getLoanUldNum().toUpperCase());
			vo.setControlReceiptNumber(form.getLoanTxnNumPrefix()+form.getLoanTxnNum());
			vo.setOperationalFlag("I");
			if ("true".equalsIgnoreCase(form
					.getLoanDamage())||"on".equalsIgnoreCase(form
							.getLoanDamage())) {
				vo.setDamageStatus("Y");
			} else {
				vo.setDamageStatus("N");
			}
			vo.setReturnPartyCode(fromPartyCode);
			vo.setUldNature(form.getLoanULDNature());
			//Added by A-2412
			vo.setUldConditionCode(form.getUldConditionCode());
			//ends
			if (form.getLoanTransactionStation() != null
					&& form.getLoanTransactionStation()
							.trim().length() > 0) {
				vo.setTxStationCode(form
						.getLoanTransactionStation().toUpperCase());
			} else {
				vo.setTxStationCode(getApplicationSession()
						.getLogonVO().getAirportCode());

			}	
			if(form.getUldConditionCode() !=null && form.getUldConditionCode().length()>0){
				vo.setUldConditionCode(form.getUldConditionCode());
			}
						
			vo.setUldNature(form.getLoanULDNature());
	if(selectedULDs ==null){
		selectedULDs =new ArrayList<ULDTransactionDetailsVO>();
		selectedULDs.add(vo);
	}
	else{
		selectedULDs.add(vo);
	}
	log.log(Log.FINE, "inside  updateSession", selectedULDs);
	return selectedULDs;
	}
}
