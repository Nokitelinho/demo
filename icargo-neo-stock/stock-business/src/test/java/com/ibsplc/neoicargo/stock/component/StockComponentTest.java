package com.ibsplc.neoicargo.stock.component;

import static com.ibsplc.neoicargo.stock.exception.StockErrors.NEO_STOCK_011;
import static com.ibsplc.neoicargo.stock.exception.StockErrors.constructErrorVO;
import static com.ibsplc.neoicargo.stock.model.StockAllocationStatus.MODE_USED;
import static com.ibsplc.neoicargo.stock.util.StockConstant.DEFAULT_PAGE_SIZE;
import static com.ibsplc.neoicargo.stock.util.mock.MockEntityGenerator.getMockPage;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockBlacklistStockModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockRangeModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockAllocationModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockHolderModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockHolderPriorityModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestFilterModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockModelGenerator.getMockStockRequestModel;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockBlacklistStockVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockAllocationVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockHolderPriorityVO;
import static com.ibsplc.neoicargo.stock.util.mock.MockVOGenerator.getMockStockRequestVO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.stock.component.feature.allocatestock.AllocateStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.approvestockrequests.ApproveStockRequestsFeature;
import com.ibsplc.neoicargo.stock.component.feature.blackliststock.BlacklistStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.cancelstockrequest.CancelStockRequestFeature;
import com.ibsplc.neoicargo.stock.component.feature.checkforblacklisteddocument.CheckForBlacklistedDocumentFeature;
import com.ibsplc.neoicargo.stock.component.feature.checkforstockdetails.IsStockDetailsExistsFeature;
import com.ibsplc.neoicargo.stock.component.feature.checkstock.CheckStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.createhistory.CreateHistoryFeature;
import com.ibsplc.neoicargo.stock.component.feature.deletedocumentfromstock.DeleteDocumentFromStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.deletestock.DeleteStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.deletestockholder.DeleteStockHolderFeature;
import com.ibsplc.neoicargo.stock.component.feature.findapprovercode.FindApproverCodeFeature;
import com.ibsplc.neoicargo.stock.component.feature.findautopopulatesubtypefeature.FindAutoPopulateSubtypeFeature;
import com.ibsplc.neoicargo.stock.component.feature.findavailableranges.FindAvailableRangesFeature;
import com.ibsplc.neoicargo.stock.component.feature.findawbstockdetailsforprint.FindAWBStockDetailsForPrintFeature;
import com.ibsplc.neoicargo.stock.component.feature.findblacklistedstock.FindBlacklistedStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.findcustomerstockdetailsfeature.FindCustomerStockDetailsFeature;
import com.ibsplc.neoicargo.stock.component.feature.finddocumentdetails.FindDocumentDetailsFeature;
import com.ibsplc.neoicargo.stock.component.feature.findmonitoringstockholderdetails.FindMonitoringStockHolderDetailsFeature;
import com.ibsplc.neoicargo.stock.component.feature.findnextdocumentnumber.FindNextDocumentNumberFeature;
import com.ibsplc.neoicargo.stock.component.feature.findpriorities.FindPrioritiesFeature;
import com.ibsplc.neoicargo.stock.component.feature.findranges.FindRangesFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockagentmappings.FindStockAgentMappingsFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockholderdetails.FindStockHolderDetailsFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockholderlov.FindStockHolderLovFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockholders.FindStockHoldersFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockholdertypecodefeature.FindStockHolderTypeCodeFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockholdertypes.FindStockHolderTypesFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockrangehistory.FindStockRangeHistoryFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockrangehistoryforpage.FindStockRangeHistoryForPageFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockrequestdetails.FindStockRequestDetailsFeature;
import com.ibsplc.neoicargo.stock.component.feature.findstockrequests.FindStockRequestsFeature;
import com.ibsplc.neoicargo.stock.component.feature.findtotalnoofdocuments.FindTotalNoOfDocumentsFeature;
import com.ibsplc.neoicargo.stock.component.feature.getstockagentmappingsfeature.GetStockAgentMappingsFeature;
import com.ibsplc.neoicargo.stock.component.feature.monitorstock.MonitorStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.rejectstockrequests.RejectStockRequestsFeature;
import com.ibsplc.neoicargo.stock.component.feature.returndocumenttostock.ReturnDocumentToStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.savestockagentmappings.SaveStockAgentMappingsFeature;
import com.ibsplc.neoicargo.stock.component.feature.savestockholderdetails.SaveStockHolderDetailsFeature;
import com.ibsplc.neoicargo.stock.component.feature.savestockrequestdetails.SaveStockRequestDetailsFeature;
import com.ibsplc.neoicargo.stock.component.feature.savestockutilisation.SaveStockUtilisationFeature;
import com.ibsplc.neoicargo.stock.component.feature.validateagentforstockholder.ValidateAgentForStockHolderFeature;
import com.ibsplc.neoicargo.stock.component.feature.validatedocument.ValidateDocumentFeature;
import com.ibsplc.neoicargo.stock.component.feature.validatenumericcode.ValidateNumericCodeFeature;
import com.ibsplc.neoicargo.stock.component.feature.validatestock.ValidateStockFeature;
import com.ibsplc.neoicargo.stock.component.feature.validatestockforvoiding.ValidateStockForVoidingFeature;
import com.ibsplc.neoicargo.stock.component.feature.validatestockholders.ValidateStockHoldersFeature;
import com.ibsplc.neoicargo.stock.component.feature.viewrange.ViewRangeFeature;
import com.ibsplc.neoicargo.stock.exception.StockBusinessException;
import com.ibsplc.neoicargo.stock.mapper.AWBDocumentValidationMapper;
import com.ibsplc.neoicargo.stock.mapper.BlackListStockMapper;
import com.ibsplc.neoicargo.stock.mapper.CustomerStockDetailsMapper;
import com.ibsplc.neoicargo.stock.mapper.DocumentFilterMapper;
import com.ibsplc.neoicargo.stock.mapper.FindAutoPopulateSubtypeMapper;
import com.ibsplc.neoicargo.stock.mapper.RangeFilterMapper;
import com.ibsplc.neoicargo.stock.mapper.RangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockAgentFilterMapper;
import com.ibsplc.neoicargo.stock.mapper.StockAgentMapper;
import com.ibsplc.neoicargo.stock.mapper.StockAllocationMapper;
import com.ibsplc.neoicargo.stock.mapper.StockFilterMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderDetailsModelMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderFilterModelMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderLovFilterMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderMapper;
import com.ibsplc.neoicargo.stock.mapper.StockHolderPriorityMapper;
import com.ibsplc.neoicargo.stock.mapper.StockRangeFilterMapper;
import com.ibsplc.neoicargo.stock.mapper.StockRangeHistoryMapper;
import com.ibsplc.neoicargo.stock.mapper.StockRangeMapper;
import com.ibsplc.neoicargo.stock.mapper.StockRequestApproveMapper;
import com.ibsplc.neoicargo.stock.mapper.StockRequestMapper;
import com.ibsplc.neoicargo.stock.mapper.TransitStockMapper;
import com.ibsplc.neoicargo.stock.model.AWBDocumentValidationModel;
import com.ibsplc.neoicargo.stock.model.AirlineValidationModel;
import com.ibsplc.neoicargo.stock.model.BlacklistStockModel;
import com.ibsplc.neoicargo.stock.model.DocumentFilter;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.model.RangeModel;
import com.ibsplc.neoicargo.stock.model.StockAgentModel;
import com.ibsplc.neoicargo.stock.model.StockAgentNeoModel;
import com.ibsplc.neoicargo.stock.model.StockAllocationModel;
import com.ibsplc.neoicargo.stock.model.StockHolderDetailsModel;
import com.ibsplc.neoicargo.stock.model.StockHolderLovModel;
import com.ibsplc.neoicargo.stock.model.StockRangeHistoryModel;
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
import com.ibsplc.neoicargo.stock.vo.AWBDocumentValidationVO;
import com.ibsplc.neoicargo.stock.vo.BlacklistStockVO;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.StockDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderDetailsVO;
import com.ibsplc.neoicargo.stock.vo.StockHolderVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeHistoryVO;
import com.ibsplc.neoicargo.stock.vo.StockRangeVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestApproveVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestFilterVO;
import com.ibsplc.neoicargo.stock.vo.StockRequestVO;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockAgentFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockDetailsFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockHolderLovFilterVO;
import com.ibsplc.neoicargo.stock.vo.filter.StockRangeFilterVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(JUnitPlatform.class)
class StockComponentTest {

