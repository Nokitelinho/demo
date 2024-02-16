package com.ibsplc.neoicargo.tracking.controller;

import com.ibsplc.neoicargo.awb.mapper.AwbMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.AuthorizedService;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.service.TrackingService;
import com.ibsplc.neoicargo.tracking.vo.FlightTimePostfixEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.TransitStationVO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.RCS;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DLV;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DEP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
public class TrackingControllerTest {

    private Quantities quantities;
    private static final LocalDateTime BASE_TIME = LocalDateTime.of(2021, 2, 14, 8, 45);
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Mock
    private TrackingService trackingService;

    private TrackingMapper trackingMapper;
    @Mock
    private AwbService awbService;

    private AwbMapper awbMapper;
    @Mock
    private ContextUtil contextUtil;

    @InjectMocks
    private TrackingController trackingController;

    @Captor
    ArgumentCaptor<List<AwbRequestVO>> awbRequestCaptor;

    @Captor
    ArgumentCaptor<AwbRequestVO> singleAwbRequestCaptor;
    
    @Mock
	private AuthorizedService authService;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        trackingMapper = MockQuantity.injectMapper(quantities, TrackingMapper.class);
        awbMapper = MockQuantity.injectMapper(quantities, AwbMapper.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnShipmentInfo() {
        // given
        var shipmentDetailsVO = new ShipmentDetailsVO();
        shipmentDetailsVO.setShipmentPrefix("020");
        shipmentDetailsVO.setMasterDocumentNumber("14514500");

        var shipmentDetailsVO_1 = new ShipmentDetailsVO();
        shipmentDetailsVO.setShipmentPrefix("020");
        shipmentDetailsVO.setMasterDocumentNumber("4546443");

        doReturn(Lists.newArrayList(shipmentDetailsVO, shipmentDetailsVO_1)).when(trackingService).getShipments(anyList());

        // when
        trackingController.getShipmentDetails(Lists.newArrayList("020-14514500", "020-4546443"));

        // then
        Mockito.verify(trackingService).getShipments(awbRequestCaptor.capture());

        var value = awbRequestCaptor.getValue();

        assertEquals(value.get(0).getShipmentPrefix(), "020");
        assertEquals(value.get(0).getMasterDocumentNumber(), "14514500");

        assertEquals(value.get(1).getShipmentPrefix(), "020");
        assertEquals(value.get(1).getMasterDocumentNumber(), "4546443");
    }

    @Test
    public void shouldReturnNothingIfEmpty() {

        // when
        var shipmentDetails = trackingController.getShipmentDetails(new ArrayList<>());

        // then
        assertEquals(shipmentDetails.size(), 0);
    }

    @Test
    public void shouldReturnProperlyMappedShipmentInfo() {
        // given
        doReturn(Lists.newArrayList(createShipmentVO("02014514500"))).when(trackingService).getShipments(anyList());

        // when
        var shipmentDetails = trackingController.getShipmentDetails(Lists.newArrayList("020-14514500"));

        // then
        Mockito.verify(trackingService).getShipments(awbRequestCaptor.capture());

        var value = awbRequestCaptor.getValue();

        assertEquals("020", value.get(0).getShipmentPrefix());
        assertEquals("14514500", value.get(0).getMasterDocumentNumber());
        var awbRequestVO = new AwbRequestVO("020-14514500");
        assertEquals(value.get(0).getShipmentPrefix(), awbRequestVO.getShipmentPrefix());
        assertEquals(value.get(0).getMasterDocumentNumber(), awbRequestVO.getMasterDocumentNumber());

        assertEquals(shipmentDetails.size(), 1);
        var shipmentDetailsModel = shipmentDetails.get(0);
        assertEquals(shipmentDetailsModel.getAwbNumber(), "02014514500");
        assertEquals(shipmentDetailsModel.getPieces(), 100);
        assertEquals(shipmentDetailsModel.getStatedWeight(), 12.34);
        assertEquals(shipmentDetailsModel.getStatedVolume(), 14.2);
        assertEquals(shipmentDetailsModel.getSpecialHandlingCode(), "GEN");
        assertEquals(shipmentDetailsModel.getProductName(), "Books");
        assertEquals(shipmentDetailsModel.getShipmentDescription(), "Shipment Description");
        assertEquals(shipmentDetailsModel.getOriginAirportCode(), "KBP");
        assertEquals(shipmentDetailsModel.getDestinationAirportCode(), "FRA");
        assertEquals(shipmentDetailsModel.getDestinationAirportCode(), "FRA");

        assertEquals(shipmentDetailsModel.getMilestones().size(), 4);
        assertEquals(shipmentDetailsModel.getMilestones().get(0).getMilestone(), "ACCEPTED");
        assertEquals(shipmentDetailsModel.getMilestones().get(1).getMilestone(), "DEPARTED");
        assertEquals(shipmentDetailsModel.getMilestones().get(2).getMilestone(), "ARRIVED");
        assertEquals(shipmentDetailsModel.getMilestones().get(3).getMilestone(), "DELIVERED");
        assertEquals(shipmentDetailsModel.getMilestones().get(0).getStatus(), "done");
        assertEquals(shipmentDetailsModel.getMilestones().get(1).getStatus(), "done");
        assertEquals(shipmentDetailsModel.getMilestones().get(2).getStatus(), "in progress");
        assertEquals(shipmentDetailsModel.getMilestones().get(3).getStatus(), "to do");

        assertEquals(shipmentDetailsModel.getDepartureTime(), BASE_TIME.minusDays(1).format(format));
        assertEquals(shipmentDetailsModel.getDepartureTimePostfix(), "S");
        assertEquals(shipmentDetailsModel.getArrivalTime(), BASE_TIME.plusDays(1).format(format));
        assertEquals(shipmentDetailsModel.getArrivalTimePostfix(), "S");
        assertEquals(shipmentDetailsModel.getTransitStations().getStops(), Lists.newArrayList("BEL", "BHM", "HSV"));
        assertEquals(shipmentDetailsModel.getTransitStations().getNumberOfFlights(), 4);
    }

    @Nested
    @DisplayName("Get Shipper and Consignee details")
    class getShipperConsigneeDetails {
        @Test
        void shouldGetShipperConsigneeDetailsAll() {
            //given
            var awb = "123-45678910";
            var shipperDetails = new HashMap<String, String>();
            shipperDetails.put("name", "shipper contact name");
            shipperDetails.put("address", "shipper address");
            shipperDetails.put("country", "shipper country");
            shipperDetails.put("city", "shipper city");
            shipperDetails.put("state", "shipper state");
            shipperDetails.put("zip_code", "shipper zip code");

            var consigneeDetails = new HashMap<String, String>();
            consigneeDetails.put("name", "consignee contact name");
            consigneeDetails.put("address", "consignee address");
            consigneeDetails.put("country", "consignee country");
            consigneeDetails.put("city", "consignee city");
            consigneeDetails.put("state", "consignee state");
            consigneeDetails.put("zip_code", "consignee zip code");

            var awbContactDetails = new AwbContactDetailsVO();
            awbContactDetails.setShipperCode("DHLABRAJ");
            awbContactDetails.setShipperDetails(shipperDetails);
            awbContactDetails.setConsigneeCode("CCBEY");
            awbContactDetails.setConsigneeDetails(consigneeDetails);

            var awbRequestVOCaptor = ArgumentCaptor.forClass(AwbRequestVO.class);

            //when
            when(awbService.getAwbContactDetails(any(AwbRequestVO.class))).thenReturn(awbContactDetails);
            var shipperConsigneeDetails = trackingController.getShipperConsigneeDetails(awb);

            //then
            verify(awbService).getAwbContactDetails(awbRequestVOCaptor.capture());
            assertEquals(awbRequestVOCaptor.getValue().getShipmentPrefix() + "-" + awbRequestVOCaptor.getValue().getMasterDocumentNumber(), awb);

            var consignee
                    = shipperConsigneeDetails.getConsigneeDetails();
            assertEquals("CCBEY", consignee.getCode());
            assertEquals("consignee city", consignee.getCity());
            assertEquals("consignee country", consignee.getCountry());
            assertEquals("consignee address", consignee.getAddress());
            assertEquals("consignee contact name", consignee.getName());
            assertEquals("consignee zip code", consignee.getZipCode());


            var shipper
                    = shipperConsigneeDetails.getShipperDetails();
            assertEquals("DHLABRAJ", shipper.getCode());
            assertEquals("shipper city", shipper.getCity());
            assertEquals("shipper country", shipper.getCountry());
            assertEquals("shipper address", shipper.getAddress());
            assertEquals("shipper contact name", shipper.getName());
            assertEquals("shipper zip code", shipper.getZipCode());
        }

        @Test
        void shouldGetShipperConsigneeWithoutDetails() {
            //given
            var awb = "123-45678910";
            var awbContactDetails = new AwbContactDetailsVO();
            awbContactDetails.setShipperCode("DHLABRAJ");
            awbContactDetails.setConsigneeCode("CCBEY");

            var awbRequestVOCaptor = ArgumentCaptor.forClass(AwbRequestVO.class);

            //when
            when(awbService.getAwbContactDetails(any(AwbRequestVO.class))).thenReturn(awbContactDetails);
            var shipperConsigneeDetails = trackingController.getShipperConsigneeDetails(awb);

            //then
            verify(awbService).getAwbContactDetails(awbRequestVOCaptor.capture());
            assertEquals(awbRequestVOCaptor.getValue().getShipmentPrefix() + "-" + awbRequestVOCaptor.getValue().getMasterDocumentNumber(), awb);

            var consignee
                    = shipperConsigneeDetails.getConsigneeDetails();

            assertEquals("CCBEY", consignee.getCode());
            assertNull(consignee.getCity());
            assertNull(consignee.getCountry());
            assertNull(consignee.getAddress());
            assertNull(consignee.getName());
            assertNull(consignee.getZipCode());

            var shipper
                    = shipperConsigneeDetails.getShipperDetails();
            assertEquals("DHLABRAJ", shipper.getCode());

            assertNull(shipper.getCity());
            assertNull(shipper.getCountry());
            assertNull(shipper.getAddress());
            assertNull(shipper.getName());
            assertNull(shipper.getZipCode());
        }

        @Test
        void shouldGetShipperConsigneeDetailsPartially() {
            //given
            var awb = "123-45678910";

            var awbContactDetails = new AwbContactDetailsVO();
            awbContactDetails.setShipperCode("DHLABRAJ");
            awbContactDetails.setConsigneeCode("CCBEY");
            awbContactDetails.setShipperDetails(new HashMap<>());
            awbContactDetails.setConsigneeDetails(new HashMap<>());

            var awbRequestVOCaptor = ArgumentCaptor.forClass(AwbRequestVO.class);

            //when
            when(awbService.getAwbContactDetails(any(AwbRequestVO.class))).thenReturn(awbContactDetails);
            var shipperConsigneeDetails = trackingController.getShipperConsigneeDetails(awb);

            //then
            verify(awbService).getAwbContactDetails(awbRequestVOCaptor.capture());
            assertEquals(awbRequestVOCaptor.getValue().getShipmentPrefix() + "-" + awbRequestVOCaptor.getValue().getMasterDocumentNumber(), awb);

            var consignee
                    = shipperConsigneeDetails.getConsigneeDetails();

            assertEquals("CCBEY", consignee.getCode());
            assertEquals("", consignee.getCity());
            assertEquals("", consignee.getCountry());
            assertEquals("", consignee.getAddress());
            assertEquals("", consignee.getName());
            assertEquals("", consignee.getZipCode());

            var shipper
                    = shipperConsigneeDetails.getShipperDetails();
            assertEquals("DHLABRAJ", shipper.getCode());

            assertEquals("", shipper.getCity());
            assertEquals("", shipper.getCountry());
            assertEquals("", shipper.getAddress());
            assertEquals("", shipper.getName());
            assertEquals("", shipper.getZipCode());
        }
    }

    @Test
    public void shouldReturnShipmentActivities() {
        // given
        var awb = "123-45678910";
        doReturn(Lists.newArrayList(new ShipmentActivityVO())).when(trackingService).getShipmentActivities(any(AwbRequestVO.class));
        var awbRequestVOCaptor = ArgumentCaptor.forClass(AwbRequestVO.class);

        // when
        var activities = trackingController.getShipmentActivities(awb);

        // then
        verify(trackingService).getShipmentActivities(awbRequestVOCaptor.capture());
        assertEquals(awbRequestVOCaptor.getValue().getShipmentPrefix() + "-" + awbRequestVOCaptor.getValue().getMasterDocumentNumber(), awb);
        assertFalse(activities.isEmpty());
    }

        private ShipmentDetailsVO createShipmentVO(String awb) {

        var shipmentDetailsVO_1 = new ShipmentDetailsVO();
        shipmentDetailsVO_1.setShipmentPrefix(awb.substring(0, 3));
        shipmentDetailsVO_1.setMasterDocumentNumber(awb.substring(3));
        shipmentDetailsVO_1.setPieces(100);
        shipmentDetailsVO_1.setWeight(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(12.34), BigDecimal.valueOf(12.34), "K"));
        shipmentDetailsVO_1.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(14.2), BigDecimal.valueOf(14.2), "B"));
        shipmentDetailsVO_1.setSpecialHandlingCode("GEN");
        shipmentDetailsVO_1.setProductName("Books");
        shipmentDetailsVO_1.setShipmentDescription("Shipment Description");
        shipmentDetailsVO_1.setOriginAirportCode("KBP");
        shipmentDetailsVO_1.setDestinationAirportCode("FRA");

        var milestoneVO_1 = new MilestoneVO();
        milestoneVO_1.setMilestone(MilestoneNameEnum.ACCEPTED);
        milestoneVO_1.setStatus(MilestoneStatusEnum.DONE);

        var milestoneVO_2 = new MilestoneVO();
        milestoneVO_2.setMilestone(MilestoneNameEnum.DEPARTED);
        milestoneVO_2.setStatus(MilestoneStatusEnum.DONE);

        var milestoneVO_3 = new MilestoneVO();
        milestoneVO_3.setMilestone(MilestoneNameEnum.ARRIVED);
        milestoneVO_3.setStatus(MilestoneStatusEnum.IN_PROGRESS);

        var milestoneVO_4 = new MilestoneVO();
        milestoneVO_4.setMilestone(MilestoneNameEnum.DELIVERED);
        milestoneVO_4.setStatus(MilestoneStatusEnum.TO_DO);

        shipmentDetailsVO_1.setMilestones(com.google.common.collect.Lists.newArrayList(milestoneVO_1, milestoneVO_2, milestoneVO_3, milestoneVO_4));

        shipmentDetailsVO_1.setDepartureTime(BASE_TIME.minusDays(1));
        shipmentDetailsVO_1.setDepartureTimePostfix(FlightTimePostfixEnum.SCHEDULED);
        shipmentDetailsVO_1.setArrivalTime(BASE_TIME.plusDays(1));
        shipmentDetailsVO_1.setArrivalTimePostfix(FlightTimePostfixEnum.SCHEDULED);
        var transitStationVO_1 = new TransitStationVO(4, Lists.newArrayList("BEL", "BHM", "HSV"));

        shipmentDetailsVO_1.setTransitStations(transitStationVO_1);

        return shipmentDetailsVO_1;
    }

    @Test
    public void shouldReturnShipmentSplits() {
        // given
        doReturn(MockDataHelper.constructShipmentMilestoneSplits_1())
                .when(trackingService).getShipmentSplits(any(AwbRequestVO.class));

        // when
        var splits = trackingController.getShipmentSplits("020-6666666");

        // then
        Assertions.assertNotNull(splits);
        Assertions.assertEquals(3, splits.size());

        Mockito.verify(trackingService).getShipmentSplits(singleAwbRequestCaptor.capture());

        var value = singleAwbRequestCaptor.getValue();
        assertEquals("020", value.getShipmentPrefix());
        assertEquals("6666666", value.getMasterDocumentNumber());

    }

    @Nested
    @DisplayName("Awb user notifications")
    class awbUserNotification {

        @Test
        void shouldGetUserAwbNotifications() {
            var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
            var milestones = MockDataHelper.constructMilestoneVO();
            var awbRequestVOCaptor = ArgumentCaptor.forClass(AwbRequestVO.class);
            var awbString = "123-45678910";
            when(trackingService.findAllMilestones()).thenReturn(milestones);
            when(awbService.getAwbUserNotifications(any(AwbRequestVO.class))).thenReturn(notificationVO);
           

            var userAwbNotifications = trackingController.getUserAwbNotifications(awbString);

            assertEquals(Map.of("ARR", false, "RCS", true, "DEP", true, "DLV", false), userAwbNotifications.getNotifications());
            assertEquals(notificationVO.getEmails(), userAwbNotifications.getEmails());
            assertEquals(notificationVO.getNotificationsKey().getTrackingAwbSerialNumber(), userAwbNotifications.getTrackingAwbSerialNumber());

            verify(awbService).getAwbUserNotifications(awbRequestVOCaptor.capture());

            assertEquals(awbString.split("-")[1], awbRequestVOCaptor.getValue().getMasterDocumentNumber());
            assertEquals(awbString.split("-")[0], awbRequestVOCaptor.getValue().getShipmentPrefix());
        }

        @Test
        void shouldSaveUserAwbNotifications() {
            var notificationModel = MockDataHelper.constructAwbUserNotificationModel();
            var awbNotificationVOCaptor = ArgumentCaptor.forClass(AwbUserNotificationVO.class);
            var milestones = MockDataHelper.constructMilestoneVO();
            var awbString = "123-45678910";
            when(trackingService.findAllMilestones()).thenReturn(milestones);
            doNothing().when(awbService).saveAwbUserNotifications((any(AwbUserNotificationVO.class)));

            var userAwbNotifications = trackingController.saveUserAwbNotifications(awbString, notificationModel);

            assertEquals(notificationModel.getNotifications(), userAwbNotifications.getNotifications());
            assertEquals(notificationModel.getEmails(), userAwbNotifications.getEmails());
            assertNull(userAwbNotifications.getTrackingAwbSerialNumber());

            verify(awbService).saveAwbUserNotifications(awbNotificationVOCaptor.capture());

            assertTrue(List.of(RCS.getLabel(), DLV.getLabel()).containsAll(awbNotificationVOCaptor.getValue().getNotificationMilestones()));
            assertEquals(notificationModel.getEmails(), awbNotificationVOCaptor.getValue().getEmails());
        }

        @Test
        void shouldDeleteUserAwbNotifications() {
            var notificationModel = MockDataHelper.constructAwbUserNotificationModel();
            var keyVOCaptor = ArgumentCaptor.forClass(AwbUserNotificationKeyVO.class);
            var trackingAwbSerialNumber = 2L;
            var loginProfile = new LoginProfile();
            loginProfile.setUserId("userId");

            doNothing().when(awbService).deleteAwbUserNotifications(any(AwbUserNotificationKeyVO.class));
            when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);

            trackingController.deleteUserAwbNotifications(trackingAwbSerialNumber);

            verify(awbService).deleteAwbUserNotifications(keyVOCaptor.capture());

            assertEquals(trackingAwbSerialNumber, keyVOCaptor.getValue().getTrackingAwbSerialNumber());
            assertEquals(loginProfile.getUserId(), keyVOCaptor.getValue().getUserId());
        }
        
        @Test
        public void shouldReturnMilestones() {
            // given
            doReturn(MockDataHelper.constructMilestoneVO())
                    .when(trackingService).findAllMilestones();
            // when
            var milestones = trackingController.findAllMilestones();

            // then
            Assertions.assertNotNull(milestones);

           

        }

    }
}