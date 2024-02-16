package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.persistor;

import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_NORMAL;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_RETURN;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_TRANSFER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.repository.StockRepository;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockDetailsPersistorTest {

  @InjectMocks private StockPersistor persistor;

  @Mock private StockDao stockDao;
  @Mock private StockRepository stockRepository;
  @Mock private ContextUtil contextUtil;

  private static final String startRange = "1001000";
  private static final String endRange = "1002000";

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("provideRangeLists")
  void shouldAddRange(List<RangeVO> rangeVOList) {
    // When
    doReturn(new LoginProfile()).when(contextUtil).callerLoginProfile();
    doNothing().when(stockDao).addRanges(any(StockVO.class), anyList());

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.addRange(new StockVO(), rangeVOList, true));
    verify(contextUtil).callerLoginProfile();
    verify(stockDao).addRanges(any(StockVO.class), anyList());
  }

  private static Stream<List<RangeVO>> provideRangeLists() {
    return Stream.of(
        List.of(RangeVO.builder().startRange(startRange).endRange(endRange).build()),
        List.of(
            RangeVO.builder()
                .startRange(startRange)
                .endRange(endRange)
                .stockAcceptanceDate(ZonedDateTime.now())
                .build()));
  }

  @ParameterizedTest
  @MethodSource("provideStockAllocationVO")
  void shouldUpdateStatus(StockAllocationVO allocationVO, boolean isDepleteFlag) {
    // Given
    var stock =
        Stock.builder()
            .manualAvailableStock(200)
            .manualAllocatedStock(100)
            .physicalAvailableStock(200)
            .physicalAllocatedStock(100)
            .build();

    // When
    doReturn(Optional.of(stock)).when(stockRepository).findById(anyLong());
    doReturn(new Stock()).when(stockRepository).save(any(Stock.class));

    // Then
    Assertions.assertDoesNotThrow(
        () ->
            persistor.updateStock(
                StockVO.builder().stockSerialNumber(1L).build(), allocationVO, isDepleteFlag));

    verify(stockRepository).findById(anyLong());
    verify(stockRepository).save(any(Stock.class));
  }

  private static Stream<Arguments> provideStockAllocationVO() {
    return Stream.of(
        Arguments.of(
            StockAllocationVO.builder()
                .isManual(true)
                .transferMode(MODE_NORMAL)
                .ranges(
                    List.of(RangeVO.builder().startRange(startRange).endRange(endRange).build()))
                .build(),
            true),
        Arguments.of(
            StockAllocationVO.builder()
                .isManual(true)
                .transferMode(MODE_TRANSFER)
                .ranges(
                    List.of(RangeVO.builder().startRange(startRange).endRange(endRange).build()))
                .build(),
            false),
        Arguments.of(
            StockAllocationVO.builder()
                .isManual(false)
                .transferMode(MODE_NORMAL)
                .ranges(
                    List.of(RangeVO.builder().startRange(startRange).endRange(endRange).build()))
                .build(),
            true),
        Arguments.of(
            StockAllocationVO.builder()
                .isManual(false)
                .transferMode(MODE_RETURN)
                .ranges(
                    List.of(RangeVO.builder().startRange(startRange).endRange(endRange).build()))
                .build(),
            false));
  }
}
