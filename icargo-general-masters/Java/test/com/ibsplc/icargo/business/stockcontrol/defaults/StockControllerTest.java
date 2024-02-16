/*
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;


import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.ibsplc.icargo.business.shared.area.country.vo.CountryValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockReuseHistoryVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
/**
 * 
 * @author A-7900
 *
 */
public class StockControllerTest extends AbstractFeatureTest {

	private StockController stockController;
    private StockController stockControllerBean;
	private StockControlDefaultsDAO stockDAO;
	private SharedAreaProxy sharedAreaProxy;
	
	@Override
	public void setup() throws Exception {

		stockController = spy(new StockController());
		stockControllerBean = mockBean("stockcontroldefaultsController", StockController.class);
		EntityManagerMock.mockEntityManager();
		stockDAO= mock(StockControlDefaultsSqlDAO.class);
		sharedAreaProxy =mockProxy(SharedAreaProxy.class);
	}
	
	@Test
	public void shouldDoAutoProcessingAllocateStock() throws BusinessException, SystemException, FinderException, PersistenceException {
		StockRequestVO stockRequestVO = new StockRequestVO();
		StockHolder stockHolder1= new StockHolder();
		Set<Stock> stocks  =new HashSet<>();
		RangeFilterVO rangeFilterVO = new RangeFilterVO();
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		Stock stock = new Stock();
		StockRequest stockRequest = new StockRequest();
		Collection<RangeVO> rangeVOs= new ArrayList();
		RangeVO rangeVO = new RangeVO();
		rangeVO.setAirlineIdentifier(1618);
		rangeVO.setDocumentSubType("S");
		rangeVO.setDocumentSubType("AWB");
		rangeVOs.add(rangeVO);
		stockRequestVO.setCompanyCode(getCompanyCode());
		stockRequestVO.setRequestRefNumber("16112");
		stockRequestVO.setAirlineIdentifier("1618");
		stockRequestVO.setDocumentType("AWB");
		stockRequestVO.setDocumentSubType("S");
		rangeFilterVO.setNumberOfDocuments("9");
		rangeFilterVO.setStockHolderCode("13512");
		rangeFilterVO.setStartRange("0");
		StockPK stockpk= new StockPK();
		stockpk.setDocumentType("AWB");
		stockpk.setDocumentSubType("S");
		stockpk.setAirlineIdentifier(1618);
		stock.setStockPK(stockpk);
		String reqRefNo = "1234";
		stock.setReorderLevel(5);
		stock.setReorderQuantity(2);
		stock.setAutoprocessQuantity(1);
		stocks.add(stock);
	    stockHolder1.setStock(stocks);
	    rangeFilterVO.setNumberOfDocuments("1");
		rangeFilterVO.setStartRange("0");
		StockRequestPK stockRequestPK= new StockRequestPK();
		stockRequestPK.setCompanyCode(getCompanyCode());
		stockRequestPK.setRequestRefNumber("13");
		stockRequestPK.setAirlineIdentifier(1618);
		stockRequest.setStockRequestPk(stockRequestPK);
		doReturn(stockHolder1).when(PersistenceController.getEntityManager()).find(eq(StockHolder.class),any(StockHolderPK.class));
		doReturn(stockRequest).when(PersistenceController.getEntityManager()).find(eq(StockRequest.class),any(StockRequestPK.class));
		doReturn(rangeVOs).when(stockController).findRanges(any(RangeFilterVO.class));
		doReturn(stockAllocationVO).when(stockControllerBean).allocateStock(any(StockAllocationVO.class));
	    stockController.autoProcessingAllocateStock(stockRequestVO,reqRefNo);

	}
	
	@Test
	public void shouldCreateHistoryTestOne() throws SystemException{
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		String status="R";
		stockAllocationVO.setAutoAllocated("Y");
		stockAllocationVO.setStockHolderCode("142671");
		stockController.createHistory(stockAllocationVO,status);
	}
	
	@Test
	public void shouldCreateHistoryTestTwo() throws SystemException{
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		String status="R";
		stockAllocationVO.setAutoAllocated("N");
		stockAllocationVO.setStockHolderCode("142671");
		stockController.createHistory(stockAllocationVO,status);
	}
	
