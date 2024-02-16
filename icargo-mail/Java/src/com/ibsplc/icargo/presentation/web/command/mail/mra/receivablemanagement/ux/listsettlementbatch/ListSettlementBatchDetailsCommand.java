/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch.ListSettlementBatchDetailsCommand;
 *
 *	Created by	:	A-3429
 *	Created on	:	Nov 18, 2021
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MailSettlementBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailSettlementBatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.receivablemanagement.ListSettlementBatchModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.mail.mra.receivablemanagement.ux.listsettlementbatch.ListSettlementBatchDetailsCommandjava
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-3429 : Nov 11,
 * 2018 : Draft
 */
public class ListSettlementBatchDetailsCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("CRA AGENTBILLING");
	private static final String CLASS_NAME = "ListSettlementBatchDetailsCommand";
	private static final String NO_DATA_FOUND = "mail.mra.receivablemanagement.listsettlementbatch.nodata";

	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 * Added by : A-3429 on Nov 18, 2021 Used for : Parameters : @param arg0
	 * Parameters : @throws BusinessDelegateException Parameters : @throws
	 * CommandInvocationException
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListSettlementBatchModel listSettlementBatchModel = (ListSettlementBatchModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		Page<PaymentBatchSettlementDetailsVO> paymentBatchSettlementDetailsVOs = null;
		MailSettlementBatchFilterVO filterVO = populateFilterVO(listSettlementBatchModel, logonAttributes);
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		paymentBatchSettlementDetailsVOs = mailTrackingMRADelegate.findSettlementBatchDetails(filterVO);
		if (Objects.isNull(paymentBatchSettlementDetailsVOs)) {
			actionContext.addError(new ErrorVO(NO_DATA_FOUND));
			return;
		}
		List<MailSettlementBatchDetails> mailSettlementBatchDetails = MailMRAModelConverter
				.constructMailSettlementBatchDetails(paymentBatchSettlementDetailsVOs);
		if (mailSettlementBatchDetails != null && !mailSettlementBatchDetails.isEmpty()) {
			PageResult<MailSettlementBatchDetails> pageList = new PageResult<>(paymentBatchSettlementDetailsVOs,
					mailSettlementBatchDetails);
			listSettlementBatchModel.setBatchDetails(pageList);
			ResponseVO responseVO = new ResponseVO();
			List<ListSettlementBatchModel> results = new ArrayList<>();
			results.add(listSettlementBatchModel);
			responseVO.setResults(results);
			actionContext.setResponseVO(responseVO);
		} else {
			List<ErrorVO> errors = new ArrayList<>();
			errors.add(new ErrorVO(NO_DATA_FOUND));
			actionContext.addAllError(errors);
		}
		log.exiting(CLASS_NAME, "execute");
	}

	private MailSettlementBatchFilterVO populateFilterVO(ListSettlementBatchModel listSettlementBatchModel,
			LogonAttributes logonAttributes) {
		log.entering(CLASS_NAME, "populateFilterVO");
		MailSettlementBatchFilterVO settlementBatchFilterVO = null;
		String selectedBatchId = listSettlementBatchModel.getSelectedBatchId();
		if (selectedBatchId != null) {
			String[] selectedBatchIdDetails = selectedBatchId.split("~");
			settlementBatchFilterVO = new MailSettlementBatchFilterVO();
			settlementBatchFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			settlementBatchFilterVO.setBatchID(selectedBatchIdDetails[0]);
			settlementBatchFilterVO.setPaCode(selectedBatchIdDetails[1]);
			settlementBatchFilterVO.setBatchSequenceNum(Long.parseLong(selectedBatchIdDetails[2]));
			settlementBatchFilterVO.setPageNumber(listSettlementBatchModel.getPageNumber());
			if (listSettlementBatchModel.getPageSize() != 0)
				settlementBatchFilterVO.setPageSize(listSettlementBatchModel.getPageSize());
			else
				settlementBatchFilterVO.setPageSize(25);
		}
		return settlementBatchFilterVO;
	}

}
