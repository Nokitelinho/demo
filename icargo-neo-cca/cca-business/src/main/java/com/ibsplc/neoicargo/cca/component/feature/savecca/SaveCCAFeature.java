package com.ibsplc.neoicargo.cca.component.feature.savecca;

import com.ibsplc.neoicargo.cca.component.feature.savecca.persistor.CcaPersistor;
import com.ibsplc.neoicargo.cca.events.CcaEventsProducer;
import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_DEFAULT_TRANSACTION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_LISTENER_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.UPDATE_OPERATION;

@Slf4j
@Component
@FeatureConfigSource("cca/savecca")
@AllArgsConstructor
public class SaveCCAFeature extends AbstractFeature<CCAMasterVO> {

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
        if (UPDATE_OPERATION.equals(ccaMasterVO.getOperation())) {
            if (CcaStatus.A.equals(ccaMasterVO.getCcaStatus())) {
                log.info("SaveCCAFeature.publishCcaApprovedEvent before calling ccaEventsProducer.publishEvent");
                final var ccaApprovedEvent = ccaEventMapper.constructCCAApprovedEventFromCCAMasterVO(
                        ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
                ccaEventsProducer.publishEvent(key, ccaApprovedEvent);
                log.info("SaveCCAFeature.publishCcaApprovedEvent after calling ccaEventsProducer.publishEvent");
            } else {
                log.info("SaveCCAFeature.publishCcaUpdateEvent before calling ccaEventsProducer.publishEvent");
                final var ccaUpdateEvent = ccaEventMapper.constructCCAUpdateEventFromCCAMasterVO(
                        ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
                ccaEventsProducer.publishEvent(key, ccaUpdateEvent);
                log.info("SaveCCAFeature.publishCcaUpdateEvent after calling ccaEventsProducer.publishEvent");
            }
        } else {
            log.info("SaveCCAFeature.publishCcaCreateEvent before calling ccaEventsProducer.publishEvent");
            final var ccaCreateEvent = ccaEventMapper.constructCCACreateEventFromCCAMasterVO(
                    ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
            ccaEventsProducer.publishEvent(key, ccaCreateEvent);
            log.info("SaveCCAFeature.publishCcaCreateEvent after calling ccaEventsProducer.publishEvent");
            if (CcaStatus.A.equals(ccaMasterVO.getCcaStatus())) {
                log.info("SaveCCAFeature.publishCcaApprovedEvent before calling ccaEventsProducer.publishEvent");
                final var ccaApprovedEvent = ccaEventMapper.constructCCAApprovedEventFromCCAMasterVO(
                        ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
                ccaEventsProducer.publishEvent(key, ccaApprovedEvent);
                log.info("SaveCCAFeature.publishCcaApprovedEvent after calling ccaEventsProducer.publishEvent");
            }
        }
    }

    private void performAudit(CCAMasterVO ccaMasterVO) {
        log.info("Start CCA Audit Logging for CCA [{}]", ccaMasterVO.getBusinessId());
        auditUtils.performAudit(new AuditConfigurationBuilder()
                .withBusinessObject(ccaMasterVO)
                .withEventName(CCA_AUDIT_EVENT_NAME)
                .withListener(CCA_AUDIT_EVENT_LISTENER_NAME)
                .withtransaction(CCA_AUDIT_DEFAULT_TRANSACTION)
                .build()
        );
    }
}
