package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMockCcaAuditVO;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_NAME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(JUnitPlatform.class)
class CcaAuditEventMapperTest {

    private static final String MASTER_DOCUMENT_NUMBER = "23323311";
    private static final String CCA_000001 = "CCA000001";

    private final CcaAuditEventMapper ccaAuditEventMapper = new CcaAuditEventMapper();

    @Test
    void mapDoesNotThrow() {
        assertDoesNotThrow(() -> ccaAuditEventMapper.map(getAuditEvent()));
    }

    @Test
    void mapReturnsCcaAudit() {
        AuditEvent auditEvent = getAuditEvent();
        var actual = ccaAuditEventMapper.map(auditEvent);
        assertNotNull(actual);
    }

    private AuditEvent getAuditEvent() {
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        var auditVO = getMockCcaAuditVO(ccaMasterVO);
        return new AuditEvent(auditVO, CCA_AUDIT_EVENT_NAME);
    }
}