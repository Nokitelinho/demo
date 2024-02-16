package com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist.invoker;

import com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.CalculateFlightDataFeature;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class CalculateFlightDateInvokerTest {

    @InjectMocks
    private CalculateFlightDateInvoker calculateFlightDateInvoker;
    @Mock
    private CalculateFlightDataFeature calculateFlightDataFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInvokeCalculateFlightDataFeature() {
        //given
        doReturn(emptyList()).when(calculateFlightDataFeature).execute(any(ShipmentDetailsVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> calculateFlightDateInvoker.invoke(any(ShipmentDetailsVO.class)));
    }
}