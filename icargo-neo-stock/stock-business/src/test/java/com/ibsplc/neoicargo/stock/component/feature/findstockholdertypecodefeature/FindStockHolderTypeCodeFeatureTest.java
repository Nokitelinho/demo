package com.ibsplc.neoicargo.stock.component.feature.findstockholdertypecodefeature;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class FindStockHolderTypeCodeFeatureTest {
  @InjectMocks private FindStockHolderTypeCodeFeature findStockHolderTypeCodeFeature;
  @Mock private StockDao stockDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldDoesNotThrowIfStockHolderFound() {
    // Given
    var stkhldname = List.of("STK1", "STK2");

    // When
    doReturn(stkhldname).when(stockDao).findStockHolderTypeCode(any(StockRequestVO.class));

    // Then
    assertDoesNotThrow(() -> findStockHolderTypeCodeFeature.perform(new StockRequestVO()));
  }

  @Test
  void shouldThrowsExceptionIfStockHolderNotFound() {
    // When
    doReturn(null).when(stockDao).findStockHolderTypeCode(any(StockRequestVO.class));

    // Then
    assertThrows(
        StockBusinessException.class,
        () -> findStockHolderTypeCodeFeature.perform(new StockRequestVO()));
  }
}
