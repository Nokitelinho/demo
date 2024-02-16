/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.DeleteAttachmentCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	02-Dec-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.DeleteAttachmentCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	02-Dec-2021	:	Draft
 */
public class DeleteAttachmentCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("Mail MRA AdvancePayment DeleteAttachmentCommand");
    private static final String STATUS_SUCCESS = "success";
 
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("AdvancePayment", "DeleteAttachmentCommand");
		 LogonAttributes logonAttributes = getLogonAttribute();  
		 AdvancePaymentModel advancePaymentModel = (AdvancePaymentModel)actionContext.getScreenModel();
		 ResponseVO responseVO = new ResponseVO();
		 List<AdvancePaymentModel> results = new ArrayList<AdvancePaymentModel>();
		 PaymentBatchDetails  detail = advancePaymentModel.getPaymentBatchDetail();
		  PaymentBatchDetailsVO paymentBatchDetailVO = updateFilterVO(detail,logonAttributes);
		  
		  new MailTrackingMRADelegate().deletePaymentBatchAttachment(paymentBatchDetailVO);
		     
	        results.add(advancePaymentModel);
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);	      		
			this.log.exiting("AdvancePayment", "DeleteAttachmentCommand");
	}

	/**
	 * 	Method		:	DeleteAttachmentCommand.updateFilterVO
	 *	Added by 	:	A-4809 on 02-Dec-2021
	 * 	Used for 	:
	 *	Parameters	:	@param paymentBatchDetail
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	PaymentBatchFilterVO
	 */
	private PaymentBatchDetailsVO updateFilterVO(PaymentBatchDetails paymentBatchDetail,
			LogonAttributes logonAttributes) {
		PaymentBatchDetailsVO paymentBatchDetailVO = new PaymentBatchDetailsVO();
		paymentBatchDetailVO.setCompanyCode(logonAttributes.getCompanyCode());
		paymentBatchDetailVO.setBatchID(paymentBatchDetail.getBatchID());
		paymentBatchDetailVO.setPaCode(paymentBatchDetail.getPaCode());
		//paymentBatchDetailVO.setBatchDate(paymentBatchDetail.getDate());
		paymentBatchDetailVO.setProcessID(paymentBatchDetail.getProcessID());
		paymentBatchDetailVO.setFileName(paymentBatchDetail.getFileName());
		return paymentBatchDetailVO;
	}

}
