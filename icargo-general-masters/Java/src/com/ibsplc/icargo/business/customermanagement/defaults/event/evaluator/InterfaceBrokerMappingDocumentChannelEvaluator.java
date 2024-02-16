/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator.InterfaceBrokerMappingDocumentChannelEvaluator.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.evaluator.Evaluator;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator.InterfaceBrokerMappingDocumentChannelEvaluator.java
 *	Version	:	Name						 :	Date					:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152053	 :	08-Aug-2022	:	Created
 */

public class InterfaceBrokerMappingDocumentChannelEvaluator implements Evaluator<CustomerVO> {

	@Override
	public boolean evaluate(CustomerVO customerVO) {
		boolean status = false;

		if (customerVO.getCustomerAgentVOs() != null && !customerVO.getCustomerAgentVOs().isEmpty()) {
			status = canTriggerInterfacing(customerVO.getCustomerAgentVOs());
		}

		if (!status && customerVO.getCustomerConsigneeVOs() != null && !customerVO.getCustomerConsigneeVOs().isEmpty()) {
			status = canTriggerInterfacing(customerVO.getCustomerConsigneeVOs());
		}

		return status;
	}

	private boolean canTriggerInterfacing(Collection<CustomerAgentVO> customerAgentVOs) {
		CustomerAgentVO documentVO = customerAgentVOs.stream()
				.filter(agentVO -> OPERATION_FLAG_INSERT.equals(agentVO.getOperationFlag()))
				.findFirst()
				.orElse(null);

		return Objects.nonNull(documentVO);
	}

}
