package com.ibsplc.neoicargo.stock.controller;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockAllocationModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.model.AWBDocumentValidationModel;
import com.ibsplc.neoicargo.stock.model.AirlineValidationModel;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.model.DocumentValidationModel;
import com.ibsplc.neoicargo.stock.model.MonitorStockModel;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.model.StockAgentModel;
import com.ibsplc.neoicargo.stock.model.StockAgentNeoModel;
import com.ibsplc.neoicargo.stock.model.StockAllocationModel;
import com.ibsplc.neoicargo.stock.model.StockDetailsModel;
import com.ibsplc.neoicargo.stock.model.StockHolderDetailsModel;
import com.ibsplc.neoicargo.stock.model.StockHolderLovModel;
import com.ibsplc.neoicargo.stock.model.StockHolderModel;
import com.ibsplc.neoicargo.stock.model.StockHolderPriorityModel;
import com.ibsplc.neoicargo.stock.model.StockRangeHistoryModel;
import com.ibsplc.neoicargo.stock.model.StockRangeModel;
import com.ibsplc.neoicargo.stock.model.StockRequestApproveModel;
import com.ibsplc.neoicargo.stock.model.StockRequestModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.DocumentFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.RangeFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockAgentFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockDetailsFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockHolderFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockHolderLovFilterModel;
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRangeFilterModel;
import com.ibsplc.neoicargo.stock.service.StockService;
import com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnitPlatform.class)
class StockControllerTest {

  @InjectMocks private StockController stockController;

  @Mock private StockService stockService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnNextAwbNumber() throws BusinessException {
    // When
    doReturn(new DocumentValidation())
        .when(stockService)
        .findNextDocumentNumber(any(DocumentFilter.class));

    // Then
    assertNotNull(stockController.findNextDocumentNumber(new DocumentFilter()));
  }

  @Test
  void shouldValidateStock() {
    // When
    doReturn(new DocumentValidation())
        .when(stockService)
        .validateStock(any(String.class), any(String.class), any(DocumentFilter.class));

    // Then
    assertNotNull(stockController.validateStock("134", "23323311", new DocumentFilter()));
  }

  @Test
  void shouldFindStockHolderDetails() {
    // When
    doReturn(new StockHolderModel()).when(stockService).findStockHolderDetails("AV", "DHLCDG");

    // Then
    assertNotNull(stockController.findStockHolderDetails("AV", "DHLCDG"));
  }

  @Test
  void shouldReturnStockHolderDetails() {
    StockHolderModel stockHolderModel = MockModelGenerator.getMockStockHolderModel("IBS", "HQ");

    // When
    doReturn(stockHolderModel).when(stockService).findStockHolderDetails("IBS", "HQ");

    // Then
    Assertions.assertEquals(stockHolderModel, stockController.findStockHolderDetails("IBS", "HQ"));
  }

  @Test
  void shouldReturnApproverCode() {
    // When
    doReturn("SAC").when(stockService).findApproverCode("IBS", "HQ", "DT", "DST");

    // Then
    Assertions.assertEquals("SAC", stockController.findApproverCode("IBS", "HQ", "DT", "DST"));
  }

  @Test
  void shouldCreateHistory() {
    // Given
    var model = getMockStockAllocationModel();

    // When
    doNothing().when(stockService).createHistory(model, MODE_USED);
    stockController.createHistory(model, MODE_USED);

    // Then
    verify(stockService).createHistory(model, MODE_USED);
  }

  @Test
  void shouldReturnMonitorStockHolderDetails() {
    // Given
    StockFilterModel stockFilterModel = MockModelGenerator.getMockStockFilterModel();
    MonitorStockModel monitorStockModel = MockModelGenerator.getMonitorStockModel(stockFilterModel);

    // When
    doReturn(monitorStockModel)
        .when(stockService)
        .findMonitoringStockHolderDetails(stockFilterModel);

    // Then
    Assertions.assertEquals(
        monitorStockModel, stockController.findMonitoringStockHolderDetails(stockFilterModel));
  }

