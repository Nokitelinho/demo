package com.ibsplc.neoicargo.tracking.inttest;

import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.dao.impl.repositories.ShipmentMilestoneEventRepository;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum.DIS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipmentMilestoneEventListenerIT extends TrackingAPIBase {

    @Autowired
    private StubTrigger trigger;
    @Autowired
    private ShipmentMilestoneEventRepository eventRepository;

    @Test
    public void shouldListenShipmentMilestoneEventSave() throws JSONException {

        //when
        trigger.trigger("saveShipmentHistoryEventRCS");
        trigger.trigger("saveShipmentHistoryEventNFD");
        trigger.trigger("saveShipmentHistoryEventRCF");
        trigger.trigger("saveShipmentHistoryEventPRE");
        trigger.trigger("saveShipmentHistoryEventMAN");
        trigger.trigger("saveShipmentHistoryEventBKD");
        trigger.trigger("saveShipmentHistoryEventDEP");
        trigger.trigger("saveShipmentHistoryEventARR");
        trigger.trigger("saveShipmentHistoryEventDIS");
        trigger.trigger("saveShipmentHistoryEventRCT");
        trigger.trigger("saveShipmentHistoryEventTRM");
        trigger.trigger("saveShipmentHistoryEventTFD");
        trigger.trigger("saveShipmentHistoryEventDRN");

        //then
        var firstKeyEvents = eventRepository.findByShipmentKeyInAndShipmentType(List.of("134-88028802"), "A");
        assertEquals(1, firstKeyEvents.size());
        var firstKeyEvent = firstKeyEvents.get(0);
        assertEquals("134-88028802", firstKeyEvent.getShipmentKey());
        assertEquals("A", firstKeyEvent.getShipmentType());
        assertEquals(15, firstKeyEvent.getShipmentSequenceNumber());
        assertEquals("CDG", firstKeyEvent.getAirportCode());
        assertEquals(30, firstKeyEvent.getPieces());
        assertEquals("RCS", firstKeyEvent.getMilestoneCode());
        assertEquals("AV", firstKeyEvent.getCompanyCode());
        assertEquals("ICOADMIN", firstKeyEvent.getLastUpdateUser());


        var secondKeyEvents = eventRepository.findByShipmentKeyInAndShipmentType(List.of("134-88758875"), "A");
        assertEquals(4, secondKeyEvents.size());
        Assertions.assertTrue(secondKeyEvents.stream().allMatch(event -> event.getShipmentKey().equals("134-88758875")));
        Assertions.assertTrue(secondKeyEvents.stream().map(ShipmentMilestoneEvent::getAirportCode)
                .distinct().collect(Collectors.toList()).containsAll(List.of("CDG", "SIN")));
        Assertions.assertTrue(secondKeyEvents.stream().map(ShipmentMilestoneEvent::getMilestoneCode)
                .distinct().collect(Collectors.toList()).containsAll(List.of("NFD", "RCF", "PRE", "MAN")));

        var thirdKeyEvents = eventRepository.findByShipmentKeyInAndShipmentType(List.of("134-23401184"), "A");
        assertEquals(1, thirdKeyEvents.size());
        var thirdKeyEvent = thirdKeyEvents.get(0);
        assertEquals("134-23401184", thirdKeyEvent.getShipmentKey());
        assertEquals("A", thirdKeyEvent.getShipmentType());
        assertEquals(17, thirdKeyEvent.getShipmentSequenceNumber());
        assertEquals("SIN", thirdKeyEvent.getAirportCode());
        assertEquals(12, thirdKeyEvent.getPieces());
        assertEquals("BKD", thirdKeyEvent.getMilestoneCode());
        Assertions.assertTrue(((Map) thirdKeyEvent.getTransactionDetails()).keySet().containsAll(List.of("flightCarrierCode", "flightNumber")));

        var fourthKeyEvents = eventRepository.findByShipmentKeyInAndShipmentType(List.of("134-37833783"), "A");
        assertEquals(2, fourthKeyEvents.size());
        Assertions.assertTrue(fourthKeyEvents.stream().allMatch(event -> event.getShipmentKey().equals("134-37833783")));
        Assertions.assertTrue(fourthKeyEvents.stream().map(ShipmentMilestoneEvent::getAirportCode)
                .distinct().collect(Collectors.toList()).containsAll(List.of("SIN", "DXB")));
        Assertions.assertTrue(fourthKeyEvents.stream().map(ShipmentMilestoneEvent::getMilestoneCode)
                .distinct().collect(Collectors.toList()).containsAll(List.of("DEP", "ARR")));

        var fifthKeyEvents = eventRepository.findByShipmentKeyInAndShipmentType(List.of("134-00100284"), "A");
        Assertions.assertEquals(4, fifthKeyEvents.size());
        Assertions.assertTrue(fifthKeyEvents.stream().allMatch(event -> event.getShipmentKey().equals("134-00100284")));
        Assertions.assertTrue(fifthKeyEvents.stream().map(ShipmentMilestoneEvent::getAirportCode)
                .distinct().collect(Collectors.toList()).containsAll(List.of("DXB")));
        Assertions.assertTrue(fifthKeyEvents.stream().map(ShipmentMilestoneEvent::getMilestoneCode)
                .distinct().collect(Collectors.toList()).containsAll(List.of("DIS", "TRM", "TFD", "DRN")));
        fifthKeyEvents.forEach(event -> {
            if (event.getMilestoneCode().equals(DIS.getLabel())) {
                assertEquals("DXB", event.getAirportCode());
                Assertions.assertTrue(((Map) event.getTransactionDetails()).keySet().containsAll(List.of("flightCarrierCode", "flightNumber", "reasonCode", "reasonDescription")));
            }
        });
        Assertions.assertTrue(fifthKeyEvents.stream().map(ShipmentMilestoneEvent::getWeight)
                .distinct().collect(Collectors.toList()).containsAll(List.of(20.0)));
        Assertions.assertTrue(fifthKeyEvents.stream().map(event -> event.getEUnit().getWeight())
                .distinct().collect(Collectors.toList()).containsAll(List.of("L")));
    }
}
