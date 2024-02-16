package com.ibsplc.neoicargo.awb.component.feature.reopenawb;

import com.ibsplc.neoicargo.awb.component.feature.reopenawb.persistor.ReopenAwbPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("reopenAwbFeature")
public class ReopenAwbFeature {

    private final ReopenAwbPersistor reopenAwbPersistor;

    public void perform(AwbValidationVO awbValidationVO) {
        log.info("Invoked reopen awb Feature!");
        reopenAwbPersistor.persist(awbValidationVO);
    }
}