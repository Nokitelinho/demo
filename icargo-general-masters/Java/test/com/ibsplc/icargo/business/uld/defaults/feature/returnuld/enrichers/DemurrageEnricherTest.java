package com.ibsplc.icargo.business.uld.defaults.feature.returnuld.enrichers;

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

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.returnuld.enrichers.DemurrageEnricherTest.java
 *	This class is used for Testing DemurrageEnricher
 */
public class DemurrageEnricherTest extends AbstractFeatureTest {

	TransactionListVO transactionListVO;
	DemurrageEnricher demurrageEnricher;
    Collection<ULDTransactionDetailsVO> transactionVOs;
    TransactionFilterVO uldTransactionFilterVO;
    ULDTransactionDetailsVO uldTransactionDetailVO;
    ULDTransactionDetailsVO uldTransactionDetailVO1;
    private static final String ULDNUM="AKE0099EK";
    private static final String TRANSACTION="T";
    private static final String CMPCOD = "AV";
    private ULDDefaultsDAO uldDefaultsMockDAO;
    ULDTransactionDetailsVO transactionVO;
    

    @Override 
	public void setup() throws Exception {
		
		transactionListVO = new TransactionListVO();
		transactionVOs = new ArrayList<>();
		uldTransactionDetailVO = new ULDTransactionDetailsVO();
		uldTransactionDetailVO1 = new ULDTransactionDetailsVO();
		demurrageEnricher = spy(new DemurrageEnricher());
		uldTransactionFilterVO = new TransactionFilterVO();
		transactionVO = new ULDTransactionDetailsVO();
	}
	
	@Test
	public void shouldInvokeEnricher() throws BusinessException, SystemException, PersistenceException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		transactionVOs.add(uldTransactionDetailVO);
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("R");
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        doReturn(uldDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(uldTransactionDetailVO).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		demurrageEnricher.enrich(transactionListVO);
		verify(uldDefaultsMockDAO, times(1)).listULDTransactionDetailsForDemurrage(any());
	} 	
	@Test
	public void shouldInvokeEnricherWhenTransactionListVOisNull() throws BusinessException, SystemException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		demurrageEnricher.enrich(transactionListVO);
		assertTrue(transactionVOs.isEmpty());
	} 
	@Test
	public void shouldInvokeEnricherWhenUldTransactionDetailVOHaveMultipleVO() throws BusinessException, SystemException, PersistenceException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		uldTransactionDetailVO1.setCompanyCode(CMPCOD);
		uldTransactionDetailVO1.setUldNumber(ULDNUM);
		uldTransactionDetailVO1.setTransactionStatus(TRANSACTION);
		transactionVOs.add(uldTransactionDetailVO1);
		transactionVOs.add(uldTransactionDetailVO);
		transactionVO.setDemurrageAmount(10);
		transactionVO.setTaxes(20);
		transactionVO.setOtherCharges(10);
		transactionVO.setWaived(10);
		transactionVO.setCurrency("AED");
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("L");
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        doReturn(uldDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(transactionVO).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		demurrageEnricher.enrich(transactionListVO);
		verify(uldDefaultsMockDAO, times(2)).listULDTransactionDetailsForDemurrage(any());
		
	} 
	@Test
	public void shouldInvokeEnricherWhenTransactionVOIsNullAndNotSettingCurrency() throws BusinessException, SystemException, PersistenceException{
		
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
		demurrageEnricher.enrich(transactionListVO);
		verify(uldDefaultsMockDAO, times(0)).listULDTransactionDetailsForDemurrage(any());
		assertTrue(Objects.isNull(transactionVO.getCurrency()));
	} 
	
	@Test
	public void shouldInvokeFindDemurrageWhenTotalIsZero() throws BusinessException, SystemException, PersistenceException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		uldTransactionDetailVO.setTotal(0);
		transactionVOs.add(uldTransactionDetailVO1);
		transactionVOs.add(uldTransactionDetailVO);
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("L");
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        doReturn(uldDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(transactionVO).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		demurrageEnricher.enrich(transactionListVO);
		verify(uldDefaultsMockDAO, times(2)).listULDTransactionDetailsForDemurrage(any());
	} 
	@Test
	public void shouldNotInvokeDemurrageEnricherWhenTotalIsNotZero() throws BusinessException, SystemException, PersistenceException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		uldTransactionDetailVO.setTotal(1);
		transactionVOs.add(uldTransactionDetailVO);
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("L");
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        doReturn(uldDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(transactionVO).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		demurrageEnricher.enrich(transactionListVO);
		verify(uldDefaultsMockDAO, times(0)).listULDTransactionDetailsForDemurrage(any());
		assertTrue(Objects.isNull(transactionVO.getCurrency()));
	} 
	
	@Test
	public void shouldThrowSystemException() throws BusinessException, SystemException, PersistenceException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		uldTransactionDetailVO.setTotal(1);
		transactionVOs.add(uldTransactionDetailVO);
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("L");
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        doReturn(null).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		doThrow(new SystemException("")).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		demurrageEnricher.enrich(transactionListVO);
	}
	
	@Test
	public void shouldInvokeFindDemurrageWhenTotalIsZeroAndSettingCurrency() throws BusinessException, SystemException, PersistenceException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		uldTransactionDetailVO.setTotal(0);
		transactionVOs.add(uldTransactionDetailVO1);
		transactionVOs.add(uldTransactionDetailVO);
		transactionVO.setDemurrageAmount(10);
		transactionVO.setTaxes(20);
		transactionVO.setOtherCharges(10);
		transactionVO.setWaived(10);
		transactionVO.setCurrency("AED");
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("L");
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        doReturn(uldDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(transactionVO).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		demurrageEnricher.enrich(transactionListVO);
		verify(uldDefaultsMockDAO, times(2)).listULDTransactionDetailsForDemurrage(any());
		assertTrue(Objects.nonNull(uldTransactionDetailVO.getCurrency()));
		
	} 
	
	@Test
	public void shouldInvokeFindDemurrageWhenTotalIsZeroAndSettingTotal() throws BusinessException, SystemException, PersistenceException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber(ULDNUM);
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		uldTransactionDetailVO.setTotal(0);
		transactionVOs.add(uldTransactionDetailVO);
		transactionVO.setDemurrageAmount(10);
		transactionVO.setTaxes(20);
		transactionVO.setOtherCharges(10);
		transactionVO.setWaived(10);
		transactionVO.setCurrency("AED");
		transactionListVO.setUldTransactionsDetails(transactionVOs);
		transactionListVO.setTransactionType("L");
		EntityManagerMock.mockEntityManager();
		uldDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        doReturn(uldDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(transactionVO).when(uldDefaultsMockDAO).listULDTransactionDetailsForDemurrage(any());
		demurrageEnricher.enrich(transactionListVO);
		verify(uldDefaultsMockDAO, times(1)).listULDTransactionDetailsForDemurrage(any());
		assertTrue(Objects.nonNull(uldTransactionDetailVO.getTotal()));
		assertTrue(Objects.equals(30.0,uldTransactionDetailVO.getTotal()));
	} 
}
