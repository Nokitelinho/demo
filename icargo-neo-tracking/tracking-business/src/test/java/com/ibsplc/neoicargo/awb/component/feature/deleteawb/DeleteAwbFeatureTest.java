package com.ibsplc.neoicargo.awb.component.feature.deleteawb;

import com.ibsplc.neoicargo.awb.component.feature.deleteawb.invoker.DeleteShipmentInvoker;
import com.ibsplc.neoicargo.awb.component.feature.deleteawb.persistor.DeleteAwbPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Invoker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
public class DeleteAwbFeatureTest {

    @InjectMocks
    private DeleteAwbFeature deleteAwbFeature;
    @Mock
    private DeleteAwbPersistor deleteAwbPersistor;
    @Mock
    private DeleteShipmentInvoker deleteShipmentInvoker;
    private ContextUtil context;
    private ApplicationContext applicationContext;

    @BeforeEach
    public void setup() {
        applicationContext = mock(ApplicationContext.class);
        context = spy(new ContextUtil(applicationContext, null));
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(deleteAwbFeature, "context", context);
    }

    @Test
    public void shouldDeleteAwb() {
        //given
        doNothing().when(deleteAwbPersistor).persist(any(AwbValidationVO.class));
        doNothing().when(deleteShipmentInvoker).invoke(any(AwbValidationVO.class));

        // then
        Assertions.assertDoesNotThrow(() -> deleteAwbFeature.perform(any(AwbValidationVO.class)));
    }

    @Test
    public void testInvoker() {
        AwbValidationVO awbValidationVO = new AwbValidationVO();

        doReturn(deleteShipmentInvoker).when(applicationContext).getBean("deleteShipmentInvoker", Invoker.class);

        doNothing().when(deleteShipmentInvoker).invoke(any(AwbValidationVO.class));

        Assertions.assertDoesNotThrow(() -> deleteAwbFeature.postInvoke(awbValidationVO));

        verify(deleteShipmentInvoker, times(1)).invoke(awbValidationVO);
    }
}