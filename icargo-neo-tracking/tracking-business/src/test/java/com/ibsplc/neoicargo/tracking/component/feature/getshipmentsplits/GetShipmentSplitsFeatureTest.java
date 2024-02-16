package com.ibsplc.neoicargo.tracking.component.feature.getshipmentsplits;

import com.ibsplc.neoicargo.awb.component.AwbComponent;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class GetShipmentSplitsFeatureTest {

    @InjectMocks
    private GetShipmentSplitsFeature getShipmentSplitsFeature;
    @Mock
    private TrackingDAO trackingDAO;
    @Mock
    private AwbComponent awbComponent;

    private TrackingMapper trackingMapper;
    private TrackingEntityMapper trackingEntityMapper;
    private Quantities quantities;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        trackingMapper = MockQuantity.injectMapper(quantities, TrackingMapper.class);
        trackingEntityMapper = MockQuantity.injectMapper(quantities, TrackingEntityMapper.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnShipmentSplits()  {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_1();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_1();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "BOM",
                "FRA",
                478
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_1(), splits);
    }

    @Test
    public void shouldReturnShipmentSplitsWithNoEventsHappened()  {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_1();
        var events = List.of();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "BOM",
                "FRA",
                478
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_1_withNoEvents(), splits);
    }

    @Test
    public void shouldReturnShipmentSplitsWithCorrectDstStatuses()  {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_2();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_2();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "BOM",
                "FRA",
                290
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);
        
        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_2(), splits);
    }

    @Test
    public void shouldReturnNormalizedShipmentSplits()  {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_3();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_3();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "A",
                "D",
                178
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_3(), splits);
    }

    @Test
    public void shouldDisplayPiecesOnlyIfArrivalWasHappened() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_4();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_4();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "A",
                "C",
                100
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);
        
        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_4(), splits);
    }

    @Test
    public void shouldReturnDeliveryPieces() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_5();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_5();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "AAA",
                "CCC",
                150
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_5(), splits);
    }

    @Test
    public void shouldReturnDeliveryPiecesSeveralTimes() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_5_1();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_5_1();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "AAA",
                "CCC",
                200
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_5_1(), splits);
    }

    @Test
    public void shouldReturnDeliveryPiecesForNormalizedByOriginSplits() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_5_2();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_5_2();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "AAA",
                "CCC",
                200
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_5_2(), splits);
    }

    @Test
    public void shouldCorrectlyCalculateFlightDateForTransferStation() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_6();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_6();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "AAA",
                "FFF",
                100
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_6(), splits);
    }

    @Test
    public void shouldReturnDepartureAsFirstStatusForOrigin() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_7();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_7();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "AAA",
                "BBB",
                160
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_7(), splits);
    }

    @Test
    public void shouldReturnActualFlightData() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var plans = MockDataHelper.constructShipmentMilestonePlansForSplitsTesting_8();
        var events = MockDataHelper.constructShipmentMilestoneEventsForSplitsTesting_8();
        var awbVO = MockDataHelper.constructAwbVO(
                request.getShipmentPrefix(),
                request.getMasterDocumentNumber(),
                quantities,
                "AAA",
                "CCC",
                200
        );

        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var splits = getShipmentSplitsFeature.perform(request);

        //then
        assertEquals(MockDataHelper.constructShipmentMilestoneSplits_8(), splits);
    }

}
