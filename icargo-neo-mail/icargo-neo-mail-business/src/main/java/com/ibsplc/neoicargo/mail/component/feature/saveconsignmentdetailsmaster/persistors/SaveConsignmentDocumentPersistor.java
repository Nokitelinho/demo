package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentdetailsmaster.persistors;

import com.ibsplc.neoicargo.mail.component.ConsignmentDocument;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class SaveConsignmentDocumentPersistor {
    private final String CLASS_NAME = "SaveConsignmentDocumentPersistor";

    public void persist(ConsignmentDocumentVO consignmentDocumentVO) {
        log.debug(CLASS_NAME + " : " + "persist" + " Entering");

        if (Objects.nonNull(consignmentDocumentVO)) {
            var consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
            if ("SM".equals(consignmentDocumentVO.getSecurityReasonCode())) {
                consignmentDocument.setConsignmentIssuerName(consignmentDocumentVO.getConsignmentIssuerName());
            }
            if (Objects.nonNull(consignmentDocumentVO.getSecurityStatusCode())) {
                consignmentDocument.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
            }
            if (Objects.nonNull(consignmentDocumentVO.getAdditionalSecurityInfo())){
                consignmentDocument.setAdditionalSecurityInfo(consignmentDocumentVO.getAdditionalSecurityInfo());
            }
        }
    }
}
