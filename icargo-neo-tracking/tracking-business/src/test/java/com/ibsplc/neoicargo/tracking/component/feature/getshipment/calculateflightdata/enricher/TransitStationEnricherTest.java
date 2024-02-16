package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.enricher;

import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.List;

import static com.ibsplc.neoicargo.tracking.helper.TestUtils.createPlanWithFlightData;

@RunWith(JUnitPlatform.class)
public class TransitStationEnricherTest {

    private final TransitStationEnricher transitStationEnricher = new TransitStationEnricher();

    private ShipmentMilestonePlanVO planDepartedKbpSplit2;
    private ShipmentMilestonePlanVO planAcceptedKbp;
    private ShipmentMilestonePlanVO planDepartedKbpSplit1;
    private ShipmentMilestonePlanVO planArrivedCph;
    private ShipmentMilestonePlanVO planDeliveredBva;
    private ShipmentMilestonePlanVO planArrivalSof;
    private ShipmentMilestonePlanVO planDepartedCph;
    private ShipmentMilestonePlanVO planArrivedBva1;
    private ShipmentMilestonePlanVO planDepartedSof;
    private ShipmentMilestonePlanVO planArrivedTll;
    private ShipmentMilestonePlanVO planDepartedTll;
    private ShipmentMilestonePlanVO planArrivedBva2;
    private ShipmentMilestonePlanVO planDepartedKbpSplit3;
    private ShipmentMilestonePlanVO planArrivedBva3;
    private ShipmentDetailsVO shipmentDetailsVO;


    @BeforeEach
    public void setup() {

        planAcceptedKbp = createPlanWithFlightData(MilestoneCodeEnum.RCS, "KBP", "", "");
        planDepartedKbpSplit1 = createPlanWithFlightData(MilestoneCodeEnum.DEP, "KBP", "111", "2021-04-02T18:00");
        planDepartedKbpSplit2 = createPlanWithFlightData(MilestoneCodeEnum.DEP, "KBP", "112", "2021-04-02T18:30");
        planDepartedKbpSplit3 = createPlanWithFlightData(MilestoneCodeEnum.DEP, "KBP", "101", "2021-04-02T18:40");

        planArrivedCph = createPlanWithFlightData(MilestoneCodeEnum.ARR, "CPH", "111", "2021-04-02T18:00");
        planArrivalSof = createPlanWithFlightData(MilestoneCodeEnum.ARR, "SOF", "112", "2021-04-02T18:30");
        planArrivedBva3 = createPlanWithFlightData(MilestoneCodeEnum.ARR, "BVA", "101", "2021-04-02T18:40");

        planDepartedCph = createPlanWithFlightData(MilestoneCodeEnum.DEP, "CPH", "120", "2021-04-03T05:00");
        planArrivedBva1 = createPlanWithFlightData(MilestoneCodeEnum.ARR, "BVA", "120", "2021-04-03T05:00");

        planDepartedSof = createPlanWithFlightData(MilestoneCodeEnum.DEP, "SOF", "130", "2021-04-03T10:00");
        planArrivedTll = createPlanWithFlightData(MilestoneCodeEnum.ARR, "TLL", "130", "2021-04-03T10:00");

        planDepartedTll = createPlanWithFlightData(MilestoneCodeEnum.DEP, "TLL", "140", "2021-04-03T11:00");
        planArrivedBva2 = createPlanWithFlightData(MilestoneCodeEnum.ARR, "BVA", "140", "2021-04-03T11:00");

        planDeliveredBva = createPlanWithFlightData(MilestoneCodeEnum.DLV, null, null, null);

        shipmentDetailsVO = new ShipmentDetailsVO();
        shipmentDetailsVO.setOriginAirportCode("KBP");
        shipmentDetailsVO.setDestinationAirportCode("BVA");
    }

    @Test
    public void shouldCalculateEmptyShipment() {

        // when
        transitStationEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getTransitStations());
        Assertions.assertEquals(0, shipmentDetailsVO.getTransitStations().getStops().size());
        Assertions.assertEquals(0, shipmentDetailsVO.getTransitStations().getNumberOfFlights());
    }

    @Test
    public void shouldCalculateSimpleShipment() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAcceptedKbp, planDepartedKbpSplit3, planArrivedBva3, planDeliveredBva));

        // when
        transitStationEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getTransitStations());
        Assertions.assertEquals(0, shipmentDetailsVO.getTransitStations().getStops().size());
        Assertions.assertEquals(1, shipmentDetailsVO.getTransitStations().getNumberOfFlights());
    }

    @Test
    public void shouldCalculateShipmentWithSplit() {

        // given
        shipmentDetailsVO.setPlans(
                List.of(planAcceptedKbp, planDepartedKbpSplit1, planDepartedKbpSplit2, planDepartedKbpSplit3,
                        planArrivedCph, planDepartedCph, planArrivalSof, planDepartedSof, planArrivedTll,
                        planDepartedTll, planArrivedBva1, planArrivedBva2, planArrivedBva3, planDeliveredBva));

        // when
        transitStationEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getTransitStations());
        Assertions.assertEquals(3, shipmentDetailsVO.getTransitStations().getStops().size());
        Assertions.assertEquals(List.of("CPH", "SOF", "TLL"), shipmentDetailsVO.getTransitStations().getStops());
        Assertions.assertEquals(6, shipmentDetailsVO.getTransitStations().getNumberOfFlights());
    }
}
