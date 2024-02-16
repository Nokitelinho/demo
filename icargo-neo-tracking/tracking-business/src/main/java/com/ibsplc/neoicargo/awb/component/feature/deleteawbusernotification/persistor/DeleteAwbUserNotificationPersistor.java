package com.ibsplc.neoicargo.awb.component.feature.deleteawbusernotification.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotificationKey;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteAwbUserNotificationPersistor {

    private final AwbDAO awbDAO;

    public void persist(AwbUserNotificationKeyVO notificationKeyVO) {
        var notificationKey = new AWBUserNotificationKey(notificationKeyVO.getTrackingAwbSerialNumber(), notificationKeyVO.getUserId());
        awbDAO.findAwbUserNotificationByKey(notificationKey).ifPresentOrElse(awbDAO::deleteAwbUserNotification, () -> log.info("There is no awb notification found to be deleted for the key: {}", notificationKey));
    }

}
