package com.ibsplc.neoicargo.stock.component.feature.allocatestock.handler.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_009;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.UtilisationDao;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
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
class StockPeriodValidatorTest {

  @InjectMocks private StockPeriodValidator validator;

  @Mock private UtilisationDao dao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() {
    // When
    doReturn(List.of()).when(dao).validateStockPeriod(any(StockAllocationVO.class), anyInt());

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(new StockAllocationVO(), 1));
  }

  @Test
  void shouldThrowStockBusinessException() {
    // When
    doReturn(List.of(1L, 5L, 6L))
        .when(dao)
        .validateStockPeriod(any(StockAllocationVO.class), anyInt());

    // Then
    var thrown =
        assertThrows(BusinessException.class, () -> validator.validate(new StockAllocationVO(), 1));
    assertEquals(NEO_STOCK_009.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
