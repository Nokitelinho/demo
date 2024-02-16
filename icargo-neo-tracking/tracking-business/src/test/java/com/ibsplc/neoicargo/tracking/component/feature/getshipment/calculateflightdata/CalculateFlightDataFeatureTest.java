package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata;

import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CalculateFlightDataFeatureTest {

    private CalculateFlightDataFeature feature = new CalculateFlightDataFeature();

    @Test
    void shouldCalculateFlightDate() {
        var shipmentDetailsVO = new ShipmentDetailsVO();
        assertEquals(shipmentDetailsVO, feature.perform(shipmentDetailsVO));
    }

}