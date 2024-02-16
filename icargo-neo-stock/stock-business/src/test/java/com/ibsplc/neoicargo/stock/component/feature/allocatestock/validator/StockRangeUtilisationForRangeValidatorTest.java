package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_001;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.UtilisationDao;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.UtilisationFilterVO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockRangeUtilisationForRangeValidatorTest {

  @InjectMocks private StockRangeUtilisationForRangeValidator validator;

  @Mock private UtilisationDao dao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() {
    // Given
    var rangesVO = new RangeVO();
    rangesVO.setStartRange("1001000");
    rangesVO.setEndRange("1002000");

    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(List.of(rangesVO));

    // When
    doReturn(0L).when(dao).findStockUtilisationForRange(any(UtilisationFilterVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(stockAllocationVO));
  }

  @Test
  void shouldThrowUtilisationException() {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setRanges(List.of(new RangeVO()));

    // When
    doReturn(1L).when(dao).findStockUtilisationForRange(any(UtilisationFilterVO.class));

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockAllocationVO));
    assertEquals(NEO_STOCK_001.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
