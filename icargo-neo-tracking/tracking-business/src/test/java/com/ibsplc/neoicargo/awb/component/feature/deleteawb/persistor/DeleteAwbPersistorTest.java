package com.ibsplc.neoicargo.awb.component.feature.deleteawb.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
public class DeleteAwbPersistorTest {
    @InjectMocks
    private DeleteAwbPersistor deleteAwbPersistor;

    @Mock
    private AwbDAO awbDAO;
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
    public void shouldDeleteAwb() {

        //given
        var shipmentPrefix = "134";
        var masterDocumentNumber = "789040";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var awbValidationVO = MockDataHelper.constructAwbValidationVO(shipmentPrefix, masterDocumentNumber);
        var awbArgumentCaptor = ArgumentCaptor.forClass(Awb.class);

        doNothing().when(awbDAO).deleteAwb(awbArgumentCaptor.capture());
        doReturn(Optional.ofNullable(MockDataHelper.constructAwbEntity(shipmentKey, quantities))).when(awbDAO).findAwbByShipmentKey(shipmentKey);

        //when
        deleteAwbPersistor.persist(awbValidationVO);

        //then
        Assertions.assertEquals(awbValidationVO.getMasterDocumentNumber(), awbArgumentCaptor.getValue().getShipmentKey().getMasterDocumentNumber());
    }

    @Test
    public void shouldNotDeleteAwb() {
        //given
        var shipmentPrefix = "134";
        var masterDocumentNumber = "789040";
        var shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        var trackingAWBMasterValidationVO = MockDataHelper.constructAwbValidationVO(shipmentPrefix, masterDocumentNumber);

        doReturn(Optional.empty()).when(awbDAO).findAwbByShipmentKey(shipmentKey);

        //when
        deleteAwbPersistor.persist(trackingAWBMasterValidationVO);

        //then
        verify(awbDAO, times(0)).saveAwb(any(Awb.class));
    }
}