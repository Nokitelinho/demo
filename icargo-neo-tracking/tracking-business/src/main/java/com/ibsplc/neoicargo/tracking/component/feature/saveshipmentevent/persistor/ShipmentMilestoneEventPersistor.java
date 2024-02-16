package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.persistor;

import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("shipmentMilestoneEventPersistor")
@RequiredArgsConstructor
public class ShipmentMilestoneEventPersistor {

    private final TrackingEntityMapper trackingEntityMapper;
    private final TrackingDAO trackingDao;

    public void persist(ShipmentMilestoneEventVO shipmentMilestoneEventVO) {
        var event = trackingEntityMapper.constructShipmentMilestoneEvent(shipmentMilestoneEventVO);
        trackingDao.saveShipmentMilestoneEvent(event);
    }
}
