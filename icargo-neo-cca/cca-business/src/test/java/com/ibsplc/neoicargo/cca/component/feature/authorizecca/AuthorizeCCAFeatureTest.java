package com.ibsplc.neoicargo.cca.component.feature.authorizecca;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.events.CCAApprovedEvent;
import com.ibsplc.neoicargo.cca.events.CcaEventsProducer;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.BulkActionEdge;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilterList;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import com.ibsplc.neoicargo.framework.tenant.audit.builder.AuditConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getBasicMockCcaMaster;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaDataFilter;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaListMasterVO;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthorizeCCAFeatureTest {

    @Mock
    private CcaDao ccaDao;

    @Mock
    private AuditUtils<AuditVO> auditUtils;

    @Captor
    private ArgumentCaptor<AuditConfig> auditConfigCaptor;

    @Mock
    private CcaEventsProducer ccaEventsProducer;

    @Spy
    private CcaMasterMapper ccaMasterMapper = Mappers.getMapper(CcaMasterMapper.class);

    @Spy
    private CcaEventMapper ccaEventMapper = Mappers.getMapper(CcaEventMapper.class);

    @InjectMocks
    private AuthorizeCCAFeature authorizeCCAFeature;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAuthorizeCCAInANewStatus() {
        // Given
        final var ccaMasterVOs = Set.of(
                getCcaListMasterVO("77803840", "CCA00001", LocalDate.now(), CcaStatus.N),
                getCcaListMasterVO("77803841", "CCA00002", LocalDate.now(), CcaStatus.A)
        );
        final var ccaMasters = List.of(
                getBasicMockCcaMaster("CCA00001", "77803840"),
                getBasicMockCcaMaster("CCA00002", "77803841")
        );
        final var ccaDataFilters = List.of(
                getCcaDataFilter(SHIPMENT_PREFIX, "77803840", "CCA00001", COMPANY_CODE),
                getCcaDataFilter(SHIPMENT_PREFIX, "77803841", "CCA00002", COMPANY_CODE)
        );
        var filtersData = CcaDataFilterList.builder()
                .ccaDataFilters(ccaDataFilters)
                .ccaScreenId(LIST_CCA_SCREEN_ID)
                .build();

        // When
        doReturn(new CCAApprovedEvent()).when(ccaEventMapper).constructCCAApprovedEventFromCcaMaster(any(CcaMaster.class));
        doReturn(ccaMasterVOs).when(ccaDao).getCCAList(ccaDataFilters);
        doReturn(ccaMasters).when(ccaDao).findCcaMastersByCcaSerialNumbers(anyList());

        // Then
        var actualResult = authorizeCCAFeature.perform(filtersData);
        assertEquals(
                List.of(
                        new BulkActionEdge(
                                SHIPMENT_PREFIX,
                                "77803840",
                                "CCA00001",
                                CcaStatus.A.name(),
                                "CCA00001 Authorized successfully"
                        )
                ),
                actualResult.getEdges()
        );
        assertEquals(1, actualResult.getErrors().size());
        var errorVO = actualResult.getErrors().get(0);
        assertEquals(CcaErrors.NEO_CCA_016.getErrorCode(), errorVO.getErrorCode());
        assertEquals(CcaErrors.NEO_CCA_016.getErrorMessage(), errorVO.getDefaultMessage());
        assertEquals("CCA00002", errorVO.getErrorData()[0]);

        // And Then
        verify(ccaEventsProducer, times(2)).publishEvent(anyString(), any());
        verify(auditUtils, times(1)).performAudit(auditConfigCaptor.capture());
        assertNotNull(auditConfigCaptor.getValue().getAuditVO());
    }
}
