package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler;

import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_ALLOCATE;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_BLACKLIST;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_BLACKLIST_REVOKE;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_RETURN;
import static com.ibsplc.neoicargo.stock.util.StockConstant.MODE_TRANSFER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.stock.dao.StockDetailsDao;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import java.time.ZonedDateTime;
import java.util.List;
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
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
class StockDetailsHandlerTest {

  @InjectMocks private StockDetailsHandler stockDetailsHandler;

  @Mock private StockDetailsDao stockDetailsDao;

  @Spy private final LocalDate localDateUtil = new LocalDate();

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("provideStockDetailsVo")
  void shouldCreateStockHolderStockDetails(
      String txncode, StockDetailsVO stockDetailsVO, int daoCallTimes) {
    // Given
    var stockAllocationVO =
        StockAllocationVO.builder()
            .companyCode("HQ")
            .stockHolderCode("AWB")
            .documentType("D1")
            .documentSubType("S1")
            .stockControlFor("AWB")
            .transferMode(txncode)
            .isNewStockFlag(txncode == null)
            .captureDateUTC(ZonedDateTime.now().minusDays(1))
            .ranges(List.of(RangeVO.builder().startRange("1001000").endRange("1002000").build()))
            .build();

    // When
    doReturn(stockDetailsVO)
        .when(stockDetailsDao)
        .find(anyString(), anyString(), anyString(), anyString(), anyInt());
    doNothing().when(stockDetailsDao).save(stockDetailsVO);

    // Then
    Assertions.assertDoesNotThrow(
        () ->
            stockDetailsHandler.createStockHolderStockDetails(
                stockAllocationVO, FLAG_YES, txncode));
    verify(stockDetailsDao, times(daoCallTimes))
        .find(anyString(), anyString(), anyString(), anyString(), anyInt());
    verify(stockDetailsDao, times(daoCallTimes)).save(any(StockDetailsVO.class));
  }

  private static Stream<Arguments> provideStockDetailsVo() {
    return Stream.of(
        Arguments.of(MODE_BLACKLIST, null, 1),
        Arguments.of(MODE_BLACKLIST, new StockDetailsVO(), 1),
        Arguments.of(MODE_BLACKLIST_REVOKE, null, 1),
        Arguments.of(MODE_BLACKLIST_REVOKE, new StockDetailsVO(), 1),
        Arguments.of(MODE_TRANSFER, null, 2),
        Arguments.of(MODE_TRANSFER, new StockDetailsVO(), 2),
        Arguments.of(MODE_RETURN, null, 2),
        Arguments.of(MODE_RETURN, new StockDetailsVO(), 2),
        Arguments.of(MODE_ALLOCATE, null, 2),
        Arguments.of(MODE_ALLOCATE, new StockDetailsVO(), 2),
        Arguments.of(null, null, 1),
        Arguments.of(null, new StockDetailsVO(), 1));
  }
}
