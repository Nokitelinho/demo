package com.ibsplc.neoicargo.cca.controller;

import com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.modal.AttachmentsData;
import com.ibsplc.neoicargo.cca.modal.AvailableReasonCodeData;
import com.ibsplc.neoicargo.cca.modal.BulkActionData;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CCAPrintFilterModel;
import com.ibsplc.neoicargo.cca.modal.CCAPrintModel;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneesPage;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataRequest;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataResponse;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilterList;
import com.ibsplc.neoicargo.cca.modal.CcaListViewPageInfo;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.modal.CcaNumbersPage;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.modal.NetValuesData;
import com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData;
import com.ibsplc.neoicargo.cca.service.CcaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.COMPANY_CODE;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaDataFilter;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaSelectFilter;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getRelatedCCAData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getReportFilterModel;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaControllerTest {

    @Mock
    private CcaService ccaService;

    @InjectMocks
    private CcaController ccaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetCCADetails() {
        // Given
        final var ccaDataFilter = getCcaDataFilter(SHIPMENT_PREFIX, "87696921", null, COMPANY_CODE);

        // When
        doReturn(new CCAMasterData()).when(ccaService).getCCADetails(any(CcaDataFilter.class));

        // Then
        assertNotNull(ccaController.getCCADetails(ccaDataFilter));
    }

    @Test
    void shouldSaveCCA() {
        // When
        doReturn(new CcaValidationData()).when(ccaService).saveCCA(any(CCAMasterData.class));

        // Then
        assertNotNull(ccaController.saveCCA(new CCAMasterData()));
    }

    @Test
    void shouldGetRelatedCCA() {
        // Given
        final var relatedCCA = getRelatedCCAData("77803840", SHIPMENT_PREFIX, "CCA00001");

        // When
        doReturn(List.of(relatedCCA)).when(ccaService).getRelatedCCA("134", "77803840");
        final var relatedCCADataReturn = ccaController.getRelatedCCA("134", "77803840");

        // Then
        assertEquals("CCA00001", relatedCCADataReturn.get(0).getCcaReferenceNumber());
    }

    @Test
    void shouldGetCCAList() {
        // Given
        final var ccaDataFilter = new CcaDataFilter();
        ccaDataFilter.setCcaReferenceNumber("CCAA");

        // When
        doReturn(new ArrayList<CCAMasterData>()).when(ccaService).getCCAList(any(CcaDataFilter.class));

        // Then
        assertNotNull(ccaController.getCCAList(ccaDataFilter));
    }

    @Test
    void shouldDeleteCCA() {
        // Given
        final var ccaDataFilters = List.of(
                getCcaDataFilter(SHIPMENT_PREFIX, "77803840", "CCA00001", COMPANY_CODE),
                getCcaDataFilter(SHIPMENT_PREFIX, "77803841", "CCA00002", COMPANY_CODE)
        );
        var filtersData = CcaDataFilterList.builder()
                .ccaDataFilters(ccaDataFilters)
                .ccaScreenId(LIST_CCA_SCREEN_ID)
                .build();

        // When
        doReturn(new BulkActionData(List.of(), List.of())).when(ccaService).deleteCCA(filtersData);

        // Then
        assertNotNull(ccaController.deleteCCA(filtersData));
    }

    @Test
    void shouldAuthorizeCCA() {
        // Given
        final var ccaDataFilters = List.of(
                getCcaDataFilter(SHIPMENT_PREFIX, "77803840", "CCA00001", COMPANY_CODE),
                getCcaDataFilter(SHIPMENT_PREFIX, "77803841", "CCA00002", COMPANY_CODE)
        );
        var filtersData = CcaDataFilterList.builder()
                .ccaDataFilters(ccaDataFilters)
                .ccaScreenId(LIST_CCA_SCREEN_ID)
                .build();

        // When
        doReturn(new BulkActionData(List.of(), List.of())).when(ccaService).authorizeCCA(filtersData);

        // Then
        assertNotNull(ccaController.authorizeCCA(filtersData));
    }

    @Test
    void shouldReRateCCA() {
        // Given
        final var reRateParam = "FRT";

        // When
        doReturn(new CCAMasterData()).when(ccaService).reRateCCA(any(String.class), any(CCAMasterData.class));

        // Then
        assertNotNull(ccaController.reRateCCA(reRateParam, new CCAMasterData()));
    }

    @Test
    void shouldGenerateCCAPrint() {
        // Given
        final var reportFilterModel = getReportFilterModel("CCA001", SHIPMENT_PREFIX, "77803840");

        // When
        doReturn(new CCAPrintModel()).when(ccaService).generateCCAPrint(any(CCAPrintFilterModel.class));

        // Then
        assertNotNull(ccaController.generateCCAPrint(reportFilterModel));
    }

    @Test
    void shouldGetAvailableReasonCodes() {
        // When
        doReturn(List.of(new AvailableReasonCodeData())).when(ccaService).getAvailableReasonCodes();

        // Then
        assertNotNull(ccaController.getAvailableReasonCodes());
    }

    @Test
    void shouldGetCCAListView() {
        // Given
        final var ccaListViewFilterData = new CCAListViewFilterData();
        ccaListViewFilterData.setSize(5);
        ccaListViewFilterData.setPage(1);

        // When
        doReturn(new CcaListViewPageInfo()).when(ccaService).getCCAListView(ccaListViewFilterData);

        // Then
        assertNotNull(ccaController.getCCAListView(ccaListViewFilterData));
    }

    @Test
    void shouldUpdateCcaMasterAttachments() {
        // Given
        final var attachmentsData = new AttachmentsData();

        // When
        doNothing().when(ccaService).updateCcaMasterAttachments(attachmentsData);

        // Then
        assertNotNull(ccaController.updateCcaMasterAttachments(attachmentsData));
    }

    @Test
    void shouldGetCCANumbers() {
        // Given
        var request = getCcaSelectFilter(false, "CCA", null);

        // When
        doReturn(new CcaNumbersPage(List.of())).when(ccaService).getCCANumbers(request);

        // Then
        assertNotNull(ccaController.getCCANumbers(request));
    }

    @Test
    void shouldGetCcaAssignees() {
        // Given
        var request = getCcaSelectFilter(false, "Mr.Freeze", null);

        // When
        doReturn(new CcaAssigneesPage(List.of())).when(ccaService).getCcaAssignees(request);

        // Then
        assertNotNull(ccaController.getCcaAssignees(request));
    }

    @Test
    void shouldUpdateCcaAssignee() throws CcaBusinessException {
        // Given
        var request = new CcaAssigneeData();

        // When
        doReturn(new CcaValidationData()).when(ccaService).updateCcaAssignee(request);

        // Then
        assertNotNull(ccaController.updateCcaAssignee(request));
    }

    @Test
    void shouldValidateCassIndicator() {
        // When
        doReturn(new CcaCassValidationDataResponse()).when(ccaService).validateCassIndicator(any(CcaCassValidationDataRequest.class));

        // Then
        assertNotNull(ccaController.validateCassIndicator(new CcaCassValidationDataRequest()));
    }

    @Test
    void shouldReCalculateCCATaxes() {
        // When
        doReturn(new CCAMasterData()).when(ccaService).reCalculateCCATaxes(any(CCAMasterData.class));

        // Then
        assertNotNull(ccaController.reCalculateCCATaxes(new CCAMasterData()));
    }

    @Test
    void shouldGetNetValues() {
        // When
        doReturn(new NetValuesData()).when(ccaService).getNetValues(any(CCAMasterData.class));

        // Then
        assertNotNull(ccaController.getNetValues(new CCAMasterData()));
    }
    @Test
    void shouldGetCCAReferenceNumber() {
        // When
        doReturn(new CCAMasterData()).when(ccaService).getCcaReferenceNumber(any(CCAMasterData.class));

        // Then
        assertNotNull(ccaController.getCcaReferenceNumber(new CCAMasterData()));
    }
}