  @InjectMocks private StockComponent stockComponent;

  @Mock private FindNextDocumentNumberFeature findNextDocumentNumberFeature;
  @Mock private ValidateStockFeature validateStockFeature;
  @Mock private FindStockHolderDetailsFeature findStockHolderDetailsFeature;
  @Mock private FindApproverCodeFeature findApproverCodeFeature;
  @Mock private FindStockHolderTypesFeature findStockHolderTypesFeature;
  @Mock private FindPrioritiesFeature findPrioritiesFeature;
  @Mock private FindMonitoringStockHolderDetailsFeature findMonitoringStockHolderDetailsFeature;
  @Mock private AllocateStockFeature allocateStockFeature;
  @Mock private CreateHistoryFeature createHistoryFeature;
  @Mock private ValidateStockHoldersFeature validateStockHoldersFeature;
  @Mock private DeleteStockFeature deleteStockFeature;
  @Mock private FindStockHolderTypeCodeFeature findStockHolderTypeCodeFeature;
  @Mock private MonitorStockFeature monitorStockFeature;
  @Mock private SaveStockHolderDetailsFeature saveStockHolderDetailsFeature;
  @Mock private ViewRangeFeature viewRangeFeature;
  @Mock private FindStockRequestsFeature findStockRequestsFeature;
  @Mock private FindStockHolderLovFeature findStockHolderLovFeature;
  @Mock private FindRangesFeature findRangesFeature;
  @Mock private RejectStockRequestsFeature rejectStockRequestsFeature;
  @Mock private FindAvailableRangesFeature findAvailableRangesFeature;
  @Mock private ApproveStockRequestsFeature approveStockRequestsFeature;
  @Mock private SaveStockRequestDetailsFeature saveStockRequestDetailsFeature;
  @Mock private CheckStockFeature checkStockFeature;
  @Mock private SaveStockAgentMappingsFeature saveStockAgentMappingsFeature;
  @Mock private FindStockAgentMappingsFeature findStockAgentMappingsFeature;
  @Mock private FindStockRequestDetailsFeature findStockRequestDetailsFeature;
  @Mock private FindTotalNoOfDocumentsFeature findTotalNoOfDocumentsFeature;
  @Mock private ValidateStockForVoidingFeature validateStockForVoidingFeature;
  @Mock private CancelStockRequestFeature cancelStockRequestFeature;
  @Mock private FindBlacklistedStockFeature findBlacklistedStockFeature;
  @Mock private FindDocumentDetailsFeature findDocumentDetailsFeature;
  @Mock private ValidateNumericCodeFeature validateNumericCodeFeature;
  @Mock private CheckForBlacklistedDocumentFeature checkForBlacklistedDocumentFeature;
  @Mock private BlacklistStockFeature blacklistStockFeature;
  @Mock private FindStockRangeHistoryFeature findStockRangeHistoryFeature;
  @Mock private FindStockRangeHistoryForPageFeature findStockRangeHistoryForPageFeature;
  @Mock private IsStockDetailsExistsFeature checkForStockDetailsFeature;
  @Mock private ValidateAgentForStockHolderFeature validateAgentForStockHolderFeature;
  @Mock private FindAutoPopulateSubtypeFeature findAutoPopulateSubtypeFeature;
  @Mock private FindStockHoldersFeature findStockHoldersFeature;
  @Mock private SaveStockUtilisationFeature saveStockUtilisationFeature;
  @Mock private FindCustomerStockDetailsFeature findCustomerStockDetailsFeature;
  @Mock private ReturnDocumentToStockFeature returnDocumentToStockFeature;
  @Mock private FindAWBStockDetailsForPrintFeature findAWBStockDetailsForPrintFeature;
  @Mock private ValidateDocumentFeature validateDocumentFeature;
  @Mock private DeleteStockHolderFeature deleteStockHolderFeature;
  @Mock private GetStockAgentMappingsFeature getStockAgentMappingsFeature;
  @Mock private DeleteDocumentFromStockFeature deleteDocumentFromStockFeature;

