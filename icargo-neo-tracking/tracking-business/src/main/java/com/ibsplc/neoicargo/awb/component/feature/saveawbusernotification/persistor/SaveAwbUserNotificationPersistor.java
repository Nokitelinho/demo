package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveAwbUserNotificationPersistor {

    private final AwbDAO awbDAO;
    private final AwbEntityMapper entityMapper;

    public void persist(AwbUserNotificationVO awbUserNotificationVO) {
        var newNotification = entityMapper.constructAwbUserNotification(awbUserNotificationVO);
        awbDAO.findAwbUserNotificationByKey(newNotification.getNotificationsKey()).ifPresentOrElse(existing -> {
            existing.setNotificationMilestones(newNotification.getNotificationMilestones());
            existing.setEmails(newNotification.getEmails());
            awbDAO.saveAwbUserNotification(existing);
        }, () -> awbDAO.saveAwbUserNotification(newNotification));
    }

}
