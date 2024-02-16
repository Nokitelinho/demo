package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.enricher;

import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.calculators.TaxesAndCommissionCalculator;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class TaxesCalculationEnricherTest {

    @Mock
    private TaxesAndCommissionCalculator taxCalculator;

    @InjectMocks
    private TaxesCalculationEnricher taxesCalculationEnricher;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enrich() {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);

        var taxDetails = new ArrayList<CcaTaxDetailsVO>();

        //When
        doReturn(taxDetails)
                .when(taxCalculator).computeTax(ccaAwbVO);

        //Then
        assertDoesNotThrow(() -> taxesCalculationEnricher.enrich(ccaMasterVO));
    }
}