package com.ibsplc.neoicargo.cca.component.feature.savecca.persistor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwb;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbChargeDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbPk;
import com.ibsplc.neoicargo.cca.dao.entity.CCAAwbRateDetail;
import com.ibsplc.neoicargo.cca.dao.entity.CcaAwbRoutingDetails;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaCustomerDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaTaxMapper;
import com.ibsplc.neoicargo.cca.mapper.UnitQuantityMapper;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbRoutingDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaDetailVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.cca.vo.DimensionsVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getBasicMockCcaMaster;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaCustomerDetailVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaRateDetailsVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getVolumeQuantity;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getWeightQuantity;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.MKT_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.TRIGGER_QUALITY_AUDIT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class CcaPersistorTest {

    @Mock
    private CcaDao ccaDao;

    @Spy
    private final CcaMasterMapper ccaMasterMapper = Mappers.getMapper(CcaMasterMapper.class);

    @Spy
    private final CcaAwbDetailMapper ccaAwbDetailMapper = Mappers.getMapper(CcaAwbDetailMapper.class);

    @Spy
    private final UnitQuantityMapper unitQuantityMapper = Mappers.getMapper(UnitQuantityMapper.class);

    @Spy
    private final CcaCustomerDetailMapper ccaCustomerDetailMapper = Mappers.getMapper(CcaCustomerDetailMapper.class);

    @Spy
    private final CcaTaxMapper ccaTaxMapper = Mappers.getMapper(CcaTaxMapper.class);

    @Mock
    private ContextUtil contextUtil;

    @Mock
    private QuantityMapper quantityMapper;

    private CcaAwbMapper ccaAwbMapper;

    @InjectMocks
    private CcaPersistor ccaPersistor;

    @BeforeEach
    public void setup() {
        final var quantities = MockQuantity.performInitialisation(null, null, LOCATION_CODE, null);
        ccaAwbMapper = MockQuantity.injectMapper(quantities, CcaAwbMapper.class);
        ReflectionTestUtils.setField(ccaAwbDetailMapper, "moneyMapper", new MoneyMapper());
        ReflectionTestUtils.setField(ccaAwbDetailMapper, "unitQuantityMapper", unitQuantityMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "moneyMapper", new MoneyMapper());
        ReflectionTestUtils.setField(ccaAwbMapper, "unitQuantityMapper", unitQuantityMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "ccaAwbDetailMapper", ccaAwbDetailMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "ccaTaxMapper", ccaTaxMapper);
        MockitoAnnotations.openMocks(this);

        var loginProfile = new LoginProfile();
        loginProfile.setFirstName("Mr.");
        loginProfile.setFirstName("Freeze");
        doReturn(new LoginProfile()).when(contextUtil).callerLoginProfile();
    }

    @ParameterizedTest
    @MethodSource("provideRoutingDetailsAndDimensions")
    void shouldSaveCCA(final Set<CcaAwbRoutingDetailsVO> ccaAwbRoutingDetails,
                       final Set<DimensionsVO> ccaAwbDetailDimensions) {
        // Given
        final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        orgCcaAwbVO.getRatingDetails().forEach(ccaRatingDetailVO -> ccaRatingDetailVO.setDimensions(ccaAwbDetailDimensions));
        orgCcaAwbVO.setCcaAwbRoutingDetails(ccaAwbRoutingDetails);

        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        revCcaAwbVO.getRatingDetails().forEach(ccaRatingDetailVO -> ccaRatingDetailVO.setDimensions(ccaAwbDetailDimensions));
        revCcaAwbVO.setCcaAwbRoutingDetails(ccaAwbRoutingDetails);
        revCcaAwbVO.setTriggerPoint(TRIGGER_QUALITY_AUDIT);

        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setShipmentDetailVOs(List.of(orgCcaAwbVO, revCcaAwbVO));
        final var ccaMaster = populateCcaMaster(List.of(orgCcaAwbVO, revCcaAwbVO));

        // When
        doReturn(ccaMaster).when(ccaMasterMapper).constructCCAMasterFromVo(ccaMasterVO);
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertNotNull(ccaPersistor.persist(ccaMasterVO));
    }

    private static Stream<Arguments> provideRoutingDetailsAndDimensions() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(Set.of(), Set.of()),
                Arguments.of(Set.of(new CcaAwbRoutingDetailsVO()), Set.of(new DimensionsVO(), new DimensionsVO()))
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldUpdateCCA(final boolean quantityShouldBeNotNull) {
        // Given
        final var weight = getWeightQuantity(13.5);
        final var volume = getVolumeQuantity(0.5);
        final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        orgCcaAwbVO.getRatingDetails().forEach(ccaRatingDetailVO -> ccaRatingDetailVO.setDimensions(Set.of()));
        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        revCcaAwbVO.getRatingDetails().forEach(ccaRatingDetailVO -> ccaRatingDetailVO.setDimensions(Set.of()));
        final var ccaMasterVO = getCCAMasterVO("44225566", "CCA000001", LocalDate.now());

        if (quantityShouldBeNotNull) {
            orgCcaAwbVO.setCurrency("USD");
            orgCcaAwbVO.setProductName("NVIDIA");
            orgCcaAwbVO.setAgentCode("AIEDXB");
            orgCcaAwbVO.setVolumetricWeight(weight);
            orgCcaAwbVO.setChargeableWeight(weight);
            orgCcaAwbVO.setAdjustedWeight(weight);
            orgCcaAwbVO.setWeight(weight);
            orgCcaAwbVO.setVolume(volume);

            orgCcaAwbVO.setAwbCharges(null);
            orgCcaAwbVO.setAwbRates(null);
            orgCcaAwbVO.setCcaCustomerDetails(Set.of());

            revCcaAwbVO.setCurrency("EUR");
            revCcaAwbVO.setWeight(weight);
            revCcaAwbVO.setProductName("NVIDIA");
            revCcaAwbVO.setAgentCode("AIEDXB");
            revCcaAwbVO.setVolume(volume);
            revCcaAwbVO.setVolumetricWeight(weight);
            revCcaAwbVO.setChargeableWeight(weight);
            revCcaAwbVO.setAdjustedWeight(weight);
            revCcaAwbVO.setDiscountAmount(10.0);
            revCcaAwbVO.setCommissionAmount(30.0);
            revCcaAwbVO.setTaxAmount(20.0);

            revCcaAwbVO.setAwbCharges(null);
            revCcaAwbVO.setAwbRates(null);
            revCcaAwbVO.setCcaCustomerDetails(Set.of(getCcaCustomerDetailVO(CustomerType.I), getCcaCustomerDetailVO(CustomerType.O), getCcaCustomerDetailVO(CustomerType.A)));

            final var awbQualityAuditDetailVO = new CcaDetailVO();
            awbQualityAuditDetailVO.setTotalNonAWBCharge(21.5);
            revCcaAwbVO.setAwbQualityAuditDetail(awbQualityAuditDetailVO);
            orgCcaAwbVO.setAwbQualityAuditDetail(awbQualityAuditDetailVO);
        }

        ccaMasterVO.setOriginalShipmentVO(orgCcaAwbVO);
        ccaMasterVO.setRevisedShipmentVO(revCcaAwbVO);
        ccaMasterVO.setShipmentDetailVOs(List.of(orgCcaAwbVO, revCcaAwbVO));
        final var ccaMaster = populateCcaMaster(List.of(orgCcaAwbVO, revCcaAwbVO));

        // When
        doReturn(new CCAFilterVO()).when(ccaMasterMapper).ccaMasterVOToCcaFilterVO(ccaMasterVO);
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));

        // Then
        assertNotNull(ccaPersistor.persist(ccaMasterVO));
    }

    @Test
    void shouldNotSaveCCAWithoutDetails() {
        // Given
        var emptyCcaMasterVO = new CCAMasterVO();
        
        // When
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertThrows(NullPointerException.class, () -> ccaPersistor.persist(emptyCcaMasterVO));
    }

    @Test
    void shouldUpdateCCAAWBDetails() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());
        final var shipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        shipmentDetailVO.getRatingDetails().forEach(ccaRatingDetailVO -> ccaRatingDetailVO.setDimensions(Set.of()));
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());

    }

    @Test
    void shouldUpdateAwbWithNullWeightAndVolume() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));
        shipmentDetailVO.setRatingDetails(Set.of(new CcaRatingDetailVO()));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        final var awb = new CCAAwb();
        var pk = new CCAAwbPk();
        pk.setCcaSerialNumber(ccaMaster.getCcaSerialNumber());
        pk.setRecordType(CcaConstants.CCA_RECORD_TYPE_REVISED);
        awb.setCcaAwbPk(pk);
        var awbDetails = new HashSet<CCAAwbDetail>(){{
            add(new CCAAwbDetail());
        }};
        awb.setAwbDetails(awbDetails);

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));
        awb.update(ccaMaster.getCcaStatus(), shipmentDetailVO,
                quantityMapper, ccaAwbDetailMapper, ccaCustomerDetailMapper, ccaTaxMapper);

        // Then
        assertEquals(0, awb.getWeight());
        assertEquals(0, awb.getAdjustedWeight());
        assertEquals(0, awb.getChargeableWeight());
        assertEquals(0, awb.getVolumetricWeight());
        assertEquals(0, awb.getDisplayWeight());
        assertEquals(0, awb.getDisplayAdjustedWeight());
        assertEquals(0, awb.getDisplayChargeableWeight());
        assertEquals(0, awb.getDisplayVolumetricWeight());
        assertEquals(0, awb.getVolume());
        assertEquals(0, awb.getDisplayVolume());
    }

    @Test
    void shouldUpdateAwbDetailsWithNullWeight() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));
        var ccaRatingDetails = new CcaRatingDetailVO();
        ccaRatingDetails.setSerialNumber(1L);
        shipmentDetailVO.setRatingDetails(Set.of(ccaRatingDetails));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        final var awb = new CCAAwb();
        final var actual = new CCAAwbDetail();
        actual.setSerialNumber(1L);
        awb.setAwbDetails(Set.of(actual));

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));
        awb.updateAwbDetails(shipmentDetailVO, quantityMapper, ccaAwbDetailMapper);

        // Then
        assertEquals(0, actual.getWeightOfShipment());
        assertEquals(0, actual.getAdjustedWeight());
        assertEquals(0, actual.getChargeableWeight());
        assertEquals(0, actual.getVolumetricWeight());
        assertEquals(0, actual.getDisplayWeightOfShipment());
        assertEquals(0, actual.getDisplayAdjustedWeight());
        assertEquals(0, actual.getDisplayChargeableWeight());
        assertEquals(0, actual.getDisplayVolumetricWeight());
    }

    @Test
    void shouldUpdateAwbDetailsWithNullVolume() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));
        var ccaRatingDetailVO = new CcaRatingDetailVO();
        ccaRatingDetailVO.setSerialNumber(1L);
        shipmentDetailVO.setRatingDetails(Set.of(ccaRatingDetailVO));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        final var awb = new CCAAwb();
        final var actual = new CCAAwbDetail();
        actual.setSerialNumber(1L);
        awb.setAwbDetails(Set.of(actual));

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));
        awb.updateAwbDetails(shipmentDetailVO, quantityMapper, ccaAwbDetailMapper);

        // Then
        assertEquals(0, actual.getVolumeOfShipment());
        assertEquals(0, actual.getDisplayVolumeOfShipment());
    }

    @Test
    void shouldUpdateCCAAWBChargeDetails() {
        // Given
        final var shipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        shipmentDetailVO.getRatingDetails().forEach(ccaRatingDetailVO -> ccaRatingDetailVO.setDimensions(Set.of()));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // Given
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));
        when(ccaMasterMapper.ccaMasterVOToCcaFilterVO(ccaMasterVO)).thenReturn(new CCAFilterVO());

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

    @Test
    void shouldUpdateAWBChargesAndRatesAsNull() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());
        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setMasterDocumentNumber("23323311");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));

        final var rateDetailVOCharge = getMoney(10.5, "USD");
        final var rateDetailVO1 = getCcaRateDetailsVO(rateDetailVOCharge, 10.5, "GEN", COMPANY_CODE,
                10.5, 1.0, MKT_TYPE);
        final var rateDetailVO2 = getCcaRateDetailsVO(rateDetailVOCharge, 10.5, "APL", COMPANY_CODE,
                10.5, 1.0, MKT_TYPE);

        shipmentDetailVO.setAwbRates(List.of(rateDetailVO1, rateDetailVO2));

        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));
        final var awb = new CCAAwb();
        CCAAwbDetail awbDetail1 = new CCAAwbDetail();
        awbDetail1.setCcaawb(awb);
        awbDetail1.setCommodityCode("GEN");

        final var awbDetail2 = new CCAAwbDetail();
        awbDetail2.setCcaawb(awb);
        awbDetail2.setCommodityCode("VAL");

        awb.setAwbDetails(Set.of(awbDetail1, awbDetail2));
        awb.setAwbRates(getAwbRates());
        awb.setAwbCharges(getAwbCharges());
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

    @Test
    void shouldUpdateAWBRatesDetailsAsNull() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));

        final var awb = getCCAAwbWithSetAwbDetails();

        awb.updateAwbCharges(shipmentDetailVO, ccaAwbDetailMapper);
        awb.updateAwbDetails(shipmentDetailVO, quantityMapper, ccaAwbDetailMapper);
        awb.setAwbRates(getAwbRates());
        awb.updateAwbRateDetails(shipmentDetailVO, ccaAwbDetailMapper);
        awb.setAwbCharges(getAwbCharges());

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

    @Test
    void shouldUpdateAwbDetails() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));
        new CCAAwb().updateAwbDetails(shipmentDetailVO, quantityMapper, ccaAwbDetailMapper);
        new CCAAwb().updateAwbRateDetails(shipmentDetailVO, ccaAwbDetailMapper);
        new CCAAwb().updateAwbDetails(shipmentDetailVO, quantityMapper, ccaAwbDetailMapper);
        new CCAAwb().updateCcaAwbRoutingDetails(shipmentDetailVO, ccaAwbDetailMapper);

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

    @Test
    void shouldUpdateAwbRateDetailsWithNullAwbRates() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());
        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setMasterDocumentNumber("23323311");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));

        final var rateDetailVOCharge = getMoney(10.5, "USD");
        final var rateDetailVO1 = getCcaRateDetailsVO(rateDetailVOCharge, 10.5, "GEN", COMPANY_CODE,
                10.5, 1.0, MKT_TYPE);
        final var rateDetailVO2 = getCcaRateDetailsVO(rateDetailVOCharge, 10.5, "APL", COMPANY_CODE,
                10.5, 1.0, MKT_TYPE);

        shipmentDetailVO.setAwbRates(List.of(rateDetailVO1, rateDetailVO2));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));
        new CCAAwb().updateAwbRateDetails(shipmentDetailVO, ccaAwbDetailMapper);

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

    @ParameterizedTest
    @MethodSource("provideRoutingDetails")
    void shouldUpdateRoutingDetails(final Set<CcaAwbRoutingDetails> ccaAwbRoutingDetails,
                                    final Set<CcaAwbRoutingDetailsVO> ccaAwbRoutingDetailsVO) {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));
        shipmentDetailVO.setCcaAwbRoutingDetails(ccaAwbRoutingDetailsVO);

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        final var awb = getCCAAwbWithSetAwbDetails();
        awb.setCcaAwbRoutingDetails(ccaAwbRoutingDetails);

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));
        awb.updateCcaAwbRoutingDetails(shipmentDetailVO, ccaAwbDetailMapper);

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

    private static Stream<Arguments> provideRoutingDetails() {
        final var ccaAwbRoutingDetailsVO = new CcaAwbRoutingDetailsVO();
        ccaAwbRoutingDetailsVO.setArgumentType("arg");
        ccaAwbRoutingDetailsVO.setDestination("des");

        final var ccaAwbRoutingDetailsVOS = new HashSet<CcaAwbRoutingDetailsVO>();
        ccaAwbRoutingDetailsVOS.add(ccaAwbRoutingDetailsVO);
        ccaAwbRoutingDetailsVOS.add(new CcaAwbRoutingDetailsVO());

        final var routingDetails = new CcaAwbRoutingDetails();
        routingDetails.setDestination("test");
        routingDetails.setDestination("test");

        final var ccaAwbRoutingDetails = new HashSet<CcaAwbRoutingDetails>();
        ccaAwbRoutingDetails.add(new CcaAwbRoutingDetails());
        ccaAwbRoutingDetails.add(routingDetails);

        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new HashSet<CcaAwbRoutingDetails>(), new HashSet<CcaAwbRoutingDetailsVO>()),
                Arguments.of(ccaAwbRoutingDetails, ccaAwbRoutingDetailsVOS)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDimensions")
    void shouldUpdateDimensions(final Set<DimensionsVO> dimensions,
                                final Set<CCAAwbDetail> awbDetails) {
        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));
        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO));
        final var ratingDetailVOS = new ArrayList<CcaRatingDetailVO>();
        final var ccaRatingDetailVO = new CcaRatingDetailVO();
        ccaRatingDetailVO.setCommodityCode("GEN");
        ccaRatingDetailVO.setDimensions(dimensions);
        ratingDetailVOS.add(ccaRatingDetailVO);

        shipmentDetailVO.setRatingDetails(ratingDetailVOS);

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        final var awb = getCCAAwbWithSetAwbDetails();
        awb.setAwbDetails(awbDetails);

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertDoesNotThrow(() -> awb.updateAwbDetails(shipmentDetailVO, quantityMapper, ccaAwbDetailMapper));
    }

    private static Stream<Arguments> provideDimensions() {
        final var ccaAwbDetail = new CCAAwbDetail();
        ccaAwbDetail.setCommodityCode("GEN");
        ccaAwbDetail.setNumberOfPieces(1);
        final var ccaAwbDetails = new HashSet<CCAAwbDetail>();
        ccaAwbDetails.add(ccaAwbDetail);

        final var dimensionsVO = new DimensionsVO();
        dimensionsVO.setPieces(42);
        final var dimensionsVOS = new HashSet<DimensionsVO>();
        dimensionsVOS.add(dimensionsVO);

        return Stream.of(
                Arguments.of(null, new HashSet<CCAAwbDetail>()),
                Arguments.of(new HashSet<DimensionsVO>(), new HashSet<CCAAwbDetail>()),
                Arguments.of(new HashSet<DimensionsVO>(), ccaAwbDetails),
                Arguments.of(null, ccaAwbDetails),
                Arguments.of(dimensionsVOS, new HashSet<CCAAwbDetail>()),
                Arguments.of(dimensionsVOS, ccaAwbDetails)
        );
    }

    private CCAAwb getCCAAwbWithSetAwbDetails() {
        final var awb = new CCAAwb();
        awb.setProductName("NVIDIA");

        final var awbDetail = new CCAAwbDetail();
        awbDetail.setCcaawb(awb);
        awbDetail.setCommodityCode("GEN");

        final var awbDetail1 = new CCAAwbDetail();
        awbDetail1.setCcaawb(awb);
        awbDetail1.setCommodityCode("VAL");

        awb.setAwbDetails(Stream.of(awbDetail, awbDetail1).collect(Collectors.toSet()));
        return awb;
    }

    private Set<CCAAwbRateDetail> getAwbRates() {
        final var mapper = new ObjectMapper();
        final var rates = new HashSet<CCAAwbRateDetail>();
        final var rateDetail = new CCAAwbRateDetail();
        rateDetail.setCharge(10.5);
        rateDetail.setChargeableWeight(10.5);
        rateDetail.setCommodityCode("GEN");
        rateDetail.setCompanyCode("AV");
        rateDetail.setDisplayChargeableWeight(10.5);
        rateDetail.setRate(1.0);
        rateDetail.setRateType("MKT");
        final var rateDetails = mapper.createObjectNode();
        rateDetails.put("charge", 10.5);
        rateDetails.put("rate", 1.0);
        rateDetails.put("rateline_id", 1);
        rateDetails.put("rating_type", "GCR");
        rateDetails.put("rating_name", "TEST");
        rateDetails.put("discount_percentage", 0);
        rateDetail.setRateDetails(rateDetails);
        rates.add(rateDetail);
        return rates;
    }

    private Set<CCAAwbChargeDetail> getAwbCharges() {
        final var charges = new HashSet<CCAAwbChargeDetail>();
        final var charge = new CCAAwbChargeDetail();
        charge.setPaymentType("pre-paid");
        charge.setChargeHead("flat charge");
        charge.setCharge(34);
        charge.setChargeHeadCode("FC");
        charge.setDueCarrier("Y");
        charge.setDueAgent("N");
        charges.add(charge);
        return charges;
    }

    private CcaMaster populateCcaMaster(Collection<CcaAwbVO> shipments) {
        return populateCcaMaster(shipments, CcaStatus.A);
    }

    private CcaMaster populateCcaMaster(Collection<CcaAwbVO> shipments, CcaStatus ccaStatus) {
        final var ccaMaster = getBasicMockCcaMaster("CCA000001", "44225566", ccaStatus);
        final var ccaAwbs = new HashSet<CCAAwb>();
        shipments.forEach(shipment -> {
            if (shipment.getAwbCharges() == null) {
                shipment.setAwbCharges(new HashSet<>());
            }
            ccaAwbs.add(ccaAwbMapper.constructCcaAwb(shipment));
        });
        ccaMaster.setCcaAwb(ccaAwbs);
        return ccaMaster;
    }

    @ParameterizedTest
    @MethodSource("ccaWorkflowTestArguments")
    void shouldAddApprovalWorkflowStepAndRemoveRejected(CcaStatus initialStatus,
                                                        CcaStatus newStatus) {
        // Given
        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));
        shipmentDetailVO.setRatingDetails(Set.of(new CcaRatingDetailVO()));

        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO), initialStatus);
        ccaMaster.setCcaWorkflow(new HashSet<>());

        var ccaMasterVO = getCCAMasterVO("44225566", "CCA000001", LocalDate.now(), newStatus);
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        doReturn(Optional.of(ccaMaster)).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertEquals("44225566", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

    private static Stream<Arguments> ccaWorkflowTestArguments() {
        return Stream.of(
                Arguments.of(CcaStatus.N, CcaStatus.R),
                Arguments.of(CcaStatus.N, CcaStatus.S),
                Arguments.of(CcaStatus.R, CcaStatus.A)
        );
    }

    @Test
    void shouldSaveApprovedCCA() {

        var loginProfile = new LoginProfile();
        loginProfile.setFirstName("Mr.");
        loginProfile.setLastName("Freeze");
        doReturn(loginProfile).when(contextUtil).callerLoginProfile();

        // Given
        final var ccaMasterVO = getCCAMasterVO("23323311", "CCA000001", LocalDate.now());

        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setShipmentPrefix("134");
        shipmentDetailVO.setNetValueImport(getMoney(1.0, "USD"));
        shipmentDetailVO.setNetValueExport(getMoney(2.0, "USD"));

        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);
        ccaMasterVO.setCcaStatus(CcaStatus.A);

        final var ccaMaster = populateCcaMaster(List.of(shipmentDetailVO, shipmentDetailVO));

        // When
        doReturn(ccaMaster).when(ccaMasterMapper).constructCCAMasterFromVo(ccaMasterVO);
        doReturn(Optional.empty()).when(ccaDao).findCCAMaster(any(CCAFilterVO.class));
        doNothing().when(ccaDao).saveCCA(any(CcaMaster.class));

        // Then
        assertEquals("23323311", ccaPersistor.persist(ccaMasterVO).getMasterDocumentNumber());
    }

}
