/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.EditBatchCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	10-Jan-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.EditBatchCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	10-Jan-2022	:	Draft
 */
public class EditBatchCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("Mail MRA AdvancePayment CreateCommand");
    private static final String STATUS_SUCCESS = "success";
    
    private static final String SCREEN_NAME="AdvancePayment";
    private static final String CLASS_NAME="EditBatchCommand";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-4809 on 10-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering(SCREEN_NAME,CLASS_NAME);
		 AdvancePaymentModel advancePaymentModel = (AdvancePaymentModel)actionContext.getScreenModel();
		 PaymentBatchDetails batchDetails = advancePaymentModel.getPaymentBatchDetail();
		 ResponseVO responseVO = new ResponseVO();
		 List<AdvancePaymentModel> results = new ArrayList<>();
		 advancePaymentModel.setPaymentBatchDetail(batchDetails);
		 results.add(advancePaymentModel); 
		 responseVO.setResults(results);
		 responseVO.setStatus(STATUS_SUCCESS);
		 actionContext.setResponseVO(responseVO);	
		this.log.exiting(SCREEN_NAME,CLASS_NAME);
	}

}
