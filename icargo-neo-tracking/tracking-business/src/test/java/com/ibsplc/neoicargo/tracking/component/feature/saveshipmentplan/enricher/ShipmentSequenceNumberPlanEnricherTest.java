package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.enricher;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentSequenceNumberPlanEnricherTest {

    @Mock
    private AwbDAO awbDAO;

    @InjectMocks
    private ShipmentSequenceNumberPlanEnricher enricher;

    private final static Quantities QUANTITIES = MockQuantity.performInitialisation(null, null, "TRV", null);

    @Test
    void shouldEnrichWithShipmentSequenceNumber() {

        var planCreatedVO = MockDataHelper.constructShipmentMilestonePlanCreatedVO();
        var shipmentKeyArr = planCreatedVO.getPlanVOs().get(0).getShipmentKey().split("-");
        var shipmentKey = new ShipmentKey(shipmentKeyArr[0], shipmentKeyArr[1]);
        var trackingAwbMaster = MockDataHelper.constructAwbEntity(shipmentKey, QUANTITIES);

        when(awbDAO.findAwbByShipmentKey(shipmentKey)).thenReturn(Optional.of(trackingAwbMaster));

        enricher.enrich(planCreatedVO);

        var shipmentSequenceNumber = planCreatedVO.getPlanVOs().stream().map(ShipmentMilestonePlanVO::getShipmentSequenceNumber).findFirst().get();
        assertEquals(shipmentSequenceNumber.intValue(), trackingAwbMaster.getShipmentSequenceNumber().intValue());

    }

    @Test
    void shouldThrowRuntimeExceptionWhenShipmentKeyIsBlank() {

        var planCreatedVO = new ShipmentMilestonePlanCreatedVO();
        planCreatedVO.setPlanVOs(List.of(new ShipmentMilestonePlanVO()));

        var exception = assertThrows(RuntimeException.class, () ->
                enricher.enrich(planCreatedVO));
        var countOfShipmentSequenceNumber = planCreatedVO.getPlanVOs().stream().map(ShipmentMilestonePlanVO::getShipmentSequenceNumber).filter(Objects::nonNull).count();

        verifyNoInteractions(awbDAO);
        assertEquals("Shipment key is null or blank", exception.getMessage());
        assertEquals(0L, countOfShipmentSequenceNumber);

    }

    @Test
    void shouldThrowRuntimeExceptionWhenShipmentKeyNotMappedToAWB() {

        var planCreatedVO = MockDataHelper.constructShipmentMilestonePlanCreatedVO();

        when(awbDAO.findAwbByShipmentKey(any())).thenReturn(Optional.empty());
        var exception = assertThrows(RuntimeException.class, () ->
                enricher.enrich(planCreatedVO));
        var countOfShipmentSequenceNumber = planCreatedVO.getPlanVOs().stream().map(ShipmentMilestonePlanVO::getShipmentSequenceNumber).filter(Objects::nonNull).count();

        assertTrue(exception.getMessage().contains("No AWB found for a shipmentKey"));
        assertEquals(0L, countOfShipmentSequenceNumber);

    }
}