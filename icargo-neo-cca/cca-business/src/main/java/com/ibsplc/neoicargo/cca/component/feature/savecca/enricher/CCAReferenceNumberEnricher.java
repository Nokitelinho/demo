package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.util.CcaParameterUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.Criterion;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.CriterionBuilder;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyCondition;
import com.ibsplc.neoicargo.framework.tenant.jpa.keygen.KeyUtils;
import com.ibsplc.neoicargo.masters.ParameterType;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.AUTO_CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SYSTEM_PARAMETER_INTERNAL_CCA_SEQUENCE_REQUIRED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.UTC;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component("ccaReferenceNumberEnricher")
@AllArgsConstructor
public class CCAReferenceNumberEnricher extends Enricher<CCAMasterVO> {

    private final KeyUtils keyUtils;
    private final CcaParameterUtil awbParameterUtil;
    private final AirlineWebComponent airlineWebComponent;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("CCAReferenceNumberEnricher invoked");
        final var airlineModel = airlineWebComponent.validateNumericCode(ccaMasterVO.getShipmentPrefix());
        final var ccaReferenceNumber = ccaMasterVO.getCcaReferenceNumber();
        ccaMasterVO.setDocumentOwnerId(airlineModel.getAirlineIdentifier());
        if (isBlank(ccaReferenceNumber)) {
            final var criterion =
                    generateCCAReferenceNumber(ccaMasterVO.getDocumentOwnerId(), ccaMasterVO.getCcaType());
            ccaMasterVO.setCcaReferenceNumber(keyUtils.getKey(criterion));
            ccaMasterVO.setCcaIssueDate(LocalDate.now());
            LocalDateTime time = LocalDateTime.now(ZoneId.of(UTC));
            ccaMasterVO.setCcaIssueDateTimeInUTC(time);
            if (!AUTO_CCA_SOURCE.equals(ccaMasterVO.getCcaSource()) && !CcaStatus.A.equals(ccaMasterVO.getCcaStatus())) {
                ccaMasterVO.setCcaStatus(CcaStatus.N);
            }
        }
    }

    Criterion generateCCAReferenceNumber(int documentOwnerIdr, String ccaType) {
        var prefix = "CCA";
        final var internalCCASequenceRequired =
                awbParameterUtil.getSystemParameter(SYSTEM_PARAMETER_INTERNAL_CCA_SEQUENCE_REQUIRED,
                ParameterType.SYSTEM_PARAMETER);
        if (FLAG_Y.equals(internalCCASequenceRequired)) {
            prefix = CCA_TYPE_INTERNAL.equals(ccaType) ? "CCI" : prefix;
        }
        return new CriterionBuilder()
                .withSequence("CCA_Reference_Number")
                .withPrefix(prefix)
                .withKeyCondition(getKeyCondition(documentOwnerIdr))
                .withNumberFormat("%06d")
                .build();
    }

    @NotNull
    private KeyCondition getKeyCondition(int documentOwnerIdr) {
        KeyCondition keyCondition = new KeyCondition();
        keyCondition.setKey("DocumentOwnerIDR");
        keyCondition.setValue(String.valueOf(documentOwnerIdr));
        return keyCondition;
    }

}
