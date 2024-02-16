package com.ibsplc.neoicargo.awb.component.feature.reopenawb;

import com.ibsplc.neoicargo.awb.component.feature.reopenawb.persistor.ReopenAwbPersistor;
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
public class ReopenAwbFeatureTest {

    @InjectMocks
    private ReopenAwbFeature reopenAwbFeature;
    @Mock
    private ReopenAwbPersistor reopenAwbPersistor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReopenAwb() {
        //given
        doNothing().when(reopenAwbPersistor).persist(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> reopenAwbFeature.perform(any(AwbValidationVO.class)));
    }
}