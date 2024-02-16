package com.ibsplc.neoicargo.awb.component.feature.executeawb;

import com.ibsplc.neoicargo.awb.component.feature.executeawb.persistor.ExecuteAwbPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
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
public class ExecuteAwbFeatureTest {
    @InjectMocks
    private ExecuteAwbFeature executeTrackingAWBMasterFeature;
    @Mock
    private ExecuteAwbPersistor executeAwbPersistor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldExecuteAwb() {
        //given
        doNothing().when(executeAwbPersistor).persist(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> executeTrackingAWBMasterFeature.perform(any(AwbValidationVO.class)));
    }
}