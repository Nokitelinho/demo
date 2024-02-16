package com.ibsplc.neoicargo.mail.component.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.framework.event.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
@Slf4j
@Component("neoMailEventsProducer")
@EnableBinding(MailEventsSink.class)
public class MailEventsProducer extends EventProducer {
    @Autowired
    private MailEventsSink mailEventsSink;


    public <T extends DomainEvent> void publishEvent(@NotNull String key, @NotNull T t) {
        log.info("Entering -> MailEventsProducer.publishEvent");
        super.publishEvent(key, t, mailEventsSink.mailOutChannel());
        log.info("Exiting -> MailEventsProducer.publishEvent");
    }
}
