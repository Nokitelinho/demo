package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBVoidedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("awbVoidedEventListener")
@EnableBinding(AwbEventsSink.class)
@RequiredArgsConstructor
public class AwbVoidedEventListener {

    private final AwbService awbService;
    private final AwbEventMapper awbEventMapper;

    @StreamListener(target = AwbEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='AWBVoided' && headers['Event-Source']=='ICARGO'")
    public void handleAwbVoidedEvent(@Payload AWBVoidedEvent awbVoidedEvent) {
        log.info("handleAWBVoidedEvent : {}-{}", awbVoidedEvent.getShipmentPrefix(), awbVoidedEvent.getMasterDocumentNumber());
        var awbValidationVO = awbEventMapper.constructAwbValidationVo(awbVoidedEvent);
        awbService.deleteAwb(awbValidationVO);
    }
}
