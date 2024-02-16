package com.ibsplc.neoicargo.tracking.events.listner;

import com.ibsplc.neoicargo.awb.events.listner.AwbEventsSink;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.events.ShipmentHistoryEvent;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEventMapper;
import com.ibsplc.neoicargo.tracking.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("shipmentMilestoneEventListener")
@EnableBinding(AwbEventsSink.class)
@RequiredArgsConstructor
public class ShipmentMilestoneEventListener {

    private final TrackingService trackingService;
    private final TrackingEventMapper trackingEventMapper;
    private final ContextUtil contextUtil;

    @StreamListener(target = AwbEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='ShipmentHistoryEvent' && headers['Event-Source']=='ICARGO'")
    public void handleShipmentMilestoneEvent(@Payload ShipmentHistoryEvent shipmentHistoryEvent) {
        log.info("handleShipmentHistoryEvent : {}", shipmentHistoryEvent.getShipmentKey());
        var shipmentMilestoneEventVO = trackingEventMapper.constructShipmentMilestoneEventVO(shipmentHistoryEvent, contextUtil);
        trackingService.saveShipmentMilestoneEvent(shipmentMilestoneEventVO);
    }
}
