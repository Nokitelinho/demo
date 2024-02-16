package com.ibsplc.neoicargo.stock.mapper;

import com.ibsplc.neoicargo.framework.tenant.audit.AuditEventMapper;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.stock.dao.entity.StockAudit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stockHolderAuditEventMapper")
@RequiredArgsConstructor
public class StockHolderAuditEventMapper extends AuditEventMapper {

  @Override
  public StockAudit map(AuditEvent auditEvent) {
    log.debug("Create CCA Audit Entity of [{}]", auditEvent.getEntityId());
    return new StockAudit();
  }
}
