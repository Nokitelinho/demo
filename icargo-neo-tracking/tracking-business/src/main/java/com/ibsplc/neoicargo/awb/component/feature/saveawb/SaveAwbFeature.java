package com.ibsplc.neoicargo.awb.component.feature.saveawb;

import com.ibsplc.neoicargo.awb.component.feature.saveawb.persistor.SaveAwbPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("saveAwbFeature")
@RequiredArgsConstructor
public class SaveAwbFeature {

    private final SaveAwbPersistor saveAwbPersistor;

    public void perform(AwbVO awbVO) {
        log.info("Invoked save awb Feature!");
        saveAwbPersistor.persist(awbVO);
    }
}