package com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist;

import com.ibsplc.neoicargo.awb.component.AwbComponent;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist.invoker.CalculateFlightDateInvoker;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Component("getShipmentListFeature")
@RequiredArgsConstructor
public class GetShipmentListFeature {

    private final TrackingDAO trackingDAO;
    private final AwbComponent awbComponent;
    private final TrackingMapper trackingMapper;
    private final TrackingEntityMapper trackingEntityMapper;
    private final CalculateFlightDateInvoker calculateFlightDateInvoker;

    public List<ShipmentDetailsVO> perform(List<AwbRequestVO> awbs) {
        var awbVOs = awbComponent.getAwbList(awbs);

        if (awbVOs.isEmpty()) {
            return new ArrayList<>();
        }

        var shipmentKeys = awbVOs.stream()
                .map(AwbVO::getBusinessId)
                .collect(Collectors.toList());

        var plansByShipment = trackingDAO.findPlansByShipmentKeys(shipmentKeys).stream()
                .map(trackingEntityMapper::constructShipmentMilestonePlanVO)
                .collect(groupingBy(ShipmentMilestonePlanVO::getShipmentKey));

        var eventsByShipment = trackingDAO.findEventsByShipmentKeysAndType(shipmentKeys, "A").stream()
                .map(trackingEntityMapper::constructShipmentMilestoneEventVO)
                .collect(groupingBy(ShipmentMilestoneEventVO::getShipmentKey));

        List<ShipmentDetailsVO> shipments = new ArrayList<>();
        awbVOs.stream()
                .map(trackingMapper::constructShipmentDetailsVO)
                .forEach(shipment -> {
                    shipment.setPlans(plansByShipment.getOrDefault(shipment.getShipmentKey(), emptyList()));
                    shipment.setEvents(eventsByShipment.getOrDefault(shipment.getShipmentKey(), emptyList()));
                    shipments.add(shipment);
                });

        shipments.forEach(calculateFlightDateInvoker::invoke);

        return shipments;
    }
}
