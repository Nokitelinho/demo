package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailScanDetailVO;

import java.util.Collection;

@EProductProxy(module = "mail", submodule = "mra", name = "MailMRAAsyncEProxy",asyncDispatch=true)
public interface MailMRAAsyncEProxy {
    void importMRAData(Collection<RateAuditVO> rateAuditVOs);

    void importMailProvisionalRateData(Collection<RateAuditVO> provisionalRateAuditVOs);

    void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO);

    void importResditDataToMRA(MailScanDetailVO mailScanDetailVO);

}
