package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.enricher;

import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import com.ibsplc.neoicargo.tracking.vo.TransitStationVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component("transitStationEnricher")
public class TransitStationEnricher extends Enricher<ShipmentDetailsVO> {

    @Override
    public void enrich(ShipmentDetailsVO shipmentDetailsVO) {
        var plans = shipmentDetailsVO.getPlans();
        var airports = plans.stream().map(ShipmentMilestonePlanVO::getAirportCode).filter(Strings::isNotEmpty)
                .distinct().collect(Collectors.toList());
        airports.remove(shipmentDetailsVO.getOriginAirportCode());
        airports.remove(shipmentDetailsVO.getDestinationAirportCode());

        var numberOfFlights = Math.toIntExact(plans.stream()
                .filter(plan -> Objects.nonNull(plan.getFlightDate()) && Strings.isNotEmpty(plan.getFlightNumber()))
                .map(plan -> Pair.of(plan.getFlightNumber(), plan.getFlightDate())).distinct().count());

        shipmentDetailsVO.setTransitStations(new TransitStationVO(numberOfFlights, airports));
    }
}
