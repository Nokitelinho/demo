package com.ibsplc.neoicargo.tracking.dao;

import com.ibsplc.neoicargo.tracking.dao.entity.Milestone;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;

import java.util.List;

public interface TrackingDAO {

    List<ShipmentMilestonePlan> findPlansByShipmentKeys(List<String> shipmentKeys);

    List<ShipmentMilestoneEvent> findEventsByShipmentKeysAndType(List<String> shipmentKeys, String shipmentType);

    void saveShipmentMilestonePlans(List<ShipmentMilestonePlan> shipmentPlan);
    
    void deletePlanByShipmentKey(String shipmentKey);

    void deletePlan(ShipmentMilestonePlanDeletedVO event);

    void deleteEventByShipmentKey(String shipmentKey);

    void saveShipmentMilestoneEvent(ShipmentMilestoneEvent event);

    List<Milestone> findAllMilestones();

}
