package com.ibsplc.neoicargo.cca.util;

import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.vo.CcaAwbRoutingDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportModal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.DESTINATION_AIRPORT;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX_INT_VALUE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getLoginProfile;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class AirportUtilTest {

    @Mock
    private AirportComponent airportComponent;

    @Mock
    private ContextUtil contextUtil;


    @InjectMocks
    private AirportUtil airportUtil;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
    }

    @Test
    void shouldValidateAirports() throws AirportBusinessException {
        // Given
        final var airportCodes = Set.of(DESTINATION_AIRPORT);
        final var airportListModals = getAirportListModals();

        // When
        doReturn(airportListModals).when(airportComponent).validateAirports(any(List.class));

        // Then
        final var validateAirportCodes = airportUtil.validateAirportCodes(airportCodes);

        assertFalse(validateAirportCodes.isEmpty());
    }

    @Test
    void shouldNotThrowErrorAndReturnEmptyMap() throws AirportBusinessException {
        // Given
        final var airportCodes = Set.of(DESTINATION_AIRPORT);

        // When
        doThrow(AirportBusinessException.class).when(airportComponent).validateAirports(any(List.class));

        // Then
        final var validateAirportCodes = airportUtil.validateAirportCodes(airportCodes);

        assertTrue(validateAirportCodes.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("valuesForDeterminationAgentCass")
    void shouldDetermineAgentCass(String cassIndicator, String countryCodeForOriginAirportCode,
                                  String countryCodeForDestinationAirportCode, Boolean expectedValue) {
        // Then
        assertEquals(expectedValue,
                airportUtil.determineAgentCass(cassIndicator, countryCodeForOriginAirportCode, countryCodeForDestinationAirportCode));
    }

    private static Stream<Arguments> valuesForDeterminationAgentCass() {
        // Given
        return Stream.of(
                Arguments.of("G", "FR", "UA", false),
                Arguments.of("D", "FR", "UA", false),
                Arguments.of("D", "UA", "UA", true),
                Arguments.of("I", "FR", "UA", true),
                Arguments.of("I", "UA", "UA", false)
        );
    }

    private List<AirportModal> getAirportListModals() {
        final var airportModal = new AirportModal();
        airportModal.setCountryCode("FR");
        airportModal.setAirportCode("PAR");

        final var airportModal2 = new AirportModal();
        airportModal2.setCountryCode("UA");
        airportModal2.setAirportCode(DESTINATION_AIRPORT);

        return List.of(airportModal, airportModal2);
    }

    @Test
    public void shouldReturnTrueWhenOwnAirlineCodeIsFirstCarrierCode() {
        // Given
        var revised = getRevisedCcaAwbVO();

        // When + Then
        assertTrue(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    @Test
    public void shouldReturnFalseWithSomeRoutingAndDifferentDestination() {
        // Given
        var revised = getRevisedCcaAwbVO();
        revised.setDestination("OOO");

        // When + Then
        assertFalse(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    @Test
    public void shouldReturnFalseWithSomeRoutingAndDifferentFirstCarrierCode() {
        // Given
        var anotherCompanyCode = "AB";
        var revised = getRevisedCcaAwbVO();
        revised.getCcaAwbRoutingDetails().forEach(routingDetail -> routingDetail.setFirstCarrierCode(anotherCompanyCode));

        // When + Then
        assertFalse(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    @Test
    public void shouldReturnTrueWhenOneRoutingAndOwnAirlineCodeIsFlightCarrierCode() {
        // Given
        var routingVO = new CcaAwbRoutingDetailsVO();
        routingVO.setDestination(DESTINATION_AIRPORT);
        routingVO.setFlightCarrierCode(COMPANY_CODE);

        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setCcaAwbRoutingDetails(Set.of(routingVO));
        revised.setDestination(DESTINATION_AIRPORT);

        // When + Then
        assertTrue(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    @Test
    public void shouldReturnFalseWithOneRoutingAndDifferentDestination() {
        // Given
        var routingVO = new CcaAwbRoutingDetailsVO();
        routingVO.setDestination(DESTINATION_AIRPORT);
        routingVO.setFlightCarrierCode(COMPANY_CODE);

        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setCcaAwbRoutingDetails(Set.of(routingVO));
        revised.setDestination("OOO");

        //When + Then
        assertFalse(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    @Test
    public void shouldReturnFalseWithOneRoutingAndDifferentFirstCarrierCode() {
        // Given
        var anotherCompanyCode = "AB";
        var routingVO = new CcaAwbRoutingDetailsVO();
        routingVO.setDestination(DESTINATION_AIRPORT);
        routingVO.setFlightCarrierCode(anotherCompanyCode);

        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setCcaAwbRoutingDetails(Set.of(routingVO));
        revised.setDestination(DESTINATION_AIRPORT);

        //Then
        assertFalse(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    @Test
    public void shouldReturnFalseWhenRoutingDetailsIsNull() {
        // Given
        final var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setDestination(DESTINATION_AIRPORT);

        // When + Then
        assertFalse(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    @Test
    public void shouldReturnFalseWhenRoutingDetailsAreEmpty() {
        // Given
        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        revised.setDestination(DESTINATION_AIRPORT);
        revised.setRoutingDetails(List.of());

        // When + Then
        assertFalse(() -> airportUtil.isLastOwnFlownRoute(revised));
    }

    private CcaAwbVO getRevisedCcaAwbVO() {
        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);

        var routing1 = new CcaAwbRoutingDetailsVO();
        routing1.setDestination(DESTINATION_AIRPORT);
        routing1.setFirstCarrierCode(COMPANY_CODE);
        var routing2 = new CcaAwbRoutingDetailsVO();
        routing2.setDestination(DESTINATION_AIRPORT);
        routing2.setFirstCarrierCode(COMPANY_CODE);

        revised.setCcaAwbRoutingDetails(Set.of(routing1, routing2));
        revised.setDestination(DESTINATION_AIRPORT);
        return revised;
    }

    @Test
    void isOwnAirlineShouldReturnFalse() {
        // Given
        var anotherShipment = String.valueOf(SHIPMENT_PREFIX_INT_VALUE + 5);
        var revised = getFullMockCcaAwbVO(anotherShipment, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);

        // When + Then
        assertFalse(airportUtil.isOwnAirline(revised));
    }

    @Test
    void isOwnAirlineShouldReturnTrue() {
        // Given
        var revised = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);

        // When + Then
        assertTrue(airportUtil.isOwnAirline(revised));
    }
}