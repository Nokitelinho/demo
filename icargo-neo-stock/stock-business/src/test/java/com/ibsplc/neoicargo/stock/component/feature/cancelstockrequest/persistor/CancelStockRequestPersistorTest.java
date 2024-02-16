package com.ibsplc.neoicargo.stock.component.feature.cancelstockrequest.persistor;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_COMPLETED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_NEW;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.dao.repository.StockRequestRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class CancelStockRequestPersistorTest {

  @InjectMocks private CancelStockRequestPersistor cancelStockRequestPersistor;

  @Mock private StockRequestRepository stockRequestRepository;

  private StockRequestVO stockRequestVO;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    stockRequestVO = new StockRequestVO();
    stockRequestVO.setCompanyCode("1");
    stockRequestVO.setRequestRefNumber("1");
    stockRequestVO.setAirlineIdentifier("1");
    stockRequestVO.setLastUpdateUser("1");
    stockRequestVO.setLastUpdateDate(ZonedDateTime.now());
  }

  @Test
  void performAndThrowException() {
    // When
    doReturn(Optional.empty())
        .when(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier("1", "1", 1);

    // Then
    assertThrows(
        StockBusinessException.class,
        () -> cancelStockRequestPersistor.cancelStockRequest(stockRequestVO));
    verify(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier("1", "1", 1);
  }

  @Test
  void perform() throws StockBusinessException {
    // Given
    var stockRequest = new StockRequest();
    stockRequest.setStatus(STATUS_NEW);

    // When
    doReturn(Optional.of(stockRequest))
        .when(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier("1", "1", 1);
    cancelStockRequestPersistor.cancelStockRequest(stockRequestVO);

    verify(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier("1", "1", 1);
  }

  @Test
  void performAndThrowExceptionWhenExistsStatusIsInvalid() {
    // Given
    var stockRequest = new StockRequest();
    stockRequest.setStatus(STATUS_COMPLETED);

    // When
    doReturn(Optional.of(stockRequest))
        .when(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier("1", "1", 1);

    // Then
    assertThrows(
        StockBusinessException.class,
        () -> cancelStockRequestPersistor.cancelStockRequest(stockRequestVO));
    verify(stockRequestRepository)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier("1", "1", 1);
  }
}
