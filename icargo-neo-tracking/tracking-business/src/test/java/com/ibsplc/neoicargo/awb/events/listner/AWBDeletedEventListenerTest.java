package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBDeletedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
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
public class AWBDeletedEventListenerTest {

    @InjectMocks
    private AwbDeletedEventListener awbDeletedEventListener;
    @Mock
    private AwbService awbService;
    @Mock
    private AwbEventMapper awbEventMapper;
    private AWBDeletedEvent awbDeletedEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldHandleAwbReopenEvent() {
        awbDeletedEvent = MockDataHelper.constructAwbDeletedEvent("001", "15615600");
        doNothing().when(awbService).deleteAwb(any(AwbValidationVO.class));
        Assertions.assertDoesNotThrow(() -> awbDeletedEventListener.handleAwbDeletedEvent(awbDeletedEvent));
    }

}