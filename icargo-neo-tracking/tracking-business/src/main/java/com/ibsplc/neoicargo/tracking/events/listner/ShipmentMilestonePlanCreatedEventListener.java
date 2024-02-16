/**
 *
 */
package com.ibsplc.neoicargo.tracking.events.listner;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanCreatedEvent;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEventMapper;
import com.ibsplc.neoicargo.tracking.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("shipmentMilestonePlanCreatedEventListener")
@EnableBinding(QmsDomainEventsSink.class)
@RequiredArgsConstructor
public class ShipmentMilestonePlanCreatedEventListener {

    private final TrackingEventMapper trackingEventMapper;
    private final TrackingService trackingService;
    private final ContextUtil contextUtil;

    @StreamListener(target = QmsDomainEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='ShipmentMilestonePlanCreatedEvent' && headers['Event-Source']=='ICARGO'")
    public void handleShipmentMilestonePlanCreatedEvent(@Payload ShipmentMilestonePlanCreatedEvent event) {
        log.info("handleShipmentMilestonePlanCreatedEvent : {}", event);
        var planCreatedVO = trackingEventMapper.constructShipmentMilestonePlanCreatedVO(event, contextUtil);
        trackingService.saveShipmentMilestonePlans(planCreatedVO);
    }

}

