package com.ibsplc.neoicargo.cca.component.feature.savecca;

import com.ibsplc.neoicargo.cca.component.feature.savecca.persistor.CcaPersistor;
import com.ibsplc.neoicargo.cca.events.CcaEventsProducer;
import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.AUTO_CCA_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.AUTO_CCA_AUDIT_TRANSACTION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_LISTENER_NAME;

@Slf4j
@Component
@FeatureConfigSource("cca/saveautocca")
@AllArgsConstructor
public class SaveAutoCCAFeature extends AbstractFeature<CCAMasterVO> {

    private final CcaPersistor ccaPersistor;
    private final CcaMasterMapper ccaMasterMapper;
    private final AuditUtils<AuditVO> auditUtils;
    private final CcaEventsProducer ccaEventsProducer;
    private final CcaEventMapper ccaEventMapper;

    @Override
    public CcaValidationData perform(CCAMasterVO ccaMasterVO) {
        log.info("Invoked SaveCCA feature");
        performAudit(ccaMasterVO);
        ccaPersistor.persist(ccaMasterVO);
        return ccaMasterMapper.ccaMasterVOToCcaValidationData(ccaMasterVO);
    }

    @Override
    public void postInvoke(CCAMasterVO ccaMasterVO) {
        final var key = String.format("%s-%s", ccaMasterVO.getShipmentPrefix(), ccaMasterVO.getMasterDocumentNumber());

        log.info("SaveAutoCCAFeature.publishCcaCreateEvent before calling ccaEventsProducer.publishEvent");
        final var ccaCreateEvent =
                ccaEventMapper.constructCCACreateEventFromCCAMasterVO(ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
        ccaEventsProducer.publishEvent(key, ccaCreateEvent);
        log.info("SaveAutoCCAFeature.publishCcaCreateEvent after calling ccaEventsProducer.publishEvent");

        log.info("SaveAutoCCAFeature.publishCcaApprovedEvent before calling ccaEventsProducer.publishEvent");
        final var ccaApprovedEvent =
                ccaEventMapper.constructCCAApprovedEventFromCCAMasterVO(ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
        ccaEventsProducer.publishEvent(key, ccaApprovedEvent);
        log.info("SaveAutoCCAFeature.publishCcaApprovedEvent after calling ccaEventsProducer.publishEvent");
    }

    private void performAudit(CCAMasterVO ccaMasterVO) {
        log.info("Start CCA Audit Logging for AUTO CCA [{}]", ccaMasterVO.getBusinessId());
        auditUtils.performAudit(new AuditConfigurationBuilder()
                .withBusinessObject(ccaMasterVO)
                .withEventName(AUTO_CCA_AUDIT_EVENT_NAME)
                .withListener(CCA_AUDIT_EVENT_LISTENER_NAME)
                .withtransaction(AUTO_CCA_AUDIT_TRANSACTION)
                .build()
        );
    }
}
