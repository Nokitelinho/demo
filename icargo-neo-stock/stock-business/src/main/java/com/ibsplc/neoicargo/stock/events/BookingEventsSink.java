package com.ibsplc.neoicargo.stock.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface BookingEventsSink {

  String BKG_EVENT_CHANNEL_INPUT = "BKG_EVENT_CHANNEL_IN";

  @Input(BKG_EVENT_CHANNEL_INPUT)
  SubscribableChannel bookingEventChannelIn();
}
