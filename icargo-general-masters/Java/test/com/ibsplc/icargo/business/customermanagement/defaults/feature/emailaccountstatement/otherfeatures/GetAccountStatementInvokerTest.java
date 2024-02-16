/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.otherfeatures.GetAccountStatementInvokerTest.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.otherfeatures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ibsplc.icargo.business.cra.agentbilling.cass.vo.CASSFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.CRAAgentBillingProxy;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


public class GetAccountStatementInvokerTest extends AbstractFeatureTest {

	private GetAccountStatementInvoker invoker;
	private CRAAgentBillingProxy agentBillingProxy;
	private EmailAccountStatementFeatureVO featureVO;

	@Override
	public void setup() throws Exception {
		invoker = spyBean("emailAccountStatementFeature.getAccountStatementInvoker", GetAccountStatementInvoker.class);
		agentBillingProxy = mockProxy(CRAAgentBillingProxy.class);
		featureVO = new EmailAccountStatementFeatureVO();
	}

	/**
	 * trigger point is MANUAL
	 */
	@Test
	public void testCase1() throws SystemException, BusinessException {
		featureVO.setTriggerPoint("MANUAL");

		doReturn(Arrays.asList(new CustomerInvoiceAWBDetailsVO())).when(agentBillingProxy)
				.findAccountStatementForPrint(any(CustomerFilterVO.class));
		invoker.invoke(featureVO);
		assertFalse(featureVO.getCustomerInvoiceAWBDetailsVOs().isEmpty());
	}

	/**
	 * trigger point is AUTO
	 */
	public void testCase2() throws SystemException, BusinessException {
		featureVO.setTriggerPoint("MANUAL");
		List<CustomerInvoiceAWBDetailsVO> detailsVOs = new ArrayList<>();
		CustomerInvoiceAWBDetailsVO detailsVO1 = new CustomerInvoiceAWBDetailsVO();
		detailsVO1.setCustomerCode("CUS1");
		detailsVO1.setInvoiceSendMode("SOAPDF,SOAXL");
		detailsVOs.add(detailsVO1);
		CustomerInvoiceAWBDetailsVO detailsVO2 = new CustomerInvoiceAWBDetailsVO();
		detailsVO2.setCustomerCode("CUS1");
		detailsVO2.setInvoiceSendMode("SOAPDF,SOAXL");
		detailsVOs.add(detailsVO2);
		CustomerInvoiceAWBDetailsVO detailsVO3 = new CustomerInvoiceAWBDetailsVO();
		detailsVO3.setCustomerCode("CUS2");
		detailsVO3.setInvoiceSendMode("SOAPDF");
		detailsVOs.add(detailsVO3);
		CustomerInvoiceAWBDetailsVO detailsVO4 = new CustomerInvoiceAWBDetailsVO();
		detailsVO4.setCustomerCode("CUS3");
		detailsVOs.add(detailsVO4);
		CustomerInvoiceAWBDetailsVO detailsVO5 = new CustomerInvoiceAWBDetailsVO();
		detailsVO5.setCustomerCode("CUS3");
		detailsVOs.add(detailsVO5);
		CustomerInvoiceAWBDetailsVO detailsVO6 = new CustomerInvoiceAWBDetailsVO();
		detailsVO6.setCustomerCode("CUS4");
		detailsVO6.setInvoiceSendMode("WRONG");
		detailsVOs.add(detailsVO6);

		doReturn(detailsVOs).when(agentBillingProxy).findCassInvoiceAccountStatementForPrint(any(CASSFilterVO.class));
		invoker.invoke(featureVO);
		assertEquals(2, featureVO.getCustomerInvoiceAWBDetailsVOs().size());
	}

}
