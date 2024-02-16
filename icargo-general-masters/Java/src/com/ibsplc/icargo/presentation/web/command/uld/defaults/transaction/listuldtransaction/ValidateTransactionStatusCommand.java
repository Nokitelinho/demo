/*
 * ValidateTransactionStatusCommand.java Created on Dec 13,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ValidateTransactionStatusCommand extends BaseCommand {

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
	private static final String ULD_COUNT_PER_UCR = "shared.airline.uldCountPerUCR";

	//private static final String PAGE_URL = "fromScmUldReconcile";

	/**
	 * Target if success
	 */
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";

	//private static final String SAVE_ULDERRORLOG = "save_ulderrorlog";

	//private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";

	//private static final String SCREENID_SCMERRORLOG = "uld.defaults.scmulderrorlog";

	// added for scm reconcile

	//private static final String SAVE_SCMERRORLOG = "save_scmerrorlog";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ValidateTransactionStatusCommand", "execute");
		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		ErrorVO err = null;
		
		TransactionListVO transactionListVO = listULDTransactionSession.getTransactionListVO();
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		Page<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionListVO.getTransactionDetailsPage();
		String[] primaryKey = listULDTransactionForm.getUldDetails();

		if (primaryKey != null && primaryKey.length > 0) {
			int cnt = 0;
			int index = 0;
			int primaryKeyLen = primaryKey.length;
			if (uldTransactionDetailsVOs != null
					&& uldTransactionDetailsVOs.size() != 0) {
				for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
					index++;
					String primaryKeyFromVO = new StringBuilder(
							uldTransactionDetailsVO.getUldNumber()).append(
							uldTransactionDetailsVO.getTransactionRefNumber())
							.append(index).toString();
					if ((cnt < primaryKeyLen)
							&& (primaryKeyFromVO.trim())
									.equalsIgnoreCase(primaryKey[cnt].trim())) {
						uldTxnDetailsVOs.add(uldTransactionDetailsVO);
						cnt++;
					}
				}
			}
		}
		
		//CRN number mandatory Check
		for(ULDTransactionDetailsVO uldTransactDetailsVO : uldTxnDetailsVOs){
				if(uldTransactDetailsVO.getControlReceiptNumber() == null ||
					uldTransactDetailsVO.getControlReceiptNumber().trim().length() == 0){
				err = new ErrorVO("uld.defaults.transaction.modify.crn.mandatory");
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(err);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		}

		/************commented for bug 103993 starts **************
		 * // Added by Preet on 18th Feb --starts
		 // Check for 0,1,2 prefix in the CRN number			 
		 int temp=0;
		 boolean prefixFlag=false;
		 for (ULDTransactionDetailsVO transactionDetailsVO : uldTxnDetailsVOs) {
			 String crnNum=transactionDetailsVO.getControlReceiptNumber();
			 String numPart=crnNum.substring(4,5);
			 log.log(Log.FINE, "numPart -----------"+ numPart);
			 log.log(Log.FINE, "temp -----------"+ temp);
			 
			 try {
				Integer.parseInt(String.valueOf(numPart));
			} catch (NumberFormatException e) {
				prefixFlag = true;
				log.log(Log.INFO,"NumberFormatexception caught");
				break;
			}
				
			 if(Integer.parseInt(numPart)==(temp)){
				 log.log(Log.FINE, "EQUAL---");
			 }else{
				 log.log(Log.FINE, "NOT EQUAL---");
				 prefixFlag=true;
				 break;
			 }
			 temp++;
			 if(temp>2){
				 temp=0;
			 }
		 }
			 }
			 	}
			 ****************/
							
			
		/***********	
		
		 if(prefixFlag){
			 ErrorVO error = new ErrorVO(
			 	"uld.defaults.loanBorrowULD.msg.err.incorrectCRNprefix");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(error);
				invocationContext.target = SAVE_FAILURE;
				listULDTransactionForm.setChkTransaction("F");
				return;
		 }
		 
		// Added by Preet on 18th Feb --ends
 ************commented for bug 103993 ends******/
		// For Identical CRN Number check
		int flag =0;
		log.log(Log.FINE, "TRANSACTION DETAILS VOS--->", uldTxnDetailsVOs);
		//	Check for similar UCR numbers 
		boolean similarCrnFlag = true;	
		HashSet<String> uniqueCRN=new HashSet<String>();
		double size=uldTxnDetailsVOs.size();
		double count=Math.ceil(size/3);
		int uldCountPerUCR= getUldCountperUCR(logonAttributes.getCompanyCode(),
				logonAttributes.getOwnAirlineIdentifier());
        boolean isNewUCRReport = uldCountPerUCR>3;  
		for (ULDTransactionDetailsVO transactionDetailsVO : uldTxnDetailsVOs) {
			if(transactionDetailsVO.getReturnCRN()!=null && !transactionDetailsVO.getReturnCRN().isEmpty()) {
				String crnNum=transactionDetailsVO.getReturnCRN().substring(5,transactionDetailsVO.getReturnCRN().length());
				uniqueCRN.add(crnNum); 
			}else {
			String crnNum=transactionDetailsVO.getControlReceiptNumber().substring(5,transactionDetailsVO.getControlReceiptNumber().length());
			uniqueCRN.add(crnNum);
		}
			
		} 
		double crnLen=uniqueCRN.size();
		
		log.log(Log.FINE, "uniqueCRN -----------", uniqueCRN);
		log.log(Log.FINE, "crnLen -----------", crnLen);
		log.log(Log.FINE, "count -----------", count);
		if(isNewUCRReport){ 
			if(crnLen==1) {             
				similarCrnFlag=false; 
			}			
		}else		
		if(crnLen==count){
			similarCrnFlag=false;
		}
		log.log(Log.FINE, "similarCrnFlag -----------", similarCrnFlag);
		if(similarCrnFlag){
			ErrorVO error = new ErrorVO(
			"uld.defaults.loanBorrowULD.msg.err.similarCRN");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
				listULDTransactionForm.setChkTransaction("F");
			invocationContext.addError(error);
				invocationContext.target = SAVE_FAILURE;
			return;
		}else{
				listULDTransactionForm.setChkTransaction("T");
				invocationContext.target = SAVE_SUCCESS;
			}
		log.exiting("ValidateTransactionStatusCommand", "exit");

	}
	
	/**
	 * Added by A-4072 for getting number of ULDs to to print in UCR report.
	 * and this count is used for generating crn number.
	 * Pick ULD count configured in airline parameter or else it should be 3 by default
	 * @param cmpCode
	 * @param ownAirlineIdr
	 * @return
	 */
	private int getUldCountperUCR(String cmpCode,int ownAirlineIdr){
		int uldCountPerUCR =3;//As per IATA default is 3
		try{
			Collection<String> parameterCodes = new ArrayList<String>();
			Map<String,String> airlineParameterMap = null;
			parameterCodes.add(ULD_COUNT_PER_UCR);
			airlineParameterMap = new AirlineDelegate().findAirlineParametersByCode(cmpCode, ownAirlineIdr, parameterCodes);
			if(airlineParameterMap != null && !airlineParameterMap.isEmpty()){
				String value = airlineParameterMap.get(ULD_COUNT_PER_UCR);
				if(value != null && value.trim().length() > 0){
					uldCountPerUCR = Integer.parseInt(value);
				}
			}
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE, "*****in the exception in findAirlineParameter"+businessDelegateException.getMessage());
		}		
		return uldCountPerUCR;
	}

}
