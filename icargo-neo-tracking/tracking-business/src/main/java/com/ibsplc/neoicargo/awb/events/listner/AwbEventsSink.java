/**
 *
 */
package com.ibsplc.neoicargo.awb.events.listner;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AwbEventsSink {

    String INPUT = "AWB_EVENT_CHANNEL_IN";

    @Input(INPUT)
    SubscribableChannel awbEventInChannel();
}
