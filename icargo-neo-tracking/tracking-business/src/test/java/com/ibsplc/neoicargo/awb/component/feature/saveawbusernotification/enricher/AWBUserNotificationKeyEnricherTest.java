package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.enricher;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.exception.TrackingErrors;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AWBUserNotificationKeyEnricherTest {

    @Mock
    private AwbDAO awbDAO;
    @Mock
    private ContextUtil contextUtil;
    @Mock
    private LoginProfile loginProfile;
    @InjectMocks
    private AWBUserNotificationKeyEnricher enricher;

    private static final String USER_ID = "userId";


    @Test
    void shouldEnrich() throws BusinessException {
        var awb = new Awb();
        awb.setSerialNumber(1L);
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        notificationVO.setNotificationsKey(null);
        when(awbDAO.findAwbByShipmentKey(notificationVO.getShipmentKey())).thenReturn(Optional.of(awb));
        when(contextUtil.callerLoginProfile()).thenReturn(loginProfile);
        when(loginProfile.getUserId()).thenReturn(USER_ID);

        enricher.enrich(notificationVO);

        assertEquals(awb.getSerialNumber(), notificationVO.getNotificationsKey().getTrackingAwbSerialNumber());
        assertEquals(USER_ID, notificationVO.getNotificationsKey().getUserId());
    }

    @Test
    void shouldThrowExceptionWhenNoAwbFound() {
        var notificationVO = MockDataHelper.constructAwbUserNotificationVO();
        when(awbDAO.findAwbByShipmentKey(notificationVO.getShipmentKey())).thenReturn(Optional.empty());

        var exception = assertThrows(NotFoundException.class, () -> enricher.enrich(notificationVO));
        assertEquals(TrackingErrors.AWB_NOT_FOUND.getErrorMessage(), exception.getMessage());

        verifyNoInteractions(contextUtil);
    }
}