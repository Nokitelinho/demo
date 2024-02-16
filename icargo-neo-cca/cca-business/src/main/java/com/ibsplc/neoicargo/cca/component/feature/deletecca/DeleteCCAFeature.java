package com.ibsplc.neoicargo.cca.component.feature.deletecca;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.events.CcaEventsProducer;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.BulkActionData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilterList;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.GetCcaListMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfigurationBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_DELETED_TRANSACTION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_LISTENER_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.cca.util.TriggerPointUtil.getTriggerPoint;

@Slf4j
@Component
@AllArgsConstructor
public class DeleteCCAFeature {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;
    private final AuditUtils<AuditVO> auditUtils;
    private final CcaEventsProducer ccaEventsProducer;
    private final CcaEventMapper ccaEventMapper;

    public BulkActionData perform(CcaDataFilterList ccaDataFilterList) {
        log.info("Invoked DeleteCCA feature");

        var ccaMasters = ccaDao.getCCAList(ccaDataFilterList.getCcaDataFilters());

        Predicate<GetCcaListMasterVO> isCcaCanBeDeleted = ccaMaster -> ccaMaster.getCcaStatus().equals(CcaStatus.N);

        var toDelete = ccaMasters.stream()
                .filter(isCcaCanBeDeleted)
                .collect(Collectors.toList());
        var cantBeDeleted = ccaMasters.stream()
                .filter(ccaMaster -> !isCcaCanBeDeleted.test(ccaMaster))
                .collect(Collectors.toList());

        for (var getCcaListMasterVO : toDelete) {
            getCcaListMasterVO.setTriggerPoint(getTriggerPoint(ccaDataFilterList.getCcaScreenId()));
            performAudit(getCcaListMasterVO);
        }

        final var deletedCcaMasters = ccaDao.deleteCCA(
                toDelete.stream()
                        .map(GetCcaListMasterVO::getCcaSerialNumber)
                        .collect(Collectors.toList())
        );

        final var bulkActionData = new BulkActionData(
                ccaMasterMapper.constructBulkActionEdges(toDelete, " Deleted successfully"),
                cantBeDeleted.stream()
                        .map(ccaMasterVO ->
                                CcaErrors.constructErrorVO(
                                        CcaErrors.NEO_CCA_015.getErrorCode(),
                                        CcaErrors.NEO_CCA_015.getErrorMessage(),
                                        ErrorType.ERROR,
                                        new String[]{ccaMasterVO.getCcaReferenceNumber()}
                                )
                        )
                        .collect(Collectors.toList())
        );
        publishCcaDeleteEvent(deletedCcaMasters);
        return bulkActionData;
    }

    private void publishCcaDeleteEvent(Collection<CcaMaster> deletedCcaMasters) {
        deletedCcaMasters.forEach(ccaMaster -> {
            final var key = String.format("%s-%s", ccaMaster.getShipmentPrefix(), ccaMaster.getMasterDocumentNumber());
            log.info("DeleteCCAFeature.publishCcaDeleteEvent before calling ccaEventsProducer.publishEvent");
            final var ccaDeleteEvent = ccaEventMapper.constructCCADeleteEventFromCcaMaster(ccaMaster);
            ccaEventsProducer.publishEvent(key, ccaDeleteEvent);
            log.info("DeleteCCAFeature.publishCcaDeleteEvent after calling ccaEventsProducer.publishEvent");
        });
    }

    private void performAudit(GetCcaListMasterVO ccaMasterVO) {
        log.info("Start CCA Audit Logging for CCA deletion [{}]", ccaMasterVO.getBusinessId());
        ccaMasterVO.setCcaStatus(CcaStatus.D);
        auditUtils.performAudit(
                new AuditConfigurationBuilder()
                        .withBusinessObject(ccaMasterVO)
                        .withEventName(CCA_AUDIT_EVENT_NAME)
                        .withListener(CCA_AUDIT_EVENT_LISTENER_NAME)
                        .withtransaction(CCA_AUDIT_DELETED_TRANSACTION)
                        .build()
        );
    }

}
