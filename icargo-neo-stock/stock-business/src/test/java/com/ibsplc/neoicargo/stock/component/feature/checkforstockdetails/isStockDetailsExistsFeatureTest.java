package com.ibsplc.neoicargo.stock.component.feature.checkforstockdetails;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class isStockDetailsExistsFeatureTest {

  @InjectMocks private IsStockDetailsExistsFeature checkForStockDetailsFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCheckForStockDetailsExists() {
    var filterVO = new DocumentFilterVO();
    doReturn(true).when(stockDao).isStockDetailsExists(filterVO);

    assertDoesNotThrow(() -> checkForStockDetailsFeature.perform(filterVO));

    verify(stockDao).isStockDetailsExists(filterVO);
  }
}
