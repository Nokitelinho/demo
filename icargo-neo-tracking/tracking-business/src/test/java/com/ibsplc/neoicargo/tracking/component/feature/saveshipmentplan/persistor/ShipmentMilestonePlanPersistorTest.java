package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.persistor;

import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentMilestonePlanPersistorTest {

    @Mock
    private TrackingEntityMapper awbEntityMapper;
    @Mock
    private TrackingDAO trackingDao;

    @InjectMocks
    private ShipmentMilestonePlanPersistor persistor;

    @Test
    void shouldSaveTrackingShipmentMilestonePlan() {
        var trackingShipmentMilestonePlanVOs = MockDataHelper.constructShipmentMilestonePlanVOs();
        var trackingShipmentMilestonePlans = MockDataHelper.constructTrackingShipmentMilestonePlanEntities();

        when(awbEntityMapper.constructShipmentMilestonePlans(trackingShipmentMilestonePlanVOs)).thenReturn(trackingShipmentMilestonePlans);

        persistor.persist(trackingShipmentMilestonePlanVOs);

        verify(trackingDao).saveShipmentMilestonePlans(trackingShipmentMilestonePlans);
    }

}