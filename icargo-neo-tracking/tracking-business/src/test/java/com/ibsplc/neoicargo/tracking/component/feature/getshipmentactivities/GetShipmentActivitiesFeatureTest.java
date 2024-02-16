package com.ibsplc.neoicargo.tracking.component.feature.getshipmentactivities;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.AuthorizedService;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.component.feature.getmilestones.GetMilestonesFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@RunWith(JUnitPlatform.class)
class GetShipmentActivitiesFeatureTest {

    private final static String MILESTONE_DRN = "DRN";
    private final static String MILESTONE_DLV = "DLV";
    private final static String MILESTONE_ARR = "ARR";
    private final static String MILESTONE_RCS = "RCS";
    private final static String MILESTONE_DEP = "DEP";
    private final static String MILESTONE_RCT = "RCT";
    private final static String MILESTONE_TFD = "TFD";
    private final static String MILESTONE_DIS = "DIS";
    

    @InjectMocks
    private GetShipmentActivitiesFeature getShipmentActivitiesFeature;
    @Mock
    private TrackingDAO trackingDAO;
    @Mock
    private AwbDAO awbDAO;
    @Mock
    private AuthorizedService authService;
    @Mock
    private ContextUtil contextUtil;

    private TrackingMapper trackingMapper;
    private TrackingEntityMapper trackingEntityMapper;
    private Quantities quantities;

