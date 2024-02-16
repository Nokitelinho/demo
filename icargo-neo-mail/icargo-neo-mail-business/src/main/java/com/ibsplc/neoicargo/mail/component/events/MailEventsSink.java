package com.ibsplc.neoicargo.mail.component.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MailEventsSink {
    String CHANNEL_NAME = "MAIL_EVENT_CHANNEL_OUT";

    String INPUT = "MAIL_EVENT_CHANNEL_IN";

    @Input(INPUT)
    SubscribableChannel mailEventInChannel();
    @Output(MailEventsSink.CHANNEL_NAME)
    MessageChannel mailOutChannel();
}
