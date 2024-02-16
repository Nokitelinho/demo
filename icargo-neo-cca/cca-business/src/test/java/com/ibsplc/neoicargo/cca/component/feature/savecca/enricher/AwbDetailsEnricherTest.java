package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CURRENCY_USD;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaMasterData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class AwbDetailsEnricherTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ContextUtil contextUtil;

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @InjectMocks
    private AwbDetailsEnricher awbDetailsEnricher;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(awbDetailsEnricher, "qualityAuditUrl", "http://localhost:9591/awb-qualityaudit/");

        // Given
        final var loginProfile = new LoginProfile();
        loginProfile.setCompanyCode(COMPANY_CODE);

        // When
        doReturn(loginProfile).when(contextUtil).callerLoginProfile();
    }

    @ParameterizedTest
    @MethodSource("getCCAMasterData")
    void shouldPerformWithoutError(CCAMasterData ccaMasterData) {
        // Given
        final var ccaMasterVO1 = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3));
        // When
        when(restTemplate.exchange(isA(String.class), eq(HttpMethod.GET), isA(HttpEntity.class), eq(CCAMasterData.class)))
                .thenReturn(ResponseEntity.ok(ccaMasterData));
        when(ccaMasterMapper.constructCCAMasterVOFromCCAData(ccaMasterData, ccaMasterData.getUnitOfMeasure()))
                .thenReturn(ccaMasterVO1);

        // Then
        assertDoesNotThrow(() -> awbDetailsEnricher.enrich(ccaMasterVO1));
    }

    @Test
    void shouldNotFoundCcaMasterDataAndPerformWithoutError() {
        // Given
        final var ccaMasterVO1 = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3));
        // When
        when(restTemplate.exchange(isA(String.class), eq(HttpMethod.GET), isA(HttpEntity.class), eq(CCAMasterData.class)))
                .thenReturn(ResponseEntity.noContent().build());

        // Then
        assertDoesNotThrow(() -> awbDetailsEnricher.enrich(ccaMasterVO1));
    }

    @Test
    void shouldEnrichCcaMasterVOForAutoCca() {
        // Given
        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMasterVO.setMasterDocumentNumber("23403273");

        // When
        when(restTemplate.exchange(isA(String.class), eq(HttpMethod.GET), isA(HttpEntity.class), eq(CCAMasterData.class)))
                .thenThrow(HttpClientErrorException.class);

        // Then
        assertDoesNotThrow(() -> awbDetailsEnricher.enrich(ccaMasterVO));
    }

    private static Stream<CCAMasterData> getCCAMasterData() {
        final var ccaMasterData = getCcaMasterData();
        ccaMasterData.setUnitOfMeasure(getUnitOfMeasure("K", "B", "C", CURRENCY_USD));

        final var ccaMasterData2 = getCcaMasterData();
        ccaMasterData2.setUnitOfMeasure(getUnitOfMeasure("K", "B", null, CURRENCY_USD));
        return Stream.of(ccaMasterData, ccaMasterData2);
    }

}