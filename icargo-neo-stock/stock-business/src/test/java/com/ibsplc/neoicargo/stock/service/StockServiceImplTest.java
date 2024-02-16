package com.ibsplc.neoicargo.stock.service;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockAllocationModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockHolderModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.stock.component.StockComponent;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.MonitorStockHolderDetailsMapper;
import com.ibsplc.neoicargo.stock.model.AWBDocumentValidationModel;
import com.ibsplc.neoicargo.stock.model.AirlineValidationModel;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.model.DocumentValidationModel;
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
import com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

@RunWith(JUnitPlatform.class)
class StockServiceImplTest {

  @InjectMocks private StockServiceImpl stockService;

  @Mock private StockComponent stockComponent;

  @Spy
  private final MonitorStockHolderDetailsMapper monitorStockHolderDetailsMapper =
      Mappers.getMapper(MonitorStockHolderDetailsMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnNextAwbNumber() throws BusinessException {
    // When
    doReturn(new DocumentValidation())
        .when(stockComponent)
        .findNextDocumentNumber(any(DocumentFilter.class));

    // Then
    assertNotNull(stockService.findNextDocumentNumber(new DocumentFilter()));
  }

  @Test
  void shouldValidateStock() {
    // When
    doReturn(new DocumentValidation())
        .when(stockComponent)
        .validateStock(any(String.class), any(String.class), any(DocumentFilter.class));

    // Then
    assertNotNull(stockService.validateStock("134", "23323311", new DocumentFilter()));
  }

  @Test
  void shouldFindStockHolderDetails() {
    // When
    doReturn(new StockHolderModel())
        .when(stockComponent)
        .findStockHolderDetails(any(String.class), any(String.class));

    // Then
    assertNotNull(stockService.findStockHolderDetails("134", "23323311"));
  }

  @Test
  void shouldFindApproverCode() {
    // When
    doReturn("SAC").when(stockComponent).findApproverCode("IBS", "APAC", "AWB", "S");

    // Then
    assertNotNull(stockService.findApproverCode("IBS", "APAC", "AWB", "S"));
  }

  @Test
  void shouldFindStockHolderTypes() {
    // When
    doReturn(List.of(new StockHolderPriorityModel()))
        .when(stockComponent)
        .findStockHolderTypes("DNSG");

    // Then
    assertNotNull(stockService.findStockHolderTypes("DNSG"));
  }

  @Test
  void shouldFindPriorities() {
    // Given
    var stockHolderCodes = List.of("HQ");

    // When
    doReturn(List.of(new StockHolderPriorityModel()))
        .when(stockComponent)
        .findPriorities("AV", stockHolderCodes);

    // Then
    assertNotNull(stockService.findPriorities("AV", stockHolderCodes));
    assertThat(stockService.findPriorities("AV", stockHolderCodes)).hasSize(1);
  }

  @Test
  void shouldFindMonitorStockHolderDetails() {
    // Given
    var stockFilterModel = MockModelGenerator.getMockStockFilterModel();

    // When
    doReturn(new MonitorStockVO())
        .when(stockComponent)
        .findMonitoringStockHolderDetails(any(StockFilterModel.class));

    // Then
    assertNotNull(stockService.findMonitoringStockHolderDetails(stockFilterModel));
  }

  @Test
  void shouldCreateHistory() {
    // Given
    var model = getMockStockAllocationModel();

    // When
    doNothing().when(stockComponent).createHistory(model, MODE_USED);
    stockService.createHistory(model, MODE_USED);

    // Then
    verify(stockComponent).createHistory(model, MODE_USED);
  }

  @Test
  void shouldAllocateStock() {
    // When
    doReturn(new StockAllocationModel())
        .when(stockComponent)
        .allocateStock(any(StockAllocationModel.class));

    // Then
    assertNotNull(stockService.allocateStock(new StockAllocationModel()));
  }

  @Test
  void shouldNotThrowExceptionWhenValidateStockHolders() throws StockBusinessException {
    // Given
    var stockHolderCodes = List.of("HQ");

    // When
    doNothing().when(stockComponent).validateStockHolders("AV", stockHolderCodes);

    // Then
    assertDoesNotThrow(() -> stockService.validateStockHolders("AV", stockHolderCodes));
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
        .when(stockComponent)
        .validateStockHolders("AV", stockHolderCodes);

    // Then
    StockBusinessException exception =
        assertThrows(
            StockBusinessException.class,
            () -> stockService.validateStockHolders("AV", stockHolderCodes));
    assertThat(NEO_STOCK_011.getErrorCode()).isEqualTo(exception.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldFindStockHolderTypeCode() throws BusinessException {
    // Given
    var model = new StockRequestModel();

    // When
    doNothing().when(stockComponent).findStockHolderTypeCode(any(StockRequestModel.class));
    stockService.findStockHolderTypeCode(model);

    // Then
    verify(stockComponent).findStockHolderTypeCode(model);
  }

  @Test
  void shouldDeleteStock() throws BusinessException {
    // When
    doNothing().when(stockComponent).deleteStock(anyList());
    stockService.deleteStock(List.of(new RangeModel()));

    // Then
    verify(stockComponent).deleteStock(anyList());
  }

  @Test
  void shouldReturnMonitorStockListViewPageInfo() {
    var stockRequestModels = List.of(getMockStockRequestModel());
    var expected = (Page<StockRequestModel>) getMockPage(stockRequestModels, 1, 2, 1);

    // When
    doReturn(expected).when(stockComponent).monitorStock(any(StockFilterModel.class));

    // Then
    assertNotNull(stockService.monitorStock(new StockFilterModel()));
  }

  @Test
  void shouldSaveStockHolderDetails() {
    // Given
    var model = getMockStockHolderModel("AV", "HQ");

    // When
    doNothing().when(stockComponent).saveStockHolderDetails(any(StockHolderModel.class));
    stockService.saveStockHolderDetails(model);

    // Then
    verify(stockComponent).saveStockHolderDetails(any(StockHolderModel.class));
  }

  @Test
  void shouldReturnViewRange() {
    // Given
    var model = new StockRangeModel();

    // When
    doReturn(model).when(stockComponent).viewRange(any(StockFilterModel.class));

    // Then
    assertNotNull(stockService.viewRange(new StockFilterModel()));
  }

  @Test
  void shouldFindStockRequests() {
    // Given
    var model = getMockStockRequestFilterModel();
    var stockRequestModels = List.of(getMockStockRequestModel());
    var expected = (Page<StockRequestModel>) getMockPage(stockRequestModels, 1, 2, 1);

    // When
    doReturn(expected).when(stockComponent).findStockRequests(1, model);
    var actual = stockService.findStockRequests(1, model);

    // Then
    verify(stockComponent).findStockRequests(1, model);
    assertThat(actual.getPageNumber()).isEqualTo(expected.getPageNumber());
    assertThat(actual.getTotalRecordCount()).isEqualTo(expected.getTotalRecordCount());
  }

  @Test
  void shouldNotFindStockRequests() {
    // Given
    var model = getMockStockRequestFilterModel();
    var expected = (Page<StockRequestModel>) getMockPage(new ArrayList<>(), 1, 2, 0);

    // When
    doReturn(expected).when(stockComponent).findStockRequests(1, model);
    var actual = stockService.findStockRequests(1, model);

    // Then
    verify(stockComponent).findStockRequests(1, model);
    assertThat(actual.getPageNumber()).isEqualTo(expected.getPageNumber());
    assertThat(actual.getTotalRecordCount()).isEqualTo(expected.getTotalRecordCount());
  }

  @Test
  void shouldReturnPageViewOfStockHolderLovModel() {
    // Given
    var page = getMockPage(List.of(new StockHolderLovModel()), 1, 1, 1);

    // When
    doReturn(page).when(stockComponent).findStockHolderLov(any(StockHolderLovFilterModel.class));
    var result = stockService.findStockHolderLov(new StockHolderLovFilterModel());

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockComponent).findStockHolderLov(any(StockHolderLovFilterModel.class));
  }

  @Test
  void shouldReturnRangeModelList() {
    // Given
    var companyCode = "AV";
    var model = new RangeModel();
    model.setCompanyCode(companyCode);

    // When
    doReturn(List.of(model)).when(stockComponent).findRanges(any(RangeFilterModel.class));

    // Then
    assertTrue(
        stockService.findRanges(new RangeFilterModel()).stream()
            .anyMatch(e -> e.getCompanyCode().equals(companyCode)));
  }

  @Test
  void shouldRejectStockRequests() {
    // Given
    var models = List.of(new StockRequestModel());

    // When
    doNothing().when(stockComponent).rejectStockRequests(models);
    stockService.rejectStockRequests(models);

    // Then
    verify(stockComponent).rejectStockRequests(models);
  }

  @Test
  void shouldReturnPageOfAvailableRanges() {
    // Given
    var page = getMockPage(List.of(new RangeModel()), 1, 1, 1);
    var filter = getMockStockFilterModel();

    // When
    doReturn(page).when(stockComponent).findAvailableRanges(any(StockFilterModel.class));
    var result = stockService.findAvailableRanges(filter);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockComponent).findAvailableRanges(any(StockFilterModel.class));
  }

  @Test
  void shouldApproveStockRequests() throws BusinessException {
    // Given
    var model = new StockRequestApproveModel();

    // When
    doNothing().when(stockComponent).approveStockRequests(any(StockRequestApproveModel.class));
    stockService.approveStockRequests(model);

    // Then
    verify(stockComponent).approveStockRequests(model);
  }

  @Test
  void shouldReturnRequestRefNumber() throws BusinessException {
    // Given
    var model = getMockStockRequestModel();
    var expected = "123";

    // When
    doReturn(expected).when(stockComponent).saveStockRequestDetails(model);

    // Then
    assertEquals(expected, stockService.saveStockRequestDetails(model));
    verify(stockComponent).saveStockRequestDetails(model);
  }

  @Test
  void shouldPassWhenStockExists() throws BusinessException {
    final var companyCode = "AV";
    final var stockholderCode = "HQ";
    final var docType = "AWB";
    final var docSubType = "S";

    doNothing().when(stockComponent).checkStock(companyCode, stockholderCode, docType, docSubType);

    assertDoesNotThrow(
        () -> stockService.checkStock(companyCode, stockholderCode, docType, docSubType));

    verify(stockComponent).checkStock(companyCode, stockholderCode, docType, docSubType);
  }

  @Test
  void shouldSaveStockAgentMappings() throws BusinessException {
    // Given
    var models = List.of(new StockAgentModel());

    // When
    doNothing().when(stockComponent).saveStockAgentMappings(models);

    // Then
    assertDoesNotThrow(() -> stockService.saveStockAgentMappings(models));

    verify(stockComponent).saveStockAgentMappings(models);
  }

  @Test
  void shouldFindStockAgentMappings() {
    // Given
    var page = getMockPage(List.of(new StockAgentModel()), 1, 1, 1);
    var model = new StockAgentFilterModel();

    // When
    doReturn(page).when(stockComponent).findStockAgentMappings(model);
    var result = stockService.findStockAgentMappings(model);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockComponent).findStockAgentMappings(model);
  }

