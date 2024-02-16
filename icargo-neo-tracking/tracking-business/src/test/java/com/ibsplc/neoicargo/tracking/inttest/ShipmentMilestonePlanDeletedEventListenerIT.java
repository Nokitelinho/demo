package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

import java.util.List;

public class ShipmentMilestonePlanDeletedEventListenerIT extends TrackingAPIBase {

    @Autowired
    private StubTrigger trigger;
    @Autowired
    private TrackingDAO trackingDAO;

    @Test
    public void shouldListenShipmentMilestonePlanDeletedEvent() throws JSONException {
        //when
        trigger.trigger("sendShipmentMilestonePlanDeletedEvent");
        //then
        List<ShipmentMilestonePlan> plansByShipmentKeys = trackingDAO.findPlansByShipmentKeys(List.of("134-55550000"));
        Assertions.assertTrue(plansByShipmentKeys.isEmpty());
    }
}