	@Test
	public void shouldDoSaveStockReuseHistory() throws SystemException, FinderException {
		Collection<StockReuseHistoryVO> stockReuseHistoryVOs = new ArrayList<>();
		StockReuseHistoryVO stockReuseHistoryvo = new StockReuseHistoryVO();
		stockReuseHistoryvo.setCompanyCode("AA");
		stockReuseHistoryvo.setMasterDocumentNumber("23050");
		stockReuseHistoryvo.setDuplicateNumber(1);
		stockReuseHistoryvo.setSequenceNumber(1);
		stockReuseHistoryvo.setCaptureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		stockReuseHistoryvo.setCountryCode("IN");
		stockReuseHistoryVOs.add(stockReuseHistoryvo);
		
		stockController.saveStockReuseHistory(stockReuseHistoryVOs);
		
	}
	@Test(expected = SystemException.class)
	public void saveStockReuseHistoryThrowException() throws SystemException, CreateException{
		Collection<StockReuseHistoryVO> stockReuseHistoryVOs = new ArrayList<>();
		StockReuseHistoryVO stockReuseHistoryvo = new StockReuseHistoryVO();
		stockReuseHistoryvo.setCompanyCode("AA");
		stockReuseHistoryvo.setMasterDocumentNumber("23050");
		stockReuseHistoryvo.setDuplicateNumber(1);
		stockReuseHistoryvo.setSequenceNumber(1);
		stockReuseHistoryvo.setCaptureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		stockReuseHistoryvo.setCountryCode("IN");
		stockReuseHistoryVOs.add(stockReuseHistoryvo);
		doThrow(CreateException.class).when(PersistenceController.getEntityManager()).persist(any(StockReuseHistoryVO.class));
		stockController.saveStockReuseHistory(stockReuseHistoryVOs);
		
	}
	
