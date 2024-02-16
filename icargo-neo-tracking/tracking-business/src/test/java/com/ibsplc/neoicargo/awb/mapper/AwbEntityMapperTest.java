package com.ibsplc.neoicargo.awb.mapper;

import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.RCS;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DEP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(JUnitPlatform.class)
class AwbEntityMapperTest {

    private AwbEntityMapper awbEntityMapper;
    private Quantities quantities;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, null, null);
        awbEntityMapper = MockQuantity.injectMapper(quantities, AwbEntityMapper.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void constructAwbUserNotificationVO() {
        var notification = MockDataHelper.constructAwbUserNotification();
        var milestones = List.of(RCS.getLabel(), DEP.getLabel());

        var notificationVO = awbEntityMapper.constructAwbUserNotificationVO(notification);

        assertEquals(notification.getNotificationsKey().getUserId(), notificationVO.getNotificationsKey().getUserId());
        assertEquals(notification.getNotificationsKey().getTrackingAwbSerialNumber(), notificationVO.getNotificationsKey().getTrackingAwbSerialNumber());
        assertEquals(notification.getEmails(), notificationVO.getEmails());
        assertEquals(milestones, notification.getNotificationMilestones());
        assertNull(notificationVO.getShipmentKey());
    }

    @Test
    void constructAwbUserNotification() {
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        var milestones = List.of(RCS.getLabel(), DEP.getLabel());

        var notification = awbEntityMapper.constructAwbUserNotification(notificationVO);

        assertEquals(notificationVO.getNotificationsKey().getUserId(), notification.getNotificationsKey().getUserId());
        assertEquals(notificationVO.getNotificationsKey().getTrackingAwbSerialNumber(), notification.getNotificationsKey().getTrackingAwbSerialNumber());
        assertEquals(notificationVO.getEmails(), notification.getEmails());
        assertEquals(milestones, notification.getNotificationMilestones());
    }

    @Test
    void mergeAwb() {
        var awbVO = MockDataHelper.constructAwbVO("134", "12345678", quantities);
        awbVO.setOrigin("DXB");
        awbVO.getAwbContactDetailsVO().setConsigneeCode("UPD");
        var awbUnderUpdate = MockDataHelper.constructAwbEntity(1L, new ShipmentKey("134", "87654321"), quantities);

        var updatedAwb = awbEntityMapper.mergeAwb(awbVO, awbUnderUpdate);

        assertEquals("12345678", updatedAwb.getShipmentKey().getMasterDocumentNumber());
        assertEquals("DXB", updatedAwb.getOrigin());
        assertEquals("UPD", updatedAwb.getAwbContactDetails().getConsigneeCode());
        assertEquals("SC", updatedAwb.getAwbContactDetails().getShipperCode());
    }

    @Test
    void mergeAwbWithoutContactDetails() {
        var awbVO = MockDataHelper.constructAwbVO("134", "12345678", quantities);
        var awbUnderUpdate = MockDataHelper.constructAwbEntity(1L, new ShipmentKey("134", "12345678"), quantities);
        awbUnderUpdate.setAwbContactDetails(null);

        var updatedAwb = awbEntityMapper.mergeAwb(awbVO, awbUnderUpdate);

        assertEquals("12345678", updatedAwb.getShipmentKey().getMasterDocumentNumber());
        assertEquals("CC", updatedAwb.getAwbContactDetails().getConsigneeCode());
        assertEquals("SC", updatedAwb.getAwbContactDetails().getShipperCode());
    }
}