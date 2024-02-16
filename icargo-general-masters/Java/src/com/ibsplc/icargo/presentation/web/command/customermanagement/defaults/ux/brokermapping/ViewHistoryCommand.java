/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping.ViewHistoryCommand.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditFiltersVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.audit.AuditDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingFilter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModelConverter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.PoaHistoryDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

public class ViewHistoryCommand extends AbstractCommand {

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		BrokerMappingModel brokerMappingModel = (BrokerMappingModel) actionContext.getScreenModel();
		BrokerMappingFilter brokerMappingFilter = brokerMappingModel.getBrokerMappingFilter();
		LogonAttributes logonAttributes = getLogonAttribute();
		AuditFiltersVO auditFilterVO = new AuditFiltersVO();

		auditFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		auditFilterVO.setStationCode(logonAttributes.getStationCode());
		auditFilterVO.setChildEntity("SHRCUSMST");
		auditFilterVO.setEntity("SHRENTAUD");
		auditFilterVO.setActionCode("POADELETE");
		HashMap<String, String> hashMap = new HashMap<>();

		if (brokerMappingFilter.getCustomerCode() != null) {
			hashMap.put("CUSCOD", brokerMappingFilter.getCustomerCode());
		}
		auditFilterVO.setKeyValues(hashMap);
		Collection<AuditDetailsVO> auditDetailsVOs = new ArrayList<>();
		try {
			auditDetailsVOs = new AuditDelegate().findPoaAuditDetails(auditFilterVO);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		if (auditDetailsVOs != null && !auditDetailsVOs.isEmpty()) {
			brokerMappingModel.setPoaHistoryDetails(BrokerMappingModelConverter.convertToAuditDetails(auditDetailsVOs,
					brokerMappingFilter.getCustomerCode()));
		} else {
			Collection<PoaHistoryDetails> poaHistoryDetails = new ArrayList<>();
			brokerMappingModel.setPoaHistoryDetails(poaHistoryDetails);
		}
		ResponseVO responseVO = new ResponseVO();
		List<BrokerMappingModel> results = new ArrayList<>();
		results.add(brokerMappingModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
			
	}
	
}
