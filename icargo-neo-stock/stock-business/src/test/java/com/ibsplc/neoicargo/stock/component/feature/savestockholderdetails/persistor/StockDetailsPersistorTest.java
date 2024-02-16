package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_003;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_017;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfig;
import com.ibsplc.neoicargo.stock.dao.entity.Range;
import com.ibsplc.neoicargo.stock.dao.entity.Stock;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
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
class StockDetailsPersistorTest {

  @InjectMocks private StockDetailsPersistor persistor;

  @Spy private StockMapper stockMapper = Mappers.getMapper(StockMapper.class);

  @Captor ArgumentCaptor<AuditConfig> auditConfigCaptor;

  @Mock private AuditUtils<AuditVO> auditUtils;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldAddNewStock() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineId(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("I");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stockHolder = new StockHolder();
    stockHolder.setCompanyCode("AV");
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setStock(new HashSet<>());

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));

    // Then
    Assertions.assertDoesNotThrow(
        () -> persistor.processStocks(stockHolder, Collections.singleton(stockVO)));
    verify(auditUtils, times(1)).performAudit(auditConfigCaptor.capture());
    verifyAudit();
    assertEquals(1, stockHolder.getStock().size());
  }

  @Test
  void shouldUpdateStock() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineIdentifier(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("U");
    stockVO.setRemarks("UPDATED");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stock = new Stock();
    stock.setCompanyCode("AV");
    stock.setStockHolderCode("HQ");
    stock.setAirlineIdentifier(1234);
    stock.setDocumentType("AWB");
    stock.setDocumentSubType("S");
    stock.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));

    var stocks = new HashSet<Stock>();
    stocks.add(stock);

    var stockHolder = new StockHolder();
    stockHolder.setCompanyCode("AV");
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setStock(stocks);

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));

    // Then
    Assertions.assertDoesNotThrow(
        () -> persistor.processStocks(stockHolder, Collections.singleton(stockVO)));
    verify(auditUtils, times(1)).performAudit(auditConfigCaptor.capture());
    verifyAudit();
    assertEquals(1, stockHolder.getStock().size());
    stockHolder.getStock().forEach(stk -> assertEquals("UPDATED", stk.getRemarks()));
  }

  @Test
  void shouldRemoveStock() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineIdentifier(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("D");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stock = new Stock();
    stock.setCompanyCode("AV");
    stock.setStockHolderCode("HQ");
    stock.setAirlineIdentifier(1234);
    stock.setDocumentType("AWB");
    stock.setDocumentSubType("S");
    stock.setRanges(Collections.emptySet());
    stock.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));

    var stocks = new HashSet<Stock>();
    stocks.add(stock);

    var stockHolder = new StockHolder();
    stockHolder.setCompanyCode("AV");
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setStock(stocks);

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));

    // Then
    Assertions.assertDoesNotThrow(
        () -> persistor.processStocks(stockHolder, Collections.singleton(stockVO)));
    verify(auditUtils, times(1)).performAudit(auditConfigCaptor.capture());
    verifyAudit();
    assertEquals(0, stockHolder.getStock().size());
  }

  @Test
  void shouldThrowsWhileRemove() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineIdentifier(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("D");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stock = new Stock();
    stock.setCompanyCode("AV");
    stock.setStockHolderCode("HQ");
    stock.setAirlineIdentifier(1234);
    stock.setDocumentType("AWB");
    stock.setDocumentSubType("S");
    stock.setRanges(Collections.emptySet());
    stock.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));
    stock.setRanges(Collections.singleton(new Range()));

    var stocks = new HashSet<Stock>();
    stocks.add(stock);

    var stockHolder = new StockHolder();
    stockHolder.setCompanyCode("AV");
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setStock(stocks);

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));

    // Then
    var thrown =
        assertThrows(
            BusinessException.class,
            () -> persistor.processStocks(stockHolder, Collections.singleton(stockVO)));
    assertEquals(NEO_STOCK_017.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(auditUtils, times(1)).performAudit(auditConfigCaptor.capture());
    verifyAudit();
  }

  @Test
  void shouldThrowsWhileUpdateStock() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineIdentifier(1233);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("U");
    stockVO.setRemarks("UPDATED");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stock = new Stock();
    stock.setCompanyCode("AV");
    stock.setStockHolderCode("HQ");
    stock.setAirlineIdentifier(1234);
    stock.setDocumentType("AWB");
    stock.setDocumentSubType("S");
    stock.setLastUpdatedTime(Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime()));

    var stocks = new HashSet<Stock>();
    stocks.add(stock);

    var stockHolder = new StockHolder();
    stockHolder.setCompanyCode("AV");
    stockHolder.setStockHolderCode("HQ");
    stockHolder.setStock(stocks);

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));

    // Then
    var thrown =
        assertThrows(
            BusinessException.class,
            () -> persistor.processStocks(stockHolder, Collections.singleton(stockVO)));
    assertEquals(NEO_STOCK_003.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(auditUtils, times(1)).performAudit(auditConfigCaptor.capture());
  }

  private void verifyAudit() {
    var auditConfig = auditConfigCaptor.getValue();
    var auditVO = auditConfig.getAuditVO();
    assertNotNull(auditVO);
    var businessObject = auditVO.getBusinessObject();
    assertNotNull(businessObject);
    assertEquals(StockVO.class, businessObject.getClass());
  }
}
