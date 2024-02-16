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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedDefaultsProxy;
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
public class StockTest extends AbstractFeatureTest {

	 private SharedDefaultsProxy sharedDefaultsProxy;
	 private SharedAreaProxy sharedAreaProxy;
	 private StockControlDefaultsDAO stockDAO;
	 private Stock stock;
	 
	
	@Override
	public void setup() throws Exception {
		
		stock=spy(Stock.class);
		EntityManagerMock.mockEntityManager();
		stockDAO= mock(StockControlDefaultsSqlDAO.class);
		sharedDefaultsProxy=mockProxy(SharedDefaultsProxy.class);
		sharedAreaProxy=mockProxy(SharedAreaProxy.class);
		
	}
	
	@Test
	public void performEnhancedFlow_restrictionexists() throws ProxyException, SystemException, PersistenceException{
		long masterDocumentNumber=12312311;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
	    systemParameters.put("stockcontrol.defaults.enhanceawbreuserestrictioncheck","Y");	        
	   
	    Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVO.setNoOfRestrictionYear(1);
		countryValidationVOs.add(countryValidationVO);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		stockReuseHistoryVO.setCaptureDate(new LocalDate("SIN", Location.ARP,false).setDate("26-May-2022"));
		
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
		stock.checkForAWBReuseRestriction(documentFilterVO, masterDocumentNumber);
	    verify(stock,atLeastOnce()).checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
	}
	
	@Test
	public void noSystemParameterExists() throws ProxyException, SystemException, PersistenceException{
		long masterDocumentNumber=12312311;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
	    doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
	    stock.checkForAWBReuseRestriction(documentFilterVO, masterDocumentNumber);
	    verify(stock,atLeastOnce()).checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
	}
	@Test
    public void shouldThrowProxyExceptionOnProxy() throws SystemException, FinderException, ProxyException, PersistenceException {
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
		systemParameters.put("stockcontrol.defaults.enhanceawbreuserestrictioncheck","Y");
		doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		long masterDocumentNumber=12312311;
		doThrow(ProxyException.class).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
		stock.checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
    }
	@Test
	public void performEnhancedFlow_restrictionNotexists() throws ProxyException, SystemException, PersistenceException{
		long masterDocumentNumber=12312311;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
	    systemParameters.put("stockcontrol.defaults.enhanceawbreuserestrictioncheck","Y");	        
	    Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVO.setNoOfRestrictionYear(1);
		countryValidationVOs.add(countryValidationVO);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		stockReuseHistoryVO.setCaptureDate(new LocalDate("SIN", Location.ARP,false).setDate("26-May-2020"));
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
		stock.checkForAWBReuseRestriction(documentFilterVO, masterDocumentNumber);
	    verify(stock,atLeastOnce()).checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
	}
	@Test
	public void performEnhancedFlow_restrictionNotexistsCaptureDate() throws ProxyException, SystemException, PersistenceException{
		long masterDocumentNumber=12312311;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
	    systemParameters.put("stockcontrol.defaults.enhanceawbreuserestrictioncheck","Y");	        
	    Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVO.setNoOfRestrictionYear(1);
		countryValidationVOs.add(countryValidationVO);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
		stock.checkForAWBReuseRestriction(documentFilterVO, masterDocumentNumber);
	    verify(stock,atLeastOnce()).checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
	}
	@Test
	public void performEnhancedFlow_restrictionNotConfigured() throws ProxyException, SystemException, PersistenceException{
		long masterDocumentNumber=12312311;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
	    systemParameters.put("stockcontrol.defaults.enhanceawbreuserestrictioncheck","Y");	        
	    Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVOs.add(countryValidationVO);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
		stock.checkForAWBReuseRestriction(documentFilterVO, masterDocumentNumber);
	    verify(stock,atLeastOnce()).checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
	}
	@Test
	public void performEnhancedFlow_SystemParaNotConfigured() throws ProxyException, SystemException, PersistenceException{
		long masterDocumentNumber=12312311;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
	    systemParameters.put("admin.defaults.cutoverparameters","xxx");	        
	    Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVOs.add(countryValidationVO);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
		stock.checkForAWBReuseRestriction(documentFilterVO, masterDocumentNumber);
	    verify(stock,atLeastOnce()).checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
	}
	@Test
	public void performEnhancedFlow_SystemParaN() throws ProxyException, SystemException, PersistenceException{
		long masterDocumentNumber=12312311;
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1122);
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("JFK");
		Map<String,String> systemParameters=new HashMap<>();
		systemParameters.put("stockcontrol.defaults.enhanceawbreuserestrictioncheck","N");	        
	    Collection<CountryValidationVO> countryValidationVOs= new ArrayList<>();
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setCountryCode("US");
		countryValidationVOs.add(countryValidationVO);
		StockReuseHistoryVO stockReuseHistoryVO = new StockReuseHistoryVO();
		doReturn(stockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("stockcontrol.defaults");
		doReturn(systemParameters).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
		doReturn(countryValidationVOs).when(sharedAreaProxy).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
		doReturn(stockReuseHistoryVO).when(stockDAO).findAWBReuseHistoryDetails(any(DocumentFilterVO.class), any(String.class));
		stock.checkForAWBReuseRestriction(documentFilterVO, masterDocumentNumber);
	    verify(stock,atLeastOnce()).checkForAWBReuseRestriction(documentFilterVO,masterDocumentNumber);
	}
	
	@Test
	public void getOriginDestIndicator(){
		String originDestinationIndicator ="";
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("LAX");
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setAirportCode("SIN");
		stock.getOriginDestinationIndicator(documentFilterVO, originDestinationIndicator, countryValidationVO);
	}
	
	@Test
	public void getOriginDestIndicatorForOrigin(){
		String originDestinationIndicator ="";
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setAwbOrigin("DFW");
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setAirportCode("DFW");
		stock.getOriginDestinationIndicator(documentFilterVO, originDestinationIndicator, countryValidationVO);
	}
	
	@Test
	public void getOriginDestIndicatorForDest(){
		String originDestinationIndicator ="";
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setAwbOrigin("DFW");
		documentFilterVO.setAwbDestination("LAX");
		CountryValidationVO countryValidationVO = new CountryValidationVO();
		countryValidationVO.setAirportCode("LAX");
		stock.getOriginDestinationIndicator(documentFilterVO, originDestinationIndicator, countryValidationVO);
	}
	
}
