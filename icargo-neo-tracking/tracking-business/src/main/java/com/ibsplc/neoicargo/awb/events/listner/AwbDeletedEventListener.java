package com.ibsplc.neoicargo.awb.events.listner;

import com.ibsplc.neoicargo.awb.events.AWBDeletedEvent;
import com.ibsplc.neoicargo.awb.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.awb.service.AwbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("awbDeletedEventListener")
@EnableBinding(AwbEventsSink.class)
@RequiredArgsConstructor
public class AwbDeletedEventListener {

    private final AwbService awbService;
    private final AwbEventMapper awbEventMapper;

    @StreamListener(target = AwbEventsSink.INPUT, condition = "headers['EVENT_TYPE']=='AWBDeleted' && headers['Event-Source']=='ICARGO'")
    public void handleAwbDeletedEvent(@Payload AWBDeletedEvent awbDeletedEvent) {
        log.info("handleAWBDeletedEvent : {}-{}", awbDeletedEvent.getShipmentPrefix(), awbDeletedEvent.getMasterDocumentNumber());
        var awbValidationVO = awbEventMapper.constructAwbValidationVo(awbDeletedEvent);
        awbService.deleteAwb(awbValidationVO);
    }
}
