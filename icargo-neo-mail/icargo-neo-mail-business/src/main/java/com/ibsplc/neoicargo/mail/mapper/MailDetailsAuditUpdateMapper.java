package com.ibsplc.neoicargo.mail.mapper;


import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEventMapper;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.mail.component.MailDetailsAudit;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagAuditVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("maildetailsUpdateAuditEventMapper")
@RequiredArgsConstructor
public class MailDetailsAuditUpdateMapper extends AuditEventMapper {

    @Autowired
    private ContextUtil contextUtil;

    @Override
    public MailDetailsAudit map(AuditEvent auditEvent) {
        MailDetailsAudit mailDetailsAudit = new MailDetailsAudit();
        mailDetailsAudit.setCompanyCode(auditEvent.getCompanyCode());
        mailDetailsAudit.setMailIdr(((MailbagVO)auditEvent.getMessage()).getMailbagId());
        return mailDetailsAudit;
    }
}
