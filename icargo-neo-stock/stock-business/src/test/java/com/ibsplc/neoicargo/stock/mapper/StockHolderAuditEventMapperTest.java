package com.ibsplc.neoicargo.stock.mapper;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderAuditVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StockHolderAuditEventMapperTest {

  private StockHolderAuditEventMapper stockHolderAuditEventMapper =
      new StockHolderAuditEventMapper();

  @Test
  void mapDoesNotThrow() {
    // Given
    var stockHolderVO = getMockStockHolderVO("AV", "HQ");
    var auditVO = getMockStockHolderAuditVO(stockHolderVO);
    var auditEvent = new AuditEvent(auditVO, STOCK_AUDIT_EVENT_NAME);

    // Then
    assertDoesNotThrow(() -> stockHolderAuditEventMapper.map(auditEvent));
  }

  @Test
  void mapReturnStockHolder() {
    // Given
    var stockHolderVO = getMockStockHolderVO("AV", "HQ");
    var auditVO = getMockStockHolderAuditVO(stockHolderVO);
    var auditEvent = new AuditEvent(auditVO, STOCK_AUDIT_EVENT_NAME);

    // Then
    var actual = stockHolderAuditEventMapper.map(auditEvent);
    assertNotNull(actual);
  }
}
