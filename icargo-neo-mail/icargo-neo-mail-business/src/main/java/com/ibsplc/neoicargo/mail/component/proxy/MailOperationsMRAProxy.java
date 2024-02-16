package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.MailOperationsMRAEProxy;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.MailOperationsMRAEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class MailOperationsMRAProxy {
    @Autowired
    private MailOperationsMRAEProxy mailOperationsMRAEProxy;
    public void recalculateDisincentiveData(Collection<RateAuditDetailsVO> rateAuditVos)throws SystemException{
        try {
            mailOperationsMRAEProxy. recalculateDisincentiveData(rateAuditVos);
        }catch(ServiceException serviceException){
            throw new SystemException(serviceException.getMessage());
        }
    }

}
