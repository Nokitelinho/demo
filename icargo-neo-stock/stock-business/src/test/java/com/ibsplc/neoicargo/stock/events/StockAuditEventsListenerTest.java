package com.ibsplc.neoicargo.stock.events;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAudit;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.stock.audit.StockAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
public class StockAuditEventsListenerTest {
  @InjectMocks private StockAuditEventsListener stockAuditEventsListener;
  @Mock private StockAuditService stockAuditService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void processEvent() {
    // Given
    var auditList = getMockStockAudit(STOCK_AUDIT_CREATE_TRANSACTION);

    // Then
    assertDoesNotThrow(() -> stockAuditEventsListener.processEvent(auditList));
    verify(stockAuditService, times(1)).saveEvent(any(AuditEvent.class));
  }
}
