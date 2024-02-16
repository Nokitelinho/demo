/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch.ClearBatchCommand.java
 *
 *	Created by	:	A-10647
 *	Created on	:	27-Jan-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailSettlementBatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.ListSettlementBatchModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch.ClearBatchCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10647	:	27-Jan-2022	:	Draft
 */
public class ClearBatchCommand extends AbstractCommand {
	private static final Log LOG = LogFactory.getLogger("CLEAR BATCH ");
	private static final String CLASS_NAME = "ClearBatchCommand";
	 private static final String STATUS_SUCCESS = "success";
	
	private static final String ERR_NONAPPLIED = "mra.mra.receivablemanagement.ux.listsettlementbatch.msg.err.nonappliedbatch";
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-10647 on 27-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOG.entering(CLASS_NAME, "execute");
		ListSettlementBatchModel listSettlementBatchModel = (ListSettlementBatchModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		 ResponseVO responseVO = new ResponseVO();
		 List<ListSettlementBatchModel> results = new ArrayList<>();
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();

	    Collection<MailSettlementBatchDetails> selectedbatchDetail = null;
		
		selectedbatchDetail = listSettlementBatchModel.getSelectedBatchDetail();
		String selectedBatchStatus = listSettlementBatchModel.getSelectedBatchStatus();
		String selectedBatchDate = listSettlementBatchModel.getSelectedBatchDate();
		for(MailSettlementBatchDetails detail:selectedbatchDetail){ 
			if(detail.getAppliedAmount()!=0 && detail.getUnappliedAmount()==0 ) {
				String status ="";
				if(selectedBatchStatus.equalsIgnoreCase("CL")) {
					status ="Cleared";
				}else {
					status ="Applied";
				}
				 Object[] obj = {status};
	              ErrorVO errorVO = new ErrorVO(ERR_NONAPPLIED,obj);
	              errorVO.setErrorDisplayType(ERROR);
	              actionContext.addError(errorVO);
				   	responseVO.setStatus(STATUS_SUCCESS);
					actionContext.setResponseVO(responseVO);
					return;
	              
			}
					
		}
		 
		PaymentBatchDetailsVO batchDetailVO = populateVO(selectedbatchDetail,listSettlementBatchModel.getSelectedBatchId(), logonAttributes,selectedBatchDate);

		
		
		try{
			mailTrackingMRADelegate.clearBatchDetails(batchDetailVO);

 		}
 		catch (BusinessDelegateException businessDelegateException) {
 			handleDelegateException(businessDelegateException);

 		}
		 results.add(listSettlementBatchModel); 
		    responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);	      		
			LOG.exiting(CLASS_NAME, "ClearBatchDetail");

	} 


	/**
	 * 
	 * 	Method		:	ClearBatchCommand.populateVO
	 *	Added by 	:	A-10647 on 27-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param selectedbatchDetail
	 *	Parameters	:	@param selectedBatchId
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Object>
	 */
	private PaymentBatchDetailsVO  populateVO(
			Collection<MailSettlementBatchDetails> selectedbatchDetail,String selectedBatchId, LogonAttributes logonAttributes,String selectedBatchDate) {
		Collection<PaymentBatchSettlementDetailsVO> settlementDetailVOs = new ArrayList<>() ;
		double amountToBeApplied =0.0;
		String[] batchData = selectedBatchId.split("~");
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();

			detailVO.setBatchID(batchData[0]);
			detailVO.setPaCode(batchData[1]);
			detailVO.setBatchSequenceNum(Long.parseLong(batchData[2]));
			detailVO.setCompanyCode(logonAttributes.getCompanyCode()); 
			LocalDate batchDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			batchDate.setDate(selectedBatchDate);
			detailVO.setBatchDate(batchDate);
		
		for(MailSettlementBatchDetails batchDetail:selectedbatchDetail){
			PaymentBatchSettlementDetailsVO settlementDetailVO = new PaymentBatchSettlementDetailsVO();
			settlementDetailVO.setBatchID(batchDetail.getBatchId());
			settlementDetailVO.setPaCode(batchDetail.getGpaCode());
			settlementDetailVO.setBatchSequenceNum(batchDetail.getBatchSequenceNum());
			settlementDetailVO.setCompanyCode(batchDetail.getCompanyCode());
			settlementDetailVO.setMailbagId(batchDetail.getMailIdr());
			settlementDetailVO.setMailSeqNum(batchDetail.getMalseqnum());
			amountToBeApplied=amountToBeApplied+batchDetail.getUnappliedAmount();
			settlementDetailVO.setAppliedAmount(doubleToMoneyConverter(batchDetail.getAppliedAmount(),batchDetail.getSettlementCurrencyCode()));
			settlementDetailVO.setUnappliedAmount(doubleToMoneyConverter(batchDetail.getUnappliedAmount(),batchDetail.getSettlementCurrencyCode()));
			settlementDetailVO.setLstUpdatedUser(logonAttributes.getUserId());
			settlementDetailVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			settlementDetailVOs.add(settlementDetailVO);
		}
		 
			
			detailVO.setAmountTobeApplied(amountToBeApplied);
			detailVO.setPaymentBatchSettlementDetails(settlementDetailVOs);
			
		
		return detailVO;
	}
	/**
	 * 
	 * 	Method		:	ClearBatchCommand.doubleToMoneyConverter
	 *	Added by 	:	A-10647 on 27-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param amount
	 *	Parameters	:	@param currencyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Money
	 */
	public Money doubleToMoneyConverter(Double amount,String currencyCode){
		Money moneyAmount=null;
	    try{
	    	moneyAmount=CurrencyHelper.getMoney(currencyCode);
	    	moneyAmount.setAmount(amount);
     }
     catch(CurrencyException e){
			LOG.log(Log.INFO,e);
		}
		return moneyAmount;
	}

}
