package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditData;
import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditedDetails;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("ccaNetValueEnricher")
@RequiredArgsConstructor
public class CCANetValueEnricher extends Enricher<CCAMasterVO> {

    @Value("${EBL_URL_NBRIDGE}")
    private String eblUrl;

    private final ServiceProxy<QualityAuditedDetails> serviceProxy;
    private final AirlineWebComponent airlineComponent;
    private final CcaMasterMapper ccaMasterMapper;
    private final MoneyMapper moneyMapper;

    public void enrich(CCAMasterVO ccaMasterVO) {
        log.info("CCANetValueEnricher Invoked");
        var useOriginalAwbValues = Optional.ofNullable(
                FeatureContextUtilThreadArray
                        .getInstance()
                        .getFeatureContext()
                        .getContextMap()
                        .get(CcaConstants.USE_ORIGINAL_AWB_VALUES)
        )
                .map(r -> (boolean)r)
                .orElse(false);
        if ((useOriginalAwbValues && ccaMasterVO.getOriginalShipmentVO() == null)
                || (!useOriginalAwbValues && ccaMasterVO.getRevisedShipmentVO() == null) ) {
            return;
        }

        final var url = eblUrl.concat("cra").concat("/").concat("calculatedueairline");
        try {
            var qualityAuditedDetails = buildQualityAuditedDetails(ccaMasterVO, useOriginalAwbValues);
            log.info("ccaNetValueEnricher before proxy call");
            var netValuesResponse =
                    serviceProxy.dispatch(url, HttpMethod.POST, qualityAuditedDetails, QualityAuditData.class);
            ccaMasterVO.getRevisedShipmentVO().setNetValueExport(moneyMapper.moneyFromDouble(
                    netValuesResponse.getExportDueAirline(), ccaMasterVO.getUnitOfMeasure()));
            ccaMasterVO.getRevisedShipmentVO().setNetValueImport(moneyMapper.moneyFromDouble(
                    netValuesResponse.getImportDueAirline(), ccaMasterVO.getUnitOfMeasure()));
            log.info("CcaNetValueEnricher after proxy call");
        } catch (RuntimeException | BusinessException e) {
            log.error("CcaNetValueEnricher Exception:", e);
            throw new SystemException(SystemException.UNEXPECTED_SERVER_ERROR, e);
        }
    }

    private QualityAuditedDetails buildQualityAuditedDetails(CCAMasterVO ccaMasterVO,
                                                             boolean useOriginalAwbValues) throws BusinessException {
        var netValuesRequest = ccaMasterMapper.constructQualityAuditedDetailsFromCcaAwbVO(
                useOriginalAwbValues ? ccaMasterVO.getOriginalShipmentVO() : ccaMasterVO.getRevisedShipmentVO(),
                ccaMasterVO);
        var airlineModel = airlineComponent.validateNumericCode(netValuesRequest.getShipmentPrefix());
        netValuesRequest.setDocumentNumber(airlineModel.getAirlineIdentifier());
        return netValuesRequest;
    }

}
