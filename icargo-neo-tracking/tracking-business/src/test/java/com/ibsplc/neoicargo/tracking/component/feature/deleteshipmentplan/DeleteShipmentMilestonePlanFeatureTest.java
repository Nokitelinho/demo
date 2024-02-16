package com.ibsplc.neoicargo.tracking.component.feature.deleteshipmentplan;

import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteShipmentMilestonePlanFeatureTest {

    @Mock
    private TrackingDAO trackingDAO;

    @InjectMocks
    private DeleteShipmentMilestonePlanFeature feature;

    @Test
    void shouldPerform() {

        var deletedEvent = MockDataHelper.constructShipmentMilestonePlanDeletedEventVO();

        feature.perform(deletedEvent);

        verify(trackingDAO).deletePlan(deletedEvent);
    }

}