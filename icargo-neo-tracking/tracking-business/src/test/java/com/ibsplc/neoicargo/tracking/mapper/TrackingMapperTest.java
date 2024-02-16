package com.ibsplc.neoicargo.tracking.mapper;

import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneVO;
import com.ibsplc.neoicargo.tracking.vo.SplitDetailsItemVO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(JUnitPlatform.class)
public class TrackingMapperTest {

    private TrackingMapper trackingMapper;
    private Quantities quantities;

    @BeforeEach
    public void setup() {
        trackingMapper = Mappers.getMapper(TrackingMapper.class);
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        trackingMapper = MockQuantity.injectMapper(quantities, TrackingMapper.class);
    }

    @Test
    void shouldConstructShipmentDetailsModel() {
        // given
        var detailsVO = MockDataHelper.constructShipmentDetailsVO(quantities);

        // when
        var actualModel = trackingMapper.constructShipmentDetailsModel(detailsVO);

        // then
        assertEquals("13412345678", actualModel.getAwbNumber());
        assertEquals(50, actualModel.getPieces());
        assertEquals(20, actualModel.getStatedWeight());
        assertEquals(10, actualModel.getStatedVolume());
        assertEquals("L", actualModel.getUnitsOfMeasure().getWeight());
        assertEquals("F", actualModel.getUnitsOfMeasure().getVolume());
        assertEquals("GEN", actualModel.getSpecialHandlingCode());
        assertEquals("Books", actualModel.getProductName());
        assertEquals("Shipment Description", actualModel.getShipmentDescription());
        assertEquals("DXB", actualModel.getOriginAirportCode());
        assertEquals("SIN", actualModel.getDestinationAirportCode());

        assertEquals(4, actualModel.getMilestones().size());
        assertEquals("ACCEPTED", actualModel.getMilestones().get(0).getMilestone());
        assertEquals("DEPARTED", actualModel.getMilestones().get(1).getMilestone());
        assertEquals("ARRIVED", actualModel.getMilestones().get(2).getMilestone());
        assertEquals("DELIVERED", actualModel.getMilestones().get(3).getMilestone());
        assertEquals("done", actualModel.getMilestones().get(0).getStatus());
        assertEquals("done", actualModel.getMilestones().get(1).getStatus());
        assertEquals("in progress", actualModel.getMilestones().get(2).getStatus());
        assertEquals("to do", actualModel.getMilestones().get(3).getStatus());

        assertEquals("21-12-2021 13:29:28", actualModel.getDepartureTime());
        assertEquals("A", actualModel.getDepartureTimePostfix());
        assertEquals("21-12-2021 19:10:00", actualModel.getArrivalTime());
        assertEquals("S", actualModel.getArrivalTimePostfix());
        assertEquals(Lists.newArrayList("BEL", "BHM", "HSV"), actualModel.getTransitStations().getStops());
        assertEquals(4, actualModel.getTransitStations().getNumberOfFlights());
    }

    @Test
    void shouldConstructMilestoneModel() {

        // given
        var milestoneVO = new MilestoneVO();
        milestoneVO.setMilestone(MilestoneNameEnum.ARRIVED);
        milestoneVO.setStatus(MilestoneStatusEnum.TO_DO);

        // when
        var actualModel = trackingMapper.constructMilestoneModel(milestoneVO);

        // then
        assertEquals("ARRIVED", actualModel.getMilestone());
        assertEquals("to do", actualModel.getStatus());
    }

    @Test
    void shouldConstructSplitDetailsItem() {

        // given
        var splitDetailsItemVO = new SplitDetailsItemVO();
        splitDetailsItemVO.setMilestoneTime(LocalDateTime.of(2021, 12, 21, 13, 29, 28));


        // when
        var actualModel = trackingMapper.constructSplitDetailsItem(splitDetailsItemVO);

        // then
        assertEquals("21-12-2021 13:29:28", actualModel.getMilestoneTime());
    }

	@Test
	void shouldConstructShipmentActivityModel() {

        // given
        var activity = MockDataHelper.constructShipmentActivityVO();

        // when
        var actual = trackingMapper.constructShipmentActivity(activity);

		// then
		assertEquals(activity.getEvent().toString(), actual.getEvent());
		assertEquals(activity.getPieces(), actual.getPieces());
		assertEquals(activity.getAirportCode(), actual.getAirportCode());
		assertEquals("22 Mar 2022, 13:50", actual.getEventTime());
		assertEquals("22 Mar 2022, 11:50", actual.getEventTimeUTC());
		assertEquals(activity.getReason(), actual.getReason());
		assertEquals(activity.getFlightData().getFlightCarrierCode(), actual.getFlightData().getFlightCarrierCode());
		assertEquals(activity.getFlightData().getFlightNumber(), actual.getFlightData().getFlightNumber());
		assertEquals(activity.getFlightData().getOriginAirportCode(), actual.getFlightData().getOriginAirportCode());
		assertEquals(activity.getFlightData().getDestinationAirportCode(), actual.getFlightData().getDestinationAirportCode());
	}

    @Test
    void constructEmailRequestData() {
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        var body = "emailBody";
        var subject = "emailSubject";
        var fromAddress = "from@address.com";

        var emailRequestData = trackingMapper.constructEmailRequestData(fromAddress, notificationVO.getEmails(), subject, body);

        assertEquals(body, emailRequestData.getBody());
        assertEquals(fromAddress, emailRequestData.getFromAddress());
        assertEquals(notificationVO.getEmails(), emailRequestData.getToAddress());
        assertEquals(subject, emailRequestData.getSubject());
        assertNull(emailRequestData.getAttachments());
        assertNull(emailRequestData.getMultiPartType());
        assertNull(emailRequestData.getCcAddress());
        assertNull(emailRequestData.getBccAddress());

    }

	@Test
	void shouldConstructShipmentActivityVO() {

		// given
		var event = MockDataHelper.constructShipmentMilestoneEventVOWithWeight(quantities);

		// when
		var actual = trackingMapper.constructShipmentActivityVO(event);

		// then
		System.out.println(actual);
		assertEquals(5, actual.getPieces());
		assertEquals("DXB", actual.getAirportCode());
		assertEquals(event.getMilestoneTime(), actual.getEventTime());
		assertEquals(event.getMilestoneTimeUTC(), actual.getEventTimeUTC());
		assertEquals(1.2, actual.getWeight().getDisplayValue().doubleValue());
		assertEquals("K", actual.getWeight().getDisplayUnit().getName());
	}
}
