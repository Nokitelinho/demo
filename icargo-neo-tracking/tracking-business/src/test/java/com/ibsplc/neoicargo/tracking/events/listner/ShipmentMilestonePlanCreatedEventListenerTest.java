package com.ibsplc.neoicargo.tracking.events.listner;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEventMapper;
import com.ibsplc.neoicargo.tracking.service.TrackingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentMilestonePlanCreatedEventListenerTest {

    @Mock
    private TrackingEventMapper trackingEventMapper;
    @Mock
    private TrackingService trackingService;
    @Mock
    private ContextUtil contextUtil;

    @InjectMocks
    ShipmentMilestonePlanCreatedEventListener listener;

    @Test
    void shouldHandleShipmentMilestonePlanCreatedEvent() {
        var createdPlan = MockDataHelper.constructShipmentMilestonePlanCreatedEvent();
        var createdPlanVOs = MockDataHelper.constructShipmentMilestonePlanCreatedVO();

        when(trackingEventMapper.constructShipmentMilestonePlanCreatedVO(createdPlan, contextUtil)).thenReturn(createdPlanVOs);

        listener.handleShipmentMilestonePlanCreatedEvent(createdPlan);

        verify(trackingService).saveShipmentMilestonePlans(createdPlanVOs);

    }
}