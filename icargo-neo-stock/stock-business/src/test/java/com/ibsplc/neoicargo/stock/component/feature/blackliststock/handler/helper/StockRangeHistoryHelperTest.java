package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class StockRangeHistoryHelperTest {
  @InjectMocks private StockRangeHistoryHelper stockRangeHistoryHelper;
  @Mock private CQRSDao cqrsDao;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(cqrsDao);
  }

  @Test
  void shouldFindStockRangeHistoryWhenStatusUsed() {
    // Given
    var vo = new StockRangeFilterVO();
    vo.setHistory(false);
    vo.setStatus("U");

    // When
    doReturn(List.of())
        .when(cqrsDao)
        .findStockUtilisationDetailsStatusUsed(any(StockRangeFilterVO.class));

    // Then
    assertDoesNotThrow(() -> stockRangeHistoryHelper.findStockRangeHistory(vo));
    verify(cqrsDao, times(1)).findStockUtilisationDetailsStatusUsed(any(StockRangeFilterVO.class));
  }

  @Test
  void shouldFindStockRangeHistoryWhenStatusUnused() {
    // Given
    var vo = new StockRangeFilterVO();
    vo.setHistory(false);
    vo.setStatus("F");

    // When
    doReturn(List.of())
        .when(cqrsDao)
        .findStockUtilisationDetailsStatusUnused(any(StockRangeFilterVO.class));

    // Then
    assertDoesNotThrow(() -> stockRangeHistoryHelper.findStockRangeHistory(vo));
    verify(cqrsDao, times(1))
        .findStockUtilisationDetailsStatusUnused(any(StockRangeFilterVO.class));
  }

  @Test
  void shouldFindStockRangeHistoryWhenStatusEmpty() {
    // Given
    var vo = new StockRangeFilterVO();
    vo.setHistory(false);
    vo.setStatus("");

    // When
    doReturn(List.of())
        .when(cqrsDao)
        .findStockUtilisationDetailsStatusEmpty(any(StockRangeFilterVO.class));

    // Then
    assertDoesNotThrow(() -> stockRangeHistoryHelper.findStockRangeHistory(vo));
    verify(cqrsDao, times(1)).findStockUtilisationDetailsStatusEmpty(any(StockRangeFilterVO.class));
  }

  @Test
  void shouldFindStockRangeHistoryWhenStatusOther() {
    // Given
    var vo = new StockRangeFilterVO();
    vo.setHistory(false);
    vo.setStatus("OTHER");

    // Then
    assertDoesNotThrow(() -> stockRangeHistoryHelper.findStockRangeHistory(vo));
  }

  @Test
  void shouldFindStockRangeHistoryWhenHistoryTrue() {
    // Given
    var vo = new StockRangeFilterVO();
    vo.setHistory(true);

    // When
    doReturn(List.of()).when(cqrsDao).findStockHistory(any(StockRangeFilterVO.class));

    // Then
    assertDoesNotThrow(() -> stockRangeHistoryHelper.findStockRangeHistory(vo));
    verify(cqrsDao, times(1)).findStockHistory(any(StockRangeFilterVO.class));
  }

  @Test
  void shouldFindStockRangeHistoryWhenAwb() {
    // Given
    var vo = new StockRangeFilterVO();
    vo.setHistory(false);
    vo.setAwb("TEST");

    // When
    doReturn(List.of()).when(cqrsDao).findAwbStockDetails(any(StockRangeFilterVO.class));

    // Then
    assertDoesNotThrow(() -> stockRangeHistoryHelper.findStockRangeHistory(vo));
    verify(cqrsDao, times(1)).findAwbStockDetails(any(StockRangeFilterVO.class));
  }
}
