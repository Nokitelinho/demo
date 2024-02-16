package com.ibsplc.neoicargo.cca.service;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditEntryType;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditFieldVO;
import com.ibsplc.neoicargo.framework.tenant.audit.ChangeGroupDetails;
import com.samskivert.mustache.Mustache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.APPROVED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AWB_CHARGE_DETAIL_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AWB_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_CC_TAX;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_COMMISSION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_NUMBER;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_OTHER_CHARGES;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_CC;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_FREIGHT;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_OTHER;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_PP;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PAYMENT_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_PP_TAX;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_STATUS;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_TAXES_AND_COMMISSION;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_TOTAL_TAX;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CHARGE_HEAD_CODE_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CHARGE_HEAD_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CHARGE_VALUE_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CREATED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.DELETED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.DUE_AGENT_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.DUE_CARRIER_FIELD;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.INITIATED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.OCDA;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.OCDC;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.PAYMENT_TYPE_REGEX;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.RECOMMENDED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.REJECTED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.UPDATED_TEMPLATE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_APPROVED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_DELETED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_INITIATED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_NEW;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECOMMENDED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_REJECTED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;

@Slf4j
@Component
@RequiredArgsConstructor
public class CcaAuditServiceImpl implements CcaAuditService {

    private final Mustache.Compiler mustacheCompiler;

    private final Map<String, Function<CCAMasterVO, String>> infoByNewStatus = Map.of(
            CCA_NEW, this::getCreatedInfo,
            CCA_INITIATED, this::getInitiatedInfo,
            CCA_RECOMMENDED, this::getRecommendedInfo,
            CCA_APPROVED, this::getApprovedInfo,
            CCA_REJECTED, this::getRejectedInfo,
            CCA_DELETED, this::getDeletedInfo
    );

    private String getCreatedInfo(CCAMasterVO ccaMaster) {
        return mustacheCompiler.loadTemplate(CREATED_TEMPLATE).execute(getChangedStatusContext(ccaMaster));
    }

    private String getInitiatedInfo(CCAMasterVO ccaMaster) {
        return mustacheCompiler.loadTemplate(INITIATED_TEMPLATE).execute(getChangedStatusContext(ccaMaster));
    }

    private String getRecommendedInfo(CCAMasterVO ccaMaster) {
        return mustacheCompiler.loadTemplate(RECOMMENDED_TEMPLATE).execute(getChangedStatusContext(ccaMaster));
    }

    private String getApprovedInfo(CCAMasterVO ccaMaster) {
        return mustacheCompiler.loadTemplate(APPROVED_TEMPLATE).execute(getChangedStatusContext(ccaMaster));
    }

    private String getRejectedInfo(CCAMasterVO ccaMaster) {
        return mustacheCompiler.loadTemplate(REJECTED_TEMPLATE).execute(getChangedStatusContext(ccaMaster));
    }

    private String getDeletedInfo(CCAMasterVO ccaMaster) {
        return mustacheCompiler.loadTemplate(DELETED_TEMPLATE).execute(getChangedStatusContext(ccaMaster));
    }

    @Override
    public String getInfoByNewStatus(CcaStatus status, CCAMasterVO ccaMaster) {
        log.debug("Create additional info for [{}] CCA [{}]",
                ccaMaster.getCcaStatus().getDescriptionInfo(), ccaMaster.getBusinessId());

        return Optional.ofNullable(infoByNewStatus.get(status.name()))
                .map(function -> function.apply(ccaMaster))
                .orElse("");
    }

    private Map<String, Object> getChangedStatusContext(CCAMasterVO ccaMasterVO) {
        var context = new HashMap<String, Object>();
        context.put(CCA_NUMBER, ccaMasterVO.getBusinessId());
        context.put(CCA_TYPE, CcaUtil.getCcaTypeDescription(ccaMasterVO.getCcaType()));
        context.put(CCA_STATUS, ccaMasterVO.getCcaStatus().getDescriptionInfo());
        context.put(CCA_SOURCE, ccaMasterVO.getCcaSource() == null ? "" : ccaMasterVO.getCcaSource());

        return context;
    }

    @Override
    public String getUpdatedInfo(Map<String, List<ChangeGroupDetails>> changeGroupDetails, CCAMasterVO ccaMaster) {
        log.debug("Create additional info for Updated CCA [{}]", ccaMaster.getBusinessId());

        return mustacheCompiler
                .loadTemplate(UPDATED_TEMPLATE)
                .execute(getUpdatedContext(changeGroupDetails, ccaMaster));
    }

