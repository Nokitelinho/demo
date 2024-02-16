package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;

@EProductProxy(module = "mail", submodule = "mra", name = "mailOperationsMRAEProxy")
public interface MailOperationsMRAEProxy {
    void recalculateDisincentiveData(Collection<RateAuditDetailsVO> rateAuditVos);
}
