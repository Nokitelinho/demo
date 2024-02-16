package com.ibsplc.neoicargo.cca.inttest;

import com.ibsplc.neoicargo.cca.component.feature.validatecass.ValidateCassIndicatorFeature;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.events.CcaEventsConsumer;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CcaChargeDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaRateDetailData;
import com.ibsplc.neoicargo.cca.modal.ReasonCodeModel;
import com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData;
import com.ibsplc.neoicargo.cca.service.CcaService;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import com.ibsplc.neoicargo.qualityaudit.events.AwbDetailDimensionEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbOtherChargeDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbRatingDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbRoutingEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import com.ibsplc.neoicargo.qualityaudit.events.InvoicedAWBUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAAWBDetailData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaCustomerDetailVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.AUTO_CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_APPROVED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_SYSTEM_REJECTED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.REJECTED_AS_PART_OF_VOIDING_CCA_REASON;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class CcaEventsConsumerIT extends CcaReservationApiBase {

    @Autowired
    private StubTrigger trigger;

    @Autowired
    private CcaService ccaService;

    @Autowired
    private CcaEventsConsumer ccaEventsConsumer;

    @Autowired
    private ValidateCassIndicatorFeature validateCassIndicatorFeature;

    @Test
    void handleCcaInvoicedEvent() {
        log.info("CcaEventsConsumerIT START");

        // Given
        final var ccaMasterData = createDefaultApprovedNotInvoicedCCA();

        // When
        ccaService.saveCCA(ccaMasterData);
        trigger.trigger("sendInvoicedCcaEvent");
        var actualCCA = ccaService.getCCADetails(createDefaultCCADataFilter());

        // Then
        assertNotNull(actualCCA);
        log.info("CcaEventsConsumerIT END ");
    }

    @ParameterizedTest
    @MethodSource("invoicedAWBUpdateEventData")
    void handleAutoCcaCreationEvent(InvoicedAWBUpdateEvent invoicedAWBUpdateEvent) {
        log.info("AutoCcaCreationEventForVoidedAwbIT START");

        // Given
        final var ccaListViewFilterData = new CCAListViewFilterData();
        ccaListViewFilterData.setShipmentPrefix("176");
        ccaListViewFilterData.setMasterDocumentNumber("66660000");

        // When
        ccaEventsConsumer.handleAutoCcaCreationEvent(invoicedAWBUpdateEvent);

        final var ccaListView = ccaService.getCCAListView(ccaListViewFilterData);
        final var autoCca = ccaListView.getEdges().stream()
                .filter(cca -> {
                    final var ccaReasons = cca.getCcaReason();
                    return ccaReasons.size() == 1 && "VD".equals(ccaReasons.get(0).getParameterCode());
                })
                .filter(cca -> CCA_APPROVED.equals(cca.getCcaStatus()))
                .filter(cca -> AUTO_CCA_SOURCE.equals(cca.getCcaSource()))
                .filter(cca -> CCA_TYPE_ACTUAL.equals(cca.getCcaType()))
                .findAny();

        // Then
        assertTrue(autoCca.isPresent());
        log.info("AutoCcaCreationEventForVoidedAwbIT END");
    }

    private static Stream<InvoicedAWBUpdateEvent> invoicedAWBUpdateEventData() {
        final var invoicedAWBUpdateEvent = getInvoicedAWBUpdateEvent();
        invoicedAWBUpdateEvent.setAwbRatingDetailEvent(null);
        invoicedAWBUpdateEvent.setAwbDetailEvent(null);
        invoicedAWBUpdateEvent.setAwbOtherChargeDetailEvent(null);
        invoicedAWBUpdateEvent.setAwbRoutingEvent(null);
        invoicedAWBUpdateEvent.setUnitOfMeasure(getUnitOfMeasure("K", "B", "C", "USD"));

        return Stream.of(getInvoicedAWBUpdateEvent(), invoicedAWBUpdateEvent);
    }

    @ParameterizedTest
    @MethodSource("ccaMasterVOValues")
    void shouldValidateCassIndicatorFeatureTest(CCAMasterVO ccaMasterVO,
                                                Set<CcaCustomerDetailVO> ccaCustomerDetailVOS) {
        // Given
        ccaMasterVO.getOriginalShipmentVO().setCcaCustomerDetails(ccaCustomerDetailVOS);

        // Then
        assertNotNull(validateCassIndicatorFeature.perform(ccaMasterVO));
    }

    @Test
    void handleHandleAwbVoidedEvent() {
        log.info("updateExistingCCAForAwbVoidedEvent START");

        // Given
        final var shipmentPrefix = "176";
        final var masterDocumentNumber = "55550000";

        final var ccaListViewFilterData = new CCAListViewFilterData();
        ccaListViewFilterData.setShipmentPrefix(shipmentPrefix);
        ccaListViewFilterData.setMasterDocumentNumber(masterDocumentNumber);

        final var awbVoidedEvent = new AwbVoidedEvent();
        awbVoidedEvent.setMasterDocumentNumber(masterDocumentNumber);
        awbVoidedEvent.setShipmentPrefix(shipmentPrefix);

        final var ccaMasterData = createDefaultApprovedNotInvoicedCCA();
        ccaMasterData.setShipmentPrefix(shipmentPrefix);
        ccaMasterData.setMasterDocumentNumber(masterDocumentNumber);
        ccaMasterData.setCcaStatus(CcaStatus.N);

        // When
        ccaService.saveCCA(ccaMasterData);

        trigger.trigger("sendAWBVoidedEvent");

        final var amountUpdatedMasters = ccaService.getCCAListView(ccaListViewFilterData).getEdges().stream()
                .filter(ccaListViewModal -> {
                    final var reason = ccaListViewModal.getCcaReason();
                    return 1 == reason.size() &&
                            REJECTED_AS_PART_OF_VOIDING_CCA_REASON.equals(reason.get(0).getParameterCode());
                })
                .filter(ccaListViewModal -> CCA_SYSTEM_REJECTED.equals(ccaListViewModal.getCcaStatus()))
                .count();

        // Then
        assertEquals(1, amountUpdatedMasters);
        log.info("updateExistingCCAForAwbVoidedEvent END");
    }

    private static Stream<Arguments> ccaMasterVOValues() {
        // Given
        final var originalCcaAwbVO = new CcaAwbVO();
        originalCcaAwbVO.setRecordType(CCA_RECORD_TYPE_ORIGINAL);
        originalCcaAwbVO.setDestination("DXB");
        originalCcaAwbVO.setOrigin("CDG");

        final var ccaMasterVO1 = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO1.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO2 = getCCAMasterVO("44440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO2.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO3 = getCCAMasterVO("44440030", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO3.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO4 = getCCAMasterVO("47440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO4.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO5 = getCCAMasterVO("47440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO5.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaCustomerDetailVO1 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO1.setCassIndicator("B");

        final var ccaCustomerDetailVO2 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO2.setCassIndicator("I");

        final var ccaCustomerDetailVO3 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);

        final var ccaCustomerDetailVO4 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO4.setCassIndicator("D");

        final var ccaCustomerDetailVO5 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO5.setCassIndicator(null);

        return Stream.of(
                Arguments.of(ccaMasterVO1, Set.of(ccaCustomerDetailVO1)),
                Arguments.of(ccaMasterVO2, Set.of(ccaCustomerDetailVO2)),
                Arguments.of(ccaMasterVO3, Set.of(ccaCustomerDetailVO3)),
                Arguments.of(ccaMasterVO4, Set.of(ccaCustomerDetailVO4)),
                Arguments.of(ccaMasterVO5, Set.of(ccaCustomerDetailVO5))
        );
    }

    private static InvoicedAWBUpdateEvent getInvoicedAWBUpdateEvent() {
        var invoicedAWBUpdateEvent = new InvoicedAWBUpdateEvent();
        invoicedAWBUpdateEvent.setShipmentPrefix("176");
        invoicedAWBUpdateEvent.setMasterDocumentNumber("66660000");
        invoicedAWBUpdateEvent.setShipmentStatus("V");
        invoicedAWBUpdateEvent.setUnitOfMeasure(getUnitOfMeasure("K", "B", null, "USD"));
        invoicedAWBUpdateEvent.setPieces(15);
        invoicedAWBUpdateEvent.setOrigin("CDG");
        invoicedAWBUpdateEvent.setDestination("DXB");
        invoicedAWBUpdateEvent.setAgentCode("DHLCDG001");
        invoicedAWBUpdateEvent.setInboundCustomerCode("DHLCDG001");
        invoicedAWBUpdateEvent.setOutboundCustomerCode("DHLCDG001");
        invoicedAWBUpdateEvent.setPayType("CC");
        invoicedAWBUpdateEvent.setCompanyCode("AV");
        invoicedAWBUpdateEvent.setCurrency("USD");

        final var awbDimension = getAwbDetailDimensionEvent();
        final var awbDetailEvent = getAwbDetailEvent(awbDimension);
        final var awbRatingDetailEvent = getAwbRatingDetailEvent();
        final var awbOtherChargeDetailEvent = getAwbOtherChargeDetailEvent();
        final var awbRoutingEvent = getAwbRoutingEvent();

        invoicedAWBUpdateEvent.setAwbDetailEvent(List.of(awbDetailEvent));
        invoicedAWBUpdateEvent.setAwbRatingDetailEvent(List.of(awbRatingDetailEvent));
        invoicedAWBUpdateEvent.setAwbOtherChargeDetailEvent(List.of(awbOtherChargeDetailEvent));
        invoicedAWBUpdateEvent.setAwbRoutingEvent(List.of(awbRoutingEvent));
        return invoicedAWBUpdateEvent;
    }

    private static AwbRoutingEvent getAwbRoutingEvent() {
        final var awbRoutingEvent = new AwbRoutingEvent();
        awbRoutingEvent.setShipmentPrefix("134");
        awbRoutingEvent.setMasterDocumentNumber("44440000");
        awbRoutingEvent.setCompanyCode("AV");
        awbRoutingEvent.setFlightCarrierCode("AV");
        awbRoutingEvent.setFlightDate("2020-10-12");
        awbRoutingEvent.setFlightNumber("1548");
        awbRoutingEvent.setFlightType("Narrow");
        awbRoutingEvent.setPieces(10);
        awbRoutingEvent.setChgWeight(15.0);
        awbRoutingEvent.setVolWeight(10.0);
        awbRoutingEvent.setWeight(10.0);
        awbRoutingEvent.setVolume(10.0);
        awbRoutingEvent.segOrgCod("CDG");
        awbRoutingEvent.setSegDstCod("DXB");
        awbRoutingEvent.setFlightCarrierId(12);
        return awbRoutingEvent;
    }

    private static AwbOtherChargeDetailEvent getAwbOtherChargeDetailEvent() {
        final var awbOtherChargeDetailEvent = new AwbOtherChargeDetailEvent();
        awbOtherChargeDetailEvent.setChargeHeadCode("HD");
        awbOtherChargeDetailEvent.setChargeHead("Document Charge");
        awbOtherChargeDetailEvent.setCharge(10.2);
        awbOtherChargeDetailEvent.setDueCarrier(true);
        awbOtherChargeDetailEvent.setDueAgent(false);
        return awbOtherChargeDetailEvent;
    }

    private static AwbRatingDetailEvent getAwbRatingDetailEvent() {
        var awbRatingDetailEvent = new AwbRatingDetailEvent();
        awbRatingDetailEvent.setCommodityCode("GEN");
        awbRatingDetailEvent.setRateType("IATA");
        awbRatingDetailEvent.setCharge(10.0);
        awbRatingDetailEvent.setChargeableWeight(50.0);
        awbRatingDetailEvent.setRate(25.0);
        return awbRatingDetailEvent;
    }

    private static AwbDetailEvent getAwbDetailEvent(AwbDetailDimensionEvent awbDimension) {
        final var awbDetailEvent = new AwbDetailEvent();
        awbDetailEvent.setChargeableWeight(0.0);
        awbDetailEvent.setAdjustedWeight(0.0);
        awbDetailEvent.setAwbDetailDimensionEvent(List.of(awbDimension));
        awbDetailEvent.setCommodityCode("GEN");
        awbDetailEvent.setGrossVolume(0.0);
        awbDetailEvent.setGrossWeight(0.0);
        awbDetailEvent.setNumberOfPieces(12L);
        awbDetailEvent.setVolumetricWeight(0.0);
        awbDetailEvent.setSccCode("GEN");
        awbDetailEvent.setShipmentDescription("GENERAL CARGO");
        return awbDetailEvent;
    }

    private static AwbDetailDimensionEvent getAwbDetailDimensionEvent() {
        final var awbDimension = new AwbDetailDimensionEvent();
        awbDimension.setHeight(5.0);
        awbDimension.setWeight(200.0);
        awbDimension.setVolume(10.0);
        awbDimension.setLength(50.0);
        awbDimension.setWidth(100.0);
        awbDimension.setTiltable(true);
        return awbDimension;
    }
    @NotNull
    public static LocalDateTime getIssueDateTimeInUtc(){
        LocalDateTime time = LocalDateTime.now(ZoneId.of(UTC));
        return time;
    }
    @NotNull
    private CCAMasterData createDefaultApprovedNotInvoicedCCA() {
        var ccaMasterData = new CCAMasterData();
        ccaMasterData.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMasterData.setMasterDocumentNumber("55550000");
        ccaMasterData.setCcaReferenceNumber("CCA000123");
        ccaMasterData.setCcaType(CCA_TYPE_ACTUAL);
        ccaMasterData.setCcaStatus(CcaStatus.A);
        ccaMasterData.setCcaIssueDateTimeInUTC(getIssueDateTimeInUtc());
        var ccaAWBDetailData = getCCAAWBDetailData("EUR", "PP", 1575.0, 0.1, 0.1, 0.1, 2508.2,
                0.0, 2508.5, 0.0, 0.0, 148703.88, 0.0,
                0.0, "DHLCDG001", "CDG", "DXB", 15, "CC", "DHLCDG001", "DHLCDG001");

        final var ccaRateDetailData1 = createCcaRateDetailData("IATA", 4.16);
        final var ccaRateDetailData2 = createCcaRateDetailData("MKT", 519.84);

        ccaAWBDetailData.setAwbCharges(List.of(getCcaChargeDetailData()));
        ccaAWBDetailData.setRatingDetails(List.of(getCcaDetailData()));
        ccaAWBDetailData.setAwbRates(List.of(ccaRateDetailData1, ccaRateDetailData2));
        ccaAWBDetailData.setNetValueExport(1.0D);
        ccaAWBDetailData.setNetValueImport(2.0D);
        ccaMasterData.setRevisedAWBData(ccaAWBDetailData);
        ccaMasterData.setOriginalAWBData(ccaAWBDetailData);
        ccaMasterData.setUnitOfMeasure(getUnitOfMeasure("K", "B", "C", "USD"));

        var reasonCode = new ReasonCodeModel();
        reasonCode.setParameterCode("01");
        ccaMasterData.setReasonCodes(Set.of(reasonCode));

        return ccaMasterData;
    }

    @NotNull
    private CcaChargeDetailData getCcaChargeDetailData() {
        var ccaChargeDetailData = new CcaChargeDetailData();
        ccaChargeDetailData.setChargeHead("Flat Charge");
        ccaChargeDetailData.setCharge((double) 34);
        ccaChargeDetailData.setChargeHeadCode("FD");
        ccaChargeDetailData.setDueCarrier(false);
        ccaChargeDetailData.setDueAgent(true);
        return ccaChargeDetailData;
    }

    @NotNull
    private CcaDetailData getCcaDetailData() {
        var ccaDetailData = new CcaDetailData();
        ccaDetailData.setNumberOfPieces((long) 15);
        ccaDetailData.setCommodityCode("DGR");
        ccaDetailData.setWeightOfShipment(15.1);
        ccaDetailData.setVolumeOfShipment(51.1);
        ccaDetailData.setChargeableWeight(15.1);
        ccaDetailData.setShipmentDescription("GOODS");
        ccaDetailData.setDisplayVolumeUnit("B");
        ccaDetailData.setVolumetricWeight(11.1);
        ccaDetailData.setAdjustedWeight(22.2);
        ccaDetailData.setDisplayWeightUnit("K");
        return ccaDetailData;
    }

    @NotNull
    private CcaRateDetailData createCcaRateDetailData(final String rateType,
                                                      final double rate) {
        var ccaRateDetailData = new CcaRateDetailData();
        ccaRateDetailData.setDisplayChargeableWeight((double) 0);
        ccaRateDetailData.setRateType(rateType);
        ccaRateDetailData.setCommodityCode("GEN");
        ccaRateDetailData.setChargeableWeight((double) 0);
        ccaRateDetailData.setCharge((double) 0);
        ccaRateDetailData.setRate(rate);
        return ccaRateDetailData;
    }

    @NotNull
    private CcaDataFilter createDefaultCCADataFilter() {
        var filter = new CcaDataFilter();
        filter.setShipmentPrefix(SHIPMENT_PREFIX);
        filter.setMasterDocumentNumber("55550000");
        filter.setCcaReferenceNumber("CCA000123");
        return filter;
    }

}
