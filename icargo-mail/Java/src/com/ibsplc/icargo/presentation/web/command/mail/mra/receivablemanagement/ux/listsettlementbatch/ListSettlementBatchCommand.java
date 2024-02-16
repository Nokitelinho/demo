/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch.ListSettlementBatchCommand.java
 *
 *	Created by	:	A-3429
 *	Created on	:	Nov 17, 2021
 *
 *  Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.ListSettlementBatchFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.ListSettlementBatchModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailSettlementBatch;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch.java;
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-3429 : Nov , 2018
 * : Draft
 */
public class ListSettlementBatchCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MRA LISTSETTLEMENT");
	private static final String CLASS_NAME = "ListSettlementBatchCommand";
	private static final String NO_DATA_FOUND = "mail.mra.receivablemanagement.listsettlementbatch.nodata";																												// ICRD-329074

	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 * Added by : A-5791 on Jun 8, 2018 Used for : Parameters : @param arg0
	 * Parameters : @throws BusinessDelegateException Parameters : @throws
	 * CommandInvocationException
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListSettlementBatchModel listSettlementbatchModel = (ListSettlementBatchModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		PaymentBatchFilterVO filterVO =populateFilterVO(listSettlementbatchModel,logonAttributes);
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		Collection<PaymentBatchDetailsVO> settlementBatchDetailVOs =mailTrackingMRADelegate.findSettlementBatches(filterVO);
		if(settlementBatchDetailVOs==null||settlementBatchDetailVOs.isEmpty()){
			actionContext.addError(new ErrorVO(NO_DATA_FOUND));
			return;
		}
		Collection<MailSettlementBatch> batchDetails = MailMRAModelConverter.constructMailSettlementBatches(settlementBatchDetailVOs);  
		listSettlementbatchModel.setBatchLists(batchDetails);
		ResponseVO responseVO = new ResponseVO();
		List<ListSettlementBatchModel> results = new ArrayList<>();
		results.add(listSettlementbatchModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 * Method : ListSettlementBatchCommand.populateFilterVO Added by : A-3429
	 * on Jun 8, 2018 Used for : Parameters : @param ListSettlementBatchModel
	 * Parameters : @param logonAttributes Parameters : @return Return type :
	 * MailSettlementBatchFilterVO
	 */

	private PaymentBatchFilterVO populateFilterVO(ListSettlementBatchModel listSettlementbatchModel,
			LogonAttributes logonAttributes) {
		log.entering(CLASS_NAME, "populateFilterVO");
		ListSettlementBatchFilter listSettlementBatchFilter = listSettlementbatchModel.getListSettlementBatchFilter();
		PaymentBatchFilterVO paymentBatchFilterVO = new PaymentBatchFilterVO();
		paymentBatchFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if (listSettlementBatchFilter.getFromDate() != null) {
			LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);
			paymentBatchFilterVO.setBatchFrom(fromdate.setDate(listSettlementBatchFilter.getFromDate()));
		}
		if (listSettlementBatchFilter.getToDate() != null) {
			LocalDate todate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);
			paymentBatchFilterVO.setBatchTo(todate.setDate(listSettlementBatchFilter.getToDate()));
		}
		paymentBatchFilterVO.setBatchStatus(listSettlementBatchFilter.getBatchStatus());
		paymentBatchFilterVO.setBatchId(listSettlementBatchFilter.getBatchId());
		log.exiting(CLASS_NAME, "populateFilterVO");
		return paymentBatchFilterVO;
	}

}
