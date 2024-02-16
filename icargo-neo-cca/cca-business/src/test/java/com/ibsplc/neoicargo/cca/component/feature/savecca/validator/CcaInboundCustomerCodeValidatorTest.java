package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaInboundCustomerCodeValidatorTest {

    @Mock
    private AirportUtil airportUtil;

    @InjectMocks
    private CcaInboundCustomerCodeValidator validator;
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
    void shouldThrowExceptionWhenInboundCustomerCodeEmpty() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().setPayType("PC");
        ccaMasterVO.getRevisedShipmentVO().setInboundCustomerCode("");

        //When
        doReturn(true).when(airportUtil).isLastOwnFlownRoute(any(CcaAwbVO.class));

        // Then
        assertThrows(BusinessException.class, () -> validator.validate(ccaMasterVO));
    }

    @Test
    void shouldNotThrowExceptionWhenPayTypeEmpty() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().setPayType("DD");
        ccaMasterVO.getRevisedShipmentVO().setInboundCustomerCode("CUSTOMER");

        // Then
        assertDoesNotThrow(() -> validator.validate(ccaMasterVO));
    }

    @Test
    void shouldNotThrowException() {
        // Given
        ccaMasterVO.getRevisedShipmentVO().setPayType("PC");
        ccaMasterVO.getRevisedShipmentVO().setInboundCustomerCode("CUSTOMER");

        //When
        doReturn(true).when(airportUtil).isLastOwnFlownRoute(any(CcaAwbVO.class));

        // Then
        assertDoesNotThrow(() -> validator.validate(ccaMasterVO));
    }
}