package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

import java.util.Collections;

public class AwbVoidedEventListenerIT extends TrackingAPIBase {

    @Autowired
    private StubTrigger trigger;
    @Autowired
    private TrackingDAO trackingDAO;
    @Autowired
    private AwbDAO awbDAO;

    @Test
    public void shouldListenAWBVoidedEvent() throws JSONException {
        //given
        trigger.trigger("triggerAWBCreatedEventForVoid");
        var shipmentPrefix = "176";
        var masterDocumentNumber = "11011066";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);

        //then
        var possibleTrackingAWBMaster = awbDAO.findAwbByShipmentKey(shipmentKey);
        Assertions.assertFalse(possibleTrackingAWBMaster.isEmpty());
        var trackingAWBMaster = possibleTrackingAWBMaster.get();
        var plansByShipmentKeys = trackingDAO.findPlansByShipmentKeys(Collections.singletonList(shipmentKey.toString()));
        var eventsByShipmentKeysAndType = trackingDAO.findEventsByShipmentKeysAndType(Collections.singletonList(shipmentKey.toString()), "A");
        Assertions.assertFalse(plansByShipmentKeys.isEmpty());
        Assertions.assertFalse(eventsByShipmentKeysAndType.isEmpty());
        Assertions.assertEquals(shipmentPrefix, trackingAWBMaster.getShipmentKey().getShipmentPrefix());
        Assertions.assertEquals(masterDocumentNumber, trackingAWBMaster.getShipmentKey().getMasterDocumentNumber());
        Assertions.assertEquals("N", trackingAWBMaster.getAwbStatus());

        trigger.trigger("triggerAWBVoidedEvent");
        possibleTrackingAWBMaster = awbDAO.findAwbByShipmentKey(shipmentKey);
        plansByShipmentKeys = trackingDAO.findPlansByShipmentKeys(Collections.singletonList(shipmentKey.toString()));
        eventsByShipmentKeysAndType = trackingDAO.findEventsByShipmentKeysAndType(Collections.singletonList(shipmentKey.toString()), "A");
        Assertions.assertTrue(plansByShipmentKeys.isEmpty());
        Assertions.assertTrue(eventsByShipmentKeysAndType.isEmpty());
        Assertions.assertTrue(possibleTrackingAWBMaster.isEmpty());
    }
}
