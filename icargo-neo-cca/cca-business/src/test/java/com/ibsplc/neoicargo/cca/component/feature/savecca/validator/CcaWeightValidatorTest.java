package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.QUANTITIES;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(JUnitPlatform.class)
public class CcaWeightValidatorTest {
    @InjectMocks
    private CcaWeightValidator validator;
    private CCAMasterVO ccaMasterVO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Given
        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(revCcaAwbVO));
        ccaMasterVO.setRevisedShipmentVO(revCcaAwbVO);
    }

    @Test
    void shouldThrowExceptionWhenChargeableWeightZero() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().getRatingDetails().forEach(ccaRatingDetailVO -> {
            ccaRatingDetailVO.setWeightOfShipment(QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.ONE));
            ccaRatingDetailVO.setChargeableWeight(QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.ZERO));
        });

        // Then
        assertThrows(BusinessException.class, () -> validator.validate(ccaMasterVO));
    }

    @Test
    void shouldThrowExceptionWhenGrossWeightZero() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().getRatingDetails().forEach(ccaRatingDetailVO -> {
            ccaRatingDetailVO.setWeightOfShipment(QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.ZERO));
            ccaRatingDetailVO.setChargeableWeight(QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.ONE));
        });

        // Then
        assertThrows(BusinessException.class, () -> validator.validate(ccaMasterVO));
    }

    @Test
    void shouldNotThrowException() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().getRatingDetails().forEach(ccaRatingDetailVO -> {
            ccaRatingDetailVO.setWeightOfShipment(QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.ONE));
            ccaRatingDetailVO.setChargeableWeight(QUANTITIES.getQuantity(Quantities.WEIGHT, BigDecimal.ONE));
        });

        // Then
        assertDoesNotThrow(() -> validator.validate(ccaMasterVO));
    }
}