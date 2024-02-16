package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
@Profile("!test")
public class AwbDetailsEnricher extends Enricher<CCAMasterVO> {

    @Value("${QUALITY_AUDIT_URL}")
    private String qualityAuditUrl;

    private final RestTemplate restTemplate;
    private final ContextUtil contextUtil;
    private final CcaMasterMapper ccaMasterMapper;

    public AwbDetailsEnricher(RestTemplate restTemplate, ContextUtil contextUtil, CcaMasterMapper ccaMasterMapper) {
        this.restTemplate = restTemplate;
        this.contextUtil = contextUtil;
        this.ccaMasterMapper = ccaMasterMapper;
    }

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) {
        log.info("Invoked AwbDetailsEnricher");
        final var shipmentPrefix = ccaMasterVO.getShipmentPrefix();
        final var masterDocumentNumber = ccaMasterVO.getMasterDocumentNumber();
        final var response = getCcaMasterDataFromQualityAuditService(shipmentPrefix, masterDocumentNumber);
        final var ccaMasterData = response.getBody();
        if (ccaMasterData != null) {
            final var unitOfMeasure = ccaMasterData.getUnitOfMeasure();
            if (unitOfMeasure.getLength() == null) {
                // TODO should be replace by awbParameterUtil.getSystemParameter(), but it is not working right now
                unitOfMeasure.setLength("C");
            }
            final var masterVOFromQualityAudit =
                    ccaMasterMapper.constructCCAMasterVOFromCCAData(ccaMasterData, unitOfMeasure);
            ccaMasterVO.setOriginalShipmentVO(masterVOFromQualityAudit.getOriginalShipmentVO());
            ccaMasterMapper.updateCCAMasterVOFromCCAData(ccaMasterVO, ccaMasterData, ccaMasterVO.getUnitOfMeasure());
        } else {
            log.warn("Cca master data was not found for - {} - shipment prefix and - {} - master document number",
                    shipmentPrefix, masterDocumentNumber);
        }
    }

    private ResponseEntity<CCAMasterData> getCcaMasterDataFromQualityAuditService(String shipmentPrefix,
                                                                                  String masterDocumentNumber) {
        final var auditUrl = buildQualityAuditUrl(shipmentPrefix, masterDocumentNumber);
        ResponseEntity<CCAMasterData> response;
        try {
            response = restTemplate.exchange(auditUrl, GET, new HttpEntity<>(getHttpHeaders()), CCAMasterData.class);
        } catch (HttpClientErrorException e) {
            log.warn("Cannot receive data from Quality Audit, shipment prefix: [{}], master document number: [{}]",
                    shipmentPrefix, masterDocumentNumber, e);
            return ResponseEntity.noContent().build();
        }
        return response;
    }

    @NotNull
    private HttpHeaders getHttpHeaders() {
        final var headers = new HttpHeaders();
        headers.set("ICO-Authorization", contextUtil.callerLoginProfile().getId_token());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @NotNull
    private String buildQualityAuditUrl(String shipmentPrefix, String masterDocumentNumber) {
        final var companyCode = contextUtil.callerLoginProfile().getCompanyCode().toLowerCase();
        return qualityAuditUrl
                .concat(companyCode)
                .concat("/private/v1")
                .concat("/qualityaudit/shipment/getAWBDetailsForCreateCCA/")
                .concat(shipmentPrefix)
                .concat("/")
                .concat(masterDocumentNumber);
    }
}
