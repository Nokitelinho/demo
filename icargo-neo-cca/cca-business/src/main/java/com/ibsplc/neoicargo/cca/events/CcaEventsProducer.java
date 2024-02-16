package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.framework.event.EventProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@Component("ccaEventsProducer")
@AllArgsConstructor
@EnableBinding(CcaEventsProducer.CcaEventsSource.class)
public class CcaEventsProducer extends EventProducer {

    private final CcaEventsSource ccaEventsSource;

    public interface CcaEventsSource {

        String OUTPUT = "CCA_EVENT_CHANNEL_OUT";

        @Output(OUTPUT)
        MessageChannel awbOutChannel();

    }

    public <E extends DomainEvent> void publishEvent(@NotNull String key, @NotNull E event) {
        LOGGER.info("Entering -> CcaEventsProducer.publishEvent");
        super.publishEvent(key, event, ccaEventsSource.awbOutChannel());
        LOGGER.info("Exiting -> CcaEventsProducer.publishEvent");
    }
}
