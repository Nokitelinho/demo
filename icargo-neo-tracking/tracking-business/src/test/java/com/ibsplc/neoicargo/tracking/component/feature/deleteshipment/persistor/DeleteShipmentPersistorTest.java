package com.ibsplc.neoicargo.tracking.component.feature.deleteshipment.persistor;

import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
public class DeleteShipmentPersistorTest {
    @InjectMocks
    private DeleteShipmentPersistor deleteShipmentPersistor;

    @Mock
    private TrackingDAO trackingDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDeleteShipment() {
        //given
        var shipmentPrefix = "134";
        var masterDocumentNumber = "789040";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var awbValidationVO = MockDataHelper.constructAwbValidationVO(shipmentPrefix, masterDocumentNumber);

        doNothing().when(trackingDAO).deletePlanByShipmentKey(shipmentKey.toString());
        doNothing().when(trackingDAO).deleteEventByShipmentKey(shipmentKey.toString());

        //when
        deleteShipmentPersistor.persist(awbValidationVO);

        //then
        verify(trackingDAO, times(1)).deleteEventByShipmentKey(shipmentKey.toString());
        verify(trackingDAO, times(1)).deletePlanByShipmentKey(shipmentKey.toString());

    }
}