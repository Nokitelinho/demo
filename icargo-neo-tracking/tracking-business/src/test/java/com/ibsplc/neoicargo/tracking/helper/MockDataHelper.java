package com.ibsplc.neoicargo.tracking.helper;

import com.ibsplc.neoicargo.awb.dao.entity.AWBContactDetails;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.events.AWBContactDetailEvent;
import com.ibsplc.neoicargo.awb.events.AWBCreatedEvent;
import com.ibsplc.neoicargo.awb.events.AWBDeletedEvent;
import com.ibsplc.neoicargo.awb.events.AWBExecutedEvent;
import com.ibsplc.neoicargo.awb.events.AWBReopenedEvent;
import com.ibsplc.neoicargo.awb.events.AWBUpdatedEvent;
import com.ibsplc.neoicargo.awb.events.AWBVoidedEvent;
import com.ibsplc.neoicargo.awb.model.AwbUserNotificationModel;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbStatusEnum;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanCreatedEvent;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanDeletedEvent;
import com.ibsplc.neoicargo.qms.events.ShipmentMilestonePlanDetailsEvent;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import com.ibsplc.neoicargo.tracking.dao.entity.Milestone;
import com.ibsplc.neoicargo.tracking.events.ShipmentHistoryEvent;
import com.ibsplc.neoicargo.tracking.vo.ActualFlightDataVO;
import com.ibsplc.neoicargo.tracking.vo.EmailMilestoneNotificationVO;
import com.ibsplc.neoicargo.tracking.vo.FlightTimePostfixEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;
import com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityFlightVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import com.ibsplc.neoicargo.tracking.vo.SplitDetailsItemVO;
import com.ibsplc.neoicargo.tracking.vo.SplitVO;
import com.ibsplc.neoicargo.tracking.vo.TransitStationVO;
import lombok.experimental.UtilityClass;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.ARR;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DEP;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DLV;
import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.RCF;

@UtilityClass
public class MockDataHelper {

    private final static LocalDate FLIGHT_DATE = LocalDate.parse("2000-01-01");
    private final static String MILESTONE_RCS = "RCS";
    private final static String MILESTONE_ARR = "ARR";
    private final static String MILESTONE_DEP = "DEP";
    private final static String MILESTONE_DLV = "DLV";
    

    public Awb constructAwbEntity(ShipmentKey shipmentkey, Quantities quantities) {
        return constructAwbEntity(1L, shipmentkey, quantities);
    }

    public Awb constructAwbEntity(Long serialNumber, ShipmentKey shipmentkey, Quantities quantities) {
        var awb = new Awb();
        awb.setShipmentKey(shipmentkey);
        awb.setShipmentSequenceNumber(1L);
        awb.setStatedPieces(100);
        awb.setSpecialHandlingCode("GEN");
        awb.setProductName("Toys");
        awb.setShipmentDescription("Shipment Description");
        awb.setOrigin("KPB");
        awb.setDestination("MUC");
        awb.setStatedWeight(100.0);
        awb.setStatedVolume(1.1);
        EUnitOfQuantity eUnit = new EUnitOfQuantity();
        eUnit.setWeight("K");
        eUnit.setVolume("B");
        awb.setEUnit(eUnit);
        awb.setLastUpdatedUser("ICARGO");
        awb.setLastUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
        awb.setCompanyCode("AV");
        awb.setAwbStatus(AwbStatusEnum.NEW.getValue());
        var trackingawbContactDetails = constructawbContactDetails();
        awb.setAwbContactDetails(trackingawbContactDetails);
        trackingawbContactDetails.setAwb(awb);
        return awb;
    }

    private AWBContactDetails constructawbContactDetails() {
        var awbContactDetails = new AWBContactDetails();
        awbContactDetails.setCompanyCode("AV");
        awbContactDetails.setShipperCode("SC");
        awbContactDetails.setConsigneeCode("CC");
        awbContactDetails.setLastUpdatedUser("ICARGO");
        return awbContactDetails;
    }

    public AwbVO constructAwbVO(String shipmentPrefix, String masterDocumentNumber, Quantities quantities) {
        return constructAwbVO(shipmentPrefix, masterDocumentNumber, quantities, "KPB", "MUC", 100);
    }