    private Map<String, Object> getUpdatedContext(Map<String, List<ChangeGroupDetails>> changeGroupDetails,
                                                  CCAMasterVO ccaMasterVO) {
        var context = new HashMap<String, Object>();
        context.put(CCA_NUMBER, ccaMasterVO.getBusinessId());
        context.put(CCA_TYPE, CcaUtil.getCcaTypeDescription(ccaMasterVO.getCcaType()));
        context.put(CCA_STATUS, ccaMasterVO.getCcaStatus().getDescriptionInfo());
        context.put(CCA_SOURCE, ccaMasterVO.getCcaSource() == null ? "" : ccaMasterVO.getCcaSource());
        addToContextUpdatedFieldsFromCcaAwb(context, changeGroupDetails);
        addOtherCharges(context, changeGroupDetails);
        addPaymentTypeDetails(context, changeGroupDetails);

        if (containsOtherCharges(context)) {
            context.put(CCA_OTHER_CHARGES, true);
        }

        if (containsTaxesOrCommission(context)) {
            context.put(CCA_TAXES_AND_COMMISSION, true);
        }

        return context;
    }

    private void addPaymentTypeDetails(HashMap<String, Object> context,
                                       Map<String, List<ChangeGroupDetails>> changeGroupDetails) {
        if (containsPaymentType(context)) {
            var payTypeObject = context.get(CCA_PAYMENT_TYPE);
            try {
                var oldPaymentType = getOldPaymentTypeValue(changeGroupDetails);
                var newPaymentType = String.valueOf(payTypeObject);
                validatePaymentType(newPaymentType);
                context.remove(CCA_PAYMENT_TYPE);
                var oldFreightPaymentAbbreviation = oldPaymentType.substring(0, 1);
                var newFreightPaymentAbbreviation = newPaymentType.substring(0, 1);
                if (!oldFreightPaymentAbbreviation.equals(newFreightPaymentAbbreviation)) {
                    context.put(CCA_PAYMENT_FREIGHT, getPaymentTypeByAbbreviation(newFreightPaymentAbbreviation));
                }
                var oldOtherPaymentAbbreviation = oldPaymentType.substring(1);
                var newOtherPaymentAbbreviation = newPaymentType.substring(1);
                if (!oldOtherPaymentAbbreviation.equals(newOtherPaymentAbbreviation)) {
                    context.put(CCA_PAYMENT_OTHER, getPaymentTypeByAbbreviation(newOtherPaymentAbbreviation));
                }
            } catch (RuntimeException e) {
                log.warn("Cannot audit the Payment Type: [{}]. Cause: ", payTypeObject, e);
            }
        }
    }

    private String getOldPaymentTypeValue(Map<String, List<ChangeGroupDetails>> changeGroupDetails) {
        return changeGroupDetails.getOrDefault(CCA_AWB_GROUP, List.of())
                .stream()
                .map(ChangeGroupDetails::getFields)
                .flatMap(Collection::stream)
                .filter(fieldVO -> CCA_PAYMENT_TYPE.equals(fieldVO.getField()))
                .map(AuditFieldVO::getOldValue)
                .map(String::valueOf)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Payment type is absent in changed fields"));
    }

    private String getPaymentTypeByAbbreviation(String abbreviation) {
        var types = Map.of(
                "C", CCA_PAYMENT_CC,
                "P", CCA_PAYMENT_PP
        );

        return types.get(abbreviation);
    }

    private boolean containsPaymentType(HashMap<String, Object> context) {
        return context.containsKey(CCA_PAYMENT_TYPE);
    }

    private void validatePaymentType(String payType) {
        if (!payType.matches(PAYMENT_TYPE_REGEX)) {
            throw new IllegalArgumentException("Incorrect format od PaymentType");
        }
    }

    private boolean containsOtherCharges(HashMap<String, Object> context) {
        return context.containsKey(OCDA) || context.containsKey(OCDC);
    }

    private boolean containsTaxesOrCommission(HashMap<String, Object> context) {
        return context.containsKey(CCA_COMMISSION)
                || context.containsKey(CCA_TOTAL_TAX)
                || context.containsKey(CCA_PP_TAX)
                || context.containsKey(CCA_CC_TAX);
    }

    private void addToContextUpdatedFieldsFromCcaAwb(HashMap<String, Object> context,
                                                     Map<String, List<ChangeGroupDetails>> changeGroupDetails) {
        Optional.ofNullable(changeGroupDetails.get(CCA_AWB_GROUP))
                .stream()
                .flatMap(Collection::stream)
                .filter(this::isNotDeleted)
                .map(ChangeGroupDetails::getFields)
                .flatMap(Collection::stream)
                .filter(AuditFieldVO::isFieldModified)
                .forEach(auditField -> context.put(auditField.getField(), auditField.getNewValue()));
    }

    private void addOtherCharges(@NotNull HashMap<String, Object> context,
                                 @NotNull Map<String, List<ChangeGroupDetails>> changeGroupDetails) {
        getUpdatedChargeChangeGroupDetails(changeGroupDetails)
                .stream()
                .collect(Collectors.groupingBy(this::getChargeType))
                .forEach((chargeType, changeDetailsOfChargeType) -> {
                    var updatedCharges = changeDetailsOfChargeType.stream()
                            .collect(Collectors.toMap(this::getChargeHead, this::getChargeValue));
                    context.put(chargeType, updatedCharges);
                });
    }

