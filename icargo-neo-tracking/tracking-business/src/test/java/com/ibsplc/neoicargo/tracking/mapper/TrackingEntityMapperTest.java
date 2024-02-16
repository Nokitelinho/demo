package com.ibsplc.neoicargo.tracking.mapper;

import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
class TrackingEntityMapperTest {

    private Quantities quantities;
    private TrackingEntityMapper trackingEntityMapper;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, null, null);
        trackingEntityMapper = MockQuantity.injectMapper(quantities, TrackingEntityMapper.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldConstructShipmentMilestoneEventVO() {

        //given
        var event = MockDataHelper.constructShipmentMilestoneEvent();

        //when
        var actualEventVO = trackingEntityMapper.constructShipmentMilestoneEventVO(event);

        //then
        assertEquals("134-23401475", actualEventVO.getShipmentKey());
        assertEquals(MilestoneCodeEnum.RCF, actualEventVO.getMilestoneCode());
        assertEquals("A", actualEventVO.getShipmentType());
        assertEquals("DXB", actualEventVO.getAirportCode());
        assertEquals(5, actualEventVO.getPieces());
        assertEquals(event.getMilestoneTime(), actualEventVO.getMilestoneTime());
        assertEquals(event.getMilestoneTimeUTC(), actualEventVO.getMilestoneTimeUTC());
        assertEquals("K", actualEventVO.getWeight().getDisplayUnit().getName());
        assertEquals(1.2, actualEventVO.getWeight().getDisplayValue().doubleValue());
        assertEquals("val_1", ((Map)actualEventVO.getTransactionDetails()).get("key_1"));
    }

    @Test
    void shouldConstructShipmentMilestoneEvent() {

        //given
        var eventVO = MockDataHelper.constructShipmentMilestoneEventVOWithWeight(quantities);

        // when
        var actualEvent = trackingEntityMapper.constructShipmentMilestoneEvent(eventVO);

        //then
        assertEquals("134-23401475", actualEvent.getShipmentKey());
        assertEquals("RCF", actualEvent.getMilestoneCode());
        assertEquals("A", actualEvent.getShipmentType());
        assertEquals("DXB", actualEvent.getAirportCode());
        assertEquals(5, actualEvent.getPieces());
        assertEquals(eventVO.getMilestoneTime(), actualEvent.getMilestoneTime());
        assertEquals(eventVO.getMilestoneTimeUTC(), actualEvent.getMilestoneTimeUTC());
        assertEquals(1.2, actualEvent.getWeight());
        assertEquals("K", actualEvent.getEUnit().getWeight());

        assertEquals("val_1", ((Map)actualEvent.getTransactionDetails()).get("key_1"));

    }

    @Test
    void shouldConstructShipmentMilestoneEventNullWeight() {

        //given
        var eventVO = MockDataHelper.constructShipmentMilestoneEventVOWithWeight(quantities);
        eventVO.setWeight(null);

        // when
        var actualEvent = trackingEntityMapper.constructShipmentMilestoneEvent(eventVO);

        //then
        assertEquals("134-23401475", actualEvent.getShipmentKey());
        assertEquals("RCF", actualEvent.getMilestoneCode());
        assertEquals("A", actualEvent.getShipmentType());
        assertEquals("DXB", actualEvent.getAirportCode());
        assertEquals(5, actualEvent.getPieces());
        assertEquals(eventVO.getMilestoneTime(), actualEvent.getMilestoneTime());
        assertEquals(eventVO.getMilestoneTimeUTC(), actualEvent.getMilestoneTimeUTC());
        assertEquals( 0.0, actualEvent.getWeight());
        assertEquals("K", actualEvent.getEUnit().getWeight());

        assertEquals("val_1", ((Map)actualEvent.getTransactionDetails()).get("key_1"));

    }
}