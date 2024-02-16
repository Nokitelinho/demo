/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.CassInvoiceAccountStatementEmailChannelMapper.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */

/**
 * Each method of this class must detail the functionality briefly in the javadoc comments preceding the method.
 * This is to be followed in case of change of functionality also.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.event.mapper;

import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.TRIGGER_AUTO;

import java.util.HashMap;

import com.ibsplc.icargo.business.cra.agentbilling.cass.vo.CASSFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.framework.event.EventConstants.ParameterMap;
import com.ibsplc.icargo.framework.event.EventMapper;
import com.ibsplc.icargo.framework.event.vo.EventVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.CassInvoiceAccountStatementEmailChannelMapper.java
 *	Version	:	Name						 :	Date					:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-118899	 :	09-Aug-2021	:	Created
 */

public class CassInvoiceAccountStatementEmailChannelMapper implements EventMapper {

	@Override
	public EventVO mapToEventVO(HashMap<ParameterMap, Object[]> parameterMap) throws Throwable {
		Object[] parameters = parameterMap.get(ParameterMap.PARAMETERS);
		return new EventVO((String) parameterMap.get(ParameterMap.EVENT)[0], parameters);
	}

	public static EmailAccountStatementFeatureVO mapToEmailAccountStatementFeatureVO(CASSFilterVO filterVO) {
		EmailAccountStatementFeatureVO featureVO = new EmailAccountStatementFeatureVO();
		featureVO.setCompanyCode(filterVO.getCompanyCode());
		featureVO.setCountryCode(filterVO.getCountry());
		featureVO.setInvoiceNumber(filterVO.getBillingReferenceNumber());
		featureVO.setTriggerPoint(TRIGGER_AUTO);
		return featureVO;
	}

}
