package com.ibsplc.neoicargo.cca.component.feature.savecca;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.cca.component.feature.savecca.persistor.CcaPersistor;
import com.ibsplc.neoicargo.cca.events.CcaEventsProducer;
import com.ibsplc.neoicargo.cca.mapper.CcaEventMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditUtils;
import com.ibsplc.neoicargo.framework.tenant.audit.AuditVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.component.feature.savecca.SaveCCAFeatureTest.SERVICE_TAX_FREIGHT_CHARGE;
import static com.ibsplc.neoicargo.cca.component.feature.savecca.SaveCCAFeatureTest.SERVICE_TAX_OCDA;
import static com.ibsplc.neoicargo.cca.component.feature.savecca.SaveCCAFeatureTest.SERVICE_TAX_OCDC;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.QUALITY_AUDITED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.TRIGGER_QUALITY_AUDIT;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class SaveAutoCCAFeatureTest {

    @Mock
    private CcaPersistor ccaPersistor;

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @Mock
    private AuditUtils<AuditVO> auditUtils;

    @Mock
    private CcaEventsProducer ccaEventsProducer;

    @Mock
    private CcaEventMapper ccaEventMapper;

    private CcaAwbVO shipmentDetailVO;

    @InjectMocks
    private SaveAutoCCAFeature saveAutoCCAFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Given
        shipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "12346548", "O");
        shipmentDetailVO.setQualityAudited(true);
        shipmentDetailVO.setQualityAuditStatus(QUALITY_AUDITED);
        setAwbTaxesForShipmentDetailVO();
    }

    @Test
    void shouldSaveCCA() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA00001", LocalDate.now());
        ccaMasterVO.setCcaType(CCA_TYPE_INTERNAL);
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        when(ccaMasterMapper.ccaMasterVOToCcaValidationData(ccaMasterVO)).thenReturn(new CcaValidationData());
        doReturn(ccaMasterVO).when(ccaPersistor).persist(any(CCAMasterVO.class));

        // Then
        assertNotNull(saveAutoCCAFeature.perform(ccaMasterVO));
    }

    @Test
    void shouldUpdateCCA() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA00001", LocalDate.now());
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        when(ccaMasterMapper.ccaMasterVOToCcaValidationData(ccaMasterVO)).thenReturn(new CcaValidationData());
        doReturn(ccaMasterVO).when(ccaPersistor).persist(any(CCAMasterVO.class));

        // Then
        assertNotNull(saveAutoCCAFeature.perform(ccaMasterVO));
    }

    @Test
    void shouldPerformApproveCCA() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA00001", LocalDate.now());
        ccaMasterVO.setCcaStatus(CcaStatus.A);
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        when(ccaMasterMapper.ccaMasterVOToCcaValidationData(ccaMasterVO)).thenReturn(new CcaValidationData());
        doReturn(ccaMasterVO).when(ccaPersistor).persist(any(CCAMasterVO.class));

        // Then
        assertNotNull(saveAutoCCAFeature.perform(ccaMasterVO));
    }

    @Test
    void shouldCheckNullEntityForCCA() {
        // Given
        final var shipmentDetailVO1 = new CcaAwbVO();
        shipmentDetailVO1.setShipmentPrefix(SHIPMENT_PREFIX);
        shipmentDetailVO1.setMasterDocumentNumber("12346548");
        shipmentDetailVO1.setQualityAudited(true);
        shipmentDetailVO1.setQualityAuditStatus(QUALITY_AUDITED);
        shipmentDetailVO1.setTriggerPoint(TRIGGER_QUALITY_AUDIT);

        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA00001", LocalDate.now());
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO1);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO1);

        // When
        when(ccaMasterMapper.ccaMasterVOToCcaValidationData(ccaMasterVO)).thenReturn(new CcaValidationData());
        doReturn(ccaMasterVO).when(ccaPersistor).persist(any(CCAMasterVO.class));

        // Then
        assertNotNull(saveAutoCCAFeature.perform(ccaMasterVO));
    }

    @Test
    void shouldPerformRejectCCA() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA00001", LocalDate.now());
        ccaMasterVO.setCcaStatus(CcaStatus.R);
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        when(ccaMasterMapper.ccaMasterVOToCcaValidationData(ccaMasterVO)).thenReturn(new CcaValidationData());
        doReturn(ccaMasterVO).when(ccaPersistor).persist(any(CCAMasterVO.class));

        // Then
        assertNotNull(saveAutoCCAFeature.perform(ccaMasterVO));
    }

    private void setAwbTaxesForShipmentDetailVO() {
        final var ccaTaxDetailsVO = new CcaTaxDetailsVO();
        ccaTaxDetailsVO.setSerialNumber(34651L);
        ccaTaxDetailsVO.setConfigurationType("TDS");
        final var jsonDataValue1 = new ObjectMapper().createObjectNode();
        jsonDataValue1.put(SERVICE_TAX_FREIGHT_CHARGE, 10.0);
        jsonDataValue1.put(SERVICE_TAX_OCDC, 10.0);
        jsonDataValue1.put(SERVICE_TAX_OCDA, 10.0);
        ccaTaxDetailsVO.setTaxDetails(jsonDataValue1);
        shipmentDetailVO.setAwbTaxes(List.of(ccaTaxDetailsVO));
    }

    @Test
    void shouldCreateEventsInPostInvoke() {
        // Given
        final var ccaMasterVO = getCCAMasterVO("12346548", "CCA00001", LocalDate.now());
        ccaMasterVO.setOriginalShipmentVO(shipmentDetailVO);
        ccaMasterVO.setRevisedShipmentVO(shipmentDetailVO);

        // When
        saveAutoCCAFeature.postInvoke(ccaMasterVO);

        // Then
        verify(ccaEventsProducer, atLeastOnce()).publishEvent(anyString(), any());
    }

}
