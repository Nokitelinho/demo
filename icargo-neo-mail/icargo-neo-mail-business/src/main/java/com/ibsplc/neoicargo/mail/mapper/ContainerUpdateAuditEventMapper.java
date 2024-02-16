package com.ibsplc.neoicargo.mail.mapper;


import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEventMapper;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.mail.component.ContainerAudit;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("containerUpdateAuditEventMapper")
@RequiredArgsConstructor
public class ContainerUpdateAuditEventMapper extends AuditEventMapper {

    @Autowired
    private ContextUtil contextUtil;

    @Override
    public ContainerAudit map(AuditEvent auditEvent) {
        ContainerAudit containerAudit = new ContainerAudit();
        containerAudit.setCompanyCode(auditEvent.getCompanyCode());
        containerAudit.setConnum(((ContainerVO)auditEvent.getMessage()).getContainerNumber());
        return containerAudit;
    }
}