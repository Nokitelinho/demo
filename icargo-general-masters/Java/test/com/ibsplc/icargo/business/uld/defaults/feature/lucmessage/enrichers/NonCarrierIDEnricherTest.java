/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.NonCarrierIDEnricherTest.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;

import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedDefaultsProxy;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.NonCarrierIDEnricherTest.java
 *	This class is used for
 */
public class NonCarrierIDEnricherTest extends AbstractFeatureTest{

	private ULDController uldController;
	NonCarrierIDEnricher enricher;
	TransactionVO transactionVO;
	SharedDefaultsProxy sharedDefaultsProxy;
	Collection<GeneralParameterConfigurationVO> generalParameterConfigurationVOs = null;
	@Override
	public void setup() throws Exception {
		uldController = mockBean("ULDController", ULDController.class);
		enricher = spy((NonCarrierIDEnricher) 
				ICargoSproutAdapter.getBean("LUCMessageFeature.NonCarrierIDEnricher"));
		transactionVO = new TransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		generalParameterConfigurationVOs = new ArrayList<>();
	}

	@Test
	public void shouldEnrichAgentPartyDetails() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.nonNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldEnrichAgentPartyDetailsWhenPartyTypeAirline() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AIRLINE);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.nonNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldEnrichAgentPartyDetailsWhenPartyTypeCustomer() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType("C");
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.nonNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldEnrichAgentPartyDetailsWhenPartyTypeNotValid() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.OTHERS);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldNotEnrichAgentPartyDetailsWhenConfigurationNotExisit() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(null).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldNotEnrichAgentPartyDetailsWhenInvalidPartyType() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.OTHERS);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(null).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldNotEnrichNonCarrierIdWhenMatchingConditionFailed() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.BREAKDOWN);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("AGENT1");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldNotEnrichNonCarrierIdWhenMatchingConditionFailedOnParameteCode() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.BREAKDOWN);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("AGENT");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldNotEnrichNonCarrierIdWhenMatchingConditionFailedTransactionStationCode() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.BREAKDOWN);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("AGENT");
		config.setConfigurationReferenceTwo("CDG");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldEnrichAgentPartyDetailsForTransactionOtherThanACP() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.BREAKDOWN);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("AGENT");
		transactionDetailVO.setFromPartyCode("EK");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(generalParameterConfigurationVOs).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.nonNull(transactionDetailVO.getNonCarrierId()));
	}
	
	@Test
	public void shouldNotEnrichAgentPartyDetailsForLUCNotRequired() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(false);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionDetailVO.setToPartyCode("EK");
		transactionDetailVO.setFromPartyCode("AGENT");
		transactionDetailVO.setTransactionStationCode("DXB");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		
		GeneralParameterConfigurationVO config = new GeneralParameterConfigurationVO();
		config.setConfigurationReferenceOne("EK");
		config.setConfigurationReferenceTwo("DXB");
		config.setParmeterCode("AGENT");
		config.setParameterValue("DHL");
		generalParameterConfigurationVOs.add(config);
		doReturn(null).when(sharedDefaultsProxy).findGeneralParameterConfigurations(any(Collection.class));
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getNonCarrierId()));
	}
	/***Further test cases to be covered during re-factoring**/

}
