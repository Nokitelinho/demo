package com.ibsplc.neoicargo.stock.events;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_EVENTS_LISTENER_NAME;

import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEventList;
import com.ibsplc.neoicargo.stock.audit.StockAuditService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component(STOCK_AUDIT_EVENTS_LISTENER_NAME)
public class StockAuditEventsListener {
  private final StockAuditService stockAuditService;

  @EventListener(
      condition = "#auditEventList.listeners.contains('" + STOCK_AUDIT_EVENTS_LISTENER_NAME + "')")
  public void processEvent(AuditEventList auditEventList) {
    log.info("AuditEventListener invoked  the [{}]", this.getClass().getSimpleName());
    ((List<AuditEvent>) auditEventList.getMessage())
        .stream()
            .filter(auditEvent -> auditEvent.getListener().equals(STOCK_AUDIT_EVENTS_LISTENER_NAME))
            .forEach(stockAuditService::saveEvent);
  }
}
