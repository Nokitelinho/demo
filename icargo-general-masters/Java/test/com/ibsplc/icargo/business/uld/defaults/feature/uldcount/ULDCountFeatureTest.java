/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.uldcount
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.uldcount;

import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import com.ibsplc.icargo.business.uld.defaults.feature.uldcount.enrichers.UCMCountEnricher;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.uldcount.ULDCountFeatureTest.java
 *	This class is used for testing ULDCountFeature
 */
public class ULDCountFeatureTest extends AbstractFeatureTest{ 
	
	private static final String FEATURE_ID = "GETUCMCNT";
	private static final String UCM_ENRICHER_BEAN = "ULDCountFeature.UCMCountEnricher";
	ULDCountFeature uldCountfeature;
	ULDFlightMessageReconcileVO uldFlightMessageReconcileVO;
	FeatureConfigVO featureConfigVO; 
	UCMCountEnricher ucmTotalCountEnricher;
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup()
 *	Added on 			: 06-Jul-2022
 * 	Used for 	:  invoke setup()
 *	Parameters	:	@throws Exception
 */
	@Override 
	public void setup() throws Exception {
		 
		uldFlightMessageReconcileVO = new ULDFlightMessageReconcileVO(); 
		uldFlightMessageReconcileVO.setAirportCode("AV");
		uldFlightMessageReconcileVO.setCarrierCode("SQ");
		 
		featureConfigVO=new FeatureConfigVO();
		featureConfigVO.setFeatureId(FEATURE_ID); 
		featureConfigVO.setEnricherId (Arrays.asList(UCM_ENRICHER_BEAN)); 
		EntityManagerMock.mockEntityManager();
		
		uldCountfeature = (ULDCountFeature)
				ICargoSproutAdapter.getBean("ULDCountFeature");
		
		ucmTotalCountEnricher = mockBean(UCM_ENRICHER_BEAN, UCMCountEnricher.class);
	}
/**
 * 
 * 	Method		:	ULDCountFeatureTest.shouldInvokeEnricherWhenFeatureIsExecuted
 *	Added on 	:	06-Jul-2022
 * 	Used for 	:    Invoke Enricher when Feature executed
 *	Parameters	:	@throws BusinessException
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
	@Test
	public void shouldInvokeEnricherWhenFeatureIsExecuted() throws BusinessException, SystemException{
		uldCountfeature.execute(uldFlightMessageReconcileVO);
		verify(ucmTotalCountEnricher, times(1)).enrich(any());
	} 
}
