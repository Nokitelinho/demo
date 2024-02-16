package com.ibsplc.neoicargo.cca.service;

import com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils;
import com.ibsplc.neoicargo.cca.component.CcaComponent;
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
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.qualityaudit.events.AwbVoidedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAFilterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaSelectFilter;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getRelatedCCAData;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getReportFilterModel;
import static com.ibsplc.neoicargo.cca.constants.CcaScreenIdConstants.LIST_CCA_SCREEN_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaServiceImplTest {

    @Mock
    private CcaComponent ccaComponent;

    @InjectMocks
    private CcaServiceImpl ccaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetCCADetails() {
        // Given
        final var ccaDataFilter = new CcaDataFilter();
        ccaDataFilter.setShipmentPrefix("134");
        ccaDataFilter.setMasterDocumentNumber("87696921");

        // When
        doReturn(new CCAMasterData()).when(ccaComponent).getCCADetails(any(CcaDataFilter.class));

        // Then
        assertNotNull(ccaService.getCCADetails(ccaDataFilter));
    }

    @Test
    void shouldSaveCCA() {
        // When
        doReturn(new CcaValidationData()).when(ccaComponent).saveCCA(any(CCAMasterData.class));

        // Then
        assertNotNull(ccaService.saveCCA(new CCAMasterData()));
    }

    @Test
    void shouldSaveAutoCCA() {
        // Given
        final var ccaMasterVO = new CCAMasterVO();

        // When
        doReturn(new CcaValidationData()).when(ccaComponent).saveAutoCCA(ccaMasterVO);

        // Then
        assertNotNull(ccaService.saveAutoCCA(ccaMasterVO));
    }

    @Test
    void shouldUpdateExistingCCAForAwbVoidedEvent() {
        // Then
        assertDoesNotThrow(() -> ccaService.updateExistingCCAForAwbVoidedEvent(new AwbVoidedEvent()));
    }

    @Test
    void shouldGetRelatedCCA() {
        // Given
        final var relatedCCA = getRelatedCCAData("87696921", SHIPMENT_PREFIX, "CCA00001");

        // When
        doReturn(List.of(relatedCCA)).when(ccaComponent).getRelatedCCA(any(String.class), any(String.class));
        final var relatedCCADataReturn = ccaService.getRelatedCCA("134", "87696921");

        // Then
        assertEquals("CCA00001", relatedCCADataReturn.get(0).getCcaReferenceNumber());
    }

    @Test
    void shouldGetCCAList() {
        // Given
        final var ccaDataFilter = new CcaDataFilter();
        ccaDataFilter.setCcaReferenceNumber("CCAA");

        // When
        doReturn(new ArrayList<CCAMasterData>()).when(ccaComponent).getCCAList(any(CcaDataFilter.class));

        // Then
        assertNotNull(ccaService.getCCAList(ccaDataFilter));
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
        doReturn(new BulkActionData(List.of(), List.of())).when(ccaComponent).deleteCCA(any());

        // Then
        assertNotNull(ccaService.deleteCCA(filtersData));
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
        doReturn(new BulkActionData(List.of(), List.of())).when(ccaComponent).authorizeCCA(any());

        // Then
        assertNotNull(ccaService.authorizeCCA(filtersData));
    }

    @Test
    void shouldReRateCCA() {
        // Given
        final var reRateParam = "FRT";

        // When
        doReturn(new CCAMasterData()).when(ccaComponent).reRateCCA(any(String.class), any(CCAMasterData.class));

        // Then
        assertNotNull(ccaService.reRateCCA(reRateParam, new CCAMasterData()));
    }

    @Test
    void shouldGenerateCCAPrint() {
        // Given
        final var reportFilterModel = getReportFilterModel("CCA001", SHIPMENT_PREFIX, "77803840");

        // When
        doReturn(new CCAPrintModel()).when(ccaComponent).generateCCAPrint(any(CCAPrintFilterModel.class));

        // Then
        assertNotNull(ccaService.generateCCAPrint(reportFilterModel));
    }

    @Test
    void shouldUpdateCcaStatusInvoiced() {
        // Given
        final var ccaFilterVO = getCCAFilterVO(SHIPMENT_PREFIX, "23323311", "CCA00001");
        ccaFilterVO.setAwbIndicator("AWB");
        ccaFilterVO.setExportBillingStatus("E");
        ccaFilterVO.setImportBillingStatus("I");
        ccaFilterVO.setBillingStatus("INV");

        // When
        doNothing().when(ccaComponent).updateCcaStatusInvoiced(any(CCAFilterVO.class));

        // Then
        assertDoesNotThrow(() -> ccaService.updateCcaStatusInvoiced(ccaFilterVO));
    }

    @Test
    void shouldGetAvailableReasonCodes() {
        // When
        doReturn(List.of(new AvailableReasonCodeData())).when(ccaComponent).getAvailableReasonCodes();

        // Then
        assertDoesNotThrow(() -> ccaService.getAvailableReasonCodes());
    }

    @Test
    void shouldGetCCAListView() {
        // When
        final var ccaListViewFilterData = new CCAListViewFilterData();

        // When
        doReturn(new CcaListViewPageInfo()).when(ccaComponent).getCCAListView(ccaListViewFilterData);

        // Then
        assertDoesNotThrow(() -> ccaService.getCCAListView(ccaListViewFilterData));
    }

    @Test
    void shouldUpdateCcaMasterAttachments() {
        // Given
        final var attachmentsData = new AttachmentsData();

        // When
        doNothing().when(ccaComponent).updateCcaMasterAttachments(attachmentsData);

        // Then
        assertDoesNotThrow(() -> ccaService.updateCcaMasterAttachments(attachmentsData));
    }

    @Test
    void shouldGetCCANumbers() {
        // Given
        var request = getCcaSelectFilter(false, "CCA", null);

        // When
        doReturn(new CcaNumbersPage(List.of())).when(ccaComponent).getCCANumbers(request);

        // Then
        assertDoesNotThrow(() -> ccaService.getCCANumbers(request));
    }

    @Test
    void shouldGetCcaAssignees() {
        // Given
        var request = getCcaSelectFilter(false, "Mr.Freeze", null);

        // When
        doReturn(new CcaAssigneesPage(List.of())).when(ccaComponent).getCcaAssignees(request);

        // Then
        assertDoesNotThrow(() -> ccaService.getCcaAssignees(request));
    }

    @Test
    void shouldUpdateCcaAssignee() throws CcaBusinessException {
        // Given
        var request = new CcaAssigneeData();

        // When
        doReturn(new CcaValidationData()).when(ccaComponent).updateCcaAssignee(request);

        // Then
        assertDoesNotThrow(() -> ccaService.updateCcaAssignee(request));
    }

    @Test
    void shouldValidateCassIndicator() {
        // When
        doReturn(new CcaCassValidationDataResponse()).when(ccaComponent).validateCassIndicator(any(CcaCassValidationDataRequest.class));

        // Then
        assertNotNull(ccaService.validateCassIndicator(new CcaCassValidationDataRequest()));
    }

    @Test
    void shouldGetNetValues() {
        // When
        doReturn(new NetValuesData()).when(ccaComponent).getNetValues(any(CCAMasterData.class));

        // Then
        assertNotNull(ccaService.getNetValues(new CCAMasterData()));
    }

}
