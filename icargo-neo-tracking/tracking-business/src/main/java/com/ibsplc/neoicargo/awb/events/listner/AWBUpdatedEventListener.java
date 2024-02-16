package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBUpdatedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("awbUpdatedEventListener")
@EnableBinding(AwbEventsSink.class)
@RequiredArgsConstructor
public class AWBUpdatedEventListener {

    private final ContextUtil contextUtil;
    private final AwbService awbService;
    private final AwbEventMapper awbEventMapper;

    @StreamListener(target = AwbEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='AWBUpdated' && headers['Event-Source']=='ICARGO'")
    public void handleAwbUpdatedEvent(@Payload AWBUpdatedEvent awbUpdatedEvent) {
        log.info("handleAwbUpdatedEvent : {}-{}", awbUpdatedEvent.getShipmentPrefix(), awbUpdatedEvent.getMasterDocumentNumber());
        var awbVO = awbEventMapper.constructAwbVO(awbUpdatedEvent, contextUtil);
        awbService.saveAwb(awbVO);
    }
}