  @Spy
  private StockAllocationMapper stockAllocationMapper =
      Mappers.getMapper(StockAllocationMapper.class);

  @Spy
  private final DocumentFilterMapper documentFilterMapper =
      Mappers.getMapper(DocumentFilterMapper.class);

  @Spy
  private final StockHolderPriorityMapper stockHolderPriorityMapper =
      Mappers.getMapper(StockHolderPriorityMapper.class);

  @Spy
  private final StockHolderMapper stockHolderMapper = Mappers.getMapper(StockHolderMapper.class);

  @Spy
  private final StockFilterMapper stockFilterMapper = Mappers.getMapper(StockFilterMapper.class);

  @Spy private RangeMapper rangeMapper = Mappers.getMapper(RangeMapper.class);

  @Spy
  private final StockRequestMapper stockRequestMapper = Mappers.getMapper(StockRequestMapper.class);

  @Spy private StockRangeMapper stockRangeMapper = Mappers.getMapper(StockRangeMapper.class);

  @Spy
  private final StockHolderLovFilterMapper stockHolderLovFilterMapper =
      Mappers.getMapper(StockHolderLovFilterMapper.class);

  @Spy
  private final RangeFilterMapper rangeFilterMapper = Mappers.getMapper(RangeFilterMapper.class);

  @Spy
  private final StockRequestApproveMapper stockRequestApproveMapper =
      Mappers.getMapper(StockRequestApproveMapper.class);

  @Spy private final StockAgentMapper stockAgentMapper = Mappers.getMapper(StockAgentMapper.class);

  @Spy
  private final StockAgentFilterMapper stockAgentFilterMapper =
      Mappers.getMapper(StockAgentFilterMapper.class);

  @Spy
  private final StockRangeHistoryMapper stockRangeHistoryMapper =
      Mappers.getMapper(StockRangeHistoryMapper.class);

  @Spy
  private final BlackListStockMapper blacklistStockMapper =
      Mappers.getMapper(BlackListStockMapper.class);

  @Spy
  private final StockRangeFilterMapper stockRangeFilterMapper =
      Mappers.getMapper(StockRangeFilterMapper.class);

  @Spy
  private final AWBDocumentValidationMapper stockControlDefaultsMapper =
      Mappers.getMapper(AWBDocumentValidationMapper.class);

  @Spy
  private final FindAutoPopulateSubtypeMapper findAutoPopulateSubtypeMapper =
      Mappers.getMapper(FindAutoPopulateSubtypeMapper.class);

  @Spy
  private final CustomerStockDetailsMapper customerStockDetailsMapper =
      Mappers.getMapper(CustomerStockDetailsMapper.class);

  @Mock private LocalDate localDate;

  @Spy
  private final StockHolderFilterModelMapper stockHolderFilterModelMapper =
      Mappers.getMapper(StockHolderFilterModelMapper.class);