    public AwbVO constructAwbVO(String shipmentPrefix, String masterDocumentNumber, Quantities quantities, String origin, String destination, Integer pieces) {
        var awbVO = new AwbVO();
        awbVO.setShipmentSequenceNumber(1L);
        awbVO.setShipmentPrefix(shipmentPrefix);
        awbVO.setMasterDocumentNumber(masterDocumentNumber);
        awbVO.setStatedPieces(pieces);
        awbVO.setSpecialHandlingCode("GEN");
        awbVO.setProductName("Toys");
        awbVO.setShipmentDescription("Shipment Description");
        awbVO.setOrigin(origin);
        awbVO.setDestination(destination);
        awbVO.setStatedWeight(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(175.0)));
        awbVO.setStatedVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(7.0)));
        awbVO.setLastUpdatedUser("ICARGO");
        awbVO.setLastUpdatedTime(LocalDateTime.now());
        awbVO.setCompanyCode("AV");
        awbVO.setAwbContactDetailsVO(constructTrackingAwbContactDetailsVO());
        awbVO.setAwbStatus(AwbStatusEnum.NEW.getValue());
        return awbVO;
    }

    private AwbContactDetailsVO constructTrackingAwbContactDetailsVO() {
        var awbContactDetailsVO = new AwbContactDetailsVO();
        awbContactDetailsVO.setCompanyCode("AV");
        awbContactDetailsVO.setShipperCode("SC");
        awbContactDetailsVO.setConsigneeCode("CC");
        awbContactDetailsVO.setLastUpdatedUser("ICARGO");
        return awbContactDetailsVO;
    }

    public List<ShipmentMilestonePlan> constructTrackingShipmentMilestonePlanEntities() {
        var plan_1 = new ShipmentMilestonePlan();
        plan_1.setShipmentKey("020-222222");
        plan_1.setAirportCode("DXB");
        plan_1.setFlightNumber("1");
        plan_1.setFlightDate(FLIGHT_DATE);
        plan_1.setMilestoneCode("RCS");
        plan_1.setShipmentSequenceNumber(11);
        plan_1.setPieces(10);
        plan_1.setCompanyCode("AV");
        var plan_2 = new ShipmentMilestonePlan();
        plan_2.setShipmentKey("020-222222");
        plan_2.setAirportCode("DXB");
        plan_2.setFlightNumber("2");
        plan_2.setFlightDate(FLIGHT_DATE);
        plan_2.setMilestoneCode("ARR");
        plan_2.setShipmentSequenceNumber(11);
        plan_2.setPieces(10);
        plan_2.setCompanyCode("AV");
        var plan_3 = new ShipmentMilestonePlan();
        plan_3.setShipmentKey("020-222222");
        plan_3.setAirportCode("KPB");
        plan_3.setFlightNumber("3");
        plan_3.setFlightDate(FLIGHT_DATE);
        plan_3.setMilestoneCode("DLV");
        plan_3.setPieces(10);
        plan_3.setShipmentSequenceNumber(11);
        plan_3.setCompanyCode("AV");
        var plan_4 = new ShipmentMilestonePlan();
        plan_4.setShipmentKey("020-222222");
        plan_4.setAirportCode("IST");
        plan_4.setFlightNumber("4");
        plan_4.setFlightDate(FLIGHT_DATE);
        plan_4.setMilestoneCode("DEP");
        plan_4.setShipmentSequenceNumber(11);
        plan_4.setPieces(5);
        plan_4.setCompanyCode("AV");
        var plan_5 = new ShipmentMilestonePlan();
        plan_5.setShipmentKey("020-222222");
        plan_5.setAirportCode("FRA");
        plan_5.setFlightNumber("5");
        plan_5.setFlightDate(FLIGHT_DATE);
        plan_5.setMilestoneCode("RCS");
        plan_5.setShipmentSequenceNumber(11);
        plan_5.setPieces(5);
        plan_5.setCompanyCode("AV");
        var plan_6 = new ShipmentMilestonePlan();
        plan_6.setShipmentKey("020-222222");
        plan_6.setAirportCode("ROM");
        plan_6.setFlightNumber("6");
        plan_6.setFlightDate(FLIGHT_DATE);
        plan_6.setMilestoneCode("RCS");
        plan_6.setShipmentSequenceNumber(11);
        plan_6.setPieces(5);
        plan_6.setCompanyCode("AV");
        var plan_7 = new ShipmentMilestonePlan();
        plan_7.setShipmentKey("020-222222");
        plan_7.setAirportCode("STO");
        plan_7.setFlightNumber("7");
        plan_7.setFlightDate(FLIGHT_DATE);
        plan_7.setMilestoneCode("RCS");
        plan_7.setPieces(5);
        plan_7.setShipmentSequenceNumber(11);
        plan_7.setCompanyCode("AV");
        var plan_8 = new ShipmentMilestonePlan();
        plan_8.setAirportCode("SIN");
        plan_8.setShipmentKey("020-222222");
        plan_8.setFlightNumber("1");
        plan_8.setFlightDate(FLIGHT_DATE);
        plan_8.setMilestoneCode("RCS");
        plan_8.setPieces(5);
        plan_8.setShipmentSequenceNumber(11);
        plan_8.setCompanyCode("AV");
        return List.of(plan_1, plan_2, plan_3, plan_4, plan_5, plan_6, plan_7, plan_8);
    }

    public static AWBCreatedEvent constructAWBCreatedEvent(String shipmentPrefix, String masterDocumentNumber) {
        var awbCreatedEvent = new AWBCreatedEvent();

        awbCreatedEvent.setShipmentPrefix(shipmentPrefix);
        awbCreatedEvent.setMasterDocumentNumber(masterDocumentNumber);
        awbCreatedEvent.setAwbStatus("N");
        awbCreatedEvent.setOrigin("DFW");
        awbCreatedEvent.setDestination("LHR");
        awbCreatedEvent.setShipmentDescription("CLOTHES");
        awbCreatedEvent.setSpecialHandlingCode("GEN");
        awbCreatedEvent.setShipperCode("DHL");
        awbCreatedEvent.setConsigneeCode("DHLLHR");
        awbCreatedEvent.setLastUpdatedUser("GHAADMIN");
        awbCreatedEvent.setLastUpdatedTime("15-08-2021 10:30:00.187");
        awbCreatedEvent.setStatedPieces(10);
        awbCreatedEvent.setStatedWeight(100.0);
        awbCreatedEvent.setStatedVolume(10.0);
        awbCreatedEvent.setAwbContacts(constructContacts());
        awbCreatedEvent.setUnitsOfMeasure(constructUnits());

        return awbCreatedEvent;
    }

    public static AWBExecutedEvent constructAWBExecutedEvent(String shipmentPrefix, String masterDocumentNumber) {
        var awbExecutedEvent = new AWBExecutedEvent();

        awbExecutedEvent.setShipmentPrefix(shipmentPrefix);
        awbExecutedEvent.setMasterDocumentNumber(masterDocumentNumber);
        awbExecutedEvent.setAwbStatus("E");
        awbExecutedEvent.setOrigin("DFW");
        awbExecutedEvent.setDestination("LHR");
        awbExecutedEvent.setShipmentDescription("CLOTHES");
        awbExecutedEvent.setSpecialHandlingCode("GEN");
        awbExecutedEvent.setShipperCode("DHL");
        awbExecutedEvent.setConsigneeCode("DHLLHR");
        awbExecutedEvent.setLastUpdatedUser("GHAADMIN");
        awbExecutedEvent.setLastUpdatedTime("15-08-2021 10:30:00.187");
        awbExecutedEvent.setStatedPieces(10);
        awbExecutedEvent.setStatedWeight(100.0);
        awbExecutedEvent.setStatedVolume(10.0);
        awbExecutedEvent.setAwbContacts(constructContacts());
        awbExecutedEvent.setUnitsOfMeasure(constructUnits());

        return awbExecutedEvent;
    }

    public static AWBUpdatedEvent constructAWBUpdatedEvent(String shipmentPrefix, String masterDocumentNumber) {
        var awbUpdatedEvent = new AWBUpdatedEvent();

        awbUpdatedEvent.setShipmentPrefix(shipmentPrefix);
        awbUpdatedEvent.setMasterDocumentNumber(masterDocumentNumber);
        awbUpdatedEvent.setAwbStatus("N");
        awbUpdatedEvent.setOrigin("DFW");
        awbUpdatedEvent.setDestination("LHR");
        awbUpdatedEvent.setShipmentDescription("CLOTHES");
        awbUpdatedEvent.setSpecialHandlingCode("GEN");
        awbUpdatedEvent.setShipperCode("DHL");
        awbUpdatedEvent.setConsigneeCode("DHLLHR");
        awbUpdatedEvent.setLastUpdatedUser("GHAADMIN");
        awbUpdatedEvent.setLastUpdatedTime("15-08-2021 10:30:00.187");
        awbUpdatedEvent.setStatedPieces(10);
        awbUpdatedEvent.setStatedWeight(100.0);
        awbUpdatedEvent.setStatedVolume(10.0);
        awbUpdatedEvent.setAwbContacts(constructContacts());
        awbUpdatedEvent.setUnitsOfMeasure(constructUnits());

        return awbUpdatedEvent;
    }

    private static List<AWBContactDetailEvent> constructContacts() {
        List<AWBContactDetailEvent> contacts = new ArrayList<>();
        AWBContactDetailEvent contact = new AWBContactDetailEvent();

        contact.setAccountNumber("100100");
        contact.setAddress("XXX");
        contact.setBillingAddress("YYY");
        contact.setCity("Dallas");
        contact.setContactCode("100");
        contact.setContactName("DHLDFW");
        contact.setContactType("SHP");
        contact.setCountry("US");
        contact.setEmail("dhl@dhl.com");
        contact.setFaxNumber("100100100");
        contact.setIataCode("1234");
        contact.setMobileNumber("200200200");
        contact.setPhoneNumber("300300300");
        contact.setState("TX");
        contact.setZipCode("1234");

        contacts.add(contact);
        return contacts;
    }

    public AWBReopenedEvent constructAWBReopenedEvent(String shipmentPrefix, String masterDocumentNumber) {
        var awbReopenedEvent = new AWBReopenedEvent();
        awbReopenedEvent.setShipmentPrefix(shipmentPrefix);
        awbReopenedEvent.setMasterDocumentNumber(masterDocumentNumber);
        return awbReopenedEvent;
    }

    public AWBVoidedEvent constructAwbVoidedEvent(String shipmentPrefix, String masterDocumentNumber) {
        var awbVoidedEvent = new AWBVoidedEvent();
        awbVoidedEvent.setShipmentPrefix(shipmentPrefix);
        awbVoidedEvent.setMasterDocumentNumber(masterDocumentNumber);
        return awbVoidedEvent;
    }

    public AWBDeletedEvent constructAwbDeletedEvent(String shipmentPrefix, String masterDocumentNumber) {
        var awbDeletedEvent = new AWBDeletedEvent();
        awbDeletedEvent.setShipmentPrefix(shipmentPrefix);
        awbDeletedEvent.setMasterDocumentNumber(masterDocumentNumber);
        return awbDeletedEvent;
    }

    public AwbValidationVO constructAwbValidationVO(String shipmentPrefix, String masterDocumentNumber) {
        var trackingAWBMasterValidationVO = new AwbValidationVO();
        trackingAWBMasterValidationVO.setShipmentPrefix(shipmentPrefix);
        trackingAWBMasterValidationVO.setMasterDocumentNumber(masterDocumentNumber);
        return trackingAWBMasterValidationVO;
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventEntities() {
        var event_1 = new ShipmentMilestoneEvent();
        event_1.setShipmentKey("020-222222");
        event_1.setAirportCode("DXB");
        event_1.setMilestoneCode("RCS");
        var event_2 = new ShipmentMilestoneEvent();
        event_2.setShipmentKey("020-222222");
        event_2.setAirportCode("MUC");
        event_2.setMilestoneCode("ARR");
        event_2.setPieces(10);
        var event_3 = new ShipmentMilestoneEvent();
        event_3.setShipmentKey("020-222222");
        event_3.setAirportCode("KPB");
        event_3.setMilestoneCode("DLV");
        var event_4 = new ShipmentMilestoneEvent();
        event_4.setShipmentKey("020-222222");
        event_4.setAirportCode("IST");
        event_4.setMilestoneCode("RCS");
        return List.of(event_1, event_2, event_3, event_4);
    }

    private static Units constructUnits() {
        var units = new Units();
        units.setWeight("K");
        units.setLength("C");
        units.setVolume("F");
        return units;
    }

    public ShipmentMilestonePlanCreatedVO constructShipmentMilestonePlanCreatedVO() {
        var planCreatedVO = new ShipmentMilestonePlanCreatedVO();
        planCreatedVO.setPlanVOs(constructShipmentMilestonePlanVOs());
        return planCreatedVO;
    }

    public List<ShipmentMilestonePlanVO> constructShipmentMilestonePlanVOs() {
        var shipmentMilestonePlanVO1 = new ShipmentMilestonePlanVO();
        shipmentMilestonePlanVO1.setShipmentKey("020-111111");
        shipmentMilestonePlanVO1.setShipmentType("A");
        shipmentMilestonePlanVO1.setAirportCode("SIN");
        shipmentMilestonePlanVO1.setOperationType("I");
        shipmentMilestonePlanVO1.setMilestoneCode(ARR);
        shipmentMilestonePlanVO1.setFlightCarrierCode("AV");
        shipmentMilestonePlanVO1.setFlightNumber("777");
        shipmentMilestonePlanVO1.setMilestoneTime(LocalDateTime.parse("2022-02-23T06:00:00"));
        shipmentMilestonePlanVO1.setMilestoneTimeUTC(LocalDateTime.parse("2022-02-22T22:00:00"));
        shipmentMilestonePlanVO1.setFlightDate(LocalDate.parse("2022-02-23"));
        shipmentMilestonePlanVO1.setCompanyCode("CODE");
        shipmentMilestonePlanVO1.setPieces(1);

        var shipmentMilestonePlanVO2 = new ShipmentMilestonePlanVO();
        shipmentMilestonePlanVO2.setShipmentKey("020-111111");
        shipmentMilestonePlanVO2.setShipmentType("A");
        shipmentMilestonePlanVO2.setAirportCode("KYV");
        shipmentMilestonePlanVO2.setOperationType("E");
        shipmentMilestonePlanVO2.setMilestoneCode(DLV);
        shipmentMilestonePlanVO2.setFlightCarrierCode("AV");
        shipmentMilestonePlanVO2.setFlightNumber("777");
        shipmentMilestonePlanVO2.setMilestoneTime(LocalDateTime.parse("2022-02-23T07:00:00"));
        shipmentMilestonePlanVO2.setMilestoneTimeUTC(LocalDateTime.parse("2022-02-22T22:00:00"));
        shipmentMilestonePlanVO2.setFlightDate(LocalDate.parse("2022-02-23"));
        shipmentMilestonePlanVO2.setCompanyCode("CODE");
        shipmentMilestonePlanVO2.setPieces(12);

        return List.of(shipmentMilestonePlanVO1, shipmentMilestonePlanVO2);
    }

    public ShipmentMilestonePlanDeletedVO constructShipmentMilestonePlanDeletedEventVO() {
        var planDeletedVO = new ShipmentMilestonePlanDeletedVO();
        planDeletedVO.setPlanVOs(constructShipmentMilestonePlanVOs());
        return planDeletedVO;
    }
    public ShipmentMilestonePlanDeletedEvent constructShipmentMilestonePlanDeletedEvent() {
        var plan = new ShipmentMilestonePlanDeletedEvent();
        plan.setShipmentKey("020-111111");
        plan.setShipmentType("A");
        plan.setMilestoneDetails(constructMilestoneDetails());

        return plan;
    
    }



    public List<ShipmentMilestonePlanDetailsEvent> constructMilestoneDetails() {
        var shipmentMilestonePlanDetailsEvent1 = new ShipmentMilestonePlanDetailsEvent();
        shipmentMilestonePlanDetailsEvent1.setMilestoneCode("ARR");
        shipmentMilestonePlanDetailsEvent1.setAirportCode("SIN");
        shipmentMilestonePlanDetailsEvent1.setOperationType("I");
        shipmentMilestonePlanDetailsEvent1.setPlannedFlightCarrier("AV");
        shipmentMilestonePlanDetailsEvent1.setPlannedFlightDate("23-Feb-2022");
        shipmentMilestonePlanDetailsEvent1.setPlannedFlightNumber("777");
        shipmentMilestonePlanDetailsEvent1.setPlannedTime("23-Feb-2022 06:00:00");
        shipmentMilestonePlanDetailsEvent1.setPlannedTimeInUTC("22-Feb-2022 22:00:00");
        shipmentMilestonePlanDetailsEvent1.setPlannedPieces(1);
        shipmentMilestonePlanDetailsEvent1.setPlannedWeight(10.0);

        var shipmentMilestonePlanDetailsEvent2 = new ShipmentMilestonePlanDetailsEvent();
        shipmentMilestonePlanDetailsEvent2.setMilestoneCode("DLV");
        shipmentMilestonePlanDetailsEvent2.setAirportCode("KYV");
        shipmentMilestonePlanDetailsEvent2.setOperationType("E");
        shipmentMilestonePlanDetailsEvent2.setPlannedFlightCarrier("AV");
        shipmentMilestonePlanDetailsEvent2.setPlannedFlightDate("23-Feb-2022");
        shipmentMilestonePlanDetailsEvent2.setPlannedFlightNumber("777");
        shipmentMilestonePlanDetailsEvent2.setPlannedTime("23-Feb-2022 07:00:00");
        shipmentMilestonePlanDetailsEvent2.setPlannedTimeInUTC("22-Feb-2022 22:00:00");
        shipmentMilestonePlanDetailsEvent2.setPlannedPieces(12);
        shipmentMilestonePlanDetailsEvent2.setPlannedWeight(100.0);

        return List.of(shipmentMilestonePlanDetailsEvent1, shipmentMilestonePlanDetailsEvent2);
    }

    public ShipmentMilestonePlanCreatedEvent constructShipmentMilestonePlanCreatedEvent() {
        var plan = new ShipmentMilestonePlanCreatedEvent();
        plan.setShipmentKey("020-111111");
        plan.setShipmentType("A");
        plan.setMilestoneDetails(constructMilestoneDetails());

        return plan;
    }

    public List<SplitVO> constructShipmentMilestoneSplits_1() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(2, List.of("DXB")),
                        "Delivered",
                        List.of(
                                new SplitDetailsItemVO(
                                        "2",
                                        "5",
                                        "BOM",
                                        "Departed",
                                        generateMilestoneTimeForEvent(2L),
                                        "A",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "5",
                                        "16",
                                        "DXB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(8L),
                                        "A",
                                        100,
                                        "AA",
                                        "3",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "16",
                                        null,
                                        "FRA",
                                        "Delivered",
                                        generateMilestoneTimeForEvent(16L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        2,
                        278,
                        new TransitStationVO(5, List.of("DXB", "CDG", "MUC")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "3",
                                        "6",
                                        "BOM",
                                        "Departed",
                                        generateMilestoneTimeForEvent(3L),
                                        "A",
                                        278,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "6",
                                        null,
                                        "DXB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(9L),
                                        "A",
                                        278,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "6_9",
                                                        "11",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(9L),
                                                        "A",
                                                        200,
                                                        "AA",
                                                        "4",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "6_10",
                                                        "12",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(10L),
                                                        "A",
                                                        78,
                                                        "AA",
                                                        "5",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "11",
                                        "17",
                                        "CDG",
                                        "Departed",
                                        generateMilestoneTimeForEvent(13L),
                                        "A",
                                        200,
                                        "AA",
                                        "6",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "12",
                                        "17",
                                        "MUC",
                                        "Arrived",
                                        generateMilestoneTimeForEvent(12L),
                                        "A",
                                        78,
                                        "AA",
                                        "7",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "17",
                                        null,
                                        "FRA",
                                        "Partially Arrived",
                                        generateMilestoneTimeForPlan(18L),
                                        "S",
                                        200,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),
                new SplitVO(
                        3,
                        100,
                        new TransitStationVO(2, List.of("AUH")),
                        "Delivered",
                        List.of(
                                new SplitDetailsItemVO(
                                        "4",
                                        "7",
                                        "BOM",
                                        "Departed",
                                        generateMilestoneTimeForEvent(4L),
                                        "A",
                                        100,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "7",
                                        "19",
                                        "AUH",
                                        "Departed",
                                        generateMilestoneTimeForEvent(15L),
                                        "A",
                                        100,
                                        "AA",
                                        "8",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "19",
                                        null,
                                        "FRA",
                                        "Delivered",
                                        generateMilestoneTimeForEvent(19L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }


    public List<SplitVO> constructShipmentMilestoneSplits_1_withNoEvents() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(2, List.of("DXB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "2",
                                        "5",
                                        "BOM",
                                        null,
                                        generateMilestoneTimeForPlan(2L),
                                        "S",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "5",
                                        "16",
                                        "DXB",
                                        null,
                                        generateMilestoneTimeForPlan(8L),
                                        "S",
                                        100,
                                        "AA",
                                        "3",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "16",
                                        null,
                                        "FRA",
                                        null,
                                        generateMilestoneTimeForPlan(16L),
                                        "S",
                                        null,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        2,
                        278,
                        new TransitStationVO(5, List.of("DXB", "CDG", "MUC")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "3",
                                        "6",
                                        "BOM",
                                        null,
                                        generateMilestoneTimeForPlan(3L),
                                        "S",
                                        278,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "6",
                                        null,
                                        "DXB",
                                        null,
                                        generateMilestoneTimeForPlan(9L),
                                        "S",
                                        278,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "6_9",
                                                        "11",
                                                        "DXB",
                                                        null,
                                                        generateMilestoneTimeForPlan(9L),
                                                        "S",
                                                        200,
                                                        "AA",
                                                        "4",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "6_10",
                                                        "12",
                                                        "DXB",
                                                        null,
                                                        generateMilestoneTimeForPlan(10L),
                                                        "S",
                                                        78,
                                                        "AA",
                                                        "5",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "11",
                                        "17",
                                        "CDG",
                                        null,
                                        generateMilestoneTimeForPlan(13L),
                                        "S",
                                        200,
                                        "AA",
                                        "6",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "12",
                                        "17",
                                        "MUC",
                                        null,
                                        generateMilestoneTimeForPlan(14L),
                                        "S",
                                        78,
                                        "AA",
                                        "7",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "17",
                                        null,
                                        "FRA",
                                        null,
                                        generateMilestoneTimeForPlan(18L),
                                        "S",
                                        null,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),
                new SplitVO(
                        3,
                        100,
                        new TransitStationVO(2, List.of("AUH")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "4",
                                        "7",
                                        "BOM",
                                        null,
                                        generateMilestoneTimeForPlan(4L),
                                        "S",
                                        100,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "7",
                                        "19",
                                        "AUH",
                                        null,
                                        generateMilestoneTimeForPlan(15L),
                                        "S",
                                        100,
                                        "AA",
                                        "8",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "19",
                                        null,
                                        "FRA",
                                        null,
                                        generateMilestoneTimeForPlan(19L),
                                        "S",
                                        null,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_1() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_1();
        var excludeEventsWithIds = Set.of(14L, 18L, 22L);
        return plans.stream()
                .filter(plan -> !excludeEventsWithIds.contains(plan.getSerialNumber()))
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toList());
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_1() {
        return List.of(
                constructPlanForSplits(1L, "BOM", "RCS", 478, "1"),
                constructPlanForSplits(2L, "BOM", "DEP", 100, "1"),
                constructPlanForSplits(3L, "BOM", "DEP", 278, "1"),
                constructPlanForSplits(4L, "BOM", "DEP", 100, "2"),
                constructPlanForSplits(5L, "DXB", "ARR", 100, "1"),
                constructPlanForSplits(6L, "DXB", "ARR", 278, "1"),
                constructPlanForSplits(7L, "AUH", "ARR", 100, "2"),
                constructPlanForSplits(8L, "DXB", "DEP", 100, "3"),
                constructPlanForSplits(9L, "DXB", "DEP", 200, "4"),
                constructPlanForSplits(10L, "DXB", "DEP", 78, "5"),
                constructPlanForSplits(11L, "CDG", "ARR", 200, "4"),
                constructPlanForSplits(12L, "MUC", "ARR", 78, "5"),
                constructPlanForSplits(13L, "CDG", "DEP", 200, "6"),
                constructPlanForSplits(14L, "MUC", "DEP", 78, "7"),
                constructPlanForSplits(15L, "AUH", "DEP", 100, "8"),
                constructPlanForSplits(16L, "FRA", "ARR", 100, "3"),
                constructPlanForSplits(17L, "FRA", "ARR", 200, "6"),
                constructPlanForSplits(18L, "FRA", "ARR", 78, "7"),
                constructPlanForSplits(19L, "FRA", "ARR", 100, "8"),
                constructPlanForSplits(20L, "FRA", "DLV", 100, "3"),
                constructPlanForSplits(21L, "FRA", "DLV", 200, "6"),
                constructPlanForSplits(22L, "FRA", "DLV", 78, "7"),
                constructPlanForSplits(23L, "FRA", "DLV", 100, "8")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_2() {
        return List.of(

                new SplitVO(
                        1,
                        80,
                        new TransitStationVO(4, List.of("DXB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "2",
                                        "BOM",
                                        "Departed",
                                        generateMilestoneTimeForEvent(1L),
                                        "A",
                                        80,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "2",
                                        null,
                                        "DXB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(3L),
                                        "A",
                                        80,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "2_3",
                                                        "6",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(3L),
                                                        "A",
                                                        40,
                                                        "AA",
                                                        "2",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "2_4",
                                                        "6",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(4L),
                                                        "A",
                                                        20,
                                                        "AA",
                                                        "3",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "2_5",
                                                        "6",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(5L),
                                                        "A",
                                                        20,
                                                        "AA",
                                                        "4",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "6",
                                        null,
                                        "FRA",
                                        "Partially Arrived",
                                        generateMilestoneTimeForEvent(8L),
                                        "S",
                                        40,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        2,
                        60,
                        new TransitStationVO(3, List.of("DXB")),
                        "Delivered",
                        List.of(
                                new SplitDetailsItemVO(
                                        "9",
                                        "10",
                                        "BOM",
                                        "Departed",
                                        generateMilestoneTimeForEvent(9L),
                                        "A",
                                        60,
                                        "AA",
                                        "5",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "10",
                                        null,
                                        "DXB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(11L),
                                        "A",
                                        60,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "10_11",
                                                        "13",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(11L),
                                                        "A",
                                                        40,
                                                        "AA",
                                                        "6",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "10_12",
                                                        "13",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(12L),
                                                        "A",
                                                        20,
                                                        "AA",
                                                        "7",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "13",
                                        null,
                                        "FRA",
                                        "Delivered",
                                        generateMilestoneTimeForEvent(14L),
                                        "A",
                                        60,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        3,
                        75,
                        new TransitStationVO(3, List.of("DXB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "17",
                                        "18",
                                        "BOM",
                                        "Departed",
                                        generateMilestoneTimeForEvent(17L),
                                        "A",
                                        75,
                                        "AA",
                                        "8",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "18",
                                        null,
                                        "DXB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(19L),
                                        "A",
                                        75,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "18_19",
                                                        "21",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(19L),
                                                        "A",
                                                        50,
                                                        "AA",
                                                        "9",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "18_20",
                                                        "21",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(20L),
                                                        "A",
                                                        25,
                                                        "AA",
                                                        "10",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "21",
                                        null,
                                        "FRA",
                                        "Partially Delivered",
                                        generateMilestoneTimeForEvent(22L),
                                        "A",
                                        75,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        4,
                        75,
                        new TransitStationVO(3, List.of("DXB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "23",
                                        "24",
                                        "BOM",
                                        "Departed",
                                        generateMilestoneTimeForEvent(23L),
                                        "A",
                                        75,
                                        "AA",
                                        "11",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "24",
                                        null,
                                        "DXB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(25L),
                                        "A",
                                        75,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "24_25",
                                                        "27",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(25L),
                                                        "A",
                                                        50,
                                                        "AA",
                                                        "12",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "24_26",
                                                        "27",
                                                        "DXB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(26L),
                                                        "A",
                                                        25,
                                                        "AA",
                                                        "13",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "27",
                                        null,
                                        "FRA",
                                        "Arrived",
                                        generateMilestoneTimeForEvent(28L),
                                        "A",
                                        75,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_2() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_2();
        var excludeEventsWithIds = Set.of(6L, 16L);
        var events = plans.stream()
                .filter(plan -> !excludeEventsWithIds.contains(plan.getSerialNumber()))
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
        events.add(constructEventForSplits(plans.get(16), Optional.of(10))); //20 -> 10, PartiallyDelivered

        return events;
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_2() {
        return List.of(
                //null, arr, arr
                constructPlanForSplits(0L, "BOM", "RCS", 290, "1"),
                constructPlanForSplits(1L, "BOM", "DEP", 80, "1"),
                constructPlanForSplits(2L, "DXB", "ARR", 80, "1"),
                constructPlanForSplits(3L, "DXB", "DEP", 40, "2"),
                constructPlanForSplits(4L, "DXB", "DEP", 20, "3"),
                constructPlanForSplits(5L, "DXB", "DEP", 20, "4"),
                constructPlanForSplits(6L, "FRA", "ARR", 40, "2"),
                constructPlanForSplits(7L, "FRA", "ARR", 20, "3"),
                constructPlanForSplits(8L, "FRA", "ARR", 20, "4"),

                //del, partDel
                constructPlanForSplits(9L, "BOM", "DEP", 60, "5"),
                constructPlanForSplits(10L, "DXB", "ARR", 60, "5"),
                constructPlanForSplits(11L, "DXB", "DEP", 40, "6"),
                constructPlanForSplits(12L, "DXB", "DEP", 20, "7"),
                constructPlanForSplits(13L, "FRA", "ARR", 40, "6"),
                constructPlanForSplits(14L, "FRA", "ARR", 20, "7"),
                constructPlanForSplits(15L, "FRA", "DLV", 40, "6"),
                constructPlanForSplits(16L, "FRA", "DLV", 20, "7"),

                //arr, arr
                constructPlanForSplits(17L, "BOM", "DEP", 75, "8"),
                constructPlanForSplits(18L, "DXB", "ARR", 75, "8"),
                constructPlanForSplits(19L, "DXB", "DEP", 50, "9"),
                constructPlanForSplits(20L, "DXB", "DEP", 25, "10"),
                constructPlanForSplits(21L, "FRA", "ARR", 50, "9"),
                constructPlanForSplits(22L, "FRA", "ARR", 25, "10"),

                //del, del
                constructPlanForSplits(23L, "BOM", "DEP", 75, "11"),
                constructPlanForSplits(24L, "DXB", "ARR", 75, "11"),
                constructPlanForSplits(25L, "DXB", "DEP", 50, "12"),
                constructPlanForSplits(26L, "DXB", "DEP", 25, "13"),
                constructPlanForSplits(27L, "FRA", "ARR", 50, "12"),
                constructPlanForSplits(28L, "FRA", "ARR", 25, "13"),
                constructPlanForSplits(29L, "FRA", "DLV", 50, "12"),
                constructPlanForSplits(30L, "FRA", "DLV", 25, "13")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_3() {
        return List.of(

                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(3, List.of("B", "C")),
                        "Delivered",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "2_5",
                                        "A",
                                        "Departed",
                                        generateMilestoneTimeForEvent(1L),
                                        "A",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "2_5",
                                        "6_7",
                                        "B",
                                        "Departed",
                                        generateMilestoneTimeForEvent(5L),
                                        "A",
                                        100,
                                        "AA",
                                        "3",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "6_7",
                                        "8",
                                        "C",
                                        "Departed",
                                        generateMilestoneTimeForEvent(7L),
                                        "A",
                                        100,
                                        "AA",
                                        "4",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "8",
                                        null,
                                        "D",
                                        "Delivered",
                                        generateMilestoneTimeForEvent(8L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        2,
                        78,
                        new TransitStationVO(6, List.of("B", "C", "F", "E")),
                        "Delivered",
                        List.of(
                                new SplitDetailsItemVO(
                                        "3",
                                        "2",
                                        "A",
                                        "Departed",
                                        generateMilestoneTimeForEvent(3L),
                                        "A",
                                        78,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "2",
                                        null,
                                        "B",
                                        "Departed",
                                        generateMilestoneTimeForEvent(5L),
                                        "A",
                                        78,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "2_5",
                                                        "6_9",
                                                        "B",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(5L),
                                                        "A",
                                                        20,
                                                        "AA",
                                                        "3",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "2_13",
                                                        "14",
                                                        "B",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(13L),
                                                        "A",
                                                        58,
                                                        "AA",
                                                        "7",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "6_9",
                                        "10",
                                        "C",
                                        "Departed",
                                        generateMilestoneTimeForEvent(9L),
                                        "A",
                                        20,
                                        "AA",
                                        "5",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "14",
                                        "16",
                                        "F",
                                        "Departed",
                                        generateMilestoneTimeForEvent(15L),
                                        "A",
                                        58,
                                        "AA",
                                        "8",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "10",
                                        "16",
                                        "E",
                                        "Departed",
                                        generateMilestoneTimeForEvent(11L),
                                        "A",
                                        20,
                                        "AA",
                                        "6",
                                        null,
                                        List.of()
                                ),


                                new SplitDetailsItemVO(
                                        "16",
                                        null,
                                        "D",
                                        "Delivered",
                                        generateMilestoneTimeForEvent(16L),
                                        "A",
                                        78,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_3() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_3();
        return plans.stream()
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_3() {
        return List.of(
                constructPlanForSplits(0L, "A", "RCS", 178, "1"),
                constructPlanForSplits(1L, "A", "DEP", 100, "1"),
                constructPlanForSplits(2L, "B", "ARR", 100, "1"),
                constructPlanForSplits(3L, "A", "DEP", 78, "2"),
                constructPlanForSplits(4L, "B", "ARR", 78, "2"),
                constructPlanForSplits(5L, "B", "DEP", 120, "3"),
                constructPlanForSplits(6L, "C", "ARR", 120, "3"),
                constructPlanForSplits(7L, "C", "DEP", 100, "4"),
                constructPlanForSplits(8L, "D", "ARR", 100, "4"),
                constructPlanForSplits(9L, "C", "DEP", 20, "5"),
                constructPlanForSplits(10L, "E", "ARR", 20, "5"),
                constructPlanForSplits(11L, "E", "DEP", 20, "6"),
                constructPlanForSplits(12L, "D", "ARR", 20, "6"),
                constructPlanForSplits(13L, "B", "DEP", 58, "7"),
                constructPlanForSplits(14L, "F", "ARR", 58, "7"),
                constructPlanForSplits(15L, "F", "DEP", 58, "8"),
                constructPlanForSplits(16L, "D", "ARR", 58, "8"),
                constructPlanForSplits(17L, "D", "DLV", 178, "8")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_4() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(2, List.of("B")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "2",
                                        "A",
                                        "Departed",
                                        generateMilestoneTimeForEvent(1L),
                                        "A",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "2",
                                        "5",
                                        "B",
                                        null,
                                        generateMilestoneTimeForPlan(3L),
                                        "S",
                                        100,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "5",
                                        null,
                                        "C",
                                        null,
                                        generateMilestoneTimeForPlan(5L),
                                        "S",
                                        null,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_4() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_4();

        var events = new LinkedList<ShipmentMilestoneEvent>();
        events.add(constructEventForSplits(plans.get(0)));
        events.add(constructEventForSplits(plans.get(1)));

        return events;
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_4() {
        return List.of(
                constructPlanForSplits(0L, "A", "RCS", 100, "1"),
                constructPlanForSplits(1L, "A", "DEP", 100, "1"),
                constructPlanForSplits(2L, "B", "ARR", 100, "1"),
                constructPlanForSplits(3L, "B", "DEP", 100, "2"),
                constructPlanForSplits(5L, "C", "ARR", 100, "2"),
                constructPlanForSplits(6L, "C", "DLV", 100, "3")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_5() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(2, List.of("BBB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "2",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(1L),
                                        "A",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "2",
                                        "5",
                                        "BBB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(3L),
                                        "A",
                                        100,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "5",
                                        null,
                                        "CCC",
                                        "Arrived",
                                        generateMilestoneTimeForEvent(5L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        2,
                        50,
                        new TransitStationVO(2, List.of("BBB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "7",
                                        "8",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(7L),
                                        "A",
                                        50,
                                        "AA",
                                        "4",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "8",
                                        "10",
                                        "BBB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(9L),
                                        "A",
                                        50,
                                        "AA",
                                        "5",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "10",
                                        null,
                                        "CCC",
                                        "Partially Delivered",
                                        generateMilestoneTimeForEvent(10L),
                                        "A",
                                        50,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_5() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_5();

        return plans.stream()
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_5() {
        return List.of(
                constructPlanForSplits(0L, "AAA", "RCS", 150, "1"),
                constructPlanForSplits(1L, "AAA", "DEP", 100, "1"),
                constructPlanForSplits(2L, "BBB", "ARR", 100, "1"),
                constructPlanForSplits(3L, "BBB", "DEP", 100, "2"),
                constructPlanForSplits(5L, "CCC", "ARR", 100, "2"),
                constructPlanForSplits(6L, "CCC", "DLV", 100, "2"),

                constructPlanForSplits(7L, "AAA", "DEP", 50, "4"),
                constructPlanForSplits(8L, "BBB", "ARR", 50, "4"),
                constructPlanForSplits(9L, "BBB", "DEP", 50, "5"),
                constructPlanForSplits(10L, "CCC", "ARR", 50, "5"),
                constructPlanForSplits(11L, "CCC", "DLV", 50, "5"),

                constructPlanForSplits(12L, "CCC", "DRN", 110, "6")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_5_1() {
        return List.of(
                new SplitVO(
                        1,
                        200,
                        new TransitStationVO(2, List.of("BBB")),
                        "Delivered",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "2",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(1L),
                                        "A",
                                        200,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "2",
                                        "5",
                                        "BBB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(3L),
                                        "A",
                                        200,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "5",
                                        null,
                                        "CCC",
                                        "Delivered",
                                        generateMilestoneTimeForEvent(5L),
                                        "A",
                                        200,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_5_1() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_5_1();

        return plans.stream()
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_5_1() {
        return List.of(
                constructPlanForSplits(0L, "AAA", "RCS", 200, "1"),
                constructPlanForSplits(1L, "AAA", "DEP", 200, "1"),
                constructPlanForSplits(2L, "BBB", "ARR", 200, "1"),
                constructPlanForSplits(3L, "BBB", "DEP", 200, "2"),
                constructPlanForSplits(5L, "CCC", "ARR", 200, "2"),
                constructPlanForSplits(6L, "CCC", "DLV", 100, "2"),
                constructPlanForSplits(7L, "CCC", "DLV", 100, "2"),
                constructPlanForSplits(8L, "CCC", "DRN", 100, "2"),
                constructPlanForSplits(9L, "CCC", "DLV", 100, "2")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_5_2() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(2, List.of("BBB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "3_5",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(1L),
                                        "A",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "3_5",
                                        "7",
                                        "BBB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(5L),
                                        "A",
                                        100,
                                        "AA",
                                        "3",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "7",
                                        null,
                                        "CCC",
                                        "Partially Delivered",
                                        generateMilestoneTimeForEvent(7L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),

                new SplitVO(
                        2,
                        100,
                        new TransitStationVO(3, List.of("BBB")),
                        "Delivered",
                        List.of(
                                new SplitDetailsItemVO(
                                        "2",
                                        "3",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(2L),
                                        "A",
                                        100,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "3",
                                        null,
                                        "BBB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(5L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "3_5",
                                                        "7",
                                                        "BBB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(5L),
                                                        "A",
                                                        50,
                                                        "AA",
                                                        "3",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "3_6",
                                                        "7",
                                                        "BBB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(6L),
                                                        "A",
                                                        50,
                                                        "AA",
                                                        "4",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "7",
                                        null,
                                        "CCC",
                                        "Delivered",
                                        generateMilestoneTimeForEvent(8L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_5_2() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_5_2();

        return plans.stream()
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_5_2() {
        return List.of(
                constructPlanForSplits(0L, "AAA", "RCS", 200, "1"),
                constructPlanForSplits(1L, "AAA", "DEP", 100, "1"),
                constructPlanForSplits(2L, "AAA", "DEP", 100, "2"),
                constructPlanForSplits(3L, "BBB", "ARR", 100, "1"),
                constructPlanForSplits(4L, "BBB", "ARR", 100, "2"),
                constructPlanForSplits(5L, "BBB", "DEP", 150, "3"),
                constructPlanForSplits(6L, "BBB", "DEP", 50, "4"),
                constructPlanForSplits(7L, "CCC", "ARR", 150, "3"),
                constructPlanForSplits(8L, "CCC", "ARR", 50, "4"),
                constructPlanForSplits(9L, "CCC", "DLV", 150, "3"),
                constructPlanForSplits(10L, "CCC", "DLV", 50, "4"),
                constructPlanForSplits(11L, "CCC", "DRN", 1, "3")
        );
    }


    public List<SplitVO> constructShipmentMilestoneSplits_6() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(7, List.of("BBB", "CCC", "DDD", "EEE")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "2",
                                        "3",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(2L),
                                        "A",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "3",
                                        null,
                                        "BBB",
                                        "Partially Departed",
                                        generateMilestoneTimeForEvent(4L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "3_4",
                                                        "6",
                                                        "BBB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(4L),
                                                        "A",
                                                        70,
                                                        "AA",
                                                        "2",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "3_5",
                                                        "8",
                                                        "BBB",
                                                        "Arrived",
                                                        generateMilestoneTimeForEvent(3L),
                                                        "A",
                                                        30,
                                                        "AA",
                                                        "3",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "6",
                                        "8",
                                        "CCC",
                                        "Departed",
                                        generateMilestoneTimeForEvent(7L),
                                        "A",
                                        70,
                                        "AA",
                                        "4",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "8",
                                        null,
                                        "DDD",
                                        "Partially Departed",
                                        generateMilestoneTimeForEvent(10L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "8_10",
                                                        "12",
                                                        "DDD",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(10L),
                                                        "A",
                                                        60,
                                                        "AA",
                                                        "5",
                                                        null,
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "8_11",
                                                        "15",
                                                        "DDD",
                                                        "Partially Arrived",
                                                        generateMilestoneTimeForEvent(9L),
                                                        "A",
                                                        40,
                                                        "AA",
                                                        "6",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "12",
                                        "15",
                                        "EEE",
                                        "Departed",
                                        generateMilestoneTimeForEvent(13L),
                                        "A",
                                        60,
                                        "AA",
                                        "7",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "15",
                                        null,
                                        "FFF",
                                        "Partially Arrived",
                                        generateMilestoneTimeForPlan(15L),
                                        "S",
                                        60,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_6() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_6();
        var excludeEventsWithIds = Set.of(5L, 8L, 11L, 15L);
        return plans.stream()
                .filter(plan -> !excludeEventsWithIds.contains(plan.getSerialNumber()))
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_6() {
        return List.of(
                constructPlanForSplits(1L, "AAA", "RCS", 100, "1"),
                constructPlanForSplits(2L, "AAA", "DEP", 100, "1"),
                constructPlanForSplits(3L, "BBB", "ARR", 100, "1"),
                constructPlanForSplits(4L, "BBB", "DEP", 70, "2"),
                constructPlanForSplits(5L, "BBB", "DEP", 30, "3"),
                constructPlanForSplits(6L, "CCC", "ARR", 70, "2"),
                constructPlanForSplits(7L, "CCC", "DEP", 70, "4"),
                constructPlanForSplits(8L, "DDD", "ARR", 30, "3"),
                constructPlanForSplits(9L, "DDD", "ARR", 70, "4"),
                constructPlanForSplits(10L, "DDD", "DEP", 60, "5"),
                constructPlanForSplits(11L, "DDD", "DEP", 40, "6"),
                constructPlanForSplits(12L, "EEE", "ARR", 60, "5"),
                constructPlanForSplits(13L, "EEE", "DEP", 60, "7"),
                constructPlanForSplits(14L, "FFF", "ARR", 60, "7"),
                constructPlanForSplits(15L, "FFF", "ARR", 40, "6")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_7() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(1, List.of()),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "2",
                                        "AAA",
                                        null,
                                        generateMilestoneTimeForPlan(1L),
                                        "S",
                                        100,
                                        "AA",
                                        "1",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "2",
                                        null,
                                        "BBB",
                                        null,
                                        generateMilestoneTimeForPlan(2L),
                                        "S",
                                        null,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),
                new SplitVO(
                        2,
                        60,
                        new TransitStationVO(1, List.of()),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "3",
                                        "4",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(3L),
                                        "A",
                                        60,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "4",
                                        null,
                                        "BBB",
                                        "Arrived",
                                        generateMilestoneTimeForEvent(4L),
                                        "A",
                                        60,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_7() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_7();
        var excludeEventsWithIds = Set.of(1L, 2L);
        return plans.stream()
                .filter(plan -> !excludeEventsWithIds.contains(plan.getSerialNumber()))
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_7() {
        return List.of(
                constructPlanForSplits(0L, "AAA", "RCS", 160, "1"),
                constructPlanForSplits(1L, "AAA", "DEP", 100, "1"),
                constructPlanForSplits(2L, "BBB", "ARR", 100, "1"),
                constructPlanForSplits(3L, "AAA", "DEP", 60, "2"),
                constructPlanForSplits(4L, "BBB", "ARR", 60, "2")
        );
    }

    public List<SplitVO> constructShipmentMilestoneSplits_8() {
        return List.of(
                new SplitVO(
                        1,
                        100,
                        new TransitStationVO(2, List.of("BBB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "1",
                                        "3_5",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForPlan(1L),
                                        "S",
                                        100,
                                        "AA",
                                        "1",
                                        new ActualFlightDataVO("BB", "9991", generateMilestoneTimeForEvent(1L)),
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "3_5",
                                        "7",
                                        "BBB",
                                        "Departed",
                                        generateMilestoneTimeForPlan(5L),
                                        "S",
                                        100,
                                        "AA",
                                        "3",
                                        new ActualFlightDataVO("BB", "9993", generateMilestoneTimeForEvent(5L)),
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "7",
                                        null,
                                        "CCC",
                                        "Arrived",
                                        generateMilestoneTimeForPlan(7L),
                                        "S",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                ),
                new SplitVO(
                        2,
                        100,
                        new TransitStationVO(3, List.of("BBB")),
                        "In progress",
                        List.of(
                                new SplitDetailsItemVO(
                                        "2",
                                        "3",
                                        "AAA",
                                        "Departed",
                                        generateMilestoneTimeForEvent(2L),
                                        "A",
                                        100,
                                        "AA",
                                        "2",
                                        null,
                                        List.of()
                                ),
                                new SplitDetailsItemVO(
                                        "3",
                                        null,
                                        "BBB",
                                        "Departed",
                                        generateMilestoneTimeForEvent(6L),
                                        "A",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of(
                                                new SplitDetailsItemVO(
                                                        "3_5",
                                                        "7",
                                                        "BBB",
                                                        "Departed",
                                                        generateMilestoneTimeForPlan(5L),
                                                        "S",
                                                        50,
                                                        "AA",
                                                        "3",
                                                        new ActualFlightDataVO("BB", "9993", generateMilestoneTimeForEvent(5L)),
                                                        List.of()
                                                ),
                                                new SplitDetailsItemVO(
                                                        "3_6",
                                                        "7",
                                                        "BBB",
                                                        "Departed",
                                                        generateMilestoneTimeForEvent(6L),
                                                        "A",
                                                        50,
                                                        "AA",
                                                        "4",
                                                        null,
                                                        List.of()
                                                )
                                        )
                                ),
                                new SplitDetailsItemVO(
                                        "7",
                                        null,
                                        "CCC",
                                        "Arrived",
                                        generateMilestoneTimeForEvent(8L),
                                        "S",
                                        100,
                                        null,
                                        null,
                                        null,
                                        List.of()
                                )
                        )
                )
        );
    }

    public List<ShipmentMilestoneEvent> constructShipmentMilestoneEventsForSplitsTesting_8() {
        var plans = constructShipmentMilestonePlansForSplitsTesting_8();
        return plans.stream()
                .map(plan -> {
                    if (plan.getSerialNumber().equals(1L) || plan.getSerialNumber().equals(3L)) {
                        plan.setFlightNumber("9991");
                        return plan;
                    }
                    if (plan.getSerialNumber().equals(5L) || plan.getSerialNumber().equals(7L)) {
                        plan.setFlightNumber("9993");
                        return plan;
                    }
                    return plan;
                })
                .map(MockDataHelper::constructEventForSplits)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForSplitsTesting_8() {
        return List.of(
                constructPlanForSplits(0L, "AAA", "RCS", 200, "1"),
                constructPlanForSplits(1L, "AAA", "DEP", 100, "1"),
                constructPlanForSplits(2L, "AAA", "DEP", 100, "2"),
                constructPlanForSplits(3L, "BBB", "ARR", 100, "1"),
                constructPlanForSplits(4L, "BBB", "ARR", 100, "2"),
                constructPlanForSplits(5L, "BBB", "DEP", 150, "3"),
                constructPlanForSplits(6L, "BBB", "DEP", 50, "4"),
                constructPlanForSplits(7L, "CCC", "ARR", 150, "3"),
                constructPlanForSplits(8L, "CCC", "ARR", 50, "4")
        );
    }


    public List<ShipmentMilestonePlan> constructShipmentMilestonePlansForActivitiesTesting() {
        return List.of(
                constructPlanForSplits(0L, "KPB", "RCS", 290, "1"),
                constructPlanForSplits(1L, "KPB", "DEP", 290, "1"),
                constructPlanForSplits(2L, "DXB", "ARR", 290, "1"),
                constructPlanForSplits(3L, "DXB", "DEP", 290, "2"),
                constructPlanForSplits(4L, "MUC", "DIS", 290, "2"),
                constructPlanForSplits(5L, "MUC", "ARR", 290, "2"),
                constructPlanForSplits(6L, "MUC", "RCT", 290, "2"),
                constructPlanForSplits(7L, "MUC", "TFD", 290, "2"),
                constructPlanForSplits(8L, "MUC", "DLV", 290, "2"),
                constructPlanForSplits(9L, "LIT", "DRN", 290, "2")
        );
    }

    private ShipmentMilestonePlan constructPlanForSplits(
            Long serialNumber, String airportCode, String milestoneCode, Integer pieces, String flightNumber) {
        var plan = new ShipmentMilestonePlan();
        plan.setSerialNumber(serialNumber);
        plan.setAirportCode(airportCode);
        plan.setMilestoneCode(milestoneCode);
        plan.setPieces(pieces);
        plan.setFlightNumber(flightNumber);
        plan.setFlightCarrierCode("AA");
        plan.setMilestoneTimeUTC(generateMilestoneTimeUTCForPlan(serialNumber));
        plan.setMilestoneTime(utcToLocalTime(plan.getMilestoneTimeUTC()));
        return plan;
    }

    private LinkedHashMap<String, String> constructEventTransactionDetails(String flightNumber) {
        var transactionDetails = new LinkedHashMap<String, String>();
        transactionDetails.put("flightNumber", flightNumber);
        transactionDetails.put("flightCarrierCode", "BB");
        return transactionDetails;
    }

    public ShipmentMilestoneEvent constructEventForSplits(ShipmentMilestonePlan plan) {
        return constructEventForSplits(plan, Optional.empty());
    }

    public ShipmentMilestoneEvent constructEventForActivities(ShipmentMilestonePlan plan, Quantities quantities) {
        var event = constructEventForSplits(plan);
        event.setWeight(1.2);
        var eUnit = new EUnitOfQuantity();
        eUnit.setWeight("K");
        event.setEUnit(eUnit);
        return event;
    }

    private ShipmentMilestoneEvent constructEventForSplits(ShipmentMilestonePlan plan, Optional<Integer> pieces) {
        var event = new ShipmentMilestoneEvent();
        event.setSerialNumber(plan.getSerialNumber());
        event.setAirportCode(plan.getAirportCode());
        event.setMilestoneCode(plan.getMilestoneCode());
        event.setPieces(pieces.orElseGet(() -> plan.getPieces()));
        event.setMilestoneTimeUTC(generateMilestoneTimeUTCForEvent(plan.getSerialNumber()));
        event.setMilestoneTime(utcToLocalTime(event.getMilestoneTimeUTC()));
        event.setTransactionDetails(constructEventTransactionDetails(plan.getFlightNumber()));
        return event;
    }

    private LocalDateTime generateMilestoneTimeUTCForPlan(Long serialNumber) {
        if (serialNumber == 0) {
            serialNumber = 1L;
        }
        if (serialNumber < 10) {
            return LocalDateTime.parse("2022-01-0" + serialNumber + "T12:00:00");
        } else if (serialNumber < 30) {
            return LocalDateTime.parse("2022-01-" + serialNumber + "T12:00:00");
        } else if (serialNumber < 58) {
            serialNumber -= 29;
            return LocalDateTime.parse("2022-02-0" + serialNumber + "T12:00:00");
        } else {
            throw new RuntimeException("Unsupported");
        }
    }

    private LocalDateTime generateMilestoneTimeUTCForEvent(Long serialNumber) {
        return generateMilestoneTimeUTCForPlan(serialNumber).plusHours(1);
    }


    private LocalDateTime generateMilestoneTimeForPlan(Long serialNumber) {
        return utcToLocalTime(
                generateMilestoneTimeUTCForPlan(serialNumber)
        );
    }

    private LocalDateTime generateMilestoneTimeForEvent(Long serialNumber) {
        return utcToLocalTime(
                generateMilestoneTimeUTCForEvent(serialNumber)
        );
    }

    private LocalDateTime utcToLocalTime(LocalDateTime utTime) {
        return utTime.minusHours(2);
    }

    public ShipmentHistoryEvent constructShipmentHistoryEvent() {
        var event = new ShipmentHistoryEvent();
        event.setShipmentKey("134-23401475");
        event.setShipmentType("A");
        event.setShipmentSequenceNumber(1L);
        event.setAirportCode("DXB");
        event.setTransactionPieces(5);
        event.setTransactionWeight(50.0);
        event.setTransactionVolume(0.05);
        event.setTransactionDate(LocalDate.parse("2022-03-22"));
        event.setMilestoneCode("RCF");
        event.setTransactionTime("2022-03-22T13:50:47.327+04:00");
        event.setLastUpdatedUser("ICOADMIN");
        event.setTransactionWeight(1.2);
        var units = new Units();
		units.setWeight("K");
		event.setUnitsOfMeasure(units);
        event.setTransactionDetails(Map.of("key_1", "val_1"));

        return event;
    }

    public ShipmentMilestoneEventVO constructShipmentMilestoneEventVO() {
        var event = new ShipmentMilestoneEventVO();
        event.setShipmentKey("134-23401475");
        event.setShipmentType("A");
        event.setShipmentSequenceNumber(1);
        event.setAirportCode("DXB");
        event.setPieces(5);
        event.setMilestoneCode(RCF);
        event.setMilestoneTime(LocalDateTime.parse("2022-03-22T13:50:47.327"));
        event.setMilestoneTimeUTC(LocalDateTime.parse("2022-03-22T09:50:47.327"));
        event.setLastUpdateUser("ICOADMIN");
        event.setCompanyCode("CODE");

        return event;
    }

    public ShipmentMilestoneEventVO constructShipmentMilestoneEventVOWithWeight(Quantities quantities) {
        var event = constructShipmentMilestoneEventVO();
        event.setWeight(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(1.2), BigDecimal.valueOf(1.2), "K"));
        event.setTransactionDetails(Map.of("key_1", "val_1"));

        return event;
    }

    public ShipmentMilestoneEvent constructShipmentMilestoneEvent() {
        var event = new ShipmentMilestoneEvent();
        event.setShipmentKey("134-23401475");
        event.setShipmentType("A");
        event.setShipmentSequenceNumber(1);
        event.setAirportCode("DXB");
        event.setPieces(5);
        event.setMilestoneCode(RCF.getLabel());
        event.setMilestoneTime(LocalDateTime.parse("2022-03-22T13:50:47.327"));
        event.setMilestoneTimeUTC(LocalDateTime.parse("2022-03-22T09:50:47.327"));
        event.setLastUpdateUser("ICOADMIN");
        event.setCompanyCode("CODE");
        event.setWeight(1.2);
        var eUnit = new EUnitOfQuantity();
        eUnit.setWeight("K");
        event.setEUnit(eUnit);
        event.setTransactionDetails(Map.of("key_1", "val_1"));

        return event;
    }

    public ShipmentDetailsVO constructShipmentDetailsVO(Quantities quantities) {
        var details = new ShipmentDetailsVO();
        details.setShipmentPrefix("134");
        details.setMasterDocumentNumber("12345678");
        details.setPieces(50);
        details.setVolume(quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(10.0), BigDecimal.valueOf(10.0), "F"));
        details.setWeight(quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(20.0), BigDecimal.valueOf(20.0), "L"));
        details.setSpecialHandlingCode("GEN");
        details.setProductName("Books");
        details.setShipmentDescription("Shipment Description");
        details.setOriginAirportCode("DXB");
        details.setDestinationAirportCode("SIN");
        details.setDepartureTime(LocalDateTime.of(2021, 12, 21, 13, 29, 28));
        details.setDepartureTimePostfix(FlightTimePostfixEnum.ACTUAL);
        details.setArrivalTime(LocalDateTime.of(2021, 12, 21, 19, 10, 0));
        details.setArrivalTimePostfix(FlightTimePostfixEnum.SCHEDULED);

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

        details.setMilestones(com.google.common.collect.Lists.newArrayList(milestoneVO_1, milestoneVO_2, milestoneVO_3, milestoneVO_4));

        details.setTransitStations(new TransitStationVO(4, Lists.newArrayList("BEL", "BHM", "HSV")));

        return details;
    }

    public ShipmentActivityVO constructShipmentActivityVO() {
        ShipmentActivityVO shipmentActivityVO = new ShipmentActivityVO();
        shipmentActivityVO.setEvent(MILESTONE_RCS);
        shipmentActivityVO.setPieces(100);
        shipmentActivityVO.setAirportCode("DXB");
        shipmentActivityVO.setEventTime(LocalDateTime.parse("2022-03-22T13:50:47.327"));
        shipmentActivityVO.setEventTimeUTC(LocalDateTime.parse("2022-03-22T11:50:47.327"));
        shipmentActivityVO.setReason("reason");
        shipmentActivityVO.setFlightData(new ShipmentActivityFlightVO("AV", "4545A", "DXB", "FRA"));
        return shipmentActivityVO;
    }


    public AWBUserNotification constructAwbUserNotification() {
        var notification = new AWBUserNotification();
        notification.setNotificationMilestones(List.of(MILESTONE_RCS, MILESTONE_DEP));
        notification.setEmails(List.of("user@domai.com", "user@gamil.com"));
        notification.setNotificationsKey(new AWBUserNotificationKey(1L, "userId"));
        notification.setCompanyCode("AV");
        notification.setLastUpdatedUser("ICARGO");

        return notification;
    }

    public List<AWBUserNotification> constructAwbUserNotifications() {
        var notification1 = new AWBUserNotification();
        notification1.setNotificationMilestones(List.of(MILESTONE_RCS, MILESTONE_DEP));
        notification1.setEmails(List.of("user@domai.com", "user@gamil.com"));
        notification1.setNotificationsKey(new AWBUserNotificationKey(1L, "userId1"));

        var notification2 = new AWBUserNotification();
        notification2.setNotificationMilestones(List.of(MILESTONE_RCS, MILESTONE_DEP,MILESTONE_ARR));
        notification2.setEmails(List.of("user@domai.com", "user2@domai.com", "user2@gamil.com"));
        notification2.setNotificationsKey(new AWBUserNotificationKey(1L, "userId2"));

        var notification3 = new AWBUserNotification();
        notification3.setNotificationMilestones(List.of(MILESTONE_ARR));
        notification3.setEmails(List.of("user3@domai.com", "user3@gamil.com"));
        notification3.setNotificationsKey(new AWBUserNotificationKey(1L, "userId3"));

        return List.of(notification1, notification2, notification3);
    }

    public AwbUserNotificationVO constructAwbUserNotificationVO() {
        var notificationVO = new AwbUserNotificationVO();
        notificationVO.setNotificationMilestones(List.of(MILESTONE_RCS, MILESTONE_DEP));
        notificationVO.setEmails(List.of("user@domai.com", "user@gamil.com"));
        notificationVO.setNotificationsKey(new AWBUserNotificationKey(1L, "userId"));
        notificationVO.setCompanyCode("AV");
        notificationVO.setShipmentKey(new ShipmentKey("123", "345678910"));
        return notificationVO;
    }

    public static AwbUserNotificationModel constructAwbUserNotificationModel() {
        var model = new AwbUserNotificationModel();
        model.setNotifications(Map.of(MILESTONE_DLV, true, MILESTONE_DEP, false, MILESTONE_RCS, true, MILESTONE_ARR, false));
        model.setEmails(List.of("user@domai.com", "user@gamil.com"));
        model.setTrackingAwbSerialNumber(1L);
        return model;
    }

    public static EmailMilestoneNotificationVO constructEmailMilestoneNotificationVO() {
        var emailVO = new EmailMilestoneNotificationVO();
        emailVO.setPieces(10);
        emailVO.setShipmentKey("123-45678910");
        emailVO.setAirline("Airline name");
        emailVO.setMilestoneCode(DEP);
        emailVO.setMilestoneTime(LocalDateTime.now().toString());
        emailVO.setNotifications(List.of(constructAwbUserNotification()));
        return emailVO;
    }
    public List<MilestoneMasterVO> constructMilestoneVO() {
    	List <MilestoneMasterVO> milestones=new ArrayList<>();
    	MilestoneMasterVO milestoneMasterVO= new MilestoneMasterVO();
    	milestoneMasterVO.setSerialNumber(1L);
    	milestoneMasterVO.setCompanyCode("AV");
    	milestoneMasterVO.setMilestoneCode("ARR");
    	milestoneMasterVO.setMilestoneDescription("Arrival");
    	milestoneMasterVO.setMilestoneType("I");
    	milestoneMasterVO.setMilestoneShipmentType("A");
    	milestoneMasterVO.setEmailNotificationFlag(true);
    	milestoneMasterVO.setActivityViewFlag(true);
    	milestones.add(milestoneMasterVO);
    	milestoneMasterVO= new MilestoneMasterVO();
    	milestoneMasterVO.setMilestoneCode("DEP");
    	milestoneMasterVO.setEmailNotificationFlag(true);
    	milestoneMasterVO.setSerialNumber(2L);
    	milestoneMasterVO.setCompanyCode("AV");
    	milestoneMasterVO.setMilestoneDescription("Departure");
    	milestoneMasterVO.setMilestoneType("E");
    	milestoneMasterVO.setMilestoneShipmentType("A");
    	milestoneMasterVO.setActivityViewFlag(true);
    	milestones.add(milestoneMasterVO);
    	milestoneMasterVO= new MilestoneMasterVO();
    	milestoneMasterVO.setMilestoneCode("DLV");
    	milestoneMasterVO.setEmailNotificationFlag(true);
    	milestoneMasterVO.setSerialNumber(3L);
    	milestoneMasterVO.setCompanyCode("AV");
    	milestoneMasterVO.setMilestoneDescription("Delivery");
    	milestoneMasterVO.setMilestoneType("I");
    	milestoneMasterVO.setMilestoneShipmentType("A");
    	milestoneMasterVO.setActivityViewFlag(true);
    	milestones.add(milestoneMasterVO);
    	milestoneMasterVO= new MilestoneMasterVO();
    	milestoneMasterVO.setMilestoneCode("RCS");
    	milestoneMasterVO.setEmailNotificationFlag(true);
    	milestoneMasterVO.setSerialNumber(4L);
    	milestoneMasterVO.setCompanyCode("AV");
    	milestoneMasterVO.setMilestoneDescription("Accepted");
    	milestoneMasterVO.setMilestoneType("E");
    	milestoneMasterVO.setMilestoneShipmentType("A");
    	milestoneMasterVO.setActivityViewFlag(true);
    	milestones.add(milestoneMasterVO);
    	milestoneMasterVO= new MilestoneMasterVO();
    	milestoneMasterVO.setMilestoneCode("TRM");
    	milestoneMasterVO.setEmailNotificationFlag(false);
    	milestoneMasterVO.setSerialNumber(5L);
    	milestoneMasterVO.setCompanyCode("AV");
    	milestoneMasterVO.setMilestoneDescription("Transfer Manifest");
    	milestoneMasterVO.setMilestoneType("T");
    	milestoneMasterVO.setMilestoneShipmentType("A");
    	milestoneMasterVO.setActivityViewFlag(true);
    	milestones.add(milestoneMasterVO);
    	return milestones;
    }
    	public List<Milestone> constructMilestoneEntity() {
        	List <Milestone> milestones=new ArrayList<>();
        	Milestone milestoneMaster= new Milestone();
        	milestoneMaster.setSerialNumber(1L);
        	milestoneMaster.setCompanyCode("AV");
        	milestoneMaster.setMilestoneCode("ARR");
        	milestoneMaster.setMilestoneDescription("Arrival");
        	milestoneMaster.setMilestoneType("I");
        	milestoneMaster.setMilestoneShipmentType("A");
        	milestoneMaster.setEmailNotificationFlag(true);
        	milestoneMaster.setActivityViewFlag(true);
        	milestones.add(milestoneMaster);
        	milestoneMaster= new Milestone();
        	milestoneMaster.setMilestoneCode("DEP");
        	milestoneMaster.setEmailNotificationFlag(true);
        	milestoneMaster.setSerialNumber(2L);
        	milestoneMaster.setCompanyCode("AV");
        	milestoneMaster.setMilestoneDescription("Departure");
        	milestoneMaster.setMilestoneType("E");
        	milestoneMaster.setMilestoneShipmentType("A");
        	milestoneMaster.setActivityViewFlag(true);
        	milestones.add(milestoneMaster);
        	milestoneMaster= new Milestone();
        	milestoneMaster.setMilestoneCode("DLV");
        	milestoneMaster.setEmailNotificationFlag(true);
        	milestoneMaster.setSerialNumber(3L);
        	milestoneMaster.setCompanyCode("AV");
        	milestoneMaster.setMilestoneDescription("Delivery");
        	milestoneMaster.setMilestoneType("I");
        	milestoneMaster.setMilestoneShipmentType("A");
        	milestoneMaster.setActivityViewFlag(true);
        	milestones.add(milestoneMaster);
        	milestoneMaster= new Milestone();
        	milestoneMaster.setMilestoneCode("RCS");
        	milestoneMaster.setEmailNotificationFlag(true);
        	milestoneMaster.setSerialNumber(4L);
        	milestoneMaster.setCompanyCode("AV");
        	milestoneMaster.setMilestoneDescription("Accepted");
        	milestoneMaster.setMilestoneType("E");
        	milestoneMaster.setMilestoneShipmentType("A");
        	milestoneMaster.setActivityViewFlag(true);
        	milestones.add(milestoneMaster);
        	milestoneMaster= new Milestone();
        	milestoneMaster.setMilestoneCode("TRM");
        	milestoneMaster.setEmailNotificationFlag(false);
        	milestoneMaster.setSerialNumber(5L);
        	milestoneMaster.setCompanyCode("AV");
        	milestoneMaster.setMilestoneDescription("Transfer Manifest");
        	milestoneMaster.setMilestoneType("T");
        	milestoneMaster.setMilestoneShipmentType("A");
        	milestoneMaster.setActivityViewFlag(false);
        	milestones.add(milestoneMaster);
        	return milestones;
    }
}
