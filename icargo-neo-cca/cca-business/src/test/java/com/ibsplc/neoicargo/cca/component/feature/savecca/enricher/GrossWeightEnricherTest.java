package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.masters.currency.exceptions.ExchangeRateNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(JUnitPlatform.class)
public class GrossWeightEnricherTest {

    private QuantityMapper quantityMapper;

    @InjectMocks
    private GrossWeightEnricher grossWeightEnricher;

    @BeforeEach
    public void setup() throws ExchangeRateNotFoundException {
        final var quantities = MockQuantity.performInitialisation(null, null, LOCATION_CODE, null);
        quantityMapper = MockQuantity.injectMapper(quantities, QuantityMapper.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateChargeableWeightInCaseOfULD() throws BusinessException {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);

        // When
        grossWeightEnricher.enrich(ccaMasterVO);
        final var chargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var grossWeight = ccaMasterVO.getRevisedShipmentVO().getWeight().getValue();

        // Then
        Assertions.assertEquals(chargeableWeight, grossWeight);
    }

    @Test
    void shouldNotUpdateChargeableWeightInNonULDCase() throws BusinessException {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        final var oldChargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var oldGrossWeight = ccaMasterVO.getRevisedShipmentVO().getWeight().getValue();

        // When
        grossWeightEnricher.enrich(ccaMasterVO);
        final var chargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var grossWeight = ccaMasterVO.getRevisedShipmentVO().getWeight().getValue();

        // Then
        Assertions.assertEquals(chargeableWeight, oldChargeableWeight);
        Assertions.assertEquals(grossWeight, oldGrossWeight);
    }

    @Test
    void shouldNotUpdateChargeableWeightInRatingDetailsNullCase() throws BusinessException {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        ccaAwbVO.setRatingDetails(null);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        final var oldChargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var oldGrossWeight = ccaMasterVO.getRevisedShipmentVO().getWeight().getValue();

        // When
        grossWeightEnricher.enrich(ccaMasterVO);
        final var chargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var grossWeight = ccaMasterVO.getRevisedShipmentVO().getWeight().getValue();

        // Then
        Assertions.assertEquals(chargeableWeight, oldChargeableWeight);
        Assertions.assertEquals(grossWeight, oldGrossWeight);
    }

    @Test
    void shouldNotUpdateChargeableWeightInRatingDetailsEmptyCase() throws BusinessException {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        ccaAwbVO.setRatingDetails(Set.of());
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        final var oldChargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var oldGrossWeight = ccaMasterVO.getRevisedShipmentVO().getWeight().getValue();

        // When
        grossWeightEnricher.enrich(ccaMasterVO);
        final var chargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var grossWeight = ccaMasterVO.getRevisedShipmentVO().getWeight().getValue();

        // Then
        Assertions.assertEquals(chargeableWeight, oldChargeableWeight);
        Assertions.assertEquals(grossWeight, oldGrossWeight);
    }

    @Test
    void shouldThrowBusinessExceptionWhenGrossWeightMoreThanChargeableWeight() {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        ccaAwbVO.getRatingDetails().forEach(detail -> {
            detail.setUlds(Set.of());
            detail.setChargeableWeight(MockModelsGeneratorUtils.getWeightQuantity(10.0));
            detail.setWeightOfShipment(MockModelsGeneratorUtils.getWeightQuantity(100.0));
        });
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);

        // When + Then
        assertThrows(
                BusinessException.class,
                () -> grossWeightEnricher.enrich(ccaMasterVO));
    }

}