package com.ibsplc.neoicargo.stock.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AwbEventsSink {

  String AWB_EVENT_CHANNEL_INPUT = "AWB_DOMAIN_EVENT_CHANNEL_IN";

  @Input(AWB_EVENT_CHANNEL_INPUT)
  SubscribableChannel stockEventChannelIn();
}