    private List<ChangeGroupDetails> getUpdatedChargeChangeGroupDetails(Map<String, List<ChangeGroupDetails>> details) {
        var detailsGroupedByAction = Optional.ofNullable(details.get(CCA_AWB_CHARGE_DETAIL_GROUP))
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(ChangeGroupDetails::getActionType));
        var createdChargeDetails = detailsGroupedByAction.getOrDefault(AuditEntryType.CREATED, new ArrayList<>());
        var deletedChargeDetails = detailsGroupedByAction.getOrDefault(AuditEntryType.DELETED, new ArrayList<>());
        var updatedChargeDetails = detailsGroupedByAction.getOrDefault(AuditEntryType.UPDATED, new ArrayList<>());
        createdChargeDetails.stream()
                .filter(createdCharge -> notContains(deletedChargeDetails, createdCharge))
                .forEach(updatedChargeDetails::add);
        return updatedChargeDetails;
    }

    private boolean notContains(List<ChangeGroupDetails> deletedDetailsList, ChangeGroupDetails createdDetails) {
        return deletedDetailsList.stream()
                .noneMatch(deletedDetails ->
                        deletedDetails.getId().equals(createdDetails.getId())
                        && haveTheSameFields(deletedDetails, createdDetails)
                );
    }

    private boolean haveTheSameFields(ChangeGroupDetails deletedDetail, ChangeGroupDetails createdDetail) {
        var deletedDetailFields = deletedDetail.getFields()
                .stream()
                .collect(Collectors.toMap(AuditFieldVO::getField, Function.identity()));
        var createdDetailFields = createdDetail.getFields()
                .stream()
                .collect(Collectors.toMap(AuditFieldVO::getField, Function.identity()));

        if (containDifferentFields(deletedDetailFields, createdDetailFields)) {
            return false;
        }

        return deletedDetailFields.entrySet()
                .stream()
                .allMatch(deletedDetailEntry -> {
                    var createdAuditField = createdDetailFields.get(deletedDetailEntry.getKey());
                    return createdAuditField != null
                            && Objects.equals(
                                    createdAuditField.getNewValue(), deletedDetailEntry.getValue().getOldValue());
                });
    }

    private boolean containDifferentFields(Map<String, AuditFieldVO> deletedDetailFields,
                                           Map<String, AuditFieldVO> createdDetailFields) {
        return !deletedDetailFields.keySet().equals(createdDetailFields.keySet());
    }

    private String getChargeHead(@NotNull ChangeGroupDetails changeDetails) {
        var chargeHead = changeDetails.getFields()
                .stream()
                .filter(field -> CHARGE_HEAD_FIELD.equals(field.getField()))
                .findFirst()
                .map(AuditFieldVO::getNewValue)
                .map(String::valueOf)
                .orElse("");

        var chargeHeadCode = changeDetails.getFields()
                .stream()
                .filter(field -> CHARGE_HEAD_CODE_FIELD.equals(field.getField()))
                .findFirst()
                .map(AuditFieldVO::getNewValue)
                .map(String::valueOf)
                .orElse("");

        return chargeHead + " (" + chargeHeadCode + ")";
    }

    private String getChargeValue(@NotNull ChangeGroupDetails changeDetails) {
        return changeDetails.getFields()
                .stream()
                .filter(field -> CHARGE_VALUE_FIELD.equals(field.getField()))
                .findFirst()
                .map(AuditFieldVO::getNewValue)
                .map(String::valueOf)
                .orElse("");
    }

    private String getChargeType(ChangeGroupDetails chargeGroup) {
        boolean isCarrierCharge = chargeGroup.getFields()
                .stream()
                .anyMatch(this::isCarrierCharge);
        if (isCarrierCharge) {
            return OCDC;
        } else {
            boolean isAgentCharge = chargeGroup.getFields()
                    .stream()
                    .anyMatch(this::isAgentCharge);
            if (isAgentCharge) {
                return OCDA;
            }

            return "";
        }
    }

    private boolean isCarrierCharge(AuditFieldVO field) {
        return DUE_CARRIER_FIELD.equals(field.getField()) && FLAG_Y.equals(field.getNewValue());
    }

    private boolean isAgentCharge(AuditFieldVO field) {
        return DUE_AGENT_FIELD.equals(field.getField()) && FLAG_Y.equals(field.getNewValue());
    }

    private boolean isNotDeleted(ChangeGroupDetails changeGroupDetails) {
        return changeGroupDetails.getActionType() != AuditEntryType.DELETED;
    }

    @Override
    public long countUpdatedChargeChangeGroupDetails(Map<String, List<ChangeGroupDetails>> changeGroupDetails) {
        return getUpdatedChargeChangeGroupDetails(changeGroupDetails).size();
    }
}