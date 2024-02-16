package com.ibsplc.neoicargo.awb.component.feature.deleteawb.invoker;

import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.tracking.component.TrackingComponent;
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
public class DeleteShipmentInvokerTest {
    @InjectMocks
    private DeleteShipmentInvoker deleteShipmentInvoker;
    @Mock
    private TrackingComponent trackingComponent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldDeleteShipment() {
        //given
        doNothing().when(trackingComponent).deleteShipment(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> deleteShipmentInvoker.invoke(any(AwbValidationVO.class)));
    }
}