  @Test
  void shouldReturnStockAllocationModel() {
    // When
    doReturn(new StockAllocationModel())
        .when(stockService)
        .allocateStock(any(StockAllocationModel.class));

    // Then
    Assertions.assertNotNull(stockController.allocateStock(new StockAllocationModel()));
  }

  @Test
  void shouldReturnStockHolderTypes() {
    // Given
    var stockHolderPriorityModels = List.of(new StockHolderPriorityModel());

    // When
    doReturn(stockHolderPriorityModels).when(stockService).findStockHolderTypes("DNSG");

    // Then
    Assertions.assertEquals(
        stockHolderPriorityModels, stockController.findStockHolderTypes("DNSG"));
  }

  @Test
  void shouldReturnPriorities() {
    // Given
    var stockHolderPriorityModels = List.of(new StockHolderPriorityModel());
    var stockHolderCodes = List.of("HQ", "HQ1");

    // When
    doReturn(stockHolderPriorityModels).when(stockService).findPriorities("AV", stockHolderCodes);

    // Then
    Assertions.assertEquals(
        stockHolderPriorityModels, stockController.findPriorities("AV", stockHolderCodes));
  }

  @Test
  void shouldNotThrowExceptionWhenValidateStockHolders() throws StockBusinessException {
    // Given
    var stockHolderCodes = List.of("HQ");

    // When
    doNothing().when(stockService).validateStockHolders("AV", stockHolderCodes);

    // Then
    assertDoesNotThrow(() -> stockController.validateStockHolders("AV", stockHolderCodes));
  }

