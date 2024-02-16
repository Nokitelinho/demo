package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.persistor.ShipmentMilestonePlanPersistor;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveShipmentMilestonePlanFeatureTest {

    @Mock
    private ShipmentMilestonePlanPersistor persistor;

    @InjectMocks
    private SaveShipmentMilestonePlanFeature feature;

    @Test
    void shouldPerform() throws BusinessException {

        var planCreatedVO = MockDataHelper.constructShipmentMilestonePlanCreatedVO();

        feature.perform(planCreatedVO);

        verify(persistor).persist(planCreatedVO.getPlanVOs());
    }

}