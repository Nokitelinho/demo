/**
 *
 */
package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBCreatedEvent;
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
@Component("awbCreatedEventListener")
@EnableBinding(AwbEventsSink.class)
@RequiredArgsConstructor
public class AwbCreatedEventListener {

    private final AwbService awbService;
    private final ContextUtil contextUtil;
    private final AwbEventMapper awbEventMapper;

    @StreamListener(target = AwbEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='AWBCreated' && headers['Event-Source']=='ICARGO'")
    public void handleAwbCreatedEvent(@Payload AWBCreatedEvent awbCreatedEvent) {
        log.info("handleAwbCreatedEvent : {}-{}", awbCreatedEvent.getShipmentPrefix(), awbCreatedEvent.getMasterDocumentNumber());
        var awbVO = awbEventMapper.constructAwbVO(awbCreatedEvent, contextUtil);
        awbService.saveAwb(awbVO);
    }
}
