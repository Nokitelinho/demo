/**
 *
 */
package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AwbCreatedEventListenerIT extends TrackingAPIBase {

    @Autowired
    private StubTrigger trigger;
    @Autowired
    private AwbDAO awbDAO;

    @Test
    public void shouldListenAwbCreatedEvent() throws JSONException {
        //given
        trigger.trigger("triggerAWBCreatedEventForExecution");
        var shipmentPrefix = "176";
        var masterDocumentNumber = "11011044";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);

        //then
        var trackingAWBMaster = awbDAO.findAwbByShipmentKey(shipmentKey).get();
        assertEquals(shipmentPrefix, trackingAWBMaster.getShipmentKey().getShipmentPrefix());
        assertEquals(masterDocumentNumber, trackingAWBMaster.getShipmentKey().getMasterDocumentNumber());
        assertEquals("N", trackingAWBMaster.getAwbStatus());
        assertEquals("K", trackingAWBMaster.getEUnit().getWeight());
        assertEquals("B", trackingAWBMaster.getEUnit().getVolume());
        assertNotNull(trackingAWBMaster.getAwbContactDetails());
        assertNotNull(trackingAWBMaster.getAwbContactDetails().getConsigneeDetails());
        assertNotNull(trackingAWBMaster.getAwbContactDetails().getShipperDetails());
    }

}
