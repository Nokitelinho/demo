package com.ibsplc.neoicargo.cca.service;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.tenant.audit.ChangeGroupDetails;

import java.util.List;
import java.util.Map;

public interface CcaAuditService {

    String getInfoByNewStatus(CcaStatus status, CCAMasterVO ccaMaster);

    String getUpdatedInfo(Map<String, List<ChangeGroupDetails>> changeGroupDetails, CCAMasterVO ccaMaster);

    long countUpdatedChargeChangeGroupDetails(Map<String, List<ChangeGroupDetails>> changeGroupDetails);
}
