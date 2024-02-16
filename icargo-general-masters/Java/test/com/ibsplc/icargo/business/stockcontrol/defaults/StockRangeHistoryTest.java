package com.ibsplc.icargo.business.stockcontrol.defaults;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import static org.mockito.Mockito.spy;


import org.hamcrest.core.IsNull;

public class StockRangeHistoryTest extends AbstractFeatureTest {
	private StockRangeHistory stockRangeHistory;
    
	

	@Override
	public void setup() throws Exception{
		EntityManagerMock.mockEntityManager();
		stockRangeHistory = spy(StockRangeHistory.class);
		
	}

	@Test
	public void stockRangeHistory() throws SystemException {
		StockRangeHistoryVO stockRangeHistoryVO = new StockRangeHistoryVO(); 
		stockRangeHistory = new StockRangeHistory(stockRangeHistoryVO);
		populateAttributes(stockRangeHistoryVO);
		assertThat(stockRangeHistoryVO,  IsNull.notNullValue());
	}
	
	public void populateAttributes(StockRangeHistoryVO stockRangeHistoryVO){
    	stockRangeHistoryVO.setRangeType("20");
    	stockRangeHistoryVO.setNumberOfDocuments(10);
    	stockRangeHistoryVO.setToStockHolderCode("1224561");
    	stockRangeHistoryVO.setAccountNumber("86942");
    	stockRangeHistoryVO.setCurrencyCode("AED");
    	stockRangeHistoryVO.setAutoAllocated("Y");
     	
	}
	
	@Test
	public void shouldGetAutoAllocated()
	{
		StockRangeHistory stockRangeHistoryOne = new StockRangeHistory();
		stockRangeHistoryOne.setAutoAllocated("Y");
		String response= stockRangeHistoryOne.getAutoAllocated();
		assertEquals(response,"Y");
		
	}
	

}
