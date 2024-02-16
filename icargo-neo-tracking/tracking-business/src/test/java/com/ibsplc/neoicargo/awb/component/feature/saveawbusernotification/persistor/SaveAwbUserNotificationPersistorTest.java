package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveAwbUserNotificationPersistorTest {

    @Mock
    private AwbDAO awbDAO;
    @Mock
    private AwbEntityMapper entityMapper;
    @InjectMocks
    private SaveAwbUserNotificationPersistor persistor;


    @Test
    void shouldPersistNew() {
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        var notification = MockDataHelper.constructAwbUserNotification();
        var awbNotificationKeyCaptor = ArgumentCaptor.forClass(AWBUserNotificationKey.class);

        when(entityMapper.constructAwbUserNotification(notificationVO)).thenReturn(notification);
        when(awbDAO.findAwbUserNotificationByKey(any(AWBUserNotificationKey.class))).thenReturn(Optional.empty());

        persistor.persist(notificationVO);

        verify(awbDAO).saveAwbUserNotification(notification);

        verify(awbDAO).findAwbUserNotificationByKey(awbNotificationKeyCaptor.capture());
        var awbNotificationKeyCaptorValue = awbNotificationKeyCaptor.getValue();
        assertEquals(notification.getNotificationsKey().getTrackingAwbSerialNumber(), awbNotificationKeyCaptorValue.getTrackingAwbSerialNumber());
        assertEquals(notification.getNotificationsKey().getUserId(), awbNotificationKeyCaptorValue.getUserId());
    }

    @Test
    void shouldPersistUpdated() {
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        var existingNotification = MockDataHelper.constructAwbUserNotification();
        var newNotification = MockDataHelper.constructAwbUserNotification();
        newNotification.setEmails(List.of("new@email.com"));
        newNotification.setNotificationMilestones(List.of("DEP"));

        when(entityMapper.constructAwbUserNotification(notificationVO)).thenReturn(newNotification);
        when(awbDAO.findAwbUserNotificationByKey(any(AWBUserNotificationKey.class))).thenReturn(Optional.of(existingNotification));

        persistor.persist(notificationVO);

        verify(awbDAO).saveAwbUserNotification(existingNotification);
        assertEquals(newNotification.getNotificationMilestones(), existingNotification.getNotificationMilestones());
        assertEquals(newNotification.getEmails(), existingNotification.getEmails());

    }

}