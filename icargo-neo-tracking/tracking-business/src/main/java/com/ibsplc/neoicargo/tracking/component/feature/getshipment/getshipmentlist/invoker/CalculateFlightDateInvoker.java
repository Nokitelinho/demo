/**
 *
 */
package com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist.invoker;

import com.ibsplc.neoicargo.framework.orchestration.Invoker;
import com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.CalculateFlightDataFeature;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("calculateFlightDateInvoker")
@RequiredArgsConstructor
public class CalculateFlightDateInvoker extends Invoker<ShipmentDetailsVO> {

    private final CalculateFlightDataFeature calculateFlightDataFeature;

    @Override
    public void invoke(ShipmentDetailsVO shipmentDetailsVO) {
        log.info("Entering CalculateFlightDateInvoker");
        calculateFlightDataFeature.execute(shipmentDetailsVO);
    }
}
