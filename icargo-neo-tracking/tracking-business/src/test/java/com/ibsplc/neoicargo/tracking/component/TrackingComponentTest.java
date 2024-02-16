package com.ibsplc.neoicargo.tracking.component;

import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.tracking.component.feature.deleteshipment.DeleteShipmentFeature;
import com.ibsplc.neoicargo.tracking.component.feature.deleteshipmentplan.DeleteShipmentMilestonePlanFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getmilestones.GetMilestonesFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist.GetShipmentListFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getshipmentactivities.GetShipmentActivitiesFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getshipmentsplits.GetShipmentSplitsFeature;
import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.SaveShipmentMilestoneEventFeature;
import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.SaveShipmentMilestonePlanFeature;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrackingComponentTest {

    @Mock
    private GetShipmentListFeature getShipmentListFeature;
    @Mock
    private DeleteShipmentFeature deleteShipmentFeature;
    @Mock
    private GetShipmentSplitsFeature getShipmentSplitsFeature;
    @Mock
    private SaveShipmentMilestonePlanFeature saveShipmentMilestonePlanFeature;
    @Mock
    private DeleteShipmentMilestonePlanFeature deleteShipmentMilestonePlanFeature;
    @Mock
    private SaveShipmentMilestoneEventFeature saveShipmentMilestoneEventFeature;
    @Mock
    private GetShipmentActivitiesFeature getShipmentActivitiesFeature;
    @Mock
    private GetMilestonesFeature getMilestonesFeature;

    @InjectMocks
    private TrackingComponent trackingComponent;

    private final static String SHIPMENT_PREFIX = "020";
    private final static String MASTER_DOCUMENT_NUMBER = "6666666";
    private final static AwbRequestVO AWB_REQUEST_VO =
            new AwbRequestVO(String.format("%s-%s", SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER));
    private final static String MILESTONE_ARR = "ARR";

    @Test
    public void shouldReturnShipmentInfo() {

        // given
        var shipmentDetailsVO = new ShipmentDetailsVO();
        shipmentDetailsVO.setShipmentPrefix(SHIPMENT_PREFIX);
        shipmentDetailsVO.setMasterDocumentNumber(MASTER_DOCUMENT_NUMBER);
        doReturn(List.of(shipmentDetailsVO)).when(getShipmentListFeature).perform(anyList());

        // when
        var shipments = trackingComponent.getShipments(List.of(AWB_REQUEST_VO));

        // then
        Assertions.assertNotNull(shipments);
        Assertions.assertEquals(1, shipments.size());
        Assertions.assertEquals(String.join("-", SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER), shipments.get(0).getShipmentKey());
    }

    @Test
    void shouldSaveTrackingShipmentMilestonePlan() {
        var planCreatedVO = MockDataHelper.constructShipmentMilestonePlanCreatedVO();
        trackingComponent.saveTrackingShipmentMilestonePlan(planCreatedVO);
        verify(saveShipmentMilestonePlanFeature).execute(planCreatedVO);
    }

    @Test
    public void shouldReturnShipmentSplits() {

        // given
        doReturn(MockDataHelper.constructShipmentMilestoneSplits_1()).when(getShipmentSplitsFeature).perform(any(AwbRequestVO.class));

        // when
        var splits = trackingComponent.getShipmentSplits(AWB_REQUEST_VO);

        // then
        Assertions.assertNotNull(splits);
        Assertions.assertEquals(3, splits.size());
    }

    @Test
    public void shouldDeleteShipment() {
        // given
        var awbValidationVO = new AwbValidationVO();
        awbValidationVO.setShipmentPrefix("020");
        awbValidationVO.setMasterDocumentNumber("6666666");
        doNothing().when(deleteShipmentFeature).perform(awbValidationVO);

        // when
        trackingComponent.deleteShipment(awbValidationVO);

        // then
        verify(deleteShipmentFeature, times(1)).perform(awbValidationVO);
    }

    @Test
    void shouldDeleteShipmentMilestonePlan() {
        var deletedEvent = MockDataHelper.constructShipmentMilestonePlanDeletedEventVO();

        trackingComponent.deleteTrackingShipmentMilestonePlan(deletedEvent);

        verify(deleteShipmentMilestonePlanFeature).perform(deletedEvent);
    }

    @Test
    void shouldSaveTrackingShipmentMilestoneEvent() {
        // given
        var event = MockDataHelper.constructShipmentMilestoneEventVO();

        // when
        trackingComponent.saveShipmentMilestoneEvent(event);

        // then
        verify(saveShipmentMilestoneEventFeature).execute(event);
    }

    @Test
    public void shouldReturnShipmentActivities() {

        // given
        var shipmentActivityVO = new ShipmentActivityVO();
        shipmentActivityVO.setEvent(MILESTONE_ARR);
        doReturn(List.of(shipmentActivityVO)).when(getShipmentActivitiesFeature).perform(eq(AWB_REQUEST_VO));

        // when
        var actualActivities = trackingComponent.getShipmentActivities(AWB_REQUEST_VO);

        // then
        Assertions.assertNotNull(actualActivities);
        Assertions.assertEquals(1, actualActivities.size());
        Assertions.assertEquals(MILESTONE_ARR, actualActivities.get(0).getEvent());
    }
    
   @Test
    public void shouldReturnMilestones() {
    	// given
        doReturn(MockDataHelper.constructMilestoneVO())
                .when(getMilestonesFeature).getMilestones();
        // when
        var milestones = trackingComponent.findAllMilestones();

        // then
        Assertions.assertNotNull(milestones);
    }
    @Test
    public void shouldReturnIfActivityViewMilestone() {
    	// given
    	doReturn(true)
                .when(getMilestonesFeature).isActivityViewMilestone(any(String.class));
        // when
        var isActivityView = trackingComponent.isActivityViewMilestone(MILESTONE_ARR);

        // then
        Assertions.assertEquals(isActivityView, true);
    }
    @Test
    public void shouldReturnIfNotificationViewMilestone() {
    	// given
    	doReturn(true)
                .when(getMilestonesFeature).isNotificationMilestone(any(String.class));
        // when
        var isNotifyView = trackingComponent.isNotificationMilestone(MILESTONE_ARR);

        // then
        Assertions.assertEquals(isNotifyView, true);
    }

}