  @Test
  void shouldThrowExceptionWhenValidateStockHolders() throws StockBusinessException {
    // Given
    var stockHolderCodes = List.of("HQ", "NONAME");
    var invalidStockHolders = List.of("NONAME");

    ErrorVO errorVo =
        constructErrorVO(
            NEO_STOCK_011.getErrorCode(),
            NEO_STOCK_011.getErrorMessage(),
            ErrorType.ERROR,
            new String[] {invalidStockHolders.toString()});

    // When
    doThrow(new StockBusinessException(errorVo))
        .when(stockService)
        .validateStockHolders("AV", stockHolderCodes);

    // Then
    StockBusinessException exception =
        assertThrows(
            StockBusinessException.class,
            () -> stockController.validateStockHolders("AV", stockHolderCodes));
    assertThat(NEO_STOCK_011.getErrorCode()).isEqualTo(exception.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldValidateStockHolderTypeCode() throws BusinessException {
    // Given
    var model = new StockRequestModel();

    // When
    doNothing().when(stockService).findStockHolderTypeCode(any(StockRequestModel.class));
    stockController.validateStockHolderTypeCode(model);

    // Then
    verify(stockService).findStockHolderTypeCode(model);
  }

  @Test
  void ShouldValidateStockHolderTypeCodeWithException() throws BusinessException {
    // Given
    var model = new StockRequestModel();

    // When
    doNothing().when(stockService).findStockHolderTypeCode(any(StockRequestModel.class));
    stockController.validateStockHolderTypeCode(model);

    // Then
    verify(stockService).findStockHolderTypeCode(model);
  }

  @Test
  void shouldDeleteStock() throws BusinessException {
    // When
    doNothing().when(stockService).deleteStock(anyList());
    stockController.deleteStock(List.of(new RangeModel()));

    // Then
    verify(stockService).deleteStock(anyList());
  }

  @Test
  void shouldReturnMonitorStockListViewPageInfo() {
    // Given
    var pageNumber = 1;
    var stockFilterModel = MockModelGenerator.getMockStockFilterModel();
    var stockRequestModels = List.of(getMockStockRequestModel());
    var monitorStockListViewPageInfo =
        (Page<StockRequestModel>) getMockPage(stockRequestModels, 1, 2, 1);

    // When
    doReturn(monitorStockListViewPageInfo).when(stockService).monitorStock(stockFilterModel);

    // Then
    assertThat(pageNumber)
        .isEqualTo(stockController.monitorStock(stockFilterModel).getPageNumber());
  }

  @Test
  void shouldReturnRangeModelList() {
    // Given
    var companyCode = "AV";
    var model = new RangeModel();
    model.setCompanyCode(companyCode);

    // When
    doReturn(List.of(model)).when(stockService).findRanges(any(RangeFilterModel.class));

    // Then
    assertThat(
            stockController.findRanges(new RangeFilterModel()).stream()
                .anyMatch(e -> e.getCompanyCode().equals(companyCode)))
        .isTrue();
  }

  @Test
  void shouldSaveStockHolderDetails() {
    // When
    doNothing().when(stockService).saveStockHolderDetails(any(StockHolderModel.class));
    stockController.saveStockHolderDetails(new StockHolderModel());

    // Then
    verify(stockService).saveStockHolderDetails(any(StockHolderModel.class));
  }

  @Test
  void shouldReturnViewRange() {
    // Given
    var model = new StockRangeModel();

    // When
    doReturn(model).when(stockService).viewRange(any(StockFilterModel.class));
    stockController.viewRange(new StockFilterModel());

    // Then
    verify(stockService).viewRange(any(StockFilterModel.class));
  }

  @Test
  void shouldFindStockRequests() {
    // Given
    var model = getMockStockRequestFilterModel();
    var stockRequestModels = List.of(getMockStockRequestModel());
    var expected = (Page<StockRequestModel>) getMockPage(stockRequestModels, 1, 2, 1);

    // When
    doReturn(expected).when(stockService).findStockRequests(1, model);
    Page<StockRequestModel> actual = stockController.findStockRequests(model, 1);

    // Then
    verify(stockService).findStockRequests(1, model);
    assertThat(actual.getPageNumber()).isEqualTo(expected.getPageNumber());
    assertThat(actual.getTotalRecordCount()).isEqualTo(expected.getTotalRecordCount());
  }

  @Test
  void shouldNotFindStockRequests() {
    // Given
    var model = getMockStockRequestFilterModel();
    var expected = (Page<StockRequestModel>) getMockPage(new ArrayList<>(), 1, 2, 1);

    // When
    doReturn(expected).when(stockService).findStockRequests(1, model);
    Page<StockRequestModel> actual = stockController.findStockRequests(model, 1);

    // Then
    verify(stockService).findStockRequests(1, model);
    assertThat(actual.getPageNumber()).isEqualTo(expected.getPageNumber());
    assertThat(actual.getTotalRecordCount()).isEqualTo(expected.getTotalRecordCount());
  }

  @Test
  void shouldReturnPageViewOfStockHolderLovModel() {
    // Given
    var page = getMockPage(List.of(new StockHolderLovModel()), 1, 1, 1);

    // When
    doReturn(page).when(stockService).findStockHolderLov(any(StockHolderLovFilterModel.class));
    var result = stockController.findStockHolderLov(new StockHolderLovFilterModel(), 1);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockService).findStockHolderLov(any(StockHolderLovFilterModel.class));
  }

  @Test
  void shouldRejectStockRequests() {
    // Given
    var models = List.of(new StockRequestModel());

    // When
    doNothing().when(stockService).rejectStockRequests(anyList());
    stockController.rejectStockRequests(models);

    // Then
    verify(stockService).rejectStockRequests(models);
  }

  @Test
  void shouldReturnPageOfAvailableRanges() {
    // Given
    var page = getMockPage(List.of(new RangeModel()), 1, 1, 1);
    var filter = getMockStockFilterModel();

    // When
    doReturn(page).when(stockService).findAvailableRanges(any(StockFilterModel.class));
    var result = stockController.findAvailableRanges(filter);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockService).findAvailableRanges(any(StockFilterModel.class));
  }

