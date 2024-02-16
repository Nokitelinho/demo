/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.AgentPartyTypeDetailsEnricherTest.java
 *
 *	Created on	:	13-Dec-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers;

import static org.mockito.Mockito.spy;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;
import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.assertTrue;

import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.enrichers.AgentPartyTypeDetailsEnricherTest.java
 *	This class is used for
 */
public class AgentPartyTypeDetailsEnricherTest extends AbstractFeatureTest {
	
	private ULDController uldController;
	AgentPartyTypeDetailsEnricher enricher;
	TransactionVO transactionVO;

	@Override
	public void setup() throws Exception {
		uldController = mockBean("ULDController", ULDController.class);
		enricher = spy((AgentPartyTypeDetailsEnricher) ICargoSproutAdapter.getBean("LUCMessageFeature.AgentPartyTypeDetailsEnricher"));
		transactionVO = new TransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
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
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doReturn("12344-11111111").when(uldController).findCRNForULDTransaction(any(), any());
		enricher.enrich(transactionVO);
		assertTrue(Objects.nonNull(transactionDetailVO.getFromPartyCode()));
		assertTrue(Objects.nonNull(transactionDetailVO.getControlReceiptNumber()));
	}
	
	@Test
	public void shouldNotEnrichAgentPartyDetailsWhenPartyTypeNotAgent() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setFromPartyCode("EK");
		transactionDetailVO.setControlReceiptNumber("111111-0000000000");
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AIRLINE);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		enricher.enrich(transactionVO);
		assertTrue(Objects.equals("EK", transactionDetailVO.getFromPartyCode()));
		assertTrue(Objects.equals("111111-0000000000", transactionDetailVO.getControlReceiptNumber()));

	}
	
	@Test
	public void shouldNotCRNWhenFindCRNServiceReturnsNull() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.ACCEPTANCE);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doReturn(null).when(uldController).findCRNForULDTransaction(any(), any());
		enricher.enrich(transactionVO);
		assertTrue(Objects.nonNull(transactionDetailVO.getFromPartyCode()));
		assertTrue(Objects.isNull(transactionDetailVO.getControlReceiptNumber()));
	}
	@Test
	public void shouldNotEnrichAgentPartyDetailsForTransactionsOtherThanAcceptance() throws SystemException {
		transactionVO.setCompanyCode("AV");
		transactionVO.setTransactionStation("DXB");
		transactionVO.setTransactionId(FlightDetailsVO.BREAKDOWN);
		ULDTransactionDetailsVO transactionDetailVO = new ULDTransactionDetailsVO();
		transactionDetailVO.setLUCMessageRequired(true);
		transactionDetailVO.setPartyType(ULDTransactionDetailsVO.AGENT);
		transactionDetailVO.setUldNumber("AKE1234AF");
		transactionVO.getUldTransactionDetailsVOs().add(transactionDetailVO);
		doReturn("12344-11111111").when(uldController).findCRNForULDTransaction(any(), any());
		enricher.enrich(transactionVO);
		assertTrue(Objects.isNull(transactionDetailVO.getFromPartyCode()));
	}
	

}
