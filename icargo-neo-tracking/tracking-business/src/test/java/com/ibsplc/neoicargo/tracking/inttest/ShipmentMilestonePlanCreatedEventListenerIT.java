package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.awb.dao.impl.repositories.AwbRepository;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ShipmentMilestonePlanCreatedEventListenerIT extends TrackingAPIBase {

    @Autowired
    private StubTrigger trigger;
    @Autowired
    private TrackingDAO trackingDAO;
    @Autowired
    private AwbRepository awbRepository;

    @Test
    public void shouldListenShipmentMilestonePlanCreatedAndSaveEvent() throws JSONException {
        //given
        trigger.trigger("sendShipmentMilestonePlanDeletedEvent");
        //when
        trigger.trigger("sendShipmentMilestonePlanCreatedEvent");
        //then
        List<ShipmentMilestonePlan> plansByShipmentKeys = trackingDAO.findPlansByShipmentKeys(List.of("176-55550000"));
        Assertions.assertFalse(plansByShipmentKeys.isEmpty());
    }

    @Test
    public void shouldListenShipmentMilestonePlanCreatedAndNotSaveEvent() throws JSONException {
        //given
        trigger.trigger("sendShipmentMilestonePlanDeletedEvent");
        awbRepository.deleteById(4L);
        //when
        trigger.trigger("sendShipmentMilestonePlanCreatedEvent");
        //then
        List<ShipmentMilestonePlan> plansByShipmentKeys = trackingDAO.findPlansByShipmentKeys(List.of("176-55550000"));
        assertTrue(plansByShipmentKeys.isEmpty());
    }
}
