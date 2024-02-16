package com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_007;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_ALLOCATED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_APPROVED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_COMPLETED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class AllocateStockRequestDetailsPersistorTest {

  @InjectMocks private StockRequestPersistor persistor;

  @Mock private StockRequestDao dao;

  @Captor ArgumentCaptor<StockRequest> stockRequestCaptor;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("provideStockRequests")
  void shouldUpdateWithoutThrows(StockRequest request, String status) {
    // Given
    var rangeVO = new RangeVO();
    rangeVO.setStartRange("1001000");
    rangeVO.setEndRange("1002000");

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setCompanyCode("HQ");
    stockAllocationVO.setRequestRefNumber("123");
    stockAllocationVO.setAirlineIdentifier(1);
    stockAllocationVO.setLastUpdateTimeForStockReq(ZonedDateTime.now());
    stockAllocationVO.setRanges(List.of(rangeVO));

    // When
    doReturn(Optional.of(request))
        .when(dao)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            anyString(), anyString(), anyInt());
    doNothing().when(dao).save(stockRequestCaptor.capture());

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.update(stockAllocationVO));
    assertEquals(status, stockRequestCaptor.getValue().getStatus());
  }

  private static Stream<Arguments> provideStockRequests() {
    return Stream.of(
        Arguments.of(
            StockRequest.builder().approvedStock(0).allocatedStock(-1001).build(), STATUS_NEW),
        Arguments.of(
            StockRequest.builder().approvedStock(1001).allocatedStock(0).build(), STATUS_COMPLETED),
        Arguments.of(
            StockRequest.builder()
                .approvedStock(1011)
                .allocatedStock(10)
                .requestedStock(1500)
                .build(),
            STATUS_ALLOCATED),
        Arguments.of(
            StockRequest.builder().approvedStock(5).allocatedStock(10).build(), STATUS_APPROVED));
  }

  @Test
  void shouldThrowStockRequestNotFound() {
    // When
    doReturn(Optional.empty())
        .when(dao)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            anyString(), anyString(), anyInt());

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> persistor.update(new StockAllocationVO()));
    assertEquals(NEO_STOCK_007.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(dao, never()).save(any(StockRequest.class));
  }
}
