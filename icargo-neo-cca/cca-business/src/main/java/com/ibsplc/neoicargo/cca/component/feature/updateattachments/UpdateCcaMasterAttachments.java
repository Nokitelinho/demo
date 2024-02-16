package com.ibsplc.neoicargo.cca.component.feature.updateattachments;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UpdateCcaMasterAttachments {

    private final CcaDao ccaDao;

    public void perform(AttachmentsData attachmentsData) {
        log.info("Invoked UpdateCcaMasterAttachments feature");
        ccaDao.updateCcaMasterAttachments(attachmentsData);
    }

}
