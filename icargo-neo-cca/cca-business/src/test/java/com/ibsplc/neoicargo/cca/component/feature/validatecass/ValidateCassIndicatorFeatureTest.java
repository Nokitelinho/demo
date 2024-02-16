package com.ibsplc.neoicargo.cca.component.feature.validatecass;

import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaCustomerDetailVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class ValidateCassIndicatorFeatureTest {

    @Mock
    private AirportUtil airportUtil;

    @InjectMocks
    private ValidateCassIndicatorFeature validateCassIndicatorFeature;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("ccaMasterVOValues")
    void shouldValidateCassIndicatorFeatureTest(CCAMasterVO ccaMasterVO,
                                                Set<CcaCustomerDetailVO> ccaCustomerDetailVOS) {

        ccaMasterVO.getOriginalShipmentVO().setCcaCustomerDetails(ccaCustomerDetailVOS);

        // When
        doReturn(Map.of("DXB", "UA", "PAR", "FR")).when(airportUtil).validateAirportCodes(any(Set.class));

        // Then
        assertNotNull(validateCassIndicatorFeature.perform(ccaMasterVO));
    }

    private static Stream<Arguments> ccaMasterVOValues() {
        // Given
        final var originalCcaAwbVO = new CcaAwbVO();
        originalCcaAwbVO.setRecordType(CCA_RECORD_TYPE_ORIGINAL);
        originalCcaAwbVO.setDestination("DXB");
        originalCcaAwbVO.setOrigin("CDG");

        final var ccaMasterVO1 = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO1.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO2 = getCCAMasterVO("44440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO2.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO3 = getCCAMasterVO("44440030", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO3.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO4 = getCCAMasterVO("47440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO4.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaMasterVO5 = getCCAMasterVO("47440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO5.setOriginalShipmentVO(originalCcaAwbVO);

        final var ccaCustomerDetailVO1 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO1.setCassIndicator("B");

        final var ccaCustomerDetailVO2 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO2.setCassIndicator("I");

        final var ccaCustomerDetailVO3 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);

        final var ccaCustomerDetailVO4 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO4.setCassIndicator("D");

        final var ccaCustomerDetailVO5 = getCcaCustomerDetailVO("DHL WORLDWIDE INTERNATIONAL",
                "CDG", "FR", "VIE90020", "12345678910", CustomerType.O);
        ccaCustomerDetailVO5.setCassIndicator(null);

        return Stream.of(
                Arguments.of(ccaMasterVO1, Set.of(ccaCustomerDetailVO1)),
                Arguments.of(ccaMasterVO2, Set.of(ccaCustomerDetailVO2)),
                Arguments.of(ccaMasterVO3, Set.of(ccaCustomerDetailVO3)),
                Arguments.of(ccaMasterVO4, Set.of(ccaCustomerDetailVO4)),
                Arguments.of(ccaMasterVO5, Set.of(ccaCustomerDetailVO5))
        );
    }

}
