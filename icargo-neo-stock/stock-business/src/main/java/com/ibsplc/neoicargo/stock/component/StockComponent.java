package com.ibsplc.neoicargo.stock.component;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
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
import com.ibsplc.neoicargo.stock.model.viewfilter.StockRequestFilterModel;
import com.ibsplc.neoicargo.stock.vo.AWBDocumentValidationVO;
import com.ibsplc.neoicargo.stock.vo.MonitorStockVO;
import com.ibsplc.neoicargo.stock.vo.RangeVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("stockComponent")
@RequiredArgsConstructor
public class StockComponent {

  private final DocumentFilterMapper documentFilterMapper;
  private final StockHolderMapper stockHolderMapper;
  private final StockAllocationMapper stockAllocationMapper;
  private final StockFilterMapper stockFilterMapper;
  private final StockHolderPriorityMapper stockHolderPriorityMapper;
  private final StockRequestMapper stockRequestMapper;
  private final StockRangeHistoryMapper stockRangeHistoryMapper;
  private final RangeMapper rangeMapper;
  private final StockHolderLovFilterMapper stockHolderLovFilterMapper;
  private final RangeFilterMapper rangeFilterMapper;
  private final StockRangeMapper stockRangeMapper;
  private final StockAgentMapper stockAgentMapper;
  private final StockAgentFilterMapper stockAgentFilterMapper;
  private final BlackListStockMapper blackListStockMapper;
  private final StockRangeFilterMapper stockRangeFilterMapper;
  private final FindNextDocumentNumberFeature findNextDocumentNumberFeature;
  private final ValidateStockFeature validateStockFeature;
  private final FindStockHolderDetailsFeature findStockHolderDetailsFeature;
  private final FindApproverCodeFeature findApproverCodeFeature;
  private final FindMonitoringStockHolderDetailsFeature findMonitoringStockHolderDetailsFeature;
  private final AllocateStockFeature allocateStockFeature;
  private final CreateHistoryFeature createHistoryFeature;
  private final FindPrioritiesFeature findPrioritiesFeature;
  private final FindStockHolderTypesFeature findStockHolderTypesFeature;
  private final FindStockRequestsFeature findStockRequestsFeature;
  private final DeleteStockFeature deleteStockFeature;
  private final ValidateStockHoldersFeature validateStockHoldersFeature;
  private final FindStockHolderTypeCodeFeature findStockHolderTypeCodeFeature;
  private final MonitorStockFeature monitorStockFeature;
  private final SaveStockHolderDetailsFeature saveStockHolderDetailsFeature;
  private final ViewRangeFeature viewRangeFeature;
  private final FindStockHolderLovFeature findStockHolderLovFeature;
  private final FindRangesFeature findRangesFeature;
  private final RejectStockRequestsFeature rejectStockRequestsFeature;
  private final FindAvailableRangesFeature findAvailableRangesFeature;
  private final ApproveStockRequestsFeature approveStockRequestsFeature;
  private final StockRequestApproveMapper stockRequestApproveMapper;
  private final SaveStockRequestDetailsFeature saveStockRequestDetailsFeature;
  private final CheckStockFeature checkStockFeature;
  private final SaveStockAgentMappingsFeature saveStockAgentMappingsFeature;
  private final FindStockAgentMappingsFeature findStockAgentMappingsFeature;
  private final FindStockRequestDetailsFeature findStockRequestDetailsFeature;
  private final FindTotalNoOfDocumentsFeature findTotalNoOfDocumentsFeature;
  private final ValidateStockForVoidingFeature validateStockForVoidingFeature;
  private final CancelStockRequestFeature cancelStockRequestFeature;
  private final FindBlacklistedStockFeature findBlacklistedStockFeature;
  private final FindDocumentDetailsFeature findDocumentDetailsFeature;
  private final ValidateNumericCodeFeature validateNumericCodeFeature;
  private final CheckForBlacklistedDocumentFeature checkForBlacklistedDocumentFeature;
  private final FindStockRangeHistoryForPageFeature findStockRangeHistoryForPageFeature;
  private final FindStockRangeHistoryFeature findStockRangeHistoryFeature;
  private final BlacklistStockFeature blacklistStockFeature;
  private final StockHolderFilterModelMapper stockHolderFilterModelMapper;
  private final FindStockHoldersFeature findStockHoldersFeature;
  private final SaveStockUtilisationFeature saveStockUtilisationFeature;
  private final IsStockDetailsExistsFeature checkForStockDetailsFeature;
  private final AWBDocumentValidationMapper aWBDocumentValidationMapper;
  private final ValidateAgentForStockHolderFeature validateAgentForStockHolderFeature;
  private final FindAutoPopulateSubtypeMapper findAutoPopulateSubtypeMapper;
  private final FindAutoPopulateSubtypeFeature findAutoPopulateSubtypeFeature;
  private final CustomerStockDetailsMapper customerStockDetailsMapper;
  private final FindCustomerStockDetailsFeature findCustomerStockDetailsFeature;
  private final ReturnDocumentToStockFeature returnDocumentToStockFeature;
  private final FindAWBStockDetailsForPrintFeature findAWBStockDetailsForPrintFeature;
  private final ValidateDocumentFeature validateDocumentFeature;
  private final DeleteStockHolderFeature deleteStockHolderFeature;
  private final StockHolderDetailsModelMapper stockHolderDetailsModelMapper;
  private final GetStockAgentMappingsFeature getStockAgentMappingsFeature;
  private final DeleteDocumentFromStockFeature deleteDocumentFromStockFeature;

