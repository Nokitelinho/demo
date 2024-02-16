package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(JUnitPlatform.class)
public class CcaServiceCargoValidatorTest {
    @InjectMocks
    private CcaServiceCargoValidator validator;
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

    @ParameterizedTest
    @MethodSource("provideServiceCargoClassAndCharge")
    void shouldNotThrowException(final String serviceCargoClass, final double charge) {
        // Given
        ccaMasterVO.getRevisedShipmentVO().getAwbRates().forEach(rate ->
                rate.setCharge(getMoney(charge, "USD")));
        ccaMasterVO.getRevisedShipmentVO().setServiceCargoClass(serviceCargoClass);

        // Then
        assertDoesNotThrow(() -> validator.validate(ccaMasterVO));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidServiceCargoClassAndCharge")
    void shouldThrowException(final String serviceCargoClass, final double charge) {
        // Given
        ccaMasterVO.getRevisedShipmentVO().getAwbRates().forEach(rate ->
                rate.setCharge(getMoney(charge, "USD")));
        ccaMasterVO.getRevisedShipmentVO().setServiceCargoClass(serviceCargoClass);

        // Then
        assertThrows(BusinessException.class, () -> validator.validate(ccaMasterVO));
    }

    private static Stream<Arguments> provideServiceCargoClassAndCharge() {
        return Stream.of(
                Arguments.of("F", 0.0),
                Arguments.of("C", 0.0),
                Arguments.of("", 1.0)
        );
    }

    private static Stream<Arguments> provideInvalidServiceCargoClassAndCharge() {
        return Stream.of(
                Arguments.of("F", 1.0),
                Arguments.of("C", 1.0)
        );
    }
}