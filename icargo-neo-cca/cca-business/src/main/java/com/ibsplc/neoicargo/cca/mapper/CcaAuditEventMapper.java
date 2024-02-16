package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.dao.entity.CcaAudit;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEventMapper;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ccaAuditEventMapper")
@RequiredArgsConstructor
public class CcaAuditEventMapper extends AuditEventMapper {


    @Override
    public CcaAudit map(AuditEvent auditEvent) {
        log.debug("Create CCA Audit Entity of [{}]", auditEvent.getEntityId());
        return new CcaAudit();
    }

}
