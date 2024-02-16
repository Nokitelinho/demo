package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.enricher;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;

import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.AWB_NOT_FOUND;

@AllArgsConstructor
@Component("awbUserNotificationKeyEnricher")
@Slf4j
public class AWBUserNotificationKeyEnricher extends Enricher<AwbUserNotificationVO> {

    private final AwbDAO awbDAO;
    private final ContextUtil contextUtil;

    @Override
    public void enrich(AwbUserNotificationVO awbUserNotificationVO) throws BusinessException {

        var awb = awbDAO.findAwbByShipmentKey(awbUserNotificationVO.getShipmentKey())
                .orElseThrow(() -> new NotFoundException(AWB_NOT_FOUND.getErrorMessage()));

        var notificationsKey = new AWBUserNotificationKey(awb.getSerialNumber(), contextUtil.callerLoginProfile().getUserId());
        awbUserNotificationVO.setNotificationsKey(notificationsKey);

    }
}
