package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.mapper.CcaRateDetailsMapper;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import com.ibsplc.neoicargo.pricing.PricingWebAPI;
import com.ibsplc.neoicargo.pricing.modal.CalculateSpotRateResponse;
import com.ibsplc.neoicargo.pricing.modal.SpotRateRequest;
import com.ibsplc.neoicargo.pricing.modal.SpotRateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@RegisterJAXRSClient(clazz = PricingWebAPI.class, targetService = "pricing-service")
public class SpotRateEnricher extends Enricher<CCAMasterVO> {
    private final PricingWebAPI pricingWebAPI;
    private final CcaRateDetailsMapper ccaRateDetailsMapper;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("spotRateEnricher Invoked");
        final var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        if (CcaUtil.isNullOrEmpty(revisedShipmentVO.getSpotRateId())) {
            log.info("exiting SpotRateEnricher since no Spot rate ID exists");
            return;
        }

        SpotRateRequest spotRateRequest = ccaRateDetailsMapper.constructSpotRateRequest(revisedShipmentVO);
        final var possibleSpotRateResponse = getSpotRateResponse(spotRateRequest);
        possibleSpotRateResponse.ifPresent(spotRateResponse -> {
            boolean isSpotRateToBeAttached = spotRateResponse.isValidated();
            revisedShipmentVO.setApplySpotRateOnCommission("Y".equals(spotRateResponse.getCommisionAllowed()));
            if (isSpotRateToBeAttached) {
                CalculateSpotRateResponse response = pricingWebAPI.calculateSpotRate(spotRateRequest);

                if (!CcaUtil.isNullOrEmpty(response.getSpotRateId())
                        && CcaConstants.SPOT_RATE_APPROVED_STATUS.equals(response.getSpotRateStatus())) {
                    addSpotRateDetails(revisedShipmentVO, response);
                }
            }
        });
    }

    private Optional<SpotRateResponse> getSpotRateResponse(SpotRateRequest spotRateRequest) throws BusinessException {
            return Optional.ofNullable(pricingWebAPI.validateSpotRate(spotRateRequest));
    }

    private void addSpotRateDetails(CcaAwbVO revisedShipmentVO, CalculateSpotRateResponse response) {
        log.info("Populating spot rate");
        Optional.ofNullable(revisedShipmentVO.getAwbRates()).ifPresent(ccaRateDetailsVOS -> {
            ccaRateDetailsVOS.stream()
                    .filter(rateDetailsVO -> CcaConstants.MKT_TYPE.equals(rateDetailsVO.getRateType()))
                    .map(ccaRateDetailsVO -> ccaRateDetailsMapper.updateSpotRateFromPricing(response, ccaRateDetailsVO, revisedShipmentVO.getUnitOfMeasure()))
                    .findAny().orElseGet(() -> populateRate(revisedShipmentVO, response, CcaConstants.MKT_TYPE));
        });
        revisedShipmentVO.setSpotRateStatus(response.getSpotRateStatus());
        applySpotRateDetails(revisedShipmentVO, response);
    }

    private CcaRateDetailsVO populateRate(CcaAwbVO revisedShipmentVO, CalculateSpotRateResponse response, String rateType) {
        CcaRateDetailsVO awbRateDetailsVO = ccaRateDetailsMapper.updateSpotRateFromPricing(response, new CcaRateDetailsVO(), revisedShipmentVO.getUnitOfMeasure());
        awbRateDetailsVO.setRateType(rateType);
        awbRateDetailsVO.setCommodityCode(revisedShipmentVO.getRatingDetails().stream()
                .findFirst()
                .map(CcaRatingDetailVO::getCommodityCode)
                .orElse(null));
        revisedShipmentVO.getAwbRates().add(awbRateDetailsVO);
        return awbRateDetailsVO;
    }

    private void applySpotRateDetails(CcaAwbVO revisedShipmentVO, CalculateSpotRateResponse response) {
        revisedShipmentVO.setSpotRateId(response.getSpotRateId());
        revisedShipmentVO.setSpotCategory(response.getSpotCategory());
        revisedShipmentVO.setRequestedSpotValue(response.getSpotValue());
    }
}
