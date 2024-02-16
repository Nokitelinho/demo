package com.ibsplc.neoicargo.stock.mapper;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAuditVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StockAuditEventMapperTest {
  private StockAuditEventMapper stockAuditEventMapper = new StockAuditEventMapper();

  @Test
  void mapDoesNotThrow() {
    // Given
    var stockVo = getMockStockVO(getMockStockHolderVO("AV", "HQ"), 1134, "AWB", "S");
    var auditVO = getMockStockAuditVO(stockVo);
    var auditEvent = new AuditEvent(auditVO, STOCK_AUDIT_EVENT_NAME);

    // Then
    assertDoesNotThrow(() -> stockAuditEventMapper.map(auditEvent));
  }

  @Test
  void mapReturnStock() {
    // Given
    var stockVo = getMockStockVO(getMockStockHolderVO("AV", "HQ"), 1134, "AWB", "S");
    var auditVO = getMockStockAuditVO(stockVo);
    var auditEvent = new AuditEvent(auditVO, STOCK_AUDIT_EVENT_NAME);

    // Then
    var actual = stockAuditEventMapper.map(auditEvent);
    assertNotNull(actual);
  }
}
