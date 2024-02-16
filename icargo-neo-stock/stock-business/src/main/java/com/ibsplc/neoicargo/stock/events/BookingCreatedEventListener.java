package com.ibsplc.neoicargo.stock.events;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.stock.mapper.BookingEventMapper;
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
@Component("bookingCreatedEventListener")
@RequiredArgsConstructor
@EnableBinding(BookingEventsSink.class)
public class BookingCreatedEventListener {

  private final StockService service;
  private final BookingEventMapper bookingEventMapper;

  @StreamListener(
      target = BookingEventsSink.BKG_EVENT_CHANNEL_INPUT,
      condition = "headers['EVENT_TYPE']=='BookingCreatedEvent'")
  public void handleBookingEvent(@Payload BookingCreatedEvent bookingData)
      throws BusinessException {
    log.info("BookingCreatedEventListener.handleBookingEvent starts");
    log.info("bookingData.getMasterDocumentNumber():{}", bookingData.getMasterDocumentNumber());
    log.info("bookingData.getShipmentPrefix():{}", bookingData.getShipmentPrefix());

    DocumentFilterModel model = bookingEventMapper.constructBookingCreatedEvent(bookingData);
    log.info(ReflectionToStringBuilder.toString(model, new MultilineRecursiveToStringStyle()));
    service.deleteDocumentFromStock(model);
    log.info("BookingCreatedEventListener.handleBookingEvent ends");
  }
}
