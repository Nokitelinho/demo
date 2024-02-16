package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification;

import com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.persistor.SaveAwbUserNotificationPersistor;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveAwbUserNotificationFeatureTest {

    @Mock
    private SaveAwbUserNotificationPersistor persistor;
    @InjectMocks
    private SaveAwbUserNotificationFeature feature;


    @Test
    void shouldPerform() throws BusinessException {
        var vo = MockDataHelper.constructAwbUserNotificationVO();
        feature.perform(vo);
        verify(persistor).persist(vo);

    }
}