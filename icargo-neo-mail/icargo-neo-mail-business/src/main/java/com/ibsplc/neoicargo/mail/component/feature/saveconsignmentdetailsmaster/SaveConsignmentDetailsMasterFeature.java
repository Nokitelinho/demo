package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentdetailsmaster;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.feature.saveconsignmentdetailsmaster.persistors.SaveConsignmentDocumentPersistor;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mail.operations.saveConsignmentDetailsMasterFeature")
@FeatureConfigSource("feature/saveconsignmentdetailsmaster")
public class SaveConsignmentDetailsMasterFeature extends AbstractFeature<ConsignmentDocumentVO> {
    public Void perform(ConsignmentDocumentVO consignmentDocumentVO) {
        new SaveConsignmentDocumentPersistor().persist(consignmentDocumentVO);
        return null;
    }
}