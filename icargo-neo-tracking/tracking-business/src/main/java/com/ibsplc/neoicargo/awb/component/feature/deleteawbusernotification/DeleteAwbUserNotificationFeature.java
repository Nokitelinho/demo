package com.ibsplc.neoicargo.awb.component.feature.deleteawbusernotification;

import com.ibsplc.neoicargo.awb.component.feature.deleteawbusernotification.persistor.DeleteAwbUserNotificationPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationKeyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteAwbUserNotificationFeature {

    private final DeleteAwbUserNotificationPersistor persistor;

    public void perform(AwbUserNotificationKeyVO awbUserNotificationKeyVO) {
        log.info("Invoked delete awb user notification feature!");
        persistor.persist(awbUserNotificationKeyVO);
    }
}
