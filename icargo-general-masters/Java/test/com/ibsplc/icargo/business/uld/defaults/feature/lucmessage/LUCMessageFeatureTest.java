/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.LUCMessageFeatureTest.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


import com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.AgentPartyTypeDetailsEnricher;
import com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.NonCarrierIDEnricher;
import com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.validators.ULDFormatValidator;
import com.ibsplc.icargo.business.uld.defaults.proxy.BusinessEvaluatorDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.LUCMessageFeatureTest.java
 *	This class is used for
 */
public class LUCMessageFeatureTest extends AbstractFeatureTest{

	LUCMessageFeature feature;
	AgentPartyTypeDetailsEnricher agentPartyTypeDetailsEnricher;
	NonCarrierIDEnricher nonCarrierIDEnricher;
	ULDFormatValidator uldFormatValidator;
	TransactionVO transactionVO;
	BusinessEvaluatorDefaultsProxy businessEvaluatorDefaultsProxy;
	@Override
	public void setup() throws Exception {
		feature = (LUCMessageFeature)ICargoSproutAdapter.getBean("LUCMessageFeature");
		agentPartyTypeDetailsEnricher = mockBean("LUCMessageFeature.AgentPartyTypeDetailsEnricher", AgentPartyTypeDetailsEnricher.class);
		nonCarrierIDEnricher = mockBean("LUCMessageFeature.NonCarrierIDEnricher", NonCarrierIDEnricher.class);
		uldFormatValidator = mockBean("LUCMessageFeature.ULDFormatValidator", ULDFormatValidator.class);
		transactionVO = new TransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		businessEvaluatorDefaultsProxy = mockProxy(BusinessEvaluatorDefaultsProxy.class);
	}

	@Test
	public void shouldFetchBEVConfigurationWhenFeatureIsExecuted() throws BusinessException, SystemException{
		
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		FeatureConfigVO config = new FeatureConfigVO();
		config.setEnricherId(new ArrayList<String>());
		config.setValidatorIds(new ArrayList<String>());
		doReturn(config).when(businessEvaluatorDefaultsProxy).fetchFeatureRules(any());
		feature.execute(transactionVO);
		verify(agentPartyTypeDetailsEnricher, times(1)).enrich(any());
		verify(nonCarrierIDEnricher, times(1)).enrich(any());
		verify(uldFormatValidator, times(1)).validate(any());
	}
	
	@Test
	public void shouldSetEmptyArrayWhenEnricherAndValidatorConfigIsNull() throws BusinessException, SystemException{
		
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doReturn(new FeatureConfigVO()).when(businessEvaluatorDefaultsProxy).fetchFeatureRules(any());
		feature.execute(transactionVO);
		verify(agentPartyTypeDetailsEnricher, times(1)).enrich(any());
		verify(nonCarrierIDEnricher, times(1)).enrich(any());
		verify(uldFormatValidator, times(1)).validate(any());
	}
	
	@Test
	public void doNothingWhenBEVConfigProxyServiceFailed() throws BusinessException, SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doThrow(ProxyException.class).when(businessEvaluatorDefaultsProxy).fetchFeatureRules(any());
		feature.execute(transactionVO);
	}
	

}
