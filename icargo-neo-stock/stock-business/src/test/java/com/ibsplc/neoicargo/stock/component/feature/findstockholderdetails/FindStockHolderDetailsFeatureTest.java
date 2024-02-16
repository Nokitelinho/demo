package com.ibsplc.neoicargo.stock.component.feature.findstockholderdetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class FindStockHolderDetailsFeatureTest {

  @InjectMocks private FindStockHolderDetailsFeature findStockHolderDetailsFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnStockHolderDetails() {
    // When
    doReturn(new StockHolderVO()).when(stockDao).findStockHolderDetails("AV", "DHLCDG");

    // Then
    assertNotNull(findStockHolderDetailsFeature.perform("AV", "DHLCDG"));
  }
}
