package com.ibsplc.neoicargo.cca.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CcaEventsSink {
    String INPUT = "AWB_EVENT_CHANNEL_IN";

    @Input(INPUT)
    SubscribableChannel awbEventInChannel();
}