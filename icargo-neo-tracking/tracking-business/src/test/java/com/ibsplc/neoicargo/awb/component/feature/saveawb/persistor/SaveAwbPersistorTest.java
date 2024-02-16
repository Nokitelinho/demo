package com.ibsplc.neoicargo.awb.component.feature.saveawb.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class SaveAwbPersistorTest {
    @InjectMocks
    private SaveAwbPersistor saveAwbPersistor;

    @Mock
    private AwbDAO awbDAO;
    private AwbEntityMapper awbEntityMapper;
    private Quantities quantities;
    private QuantityMapper quantityMapper;
    private static final String shipmentPrefix = "134";
    private static final String masterDocumentNumber = "789040";
    private ShipmentKey shipmentKey;
    private AwbVO awbVO;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
        quantityMapper = MockQuantity.injectMapper(quantities, QuantityMapper.class);
        awbEntityMapper = MockQuantity.injectMapper(quantities, AwbEntityMapper.class);
        MockitoAnnotations.initMocks(this);

        shipmentKey = new ShipmentKey(shipmentPrefix, masterDocumentNumber);
        awbVO = MockDataHelper.constructAwbVO(shipmentPrefix, masterDocumentNumber, quantities);
    }

    @Test
    public void shouldSaveAwb() {

        //given
        var trackingAWBMasterCaptor = ArgumentCaptor.forClass(Awb.class);

        doNothing().when(awbDAO).saveAwb(trackingAWBMasterCaptor.capture());
        doReturn(Optional.empty()).when(awbDAO).findAwbByShipmentKey(shipmentKey);

        //when
        saveAwbPersistor.persist(awbVO);

        //then
        Awb actual = trackingAWBMasterCaptor.getValue();
        Assertions.assertEquals(awbVO.getMasterDocumentNumber(), actual.getShipmentKey().getMasterDocumentNumber());
        Assertions.assertEquals("KPB", actual.getOrigin());
        Assertions.assertEquals("MUC", actual.getDestination());
        Assertions.assertEquals(100, actual.getStatedPieces());
        Assertions.assertEquals("SC", actual.getAwbContactDetails().getShipperCode());
    }

    @Test
    public void shouldUpdateAwb() {

        //given
        awbVO.setShipmentDescription("updated");
        awbVO.getAwbContactDetailsVO().setCompanyCode("updated");
        var trackingAWBMasterCaptor = ArgumentCaptor.forClass(Awb.class);

        doNothing().when(awbDAO).saveAwb(trackingAWBMasterCaptor.capture());
        doReturn(Optional.ofNullable(MockDataHelper.constructAwbEntity(shipmentKey, quantities))).when(awbDAO).findAwbByShipmentKey(shipmentKey);

        //when
        saveAwbPersistor.persist(awbVO);

        //then
        Assertions.assertEquals(awbVO.getMasterDocumentNumber(), trackingAWBMasterCaptor.getValue().getShipmentKey().getMasterDocumentNumber());
        Assertions.assertEquals("updated", trackingAWBMasterCaptor.getValue().getShipmentDescription());
        Assertions.assertEquals("updated", trackingAWBMasterCaptor.getValue().getAwbContactDetails().getCompanyCode());
    }

    @Test
    public void shouldSaveAwbWithoutContactDetails() {

        //given
        awbVO.setAwbContactDetailsVO(null);
        var trackingAWBMasterCaptor = ArgumentCaptor.forClass(Awb.class);

        doNothing().when(awbDAO).saveAwb(trackingAWBMasterCaptor.capture());
        doReturn(Optional.empty()).when(awbDAO).findAwbByShipmentKey(shipmentKey);

        //when
        saveAwbPersistor.persist(awbVO);

        //then
        Assertions.assertEquals(awbVO.getMasterDocumentNumber(), trackingAWBMasterCaptor.getValue().getShipmentKey().getMasterDocumentNumber());
        Assertions.assertNull(trackingAWBMasterCaptor.getValue().getAwbContactDetails());
    }

    @Test
    public void shouldUpdateAwbWithoutContact() {

        //given
        awbVO.setShipmentDescription("updated");
        var trackingAWBMasterCaptor = ArgumentCaptor.forClass(Awb.class);
        var existAwb = MockDataHelper.constructAwbEntity(shipmentKey, quantities);
        existAwb.setAwbContactDetails(null);

        doNothing().when(awbDAO).saveAwb(trackingAWBMasterCaptor.capture());
        doReturn(Optional.ofNullable(existAwb)).when(awbDAO).findAwbByShipmentKey(shipmentKey);

        //when
        saveAwbPersistor.persist(awbVO);

        //then
        Assertions.assertEquals(awbVO.getMasterDocumentNumber(), trackingAWBMasterCaptor.getValue().getShipmentKey().getMasterDocumentNumber());
        Assertions.assertEquals("updated", trackingAWBMasterCaptor.getValue().getShipmentDescription());
        Assertions.assertEquals("SC", trackingAWBMasterCaptor.getValue().getAwbContactDetails().getShipperCode());
        Assertions.assertEquals("CC", trackingAWBMasterCaptor.getValue().getAwbContactDetails().getConsigneeCode());
    }

}