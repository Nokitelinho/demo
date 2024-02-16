package com.ibsplc.neoicargo.stock.component.feature.blackliststock.handler.persistor;

import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockBlacklistStockVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator.RangeFormatValidator;
import com.ibsplc.neoicargo.stock.dao.entity.BlackListStock;
import com.ibsplc.neoicargo.stock.dao.repository.BlackListStockRepository;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
public class BlackListStockPersistorTest {

  @InjectMocks private BlackListStockPersistor blackListStockPersistor;
  @Mock private BlackListStockRepository blackListStockRepository;
  @Mock private RangeFormatValidator rangeFormatValidator;

  @Spy
  private BlackListStockMapper blacklistStockMapper = Mappers.getMapper(BlackListStockMapper.class);

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(rangeFormatValidator, blackListStockRepository);
  }

  @Test
  void shouldDeleteTransitStock() throws BusinessException {
    // Given
    var vo = getMockBlacklistStockVO();

    // When
    doNothing().when(rangeFormatValidator).validate(any(StockAllocationVO.class));

    // Then
    assertDoesNotThrow(() -> blackListStockPersistor.persist(vo));
    verify(blackListStockRepository, times(1)).save(any(BlackListStock.class));
    verify(rangeFormatValidator, times(1)).validate(any(StockAllocationVO.class));
  }
}
