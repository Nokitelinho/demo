/*
 * DeleteAccessoryDetailsCommand.java Created on Feb 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.INFO;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2412
 *
 */
public class DeleteAccessoryDetailsCommand extends BaseCommand {

	/**
	 * Logger for DeleteAccessoryDetailsCommand
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");

	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	/*
	 * target String if success
	 */
	private static final String DELETE_SUCCESS = "delete_success";

	/*
	 * target String if failure
	 */
	//private static final String DELETE_FAILURE = "delete_failure";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("DeleteAccessoryDetailsCommand", "execute");
		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		//TransactionVO transactionVO = new TransactionVO();
		TransactionListVO transactionListVO = listULDTransactionSession
				.getTransactionListVO();
		Collection<AccessoryTransactionVO> uldAccDetailsVOs = new ArrayList<AccessoryTransactionVO>();
		Collection<AccessoryTransactionVO> uldAccessoryDetailsVOs = transactionListVO
				.getAccessoryTransactions();
		log.log(Log.FINE, "uldAccessoryDetailsVOs----", uldAccessoryDetailsVOs);
		String[] primaryKey = listULDTransactionForm.getAccessoryDetails();

		
		if (primaryKey != null && primaryKey.length > 0) {
			int cnt = 0;
			int index = 0;
			int primaryKeyLen = primaryKey.length;
			if (uldAccessoryDetailsVOs != null
					&& uldAccessoryDetailsVOs.size() != 0) {
				for (AccessoryTransactionVO uldAccVo : uldAccessoryDetailsVOs) {
					index++;
					String primaryKeyFromVO = new StringBuilder(uldAccVo
							.getAccessoryCode()).append(
							uldAccVo.getTransactionRefNumber()).append(index)
							.toString();
					if ((cnt < primaryKeyLen)
							&& (primaryKeyFromVO.trim())
									.equalsIgnoreCase(primaryKey[cnt].trim())) {
						log.log(Log.FINE, "primaryKey----", primaryKey[cnt]);
						uldAccVo.setOperationalFlag("D");
						uldAccDetailsVOs.add(uldAccVo);
						cnt++;
					}
				}
			}
		}

		log.log(Log.FINE, "uldAccDetailsVOs-------", uldAccDetailsVOs);
		try {
			uldDefaultsDelegate.deleteAccessoryDetails(uldAccDetailsVOs);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		transactionFilterVO = listULDTransactionSession
				.getTransactionFilterVO();
		TransactionListVO txnListVO = new TransactionListVO();
		try {
			txnListVO = uldDefaultsDelegate
					.listULDTransactionDetails(transactionFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}
		if (txnListVO == null) {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.noenquiriesfound");
			errorVO.setErrorDisplayType(INFO);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
		}
		listULDTransactionSession.setTransactionListVO(txnListVO);
		listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		listULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = DELETE_SUCCESS;
	}

}
