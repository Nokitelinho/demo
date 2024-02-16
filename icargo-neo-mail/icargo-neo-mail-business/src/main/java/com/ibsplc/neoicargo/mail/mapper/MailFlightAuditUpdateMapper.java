package com.ibsplc.neoicargo.mail.mapper;


import com.ibsplc.neoicargo.framework.tenant.audit.AuditEventMapper;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.mail.component.AssignedFlight;
import com.ibsplc.neoicargo.mail.component.MailFlightAudit;
import com.ibsplc.neoicargo.mail.vo.AssignedFlightVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("mailFlightUpdateAuditEventMapper")
@RequiredArgsConstructor
public class MailFlightAuditUpdateMapper extends AuditEventMapper {

    @Override
    public MailFlightAudit map(AuditEvent auditEvent) {
        MailFlightAudit mailFlightAudit = new MailFlightAudit();
        mailFlightAudit.setCompanyCode(auditEvent.getCompanyCode());

        mailFlightAudit.setCarrierId(((AssignedFlightVO)auditEvent.getMessage()).getCarrierId());
        mailFlightAudit.setFlightNumber(((AssignedFlightVO)auditEvent.getMessage()).getFlightNumber());
        mailFlightAudit.setFlightSequenceNumber(((AssignedFlightVO) auditEvent.getMessage()).getFlightSequenceNumber());
        mailFlightAudit.setLegSerialNumber(((AssignedFlightVO) auditEvent.getMessage()).getLegSerialNumber());
        return mailFlightAudit;
    }


}
