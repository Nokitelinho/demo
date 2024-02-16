package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.cca.service.CcaAuditService;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEntryType;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditFieldVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditService;
import com.ibsplc.neoicargo.framework.tenant.audit.ChangeGroupDetails;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEventList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AUDIT_EVENT_LISTENER_NAME;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AWB_CHARGE_DETAIL_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_MASTER_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_STATUS;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Slf4j
@RequiredArgsConstructor
@Component(CCA_AUDIT_EVENT_LISTENER_NAME)
public class CcaAuditEventListener {

    private final ContextUtil contextUtil;
    private final CcaAuditService ccaAuditService;
    private final AuditService auditService;

    @SuppressWarnings("unchecked")
    @EventListener(condition = "#auditEventList.listeners.contains('" + CCA_AUDIT_EVENT_LISTENER_NAME + "')")
    public void processEvent(AuditEventList auditEventList) {
        log.info("AuditEventListener invoked  the [{}]", this.getClass().getSimpleName());
        var auditEvents = (List<AuditEvent>) auditEventList.getMessage();
        Optional.ofNullable(auditEvents).stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(auditEvent -> auditEvent.getListener().equals(CCA_AUDIT_EVENT_LISTENER_NAME))
                .forEach(this::updateAndSave);
    }

    private void updateAndSave(AuditEvent auditEvent) {
        setActionTimeStationIfNull(auditEvent);
        var ccaMasterVO = (CCAMasterVO) auditEvent.getMessage();
        auditEvent.setTriggerSource(ccaMasterVO.getTriggerPoint());
        if (isStatusChanged(auditEvent)) {
            var message = CcaStatus.N.equals(ccaMasterVO.getCcaStatus())
                    ? "Created"
                    : ccaMasterVO.getCcaStatus().getDescriptionInfo();
            auditEvent.setTransaction("CCA " + message);
            auditEvent.setAddInfo(ccaAuditService.getInfoByNewStatus(ccaMasterVO.getCcaStatus(), ccaMasterVO));
            saveAuditEvent(auditEvent);
        }

        if (isUpdated(auditEvent)) {
            auditEvent.setTransaction("CCA Updated");
            auditEvent.setAddInfo(ccaAuditService.getUpdatedInfo(auditEvent.getChangeGroupDetails(), ccaMasterVO));
            saveAuditEvent(auditEvent);
        }
    }

    // TODO: 5/27/2022 IAN-42052 - remove temporary fix after audit framework update
    private void setActionTimeStationIfNull(AuditEvent auditEvent) {
        if (auditEvent.getActionDateTimeStation() == null) {
            var stationTimezone = ZoneId.of(
                    this.contextUtil.callerLoginProfile().getAdditionalClaims().get("user_timezone").toString());
            auditEvent.setActionDateTimeStation(LocalDateTime.now(stationTimezone));
        }
    }

    private boolean isStatusChanged(AuditEvent auditEvent) {
        return Optional.ofNullable(auditEvent.getChangeGroupDetails())
                .map(groupDetails -> groupDetails.get(CCA_MASTER_GROUP))
                .stream()
                .flatMap(Collection::stream)
                .findFirst()
                .map(ChangeGroupDetails::getFields)
                .stream()
                .flatMap(Collection::stream)
                .filter(this::isCcaStatusField)
                .findFirst()
                .map(this::isValueChanged)
                .orElse(false);
    }

    private boolean isCcaStatusField(AuditFieldVO field) {
        return CCA_STATUS.equalsIgnoreCase(field.getField());
    }

    private boolean isValueChanged(AuditFieldVO auditField) {
        return auditField != null && !Objects.equals(auditField.getOldValue(), auditField.getNewValue());
    }

    private boolean isUpdated(AuditEvent auditEvent) {
        var changeGroupDetails = auditEvent.getChangeGroupDetails();

        if (isNullOrEmpty(changeGroupDetails) || isCcaDeleteEvent(changeGroupDetails)) {
            return false;
        }

        long numberOfChangedFieldsExceptOtherChargesAndStatusField =
                changeGroupDetails.entrySet()
                        .stream()
                        .filter(entry -> isNotChargeGroup(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .flatMap(Collection::stream)
                        .map(ChangeGroupDetails::getFields)
                        .flatMap(Collection::stream)
                        .filter(field -> field.isFieldModified() && !isCcaStatusField(field))
                        .count();

        long updatedChargeFieldsCount = ccaAuditService.countUpdatedChargeChangeGroupDetails(changeGroupDetails);

        return numberOfChangedFieldsExceptOtherChargesAndStatusField + updatedChargeFieldsCount > 0;
    }

    private boolean isNotChargeGroup(String changeGroup) {
        return !CCA_AWB_CHARGE_DETAIL_GROUP.equals(changeGroup);
    }

    private boolean isCcaDeleteEvent(Map<String, List<ChangeGroupDetails>> changeGroupDetails){
        return Optional.ofNullable(changeGroupDetails.get(CCA_MASTER_GROUP))
                .map(ccaMasterDetails ->
                        ccaMasterDetails.stream()
                                .allMatch(details -> details.getActionType().equals(AuditEntryType.DELETED))
                )
                .orElse(false);
    }

    private void saveAuditEvent(AuditEvent auditEvent) {
        try {
            auditService.saveAuditEvent(auditEvent);
        } catch (IOException | RuntimeException e) {
            log.warn("Cannot save audit event [{}] for [{}]", auditEvent.getEventName(), auditEvent.getEntityId(), e);
        }
    }
}
