package com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist;

import com.ibsplc.neoicargo.awb.component.AwbComponent;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.CalculateFlightDataFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist.invoker.CalculateFlightDateInvoker;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
public class GetShipmentListFeatureTest {

    @InjectMocks
    private GetShipmentListFeature getShipmentListFeature;
    @Mock
    private TrackingDAO trackingDAO;
    @Mock
    private CalculateFlightDataFeature calculateFlightDataFeature;
    @Mock
    private AwbComponent awbComponent;
    @Mock
    private CalculateFlightDateInvoker calculateFlightDateInvoker;

    private TrackingMapper trackingMapper;
    private TrackingEntityMapper trackingEntityMapper;
    private Quantities quantities;
    private QuantityMapper quantityMapper;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        quantityMapper = MockQuantity.injectMapper(quantities, QuantityMapper.class);
        trackingMapper = MockQuantity.injectMapper(quantities, TrackingMapper.class);
        trackingEntityMapper = MockQuantity.injectMapper(quantities, TrackingEntityMapper.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnShipmentDetails() {

        //given
        var shipmentKeyString = "020-222222";
        var request = new AwbRequestVO(shipmentKeyString);

        var awbVO = MockDataHelper.constructAwbVO(request.getShipmentPrefix(), request.getMasterDocumentNumber(), quantities);
        var trackingShipmentMilestonePlans = MockDataHelper.constructTrackingShipmentMilestonePlanEntities();
        var trackingShipmentMilestoneEvents = MockDataHelper.constructShipmentMilestoneEventEntities();

        doReturn(trackingShipmentMilestonePlans).when(trackingDAO).findPlansByShipmentKeys(List.of(shipmentKeyString));
        doReturn(trackingShipmentMilestoneEvents).when(trackingDAO)
                .findEventsByShipmentKeysAndType(List.of(shipmentKeyString), "A");
        doAnswer(returnsFirstArg()).when(calculateFlightDataFeature).execute(any(ShipmentDetailsVO.class));
        doReturn(List.of(awbVO)).when(awbComponent).getAwbList(List.of(request));

        //when
        var details = getShipmentListFeature.perform(List.of(request));

        //then
        Assertions.assertNotNull(details);
        Assertions.assertEquals("020", details.get(0).getShipmentPrefix());
        Assertions.assertEquals("222222", details.get(0).getMasterDocumentNumber());
        verify(calculateFlightDateInvoker, times(1)).invoke(any(ShipmentDetailsVO.class));
        verify(trackingDAO).findPlansByShipmentKeys(anyList());
        verify(trackingDAO).findEventsByShipmentKeysAndType(anyList(), anyString());
    }

    @Test
    public void shouldReturnNull() {

        //given
        var shipmentKey = new ShipmentKey("134", "789040");
        var request = new AwbRequestVO("134-789040");

        doReturn(emptyList()).when(awbComponent).getAwbList(List.of(request));

        //when
        var details = getShipmentListFeature.perform(List.of(request));

        //then
        Assertions.assertNotNull(details);
        Assertions.assertEquals(0, details.size());
    }
}
