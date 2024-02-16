package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.enricher;

import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ibsplc.neoicargo.tracking.helper.TestUtils.createEvent;
import static com.ibsplc.neoicargo.tracking.helper.TestUtils.createPlan;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum.ACCEPTED;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum.ARRIVED;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum.DELIVERED;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum.DEPARTED;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum.DONE;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum.IN_PROGRESS;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum.TO_DO;
import static java.util.Collections.emptyList;

public class MilestoneEnricherTest {

    private final MilestoneEnricher milestoneEnricher = new MilestoneEnricher();

    private ShipmentMilestonePlanVO planAccepted;
    private ShipmentMilestonePlanVO planDeparted;
    private ShipmentMilestonePlanVO planDepartedNotOrigin;
    private ShipmentMilestonePlanVO planDepartureSplit;
    private ShipmentMilestonePlanVO planArrived;
    private ShipmentMilestonePlanVO planArrivedNotDestination;
    private ShipmentMilestonePlanVO planDelivered;
    private ShipmentMilestonePlanVO planArrivalSplit;

    private ShipmentMilestoneEventVO eventAccepted;
    private ShipmentMilestoneEventVO eventPartiallyAccepted;
    private ShipmentMilestoneEventVO eventDeparted;
    private ShipmentMilestoneEventVO eventDepartedNotOrigin;
    private ShipmentMilestoneEventVO eventDepartureSplit;
    private ShipmentMilestoneEventVO eventArrived;
    private ShipmentMilestoneEventVO eventArrivedNotDestination;
    private ShipmentMilestoneEventVO eventArrivalSplit;
    private ShipmentMilestoneEventVO eventDelivered;
    private ShipmentDetailsVO shipmentDetailsVO;

    private String originAirport = "BOM";
    private String destinationAirport = "FRA";
    private String intermediaAirport = "SMTH";

    @BeforeEach
    public void setup() {

        planAccepted = createPlan(MilestoneCodeEnum.RCS, originAirport);
        planDeparted = createPlan(MilestoneCodeEnum.DEP, originAirport);
        planDepartedNotOrigin = createPlan(MilestoneCodeEnum.DEP, intermediaAirport);
        planDepartureSplit = createPlan(MilestoneCodeEnum.DEP, originAirport);
        planArrived = createPlan(MilestoneCodeEnum.ARR, destinationAirport);
        planArrivedNotDestination = createPlan(MilestoneCodeEnum.ARR, intermediaAirport);
        planArrivalSplit = createPlan(MilestoneCodeEnum.ARR, destinationAirport);
        planDelivered = createPlan(MilestoneCodeEnum.DLV, destinationAirport);

        eventAccepted = createEvent(MilestoneCodeEnum.RCS, originAirport);
        eventPartiallyAccepted = createEvent(MilestoneCodeEnum.FOH, originAirport);
        eventDeparted = createEvent(MilestoneCodeEnum.DEP, originAirport);
        eventDepartedNotOrigin = createEvent(MilestoneCodeEnum.DEP, intermediaAirport);
        eventDepartureSplit = createEvent(MilestoneCodeEnum.DEP, originAirport);
        eventArrived = createEvent(MilestoneCodeEnum.ARR, destinationAirport);
        eventArrivedNotDestination = createEvent(MilestoneCodeEnum.ARR, intermediaAirport);
        eventArrivalSplit = createEvent(MilestoneCodeEnum.ARR, destinationAirport);
        eventDelivered = createEvent(MilestoneCodeEnum.DLV, destinationAirport);

        shipmentDetailsVO = new ShipmentDetailsVO();
        shipmentDetailsVO.setOriginAirportCode(originAirport);
        shipmentDetailsVO.setDestinationAirportCode(destinationAirport);
    }

    @Test
    public void shouldCalculateEmptyShipment() {

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), TO_DO, TO_DO, TO_DO, TO_DO);
    }

    @Test
    public void shouldCalculatePlannedShipment() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planArrived, planDelivered));
        shipmentDetailsVO.setEvents(emptyList());

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), TO_DO, TO_DO, TO_DO, TO_DO);
    }

    @Test
    public void shouldCalculateAcceptedShipment() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planArrived, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventAccepted));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, TO_DO, TO_DO, TO_DO);
    }

    @Test
    public void shouldCalculateDepartedShipment() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planArrived, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventAccepted, eventDeparted));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, DONE, TO_DO, TO_DO);
    }

    @Test
    public void shouldCalculateArrivedShipment() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planArrived, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventAccepted, eventDeparted, eventArrived));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, DONE, DONE, TO_DO);
    }

    @Test
    public void shouldCalculateDeliveredShipment() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planArrived, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventAccepted, eventDeparted, eventDelivered));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, DONE, TO_DO, DONE);
    }

    @Test
    public void shouldCalculateDepartedShipmentWithIncompiltedIntermediaryDeparture() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planDepartedNotOrigin, planArrived, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventAccepted, eventDeparted, eventDelivered));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, DONE, TO_DO, DONE);
    }

    @Test
    public void shouldCalculateArrivedShipmentWithIncompiltedIntermediaryArrival() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planDepartedNotOrigin, planArrived, planArrivedNotDestination, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventAccepted, eventDeparted, eventDelivered));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, DONE, TO_DO, DONE);
    }

    @Test
    public void shouldCalculateCompletedShipmentWithSplit() {

        // given
        shipmentDetailsVO.setPlans(
                List.of(planAccepted, planDeparted, planDepartureSplit, planArrived, planArrivalSplit, planDelivered));
        shipmentDetailsVO.setEvents(
                List.of(eventAccepted, eventDeparted, eventDepartureSplit, eventArrived, eventArrivalSplit,
                        eventDelivered));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, DONE, DONE, DONE);
    }

    @Test
    public void shouldCalculatePartiallyCompletedShipmentWithSplit() {

        // given
        shipmentDetailsVO.setPlans(
                List.of(planAccepted, planDeparted, planDepartureSplit, planArrived, planArrivalSplit, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventAccepted, eventDeparted, eventArrived));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), DONE, IN_PROGRESS, IN_PROGRESS, TO_DO);
    }

    @Test
    public void shouldCalculatePartiallyAcceptedShipment() {

        // given
        shipmentDetailsVO.setPlans(List.of(planAccepted, planDeparted, planArrived, planDelivered));
        shipmentDetailsVO.setEvents(List.of(eventPartiallyAccepted));

        // when
        milestoneEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertNotNull(shipmentDetailsVO.getMilestones());
        assertMilestones(shipmentDetailsVO.getMilestones(), IN_PROGRESS, TO_DO, TO_DO, TO_DO);
    }

    private void assertMilestones(List<MilestoneVO> milestones, MilestoneStatusEnum statusAccepted,
                                  MilestoneStatusEnum statusDeparted, MilestoneStatusEnum statusArrived,
                                  MilestoneStatusEnum statusDelivered) {
        Assertions.assertEquals(ACCEPTED, milestones.get(0).getMilestone());
        Assertions.assertEquals(statusAccepted, milestones.get(0).getStatus());

        Assertions.assertEquals(DEPARTED, milestones.get(1).getMilestone());
        Assertions.assertEquals(statusDeparted, milestones.get(1).getStatus());

        Assertions.assertEquals(ARRIVED, milestones.get(2).getMilestone());
        Assertions.assertEquals(statusArrived, milestones.get(2).getStatus());

        Assertions.assertEquals(DELIVERED, milestones.get(3).getMilestone());
        Assertions.assertEquals(statusDelivered, milestones.get(3).getStatus());

    }
}
