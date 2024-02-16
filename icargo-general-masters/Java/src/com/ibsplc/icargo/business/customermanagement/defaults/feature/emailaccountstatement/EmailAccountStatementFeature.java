/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.EmailAccountStatementFeature.java
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
package com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.customermanagement.defaults.CustomerBusinessException;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.MsgbrokerMessageProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.EmailAccountStatementFeature.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-104246	 :	26-Jul-2021	:	Created
 */

@FeatureComponent("customermanagement.defaults.emailAccountStatementFeature")
@Feature(exception = CustomerBusinessException.class)
public class EmailAccountStatementFeature extends AbstractFeature<EmailAccountStatementFeatureVO> {

	@Autowired
	private Proxy proxy;

	@Override
	protected void preInvoke(EmailAccountStatementFeatureVO featureVO) throws SystemException, BusinessException {
		invoke("emailAccountStatementFeature.getAccountStatementInvoker", featureVO);
	}

	@Override
	protected FeatureConfigVO fetchFeatureConfig(EmailAccountStatementFeatureVO featureVO) {
		FeatureConfigVO vo = new FeatureConfigVO();
		vo.setValidatorIds(new ArrayList<>());

		List<String> enrichers = new ArrayList<>();
		enrichers.add("emailAccountStatementFeature.createMessageTemplateEnricher");
		vo.setEnricherId(enrichers);
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected EmailAccountStatementFeatureVO perform(EmailAccountStatementFeatureVO featureVO)
			throws SystemException, BusinessException {
		Collection<BaseMessageVO> baseMessageVOs = new ArrayList<>();
		baseMessageVOs.addAll(featureVO.getEmailTemplateVOs());

		proxy.get(MsgbrokerMessageProxy.class).encodeAndSaveMessages(baseMessageVOs);

		return featureVO;
	}

}
