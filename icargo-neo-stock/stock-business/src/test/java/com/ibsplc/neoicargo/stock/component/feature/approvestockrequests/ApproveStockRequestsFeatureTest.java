package com.ibsplc.neoicargo.stock.component.feature.approvestockrequests;

import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_ALLOCATED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_APPROVED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_COMPLETED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.STATUS_NEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.StockRequestDao;
import com.ibsplc.neoicargo.stock.dao.entity.StockRequest;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class ApproveStockRequestsFeatureTest {
  @InjectMocks private ApproveStockRequestsFeature approveStockRequestsFeature;
  @Mock private StockRequestDao stockRequestDao;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private StockRequestVO populateStockRequestVO() {
    var stockRequestVO = new StockRequestVO();
    stockRequestVO.setCompanyCode("AV");
    stockRequestVO.setRequestRefNumber("1234");
    stockRequestVO.setAirlineIdentifier("1191");

    return stockRequestVO;
  }

  private StockRequestApproveVO populateStockRequestApproveVO(StockRequestVO stockRequestVO) {
    var vo = new StockRequestApproveVO();
    vo.setCompanyCode("AV");
    vo.setApproverCode("HQ");
    vo.setStockRequests(List.of(stockRequestVO));

    return vo;
  }

  @Test
  void shouldApproveStockRequests() {
    // Given
    var stockRequest = new StockRequest();
    var stockRequestVO = populateStockRequestVO();
    var vo = populateStockRequestApproveVO(stockRequestVO);

    // When
    doReturn(Optional.of(stockRequest))
        .when(stockRequestDao)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            anyString(), anyString(), anyInt());

    // Then
    Assertions.assertDoesNotThrow(() -> approveStockRequestsFeature.perform(vo));
    verify(stockRequestDao, times(1)).save(stockRequest);
    assertThat(STATUS_NEW).isEqualTo(stockRequest.getStatus());
  }

  @Test
  void shouldSetStatusCompletedForStockRequest() {
    // Given
    var stockRequest = new StockRequest();
    var stockRequestVO = populateStockRequestVO();
    var vo = populateStockRequestApproveVO(stockRequestVO);

    stockRequestVO.setApprovedStock(1L);
    stockRequestVO.setAllocatedStock(1L);

    // When
    doReturn(Optional.of(stockRequest))
        .when(stockRequestDao)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            anyString(), anyString(), anyInt());

    // Then
    Assertions.assertDoesNotThrow(() -> approveStockRequestsFeature.perform(vo));
    verify(stockRequestDao, times(1)).save(stockRequest);
    assertThat(STATUS_COMPLETED).isEqualTo(stockRequest.getStatus());
  }

  @Test
  void shouldSetStatusApprovedForStockRequest() {
    // Given
    var stockRequest = new StockRequest();
    var stockRequestVO = populateStockRequestVO();
    var vo = populateStockRequestApproveVO(stockRequestVO);

    stockRequestVO.setApprovedStock(0L);
    stockRequestVO.setAllocatedStock(1L);

    // When
    doReturn(Optional.of(stockRequest))
        .when(stockRequestDao)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            anyString(), anyString(), anyInt());

    // Then
    Assertions.assertDoesNotThrow(() -> approveStockRequestsFeature.perform(vo));
    verify(stockRequestDao, times(1)).save(stockRequest);
    assertThat(STATUS_APPROVED).isEqualTo(stockRequest.getStatus());
  }

  @Test
  void shouldSetStatusAllocatedForStockRequest() {
    // Given
    var stockRequest = new StockRequest();
    var stockRequestVO = populateStockRequestVO();
    var vo = populateStockRequestApproveVO(stockRequestVO);

    stockRequestVO.setApprovedStock(1L);
    stockRequestVO.setAllocatedStock(1L);
    stockRequestVO.setRequestedStock(2L);

    // When
    doReturn(Optional.of(stockRequest))
        .when(stockRequestDao)
        .findByCompanyCodeAndRequestRefNumberAndAirlineIdentifier(
            anyString(), anyString(), anyInt());

    // Then
    Assertions.assertDoesNotThrow(() -> approveStockRequestsFeature.perform(vo));
    verify(stockRequestDao, times(1)).save(stockRequest);
    assertThat(STATUS_ALLOCATED).isEqualTo(stockRequest.getStatus());
  }
}
