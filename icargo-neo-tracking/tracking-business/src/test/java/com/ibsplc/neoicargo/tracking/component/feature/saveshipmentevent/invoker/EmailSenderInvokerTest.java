package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.invoker;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.tracking.component.feature.emailnotification.EmailNotificationFeature;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.service.TrackingService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.ARR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailSenderInvokerTest {

    @Mock
    private EmailNotificationFeature emailFeature;
    @Mock
    private TrackingMapper mapper;
    @Mock
    private TrackingDAO trackingDAO;
    @Mock
    private TrackingService trackingService;
    
    @Mock
    private AwbDAO awbDAO;
    @InjectMocks
    private EmailSenderInvoker invoker;

    @Test
    void shouldInvokeEmailFeatureWhenAllChecksPassed() throws BusinessException {
        var eventVO = MockDataHelper.constructShipmentMilestoneEventVO();
        var notifications = MockDataHelper.constructAwbUserNotifications();
        var notificationsFiltered = notifications.stream()
                .filter(notification -> notification.getNotificationMilestones().contains("ARR"))
                .collect(Collectors.toList());
        var plans = MockDataHelper.constructTrackingShipmentMilestonePlanEntities();
        var events = MockDataHelper.constructShipmentMilestoneEventEntities();
        var emailVO = MockDataHelper.constructEmailMilestoneNotificationVO();
        var awb = new Awb();
        awb.setSerialNumber(1L);
        awb.setDestination("MUC");
        awb.setOrigin("DXB");
        eventVO.setMilestoneCode(ARR);
        eventVO.setAirportCode("MUC");
        when(awbDAO.findAwbByShipmentKey(any(ShipmentKey.class))).thenReturn(Optional.of(awb));
        when(awbDAO.findAwbUserNotificationsByAwbSerialNumber(awb.getSerialNumber())).thenReturn(notifications);
        when(trackingDAO.findPlansByShipmentKeys(List.of(eventVO.getShipmentKey()))).thenReturn(plans);
        when(trackingDAO.findEventsByShipmentKeysAndType(List.of(eventVO.getShipmentKey()), "A")).thenReturn(events);
        when(mapper.constructEmailMilestoneNotificationVO(eventVO, notificationsFiltered, 10)).thenReturn(emailVO);
        when(trackingService.isNotificationMilestone(any(String.class))).thenReturn(true);
        invoker.invoke(eventVO);
        verify(emailFeature).execute(emailVO);
    }

    @Test
    void shouldNotInvokeWhenEventTypeIsNotMatched() throws BusinessException {
        var eventVO = MockDataHelper.constructShipmentMilestoneEventVO();
        when(trackingService.isNotificationMilestone(any(String.class))).thenReturn(false);
        invoker.invoke(eventVO);
        verifyNoInteractions(mapper);
        verifyNoInteractions(emailFeature);
    }

    @Test
    void shouldNotInvokeWhenNoNotifications() throws BusinessException {
        var eventVO = MockDataHelper.constructShipmentMilestoneEventVO();
        eventVO.setMilestoneCode(ARR);
        var awb = new Awb();
        awb.setDestination("SIN");
        awb.setOrigin("DXB");
        when(awbDAO.findAwbByShipmentKey(any(ShipmentKey.class))).thenReturn(Optional.of(awb));
        when(awbDAO.findAwbUserNotificationsByAwbSerialNumber(awb.getSerialNumber())).thenReturn(List.of());
        when(trackingService.isNotificationMilestone(any(String.class))).thenReturn(true);
        invoker.invoke(eventVO);

        verifyNoMoreInteractions(awbDAO);
        verifyNoInteractions(mapper);
        verifyNoInteractions(emailFeature);
    }


}