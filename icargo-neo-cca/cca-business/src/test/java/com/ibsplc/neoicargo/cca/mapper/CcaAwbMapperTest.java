package com.ibsplc.neoicargo.cca.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.events.CCAApprovedEvent;
import com.ibsplc.neoicargo.cca.events.CCADeleteEvent;
import com.ibsplc.neoicargo.cca.modal.CcaChargeDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaRateDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaTaxValueData;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
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

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaCustomerDetailVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCCAAwbDetailDimensions;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaMaster;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getVolumeQuantity;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getWeightQuantity;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaAwbMapperTest {

    private static final String MASTER_DOCUMENT_NUMBER = "23323311";
    private static final String SHIPMENT_PREFIX = "134";

    private final CcaAwbMapper ccaAwbMapper = Mappers.getMapper(CcaAwbMapper.class);
    private final UnitQuantityMapper unitQuantityMapper = Mappers.getMapper(UnitQuantityMapper.class);

    @Spy
    private final CcaAwbDetailMapper ccaAwbDetailMapper = Mappers.getMapper(CcaAwbDetailMapper.class);

    @Spy
    private final QuantityMapper quantityMapper = Mappers.getMapper(QuantityMapper.class);

    @Mock
    private MoneyMapper moneyMapper;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(ccaAwbDetailMapper, "unitQuantityMapper", unitQuantityMapper);
        ReflectionTestUtils.setField(ccaAwbDetailMapper, "moneyMapper", new MoneyMapper());

        ReflectionTestUtils.setField(ccaAwbMapper, "unitQuantityMapper", unitQuantityMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "ccaAwbDetailMapper", ccaAwbDetailMapper);

        final var quantities = MockQuantity.performInitialisation(null, null, LOCATION_CODE, null);
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(quantityMapper, "quanties", quantities);
    }

    @Test
    void shouldGetInboundCustomerDetails() {
        // Given
        final var awbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_ORIGINAL);
        awbVO.setCcaCustomerDetails(Set.of(getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL", "CDG", "FR", "VIE90020", "12345678910", CustomerType.I)));

        // Then
        final var ccaCustomerDetailData = ccaAwbMapper.getInboundCustomerDetails(awbVO);

        assertNotNull(ccaCustomerDetailData.getCustomerName());
        assertNotNull(ccaCustomerDetailData.getCountryCode());
        assertNotNull(ccaCustomerDetailData.getStationCode());
        assertNotNull(ccaCustomerDetailData.getAccountNumber());
        assertNotNull(ccaCustomerDetailData.getIataCode());
    }

    @Test
    void shouldGetOutboundCustomerDetails() {
        // Given
        final var awbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_ORIGINAL);
        awbVO.setCcaCustomerDetails(Set.of(getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL", "CDG", "FR", "VIE90020", "12345678910", CustomerType.O)));

        // Then
        final var ccaCustomerDetailData = ccaAwbMapper.getOutboundCustomerDetails(awbVO);

        assertNotNull(ccaCustomerDetailData.getCustomerName());
        assertNotNull(ccaCustomerDetailData.getCountryCode());
        assertNotNull(ccaCustomerDetailData.getStationCode());
        assertNotNull(ccaCustomerDetailData.getAccountNumber());
        assertNotNull(ccaCustomerDetailData.getIataCode());
    }

    @Test
    void shouldGeNertValues() {
        // Given
        final var awbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_ORIGINAL);

        // Then
        final var ccaAwb = ccaAwbMapper.constructCcaAwb(awbVO);

        assertEquals(1.0, ccaAwb.getNetValueImport());
        assertEquals(2.0, ccaAwb.getNetValueExport());
    }

    @Test
    void mapDisplayTaxDetailsShouldMapDisplayTaxDetails() {
        // Given
        final var taxDetails = new ObjectMapper().createObjectNode();
        taxDetails.put("Service TAX OCDC", 0.0);
        taxDetails.put("Service TAX OCDA", 0.0);
        taxDetails.put("Service TAX on Commission", 10.0);
        taxDetails.put("Service TAX Freight Charges", 110.0);

        // When
        final var actual = ccaAwbMapper.mapDisplayTaxDetails(taxDetails);

        // Then
        assertEquals(4, actual.size());
        assertTrue(actual.contains(getTaxValueData("Service TAX OCDC", 0.0)));
        assertTrue(actual.contains(getTaxValueData("Service TAX OCDA", 0.0)));
        assertTrue(actual.contains(getTaxValueData("Service TAX on Commission", 10.0)));
        assertTrue(actual.contains(getTaxValueData("Service TAX Freight Charges", 110.0)));
    }

    @Test
    void mapDisplayTaxDetailsShouldReturnEmptyWhenTaxDetailsWrongObject() {
        // When
        final var actual = ccaAwbMapper.mapDisplayTaxDetails(new ArrayList<>());

        // Then
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void mapDisplayTaxDetailsShouldReturnEmptyWhenTaxDetailsNull() {
        // When
        final var actual = ccaAwbMapper.mapDisplayTaxDetails(null);

        // Then
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @ParameterizedTest
    @MethodSource("getCCAMasterData")
    void shouldUpdateAwbForCcaDeleteEvent(CcaMaster ccaMaster) {
        // Given
        final var moneyObject = getMoney(10.0, "USD");
        final var unitOfMeasure = getUnitOfMeasure("K", "B", "C", "USD");

        // When
        ReflectionTestUtils.setField(ccaAwbMapper, "moneyMapper", moneyMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "quantityMapper", quantityMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "ccaAwbDetailMapper", ccaAwbDetailMapper);

        doReturn(new CcaChargeDetailData()).when(ccaAwbDetailMapper).constructCcaChargeDetailData(any(CcaChargeDetailsVO.class));
        doReturn(new CcaRateDetailData()).when(ccaAwbDetailMapper).constructCcaRateDetailData(any(CcaRateDetailsVO.class));
        doReturn(moneyObject).when(moneyMapper).moneyFromDouble(any(Double.class), eq(unitOfMeasure));
        doReturn(getWeightQuantity(100)).when(quantityMapper).getQuantityWeight(any(Double.class), eq(unitOfMeasure));
        doReturn(getVolumeQuantity(100)).when(quantityMapper).getQuantityVolume(any(Double.class), eq(unitOfMeasure));

        // Then
        assertDoesNotThrow(() -> ccaAwbMapper.updateAwbForCcaDeleteEvent(ccaMaster, new CCADeleteEvent()));
    }

    @ParameterizedTest
    @MethodSource("getCCAMasterData")
    void shouldUpdateAwbForCcaApproveEvent(CcaMaster ccaMaster) {
        // Given
        final var moneyObject = getMoney(10.0, "USD");
        final var unitOfMeasure = getUnitOfMeasure("K", "B", "C", "USD");

        // When
        ReflectionTestUtils.setField(ccaAwbMapper, "moneyMapper", moneyMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "quantityMapper", quantityMapper);
        ReflectionTestUtils.setField(ccaAwbMapper, "ccaAwbDetailMapper", ccaAwbDetailMapper);

        doReturn(new CcaChargeDetailData()).when(ccaAwbDetailMapper).constructCcaChargeDetailData(any(CcaChargeDetailsVO.class));
        doReturn(new CcaRateDetailData()).when(ccaAwbDetailMapper).constructCcaRateDetailData(any(CcaRateDetailsVO.class));
        doReturn(moneyObject).when(moneyMapper).moneyFromDouble(any(Double.class), eq(unitOfMeasure));
        doReturn(getWeightQuantity(100)).when(quantityMapper).getQuantityWeight(any(Double.class), eq(unitOfMeasure));
        doReturn(getVolumeQuantity(100)).when(quantityMapper).getQuantityVolume(any(Double.class), eq(unitOfMeasure));

        // Then
        assertDoesNotThrow(() -> ccaAwbMapper.updateAwbForCCAApprovedEvent(ccaMaster, new CCAApprovedEvent()));
    }

    private static Stream<CcaMaster> getCCAMasterData() {
        final var fullMockCcaMaster = getFullMockCcaMaster();
        fullMockCcaMaster.getCcaAwb()
                .forEach(awb -> awb.getAwbDetails()
                        .forEach(awbDetails -> {
                            final var dimensions = getFullMockCCAAwbDetailDimensions(awbDetails);
                            awbDetails.setDimensions(Set.of(dimensions));
                        }));

        return Stream.of(fullMockCcaMaster, getFullMockCcaMaster());
    }

    @NotNull
    private CcaTaxValueData getTaxValueData(String name, Object value) {
        return CcaTaxValueData.builder().taxName(name).taxValue(String.valueOf(value)).build();
    }

}