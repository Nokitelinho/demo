package com.ibsplc.neoicargo.stock.component.feature.checkstock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.dao.StockDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class CheckStockFeatureTest {

  @InjectMocks private CheckStockFeature checkStockFeature;

  @Mock private StockDao stockDao;

  private final String companyCode = "AV";
  private final String stockholderCode = "HQ";
  private final String docType = "AWB";
  private final String docSubType = "S";

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldPassWhenStockExists() {
    doReturn(true).when(stockDao).checkStock(companyCode, stockholderCode, docType, docSubType);

    assertDoesNotThrow(
        () -> checkStockFeature.perform(companyCode, stockholderCode, docType, docSubType));

    verify(stockDao).checkStock(companyCode, stockholderCode, docType, docSubType);
  }

  @Test
  void shouldNotPassWhenStockDoesntExists() {
    doReturn(false).when(stockDao).checkStock(companyCode, stockholderCode, docType, docSubType);

    assertThrows(
        BusinessException.class,
        () -> checkStockFeature.perform(companyCode, stockholderCode, docType, docSubType));

    verify(stockDao).checkStock(companyCode, stockholderCode, docType, docSubType);
  }
}
