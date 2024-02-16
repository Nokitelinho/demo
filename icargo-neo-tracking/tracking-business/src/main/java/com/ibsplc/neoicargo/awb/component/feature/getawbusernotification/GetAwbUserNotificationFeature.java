package com.ibsplc.neoicargo.awb.component.feature.getawbusernotification;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;

import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.AWB_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAwbUserNotificationFeature {

    private final AwbDAO awbDAO;
    private final AwbEntityMapper entityMapper;
    private final ContextUtil contextUtil;

    public AwbUserNotificationVO perform(AwbRequestVO awbRequestVO) {
        log.info("Invoked get awb user notification feature!");

        var shipmentKey = new ShipmentKey(awbRequestVO.getShipmentPrefix(), awbRequestVO.getMasterDocumentNumber());
        var awb = awbDAO.findAwbByShipmentKey(shipmentKey)
                .orElseThrow(() -> new NotFoundException(AWB_NOT_FOUND.getErrorMessage()));

        var awbUserNotification = awbDAO.findAwbUserNotificationByKey(new AWBUserNotificationKey(awb.getSerialNumber(), contextUtil.callerLoginProfile().getUserId()));

        return entityMapper.constructAwbUserNotificationVO(awbUserNotification.orElse(null));
    }
}
