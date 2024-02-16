package com.ibsplc.neoicargo.tracking.events.listner;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanDeletedEvent;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEventMapper;
import com.ibsplc.neoicargo.tracking.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("shipmentMilestonePlanDeletedEventListener")
@EnableBinding(QmsDomainEventsSink.class)
@RequiredArgsConstructor
public class ShipmentMilestonePlanDeletedEventListener {

    private final TrackingService trackingService;
    private final TrackingEventMapper trackingEventMapper;
    private final ContextUtil contextUtil;

    @StreamListener(target = QmsDomainEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='ShipmentMilestonePlanDeletedEvent' && headers['Event-Source']=='ICARGO'")
    public void handleShipmentMilestonePlanDeletedEvent(@Payload ShipmentMilestonePlanDeletedEvent event) {
        log.info("handleShipmentMilestonePlanDeletedEvent : {}", event);
        var shipmentMilestonePlanDeletedVO = trackingEventMapper.constructShipmentMilestonePlanDeletedVO(event,contextUtil);
        trackingService.deleteShipmentMilestonePlans(shipmentMilestonePlanDeletedVO);
    }
}