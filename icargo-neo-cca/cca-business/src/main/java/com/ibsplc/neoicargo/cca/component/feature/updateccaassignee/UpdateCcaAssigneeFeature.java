package com.ibsplc.neoicargo.cca.component.feature.updateccaassignee;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UpdateCcaAssigneeFeature {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;

    public CcaValidationData perform(CcaAssigneeData ccaAssigneeData, String companyCode) throws CcaBusinessException {
        log.info("Invoked UpdateCcaMasterAttachments feature");
        ccaDao.updateCcaMasterAssignee(ccaAssigneeData, companyCode);
        return ccaMasterMapper.constructCcaValidationData(
                ccaAssigneeData,
                ccaAssigneeData.getCcaReferenceNumber() + " assignee is successfully updated."
        );
    }
}
