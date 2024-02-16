package com.ibsplc.neoicargo.stock.audit;

import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.BUSINESS_ID;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.CREATED_STOCK_HOLDER_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.CREATED_STOCK_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.GROUP_SUFFIX;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.NEW_SUFFIX;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.OLD_SUFFIX;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.REMOVED_STOCK_HOLDER_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.REMOVED_STOCK_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_REMOVED_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_AUDIT_UPDATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_CREATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_REMOVED_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.STOCK_HOLDER_AUDIT_UPDATE_TRANSACTION;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.UPDATED_STOCK_HOLDER_TEMPLATE;
import static com.ibsplc.neoicargo.stock.util.StockAuditConstants.UPDATED_STOCK_TEMPLATE;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditFieldVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditService;
import com.ibsplc.neoicargo.framework.tenant.audit.ChangeGroupDetails;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.samskivert.mustache.Mustache;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockAuditService {
  private final AuditService auditService;
  private final Mustache.Compiler mustacheCompiler;
  private static final Map<String, String> TEMPLATES_BY_GROUP =
      Map.of(
          STOCK_HOLDER_AUDIT_CREATE_TRANSACTION,
          CREATED_STOCK_HOLDER_TEMPLATE,
          STOCK_HOLDER_AUDIT_UPDATE_TRANSACTION,
          UPDATED_STOCK_HOLDER_TEMPLATE,
          STOCK_AUDIT_CREATE_TRANSACTION,
          CREATED_STOCK_TEMPLATE,
          STOCK_AUDIT_UPDATE_TRANSACTION,
          UPDATED_STOCK_TEMPLATE,
          STOCK_AUDIT_REMOVED_TRANSACTION,
          REMOVED_STOCK_TEMPLATE,
          STOCK_HOLDER_AUDIT_REMOVED_TRANSACTION,
          REMOVED_STOCK_HOLDER_TEMPLATE);

  public void saveEvent(AuditEvent auditEvent) {
    log.info("{} with id: {} ", auditEvent.getTransaction(), auditEvent.getEntityId());
    auditEvent.setAddInfo(
        mustacheCompiler
            .loadTemplate(TEMPLATES_BY_GROUP.get(auditEvent.getTransaction()))
            .execute(getUpdatedContext(auditEvent)));
    try {
      auditService.saveAuditEvent(auditEvent);
    } catch (IOException | RuntimeException e) {
      log.warn(
          "Cannot save audit event [{}] for [{}]",
          auditEvent.getEventName(),
          auditEvent.getEntityId(),
          e);
    }
  }

  private Map<String, Object> getUpdatedContext(AuditEvent event) {
    var vo = (AbstractVO) event.getMessage();
    log.debug("Create additional info for entity [{}]", vo.getBusinessId());
    var context = new HashMap<String, Object>();
    context.put(BUSINESS_ID, vo.getBusinessId());
    Optional.ofNullable(event.getChangeGroupDetails().get(event.getEventName() + GROUP_SUFFIX))
        .stream()
        .flatMap(java.util.Collection::stream)
        .map(ChangeGroupDetails::getFields)
        .flatMap(java.util.Collection::stream)
        .filter(AuditFieldVO::isFieldModified)
        .forEach(
            auditField -> {
              // in case of create transaction oldValue == null
              context.put(auditField.getField() + OLD_SUFFIX, auditField.getOldValue());
              context.put(auditField.getField() + NEW_SUFFIX, auditField.getNewValue());
            });
    return context;
  }
}
