package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.util.CcaParameterUtil;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.masters.ParameterType;
import com.ibsplc.neoicargo.masters.currency.exceptions.ExchangeRateNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.LOCATION_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class AdjustedWeightEnricherTest {

    @InjectMocks
    private AdjustedWeightEnricher adjustedWeightEnricher;

    @Mock
    private CcaParameterUtil ccaParameterUtil;
    private QuantityMapper quantityMapper;

    @BeforeEach
    public void setup() throws ExchangeRateNotFoundException {
        final var quantities = MockQuantity.performInitialisation(null, null, LOCATION_CODE, null);
        quantityMapper = MockQuantity.injectMapper(quantities, QuantityMapper.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateChargeableWeightInCaseOfSCC() throws BusinessException {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        doReturn("SCC").when(ccaParameterUtil).getSystemParameter(any(String.class), eq(ParameterType.SYSTEM_PARAMETER));

        // When
        adjustedWeightEnricher.enrich(ccaMasterVO);
        final var chargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var adjustedWeight = ccaMasterVO.getRevisedShipmentVO().getAdjustedWeight().getValue();

        // Then
        Assertions.assertEquals(chargeableWeight, adjustedWeight);
    }

    @Test
    void shouldNotUpdateChargeableWeightInCaseOfSCC() throws BusinessException {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        final var oldChargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var oldAdjustedWeight = ccaMasterVO.getRevisedShipmentVO().getAdjustedWeight().getValue();
        doReturn("TEST").when(ccaParameterUtil).getSystemParameter(any(String.class), eq(ParameterType.SYSTEM_PARAMETER));

        // When
        adjustedWeightEnricher.enrich(ccaMasterVO);
        final var chargeableWeight = ccaMasterVO.getRevisedShipmentVO().getChargeableWeight().getValue();
        final var adjustedWeight = ccaMasterVO.getRevisedShipmentVO().getAdjustedWeight().getValue();

        // Then
        Assertions.assertEquals(chargeableWeight, oldChargeableWeight);
        Assertions.assertEquals(adjustedWeight, oldAdjustedWeight);
    }

}