package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

public class AwbExecutedEventListenerIT extends TrackingAPIBase {

    @Autowired
    private StubTrigger trigger;
    @Autowired
    private AwbDAO awbDAO;

    @Test
    public void shouldListenAwbExecutedEvent() throws JSONException {
        //given
        trigger.trigger("triggerAWBCreatedEventForExecution");
        var shipmentPrefix = "176";
        var masterDocumentNumber = "11011044";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);

        //then
        var possibleTrackingAWBMaster = awbDAO.findAwbByShipmentKey(shipmentKey);
        Assertions.assertFalse(possibleTrackingAWBMaster.isEmpty());
        var trackingAWBMaster = possibleTrackingAWBMaster.get();
        Assertions.assertEquals(shipmentPrefix, trackingAWBMaster.getShipmentKey().getShipmentPrefix());
        Assertions.assertEquals(masterDocumentNumber, trackingAWBMaster.getShipmentKey().getMasterDocumentNumber());
        Assertions.assertEquals("N", trackingAWBMaster.getAwbStatus());

        trigger.trigger("triggerAWBExecutedEvent");
        possibleTrackingAWBMaster = awbDAO.findAwbByShipmentKey(shipmentKey);
        Assertions.assertFalse(possibleTrackingAWBMaster.isEmpty());
        trackingAWBMaster = possibleTrackingAWBMaster.get();
        Assertions.assertEquals(shipmentPrefix, trackingAWBMaster.getShipmentKey().getShipmentPrefix());
        Assertions.assertEquals(masterDocumentNumber, trackingAWBMaster.getShipmentKey().getMasterDocumentNumber());
        Assertions.assertEquals("E", trackingAWBMaster.getAwbStatus());
    }
}