  @Test
  void shouldFindStockRequestDetails() {
    // Given
    var expected = getMockStockRequestModel();
    var filterModel = getMockStockRequestFilterModel();

    // When
    doReturn(expected).when(stockComponent).findStockRequestDetails(filterModel);

    // Then
    var actual = stockService.findStockRequestDetails(filterModel);
    assertThat(actual).isEqualTo(expected);
    verify(stockComponent).findStockRequestDetails(filterModel);
  }

  @Test
  void shouldReturnTotalNoOfDocuments() {
    // When
    doReturn(2).when(stockComponent).findTotalNoOfDocuments(any(StockFilterModel.class));

    // Then
    assertEquals(2, stockService.findTotalNoOfDocuments(new StockFilterModel()));
    verify(stockComponent).findTotalNoOfDocuments(any(StockFilterModel.class));
  }

  @Test
  void shouldValidateStockForVoiding() {
    // Given
    var model = new BlacklistStockModel();

    // When
    doReturn(model).when(stockComponent).validateStockForVoiding(model);
    stockService.validateStockForVoiding(model);

    // Then
    verify(stockComponent).validateStockForVoiding(model);
  }

  @Test
  void shouldCancelStockRequest() throws StockBusinessException {
    // When
    doNothing().when(stockComponent).cancelStockRequest(any(StockRequestModel.class));
    stockService.cancelStockRequest(new StockRequestModel());

    // Then
    verify(stockComponent).cancelStockRequest(any(StockRequestModel.class));
  }

