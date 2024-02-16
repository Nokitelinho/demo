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
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(JUnitPlatform.class)
class CcaPiecesValidatorTest {
    @InjectMocks
    private CcaPiecesValidator validator;
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
    void shouldThrowExceptionWhenPiecesZero() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().setPieces(0);

        // Then
        assertThrows(BusinessException.class, () -> validator.validate(ccaMasterVO));
    }

    @Test
    void shouldNotThrowException() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().setPieces(10);

        // Then
        assertDoesNotThrow(() -> validator.validate(ccaMasterVO));
    }
}