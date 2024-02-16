package com.ibsplc.neoicargo.awb.component.feature.deleteawbusernotification.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAwbUserNotificationPersistorTest {

    @Mock
    private AwbDAO awbDAO;
    @InjectMocks
    private DeleteAwbUserNotificationPersistor persistor;

    @Test
    void shouldDeleteWhenNotificationFound() {
        var keyVO = new AwbUserNotificationKeyVO(1L, "userId");
        var notification = MockDataHelper.constructAwbUserNotification();
        var awbNotificationKeyCaptor = ArgumentCaptor.forClass(AWBUserNotificationKey.class);

        when(awbDAO.findAwbUserNotificationByKey(any(AWBUserNotificationKey.class))).thenReturn(Optional.of(notification));

        persistor.persist(keyVO);

        verify(awbDAO).deleteAwbUserNotification(notification);
        verify(awbDAO).findAwbUserNotificationByKey(awbNotificationKeyCaptor.capture());
        var awbNotificationKeyCaptorValue = awbNotificationKeyCaptor.getValue();
        assertEquals(keyVO.getTrackingAwbSerialNumber(), awbNotificationKeyCaptorValue.getTrackingAwbSerialNumber());
        assertEquals(keyVO.getUserId(), awbNotificationKeyCaptorValue.getUserId());

    }
}