/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.EmailAccountStatementFeatureTest.java
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;

import com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntController;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.enrichers.MessageTemplateEnricher;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.otherfeatures.GetAccountStatementInvoker;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.MsgbrokerMessageProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.template.email.CustomerAccountStatementEmailTemplateVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.EmailAccountStatementFeatureTest.java
 *	Version	:	Name						  :	Date					:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for  IASCB-104246  :	03-Aug-2021	:	Created
 */

public class EmailAccountStatementFeatureTest extends AbstractFeatureTest {

	private EmailAccountStatementFeature feature;
	private GetAccountStatementInvoker invoker;
	private MessageTemplateEnricher enricher;
	private MsgbrokerMessageProxy proxy;
	
	@Override
	public void setup() throws Exception {
		feature = (EmailAccountStatementFeature) ICargoSproutAdapter.getBean("customermanagement.defaults.emailAccountStatementFeature");
		mockBean("customerMgmntController", CustomerMgmntController.class);
		invoker = mockBean("emailAccountStatementFeature.getAccountStatementInvoker", GetAccountStatementInvoker.class);
		enricher = mockBean("emailAccountStatementFeature.createMessageTemplateEnricher", MessageTemplateEnricher.class);
		proxy = mockProxy(MsgbrokerMessageProxy.class);
	}

	@Test
	public void doVerifyAllInvokersAndEnrichersAndProxyHasBeenInvoked() throws BusinessException, SystemException {
		EmailAccountStatementFeatureVO featureVO = new EmailAccountStatementFeatureVO();
		featureVO.setEmailTemplateVOs(Arrays.asList(new CustomerAccountStatementEmailTemplateVO()));
		
		doNothing().when(invoker).invoke(any(EmailAccountStatementFeatureVO.class));
		doNothing().when(enricher).enrich(any(EmailAccountStatementFeatureVO.class));
		doNothing().when(proxy).encodeAndSaveMessages(anyListOf(BaseMessageVO.class));
		feature.execute(featureVO);

		verify(invoker, times(1)).invoke(any(EmailAccountStatementFeatureVO.class));
		verify(enricher, times(1)).enrich(any(EmailAccountStatementFeatureVO.class));
		verify(proxy, times(1)).encodeAndSaveMessages(anyListOf(BaseMessageVO.class));
	}

}
