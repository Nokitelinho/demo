package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_008;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DOCUMENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.persistor.StockPersistor;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator.StockPeriodValidator;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator.StockRangeValidator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderParametersVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockHolderHandlerTest {

  @InjectMocks private StockHolderHandler stockHolderHandler;

  @Mock private StockDao stockDao;
  @Mock private StockPersistor stockPersistor;
  @Mock private StockRangeValidator stockRangeValidator;
  @Mock private StockPeriodValidator stockPeriodValidator;
  @Mock private StockHandler stockHandler;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldAllocateStock(String lastUpdateUser) throws BusinessException {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .companyCode("HQ")
            .stockHolderCode("AWB")
            .isNewStockFlag(true)
            .isBlacklist(false)
            .documentType(DOCUMENT_TYPE)
            .lastUpdateUser(lastUpdateUser)
            .build();

    var parametersVO =
        StockHolderParametersVO.builder()
            .enableStockHistory(true)
            .stockIntroductionPeriod(10)
            .build();

    var stockVO = StockVO.builder().companyCode("HQ").build();

    // When
    doReturn(new StockHolderVO()).when(stockDao).findStockHolderDetails(anyString(), anyString());
    doNothing()
        .when(stockPeriodValidator)
        .validate(stockAllocationVO, parametersVO.getStockIntroductionPeriod());
    doReturn(stockVO).when(stockDao).findStockWithRanges("HQ", "AWB", 0, DOCUMENT_TYPE, null);
    doNothing().when(stockRangeValidator).validate(stockVO, stockAllocationVO, false);
    doNothing().when(stockHandler).allocate(stockVO, stockAllocationVO);
    doNothing().when(stockPersistor).updateStock(stockVO, stockAllocationVO, false);

    // Then
    Assertions.assertDoesNotThrow(
        () -> stockHolderHandler.allocate(stockAllocationVO, parametersVO));

    verify(stockDao).findStockHolderDetails(anyString(), anyString());
    verify(stockPeriodValidator).validate(any(StockAllocationVO.class), anyInt());
    verify(stockDao).findStockWithRanges("HQ", "AWB", 0, DOCUMENT_TYPE, null);
    verify(stockRangeValidator)
        .validate(any(StockVO.class), any(StockAllocationVO.class), anyBoolean());
    verify(stockHandler).allocate(any(StockVO.class), any(StockAllocationVO.class));
    verify(stockPersistor)
        .updateStock(any(StockVO.class), any(StockAllocationVO.class), anyBoolean());
  }

  @Test
  void shouldThrowStockHolderAlreadyDeletedException() {
    // When
    doReturn(null).when(stockDao).findStockHolderDetails(anyString(), anyString());

    // Then
    var thrown =
        assertThrows(
            StockBusinessException.class,
            () ->
                stockHolderHandler.allocate(
                    new StockAllocationVO(), new StockHolderParametersVO()));
    assertEquals(NEO_STOCK_008.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldThrowStockNotFound() throws BusinessException {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder().companyCode("HQ").stockHolderCode("AWB").build();

    // When
    doReturn(new StockHolderVO()).when(stockDao).findStockHolderDetails(anyString(), anyString());
    doNothing().when(stockPeriodValidator).validate(any(StockAllocationVO.class), anyInt());
    doReturn(null).when(stockDao).findStockWithRanges(null, null, 0, null, null);

    // Then
    var thrown =
        assertThrows(
            StockBusinessException.class,
            () -> stockHolderHandler.allocate(stockAllocationVO, new StockHolderParametersVO()));
    assertEquals(NEO_STOCK_003.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }

  @ParameterizedTest
  @MethodSource("provideTimeAndUser")
  void shouldDepleteStock(List<RangeVO> ranges, String lastUpdateUser) throws BusinessException {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder().ranges(ranges).lastUpdateUser(lastUpdateUser).build();

    var stockVO = StockVO.builder().companyCode("HQ").build();

    // When
    doReturn(stockVO).when(stockDao).findStockWithRanges(null, null, 0, null, null);
    doNothing().when(stockRangeValidator).validate(stockVO, stockAllocationVO, true);
    doNothing().when(stockHandler).deplete(stockVO, stockAllocationVO, true);
    doNothing().when(stockPersistor).updateStock(stockVO, stockAllocationVO, true);

    // Then
    Assertions.assertDoesNotThrow(() -> stockHolderHandler.deplete(stockAllocationVO, true));

    verify(stockDao).findStockWithRanges(null, null, 0, null, null);
    verify(stockRangeValidator)
        .validate(any(StockVO.class), any(StockAllocationVO.class), anyBoolean());
    verify(stockHandler).deplete(any(StockVO.class), any(StockAllocationVO.class), anyBoolean());
    verify(stockPersistor)
        .updateStock(any(StockVO.class), any(StockAllocationVO.class), anyBoolean());
  }

  private static Stream<Arguments> provideTimeAndUser() {
    return Stream.of(
        Arguments.of(List.of(RangeVO.builder().lastUpdateTime(ZonedDateTime.now()).build()), "ICO"),
        Arguments.of(List.of(new RangeVO()), null));
  }
}
