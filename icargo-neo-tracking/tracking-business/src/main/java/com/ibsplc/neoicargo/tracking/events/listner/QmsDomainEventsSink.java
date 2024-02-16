/**
 *
 */
package com.ibsplc.neoicargo.tracking.events.listner;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface QmsDomainEventsSink {

    String INPUT = "QMS_DOMAIN_EVENTS_CHANNEL_IN";

    @Input(INPUT)
    SubscribableChannel qmsDomainEventsInChannel();
}
