/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.enrichers.DemurrageDetailsEnricherTest.java
 * Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.enrichers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.ULDDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.enrichers.DemurrageDetailsEnricherTest.java
 *	This class is used as test class for DemurrageDetailsEnricher
 */
public class DemurrageDetailsEnricherTest extends AbstractFeatureTest  {
	
	DemurrageDetailsEnricher enricher;
	TransactionVO transactionVO;
	ULDDefaultsProxy uldDefaultsProxy;
	ULDAgreementVO agreementVO;
	
	@Override
	public void setup() throws Exception {

		enricher = spy((DemurrageDetailsEnricher) ICargoSproutAdapter.getBean("UpdateULDDemurrageDetailsFeature.DemurrageDetailsEnricher"));
		transactionVO = new TransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		 uldDefaultsProxy = mockProxy(ULDDefaultsProxy.class);
		 agreementVO = new ULDAgreementVO();
		 doReturn(agreementVO).when(uldDefaultsProxy).findBestFitULDAgreement(any());
	}
	@Test
	public void shouldEnrichWithAgreementDetails() throws SystemException {
	
		agreementVO.setAgreementNumber("AGN1");
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		enricher.enrich(transactionVO);
	}
	@Test
	public void shouldProceedEvenIfAgreementIsNull() throws SystemException, ProxyException {
		ArgumentCaptor<ULDTransactionDetailsVO> captor=ArgumentCaptor.forClass(ULDTransactionDetailsVO.class);

		agreementVO.setAgreementNumber("AGN1");
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doReturn(null).when(uldDefaultsProxy).findBestFitULDAgreement(any());
		enricher.enrich(transactionVO);
		verify(uldDefaultsProxy,times(1)).findBestFitULDAgreement(captor.capture());
		ULDTransactionDetailsVO uldTransactionDetailsVO = captor.getValue();
		assertTrue(Objects.equals("AKE1234AF", uldTransactionDetailsVO.getUldNumber()));
	}
	@Test
	public void shouldProceedEvenIfProxyFails() throws SystemException, ProxyException {
		ArgumentCaptor<ULDTransactionDetailsVO> captor=ArgumentCaptor.forClass(ULDTransactionDetailsVO.class);
		agreementVO.setAgreementNumber("AGN1");
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doThrow(ProxyException.class).when(uldDefaultsProxy).findBestFitULDAgreement(any());
		enricher.enrich(transactionVO);
		verify(uldDefaultsProxy,times(1)).findBestFitULDAgreement(captor.capture());
		ULDTransactionDetailsVO uldTransactionDetailsVO = captor.getValue();
		assertTrue(Objects.equals("AKE1234AF", uldTransactionDetailsVO.getUldNumber()));
	}

}