  public DocumentValidation findNextDocumentNumber(DocumentFilter documentFilter)
      throws BusinessException {
    return findNextDocumentNumberFeature.perform(documentFilterMapper.mapModelToVo(documentFilter));
  }

  public DocumentValidation validateStock(
      String prefix, String mstdocnum, DocumentFilter documentFilter) {
    var documentFilterVO = documentFilterMapper.mapModelToVo(documentFilter);
    documentFilterVO.setPrefix(prefix);
    documentFilterVO.setMstdocnum(mstdocnum);
    documentFilterVO.setDocumentType("AWB");
    return validateStockFeature.execute(documentFilterVO);
  }

  public StockHolderModel findStockHolderDetails(String companyCode, String stockHolderCode) {
    return stockHolderMapper.mapVoToModel(
        findStockHolderDetailsFeature.perform(companyCode, stockHolderCode));
  }

  public String findApproverCode(
      String companyCode,
      String stockHolderCode,
      // Integer airlineId,
      String docType,
      String docSubType) {
    return findApproverCodeFeature.perform(companyCode, stockHolderCode, docType, docSubType);
  }

  public StockAllocationModel allocateStock(StockAllocationModel stockAllocationModel) {
    return allocateStockFeature.execute(stockAllocationMapper.mapModelToVo(stockAllocationModel));
  }

  public void createHistory(StockAllocationModel model, String status) {
    var vo = stockAllocationMapper.mapModelToVo(model);
    createHistoryFeature.perform(vo, status);
  }

  public MonitorStockVO findMonitoringStockHolderDetails(StockFilterModel stockFilterModel) {
    var vo = stockFilterMapper.mapModelToVo(stockFilterModel);
    return findMonitoringStockHolderDetailsFeature.execute(vo);
  }

  public List<StockHolderPriorityModel> findStockHolderTypes(String companyCode) {
    var stockHolderVOs = findStockHolderTypesFeature.perform(companyCode);
    return stockHolderPriorityMapper.mapVoToModel(stockHolderVOs);
  }

  public List<StockHolderPriorityModel> findPriorities(
      String companyCode, List<String> stockHolderCodes) {
    var stockHolderPriorityModels = findPrioritiesFeature.perform(companyCode, stockHolderCodes);
    return stockHolderPriorityMapper.mapVoToModel(stockHolderPriorityModels);
  }

  public void validateStockHolders(String companyCode, List<String> stockHolderCodes)
      throws StockBusinessException {
    validateStockHoldersFeature.perform(companyCode, stockHolderCodes);
  }

