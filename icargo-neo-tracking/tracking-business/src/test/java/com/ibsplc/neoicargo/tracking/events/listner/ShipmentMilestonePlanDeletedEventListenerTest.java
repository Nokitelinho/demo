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
class ShipmentMilestonePlanDeletedEventListenerTest {

    @Mock
    private TrackingService trackingService;
    @Mock
    private TrackingEventMapper trackingEventMapper;
    
    @Mock
    private ContextUtil contextUtil;

    @InjectMocks
    private ShipmentMilestonePlanDeletedEventListener listener;

    @Test
    void shouldHandleShipmentMilestonePlanDeletedEvent() {
        var deletedEvent = MockDataHelper.constructShipmentMilestonePlanDeletedEvent();
        var deletedVO = MockDataHelper.constructShipmentMilestonePlanDeletedEventVO();
        when(trackingEventMapper.constructShipmentMilestonePlanDeletedVO(deletedEvent,contextUtil)).thenReturn(deletedVO);

        listener.handleShipmentMilestonePlanDeletedEvent(deletedEvent);

        verify(trackingService).deleteShipmentMilestonePlans(deletedVO);
    }
}