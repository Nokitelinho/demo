package com.ibsplc.neoicargo.awb.component.feature.executeawb;

import com.ibsplc.neoicargo.awb.component.feature.executeawb.persistor.ExecuteAwbPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("executeAwbFeature")
public class ExecuteAwbFeature {

    private final ExecuteAwbPersistor executeAwbPersistor;

    public void perform(AwbValidationVO awbValidationVO) {
        log.info("Invoked execute awb Feature!");
        executeAwbPersistor.persist(awbValidationVO);
    }
}