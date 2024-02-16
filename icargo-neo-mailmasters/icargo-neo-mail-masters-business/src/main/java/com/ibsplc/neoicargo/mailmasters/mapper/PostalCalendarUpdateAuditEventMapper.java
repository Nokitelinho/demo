package com.ibsplc.neoicargo.mailmasters.mapper;

import com.ibsplc.neoicargo.framework.tenant.audit.AuditEventMapper;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.mailmasters.component.MailMasterAudit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("postalCalendarUpdateAuditEventMapper")
@RequiredArgsConstructor
public class PostalCalendarUpdateAuditEventMapper extends AuditEventMapper {
    private static final String POSTAL_CALENDAR ="Postal Calendar";

    @Override
    public MailMasterAudit map(AuditEvent auditEvent) {
        MailMasterAudit postalCalendarAudit = new MailMasterAudit();
        postalCalendarAudit.setCompanyCode(auditEvent.getCompanyCode());
        postalCalendarAudit.setAuditingEntity(POSTAL_CALENDAR);

        return postalCalendarAudit;
    }

}
