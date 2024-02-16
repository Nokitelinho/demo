/*
 * CalculateAcceptedAmtCommand.java Created on Sep 27, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class CalculateAcceptedAmtCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory
			.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");

	private static final String CLASS_NAME = "CalculateAcceptedAmtCommand";

	/**
	 * module name for rejection memo Screen
	 * 
	 */
	private static final String REJECTION_MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * screen id for Rejection memo Screen
	 * 
	 */
	private static final String REJECTION_SCREENID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";

	private static final String CALCULATE_SUCCESS = "calculate_success";

	private static final String CALCULATE_FAILURE = "calculate_failure";
	
	private static final String BLGCURCOD = "USD";

	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		log.log(Log.INFO, "inside calc" );
		RejectionMemoForm rejectionMemoForm = (RejectionMemoForm) invocationContext.screenModel;
		RejectionMemoSession rejectionMemoSession = (RejectionMemoSession) getScreenSession(
				REJECTION_MODULE_NAME, REJECTION_SCREENID);
		double billedAmt =0.0;
		double acceptedAmt =0.0;
		double rejectedAmt =0.0;
		double zero=0.0;
		Money moneyAcceptedAmt=null;
		Money moneyRejectedAmt=null;
		try{
		if(rejectionMemoForm
				.getBillCurBilledAmount()!=null && rejectionMemoForm
				.getBillCurBilledAmount().trim().length()>0 ){
		 billedAmt = Double.parseDouble(rejectionMemoForm
				.getBillCurBilledAmount());
		}
		if(rejectionMemoForm
				.getBilCuracceptedAmount()!=null && rejectionMemoForm
				.getBilCuracceptedAmount().trim().length()>0 ){
		 acceptedAmt = Double.parseDouble(rejectionMemoForm
				.getBilCuracceptedAmount());
		}
		moneyAcceptedAmt=CurrencyHelper.getMoney(BLGCURCOD);
		moneyAcceptedAmt.setAmount(acceptedAmt);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		if (acceptedAmt > billedAmt) {
			// rejectionMemoForm.setAcceptedAmount(String.valueOf(acceptedAmt));
			errorVO = new ErrorVO(
					"mailtracking.mra.airlinebilling.rejectionmemo.err.acceptedamountgreaterthanbilledamount");
			errors.add(errorVO);
		}
		
			if (errors != null && errors.size() > 0) {
				RejectionMemoVO rejectionMemoVO = rejectionMemoSession
						.getRejectionMemoVO();
				rejectionMemoVO.setBillingAcceptedAmount(moneyAcceptedAmt);
				invocationContext.addAllError(errors);
				invocationContext.target = CALCULATE_FAILURE;
				return;
			}
		
		log.log(Log.INFO, "before calculation" );
		rejectedAmt = billedAmt - acceptedAmt;
		moneyRejectedAmt=CurrencyHelper.getMoney(BLGCURCOD);
		moneyRejectedAmt.setAmount(rejectedAmt);
		log.log(Log.INFO, "before =====>>>>" );
		RejectionMemoVO rejectionMemoVO =null;
		if(rejectionMemoSession
				.getRejectionMemoVO()!=null){
			 rejectionMemoVO = rejectionMemoSession
			.getRejectionMemoVO();
		}else{
			 rejectionMemoVO=new RejectionMemoVO();
		}
		
		log.log(Log.INFO, "Before setting to vO" );
		rejectionMemoVO.setContractAcceptedAmount(zero);
		rejectionMemoVO.setContractRejectedAmount(zero);
		rejectionMemoVO.setBillingAcceptedAmount(moneyAcceptedAmt);
		rejectionMemoVO.setBillingRejectedAmount(moneyRejectedAmt);
		rejectionMemoForm.setRejectedAmount(String.valueOf(rejectedAmt));
		}catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
		invocationContext.target = CALCULATE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}
}
