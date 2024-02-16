package com.ibsplc.neoicargo.cca.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibsplc.neoicargo.cca.dto.CcaChangedStatusVO;
import com.ibsplc.neoicargo.cca.service.CcaAuditService;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditService;
import com.ibsplc.neoicargo.framework.tenant.audit.event.AuditEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMockCcaAuditListWithChangedStatus;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMockCcaAuditListWithChangedType;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMockCcaAuditListWithoutChanges;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_APPROVED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_NEW;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(JUnitPlatform.class)
class CcaAuditEventListenerTest {

    private static final String MASTER_DOCUMENT_NUMBER = "23323311";
    private static final String CCA_000001 = "CCA000001";

    @Mock
    private CcaAuditService ccaAuditService;

    @Mock
    private AuditService auditService;

    @Mock
    private ContextUtil contextUtil;

    @InjectMocks
    private CcaAuditEventListener ccaAuditEventListener;

    @Captor
    ArgumentCaptor<AuditEvent> auditEventCaptor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processEventProcessCcaAuditsDoesNotThrowException() {
        // Given
        var auditList = getMockCcaAuditListWithoutChanges(MASTER_DOCUMENT_NUMBER, CCA_000001);

        // When + Then
        assertDoesNotThrow(() -> ccaAuditEventListener.processEvent(auditList));
    }

    @Test
    void processEventProcessCcaAuditsDoesNotThrowExceptionWhenAuditServiceCannotSave() throws JsonProcessingException {
        // Given
        var auditList = getMockCcaAuditListWithoutChanges(MASTER_DOCUMENT_NUMBER, CCA_000001);
        doThrow(new RuntimeException())
                .when(auditService).saveAuditEvent(any(AuditEvent.class));

        // When + Then
        assertDoesNotThrow(() -> ccaAuditEventListener.processEvent(auditList));
    }

    @Test
    void processEventDoesNotSaveAuditEventWhenThereIsNoChanges() throws JsonProcessingException {
        // Given
        var auditList = getMockCcaAuditListWithoutChanges(MASTER_DOCUMENT_NUMBER, CCA_000001);

        // When
        ccaAuditEventListener.processEvent(auditList);

        // Then
        verify(auditService, never()).saveAuditEvent(any(AuditEvent.class));
    }

    @Test
    void processEventSaveAuditEventWhenCcaIsCreated() throws JsonProcessingException {
        // Given
        var changedStatusData = CcaChangedStatusVO.builder()
                .masterNumber(MASTER_DOCUMENT_NUMBER)
                .ccaNumber(CCA_000001)
                .oldStatus(null)
                .newStatus(CCA_NEW)
                .build();
        var auditList = getMockCcaAuditListWithChangedStatus(changedStatusData);

        // When
        ccaAuditEventListener.processEvent(auditList);

        // Then
        verify(auditService, atLeastOnce()).saveAuditEvent(any(AuditEvent.class));
        verify(ccaAuditService, atLeastOnce()).getInfoByNewStatus(any(CcaStatus.class), any(CCAMasterVO.class));
    }

    @Test
    void processEventSaveAuditEventWhenCcaStatusIsChanged() throws JsonProcessingException {
        // Given
        var changedStatusData = CcaChangedStatusVO.builder()
                .masterNumber(MASTER_DOCUMENT_NUMBER)
                .ccaNumber(CCA_000001)
                .oldStatus(CCA_NEW)
                .newStatus(CCA_APPROVED)
                .build();
        var auditList = getMockCcaAuditListWithChangedStatus(changedStatusData);

        // When
        ccaAuditEventListener.processEvent(auditList);

        // Then
        verify(auditService, atLeastOnce()).saveAuditEvent(any(AuditEvent.class));
        verify(ccaAuditService, atLeastOnce()).getInfoByNewStatus(any(CcaStatus.class), any(CCAMasterVO.class));
    }

    @Test
    void processEventSaveAuditEventWhenCcaTypeIsChanged() throws JsonProcessingException {
        // Given
        var auditList = getMockCcaAuditListWithChangedType(MASTER_DOCUMENT_NUMBER, CCA_000001);

        // When
        ccaAuditEventListener.processEvent(auditList);

        // Then
        verify(auditService, atLeastOnce()).saveAuditEvent(any(AuditEvent.class));
        verify(ccaAuditService, atLeastOnce()).getUpdatedInfo(anyMap(), any(CCAMasterVO.class));
    }

    // TODO: 5/27/2022 IAN-42052 - remove temporary test after audit framework update

    @SuppressWarnings("rawtypes")
    @Test
    void processEventSetActionTimeStationWhenItNull() throws JsonProcessingException {
        // Given
        var changedStatusData = CcaChangedStatusVO.builder()
                .masterNumber(MASTER_DOCUMENT_NUMBER)
                .ccaNumber(CCA_000001)
                .oldStatus(CCA_NEW)
                .newStatus(CCA_APPROVED)
                .build();
        var auditList = getMockCcaAuditListWithChangedStatus(changedStatusData);
        var auditEvent = (AuditEvent)((List)auditList.getMessage()).get(0);
        auditEvent.setActionDateTimeStation(null);
        var profile = new LoginProfile();
        profile.setAdditionalClaims(Map.of("user_timezone", "UTC"));
        doReturn(profile)
                .when(contextUtil).callerLoginProfile();

        // When
        ccaAuditEventListener.processEvent(auditList);

        // Then
        verify(auditService).saveAuditEvent(auditEventCaptor.capture());
        var actual = auditEventCaptor.getValue();
        assertNotNull(actual.getActionDateTimeStation());
    }
}