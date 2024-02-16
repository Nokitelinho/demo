package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

public class AwbDeletedEventListenerIT extends TrackingAPIBase {

    @Autowired
    private StubTrigger trigger;
    @Autowired
    private TrackingDAO trackingDAO;
    @Autowired
    private AwbDAO awbDAO;

    @Test
    public void shouldListenAWBDeletedEvent() throws JSONException {
        //given
        trigger.trigger("triggerAWBCreatedEventForDelete");
        var shipmentPrefix = "176";
        var masterDocumentNumber = "11011022";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var possibleTrackingAWBMaster = awbDAO.findAwbByShipmentKey(shipmentKey);
        Assertions.assertTrue(possibleTrackingAWBMaster.isPresent());

        // when
        trigger.trigger("triggerAWBDeletedEvent");

        //then
        possibleTrackingAWBMaster = awbDAO.findAwbByShipmentKey(shipmentKey);
        Assertions.assertFalse(possibleTrackingAWBMaster.isPresent());
    }

    @Test
    public void shouldListenAWBDeletedEventDoNothingIfNoAwb() throws JSONException {

        // when
        Assertions.assertDoesNotThrow( () -> trigger.trigger("sendAWBDeletedEvent"));
    }
}
