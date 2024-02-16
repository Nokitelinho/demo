package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBReopenedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("awbReopenedEventListener")
@EnableBinding(AwbEventsSink.class)
@RequiredArgsConstructor
public class AWBReopenedEventListener {

    private final AwbService awbService;
    private final AwbEventMapper awbEventMapper;

    @StreamListener(target = AwbEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='AWBReopened' && headers['Event-Source']=='ICARGO'")
    public void handleAwbReopenedEvent(@Payload AWBReopenedEvent awbReopenedEvent) {
        log.info("handleAwbReopenedEvent : {}-{}", awbReopenedEvent.getShipmentPrefix(), awbReopenedEvent.getMasterDocumentNumber());
        var awbValidationVO = awbEventMapper.constructAwbValidationVo(awbReopenedEvent);
        awbService.reopenAwb(awbValidationVO);
    }
}
