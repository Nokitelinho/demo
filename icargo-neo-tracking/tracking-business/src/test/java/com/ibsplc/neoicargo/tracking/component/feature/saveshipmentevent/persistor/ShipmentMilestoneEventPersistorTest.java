package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.persistor;

import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
class ShipmentMilestoneEventPersistorTest {

    @Mock
    private TrackingDAO trackingDao;
    private TrackingEntityMapper trackingEntityMapper;
    private Quantities quantities;

    @InjectMocks
    private ShipmentMilestoneEventPersistor persistor;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        trackingEntityMapper = MockQuantity.injectMapper(quantities, TrackingEntityMapper.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSaveTrackingShipmentMilestoneEvent() {
        // given
        var eventVO = MockDataHelper.constructShipmentMilestoneEventVO();
        var event = MockDataHelper.constructShipmentMilestoneEvent();
        var eventCaptor = ArgumentCaptor.forClass(ShipmentMilestoneEvent.class);

        // when
        persistor.persist(eventVO);

        // then
        verify(trackingDao).saveShipmentMilestoneEvent(eventCaptor.capture());
        Assertions.assertEquals(event.getShipmentKey(), eventCaptor.getValue().getShipmentKey());
        Assertions.assertEquals(event.getMilestoneTime(), eventCaptor.getValue().getMilestoneTime());
        Assertions.assertEquals(event.getMilestoneCode(), eventCaptor.getValue().getMilestoneCode());
    }
}