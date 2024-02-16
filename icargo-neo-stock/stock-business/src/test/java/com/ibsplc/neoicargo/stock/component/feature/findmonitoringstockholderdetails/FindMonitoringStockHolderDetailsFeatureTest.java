package com.ibsplc.neoicargo.stock.component.feature.findmonitoringstockholderdetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindMonitoringStockHolderDetailsFeatureTest {
  @InjectMocks
  private FindMonitoringStockHolderDetailsFeature findMonitoringStockHolderDetailsFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnMonitoringStockHolderDetail() {
    // Given
    var stockFilterVO = MockModelGenerator.getMockStockFilterVO();

    // When
    doReturn(new MonitorStockVO())
        .when(stockDao)
        .findMonitoringStockHolderDetails(any(StockFilterVO.class));

    // Then
    assertNotNull(findMonitoringStockHolderDetailsFeature.perform(stockFilterVO));
  }
}
