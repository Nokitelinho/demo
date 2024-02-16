package com.ibsplc.neoicargo.cca.component.feature.authorizecca;

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

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_DEFAULT_TRANSACTION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_LISTENER_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_NAME;
import static com.ibsplc.neoicargo.cca.util.TriggerPointUtil.getTriggerPoint;

@Slf4j
@Component
@AllArgsConstructor
public class AuthorizeCCAFeature {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;
    private final AuditUtils<AuditVO> auditUtils;
    private final CcaEventsProducer ccaEventsProducer;
    private final CcaEventMapper ccaEventMapper;

    public BulkActionData perform(CcaDataFilterList filtersData) {
        log.info("Invoked AuthorizeCCA feature");

        var ccaMastersVO = ccaDao.getCCAList(filtersData.getCcaDataFilters());

        Predicate<GetCcaListMasterVO> isCcaCanBeAuthorized = ccaMaster -> ccaMaster.getCcaStatus().equals(CcaStatus.N);
        var toAuthorize = ccaMastersVO.stream()
                .filter(isCcaCanBeAuthorized)
                .collect(Collectors.toList());
        var cantBeAuthorized = ccaMastersVO.stream()
                .filter(ccaMaster -> !isCcaCanBeAuthorized.test(ccaMaster))
                .collect(Collectors.toList());

        toAuthorize.forEach(ccaMasterVO -> {
            ccaMasterVO.setCcaStatus(CcaStatus.A);
            ccaMasterVO.setTriggerPoint(getTriggerPoint(filtersData.getCcaScreenId()));
            performAudit(ccaMasterVO);
        });

        //Needed to trigger Audit
        var ccaMasters = ccaDao.findCcaMastersByCcaSerialNumbers(
                toAuthorize.stream()
                        .map(GetCcaListMasterVO::getCcaSerialNumber)
                        .collect(Collectors.toList())
        );
        ccaMasters.forEach(ccaMaster -> ccaMaster.setCcaStatus(CcaStatus.A));
        ccaDao.saveAllCCAs(ccaMasters);

        final var bulkActionData = new BulkActionData(
                ccaMasterMapper.constructBulkActionEdges(toAuthorize, " Authorized successfully"),
                cantBeAuthorized.stream()
                        .map(ccaMasterVO ->
                                CcaErrors.constructErrorVO(
                                        CcaErrors.NEO_CCA_016.getErrorCode(),
                                        CcaErrors.NEO_CCA_016.getErrorMessage(),
                                        ErrorType.ERROR,
                                        new String[]{ccaMasterVO.getCcaReferenceNumber()}
                                )
                        )
                        .collect(Collectors.toList())
        );
        publishCCAApprovedEvent(ccaMasters);
        return bulkActionData;
    }

    private void publishCCAApprovedEvent(Collection<CcaMaster> approvedCca) {
        approvedCca.forEach(ccaMaster -> {
            final var key = String.format("%s-%s", ccaMaster.getShipmentPrefix(), ccaMaster.getMasterDocumentNumber());
            log.info("AuthorizeCCAFeature.publishCCAApprovedEvent before calling ccaEventsProducer.publishEvent");
            final var ccaApprovedEvent = ccaEventMapper.constructCCAApprovedEventFromCcaMaster(ccaMaster);
            ccaEventsProducer.publishEvent(key, ccaApprovedEvent);
            log.info("AuthorizeCCAFeature.publishCCAApprovedEvent after calling ccaEventsProducer.publishEvent");
        });
    }

    private void performAudit(GetCcaListMasterVO ccaMasterVO) {
        log.info("Start CCA Audit Logging for CCA [{}]", ccaMasterVO.getBusinessId());
        auditUtils.performAudit(
                new AuditConfigurationBuilder()
                        .withBusinessObject(ccaMasterVO)
                        .withEventName(CCA_AUDIT_EVENT_NAME)
                        .withListener(CCA_AUDIT_EVENT_LISTENER_NAME)
                        .withtransaction(CCA_AUDIT_DEFAULT_TRANSACTION)
                        .build()
        );
    }
}
