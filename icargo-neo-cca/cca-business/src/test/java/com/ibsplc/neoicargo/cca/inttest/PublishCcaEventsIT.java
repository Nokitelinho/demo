package com.ibsplc.neoicargo.cca.inttest;

import com.ibsplc.neoicargo.cca.events.CcaEventsProducer;
import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashMap;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaMaster;

@Slf4j
public class PublishCcaEventsIT extends CcaReservationApiBase {

    @Autowired
    private CcaEventsProducer ccaEventsProducer;

    @Autowired
    private CcaEventMapper ccaEventMapper;

    @Autowired
    private ContextUtil contextUtil;

    @BeforeEach
    public void setup() {
        final var txContext = ContextUtil.getTxContext();
        if (txContext == null) {
            contextUtil.setTxContext(new HashMap<>());
        }
    }

    @AfterEach
    public void cleanup() {
        contextUtil.removeTxContext();
    }

    void sendCcaApprovedEvent() {
        log.info("Entering sendCcaApprovedEvent in PublishCcaEventsIT");
        final var ccaMasterVO = getCCAMasterVO("88898832", "CCA000001",
                LocalDate.of(2020, 12, 3));
        final var key = String.format("%s-%s", ccaMasterVO.getShipmentPrefix(), ccaMasterVO.getMasterDocumentNumber());
        final var ccaApprovedEvent = ccaEventMapper.constructCCAApprovedEventFromCCAMasterVO(ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
        ccaEventsProducer.publishEvent(key, ccaApprovedEvent);
        log.info("Exiting sendCcaApprovedEvent in PublishCcaEventsIT");
    }

    void sendCcaCreateEvent() {
        log.info("Entering sendCcaCreateEvent in PublishCcaEventsIT");
        final var ccaMasterVO = getCCAMasterVO("28898832", "CCA050001",
                LocalDate.of(2020, 12, 3));
        final var key = String.format("%s-%s", ccaMasterVO.getShipmentPrefix(), ccaMasterVO.getMasterDocumentNumber());
        final var ccaCreateEvent = ccaEventMapper.constructCCACreateEventFromCCAMasterVO(ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
        ccaEventsProducer.publishEvent(key, ccaCreateEvent);
        log.info("Exiting sendCcaCreateEvent in PublishCcaEventsIT");
    }

    void sendCcaDeleteEvent() {
        log.info("Entering sendCcaDeleteEvent in PublishCcaEventsIT");
        final var ccaMaster = getFullMockCcaMaster();
        ccaMaster.getCcaAwb().forEach(awb -> {
            final var unitOfQuantity = awb.getUnitOfQuantity();
            unitOfQuantity.setVolume("B");
        });

        final var key = String.format("%s-%s", ccaMaster.getShipmentPrefix(), ccaMaster.getMasterDocumentNumber());
        final var ccaDeleteEvent = ccaEventMapper.constructCCADeleteEventFromCcaMaster(ccaMaster);
        ccaEventsProducer.publishEvent(key, ccaDeleteEvent);
        log.info("Exiting sendCcaDeleteEvent in PublishCcaEventsIT");
    }

    void sendCcaUpdateEvent() {
        log.info("Entering sendCcaUpdateEvent in PublishCcaEventsIT");
        final var ccaMasterVO = getCCAMasterVO("25898632", "CCA790001",
                LocalDate.of(2020, 12, 3));
        final var key = String.format("%s-%s", ccaMasterVO.getShipmentPrefix(), ccaMasterVO.getMasterDocumentNumber());
        final var ccaUpdateEvent = ccaEventMapper.constructCCAUpdateEventFromCCAMasterVO(ccaMasterVO, ccaMasterVO.getUnitOfMeasure());
        ccaEventsProducer.publishEvent(key, ccaUpdateEvent);
        log.info("Exiting sendCcaUpdateEvent in PublishCcaEventsIT");
    }
}
