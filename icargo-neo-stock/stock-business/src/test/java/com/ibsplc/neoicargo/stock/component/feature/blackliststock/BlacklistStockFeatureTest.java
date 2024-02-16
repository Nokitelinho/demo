package com.ibsplc.neoicargo.stock.component.feature.blackliststock;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getLoginProfile;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockBlacklistStockVO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator.StockRangeUtilisationForRangeValidator;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.BlackListStockHandler;
import com.ibsplc.neoicargo.stock.component.feature.monitorstock.validator.StockFilterValidator;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
public class BlacklistStockFeatureTest {

  @InjectMocks private BlacklistStockFeature blacklistStockFeature;

  @Mock private ContextUtil contextUtil;
  @Mock private BlackListStockHandler blackListStockHandler;
  @Mock private StockDao stockDao;
  @Mock private StockFilterValidator stockFilterValidator;
  @Mock private StockRangeUtilisationForRangeValidator rangeUtilisationValidator;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(
        stockDao, stockFilterValidator, rangeUtilisationValidator, blackListStockHandler);
  }

  @Test
  void shouldBlacklistWhenRangesAreAvailable() throws BusinessException {
    // Given
    var blacklistStockVO = getMockBlacklistStockVO();

    // When
    doNothing().when(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    doNothing().when(blackListStockHandler).blacklistStockHolderStock(blacklistStockVO);
    doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();

    // Then
    Assertions.assertDoesNotThrow(() -> blacklistStockFeature.perform(blacklistStockVO));
    verify(rangeUtilisationValidator, times(1)).validate(any(StockAllocationVO.class));
    verify(blackListStockHandler, times(1)).blacklistStockHolderStock(blacklistStockVO);
  }

  @ParameterizedTest
  @MethodSource("provideTestDataForBlackListStock")
  void shouldBlacklistWhenRangesAreNotAvailable(BlacklistStockVO blacklistStockVO)
      throws BusinessException {

    // When
    doNothing().when(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    doNothing().when(stockFilterValidator).validate(any(StockFilterVO.class));
    doNothing().when(blackListStockHandler).blacklistStockHolderStock(blacklistStockVO);
    doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
    doReturn(StockVO.builder().ranges(List.of(new RangeVO())).build())
        .when(stockDao)
        .findStockWithRanges(null, null, 0, null, null);

    // Then
    Assertions.assertDoesNotThrow(() -> blacklistStockFeature.perform(blacklistStockVO));
    verify(rangeUtilisationValidator, times(1)).validate(any(StockAllocationVO.class));
    verify(stockFilterValidator, times(1)).validate(any(StockFilterVO.class));
    verify(blackListStockHandler, times(1)).blacklistStockHolderStock(blacklistStockVO);
    verify(stockDao, times(1)).findStockWithRanges(null, null, 0, null, null);
  }

  @Test
  void shouldThrownExceptionWhileBlacklist() throws BusinessException {
    // Given
    var blacklistStockVO = getMockBlacklistStockVO();
    blacklistStockVO.setRangeFrom(null);
    blacklistStockVO.setRangeTo(null);

    // When
    doNothing().when(rangeUtilisationValidator).validate(any(StockAllocationVO.class));
    doNothing().when(stockFilterValidator).validate(any(StockFilterVO.class));
    doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
    doReturn(null)
        .when(stockDao)
        .findStockWithRanges(anyString(), anyString(), anyInt(), anyString(), anyString());

    // Then
    Assertions.assertThrows(
        StockBusinessException.class, () -> blacklistStockFeature.perform(blacklistStockVO));
    verify(rangeUtilisationValidator, times(1)).validate(any(StockAllocationVO.class));
    verify(stockFilterValidator, times(1)).validate(any(StockFilterVO.class));
    verify(stockDao, times(1))
        .findStockWithRanges(anyString(), anyString(), anyInt(), anyString(), anyString());
  }

  private static Stream<Arguments> provideTestDataForBlackListStock() {
    return Stream.of(
        Arguments.of(BlacklistStockVO.builder().build()),
        Arguments.of(BlacklistStockVO.builder().rangeFrom("000000").build()));
  }
}
