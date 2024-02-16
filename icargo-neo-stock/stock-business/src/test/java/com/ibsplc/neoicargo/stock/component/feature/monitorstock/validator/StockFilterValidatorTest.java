package com.ibsplc.neoicargo.stock.component.feature.monitorstock.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockFilterValidatorTest {
  @InjectMocks private StockFilterValidator validator;
  @Mock private StockDao dao;

  private StockFilterVO populateStockFilterVO() {
    var stockFilterVO = new StockFilterVO();
    stockFilterVO.setCompanyCode("AVFMD");
    stockFilterVO.setAirlineIdentifier(1191);
    stockFilterVO.setStockHolderCode("HQ");
    stockFilterVO.setStockHolderType("H");
    stockFilterVO.setDocumentType("AWB");
    stockFilterVO.setDocumentSubType("S");
    stockFilterVO.setPageNumber(1);
    stockFilterVO.setAbsoluteIndex(1);
    stockFilterVO.setPrivilegeLevelType("TEST-level-type");
    stockFilterVO.setPrivilegeLevelValue(null);
    stockFilterVO.setPrivilegeRule("TEST-rule");

    return stockFilterVO;
  }

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldValidateWithoutThrows() {
    // Given
    var stockFilterVO = populateStockFilterVO();

    // When
    doReturn(new StockHolderVO()).when(dao).findStockHolderDetails(anyString(), anyString());

    // Then
    assertDoesNotThrow(() -> validator.validate(stockFilterVO));
  }

  @Test
  void shouldValidateCompanyCode() {
    // Given
    var stockFilterVO = populateStockFilterVO();
    stockFilterVO.setCompanyCode(null);

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockFilterVO));
    assertEquals("NEO_STOCK_011", thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldValidateStockHolderCode() {
    // Given
    var stockFilterVO = populateStockFilterVO();
    stockFilterVO.setStockHolderCode(null);

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockFilterVO));
    assertEquals("NEO_STOCK_011", thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldValidateDocumentType() {
    // Given
    var stockFilterVO = populateStockFilterVO();
    stockFilterVO.setDocumentType(null);

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockFilterVO));
    assertEquals("NEO_STOCK_011", thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldValidateDocumentSubType() {
    // Given
    var stockFilterVO = populateStockFilterVO();
    stockFilterVO.setDocumentSubType(null);

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockFilterVO));
    assertEquals("NEO_STOCK_011", thrown.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldThrowStockHolderNotExist() {
    // Given
    var stockFilterVO = populateStockFilterVO();

    // When
    doReturn(null).when(dao).findStockHolderDetails(anyString(), anyString());

    // Then
    var thrown = assertThrows(BusinessException.class, () -> validator.validate(stockFilterVO));
    assertEquals("NEO_STOCK_011", thrown.getErrors().get(0).getErrorCode());
  }
}
