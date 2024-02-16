package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.AUTO_CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;

@Slf4j
@Component("awbDetailsEnricher")
@Profile("test")
public class MockAwbDetailsEnricher extends Enricher<CCAMasterVO> {

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) {
        log.info("Invoked MockAwbDetailsEnricher for test env");
        final var masterDocumentNumber = "66660000";
        final var shipmentPrefix = "176";

        final var orgShipmentDetailVO = getFullMockCcaAwbVO(shipmentPrefix, masterDocumentNumber, "O");
        final var revShipmentDetailVO = getFullMockCcaAwbVO(shipmentPrefix, masterDocumentNumber, "R");
        ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgShipmentDetailVO, revShipmentDetailVO));
        ccaMasterVO.setCcaStatus(CcaStatus.A);
        ccaMasterVO.setCcaSource(AUTO_CCA_SOURCE);
        ccaMasterVO.setCcaType(CCA_TYPE_ACTUAL);
        ccaMasterVO.setAutoCCAFlag(FLAG_Y);
        ccaMasterVO.setShipmentPrefix(shipmentPrefix);
        ccaMasterVO.setMasterDocumentNumber(masterDocumentNumber);
        ccaMasterVO.setCcaReason("VD");
    }
}
