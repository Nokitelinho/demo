package com.ibsplc.neoicargo.awb.component.feature.saveawb;

import com.ibsplc.neoicargo.awb.component.feature.saveawb.persistor.SaveAwbPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(JUnitPlatform.class)
public class SaveAwbFeatureTest {

    @InjectMocks
    private SaveAwbFeature saveAwbFeature;
    @Mock
    private SaveAwbPersistor saveAwbPersistor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveTrackingAWBMaster() {
        //given
        doNothing().when(saveAwbPersistor).persist(any(AwbVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> saveAwbFeature.perform(any(AwbVO.class)));
    }
}