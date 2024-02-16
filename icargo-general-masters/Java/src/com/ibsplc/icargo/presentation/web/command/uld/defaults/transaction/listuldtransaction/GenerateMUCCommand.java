/*
 * GenerateMUCCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2412
 *
 */
public class GenerateMUCCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Loan Borrow ULD ");

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	private static final String MUC_SUCCESS = "muc_success";

	private static final String MUC_FAILURE = "muc_failure";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();

		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		TransactionListVO transactionListVO = listULDTransactionSession
				.getTransactionListVO();
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		Page<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionListVO
				.getTransactionDetailsPage();
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
		log.log(Log.FINE, "TRANSACTION DETAILS VOS--->", uldTxnDetailsVOs);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		Collection<ULDTransactionDetailsVO> uldTxnVosFromServer=new ArrayList<ULDTransactionDetailsVO>();
		try {
			uldTxnVosFromServer=delegate.sendMUCMessage(uldTxnDetailsVOs);
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			error = handleDelegateException(ex);
		}

		log.log(Log.ALL, "uldTxnVosFromServer is", uldTxnVosFromServer);
		if(error!=null && error.size()>0){
	    	   log.log(Log.ALL, "generateMUCMessage errors-----", error.size());
			invocationContext.addAllError(error);
	   		   invocationContext.target = MUC_FAILURE;
	   		   for(ErrorVO vo:error){
	   			   log.log(Log.ALL, "generateMUCMessage error code is", vo.getErrorCode());
	   		   }
	   		return;
	    }
		//Commented and added By a-3045 for CR QF1142 starts
		/*StringBuffer alreadyExistingUlds = new StringBuffer("");
		StringBuffer alreadyExistingNums = new StringBuffer("");
		if(uldTxnVosFromServer !=null && uldTxnVosFromServer.size()>0){
			for(ULDTransactionDetailsVO vo:uldTxnVosFromServer){
				if(alreadyExistingUlds.toString().equals("")) {
					alreadyExistingUlds.append(vo.getUldNumber().toUpperCase());
					alreadyExistingNums.append(vo.getTransactionRefNumber());
				}else {
	    			alreadyExistingUlds.append(" , ");
	    			alreadyExistingNums.append(" , ");
	    			alreadyExistingUlds.append(vo.getUldNumber().toUpperCase());
	    			alreadyExistingNums.append(vo.getTransactionRefNumber());
	    		}
			}
			ErrorVO err = new ErrorVO(
					"uld.defaults.loanborrowuld.msg.muc.uldexists",
					new Object[] { alreadyExistingUlds.toString(),alreadyExistingNums.toString()});
			invocationContext.addError(err);
			invocationContext.target = MUC_SUCCESS;
		}else{*/		
		listULDTransactionForm.setMucRefNum("");
		log.log(Log.ALL, "listULDTransactionForm.getMucRefNum()",
				listULDTransactionForm.getMucRefNum());
			if(uldTxnVosFromServer !=null && uldTxnVosFromServer.size()>0){
				for(ULDTransactionDetailsVO vo:uldTxnVosFromServer){
					listULDTransactionForm.setMucRefNum(vo.getMucReferenceNumber());
					log.log(Log.ALL, "listULDTransactionForm.getMucRefNum()",
							listULDTransactionForm.getMucRefNum());
					log.log(Log.ALL, "vo.getMucReferenceNumber()", vo.getMucReferenceNumber());
				}
			}	
			//Commented and added By a-3045 for CR QF1142 ends
			invocationContext.addError(new ErrorVO("uld.defaults.loanborrowuld.msg.muc.savedsuccessfully"));
			invocationContext.target = MUC_SUCCESS;
		//}


	}

}
