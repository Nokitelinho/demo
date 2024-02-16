package com.ibsplc.neoicargo.tracking.component.feature.deleteshipment;

import com.ibsplc.neoicargo.tracking.component.feature.deleteshipment.persistor.DeleteShipmentPersistor;
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
public class DeleteShipmentFeatureTest {
    @InjectMocks
    private DeleteShipmentFeature deleteShipmentFeature;
    @Mock
    private DeleteShipmentPersistor deleteShipmentPersistor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDeleteShipment() {
        //given
        doNothing().when(deleteShipmentPersistor).persist(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> deleteShipmentFeature.perform(any(AwbValidationVO.class)));
    }
}