  @Test
  void shouldApproveStockRequests() throws BusinessException {
    // Given
    var model = new StockRequestApproveModel();

    // When
    doNothing().when(stockService).approveStockRequests(any(StockRequestApproveModel.class));
    stockController.approveStockRequests(model);

    // Then
    verify(stockService).approveStockRequests(model);
  }

  @Test
  void shouldReturnRequestRefNumber() throws BusinessException {
    // Given
    var model = new StockRequestModel();

    // When
    doReturn("123").when(stockService).saveStockRequestDetails(any(StockRequestModel.class));
    stockController.saveStockRequestDetails(model);

    // Then
    verify(stockService).saveStockRequestDetails(model);
  }

  @Test
  void shouldPassWhenStockExists() throws BusinessException {
    final var companyCode = "AV";
    final var stockholderCode = "HQ";
    final var docType = "AWB";
    final var docSubType = "S";

    doNothing().when(stockService).checkStock(companyCode, stockholderCode, docType, docSubType);

    assertDoesNotThrow(
        () -> stockController.checkStock(companyCode, stockholderCode, docType, docSubType));

    verify(stockService).checkStock(companyCode, stockholderCode, docType, docSubType);
  }

  @Test
  void shouldSaveStockAgentMappings() throws BusinessException {
    var models = List.of(new StockAgentModel());

    // When
    doNothing().when(stockService).saveStockAgentMappings(models);

    // Then
    assertDoesNotThrow(() -> stockController.saveStockAgentMappings(models));
    verify(stockService).saveStockAgentMappings(models);
  }