  public void findStockHolderTypeCode(StockRequestModel model) throws BusinessException {
    findStockHolderTypeCodeFeature.perform(stockRequestMapper.mapModelToVo(model));
  }
`
  public Page<MonitorStockModel> monitorStock(StockFilterModel stockFilterModel) {
    var vo = stockFilterMapper.mapModelToVo(stockFilterModel);
    return monitorStockFeature.execute(vo);
  }

  public void deleteStock(List<RangeModel> rangesModel) throws BusinessException {
    deleteStockFeature.perform(rangeMapper.mapModelToVo(rangesModel));
  }

  public void saveStockHolderDetails(StockHolderModel stockHolderModel) {
    final var stockHolderVO = stockHolderMapper.mapModelToVo(stockHolderModel);
    saveStockHolderDetailsFeature.execute(stockHolderVO);
  }

  public StockRangeModel viewRange(StockFilterModel stockFilterModel) {
    var vo = stockFilterMapper.mapModelToVo(stockFilterModel);
    var stockRangeVO = viewRangeFeature.perform(vo);
    return stockRangeMapper.mapVoToModel(stockRangeVO);
  }

  public Page<StockRequestModel> findStockRequests(
      int displayPage, StockRequestFilterModel stockRequestFilterModel) {
    return findStockRequestsFeature.perform(displayPage, stockRequestFilterModel);
  }

  public Page<StockHolderLovModel> findStockHolderLov(
      StockHolderLovFilterModel stockHolderLovFilterModel) {
    var vo = stockHolderLovFilterMapper.mapModelToVo(stockHolderLovFilterModel);
    return findStockHolderLovFeature.perform(vo);
  }

  public List<RangeModel> findRanges(RangeFilterModel rangeFilterModel) {
    var rangeVOs = findRangesFeature.perform(rangeFilterMapper.mapModelToVo(rangeFilterModel));

    return rangeMapper.mapVoToModel(rangeVOs);
  }

  public void rejectStockRequests(List<StockRequestModel> stockRequestsModels) {
    var stockRequestVOS = stockRequestMapper.mapModelsToVos(stockRequestsModels);
    rejectStockRequestsFeature.perform(stockRequestVOS);
  }

  public Page<RangeModel> findAvailableRanges(StockFilterModel stockFilterModel) {
    return findAvailableRangesFeature.perform(stockFilterModel);
  }

  public void approveStockRequests(StockRequestApproveModel stockRequestApproveModel) {
    approveStockRequestsFeature.execute(
        stockRequestApproveMapper.mapModelToVo(stockRequestApproveModel));
  }

  public String saveStockRequestDetails(StockRequestModel stockRequestModel)
      throws BusinessException {
    var vo = stockRequestMapper.mapModelToVo(stockRequestModel);
    return saveStockRequestDetailsFeature.perform(vo);
  }

  public void checkStock(
      String companyCode, String stockHolderCode, String docType, String docSubType)
      throws BusinessException {
    checkStockFeature.perform(companyCode, stockHolderCode, docType, docSubType);
  }

  public void saveStockAgentMappings(List<StockAgentModel> stockAgentModels)
      throws BusinessException {
    saveStockAgentMappingsFeature.perform(stockAgentMapper.mapModelToVo(stockAgentModels));
  }

  public Page<StockAgentModel> findStockAgentMappings(StockAgentFilterModel stockAgentFilterModel) {
    return findStockAgentMappingsFeature.perform(
        stockAgentFilterMapper.mapModelToVo(stockAgentFilterModel));
  }

  public StockRequestModel findStockRequestDetails(
      StockRequestFilterModel stockRequestFilterModel) {
    var vo = stockRequestMapper.mapModelToVo(stockRequestFilterModel);
    return findStockRequestDetailsFeature.perform(vo);
  }

  public int findTotalNoOfDocuments(StockFilterModel stockFilterModel) {
    var stockFilterVO = stockFilterMapper.mapModelToVo(stockFilterModel);
    return findTotalNoOfDocumentsFeature.perform(stockFilterVO);
  }

  public BlacklistStockModel validateStockForVoiding(BlacklistStockModel blacklistStockModel) {
    var blacklistStockVO = blackListStockMapper.mapModelToVo(blacklistStockModel);
    return validateStockForVoidingFeature.perform(blacklistStockVO);
  }

  public void cancelStockRequest(StockRequestModel stockRequestModel)
      throws StockBusinessException {
    var stockRequestVO = stockRequestMapper.mapModelToVo(stockRequestModel);
    cancelStockRequestFeature.perform(stockRequestVO);
  }

  public Page<BlacklistStockModel> findBlacklistedStock(
      StockFilterModel stockFilterModel, int displayPage) {
    return findBlacklistedStockFeature.perform(stockFilterModel, displayPage);
  }

  public StockRequestModel findDocumentDetails(
      String companyCode, int airlineIdentifier, String documentNumber) {
    var stockRequestVO =
        findDocumentDetailsFeature.perform(companyCode, airlineIdentifier, documentNumber);
    return stockRequestMapper.mapVoToModel(stockRequestVO);
  }

  public AirlineValidationModel validateNumericCode(String companyCode, String shipmentPrefix)
      throws BusinessException {
    return validateNumericCodeFeature.perform(companyCode, shipmentPrefix);
  }

  public Boolean checkForBlacklistedDocument(
      String companyCode, String doctype, String documentNumber) {
    return checkForBlacklistedDocumentFeature.perform(companyCode, doctype, documentNumber);
  }

  public Page<StockRangeHistoryModel> findStockRangeHistoryForPage(
      StockRangeFilterModel stockRangeFilterModel) {
    var vo = stockRangeFilterMapper.mapModelToVo(stockRangeFilterModel);
    return findStockRangeHistoryForPageFeature.perform(vo);
  }

  public List<StockRangeHistoryModel> findStockRangeHistory(
      StockRangeFilterModel stockRangeFilterModel) {
    var vo = stockRangeFilterMapper.mapModelToVo(stockRangeFilterModel);
    return stockRangeHistoryMapper.mapVoToModel(findStockRangeHistoryFeature.perform(vo));
  }

  public void blacklistStock(BlacklistStockModel blacklistStockModel) throws BusinessException {
    var vo = blackListStockMapper.mapModelToVo(blacklistStockModel);
    blacklistStockFeature.perform(vo);
  }

  public Boolean isStockDetailsExists(DocumentFilterModel documentFilterModel) {
    var vo = documentFilterMapper.mapModelToVo(documentFilterModel);
    return checkForStockDetailsFeature.perform(vo);
  }

  public void validateAgentForStockHolder(AWBDocumentValidationModel documentValidationModel)
      throws StockBusinessException {
    validateAgentForStockHolderFeature.perform(
        aWBDocumentValidationMapper.mapModelToVo(documentValidationModel));
  }

  public String findAutoPopulateSubtype(DocumentFilterModel documentFilterModel)
      throws StockBusinessException {
    var documentFilterVO = findAutoPopulateSubtypeMapper.mapModelToVo(documentFilterModel);
    return findAutoPopulateSubtypeFeature.perform(documentFilterVO);
  }

  public Page<StockHolderDetailsModel> findStockHolders(StockHolderFilterModel filterModel) {
    return findStockHoldersFeature.perform(stockHolderFilterModelMapper.mapModelToVo(filterModel));
  }

  public void saveStockUtilisation(StockAllocationModel stockAllocationModel, String status) {
    var vo = stockAllocationMapper.mapModelToVo(stockAllocationModel);
    saveStockUtilisationFeature.perform(vo, status);
  }

  public StockDetailsModel findCustomerStockDetails(StockDetailsFilterModel stockDetailsFilterModel)
      throws StockBusinessException {
    var stockDetailsVO =
        findCustomerStockDetailsFeature.perform(
            customerStockDetailsMapper.mapModelToVo(stockDetailsFilterModel));
    return customerStockDetailsMapper.mapVoToModel(stockDetailsVO);
  }

  public void returnDocumentToStock(StockAllocationModel stockAllocationModel) {
    returnDocumentToStockFeature.perform(stockAllocationMapper.mapModelToVo(stockAllocationModel));
  }

  public Collection<RangeModel> findAWBStockDetailsForPrint(StockFilterModel stockFilterModel)
      throws StockBusinessException {
    var vo = stockFilterMapper.mapModelToVo(stockFilterModel);
    Collection<RangeVO> rangeVOs = findAWBStockDetailsForPrintFeature.perform(vo);
    return rangeMapper.mapModelToVo(rangeVOs);
  }

  public DocumentValidationModel validateDocument(DocumentFilterModel documentFilterModel)
      throws BusinessException {
    AWBDocumentValidationVO documentValidationVO =
        (AWBDocumentValidationVO)
            validateDocumentFeature.perform(documentFilterMapper.mapModelToVo(documentFilterModel));
    return aWBDocumentValidationMapper.mapVoToModel(documentValidationVO);
  }

  public void deleteStockHolder(StockHolderDetailsModel stockHolderDetailsModel) {
    deleteStockHolderFeature.execute(
        stockHolderDetailsModelMapper.mapModelToVo(stockHolderDetailsModel));
  }

  public List<StockAgentNeoModel> getStockAgentMappings(
      StockAgentFilterModel stockAgentFilterModel) {
    return getStockAgentMappingsFeature.perform(
        stockAgentFilterMapper.mapModelToVo(stockAgentFilterModel));
  }

  public void deleteDocumentFromStock(DocumentFilterModel documentFilterModel)
      throws BusinessException {
    var vo = documentFilterMapper.mapModelToVo(documentFilterModel);
    deleteDocumentFromStockFeature.perform(vo);
  }
}