	@Test
	public void removeStockReuseHistoryTest() throws PersistenceException, SystemException, FinderException, RemoveException, OptimisticConcurrencyException {
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		Collection<Integer> sernums = new ArrayList<>();
		sernums.add(1);
		StockReuseHistory stockReuseHistoryMock = mock(StockReuseHistory.class);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		StockReuseHistory stockReuseHistory = new StockReuseHistory();
		stockReuseHistory.setCaptureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));

		stockReuseHistoryVO.setCompanyCode("AA");
		stockReuseHistoryVO.setMasterDocumentNumber("23050");
		stockReuseHistoryVO.setDuplicateNumber(1);
		stockReuseHistoryVO.setSequenceNumber(1);
		stockReuseHistoryVO.setCaptureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		stockReuseHistoryVO.setCountryCode("IN");
		stockReuseHistoryVO.setSerialNumber(1);
		
		
		doReturn(sernums).when(stockDAO).removeStockReuseHistory(stockReuseHistoryVO);
		doReturn(stockReuseHistoryMock).when(PersistenceController.getEntityManager()).find(eq(StockReuseHistory.class),any(StockReuseHistoryPK.class));
		doNothing().when(stockReuseHistoryMock).remove();
		stockController.removeStockReuseHistory(stockReuseHistoryVO);
	}
	@Test(expected=SystemException.class)
	public void removeStockReuseHistoryTestFinderException1() throws PersistenceException, SystemException, FinderException, RemoveException, OptimisticConcurrencyException {
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		Collection<Integer> sernums = new ArrayList<>();
		sernums.add(1);
		StockReuseHistory stockReuseHistoryMock = mock(StockReuseHistory.class);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		StockReuseHistory stockReuseHistory = new StockReuseHistory();
		stockReuseHistory.setCaptureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));

		stockReuseHistoryVO.setCompanyCode("AA");
		stockReuseHistoryVO.setMasterDocumentNumber("23050");
		stockReuseHistoryVO.setDuplicateNumber(1);
		stockReuseHistoryVO.setSequenceNumber(1);
		stockReuseHistoryVO.setCaptureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		stockReuseHistoryVO.setCountryCode("IN");
		stockReuseHistoryVO.setSerialNumber(1);
		
		
		doReturn(sernums).when(stockDAO).removeStockReuseHistory(stockReuseHistoryVO);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(StockReuseHistory.class),any(StockReuseHistoryPK.class));
		doNothing().when(stockReuseHistoryMock).remove();
		stockController.removeStockReuseHistory(stockReuseHistoryVO);
	}
	@Test(expected=SystemException.class)
	public void removeExceptionWhenRemove() throws PersistenceException, SystemException, FinderException, RemoveException, OptimisticConcurrencyException {
		StockReuseHistory stockReuseHistory = new StockReuseHistory();
		doReturn(stockReuseHistory).when(PersistenceController.getEntityManager()).find(eq(StockReuseHistoryVO.class),eq(StockReuseHistoryPK.class));
		doThrow(RemoveException.class).when(PersistenceController.getEntityManager()).remove(stockReuseHistory);
		stockReuseHistory.remove();
	}
	@Test
    public void verifyStockReuseHistoryGettersSetter() throws Exception{
		assertTrue(new PojoGetSetTester().testGettersAndSetters(StockReuseHistory.class));
    }
	@Test
    public void shouldDoUpdateStockReuseHistory() throws SystemException, FinderException {
        Collection<StockReuseHistoryVO> stockReuseHistoryVOs = new ArrayList<>();
        StockReuseHistoryVO stockReuseHistoryvo = new StockReuseHistoryVO();
        stockReuseHistoryvo.setCompanyCode("AA");
        stockReuseHistoryvo.setMasterDocumentNumber("23050");
        stockReuseHistoryvo.setDuplicateNumber(1);
        stockReuseHistoryvo.setSequenceNumber(1);
        stockReuseHistoryvo.setCaptureDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
        stockReuseHistoryvo.setCountryCode("IN");
        stockReuseHistoryVOs.add(stockReuseHistoryvo);

        stockController.updateStockReuseHistory(stockReuseHistoryVOs);

    }
	
	
	@Test
    public void shouldNotThrowErrorOnAwbReuse_NoRestriction() throws SystemException, FinderException, ProxyException, PersistenceException {
		
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		DocumentFilterVO documentFilterVo = new DocumentFilterVO();
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		stockReuseHistoryVO.setCaptureDate(new LocalDate("SIN", Location.ARP,false).setDate("26-May-2022"));
		Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVOs.add(countryValidationVO);
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVo);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
	    stockController.doAWBReUseRestrictionCheckEnhanced(documentFilterVo);

    }
	
	@Test
    public void shouldThrowProxyExceptionOnProxy() throws SystemException, FinderException, ProxyException, PersistenceException {
		DocumentFilterVO documentFilterVo = new DocumentFilterVO();
		doThrow(ProxyException.class).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVo);
		stockController.doAWBReUseRestrictionCheckEnhanced(documentFilterVo);

    }
	
	@Test(expected=SystemException.class)
    public void shouldThrowErrorOnAwbReuse_NoRestriction() throws SystemException, FinderException, ProxyException, PersistenceException {
		
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		DocumentFilterVO documentFilterVo = new DocumentFilterVO();
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		stockReuseHistoryVO.setCaptureDate(new LocalDate("SIN", Location.ARP,false).setDate("26-May-2022"));
		Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVO.setNoOfRestrictionYear(1);
		countryValidationVOs.add(countryValidationVO);
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVo);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
	    stockController.doAWBReUseRestrictionCheckEnhanced(documentFilterVo);

    }
	
	@Test()
    public void shouldMotThrowErrorOnAwbReuse_NoRestriction() throws SystemException, FinderException, ProxyException, PersistenceException {
		
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		DocumentFilterVO documentFilterVo = new DocumentFilterVO();
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		stockReuseHistoryVO.setCaptureDate(new LocalDate("SIN", Location.ARP,false).setDate("26-May-2020"));
		Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVO.setNoOfRestrictionYear(1);
		countryValidationVOs.add(countryValidationVO);
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVo);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
	    stockController.doAWBReUseRestrictionCheckEnhanced(documentFilterVo);

    }
}
