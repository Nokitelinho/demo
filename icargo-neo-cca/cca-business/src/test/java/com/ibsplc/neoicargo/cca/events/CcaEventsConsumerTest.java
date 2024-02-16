package com.ibsplc.neoicargo.cca.events;

import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.service.CcaService;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import com.ibsplc.neoicargo.qualityaudit.events.InvoicedAWBUpdateEvent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class CcaEventsConsumerTest {

    @Mock
    private CcaService ccaService;

    @Mock
    private CcaEventMapper ccaEventMapper;

    @Spy
    private CcaMasterMapper ccaMasterMapper;

    @InjectMocks
    private CcaEventsConsumer awbEventsConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("mapperReturnValue")
    void shouldHandleAwbInvoicedEvent(CCAFilterVO mapperReturnValue) {
        // Given
        final var ccaInvoicedEvent = getCcaInvoicedEvent();

        // When
        when(ccaEventMapper.constructCCAFilterVO(ccaInvoicedEvent)).thenReturn(mapperReturnValue);
        doNothing().when(ccaService).updateCcaStatusInvoiced(any(CCAFilterVO.class));

        // Then
        assertDoesNotThrow(() -> awbEventsConsumer.handleCcaInvoicedEvent(ccaInvoicedEvent));
        assertDoesNotThrow(() -> awbEventsConsumer.handleCcaInvoicedEvent(null));
    }


    @ParameterizedTest
    @MethodSource("dataForHandleAutoCcaCreationEvent")
    void shouldHandleAutoCcaCreationEvent(InvoicedAWBUpdateEvent invoicedAWBUpdateEvent) {
        // When
        when(ccaService.saveAutoCCA(new CCAMasterVO())).thenReturn(new CcaValidationData());

        // Then
        assertDoesNotThrow(() -> awbEventsConsumer.handleAutoCcaCreationEvent(invoicedAWBUpdateEvent));
    }

    @ParameterizedTest
    @MethodSource("dataForHandleAwbVoidedEvent")
    void shouldHandleAwbVoidedEvent(AwbVoidedEvent awbVoidedEvent) {
        // When
        doNothing().when(ccaService).updateExistingCCAForAwbVoidedEvent(awbVoidedEvent);

        // Then
        assertDoesNotThrow(() -> awbEventsConsumer.handleAwbVoidedEvent(awbVoidedEvent));
    }

    private static Stream<AwbVoidedEvent> dataForHandleAwbVoidedEvent() {
        return Stream.of(null, new AwbVoidedEvent());
    }

    private static Stream<InvoicedAWBUpdateEvent> dataForHandleAutoCcaCreationEvent() {
        // Given
        final var invoicedAWBUpdateEvent = new InvoicedAWBUpdateEvent();
        invoicedAWBUpdateEvent.setShipmentPrefix("134");
        invoicedAWBUpdateEvent.setMasterDocumentNumber("91091044");
        invoicedAWBUpdateEvent.setShipmentStatus("V");
        invoicedAWBUpdateEvent.setUnitOfMeasure(getUnitOfMeasure("K", "B", null, "USD"));

        final var invoicedAWBUpdateEvent2 = new InvoicedAWBUpdateEvent();
        invoicedAWBUpdateEvent2.setShipmentPrefix("134");
        invoicedAWBUpdateEvent2.setMasterDocumentNumber("91091044");
        invoicedAWBUpdateEvent2.setShipmentStatus("V");
        invoicedAWBUpdateEvent2.setUnitOfMeasure(getUnitOfMeasure("K", "B", "C", "USD"));

        return Stream.of(null, invoicedAWBUpdateEvent, invoicedAWBUpdateEvent2);
    }

    @NotNull
    private CcaInvoicedEvent getCcaInvoicedEvent() {
        final var ccaInvoicedEvent = new CcaInvoicedEvent();
        ccaInvoicedEvent.setShipmentPrefix("134");
        ccaInvoicedEvent.setMasterDocumentNumber("23323311");
        ccaInvoicedEvent.setOwnerId(1134);
        ccaInvoicedEvent.setDuplicateNumber(1);
        ccaInvoicedEvent.setSequenceNumber(1);
        ccaInvoicedEvent.setExportBillingStatus("A");
        ccaInvoicedEvent.setExportImportFlag("E");
        ccaInvoicedEvent.setAwbIndicator("AWB");
        return ccaInvoicedEvent;
    }

    private static Stream<CCAFilterVO> mapperReturnValue() {
        return Stream.of(new CCAFilterVO(), null);
    }

}