  @Test
  void shouldReturnPageOfBlacklistedStock() {
    // Given
    var expected = getMockPage(List.of(new StockFilterModel()), 1, DEFAULT_PAGE_SIZE, 1);
    var stockFilterModel = new StockFilterModel();

    // When
    doReturn(expected).when(stockComponent).findBlacklistedStock(stockFilterModel, 1);
    var actual = stockService.findBlacklistedStock(stockFilterModel, 1);

    // Then
    assertThat(actual).isEqualTo(expected);
    verify(stockComponent).findBlacklistedStock(stockFilterModel, 1);
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
        .when(stockComponent)
        .findDocumentDetails(companyCode, airlineIdentifier, documentNumber);
    stockService.findDocumentDetails(companyCode, airlineIdentifier, documentNumber);

    // Then
    verify(stockComponent).findDocumentDetails(companyCode, airlineIdentifier, documentNumber);
  }

  @Test
  void shouldValidateNumericCode() throws BusinessException {
    doReturn(new AirlineValidationModel()).when(stockComponent).validateNumericCode("IBS", "123");

    var actual = stockService.validateNumericCode("IBS", "123");

    assertThat(actual).isNotNull();
    verify(stockComponent).validateNumericCode("IBS", "123");
  }

  @Test
  void shouldCheckForBlacklistedDocument() {
    doReturn(true).when(stockComponent).checkForBlacklistedDocument("IBS", "AWB", "9000000");

    var actual = stockService.checkForBlacklistedDocument("IBS", "AWB", "9000000");

    assertThat(actual).isTrue();
    verify(stockComponent).checkForBlacklistedDocument("IBS", "AWB", "9000000");
  }

