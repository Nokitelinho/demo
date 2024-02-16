package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBVoidedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
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
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class AWBVoidEventListenerTest {

    @InjectMocks
    private AwbVoidedEventListener awbVoidedEventListener;
    @Mock
    private AwbService awbService;
    @Mock
    private ContextUtil context;
    private AWBVoidedEvent awbVoidedEvent;
    private Quantities quantities;
    private QuantityMapper quantityMapper;
    private AwbEventMapper awbEventMapper;

    @BeforeEach
    public void setup() {
        quantities = MockQuantity.performInitialisation(null, null, null, null);
        quantityMapper = MockQuantity.injectMapper(quantities, QuantityMapper.class);
        awbEventMapper = MockQuantity.injectMapper(quantities, AwbEventMapper.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldHandleAwbVoidedEvent() {
        awbVoidedEvent = MockDataHelper.constructAwbVoidedEvent("001", "15615600");
        doReturn(new LoginProfile()).when(context).callerLoginProfile();
        doNothing().when(awbService).reopenAwb(any(AwbValidationVO.class));
        Assertions.assertDoesNotThrow(() -> awbVoidedEventListener.handleAwbVoidedEvent(awbVoidedEvent));
    }

}