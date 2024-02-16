package com.ibsplc.icargo.business.stockcontrol.defaults;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.pojo.PojoGetSetTester;

public class StockReuseHistoryPKTest extends AbstractFeatureTest {

	
	private StockReuseHistoryPK stockReuseHistoryPK;
	@Override
	public void setup() throws Exception {
		stockReuseHistoryPK = new StockReuseHistoryPK();
		stockReuseHistoryPK.setCompanyCode("AA");
		stockReuseHistoryPK.setMasterDocumentNumber("23050");
		stockReuseHistoryPK.setDuplicateNumber(1);
		stockReuseHistoryPK.setSequenceNumber(1);
		stockReuseHistoryPK.setSerialNumber(1);
	}
	@Test
    public void verifyStockReuseHistoryPKGettersSetter() throws Exception{
		assertTrue(new PojoGetSetTester().testGettersAndSetters(StockReuseHistoryPK.class));
    }
	@Test
	public void equalsMethodTest() {
		assertTrue(stockReuseHistoryPK.equals(stockReuseHistoryPK));
	}
	@Test
	public void toStringTest() {
		assertNotNull(stockReuseHistoryPK.toString());
	}

}
