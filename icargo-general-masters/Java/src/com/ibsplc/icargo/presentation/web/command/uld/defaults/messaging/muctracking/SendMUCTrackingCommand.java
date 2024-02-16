/*
 * SendMUCTrackingCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class SendMUCTrackingCommand extends BaseCommand{
	
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("MUC Tracking");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.messaging.muctracking";

	/**
	 * Target if success
	 */
	private static final String SEND_SUCCESS = "send_success";	
	
	private static final String SEND_FAILURE = "send_failure";	
	//added by a-3045 for bug 18211 starts
	private static final String LOAN_STR = "Loan";
	private static final String BORROW_STR = "Borrow";
	private static final String LOAN = "L";
	private static final String BORROW = "B";
	//added by a-3045 for bug 18211 ends
	//added by a-3045 for bug 18654 starts
	private static final String MUC_RESENT_SUCCESS = "Resent_Success";	
	//added by a-3045 for bug 18654 ends
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("SendMUCTrackingCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MUCTrackingForm mucTrackingForm = (MUCTrackingForm) invocationContext.screenModel;
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);		
				TransactionListVO transactionListVO = mucTrackingSession
						.getListDisplayColl();
				Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
				Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionListVO
						.getUldTransactionsDetails();
				String[] primaryKey = mucTrackingForm.getSelectedrow();
				//log.log(Log.FINE, "primaryKey--->" + primaryKey.length);
				if (primaryKey != null && primaryKey.length > 0) {
					int cnt = 0;
					int index = 0;
					int primaryKeyLen = primaryKey.length;
					if (uldTransactionDetailsVOs != null
							&& uldTransactionDetailsVOs.size() != 0) {
						for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
							log.log(Log.FINE, "dfsghfhgf--->");
							index++;
							String primaryKeyFromVO = new StringBuilder(
									uldTransactionDetailsVO.getUldNumber()).append(
									uldTransactionDetailsVO.getTransactionRefNumber())
									.append(index).toString();
							log.log(Log.FINE, "primaryKeyFromVO--->",
									primaryKeyFromVO);
							if ((cnt < primaryKeyLen)
									&& (primaryKeyFromVO.trim())
											.equalsIgnoreCase(primaryKey[cnt].trim())) {
								log.log(Log.FINE, "pghgfhdfhKey--->");
								uldTxnDetailsVOs.add(uldTransactionDetailsVO);
								cnt++;
							}
						}
					}
				}								
				log.log(Log.FINE, "TRANSACTION DETAILS VOS--->",
						uldTxnDetailsVOs);
				//added by a-3045 for bug 18211 starts			
				Collection<ULDTransactionDetailsVO> uldTxnDtlsVOs=new ArrayList<ULDTransactionDetailsVO>();
				if((uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() > 0)){
					for(ULDTransactionDetailsVO vo: uldTxnDetailsVOs){									
						if(vo.getTransactionType().equals(LOAN_STR)){
							vo.setTransactionType(LOAN);
						}else if(vo.getTransactionType().equals(BORROW_STR)){
							vo.setTransactionType(BORROW);
						}
						uldTxnDtlsVOs.add(vo);
					}			
				}			
				//added by a-3045 for bug 18211 ends	
				ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				Collection<ULDTransactionDetailsVO> uldTxnVosFromServer=new ArrayList<ULDTransactionDetailsVO>();
				try {
					uldTxnVosFromServer=delegate.sendMUCMessage(uldTxnDtlsVOs);
				} catch (BusinessDelegateException ex) {
					ex.getMessage();
					error = handleDelegateException(ex);
				}
				
				log.log(Log.ALL, "uldTxnVosFromServer is", uldTxnVosFromServer);
				if(error!=null && error.size()>0){
					   log.log(Log.ALL, "generateMUCMessage errors-----", error.size());
					invocationContext.addAllError(error);
						   invocationContext.target = SEND_FAILURE;
						   for(ErrorVO vo:error){
							   log.log(Log.ALL,
									"generateMUCMessage error code is", vo.getErrorCode());
						   }
						return;
				}	
					//added by a-3045 for bug 18654 starts
					if((uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() > 0)){
						mucTrackingForm.setMucResentFlag(MUC_RESENT_SUCCESS);
					}
					//added by a-3045 for bug 18654 ends
					invocationContext.addError(new ErrorVO("uld.defaults.loanborrowuld.msg.muc.savedsuccessfully"));
					invocationContext.target = SEND_SUCCESS;		
	}

}
