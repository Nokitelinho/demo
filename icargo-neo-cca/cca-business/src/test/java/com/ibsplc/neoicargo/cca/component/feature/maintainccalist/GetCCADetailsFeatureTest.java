package com.ibsplc.neoicargo.cca.component.feature.maintainccalist;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaWorkflowData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaWorkflowVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class GetCCADetailsFeatureTest {

    @Mock
    private CcaDao ccaDao;

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @InjectMocks
    private GetCCADetailsFeature GetCCADetailsFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetCCADetails() {
        // Given
        final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        final var ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgCcaAwbVO, revCcaAwbVO));
        final var ccaMasterData = getCcaMasterData(CcaStatus.A);

        // When
        doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any(CcaDataFilter.class));
        doReturn(ccaMasterData).when(ccaMasterMapper).constructCCAMasterData(ccaMasterVO);

        // Then
        assertNotNull(GetCCADetailsFeature.perform(new CcaDataFilter()));
    }

    @Test
    void shouldReturnNullIfCCADoesNotExist() {
        // When
        doReturn(null).when(ccaDao).getCCADetails(any(CcaDataFilter.class));

        // Then
        assertNull(GetCCADetailsFeature.perform(new CcaDataFilter()));
    }

    @Test
    void shouldGetCCADetailsWithWorkFlowData() {
        // Given
        final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");

        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setShipmentDetailVOs(Set.of(orgCcaAwbVO, revCcaAwbVO));
        ccaMasterVO.setCcaWorkFlows(
                List.of(
                        getCcaWorkflowVO(CcaStatus.N),
                        getCcaWorkflowVO(CcaStatus.A)
                )
        );

        final var ccaMasterData = new CCAMasterData();
        ccaMasterData.setCcaStatus(CcaStatus.A);
        ccaMasterData.setCcaWorkFlows(
                List.of(
                        getCcaWorkflowData(CcaStatus.N),
                        getCcaWorkflowData(CcaStatus.A)
                )
        );

        // When
        doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any(CcaDataFilter.class));
        doReturn(ccaMasterData).when(ccaMasterMapper).constructCCAMasterData(ccaMasterVO);

        // Then
        assertNotNull(GetCCADetailsFeature.perform(new CcaDataFilter()).getCcaWorkFlows());
    }


    @Test
    void shouldGetCCADetailsWithDifferentBillingPeriod() {
        // Given
        final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        final var ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgCcaAwbVO, revCcaAwbVO));
        ccaMasterVO.setBillingPeriodFrom(LocalDate.of(2020, 12, 3));
        ccaMasterVO.setBillingPeriodTo(LocalDate.of(2020, 12, 3));
        final var ccaMasterData = getCcaMasterData(CcaStatus.A);

        // When
        doReturn(ccaMasterVO).when(ccaDao).getCCADetails(any(CcaDataFilter.class));
        doReturn(ccaMasterData).when(ccaMasterMapper).constructCCAMasterData(ccaMasterVO);

        // Then
        assertNotNull(GetCCADetailsFeature.perform(new CcaDataFilter()));
    }

    @NotNull
    private CCAMasterData getCcaMasterData(final CcaStatus ccaStatus) {
        final var ccaMasterData = new CCAMasterData();
        ccaMasterData.setCcaStatus(ccaStatus);
        return ccaMasterData;
    }

}
