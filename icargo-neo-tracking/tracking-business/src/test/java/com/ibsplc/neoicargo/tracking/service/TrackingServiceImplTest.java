package com.ibsplc.neoicargo.tracking.service;

import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.tracking.component.TrackingComponent;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.ARR;

@ExtendWith(MockitoExtension.class)
public class TrackingServiceImplTest {


    private final static String MILESTONE_ARR = "ARR";
    @InjectMocks
    private TrackingServiceImpl trackingService;

    @Mock
    private TrackingComponent trackingComponent;

    @Test
    public void shouldReturnShipmentInfo() {
        // given
        var shipmentDetailsVO1 = new ShipmentDetailsVO();
        shipmentDetailsVO1.setShipmentPrefix("020");
        shipmentDetailsVO1.setMasterDocumentNumber("354547");

        doReturn(Lists.newArrayList(shipmentDetailsVO1))
                .when(trackingComponent).getShipments(anyList());

        // when
        var shipments = trackingService.getShipments(Lists.newArrayList(new AwbRequestVO()));

        // then
        Assertions.assertEquals(shipments.get(0).getShipmentPrefix(), "020");
        Assertions.assertEquals(shipments.get(0).getMasterDocumentNumber(), "354547");
    }

    @Test
    public void shouldReturnShipmentSplits() {
        // given
        var shipmentDetailsVO1 = new ShipmentDetailsVO();
        shipmentDetailsVO1.setShipmentPrefix("020");
        shipmentDetailsVO1.setMasterDocumentNumber("354546");

        doReturn(MockDataHelper.constructShipmentMilestoneSplits_1())
                .when(trackingComponent).getShipmentSplits(any(AwbRequestVO.class));

        // when
        var splits = trackingService.getShipmentSplits(new AwbRequestVO("020-666666"));

        // then
        Assertions.assertEquals(3, splits.size());
    }
    @Test
    void shouldSaveShipmentMilestonePlans() {
        var planCreatedVO = MockDataHelper.constructShipmentMilestonePlanCreatedVO();

        trackingService.saveShipmentMilestonePlans(planCreatedVO);
        verify(trackingComponent).saveTrackingShipmentMilestonePlan(planCreatedVO);
    }

    @Test
    void shouldDeleteShipmentMilestonePlans() {
        var deletedEvent = MockDataHelper.constructShipmentMilestonePlanDeletedEventVO();
        trackingService.deleteShipmentMilestonePlans(deletedEvent);

        verify(trackingComponent).deleteTrackingShipmentMilestonePlan(deletedEvent);
    }

    @Test
    void shouldSaveShipmentMilestoneEvent() {
        // given
        var event = MockDataHelper.constructShipmentMilestoneEventVO();

        // when
        trackingService.saveShipmentMilestoneEvent(event);

        // then
        verify(trackingComponent).saveShipmentMilestoneEvent(event);
    }

    @Test
    public void shouldReturnShipmentActivities() {
        // given
        var shipmentActivity = new ShipmentActivityVO();
        shipmentActivity.setEvent(MILESTONE_ARR);

        doReturn(Lists.newArrayList(shipmentActivity))
                .when(trackingComponent).getShipmentActivities(any(AwbRequestVO.class));

        // when
        var actualActivity = trackingService.getShipmentActivities(new AwbRequestVO());

        // then
        Assertions.assertEquals(MILESTONE_ARR, actualActivity.get(0).getEvent());
    }
    @Test
    public void shouldReturnMilestones() {
    	// given
        doReturn(MockDataHelper.constructMilestoneVO())
                .when(trackingComponent).findAllMilestones();
        // when
        var milestones = trackingService.findAllMilestones();

        // then
        Assertions.assertNotNull(milestones);
    }
    @Test
    public void shouldReturnIfActivityViewMilestone() {
    	// given
    	doReturn(true)
                .when(trackingComponent).isActivityViewMilestone(any(String.class));
        // when
        var isActivityView = trackingService.isActivityViewMilestone(MILESTONE_ARR);

        // then
        Assertions.assertEquals(isActivityView, true);
    }
    @Test
    public void shouldReturnIfNotificationViewMilestone() {
    	// given
    	doReturn(true)
                .when(trackingComponent).isNotificationMilestone(any(String.class));
        // when
        var isNotifyView = trackingService.isNotificationMilestone(MILESTONE_ARR);

        // then
        Assertions.assertEquals(isNotifyView, true);
    }

}
