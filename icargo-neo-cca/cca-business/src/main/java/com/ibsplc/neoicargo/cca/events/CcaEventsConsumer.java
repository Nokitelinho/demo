package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.service.CcaService;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import com.ibsplc.neoicargo.qualityaudit.events.InvoicedAWBUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(CcaEventsSink.class)
@Slf4j
@AllArgsConstructor
public class CcaEventsConsumer {

    private final CcaService ccaService;
    private final CcaMasterMapper ccaMasterMapper;
    private final CcaEventMapper ccaEventMapper;

    @StreamListener(target = CcaEventsSink.INPUT,
            condition = "headers['EVENT_TYPE']=='updateInvoicedAwbEvent' && headers['Event-Source']=='ICARGO'")
    void handleCcaInvoicedEvent(@Payload CcaInvoicedEvent ccaInvoicedEvent) {
        log.info("Entering -> CcaEventsConsumer.handleCcaInvoicedEvent");
        final var ccaFilterVO = ccaEventMapper.constructCCAFilterVO(ccaInvoicedEvent);
        if (ccaFilterVO != null) {
            ccaService.updateCcaStatusInvoiced(ccaFilterVO);
        }
        log.info("Exiting -> CcaEventsConsumer.handleCcaInvoicedEvent");
    }

    @StreamListener(target = CcaEventsSink.INPUT,
                    condition = "headers['EVENT_TYPE']=='InvoicedAWBUpdateEvent' " +
                            "&& headers['Event-Source']=='neo-qualityaudit-business'")
    public void handleAutoCcaCreationEvent(@Payload InvoicedAWBUpdateEvent invoicedUpdateEvent) {
        log.info("Entering -> AutoCcaCreationEvent.handleAutoCcaCreationEvent");
        if (invoicedUpdateEvent != null) {
            final var unitOfMeasure = invoicedUpdateEvent.getUnitOfMeasure();
            if (unitOfMeasure.getLength() == null) {
                // TODO should be replace by awbParameterUtil.getSystemParameter(), but it is not working right now
                unitOfMeasure.setLength("C");
            }
            final var ccaMasterVO =
                    ccaMasterMapper.constructCCAMasterVOFromInvoicedAWBUpdateEvent(invoicedUpdateEvent, unitOfMeasure);
            ccaService.saveAutoCCA(ccaMasterVO);
        }
        log.info("Exiting -> AutoCcaCreationEvent.handleAutoCcaCreationEvent");
    }

    @StreamListener(target = CcaEventsSink.INPUT,
                    condition = "headers['EVENT_TYPE']=='AWBVoidedEvent' && headers['Event-Source']=='ICARGO'")
    public void handleAwbVoidedEvent(@Payload AwbVoidedEvent awbVoidedEvent) {
        log.info("Entering -> CcaEventsConsumer.updateExistingCCAForAwbVoidedEvent");
        if (awbVoidedEvent != null) {
            ccaService.updateExistingCCAForAwbVoidedEvent(awbVoidedEvent);
        }
        log.info("Exiting -> CcaEventsConsumer.updateExistingCCAForAwbVoidedEvent");
    }

}
