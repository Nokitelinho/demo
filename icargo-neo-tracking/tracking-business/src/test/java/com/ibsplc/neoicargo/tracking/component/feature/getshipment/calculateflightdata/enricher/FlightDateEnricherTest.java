package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.enricher;

import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.ibsplc.neoicargo.tracking.helper.TestUtils.createEvent;
import static com.ibsplc.neoicargo.tracking.helper.TestUtils.createMilestone;
import static com.ibsplc.neoicargo.tracking.helper.TestUtils.createPlan;
import static com.ibsplc.neoicargo.tracking.vo.FlightTimePostfixEnum.ACTUAL;
import static com.ibsplc.neoicargo.tracking.vo.FlightTimePostfixEnum.SCHEDULED;
import static java.util.Collections.emptyList;

public class FlightDateEnricherTest {

    private final FlightDateEnricher flightDateEnricher = new FlightDateEnricher();

    private ShipmentDetailsVO shipmentDetailsVO;
    private ShipmentMilestonePlanVO planDeparture;
    private ShipmentMilestonePlanVO planDepartureSplit;
    private ShipmentMilestonePlanVO planArrival;
    private ShipmentMilestonePlanVO planArrivalSplit;
    private ShipmentMilestoneEventVO eventDeparture;
    private ShipmentMilestoneEventVO eventDepartureSplit;
    private ShipmentMilestoneEventVO eventArrival;
    private ShipmentMilestoneEventVO eventArrivalSplit;

    private String originAirport = "BOM";
    private String destinationAirport = "FRA";

    @BeforeEach
    public void setup() {

        shipmentDetailsVO = new ShipmentDetailsVO();

        shipmentDetailsVO.setOriginAirportCode(originAirport);
        shipmentDetailsVO.setDestinationAirportCode(destinationAirport);

        int departureTimeZone = 3;
        int arrivalTimeZone = 1;


        planDeparture = createPlan(MilestoneCodeEnum.DEP, originAirport, 2,0, departureTimeZone);
        planDepartureSplit = createPlan(MilestoneCodeEnum.DEP, originAirport, 3, 0, departureTimeZone);
        planArrival = createPlan(MilestoneCodeEnum.ARR, destinationAirport, 5, 0, arrivalTimeZone);
        planArrivalSplit = createPlan(MilestoneCodeEnum.ARR, destinationAirport, 6, 0, arrivalTimeZone);

        eventDeparture = createEvent(MilestoneCodeEnum.DEP, originAirport,2, -20, departureTimeZone);
        eventDepartureSplit = createEvent(MilestoneCodeEnum.DEP, originAirport, 3, 12, departureTimeZone);
        eventArrival = createEvent(MilestoneCodeEnum.ARR, destinationAirport, 5, 25, arrivalTimeZone);
        eventArrivalSplit = createEvent(MilestoneCodeEnum.ARR, destinationAirport, 6, 35, arrivalTimeZone);

        shipmentDetailsVO.setMilestones(
                List.of(createMilestone(MilestoneNameEnum.ACCEPTED), createMilestone(MilestoneNameEnum.DEPARTED),
                        createMilestone(MilestoneNameEnum.ARRIVED), createMilestone(MilestoneNameEnum.DELIVERED)));
    }

    @Test
    public void shouldCalculateFlightBasedOnPlans() {

        // given
        shipmentDetailsVO.setPlans(List.of(planDeparture, planArrival));
        shipmentDetailsVO.setEvents(emptyList());

        // when
        flightDateEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertEquals(shipmentDetailsVO.getDepartureTimePostfix(), SCHEDULED);
        Assertions.assertEquals(shipmentDetailsVO.getDepartureTime(), LocalDateTime.parse("2021-04-02T16:00"));
        Assertions.assertEquals(shipmentDetailsVO.getArrivalTimePostfix(), SCHEDULED);
        Assertions.assertEquals(shipmentDetailsVO.getArrivalTime(), LocalDateTime.parse("2021-04-02T17:00"));
    }

    @Test
    public void shouldCalculateFlightBasedOnPlansWithSplit() {

        // given
        shipmentDetailsVO.setPlans(List.of(planDeparture, planArrival, planArrivalSplit, planArrivalSplit));
        shipmentDetailsVO.setEvents(emptyList());

        // when
        flightDateEnricher.enrich(this.shipmentDetailsVO);

        // then
        Assertions.assertEquals(shipmentDetailsVO.getDepartureTimePostfix(), SCHEDULED);
        Assertions.assertEquals(shipmentDetailsVO.getDepartureTime(), LocalDateTime.parse("2021-04-02T16:00"));
        Assertions.assertEquals(shipmentDetailsVO.getArrivalTimePostfix(), SCHEDULED);
        Assertions.assertEquals(shipmentDetailsVO.getArrivalTime(), LocalDateTime.parse("2021-04-02T18:00"));
    }

    @Test
    public void shouldCalculateCompletedFlight() {

        // given
        shipmentDetailsVO.getMilestones().get(1).setStatus(MilestoneStatusEnum.DONE);
        shipmentDetailsVO.getMilestones().get(2).setStatus(MilestoneStatusEnum.DONE);
        shipmentDetailsVO.setPlans(List.of(planDeparture, planArrival));
        shipmentDetailsVO.setEvents(List.of(eventDeparture, eventArrival));

        // when
        flightDateEnricher.enrich(this.shipmentDetailsVO);

        // then
        Assertions.assertEquals(shipmentDetailsVO.getDepartureTimePostfix(), ACTUAL);
        Assertions.assertEquals(shipmentDetailsVO.getDepartureTime(), LocalDateTime.parse("2021-04-02T15:40"));
        Assertions.assertEquals(shipmentDetailsVO.getArrivalTimePostfix(), ACTUAL);
        Assertions.assertEquals(shipmentDetailsVO.getArrivalTime(), LocalDateTime.parse("2021-04-02T17:25"));
    }

    @Test
    public void shouldCalculatePartiallyCompletedFlight() {

        // given
        shipmentDetailsVO.getMilestones().get(1).setStatus(MilestoneStatusEnum.IN_PROGRESS);
        shipmentDetailsVO.getMilestones().get(2).setStatus(MilestoneStatusEnum.IN_PROGRESS);
        shipmentDetailsVO.setPlans(List.of(planDeparture, planDepartureSplit, planArrival, planArrivalSplit));
        shipmentDetailsVO.setEvents(List.of(eventDeparture, eventDepartureSplit, eventArrival, eventArrivalSplit));

        // when
        flightDateEnricher.enrich(shipmentDetailsVO);

        // then
        Assertions.assertEquals(ACTUAL, shipmentDetailsVO.getDepartureTimePostfix());
        Assertions.assertEquals(LocalDateTime.parse("2021-04-02T15:40"), shipmentDetailsVO.getDepartureTime());
        Assertions.assertEquals(SCHEDULED, shipmentDetailsVO.getArrivalTimePostfix());
        Assertions.assertEquals(LocalDateTime.parse("2021-04-02T18:00"), shipmentDetailsVO.getArrivalTime());
    }
}