  @Spy
  private final StockHolderDetailsModelMapper stockHolderDetailsModelMapper =
      Mappers.getMapper(StockHolderDetailsModelMapper.class);

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(
        stockAllocationMapper, "rangeMapper", Mappers.getMapper(RangeMapper.class));
    ReflectionTestUtils.setField(
        blacklistStockMapper, "transitStockMapper", Mappers.getMapper(TransitStockMapper.class));
  }

  @Test
  void shouldReturnNextAwbNumber() throws BusinessException {
    // When
    doReturn(new DocumentValidation())
        .when(findNextDocumentNumberFeature)
        .perform(any(DocumentFilterVO.class));

    // Then
    assertNotNull(stockComponent.findNextDocumentNumber(new DocumentFilter()));
  }

  @Test
  void shouldValidateStock() {
    // Given
    var documentValidation = new DocumentValidation();
    documentValidation.setStockHolderCode("DHLCDG");

    // When
    doReturn(documentValidation).when(validateStockFeature).execute(any(DocumentFilterVO.class));
    // Then
    Assertions.assertEquals(
        "DHLCDG",
        stockComponent.validateStock("134", "DHLCDG", new DocumentFilter()).getStockHolderCode());
  }

  @Test
  void shouldFindStockHolderDetails() {
    // Given
    var stockHolderVO = new StockHolderVO();
    stockHolderVO.setStockHolderCode("DHLCDG");

    // When
    doReturn(stockHolderVO)
        .when(findStockHolderDetailsFeature)
        .perform(any(String.class), any(String.class));

    // Then
    Assertions.assertEquals(
        "DHLCDG", stockComponent.findStockHolderDetails("134", "DHLCDG").getStockHolderCode());
  }

  @Test
  void shouldFindApproverCode() {
    String stockHolderVO = "SAC";

    // When
    doReturn(stockHolderVO).when(findApproverCodeFeature).perform("IBS", "HQ", "DT", "DST");

    // Then
    assertEquals("SAC", stockComponent.findApproverCode("IBS", "HQ", "DT", "DST"));
  }

  @Test
  void shouldfindMonitoringStockHolderDetails() {
    // Given
    var sthkCode = "STHK_CODE";
    var stockFilterModel = getMockStockFilterModel();
    var stockFilterVO = MockModelGenerator.getMockStockFilterVO();
    var monitorStockVO = new MonitorStockVO();
    monitorStockVO.setStockHolderCode(sthkCode);

    // When
    doReturn(monitorStockVO)
        .when(findMonitoringStockHolderDetailsFeature)
        .execute(any(StockFilterVO.class));

    doReturn(stockFilterVO).when(stockFilterMapper).mapModelToVo(any(StockFilterModel.class));

    // Then
    assertEquals(
        sthkCode,
        stockComponent.findMonitoringStockHolderDetails(stockFilterModel).getStockHolderCode());
  }

  @Test
  void shouldAllocateStock() {
    // Given
    var stockAllocationModel = new StockAllocationModel();
    stockAllocationModel.setStockHolderCode("DHLCDG");

    // When
    doReturn(stockAllocationModel).when(allocateStockFeature).execute(any(StockAllocationVO.class));
    // Then
    Assertions.assertEquals(
        "DHLCDG", stockComponent.allocateStock(new StockAllocationModel()).getStockHolderCode());
  }

  @Test
  void shouldCreateHistory() {
    // Given
    var model = getMockStockAllocationModel();
    var vo = getMockStockAllocationVO();

    // When
    doReturn(vo).when(stockAllocationMapper).mapModelToVo(model);
    doNothing().when(createHistoryFeature).perform(vo, MODE_USED);
    stockComponent.createHistory(model, MODE_USED);

    // Then
    verify(stockAllocationMapper).mapModelToVo(model);
    verify(createHistoryFeature).perform(vo, MODE_USED);
    assertThat(model.getCompanyCode()).isEqualTo(vo.getCompanyCode());
    assertThat(model.getAllocatedStock()).isEqualTo(vo.getAllocatedStock());
    assertThat(model.getDocumentType()).isEqualTo(vo.getDocumentType());
    assertThat(model.getDocumentSubType()).isEqualTo(vo.getDocumentSubType());
    assertThat(model.isAllocate()).isEqualTo(vo.isAllocate());
  }

  @Test
  void shouldReturnStockHolderTypes() {
    // Given
    var expected = List.of(getMockStockHolderPriorityModel());
    var stockHolderVOs = List.of(getMockStockHolderPriorityVO());

    // When
    doReturn(stockHolderVOs).when(findStockHolderTypesFeature).perform("DNSG");
    var actual = stockComponent.findStockHolderTypes("DNSG");

    // Then
    assertThat(actual).hasSize(1);
    assertThat(actual.get(0).getStockHolderType()).isEqualTo(expected.get(0).getStockHolderType());
    assertThat(actual.get(0).getCompanyCode()).isEqualTo(expected.get(0).getCompanyCode());
    assertThat(actual.get(0).getPriority()).isEqualTo(expected.get(0).getPriority());
  }

  @Test
  void shouldReturnPriorities() {
    // Given
    var expected = List.of(getMockStockHolderPriorityModel());
    var stockHolderVOs = List.of(getMockStockHolderPriorityVO());
    var stockHolderCodes = List.of("HQ");

    // When
    doReturn(stockHolderVOs).when(findPrioritiesFeature).perform("AV", stockHolderCodes);
    var actual = stockComponent.findPriorities("AV", stockHolderCodes);

    // Then
    assertThat(actual).hasSize(1);
    assertThat(actual.get(0).getStockHolderType()).isEqualTo(expected.get(0).getStockHolderType());
    assertThat(actual.get(0).getCompanyCode()).isEqualTo(expected.get(0).getCompanyCode());
    assertThat(actual.get(0).getPriority()).isEqualTo(expected.get(0).getPriority());
  }

  @Test
  void shouldNotThrowExceptionWhenValidateStockHolders() throws StockBusinessException {
    // Given
    var stockHolderCodes = List.of("HQ");

    // When
    doNothing().when(validateStockHoldersFeature).perform("AV", stockHolderCodes);

    // Then
    assertDoesNotThrow(() -> stockComponent.validateStockHolders("AV", stockHolderCodes));
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
        .when(validateStockHoldersFeature)
        .perform("AV", stockHolderCodes);

    // Then
    StockBusinessException exception =
        assertThrows(
            StockBusinessException.class,
            () -> stockComponent.validateStockHolders("AV", stockHolderCodes));
    assertThat(NEO_STOCK_011.getErrorCode()).isEqualTo(exception.getErrors().get(0).getErrorCode());
  }

  @Test
  void shouldDeleteStock() throws BusinessException {
    // Given
    var rangeModel = getMockRangeModel();

    // When
    doNothing().when(deleteStockFeature).perform(anyList());
    stockComponent.deleteStock(List.of(rangeModel));

    // Then
    verify(deleteStockFeature).perform(anyList());
  }

  @Test
  void shouldFindStockHolderTypeCode() throws BusinessException {
    // Given
    var model = new StockRequestModel();
    var vo = new StockRequestVO();

    // When
    doReturn(vo).when(stockRequestMapper).mapModelToVo(any(StockRequestModel.class));
    doNothing().when(findStockHolderTypeCodeFeature).perform(vo);
    stockComponent.findStockHolderTypeCode(model);

    // Then
    verify(findStockHolderTypeCodeFeature).perform(vo);
  }

  @Test
  void shouldReturnMonitorStockListViewPageInfo() {
    // Given
    int pageNumber = 1;
    var stockFilterModel = getMockStockFilterModel();
    var stockRequestModels = List.of(getMockStockRequestModel());
    var stockRequestVOs = List.of(getMockStockRequestVO());
    var monitorStockListViewPageInfo =
        (Page<StockRequestModel>) getMockPage(stockRequestModels, 1, 2, stockRequestVOs.size());

    // When
    doReturn(monitorStockListViewPageInfo)
        .when(monitorStockFeature)
        .execute(any(StockFilterVO.class));

    doReturn(new StockFilterVO()).when(stockFilterMapper).mapModelToVo(any(StockFilterModel.class));

    // Then
    assertEquals(pageNumber, stockComponent.monitorStock(stockFilterModel).getPageNumber());
  }

  @Test
  void shouldSaveStockHolderDetails() throws BusinessException {
    // Given
    var model = getMockStockHolderModel("AV", "HQ");

    // When
    doNothing().when(saveStockHolderDetailsFeature).perform(any(StockHolderVO.class));
    stockComponent.saveStockHolderDetails(model);

    // Then
    verify(saveStockHolderDetailsFeature).execute(any(StockHolderVO.class));
  }

  @Test
  void shouldReturnViewRange() {
    // Given
    var model = getMockStockFilterModel();

    // When
    doReturn(new StockRangeVO()).when(viewRangeFeature).perform(any(StockFilterVO.class));
    stockComponent.viewRange(model);

    // Then
    verify(viewRangeFeature).perform(any(StockFilterVO.class));
  }

  @Test
  void shouldFindStockRequests() {
    // Given
    var model = getMockStockRequestFilterModel();
    var stockRequestModels = List.of(getMockStockRequestModel());
    var expected = (Page<StockRequestModel>) getMockPage(stockRequestModels, 1, 2, 1);

    // When
    doReturn(expected).when(findStockRequestsFeature).perform(1, model);
    var actual = stockComponent.findStockRequests(1, model);

    // Then
    verify(findStockRequestsFeature).perform(1, model);
    assertThat(actual.getPageNumber()).isEqualTo(expected.getPageNumber());
    assertThat(actual.getTotalRecordCount()).isEqualTo(expected.getTotalRecordCount());
  }

  @Test
  void shouldNotFindStockRequests() {
    // Given
    var model = getMockStockRequestFilterModel();
    var expected = (Page<StockRequestModel>) getMockPage(new ArrayList<>(), 1, 2, 0);

    // When
    doReturn(expected).when(findStockRequestsFeature).perform(1, model);
    var actual = stockComponent.findStockRequests(1, model);

    // Then
    verify(findStockRequestsFeature).perform(1, model);
    assertThat(actual.getPageNumber()).isEqualTo(expected.getPageNumber());
    assertThat(actual.getTotalRecordCount()).isEqualTo(expected.getTotalRecordCount());
  }

  @Test
  void shouldReturnPageViewOfStockHolderLovModel() {
    // Given
    var expected =
        (Page<StockHolderLovModel>) getMockPage(List.of(new StockHolderLovModel()), 1, 1, 1);
    var vo = new StockHolderLovFilterVO();

    // When
    doReturn(vo)
        .when(stockHolderLovFilterMapper)
        .mapModelToVo(any(StockHolderLovFilterModel.class));
    doReturn(expected).when(findStockHolderLovFeature).perform(any(StockHolderLovFilterVO.class));
    var result = stockComponent.findStockHolderLov(new StockHolderLovFilterModel());

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(findStockHolderLovFeature).perform(any(StockHolderLovFilterVO.class));
  }

  @Test
  void shouldReturnRangeModelList() {
    // Given
    var companyCode = "AV";
    var vo = new RangeVO();
    vo.setCompanyCode(companyCode);

    // When
    doReturn(List.of(vo)).when(findRangesFeature).perform(any(RangeFilterVO.class));

    doReturn(new RangeFilterVO()).when(rangeFilterMapper).mapModelToVo(any(RangeFilterModel.class));

    // Then
    assertTrue(
        stockComponent.findRanges(new RangeFilterModel()).stream()
            .anyMatch(e -> e.getCompanyCode().equals(companyCode)));
  }

  @Test
  void shouldRejectStockRequests() {
    // Given
    var vos = List.of(new StockRequestVO());
    var models = List.of(new StockRequestModel());

    // When
    doReturn(vos).when(stockRequestMapper).mapModelsToVos(anyList());
    doNothing().when(rejectStockRequestsFeature).perform(vos);
    stockComponent.rejectStockRequests(models);

    // Then
    verify(rejectStockRequestsFeature).perform(vos);
  }

  @Test
  void shouldReturnPageOfAvailableRanges() {
    // Given
    var page = getMockPage(List.of(new RangeModel()), 1, 1, 1);
    var filter = getMockStockFilterModel();

    // When
    doReturn(page).when(findAvailableRangesFeature).perform(any(StockFilterModel.class));
    var result = stockComponent.findAvailableRanges(filter);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(findAvailableRangesFeature).perform(any(StockFilterModel.class));
  }

  @Test
  void shouldApproveStockRequests() throws BusinessException {
    // Given
    var model = new StockRequestApproveModel();
    var vo = new StockRequestApproveVO();

    // When
    doReturn(vo).when(stockRequestApproveMapper).mapModelToVo(any(StockRequestApproveModel.class));
    doNothing().when(approveStockRequestsFeature).perform(vo);
    stockComponent.approveStockRequests(model);

    // Then
    verify(approveStockRequestsFeature).execute(vo);
  }

  @Test
  void shouldReturnRequestRefNumber() throws BusinessException {
    // Given
    var model = new StockRequestModel();
    var vo = new StockRequestVO();
    var expected = "123";

    // When
    doReturn(vo).when(stockRequestMapper).mapModelToVo(any(StockRequestModel.class));
    doReturn(expected).when(saveStockRequestDetailsFeature).perform(vo);
    stockComponent.saveStockRequestDetails(model);

    // Then
    verify(saveStockRequestDetailsFeature).perform(vo);
  }

  @Test
  void shouldPassWhenStockExists() throws BusinessException {
    final var companyCode = "AV";
    final var stockholderCode = "HQ";
    final var docType = "AWB";
    final var docSubType = "S";

    doNothing().when(checkStockFeature).perform(companyCode, stockholderCode, docType, docSubType);

    assertDoesNotThrow(
        () -> stockComponent.checkStock(companyCode, stockholderCode, docType, docSubType));

    verify(checkStockFeature).perform(companyCode, stockholderCode, docType, docSubType);
  }

  @Test
  void shouldSaveStockAgentMappings() throws BusinessException {
    // Given
    var models = List.of(new StockAgentModel());
    var voList = stockAgentMapper.mapModelToVo(models);

    doReturn(voList).when(stockAgentMapper).mapModelToVo(models);
    doNothing().when(saveStockAgentMappingsFeature).perform(voList);

    assertDoesNotThrow(() -> stockComponent.saveStockAgentMappings(models));

    verify(saveStockAgentMappingsFeature).perform(voList);
  }

  @Test
  void shouldFindStockAgentMappings() {
    // Given
    var page = getMockPage(List.of(new StockAgentModel()), 1, 1, 1);
    var model = new StockAgentFilterModel();
    var vo = new StockAgentFilterVO();

    // When
    doReturn(vo).when(stockAgentFilterMapper).mapModelToVo(model);
    doReturn(page).when(findStockAgentMappingsFeature).perform(vo);
    var result = stockComponent.findStockAgentMappings(model);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(findStockAgentMappingsFeature).perform(vo);
  }

  @Test
  void shouldFindStockRequestDetails() {
    // Given
    var expected = getMockStockRequestModel();
    var filterModel = getMockStockRequestFilterModel();

    // When
    doReturn(expected)
        .when(findStockRequestDetailsFeature)
        .perform(any(StockRequestFilterVO.class));

    // Then
    var actual = stockComponent.findStockRequestDetails(filterModel);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldReturnTotalNoOfDocuments() {
    // Given
    var model = getMockStockFilterModel();

    // When
    doReturn(2).when(findTotalNoOfDocumentsFeature).perform(any(StockFilterVO.class));
    stockComponent.findTotalNoOfDocuments(model);

    // Then
    verify(findTotalNoOfDocumentsFeature).perform(any(StockFilterVO.class));
  }

  @Test
  void shouldValidateStockForVoiding() {
    // Given
    var model = new BlacklistStockModel();

    // When
    doReturn(model).when(validateStockForVoidingFeature).perform(any(BlacklistStockVO.class));
    stockComponent.validateStockForVoiding(model);

    // Then
    verify(validateStockForVoidingFeature).perform(any(BlacklistStockVO.class));
  }

  @Test
  void shouldCancelStockRequest() throws StockBusinessException {
    // When
    doNothing().when(cancelStockRequestFeature).perform(any(StockRequestVO.class));
    stockComponent.cancelStockRequest(new StockRequestModel());

    // Then
    verify(cancelStockRequestFeature).perform(any(StockRequestVO.class));
  }

  @Test
  void shouldReturnPageOfBlacklistedStock() {
    // Given
    var expected = getMockPage(List.of(new StockFilterModel()), 1, DEFAULT_PAGE_SIZE, 1);
    var stockFilterModel = new StockFilterModel();

    // When
    doReturn(expected).when(findBlacklistedStockFeature).perform(stockFilterModel, 1);
    var actual = stockComponent.findBlacklistedStock(stockFilterModel, 1);

    // Then
    assertThat(actual).isEqualTo(expected);
    verify(findBlacklistedStockFeature).perform(stockFilterModel, 1);
  }

  @Test
  void shouldFindDocumentDetails() {
    // Given
    var vo = new StockRequestVO();
    var companyCode = "AV";
    var airlineIdentifier = 1134;
    var documentNumber = "1234123";

    // When
    doReturn(vo)
        .when(findDocumentDetailsFeature)
        .perform(companyCode, airlineIdentifier, documentNumber);
    stockComponent.findDocumentDetails(companyCode, airlineIdentifier, documentNumber);

    // Then
    verify(findDocumentDetailsFeature).perform(companyCode, airlineIdentifier, documentNumber);
  }

  @Test
  void shouldValidateNumericCode() throws BusinessException {
    doReturn(new AirlineValidationModel()).when(validateNumericCodeFeature).perform("IBS", "123");

    var actual = stockComponent.validateNumericCode("IBS", "123");

    assertThat(actual).isNotNull();
    verify(validateNumericCodeFeature).perform("IBS", "123");
  }

  @Test
  void shouldCheckForBlacklistedDocument() {
    doReturn(true).when(checkForBlacklistedDocumentFeature).perform("IBS", "AWB", "9000000");

    var actual = stockComponent.checkForBlacklistedDocument("IBS", "AWB", "9000000");

    assertThat(actual).isTrue();
    verify(checkForBlacklistedDocumentFeature).perform("IBS", "AWB", "9000000");
  }

  @Test
  void shouldFindStockRangeHistoryForPage() {
    // Given
    var filterModel = new StockRangeFilterModel();
    var page = getMockPage(List.of(new StockRangeHistoryModel()), 1, 1, 1);

    // When
    doReturn(page).when(findStockRangeHistoryForPageFeature).perform(any(StockRangeFilterVO.class));
    var result = stockComponent.findStockRangeHistoryForPage(filterModel);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(findStockRangeHistoryForPageFeature).perform(any(StockRangeFilterVO.class));
  }

  @Test
  void shouldFindStockRangeHistory() {
    // Given
    var filterModel = new StockRangeFilterModel();
    var vos = List.of(new StockRangeHistoryVO());

    // When
    doReturn(vos).when(findStockRangeHistoryFeature).perform(any(StockRangeFilterVO.class));
    var result = stockComponent.findStockRangeHistory(filterModel);

    // Then
    assertThat(result.size()).isEqualTo(1);
    verify(findStockRangeHistoryFeature).perform(any(StockRangeFilterVO.class));
  }

  @Test
  void shouldBlacklistStock() throws BusinessException {
    // Given
    var model = getMockBlacklistStockModel();

    // When
    doNothing().when(blacklistStockFeature).perform(any(BlacklistStockVO.class));
    doReturn(getMockBlacklistStockVO()).when(blacklistStockMapper).mapModelToVo(model);
    stockComponent.blacklistStock(model);

    // Then
    verify(blacklistStockFeature).perform(any(BlacklistStockVO.class));
  }

  @Test
  void shouldCheckForStockDetails() {
    // Given
    var filterModel = new DocumentFilterModel();

    // When
    doReturn(false).when(checkForStockDetailsFeature).perform(any(DocumentFilterVO.class));
    var result = stockComponent.isStockDetailsExists(filterModel);

    // Then
    verify(checkForStockDetailsFeature).perform(any(DocumentFilterVO.class));
    assertThat(result).isFalse();
  }

  @Test
  void shouldValidateAgentForStockHolder() throws StockBusinessException {
    // Given
    var documentValidationModel = new AWBDocumentValidationModel();

    // When
    doNothing()
        .when(validateAgentForStockHolderFeature)
        .perform(any(AWBDocumentValidationVO.class));
    stockComponent.validateAgentForStockHolder(documentValidationModel);

    // Then
    verify(validateAgentForStockHolderFeature).perform(any(AWBDocumentValidationVO.class));
  }

  @Test
  void shouldFindAutoPopulateSubtype() throws StockBusinessException {
    // Given
    var model = new DocumentFilterModel();

    // When
    doReturn("S").when(findAutoPopulateSubtypeFeature).perform(any(DocumentFilterVO.class));
    stockComponent.findAutoPopulateSubtype(model);

    // Then
    verify(findAutoPopulateSubtypeFeature).perform(any(DocumentFilterVO.class));
  }

  @Test
  void shouldFindStockHolders() {
    // Given
    var filterModel = new StockHolderFilterModel();
    var page = getMockPage(List.of(new StockHolderFilterModel()), 1, 1, 1);

    // When
    doReturn(page).when(findStockHoldersFeature).perform(any(StockHolderFilterVO.class));
    var result = stockComponent.findStockHolders(filterModel);

    // Then
    assertThat(result.getTotalRecordCount()).isEqualTo(1);
    verify(findStockHoldersFeature).perform(any(StockHolderFilterVO.class));
  }

  @Test
  void shouldSaveStockUtilisation() {
    // Given
    var stockAllocationModel = new StockAllocationModel();
    var status = "B";

    // When
    doNothing()
        .when(saveStockUtilisationFeature)
        .perform(any(StockAllocationVO.class), anyString());
    stockComponent.saveStockUtilisation(stockAllocationModel, status);

    // Then
    verify(saveStockUtilisationFeature).perform(any(StockAllocationVO.class), anyString());
  }

  @Test
  void shouldFindCustomerStockDetails() throws StockBusinessException {
    // Given
    var model = new StockDetailsFilterModel();
    var vo = new StockDetailsVO();

    // When
    doReturn(vo).when(findCustomerStockDetailsFeature).perform(any(StockDetailsFilterVO.class));
    stockComponent.findCustomerStockDetails(model);

    // Then
    verify(findCustomerStockDetailsFeature).perform(any(StockDetailsFilterVO.class));
  }

  @Test
  void shouldReturnDocumentToStock() {
    // Given
    var model = new StockAllocationModel();

    // When
    doNothing().when(returnDocumentToStockFeature).perform(any(StockAllocationVO.class));
    stockComponent.returnDocumentToStock(model);

    // Then
    verify(returnDocumentToStockFeature).perform(any(StockAllocationVO.class));
  }

  @Test
  void shouldFindAWBStockDetailsForPrint() throws StockBusinessException {
    // Given
    var stockFilterModel = getMockStockFilterModel();
    var stockFilterVO = MockModelGenerator.getMockStockFilterVO();
    Collection<RangeVO> collection = new ArrayList<>();

    // When
    doReturn(collection).when(findAWBStockDetailsForPrintFeature).perform(any(StockFilterVO.class));

    doReturn(stockFilterVO).when(stockFilterMapper).mapModelToVo(any(StockFilterModel.class));

    var result = stockComponent.findAWBStockDetailsForPrint(stockFilterModel);
    assertNotNull(result);
  }

  @Test
  void shouldValidateDocument() throws BusinessException {
    // Given
    var documentFilterModel = new DocumentFilterModel();

    // When
    doReturn(new AWBDocumentValidationVO())
        .when(validateDocumentFeature)
        .perform(any(DocumentFilterVO.class));
    stockComponent.validateDocument(documentFilterModel);

    // Then
    verify(validateDocumentFeature).perform(any(DocumentFilterVO.class));
  }

  @Test
  void shouldDeleteStockHolder() throws BusinessException {
    // Given
    var stockHolderDetailsModel = new StockHolderDetailsModel();
    var stockHolderDetailsVO = new StockHolderDetailsVO();

    // When
    doReturn(null).when(deleteStockHolderFeature).execute(any(StockHolderDetailsVO.class));
    stockComponent.deleteStockHolder(stockHolderDetailsModel);

    // Then
    verify(deleteStockHolderFeature).execute(any(StockHolderDetailsVO.class));
  }

  @Test
  void shouldGetStockAgentMappings() {
    // Given
    var stockAgentFilterModel = new StockAgentFilterModel();

    // When
    doReturn(new ArrayList<StockAgentNeoModel>())
        .when(getStockAgentMappingsFeature)
        .perform(any(StockAgentFilterVO.class));
    stockComponent.getStockAgentMappings(stockAgentFilterModel);

    // Then
    verify(getStockAgentMappingsFeature).perform(any(StockAgentFilterVO.class));
  }

  @Test
  void shouldDeleteDocumentFromStock() throws BusinessException {
    // Given
    var model = new DocumentFilterModel();

    // When
    doNothing().when(deleteDocumentFromStockFeature).perform(any(DocumentFilterVO.class));
    stockComponent.deleteDocumentFromStock(model);

    // Then
    verify(deleteDocumentFromStockFeature).perform(any(DocumentFilterVO.class));
  }
}