  @Test
  void shouldFindStockAgentMappings() {
    var page = getMockPage(List.of(new StockAgentModel()), 1, 1, 1);
    var model = new StockAgentFilterModel();

    // When
    doReturn(page).when(stockService).findStockAgentMappings(model);
    var result = stockController.findStockAgentMappings(model);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockService).findStockAgentMappings(model);
  }

  @Test
  void shouldFindStockRequestDetails() {
    // Given
    var expected = getMockStockRequestModel();
    var filterModel = getMockStockRequestFilterModel();

    // When
    doReturn(expected).when(stockService).findStockRequestDetails(filterModel);

    // Then
    var actual = stockController.findStockRequestDetails(filterModel);
    assertThat(actual).isEqualTo(expected);
    verify(stockService).findStockRequestDetails(filterModel);
  }

  @Test
  void shouldReturnTotalNoOfDocuments() {
    // When
    doReturn(2).when(stockService).findTotalNoOfDocuments(any(StockFilterModel.class));
    stockController.findTotalNoOfDocuments(new StockFilterModel());

    // Then
    verify(stockService).findTotalNoOfDocuments(any(StockFilterModel.class));
  }

  @Test
  void shouldValidateStockForVoiding() {
    // Given
    var model = new BlacklistStockModel();

    // When
    doReturn(model).when(stockService).validateStockForVoiding(model);
    stockController.validateStockForVoiding(model);

    // Then
    verify(stockService).validateStockForVoiding(model);
  }

  @Test
  void shouldCancelStockRequest() throws StockBusinessException {
    // When
    doNothing().when(stockService).cancelStockRequest(any(StockRequestModel.class));
    stockController.cancelStockRequest(new StockRequestModel());

    // Then
    verify(stockService).cancelStockRequest(any(StockRequestModel.class));
  }

  @Test
  void shouldReturnPageOfBlacklistedStock() {
    // Given
    var expected = getMockPage(List.of(new StockFilterModel()), 1, DEFAULT_PAGE_SIZE, 1);
    var stockFilterModel = new StockFilterModel();

    // When
    doReturn(expected).when(stockService).findBlacklistedStock(stockFilterModel, 1);
    var actual = stockController.findBlacklistedStock(stockFilterModel, 1);

    // Then
    assertThat(actual).isEqualTo(expected);
    verify(stockService).findBlacklistedStock(stockFilterModel, 1);
  }

  @Test
  void shouldFindDocumentDetails() {
    // Given
    var model = new StockRequestModel();
    var companyCode = "AV";
    var airlineIdentifier = 1134;
    var documentNumber = "1234123";

    // When
    doReturn(model)
        .when(stockService)
        .findDocumentDetails(companyCode, airlineIdentifier, documentNumber);
    stockController.findDocumentDetails(companyCode, airlineIdentifier, documentNumber);

    // Then
    verify(stockService).findDocumentDetails(companyCode, airlineIdentifier, documentNumber);
  }

  @Test
  void shouldValidateNumericCode() throws BusinessException {
    doReturn(new AirlineValidationModel()).when(stockService).validateNumericCode("IBS", "123");

    var actual = stockController.validateNumericCode("IBS", "123");

    assertThat(actual).isNotNull();
    verify(stockService).validateNumericCode("IBS", "123");
  }

  @Test
  void shouldCheckForBlacklistedDocument() {
    doReturn(true).when(stockService).checkForBlacklistedDocument("IBS", "AWB", "9000000");

    var actual = stockController.checkForBlacklistedDocument("IBS", "AWB", "9000000");

    assertThat(actual).isTrue();
    verify(stockService).checkForBlacklistedDocument("IBS", "AWB", "9000000");
  }

  @Test
  void shouldFindStockRangeHistoryForPage() {
    // Given
    var filterModel = new StockRangeFilterModel();
    var page = getMockPage(List.of(new StockRangeHistoryModel()), 1, 1, 1);

    // When
    doReturn(page).when(stockService).findStockRangeHistoryForPage(filterModel);
    var result = stockController.findStockRangeHistoryForPage(filterModel);

    //
    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockService).findStockRangeHistoryForPage(filterModel);
  }

  @Test
  void shouldFindStockRangeHistory() {
    // Given
    var filterModel = new StockRangeFilterModel();
    var models = List.of(new StockRangeHistoryModel());

    // When
    doReturn(models).when(stockService).findStockRangeHistory(filterModel);
    var result = stockController.findStockRangeHistory(filterModel);

    //
    // Then
    assertThat(result.size()).isEqualTo(1);
    verify(stockService).findStockRangeHistory(filterModel);
  }

  @Test
  void shouldBlacklistStock() throws BusinessException {
    // When
    doNothing().when(stockService).blacklistStock(any(BlacklistStockModel.class));
    stockController.blacklistStock(new BlacklistStockModel());

    // Then
    verify(stockService).blacklistStock(any(BlacklistStockModel.class));
  }

  @Test
  void shouldCheckForStockDetails() {
    // Given
    var filterModel = new DocumentFilterModel();

    // When
    doReturn(false).when(stockService).isStockDetailsExists(filterModel);
    var result = stockController.isStockDetailsExists(filterModel);

    // Then
    verify(stockService).isStockDetailsExists(filterModel);
    assertThat(result).isFalse();
  }

  @Test
  void shouldValidateAgentForStockHolder() throws StockBusinessException {

    var documentValidationModel = new AWBDocumentValidationModel();
    // When
    doNothing()
        .when(stockService)
        .validateAgentForStockHolder(any(AWBDocumentValidationModel.class));
    stockController.validateAgentForStockHolder(documentValidationModel);

    // Then
    verify(stockService).validateAgentForStockHolder(documentValidationModel);
  }

  @Test
  void shouldFindAutoPopulateSubtype() throws StockBusinessException {
    var model = new DocumentFilterModel();
    // When
    doReturn("S").when(stockService).findAutoPopulateSubtype(any(DocumentFilterModel.class));
    stockController.findAutoPopulateSubtype(model);

    // Then
    verify(stockService).findAutoPopulateSubtype(any(DocumentFilterModel.class));
  }

  @Test
  void shouldFindStockHolders() {
    // Given
    var filterModel = new StockHolderFilterModel();
    var page = getMockPage(List.of(new StockHolderFilterModel()), 1, 1, 1);

    // When
    doReturn(page).when(stockService).findStockHolders(filterModel);
    var result = stockController.findStockHolders(filterModel);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockService).findStockHolders(filterModel);
  }

  @Test
  void shouldSaveStockUtilisation() {
    // Given
    var stockAllocationModel = new StockAllocationModel();
    var status = "B";

    // When
    doNothing()
        .when(stockService)
        .saveStockUtilisation(any(StockAllocationModel.class), anyString());
    stockController.saveStockUtilisation(stockAllocationModel, status);

    // Then
    verify(stockService).saveStockUtilisation(stockAllocationModel, status);
  }

  @Test
  void shouldFindCustomerStockDetails() throws StockBusinessException {
    var model = new StockDetailsModel();
    var filter = new StockDetailsFilterModel();
    // When
    doReturn(model).when(stockService).findCustomerStockDetails(any(StockDetailsFilterModel.class));
    stockController.findCustomerStockDetails(filter);

    // Then
    verify(stockService).findCustomerStockDetails(any(StockDetailsFilterModel.class));
  }

  @Test
  void shouldReturnDocumentToStock() {
    var model = new StockAllocationModel();
    // When
    doNothing().when(stockService).returnDocumentToStock(any(StockAllocationModel.class));
    stockController.returnDocumentToStock(model);

    // Then
    verify(stockService).returnDocumentToStock(any(StockAllocationModel.class));
  }

  @Test
  void shouldFindAWBStockDetailsForPrint() throws StockBusinessException {
    // Given
    var stockFilterModel = new StockFilterModel();
    Collection<RangeModel> collection = new ArrayList<>();

    // When
    doReturn(collection)
        .when(stockService)
        .findAWBStockDetailsForPrint(any(StockFilterModel.class));
    var result = stockController.findAWBStockDetailsForPrint(stockFilterModel);

    verify(stockService).findAWBStockDetailsForPrint(stockFilterModel);
  }

  @Test
  void shouldValidateDocument() throws BusinessException {
    // Given
    var documentFilterModel = new DocumentFilterModel();

    // When
    doReturn(new DocumentValidationModel())
        .when(stockService)
        .validateDocument(any(DocumentFilterModel.class));
    stockController.validateDocument(documentFilterModel);

    // Then
    verify(stockService).validateDocument(documentFilterModel);
  }

  @Test
  void shouldDeleteStockHolder() throws BusinessException {
    // Given
    var stockHolderDetailsModel = new StockHolderDetailsModel();

    // When
    doNothing().when(stockService).deleteStockHolder(any(StockHolderDetailsModel.class));
    stockController.deleteStockHolder(stockHolderDetailsModel);

    // Then
    verify(stockService).deleteStockHolder(stockHolderDetailsModel);
  }

  @Test
  void shouldGetStockAgentMappings() {
    // Given
    var stockAgentFilterModel = new StockAgentFilterModel();

    // When
    doReturn(new ArrayList<StockAgentNeoModel>())
        .when(stockService)
        .getStockAgentMappings(any(StockAgentFilterModel.class));
    stockController.getStockAgentMappings(stockAgentFilterModel);

    // Then
    verify(stockService).getStockAgentMappings(stockAgentFilterModel);
  }

  @Test
  void shouldDeleteDocumentFromStock() throws BusinessException {
    var model = new DocumentFilterModel();
    // When
    doNothing().when(stockService).deleteDocumentFromStock(any(DocumentFilterModel.class));
    stockController.deleteDocumentFromStock(model);

    // Then
    verify(stockService).deleteDocumentFromStock(any(DocumentFilterModel.class));
  }
}
