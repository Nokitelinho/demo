package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_010;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.helper.RangeHelper;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.persistor.StockPersistor;
import com.ibsplc.neoicargo.stock.dao.RangeDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.proxy.impl.DocumentTypeProxy;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.DuplicateRangeFilterVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockHandlerTest {

  @InjectMocks private StockHandler stockHandler;

  @Mock private StockPersistor stockPersistor;
  @Mock private RangeDao rangeDao;
  @Mock private RangeHelper rangeHelper;
  @Mock private DocumentTypeProxy documentTypeProxy;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void shouldAllocateStock(boolean isNewStockFlag) {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .isNewStockFlag(isNewStockFlag)
            .isBlacklist(false)
            .ranges(List.of(new RangeVO()))
            .build();

    // When
    doReturn(List.of()).when(rangeDao).findDuplicateRanges(any(DuplicateRangeFilterVO.class));
    doReturn(List.of(new RangeVO())).when(rangeHelper).findRangesForMerge(anyList());
    doReturn(List.of(new SharedRangeVO()))
        .when(documentTypeProxy)
        .mergeRanges(any(DocumentVO.class));
    doNothing().when(rangeDao).removeAll(anyList());
    doNothing().when(stockPersistor).addRange(any(StockVO.class), anyList(), anyBoolean());

    // Then
    Assertions.assertDoesNotThrow(() -> stockHandler.allocate(new StockVO(), stockAllocationVO));

    verify(rangeHelper, times(2)).findRangesForMerge(anyList());
    verify(documentTypeProxy).mergeRanges(any(DocumentVO.class));
    verify(rangeDao).removeAll(anyList());
    verify(stockPersistor).addRange(any(StockVO.class), anyList(), anyBoolean());
  }

  @Test
  void shouldThrowRangeAlreadyExistsException() {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .isNewStockFlag(true)
            .isBlacklist(false)
            .ranges(List.of(new RangeVO()))
            .build();

    // When
    doReturn(List.of(new RangeVO()))
        .when(rangeDao)
        .findDuplicateRanges(any(DuplicateRangeFilterVO.class));

    // Then
    var thrown =
        assertThrows(
            StockBusinessException.class,
            () -> stockHandler.allocate(new StockVO(), stockAllocationVO));

    assertEquals(NEO_STOCK_010.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(rangeDao).findDuplicateRanges(any(DuplicateRangeFilterVO.class));
    verifyNoMoreInteractions(rangeDao, documentTypeProxy, stockPersistor);
  }

  @ParameterizedTest
  @MethodSource("provideDepleteSources")
  void shouldDepleteStock(
      long asciiStartRange, long asciiEndRange, boolean isBlacklist, List<RangeVO> splitRange) {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .isNewStockFlag(true)
            .isBlacklist(false)
            .isManual(true)
            .ranges(List.of(RangeVO.builder().startRange("1001000").endRange("1001100").build()))
            .build();

    var stockVO =
        StockVO.builder()
            .ranges(
                new ArrayList<>(
                    List.of(
                        RangeVO.builder()
                            .asciiStartRange(asciiStartRange)
                            .asciiEndRange(asciiEndRange)
                            .isManual(true)
                            .build())))
            .build();

    // When
    doReturn(splitRange).when(rangeHelper).splitRanges(anySet(), anyList());
    doNothing().when(rangeDao).removeAll(anyList());
    doNothing().when(stockPersistor).addRange(any(StockVO.class), anyList(), anyBoolean());

    // Then
    Assertions.assertDoesNotThrow(
        () -> stockHandler.deplete(stockVO, stockAllocationVO, isBlacklist));
  }

  private static Stream<Arguments> provideDepleteSources() {
    return Stream.of(
        Arguments.of(1000000, 1002000, true, List.of()),
        Arguments.of(1000000, 1001010, false, List.of()),
        Arguments.of(1000000, 1001010, false, List.of(new RangeVO())),
        Arguments.of(1000000, 1001010, true, List.of()),
        Arguments.of(1001010, 1002000, true, List.of()),
        Arguments.of(1001010, 1001015, true, List.of()),
        Arguments.of(1001000, 1001100, true, List.of()));
  }
}
