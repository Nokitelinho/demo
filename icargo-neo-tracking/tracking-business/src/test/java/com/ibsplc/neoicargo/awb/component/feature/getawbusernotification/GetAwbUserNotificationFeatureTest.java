package com.ibsplc.neoicargo.awb.component.feature.getawbusernotification;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAwbUserNotificationFeatureTest {

    @Mock
    private AwbDAO awbDAO;
    @Mock
    private ContextUtil contextUtil;
    @Mock
    private LoginProfile loginProfile;
    @Mock
    private AwbEntityMapper awbEntityMapper;


    @InjectMocks
    private GetAwbUserNotificationFeature feature;

    private static final String SHIPMENT_PREFIX = "123";
    private static final String MASTER_DOCUMENT_MASTER = "45678910";
    private static final String AWB = "123-45678910";
    private static final String USER_ID = "userId";
    private static final AwbRequestVO AWB_REQUEST_VO = new AwbRequestVO(AWB);


    @Test
    void shouldPerformAndReturnAwbUserNotification() {
        var awb = new Awb();
        awb.setSerialNumber(1L);
        var notification = MockDataHelper.constructAwbUserNotification();
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        var shipmentKeyArgumentCaptor
                = ArgumentCaptor.forClass(ShipmentKey.class);
        var awbNotificationKeyCaptor = ArgumentCaptor.forClass(AWBUserNotificationKey.class);
        when(awbDAO.findAwbByShipmentKey(any(ShipmentKey.class))).thenReturn(Optional.of(awb));
        when(awbDAO.findAwbUserNotificationByKey(any(AWBUserNotificationKey.class))).thenReturn(Optional.of(notification));
        when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
        when(loginProfile.getUserId()).thenReturn(USER_ID);
        when(awbEntityMapper.constructAwbUserNotificationVO(notification)).thenReturn(notificationVO);


        var awbUserNotificationVO = feature.perform(AWB_REQUEST_VO);

        assertEquals(notificationVO, awbUserNotificationVO);

        verify(awbDAO).findAwbByShipmentKey(shipmentKeyArgumentCaptor.capture());
        var captorValue = shipmentKeyArgumentCaptor.getValue();
        assertEquals(SHIPMENT_PREFIX, captorValue.getShipmentPrefix());
        assertEquals(MASTER_DOCUMENT_MASTER, captorValue.getMasterDocumentNumber());

        verify(awbDAO).findAwbUserNotificationByKey(awbNotificationKeyCaptor.capture());
        var awbNotificationKeyCaptorValue = awbNotificationKeyCaptor.getValue();
        assertEquals(awb.getSerialNumber(), awbNotificationKeyCaptorValue.getTrackingAwbSerialNumber());
        assertEquals(USER_ID, awbNotificationKeyCaptorValue.getUserId());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenAwbNotFound() {

        var shipmentKeyArgumentCaptor
                = ArgumentCaptor.forClass(ShipmentKey.class);
        when(awbDAO.findAwbByShipmentKey(any(ShipmentKey.class))).thenReturn(Optional.empty());


        verifyNoInteractions(awbDAO);
        verifyNoInteractions(contextUtil);
        verifyNoInteractions(awbEntityMapper);


        assertThrows(NotFoundException.class, () -> feature.perform(AWB_REQUEST_VO));

        verify(awbDAO).findAwbByShipmentKey(shipmentKeyArgumentCaptor.capture());
        var captorValue = shipmentKeyArgumentCaptor.getValue();
        assertEquals(SHIPMENT_PREFIX, captorValue.getShipmentPrefix());
        assertEquals(MASTER_DOCUMENT_MASTER, captorValue.getMasterDocumentNumber());
    }

}