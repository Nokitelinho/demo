package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(JUnitPlatform.class)
class CalculateCCATaxesFeatureTest {

    private final CalculateCCATaxesFeature calculateCCATaxesFeature = new CalculateCCATaxesFeature();

    @Test
    void performShouldNotThrowAnyExceptions() {
        // Given
        var masterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());

        //When + Then
        var actual = assertDoesNotThrow(() -> calculateCCATaxesFeature.perform(masterVO));
    }
}