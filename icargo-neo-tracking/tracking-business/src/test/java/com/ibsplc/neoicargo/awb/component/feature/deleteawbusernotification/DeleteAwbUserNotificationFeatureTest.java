package com.ibsplc.neoicargo.awb.component.feature.deleteawbusernotification;

import com.ibsplc.neoicargo.awb.component.feature.deleteawbusernotification.persistor.DeleteAwbUserNotificationPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteAwbUserNotificationFeatureTest {
    @Mock
    private DeleteAwbUserNotificationPersistor persistor;
    @InjectMocks
    private DeleteAwbUserNotificationFeature feature;


    @Test
    void shouldPerform() {
        var notificationKey = new AwbUserNotificationKeyVO(1L, "userID");
        feature.perform(notificationKey);
        verify(persistor).persist(notificationKey);
    }
}