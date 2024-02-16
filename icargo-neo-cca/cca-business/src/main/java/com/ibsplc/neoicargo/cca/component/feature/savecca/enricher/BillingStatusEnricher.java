package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditData;
import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditedDetails;
import com.ibsplc.neoicargo.cca.constants.InboundBillingStatus;
import com.ibsplc.neoicargo.cca.constants.OutboundBillingStatus;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillingStatusEnricher extends Enricher<CCAMasterVO> {

    @Value("${EBL_URL_NBRIDGE}")
    private String eblUrl;

    private final ServiceProxy<QualityAuditedDetails> serviceProxy;
    private final AirlineWebComponent airlineComponent;
    private final CcaMasterMapper ccaMasterMapper;
    private final ContextUtil contextUtil;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) {
        log.info("BillingStatusEnricher Invoked");
        if (ccaMasterVO.getRevisedShipmentVO() != null) {
            // All internal CCAs are Non - Billable
            if (CCA_TYPE_INTERNAL.equals(ccaMasterVO.getCcaType())) {
                ccaMasterVO.setExportBillingStatus(OutboundBillingStatus.NON_BILLABLE.getStatusValueInDb());
                ccaMasterVO.setImportBillingStatus(InboundBillingStatus.NON_BILLABLE.getStatusValueInDb());
            } else {
                final var url = eblUrl.concat("cra").concat("/").concat("getBillingStatus");
                try {
                    var qualityAuditedDetails = buildQualityAuditedDetails(ccaMasterVO);
                    log.info("BillingStatusEnricher before proxy call");
                    final var billingStatusResponse =
                            serviceProxy.dispatch(url, HttpMethod.POST, qualityAuditedDetails, QualityAuditData.class);
                    ccaMasterVO.setExportBillingStatus(billingStatusResponse.getExportBillingStatus());
                    ccaMasterVO.setImportBillingStatus(billingStatusResponse.getImportBillingStatus());

                    if (billingStatusResponse.getError() != null) {
                        billingStatusResponse.getError()
                                .forEach(error -> log.error("BillingStatusEnricher error {}:", error));
                    }
                    log.info("BillingStatusEnricher after proxy call");
                } catch (RuntimeException | BusinessException e) {
                    log.error("BillingStatusEnricher Exception:", e);
                    throw new SystemException(SystemException.UNEXPECTED_SERVER_ERROR, e);
                }
            }
        }
    }

    private QualityAuditedDetails buildQualityAuditedDetails(CCAMasterVO ccaMasterVO) throws BusinessException {
        var revisedAwb = ccaMasterVO.getRevisedShipmentVO();
        var qualityAuditedDetails =
                ccaMasterMapper.constructQualityAuditedDetailsFromCcaAwbVO(revisedAwb, ccaMasterVO);
        var airlineModel = airlineComponent.validateNumericCode(qualityAuditedDetails.getShipmentPrefix());
        qualityAuditedDetails.setDocumentNumber(airlineModel.getAirlineIdentifier());
        qualityAuditedDetails.setCompanyCode(contextUtil.callerLoginProfile().getCompanyCode());
        return qualityAuditedDetails;
    }
}
