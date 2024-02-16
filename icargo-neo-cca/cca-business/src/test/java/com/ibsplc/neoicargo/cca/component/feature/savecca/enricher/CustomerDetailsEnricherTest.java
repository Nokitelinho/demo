package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.component.feature.savecca.helper.CcaBillingTypeHelper;
import com.ibsplc.neoicargo.cca.mapper.CcaCustomerDetailMapper;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCustomerModels;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnitPlatform.class)
public class CustomerDetailsEnricherTest {

    @Mock
    private CustomerComponent customerComponent;
    @Mock
    private CcaBillingTypeHelper paymentTypeValidateHelper;
    @Spy
    private CcaCustomerDetailMapper ccaCustomerDetailMapper = Mappers.getMapper(CcaCustomerDetailMapper.class);
    @InjectMocks
    private CustomerDetailsEnricher customerDetailsEnricher;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldEnrichCustomerDetails() throws CustomerBusinessException {
        // Given
        final var orgShipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        final var revShipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        final var ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgShipmentDetailVO, revShipmentDetailVO));
        final var customerModels = getCustomerModels("DHLCDG001", "DHL WORLDWIDE INTERNATIONAL", "CDG", "FR", "VIE90020", "12345678910");
        final var customerModels1 = getCustomerModels("DHLABR", "DHL WORLDWIDE INTERNATIONAL", "VIE", "AT", "VIE90020", "06471280012");
        final var customerModels2 = getCustomerModels("AIEDXB", "DHL WORLDWIDE INTERNATIONAL", "DFW", "IN", "VIE90020", "06471280012");

        // When
        doReturn(customerModels).when(customerComponent).getCustomerDetails("DHLCDG001");
        doReturn(customerModels1).when(customerComponent).getCustomerDetails("DHLABR");
        doReturn(customerModels2).when(customerComponent).getCustomerDetails("AIEDXB");

        // Then
        assertDoesNotThrow(() -> customerDetailsEnricher.enrich(ccaMasterVO));
        verifyCorrect(ccaMasterVO.getOriginalShipmentVO());
        verifyCorrect(ccaMasterVO.getRevisedShipmentVO());
    }

    private void verifyCorrect(CcaAwbVO revisedShipmentVO) {
        for (final var ccaCustomerDetail : revisedShipmentVO.getCcaCustomerDetails()) {
            assertNotNull(ccaCustomerDetail.getCustomerName());
            assertNotNull(ccaCustomerDetail.getCountryCode());
            assertNotNull(ccaCustomerDetail.getStationCode());
            assertNotNull(ccaCustomerDetail.getAccountNumber());
            assertNotNull(ccaCustomerDetail.getIataCode());
        }
    }

    @Test
    void shouldNotSet() throws CustomerBusinessException {
        // Given
        final var orgShipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        final var revShipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        final var ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgShipmentDetailVO, revShipmentDetailVO));

        // When
        doThrow(CustomerBusinessException.class).when(customerComponent).getCustomerDetails(anyString());

        // Then
        assertDoesNotThrow(() -> customerDetailsEnricher.enrich(ccaMasterVO));
        verify(ccaMasterVO.getOriginalShipmentVO());
        verify(ccaMasterVO.getRevisedShipmentVO());
    }

    private void verify(CcaAwbVO ccaAwbVO) {
        for (final var ccaCustomerDetail : ccaAwbVO.getCcaCustomerDetails()) {
            assertNull(ccaCustomerDetail.getCustomerName());
            assertNull(ccaCustomerDetail.getCountryCode());
            assertNull(ccaCustomerDetail.getStationCode());
            assertNull(ccaCustomerDetail.getAccountNumber());
            assertNull(ccaCustomerDetail.getIataCode());
        }
    }

}