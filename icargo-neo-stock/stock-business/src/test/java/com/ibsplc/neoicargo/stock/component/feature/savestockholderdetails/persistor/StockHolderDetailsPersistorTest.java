package com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.persistor;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_016;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfig;
import com.ibsplc.neoicargo.stock.dao.entity.StockHolder;
import com.ibsplc.neoicargo.stock.dao.repository.StockHolderRepository;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockMapper;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockVO;
import java.time.ZonedDateTime;
import java.util.Collections;
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
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
class StockHolderDetailsPersistorTest {
  @InjectMocks private StockHolderDetailsPersistor stockHolderDetailsPersistor;
  @Mock private AuditUtils<AuditVO> auditUtils;
  @Mock private StockDetailsPersistor stockDetailsPersistor;
  @Mock private StockHolderRepository stockHolderRepository;
  @Spy private StockHolderMapper stockHolderMapper = Mappers.getMapper(StockHolderMapper.class);
  @Spy private StockMapper stockMapper = Mappers.getMapper(StockMapper.class);
  @Captor ArgumentCaptor<StockHolder> stockHolder;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(stockHolderMapper, "stockMapper", stockMapper);
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

    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setOperationFlag("I");
    stockHolderVO.setStock(Collections.singleton(stockVO));
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));
    doReturn(Optional.empty())
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());

    // Then
    Assertions.assertDoesNotThrow(() -> stockHolderDetailsPersistor.save(stockHolderVO));
    verify(auditUtils, times(2)).performAudit(any(AuditConfig.class));
    verify(stockHolderRepository, times(1)).save(any(StockHolder.class));
    verify(stockHolderRepository, times(1))
        .findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
  }

  @Test
  void shouldThrowsWhileSave() {
    // Given

    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setOperationFlag("I");
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doReturn(Optional.of(new StockHolder()))
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());

    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> stockHolderDetailsPersistor.save(stockHolderVO));
    assertEquals(NEO_STOCK_016.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
    verify(auditUtils, times(0)).performAudit(any(AuditConfig.class));
    verify(stockHolderRepository, times(0)).save(any(StockHolder.class));
    verify(stockHolderRepository, times(1))
        .findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
  }

  @Test
  void shouldUpdateStock() throws StockBusinessException {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineId(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("U");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setStockHolderName("name");
    stockHolderVO.setStockHolderContactDetails("details");
    stockHolderVO.setStock(Collections.singleton(stockVO));
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));
    doReturn(Optional.of(new StockHolder()))
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
    doNothing().when(stockDetailsPersistor).processStocks(any(StockHolder.class), anyCollection());

    // Then
    Assertions.assertDoesNotThrow(() -> stockHolderDetailsPersistor.update(stockHolderVO));
    verify(auditUtils, times(1)).performAudit(any(AuditConfig.class));
    verify(stockDetailsPersistor, times(1)).processStocks(stockHolder.capture(), anyCollection());
    verify(stockHolderRepository, times(1))
        .findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());
    assertEquals(stockHolderVO.getStockHolderName(), stockHolder.getValue().getStockHolderName());
    assertEquals(
        stockHolderVO.getStockHolderContactDetails(),
        stockHolder.getValue().getStockHolderContactDetails());
  }

  @Test
  void shouldThrowsUpdateStock() {
    // Given
    var stockVO = new StockVO();
    stockVO.setCompanyCode("AV");
    stockVO.setStockHolderCode("HQ");
    stockVO.setAirlineId(1234);
    stockVO.setDocumentType("AWB");
    stockVO.setDocumentSubType("S");
    stockVO.setOperationFlag("U");
    stockVO.setLastUpdateTime(ZonedDateTime.now());

    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setCompanyCode("AV");
    stockHolderVO.setStockHolderCode("HQ");
    stockHolderVO.setStockHolderName("name");
    stockHolderVO.setStockHolderContactDetails("details");
    stockHolderVO.setStock(Collections.singleton(stockVO));
    stockHolderVO.setLastUpdateTime(ZonedDateTime.now());

    // When
    doNothing().when(auditUtils).performAudit(any(AuditConfig.class));
    doReturn(Optional.empty())
        .when(stockHolderRepository)
        .findByCompanyCodeAndStockHolderCode(
            stockHolderVO.getCompanyCode(), stockHolderVO.getStockHolderCode());

    // Then
    var thrown =
        assertThrows(
            BusinessException.class, () -> stockHolderDetailsPersistor.update(stockHolderVO));
    assertEquals(NEO_STOCK_011.getErrorCode(), thrown.getErrors().get(0).getErrorCode());
  }
}
