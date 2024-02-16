package com.ibsplc.neoicargo.cca.component.feature.validatecass;

import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataResponse;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@FeatureConfigSource("cca/validatecass")
@AllArgsConstructor
public class ValidateCassIndicatorFeature extends AbstractFeature<CCAMasterVO> {

    private final AirportUtil airportUtil;

    @Override
    public CcaCassValidationDataResponse perform(CCAMasterVO ccaMasterVO) {
        log.info("ValidateCassIndicatorFeature invoked");
        final var ccaCassValidationData = new CcaCassValidationDataResponse();
        final var originalShipmentVO = ccaMasterVO.getOriginalShipmentVO();
        originalShipmentVO.getCcaCustomerDetails().stream()
                .filter(cusDetails -> CustomerType.O.equals(cusDetails.getCustomerType()))
                .forEach(ccaCustomerDetailVO -> {
                    final var cassIndicator = ccaCustomerDetailVO.getCassIndicator();
                    if (cassIndicator == null) {
                        ccaCassValidationData.setIsAgentCass(false);
                    } else if ("B".equals(cassIndicator)) {
                        ccaCassValidationData.setIsAgentCass(true);
                    } else {
                        final var originsAirportMap = airportUtil.validateAirportCodes(
                                Set.of(originalShipmentVO.getOrigin()));
                        final var destinationsAirportMap = airportUtil.validateAirportCodes(
                                Set.of(originalShipmentVO.getDestination()));
                        final var countryCodeForOriginAirportCode = originsAirportMap.get(
                                originalShipmentVO.getOrigin());
                        final var countryCodeForDestinationAirportCode = destinationsAirportMap.get(
                                originalShipmentVO.getDestination());
                        final var isAgentCass = airportUtil.determineAgentCass(
                                cassIndicator, countryCodeForOriginAirportCode, countryCodeForDestinationAirportCode);
                        ccaCassValidationData.setIsAgentCass(isAgentCass);
                    }
                });
        return ccaCassValidationData;
    }

}
