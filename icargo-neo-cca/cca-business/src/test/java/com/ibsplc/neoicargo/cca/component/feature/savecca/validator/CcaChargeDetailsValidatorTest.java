package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaChargeDetailsVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(JUnitPlatform.class)
public class CcaChargeDetailsValidatorTest {

    @InjectMocks
    private CcaChargeDetailsValidator ccaChargeDetailsValidator;
    private CCAMasterVO ccaMasterVO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Given
        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgCcaAwbVO, revCcaAwbVO));
        ccaMasterVO.setRevisedShipmentVO(revCcaAwbVO);
        ccaMasterVO.setOriginalShipmentVO(orgCcaAwbVO);
    }

    @Test
    void shouldBeValid() {
        // Given
        final var orgChargeZero = getCcaChargeDetailsVO("pre-paid", "test", getMoney(34.0, "USD"), "FD",
                false, true);
        final var revChargeZero = getCcaChargeDetailsVO("pre-paid", "test", getMoney(0.0, "USD"), "FD",
                false, true);
        final var orgCharge = getCcaChargeDetailsVO("pre-paid", "test", getMoney(34.0, "USD"), "KK",
                false, true);
        final var revCharge = getCcaChargeDetailsVO("pre-paid", "test", getMoney(100.0, "USD"), "KK",
                false, true);

        ccaMasterVO.getRevisedShipmentVO().setAwbCharges(List.of(orgCharge, orgChargeZero));
        ccaMasterVO.getOriginalShipmentVO().setAwbCharges(List.of(revCharge, revChargeZero));

        // Then
        assertDoesNotThrow(() -> ccaChargeDetailsValidator.validate(ccaMasterVO));
    }

    @Test
    void shouldUpdateExistingChargeToZero() {
        // Given
        final var orgCharge = getCcaChargeDetailsVO("pre-paid", "test", getMoney(34.0, "USD"), "FD",
                false, true);
        final var revCharge = getCcaChargeDetailsVO("pre-paid", "test", getMoney(0.0, "USD"), "FD",
                false, true);
        ccaMasterVO.getRevisedShipmentVO().setAwbCharges(List.of(orgCharge));
        ccaMasterVO.getOriginalShipmentVO().setAwbCharges(List.of(revCharge));

        // Then
        assertDoesNotThrow(() -> ccaChargeDetailsValidator.validate(ccaMasterVO));
    }

    @Test
    void shouldNotUpdateChargeToZero() {
        // Given
        final var orgCharge = getCcaChargeDetailsVO("pre-paid", "original", getMoney(34.0, "USD"), "OR",
                false, true);
        final var revCharge = getCcaChargeDetailsVO("pre-paid", "revised", getMoney(0.0, "USD"), "RV",
                false, true);
        ccaMasterVO.getOriginalShipmentVO().setAwbCharges(List.of(orgCharge));
        ccaMasterVO.getRevisedShipmentVO().setAwbCharges(List.of(revCharge));

        // Then
        assertThrows(BusinessException.class, () -> ccaChargeDetailsValidator.validate(ccaMasterVO));
    }

    @Test
    void shouldNotSaveOriginalChargeDuplicates() {
        // Given
        final var orgCharge = getCcaChargeDetailsVO("pre-paid", "original", getMoney(34.0, "USD"), "OR",
                false, true);
        final var duplicateCharge = getCcaChargeDetailsVO("pre-paid", "original", getMoney(0.0, "USD"), "OR",
                false, true);
        ccaMasterVO.getOriginalShipmentVO().setAwbCharges(List.of(orgCharge, duplicateCharge));

        // Then
        assertThrows(BusinessException.class, () -> ccaChargeDetailsValidator.validate(ccaMasterVO));
    }

    @Test
    void shouldNotSaveRevisedChargeDuplicates() {
        // Given
        final var revCharge = getCcaChargeDetailsVO("pre-paid", "revised", getMoney(34.0, "USD"), "RV",
                false, true);
        final var duplicateCharge = getCcaChargeDetailsVO("pre-paid", "revised", getMoney(10.0, "USD"), "RV",
                false, true);
        ccaMasterVO.getRevisedShipmentVO().setAwbCharges(List.of(revCharge, duplicateCharge));

        // Then
        assertThrows(BusinessException.class, () -> ccaChargeDetailsValidator.validate(ccaMasterVO));
    }

}