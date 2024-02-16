package com.ibsplc.neoicargo.tracking.component.feature.getshipmentactivities;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.AuthorizedService;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.component.feature.getmilestones.GetMilestonesFeature;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityFlightVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetShipmentActivitiesFeature {

    private final TrackingDAO trackingDAO;
    private final AwbDAO awbDAO;
    private final TrackingEntityMapper entityMapper;
    private final TrackingMapper trackingMapper;
    private final AuthorizedService authService;
    private final ContextUtil contextUtil;
    private final GetMilestonesFeature getMilestonesFeature;

    private static final List<MilestoneCodeEnum> AIRPORT_LEVEL_MILESTONES = List.of(MilestoneCodeEnum.CPT, MilestoneCodeEnum.FOH, MilestoneCodeEnum.RCS);


    @SuppressWarnings("unchecked")
    public List<ShipmentActivityVO> perform(AwbRequestVO awbRequestVO) {
        log.info("Perform Get Shipment Activities Feature!");

        final var shipmentType = "A";
        var shipmentKey = new ShipmentKey(awbRequestVO.getShipmentPrefix(), awbRequestVO.getMasterDocumentNumber());

        var plans = entityMapper.constructShipmentMilestonePlansVO(
                trackingDAO.findPlansByShipmentKeys(List.of(shipmentKey.toString())));
        var flightMap = constructFlightAirports(plans);

        var awb = awbDAO.findAwbByShipmentKey(shipmentKey);
        var airportLevelFlightVO = awb
                .map(value -> new ShipmentActivityFlightVO(null, null, value.getOrigin(), value.getDestination()))
                .orElseGet(ShipmentActivityFlightVO::new);

        authService.authorizeFor(contextUtil.getTenant());
        var events = entityMapper.constructShipmentMilestoneEventsVO(
                trackingDAO.findEventsByShipmentKeysAndType(List.of(shipmentKey.toString()), shipmentType));

        return events.stream()
                .filter(event -> getMilestonesFeature.isActivityViewMilestone(event.getMilestoneCode().getLabel()))
                .sorted(Comparator.comparing(ShipmentMilestoneEventVO::getMilestoneTimeUTC).reversed())
                .map(event -> toShipmentActivityVO(event, flightMap, airportLevelFlightVO))
                .collect(Collectors.toList());
    }

    private ShipmentActivityVO toShipmentActivityVO(ShipmentMilestoneEventVO event, Map<String, ShipmentActivityFlightVO> flightMap, ShipmentActivityFlightVO airportLevelFlightVO) {
        var shipmentActivityVO = trackingMapper.constructShipmentActivityVO(event);

        shipmentActivityVO.setEvent(event.getMilestoneCode().getLabel());

        var flightNumber = event.getTransactionDetails() == null ? null :
                (String) ((Map) event.getTransactionDetails()).get("flightNumber");
        if (AIRPORT_LEVEL_MILESTONES.contains(event.getMilestoneCode())) {
            shipmentActivityVO.setFlightData(airportLevelFlightVO);
        } else {
            shipmentActivityVO.setFlightData(flightMap.getOrDefault(flightNumber, getTransshipmentFlight(event)));
        }

        if (event.getMilestoneCode().equals(MilestoneCodeEnum.DIS)) {
            shipmentActivityVO.setReason((String) ((Map) event.getTransactionDetails()).get("reasonDescription"));
        }

        if (event.getMilestoneCode().equals(MilestoneCodeEnum.RCT) || event.getMilestoneCode().equals(MilestoneCodeEnum.TFD)) {
            shipmentActivityVO.setFromCarrier((String) ((Map) event.getTransactionDetails()).get("fromCarrier"));
            shipmentActivityVO.setToCarrier((String) ((Map) event.getTransactionDetails()).get("flightCarrierCode"));
        }

        return shipmentActivityVO;
    }

    private Map<String, ShipmentActivityFlightVO> constructFlightAirports(List<ShipmentMilestonePlanVO> plans) {

        Map<String, ShipmentActivityFlightVO> flightsToAirports = new HashMap<>();

        plans.forEach(plan -> {
            if (plan.getFlightNumber() != null) {
                flightsToAirports.putIfAbsent(plan.getFlightNumber(), new ShipmentActivityFlightVO(plan.getFlightCarrierCode(), plan.getFlightNumber(), null, null));
                if (plan.getMilestoneCode().equals(MilestoneCodeEnum.DEP)) {
                    flightsToAirports.get(plan.getFlightNumber()).setOriginAirportCode(plan.getAirportCode());
                }
                if (plan.getMilestoneCode().equals(MilestoneCodeEnum.ARR)) {
                    flightsToAirports.get(plan.getFlightNumber()).setDestinationAirportCode(plan.getAirportCode());
                }
            }
        });

        return flightsToAirports;
    }

    private ShipmentActivityFlightVO getTransshipmentFlight(ShipmentMilestoneEventVO event) {
        var flightNumber = event.getTransactionDetails() == null ? null :
            (String) ((Map) event.getTransactionDetails()).get("flightNumber");
        var flightCarrierCode = event.getTransactionDetails() == null ? null :
            (String) ((Map) event.getTransactionDetails()).get("flightCarrierCode");
        var origin =event.getTransactionDetails() == null ? null :
            (String) ((Map) event.getTransactionDetails()).get("origin");
        var destination =event.getTransactionDetails() == null ? null :
            (String) ((Map) event.getTransactionDetails()).get("pou");
       return new ShipmentActivityFlightVO(flightCarrierCode, flightNumber, origin, destination);
    }

}
