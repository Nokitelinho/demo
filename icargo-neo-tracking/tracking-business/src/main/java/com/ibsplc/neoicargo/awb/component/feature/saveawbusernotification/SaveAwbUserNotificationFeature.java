package com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification;

import com.ibsplc.neoicargo.awb.component.feature.saveawbusernotification.persistor.SaveAwbUserNotificationPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbUserNotificationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@FeatureConfigSource("feature/awb/saveusernotification")
@Component("saveAwbUserNotificationFeature")
public class SaveAwbUserNotificationFeature extends AbstractFeature<AwbUserNotificationVO> {

    private final SaveAwbUserNotificationPersistor persistor;

    @Override
    protected Void perform(AwbUserNotificationVO awbUserNotificationVO) throws BusinessException {
        log.info("Invoked save awb user notification feature!");
        persistor.persist(awbUserNotificationVO);
        return null;
    }
}
