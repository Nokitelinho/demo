package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditedDetails;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataRequest;
import com.ibsplc.neoicargo.cca.modal.CcaCustomerDetailData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaListViewVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.qualityaudit.events.AwbDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbOtherChargeDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbRatingDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbRoutingEvent;
import com.ibsplc.neoicargo.qualityaudit.events.InvoicedAWBUpdateEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaChargeDetailsVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaCustomerDetailData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaMasterMapperTest {

    private static final String MASTER_DOCUMENT_NUMBER = "23323311";
    private static final String SHIPMENT_PREFIX = "134";
    private static final String CCA_000001 = "CCA000001";
    private static final Units UNITS = new Units().weight("K").volume("B");

    @Spy
    private final QuantityMapper quantityMapper = Mappers.getMapper(QuantityMapper.class);

    @Mock
    private MoneyMapper moneyMapper;

    private final CcaMasterMapper ccaMasterMapper = Mappers.getMapper(CcaMasterMapper.class);

    @BeforeEach
    void setUp() {
        final var quantities = MockQuantity.performInitialisation(null, null, LOCATION_CODE, null);
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(quantityMapper, "quanties", quantities);
    }

    @Test
    void setUnitsOfMeasure_shouldSetUnitInOriginalAwb() {
        // Given
        final var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        final var originalAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_ORIGINAL);
        ccaMasterVO.setOriginalShipmentVO(originalAwbVO);
        ccaMasterVO.setShipmentDetailVOs(Set.of(originalAwbVO));
        ccaMasterVO.setUnitOfMeasure(UNITS);

        // When
        ccaMasterMapper.setUnitsOfMeasure(ccaMasterVO);

        // Then
        assertEquals(UNITS, ccaMasterVO.getUnitOfMeasure());
        final var originalShipmentVO = ccaMasterVO.getOriginalShipmentVO();
        assertEquals(UNITS, originalShipmentVO.getUnitOfMeasure());
        originalShipmentVO.getRatingDetails()
                .stream()
                .map(CcaRatingDetailVO::getUnitOfMeasure)
                .forEach(detailUnit -> assertEquals(UNITS, detailUnit));
    }

    @Test
    void setUnitsOfMeasure_shouldSetUnitInRevisedAwb() {
        // Given
        final var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        final var revisedAwb = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        ccaMasterVO.setRevisedShipmentVO(revisedAwb);
        ccaMasterVO.setShipmentDetailVOs(Set.of(revisedAwb));
        ccaMasterVO.setUnitOfMeasure(UNITS);

        // When
        ccaMasterMapper.setUnitsOfMeasure(ccaMasterVO);

        // Then
        assertEquals(UNITS, ccaMasterVO.getUnitOfMeasure());
        final var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        assertEquals(UNITS, revisedShipmentVO.getUnitOfMeasure());
        revisedShipmentVO.getRatingDetails()
                .stream()
                .map(CcaRatingDetailVO::getUnitOfMeasure)
                .forEach(detailUnit -> assertEquals(UNITS, detailUnit));
    }

    @Test
    void setUnitsOfMeasure_shouldNotThrow_whenOriginalAwbAndRevisedAwbNull() {
        // Given
        final var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        ccaMasterVO.setOriginalShipmentVO(null);
        ccaMasterVO.setRevisedShipmentVO(null);
        ccaMasterVO.setShipmentDetailVOs(null);
        ccaMasterVO.setUnitOfMeasure(UNITS);

        // When + Then
        assertDoesNotThrow(() -> ccaMasterMapper.setUnitsOfMeasure(ccaMasterVO));
        assertEquals(UNITS, ccaMasterVO.getUnitOfMeasure());
    }

    @Test
    void setUnitsOfMeasure_shouldNotThrow_whenCcaMasterNull() {
        // When + Then
        assertDoesNotThrow(() -> ccaMasterMapper.setUnitsOfMeasure(null));
    }

    @Test
    void setUnitsOfMeasure_shouldNotThrow_whenRatingDetailsNull() {
        // Given
        final var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        final var originalAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_ORIGINAL);
        originalAwbVO.setRatingDetails(null);
        ccaMasterVO.setOriginalShipmentVO(originalAwbVO);
        ccaMasterVO.setShipmentDetailVOs(Set.of(originalAwbVO));
        ccaMasterVO.setUnitOfMeasure(UNITS);

        // When + Then
        assertDoesNotThrow(() -> ccaMasterMapper.setUnitsOfMeasure(ccaMasterVO));
        assertEquals(UNITS, ccaMasterVO.getUnitOfMeasure());
    }

    @Test
    void shouldGetCustomerDetails() {
        // Given
        final var ccaListViewVO = new CcaListViewVO();
        ccaListViewVO.setAgentDetails(Set.of(getCcaCustomerDetailData()));
        ccaListViewVO.setOutboundCustomerDetails(Set.of(getCcaCustomerDetailData()));
        ccaListViewVO.setInboundCustomerDetails(Set.of(getCcaCustomerDetailData()));

        // When
        final var inboundCustomerDetails = ccaMasterMapper.getInboundCustomerDetails(ccaListViewVO);
        final var outboundCustomerDetails = ccaMasterMapper.getOutboundCustomerDetails(ccaListViewVO);
        final var agentDetails = ccaMasterMapper.getAgentDetails(ccaListViewVO);

        // Then
        verify(inboundCustomerDetails);
        verify(outboundCustomerDetails);
        verify(agentDetails);
    }

    @Test
    void shouldSetDataFromCcaMaster() {
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        var qualityAuditedDetails = new QualityAuditedDetails();

        ccaMasterMapper.setCcaMasterVOToNetValueRequest(ccaMasterVO, qualityAuditedDetails);
        assertEquals(ccaMasterVO.getShipmentPrefix(), qualityAuditedDetails.getShipmentPrefix());
        assertEquals(ccaMasterVO.getMasterDocumentNumber(), qualityAuditedDetails.getMasterDocumentNumber());
        assertEquals(ccaMasterVO.getCcaIssueDate().atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), qualityAuditedDetails.getExecutionDate());
        assertNotNull(qualityAuditedDetails.getUnitOfMeasure());
    }

    @Test
    void shouldConstructTotalValues() {
        final var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        ccaAwbVO.setAwbOtherChargePaymentType("PP");
        final var charge = getMoney(240D, "USD");
        final var chargeVO1 = getCcaChargeDetailsVO("pre-paid", "flat charge", charge, "FC",
                true, false);
        final var chargeVO2 = getCcaChargeDetailsVO("pre-paid", "flat charge", charge, "FD",
                false, true);

        Collection<CcaChargeDetailsVO> newChargeCollection = Stream.concat(ccaAwbVO.getAwbCharges().stream(), Set.of(chargeVO1, chargeVO2).stream()).collect(Collectors.toSet());
        ccaAwbVO.setAwbCharges(newChargeCollection);

        final var netValueRequest = new QualityAuditedDetails();

        ccaMasterMapper.constructTotalValues(ccaAwbVO, netValueRequest);
        assertEquals(240.0, netValueRequest.getTotalDueAgtPPDChg().doubleValue());
        assertEquals(240.0, netValueRequest.getTotalDueCarPPDChg().doubleValue());

        ccaAwbVO.setAwbOtherChargePaymentType("CC");

        ccaMasterMapper.constructTotalValues(ccaAwbVO, netValueRequest);
        assertEquals(240.0, netValueRequest.getTotalDueAgtCCFChg().doubleValue());
        assertEquals(240.0, netValueRequest.getTotalDueCarCCFChg().doubleValue());

    }

    private void verify(CcaCustomerDetailData ccaCustomerDetailData) {
        assertNotNull(ccaCustomerDetailData.getCustomerName());
        assertNotNull(ccaCustomerDetailData.getCountryCode());
        assertNotNull(ccaCustomerDetailData.getStationCode());
        assertNotNull(ccaCustomerDetailData.getAccountNumber());
        assertNotNull(ccaCustomerDetailData.getIataCode());
    }

    @Test
    void enrichMasterVOWithShipmentDetails() {
        // Given
        final var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        final var original = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_ORIGINAL);
        final var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        ccaMasterVO.setShipmentDetailVOs(null);
        ccaMasterVO.setOriginalShipmentVO(original);
        ccaMasterVO.setRevisedShipmentVO(revised);

        // When
        ccaMasterMapper.enrichMasterVOWithShipmentDetails(ccaMasterVO);

        // Then
        var actual = ccaMasterVO.getShipmentDetailVOs();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertTrue(actual.contains(original));
        assertTrue(actual.contains(revised));
    }

    @Test
    void enrichMasterVOWithShipmentDetailsShouldSetEmptyCollectionWhenRevisedAndOriginalAwbEmpty() {
        // Given
        final var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_000001, LocalDate.now());
        ccaMasterVO.setShipmentDetailVOs(null);
        ccaMasterVO.setOriginalShipmentVO(null);
        ccaMasterVO.setRevisedShipmentVO(null);

        // When
        ccaMasterMapper.enrichMasterVOWithShipmentDetails(ccaMasterVO);

        // Then
        var actual = ccaMasterVO.getShipmentDetailVOs();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @ParameterizedTest
    @MethodSource("invoicedAWBUpdateEventData")
    void shouldSetRatesAndChargesAndDetails(InvoicedAWBUpdateEvent invoicedAWBUpdateEvent) {
        // Given
        final var moneyObject = getMoney(10.0, "USD");
        ReflectionTestUtils.setField(ccaMasterMapper, "moneyMapper", moneyMapper);
        ReflectionTestUtils.setField(ccaMasterMapper, "quantityMapper", quantityMapper);

        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setOriginalShipmentVO(new CcaAwbVO());
        ccaMasterVO.setRevisedShipmentVO(new CcaAwbVO());
        final var unitOfMeasure = getUnitOfMeasure("K", "B", "C", "USD");

        // When
        doReturn(moneyObject).when(moneyMapper).moneyFromDouble(any(Double.class), eq(unitOfMeasure));

        assertDoesNotThrow(() -> ccaMasterMapper.setRatesAndChargesAndDetails(invoicedAWBUpdateEvent, unitOfMeasure, ccaMasterVO));
    }

    private static Stream<InvoicedAWBUpdateEvent> invoicedAWBUpdateEventData() {
        // Given
        final var invoicedAWBUpdateEvent = new InvoicedAWBUpdateEvent();
        invoicedAWBUpdateEvent.setAwbRatingDetailEvent(List.of(new AwbRatingDetailEvent()));
        invoicedAWBUpdateEvent.setAwbDetailEvent(List.of(new AwbDetailEvent()));
        invoicedAWBUpdateEvent.setAwbOtherChargeDetailEvent(List.of(new AwbOtherChargeDetailEvent()));
        invoicedAWBUpdateEvent.setAwbRoutingEvent(List.of(new AwbRoutingEvent()));

        return Stream.of(new InvoicedAWBUpdateEvent(), invoicedAWBUpdateEvent);
    }

    @Test
    void shouldCreateCCAMasterVOFromCcaAwbVO() {
        // Given
        final var ccaCassValidationDataRequest = new CcaCassValidationDataRequest();
        ccaCassValidationDataRequest.setAgentCode("DHLCDG001");
        ccaCassValidationDataRequest.setDestination("DXB");
        ccaCassValidationDataRequest.setOrigin("LAX");
        ccaCassValidationDataRequest.setInboundCustomerCode("CARGOMST");
        ccaCassValidationDataRequest.setOutboundCustomerCode("CARGOMST");

        // Then
        assertNotNull(ccaMasterMapper.createCCAMasterVOFromCcaAwbVO(ccaCassValidationDataRequest));
    }
}