  @Test
  void shouldFindStockRangeHistoryForPage() {
    // Given
    var filterModel = new StockRangeFilterModel();
    var page = getMockPage(List.of(new StockRangeHistoryModel()), 1, 1, 1);

    // When
    doReturn(page).when(stockComponent).findStockRangeHistoryForPage(filterModel);
    var result = stockService.findStockRangeHistoryForPage(filterModel);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockComponent).findStockRangeHistoryForPage(filterModel);
  }

  @Test
  void shouldBlacklistStock() throws BusinessException {
    // When
    doNothing().when(stockComponent).blacklistStock(any(BlacklistStockModel.class));

    // Then
    stockService.blacklistStock(new BlacklistStockModel());
    verify(stockComponent).blacklistStock(any(BlacklistStockModel.class));
  }

  @Test
  void shouldCheckForStockDetails() {
    // Given
    var filterModel = new DocumentFilterModel();

    // When
    doReturn(false).when(stockComponent).isStockDetailsExists(filterModel);
    var result = stockService.isStockDetailsExists(filterModel);

    // Then
    verify(stockComponent).isStockDetailsExists(filterModel);
    assertThat(result).isFalse();
  }

  @Test
  void shouldValidateAgentForStockHolder() throws StockBusinessException {
    // Given
    var documentValidationModel = new AWBDocumentValidationModel();

    // When
    doNothing().when(stockComponent).validateAgentForStockHolder(documentValidationModel);
    stockService.validateAgentForStockHolder(documentValidationModel);

    // Then
    verify(stockComponent).validateAgentForStockHolder(documentValidationModel);
  }

  @Test
  void shouldFindAutoPopulateSubtype() throws StockBusinessException {
    // Given
    var model = new DocumentFilterModel();

    // When
    doReturn("S").when(stockComponent).findAutoPopulateSubtype(model);
    stockService.findAutoPopulateSubtype(model);

    // Then
    verify(stockComponent).findAutoPopulateSubtype(model);
  }

  @Test
  void shouldFindStockHolders() {
    // Given
    var filterModel = new StockHolderFilterModel();
    var page = getMockPage(List.of(new StockHolderFilterModel()), 1, 1, 1);

    // When
    doReturn(page).when(stockComponent).findStockHolders(filterModel);
    var result = stockService.findStockHolders(filterModel);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(stockComponent).findStockHolders(filterModel);
  }

  @Test
  void shouldSaveStockUtilisation() {
    // Given
    var stockAllocationModel = new StockAllocationModel();
    var status = "B";

    // When
    doNothing().when(stockComponent).saveStockUtilisation(stockAllocationModel, status);
    stockService.saveStockUtilisation(stockAllocationModel, status);

    // Then
    verify(stockComponent).saveStockUtilisation(stockAllocationModel, status);
  }

  @Test
  void shouldFindCustomerStockDetails() throws StockBusinessException {
    // Given
    var model = new StockDetailsModel();
    var filter = new StockDetailsFilterModel();

    // When
    doReturn(model).when(stockComponent).findCustomerStockDetails(filter);
    stockService.findCustomerStockDetails(filter);

    // Then
    verify(stockComponent).findCustomerStockDetails(filter);
  }

  @Test
  void shouldReturnDocumentToStock() {
    // Given
    var model = new StockAllocationModel();

    // When
    doNothing().when(stockComponent).returnDocumentToStock(model);
    stockService.returnDocumentToStock(model);

    // Then
    verify(stockComponent).returnDocumentToStock(model);
  }

  @Test
  void shouldFindAWBStockDetailsForPrint() throws StockBusinessException {
    // Given
    var stockFilterModel = new StockFilterModel();
    Collection<RangeModel> collection = new ArrayList<>();
    // When
    doReturn(collection).when(stockComponent).findAWBStockDetailsForPrint(stockFilterModel);
    stockService.findAWBStockDetailsForPrint(stockFilterModel);
    // Then
    verify(stockComponent).findAWBStockDetailsForPrint(stockFilterModel);
  }

  @Test
  void shouldValidateDocument() throws BusinessException {
    // Given
    var documentFilterModel = new DocumentFilterModel();

    // When
    doReturn(new DocumentValidationModel())
        .when(stockComponent)
        .validateDocument(any(DocumentFilterModel.class));
    stockService.validateDocument(documentFilterModel);

    // Then
    verify(stockComponent).validateDocument(documentFilterModel);
  }

  @Test
  void shouldDeleteStockHolder() throws BusinessException {
    // Given
    var stockHolderDetailsModel = new StockHolderDetailsModel();

    // When
    doNothing().when(stockComponent).deleteStockHolder(any(StockHolderDetailsModel.class));
    stockService.deleteStockHolder(stockHolderDetailsModel);

    // Then
    verify(stockComponent).deleteStockHolder(stockHolderDetailsModel);
  }

  @Test
  void shouldGetStockAgentMappings() {
    // Given
    var stockAgentFilterModel = new StockAgentFilterModel();

    // When
    doReturn(new ArrayList<StockAgentNeoModel>())
        .when(stockComponent)
        .getStockAgentMappings(any(StockAgentFilterModel.class));
    stockService.getStockAgentMappings(stockAgentFilterModel);

    // Then
    verify(stockComponent).getStockAgentMappings(stockAgentFilterModel);
  }

  @Test
  void shouldDeleteDocumentFromStock() throws BusinessException {
    // Given
    var model = new DocumentFilterModel();

    // When
    doNothing().when(stockComponent).deleteDocumentFromStock(any(DocumentFilterModel.class));
    stockService.deleteDocumentFromStock(model);

    // Then
    verify(stockComponent).deleteDocumentFromStock(any(DocumentFilterModel.class));
  }
}
