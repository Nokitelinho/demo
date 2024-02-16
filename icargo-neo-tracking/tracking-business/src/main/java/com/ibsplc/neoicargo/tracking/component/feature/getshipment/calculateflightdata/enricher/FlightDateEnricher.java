package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.enricher;

import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.tracking.vo.FlightTimePostfixEnum.ACTUAL;
import static com.ibsplc.neoicargo.tracking.vo.FlightTimePostfixEnum.SCHEDULED;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.ARR;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DEP;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum.DONE;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum.TO_DO;

@Slf4j
@Component("flightDateEnricher")
public class FlightDateEnricher extends Enricher<ShipmentDetailsVO> {

    @Override
    public void enrich(ShipmentDetailsVO shipmentDetailsVO) {
        var mainAirports = List.of(shipmentDetailsVO.getOriginAirportCode(), shipmentDetailsVO.getDestinationAirportCode());
        var plans = shipmentDetailsVO.getPlans().stream().filter(plan -> mainAirports.contains(plan.getAirportCode())).collect(Collectors.toList());
        var events = shipmentDetailsVO.getEvents().stream().filter(plan -> mainAirports.contains(plan.getAirportCode())).collect(Collectors.toList());
        if (shipmentDetailsVO.getMilestones().get(1).getStatus().equals(TO_DO)) {
            shipmentDetailsVO.setDepartureTimePostfix(SCHEDULED);
            plans.stream().filter(plan -> plan.getMilestoneCode().equals(DEP))
                    .min(Comparator.comparing(ShipmentMilestonePlanVO::getMilestoneTimeUTC))
                    .ifPresent(plan -> shipmentDetailsVO.setDepartureTime(plan.getMilestoneTime()));
        } else {
            shipmentDetailsVO.setDepartureTimePostfix(ACTUAL);
            events.stream().filter(event -> event.getMilestoneCode().equals(DEP))
                    .min(Comparator.comparing(ShipmentMilestoneEventVO::getMilestoneTimeUTC))
                    .ifPresent(event -> shipmentDetailsVO.setDepartureTime(event.getMilestoneTime()));
        }

        if (shipmentDetailsVO.getMilestones().get(2).getStatus().equals(DONE)) {
            shipmentDetailsVO.setArrivalTimePostfix(ACTUAL);
            events.stream().filter(event -> event.getMilestoneCode().equals(ARR))
                    .max(Comparator.comparing(ShipmentMilestoneEventVO::getMilestoneTimeUTC))
                    .ifPresent(event -> shipmentDetailsVO.setArrivalTime(event.getMilestoneTime()));
        } else {
            shipmentDetailsVO.setArrivalTimePostfix(SCHEDULED);
            plans.stream().filter(plan -> plan.getMilestoneCode().equals(ARR))
                    .max(Comparator.comparing(ShipmentMilestonePlanVO::getMilestoneTimeUTC))
                    .ifPresent(plan -> shipmentDetailsVO.setArrivalTime(plan.getMilestoneTime()));
        }
    }
}
