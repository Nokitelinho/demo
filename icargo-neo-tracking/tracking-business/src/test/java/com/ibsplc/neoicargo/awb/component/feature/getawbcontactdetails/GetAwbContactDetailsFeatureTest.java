package com.ibsplc.neoicargo.awb.component.feature.getawbcontactdetails;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBContactDetails;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.tracking.exception.TrackingErrors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAwbContactDetailsFeatureTest {

    @Mock
    private AwbDAO awbDAO;
    @Mock
    private AwbEntityMapper mapper;

    @InjectMocks
    private GetAwbContactDetailsFeature feature;

    private static final String SHIPMENT_PREFIX = "123";
    private static final String MASTER_DOCUMENT_MASTER = "45678910";
    private static final String AWB = "123-45678910";

    @Test
    void shouldReturnawbContactDetailsWhenAwbFoundAndPersonalInfoFound() {
        var awbRequestVO = new AwbRequestVO(AWB);
        var personalInfo = new AWBContactDetails();
        var personalInfoVO = new AwbContactDetailsVO();
        var awb = new Awb();
        awb.setAwbContactDetails(personalInfo);
        var shipmentKeyArgumentCaptor
                = ArgumentCaptor.forClass(ShipmentKey.class);
        when(awbDAO.findAwbByShipmentKey(any(ShipmentKey.class))).thenReturn(Optional.of(awb));
        when(mapper.constructAwbContactDetailsVO(personalInfo)).thenReturn(personalInfoVO);

        AwbContactDetailsVO awbContactDetailsVO = feature.perform(awbRequestVO);
        assertEquals(personalInfoVO, awbContactDetailsVO);

        verify(awbDAO).findAwbByShipmentKey(shipmentKeyArgumentCaptor.capture());
        var captorValue = shipmentKeyArgumentCaptor.getValue();
        assertEquals(SHIPMENT_PREFIX, captorValue.getShipmentPrefix());
        assertEquals(MASTER_DOCUMENT_MASTER, captorValue.getMasterDocumentNumber());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAwbNotFound() {
        var awbRequestVO = new AwbRequestVO(AWB);
        var shipmentKeyArgumentCaptor
                = ArgumentCaptor.forClass(ShipmentKey.class);
        when(awbDAO.findAwbByShipmentKey(any(ShipmentKey.class))).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> feature.perform(awbRequestVO));

        assertEquals(TrackingErrors.AWB_NOT_FOUND.getErrorMessage(), exception.getMessage());

        verify(awbDAO).findAwbByShipmentKey(shipmentKeyArgumentCaptor.capture());
        var captorValue = shipmentKeyArgumentCaptor.getValue();
        assertEquals(SHIPMENT_PREFIX, captorValue.getShipmentPrefix());
        assertEquals(MASTER_DOCUMENT_MASTER, captorValue.getMasterDocumentNumber());
    }

    @Test
    void shouldReturnNullWhenAwbFoundButPersonalInfoNotFound() {
        var awbRequestVO = new AwbRequestVO(AWB);
        var awb = new Awb();
        var shipmentKeyArgumentCaptor
                = ArgumentCaptor.forClass(ShipmentKey.class);
        when(awbDAO.findAwbByShipmentKey(any(ShipmentKey.class))).thenReturn(Optional.of(awb));

        assertNull(feature.perform(awbRequestVO));

        verify(awbDAO).findAwbByShipmentKey(shipmentKeyArgumentCaptor.capture());
        var captorValue = shipmentKeyArgumentCaptor.getValue();
        assertEquals(SHIPMENT_PREFIX, captorValue.getShipmentPrefix());
        assertEquals(MASTER_DOCUMENT_MASTER, captorValue.getMasterDocumentNumber());
    }
}