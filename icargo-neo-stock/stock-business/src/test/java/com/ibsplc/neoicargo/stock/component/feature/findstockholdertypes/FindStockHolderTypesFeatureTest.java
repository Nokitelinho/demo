package com.ibsplc.neoicargo.stock.component.feature.findstockholdertypes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockHolderPriorityVO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class FindStockHolderTypesFeatureTest {

  @InjectMocks private FindStockHolderTypesFeature findStockHolderTypesFeature;

  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnStockHolderTypes() {
    // When
    doReturn(List.of(new StockHolderPriorityVO())).when(stockDao).findStockHolderTypes("DNSG");

    // Then
    assertNotNull(findStockHolderTypesFeature.perform("DNSG"));
  }
}
