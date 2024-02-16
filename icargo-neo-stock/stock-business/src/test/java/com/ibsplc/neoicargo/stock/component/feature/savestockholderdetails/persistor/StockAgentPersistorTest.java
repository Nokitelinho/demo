package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_015;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import com.ibsplc.neoicargo.stock.dao.repository.StockAgentRepository;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
class StockAgentPersistorTest {
  @InjectMocks private StockAgentPersistor stockAgentPersistor;
  @Mock private StockAgentRepository stockAgentRepository;
  @Spy private StockAgentMapper stockAgentMapper = Mappers.getMapper(StockAgentMapper.class);
  @Captor ArgumentCaptor<StockAgent> stockAgent;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldSave() {
    // Given
    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doReturn(Optional.empty())
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());

    // Then
    Assertions.assertDoesNotThrow(() -> stockAgentPersistor.save(stockHolderVO));
    verify(stockAgentRepository, times(1))
        .findByCompanyCodeAndAgentCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
    verify(stockAgentRepository, times(1)).save(stockAgent.capture());
    assertEquals(stockHolderVO.getCompanyCode(), stockAgent.getValue().getCompanyCode());
  }

  @Test
  void shouldThrowsWhileSave() {
    // Given
    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doReturn(Optional.of(new StockAgent()))
        .when(stockAgentRepository)
        .findByCompanyCodeAndAgentCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> stockAgentPersistor.save(stockHolderVO));
    assertEquals(NEO_STOCK_015.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(stockAgentRepository, times(1))
        .findByCompanyCodeAndAgentCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
    verify(stockAgentRepository, times(0)).save(any(StockAgent.class));
  }
}
