/*
 * IgnoreReinstateMUCCommand.java  Created on Aug 15, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
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
 * @author a-3045
 *
 */
public class IgnoreReinstateMUCCommand extends BaseCommand {

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

	/**
	 * Target if success
	 */
	private static final String ACTION_SUCCESS = "action_success";
	
	private static final String STR_MUCREQUIRED = "MUC Required";
	
	private static final String STR_NOT_TO_BE_REPORTED = "Not to be Reported";
	
	private static final String MUCREQUIRED = "Q";
	
	private static final String NOT_TO_BE_REPORTED = "N";
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("IgnoreReinstateMUCCommand", "execute");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();	
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		TransactionListVO transactionListVO = listULDTransactionSession.getTransactionListVO();
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		log.log(Log.INFO,"Hey dude i m hereeee");				
		Collection<ULDTransactionDetailsVO> uldTxnDtlsVOs = new ArrayList<ULDTransactionDetailsVO>();
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
						uldTxnDtlsVOs.add(uldTransactionDetailsVO);
						cnt++;
					}
				}
			}
		}	
		if (uldTxnDtlsVOs != null && uldTxnDtlsVOs.size() != 0) {
			for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTxnDtlsVOs) {				
				  log.log(Log.INFO,
						"uldTransactionDetailsVO.getLastUpdateTime() ======= ",
						uldTransactionDetailsVO.getLastUpdateTime());
				log.log(Log.INFO,
						"uldTransactionDetailsVO.getLastUpdateTime() ======",
						uldTransactionDetailsVO.getLastUpdateUser());
				log.log(Log.INFO,
						"uldTransactionDetailsVO.getMucIataStatus() ====",
						uldTransactionDetailsVO.getMucIataStatus());
				if(uldTransactionDetailsVO.getMucIataStatus().equals(STR_MUCREQUIRED)){
					  uldTransactionDetailsVO.setMucIataStatus(NOT_TO_BE_REPORTED);
				  }else if(uldTransactionDetailsVO.getMucIataStatus().equals(STR_NOT_TO_BE_REPORTED)){
					  uldTransactionDetailsVO.setMucIataStatus(MUCREQUIRED);
				  }				  
				  uldTransactionDetailsVO.setLastUpdateTime(uldTransactionDetailsVO.getLastUpdateTime());
				  uldTransactionDetailsVO.setLastUpdateUser(uldTransactionDetailsVO.getLastUpdateUser());	
				  uldTxnDetailsVOs.add(uldTransactionDetailsVO);
			}			
		}				
		try {		
			uldDefaultsDelegate.updateMUCStatus(uldTxnDetailsVOs);		
		}catch (BusinessDelegateException businessDelegateException) {
		    businessDelegateException.getMessage();
		    errors = handleDelegateException(businessDelegateException);
		}		     
		listULDTransactionSession.setTransactionListVO(transactionListVO);
		listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		listULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting("IgnoreReinstateMUCCommand", "execute");
	}

}
