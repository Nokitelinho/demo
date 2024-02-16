/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.enrichers.MessageTemplateEnricherTest.java
 *
 * Copyright 2021 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.enrichers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntController;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedBillingSiteProxy;
import com.ibsplc.icargo.business.shared.billingsite.vo.BillingSiteFilterVO;
import com.ibsplc.icargo.business.shared.billingsite.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class MessageTemplateEnricherTest extends AbstractFeatureTest{

	private MessageTemplateEnricher enricher;
	private CustomerMgmntController controller;
	private SharedBillingSiteProxy sharedBillingSiteProxy;
	private EmailAccountStatementFeatureVO featureVO;
	
	@Override
	public void setup() throws Exception {
		enricher = spyBean("emailAccountStatementFeature.createMessageTemplateEnricher", MessageTemplateEnricher.class);
		controller = mockBean("customerMgmntController", CustomerMgmntController.class);
		sharedBillingSiteProxy = mockProxy(SharedBillingSiteProxy.class);
		
		CustomerInvoiceAWBDetailsVO invoiceAWBDetailsVO = new CustomerInvoiceAWBDetailsVO();
		invoiceAWBDetailsVO.setInvoiceSendMode("SOAPDF");
		invoiceAWBDetailsVO.setInvoiceTooDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		List<CustomerInvoiceAWBDetailsVO> invoiceAWBDetailsVOs = new ArrayList<>(Arrays.asList(invoiceAWBDetailsVO));
		List<List<CustomerInvoiceAWBDetailsVO>> customerInvoiceVOList = new ArrayList<>();
		customerInvoiceVOList.add(invoiceAWBDetailsVOs);
		featureVO = new EmailAccountStatementFeatureVO();
		featureVO.setCustomerInvoiceAWBDetailsVOs(customerInvoiceVOList);
	}
	
	/**
	 * Scenario : triggerPoint is MANUAL and distributionMode is SOAXL
	 */
	@Test
	public void testCase1() throws ProxyException, SystemException {
		featureVO.setTriggerPoint("MANUAL");
		featureVO.setInvoiceDistributionMode(Arrays.asList("SOAXL"));
		
		doReturn(new BillingSiteVO()).when(sharedBillingSiteProxy).findBillingSiteDetails(any(BillingSiteFilterVO.class));
		doReturn("XL Byte".getBytes()).when(controller).createWorkBookBytesForExcelReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		enricher.enrich(featureVO);
		verify(controller, times(1)).createWorkBookBytesForExcelReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		assertFalse(featureVO.getEmailTemplateVOs().iterator().next().getMessageAttachments().isEmpty());
	}
	
	/**
	 * Scenario : triggerPoint is MANUAL and distributionMode is SOAPDF
	 */
	@Test
	public void testCase2() throws ProxyException, SystemException {
		featureVO.setTriggerPoint("MANUAL");
		featureVO.setInvoiceDistributionMode(Arrays.asList("SOAPDF"));
		
		BillingSiteVO siteVO = new BillingSiteVO();
		siteVO.setEmailID("emailid");
		
		doReturn(siteVO).when(sharedBillingSiteProxy).findBillingSiteDetails(any(BillingSiteFilterVO.class));
		doReturn("PDF Byte".getBytes()).when(controller).createBytesForPDFReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		enricher.enrich(featureVO);
		verify(controller, times(1)).createBytesForPDFReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		assertFalse(featureVO.getEmailTemplateVOs().iterator().next().getMessageAttachments().isEmpty());
	}

	/**
	 * Scenario : triggerPoint is AUTO and distributionMode is SOAPDF
	 */
	@Test
	public void testCase3() throws SystemException {
		featureVO.setTriggerPoint("AUTO");

		doReturn("PDF Byte".getBytes()).when(controller).createBytesForPDFReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		enricher.enrich(featureVO);
		verify(controller, times(1)).createBytesForPDFReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		assertFalse(featureVO.getEmailTemplateVOs().iterator().next().getMessageAttachments().isEmpty());
	}
	
	@Test
	public void testCase4() throws ProxyException, SystemException {
		featureVO.setTriggerPoint("MANUAL");
		featureVO.setInvoiceDistributionMode(Arrays.asList("SOAXL"));
		
		doThrow(ProxyException.class).when(sharedBillingSiteProxy).findBillingSiteDetails(any(BillingSiteFilterVO.class));
		doThrow(SystemException.class).when(controller).createWorkBookBytesForExcelReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		enricher.enrich(featureVO);
		assertTrue(featureVO.getEmailTemplateVOs().iterator().next().getMessageAttachments().isEmpty());
	}
	
	@Test()
	public void testCase5() throws SystemException {
		featureVO.setTriggerPoint("AUTO");
		featureVO.getCustomerInvoiceAWBDetailsVOs().iterator().next().get(0).setBillingSiteEmail("emailid");

		doThrow(SystemException.class).when(controller).createBytesForPDFReport(anyListOf(CustomerInvoiceAWBDetailsVO.class));
		enricher.enrich(featureVO);
		assertTrue(featureVO.getEmailTemplateVOs().iterator().next().getMessageAttachments().isEmpty());
	}

}
