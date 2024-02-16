package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBExecutedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("awbExecutedEventListener")
@EnableBinding(AwbEventsSink.class)
@RequiredArgsConstructor
public class AwbExecutedEventListener {

    private final AwbService awbService;
    private final AwbEventMapper awbEventMapper;

    @StreamListener(target = AwbEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='AWBExecuted' && headers['Event-Source']=='ICARGO'")
    public void handleAwbExecutedEvent(@Payload AWBExecutedEvent awbExecutedEvent) {
        log.info("handleAwbExecutedEvent : {}-{}", awbExecutedEvent.getShipmentPrefix(), awbExecutedEvent.getMasterDocumentNumber());
        var awbValidationVO = awbEventMapper.constructAwbValidationVo(awbExecutedEvent);
        awbService.executeAwb(awbValidationVO);
    }
}
