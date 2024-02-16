package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent;

import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.persistor.ShipmentMilestoneEventPersistor;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveShipmentMilestoneEventFeatureTest {

    @Mock
    private ShipmentMilestoneEventPersistor persistor;

    @InjectMocks
    private SaveShipmentMilestoneEventFeature feature;

    @Test
    void shouldPerform() {
        // given
        var event = MockDataHelper.constructShipmentMilestoneEventVO();

        // when
        feature.perform(event);

        // then
        verify(persistor).persist(event);
    }

}