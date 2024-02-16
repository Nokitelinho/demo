package com.ibsplc.neoicargo.stock.component.feature.allocatestock.validator;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_004;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.persistor.StockHolderPersistor;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockHolderValidatorTest {

  @InjectMocks private StockHolderValidator validator;

  @Mock private StockDao dao;
  @Mock private StockHolderPersistor persistor;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setNewStockFlag(true);
    stockAllocationVO.setOperationFlag(StockAllocationVO.OPERATION_FLAG_INSERT);
    stockAllocationVO.setCompanyCode("HQ");
    stockAllocationVO.setStockHolderCode("AWB");

    // When
    doReturn(new StockHolderVO()).when(dao).findStockHolderDetails(anyString(), anyString());
    doNothing().when(persistor).addStock(any(StockAllocationVO.class));

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(stockAllocationVO));
  }

  @Test
  void shouldNotValidateDueToNewStockFlag() {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setNewStockFlag(false);
    stockAllocationVO.setOperationFlag(StockAllocationVO.OPERATION_FLAG_INSERT);
    stockAllocationVO.setCompanyCode("HQ");
    stockAllocationVO.setStockHolderCode("AWB");

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(stockAllocationVO));
    verify(dao, never()).findStockHolderDetails(anyString(), anyString());
    verify(persistor, never()).addStock(any(StockAllocationVO.class));
  }

  @Test
  void shouldNotCallPersistor() {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setNewStockFlag(true);
    stockAllocationVO.setOperationFlag(StockAllocationVO.OPERATION_FLAG_DELETE);
    stockAllocationVO.setCompanyCode("HQ");
    stockAllocationVO.setStockHolderCode("AWB");

    // When
    doReturn(new StockHolderVO()).when(dao).findStockHolderDetails(anyString(), anyString());

    // Then
    Assertions.assertDoesNotThrow(() -> validator.validate(stockAllocationVO));
    verify(dao).findStockHolderDetails(anyString(), anyString());
    verify(persistor, never()).addStock(any(StockAllocationVO.class));
  }

  @Test
  void shouldThrowStockHolderNotExist() {
    // Given
    var stockAllocationVO = new StockAllocationVO();
    stockAllocationVO.setNewStockFlag(true);

    // When
    doReturn(null).when(dao).findStockHolderDetails(anyString(), anyString());

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockAllocationVO));
    assertEquals(NEO_STOCK_004.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(persistor, never()).addStock(any(StockAllocationVO.class));
  }
}
