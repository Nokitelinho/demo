package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockRangeValidatorTest {

  @InjectMocks private StockRangeValidator stockRangeValidator;

  @Mock private RangeFormatValidator rangeFormatValidator;
  @Mock private BlackListRangeValidator blackListRangeValidator;
  @Mock private InvalidStockValidator invalidStockValidator;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() throws BusinessException {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setNewStockFlag(false);

    // When
    doNothing().when(rangeFormatValidator).validate(any(StockAllocationVO.class));
    doNothing().when(blackListRangeValidator).validate(any(StockAllocationVO.class));
    doNothing()
        .when(invalidStockValidator)
        .validate(any(StockVO.class), any(StockAllocationVO.class));

    // Then
    Assertions.assertDoesNotThrow(
        () -> stockRangeValidator.validate(new StockVO(), stockAllocationVO, false));
    verify(rangeFormatValidator).validate(any(StockAllocationVO.class));
    verify(blackListRangeValidator).validate(any(StockAllocationVO.class));
    verify(invalidStockValidator).validate(any(StockVO.class), any(StockAllocationVO.class));
  }

  @Test
  void shouldNotValidateBlacklist() throws BusinessException {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setNewStockFlag(true);

    // When
    doNothing().when(rangeFormatValidator).validate(any(StockAllocationVO.class));
    doNothing()
        .when(invalidStockValidator)
        .validate(any(StockVO.class), any(StockAllocationVO.class));

    // Then
    Assertions.assertDoesNotThrow(
        () -> stockRangeValidator.validate(new StockVO(), stockAllocationVO, true));
    verify(rangeFormatValidator).validate(any(StockAllocationVO.class));
    verify(blackListRangeValidator, never()).validate(any(StockAllocationVO.class));
    verify(invalidStockValidator, never())
        .validate(any(StockVO.class), any(StockAllocationVO.class));
  }

  @Test
  void shouldNotValidateNewStock() throws BusinessException {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setNewStockFlag(false);

    // When
    doNothing().when(rangeFormatValidator).validate(any(StockAllocationVO.class));
    doNothing()
        .when(invalidStockValidator)
        .validate(any(StockVO.class), any(StockAllocationVO.class));

    // Then
    Assertions.assertDoesNotThrow(
        () -> stockRangeValidator.validate(new StockVO(), stockAllocationVO, true));
    verify(rangeFormatValidator).validate(any(StockAllocationVO.class));
    verify(blackListRangeValidator, never()).validate(any(StockAllocationVO.class));
    verify(invalidStockValidator, never())
        .validate(any(StockVO.class), any(StockAllocationVO.class));
  }
}
