/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeatureTest.java
 * Copyright 2023 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.tgc.defaults.vo.VisitDeclarationVO;
import com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.enrichers.DemurrageDetailsEnricher;
import com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.persistors.UpdateDemurrageDetailsPersistor;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.BusinessEvaluatorDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.proxy.ULDDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeatureTest.java
 *	This class is used as test file for UpdateULDDemurrageDetailsFeature
 */
public class UpdateULDDemurrageDetailsFeatureTest  extends AbstractFeatureTest {
	
	private UpdateULDDemurrageDetailsFeature feature;
	private DemurrageDetailsEnricher demurrageEnricher;
	private static final String ENRICHER="UpdateULDDemurrageDetailsFeature.DemurrageDetailsEnricher"; 
	TransactionVO transactionVO;
	ULDTransactionDetailsVO uldTransactionDetailsVO;
	Collection<ULDTransactionDetailsVO> transactionDetailsVOs;
	private UpdateDemurrageDetailsPersistor persistor;
	ULDDefaultsProxy uldDefaultsProxy;
	ULDAgreementVO agreementVO;
	BusinessEvaluatorDefaultsProxy businessEvaluatorDefaultsProxy;
	FeatureConfigVO featureConfigVO;
	@Override
	public void setup() throws Exception {
		featureConfigVO = new FeatureConfigVO();
		transactionVO = new TransactionVO();
		businessEvaluatorDefaultsProxy = mockProxy(BusinessEvaluatorDefaultsProxy.class);
		
		feature = (UpdateULDDemurrageDetailsFeature)ICargoSproutAdapter.getBean("UpdateULDDemurrageDetailsFeature");
		 demurrageEnricher = mockBean(ENRICHER, DemurrageDetailsEnricher.class);
		 transactionDetailsVOs = new ArrayList<>();
		 persistor = mockBean("UpdateULDDemurrageDetailsFeature.UpdateDemurrageDetailsPersistor", UpdateDemurrageDetailsPersistor.class);
		 uldTransactionDetailsVO= new ULDTransactionDetailsVO();
		 uldDefaultsProxy = mockProxy(ULDDefaultsProxy.class);
		 agreementVO = new ULDAgreementVO();
		 doReturn(agreementVO).when(uldDefaultsProxy).findBestFitULDAgreement(any());
		 featureConfigVO.setEnricherId(Arrays.asList(ENRICHER));
		 doReturn(featureConfigVO).when(businessEvaluatorDefaultsProxy).fetchFeatureRules(any(FeatureConfigVO.class));
		
	}
	
	@Test
	public void shouldInvokeFeatureAndEnricherWhenExecuted() throws BusinessException, SystemException{
		doNothing().when(persistor).persist(any());
		uldTransactionDetailsVO.setUldNumber("AKE12345EK");
		transactionDetailsVOs.add(uldTransactionDetailsVO);
		transactionVO.setUldTransactionDetailsVOs(transactionDetailsVOs);
		transactionVO.setTransactionType("T");
		agreementVO.setAgreementNumber("AGN1");
		feature.execute(transactionVO);
		verify(demurrageEnricher, times(1)).enrich(any());
	} 
	
	@Test
	public void shouldThrowProxyExceptionIsThrownFromFeatureConfigFetching() throws BusinessException, SystemException {
		doThrow(ProxyException.class).when(businessEvaluatorDefaultsProxy).fetchFeatureRules(any(FeatureConfigVO.class));
		feature.execute(transactionVO);
		verify(demurrageEnricher, times(0)).enrich(any());

	}
	

}