	@Mock
	private GetMilestonesFeature getMilestonesFeature;
    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        trackingMapper = MockQuantity.injectMapper(quantities, TrackingMapper.class);
        trackingEntityMapper = MockQuantity.injectMapper(quantities, TrackingEntityMapper.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnShipmentActivities()  {

        //given
        var shipmentKeyString = "020-222222";
        var shipmentKey = new ShipmentKey("020", "222222");
        var request = new AwbRequestVO(shipmentKeyString);
        var shipmentKeyType = "A";

        var awb = MockDataHelper.constructAwbEntity(shipmentKey, quantities);
        var plans = MockDataHelper.constructShipmentMilestonePlansForActivitiesTesting();
        var events = plans.stream().map(plan -> MockDataHelper.constructEventForActivities(plan, quantities))
                .collect(Collectors.toList());

        events.stream().forEach(event -> {
            if(MILESTONE_DIS.equals(event.getMilestoneCode())){
                ((Map) event.getTransactionDetails()).put("reasonDescription", "some_reason");
            }
            if(MILESTONE_TFD.equals(event.getMilestoneCode())){
                ((Map) event.getTransactionDetails()).put("fromCarrier", "carrier_1");
                ((Map) event.getTransactionDetails()).put("flightCarrierCode", "carrier_2");
            }
        });

        events.get(3).setTransactionDetails(null);
		doReturn(true).when(getMilestonesFeature).isActivityViewMilestone(any(String.class));
        doReturn(plans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(events).when(trackingDAO).findEventsByShipmentKeysAndType(List.of(shipmentKeyString), shipmentKeyType);
        doReturn(Optional.ofNullable(awb)).when(awbDAO).findAwbByShipmentKey(shipmentKey);

        //when
        var actualActivities = getShipmentActivitiesFeature.perform(request);

        //then
        assertEquals(10, actualActivities.size());

        var actualDrn = actualActivities.get(0);
        assertEquals(MILESTONE_DRN, actualDrn.getEvent());
        assertEquals("LIT", actualDrn.getAirportCode());
        assertEquals("AA", actualDrn.getFlightData().getFlightCarrierCode());
        assertEquals("2", actualDrn.getFlightData().getFlightNumber());
        assertEquals(1.2, actualDrn.getWeight().getDisplayValue().doubleValue());
        assertEquals("K", actualDrn.getWeight().getDisplayUnit().getName());

        var actualDlv = actualActivities.get(1);
        assertEquals(MILESTONE_DLV, actualDlv.getEvent());
        assertEquals("MUC", actualDlv.getAirportCode());
        assertEquals("AA", actualDlv.getFlightData().getFlightCarrierCode());
        assertEquals("2", actualDlv.getFlightData().getFlightNumber());
        assertEquals("DXB", actualDlv.getFlightData().getOriginAirportCode());
        assertEquals("MUC", actualDlv.getFlightData().getDestinationAirportCode());
        assertEquals(1.2, actualDlv.getWeight().getDisplayValue().doubleValue());
        assertEquals("K", actualDlv.getWeight().getDisplayUnit().getName());
        assertNull(actualDlv.getFromCarrier());
        assertNull(actualDlv.getToCarrier());

        var actualTFD = actualActivities.get(2);
		assertEquals(MILESTONE_TFD, actualTFD.getEvent());
        assertEquals("MUC", actualTFD.getAirportCode());
        assertEquals("AA", actualTFD.getFlightData().getFlightCarrierCode());
        assertEquals("2", actualTFD.getFlightData().getFlightNumber());
        assertEquals("DXB", actualTFD.getFlightData().getOriginAirportCode());
        assertEquals("MUC", actualTFD.getFlightData().getDestinationAirportCode());
        assertEquals(1.2, actualTFD.getWeight().getDisplayValue().doubleValue());
        assertEquals("K", actualTFD.getWeight().getDisplayUnit().getName());
        assertEquals("carrier_1", actualTFD.getFromCarrier());
        assertEquals("carrier_2", actualTFD.getToCarrier());
        
        var actualRCT = actualActivities.get(3);
		assertEquals(MILESTONE_RCT, actualRCT.getEvent());
        assertEquals("MUC", actualRCT.getAirportCode());
        assertEquals("AA", actualRCT.getFlightData().getFlightCarrierCode());
        assertEquals("2", actualRCT.getFlightData().getFlightNumber());
        assertEquals("DXB", actualRCT.getFlightData().getOriginAirportCode());
        assertEquals("MUC", actualRCT.getFlightData().getDestinationAirportCode());
        assertEquals(1.2, actualRCT.getWeight().getDisplayValue().doubleValue());
        assertEquals("K", actualRCT.getWeight().getDisplayUnit().getName());
        assertNull(actualRCT.getFromCarrier());
        assertEquals("BB", actualRCT.getToCarrier());

        var actualOfld = actualActivities.get(5);
		assertEquals(MILESTONE_DIS, actualOfld.getEvent());
        assertEquals("MUC", actualOfld.getAirportCode());
        assertEquals("some_reason", actualOfld.getReason());
        assertEquals("AA", actualOfld.getFlightData().getFlightCarrierCode());
        assertEquals("2", actualOfld.getFlightData().getFlightNumber());
        assertEquals("DXB", actualOfld.getFlightData().getOriginAirportCode());
        assertEquals("MUC", actualOfld.getFlightData().getDestinationAirportCode());

        var actualDep2 = actualActivities.get(6);
        assertEquals(MILESTONE_DEP, actualDep2.getEvent());
        assertEquals("DXB", actualDep2.getAirportCode());
        assertNull( actualDep2.getFlightData().getFlightCarrierCode());
        assertNull( actualDep2.getFlightData().getFlightNumber());
        assertNull( actualDep2.getFlightData().getOriginAirportCode());
        assertNull( actualDep2.getFlightData().getDestinationAirportCode());

        var actualArr = actualActivities.get(7);
        assertEquals(MILESTONE_ARR, actualArr.getEvent());
        assertEquals("DXB", actualArr.getAirportCode());
        assertEquals("AA", actualArr.getFlightData().getFlightCarrierCode());
        assertEquals("1", actualArr.getFlightData().getFlightNumber());
        assertEquals("KPB", actualArr.getFlightData().getOriginAirportCode());
        assertEquals("DXB", actualArr.getFlightData().getDestinationAirportCode());

        var actualAcc = actualActivities.get(8);
		assertEquals(MILESTONE_RCS, actualAcc.getEvent());
        assertEquals(290, actualAcc.getPieces());
        assertEquals("KPB", actualAcc.getAirportCode());
        assertEquals(LocalDateTime.parse("2022-01-01T11:00"), actualAcc.getEventTime());
        assertEquals(LocalDateTime.parse("2022-01-01T13:00"), actualAcc.getEventTimeUTC());
        assertEquals("KPB", actualAcc.getFlightData().getOriginAirportCode());
        assertEquals("MUC", actualAcc.getFlightData().getDestinationAirportCode());

        var actualDep = actualActivities.get(9);
        assertEquals(MILESTONE_DEP, actualDep.getEvent());
        assertEquals("KPB", actualDep.getAirportCode());
        assertEquals("AA", actualDep.getFlightData().getFlightCarrierCode());
        assertEquals("1", actualDep.getFlightData().getFlightNumber());
        assertEquals("KPB", actualDep.getFlightData().getOriginAirportCode());
        assertEquals("DXB", actualDep.getFlightData().getDestinationAirportCode());

    }

}
