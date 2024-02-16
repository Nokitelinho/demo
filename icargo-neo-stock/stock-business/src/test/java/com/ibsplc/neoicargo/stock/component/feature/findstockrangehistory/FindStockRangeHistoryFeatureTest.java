package com.ibsplc.neoicargo.stock.component.feature.findstockrangehistory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper.StockRangeHistoryHelper;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class FindStockRangeHistoryFeatureTest {

  @InjectMocks private FindStockRangeHistoryFeature findStockRangeHistoryFeature;

  @Mock private StockRangeHistoryHelper stockRangeHistoryHelper;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnStockHolderTypes() {
    // When
    doReturn(List.of(new StockRangeHistoryVO()))
        .when(stockRangeHistoryHelper)
        .findStockRangeHistory(any(StockRangeFilterVO.class));

    // Then
    assertNotNull(findStockRangeHistoryFeature.perform(new StockRangeFilterVO()));
  }
}
