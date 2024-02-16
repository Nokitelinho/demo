package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("calculateFlightDataFeature")
@RequiredArgsConstructor
@FeatureConfigSource("feature/shipment/calculateflightdata")
public class CalculateFlightDataFeature extends AbstractFeature<ShipmentDetailsVO> {

    @SuppressWarnings("unchecked")
    @Override
    public ShipmentDetailsVO perform(ShipmentDetailsVO shipmentDetailsVO) {
       log.info("Entering Calculate Flight Data Feature");
       return shipmentDetailsVO;
    }

}
