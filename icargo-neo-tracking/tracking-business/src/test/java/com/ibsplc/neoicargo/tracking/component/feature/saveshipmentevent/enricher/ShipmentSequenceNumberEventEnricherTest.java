package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.enricher;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentSequenceNumberEventEnricherTest {

    @Mock
    private AwbDAO awbDAO;

    @InjectMocks
    private ShipmentSequenceNumberEventEnricher enricher;

    private final static Quantities QUANTITIES = MockQuantity.performInitialisation(null, null, "TRV", null);

    @Test
    void shouldEnrichWithShipmentSequenceNumber() {

        // given
        var eventVO = MockDataHelper.constructShipmentMilestoneEventVO();
        var shipmentKeyArr = eventVO.getShipmentKey().split("-");
        var shipmentKey = new ShipmentKey(shipmentKeyArr[0], shipmentKeyArr[1]);
        var trackingAwbMaster = MockDataHelper.constructAwbEntity(shipmentKey, QUANTITIES);

        when(awbDAO.findAwbByShipmentKey(shipmentKey)).thenReturn(Optional.of(trackingAwbMaster));

        // when
        enricher.enrich(eventVO);

        // then
        assertEquals(trackingAwbMaster.getShipmentSequenceNumber().intValue(), (int) eventVO.getShipmentSequenceNumber());
    }

    @Test
    void shouldThrowRuntimeExceptionWhenShipmentKeyNotMappedToAWB() {

        var eventVO = MockDataHelper.constructShipmentMilestoneEventVO();

        when(awbDAO.findAwbByShipmentKey(any())).thenReturn(Optional.empty());
        var exception = assertThrows(RuntimeException.class, () ->
                enricher.enrich(eventVO));
        //var countOfShipmentSequenceNumber = planCreatedVO.getPlanVOs().stream().map(ShipmentMilestonePlanVO::getShipmentSequenceNumber).filter(Objects::nonNull).count();

        assertTrue(exception.getMessage().contains("No AWB found for a shipmentKey"));
       //assertEquals(0L, countOfShipmentSequenceNumber);

    }
}