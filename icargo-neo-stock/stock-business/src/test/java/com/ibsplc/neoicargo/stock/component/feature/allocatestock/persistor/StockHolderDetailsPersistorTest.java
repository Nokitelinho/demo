package com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import java.time.ZonedDateTime;
import java.util.HashSet;
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
class StockHolderDetailsPersistorTest {

  @InjectMocks private StockHolderPersistor persistor;

  @Mock private StockHolderRepository repository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldAddStock() {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setCompanyCode("HQ");
    stockAllocationVO.setStockHolderCode("AWB");
    stockAllocationVO.setLastUpdateTime(ZonedDateTime.now());

    var stockHolder = new StockHolder();
    stockHolder.setStock(new HashSet<>());

    // When
    doReturn(Optional.of(stockHolder))
        .when(repository)
        .findByCompanyCodeAndStockHolderCode("HQ", "AWB");
    doReturn(stockHolder).when(repository).save(any(StockHolder.class));

    // Then
    Assertions.assertDoesNotThrow(() -> persistor.addStock(stockAllocationVO));
    verify(repository, times(1)).findByCompanyCodeAndStockHolderCode(anyString(), anyString());
    verify(repository, times(1)).save(any(StockHolder.class));
  }
}
