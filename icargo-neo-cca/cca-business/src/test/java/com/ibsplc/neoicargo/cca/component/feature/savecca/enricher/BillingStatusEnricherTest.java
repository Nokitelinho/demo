package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditData;
import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditedDetails;
import com.ibsplc.neoicargo.cca.constants.InboundBillingStatus;
import com.ibsplc.neoicargo.cca.constants.OutboundBillingStatus;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
class BillingStatusEnricherTest {

    @Mock
    private AirlineWebComponent airlineComponent;

    @Mock
    private ServiceProxy<QualityAuditedDetails> serviceProxy;

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @Mock
    private ContextUtil contextUtil;

    private CCAMasterVO ccaMasterVO;

    @InjectMocks
    private BillingStatusEnricher billingStatusEnricher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(billingStatusEnricher, "eblUrl", "http://localhost:9591/ebl-nbridge/v1/");

        // Given
        ccaMasterVO = new CCAMasterVO();

        // Then
        doReturn(new LoginProfile()).when(contextUtil).callerLoginProfile();
    }

    @ParameterizedTest
    @MethodSource("valuesForBillingStatusEnricher")
    void shouldEnrichBillingStatuses(QualityAuditData qualityAuditData, String importBillingStatus,
                                     String exportBillingStatus, String ccaType) throws BusinessException {
        //Given
        ccaMasterVO.setRevisedShipmentVO(new CcaAwbVO());
        ccaMasterVO.setCcaType(ccaType);

        final var airlineModel = new AirlineModel();
        airlineModel.setAirlineIdentifier(1134);

        // When
        doReturn(new QualityAuditedDetails()).when(ccaMasterMapper).constructQualityAuditedDetailsFromCcaAwbVO(any(), any());
        doNothing().when(ccaMasterMapper).constructTotalValues(any(), any());
        doReturn(airlineModel).when(airlineComponent).validateNumericCode(any());

        doReturn(qualityAuditData).when(serviceProxy).dispatch(anyString(),
                eq(HttpMethod.POST), any(QualityAuditedDetails.class), any(Class.class));

        billingStatusEnricher.enrich(ccaMasterVO);

        //Then
        assertEquals(importBillingStatus, ccaMasterVO.getImportBillingStatus());
        assertEquals(exportBillingStatus, ccaMasterVO.getExportBillingStatus());
    }

    private static Stream<Arguments> valuesForBillingStatusEnricher() {
        // Given
        final var qualityAuditData = new QualityAuditData();
        qualityAuditData.setExportBillingStatus(OutboundBillingStatus.CASS_BILLABLE.getStatusValueInDb());
        qualityAuditData.setImportBillingStatus(InboundBillingStatus.IMPORT_BILLED.getStatusValueInDb());

        final var qualityAuditData2 = new QualityAuditData();
        qualityAuditData2.setExportBillingStatus(OutboundBillingStatus.CUSTOMER_BILLABLE.getStatusValueInDb());
        qualityAuditData2.setImportBillingStatus(InboundBillingStatus.ON_HOLD.getStatusValueInDb());
        qualityAuditData2.setError(List.of("test error"));

        return Stream.of(Arguments.of(qualityAuditData, "N", "N", CCA_TYPE_INTERNAL),
                Arguments.of(qualityAuditData, "A", "C", CCA_TYPE_ACTUAL),
                Arguments.of(qualityAuditData2, "O", "S", CCA_TYPE_ACTUAL));
    }

    @Test
    void shouldNotEnrich() throws BusinessException {
        //When
        billingStatusEnricher.enrich(ccaMasterVO);

        //Then
        verify(serviceProxy, never()).dispatch(anyString(),
                eq(HttpMethod.POST), any(QualityAuditedDetails.class), any(Class.class));
    }

    @Test
    void shouldHandleException() {
        //Given
        ccaMasterVO.setRevisedShipmentVO(new CcaAwbVO());

        //When
        doThrow(RuntimeException.class).when(serviceProxy).dispatch(anyString(),
                eq(HttpMethod.POST), any(QualityAuditedDetails.class), any(Class.class));
        //Then
        assertThrows(SystemException.class, () -> billingStatusEnricher.enrich(ccaMasterVO));
    }

}
