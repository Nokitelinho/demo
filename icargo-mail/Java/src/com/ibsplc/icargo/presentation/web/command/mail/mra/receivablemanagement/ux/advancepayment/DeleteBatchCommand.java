package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.PaymentBatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.AdvancePaymentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.advancepayment.CreateCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8331	:	01-Dec-2021	:	Draft
 */



public class DeleteBatchCommand extends AbstractCommand{

	private static final String DELETE_SUCCESS = "success";
	private Log log = LogFactory.getLogger("Mail MRA AdvancePayment DeleteBatchCommand");

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate ();
		LogonAttributes logonAttributes = getLogonAttribute();  
		String companyCode = logonAttributes.getCompanyCode();
		PaymentBatchDetailsVO advancePaymentVO = new PaymentBatchDetailsVO();
		AdvancePaymentModel advancePaymentModel = (AdvancePaymentModel)actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		List<AdvancePaymentModel> results = new ArrayList<AdvancePaymentModel>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		PaymentBatchDetails batchDetails = advancePaymentModel.getPaymentBatchDetail();
		
		if(batchDetails!=null)
		{
		
			advancePaymentVO.setCompanyCode(companyCode);
			advancePaymentVO.setBatchID(batchDetails.getBatchID());
			advancePaymentVO.setPaCode(batchDetails.getPaCode());
			advancePaymentVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(batchDetails.getDate()));
			advancePaymentVO.setBatchStatus(batchDetails.getBatchStatus());
			advancePaymentVO.setLstUpdatedUser(logonAttributes.getUserId());
    		advancePaymentVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		
		}
		
		try{
			mailTrackingMRADelegate.deletePaymentBatch(advancePaymentVO);

 		}
 		catch (BusinessDelegateException businessDelegateException) {
 			errors=handleDelegateException(businessDelegateException);

 		}
		
		
			
		    results.add(advancePaymentModel); 
		    responseVO.setResults(results);
			responseVO.setStatus(DELETE_SUCCESS);
			actionContext.setResponseVO(responseVO);	      		
			this.log.exiting("AdvancePayment", "CreateCommand");
	}
	
	
	

}
