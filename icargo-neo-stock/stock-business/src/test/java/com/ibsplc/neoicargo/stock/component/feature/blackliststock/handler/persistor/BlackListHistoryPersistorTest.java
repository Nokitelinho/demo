package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockBlacklistStockVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockRangeVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockTransitStockVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.invoker.CreateHistoryInvoker;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.helper.StockRangeHistoryHelper;
import com.ibsplc.neoicargo.stock.dao.CQRSDao;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class BlackListHistoryPersistorTest {
  private static final String COMPANY_CODE = "AV";
  private static final int AIRLINE_IDENTIFIER = 1234;
  private static final String STOCK_HOLDER_CODE = "HQ";
  private static final String DOC_TYPE = "AWB";
  private static final String DOC_SUB_TYPE = "S";

  @InjectMocks BlackListHistoryPersistor blackListHistoryPersistor;
  @Mock private CQRSDao cqrsDao;
  @Mock private CreateHistoryInvoker createHistoryInvoker;
  @Mock private BlackListStockPersistor blackListStockPersistor;
  @Mock private StockRangeHistoryHelper stockRangeHistoryHelper;
  @Mock private LocalDate localDateUtil = new LocalDate();

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(cqrsDao, blackListStockPersistor, localDateUtil, createHistoryInvoker);
  }

  @ParameterizedTest
  @MethodSource("provideDataBlackList")
  void shouldCreateBlacklistHistoryForUsedRanges(StockRangeHistoryVO stockRangeHistoryVO) {
    // Given
    var vo = getMockBlacklistStockVO();

    // When
    doReturn(List.of(stockRangeHistoryVO))
        .when(stockRangeHistoryHelper)
        .findStockRangeHistory(any(StockRangeFilterVO.class));

    // Then
    assertDoesNotThrow(() -> blackListHistoryPersistor.createBlacklistHistoryForUsedRanges(vo));
    verify(stockRangeHistoryHelper, times(1)).findStockRangeHistory(any(StockRangeFilterVO.class));
    verify(createHistoryInvoker, times(1)).invoke(any(StockAllocationVO.class));
  }

  private static Stream<Arguments> provideDataBlackList() {
    return Stream.of(
        Arguments.of(StockRangeHistoryVO.builder().startRange("1").endRange("2").build()),
        Arguments.of(
            StockRangeHistoryVO.builder().startRange("9999999").endRange("9999999").build()));
  }

  @Test
  void shouldUpdateBlackHistoryWhenEmptyList() throws BusinessException {
    // Given
    var blacklistStockVO = getMockBlacklistStockVO();
    var transitStockVO =
        getMockTransitStockVO(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);

    // Then
    assertDoesNotThrow(
        () ->
            blackListHistoryPersistor.updateBlackHistory(
                List.of(), blacklistStockVO, transitStockVO));
    verify(blackListStockPersistor, times(0)).persist(any(BlacklistStockVO.class));
    verify(createHistoryInvoker, times(0)).invoke(any(StockAllocationVO.class));
  }

  @Test
  void shouldUpdateBlackHistory() throws BusinessException {
    // Given
    var blacklistStockVO = getMockBlacklistStockVO();
    var transitStockVO =
        getMockTransitStockVO(
            COMPANY_CODE, STOCK_HOLDER_CODE, AIRLINE_IDENTIFIER, DOC_TYPE, DOC_SUB_TYPE);
    var rangeVOS =
        List.of(getMockRangeVO(COMPANY_CODE, DOC_TYPE, DOC_SUB_TYPE, 10000000, 20000000));

    // When
    doReturn(null).when(localDateUtil).getLocalDate(NO_STATION, false);

    // Then
    assertDoesNotThrow(
        () ->
            blackListHistoryPersistor.updateBlackHistory(
                rangeVOS, blacklistStockVO, transitStockVO));
    verify(blackListStockPersistor, times(1)).persist(any(BlacklistStockVO.class));
    verify(createHistoryInvoker, times(1)).invoke(any(StockAllocationVO.class));
    verify(localDateUtil, times(1)).getLocalDate(NO_STATION, false);
  }
}
