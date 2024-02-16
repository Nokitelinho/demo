package com.ibsplc.neoicargo.stock.events;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.mapper.AwbEventMapper;
import com.ibsplc.neoicargo.stock.model.viewfilter.DocumentFilterModel;
import com.ibsplc.neoicargo.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component("awbCreatedEventListener")
@RequiredArgsConstructor
@EnableBinding(AwbEventsSink.class)
public class AwbCreatedEventListener {

  private final StockService service;
  private final AwbEventMapper awbEventMapper;

  @StreamListener(
      target = AwbEventsSink.AWB_EVENT_CHANNEL_INPUT,
      condition = "headers['EVENT_TYPE']=='AWBCreatedEvent'")
  public void handleAwbEvent(@Payload AWBCreatedEvent awbData) throws BusinessException {
    log.info("AwbCreatedEventListener.handleAwbEvent starts");
    log.info("awbData.getMasterDocumentNumber():{}", awbData.getMasterDocumentNumber());
    log.info("awbData.getShipmentPrefix():{}", awbData.getShipmentPrefix());

    DocumentFilterModel model = awbEventMapper.constructAwbCreatedEvent(awbData);
    log.info(ReflectionToStringBuilder.toString(model, new MultilineRecursiveToStringStyle()));
    service.deleteDocumentFromStock(model);
    log.info("AwbCreatedEventListener.handleAwbEvent ends");
  }
}
