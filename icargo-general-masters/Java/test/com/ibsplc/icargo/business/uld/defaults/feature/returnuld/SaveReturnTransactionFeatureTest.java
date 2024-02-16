package com.ibsplc.icargo.business.uld.defaults.feature.returnuld;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.feature.returnuld.enrichers.DemurrageEnricher;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.returnuld.DemurrageFeatureTest.java
 *	This class is used for Testing 
 */
public class SaveReturnTransactionFeatureTest extends AbstractFeatureTest {
	
	
	TransactionListVO transactionListVO;
    Collection<ULDTransactionDetailsVO> transactionVOs;
    ULDTransactionDetailsVO uldTransactionDetailVO;
    private static final String ULDNUM="AKE0099EK";
    private static final String TRANSACTION="T";
    private static final String CMPCOD = "AV";
	private SaveReturnTransactionFeature demurrageFeature;
	private DemurrageEnricher demurrageEnricher;
    private ULDController uldController;
	private static final String ENRICHER="SaveReturnTransactionFeature.DemurrageEnricher"; 
    ULDTransactionDetailsVO transactionVO;
	
	@Override 
	public void setup() throws Exception {
		
		transactionListVO = new TransactionListVO();
		transactionVOs = new ArrayList<>();
		uldTransactionDetailVO = new ULDTransactionDetailsVO();
		demurrageFeature = (SaveReturnTransactionFeature)ICargoSproutAdapter.getBean("SaveReturnTransactionFeature");
		demurrageEnricher = mockBean(ENRICHER, DemurrageEnricher.class);
        uldController = mockBean("ULDController", ULDController.class);
        transactionVO = new ULDTransactionDetailsVO();
	}

	@Test
	public void shouldInvokeEnricherWhenFeatureIsExecuted() throws BusinessException, SystemException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		transactionVOs.add(uldTransactionDetailVO);
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("R");
		demurrageFeature.execute(transactionListVO);
		verify(demurrageEnricher, times(1)).enrich(any());
	} 
	
	@Test
	public void shouldInvokeControllerWhenFeatureIsExecuted() throws BusinessException, SystemException{
		
		ArgumentCaptor<TransactionListVO> captor=ArgumentCaptor.forClass(TransactionListVO.class);
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		transactionVOs.add(uldTransactionDetailVO);
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("R");
		demurrageFeature.execute(transactionListVO);
		verify(uldController,times(1)).saveReturnTransaction(captor.capture());
		TransactionListVO trnListVO = captor.getValue();
		assertTrue(Objects.nonNull(trnListVO));
	} 
}
