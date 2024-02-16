package com.ibsplc.neoicargo.cca.component;


import com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils;
import com.ibsplc.neoicargo.cca.component.feature.authorizecca.AuthorizeCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.CalculateCCATaxesFeature;
import com.ibsplc.neoicargo.cca.component.feature.deletecca.DeleteCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.getavailablereasoncodes.GetAvailableReasonCodesFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccaassignees.GetCcaAssigneesFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccalistview.GetCCAListViewFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccanumbers.GetCCANumbersFeature;
import com.ibsplc.neoicargo.cca.component.feature.getccanumbers.GetCCAReferenceNumberFeature;
import com.ibsplc.neoicargo.cca.component.feature.getnetvalues.GetNetValuesFeature;
import com.ibsplc.neoicargo.cca.component.feature.maintainccalist.GetCCADetailsFeature;
import com.ibsplc.neoicargo.cca.component.feature.maintainccalist.GetCCAListFeature;
import com.ibsplc.neoicargo.cca.component.feature.maintainccalist.GetRelatedCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.report.CCAPrintFeature;
import com.ibsplc.neoicargo.cca.component.feature.reratecca.RerateCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.savecca.SaveAutoCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.savecca.SaveCCAFeature;
import com.ibsplc.neoicargo.cca.component.feature.updateattachments.UpdateCcaMasterAttachments;
import com.ibsplc.neoicargo.cca.component.feature.updateccaassignee.UpdateCcaAssigneeFeature;
import com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced.CCAUpdateInvoicedFeature;
import com.ibsplc.neoicargo.cca.component.feature.updateccainvoiced.CCAUpdateVoidedFeature;
import com.ibsplc.neoicargo.cca.component.feature.validatecass.ValidateCassIndicatorFeature;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaPrintMapper;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.BulkActionData;
import com.ibsplc.neoicargo.cca.modal.CCAAWBDetailData;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CCAPrintModel;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataRequest;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataResponse;
import com.ibsplc.neoicargo.cca.modal.CcaChargeDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilterList;
import com.ibsplc.neoicargo.cca.modal.CcaDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaRateDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.modal.NetValuesData;
import com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAPrintFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAAWBDetailData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAFilterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaDataFilter;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaRateDetailsVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaSelectFilter;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getDefaultUnitsOfMeasure;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getLoginProfile;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getMoney;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getRelatedCCAData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getReportFilterModel;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.APPROVED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_INTERNAL;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class CcaComponentTest {

    @Mock
    private GetCCADetailsFeature GetCCADetailsFeature;

    @Mock
    private SaveCCAFeature saveCCAFeature;

    @Mock
    private GetRelatedCCAFeature getRelatedCCAFeature;

    @Mock
    private GetCCAListFeature getCCAFeature;

    @Mock
    private DeleteCCAFeature deleteCCAFeature;

    @Mock
    private AuthorizeCCAFeature authorizeCCAFeature;

    @Mock
    private RerateCCAFeature rerateCCAFeature;

    @Mock
    private GetAvailableReasonCodesFeature getAvailableReasonCodesFeature;

    @Spy
    private final CcaMasterMapper ccaMasterMapper = Mappers.getMapper(CcaMasterMapper.class);

    @Spy
    private final CcaPrintMapper ccaPrintMapper = Mappers.getMapper(CcaPrintMapper.class);

    @Mock
    private ContextUtil contextUtil;

    @Mock
    private CCAPrintFeature ccaPrintFeature;

    @Mock
    private CCAUpdateInvoicedFeature ccaUpdateInvoicedFeature;

    @Mock
    private GetCCAListViewFeature getCCAListViewFeature;

    @Mock
    private UpdateCcaMasterAttachments updateCcaMasterAttachments;

    @Mock
    private GetCCANumbersFeature getCCANumbersFeature;

    @Mock
    private GetCcaAssigneesFeature getCcaAssigneesFeature;

    @Mock
    private UpdateCcaAssigneeFeature updateCcaAssigneeFeature;

    @Mock
    private CalculateCCATaxesFeature calculateCCATaxesFeature;

    @Mock
    private SaveAutoCCAFeature saveAutoCCAFeature;

    @Mock
    private ValidateCassIndicatorFeature validateCassIndicatorFeature;

    @Mock
    private CCAUpdateVoidedFeature ccaUpdateVoidedFeature;

    @Mock
    private GetNetValuesFeature getNetValuesFeature;
    @Mock
    private GetCCAReferenceNumberFeature getCCAReferenceNumberFeature;

    @InjectMocks
    private CcaComponent ccaComponent;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(ccaMasterMapper, "moneyMapper", new MoneyMapper());
        ReflectionTestUtils.setField(ccaMasterMapper, "ccaAwbMapper", Mappers.getMapper(CcaAwbMapper.class));
        MockitoAnnotations.openMocks(this);

        doReturn(getLoginProfile()).when(contextUtil).callerLoginProfile();
    }

    @Test
    void shouldGetCCADetails() {
        // Given
        final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, "87696921", null, COMPANY_CODE);

        // When
        doReturn(new CCAMasterData()).when(GetCCADetailsFeature).perform(any(CcaDataFilter.class));

        // Then
        assertNotNull(ccaComponent.getCCADetails(ccaDataFilter));
    }

    @ParameterizedTest
    @MethodSource("provideCCAValidationData")
    void shouldSaveCCA(final CcaValidationData ccaValidationData,
                       final CCAMasterData ccaMasterData,
                       final boolean statusShouldBeValid) {
        // When
        doReturn(ccaValidationData).when(saveCCAFeature).execute(any(CCAMasterVO.class));
        when(calculateCCATaxesFeature.execute(any(CCAMasterVO.class))).thenAnswer(i -> i.getArguments()[0]);

        // Then
        if (ccaValidationData == null) {
            assertNull(ccaComponent.saveCCA(new CCAMasterData()));
        } else {
            final var validationData = ccaComponent.saveCCA(ccaMasterData);
            assertNotNull(validationData);

            // And
            if (statusShouldBeValid) {
                // Then
                assertEquals(ccaValidationData.getCcaReferenceNumber() + " " + APPROVED + " successfully", validationData.getStatusMessage());
            } else {
                assertEquals(ccaValidationData.getCcaReferenceNumber() + " " + "Invalid status", validationData.getStatusMessage());
            }
        }
    }

    private static Stream<Arguments> provideCCAValidationData() {
        // Given
        final var ccaMasterData1 = new CCAMasterData();
        ccaMasterData1.setCcaStatus(CcaStatus.A);

        return Stream.of(
                Arguments.of(new CcaValidationData(), ccaMasterData1, true),
                Arguments.of(null, null, false)
        );
    }

    @Test
    void shouldCheckCcaValidationNullWithSaveCCA() {
        // Given
        final var ccaMasterData = new CCAMasterData();
        ccaMasterData.setShipmentPrefix(SHIPMENT_PREFIX);
        ccaMasterData.setMasterDocumentNumber("77803840");
        ccaMasterData.setCcaType(CCA_TYPE_INTERNAL);
        ccaMasterData.setCcaStatus(CcaStatus.N);
        ccaMasterData.setUnitOfMeasure(getDefaultUnitsOfMeasure());
        ccaMasterData.setRevisedAWBData(new CCAAWBDetailData());

        // When
        CCAMasterVO ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setRevisedShipmentVO(getCcaAwbVO());
        doReturn(ccaMasterVO).when(ccaMasterMapper).constructCCAMasterVOFromCCAData(any(CCAMasterData.class), any(Units.class));
        doReturn(null).when(saveCCAFeature).execute(any(CCAMasterVO.class));

        // Then
        assertNull(ccaComponent.saveCCA(ccaMasterData));
    }

    @Test
    void shouldGetRelatedCCA() {
        // Given
        final var relatedCCA = getRelatedCCAData("44440000", SHIPMENT_PREFIX, "CCA000001");

        // When
        doReturn(List.of(relatedCCA)).when(getRelatedCCAFeature).perform(any(CcaDataFilter.class));
        ccaComponent.getRelatedCCA("134", "44440000");

        // Then
        verify(getRelatedCCAFeature, times(1)).perform(any(CcaDataFilter.class));
    }

    @Test
    void shouldGetCCAList() {
        // Given
        final var ccaDataFilter = new CcaDataFilter();
        ccaDataFilter.setCcaReferenceNumber("CCAA");

        // When
        doReturn(new ArrayList<CCAMasterData>()).when(getCCAFeature).perform(any(CcaDataFilter.class));

        // Then
        assertNotNull(ccaComponent.getCCAList(ccaDataFilter));
    }

    @ParameterizedTest
    @MethodSource("provideAutoCCAValidationData")
    void shouldSaveAutoCCA(final CcaValidationData ccaValidationData,
                           final CCAMasterData ccaMasterData,
                           final boolean statusShouldBeValid) {
        // When
        doReturn(ccaValidationData).when(saveAutoCCAFeature).execute(any(CCAMasterVO.class));

        // Then
        if (ccaValidationData == null) {
            assertNull(ccaComponent.saveAutoCCA(new CCAMasterVO()));
        } else {
            final var validationData = ccaComponent.saveAutoCCA(new CCAMasterVO());
            assertNotNull(validationData);

            // And
            if (statusShouldBeValid) {
                // Then
                assertEquals(ccaValidationData.getCcaReferenceNumber() + " " + APPROVED + " successfully", validationData.getStatusMessage());
            } else {
                assertEquals(ccaValidationData.getCcaReferenceNumber() + " " + "Invalid status", validationData.getStatusMessage());
            }
        }
    }

    private static Stream<Arguments> provideAutoCCAValidationData() {
        // Given
        final var ccaValidationData = new CcaValidationData();
        ccaValidationData.setCcaReferenceNumber("123456789");

        final var ccaMasterData = new CCAMasterData();
        ccaMasterData.setCcaStatus(CcaStatus.A);

        return Stream.of(
                Arguments.of(new CcaValidationData(), ccaMasterData, false),
                Arguments.of(ccaValidationData, ccaMasterData, false),
                Arguments.of(null, ccaMasterData, true)
        );
    }

    @Test
    void shouldDeleteCCA() {
        // Given
        var ccaDataFilter = MockModelsGeneratorUtils.getCcaDataFilter(
                "134",
                "77803840",
                "CCA000001",
                null
        );
        var filtersData = CcaDataFilterList.builder()
                .ccaDataFilters(List.of(ccaDataFilter))
                .ccaScreenId(LIST_CCA_SCREEN_ID)
                .build();

        // When
        doReturn(new BulkActionData(List.of(), List.of())).when(deleteCCAFeature).perform(any(CcaDataFilterList.class));

        // Then
        assertNotNull(ccaComponent.deleteCCA(filtersData));
    }

    @Test
    void shouldAuthorizeCCA() {
        // Given
        var ccaDataFilter = MockModelsGeneratorUtils.getCcaDataFilter(
                "134",
                "77803840",
                "CCA000001",
                null
        );
        var filtersData = CcaDataFilterList.builder()
                .ccaDataFilters(List.of(ccaDataFilter))
                .ccaScreenId(LIST_CCA_SCREEN_ID)
                .build();

        // When

        doReturn(new BulkActionData(List.of(), List.of())).when(authorizeCCAFeature).perform(any(CcaDataFilterList.class));

        // Then
        assertNotNull(ccaComponent.authorizeCCA(filtersData));
    }

    @Test
    void shouldReRateCCA() {
        // Given
        final var ccaMasterData = getCcaMasterData();

        final var ccaMasterDataResponse = getCcaMasterData();
        ccaMasterDataResponse.getRevisedAWBData().setAwbRates(buildAwbRateDetails());
        ccaMasterDataResponse.getRevisedAWBData().setAwbCharges(buildAwbChargeDetailData());

        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setShipmentPrefix("134");
        ccaMasterVO.setMasterDocumentNumber("77803840");

        ccaMasterVO.setRevisedShipmentVO(getCcaAwbVO());

        // When
        doReturn(ccaMasterVO).when(ccaMasterMapper).constructCCAMasterVOFromCCAData(ccaMasterData, null);
        doReturn(ccaMasterData).when(rerateCCAFeature).execute(any(CCAMasterVO.class));

        // Then
        assertEquals("77803840", ccaComponent.reRateCCA("FRT", ccaMasterData).getMasterDocumentNumber());
    }

    @Test
    void shouldGenerateCCAPrint() {
        // Given
        final var reportFilterModel = getReportFilterModel("CCA001", SHIPMENT_PREFIX, "77803840");

        // When
        doReturn(new CCAPrintModel()).when(ccaPrintFeature).execute(any(CCAPrintFilterVO.class));

        // Then
        assertNotNull(ccaComponent.generateCCAPrint(reportFilterModel));
    }

    @Test
    void shouldUpdateCcaStatusInvoiced() {
        // Given
        final var ccaFilterVO = getCCAFilterVO(SHIPMENT_PREFIX, "23323311", "CCA00001");

        // When
        ccaComponent.updateCcaStatusInvoiced(ccaFilterVO);

        // Then
        verify(ccaUpdateInvoicedFeature, times(1)).perform(any(CCAFilterVO.class));
    }

    @Test
    void shouldGetAvailableReasonCodes() {
        // When
        ccaComponent.getAvailableReasonCodes();

        // Then
        verify(getAvailableReasonCodesFeature, times(1)).perform();
    }

    @Test
    void shouldMapWithoutError() {
        //Given
        final var orgShipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        final var revShipmentDetailVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        final var ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgShipmentDetailVO, revShipmentDetailVO));

        //Then
        assertDoesNotThrow(() -> ccaMasterMapper.setPaymentTypes(ccaMasterVO));
    }

    @Test
    void shouldSetTotalsWithoutError() {
        //Given
        final var orgCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "O");
        final var revCcaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, "44225566", "R");
        final var ccaMasterVO = getCCAMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), Set.of(orgCcaAwbVO, revCcaAwbVO));

        //Then
        assertDoesNotThrow(() -> ccaMasterMapper.setTotals(ccaMasterVO));
    }

    @Test
    void shouldGetCCAListView() {
        // Given
        final var ccaListViewFilterData = new CCAListViewFilterData();

        // When
        ccaComponent.getCCAListView(ccaListViewFilterData);

        // Then
        verify(getCCAListViewFeature, times(1)).execute(any(CCAListViewFilterVO.class));
    }

    @Test
    void shouldUpdateCcaMasterAttachments() {
        // Given
        final var attachmentsData = new AttachmentsData();

        // When
        ccaComponent.updateCcaMasterAttachments(attachmentsData);

        // Then
        verify(updateCcaMasterAttachments, times(1)).perform(attachmentsData);
    }

    @Test
    void shouldGetCCANumbers() {
        /// Given
        var request = getCcaSelectFilter(false, "CCA", null);

        // When
        ccaComponent.getCCANumbers(request);

        // Then
        verify(getCCANumbersFeature, times(1)).perform(request, COMPANY_CODE);
    }

    @Test
    void shouldGetCcaAssignees() {
        // Given
        var request = getCcaSelectFilter(false, "Mr.Freeze", null);

        // When
        ccaComponent.getCcaAssignees(request);

        // Then
        verify(getCcaAssigneesFeature, times(1)).perform(request);
    }

    @Test
    void shouldUpdateCcaAssignee() throws CcaBusinessException {
        // Given
        var request = new CcaAssigneeData();

        // When
        ccaComponent.updateCcaAssignee(request);

        // Then
        verify(updateCcaAssigneeFeature, times(1)).perform(request, COMPANY_CODE);
    }

    @Test
    void shouldValidateCassIndicator() {
        // Given
        final var ccaCassValidationDataRequest = new CcaCassValidationDataRequest();
        ccaCassValidationDataRequest.setAgentCode("DHLCDG001");
        ccaCassValidationDataRequest.setDestination("DXB");
        ccaCassValidationDataRequest.setOrigin("LAX");
        ccaCassValidationDataRequest.setInboundCustomerCode("CARGOMST");
        ccaCassValidationDataRequest.setOutboundCustomerCode("CARGOMST");

        final var ccaMasterVO = new CCAMasterVO();

        // When
        doReturn(ccaMasterVO).when(ccaMasterMapper).createCCAMasterVOFromCcaAwbVO(ccaCassValidationDataRequest);
        doReturn(new CcaCassValidationDataResponse()).when(validateCassIndicatorFeature).execute(ccaMasterVO);
        ccaComponent.validateCassIndicator(ccaCassValidationDataRequest);

        // Then
        verify(validateCassIndicatorFeature, times(1)).execute(ccaMasterVO);
    }


    @Test
    void shouldUpdateExistingCCAForAwbVoidedEvent() {
        // Given
        final var awbVoidedEvent = new AwbVoidedEvent();

        // When
        ccaComponent.updateExistingCCAForAwbVoidedEvent(awbVoidedEvent);

        // Then
        verify(ccaUpdateVoidedFeature, times(1)).perform(awbVoidedEvent);
    }

    @Test
    void shouldGetNetValues() {
        // When
        doReturn(new NetValuesData()).when(getNetValuesFeature).execute(any(CCAMasterVO.class));

        // Then
        assertNotNull(ccaComponent.getNetValues(new CCAMasterData()));
        verify(getNetValuesFeature, times(1)).execute(any(CCAMasterVO.class));
    }
    @Test
    void shouldGetCCAReferenceNumber() {

        // When
        doReturn(new CCAMasterData ()).when(getCCAReferenceNumberFeature).execute(any(CCAMasterVO.class));

        // Then
        assertNotNull(ccaComponent.getCcaReferenceNumber(new CCAMasterData()));
        verify(getCCAReferenceNumberFeature, times(1)).execute(any(CCAMasterVO.class));
    }
    private CCAMasterData getCcaMasterData() {
        final var ccaAWBDetailData = getCCAAWBDetailData("EUR", "PP", 2508.2, 0.1, 0.1, 0.1, 1575.0,
                0.0, 2508.5, 0.0, 0.0, 148703.88, 0.0,
                0.0, "DHLABRAJ", "CDG", "DXB", 15, "CC", "FFFABRAJ", "AIEDXB");
        ccaAWBDetailData.setRatingDetails(List.of(new CcaDetailData()));
        ccaAWBDetailData.setAwbCharges(List.of(new CcaChargeDetailData()));
        ccaAWBDetailData.setAwbRates(List.of(new CcaRateDetailData()));
        ccaAWBDetailData.setTotalDueAgtCCFChg(12.00);
        ccaAWBDetailData.setTotalOtherCharges(56.36);

        final var ccaMasterData = new CCAMasterData();
        ccaMasterData.setCompanyCode(COMPANY_CODE);
        ccaMasterData.setDocumentOwnerId(1134);
        ccaMasterData.setShipmentPrefix("134");
        ccaMasterData.setMasterDocumentNumber("77803840");
        ccaMasterData.setCcaType(CCA_TYPE_INTERNAL);
        ccaMasterData.setCcaStatus(CcaStatus.N);
        ccaMasterData.setOriginalAWBData(ccaAWBDetailData);
        ccaMasterData.setRevisedAWBData(ccaAWBDetailData);
        ccaMasterData.setCcaRemarks("add new CCA");
        ccaMasterData.setCcaIssueDate("10-10-2021");
        return ccaMasterData;
    }

    private List<CcaChargeDetailData> buildAwbChargeDetailData() {
        final var aWBChargeDetailData = new CcaChargeDetailData();
        aWBChargeDetailData.setChargeHead("Flat Charge");
        aWBChargeDetailData.setChargeHeadCode("FC");
        aWBChargeDetailData.setCharge(200);
        return List.of(aWBChargeDetailData);
    }

    private List<CcaRateDetailData> buildAwbRateDetails() {
        final var aWBRateDetailData = new CcaRateDetailData();
        aWBRateDetailData.setCharge(10.5);
        aWBRateDetailData.setCommodityCode("GEN");
        aWBRateDetailData.setRate(2.0);
        aWBRateDetailData.setRateType("RECIATA");
        return List.of(aWBRateDetailData);
    }

    @NotNull
    private CcaAwbVO getCcaAwbVO() {
        final var shipmentDetailVO = new CcaAwbVO();
        shipmentDetailVO.setOrigin("CDG");
        shipmentDetailVO.setDestination("DXB");
        shipmentDetailVO.setCurrency("USD");
        shipmentDetailVO.setRatingParameter("FRT");

        final var awbRate = getCcaRateDetailsVO(getMoney(15.0, "USD"), 10.5, "GEN",
                COMPANY_CODE, 0.0, 10.0, "IATA");
        shipmentDetailVO.setAwbRates(List.of(awbRate));
        return shipmentDetailVO;
    }

}
