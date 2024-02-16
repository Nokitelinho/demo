/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.uldcount.enrichers
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.uldcount.enrichers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.assertEquals;

import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.uldcount.enrichers.UCMTotalCountEnricherTest.java
 *	This class is used for getting UCM Reported Count using UCMTotalCountEnricher
 */
public class UCMCountEnricherTest extends AbstractFeatureTest{
	
	private UCMCountEnricher ucmCountEnricher;
	private ULDFlightMessageReconcileVO uldFlightMessageReconcileVO; 
	private ULDController uldController;
	Collection<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileVOs;
	Collection<UldManifestVO> manifestedVOs;
	Collection<ULDFlightMessageReconcileDetailsVO> reconcileVOs; 
	private static final String UCM_ENRICHER_BEAN = "ULDCountFeature.UCMCountEnricher";
	private static final String CMPCOD="AV";
	private static final String FLTNO="AV1231";
	private static final String ULDSTATUS="S";
	private static final String ULDSTATUSMAN="MAN";
	private static final String ULDSTATUSMFT="MFT";
	private static final String BAGGAGE="B";
	ULDFlightMessageReconcileDetailsVO reconcileDetailsVO;
	UldManifestVO manifestVO;
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup()
 *	Added on 			: 06-Jul-2022
 * 	Used for 	:        setup()
 *	Parameters	:	@throws Exception
 */
	@Override
	public void setup() throws Exception {
		uldFlightMessageReconcileVO = new ULDFlightMessageReconcileVO();
		reconcileDetailsVO = new ULDFlightMessageReconcileDetailsVO();
		uldFlightMessageReconcileVOs = new ArrayList<>();
		reconcileDetailsVO.setCompanyCode(CMPCOD); 
		reconcileDetailsVO.setFlightNumber(FLTNO);
		reconcileDetailsVO.setUldStatus(ULDSTATUS); 
		reconcileDetailsVO.setFlightCarrierIdentifier(123);
		reconcileDetailsVO.setFlightSequenceNumber(432);
		reconcileDetailsVO.setAirportCode("CDG");
		uldFlightMessageReconcileVOs.add(reconcileDetailsVO);
		uldController = mockBean("ULDController",ULDController.class);
		reconcileVOs=new ArrayList<>();
		manifestVO=new UldManifestVO();
		manifestedVOs=new ArrayList<>();
		manifestVO.setAirportCode("CDG");
		manifestVO.setCarrierId(435);
		manifestVO.setFlightNumber("AV2566");
		ucmCountEnricher = (UCMCountEnricher) ICargoSproutAdapter
				.getBean(UCM_ENRICHER_BEAN);
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldInvokeEnrich
	 *	Added on 	:	06-Jul-2022
	 * 	Used for 	:   for invoke Enricher
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test 
	public void shouldInvokeEnrich()   
				throws SystemException {
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getReconcileDetailsVOs().stream().iterator().next()
				.getUldStatus(), is(ULDSTATUS));
		assertTrue(Objects.nonNull(uldFlightMessageReconcileVO.getUcmReportedCount()));
		assertThat(uldFlightMessageReconcileVO.getUcmReportedCount(), is(1));
		
	} 
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldNotInvokeEnrich
	 *	Added on 	:	06-Jul-2022
	 * 	Used for 	:   should not invoke Enricher 
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test 
	public void shouldNotInvokeEnrich()    
				throws SystemException {
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO); 
		assertTrue(Objects.isNull(uldFlightMessageReconcileVO.getReconcileDetailsVOs()));
		assertThat(uldFlightMessageReconcileVO.getUcmReportedCount(), is(0));
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldInvokeEnrich1
	 *	Added on 	:	18-Jul-2022
	 * 	Used for 	:   invoke update for manualUldCount
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test 
	public void shouldUpdateForManualUldCount()   
				throws SystemException {
		reconcileDetailsVO.setUldSource(ULDSTATUSMAN);
		ULDFlightMessageReconcileDetailsVO reconcileDetailsVO1= new ULDFlightMessageReconcileDetailsVO();
		reconcileDetailsVO1.setUldSource(ULDSTATUSMFT);
		uldFlightMessageReconcileVOs.add(reconcileDetailsVO1);
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getReconcileDetailsVOs().stream().iterator().next()
				.getUldSource(), is("MAN"));
		assertTrue(Objects.nonNull(uldFlightMessageReconcileVO.getManualUldCount()));
		assertThat(uldFlightMessageReconcileVO.getManualUldCount(), is(1));
	} 
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldNotUpdateForManualUldCount
	 *	Added on 	:	18-Jul-2022
	 * 	Used for 	:   should not invoke update for manualUldCount
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test 
	public void shouldNotUpdateForManualUldCount()   
				throws SystemException {
		reconcileDetailsVO.setUldSource(ULDSTATUSMFT);
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertTrue(Objects.nonNull(uldFlightMessageReconcileVO.getManualUldCount()));
		assertThat(uldFlightMessageReconcileVO.getManualUldCount(), is(0));
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldUpdateForManifestedUldCount
	 *	Added on 	:	24-Jul-2022
	 * 	Used for 	:	For Count ManifestedUld 
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void  shouldUpdateForManifestedUldCount()   
			throws SystemException { 
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		manifestedVOs.add(manifestVO);
		EntityManagerMock.mockEntityManager();
		ContextUtils.storeTxBusinessParameter(ULDSTATUSMFT,(Serializable) manifestedVOs);
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat((int)manifestedVOs.stream().count(),is(1));
		
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldNotUpdateForManifestedUldCount
	 *	Added on 	:	24-Jul-2022
	 * 	Used for 	:	No Update for ManifestedUld count
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void  shouldNotUpdateForManifestedUldCount()   
			throws SystemException { 
		
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		EntityManagerMock.mockEntityManager();
		ContextUtils.storeTxBusinessParameter(ULDSTATUSMFT,(Serializable) manifestedVOs);
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat((int)manifestedVOs.stream().count(),is(0));
 
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldUpdateForBaggageUldCount
	 *	Added on 	:	24-Jul-2022
	 * 	Used for 	:	For count BaggageUld
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void shouldUpdateForBaggageUldCount()   
			throws SystemException { 
		reconcileDetailsVO.setContent(BAGGAGE);
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs); 
		EntityManagerMock.mockEntityManager();
		doReturn(uldFlightMessageReconcileVO).when(uldController).listUCMMessage(any());
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getReconcileDetailsVOs().stream().iterator().next().getContent(), is(BAGGAGE));
		
	} 
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldNotUpdateForBaggageUldCount
	 *	Added on 	:	24-Jul-2022
	 * 	Used for 	:	Should not update count of BaggageUld
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void shouldNotUpdateForBaggageUldCount()   
			throws SystemException {
		
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		EntityManagerMock.mockEntityManager();
		doReturn(uldFlightMessageReconcileVO).when(uldController).listUCMMessage(any());
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO); 
		assertEquals((uldFlightMessageReconcileVO.getReconcileDetailsVOs().stream().iterator().next().getContent()),null);
		
	}  
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldUpdateTotalUldCountWhenHaveValueInManUldMftUldBagUld
	 *	Added on 	:	26-Jul-2022
	 * 	Used for 	:	Should update totalUldCount when have value in BaggageULD, ManualULD and ManifestULD
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void shouldUpdateTotalUldCountWhenHaveValueInManUldMftUldBagUld()   
			throws SystemException {
		
		reconcileDetailsVO.setUldSource(ULDSTATUSMAN);
		ULDFlightMessageReconcileDetailsVO reconcileDetailsVO1= new ULDFlightMessageReconcileDetailsVO();
		reconcileDetailsVO1.setUldSource(ULDSTATUSMFT);
		uldFlightMessageReconcileVOs.add(reconcileDetailsVO1);
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		manifestedVOs.add(manifestVO);
		EntityManagerMock.mockEntityManager();
		ContextUtils.storeTxBusinessParameter(ULDSTATUSMFT,(Serializable) manifestedVOs);
		reconcileDetailsVO.setContent(BAGGAGE);
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs); 
		EntityManagerMock.mockEntityManager();
		doReturn(uldFlightMessageReconcileVO).when(uldController).listUCMMessage(any());
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getTotalUldCount(), is(3));
		
	} 
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldUpdateTotalUldCountWhenHaveValueInManUldAndNotInMftUldBagUld
	 *	Added on 	:	26-Jul-2022
	 * 	Used for 	:	Should update totalUldCount when have value in ManualULD and not in BaggageULD and ManifesTULD
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void shouldUpdateTotalUldCountWhenHaveValueInManUldAndNotInMftUldBagUld()   
			throws SystemException {
		
		reconcileDetailsVO.setUldSource(ULDSTATUSMAN);
		ULDFlightMessageReconcileDetailsVO reconcileDetailsVO1= new ULDFlightMessageReconcileDetailsVO();
		reconcileDetailsVO1.setUldSource(ULDSTATUSMFT);
		uldFlightMessageReconcileVOs.add(reconcileDetailsVO1);
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		EntityManagerMock.mockEntityManager();
		ContextUtils.storeTxBusinessParameter(ULDSTATUSMFT,(Serializable) manifestedVOs);
		EntityManagerMock.mockEntityManager();
		doReturn(uldFlightMessageReconcileVO).when(uldController).listUCMMessage(any());
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getTotalUldCount(), is(1));
		
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldUpdateTotalUldCountWhenHaveValueInMftUldAndNotInManUldBagUld
	 *	Added on 	:	26-Jul-2022
	 * 	Used for 	:	Should update totalUldCount when have value in ManifestULD and not in BaggageULD and ManifesTULD
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void shouldUpdateTotalUldCountWhenHaveValueInMftUldAndNotInManUldBagUld()   
			throws SystemException {
		
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		manifestedVOs.add(manifestVO);
		EntityManagerMock.mockEntityManager();
		ContextUtils.storeTxBusinessParameter(ULDSTATUSMFT,(Serializable) manifestedVOs);
		EntityManagerMock.mockEntityManager();
		doReturn(uldFlightMessageReconcileVO).when(uldController).listUCMMessage(any());
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getTotalUldCount(), is(1));
		
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldUpdateTotalUldCountWhenHaveValueInBagUldAndNotInManUldMftUld
	 *	Added on 	:	26-Jul-2022
	 * 	Used for 	:	Should update totalUldCount when have value in BaggageULD and not in ManualULD and ManifesTULD 
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void shouldUpdateTotalUldCountWhenHaveValueInBagUldAndNotInManUldMftUld()   
			throws SystemException {
		
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		reconcileDetailsVO.setContent(BAGGAGE);
		EntityManagerMock.mockEntityManager();
		ContextUtils.storeTxBusinessParameter(ULDSTATUSMFT,(Serializable) manifestedVOs);
		EntityManagerMock.mockEntityManager();
		doReturn(uldFlightMessageReconcileVO).when(uldController).listUCMMessage(any());
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getTotalUldCount(), is(1));
		
	}
	/**
	 * 
	 * 	Method		:	UCMTotalCountEnricherTest.shouldNotUpdateTotalUldCountWhenNoValueInBagUldManUldMftUld
	 *	Added on 	:	26-Jul-2022
	 * 	Used for 	:	Should not update totalUldCount when no value in ManualULD,ManifesTULD and BaggageULD
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	@Test
	public void shouldNotUpdateTotalUldCountWhenNoValueInBagUldManUldMftUld()   
			throws SystemException {
		
		uldFlightMessageReconcileVO.setReconcileDetailsVOs(uldFlightMessageReconcileVOs);
		EntityManagerMock.mockEntityManager();
		ContextUtils.storeTxBusinessParameter(ULDSTATUSMFT,(Serializable) manifestedVOs);
		doReturn(uldFlightMessageReconcileVO).when(uldController).listUCMMessage(any());
		ucmCountEnricher.enrich(uldFlightMessageReconcileVO);
		assertThat(uldFlightMessageReconcileVO.getTotalUldCount(), is(0));
		
	}
	
}