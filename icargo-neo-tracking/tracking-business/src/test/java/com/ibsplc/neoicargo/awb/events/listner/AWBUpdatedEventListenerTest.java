package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBUpdatedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
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

@RunWith(JUnitPlatform.class)
public class AWBUpdatedEventListenerTest {

    @InjectMocks
    private AWBUpdatedEventListener awbUpdatedEventListener;
    @Mock
    private AwbService awbService;
    @Mock
    private ContextUtil context;
    @Mock
    private AwbEventMapper awbEventMapper;
    private AWBUpdatedEvent awbUpdatedEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldHandleAwbUpdatedEvent() {
        awbUpdatedEvent = MockDataHelper.constructAWBUpdatedEvent("001", "15615600");
        doNothing().when(awbService).saveAwb(any(AwbVO.class));
        Assertions.assertDoesNotThrow(() -> awbUpdatedEventListener.handleAwbUpdatedEvent(awbUpdatedEvent));
    }

}