package com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CCAUpdateVoidedFeature {

    private final CcaDao ccaDao;

    public void perform(AwbVoidedEvent awbVoidedEvent) {
        log.info("Invoked CCA Update Voided Feature");
        ccaDao.updateExistingCCAForAwbVoidedEvent(awbVoidedEvent);
    }

}
