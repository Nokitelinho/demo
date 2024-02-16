package com.ibsplc.neoicargo.awb.mapper;

import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DEP;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.RCS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
class AwbMapperTest {

    private AwbMapper awbMapper = Mappers.getMapper(AwbMapper.class);

    @Test
    void constructDefaultAwbUserNotificationModelWhenVoISNull() {
    	var milestones= MockDataHelper.constructMilestoneVO();
        var model = awbMapper.constructAwbUserNotificationModel(null,milestones);
        assertEquals(model.getNotifications().entrySet().stream().filter(java.util.Map.Entry::getValue).count(),0);
        assertTrue(model.getEmails().isEmpty());
        assertNull(model.getTrackingAwbSerialNumber());

    }

    @Test
    void constructAwbUserNotificationModel() {
    	var milestones= MockDataHelper.constructMilestoneVO();
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        var model = awbMapper.constructAwbUserNotificationModel(notificationVO,milestones);
        assertEquals( model.getNotifications().entrySet().stream().filter(java.util.Map.Entry::getValue).map(v-> v.getKey()).collect(java.util.stream.Collectors.toList()),List.of("RCS", "DEP"));
        assertEquals(notificationVO.getEmails(), model.getEmails());
        assertEquals(notificationVO.getNotificationsKey().getTrackingAwbSerialNumber(), model.getTrackingAwbSerialNumber());

    }
}