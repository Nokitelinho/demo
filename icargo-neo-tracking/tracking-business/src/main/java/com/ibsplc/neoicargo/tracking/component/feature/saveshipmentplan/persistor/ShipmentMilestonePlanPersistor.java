package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.persistor;

import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("shipmentMilestonePlanPersistor")
@RequiredArgsConstructor
public class ShipmentMilestonePlanPersistor {

    private final TrackingEntityMapper awbEntityMapper;
    private final TrackingDAO trackingDao;

    public void persist(List<ShipmentMilestonePlanVO> shipmentMilestonePlanVOs) {
        var plans = awbEntityMapper.constructShipmentMilestonePlans(shipmentMilestonePlanVOs);
        trackingDao.saveShipmentMilestonePlans(plans);
    }
}
